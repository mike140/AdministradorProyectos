/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package administradorproyectos;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class proyecto_usuarioController implements Initializable {
    
    private AdministradorProyectos main;
    
    @FXML
    private ComboBox usuario;
    @FXML
    private Button agregar;
    
    ArrayList<String> usuarios_nombres;
    ArrayList<Integer> usuarios_id;
    DataBase db;
    
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
        
        db = main.getDataBase();
        usuarios_nombres = new ArrayList();
        
        try{
            ResultSet query = db.select("SELECT `todos`.`NOMBRE` FROM `usuario` AS `todos` LEFT JOIN (SELECT `usuario`.`ID` FROM `usuario` LEFT JOIN `proyecto_usuario` ON `usuario`.`ID` = `proyecto_usuario`.`USUARIO_ID` WHERE `proyecto_usuario`.`PROYECTO_ID` = " + main.getProyecto_id() + ") AS `registrados` ON `todos`.`ID` = `registrados`.`ID` WHERE `registrados`.`ID` IS NULL");
        
            query.beforeFirst();

            while( query.next() ){
                usuarios_nombres.add( query.getString(1) );
            }
        }catch(SQLException e){
            
        }
        

        ObservableList<String> options = FXCollections.observableArrayList( usuarios_nombres );
        usuario.setItems(options);
        usuario.setValue("Escoja el usuario: ");
    }
    
    @FXML
    public void agregar() {
        //String user = usuario.getSelectionModel().getSelectedItem().toString();
        int index_usuario = usuario.getSelectionModel().getSelectedIndex();
        String nombre = usuarios_nombres.get(index_usuario);
        
        ArrayList<Integer> usuario_id = db.getIndexOf("usuario", "NOMBRE", nombre);
        String values[] = { String.valueOf( usuario_id.get(0) ), main.getProyecto_id(), "2" };
        
        if(!db.insert("proyecto_usuario", values)) {
            JOptionPane.showMessageDialog(null, "Error ya esta agregado el usuario");
            return;
        } else {
            main.cambiarDePantalla("dashboard.fxml", main.getProyecto().getTitulo()); 
        }
    }
    
    @FXML
    public void salir(){
        main.cambiarDePantalla("dashboard.fxml", main.getProyecto().getTitulo()); 
    }
    
}
