package ra.bt2.dao;

import ra.bt2.models.Candidates;
import ra.bt2.models.Skills;
import ra.bt2.utils.ConnectionDB;

import java.sql.*;
import java.util.List;

public class CandidateDao {
    public void addCandSk(Candidates candidate, List<Skills> skills) {
        Connection connection = null;
        PreparedStatement ppsta = null;
        PreparedStatement pstmtSkills = null;
        try {
            connection = ConnectionDB.getConnection();
            connection.setAutoCommit(false);
            String insertCand = "INSERT INTO candidates (firstName, lastName, dob, phone, email) VALUES (?, ?, ?, ?, ?)";
            ppsta = connection.prepareStatement(insertCand, Statement.RETURN_GENERATED_KEYS);
            ppsta.setString(1, candidate.getFirstName());
            ppsta.setString(2, candidate.getLastName());
            ppsta.setDate(3, new java.sql.Date(candidate.getDob().getTime()));
            ppsta.setString(4, candidate.getPhone());
            ppsta.setString(5, candidate.getEmail());
            ppsta.executeUpdate();
            int candId = -1;
            try (ResultSet rs = ppsta.getGeneratedKeys()) {
                if (rs.next()) {
                    candId = rs.getInt(1);
                }
            }
            if (candId == -1) {
                throw new SQLException("Không tìm thấy ID ứng viên.");
            }
            String insertSkills = "INSERT INTO candidate_skills (candidate_id, skill_id) VALUES (?, ?)";
            pstmtSkills = connection.prepareStatement(insertSkills);
            for (Skills skill : skills) {
                pstmtSkills.setInt(1, candId);
                pstmtSkills.setInt(2, skill.getId());
                pstmtSkills.addBatch();
            }
            pstmtSkills.executeBatch();
            connection.commit();
        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Lỗi khi rollback: " + rollbackEx.getMessage());
                }
            }
        } finally {
            try {
                if (ppsta != null) ppsta.close();
                if (pstmtSkills != null) pstmtSkills.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
