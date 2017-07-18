package fun.personaluse.certdisplay;


import fun.personalacademics.model.CertificateBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CertTable extends TableView<CertificateBean>{
	
    private TableColumn<CertificateBean, CertificateBean> certTableCol;
    private ObservableList<CertificateBean> certs;
	
	public CertTable(){
		this.certTableCol = new TableColumn<>();
		getColumns().add(this.certTableCol);
		initializeTable();
		this.certs = FXCollections.observableArrayList();
		super.setItems(certs);
		this.certTableCol.setText("Certificates");
		this.certTableCol.prefWidthProperty().bind(widthProperty());
		setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	}
	
	private void initializeTable(){
		certTableCol.setCellValueFactory(new PropertyValueFactory<CertificateBean, CertificateBean>("name"));
		setFixedCellSize(60);
		certTableCol.setCellFactory(new CertDisplayerCallback());
		getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
	public ObservableList<CertificateBean> retriveItems(){
		return certs;
	}
	
	public void inputItems(ObservableList<CertificateBean> certs){
		this.certs.clear();
		this.certs.addAll(certs);
	}
	
	

}
