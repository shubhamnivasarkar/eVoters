package com.core.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.core.pojos.Voter;
import static com.core.utils.DBUtils.getConnection;
import com.core.exception.InvalidVoterException;

public class VoterDAOImpl implements IVoterDAO {
    private Connection conn;
    private PreparedStatement register;
    private PreparedStatement selectAll;
    private PreparedStatement selectByID;
    private PreparedStatement updateStatus;
    private PreparedStatement selectByEmailAndPassword;
    private static String tablename;
    
    static {
        tablename = "voters";
    }
    
    public VoterDAOImpl () throws ClassNotFoundException, SQLException {
        this.conn = getConnection();
        this.register = conn.prepareStatement("INSERT INTO " + tablename + " VALUES (default, ?, ?, ?, 0, ?)");
        this.selectAll = conn.prepareStatement("SELECT * FROM " + tablename);
        this.selectByID = conn.prepareStatement("SELECT * FROM " + tablename + " WHERE id = ?");
        this.updateStatus = conn.prepareStatement("UPDATE " + tablename + " SET status = ? WHERE id = ?");
        this.selectByEmailAndPassword = conn.prepareStatement("SELECT * FROM " + tablename + " WHERE email = ? AND password = ?");
    }

    @Override
    public List<Voter> getVoters() throws SQLException {
        ArrayList<Voter> voters = new ArrayList<>();
        try (ResultSet result = selectAll.executeQuery()) {
            while(result.next()) {
                voters.add(new Voter(
                  result.getInt(1),
                  result.getString(2),
                  result.getString(3),
                  result.getString(4),
                  result.getInt(5),
                  result.getString(6)
                ));
            }
        }
        return voters;
    }

    @Override
    public String registerVoter(Voter v) throws SQLException {
        register.setString(1, v.getName());
        register.setString(2, v.getEmail());
        register.setString(3, v.getPassword());
        register.setString(4, v.getRole());
        if (register.executeUpdate() == 1) {
            return "Voter registered successfully.";
        }
        return "Voter registration failed.";
    }

    @Override
    public boolean updateStatus(int id, boolean status) throws SQLException {
        updateStatus.setInt(1, status ? 1 : 0);
        updateStatus.setInt(2, id);
        return updateStatus.executeUpdate() == 1;
    }

    @Override
    public Voter getVoterByID(int id) throws SQLException, InvalidVoterException {
        selectByID.setInt(1, id);
        try (ResultSet result = selectByID.executeQuery()) {
            if (result.next()) {
                return new Voter(
                  result.getInt(1),
                  result.getString(2),
                  result.getString(3),
                  result.getString(4),
                  result.getInt(5),
                  result.getString(6)
                );
            }
            throw new InvalidVoterException("Voter with ID " + id + " doesn't exist.");
        }
    }
    
    @Override
    public Voter authenticate(String email, String password) throws SQLException, InvalidVoterException {
      selectByEmailAndPassword.setString(1, email);
      selectByEmailAndPassword.setString(2, password);
      try (ResultSet result = selectByEmailAndPassword.executeQuery()) {
        if (result.next()) {
            return new Voter(
              result.getInt(1),
              result.getString(2),
              result.getString(3),
              result.getString(4),
              result.getInt(5),
              result.getString(6)
            );
        }
        throw new InvalidVoterException("Invalid email or password.");
    }
    }

    @Override
    public void close() throws SQLException {
        if (register != null) register.close();
        if (selectAll != null) selectAll.close();
        if (selectByID != null) selectByID.close();
        if (updateStatus != null) updateStatus.close();
        if (selectByEmailAndPassword != null) selectByEmailAndPassword.close();
    }
}
