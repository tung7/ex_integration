package com.tung7.ex.repository.aop.domain;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/4/13.
 * @update
 */

public class AuditLogBean {
    private Long id;
    private String loginName;
    private String name;
    private Long operationTime;
    private String module;
    private Terminal terminal;
    private String description;
    private String ip;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Long operationTime) {
        this.operationTime = operationTime;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public String toString() {
        return "AuditLogBean{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                ", operationTime=" + operationTime +
                ", module='" + module + '\'' +
                ", terminal=" + terminal +
                ", description='" + description + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }

    public enum Terminal {
        PC("pc"),IOS("ios"), ANDROID("android");

        private String name;
        Terminal(String name){
            this.name = name;
        }
        public String toString() {
            return this.name;
        }
    }

}
