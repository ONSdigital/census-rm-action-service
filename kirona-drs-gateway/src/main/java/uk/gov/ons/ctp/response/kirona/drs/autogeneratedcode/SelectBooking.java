
package uk.gov.ons.ctp.response.kirona.drs.autogeneratedcode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for selectBooking complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="selectBooking">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="selectBooking" type="{http://autogenerated.OTWebServiceApi.xmbrace.com/}xmbSelectBooking" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "selectBooking", propOrder = {
    "selectBooking"
})
public class SelectBooking {

    protected XmbSelectBooking selectBooking;

    /**
     * Gets the value of the selectBooking property.
     * 
     * @return
     *     possible object is
     *     {@link XmbSelectBooking }
     *     
     */
    public XmbSelectBooking getSelectBooking() {
        return selectBooking;
    }

    /**
     * Sets the value of the selectBooking property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmbSelectBooking }
     *     
     */
    public void setSelectBooking(XmbSelectBooking value) {
        this.selectBooking = value;
    }

}
