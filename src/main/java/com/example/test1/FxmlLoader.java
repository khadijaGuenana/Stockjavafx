package com.example.test1;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.net.URL;

public class FxmlLoader {

    private AnchorPane view ;
    public Pane getPage(String fileName){
        try {
            URL fileUrl = AccueilController.class.getResource(fileName+".fxml");
            if (fileUrl ==null){
                throw new FileNotFoundException("FXML fille can't be found");
            }
            view = FXMLLoader.load(fileUrl);
        }catch (Exception e){

            System.out.println("No  page "+fileName+"please check Fxmlloader");
            System.out.println("erreeuuuuur is" + e);
        }
        return view ;

    }






}
