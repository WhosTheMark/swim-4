//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.17 at 02:07:24 PM CET 
//


package model;

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
 *         &lt;element ref="{}consumers"/>
 *         &lt;element ref="{}producers"/>
 *         &lt;element name="duration" type="{}time_t"/>
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
    "consumers",
    "producers",
    "duration"
})
@XmlRootElement(name = "scenario")
public class Scenario {

    @XmlElement(required = true)
    protected Consumers consumers;
    @XmlElement(required = true)
    protected Producers producers;
    @XmlElement(required = true)
    protected TimeT duration;

    /**
     * Gets the value of the consumers property.
     * 
     * @return
     *     possible object is
     *     {@link Consumers }
     *     
     */
    public Consumers getConsumers() {
        return consumers;
    }

    /**
     * Sets the value of the consumers property.
     * 
     * @param value
     *     allowed object is
     *     {@link Consumers }
     *     
     */
    public void setConsumers(Consumers value) {
        this.consumers = value;
    }

    /**
     * Gets the value of the producers property.
     * 
     * @return
     *     possible object is
     *     {@link Producers }
     *     
     */
    public Producers getProducers() {
        return producers;
    }

    /**
     * Sets the value of the producers property.
     * 
     * @param value
     *     allowed object is
     *     {@link Producers }
     *     
     */
    public void setProducers(Producers value) {
        this.producers = value;
    }

    /**
     * Gets the value of the duration property.
     * 
     * @return
     *     possible object is
     *     {@link TimeT }
     *     
     */
    public TimeT getDuration() {
        return duration;
    }

    public int getDurationInMs() {
    	return duration.getTimeElementInMs();
    }
    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeT }
     *     
     */
    public void setDuration(TimeT value) {
        this.duration = value;
    }
    
    public boolean equals(Object o) {
    	if(o.getClass() == Scenario.class) {
    		Scenario aux = (Scenario) o;
    		return consumers.equals(aux.getConsumers())
    			&& producers.equals(aux.getProducers())
    			&& duration.equals(aux.getDuration());
    	}
    	return false;
    }

}
