
package com.landray.kmss.xdf.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>addAttMain complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="addAttMain">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attMain" type="{http://webservice.xdf.kmss.landray.com/}attMain" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addAttMain", propOrder = {
    "attMain"
})
public class AddAttMain {

    protected AttMain attMain;

    /**
     * ��ȡattMain���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link AttMain }
     *     
     */
    public AttMain getAttMain() {
        return attMain;
    }

    /**
     * ����attMain���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link AttMain }
     *     
     */
    public void setAttMain(AttMain value) {
        this.attMain = value;
    }

}
