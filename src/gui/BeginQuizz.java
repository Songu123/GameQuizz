package gui;

import common.CurrentTime;
import dao.AnswerDAO;
import dao.QuestionDAO;
import dao.ResultDAO;
import dao.ResultDetailDAO;
import database.Database;
import entity.Answer;
import entity.Question;
import entity.Result;
import entity.ResultDetail;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class BeginQuizz extends JFrame {
    private static List<Question> questionlist = new ArrayList<>();
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



    public BeginQuizz(int quizzId, int userId, String currentTime) {
//        chèn dữ liệu vào table Result
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
                if (buttonGroup.getSelection() == null) {
                    JOptionPane.showMessageDialog(BeginQuizz.this, "Vui lòng chọn một câu trả lời", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return; // Exit the actionPerformed method if no radio button is selected
                }

                String selectedValue = getSelectedAnswerText(buttonGroup);
                System.out.println(selectedValue);
                int selectedAnswerId = getSelectAnswerId(answerList);
                countQuestion = getCountQuestion(quizzId);
                boolean isCorrect = getIsCorrect(answerList, selectedAnswerId);

                try {
                    ResultDAO rd = new ResultDAO(Database.getConnection());
                    resultId = rd.getResultId(currentTime); // Insert result and get its ID
                    addResultDetail(resultId, questionlist.get(index).getId(), selectedAnswerId, isCorrect);
                } catch (SQLException e) {
                    throw new RuntimeException("Error while adding result details", e);
                }

                answerList.clear();

                if (index < questionlist.size()-1) {
                    // Move to the next question
                    index++;
                    int i = 0;
                    int questionId = questionlist.get(index).getId();
                    indexQuestion.setText("Câu " + (index + 1) + ":");
                    jTextQuestion.setText(questionlist.get(index).getContent());

                    answerList = getAnswerQuizz(questionId);

                    // Set answer options on the UI
                    for (Answer a : answerList) {
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

                    buttonGroup.clearSelection(); // Clear the selection of JRadioButtons
                } else {
                    JOptionPane.showMessageDialog(null, "Bạn đã hoàn thành bài thi!"); // Display completion message
                    dispose();
                    JOptionPane.showMessageDialog(null, "Số câu đúng: " + getPointQuizz(resultId) + "/" + countQuestion); // Display completion message
                    int result = showConfirmDialog(getPointQuizz(resultId), countQuestion);
                    checkCofirmDialog(userId, result, quizzId,resultId);
                    questionlist.clear();
                    answerList.clear();
//                    Tắt jframe và trở lại chọn đề
                }
            }
        });


        // Kiểm tra nếu danh sách câu hỏi không null và có phần tử
        questionlist = getQuestionQuizz(quizzId);
        if (questionlist != null && !questionlist.isEmpty()) {
            System.out.println(questionlist);
            // Hiển thị câu hỏi đầu tiên (nếu có)
            if (!questionlist.isEmpty()) {
                index = 0;
                int questionId = questionlist.get(index).getId();
                indexQuestion.setText("Câu " + (index + 1) + ":");

                answerList = getAnswerQuizz(questionId);
                System.out.println(answerList);
                // Lấy danh sách các câu trả lời

                // Kiểm tra nếu danh sách câu trả lời không null và có phần tử
                if (answerList != null && !answerList.isEmpty()) {
                    // Lặp qua danh sách câu trả lời và lọc ra các câu trả lời liên quan đến câu hỏi hiện tại
                    // Hiển thị nội dung câu hỏi và các phương án trả lời
                    jTextQuestion.setText(questionlist.get(index).getContent());

                    int i = 0;
                    for (Answer a : answerList) {
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
                        }
                        i++;
                    }
                } else {
                    System.out.println("No answers found for questions in the quiz.");
                }
            } else {
                System.out.println("No questions found for the quiz.");
            }
        } else {
            System.out.println("No questions found in the database.");
        }

        buttonGroup.clearSelection(); // Clear the selection of JRadioButtons
    }

//    Lấy danh sách câu hỏi từ đề thi
public List<Question> getQuestionQuizz(int quizzId) {
    List<Question> questionList = new ArrayList<>();
    try {
        QuestionDAO questionDAO = new QuestionDAO( Database.getConnection());
        for (Question q: questionDAO.getAllQuestions()){
            if (q.getQuizzes_id() == quizzId){
                questionList.add(q);
            }
        }

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    return questionList;
}


    public List<Answer> getAnswerQuizz(int questionId){
        List<Answer> answerList = new ArrayList<>();
        try {
            AnswerDAO answerDAO = new AnswerDAO(Database.getConnection());
            System.out.println(answerDAO.getAllAnswers());
            for (Answer a: answerDAO.getAllAnswers()){
                if (a.getQuestionId() == questionId){
                    answerList.add(a);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return answerList;
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

//    Lấy số câu hỏi trong đề
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

    public static int showConfirmDialog(int answerCorrect, int sumQuestion) {
        String message = "Số câu đúng: " + answerCorrect + "/" + sumQuestion + "\nBạn có muốn xem lại bài thi không?";
        return JOptionPane.showConfirmDialog(null, message, "Xác nhận xem lại bài thi", JOptionPane.YES_NO_OPTION);
    }

    public static void checkCofirmDialog(int userId, int result, int quizId, int resultId){
        if (result == JOptionPane.YES_OPTION) {
            System.out.println("Bạn đã chọn Yes để xem lại bài thi.");
            ShowAnswer showAnswer = new ShowAnswer(userId, quizId, resultId);

        } else {
            System.out.println("Bạn đã chọn No hoặc đã đóng hộp thoại.");
            QuizTopicSelection quizTopicSelection = new QuizTopicSelection(userId);

        }
    }
//    public static void main(String[] args) throws SQLException {
//        BeginQuizz quiz = new BeginQuizz(1,1, CurrentTime.getCurrentTime());
//    }


}
