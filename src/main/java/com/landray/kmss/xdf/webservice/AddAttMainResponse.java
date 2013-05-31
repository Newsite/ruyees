
package com.landray.kmss.xdf.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>addAttMainResponse complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="addAttMainResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="returnMsg" type="{http://webservice.xdf.kmss.landray.com/}returnMsg" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addAttMainResponse", propOrder = {
    "returnMsg"
})
public class AddAttMainResponse {

    protected ReturnMsg returnMsg;

    /**
     * ��ȡreturnMsg���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ReturnMsg }
     *     
     */
    public ReturnMsg getReturnMsg() {
        return returnMsg;
    }

    /**
     * ����returnMsg���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMsg }
     *     
     */
    public void setReturnMsg(ReturnMsg value) {
        this.returnMsg = value;
    }

}
