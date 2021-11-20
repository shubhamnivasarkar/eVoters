package com.core.pojos;

public class Candidate {
    private int id;
    private String name;
    private String party;
    private int votes;
    
    public Candidate() {}
    
    public Candidate(String name, String party) {
      this.name = name;
      this.party = party;
    }

    public Candidate(String name, String party, int votes) {
        this(name, party);
        this.votes = votes;
    }

    public Candidate(int id, String name, String party, int votes) {
        this(name, party, votes);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Candidate [id=" + id + ", name=" + name + ", party=" + party + ", votes=" + votes + "]";
    }
}// Anshuman Gupta
