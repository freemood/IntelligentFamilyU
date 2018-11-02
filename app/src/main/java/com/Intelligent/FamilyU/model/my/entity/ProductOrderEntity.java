package com.Intelligent.FamilyU.model.my.entity;

import java.io.Serializable;

public class ProductOrderEntity implements Serializable {
    private BuyChannel buyChannel;//订单渠道 = ['APP', 'WEB', 'GATEWAY'],
    private String buyUser;//订购用户 ,
    private String comment;//备注 ,
    private String createTime;//创建时间 ,
    private String createUserId;//创建人Id ,
    private String createUserName;//创建人名称 ,
    private String endTime;//失效时间 ,
    private String gatewaySn;//关联网关 ,
    private String id;//主键 ,
    private String orderNo;//订单号 ,
    private String orderTimes;//订购时长 ,
    private String orderType;//订单类型 ,
    private int payAmount;//实付金额 ,
    private String paySerialNo;//支付流水号 ,
    private PayType payType;//支付方式 = ['ALIPAY', 'WECHAT', 'UNIFIED'],
    private String priceOptionId;//计费方案ID ,
    private String productId;//产品编号 ,
    private String startTime;//生效时间 ,
    private String status;//订单状态 ,
    private String updateTime;//更新时间 ,
    private String updateUserId;//更新人ID ,
    private String updateUserName;//更新人名称

    public class BuyChannel {
        private int index;
        private String text;
        private String value;
        private int mask;

        public int getIndex() {
            return index;
        }

        public BuyChannel setIndex(int index) {
            this.index = index;
            return this;
        }

        public String getText() {
            return text;
        }

        public BuyChannel setText(String text) {
            this.text = text;
            return this;
        }

        public String getValue() {
            return value;
        }

        public BuyChannel setValue(String value) {
            this.value = value;
            return this;
        }

        public int getMask() {
            return mask;
        }

        public BuyChannel setMask(int mask) {
            this.mask = mask;
            return this;
        }
    }

    public class PayType{
        private int index;
        private String text;
        private String value;
        private int mask;

        public int getIndex() {
            return index;
        }

        public PayType setIndex(int index) {
            this.index = index;
            return this;
        }

        public String getText() {
            return text;
        }

        public PayType setText(String text) {
            this.text = text;
            return this;
        }

        public String getValue() {
            return value;
        }

        public PayType setValue(String value) {
            this.value = value;
            return this;
        }

        public int getMask() {
            return mask;
        }

        public PayType setMask(int mask) {
            this.mask = mask;
            return this;
        }
    }

    public BuyChannel getBuyChannel() {
        return buyChannel;
    }

    public ProductOrderEntity setBuyChannel(BuyChannel buyChannel) {
        this.buyChannel = buyChannel;
        return this;
    }

    public String getBuyUser() {
        return buyUser;
    }

    public ProductOrderEntity setBuyUser(String buyUser) {
        this.buyUser = buyUser;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public ProductOrderEntity setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public ProductOrderEntity setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public ProductOrderEntity setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
        return this;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public ProductOrderEntity setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
        return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public ProductOrderEntity setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getGatewaySn() {
        return gatewaySn;
    }

    public ProductOrderEntity setGatewaySn(String gatewaySn) {
        this.gatewaySn = gatewaySn;
        return this;
    }

    public String getId() {
        return id;
    }

    public ProductOrderEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public ProductOrderEntity setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public String getOrderTimes() {
        return orderTimes;
    }

    public ProductOrderEntity setOrderTimes(String orderTimes) {
        this.orderTimes = orderTimes;
        return this;
    }

    public String getOrderType() {
        return orderType;
    }

    public ProductOrderEntity setOrderType(String orderType) {
        this.orderType = orderType;
        return this;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public ProductOrderEntity setPayAmount(int payAmount) {
        this.payAmount = payAmount;
        return this;
    }

    public String getPaySerialNo() {
        return paySerialNo;
    }

    public ProductOrderEntity setPaySerialNo(String paySerialNo) {
        this.paySerialNo = paySerialNo;
        return this;
    }

    public PayType getPayType() {
        return payType;
    }

    public ProductOrderEntity setPayType(PayType payType) {
        this.payType = payType;
        return this;
    }

    public String getPriceOptionId() {
        return priceOptionId;
    }

    public ProductOrderEntity setPriceOptionId(String priceOptionId) {
        this.priceOptionId = priceOptionId;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public ProductOrderEntity setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public ProductOrderEntity setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ProductOrderEntity setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public ProductOrderEntity setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public ProductOrderEntity setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
        return this;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public ProductOrderEntity setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
        return this;
    }
}
