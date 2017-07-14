package fun.personaluse.crypto;

import biz.ui.launchers.generic.AppLauncher;
import javafx.application.Application;
import javafx.stage.Stage;

public class BundleBreakerApp extends AppLauncher<BundleBreakerController>{

	@Override
	public String getPathtoFXML() {
		return "/resources/bundleBreakerGUI.fxml";
	}

	@Override
	public void init() {
		
	}

	@Override
	public String getStageTitle() {
		return "Bundle Breaker";
	}

}
