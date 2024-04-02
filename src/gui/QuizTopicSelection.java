package gui;

import common.CurrentTime;
import dao.QuestionDAO;
import dao.QuizzDAO;
import database.Database;
import entity.Question;
import entity.Quizz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizTopicSelection extends JFrame {
    private static List<Quizz> quizzList = new ArrayList<>();
    private List<Integer> indexQuizzList = new ArrayList<>();
    private String currentTime = CurrentTime.getCurrentTime();
    private JComboBox<String> quizComboBox;
    private JButton startButton;
    private JButton cancelButton; // Thêm nút cancel

    public QuizTopicSelection(int userId) {
        setTitle("Quiz Topic Selection");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        try {
            Connection conn = Database.getConnection();
            QuizzDAO quizzDAO = new QuizzDAO(conn);
            quizzList = quizzDAO.getAllQuizzes();

            List<String> contentQuizzList = new ArrayList<>();
            indexQuizzList = new ArrayList<>();
            for (Quizz q : quizzList) {
                contentQuizzList.add(q.getName());
                indexQuizzList.add(q.getId());
            }

            List<String> quizList = contentQuizzList;
            quizComboBox = new JComboBox<>(quizList.toArray(new String[0]));
            add(new JLabel("Select a quiz:"));
            add(quizComboBox);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cancelButton = new JButton("Cancel");

        startButton = new JButton("Start Quiz");

        add(cancelButton);

        add(startButton);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startQuiz(userId);
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Thực hiện hành động khi nút cancel được nhấn
                dispose(); // Đóng cửa sổ hiện tại
                UserFrame userFrame = new UserFrame(userId);
            }
        });

        setVisible(true);
    }

    private void startQuiz(int userId) {
        String selectedQuiz = (String) quizComboBox.getSelectedItem();
        int indexQuiz = quizComboBox.getSelectedIndex();
        if (selectedQuiz != null) {
            JOptionPane.showMessageDialog(null, "You selected: " + selectedQuiz);
            if (checkExist(indexQuizzList.get(indexQuiz))) {
                BeginQuizz quiz = new BeginQuizz(indexQuizzList.get(indexQuiz), userId, currentTime);
            } else {
                JOptionPane.showMessageDialog(null, "Không có câu hỏi nào trong đề này");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a quiz");
        }
    }

    public static boolean checkExist(int quizzId) {
        boolean success = false;
        try {
            QuestionDAO questionDAO = new QuestionDAO(Database.getConnection());
            List<Question> questionlist = questionDAO.getAllQuestions();

            for (Question q : questionlist) {
                if (q.getQuizzes_id() == quizzId) {
                    success = true;
                    break;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return success;
    }

}