package fun.personaluse.certdisplay;

import biz.ui.controller.utils.ControllerUtils;
import fun.personalacademics.model.CertificateBean;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class CertDisplayerController extends ControllerUtils{
	
    @FXML
    private TableView<CertificateBean> certTable;

    @FXML
    private TableColumn<CertificateBean, CertificateBean> certTableCol;

    @FXML
    private TextArea certTextArea;
    
	@Override
	public void initialize() {
	}
	
	public void onClickedCert(){
		CertificateBean bean = certTable.getSelectionModel().getSelectedItem();
		if (bean == null) return;
		certTextArea.setText(bean.toString());
	}
	


	
	
	
	
	
	

}
