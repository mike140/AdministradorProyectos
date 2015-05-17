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
    
    public boolean isConnected(){
        return exito;
    }
    
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
    
    public Connection getConnection(){
        return connection;
    }
    
    public void close() throws SQLException{
        connection.close();
    }
    
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
    
}
