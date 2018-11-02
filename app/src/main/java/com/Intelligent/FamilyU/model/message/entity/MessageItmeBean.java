package com.Intelligent.FamilyU.model.message.entity;

import java.io.Serializable;


public class MessageItmeBean implements Serializable {
    private String buyUser;
    private String createUserId;
    private String createUserName;
    private String gatewaySn;
    private String id;
    private String orderNo;
    private String orderType;
    private String priceOptionId;
    private String productId;


    private BuyChannel buyChannel;
    private PayType payType;
    public class BuyChannel{
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

    public String getBuyUser() {
        return buyUser;
    }

    public MessageItmeBean setBuyUser(String buyUser) {
        this.buyUser = buyUser;
        return this;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public MessageItmeBean setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
        return this;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public MessageItmeBean setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
        return this;
    }

    public String getGatewaySn() {
        return gatewaySn;
    }

    public MessageItmeBean setGatewaySn(String gatewaySn) {
        this.gatewaySn = gatewaySn;
        return this;
    }

    public String getId() {
        return id;
    }

    public MessageItmeBean setId(String id) {
        this.id = id;
        return this;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public MessageItmeBean setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public String getOrderType() {
        return orderType;
    }

    public MessageItmeBean setOrderType(String orderType) {
        this.orderType = orderType;
        return this;
    }

    public String getPriceOptionId() {
        return priceOptionId;
    }

    public MessageItmeBean setPriceOptionId(String priceOptionId) {
        this.priceOptionId = priceOptionId;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public MessageItmeBean setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public BuyChannel getBuyChannel() {
        return buyChannel;
    }

    public MessageItmeBean setBuyChannel(BuyChannel buyChannel) {
        this.buyChannel = buyChannel;
        return this;
    }

    public PayType getPayType() {
        return payType;
    }

    public MessageItmeBean setPayType(PayType payType) {
        this.payType = payType;
        return this;
    }
}
