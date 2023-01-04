package com.example.myHome.myHomespring.domain.quant;

public class QuantStrategyInfoMember {
    private String capitalRankingHigh;
    private String capitalRankingLow;
    private String capitalPercentHigh;
    private String capitalPercentLow;
    private String operatingProfitRatio;
    private String netProfitRation;
    private String roe;
    private String roa;
    private String debtRatio;
    private String capitalRetentionRate;
    private String eps;
    private String per;
    private String bps;
    private String pbr;
    private String cashDps;
    private String dividendYield;

    //16 개
    public QuantStrategyInfoMember(String capitalRankingHigh, String capitalRankingLow, String capitalPercentHigh, String capitalPercentLow, String operatingProfitRatio, String netProfitRation, String roe, String roa, String debtRatio, String capitalRetentionRate, String eps, String per, String bps, String pbr, String cashDps, String dividendYield) {
        this.capitalRankingHigh = capitalRankingHigh;
        this.capitalRankingLow = capitalRankingLow;
        this.capitalPercentHigh = capitalPercentHigh;
        this.capitalPercentLow = capitalPercentLow;
        this.operatingProfitRatio = operatingProfitRatio;
        this.netProfitRation = netProfitRation;
        this.roe = roe;
        this.roa = roa;
        this.debtRatio = debtRatio;
        this.capitalRetentionRate = capitalRetentionRate;
        this.eps = eps;
        this.per = per;
        this.bps = bps;
        this.pbr = pbr;
        this.cashDps = cashDps;
        this.dividendYield = dividendYield;
    }

    public QuantStrategyInfoMember() {

    }
}
