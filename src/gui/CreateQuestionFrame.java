package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateQuestionFrame extends JFrame {
    private JTextField questionField;
    private JTextField optionAField;
    private JTextField optionBField;
    private JTextField optionCField;
    private JTextField optionDField;
    private JRadioButton optionARadioButton;
    private JRadioButton optionBRadioButton;
    private JRadioButton optionCRadioButton;
    private JRadioButton optionDRadioButton;

    public CreateQuestionFrame() {
        setTitle("Create Question");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(7, 2, 10, 10));

        JLabel questionLabel = new JLabel("Question:");
        questionField = new JTextField(20);

        JLabel optionALabel = new JLabel("Option A:");
        optionAField = new JTextField(20);

        JLabel optionBLabel = new JLabel("Option B:");
        optionBField = new JTextField(20);

        JLabel optionCLabel = new JLabel("Option C:");
        optionCField = new JTextField(20);

        JLabel optionDLabel = new JLabel("Option D:");
        optionDField = new JTextField(20);

        JLabel correctAnswerLabel = new JLabel("Correct Answer:");
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout());

        ButtonGroup group = new ButtonGroup();
        optionARadioButton = new JRadioButton("A");
        optionBRadioButton = new JRadioButton("B");
        optionCRadioButton = new JRadioButton("C");
        optionDRadioButton = new JRadioButton("D");

        group.add(optionARadioButton);
        group.add(optionBRadioButton);
        group.add(optionCRadioButton);
        group.add(optionDRadioButton);

        radioPanel.add(optionARadioButton);
        radioPanel.add(optionBRadioButton);
        radioPanel.add(optionCRadioButton);
        radioPanel.add(optionDRadioButton);

        JButton createButton = new JButton("Create");
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createQuestion();
            }
        });

        mainPanel.add(questionLabel);
        mainPanel.add(questionField);
        mainPanel.add(optionALabel);
        mainPanel.add(optionAField);
        mainPanel.add(optionBLabel);
        mainPanel.add(optionBField);
        mainPanel.add(optionCLabel);
        mainPanel.add(optionCField);
        mainPanel.add(optionDLabel);
        mainPanel.add(optionDField);
        mainPanel.add(correctAnswerLabel);
        mainPanel.add(radioPanel);
        mainPanel.add(new JLabel());
        mainPanel.add(createButton);

        add(mainPanel);
    }

    private void createQuestion() {
        String question = questionField.getText();
        String optionA = optionAField.getText();
        String optionB = optionBField.getText();
        String optionC = optionCField.getText();
        String optionD = optionDField.getText();
        String correctAnswer = "";

        if (optionARadioButton.isSelected()) {
            correctAnswer = "A";
        } else if (optionBRadioButton.isSelected()) {
            correctAnswer = "B";
        } else if (optionCRadioButton.isSelected()) {
            correctAnswer = "C";
        } else if (optionDRadioButton.isSelected()) {
            correctAnswer = "D";
        }

        // TODO: Thực hiện xử lý với thông tin câu hỏi và đáp án ở đây
        System.out.println("Question: " + question);
        System.out.println("Option A: " + optionA);
        System.out.println("Option B: " + optionB);
        System.out.println("Option C: " + optionC);
        System.out.println("Option D: " + optionD);
        System.out.println("Correct Answer: " + correctAnswer);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CreateQuestionFrame().setVisible(true);
            }
        });
    }
}
