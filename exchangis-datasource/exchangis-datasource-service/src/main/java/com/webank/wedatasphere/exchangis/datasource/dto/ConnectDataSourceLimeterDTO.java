package com.webank.wedatasphere.exchangis.datasource.dto;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Date;

public class ConnectDataSourceLimeterDTO {

    private Date currentDate;

    private Integer attemptTime;

    private RateLimiter rateLimiter;

    public ConnectDataSourceLimeterDTO() {
    }

    public ConnectDataSourceLimeterDTO(RateLimiter rateLimiter, Date currentDate) {
        this.attemptTime = 1;
        this.rateLimiter = rateLimiter;
        this.currentDate = currentDate;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Integer getAttemptTime() {
        return attemptTime;
    }

    public void setAttemptTime(Integer attemptTime) {
        this.attemptTime = attemptTime;
    }

    public RateLimiter getRateLimiter() {
        return rateLimiter;
    }

    public void setRateLimiter(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    public void addAttemptTime() {
        this.attemptTime = this.attemptTime + 1;
    }

    public void resetAttemptTime() {
        this.attemptTime = 1;
    }

    public boolean isLimit() {
        return this.attemptTime >= 100;
    }

    @Override
    public String toString() {
        return "ConnectDataSourceLimeterDTO{" +
                "currentDate='" + currentDate + '\'' +
                ", attemptTime=" + attemptTime +
                ", rateLimiter=" + rateLimiter +
                '}';
    }
}
