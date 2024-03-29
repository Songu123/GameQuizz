package dao;

import entity.Quizz;
import entity.User;

import java.sql.*;
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

//    public void insertQuizz(String name, int userId){
//        String sql = "Insert into quizzes (name, user_id) values ( ?, ?)";
//        try (PreparedStatement ps = connection.prepareStatement(sql)){
//            ps.setString(1, name);
//            ps.setInt(2, userId);
//            ps.executeUpdate();
//            System.out.println("Result inserted successfully.");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public int insertQuizz(String name, int userId) {
        String sql = "INSERT INTO quizzes (name, user_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setInt(2, userId);
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting quiz failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Inserting quiz failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting quiz: " + e.getMessage(), e);
        }
    }
}
