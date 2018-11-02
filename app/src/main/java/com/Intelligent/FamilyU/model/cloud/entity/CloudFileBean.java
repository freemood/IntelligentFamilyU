package com.Intelligent.FamilyU.model.cloud.entity;

public class CloudFileBean {
    private String userName;
    private String serialNo;
    private String fileName;
    private String type;
    private String createTime;
    private String status;
    private String fileLength;
    private String fileDowdloadLength;
    private String fileUpPath;
    private String fileDownPath;
    private String sdcardFilePath;

    public String getFileName() {
        return fileName;
    }

    public CloudFileBean setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getType() {
        return type;
    }

    public CloudFileBean setType(String type) {
        this.type = type;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public CloudFileBean setCreateTime(String create_time) {
        this.createTime = create_time;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public CloudFileBean setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getFileLength() {
        return fileLength;
    }

    public CloudFileBean setFileLength(String fileLength) {
        this.fileLength = fileLength;
        return this;
    }

    public String getFileDowdloadLength() {
        return fileDowdloadLength;
    }

    public CloudFileBean setFileDowdloadLength(String fileDowdloadLength) {
        this.fileDowdloadLength = fileDowdloadLength;
        return this;
    }

    public String getFileUpPath() {
        return fileUpPath;
    }

    public CloudFileBean setFileUpPath(String fileUpPath) {
        this.fileUpPath = fileUpPath;
        return this;
    }

    public String getFileDownPath() {
        return fileDownPath;
    }

    public CloudFileBean setFileDownPath(String fileDownPath) {
        this.fileDownPath = fileDownPath;
        return this;
    }

    public String getSdcardFilePath() {
        return sdcardFilePath;
    }

    public CloudFileBean setSdcardFilePath(String sdcardFilePath) {
        this.sdcardFilePath = sdcardFilePath;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public CloudFileBean setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public CloudFileBean setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }
}
