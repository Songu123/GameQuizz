package gui;

import entity.Question;
import entity.Answer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddQuizz extends JFrame {
    public List<Answer> answerList = new ArrayList<>();
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton addNewQuestion;
    private JButton finishCreateQuizz;
    private JLabel contentQuestion;
    private JLabel contentAnswerA;
    private JLabel contentAnswerB;
    private JLabel contentAnswerC;
    private JLabel contentAnswerD;
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
                String textQuestion = contentQuestion.getText();

                String answerA = contentAnswerA.getText();
                String answerB = contentAnswerB.getText();
                String answerC = contentAnswerC.getText();
                String answerD = contentAnswerD.getText();

                ButtonModel buttonModel = buttonGroup.getSelection();
                if (buttonModel.isSelected()){
                    String answerCorrect = buttonModel.getActionCommand();
                }else {
                    JOptionPane.showMessageDialog(null, "Bạn chưa chọn đáp án chính xác!");
                }




            }
        });
        finishCreateQuizz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

    public void addListAnswer(int questionId, String answerA, String answerB, String answerC, String answerD, String answerCorrect){
        for (int i = 0; i < 3; i++) {
            switch (i){
                case 0:
                    Answer answer = new Answer(answerA,questionId,)
                    break;
            }
        }
    }

    public static void main(String[] args) {
        AddQuizz addQuizz = new AddQuizz(1);
    }
}
