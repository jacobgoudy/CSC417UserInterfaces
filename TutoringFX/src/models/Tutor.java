package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tutor extends Model {
    
    public static final String TABLE = "tutor";
    
    private int id = 0;
    private String name;
    private String email;
    private Subject subject;
  
    // Default constructor
    Tutor() {
    }
    
    public Tutor(String name, String email, Subject subject) {
        this.name = name;
        this.email = email;
        this.subject = subject;
    }
    
    // Getters
    @Override
    public int getId() { 
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public Subject getSubject() {
        return subject;
    }
    
    // Setters
    public void setName(String name) {
        this.name = name;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    // used for SELECT operations in ORM.load, ORM.findAll, ORM.findOne
    @Override
    void load(ResultSet rs) {
        try {
            id = rs.getInt("id");
            name = rs.getString("name");
            email = rs.getString("email");
            Subject tutorSubject = ORM.findOne(Subject.class, "where id=?",
                    new Object[]{rs.getInt("subject_id")});
            subject = tutorSubject;
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex.getClass() + ":" + ex.getMessage());
        }
    }
  
    // user for INSERT operations in ORM.store (for new record)
    @Override
    void insert() {
        Connection cx = ORM.connection();
        try {
            String sql = String.format(
                "insert into %s (name,email,subject_id) values (?,?,?)", TABLE);
            PreparedStatement st = cx.prepareStatement(sql);
            int i = 0;
            st.setString(++i, name);
            st.setString(++i, email);
            st.setInt(++i, subject.getId());
            st.executeUpdate();
            id = ORM.getMaxId(TABLE);
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex.getClass() + ":" + ex.getMessage());
        }
    }
  
    // used for UPDATE operations in ORM.store (for existing record)
    @Override
    void update() {
        Connection cx = ORM.connection();
        try {
            String sql = String.format(
                "update %s set name=?,email=?,subject_id=? where id=?", TABLE);
            PreparedStatement st = cx.prepareStatement(sql);
            int i = 0;
            st.setString(++i, name);
            st.setString(++i, email);
            st.setInt(++i, subject.getId());
            st.setInt(++i, id);
            st.executeUpdate();
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex.getClass() + ":" + ex.getMessage());
        }
    }
    
    @Override
    public String toString() {
        return String.format("(%s,%s,%s,%s)", id, name, email, subject);
    }
}
