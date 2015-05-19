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
public class loginController implements Initializable {
    
    private AdministradorProyectos main;
    
    //este es el controlador de login
    //aqui declaras las ID de esta forma
    
    @FXML
    private TextField correo_electronico; //Donde el nombre de la variable es el id, si guardas el archivo en el fxml te sale mira
    //a ok sip ya para conectar el fxml con java aja ahora.
    
    @FXML
    private TextField password;
    
    @FXML
    private Button entrarBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        main = AdministradorProyectos.getInstance();
    } 
    
    @FXML
    public void entrar() {
        main.entrarGUI();
    }
    
    @FXML
    public void salir() throws Exception{
        if( JOptionPane.showConfirmDialog(null, "¿Está seguro que desea salir?") == 0 )
            System.exit(0);
    }
    
    @FXML
    public void acceder(){
        if( !Validate.isMail(correo_electronico.getText(), 100) || !Validate.isPass(password.getText(), 30) )
            return;
        
        DataBase db = main.getDataBase();
        
        ArrayList<Integer> lista = db.getIndexOf("usuario", "CORREO", correo_electronico.getText().toUpperCase() );
        if( lista.isEmpty() ){
            JOptionPane.showMessageDialog(null, "No existe un usuario con ese correo");
            return;
        }
        
        String pass = db.getValueOf("usuario", "PASSWORD", lista.get(0) );
        String name = db.getValueOf("usuario", "NOMBRE", lista.get(0) );
        
        if( pass.matches( password.getText() ) ){
            JOptionPane.showMessageDialog(null, "Bienvenido " + name + "!");
            //JOptionPane.showMessageDialog(null, db.getValuesInColumn("usuario", "NOMBRE").length );
            main.setUsuario_id(db.getValueOf("usuario", "ID", lista.get(0) ));
            AdministradorProyectos.getInstance().cambiarDePantalla("abrir_proyecto.fxml", "Proyecto");
            
        }
        else{
            JOptionPane.showMessageDialog(null, "La contraseña no es correcta");
            return;
        }
        
    }
    
    @FXML
    public void registrarse() {
        System.out.println("LOL");
        main.registrarseGUI();
    }
    
}
