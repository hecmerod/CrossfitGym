package crossfitgym.Classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Gym implements Serializable{
    private ArrayList<Grupo> grupos = new ArrayList<>();
    private ArrayList<SesionTipo> tiposSesion = new ArrayList<>();
    
    public Gym() {}
    
    public ArrayList<Grupo> getGrupos() {return grupos;}
    public ArrayList<SesionTipo> getTiposSesion() {return tiposSesion;}
}
