/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitgym;

import crossfitgym.Controllers.MainController;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author usuario
 */
public class CrossfitGym extends Application {
    
    MainController mainController;
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml")); 
        
        Scene scene = new Scene(loader.load());   
        
        mainController = loader.<MainController>getController(); 
        mainController.initStage(stage);
        
        stage.setScene(scene);
        stage.setTitle("ETSINF Crossfit"); 
        stage.show();
    }
    
    @Override
    public void stop(){
        try { 
            FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir") 
                                                    + "/DB/gymObj");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(mainController.getGym());
            objectOut.close();
            System.out.println("GUARDADO CON ÉXITO");
        } catch (IOException ex) {System.out.println("FALLO EN EL GUARDADO");} 
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
