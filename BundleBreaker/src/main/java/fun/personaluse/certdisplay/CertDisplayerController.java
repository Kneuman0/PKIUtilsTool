package fun.personaluse.certdisplay;

import java.util.List;
import java.util.Optional;

import com.zeva.tlGen.dataModel.CertificateBean;

import biz.ui.controller.utils.ControllerUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CertDisplayerController extends ControllerUtils{
	
    @FXML
    private TableView<CertificateBean> certTable;

    @FXML
    private TableColumn<CertificateBean, CertificateBean> certTableCol;

    @FXML
    private TextArea certTextArea;
    
    private ObservableList<CertificateBean> certs;

	@Override
	public void initialize() {
	}
	
	public void onClickedCert(){
		CertificateBean bean = certTable.getSelectionModel().getSelectedItem();
		if (bean != null) return;
		certTextArea.setText(bean.toString());
	}
	


	
	
	
	
	
	

}
