package com.core.dao;

import java.util.List;
import com.core.pojos.Voter;
import java.sql.SQLException;
import com.core.exception.InvalidVoterException;

public interface IVoterDAO  extends IDAO {
    public List<Voter> getVoters () throws SQLException;
    public String registerVoter (Voter v) throws SQLException;
    public boolean updateStatus (int id, boolean status) throws SQLException;
    public Voter getVoterByID (int id) throws SQLException, InvalidVoterException;
    public Voter authenticate (String email, String password) throws SQLException, InvalidVoterException;
}
