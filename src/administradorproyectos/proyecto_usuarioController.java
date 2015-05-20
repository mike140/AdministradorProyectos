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
public class proyecto_usuarioController implements Initializable {
    
    private AdministradorProyectos main;
    
    @FXML
    private ComboBox usuario;
    @FXML
    private Button agregar;
    
    ArrayList<String> usuarios_nombres;
    DataBase db;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        main = AdministradorProyectos.getInstance();
        db = main.getDataBase();
        usuarios_nombres = new ArrayList();
        
        HashMap<String, String> registro;
        int numeroRegistros = db.getCount("proyecto_usuario");
        String nombre;
        
        for(int i = 1; i <= numeroRegistros; i++){
            registro = db.fetchArray("proyecto_usuario", i);
            nombre = db.getValueOf("usuario", "NOMBRE", i);
            
            if( !registro.get("PROYECTO_ID").matches(main.getProyecto_id()) && !usuarios_nombres.contains(nombre) ){
                usuarios_nombres.add(nombre);
            }
                
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
        
        db.insert("proyecto_usuario", values);
    }
    
    @FXML
    public void salir(){
            String proyectName = main.getDataBase().getValueOf("proyecto", "TITULO", Integer.valueOf(main.getProyecto_id()) );
            main.cambiarDePantalla("dashboard.fxml", proyectName); 
    }
    
}
