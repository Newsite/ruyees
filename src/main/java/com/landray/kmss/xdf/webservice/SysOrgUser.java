package com.landray.kmss.xdf.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sysOrgUser", propOrder = { "companyId", "deptId", "fdIsEmp",
		"loginName", "mail", "name", "potoImg", "pwd", "sfz", "tel", "title",
		"fdUrl" })
public class SysOrgUser {

	protected String companyId;
	protected String deptId;
	protected String fdIsEmp;
	protected String loginName;
	protected String mail;
	protected String name;
	protected byte[] potoImg;
	protected String pwd;
	protected String sfz;
	protected String tel;
	protected String title;
	protected String fdUrl;

	/**
	 * ��ȡcompanyId���Ե�ֵ��
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCompanyId() {
		return companyId;
	}

	/**
	 * ����companyId���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCompanyId(String value) {
		this.companyId = value;
	}

	/**
	 * ��ȡdeptId���Ե�ֵ��
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * ����deptId���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDeptId(String value) {
		this.deptId = value;
	}

	/**
	 * ��ȡfdIsEmp���Ե�ֵ��
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getFdIsEmp() {
		return fdIsEmp;
	}

	/**
	 * ����fdIsEmp���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setFdIsEmp(String value) {
		this.fdIsEmp = value;
	}

	/**
	 * ��ȡloginName���Ե�ֵ��
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * ����loginName���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setLoginName(String value) {
		this.loginName = value;
	}

	/**
	 * ��ȡmail���Ե�ֵ��
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * ����mail���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMail(String value) {
		this.mail = value;
	}

	/**
	 * ��ȡname���Ե�ֵ��
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * ����name���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * ��ȡpotoImg���Ե�ֵ��
	 * 
	 * @return possible object is byte[]
	 */
	public byte[] getPotoImg() {
		return potoImg;
	}

	/**
	 * ����potoImg���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is byte[]
	 */
	public void setPotoImg(byte[] value) {
		this.potoImg = value;
	}

	/**
	 * ��ȡpwd���Ե�ֵ��
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * ����pwd���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPwd(String value) {
		this.pwd = value;
	}

	/**
	 * ��ȡsfz���Ե�ֵ��
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSfz() {
		return sfz;
	}

	/**
	 * ����sfz���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSfz(String value) {
		this.sfz = value;
	}

	/**
	 * ��ȡtel���Ե�ֵ��
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * ����tel���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTel(String value) {
		this.tel = value;
	}

	/**
	 * ��ȡtitle���Ե�ֵ��
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * ����title���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTitle(String value) {
		this.title = value;
	}

	public String getFdUrl() {
		return fdUrl;
	}

	public void setFdUrl(String fdUrl) {
		this.fdUrl = fdUrl;
	}

}
