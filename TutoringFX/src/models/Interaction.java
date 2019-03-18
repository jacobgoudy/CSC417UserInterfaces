package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static models.Tutor.TABLE;

public class Interaction extends Model {
    
    public static final String TABLE = "interaction";
    
    private int id = 0;
    private int tutor_id;
    private int student_id;
    private String report;
  
    // Default constructor
    Interaction() {
    }
    
    public Interaction(int tutor_id, int student_id, String report) {
        this.tutor_id = tutor_id;
        this.student_id = student_id;
        this.report = report;
    }
    
    // Getters
    @Override
    public int getId() { 
        return id;
    }
    
    public int getTutorId() {
        return tutor_id;
    }
    
    public int getStudentId() {
        return student_id;
    }
    
    public String getReport() {
        return report;
    }
    
    // Setters
    public void setTutorId(int tutor_id) {
        this.tutor_id = tutor_id;
    }
    
    public void setStudentId(int student_id) {
        this.student_id = student_id;
    }
    
    public void setReport(String report) {
        this.report = report;
    }
  
    // used for SELECT operations in ORM.load, ORM.findAll, ORM.findOne
    @Override
    void load(ResultSet rs) {
        try {
            id = rs.getInt("id");
            tutor_id = rs.getInt("tutor_id");
            student_id = rs.getInt("student_id");
            report = rs.getString("report");
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex.getClass() + ":" + ex.getMessage());
        }
    }
  
    // user for INSERT operations in ORM.store (for new record)
    @Override
    public void insert() {
        Connection cx = ORM.connection();
        try {
            String sql = String.format(
                "insert into %s (tutor_id,student_id,report) values (?,?,?)", TABLE);
            PreparedStatement st = cx.prepareStatement(sql);
            int i = 0;
            st.setInt(++i, tutor_id);
            st.setInt(++i, student_id);
            st.setString(++i, report);
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
                "update %s set tutor_id=?,student_id=?,report=? where id=?", TABLE);
            PreparedStatement st = cx.prepareStatement(sql);
            int i = 0;
            st.setInt(++i, tutor_id);
            st.setInt(++i, student_id);
            st.setString(++i, report);
            st.setInt(++i, id);
            st.executeUpdate();
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex.getClass() + ":" + ex.getMessage());
        }
    }
    
    @Override
    public String toString() {
        return String.format("(%s,%s,%s,%s)", id, tutor_id, student_id, report);
    }
}
