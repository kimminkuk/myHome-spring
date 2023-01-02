package com.example.myHome.myHomespring.domain.quant;

import java.util.Date;

public class QuantStrategyMember {
    private Long id;

    private String userName;

    private String saveTime;

    private String strategyTitle;
    private String strategyInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }

    public String getStrategyTitle() {
        return strategyTitle;
    }

    public void setStrategyTitle(String strategyTitle) {
        this.strategyTitle = strategyTitle;
    }

    public String getStrategyInfo() {
        return strategyInfo;
    }

    public void setStrategyInfo(String strategyInfo) {
        this.strategyInfo = strategyInfo;
    }

    public QuantStrategyMember(String userName, String saveTime, String strategyTitle, String strategyInfo) {
        this.userName = userName;
        this.saveTime = saveTime;
        this.strategyTitle = strategyTitle;
        this.strategyInfo = strategyInfo;
    }

    public QuantStrategyMember() {

    }
}
