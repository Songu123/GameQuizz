import dao.AnswerDAO;
import dao.QuestionDAO;
import dao.ResultDetailDAO;
import database.Database;
import entity.Answer;
import entity.Question;
import entity.ResultDetail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowAnswer extends JFrame {
    private static List<Question> questionList = new ArrayList<>();
    private static List<Answer> answerList = new ArrayList<>();
    private static List<ResultDetail> resultDetailList = new ArrayList<>();

    public List<Question> questions = new ArrayList<>();
    public ShowAnswer(int quizId, int resultId) {
        try {
            QuestionDAO questionDAO = new QuestionDAO(Database.getConnection());
            AnswerDAO answerDAO = new AnswerDAO(Database.getConnection());
            ResultDetailDAO resultDetailDAO = new ResultDetailDAO(Database.getConnection());

            questionList = questionDAO.getAllQuestions();
            answerList = answerDAO.getAllAnswers();
            resultDetailList = resultDetailDAO.getResultDetail(resultId);

            for (Question q : questionList) {
                if (q.getQuizzes_id() == quizId) {
                    questions.add(q);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setTitle("ĐÁP ÁN CỦA BẠN");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Thiết lập layout cho panel

        JScrollPane scrollPane = new JScrollPane(panel);
        getContentPane().add(scrollPane);

        for (int i = 0; i < questions.size(); i++) {
            Question currentQuestion = questions.get(i);

            // Tạo label cho câu hỏi và số câu
            JLabel questionLabel = new JLabel("<html><b>Câu hỏi " + (i + 1) +": " +currentQuestion.getContent() + "</b></html>");
            questionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            panel.add(questionLabel);

            JLabel answerLabel = new JLabel("<html><b>Đáp án bạn chọn:</b> " + getUserAnswer(currentQuestion.getId(), resultDetailList) + "</html>");
            answerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            panel.add(answerLabel);

            JLabel correctAnswerLabel = new JLabel("<html><b>Đáp án đúng:</b> " + getCorrectAnswerByQuestionId(currentQuestion.getId(), answerList) + "</html>");
            correctAnswerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            panel.add(correctAnswerLabel);

            panel.add(Box.createVerticalStrut(10));

            JSeparator separator = new JSeparator();
            panel.add(separator);
        }

        setVisible(true);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Đóng cửa sổ JFrame khi nút được nhấp
            }
        });
        getContentPane().add(closeButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static String getCorrectAnswerByQuestionId(int questionId, List<Answer> answerList) {
        String correctAnswer = null;
        for (Answer answer : answerList) {
            if (answer.getQuestionId() == questionId && answer.isCorrect()) {
                correctAnswer = answer.getContent();
                break;
            }
        }
        return correctAnswer;
    }

    public static String getUserAnswer(int questionId, List<ResultDetail> resultDetailList) {
        String userAnswer = null;
        int answerId = 0;
        for (ResultDetail resultDetail : resultDetailList) {
            if (resultDetail.getQuestionId() == questionId) {
                answerId = resultDetail.getAnswerId();
                for (Answer a : answerList) {
                    if (answerId == a.getId()) {
                        userAnswer = a.getContent();
                        break;
                    }
                }
                break;
            }
        }
        return userAnswer;
    }

    public static void main(String[] args) {
        ShowAnswer showAnswer = new ShowAnswer(1, 14);
    }
}
