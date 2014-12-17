//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.17 at 02:07:24 PM CET 
//


package model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sizeUnitType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="sizeUnitType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="bytes"/>
 *     &lt;enumeration value="Kbytes"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "sizeUnitType")
@XmlEnum
public enum SizeUnitType {

    @XmlEnumValue("bytes")
    BYTES("bytes"),
    @XmlEnumValue("Kbytes")
    KBYTES("Kbytes");
    private final String value;

    SizeUnitType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SizeUnitType fromValue(String v) {
        for (SizeUnitType c: SizeUnitType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
