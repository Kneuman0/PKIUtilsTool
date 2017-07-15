package fun.personaluse.crypto;

import java.io.File;
import java.util.List;
import java.util.Optional;

import com.zeva.tlGen.dataModel.CertificateBean;
import com.zeva.tlGen.utils.CertificateUtilities;

import biz.ui.controller.utils.IPopupController;
import fun.personaluse.certdisplay.CertDisplayer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;

public class BundleBreakerController extends CryptToolController implements IPopupController{
	
    @FXML
    private VBox certDisplayVbox;
    
    @FXML
    private TextArea b64TextArea;
    
    private CertDisplayer certDisplayer;

    
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
    	List<File> certLocations = super.requestFiles("X509 Certs", null, getX509CertsExtensionFilter());
    	if(certLocations == null || certLocations.size() == 0) return;
    	displayMessage("Export", "Please Choose a location to export", null);
    	File exportFolder = super.getExportLocation();
    	if(exportFolder == null) return;
    	List<CertificateBean> certs = super.encapsulateX509Certs(certLocations);
    	certDisplayer.getCertList().addAll(certs);
    	super.exportCertsToPem(certs, exportFolder);
    }
    
    public void onInspectCerts(){
    	List<File> certLocations = super.requestFiles("Import Certs", null, getAllCertFileExtensionFilters());
    	if(certLocations == null || certLocations.isEmpty()) return;
    	List<CertificateBean> certs = super.getCertificates(certLocations);
    	certDisplayer.getCertList().addAll(certs);
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

	

}
