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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 *
 * @author OskR219
 */
public class proyectoController implements Initializable {
    
    private AdministradorProyectos main;
    
    @FXML
    private TextField titulo;
    @FXML
    private TextArea descripcion;
    @FXML
    private DatePicker fecha;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        main = AdministradorProyectos.getInstance();
        fecha.setValue( LocalDate.now() );
    }
    
    @FXML
    public void abrirBtn() {
        if( JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea salir?") == 0 ){
            System.out.println("Estoy a punto de entrar");
            main.cambiarDePantalla("abrir_proyecto.fxml", "Abrir Proyecto");
        }
            
    }
    
    @FXML
    public void crearBtn() {
        DataBase db = main.getDataBase();
        
        if( !Validate.isCorrectSize( titulo.getText() , 100, "Titulo") )
            return;
        
        if( !Validate.isCorrectSize(descripcion.getText(), 300, "Descripcion") )
            return;
        
        ArrayList<Integer> lista = db.getIndexOf("proyecto", "TITULO", titulo.getText().toUpperCase() );
        if( !lista.isEmpty() ){
            JOptionPane.showMessageDialog(null, "Error ya existe un proyecto con el mismo titulo. Escoja otro");
            return;
        }
        
        System.out.println( fecha.getEditor().getText() );
        String values[] = {titulo.getText(), descripcion.getText(), fecha.getEditor().getText() };
        db.insert("proyecto", values);
        JOptionPane.showMessageDialog(null, "Proyecto creado con exito");
        main.cambiarDePantalla("dashboard.fxml", titulo.getText() );
    }
    
}
