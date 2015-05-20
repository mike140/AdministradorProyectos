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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    @FXML
    private Button asignar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        main = AdministradorProyectos.getInstance();
        
        DataBase db = main.getDataBase();
        System.out.println(main.getUsuario_id());
        String nombres[] = db.getValuesInColumn("`usuario`, `proyecto_usuario`", "NOMBRE", "WHERE `usuario`.`ID` = `proyecto_usuario`.`USUARIO_ID` AND `proyecto_usuario`.`PROYECTO_ID` = " + main.getProyecto_id() );
        ArrayList<Integer> tareas = db.getIndexOf("tarea", "PROYECTO_ID", String.valueOf( main.getProyecto_id()) );
        
        ObservableList<String> options = FXCollections.observableArrayList( nombres );
        usuario.setItems(options);
        usuario.setValue("Escoja el usuario: ");
        
        ObservableList<Integer> options2 = FXCollections.observableArrayList( tareas );
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
        
        String arreglo[] = { String.valueOf(user_id.get(0)) , String.valueOf(task_id.get(0))};
        
        db.insert("tarea_usuario", arreglo );
        JOptionPane.showMessageDialog(null, "Se ha asignado la tarea al usuario");
    }
    
    @FXML
    public void salir(){
        
            String proyectName = main.getDataBase().getValueOf("proyecto", "TITULO", Integer.valueOf(main.getProyecto_id()) );
            main.cambiarDePantalla("dashboard.fxml", proyectName);
            
    }
    
    
    
}
