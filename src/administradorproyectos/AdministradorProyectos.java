package administradorproyectos;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AdministradorProyectos extends Application {
    
    private Stage mainStage;
    
    private String proyecto_id;
    private String usuario_id;
    private Proyecto proyecto;

    public Proyecto getProyecto() {
        return proyecto;
    }

    public String getProyecto_id() {
        return proyecto_id;
    }

    public void setProyecto_id(String proyecto_id) {
        this.proyecto_id = proyecto_id;
        this.proyecto = new Proyecto(proyecto_id);
        
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getUsuario_id() {
        return usuario_id;
    }
    
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
        mainStage.setTitle("Task Builder");
        mainStage.centerOnScreen();
        //mainStage.getIcons().add( new Image("\\archivos\\icon.png") );
         
        database = new DataBase("tareas", "root", "");
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public DataBase getDataBase(){
        return database;
    }
    
    public void entrarGUI() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("proyecto.fxml"));
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setResizable(true);
            mainStage.centerOnScreen();
        } catch(Exception e) {
            
        }
        
    }
    
    public void registrarseGUI() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("usuario.fxml"));
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setResizable(false);
            mainStage.setTitle("Registro de Usuarios");
            mainStage.centerOnScreen();
        } catch(Exception e) {
            
        }
        
    }
    
    public void abrirProyectoGUI() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setResizable(true);
            mainStage.centerOnScreen();
        } catch(Exception e) {
            
        }
        
    }
    
    public void crearProyectoGUI() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.centerOnScreen();
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
    public void cambiarDePantalla(String screen, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(screen)); //este es el archivo fxml
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setResizable(false);
            mainStage.setTitle(title);
            mainStage.centerOnScreen();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        //ok? okay y para los fxml nuevos tienes que asignarles un controlador de esta forma
        // en esa parte eliges el controlador y los controladores utiliza el template que ya esta o mas facil solo copias un controlador y lo pegas asi:
    }
    
    public HashMap<String, Tarea> getTareasUsuario() {
        HashMap<String, Tarea>  tareas = new HashMap();
        
        String tareas_id[] = database.getValuesInColumn("`tarea`, `tarea_usuario` ", 
                "`tarea`.`ID`", "WHERE `tarea`.`PROYECTO_ID` = " + proyecto_id + 
                        " AND `tarea`.`ID` = `tarea_usuario`.`TAREA_ID` AND `tarea_usuario`.`USUARIO_ID` = " + usuario_id);
        Tarea temp;
        
        for(String x : tareas_id){
            
            temp = new Tarea();
            HashMap<String, String> registro = database.fetchArray("tarea", Integer.valueOf(x) );
            
            temp.setTitulo( registro.get("TITULO") );
            temp.setDescripcion( registro.get("DESCRIPCION") );
            temp.setFecha_inicio( registro.get("FECHA_INICIO") );
            temp.setFecha_fin( registro.get("FECHA_FIN") );
            temp.setProyecto_id( Integer.valueOf( registro.get("PROYECTO_ID") ) );
            
            if( registro.get("ESTADO").matches("0") )
                temp.setEstado(false);
            else
                temp.setEstado(true);
            tareas.put(x, temp);
        }
        return tareas;
    }
    
    public HashMap<String, Tarea> getTareas() {
        HashMap<String, Tarea> tareas = new HashMap();
        
        String tareas_id[] = database.getValuesInColumn("`tarea`, `tarea_usuario` ", 
                "`tarea`.`ID`", "WHERE `tarea`.`PROYECTO_ID` = " + proyecto_id + 
                        " AND `tarea`.`ID` = `tarea_usuario`.`TAREA_ID`");
        Tarea temp;
        
        for(String x : tareas_id){
            
            temp = new Tarea();
            HashMap<String, String> registro = database.fetchArray("tarea", Integer.valueOf(x) );
            
            temp.setTitulo( registro.get("TITULO") );
            temp.setDescripcion( registro.get("DESCRIPCION") );
            temp.setFecha_inicio( registro.get("FECHA_INICIO") );
            temp.setFecha_fin( registro.get("FECHA_FIN") );
            temp.setProyecto_id( Integer.valueOf( registro.get("PROYECTO_ID") ) );
            
            if( registro.get("ESTADO").matches("0") )
                temp.setEstado(false);
            else
                temp.setEstado(true);
            
            tareas.put( x , temp);
        }

        return tareas;
    }
    
}

class Proyecto {
    
    private String id, titulo, descripcion;
    private LocalDate fecha;

    public Proyecto(String id) {
        this.id = id;
        HashMap<String, String> proyecto = AdministradorProyectos.getInstance().getDataBase().fetchArray("`proyecto` ", Integer.valueOf(id));
        this.titulo = proyecto.get("TITULO");
        this.descripcion = proyecto.get("DESCRIPCION");
        this.fecha = LocalDate.of(Integer.parseInt(proyecto.get("FECHA").split("/")[2]), Integer.parseInt(proyecto.get("FECHA").split("/")[1]), Integer.parseInt(proyecto.get("FECHA").split("/")[0]));
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
}

class Tarea {
    private int id, proyecto_id;
    private String titulo, descripcion, fecha_inicio, fecha_fin;
    private boolean estado;
    
    public Tarea(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProyecto_id() {
        return proyecto_id;
    }

    public void setProyecto_id(int proyecto_id) {
        this.proyecto_id = proyecto_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
}