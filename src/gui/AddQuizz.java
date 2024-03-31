package gui;

import dao.AnswerDAO;
import dao.QuestionDAO;
import database.Database;
import entity.Answer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class AddQuizz extends JFrame {
    public List<Answer> answerList = new ArrayList<>();
    private JTextField contentQuestion;
    private JTextField contentAnswerA;
    private JTextField contentAnswerB;
    private JTextField contentAnswerC;
    private JTextField contentAnswerD;
    private JButton addNewQuestion;
    private JButton finishCreateQuizz;
    private JLabel questionLabel;
    private JLabel answerA;
    private JLabel answerB;
    private JLabel answerC;
    private JLabel answerD;
    private JRadioButton aRadioButton;
    private JRadioButton bRadioButton;
    private JRadioButton cRadioButton;
    private JRadioButton dRadioButton;
    private JPanel addQuizzPanel;

    private ButtonGroup buttonGroup;

    public AddQuizz(int quizzId){
        setTitle("Create Question");
        setContentPane(addQuizzPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(aRadioButton);
        buttonGroup.add(bRadioButton);
        buttonGroup.add(cRadioButton);
        buttonGroup.add(dRadioButton);

        addNewQuestion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int questionId = 0;

                String textQuestion = contentQuestion.getText();

                String answerA = contentAnswerA.getText();
                String answerB = contentAnswerB.getText();
                String answerC = contentAnswerC.getText();
                String answerD = contentAnswerD.getText();

                    String answerCorrect = getSelectedButtonText(buttonGroup);
                    questionId = addQuestion(textQuestion, quizzId);
                    addListAnswer(questionId, answerA, answerB, answerC, answerD, answerCorrect);
                resetFields();
            }
        });

        finishCreateQuizz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                    JOptionPane.showMessageDialog(null, "Đã hoàn thành đề!");
                    dispose();
            }
        });


    }

    private String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }
        JOptionPane.showMessageDialog(null, "Bạn chưa chọn đáp án chính xác!");
        return null; // Return null if no button is selected
    }


    public void addListAnswer(int questionId, String answerA, String answerB, String answerC, String answerD, String answerCorrect) {
        for (int i = 0; i < 4; i++) {
            boolean isCorrect = false;
            String answerContent = "";

            switch (i) {
                case 0:
                    if (answerCorrect.equals("A")) {
                        isCorrect = true;
                    }
                    answerContent = answerA;
                    break;
                case 1:
                    if (answerCorrect.equals("B")) {
                        isCorrect = true;
                    }
                    answerContent = answerB;
                    break;
                case 2:
                    if (answerCorrect.equals("C")) {
                        isCorrect = true;
                    }
                    answerContent = answerC;
                    break;
                case 3:
                    if (answerCorrect.equals("D")) {
                        isCorrect = true;
                    }
                    answerContent = answerD;
                    break;
                default:
                    break;
            }

            Answer answer = new Answer(answerContent, questionId, isCorrect);
            answerList.add(answer);
        }

        for (Answer a: answerList){
            try {
                AnswerDAO answerDAO = new AnswerDAO(Database.getConnection());
                answerDAO.insertAnswer(a.getContent(), a.getQuestionId(), a.isCorrect());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int addQuestion(String content, int quizzId){
        int questionId = 0;
        try {
            QuestionDAO questionDAO = new QuestionDAO(Database.getConnection());
            questionId = questionDAO.insertQuestion(content, quizzId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return questionId;
    }

    private void resetFields() {
        // Clear text of JTextField
        contentQuestion.setText("");
        AddQuizz.this.contentAnswerA.setText("");
        AddQuizz.this.contentAnswerB.setText("");
        AddQuizz.this.contentAnswerC.setText("");
        AddQuizz.this.contentAnswerD.setText("");

        // Deselect all radio buttons in ButtonGroup
        buttonGroup.clearSelection();
    }


//    public static void main(String[] args) {
////        AddQuizz addQuizz = new AddQuizz(1);
//    }
}
