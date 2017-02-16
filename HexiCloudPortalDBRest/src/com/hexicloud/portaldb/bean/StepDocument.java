package com.hexicloud.portaldb.bean;

import java.io.Serializable;

public class StepDocument implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6606025768623129422L;

	private int stepId;
	private String docType;
	private String docTypeExtn;
	private String docFieldId;

	public StepDocument() {

	}

	/**
	 * @param stepId
	 * @param docType
	 * @param docTypeExtn
	 * @param docFieldId
	 */
	public StepDocument(int stepId, String docType, String docTypeExtn, String docFieldId) {
		this.stepId = stepId;
		this.docType = docType;
		this.docTypeExtn = docTypeExtn;
		this.docFieldId = docFieldId;
	}

	/**
	 * @return the stepId
	 */
	public int getStepId() {
		return stepId;
	}

	/**
	 * @param stepId
	 *            the stepId to set
	 */
	public void setStepId(int stepId) {
		this.stepId = stepId;
	}

	/**
	 * @return the docType
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * @param docType
	 *            the docType to set
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	/**
	 * @return the docTypeExtn
	 */
	public String getDocTypeExtn() {
		return docTypeExtn;
	}

	/**
	 * @param docTypeExtn
	 *            the docTypeExtn to set
	 */
	public void setDocTypeExtn(String docTypeExtn) {
		this.docTypeExtn = docTypeExtn;
	}

	/**
	 * @return the docFieldId
	 */
	public String getDocFieldId() {
		return docFieldId;
	}

	/**
	 * @param docFieldId
	 *            the docFieldId to set
	 */
	public void setDocFieldId(String docFieldId) {
		this.docFieldId = docFieldId;
	}

}
