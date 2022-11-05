package com.example.myHome.myHomespring.domain.facility;

public class FacReserveTimeMember {
    private Long id;
    private String reserveFacTitle;
    private String userName;
    private String reserveTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReserveFacTitle() {
        return reserveFacTitle;
    }

    public void setReserveFacTitle(String reserveFacTitle) {
        this.reserveFacTitle = reserveFacTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(String reserveTime) {
        this.reserveTime = reserveTime;
    }

    public FacReserveTimeMember() {

    }

    public FacReserveTimeMember(String reserveFacTitle, String userName, String reserveTime) {
        this.reserveFacTitle = reserveFacTitle;
        this.userName = userName;
        this.reserveTime = reserveTime;
    }

    public FacReserveTimeMember(String reserveFacTitle, String userName) {
        this.reserveFacTitle = reserveFacTitle;
        this.userName = userName;
    }
}
