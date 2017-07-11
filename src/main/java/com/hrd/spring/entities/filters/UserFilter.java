package com.hrd.spring.entities.filters;

import java.security.Timestamp;

public class UserFilter {
	private String username;
    private String email;
    private String status;
    private Timestamp createdDate;
    private Timestamp toCreatedDate;

    public UserFilter() {
    }

    public UserFilter(String username, String email, String status, Timestamp createdDate, Timestamp toCreatedDate) {
        this.username = username;
        this.email = email;
        this.status = status;
        this.createdDate = createdDate;
        this.toCreatedDate = toCreatedDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getToCreatedDate() {
        return toCreatedDate;
    }

    public void setToCreatedDate(Timestamp toCreatedDate) {
        this.toCreatedDate = toCreatedDate;
    }

}
