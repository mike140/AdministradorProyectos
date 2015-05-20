package administradorproyectos;

import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;

public class DataBase {
    private String driver = "com.mysql.jdbc.Driver";
    private Connection connection;
    private final String server = "jdbc:mysql://localhost/";
    private boolean exito;
    
    public DataBase(String dataBaseName, String user, String pass){
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(server + dataBaseName,user,pass);
            System.out.println("Conexion realizada con exito");
            exito = true;
        }
        catch (ClassNotFoundException | SQLException e){
            System.out.println("No se pudo conectar a la Base de Datos");
            exito = false;
        }
    }
    
    //Retorna el número de registros que hay en una tabla, si ocurre un error retorna -1
    public int getCount(String table){
        try{
            Statement query = (Statement) connection.createStatement();
            String comando = "SELECT COUNT(*) FROM " + table; 
            ResultSet rs = query.executeQuery(comando);
            
            rs.first();
            int number = Integer.valueOf( rs.getString(1) );
            return number;
        }catch(SQLException e){
            return -1;
        }
    }
    
    //Retorna un arreglo de String con todos los valores que tiene la columna de una tabla
    public String[] getValuesInColumn(String table, String column){
        ArrayList<String> lista = new ArrayList();
        
        try{
            Statement query = (Statement) connection.createStatement();
            String comando = "SELECT " + column + " FROM " + table; 
            
            ResultSet rs = query.executeQuery(comando);
            
            rs.beforeFirst();
            
            while( rs.next() )
                lista.add( rs.getString(1) );
            String arreglo[] = new String[ lista.size() ];
            
            lista.toArray( arreglo );
            return arreglo;
        }catch(SQLException e){
            return null;
        }
    }
    
    //Retorna un arreglo de String con todos los valores que tiene la columna de una tabla
    public String[] getValuesInColumn(String table, String column, String extra){
        ArrayList<String> lista = new ArrayList();
        
        try{
            Statement query = (Statement) connection.createStatement();
            String comando = "SELECT " + column + " FROM " + table + extra;
            ResultSet rs = query.executeQuery(comando);
            
            rs.beforeFirst();
            
            while( rs.next() )
                lista.add( rs.getString(1) );
            String arreglo[] = new String[ lista.size() ];
            
            lista.toArray( arreglo );
            return arreglo;
        }catch(SQLException e){
            return null;
        }
    }
    
    //Inserta los valores de un registro en una tabla. En los valores se omite el id
    //Retorna un boleano indicando si se pudo realizar la inserción
    public boolean insert(String tableName, String[] values){
        try {
            Statement query = (Statement) connection.createStatement();
            String q1 = "INSERT INTO " + tableName + " VALUES( null, ";
            
            for(String txt : values){
                if( txt.equals("NULL") )
                    q1 += txt + ", ";
                else
                    q1 += "'" + txt + "', ";
            }
            
            q1 = q1.substring( 0, q1.length()-2 ).concat(")");
            query.executeUpdate(q1);
            query.close();
        } catch (SQLException e) {
            System.out.println("FAIL \n error: " + e.getMessage());
            return false;
        }
        return true;
    }
    
    //Retorna verdadero si la base de datos está conectada
    public boolean isConnected(){
        return exito;
    }
    
    //Executa un comando sql y devuelve verdadero si se pudo realizar con exito
    public boolean executeQuery(String command){
        try {
            Statement query = (Statement) connection.createStatement();
            query.executeUpdate(command);
            query.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    //Devuelve un arreglo asociativo con el registro de posición "index" de una tabla
    public HashMap<String, String>fetchArray(String table, int index){
        HashMap<String, String> mapa = new HashMap();
        
        try{
            Statement query = (Statement) connection.createStatement();
            String comando = "SELECT * FROM " + table + " WHERE ID = " + index;
            ResultSet rs = query.executeQuery(comando);
            
            if( !rs.first() )
                return null;
            
            ResultSetMetaData rsmd = rs.getMetaData();
            String valor, key;
            int cant = rsmd.getColumnCount();
            
            for(int i = 1; i <= cant; i++){
                key = rsmd.getColumnName(i);
                valor = rs.getString(key);
                mapa.put(key, valor);
            }   
            return mapa;
        } catch(SQLException e){
            return null;
        }
    }
    
    //Retorna la conexión de la base de datos
    public Connection getConnection(){
        return connection;
    }
    
    //Cierra la conexión de la base de datos
    public void close() throws SQLException{
        connection.close();
    }
    
    //Retorna una lista con todos los ids de los registros de una tabla que tengan un determinado valor
    //en una columna especifica. Si no existe ningún registro con ese valor retorna null
    public ArrayList<Integer> getIndexOf(String tableName, String columnName, String value){
        try{
            ArrayList<Integer>lista = new ArrayList();
            Statement query = (Statement) connection.createStatement();
            String comando = "SELECT ID FROM " + tableName + " WHERE " + columnName + " = '" + value + "'";
            ResultSet rs = query.executeQuery(comando);
            rs.beforeFirst();
            
            while( rs.next() )
                lista.add( Integer.valueOf( rs.getString(1) ) );
            return lista;
        }catch(SQLException e){
            return null;
        }
    }
    
    //Obtener el valor de una columna, en un registro de posición "index" de una determinada tabla
    public String getValueOf(String tableName, String columnName, int index){
        try{
            Statement query = (Statement) connection.createStatement();
            String comando = "SELECT " + columnName + " FROM " + tableName + " WHERE ID = " + String.valueOf(index);
            ResultSet rs = query.executeQuery(comando);
            
            rs.first();
            return rs.getString(1);
        }catch(SQLException e){
            return null;
        }     
    }
    
    //Obtener un arreglo con los nombres de todas las columnas de una tabla
    public String[] getColumnsOf(String tableName){
        try{
            Statement query = (Statement) connection.createStatement();
            String comando = "SELECT * FROM " + tableName + " WHERE ID = 1";
            ResultSet rs = query.executeQuery(comando);
            
            if( !rs.first() )
                return null;
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int cant = rsmd.getColumnCount();
            String arreglo[] = new String[cant];
            
            for(int i = 1; i <= cant; i++)
                arreglo[i - 1] = rsmd.getColumnName(i);
            return arreglo;
        } catch(SQLException e){
            return null;
        }
    }
    
    //Elimina un indice en una tabla, retorna verdadero si se pudo eliminar con exito
    public boolean removeIndex(String tableName, int index){
        try{
            Statement query = (Statement) connection.createStatement();
            String comando = "DELETE FROM " + tableName + " WHERE ID = " + String.valueOf(index);
            query.executeUpdate(comando);
            
            return true;
        }catch(SQLException e){
            return false;
        } 
    }
    
    public boolean finalizeTask(int id){
        return executeQuery("UPDATE tarea SET ESTADO = 1 WHERE ID = " + id);
    }
    
    public ResultSet select(String command){
        try{
            Statement query = (Statement) connection.createStatement();
            ResultSet rs = query.executeQuery(command);
            
            return rs;
        }catch(SQLException e){
            return null;
        }     
    }
    
    /*
    ResultSet query = database.select("SELECT (*) FROM usuario");
    
    query.beforeFirst();  // situa el puntero antes del primer registro
    
    while( query.next() ){  //pasa al siguiente registro y retorna un boleano si existe 
        
        System.out.print( query.getString(1) ); //Obtiene un string de la primera columna
        System.out.print( query.getString(2) ); //Obtiene un string de la segunda columna
        System.out.print( query.getString(3) ); //Obtiene un string de la tercera columna
    }
    
    */
    
}
