/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package administradorproyectos;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javax.swing.JOptionPane;
import netscape.javascript.JSObject;

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
    @FXML
    private WebView calendario;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        main = AdministradorProyectos.getInstance();
        calendario.getEngine().load(this.getClass().getResource("scheduler/index.html").toExternalForm());
        calendario.getEngine().getLoadWorker().stateProperty().addListener(
            new ChangeListener<State>() {
                @Override
                public void changed(ObservableValue<? extends State> ov,
                    State oldState, State newState) {
                    if (newState == State.SUCCEEDED) {
                            JSObject win = (JSObject) calendario.getEngine().executeScript("window");
                            win.setMember("app", new JavaApp());
                        }
                    }
                }
        );
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
    
    public class JavaApp {
        
        public void evento(String evento) {
            System.out.println(evento);
        }
    }
    
}
