package com.Intelligent.FamilyU.model.plugin.bean;

import java.io.Serializable;
import java.util.List;

public class PluginPageEntity implements Serializable {
    //查询结果
    List<PluginGatewayInfo> data;
    //数据总数量
    private Integer total;

    public List<PluginGatewayInfo> getData() {
        return data;
    }

    public void setData(List<PluginGatewayInfo> data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public  class    PluginGatewayInfo {
        //网关插件安装信息
        private PluginGatewayEntity pluginGateway;
        // 插件版本
        private PluginVersionEntity pluginVersion;

        public PluginGatewayEntity getPluginGateway() {
            return pluginGateway;
        }

        public void setPluginGateway(PluginGatewayEntity pluginGateway) {
            this.pluginGateway = pluginGateway;
        }

        public PluginVersionEntity getPluginVersion() {
            return pluginVersion;
        }

        public void setPluginVersion(PluginVersionEntity pluginVersion) {
            this.pluginVersion = pluginVersion;
        }
    }

    public class PluginGatewayEntity {
        private String omment;//备注,
        private String createTime;//创建时间,
        private String gatewayId;//所属终端,
        private String id;//主键,
        private String pluginId;//插件ID,
        private String pluginName;//插件名称,
        private String pluginVersionId;//插件版本ID,
        private StatusInfo Status;//状态
        private String updateTime;//修改时间

        class StatusInfo {
            private String index;
            private String text;
            private String value;
            private String mask;

            public String getIndex() {
                return index;
            }

            public void setIndex(String index) {
                this.index = index;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getMask() {
                return mask;
            }

            public void setMask(String mask) {
                this.mask = mask;
            }
        }

        public String getOmment() {
            return omment;
        }

        public void setOmment(String omment) {
            this.omment = omment;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getGatewayId() {
            return gatewayId;
        }

        public void setGatewayId(String gatewayId) {
            this.gatewayId = gatewayId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPluginId() {
            return pluginId;
        }

        public void setPluginId(String pluginId) {
            this.pluginId = pluginId;
        }

        public String getPluginName() {
            return pluginName;
        }

        public void setPluginName(String pluginName) {
            this.pluginName = pluginName;
        }

        public String getPluginVersionId() {
            return pluginVersionId;
        }

        public void setPluginVersionId(String pluginVersionId) {
            this.pluginVersionId = pluginVersionId;
        }

        public StatusInfo getStatus() {
            return Status;
        }

        public void setStatus(StatusInfo status) {
            Status = status;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }

    public class PluginVersionEntity {
        private String area;//归属区域,
        private String author;//作者,
        private String comment;//备注,
        private String createTime;//创建时间,
        private Integer downloadTimes;//下载次数,
        private String icon;//版本图标,
        private String id;//主键,
        private Integer installTimes;//安装次数,
        private String obrPluginName;//obr插件名称,
        private String permissionGroupId;//权限分组ID,
        //private PluginEntity plugin;//插件主信息,
        private String pluginId;//插件ID,
        // pluginPermissionGroup(权限分组, optional):权限分组,
        private StatusInfo status;//状态 =['RELEASE','PRIVATE','DISCARD'],
        private String updateTime;//修改时间,
        private String url;//地址,
        private String version;//版本

        class StatusInfo {
            private String index;
            private String text;
            private String value;
            private String mask;

            public String getIndex() {
                return index;
            }

            public void setIndex(String index) {
                this.index = index;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getMask() {
                return mask;
            }

            public void setMask(String mask) {
                this.mask = mask;
            }
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Integer getDownloadTimes() {
            return downloadTimes;
        }

        public void setDownloadTimes(Integer downloadTimes) {
            this.downloadTimes = downloadTimes;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getInstallTimes() {
            return installTimes;
        }

        public void setInstallTimes(Integer installTimes) {
            this.installTimes = installTimes;
        }

        public String getObrPluginName() {
            return obrPluginName;
        }

        public void setObrPluginName(String obrPluginName) {
            this.obrPluginName = obrPluginName;
        }

        public String getPermissionGroupId() {
            return permissionGroupId;
        }

        public void setPermissionGroupId(String permissionGroupId) {
            this.permissionGroupId = permissionGroupId;
        }

        public String getPluginId() {
            return pluginId;
        }

        public void setPluginId(String pluginId) {
            this.pluginId = pluginId;
        }

        public StatusInfo getStatus() {
            return status;
        }

        public void setStatus(StatusInfo status) {
            this.status = status;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

}
