import com.sun.net.httpserver.Authenticator;
import dao.AnswerDAO;
import dao.QuestionDAO;
import dao.ResultDAO;
import dao.ResultDetailDAO;
import database.Database;
import entity.Answer;
import entity.Question;
import entity.Result;
import entity.ResultDetail;

import javax.naming.spi.DirStateFactory;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Quiz extends JFrame {
    private static Database database = new Database();
    private static List<Question> questionlist = new ArrayList<>();
    private static List<Question> questionQuizz = new ArrayList<>();
    private static List<Answer> answerQuizz = new ArrayList<Answer>();
    private static List<Answer> answerList = new ArrayList<>();
    private static Result result;
    private static int resultId;
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
    public static int countQuestion = 0;



    public Quiz(int quizzId, int userId, String currentTime) {
//        JFrame frame = new JFrame("Quiz");
        addResult(userId, quizzId, currentTime);

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
                String selectedValue = getSelectedAnswerText(buttonGroup);
                int selectedAnswerId = getSelectAnswerId(answerQuizz);
                countQuestion = getCountQuestion(quizzId);
                boolean isCorrect = getIsCorrect(answerQuizz, selectedAnswerId);

                try {
                    ResultDAO rd = new ResultDAO(Database.getConnection());
                    resultId = rd.getResultId(currentTime); // Insert result and get its ID
                    addResultDetail(resultId, questionlist.get(index).getId(), selectedAnswerId, isCorrect);
                } catch (SQLException e) {
                    throw new RuntimeException("Error while adding result details", e);
                }

                answerQuizz.clear();

                if (index < questionQuizz.size()-1) {
                    // Move to the next question
                    index++;
                    int i = 0;

                    indexQuestion.setText("Câu " + (index + 1) + ":");
                    jTextQuestion.setText(questionQuizz.get(index).getContent());

                    // Populate answer options for the next question
                    for (Answer a : answerList) {
                        if (a.getQuestionId() == questionQuizz.get(index).getId()) {
                            answerQuizz.add(a);
                        }
                    }

                    // Set answer options on the UI
                    for (Answer a : answerQuizz) {
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

                    // Check if any radio button is selected
                    if (buttonGroup.getSelection() == null) {
                        JOptionPane.showMessageDialog(Quiz.this, "Vui lòng chọn một câu trả lời", "Thông báo", JOptionPane.WARNING_MESSAGE);
                        return; // Exit the actionPerformed method if no radio button is selected
                    }

                    buttonGroup.clearSelection(); // Clear the selection of JRadioButtons
                } else {

                    JOptionPane.showMessageDialog(null, "Bạn đã hoàn thành bài thi!"); // Display completion message
                    JOptionPane.showMessageDialog(null, "Số câu đúng: " + getPointQuizz(resultId) + "/" + countQuestion); // Display completion message
                    questionQuizz.clear();
                    dispose();
                }
            }
        });



        try {
            QuestionDAO questionDAO = new QuestionDAO(Database.getConnection());
            AnswerDAO answerDAO = new AnswerDAO(Database.getConnection());
//            List<Answer> answerQuizz = new ArrayList<>();
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
                for (Answer a: answerList){
                    if (a.getQuestionId() == questionQuizz.get(index).getId()){
                        answerQuizz.add(a);
                    }
                }

                jTextQuestion.setText(questionQuizz.get(index).getContent());
                int i = 0;
                for (Answer a : answerQuizz) {
                    if (a.getQuestionId() == questionlist.get(index).getId()) {

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

// Hàm lấy dữ liệu đáp án đã chọn
    private String getSelectedAnswerText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

//    Hàm kiểm tra id của đáp án đã được chọn
    private int getSelectAnswerId(List<Answer> answerQuizz){
        String selectAnswerText = getSelectedAnswerText(buttonGroup);
        int selectedAnswerId = 0;


        for (Answer a: answerQuizz){
            if (a.getContent().equals(selectAnswerText)){
                selectedAnswerId = a.getId();
            }
        }

//        System.out.println(getSelectedAnswerText(buttonGroup));
//        System.out.println(selectedAnswerId);
        return selectedAnswerId;
    }

//    Lấy dữ liệu đúng sai của đáp án
    private boolean getIsCorrect(List<Answer> answerQuizz, int answerId ){
        boolean isTrue = false;
        for (Answer a: answerQuizz){
            if (a.getId() == answerId){
                isTrue = a.isCorrect();
            }
        }
        return isTrue;
    }


//    Chèn dữ liệu vào table Result
    private void addResult(int userId, int quizzId, String time){
        result = new Result(userId, quizzId, time);
        try {
            ResultDAO resultDAO = new ResultDAO(Database.getConnection());
            resultDAO.insertResult(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//    Chèn dữ liệu vào table ResultDetail
    private void addResultDetail(int resultId, int questionId, int answerId, boolean isTrue){
        ResultDetail rd = new ResultDetail(resultId, questionId, answerId, isTrue);
        try{
            ResultDetailDAO resultDetailDAO = new ResultDetailDAO(Database.getConnection());
            resultDetailDAO.insertResultDetails(rd);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getPointQuizz(int resultId){
        List<ResultDetail> resultDetails = new ArrayList<>();
        int sum = 0;
        try {
            ResultDetailDAO resultDetailDAO = new ResultDetailDAO(Database.getConnection());
            resultDetails = resultDetailDAO.getResultDetail(resultId);
            for (ResultDetail rd: resultDetails){
                if (rd.isTrue()){
                    sum++;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sum;
    }

    private int getCountQuestion(int quizzId){
        int count = 0;
        try {
            QuestionDAO questionDAO = new QuestionDAO(Database.getConnection());
            count = questionDAO.getCountQuestion(quizzId);
            System.out.println(count);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
    public static void main(String[] args) throws SQLException {
        Quiz quiz = new Quiz(1,1, CurrentTime.getCurrentTime());

    }


}
