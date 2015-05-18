/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package administradorproyectos;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 *
 * @author 130034
 */
public class registerController implements Initializable {
    
    private AdministradorProyectos main;
    
    @FXML
    private TextField nombre;
    @FXML
    private TextField apellido;
    @FXML
    private TextField correo;
    @FXML
    private TextField password;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        main = AdministradorProyectos.getInstance();
    }
    
    @FXML
    public void salir(){
        if( JOptionPane.showConfirmDialog(null, "¿Está seguro que desea salir?") == 0 )
            main.cambiarDePantalla("login.fxml", "Task Builder");
    }
    
    @FXML
    public void registrarseBtn() {
        DataBase db = main.getDataBase();
        
        ArrayList<Integer> lista = db.getIndexOf("usuario", "CORREO", correo.getText().toUpperCase() );
        
        if( !lista.isEmpty() ){
            JOptionPane.showMessageDialog(null, "Lo siento, el correo electrónico ya se encuentra registrado");
            return;
        }
        
        boolean valida = main.nuevoUsuario(nombre.getText(), apellido.getText(), correo.getText(), password.getText());
        
        if(valida){
            JOptionPane.showMessageDialog(null, "Se ha registrado con éxito al Usuario");
            main.cambiarDePantalla("login.fxml", "Task Builder");
        }
    }
    
}
