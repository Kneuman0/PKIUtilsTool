package fun.personaluse.certdisplay;

import com.zeva.tlGen.dataModel.CertificateBean;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class CertDisplayerCallback implements Callback<TableColumn<CertificateBean, CertificateBean>,
TableCell<CertificateBean, CertificateBean>>{

	@Override
	public TableCell<CertificateBean, CertificateBean> 
	call(TableColumn<CertificateBean, CertificateBean> param) {
		// TODO Auto-generated method stub
		return new CertDisplayCellValueMaker();
	}

}
