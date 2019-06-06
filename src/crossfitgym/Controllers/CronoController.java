package crossfitgym.Controllers;

import crossfitgym.Classes.SesionTipo;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

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
        this.grupo.setText(nG + "");
        this.sesion.setText(sT.getNombre());
        setImages();
        
        initColaTiempos();
    }
    
    private void initColaTiempos() {
        qTiempos = new LinkedList();
        if(this.sTipo.getTCalentamiento() != 0) qTiempos.addLast(this.sTipo.getTCalentamiento());
        for(int i = 0; i < this.sTipo.getNumCircuitos(); i++) {
            for(int j = 0; j < this.sTipo.getNumEjercicios(); j++) {
                qTiempos.addLast(this.sTipo.getTEjercicios());
                if (j < this.sTipo.getNumEjercicios() - 1) qTiempos.addLast(this.sTipo.getTDescanso());
            }
            if(i < this.sTipo.getNumCircuitos() - 1)qTiempos.addLast(this.sTipo.getTDCircuitos());
        }    
    }
    
    private void setImages() {
        BufferedImage image = null;
        for(int i = 0; i < this.sTipo.getNumEjercicios(); i++) {
            ej[i].setDisable(false); ej[i].setVisible(true);  
            System.err.println(sTipo.getEjercicios().get(i));
            try {
                image = ImageIO.read(new File(System.getProperty("user.dir") 
                                    + "/DB/Images/" 
                                    + sTipo.getEjercicios().get(i) + ".PNG"));                                        
                ej[i].setImage(SwingFXUtils.toFXImage(image, null)); 
            } catch(IOException e){
                System.out.println(i);
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
            initColaTiempos();
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

    @FXML
    private void back(ActionEvent event) {
        try {
            crono.cancel();
            FXMLLoader cargador = new FXMLLoader(getClass()
                        .getResource("/crossfitgym/Main.fxml"));
            Parent root = cargador.load();
            
            MainController controller = cargador
                    .<MainController>getController();
            controller.initStage(this.stage);
            
            Scene scene = new Scene(root);
            
            this.stage.setScene(scene);
            this.stage.show();
        }catch(IOException e){}
    }
    
    
   private class MyService extends Service<Void> {

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                protected boolean ended = false;
                protected int actualTime;
                protected long sumTime;
                protected int contador, resto;
                                
                void calcula() {                    
                    long nowTime = System.currentTimeMillis();
                    Long totalTime = (nowTime - startTime) - stopTime;
                    prevTime = nowTime;

                    int minutos = totalTime.intValue() / 60000;
                    int resto = totalTime.intValue() % 60000;
                    int segundos = resto / 1000;
                    int centesimas = resto % 60;
                    
                    if(minutos * 60 + segundos >= actualTime) nextTime();
                    
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
                    
                    
                    try {Thread.sleep(1000);} catch(InterruptedException e) {}
                    
                    startTime = System.currentTimeMillis();
                    prevTime = startTime;                    
                    stopTime = 0;
                    sumTime += qTiempos.poll();
                    if(qTiempos.peekFirst() != null) {
                        actualTime = qTiempos.peekFirst();
                        if(actualTime == sTipo.getTEjercicios()) ejercicio();
                        else {descanso();}
                    }
                    else {      
                        ended = true;
                        this.cancel();                          
                    }
                }
                
                void ejercicio() {
                    contEj++;
                    System.out.println(contEj);
                }
                
                void descanso() {System.err.println("descanso de " + actualTime);}
                void calentamiento() {System.err.println("calentamiento");}
                
                @Override protected Void call() {
                    if (stopped) {
                        startTime = System.currentTimeMillis();
                        prevTime = startTime; actualTime = 100; contEj = 0;
                        sumTime = 0; contador = 1;
                        stopped = false;
                        actualTime = qTiempos.peek();
                        if(sTipo.getTCalentamiento() != 0) {
                            resto = 0;
                            calentamiento();
                        } else {ejercicio(); resto = 1; }
                    } else {
                        long nowTime = System.currentTimeMillis();
                        actualTime = qTiempos.peek();
                        Long elapsedTime = nowTime - prevTime;
                        
                        stopTime = stopTime + elapsedTime;
                        System.err.println(stopTime);
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
                    this.actualTime = 100000;
                    super.cancelled();
                    prevTime = System.currentTimeMillis();
                    if(ended) Platform.runLater(() -> { tiempo.setValue(sumTime / 60 + ":"
                            + sumTime % 60); });
                }
            };
        }
    }
    
}
