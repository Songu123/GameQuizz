import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizTopicSelection extends JFrame {

    private JComboBox<String> quizComboBox;
    private JButton startButton;

    public QuizTopicSelection() {
        setTitle("Quiz Topic Selection");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // Create JComboBox with quiz options
        String[] quizzes = {"Math Quiz", "Science Quiz", "History Quiz", "English Quiz"};
        quizComboBox = new JComboBox<>(quizzes);
        add(new JLabel("Select a quiz:"));
        add(quizComboBox);

        // Create Start button
        startButton = new JButton("Start Quiz");
        add(startButton);

        // Add action listener to Start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedQuiz = (String) quizComboBox.getSelectedItem();
                if (selectedQuiz != null) {
                    JOptionPane.showMessageDialog(null, "You selected: " + selectedQuiz);
                    // Add your code here to start the selected quiz
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a quiz");
                }
            }
        });

        setVisible(true);
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
