/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.landray.kmss.xdf.webservice;

import java.util.logging.Logger;

/**
 * This class was generated by Apache CXF 2.7.0 2012-12-25T17:01:42.785+08:00
 * Generated source version: 2.7.0
 * 
 */

@javax.jws.WebService(serviceName = "IAttMainWebServiceService", portName = "IAttMainWebServicePort", targetNamespace = "http://webservice.xdf.kmss.landray.com/", wsdlLocation = "http://192.168.25.169:9080/ixdf/sys/webservice/attMainWebService?wsdl", endpointInterface = "com.landray.kmss.xdf.webservice.attmain.IAttMainWebService")
public class IAttMainWebServiceImpl implements IAttMainWebService {

	private static final Logger LOG = Logger
			.getLogger(IAttMainWebServiceImpl.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.landray.kmss.xdf.webservice.attmain.IAttMainWebService#getAttMain
	 * (com.landray.kmss.xdf.webservice.attmain.AttMain attMain )*
	 */
	public ReturnMsg getAttMain(
			AttMain attMain)
			throws Exception {
		LOG.info("Executing operation getAttMain");
		System.out.println(attMain);
		try {
			ReturnMsg _return = new ReturnMsg();
			_return.setCode("Code-1625245133");
			_return.setModelId("ModelId1122981483");
			_return.setModelName("ModelName-1177831247");
			_return.setMsg("Msg372031969");
			java.util.List<java.lang.String> _returnUrls = new java.util.ArrayList<java.lang.String>();
			java.lang.String _returnUrlsVal1 = "_returnUrlsVal808571326";
			_returnUrls.add(_returnUrlsVal1);
			_return.getUrls().addAll(_returnUrls);
			return _return;
		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		// throw new Exception_Exception("Exception...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.landray.kmss.xdf.webservice.attmain.IAttMainWebService#addAttMain
	 * (com.landray.kmss.xdf.webservice.attmain.AttMain attMain )*
	 */
	public ReturnMsg addAttMain(
			AttMain attMain)
			throws Exception {
		LOG.info("Executing operation addAttMain");
		System.out.println(attMain);
		try {
			ReturnMsg _return = new ReturnMsg();
			_return.setCode("Code-1805659410");
			_return.setModelId("ModelId-81019816");
			_return.setModelName("ModelName-1747129512");
			_return.setMsg("Msg953030771");
			java.util.List<java.lang.String> _returnUrls = new java.util.ArrayList<java.lang.String>();
			java.lang.String _returnUrlsVal1 = "_returnUrlsVal-1674850187";
			_returnUrls.add(_returnUrlsVal1);
			_return.getUrls().addAll(_returnUrls);
			return _return;
		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		// throw new Exception_Exception("Exception...");
	}

}
