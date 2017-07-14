package fun.personaluse.crypto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.DatatypeConverter;

import com.zeva.certs.utils.DataCertificateUtilities;
import com.zeva.tlGen.dataModel.CertificateBean;
import com.zeva.tlGen.utils.CertificateEncapsulater;
import com.zeva.tlGen.utils.CertificateEncapsulater.CERT_TYPES;
import com.zeva.tlGen.utils.CertificateUtilities;

import biz.ui.controller.utils.ControllerUtils;
import biz.ui.controller.utils.IPopupController;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public abstract class CryptToolController extends ControllerUtils implements IPopupController{
	
	@Override
	public abstract void initialize();
	
	protected List<CertificateBean> listBundle (File bundleLoc){
		CertificateEncapsulater certEncap = null;
		try {
			certEncap = new CertificateEncapsulater(bundleLoc, CERT_TYPES.P7B);
			return certEncap.getEncapulatedCerts();
		} catch (Exception e) {
			displayErrorMessage("Bundle Error", "There was an Error reading the bundle:", null, e);
			return null;
		}

	}
	
	protected List<File> getPEMLocations(){
		return requestFiles("PEM Locations", null, getPemExtensionFilter());
	}
	
	protected File getPEMLocation(){
		return requestFile("PEM Location", null, getPemExtensionFilter());
	}
		
	protected List<CertificateBean> listPEM(File pemLoc){
		CertificateEncapsulater certEncap = null;
		try {
			certEncap = new CertificateEncapsulater(pemLoc, CERT_TYPES.PEM);
			return certEncap.getEncapulatedCerts();
		} catch (Exception e) {
			displayErrorMessage("PEM Error", "There was an Error reading the PEM:", null, e);
			return null;
		}
	}
	
	protected List<CertificateBean> listPEMs(List<File> pemLocations){
		List<CertificateBean> certs = new ArrayList<>();
		for(File bundle : pemLocations){
			certs.addAll(listPEM(bundle));
		}
		
		return certs;
	}
	
	public List<CertificateBean> encapsulateX509Certs(List<File> certs){
		List<CertificateBean> beans = new ArrayList<>();
		for(File cert : certs){
			try {
				CertificateEncapsulater certEncap = new CertificateEncapsulater(cert, CERT_TYPES.CER);
				beans.addAll(certEncap.getEncapulatedCerts());
			} catch (Exception e) {
				displayErrorMessage("X509 Cert Error", "Error Reading X509 Cert: ", null, e);
			}
		}
		
		return beans;
	}
	
	public void exportCertsToPem(List<CertificateBean> certs, File location) {

		List<String> encodedCerts = new ArrayList<>();
		for (CertificateBean cert : certs) {
			encodedCerts.add(CertificateUtilities.toPemFormat(cert));
			System.out.println(CertificateUtilities.toPemFormat(cert));
		}

		try(PrintWriter writer = new PrintWriter(new FileWriter(location));){
			for (String formattedCert : encodedCerts) {
				writer.println(formattedCert);
			}
		} catch(IOException e){
			displayErrorMessage("File Error", "Error Saving to File: ", null, e);
		}
	}
	
	protected List<CertificateBean> listBundles(List<File> bundleLocations){
		List<CertificateBean> certs = new ArrayList<>();
		for(File bundle : bundleLocations){
			certs.addAll(listBundle(bundle));
		}
		
		return certs;
	}
	
	protected void exportToCerts(File dirLocation, List<CertificateBean> certs){
		for(CertificateBean cert : certs){
			String fileName = (dirLocation.getAbsolutePath() + "/" + cert.getStringName()
			+ cert.getParentCert().getSerialNumber().toString() + ".cer").replace("\\", "/");
			try(FileOutputStream out = new FileOutputStream(new File(fileName))){
				out.write(cert.getParentCert().getEncoded());
			} catch (IOException | CertificateEncodingException e) {
				displayErrorMessage("Export Error", "Error Exporting Certificates: ", null, e);
			}
		}
	}
	
	protected List<CertificateBean> getCertificates(List<File> certFiles){
		List<CertificateBean> certs = new ArrayList<>();
		for(File file : certFiles){
			try {
				CertificateEncapsulater certEncap = new CertificateEncapsulater(file);
				certs.addAll(certEncap.getEncapulatedCerts());
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				continue;
			}
		}
		
		return certs;
	}
	
	protected List<CertificateBean> convertBase64IntoCert(String b64) throws Exception{
		String correctPEM = CertificateUtilities.toPemFormat(b64);
		System.out.println(correctPEM);
		return new CertificateEncapsulater(correctPEM, CERT_TYPES.PEM).getEncapulatedCerts();
	}
	
	
	public File getBundleLocation(){
		return requestFile("Bundle Location", null, getBundleExtensionFilter());
	}
	
	public List<File> getBundleLocations(){
		return requestFiles("Bundle Locations", null, getBundleExtensionFilter());
	}
	
	public File getExportLocation(){
		return requestDirectory("Export Location", null);
	}
	
	public static ExtensionFilter getBundleExtensionFilter(){
		return new ExtensionFilter("Bundles", "*.p7b", "*.p7c", 
				"*.P7B", "*.P7C");
	}
	
	public static ExtensionFilter getPemExtensionFilter(){
		return new ExtensionFilter("PEM Files",  "*.PEM", "*.pem");
	}
	
	public static ExtensionFilter getX509CertsExtensionFilter(){
		return new ExtensionFilter("X509 Certs", "*.cer", "*.CER", "*.der", "*.DER");
	}
	
	public static ExtensionFilter getAllCertFileExtensionFilters(){
		return new ExtensionFilter("Cert Files", "*.p7b", "*.p7c", 
				"*.P7B", "*.P7C", "*.PEM", "*.pem", "X509 Certs", "*.cer", "*.CER", "*.der", "*.DER");
	}
	

}
