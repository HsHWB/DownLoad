package com.example.root.entities;

import java.io.Serializable;

/**
 * Created by root on 15-7-23.
 */
public class FileInfo implements Serializable{

    private String fileName;
    private String fileId;
    private String fileUrl;
    private int length;
    private int finished;

    public FileInfo(){
        super();
    }

    public FileInfo(String fileName, String fileId, String fileUrl, int length, int finished) {
        this.fileName = fileName;
        this.fileId = fileId;
        this.fileUrl = fileUrl;
        this.length = length;
        this.finished = finished;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "fileName='" + fileName + '\'' +
                ", fileId='" + fileId + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", length=" + length +
                ", finished=" + finished +
                '}';
    }
}
