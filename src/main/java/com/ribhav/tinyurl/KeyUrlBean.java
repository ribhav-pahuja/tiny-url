package com.ribhav.tinyurl;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User : ribhavpahuja
 * Date : 06/11/21
 * Time : 1:41 AM
 */
@Document
public class KeyUrlBean {

    @Id
    private String key;
    private String url;
    private long modifiedTime;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
