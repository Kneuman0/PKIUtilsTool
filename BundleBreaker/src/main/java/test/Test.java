package test;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import biz.ui.controller.utils.ControllerUtils;
import biz.ui.launchers.generic.ProgressSplashScreen;
import biz.ui.launchers.generic.SplashScreen;
import fun.personalacademics.utils.RadixConverter;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Test extends Application{

	public static void main(String[] args) {
		launch(args);
//		BigInteger TWO_COMPL_REF = BigInteger.ONE.shiftLeft(16);
//		BigInteger num = new BigInteger("ffdd4477", 16);
		System.out.println(RadixConverter.hexToUnsignedInt("ffdd447799"));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ProgressSplashScreen screen = new progImpl();
		
		primaryStage.setScene(screen.getScene());
		primaryStage.show();
		primaryStage.setTitle("Loading...");
		screen.updateProgress(.33, "[describe task thats executing here]");
		Text caption = new Text("ACME Department Stores");
		    caption.setFill(Color.BLACK);
		    caption.setStyle("-fx-font: 24 System;");
		Label label = new Label("Loading... This may take between 5 and 10 seconds");
		List<Node> nodes = new ArrayList<Node>(screen.getVBox().getChildren());
		nodes.add(0, caption);
		nodes.add(1, label);
		screen.getVBox().getChildren().clear();
		screen.getVBox().getChildren().addAll(nodes);
		screen.setBackground(Color.LIGHTGRAY);
		
	}
	
	public class progImpl extends ProgressSplashScreen{

		@Override
		protected InputStream getImageLoc() {
//			return this.getClass().getResourceAsStream("/resources/add.png");
			return null;
		}
		
	}
	
	public class DoIt extends ControllerUtils{

		@Override
		public void initialize() {
			// TODO Auto-generated method stub
			
		}
		
		public void displaySuccessAlert(){
			super.displaySuccessMessage("Success!", "ACME Department Stores",
					"[put action here] was successful!");
		}
		
		public void displayErrorAlert(){
			super.displayErrorMessage("Oops!", "ACME Department Stores",
					"An error occured while processing:\n[put operation here]", null);
		}
		
	}

}
