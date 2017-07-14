package fun.personaluse.certdisplay;


import java.util.List;

import com.zeva.tlGen.dataModel.CertificateBean;

import fun.personaluse.listeners.OnDeleteCert;
import fun.personaluse.listeners.OnMouseClicked;
import fun.personaluse.listeners.OnSelectionChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CertDisplayer extends VBox{
	
	
    @FXML
    private CertTable certTable;

    @FXML
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
		certTextArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		setVgrow(certTable, Priority.ALWAYS);
		setVgrow(certTextArea, Priority.ALWAYS);
	}
	

	
	public ObservableList<CertificateBean> getCertList(){
		return certTable.retriveItems();
	}
	
	public List<CertificateBean> getSelectedCerts(){
		return certTable.getSelectionModel().getSelectedItems();
	}
	
	

	
	

}
