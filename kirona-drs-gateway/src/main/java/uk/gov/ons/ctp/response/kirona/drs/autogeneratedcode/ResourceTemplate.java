
package uk.gov.ons.ctp.response.kirona.drs.autogeneratedcode;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resourceTemplate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resourceTemplate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://autogenerated.OTWebServiceApi.xmbrace.com/}entity">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="theAbilities" type="{http://autogenerated.OTWebServiceApi.xmbrace.com/}ability" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resourceTemplate", propOrder = {
    "name",
    "theAbilities"
})
public class ResourceTemplate
    extends Entity
{

    protected String name;
    protected List<Ability> theAbilities;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the theAbilities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the theAbilities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTheAbilities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Ability }
     * 
     * 
     */
    public List<Ability> getTheAbilities() {
        if (theAbilities == null) {
            theAbilities = new ArrayList<Ability>();
        }
        return this.theAbilities;
    }

}
