//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.17 at 02:07:24 PM CET 
//


package model;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for behaviour_t complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="behaviour_t">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="frequency" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="processingTime" type="{}time_t"/>
 *         &lt;element name="datasize" type="{}datasize_t"/>
 *       &lt;/sequence>
 *       &lt;attribute name="begin" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="end" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *       &lt;attribute name="timeUnit" use="required" type="{}timeUnitType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "behaviour_t", propOrder = {
    "frequency",
    "processingTime",
    "datasize"
})
public class BehaviourT implements Comparable<BehaviourT>{

    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger frequency;
    @XmlElement(required = true)
    protected TimeT processingTime;
    @XmlElement(required = true)
    protected DatasizeT datasize;
    @XmlAttribute(name = "begin", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger begin;
    @XmlAttribute(name = "end", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger end;
    @XmlAttribute(name = "timeUnit", required = true)
    protected TimeUnitType timeUnit;

    /**
     * Gets the value of the frequency property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getFrequency() {
        return frequency;
    }

    /**
     * Sets the value of the frequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setFrequency(BigInteger value) {
        this.frequency = value;
    }

    /**
     * Gets the value of the processingTime property.
     * 
     * @return
     *     possible object is
     *     {@link TimeT }
     *     
     */
    public TimeT getProcessingTime() {
        return processingTime;
    }

    /**
     * Sets the value of the processingTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeT }
     *     
     */
    public void setProcessingTime(TimeT value) {
        this.processingTime = value;
    }

    /**
     * Gets the value of the datasize property.
     * 
     * @return
     *     possible object is
     *     {@link DatasizeT }
     *     
     */
    public DatasizeT getDatasize() {
        return datasize;
    }

    /**
     * Sets the value of the datasize property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatasizeT }
     *     
     */
    public void setDatasize(DatasizeT value) {
        this.datasize = value;
    }

    /**
     * Gets the value of the begin property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBegin() {
        return begin;
    }

    /**
     * Sets the value of the begin property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBegin(BigInteger value) {
        this.begin = value;
    }

    /**
     * Gets the value of the end property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEnd() {
        return end;
    }

    /**
     * Sets the value of the end property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEnd(BigInteger value) {
        this.end = value;
    }

    /**
     * Gets the value of the timeUnit property.
     * 
     * @return
     *     possible object is
     *     {@link TimeUnitType }
     *     
     */
    public TimeUnitType getTimeUnit() {
        return timeUnit;
    }

    /**
     * Sets the value of the timeUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeUnitType }
     *     
     */
    public void setTimeUnit(TimeUnitType value) {
        this.timeUnit = value;
    }

    public boolean equals(Object o) {
    	if(o.getClass() == BehaviourT.class) {
    		BehaviourT aux = (BehaviourT) o;
    		return frequency.equals(aux.getFrequency())
    			&& processingTime.equals(aux.getProcessingTime())
    			&& datasize.equals(aux.getDatasize())
    			&& begin.equals(aux.getBegin())
    			&& end.equals(aux.getEnd())
    			&& timeUnit.equals(aux.getTimeUnit());
    	}
    	return false;
    }
    
    public int getBeginInMs() {
    	return getTimeElementInMs(begin, timeUnit);
    }
    
    public int getEndInMs() {
    	return getTimeElementInMs(end, timeUnit);
    }
    
    public int getProcessingTimeInMs() {
    	return getTimeElementInMs(processingTime.getValue(), processingTime.getTimeUnit());
    }
    
    public int getDatasizeInBytes() {
    	return datasize.getDatasizeInBytes();
    }
    
    private int getTimeElementInMs(BigInteger timeLimit, TimeUnitType unit) {
    	int value = timeLimit.intValue();
    	switch(unit) {
    	case MINS:
    		value = value * 3600;
    		break;
    	case MS:
    		break;
    	case SECS:
    		value = value * 60;
    		break;
    	}
    	return value;
    }
    
    public boolean doesBehaviourOverlapWith(BehaviourT behaviour) {
    	boolean overlap;
    	if(getBeginInMs() < behaviour.getBeginInMs()) {
    		 overlap = getEndInMs() > behaviour.getBeginInMs();
    	} else {
    		overlap = behaviour.getEndInMs() > getBeginInMs();
    	}
    	return overlap;
    }

	public int compareTo(BehaviourT arg) {
		int result;
		if(getBeginInMs() < arg.getBeginInMs()) {
			result = -1;
		} else if(getBeginInMs() > arg.getBeginInMs()) {
			result = 1;
		} else {
			if(getEndInMs() <= arg.getEndInMs()) {
				result = -1;
			} else {
				result = 1;
			}
		}
		return result;
	}
	
	public boolean isPossibleBehaviour() {
		return getBeginInMs() < getEndInMs();
	}
	
	public boolean isCompatibleWithScenarioDuration(int duration) {
		return getEndInMs() < duration;
	}
	public String toString() {
		   return "{ frequency : " + frequency.toString()
				   + " processing time : " + processingTime.toString()
				   + " datasize : " + datasize.toString()
				   + " begin : " + begin.toString()
				   + " end : " + end.toString()
				   + " timeUnit : " + timeUnit.toString() + " }";
	}
}
