/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package administradorproyectos;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author OskR219
 */
public class abrirProyectoController implements Initializable {
    
    private AdministradorProyectos main;
    
    @FXML
    private ComboBox proyectos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        main = AdministradorProyectos.getInstance();
        
        DataBase db = main.getDataBase();
        
        String nombres[] = db.getValuesInColumn("proyecto", "TITULO");
        String fechas[] = db.getValuesInColumn("proyecto", "FECHA");
        
        System.out.println("Holi");
        
        ArrayList<String>lista = new ArrayList();
        proyectos.setValue( "Eliga el proyecto" );
        
        for(int i = 0; i < nombres.length; i++)
            lista.add( nombres[i] + " -- " + fechas[i] );
        
        ObservableList<String> options = FXCollections.observableArrayList( lista );
        proyectos.setItems(options);
    }
    
    public void abreProyecto(){
        String nombre = proyectos.getSelectionModel().getSelectedItem().toString();
        DataBase db = main.getDataBase();
        ArrayList<Integer> lista = db.getIndexOf("proyecto", "TITULO", nombre);
        main.setIdProyecto( lista.get(0) );
        main.cambiarDePantalla("dashboard.fxml", nombre);
    }
    
}
