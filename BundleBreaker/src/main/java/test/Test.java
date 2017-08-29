package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import biz.ui.controller.utils.ControllerUtils;
import biz.ui.launchers.generic.SplashScreen;
import fun.personalacademics.model.CertificateBean;
import fun.personalacademics.model.ExtensionsBean;
import fun.personalacademics.utils.CertificateUtilities;
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

	public static void main(String[] args) throws CertificateException, IOException {
//		launch(args);
		X509Certificate cert = (X509Certificate)CertificateFactory.getInstance("X.509").generateCertificate(new FileInputStream("/home/karottop/Desktop/cert.cer"));
		System.out.println(new ExtensionsBean(cert));
		try {
            // Load the JDK's cacerts keystore file
            String filename = System.getProperty("java.home") + "/lib/security/cacerts".replace('/', File.separatorChar);
            FileInputStream is = new FileInputStream(filename);
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            String password = "changeit";
            keystore.load(is, password.toCharArray());

            // This class retrieves the most-trusted CAs from the keystore
            PKIXParameters params = new PKIXParameters(keystore);

            // Get the set of trust anchors, which contain the most-trusted CA certificates
            Iterator it = params.getTrustAnchors().iterator();
            
            while( it.hasNext() ) {
                TrustAnchor ta = (TrustAnchor)it.next();
                // Get certificate
                X509Certificate certs = ta.getTrustedCert();
                try {
					System.out.println(new CertificateBean(certs));
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
            System.out.println("Size: " + params.getTrustAnchors().size());
        } catch (CertificateException e) {
        } catch (KeyStoreException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (InvalidAlgorithmParameterException e) {
        } catch (IOException e) {
        } 
		
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
