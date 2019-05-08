package crossfitgym.Classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Grupo implements Serializable{
    private String nombre, descripcion;
    private ArrayList<Sesion> sesiones;
    
    public Grupo(String n, String d) {
        this.nombre = n; this.descripcion = d;
    }
    
    public String getNombre() {return nombre;}
    public String getDescripcion() {return descripcion;}    
    public ArrayList<Sesion> getSesiones() {return sesiones;}   
    
    public void setNombre(String n) {this.nombre = n;}
    public void setDescripcion(String d) {this.descripcion = d;}
    public void setSesiones(ArrayList<Sesion> s) {this.sesiones = s;}
    
}
