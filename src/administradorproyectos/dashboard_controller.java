/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package administradorproyectos;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 *
 * @author OskR219
 */
public class dashboard_controller implements Initializable {
    
    private AdministradorProyectos main;
    
    @FXML
    private TextArea mensaje;
    @FXML
    private Button enviar_mensaje;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        main = AdministradorProyectos.getInstance();
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
        
    }
}
