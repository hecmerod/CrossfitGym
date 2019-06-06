package crossfitgym.Controllers;

import crossfitgym.Classes.SesionTipo;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class CronoController implements Initializable{

    private Stage stage;
    
    @FXML
    private Font x1;
    @FXML
    private Insets x2;
    @FXML
    private Insets x3;
    
    @FXML
    private Font x6;
    @FXML
    private Label grupo;
    @FXML
    private Label sesion;
    @FXML
    private Insets x4;
    @FXML
    private Insets x5;
    @FXML
    private Insets x7;
    
    @FXML
    private Label timer;
    @FXML
    private Button start_pause;
    
    @FXML
    private ImageView ej0, ej1, ej2, ej3, ej4, ej5, ej6, ej7, ej8, ej9, ej10,
            ej11, ej12, ej13;
    private ImageView[] ej;

    
    private static final int DELAY = 100;
    
    private static long prevTime = 0, startTime = 0, stopTime = 0;
 
    private boolean firstTime = true, stopped = true, paused = false;
    
    private int contEj = 0;
    
    private MyService crono;
    
    private int nGrupo;
    private SesionTipo sTipo;    
    
    
    private StringProperty tiempo = new SimpleStringProperty();
    
    private LinkedList<Integer> qTiempos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ej = new ImageView[14];
        ej[0] = ej0; ej[1] = ej1; ej[2] = ej2; ej[3] = ej3; ej[4] = ej4;
        ej[5] = ej5; ej[6] = ej6; ej[7] = ej7; ej[8] = ej8; ej[9] = ej9;
        ej[10] = ej10; ej[11] = ej11; ej[12] = ej12; ej[13] = ej13;       
        
        tiempo.setValue("00:00");
        timer.textProperty().bind(tiempo);
        
        crono = new MyService();
        
        
    }    
    
    public void initStage(Stage s, SesionTipo sT, int nG) {
        this.stage = s; this.sTipo = sT; this.nGrupo = nG;     
        setImages();
        
        qTiempos = new LinkedList();
        if(sT.getTCalentamiento() != 0) qTiempos.addLast(sT.getTCalentamiento());
        for(int i = 0; i < sT.getNumCircuitos(); i++) {
            for(int j = 0; j < sT.getNumEjercicios(); j++) {
                qTiempos.addLast(sT.getTEjercicios());
                qTiempos.addLast(sT.getTDescanso());
            }
            if(i < sT.getNumCircuitos() - 1)qTiempos.addLast(sT.getTDCircuitos());
        }    
    }
    
    private void setImages() {
        BufferedImage image = null;
        for(int i = 0; i <= this.sTipo.getNumEjercicios(); i++) {
            ej[i].setDisable(false); ej[i].setVisible(true);  
            try {
                image = ImageIO.read(new File(System.getProperty("user.dir") 
                                        + "/DB/Images/Sit-ups.JPG"));
                                        //+ this.sTipo.getEjercicios().get(i)));
                ej[i].setImage(SwingFXUtils.toFXImage(image, null)); 
            } catch(IOException e) {
                System.err.println("No se ha podido cargar la foto del ejercico "
                        + i);
            }
        }
    }
    @FXML private void next(ActionEvent event) {
        contEj++;
    }
    
    @FXML
    private void stop(ActionEvent event) {   
        if(paused) tiempo.setValue("00:00");
        crono.cancel();
        startTime = prevTime = stopTime = 0;                
        stopped = true; paused = false;
        start_pause.setText("PLAY");
    }   
    

    @FXML
    private void start_pause(ActionEvent event) {        
        if(firstTime) { 
            crono.start(); firstTime = false; 
            start_pause.setText("PAUSE");
        }
        else if (stopped) { 
            tiempo.setValue("00:00");         
            crono.restart(); 
            start_pause.setText("PAUSE");
        }
        else if (!paused) {
            paused = true;
            System.err.println("pausa");
            crono.cancel();
            start_pause.setText("PLAY");
        }
        else {
            paused = false;
            crono.restart();
            start_pause.setText("PAUSE");
        }
    }
    
    
   private class MyService extends Service<Void> {

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                
                protected int actualTime;
                protected long sumTime = 0;
                
                void calcula() {                    
                    long nowTime = System.currentTimeMillis();
                    Long totalTime = (nowTime - startTime) - stopTime;
                    prevTime = nowTime;

                    final Integer minutos = totalTime.intValue() / 60000;
                    final Integer resto = totalTime.intValue() % 60000;
                    final Integer segundos = resto / 1000;
                    final Integer centesimas;
                    
                    if(minutos * 60 + segundos > actualTime) { 
                        
                        centesimas = 0;
                        nextTime();
                        
                    } 
                    else centesimas = resto % 60;
                    
                    Platform.runLater(() -> {  
                        if(minutos == 0)
                            tiempo.setValue(String.format("%02d", segundos) + ":"
                                    + String.format("%02d", centesimas));
                        else
                            tiempo.setValue(String.format("%02d", minutos) + ":"
                                + String.format("%02d", segundos));
                        });                    
                    
                    
                }
                void nextTime() { 
                    Platform.runLater(() -> {                         
                            tiempo.setValue(String.format("%02d", actualTime) + ":00");
                        });    
                    //SONIDO NO IMPLEMENTADO
                    /*AudioClip audio = Applet.newAudioClip(getClass()
                            .getClassLoader().getResource("sonido.mp3"));
                    new Thread() {
                        @Override public void run() {
                        audio.play();
                        }
                    }.start();*/
                    
                    try {Thread.sleep(1000);} catch(InterruptedException e) {}
                    
                    startTime = System.currentTimeMillis();
                    prevTime = startTime;
                    if(actualTime == sTipo.getTEjercicios()) contEj++;
                    
                    System.err.println(actualTime);
                    System.err.println("Ejercicio: " + contEj);
                    
                    sumTime += qTiempos.poll();
                    if(qTiempos.peekFirst() != null) actualTime = qTiempos.peekFirst();  
                    else{System.err.println("kasd"); this.cancel();}                    
                
                     
                    
                }

                @Override
                protected Void call() {
                    if (stopped) {
                        startTime = System.currentTimeMillis();
                        prevTime = startTime;
                        stopped = false;
                        actualTime = qTiempos.peek();                        
                    } else {
                        long nowTime = System.currentTimeMillis();
                        Long elapsedTime = nowTime - prevTime;
                        stopTime = stopTime + elapsedTime;
                    }
                    while (true) {
                        try {
                            Thread.sleep(DELAY);
                        } catch (InterruptedException ex) {
                            if (isCancelled()) {
                                break;
                            }
                        }
                        if (isCancelled()) {
                            break;
                        }
                        calcula();
                    }
                    return null;
                }

                @Override
                protected void cancelled() {
                    super.cancelled();
                    prevTime = System.currentTimeMillis();                    
                }
            };
        }
    }
    
}
