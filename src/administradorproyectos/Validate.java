package administradorproyectos;

import java.util.Scanner;
import javax.swing.JOptionPane;

public class Validate {
    
    private final static Scanner entrada = new Scanner( System.in );
    
    public static boolean isName(String text, int length, String field){
        if( text.matches("") ){
            JOptionPane.showMessageDialog(null, "Error favor de ingresar un valo para "
                    + field);
            return false;
        }
        
        if( !text.matches("[a-zA-Z áéíóúü]*") ){
            JOptionPane.showMessageDialog(null, "Error, El campo \"" + field + 
                    "\" solo debe contener letras y espacios");
            return false;
        }
        
        if( text.length() > length - 1 ){
            JOptionPane.showMessageDialog(null, "Error, El campo \"" + field + 
                    "\" tiene una longitud máxia de " + length);
            return false;
        }

        return true;
    }
    
    public static boolean isPhone(String text){
        if( text.matches("") ){
            JOptionPane.showMessageDialog(null, "Error favor de ingresar un valor para Teléfono");
            return false;
        }
        
        if( (text.length() != 8 && text.length() != 10) || !text.matches("[0-9]*") ){
            JOptionPane.showMessageDialog(null,"Error el numero telefonico solo debe "
                    + "contener numeros y tener un tamaño de 8 a 10 digitos" );
            return false;
        }   
        else
            return true;
    }
    
    public static boolean isPositiveInt(String text, String field){
        if( text.matches("") ){
            JOptionPane.showMessageDialog(null, "Error favor de ingresar un valor para "
                + field);
            return false;
        }
        
        if( ! (text.length() < 7) ){
            JOptionPane.showMessageDialog(null, "El campo \"" + field + "\" debe tener"
                    + "Una longitud menor a " + 7);
            return false;
        }
        
        if( !text.matches("[0-9]*") ){
            JOptionPane.showMessageDialog(null, "El campo \"" + field + "\" solo"
                    + "debe contener numeros");
            return false;
        }
        return true;
    }
    
    public static boolean isMail(String text, int length){
        if( text.matches("") ){
            JOptionPane.showMessageDialog(null, "Error favor de ingresar un valor par Correo");
            return false;
        }
        
        if(  text.length() < (length - 1) )
            return true;
        else{
            JOptionPane.showMessageDialog(null, "La dirección de correo electrónico "
                    + "solo puede contener caracteres alfanumericos\n y una longitud "
                    + "menor a " + length);
            return false;
        }
    }
    
    public static boolean isCorrectSize(String text, int length, String field){
        if( text.matches("") ){
            JOptionPane.showMessageDialog(null, "Error favor de ingresar un valor para"
                    + field);
            return false;
        }
        
        if( text.length() > length - 1){
            JOptionPane.showMessageDialog(null, "Error el campo \"" + field + ""
                    + "debe tener un tamaño menor a" + length);
            return false;
        }    
        else
            return true;
    }
    
    public static boolean isPass(String text, int length){
        if( text.matches("") ){
            JOptionPane.showMessageDialog(null, "Error favor de ingresar un valor para Password");
            return false;
        }
        
        if( text.length() < length -1 && text.matches("[a-zA-Z0-9]*") )
            return true;
        else{
            JOptionPane.showMessageDialog(null, "Error la contraseña solo puede "
                    + "contener caracteres alfanumericos\ny un tamaño menor a " + length);
            return false;
        }
    }

}
