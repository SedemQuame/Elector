package com.example.voteapp.model;

public class Election {
    private String electionName;
    private String electionDescription;
    private String imageUrl;


    public Election(String electionName, String electionDescription, String imageUrl) {
        this.electionName = electionName;
        this.electionDescription = electionDescription;
        this.imageUrl = imageUrl;
    }

    public String getElectionName() {
        return electionName;
    }

    public String getElectionDescription() {
        return electionDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
