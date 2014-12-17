//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.17 at 02:07:24 PM CET 
//


package model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="consumer" type="{}consumer_t" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "consumers"
})
@XmlRootElement(name = "consumers")
public class Consumers {

    @XmlElement(name = "consumer", required = true)
    protected List<ConsumerT> consumers;

    /**
     * Gets the value of the consumers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the consumers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConsumers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConsumerT }
     * 
     * 
     */
    public List<ConsumerT> getConsumers() {
        if (consumers == null) {
            consumers = new ArrayList<ConsumerT>();
        }
        return this.consumers;
    }

    public void setConsumers(List<ConsumerT> consumers) {
    	this.consumers = consumers;
    }
    
    public boolean equals(Object o) {
    	if(o.getClass() == Consumers.class) {
    		Consumers aux = (Consumers) o;
    		return consumers.equals(aux.getConsumers());
    	}
    	return false;
    }
}
