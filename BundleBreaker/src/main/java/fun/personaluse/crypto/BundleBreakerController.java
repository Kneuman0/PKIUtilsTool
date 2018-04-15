package fun.personaluse.crypto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.management.modelmbean.XMLParseException;
import javax.xml.bind.JAXBException;

import biz.ui.controller.utils.IPopupController;
import biz.ui.filesystem.FriendlyExtensionFilter;
import fun.personalacademics.controllers.TrustListParsingController;
import fun.personalacademics.model.CertificateBean;
import fun.personalacademics.popup.GetURLPopup;
import fun.personalacademics.utils.CertificateUtilities;
import fun.personalacademics.utils.RadixConverter;
import fun.personaluse.certdisplay.CertDisplayer;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

@SuppressWarnings("restriction")
public class BundleBreakerController extends TrustListParsingController implements IPopupController{
	
    @FXML
    private VBox certDisplayVbox;
    
    @FXML
    private TextArea b64TextArea,
    				hashInput,
    				hashOutput,
    				temp,
    				hextToHumanTextBox,
    				hextToDecimalTextBox,
    				formatHexField,
    				checksumTextField;
    
    private CertDisplayer certDisplayer;
    
    @FXML
    private RadioButton sha3512,
    					sha1RB,
    					ecohRB,
    					sha256RB,
    					md5;
    
    @FXML
    private TextField checksumFile;
    
    @FXML
    private ComboBox<String> hashAlgComboBox;
    
	@Override
	public void initialize() {
		this.certDisplayer = new CertDisplayer();
		certDisplayVbox.getChildren().add(certDisplayer);
		hashAlgComboBox.getItems().addAll("MD5", "SHA1", "SHA-256");
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
    	File exportFolder = super.getExportDirectory();
    	if(exportFolder == null) return;
    	super.exportToCerts(exportFolder, certs);
    	displayMessage("Success!", "Your certificates sucessfully exported!",
    			"Location: " + exportFolder.getAbsolutePath());
    }

    
    public void onInspectCerts(){
    	System.out.println(CertificateUtilities.ALL_CERT_EXTS);
    	List<File> certLocations = super.requestFiles("Import Certs", null, CertificateUtilities.ALL_CERT_EXTS);
    	if(certLocations == null || certLocations.isEmpty()) return;
    	List<CertificateBean> certs = super.getCertificates(certLocations);
    	certDisplayer.getCertList().addAll(certs);
//    	try {
//			System.out.println(getValidationPath(certs.get(0)));
//		} catch (CertificateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
    
    public void onExportToBundle(){
    	if(certDisplayer.getSelectedCerts() == null || certDisplayer.getSelectedCerts().size() == 0){
    		displayErrorMessage("No Certs Selected", "You must select the "
    				+ "certificates you want to export", null,null);
    		return;
    	}
    	try {
			saveCertsToPKCS7File(certDisplayer.getSelectedCerts());
		} catch (Exception e) {
			displayErrorMessage("Export Error", "An Error Occured when Exporting Bundle",null, e);
			return;
		}
    }
    
    public void onImportTrustListCerts(){
    	List<CertificateBean> beans = null;
    	try {
			beans = getCertsFromTrustList();
		} catch (JAXBException | XMLParseException e) {
			displayErrorMessage("Trust List Parsing Error", "Error Parsing Trust List", null, e);
		}
    	
    	if(beans == null || beans.isEmpty()) return;
    	
    	certDisplayer.getCertList().addAll(beans);    	
    }
    
    public void onImportTrustListCertsFromURL(){
    	List<CertificateBean> beans = null;
    	try {
			beans = getCertsFromTrustListURL();
		} catch (JAXBException | XMLParseException | IOException e) {
			displayErrorMessage("Trust List Parsing Error", "Error Parsing Trust List", null, e);
		}
    	
    	if(beans == null || beans.isEmpty()) return;
    	
    	certDisplayer.getCertList().addAll(beans); 
    }
    
    public void onBrowseForChecksumFile() {
    	File file = super.requestFile("Select Checksum File", null);
    	if(file == null) return;
    	this.checksumFile.setText(file.getAbsolutePath());
    }
    
    public void onValidateChecksum() {
    	if(this.checksumFile.getText().isEmpty()) return;
    	File file = new File(this.checksumFile.getText()
    			.replace(File.pathSeparatorChar, '/'));
    	String hashingAlg = "";
    	if(hashAlgComboBox.getSelectionModel().isEmpty()) {
    		displayErrorMessage("Hash Alg.", "You must choose a Hashing Algorithm",
    				null, null);
    		return;
    	}else {
    		hashingAlg = hashAlgComboBox.getSelectionModel().getSelectedItem().toString();
    	}
    	
    	try {
    		String result = super.checksumIsValid(file, hashingAlg,
    				checksumTextField.getText()) ? "Pass" : "Fail";
			this.checksumTextField.setText(String.format("Result: %s\n%s: %s", 
					result, hashingAlg, super.checkSumFile(file, hashingAlg))); 			
		} catch (Exception e) {
			displayErrorMessage("Error Calculating Hash",
					"The Following Error Occured: ", null, e);
		}
    }
    
    public void onImportCertsFromAATL() {
    	File xml = requestFile("AATL XML", "", new FriendlyExtensionFilter("XML File","*.xml").get());
    	if(xml == null) return;
    	try {
			this.certDisplayer.getCertList().addAll(super.extractCertsFromAATLXML(xml));
		} catch (FileNotFoundException e) {
			displayErrorMessage("Error Parsing AATL XML", 
					"Error parsing AATL XML - please make sure it was\nexported from the PDF correctly",
					null, e);
		}
    }
    
    public void onImportCertsFromAATLUrl() {
      	try {
			this.certDisplayer.getCertList().addAll(extractCertsFromAATLURL());
		} catch (Exception e) {
			displayErrorMessage("Error Parsing AATL XML", 
					"Error parsing AATL URL - please make sure the URL\npoints to the Adobe AATL PDF",
					null, e);
		}
    }

}
