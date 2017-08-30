package fun.personaluse.listeners;

import java.util.List;
import java.util.Optional;

import biz.ui.controller.utils.ControllerUtils;
import fun.personalacademics.model.CertificateBean;
import fun.personalacademics.utils.TrustListUtilFactory;
import fun.personaluse.certdisplay.CertTable;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

@SuppressWarnings("restriction")
public class OnDeleteCert extends ControllerUtils implements EventHandler<KeyEvent>{
	
	private TextArea display;
	
	public OnDeleteCert(TextArea displayArea) {
		this.display = displayArea;
	}

	@Override
	public void handle(KeyEvent event) {
		if(event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE){
			// get tableview source
			CertTable table = (CertTable)event.getSource();
			// make sure user really wants to delete
			
			Optional<ButtonType> optional = super.displayQueryToContinuePrompt("Confirm Delete", 
					"Deleting cannot be undone, are you sure you\n"
					+ "want to delete the highlighted certificates?", null);
			if(optional.get() == ButtonType.YES){
				List<CertificateBean> beans = table.getSelectionModel().getSelectedItems();
				if(beans != null){
					TrustListUtilFactory.deleteSelectedItems(table.retriveItems(), beans);	
					display.clear();
				}
			}
		}
	}

	@Override
	public void initialize() {
		// leave blank
	}
	
	
}
