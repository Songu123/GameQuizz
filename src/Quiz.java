import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Quiz {
    public static List<Question> list = new ArrayList<>();
    public static List<Answer> answerList = new ArrayList<>();
    private JTextField jTextQuestion;
    private JRadioButton answer1;
    private JRadioButton answer2;
    private JRadioButton answer3;
    private JRadioButton answer4;
    private JButton nextButton;
    private JPanel containerGame;

    public static int index = 0;
    public static void listQuestion(List<Question> list) throws SQLException {
        Database database = new Database();
        Connection connection = database.connectDatabase();
        System.out.println("Connect success....");
        try {
            String query = "SELECT * FROM questions"; // Corrected syntax with "FROM"
            PreparedStatement ps = connection.prepareStatement(query);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String content = rs.getString("content");
                    Question question = new Question(id, content);
                    list.add(question);
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Print stack trace for debugging
                System.err.println("Error executing query: " + e.getMessage());
            }
        }catch (Exception e){
            e.getMessage();
        }



    }

    public Quiz(){
        JFrame frame = new JFrame("Quiz");
        frame.setContentPane(containerGame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 450);
        frame.setLocationRelativeTo(null);
//        frame.pack();
        frame.setVisible(true);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    showQuiz();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static void main(String[] args) throws SQLException {
        listQuestion(list);
        for(Question q: list){
            System.out.println(q);
        }
        showQuiz();
    }

    public static void showQuiz() throws SQLException {
        index++;
        Quiz quiz = new Quiz();
        Database database = new Database();
        Connection connection = database.connectDatabase();
//        Truy vấn câu hỏi
        String query = "SELECT * FROM questions where id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, index);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String content = rs.getString("content");
                quiz.jTextQuestion.setText(content);
//                Truy vấn đáp án dựa trên id của câu hỏi
                String query2 = "SELECT * FROM answers where question_id = ?";
                PreparedStatement ps2 = connection.prepareStatement(query2);
                ps2.setInt(1, id);
                ResultSet rs2 = ps2.executeQuery();
                int count = 1;
                while (rs2.next()){
                    if (count > 4){
                        count = 1;
                    }
                    String contentAnswer= rs2.getString("content");
                    switch (count){
                        case 1:
                            quiz.answer1.setText(contentAnswer);
                            break;
                        case 2:
                            quiz.answer2.setText(contentAnswer);
                            break;
                        case 3:
                            quiz.answer3.setText(contentAnswer);
                            break;
                        case 4:
                            quiz.answer4.setText(contentAnswer);
                            break;
                    }
                    count++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print stack trace for debugging
            System.err.println("Error executing query: " + e.getMessage());
        }

    }

}
