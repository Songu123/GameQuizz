package dao;

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
}
