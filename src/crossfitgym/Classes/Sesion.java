package crossfitgym.Classes;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

public class Sesion implements Serializable{
    private LocalDateTime fecha;
    private SesionTipo tipo;
    private Duration duracion;
    
    public Sesion(LocalDateTime f, SesionTipo t, Duration d) {
        this.fecha = f; this.tipo = t; this.duracion = d;        
    }
    
   public LocalDateTime getFecha() {return this.fecha;}
   public SesionTipo getTipo() {return this.tipo;}
   public Duration getDuracion() {return this.duracion;}
}
