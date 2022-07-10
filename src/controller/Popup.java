package controller;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Popup {
   
    
	public static void display()
	{
		Stage popupwindow = new Stage();
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("About");
		Label label1= new Label("This program will allow the user to create a profile and select"
					+ "their preferred modules and reserve modules.\n"
					+ "The program can be saved at any stage and be reloaded at any time to be completed.\n"
					+ "");
		label1.setWrapText(true);
      
		Scene scene1= new Scene(label1, 300, 250);
		popupwindow.setScene(scene1);
		popupwindow.showAndWait();
       
	}

}