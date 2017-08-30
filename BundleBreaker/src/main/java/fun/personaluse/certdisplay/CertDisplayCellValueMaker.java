package fun.personaluse.certdisplay;


import fun.personalacademics.model.CertificateBean;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;

@SuppressWarnings("restriction")
public class CertDisplayCellValueMaker extends TableCell<CertificateBean, CertificateBean>{

	GridPane grid;
	Label name;
	Label serialNumber;
	VBox innerVB;
	GridPane innerLowerGrid;
	TextFlow flow;
	
	public CertDisplayCellValueMaker(){
				
		grid = new GridPane();
		innerVB = new VBox();
		innerVB.setPadding(new Insets(0, 0, 0, 3));
		innerLowerGrid = new GridPane();
		
		serialNumber = new Label("");
		serialNumber.setFont(Font.font ("System", 10));
		innerLowerGrid.add(serialNumber, 1, 0);
		name = new Label();
		name.setPrefWidth(Region.USE_COMPUTED_SIZE);
		
		innerVB.getChildren().addAll(name, serialNumber);
		
		
		// set lower nested grid
		grid.add(innerVB, 2, 0);
		setGraphic(grid);
	}
	
	@Override
	public void updateItem(CertificateBean item, boolean empty){
		super.updateItem(item, empty);
		
		if(item == null || empty){
			setGraphic(null);		
		}else{
			setGraphic(grid);
			name.setText(item.getStringName());
			if(item.getParentCert() != null){
				serialNumber.setText("SN: " + item.getParentCert().getSerialNumber().toString());
						
			}
			
		}
	}

	
}
