package dao;

import database.Database;
import entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultDetailDAO {
    private final Connection connection;

    public ResultDetailDAO(Connection connection) {
        this.connection = connection;
    }

    public List<ResultDetail> getAllResultDetails() {
        List<ResultDetail> resultDetails = new ArrayList<>();
        String query = "Select * from results";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int resultId = rs.getInt("result_id");
                int questionId = rs.getInt("question_id");
                int answerId = rs.getInt("answer_id");
                boolean isTrue = rs.getBoolean("is_true");
                ResultDetail resultDetail = new ResultDetail(id, resultId, questionId, answerId, isTrue);
                resultDetails.add(resultDetail);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultDetails;
    }

    public List<ResultDetail> getResultDetail(int resultId){
        List<ResultDetail> resultDetails = new ArrayList<>();
        String sql = "select * from result_details where result_id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, resultId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
//                int id = rs.getInt("id");
//                int resultId = rs.getInt("result_id");
                int questionId = rs.getInt("question_id");
                int answerId = rs.getInt("answer_id");
                boolean isTrue = rs.getBoolean("is_true");
                ResultDetail resultDetail = new ResultDetail(questionId, answerId, isTrue);
                resultDetails.add(resultDetail);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultDetails;
    }

    public void insertResultDetails(ResultDetail resultDetail) {
        String sql = "INSERT INTO result_details (result_id, question_id, answer_id, is_true) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, resultDetail.getResultId());
            ps.setInt(2, resultDetail.getQuestionId());
            ps.setInt(3, resultDetail.getAnswerId());
            ps.setBoolean(4, resultDetail.isTrue());
            ps.execute();
            System.out.println("ResultDetails inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting result details:");
            e.printStackTrace(); // Print stack trace for debugging
            throw new RuntimeException("Error inserting result details", e);
        }
    }
}
