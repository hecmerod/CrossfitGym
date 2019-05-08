package crossfitgym.Classes;

import java.io.Serializable;

public class SesionTipo implements Serializable{
    private int tCalentamiento, numEjercicios, tEjercicios, tDescanso,
                numCircuitos, tDCircuitos;
    
    public SesionTipo(int tC, int nE, int tE, int tD, int nC, int tDC) {
        this.tCalentamiento = tC; this.numEjercicios = nE;
        this.tEjercicios = tE; this.tDescanso = tD; this.numCircuitos = nC;
        this.tDCircuitos = tDC;        
    }
    
    public int getTCalentamiento() {return this.tCalentamiento;}
    public int getNumEjercicios() {return this.numEjercicios;}
    public int getTEjercicios() {return this.tEjercicios;}
    public int getTDescanso() {return this.tDescanso;}
    public int getNumCircuitos() {return this.numCircuitos;}
    public int getTDCircuitos() {return this.tDCircuitos;}
    
    public void setTCalentamiento(int tC) {this.tCalentamiento = tC;}
    public void setNumEjercicios(int nE) {this.numEjercicios = nE;}
    public void setTEjercicios(int tE) {this.tEjercicios = tE;}
    public void setTDescanso(int tD) {this.tDescanso = tD;}
    public void setNumCircuitos(int nC) {this.numCircuitos = nC;}
    public void setTDCircuitos(int tDC) {this.tCalentamiento = tDC;}
}
