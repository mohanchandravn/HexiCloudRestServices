package com.hexicloud.portaldb.bean;

import java.io.Serializable;

import java.math.BigDecimal;

import java.sql.Timestamp;

public class ClmData implements Serializable {
    @SuppressWarnings("compatibility:3775056980475736013")
    private static final long serialVersionUID = 928820173217527175L;

    private BigDecimal registryId;


    private String customerName;
    private String country;
    private String division;
    private String clusterName;
    private String region;
    private BigDecimal subscriptionId;
    private BigDecimal subscriptionPlanNumber;
    private BigDecimal contractNumber;
    private String subscriptionLineStatus;
    private BigDecimal csiNum;
    private Timestamp lineStartDate;
    private Timestamp lineEndDate;
    private String subscriptionType;
    private String fiscalQuarter;
    private String fiscalYear;
    private String productTier4;
    private String productTier5;
    private String productTier6;
    private String productTier7;
    private String productTier8;
    private String productTier9;
    private String itemDescription;
    private String partNumber;
    private BigDecimal totalContractValueTcvCd;
    private BigDecimal adjustedArrCd;
    private boolean engagements;
    private String additionalInfo;

    public ClmData() {
        super();
    }

    public ClmData(BigDecimal registryId, String customerName, String country, String division, String clusterName,
                   String region, BigDecimal subscriptionId, BigDecimal subscriptionPlanNumber,
                   BigDecimal contractNumber, String subscriptionLineStatus, BigDecimal csiNum, Timestamp lineStartDate,
                   Timestamp lineEndDate, String subscriptionType, String fiscalQuarter, String fiscalYear,
                   String productTier4, String productTier5, String productTier6, String productTier7,
                   String productTier8, String productTier9, String itemDescription, String partNumber,
                   BigDecimal totalContractValueTcvCd, BigDecimal adjustedArrCd, boolean engagements,
                   String additionalInfo) {
        this.registryId = registryId;
        this.customerName = customerName;
        this.country = country;
        this.division = division;
        this.clusterName = clusterName;
        this.region = region;
        this.subscriptionId = subscriptionId;
        this.subscriptionPlanNumber = subscriptionPlanNumber;
        this.contractNumber = contractNumber;
        this.subscriptionLineStatus = subscriptionLineStatus;
        this.csiNum = csiNum;
        this.lineStartDate = lineStartDate;
        this.lineEndDate = lineEndDate;
        this.subscriptionType = subscriptionType;
        this.fiscalQuarter = fiscalQuarter;
        this.fiscalYear = fiscalYear;
        this.productTier4 = productTier4;
        this.productTier5 = productTier5;
        this.productTier6 = productTier6;
        this.productTier7 = productTier7;
        this.productTier8 = productTier8;
        this.productTier9 = productTier9;
        this.itemDescription = itemDescription;
        this.partNumber = partNumber;
        this.totalContractValueTcvCd = totalContractValueTcvCd;
        this.adjustedArrCd = adjustedArrCd;
        this.engagements = engagements;
        this.additionalInfo = additionalInfo;
    }

    public void setRegistryId(BigDecimal registryId) {
        this.registryId = registryId;
    }

    public BigDecimal getRegistryId() {
        return registryId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDivision() {
        return division;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public void setSubscriptionId(BigDecimal subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public BigDecimal getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionPlanNumber(BigDecimal subscriptionPlanNumber) {
        this.subscriptionPlanNumber = subscriptionPlanNumber;
    }

    public BigDecimal getSubscriptionPlanNumber() {
        return subscriptionPlanNumber;
    }

    public void setContractNumber(BigDecimal contractNumber) {
        this.contractNumber = contractNumber;
    }

    public BigDecimal getContractNumber() {
        return contractNumber;
    }

    public void setSubscriptionLineStatus(String subscriptionLineStatus) {
        this.subscriptionLineStatus = subscriptionLineStatus;
    }

    public String getSubscriptionLineStatus() {
        return subscriptionLineStatus;
    }

    public void setCsiNum(BigDecimal csiNum) {
        this.csiNum = csiNum;
    }

    public BigDecimal getCsiNum() {
        return csiNum;
    }

    public void setLineStartDate(Timestamp lineStartDate) {
        this.lineStartDate = lineStartDate;
    }

    public Timestamp getLineStartDate() {
        return lineStartDate;
    }

    public void setLineEndDate(Timestamp lineEndDate) {
        this.lineEndDate = lineEndDate;
    }

    public Timestamp getLineEndDate() {
        return lineEndDate;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setFiscalQuarter(String fiscalQuarter) {
        this.fiscalQuarter = fiscalQuarter;
    }

    public String getFiscalQuarter() {
        return fiscalQuarter;
    }

    public void setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public String getFiscalYear() {
        return fiscalYear;
    }

    public void setProductTier4(String productTier4) {
        this.productTier4 = productTier4;
    }

    public String getProductTier4() {
        return productTier4;
    }

    public void setProductTier5(String productTier5) {
        this.productTier5 = productTier5;
    }

    public String getProductTier5() {
        return productTier5;
    }

    public void setProductTier6(String productTier6) {
        this.productTier6 = productTier6;
    }

    public String getProductTier6() {
        return productTier6;
    }

    public void setProductTier7(String productTier7) {
        this.productTier7 = productTier7;
    }

    public String getProductTier7() {
        return productTier7;
    }

    public void setProductTier8(String productTier8) {
        this.productTier8 = productTier8;
    }

    public String getProductTier8() {
        return productTier8;
    }

    public void setProductTier9(String productTier9) {
        this.productTier9 = productTier9;
    }

    public String getProductTier9() {
        return productTier9;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setTotalContractValueTcvCd(BigDecimal totalContractValueTcvCd) {
        this.totalContractValueTcvCd = totalContractValueTcvCd;
    }

    public BigDecimal getTotalContractValueTcvCd() {
        return totalContractValueTcvCd;
    }

    public void setAdjustedArrCd(BigDecimal adjustedArrCd) {
        this.adjustedArrCd = adjustedArrCd;
    }

    public BigDecimal getAdjustedArrCd() {
        return adjustedArrCd;
    }

    public void setEngagements(boolean engagements) {
        this.engagements = engagements;
    }

    public boolean isEngagements() {
        return engagements;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }
}
