package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserFrame extends JFrame {
    private JButton btnStart;
    private JPanel quizzPanel;
    private JButton btnHistory;
    private JButton btnExit;

    public UserFrame(int userId){
        setContentPane(quizzPanel);
        setSize(400, 400);
        setTitle("QUIZZ GAME");
        setLocationRelativeTo(null);

        setVisible(true);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                QuizTopicSelection quizTopicSelection = new QuizTopicSelection(userId);
            }
        });
        btnHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                QuizHistoryFrame quizHistoryFrame = new QuizHistoryFrame(userId);
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                Main main = new Main();
            }
        });
    }

    public static void main(String[] args) {
        UserFrame userFrame = new UserFrame(1);
    }
}
