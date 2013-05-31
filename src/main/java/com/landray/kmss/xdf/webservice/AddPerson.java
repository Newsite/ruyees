
package com.landray.kmss.xdf.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>addPerson complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="addPerson">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="user" type="{http://webservice.xdf.kmss.landray.com/}sysOrgUser" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addPerson", propOrder = {
    "user"
})
public class AddPerson {

    protected SysOrgUser user;

    /**
     * 获取user属性的值。
     * 
     * @return
     *     possible object is
     *     {@link SysOrgUser }
     *     
     */
    public SysOrgUser getUser() {
        return user;
    }

    /**
     * 设置user属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link SysOrgUser }
     *     
     */
    public void setUser(SysOrgUser value) {
        this.user = value;
    }

}
