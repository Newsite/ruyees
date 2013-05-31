package com.landray.kmss.xdf.webservice;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import com.landray.kmss.xdf.webservice.peizhi.WSDLURL;

public class ToAdTdsUtils {

	private static final QName SERVICE_NAME = new QName(
			"http://webservice.xdf.kmss.landray.com/",
			"IToAdTdsWebServiceService");

	public static URL WSDL_LOCATION;

	static {
		URL url = null;
		try {
			url = new URL(WSDLURL.AD_WSDLURL);
		} catch (MalformedURLException e) {
			java.util.logging.Logger.getLogger(
					IAttMainWebServiceService.class.getName()).log(
					java.util.logging.Level.INFO,
					"Can not initialize the default wsdl from {0}",
					WSDLURL.AD_WSDLURL);
		}
		WSDL_LOCATION = url;
	}

	public static int addPerson(SysOrgUser user) throws Exception {
		URL wsdlURL = WSDL_LOCATION;
		IToAdTdsWebServiceService ss = new IToAdTdsWebServiceService(wsdlURL,
				SERVICE_NAME);
		IToAdTdsWebService port = ss.getIToAdTdsWebServicePort();
		return port.addPerson(user);
	}

}
