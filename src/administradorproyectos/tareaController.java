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
public class tareaController implements Initializable {
    
    private AdministradorProyectos main;
    
    @FXML
    private TextField titulo;
    @FXML
    private TextArea descripcion;
    @FXML
    private DatePicker fecha_inicio;
    @FXML
    private DatePicker fecha_fin;
    @FXML
    private ComboBox proyecto;
    @FXML
    private Button agregar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        main = AdministradorProyectos.getInstance();
        fecha_inicio.setValue( LocalDate.now() );
    }
    

    
    @FXML
    public void crearBtn() {
    }
    
    @FXML
    public void salir(){
        if( JOptionPane.showConfirmDialog(null, "¿Está seguro que desea salir?") == 0 ){
            String proyectName = main.getDataBase().getValueOf("proyecto", "TITULO", Integer.valueOf(main.getProyecto_id()) );
            main.cambiarDePantalla("dashboard.fxml", proyectName);
        }
            
    }
    
}
