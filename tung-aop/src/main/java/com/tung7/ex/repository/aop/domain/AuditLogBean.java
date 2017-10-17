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
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public AuditLogBean setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public String getType() {
        return type;
    }

    public AuditLogBean setType(String type) {
        this.type = type;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public AuditLogBean setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public Long getId() {
        return id;
    }

    public AuditLogBean setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AuditLogBean setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getLoginName() {
        return loginName;
    }

    public AuditLogBean setLoginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public String getName() {
        return name;
    }

    public AuditLogBean setName(String name) {
        this.name = name;
        return this;
    }

    public Long getOperationTime() {
        return operationTime;
    }

    public AuditLogBean setOperationTime(Long operationTime) {
        this.operationTime = operationTime;
        return  this;
    }

    public String getModule() {
        return module;
    }

    public AuditLogBean setModule(String module) {
        this.module = module;
        return this;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public AuditLogBean setTerminal(Terminal terminal) {
        this.terminal = terminal;
        return this;
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
                ", type='" + type + '\'' +
                ", status=" + status +
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
