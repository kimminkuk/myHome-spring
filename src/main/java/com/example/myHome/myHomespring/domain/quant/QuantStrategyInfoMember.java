package com.example.myHome.myHomespring.domain.quant;

public class QuantStrategyInfoMember {
    private String capitalRankingHigh;
    private String capitalRankingLow;
    private String capitalPercentHigh;
    private String capitalPercentLow;
    private String marketCapitalization;
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
    private String sales;
    //16 개
    public QuantStrategyInfoMember(String capitalRankingHigh, String capitalRankingLow, String capitalPercentHigh, String capitalPercentLow, String marketCapitalization,
                                   String operatingProfitRatio, String netProfitRation, String roe, String roa, String debtRatio, String capitalRetentionRate, String eps,
                                   String per, String bps, String pbr, String cashDps, String dividendYield, String sales) {

        this.capitalRankingHigh = capitalRankingHigh;
        this.capitalRankingLow = capitalRankingLow;
        this.capitalPercentHigh = capitalPercentHigh;
        this.capitalPercentLow = capitalPercentLow;
        this.marketCapitalization = marketCapitalization;
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
        this.sales = sales;
    }

    public String getMarketCapitalization() {
        return marketCapitalization;
    }

    public void setMarketCapitalization(String marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

    public QuantStrategyInfoMember() {

    }

    public String getCapitalRankingHigh() {
        return capitalRankingHigh;
    }

    public void setCapitalRankingHigh(String capitalRankingHigh) {
        this.capitalRankingHigh = capitalRankingHigh;
    }

    public String getCapitalRankingLow() {
        return capitalRankingLow;
    }

    public void setCapitalRankingLow(String capitalRankingLow) {
        this.capitalRankingLow = capitalRankingLow;
    }

    public String getCapitalPercentHigh() {
        return capitalPercentHigh;
    }

    public void setCapitalPercentHigh(String capitalPercentHigh) {
        this.capitalPercentHigh = capitalPercentHigh;
    }

    public String getCapitalPercentLow() {
        return capitalPercentLow;
    }

    public void setCapitalPercentLow(String capitalPercentLow) {
        this.capitalPercentLow = capitalPercentLow;
    }

    public String getOperatingProfitRatio() {
        return operatingProfitRatio;
    }

    public void setOperatingProfitRatio(String operatingProfitRatio) {
        this.operatingProfitRatio = operatingProfitRatio;
    }

    public String getNetProfitRation() {
        return netProfitRation;
    }

    public void setNetProfitRation(String netProfitRation) {
        this.netProfitRation = netProfitRation;
    }

    public String getRoe() {
        return roe;
    }

    public void setRoe(String roe) {
        this.roe = roe;
    }

    public String getRoa() {
        return roa;
    }

    public void setRoa(String roa) {
        this.roa = roa;
    }

    public String getDebtRatio() {
        return debtRatio;
    }

    public void setDebtRatio(String debtRatio) {
        this.debtRatio = debtRatio;
    }

    public String getCapitalRetentionRate() {
        return capitalRetentionRate;
    }

    public void setCapitalRetentionRate(String capitalRetentionRate) {
        this.capitalRetentionRate = capitalRetentionRate;
    }

    public String getEps() {
        return eps;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getBps() {
        return bps;
    }

    public void setBps(String bps) {
        this.bps = bps;
    }

    public String getPbr() {
        return pbr;
    }

    public void setPbr(String pbr) {
        this.pbr = pbr;
    }

    public String getCashDps() {
        return cashDps;
    }

    public void setCashDps(String cashDps) {
        this.cashDps = cashDps;
    }

    public String getDividendYield() {
        return dividendYield;
    }

    public void setDividendYield(String dividendYield) {
        this.dividendYield = dividendYield;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }
}
