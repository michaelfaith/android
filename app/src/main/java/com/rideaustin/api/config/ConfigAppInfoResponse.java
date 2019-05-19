package com.rideaustin.api.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ConfigAppInfoResponse implements Serializable {

    public static final long serialVersionUID = -1552922465746563282L;
    @SerializedName("downloadUrl")
    @Expose
    private String downloadUrl;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("mandatoryUpgrade")
    @Expose
    private boolean mandatoryUpgrade;
    @SerializedName("platformType")
    @Expose
    private String platformType;
    @SerializedName("userAgentHeader")
    @Expose
    private String userAgentHeader;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("version")
    @Expose
    private String version;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public int getId() {
        return id;
    }

    public boolean isMandatoryUpgrade() {
        return mandatoryUpgrade;
    }

    public String getPlatformType() {
        return platformType;
    }

    public String getUserAgentHeader() {
        return userAgentHeader;
    }

    public String getUuid() {
        return uuid;
    }

    public String getVersion() {
        return version;
    }


    public void setMandatoryUpgrade(boolean mandatoryUpgrade) {
        this.mandatoryUpgrade = mandatoryUpgrade;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public void setUserAgentHeader(String userAgentHeader) {
        this.userAgentHeader = userAgentHeader;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ConfigAppInfoResponse{" +
                "downloadUrl='" + downloadUrl + '\'' +
                ", id=" + id +
                ", mandatoryUpgrade=" + mandatoryUpgrade +
                ", platformType='" + platformType + '\'' +
                ", userAgentHeader='" + userAgentHeader + '\'' +
                ", uuid='" + uuid + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}