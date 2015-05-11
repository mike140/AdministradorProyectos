/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package administradorproyectos;

import java.net.URL;
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
    public void registrarseBtn() {
        boolean valida = main.nuevoUsuario(nombre.getText(), apellido.getText(), correo.getText(), password.getText());
        
        if(valida){
            JOptionPane.showMessageDialog(null, "Se ha registrado con éxito al Usuario");
            main.cambiarDePantalla("usuario.fxml");
        }
            
        return;
    }
    
}
