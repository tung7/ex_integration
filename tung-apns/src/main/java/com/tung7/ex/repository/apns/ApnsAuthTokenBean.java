package com.tung7.ex.repository.apns;

/**
 * @author Tung
 * @version 1.0
 * @date 2016/12/16
 * @update
 */
public class ApnsAuthTokenBean {
    private String apnsAuthKey;
    private String teamID;
    private String keyID;
    private String defaultTopic;

    public ApnsAuthTokenBean() {}

    public ApnsAuthTokenBean(String apnsAuthKey, String teamID, String keyID, String defaultTopic) {
        this.apnsAuthKey = apnsAuthKey;
        this.teamID = teamID;
        this.keyID = keyID;
        this.defaultTopic = defaultTopic;
    }

    public String getApnsAuthKey() {
        return apnsAuthKey;
    }

    public void setApnsAuthKey(String apnsAuthKey) {
        this.apnsAuthKey = apnsAuthKey;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getKeyID() {
        return keyID;
    }

    public void setKeyID(String keyID) {
        this.keyID = keyID;
    }

    public String getDefaultTopic() {
        return defaultTopic;
    }

    public void setDefaultTopic(String defaultTopic) {
        this.defaultTopic = defaultTopic;
    }
}