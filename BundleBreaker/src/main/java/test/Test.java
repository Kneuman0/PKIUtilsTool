package test;

import java.io.ByteArrayInputStream;
//import org.bouncycastle.openpgp.operator.jcajce.;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.compress.utils.IOUtils;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.util.ASN1Dump;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import fun.personalacademics.model.ExtensionsBean;
import fun.personalacademics.utils.CertificateEncapsulater;
import javafx.application.Application;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class Test extends Application{

	public static void main(String[] args) throws Exception {
//		System.out.println(Arrays.toString(args));
//		
//		
//		    try {
//		    	InputStream signedData = new FileInputStream("/home/karottop/Desktop/public/signedData.msi");
//		    	InputStream signature = new FileInputStream("/home/karottop/Desktop/public/SignedDataSig.gpg");
//		        signature = PGPUtil.getDecoderStream(signature);
//		        JcaPGPObjectFactory pgpFact = new JcaPGPObjectFactory(signature);
//		        PGPSignature sig = ((PGPSignatureList) pgpFact.nextObject()).get(0);
//		        PGPPublicKey key = pgpPubRingCollection.getPublicKey(sig.getKeyID());
//		        sig.init(new JcaPGPContentVerifierBuilderProvider().setProvider("BC"), key);
//		        byte[] buff = new byte[1024];
//		        int read = 0;
//		        while ((read = signedData.read(buff)) != -1) {
//		            sig.update(buff, 0, read);
//		        }
//		        signedData.close();
//		        return sig.verify();
//		    }
//		    catch (Exception ex) {
//		        // can we put a logger here please?
//		        return false;
//		    }
		
		
		
//		launch(args);
//		CertificateFactory fact = CertificateFactory.getInstance("X.509");
//		BouncyCastleProvider BC = new BouncyCastleProvider();
//		
//		X509Certificate cert = (X509Certificate)fact.generateCertificate(new FileInputStream("/home/karottop/Desktop/public/Certs/IRSIssuingCA.cer"));
////		System.out.println(ASN1Dump.dumpAsString(ASN1Primitive.fromByteArray(cert.getEncoded())));
////		List<X509Certificate> list = new ArrayList<>();
////		list.add(cert);
////		CollectionCertStoreParameters params = new CollectionCertStoreParameters(list);
////		CertStore store = CertStore.getInstance("Collection", params, BC);
////
////		//create cert path
////		List certChain = new ArrayList();
////		certChain.add(cert);
////		CertPath certPath = fact.generateCertPath(certChain);
//		
//	    // create CertPathBuilder that implements the "PKIX" algorithm
//		CertificateEncapsulater certEncap = new CertificateEncapsulater(new File("/home/karottop/Desktop/public/Certs/IRSRootTrustStore.p7b"));
//		Set<TrustAnchor> trustAnchors = new HashSet<TrustAnchor>();
//		for(X509Certificate certs : certEncap.getCerts()) {
//			trustAnchors.add(new TrustAnchor(certs, null));
//		}
//		X509CertSelector selector = new X509CertSelector();
//		selector.setCertificate(cert);
//		PKIXBuilderParameters anchorParams = new PKIXBuilderParameters(trustAnchors, selector);
//		
//		//validation
////		CertPathValidator certPathValidator = CertPathValidator.getInstance("PKIX", BC);
////		anchorParams.addCertStore(store);
////		anchorParams.setDate(new Date());
////		CertPathParameters parm = anchorParams;
////		try {
////		CertPathValidatorResult result = certPathValidator.validate(certPath, anchorParams);
////		System.out.println("certificate path validated");
////
////		} catch (CertPathValidatorException e) {
////		System.out.println("validation failed on certificate number " + e.getIndex() + ", details: " + e.getMessage());
////		}
//		
//	    CertPathBuilder cpb = null;
//	    try {
//	        cpb = CertPathBuilder.getInstance("PKIX", BC);
//	    } catch (NoSuchAlgorithmException nsae) {
//	        System.err.println(nsae);
//	    }
//	    // build certification path using specified parameters ("params")
//	    try {
//	        CertPathBuilderResult cpbResult = cpb.build(anchorParams);
//	        CertPath cp = cpbResult.getCertPath();
//	        System.out.println("build passed, path contents: " + cp);
//	    } catch (InvalidAlgorithmParameterException iape) {
//	        System.err.println("build failed: " + iape);
//	    } catch (CertPathBuilderException cpbe) {
//	        System.err.println("build failed: " + cpbe);
//	    }
////		
////		System.out.println(
////				ASN1Dump.dumpAsString(
////						ASN1Primitive.fromByteArray(
////								IOUtils.toByteArray(
////										new FileInputStream("/home/karottop/Desktop/public/Certs/uri_0-ocsp.ocsp")))));
////		
////		System.out.println(Runtime.getRuntime().availableProcessors());
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
