package fun.personaluse.listeners;

import com.zeva.tlGen.dataModel.CertificateBean;

import fun.personaluse.certdisplay.CertDisplayer;
import fun.personaluse.certdisplay.CertTable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;

public class OnSelectionChangeListener implements ChangeListener<CertificateBean>{
	
	private TextArea textArea;
	private CertTable certTable;
	
	public OnSelectionChangeListener(TextArea textArea, CertTable certTable) {
		this.textArea = textArea;
		this.certTable = certTable;
	}

	@Override
	public void changed(ObservableValue<? extends CertificateBean> observable, CertificateBean oldValue,
			CertificateBean newValue) {
		// if tableview, display the overriden certificate bean string
			Object item = certTable.getSelectionModel().getSelectedItem();
			if (item != null)
				textArea.setText(item.toString());
		
	}

}
