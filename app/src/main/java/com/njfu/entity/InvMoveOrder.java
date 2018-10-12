package com.njfu.entity;

import java.math.BigDecimal;
import java.util.Date;

public class InvMoveOrder {

	private String orderId;

    private String orderStatus;

    private String transId;

    private String itemNumber;

    private String billListId;

    private String sourceWhId;

    private String sourceLocaId;

    private String sourceOrderNumber;

    private String sourceOrgId;

    private String destWhId;

    private String destLocaId;

    private String destOrderNumber;

    private String destOrgId;

    private BigDecimal orderQuantity;

    private BigDecimal execQuantity;

    private String lotNumber;

    private String serialNumber;

    private String operateDepa;

    private String operateBy;

    private Date operateDate;

    private String processDepa;

    private String processBy;

    private Date processDate;

    private String attribute;

    private String execBy;

    private Date execDate;

    private String execErrMsg;

    private String createBy;

    private Date createDate;

    private String modifyBy;

    private Date modifyDate;

    private String itemSpec;

    private String orderType;

    private BigDecimal copr;

    private String suId;

    private String isFree;

    private String suType;

    private String projectNo;

    private Integer flagCode;

    public InvMoveOrder(String orderId, String orderStatus, String transId, String itemNumber, String billListId, String sourceWhId, String sourceLocaId, String sourceOrderNumber, String sourceOrgId, String destWhId, String destLocaId, String destOrderNumber, String destOrgId, BigDecimal orderQuantity, BigDecimal execQuantity) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.transId = transId;
        this.itemNumber = itemNumber;
        this.billListId = billListId;
        this.sourceWhId = sourceWhId;
        this.sourceLocaId = sourceLocaId;
        this.sourceOrderNumber = sourceOrderNumber;
        this.sourceOrgId = sourceOrgId;
        this.destWhId = destWhId;
        this.destLocaId = destLocaId;
        this.destOrderNumber = destOrderNumber;
        this.destOrgId = destOrgId;
        this.orderQuantity = orderQuantity;
        this.execQuantity = execQuantity;
    }

    public InvMoveOrder(String orderId, String orderStatus, String transId, String itemNumber, String billListId, String sourceWhId, String sourceLocaId, String sourceOrderNumber, String sourceOrgId, String destWhId, String destLocaId, String destOrderNumber, String destOrgId, BigDecimal orderQuantity, BigDecimal execQuantity, String lotNumber, String serialNumber, String operateDepa, String operateBy, Date operateDate, String processDepa, String processBy, Date processDate, String attribute, String execBy, Date execDate, String execErrMsg, String createBy, Date createDate, String modifyBy, Date modifyDate, String itemSpec, String orderType, BigDecimal copr) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.transId = transId;
        this.itemNumber = itemNumber;
        this.billListId = billListId;
        this.sourceWhId = sourceWhId;
        this.sourceLocaId = sourceLocaId;
        this.sourceOrderNumber = sourceOrderNumber;
        this.sourceOrgId = sourceOrgId;
        this.destWhId = destWhId;
        this.destLocaId = destLocaId;
        this.destOrderNumber = destOrderNumber;
        this.destOrgId = destOrgId;
        this.orderQuantity = orderQuantity;
        this.execQuantity = execQuantity;
        this.lotNumber = lotNumber;
        this.serialNumber = serialNumber;
        this.operateDepa = operateDepa;
        this.operateBy = operateBy;
        this.operateDate = operateDate;
        this.processDepa = processDepa;
        this.processBy = processBy;
        this.processDate = processDate;
        this.attribute = attribute;
        this.execBy = execBy;
        this.execDate = execDate;
        this.execErrMsg = execErrMsg;
        this.createBy = createBy;
        this.createDate = createDate;
        this.modifyBy = modifyBy;
        this.modifyDate = modifyDate;
        this.itemSpec = itemSpec;
        this.orderType = orderType;
        this.copr = copr;
    }

    public String getSuId() {
        return suId;
    }

    public void setSuId(String suId) {
        this.suId = suId;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public String getSuType() {
        return suType;
    }

    public void setSuType(String suType) {
        this.suType = suType;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public Integer getFlagCode() {
        return flagCode;
    }

    public void setFlagCode(Integer flagCode) {
        this.flagCode = flagCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getBillListId() {
        return billListId;
    }

    public void setBillListId(String billListId) {
        this.billListId = billListId;
    }

    public String getSourceWhId() {
        return sourceWhId;
    }

    public void setSourceWhId(String sourceWhId) {
        this.sourceWhId = sourceWhId;
    }

    public String getSourceLocaId() {
        return sourceLocaId;
    }

    public void setSourceLocaId(String sourceLocaId) {
        this.sourceLocaId = sourceLocaId;
    }

    public String getSourceOrderNumber() {
        return sourceOrderNumber;
    }

    public void setSourceOrderNumber(String sourceOrderNumber) {
        this.sourceOrderNumber = sourceOrderNumber;
    }

    public String getSourceOrgId() {
        return sourceOrgId;
    }

    public void setSourceOrgId(String sourceOrgId) {
        this.sourceOrgId = sourceOrgId;
    }

    public String getDestWhId() {
        return destWhId;
    }

    public void setDestWhId(String destWhId) {
        this.destWhId = destWhId;
    }

    public String getDestLocaId() {
        return destLocaId;
    }

    public void setDestLocaId(String destLocaId) {
        this.destLocaId = destLocaId;
    }

    public String getDestOrderNumber() {
        return destOrderNumber;
    }

    public void setDestOrderNumber(String destOrderNumber) {
        this.destOrderNumber = destOrderNumber;
    }

    public String getDestOrgId() {
        return destOrgId;
    }

    public void setDestOrgId(String destOrgId) {
        this.destOrgId = destOrgId;
    }

    public BigDecimal getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(BigDecimal orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public BigDecimal getExecQuantity() {
        return execQuantity;
    }

    public void setExecQuantity(BigDecimal execQuantity) {
        this.execQuantity = execQuantity;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getOperateDepa() {
        return operateDepa;
    }

    public void setOperateDepa(String operateDepa) {
        this.operateDepa = operateDepa;
    }

    public String getOperateBy() {
        return operateBy;
    }

    public void setOperateBy(String operateBy) {
        this.operateBy = operateBy;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getProcessDepa() {
        return processDepa;
    }

    public void setProcessDepa(String processDepa) {
        this.processDepa = processDepa;
    }

    public String getProcessBy() {
        return processBy;
    }

    public void setProcessBy(String processBy) {
        this.processBy = processBy;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getExecBy() {
        return execBy;
    }

    public void setExecBy(String execBy) {
        this.execBy = execBy;
    }

    public Date getExecDate() {
        return execDate;
    }

    public void setExecDate(Date execDate) {
        this.execDate = execDate;
    }

    public String getExecErrMsg() {
        return execErrMsg;
    }

    public void setExecErrMsg(String execErrMsg) {
        this.execErrMsg = execErrMsg;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public BigDecimal getCopr() {
        return copr;
    }

    public void setCopr(BigDecimal copr) {
        this.copr = copr;
    }
}