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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javax.swing.JOptionPane;
import netscape.javascript.JSObject;

/**
 *
 * @author OskR219
 */
public class dashboard_controller implements Initializable {
    
    final String COLOR = "#4bc0fd";
    
    private AdministradorProyectos main;
    
    @FXML
    private TextArea mensaje;
    @FXML
    private Button enviar_mensaje;
    @FXML
    private WebView calendario;
    @FXML
    private String proyecto_id;
    @FXML
    private TextArea lista_mensajes;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        main = AdministradorProyectos.getInstance();
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
        
        
    }
    
    private void scriptAgregarEvento(String id, String evento, String fechaInicio, String fechaTermino, String color) {
        calendario.getEngine().executeScript("agregarEvento('" + id + "', '" + evento + "', '" + fechaInicio + "', '" + fechaTermino + "', '" + color + "');");
    }
    
    @FXML
    public void salir(){
        if( JOptionPane.showConfirmDialog(null, "¿Está seguro que desea salir del proyecto actual?") == 0 )
            main.cambiarDePantalla("login.fxml", "Task Builder");
    }
    
    @FXML
    public void proyecto(){
        main.entrarGUI();
    }
    
    @FXML
    public void enviarMensaje(){  
        DataBase db = main.getDataBase();
        Connection connection = db.getConnection();
        String id_proyecto = main.getProyecto_id();
        
        HashMap<String, String> proyecto = db.fetchArray("proyecto", Integer.valueOf(id_proyecto) );     
        String values[] = { proyecto_id, main.getUsuario_id(), mensaje.getText(), LocalDate.now().toString() };
        db.insert("mensaje", values);
        
        String mensajeTotal = "";
            
            try{
                Statement query = (Statement) connection.createStatement();
                String comando = "SELECT `usuario`.`NOMBRE`, `mensaje`.`MENSAJE`, `mensaje`.`FECHA`  FROM `mensaje`, `usuario` WHERE `PROYECTO_ID` = " + id_proyecto + " AND `usuario`.`ID` = `mensaje`.`USUARIO_ID`";
                ResultSet rs = query.executeQuery(comando);
                rs.beforeFirst();
                
                while( rs.next() ){
                    for(int j = 1; j <= 3; j++)
                        mensajeTotal += rs.getString(j) + "\n";
                }
                mensajeTotal += "\n\n";

            }catch(SQLException e){
                System.out.println("No puedo");
            }
            lista_mensajes.setText(mensajeTotal);
        }
        

    
    public class JavaApp {
        
        public void evento(String evento) {
            JOptionPane.showMessageDialog(null, "Descripcion:\n " + main.getTareas().get(evento).getDescripcion() + "!");
        }
    }
    
}
