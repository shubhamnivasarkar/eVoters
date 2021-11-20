package com.core.dao;

import java.util.List;
import java.sql.SQLException;
import com.core.pojos.Candidate;
import com.core.exception.InvalidCandidateException;

public interface ICandidateDAO extends IDAO {
    public List<Candidate> getCandidates () throws SQLException;
    public String registerCandidate (Candidate c) throws SQLException;
    public boolean updateVote (int id) throws SQLException;
    public Candidate getCandidateByID (int id) throws SQLException, InvalidCandidateException;
}
