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
    private Button entrarBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        main = AdministradorProyectos.getInstance();
    } 
    
    @FXML
    public void entrar() {
        main.entrarGUI();
    }
    
    @FXML // Asi declaras una funcion para que la puedas usar con los botones. ok? a ya ok ok
    public void unafuncion() {
        System.out.println(correo_electronico.getText());
        //ahora para cambiar de pantalla o FXML haces esto
        //Lo mandas llamar desde aca de esta forma:
        AdministradorProyectos.getInstance().cambiarDePantalla("usuario.fxml");
    }
    
    @FXML
    public void registrarse() {
        System.out.println("LOL");
        main.registrarseGUI();
    }
    
}
