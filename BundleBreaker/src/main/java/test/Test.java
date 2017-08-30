package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import fun.personalacademics.model.ExtensionsBean;
import javafx.application.Application;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class Test extends Application{

	public static void main(String[] args) throws CertificateException, IOException {
//		launch(args);
		X509Certificate cert = (X509Certificate)CertificateFactory.getInstance("X.509").generateCertificate(new FileInputStream("/home/karottop/Desktop/cert.cer"));
		System.out.println(new ExtensionsBean(cert));
			
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void start(Stage primaryStage) throws Exception {
//		ProgressSplashScreen screen = new progImpl();
//		
//		primaryStage.setScene(screen.getScene());
//		primaryStage.show();
//		primaryStage.setTitle("Loading...");
//		screen.updateProgress(.33, "[describe task thats executing here]");
//		Text caption = new Text("ACME Department Stores");
//		    caption.setFill(Color.BLACK);
//		    caption.setStyle("-fx-font: 24 System;");
//		Label label = new Label("Loading... This may take between 5 and 10 seconds");
//		List<Node> nodes = new ArrayList<Node>(screen.getVBox().getChildren());
//		nodes.add(0, caption);
//		nodes.add(1, label);
//		screen.getVBox().getChildren().clear();
//		screen.getVBox().getChildren().addAll(nodes);
//		screen.setBackground(Color.LIGHTGRAY);
//		
//	}
	
//	public class progImpl extends ProgressSplashScreen{
//
//		@Override
//		protected InputStream getImageLoc() {
////			return this.getClass().getResourceAsStream("/resources/add.png");
//			return null;
//		}
//		
//	}
//	
//	public class DoIt extends ControllerUtils{
//
//		@Override
//		public void initialize() {
//			// TODO Auto-generated method stub
//			
//		}
//		
//		public void displaySuccessAlert(){
//			super.displaySuccessMessage("Success!", "ACME Department Stores",
//					"[put action here] was successful!");
//		}
//		
//		public void displayErrorAlert(){
//			super.displayErrorMessage("Oops!", "ACME Department Stores",
//					"An error occured while processing:\n[put operation here]", null);
//		}
//		
//	}

}
