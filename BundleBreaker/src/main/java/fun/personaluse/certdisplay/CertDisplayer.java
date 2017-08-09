package fun.personaluse.certdisplay;


import java.util.List;

import fun.personalacademics.model.CertificateBean;
import fun.personaluse.listeners.OnDeleteCert;
import fun.personaluse.listeners.OnMouseClicked;
import fun.personaluse.listeners.OnSelectionChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class CertDisplayer extends VBox{
	
    private CertTable certTable;
    private TextArea certTextArea;
    
    
	
	public CertDisplayer(){
		super(10);
		this.certTable = new CertTable();
		certTextArea = new TextArea();
		certTextArea.setPrefHeight(300);
		certTable.setOnKeyPressed(new OnDeleteCert(certTextArea));
		certTable.setOnMouseClicked(new OnMouseClicked(certTextArea));
		certTable.getSelectionModel().selectedItemProperty()
			.addListener(new OnSelectionChangeListener(certTextArea, certTable));
		getChildren().addAll(certTable, certTextArea);
		setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		certTextArea.setMaxSize(Double.MAX_VALUE, 500);
	}
	

	
	public ObservableList<CertificateBean> getCertList(){
		return certTable.retriveItems();
	}
	
	public List<CertificateBean> getSelectedCerts(){
		return certTable.getSelectionModel().getSelectedItems();
	}
	
	

	
	

}
