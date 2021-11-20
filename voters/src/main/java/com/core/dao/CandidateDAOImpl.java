package com.core.dao;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;

import com.core.pojos.Candidate;
import static com.core.utils.DBUtils.getConnection;
import com.core.exception.InvalidCandidateException;

public class CandidateDAOImpl implements ICandidateDAO {
    private Connection conn;
    private PreparedStatement register;
    private PreparedStatement selectAll;
    private PreparedStatement selectByID;
    private PreparedStatement updateVote;
    private PreparedStatement partyWiseVote;
    private static String tablename;
    
    static {
        tablename = "candidates";
    }
    
    public CandidateDAOImpl() throws ClassNotFoundException, SQLException {
        this.conn = getConnection();
        this.register = conn.prepareStatement("INSERT INTO " + tablename + " VALUES (default, ?, ?, 0)");
        this.selectAll = conn.prepareStatement("SELECT * FROM " + tablename);
        this.selectByID = conn.prepareStatement("SELECT * FROM " + tablename + " WHERE id = ?");
        this.updateVote = conn.prepareStatement("UPDATE " + tablename + " SET votes = votes + 1 WHERE id = ?");
        this.partyWiseVote = conn.prepareStatement("SELECT party, SUM(votes) as votes FROM " + tablename + " GROUP BY party ORDER BY votes DESC");
    }

    @Override
    public String registerCandidate(Candidate c) throws SQLException {
        register.setString(1, c.getName());
        register.setString(2, c.getParty());
        if (register.executeUpdate() == 1) {
            return "Candidate registered successfully.";
        }
        return "Candidate registration failed.";
    }
    
    @Override
    public Candidate getCandidateByID(int id) throws SQLException, InvalidCandidateException {
        selectByID.setInt(1, id);
        try (ResultSet result = selectByID.executeQuery()) {
            if(result.next()) {
                return new Candidate(result.getInt(1), result.getString(2), result.getString(3), result.getInt(4));
            }
            throw new InvalidCandidateException("Candidate with ID " + id + " doesn't exist.");
        }
    }

    @Override
    public List<Candidate> getCandidates() throws SQLException {
        ArrayList<Candidate> candidates = new ArrayList<>();
        try (ResultSet result = selectAll.executeQuery()) {
            while(result.next()) {
                candidates.add(new Candidate(result.getInt(1), result.getString(2), result.getString(3), result.getInt(4)));
            }
        }
        return candidates;
    }
    
    @Override
    public boolean updateVote(int id) throws SQLException {
        updateVote.setInt(1, id);
        return updateVote.executeUpdate() == 1;
    }

    @Override
    public void close() throws SQLException {
        if (register != null) register.close();
        if (selectAll != null) selectAll.close();
        if (selectByID != null) selectByID.close();
        if (updateVote != null) updateVote.close();
        if (partyWiseVote != null) partyWiseVote.close();
    }
    
    public List<Candidate> getRanking () throws SQLException {
      return this.getCandidates()
          .stream()
          .sorted((c1, c2) -> {
            return ((Integer)c2.getVotes()).compareTo((Integer)c1.getVotes());
          })
          .collect(Collectors.toList());
    }
    
    public Map<String, Integer> getPartyWiseVoteCount () throws SQLException {
      HashMap<String, Integer> partyWiseVoteCount = new HashMap<>();
      try (ResultSet result = this.partyWiseVote.executeQuery()) {
        while(result.next()) {
          partyWiseVoteCount.put(result.getString(1), result.getInt(2));
        }
      }
      return partyWiseVoteCount;
    }
}

