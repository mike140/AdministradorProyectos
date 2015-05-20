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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        main = AdministradorProyectos.getInstance();
        fecha_inicio.setValue( LocalDate.now() );
    }
    

    
    @FXML
    public void agregar() {
        DataBase db = main.getDataBase();
        
        if( !Validate.isCorrectSize( titulo.getText() , 100, "Titulo") )
            return;
        
        if( !Validate.isCorrectSize(descripcion.getText(), 300, "Descripcion") )
            return;
        
        String values[] = {titulo.getText(), descripcion.getText(), fecha_inicio.getEditor().getText(), fecha_fin.getEditor().getText(), main.getProyecto_id(), "0" };
        
        if(fecha_inicio.getValue().isAfter(main.getProyecto().getFecha())) {
            JOptionPane.showMessageDialog(null, "Error la fecha de inicio no puede ser mayor a la fecha de termino del proyecto");
            return;
        }
        
        if(fecha_fin.getValue().isAfter(main.getProyecto().getFecha())) {
            JOptionPane.showMessageDialog(null, "Error la fecha de fin no puede ser mayor a la fecha de termino del proyecto");
            return;
        }
        
        if(fecha_inicio.getValue().isAfter(fecha_fin.getValue())) {
            JOptionPane.showMessageDialog(null, "Error la fecha de fin no puede ser menor a la fecha de inicio");
            return;
        }
        
        if( !db.insert("tarea", values) ){
            JOptionPane.showMessageDialog(null, "Error ya existe una tarea con ese titulo");
            return;
        }
        
        String tareas_id[] = db.getValuesInColumn("`tarea`", 
                "`tarea`.`ID`", " WHERE `tarea`.`PROYECTO_ID` = '" + main.getProyecto_id() + 
                        "' AND `tarea`.`TITULO` = '" + titulo.getText() + "'");        
        
        HashMap<String, String> registro = db.fetchArray("tarea", Integer.valueOf( tareas_id[0] ) );
        String values_tarea_usuario[] = {main.getUsuario_id(), registro.get("ID") };
        db.insert("tarea_usuario", values_tarea_usuario);
        JOptionPane.showMessageDialog(null, "Tarea creado con exito");
        main.cambiarDePantalla("dashboard.fxml", titulo.getText() );
    }
    
    @FXML
    public void salir(){
        
            String proyectName = main.getDataBase().getValueOf("proyecto", "TITULO", Integer.valueOf(main.getProyecto_id()) );
            main.cambiarDePantalla("dashboard.fxml", proyectName);
        
            
    }
    
}
