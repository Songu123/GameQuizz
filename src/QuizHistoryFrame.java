import database.Database;
import entity.QuizHistoryEntry;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizHistoryFrame extends JFrame {
    private JTable historyTable;
    private List<QuizHistoryEntry> quizHistoryEntryList = new ArrayList<>();
    private int index = 0;

    public QuizHistoryFrame(int userId) {
        setTitle("Quiz History");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo bảng lịch sử thi
        historyTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(historyTable);
        add(scrollPane);

        // Tạo model cho bảng lịch sử thi
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("<html><b>Số thứ tự</b></html>");
        model.addColumn("<html><b>Tên Quiz</b></html>");
        model.addColumn("<html><b>Số câu</b></html>");
        model.addColumn("<html><b>Số đáp án đúng</b></html>");
        model.addColumn("<html><b>Ngày thi</b></html>");
        historyTable.setModel(model);

        // In đậm các tiêu đề cột
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) historyTable.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        headerRenderer.setFont(headerRenderer.getFont().deriveFont(Font.BOLD)); // Đặt font đậm cho tiêu đề cột

        // Đặt màu và kích thước của viền cho bảng
        historyTable.setShowGrid(true);
        historyTable.setGridColor(Color.BLACK);
        historyTable.setIntercellSpacing(new Dimension(0, 0)); // Đặt khoảng cách giữa các ô là 0

        // Lấy dữ liệu từ cơ sở dữ liệu và thêm vào bảng
        try {
            List<QuizHistoryEntry> quizHistory = getQuizHistoryFromDatabase(userId);
            for (QuizHistoryEntry entry : quizHistory) {
                index++;
                model.addRow(new Object[]{
                        (index),
                        entry.getQuizName(),
                        entry.getQuestionCount(),
                        entry.getCorrectAnswers(),
                        entry.getQuizDate()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể lấy dữ liệu từ cơ sở dữ liệu.");
        }

        setVisible(true);
    }

    // Hàm giả lập để lấy dữ liệu lịch sử thi từ cơ sở dữ liệu
    private List<QuizHistoryEntry> getQuizHistoryFromDatabase(int userId) throws SQLException {
        Connection connection = Database.getConnection();
        String query = "SELECT q.name AS quiz_name, COUNT(q2.quizz_id) AS question_count, SUM(a.is_correct = 1) AS correct_answer_count, r.time AS result_time\n" +
                "FROM quizzes q\n" +
                "INNER JOIN results r ON r.quiz_id = q.id\n" +
                "INNER JOIN result_details rd ON rd.result_id = r.id\n" +
                "INNER JOIN questions q2  ON q2.id = rd.question_id \n" +
                "INNER JOIN answers a ON a.id = rd.answer_id\n" +
                "WHERE r.user_id = ? \n" +
                "GROUP BY r.time;";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("quiz_name");
                int countQuestion = rs.getInt("question_count");
                int correctAnswer = rs.getInt("correct_answer_count");
                String time = rs.getString("result_time");
                QuizHistoryEntry quizHistoryEntry = new QuizHistoryEntry(name, countQuestion, correctAnswer, time);
                quizHistoryEntryList.add(quizHistoryEntry);
            }
        }

        return quizHistoryEntryList;
    }

    public static void main(String[] args) {
        QuizHistoryFrame quizHistoryFrame = new QuizHistoryFrame(1);
    }
}
