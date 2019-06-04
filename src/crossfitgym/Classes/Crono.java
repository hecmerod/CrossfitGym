package crossfitgym.Classes;

import crossfitgym.Controllers.CronoController;
import javafx.beans.property.StringProperty;

public class Crono extends Thread {
    private CronoController cronoController;
    
    private int hh = 15, mm = 20;
    private final StringProperty timer;
    
    public Crono(StringProperty s) {this.timer = s;}
    
    @Override public void run() {
        timer.setValue(hh+":"+mm);
    }
}
