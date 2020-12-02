package com.example.sdlapp;

public class Club {

    private String clubName;
    private String clubDesc;
    private String clubWebsite;

    public Club(String clubName, String clubDesc, String clubWebsite) {
        this.clubName = clubName;
        this.clubDesc = clubDesc;
        this.clubWebsite = clubWebsite;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubDesc() {
        return clubDesc;
    }

    public void setClubDesc(String clubDesc) {
        this.clubDesc = clubDesc;
    }

    public String getClubWebsite() {
        return clubWebsite;
    }

    public void setClubWebsite(String clubWebsite) {
        this.clubWebsite = clubWebsite;
    }
}
