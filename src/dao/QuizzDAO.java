package dao;

import entity.Quizz;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizzDAO {
    private final Connection connection;

    public QuizzDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Quizz> getAllQuizzes() {
        List<Quizz> quizzes = new ArrayList<>();
        String query = "Select * from quizzes";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int userId = rs.getInt("user_id");
                Quizz quizz = new Quizz(id, name, userId);
                quizzes.add(quizz);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quizzes;
    }
}
