package fun.personaluse.listeners;


import fun.personaluse.certdisplay.CertTable;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class OnMouseClicked implements EventHandler<MouseEvent> {

	public TextArea textArea;

	public OnMouseClicked(TextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void handle(MouseEvent event) {
		// if tableview, display the overriden certificate bean string
		if (event.getSource() instanceof CertTable) {
			CertTable table = (CertTable) event.getSource();
			Object item = table.getSelectionModel().getSelectedItem();
			if (item != null)
				textArea.setText(item.toString());
		}
	}

}
