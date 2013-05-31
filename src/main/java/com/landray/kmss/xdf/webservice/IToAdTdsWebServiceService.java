package com.landray.kmss.xdf.webservice;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

import com.landray.kmss.xdf.webservice.peizhi.WSDLURL;

/**
 * This class was generated by Apache CXF 2.7.0
 * 2012-12-25T16:59:40.442+08:00
 * Generated source version: 2.7.0
 * 
 */
@WebServiceClient(name = "IToAdTdsWebServiceService", 
                  wsdlLocation = "http://192.168.25.169:9080/ixdf/sys/webservice/toAdTdsWebService?wsdl",
                  targetNamespace = "http://webservice.xdf.kmss.landray.com/") 
public class IToAdTdsWebServiceService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://webservice.xdf.kmss.landray.com/", "IToAdTdsWebServiceService");
    public final static QName IToAdTdsWebServicePort = new QName("http://webservice.xdf.kmss.landray.com/", "IToAdTdsWebServicePort");
    static {
        URL url = null;
        try {
            url = new URL(WSDLURL.AD_WSDLURL);
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(IToAdTdsWebServiceService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://192.168.25.169:9080/ixdf/sys/webservice/toAdTdsWebService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public IToAdTdsWebServiceService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public IToAdTdsWebServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public IToAdTdsWebServiceService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns IToAdTdsWebService
     */
    @WebEndpoint(name = "IToAdTdsWebServicePort")
    public IToAdTdsWebService getIToAdTdsWebServicePort() {
        return super.getPort(IToAdTdsWebServicePort, IToAdTdsWebService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IToAdTdsWebService
     */
    @WebEndpoint(name = "IToAdTdsWebServicePort")
    public IToAdTdsWebService getIToAdTdsWebServicePort(WebServiceFeature... features) {
        return super.getPort(IToAdTdsWebServicePort, IToAdTdsWebService.class, features);
    }

}
