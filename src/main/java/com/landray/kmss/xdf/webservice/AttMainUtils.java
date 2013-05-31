package com.landray.kmss.xdf.webservice;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import com.landray.kmss.xdf.webservice.peizhi.WSDLURL;

public class AttMainUtils {

	private static final QName SERVICE_NAME = new QName(
			"http://webservice.xdf.kmss.landray.com/",
			"IAttMainWebServiceService");

	public static URL WSDL_LOCATION;

	

	static {
		URL url = null;
		try {
			url = new URL(WSDLURL.ATT_WSDLURL);
		} catch (MalformedURLException e) {
			java.util.logging.Logger.getLogger(
					IAttMainWebServiceService.class.getName())
					.log(java.util.logging.Level.INFO,
							"Can not initialize the default wsdl from {0}",
							WSDLURL.ATT_WSDLURL);
		}
		WSDL_LOCATION = url;
	}

	public static ReturnMsg addAttMain(AttMain attMain) throws Exception {
		URL wsdlURL = WSDL_LOCATION;
		IAttMainWebServiceService ss = new IAttMainWebServiceService(wsdlURL,
				SERVICE_NAME);
		IAttMainWebService port = ss.getIAttMainWebServicePort();
		return port.addAttMain(attMain);
	}
}
