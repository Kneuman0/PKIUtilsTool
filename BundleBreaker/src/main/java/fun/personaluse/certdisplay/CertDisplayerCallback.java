package fun.personaluse.certdisplay;

import fun.personalacademics.model.CertificateBean;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

@SuppressWarnings("restriction")
public class CertDisplayerCallback implements Callback<TableColumn<CertificateBean, CertificateBean>,
TableCell<CertificateBean, CertificateBean>>{

	@Override
	public TableCell<CertificateBean, CertificateBean> 
	call(TableColumn<CertificateBean, CertificateBean> param) {
		// TODO Auto-generated method stub
		return new CertDisplayCellValueMaker();
	}

}
