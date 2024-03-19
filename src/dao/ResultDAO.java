package dao;

import entity.Answer;
import entity.Quizz;
import entity.Result;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultDAO {
    private final Connection connection;

    public ResultDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Result> getAllResults() {
        List<Result> results = new ArrayList<>();
        String query = "Select * from results";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                int quizzId = rs.getInt("quizz_id");
                Date time = rs.getDate("time");
                Result result = new Result(id, userId, quizzId, time);
                results.add(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }
}
