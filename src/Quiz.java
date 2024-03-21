import dao.AnswerDAO;
import dao.QuestionDAO;
import database.Database;
import entity.Answer;
import entity.Question;
import entity.Quizz;

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

public class Quiz extends JFrame {
    private static Database database = new Database();
    private static List<Question> questionlist = new ArrayList<>();
    private static List<Question> questionQuizz = new ArrayList<>();
    private static List<Answer> answerList = new ArrayList<>();
    private JTextField jTextQuestion;
    private JRadioButton answer1;
    private JRadioButton answer2;
    private JRadioButton answer3;
    private JRadioButton answer4;

    private ButtonGroup buttonGroup = new ButtonGroup(); // ButtonGroup to group radio buttons

    private JButton nextButton;
    private JPanel containerGame;
    private JLabel indexQuestion;

    public static int index = 0;


    public Quiz(int quizzId) {
//        JFrame frame = new JFrame("Quiz");
        setContentPane(containerGame);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        buttonGroup.add(answer1); // Add radio buttons to ButtonGroup
        buttonGroup.add(answer2);
        buttonGroup.add(answer3);
        buttonGroup.add(answer4);
        setVisible(true);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (index < questionQuizz.size()){
                    System.out.println(questionQuizz.size());
                    index++;
                    int i = 0;

                    indexQuestion.setText("Câu " + (index + 1) + ":");
                    jTextQuestion.setText(questionQuizz.get(index).getContent());

                    for (Answer a : answerList) {
                        if (a.getQuestionId() == questionQuizz.get(index).getId()) {

                            switch (i) {
                                case 0:
                                    answer1.setText(a.getContent());
                                    break;
                                case 1:
                                    answer2.setText(a.getContent());
                                    break;
                                case 2:
                                    answer3.setText(a.getContent());
                                    break;
                                case 3:
                                    answer4.setText(a.getContent());
                                    break;
                                default:
                                    i = 0;
                                    return;
                            }
                            i++;
                        }
                    }
                    buttonGroup.clearSelection(); // Clear the selection of JRadioButtons
                } else {

                    JOptionPane.showMessageDialog(null, "Bạn đã hoàn thành bài thi!"); // Display completion message
                    questionQuizz.clear();
                    dispose();
                }
            }
        });

        try {
            QuestionDAO questionDAO = new QuestionDAO(Database.getConnection());
            AnswerDAO answerDAO = new AnswerDAO(Database.getConnection());
            boolean isExist = false;

            questionlist = questionDAO.getAllQuestions();

            for (Question q: questionlist){
                if (q.getQuizzes_id() == quizzId) {
//                    isExist = true;
                    questionQuizz.add(q);
                }
            }

                System.out.println(questionQuizz);

                answerList = answerDAO.getAllAnswers();
                index = 0;

                indexQuestion.setText("Câu " + (index + 1) + ":");
                jTextQuestion.setText(questionQuizz.get(index).getContent());
                int i = 0;
                for (Answer a : answerList) {
                    if (a.getQuestionId() == questionQuizz.get(index).getId()) {

                        switch (i) {
                            case 0:
                                answer1.setText(a.getContent());
                                break;
                            case 1:
                                answer2.setText(a.getContent());
                                break;
                            case 2:
                                answer3.setText(a.getContent());
                                break;
                            case 3:
                                answer4.setText(a.getContent());
                                break;
                            default:
                                i = 0;
                                return;
                        }
                        i++;
                    }
                }
                buttonGroup.clearSelection(); // Clear the selection of JRadioButtons



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//    public static void main(String[] args) throws SQLException {
//        Quiz quiz = new Quiz();
//    }


}
