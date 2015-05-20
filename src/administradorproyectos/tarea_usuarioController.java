/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package administradorproyectos;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author OskR219
 */
public class tarea_usuarioController implements Initializable {
    
    private AdministradorProyectos main;
    
    @FXML
    private ComboBox usuario;
    @FXML
    private ComboBox tarea;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        main = AdministradorProyectos.getInstance();
        main.getMainStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            
            @Override
            public void handle(WindowEvent ev) {
                ev.consume();
                salir();
            }
            
        });
        DataBase db = main.getDataBase();
        System.out.println(main.getUsuario_id());
        String nombres[] = db.getValuesInColumn("`usuario`, `proyecto_usuario`", "NOMBRE", "WHERE `usuario`.`ID` = `proyecto_usuario`.`USUARIO_ID` AND `proyecto_usuario`.`PROYECTO_ID` = " + main.getProyecto_id() );
        ArrayList<Integer> tareas = db.getIndexOf("tarea", "PROYECTO_ID", String.valueOf( main.getProyecto_id()) );
        
        HashMap<String, String> registro;
        ArrayList<String> tareas_nombres = new ArrayList();
        
        for(int id_tarea : tareas){
            registro = db.fetchArray("tarea", id_tarea);
            tareas_nombres.add( registro.get("TITULO") );
        }
        
        ObservableList<String> options = FXCollections.observableArrayList( nombres );
        usuario.setItems(options);
        usuario.setValue("Escoja el usuario: ");
        
        ObservableList<String> options2 = FXCollections.observableArrayList( tareas_nombres );
        tarea.setItems(options2);
        tarea.setValue("Asignar a tarea: ");
    }
    

    
    @FXML
    public void asignar() {
        DataBase db = main.getDataBase();
        
        String user = usuario.getSelectionModel().getSelectedItem().toString();
        String task = tarea.getSelectionModel().getSelectedItem().toString();
        
        ArrayList<Integer> user_id = db.getIndexOf("usuario", "NOMBRE", user);
        ArrayList<Integer> task_id = db.getIndexOf("tarea", "TITULO", task);
        
        System.out.println("Primer valor: " + String.valueOf(user_id.get(0)));
        System.out.println( "Segundo valor: " +String.valueOf(task_id.get(0)));
        
        String arreglo[] = { String.valueOf(user_id.get(0)) , String.valueOf(task_id.get(0))};
        
        
        if(!db.insert("tarea_usuario", arreglo )) {
            JOptionPane.showMessageDialog(null, "Error ya esta asignada la tarea al usuario");
            return;
        } else {
            JOptionPane.showMessageDialog(null, "Se ha asignado la tarea al usuario");
            main.cambiarDePantalla("dashboard.fxml", main.getProyecto().getTitulo());
        }
        
    }
    
    @FXML
    public void salir(){
        main.cambiarDePantalla("dashboard.fxml", main.getProyecto().getTitulo());
            
    }
    
    
    
}
