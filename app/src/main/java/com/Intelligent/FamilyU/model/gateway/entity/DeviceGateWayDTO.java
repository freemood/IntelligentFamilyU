package com.Intelligent.FamilyU.model.gateway.entity;

import java.io.Serializable;

public class DeviceGateWayDTO implements Serializable {

    private String areaId;
    private String broadbandAccount;
    //  CA卡序列号
    private String caSn;
    //CM MAC
    private String cmMac;
    private String comment;
    private String createTime;
    //设备ID
    private String deviceId;
    private String emtaMac;
    private String id;
    private String nagraUniqueId;
    private String name;
    private String oui;
    private String prdclass;
    private String routerMac;
    //网关序列号
    private String serialNo;
    private String stbMac;
    //厂家id
    private String supplierId;
    private String updateTime;
    private String userId;
    private DeviceDTO deviceDTO;

    public DeviceDTO getDeviceDTO() {
        return deviceDTO;
    }

    public DeviceGateWayDTO setDeviceDTO(DeviceDTO deviceDTO) {
        this.deviceDTO = deviceDTO;
        return this;
    }

    public class DeviceDTO {
        // 代码
        private String code;
        //备注
        private String comment;
        //创建时间
        private String createTime;
        //主键
        private String id;
        //名称
        private String name;
        //终端厂家id
        private String supplierId;
        //更新时间
        private String updateTime;

        public String getCode() {
            return code;
        }

        public DeviceDTO setCode(String code) {
            this.code = code;
            return this;
        }

        public String getComment() {
            return comment;
        }

        public DeviceDTO setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public String getCreateTime() {
            return createTime;
        }

        public DeviceDTO setCreateTime(String createTime) {
            this.createTime = createTime;
            return this;
        }

        public String getId() {
            return id;
        }

        public DeviceDTO setId(String id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public DeviceDTO setName(String name) {
            this.name = name;
            return this;
        }

        public String getSupplierId() {
            return supplierId;
        }

        public DeviceDTO setSupplierId(String supplierId) {
            this.supplierId = supplierId;
            return this;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public DeviceDTO setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
            return this;
        }
        private  DeviceSupplierDTO deviceSupplierDTO;

        public DeviceSupplierDTO getDeviceSupplierDTO() {
            return deviceSupplierDTO;
        }

        public DeviceDTO setDeviceSupplierDTO(DeviceSupplierDTO deviceSupplierDTO) {
            this.deviceSupplierDTO = deviceSupplierDTO;
            return this;
        }

        public class DeviceSupplierDTO {
            //终端厂家代码
            private String code;
            //备注 ,
            private String comment;
            //创建时间 ,
            private String createTime;
            //主键 ,
            private String id;
            //终端厂家名称 ,
            private String name;
            //更新时间
            private String updateTime;

            public String getCode() {
                return code;
            }

            public DeviceSupplierDTO setCode(String code) {
                this.code = code;
                return this;
            }

            public String getComment() {
                return comment;
            }

            public DeviceSupplierDTO setComment(String comment) {
                this.comment = comment;
                return this;
            }

            public String getCreateTime() {
                return createTime;
            }

            public DeviceSupplierDTO setCreateTime(String createTime) {
                this.createTime = createTime;
                return this;
            }

            public String getId() {
                return id;
            }

            public DeviceSupplierDTO setId(String id) {
                this.id = id;
                return this;
            }

            public String getName() {
                return name;
            }

            public DeviceSupplierDTO setName(String name) {
                this.name = name;
                return this;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public DeviceSupplierDTO setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
                return this;
            }
        }
    }

    private StatusGateway status;

    public StatusGateway getStatus() {
        return status;
    }

    public DeviceGateWayDTO setStatus(StatusGateway status) {
        this.status = status;
        return this;
    }

    public class StatusGateway {
        private String index;
        private String text;
        private String value;
        private String mask;

        public String getIndex() {
            return index;
        }

        public StatusGateway setIndex(String index) {
            this.index = index;
            return this;
        }

        public String getText() {
            return text;
        }

        public StatusGateway setText(String text) {
            this.text = text;
            return this;
        }

        public String getValue() {
            return value;
        }

        public StatusGateway setValue(String value) {
            this.value = value;
            return this;
        }

        public String getMask() {
            return mask;
        }

        public StatusGateway setMask(String mask) {
            this.mask = mask;
            return this;
        }
    }

    private Type type;

    public Type getType() {
        return type;
    }

    public DeviceGateWayDTO setType(Type type) {
        this.type = type;
        return this;
    }

    class Type {
        private String index;
        private String text;
        private String value;
        private String mask;

        public String getIndex() {
            return index;
        }

        public Type setIndex(String index) {
            this.index = index;
            return this;
        }

        public String getText() {
            return text;
        }

        public Type setText(String text) {
            this.text = text;
            return this;
        }

        public String getValue() {
            return value;
        }

        public Type setValue(String value) {
            this.value = value;
            return this;
        }

        public String getMask() {
            return mask;
        }

        public Type setMask(String mask) {
            this.mask = mask;
            return this;
        }
    }

    public String getAreaId() {
        return areaId;
    }

    public DeviceGateWayDTO setAreaId(String areaId) {
        this.areaId = areaId;
        return this;
    }

    public String getCaSn() {
        return caSn;
    }

    public DeviceGateWayDTO setCaSn(String caSn) {
        this.caSn = caSn;
        return this;
    }

    public String getCmMac() {
        return cmMac;
    }

    public DeviceGateWayDTO setCmMac(String cmMac) {
        this.cmMac = cmMac;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public DeviceGateWayDTO setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public DeviceGateWayDTO setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public DeviceGateWayDTO setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public String getEmtaMac() {
        return emtaMac;
    }

    public DeviceGateWayDTO setEmtaMac(String emtaMac) {
        this.emtaMac = emtaMac;
        return this;
    }

    public String getId() {
        return id;
    }

    public DeviceGateWayDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getNagraUniqueId() {
        return nagraUniqueId;
    }

    public DeviceGateWayDTO setNagraUniqueId(String nagraUniqueId) {
        this.nagraUniqueId = nagraUniqueId;
        return this;
    }

    public String getName() {
        return name;
    }

    public DeviceGateWayDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getOui() {
        return oui;
    }

    public DeviceGateWayDTO setOui(String oui) {
        this.oui = oui;
        return this;
    }

    public String getPrdclass() {
        return prdclass;
    }

    public DeviceGateWayDTO setPrdclass(String prdclass) {
        this.prdclass = prdclass;
        return this;
    }

    public String getRouterMac() {
        return routerMac;
    }

    public DeviceGateWayDTO setRouterMac(String routerMac) {
        this.routerMac = routerMac;
        return this;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public DeviceGateWayDTO setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public String getStbMac() {
        return stbMac;
    }

    public DeviceGateWayDTO setStbMac(String stbMac) {
        this.stbMac = stbMac;
        return this;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public DeviceGateWayDTO setSupplierId(String supplierId) {
        this.supplierId = supplierId;
        return this;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public DeviceGateWayDTO setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public DeviceGateWayDTO setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getBroadbandAccount() {
        return broadbandAccount;
    }

    public void setBroadbandAccount(String broadbandAccount) {
        this.broadbandAccount = broadbandAccount;
    }
}
