package fun.personaluse.crypto;

import javax.swing.JOptionPane;

import com.sun.javafx.application.LauncherImpl;

@SuppressWarnings("restriction")
public class BundleBreakerMain {
	
	public static void main(String[] args){
		// Check Java version before launching the JavaFX application
		String version = System.getProperty("java.version");
		int major = parseMajorVersion(version);
		if (major < 17) {
			JOptionPane.showMessageDialog(null,
				"This application requires Java 17 or later.\n" +
				"Detected Java version: " + version + "\n" +
				"Please install a current Java runtime and try again.",
				"Java Version Not Supported",
				JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		
		LauncherImpl.launchApplication(BundleBreakerApp.class, null);
	}
	
	private static int parseMajorVersion(String version) {
		if (version == null || version.isEmpty()) {
			return 0;
		}
		// Java 8 and earlier: 1.x.y, Java 9+ : x.y.z
		if (version.startsWith("1.")) {
			version = version.substring(2);
		}
		int dotIndex = version.indexOf('.');
		String majorStr = dotIndex > 0 ? version.substring(0, dotIndex) : version;
		try {
			return Integer.parseInt(majorStr);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

}
