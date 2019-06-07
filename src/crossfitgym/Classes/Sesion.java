package crossfitgym.Classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Sesion implements Serializable{
    private LocalDateTime fecha;
    private SesionTipo tipo;
    private long duracion;
    
    public Sesion(LocalDateTime f, SesionTipo t, long d) {
        this.fecha = f; this.tipo = t; this.duracion = d;        
    }
    
   public LocalDateTime getFecha() {return this.fecha;}
   public SesionTipo getTipo() {return this.tipo;}
   public long getDuracion() {return this.duracion;}
}
