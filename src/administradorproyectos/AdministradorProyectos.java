/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package administradorproyectos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author 130034
 */
public class AdministradorProyectos extends Application {
    
    private Stage mainStage;
    
    private static AdministradorProyectos instance;
    
    public static AdministradorProyectos getInstance() {
        return instance;
    }
    
    private DataBase database;
    
    @Override
    public void start(Stage stage) throws Exception {
        instance = this;
        mainStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        
        Scene scene = new Scene(root);
        
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
        
        database = new DataBase("tareas", "root", "");
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void entrarGUI() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("proyecto.fxml"));
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setResizable(true);
        } catch(Exception e) {
            
        }
        
    }
    
    public void registrarseGUI() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("usuario.fxml"));
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setResizable(false);
        } catch(Exception e) {
            
        }
        
    }
    
    public void abrirProyectoGUI() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setResizable(true);
        } catch(Exception e) {
            
        }
        
    }
    
    public void crearProyectoGUI() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
        } catch(Exception e) {
            
        }
        
    }
    
    public boolean nuevoUsuario(String nombre, String apellido, String correo, String password) {
        if( !Validate.isName(nombre, 50, "Nombre") ||  !Validate.isName(apellido, 100, "Apellido") )
            return false;
        
        if( !Validate.isMail(correo,  100) || !Validate.isPass(password, 30) )
            return false;
        
        String arreglo[] = {nombre, apellido, correo, password};
        database.insert("usuario", arreglo );
        return true;
    }
    
    
    //Esta es la clase principal de el programa donde se maneja todo entonces aqui declaras tu funcion para cambiar de pantalla o conectar a BD o lo que sea
    public void cambiarDePantalla(String screen) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(screen)); //este es el archivo fxml
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setResizable(false);
        } catch(Exception e) {
            
        }
        //ok? okay y para los fxml nuevos tienes que asignarles un controlador de esta forma
        // en esa parte eliges el controlador y los controladores utiliza el template que ya esta o mas facil solo copias un controlador y lo pegas asi:
    }
}



//Si? a ok sip ya te entendi oye entonces ahorita hago nuevousuario y login? o pongo otros formularios? haz el de nuevo usuario y login, :) okay ;) ahorita vengo que paso=? a no que ahorita te los paso jajaok