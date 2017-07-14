package com.zeva.tlGen.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import javafx.scene.control.TreeItem;
import javafx.stage.FileChooser.ExtensionFilter;

import com.zeva.temp.jaxb.datamodel.DigitalIdentityType;
import com.zeva.temp.jaxb.datamodel.TrustStatusListType;
import com.zeva.tlGen.dataModel.CertificateBean;
import com.zeva.tlGen.dataModel.ProviderAttribute;

public class CertificateUtilFactory extends CertificateUtilities{
		
	public List<CertificateBean> getSampleCerts() {
		List<CertificateBean> things = new ArrayList<CertificateBean>();
		things.add(new CertificateBean());
		things.add(new CertificateBean());
		CertificateBean bean = new CertificateBean();
		bean.setChildrenCerts(things);
		things.add(bean);
		return things;
	}

	public void deleteSelectedItems(List<TreeItem<Object>> all,
			List<TreeItem<Object>> selected) {
		for (int i = selected.size() - 1; i >= 0; i--) {
			all.remove(selected.get(i));
		}
	}
	
	
	public PrivateKey getPrivateKey(String password, String alias){
		PrivateKey privKey = null;
		try {
			String storeName = "KeyStore.jks";
		    
		    KeyStore inStore = KeyStore.getInstance("PKCS12");
		    inStore.load(new FileInputStream(storeName), password.toCharArray());
		    privKey = (PrivateKey)inStore.getKey(alias, password.toCharArray());
		  } catch (Exception e) {
		    e.printStackTrace();
		    throw new AssertionError(e.getMessage());
		  }
		return privKey;
	}
	
	public X509Certificate getCertificate(String alias, String password){
		String storeName = "KeyStore.jks";
		X509Certificate cert = null;
		
		try {
			KeyStore outStore = KeyStore.getInstance("PKCS12");
			InputStream stream = new FileInputStream(storeName);
			outStore.load(stream, password.toCharArray());
			cert = (X509Certificate)outStore.getCertificate(alias);
			stream.close();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cert;
	}
	
	public void inputPrivateKeyAndCert(String password, String keyAlias,
			String certAlias, X509Certificate cert, PrivateKey key){
		try {
			String storeName = "KeyStore.jks";

		    // Note: if you just want to store this certificate then write the
		    // contents of selfCert.getEncoded() to file

		    Certificate[] outChain = { cert };
		    InputStream stream = new FileInputStream(storeName);
		    KeyStore outStore = KeyStore.getInstance("PKCS12");
		    outStore.load(stream, password.toCharArray());
		    outStore.setKeyEntry(keyAlias, key, password.toCharArray(),
		        outChain);
		    outStore.setCertificateEntry(certAlias, cert);
		    stream.close();
		    OutputStream outputStream = new FileOutputStream(storeName);
		    outStore.store(outputStream, password.toCharArray());
		    outputStream.flush();
		    outputStream.close();
	 	} catch (Exception e) {
		    e.printStackTrace();
		    throw new AssertionError(e.getMessage());
		}
	}
	
	public void inputCertificate(X509Certificate cert, String password, String alias){
		try{
			String storeName = "KeyStore.jks";
			KeyStore outStore = KeyStore.getInstance("PKCS12");
			InputStream stream = new FileInputStream(storeName);
		    outStore.load(stream, password.toCharArray());
		    outStore.setCertificateEntry(alias, cert);
		    stream.close();
		    
		    OutputStream outputStream = new FileOutputStream(storeName);
		    outStore.store(outputStream, password.toCharArray());
		    outputStream.flush();
		    outputStream.close();
		} catch (Exception e) {
		    e.printStackTrace();
		    throw new AssertionError(e.getMessage());
		}
	}

	public List<ExtensionFilter> getSupportedExtFilters() {
		List<ExtensionFilter> filters = new ArrayList<>();
		filters.add(new ExtensionFilter("All Supported Certificates", "*.pem",
				"*.cer"));
		filters.add(new ExtensionFilter("Privacy Enhanced Mail", "*.pem"));
		filters.add(new ExtensionFilter("Security Certificate", "*.cer"));
		return filters;
	}

	public TrustStatusListType buildTrustList(List<ProviderAttribute> beans, String defaultLocation) throws FileNotFoundException {
		
//		TrustList tl = XmlUtilities.<TrustList>readInObjectFromXML(defaultLocation);
//		tl.setTsps(beans);

		return null;
	}

	

	public void exportNodesToPem(List<TreeItem<ProviderAttribute>> nodes,
			File location) throws CertificateEncodingException, IOException {
		List<X509Certificate> certs = new ArrayList<>();
		for (TreeItem<ProviderAttribute> node : nodes)
			certs.add(node.getValue().<DigitalIdentityType>getEncapsulatedBean().getParentCert());

		exportCertsToPem(certs, location);

	}


	public static void exportCertsToPem(List<X509Certificate> certs,
			File location) throws CertificateEncodingException, IOException {

		List<String> encodedCerts = new ArrayList<>();
		for (X509Certificate cert : certs) {
			byte[] binary = cert.getEncoded();
			String base64 = DatatypeConverter.printBase64Binary(binary);
			String formatedBase64 = insertPeriodically(
					base64.replaceAll("\r", ""), "\r", 65);
			String begin = "-----BEGIN CERTIFICATE-----\n";
			String end = "\n-----END CERTIFICATE-----";
			encodedCerts.add(begin + formatedBase64 + end);
		}

		PrintWriter writer = new PrintWriter(new FileWriter(location));
		for (String formattedCert : encodedCerts) {
			writer.println(formattedCert);
		}
		writer.close();
	}

}
