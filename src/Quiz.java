import entity.Answer;
import entity.Question;

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

public class Quiz {
    public static List<Question> list = new ArrayList<>();
    public static List<Answer> answerList = new ArrayList<>();
    private JTextField jTextQuestion;
    private JRadioButton answer1;
    private JRadioButton answer2;
    private JRadioButton answer3;
    private JRadioButton answer4;
    private JButton nextButton;
    private JPanel containerGame;

    public static int index = 0;


    public Quiz(){
        JFrame frame = new JFrame("Quiz");
        frame.setContentPane(containerGame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 450);
        frame.setLocationRelativeTo(null);
//        frame.pack();
        frame.setVisible(true);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

    public static void main(String[] args) throws SQLException {

    }



}
