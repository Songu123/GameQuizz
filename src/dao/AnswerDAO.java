package dao;

import entity.Answer;
import entity.Quizz;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerDAO {
    private final Connection connection;

    public AnswerDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Answer> getAllAnswers() {
        List<Answer> answers = new ArrayList<>();
        String query = "Select * from answers";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String content = rs.getString("content");
                int questionId = rs.getInt("questionId");
                boolean isCorrect = rs.getBoolean("is_true");
                Answer answer = new Answer(id, content, questionId, isCorrect);
                answers.add(answer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return answers;
    }
}
