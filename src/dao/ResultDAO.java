package dao;

import entity.Result;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ResultDAO {
    private final Connection connection;

    public ResultDAO(Connection connection) {
        this.connection = connection;
    }

//    Lấy hết tất cả dữ liệu table results
    public List<Result> getAllResults() {
        List<Result> results = new ArrayList<>();
        String query = "Select * from results";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                int quizzId = rs.getInt("quizz_id");
                String time = rs.getString("time");
                Result result = new Result(id, userId, quizzId, time);
                results.add(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

//   Lấy resultId từ time
    public int getResultId(String time){
        int id = 0;
        String query = "select * from results where time = ? ";
        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, time);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

//    Thêm result mới
    public void insertResult(Result result) {
        String sql = "INSERT INTO results (user_id, quiz_id, time) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, result.getUserId());
            ps.setInt(2, result.getQuizId());
            ps.setString(3, result.getTime()); // Assuming result.getTime() returns a string in the format 'yyyy-MM-dd HH:mm:ss'
            ps.executeUpdate();
            System.out.println("Result inserted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting result", e);
        }
    }


    private Date convertStringToDateSql(String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Parse the string into a LocalDateTime object
        LocalDateTime localDateTime = LocalDateTime.parse(s, formatter);

        // Convert LocalDateTime to java.sql.Timestamp
        Timestamp timestamp = Timestamp.valueOf(localDateTime);

        // Convert Timestamp to java.sql.Date
        return new Date(timestamp.getTime());
    }
}


