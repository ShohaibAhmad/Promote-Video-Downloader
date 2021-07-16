package com.promoteprovider.promotevideodownloader.Models;

import android.net.Uri;

public class WhatsUpStatusModel {
    private String name;
    private Uri uri;
    private String path;
    private String fileName;

    public WhatsUpStatusModel(String name, Uri uri, String path, String fileName) {
        this.name = name;
        this.uri = uri;
        this.path = path;
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
