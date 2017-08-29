package fun.personaluse.crypto;

import java.io.File;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;

import biz.ui.controller.utils.IPopupController;
import fun.personalacademics.controllers.CryptToolController;
import fun.personalacademics.model.CertificateBean;
import fun.personalacademics.utils.CertificateUtilities;
import fun.personalacademics.utils.RadixConverter;
import fun.personaluse.certdisplay.CertDisplayer;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class BundleBreakerController extends CryptToolController implements IPopupController{
	
    @FXML
    private VBox certDisplayVbox;
    
    @FXML
    private TextArea b64TextArea,
    				hashInput,
    				hashOutput,
    				temp,
    				hextToHumanTextBox,
    				hextToDecimalTextBox,
    				formatHexField;
    
    private CertDisplayer certDisplayer;
    
    @FXML
    private RadioButton sha3512,
    					sha1RB,
    					ecohRB,
    					sha256RB,
    					md5;
    
	@Override
	public void initialize() {
		this.certDisplayer = new CertDisplayer();
		certDisplayVbox.getChildren().add(certDisplayer);
	}
 
    @FXML
    public void OnClose() {
    	this.stage.close();
    }

    @FXML
    public void onBreakBundleListener() {
    	List<File> bundles = super.getBundleLocations();
    	if(bundles == null || bundles.size() == 0) return;
    	List<CertificateBean> certs = super.listBundles(bundles);
    	certDisplayer.getCertList().addAll(certs);
    	if(certs == null || certs.size() == 0) return;
    	exportCerts(certs);
    }

    @FXML
    public void onBreakPemListener() {
    	List<File> pems = super.getPEMLocations();
    	if(pems == null || pems.size() == 0) return;
    	List<CertificateBean> certs = super.listPEMs(pems);
    	certDisplayer.getCertList().addAll(certs);
    	if(certs == null || certs.size() == 0) return;
    	exportCerts(certs);
    }
    
    private void exportCerts(List<CertificateBean> certs){
    	displayMessage("Export", "Please Choose a location to export", null);
    	File exportFolder = super.getExportLocation();
    	if(exportFolder == null) return;
    	super.exportToCerts(exportFolder, certs);
    	displayMessage("Success!", "Your certificates sucessfully exported!",
    			"Location: " + exportFolder.getAbsolutePath());
    }

    @FXML
    public void combineCertsListener() {
    	List<File> certLocations = super.requestFiles("X509 Certs", null, CertificateUtilities.X509_CERT_EXTS);
    	if(certLocations == null || certLocations.size() == 0) return;
    	displayMessage("Export", "Please Choose a location to export", null);
    	File exportFolder = super.getExportLocation();
    	if(exportFolder == null) return;
    	List<CertificateBean> certs = super.encapsulateX509Certs(certLocations);
    	certDisplayer.getCertList().addAll(certs);
    	super.exportCertsToPem(certs, exportFolder);
    }
    
    public void onInspectCerts(){
    	System.out.println(CertificateUtilities.ALL_CERT_EXTS);
    	List<File> certLocations = super.requestFiles("Import Certs", null, CertificateUtilities.ALL_CERT_EXTS);
    	if(certLocations == null || certLocations.isEmpty()) return;
    	List<CertificateBean> certs = super.getCertificates(certLocations);
    	certDisplayer.getCertList().addAll(certs);
    	try {
			System.out.println(getValidationPath(certs.get(0)));
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void onInspectKeyStore(){
    	List<CertificateBean> beans = importDefaultJavaKeyStores();
    	if(beans != null && !beans.isEmpty()) certDisplayer.getCertList().addAll(beans);
    }
    
    public void onTextToCertConversion(){
    	try {
    		//display fixed text
    		
    		b64TextArea.setText(CertificateUtilities.toPemFormat(b64TextArea.getText()));
    		List<CertificateBean> certs = super.convertBase64IntoCert(b64TextArea.getText());
			certDisplayer.getCertList().addAll(certs);
			
			displayMessage("Certs Added", certs.size() + " cert/s have been added", null);			
		} catch (Exception e) {
			displayErrorMessage("Certificate Error", "Your base64 was not in the proper form",
					"If you are copying and pasting multiple base 64 certs\n"
					+ "you must encapsulate each base 64 string with '-----BEGIN CERTIFICATE-----\n"
					+ "and -----END CERTIFICATE-----'", e);
		}
    }
    
    public void onExportCertsToPEM(){
    	if(certDisplayer.getSelectedCerts() == null || certDisplayer.getSelectedCerts().size() == 0){
    		displayErrorMessage("No Certs Selected", "You must select the "
    				+ "certificates you want to export", null,null);
    		return;
    	}
    	File location = requestSave("Export to PEM", null);
    	if(location != null){
    		super.exportCertsToPem(certDisplayer.getSelectedCerts(), location);	
    		displayMessage("Success!", "Your" + certDisplayer.getSelectedCerts().size()
    				+ "certificates sucessfully exported!",
        			"Location: " + location.getAbsolutePath());
    	}    	
    }
    
    public void onExportCertstoCER(){
    	if(certDisplayer.getSelectedCerts() == null || certDisplayer.getSelectedCerts().size() == 0){
    		displayErrorMessage("No Certs Selected", "You must select the "
    				+ "certificates you want to export", null,null);
    		return;
    	}
    	exportCerts(certDisplayer.getSelectedCerts());
    }
    
    public void onHash(){
    	if(sha1RB.isSelected()){
    		hashOutput.setText(hashSha1(hashInput.getText()));
    	}else if(sha256RB.isSelected()){
    		hashOutput.setText(hashSha256(hashInput.getText()));
    	}else if(sha3512.isSelected()){
    		try {
				hashOutput.setText(hashSha3(hashInput.getText()));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
    	}else if(md5.isSelected()){
    		hashOutput.setText(hashmd5(hashInput.getText()));
    	}else{
    		
    	}
    }
    
    public void onConvertHexToHumanReadable(){
    	if(hextToHumanTextBox.getText().isEmpty()) return;
    	hextToHumanTextBox.setText(RadixConverter.hexToASCII(hextToHumanTextBox.getText()));
    }

    public void onConvertHexToDecimal(){
    	if(hextToDecimalTextBox.getText().isEmpty()) return;
    	hextToDecimalTextBox.setText(
    			RadixConverter.hexToDecimal(
    					hextToDecimalTextBox.getText()));
    }
    
    public void onConvertToColonSepHex(){
    	System.out.println(CertificateUtilities.toColonSepHex(formatHexField.getText()));
    	formatHexField.setText(CertificateUtilities.toColonSepHex(formatHexField.getText()));
    }
    
    public void onGetCertsFromURL(){
    	List<CertificateBean> beans = getCertsFromURL();
    	if(beans.isEmpty() || beans == null) return;
    	certDisplayer.getCertList().addAll(beans);
    }

}
