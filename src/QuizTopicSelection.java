import dao.QuestionDAO;
import dao.QuizzDAO;
import database.Database;
import entity.Question;
import entity.Quizz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.util.Vector;

public class QuizTopicSelection extends JFrame {
    private static List<Quizz> quizzList = new ArrayList<>();
    private List<Integer> indexQuizzList = new ArrayList<>();

    private JComboBox<String> quizComboBox;
    private JButton startButton;

    public QuizTopicSelection() {
        setTitle("Quiz Topic Selection");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // Create JComboBox with quiz options
        try {
            Connection conn = Database.getConnection();
            QuizzDAO quizzDAO = new QuizzDAO(conn);
            quizzList = quizzDAO.getAllQuizzes();

            // Create a list to hold the quiz names
            List<String> contentQuizzList = new ArrayList<>();
            indexQuizzList = new ArrayList<>();
            for (Quizz q: quizzList){
                System.out.println(q);
                contentQuizzList.add(q.getName());
                indexQuizzList.add(q.getId());
            }

            // Create the JComboBox with the list of quiz names
            List<String> quizList = contentQuizzList; // Assuming you have a method to retrieve quiz topics
            quizComboBox = new JComboBox<>(quizList.toArray(new String[0]));
            add(new JLabel("Select a quiz:"));
            add(quizComboBox);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


// Create Start button
        startButton = new JButton("Start Quiz");
        add(startButton);

        // Add action listener to Start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedQuiz = (String) quizComboBox.getSelectedItem();
                int indexQuiz = quizComboBox.getSelectedIndex();
                if (selectedQuiz != null) {
                    JOptionPane.showMessageDialog(null, "You selected: " + selectedQuiz);
                    // Add your code here to start the selected quiz
                    if (checkExist(indexQuizzList.get(indexQuiz))){
                        Quiz quiz = new Quiz(indexQuizzList.get(indexQuiz));
                    }else {
                        JOptionPane.showMessageDialog(null, "Không có câu hỏi nào trong đề này");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a quiz");
                }
            }
        });
        setVisible(true);
    }

//    Kiểm tra trong Quizz có câu hỏi hay không
    public static boolean checkExist(int quizzId){
        boolean success = false;
        try {
            QuestionDAO questionDAO = new QuestionDAO(Database.getConnection());
            List<Question> questionlist = questionDAO.getAllQuestions();

            for (Question q: questionlist){
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
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QuizTopicSelection();
            }
        });
    }
}
