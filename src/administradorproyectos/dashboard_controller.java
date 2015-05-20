/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package administradorproyectos;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;
import netscape.javascript.JSObject;

/**
 *
 * @author OskR219
 */
public class dashboard_controller implements Initializable {
    
    final String COLOR = "#4bc0fd";
    private String proyecto_id;
    
    private AdministradorProyectos main;
    
    @FXML
    private TextArea mensaje;
    @FXML
    private Button enviar_mensaje;
    @FXML
    private WebView calendario;
    
    @FXML
    private TextArea lista_mensajes;
    @FXML
    private Accordion tareas;
    @FXML
    private AnchorPane estadisticas;
    @FXML
    private MenuItem menuProyecto;
    @FXML
    private MenuItem menuUsuario;
    @FXML
    private MenuItem menuTarea;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        main = AdministradorProyectos.getInstance();
        main.getMainStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            
            @Override
            public void handle(WindowEvent ev) {
                ev.consume();
                salir();
            }
            
        });
        if(!main.isAdminProyecto()) {
            menuProyecto.setDisable(true);
            menuUsuario.setDisable(true);
            menuTarea.setDisable(true);
        }
        proyecto_id = main.getProyecto_id();
        calendario.getEngine().load(this.getClass().getResource("scheduler/index.html").toExternalForm());
        calendario.getEngine().getLoadWorker().stateProperty().addListener(
            new ChangeListener<State>() {
                @Override
                public void changed(ObservableValue<? extends State> ov,
                    State oldState, State newState) {
                    if (newState == State.SUCCEEDED) {
                            JSObject win = (JSObject) calendario.getEngine().executeScript("window");
                            win.setMember("app", new JavaApp());
                            Iterator<Map.Entry<String, Tarea>> iterator = main.getTareas().entrySet().iterator() ;
                            while(iterator.hasNext()){
                                Map.Entry<String, Tarea> tarea = iterator.next();
                                scriptAgregarEvento(
                                    tarea.getKey(), 
                                    tarea.getValue().getTitulo(), 
                                    tarea.getValue().getFecha_inicio().split("/")[2] + "-" + tarea.getValue().getFecha_inicio().split("/")[1] + "-" + tarea.getValue().getFecha_inicio().split("/")[0],
                                    tarea.getValue().getFecha_fin().split("/")[2] + "-" + tarea.getValue().getFecha_fin().split("/")[1] + "-" + tarea.getValue().getFecha_fin().split("/")[0],
                                    COLOR
                                );
                            }
                        }
                    }
                }
        );
        cargarMensajes();
        Iterator<Map.Entry<String, Tarea>> iterator = main.getTareasUsuario().entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Tarea> tarea = iterator.next();
            final String id = tarea.getKey();
            VBox box = new VBox(5);
            final CheckBox estado = new CheckBox("¿Completada?");
            
            if(tarea.getValue().isEstado()) {
                estado.setSelected(true);
            } else {
                estado.setSelected(false);
            }
            estado.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent event) {
                    if(estado.isSelected()){
                        main.getDataBase().finalizeTask(Integer.parseInt(id));
                    } else {
                        main.getDataBase().unfinaliceTask(Integer.parseInt(id));
                    }
                    main.cambiarDePantalla("dashboard.fxml", main.getProyecto().getTitulo());
                }
                
            });
            
            
            Text descripcion = new Text(tarea.getValue().getDescripcion());
            descripcion.setWrappingWidth(200);
            box.getChildren().add(descripcion);
            box.getChildren().add(estado);
            if(main.isAdminProyecto()) {
                Button eliminar = new Button("Eliminar");
                eliminar.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        main.getDataBase().removeIndex("`tarea`", Integer.parseInt(id));
                        main.cambiarDePantalla("dashboard.fxml", main.getProyecto().getTitulo());
                    }

                });
                box.getChildren().add(eliminar);
            }          
            TitledPane t = new TitledPane(tarea.getValue().getTitulo(), box);
            tareas.getPanes().add(t);
        }
        try {
            estadisticas.getChildren().add(crearGrafica());
        } catch (SQLException ex) {
            
        }
    }
    
    protected BarChart<Number, String> crearGrafica() throws SQLException {
        ResultSet query = main.getDataBase().select("SELECT COUNT(*) AS TOTAL, SUM(IF(`tarea`.`ESTADO` = 1, 1, 0)) AS COMPLEATADAS, `usuario`.`NOMBRE` FROM `tarea_usuario`, `usuario`, `tarea` WHERE `tarea_usuario`.`TAREA_ID` = `tarea`.`ID` AND `usuario`.`ID` = `tarea_usuario`.`USUARIO_ID` AND `tarea`.`PROYECTO_ID` = " + main.getProyecto_id() + " GROUP BY  `usuario`.`NOMBRE`");
    
        query.beforeFirst();  
        ArrayList<String> nombres = new ArrayList<String>();
        ArrayList<String> tareas = new ArrayList<String>();
        ArrayList<String> completadas = new ArrayList<String>();
        while( query.next() ){  

            nombres.add(query.getString(3));
            tareas.add(query.getString(1) ); 
            completadas.add(query.getString(2) ); 
        }
        final String[] nombresArray = new String[nombres.size()];
        
        nombres.toArray(nombresArray);
        final CategoryAxis yAxis = new CategoryAxis();
        final NumberAxis xAxis = new NumberAxis();
        final BarChart<Number,String> bc = new BarChart<Number,String>(xAxis,yAxis);
        // setup chart
        bc.setTitle("Tareas por usuario");
        yAxis.setLabel("Usuarios");
        yAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(nombresArray)));
        xAxis.setLabel("Tareas");
        // add starting data
        XYChart.Series<Number,String> series1 = new XYChart.Series<Number,String>();
        series1.setName("Totales");
        XYChart.Series<Number,String> series2 = new XYChart.Series<Number,String>();
        series2.setName("Completadas");
        for(int i = 0; i < nombres.size(); i++) {
            series1.getData().add(new XYChart.Data<Number,String>(Integer.parseInt(tareas.get(i)), nombresArray[i]));
        }
        for(int i = 0; i < nombres.size(); i++) {
            series2.getData().add(new XYChart.Data<Number,String>(Integer.parseInt(completadas.get(i)), nombresArray[i]));
        }
        bc.getData().add(series1);
        bc.getData().add(series2);
        return bc;
    }
    
    private void scriptAgregarEvento(String id, String evento, String fechaInicio, String fechaTermino, String color) {
        calendario.getEngine().executeScript("agregarEvento('" + id + "', '" + evento + "', '" + fechaInicio + "', '" + fechaTermino + "', '" + color + "');");
    }
    
    @FXML
    public void salir(){
        if( JOptionPane.showConfirmDialog(null, "¿Está seguro que desea salir?") == 0 )
            main.cambiarDePantalla("login.fxml", "Task Builder");
    }
    
    @FXML
    public void proyecto(){
        main.cambiarDePantalla("proyecto_usuario.fxml", "Agregar usuarios al proyecto");
    }
    
    @FXML
    public void tarea(){
            main.cambiarDePantalla("tarea.fxml", "Nueva Tarea");
    }
    
    public void usuario(){
            main.cambiarDePantalla("tarea_usuario.fxml", "Asignar Usuario");
    }
    
    @FXML
    public void enviarMensaje() {  
        DataBase db = main.getDataBase();
        Connection connection = db.getConnection();
        String id_proyecto = main.getProyecto_id();
        
        HashMap<String, String> proyecto = db.fetchArray("proyecto", Integer.valueOf(id_proyecto) );     
        String values[] = { proyecto_id, main.getUsuario_id(), mensaje.getText(), LocalDate.now().toString() };
        db.insert("mensaje", values);
        mensaje.setText("");
        
        String mensajeTotal = "";
            
        try{
            Statement query = (Statement) connection.createStatement();
            String comando = "SELECT `usuario`.`NOMBRE`, `mensaje`.`FECHA`, `mensaje`.`MENSAJE`  FROM `mensaje`, `usuario` WHERE `PROYECTO_ID` = " + id_proyecto + " AND `usuario`.`ID` = `mensaje`.`USUARIO_ID` ORDER BY `mensaje`.`ID` DESC";
            ResultSet rs = query.executeQuery(comando);
            rs.beforeFirst();

            while( rs.next() ){
                mensajeTotal += rs.getString(1) + " -- " + rs.getString(2);
                mensajeTotal += "\n" + rs.getString(3) + "\n\n";
            }

        }catch(SQLException e){
            System.out.println("No puedo");
        }
        lista_mensajes.setText(mensajeTotal);
    }
    
    public void cargarMensajes() {  
        DataBase db = main.getDataBase();
        Connection connection = db.getConnection();
        String id_proyecto = main.getProyecto_id();
        
        String mensajeTotal = "";
            
        try{
            Statement query = (Statement) connection.createStatement();
            String comando = "SELECT `usuario`.`NOMBRE`, `mensaje`.`FECHA`, `mensaje`.`MENSAJE`  FROM `mensaje`, `usuario` WHERE `PROYECTO_ID` = " + id_proyecto + " AND `usuario`.`ID` = `mensaje`.`USUARIO_ID`ORDER BY `mensaje`.`ID` DESC";
            ResultSet rs = query.executeQuery(comando);
            rs.beforeFirst();

            while( rs.next() ){
                mensajeTotal += rs.getString(1) + " -- " + rs.getString(2);
                mensajeTotal += "\n" + rs.getString(3) + "\n\n";
            }

        }catch(SQLException e){
            System.out.println("No puedo");
        }
        lista_mensajes.setText(mensajeTotal);
    }
        

    
    public class JavaApp {
        
        public void evento(String evento) {
            JOptionPane.showMessageDialog(null, "Descripcion:\n " + main.getTareas().get(evento).getDescripcion());
        }
    }
    
}
