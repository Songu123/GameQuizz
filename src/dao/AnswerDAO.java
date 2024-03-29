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
                int questionId = rs.getInt("question_id");
                boolean isCorrect = rs.getBoolean("is_correct");
                Answer answer = new Answer(id, content, questionId, isCorrect);
                answers.add(answer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return answers;
    }

    public List<Answer> getAnswersWithQuestionId(int questionId) {
        List<Answer> answers = new ArrayList<>();
        String query = "Select * from answers where quesiton_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, questionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String content = rs.getString("content");
                int question_id = rs.getInt("question_id");
                boolean isCorrect = rs.getBoolean("is_correct");
                Answer answer = new Answer(id, content, question_id, isCorrect);
                answers.add(answer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return answers;
    }

    public void insertAnswer(String content, int questionId, int isCorrect){
        String sql = "Insert into answers(content, question_id, is_correct) values (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, content);
            ps.setInt(2, questionId);
            ps.setInt(3, isCorrect);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
