
package uk.gov.ons.ctp.response.kirona.drs.autogeneratedcode;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "SOAP", targetNamespace = "com.xmbrace.OTWebServiceApi.5.2.0.16", wsdlLocation = "file:/nfshome/philippe.brossier/code_ctp/responsemanagement-service/kirona-drs-gateway/soap_1.wsdl")
public class SOAP_Service
    extends Service
{

    private final static URL SOAP_WSDL_LOCATION;
    private final static WebServiceException SOAP_EXCEPTION;
    private final static QName SOAP_QNAME = new QName("com.xmbrace.OTWebServiceApi.5.2.0.16", "SOAP");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/nfshome/philippe.brossier/code_ctp/responsemanagement-service/kirona-drs-gateway/soap_1.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SOAP_WSDL_LOCATION = url;
        SOAP_EXCEPTION = e;
    }

    public SOAP_Service() {
        super(__getWsdlLocation(), SOAP_QNAME);
    }

    public SOAP_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), SOAP_QNAME, features);
    }

    public SOAP_Service(URL wsdlLocation) {
        super(wsdlLocation, SOAP_QNAME);
    }

    public SOAP_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SOAP_QNAME, features);
    }

    public SOAP_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SOAP_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns SOAP
     */
    @WebEndpoint(name = "SOAPImplPort")
    public SOAP getSOAPImplPort() {
        return super.getPort(new QName("com.xmbrace.OTWebServiceApi.5.2.0.16", "SOAPImplPort"), SOAP.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SOAP
     */
    @WebEndpoint(name = "SOAPImplPort")
    public SOAP getSOAPImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("com.xmbrace.OTWebServiceApi.5.2.0.16", "SOAPImplPort"), SOAP.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SOAP_EXCEPTION!= null) {
            throw SOAP_EXCEPTION;
        }
        return SOAP_WSDL_LOCATION;
    }

}
