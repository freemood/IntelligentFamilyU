package com.Intelligent.FamilyU.model.cloud.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class CloudSearchFileListBean implements Serializable {

    private ArrayList<CloudStorageFile> files;



    public ArrayList<CloudStorageFile> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<CloudStorageFile> files) {
        this.files = files;
    }
}
