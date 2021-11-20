package com.core.pojos;

public class Voter {
    private int id;
    private String name;
    private String email;
    private String password;
    private int status;
    private String role;
    
    public Voter () {}
    
    public Voter(String name, String email, String password, String role) {
      this.name = name;
      this.email = email;
      this.password = password;
      this.role = role;
    }

    public Voter(String name, String email, String password, int status, String role) {
        this(name, email, password, role);
        this.status = status;
    }
    
    public Voter(int id, String name, String email, String password, int status, String role) {
        this(name, email, password, status, role);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Voters [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", status="
                + status + ", role=" + role + "]";
    }
}
