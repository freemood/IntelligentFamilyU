package com.Intelligent.FamilyU.model.download.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 */

public class RemoteOneTaskBean implements Serializable {

    @SerializedName("messageId")
    private int messageId;
    @SerializedName("serialNo")
    private String serialNo;//网关设备序列号 ,
    @SerializedName("success")
    private boolean success;//任务是否成功 ,
    @SerializedName("taskId")
    private String taskId;//任务id ,
    private TaskInfo task;//获取任务信息列表 ,
    @SerializedName("type")
    private String type;// ['build', 'start', 'continueTask', 'stop', 'getTaskList', 'getTask', 'getSavePath', 'deleteTask', 'pause']

    public class TaskInfo {
        private boolean acceptRanges;
        private int download;
        private String fileName;
        private int length;
        private String path;
        private String status;
        private String taskId;
        private String url;

        public boolean isAcceptRanges() {
            return acceptRanges;
        }

        public void setAcceptRanges(boolean acceptRanges) {
            this.acceptRanges = acceptRanges;
        }

        public int getDownload() {
            return download;
        }

        public void setDownload(int download) {
            this.download = download;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TaskInfo getTask() {
        return task;
    }

    public void setTask(TaskInfo task) {
        this.task = task;
    }
}
