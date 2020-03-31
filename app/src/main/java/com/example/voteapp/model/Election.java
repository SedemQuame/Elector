package com.example.voteapp.model;

public class Election {
    private String electionName;
    private String electionDescription;
    private String imageUrl;
    private String electionId;
    private String electionStartDate;
    private String electionEndDate;
    private String electionCandidates;
    private String electionStatus;


    public Election(String electionName, String electionDescription, String imageUrl, String electionId, String electionStartDate, String electionEndDate, String electionCandidates, String electionStatus) {
        this.electionName = electionName;
        this.electionDescription = electionDescription;
        this.imageUrl = imageUrl;
        this.electionId = electionId;
        this.electionStartDate = electionStartDate;
        this.electionEndDate = electionEndDate;
        this.electionCandidates = electionCandidates;
        this.electionStatus = electionStatus;
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

    public String getElectionId() {
        return electionId;
    }

    public String getElectionStartDate() {
        return electionStartDate;
    }

    public String getElectionEndDate() {
        return electionEndDate;
    }

    public String getElectionCandidates() {
        return electionCandidates;
    }

    public String getElectionStatus() {
        return electionStatus;
    }
}
