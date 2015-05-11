/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package administradorproyectos;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 *
 * @author OskR219
 */
public class homeController implements Initializable {
    
    private AdministradorProyectos main;
    
    @FXML
    private Button abrirBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        main = AdministradorProyectos.getInstance();
    } 
    
    @FXML
    public void abrirProyecto() {
        main.abrirProyectoGUI();
    }
    
    @FXML
    public void crearProyecto() {
        main.crearProyectoGUI();
    }
    
}
