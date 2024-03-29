package dao;

import entity.Answer;
import entity.Question;
import entity.Quizz;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private final Connection connection;

    public QuestionDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        String query = "Select * from questions";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String content = rs.getString("content");
                int quizzId = rs.getInt("quizz_id");
                Question question = new Question(id, content, quizzId);
                questions.add(question);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return questions;
    }

    public int insertQuestion(String content, int quizzId){
        int questionId = 0;
        String sql = "Insert into questions(content, quizz_id) values (?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, content);
            ps.setInt(2, quizzId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return questionId;
    }

    public int getCountQuestion(int quizzId){
        int quantityQuestion = 0;
        String sql = "select count(*) as count_question from questions where quizz_id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quizzId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                quantityQuestion = rs.getInt("count_question");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quantityQuestion;
    }
}
