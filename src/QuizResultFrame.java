import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizResultFrame extends JFrame {
    private JTextArea resultTextArea;

    public QuizResultFrame(String[] questions, String[] userAnswers, String[] correctAnswers) {
        setTitle("Quiz Result");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        resultTextArea = new JTextArea(20, 40);
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);

        StringBuilder result = new StringBuilder();
        result.append("Quiz Result:\n\n");
        for (int i = 0; i < questions.length; i++) {
            result.append("Question ").append(i + 1).append(": ").append(questions[i]).append("\n");
            result.append("Your Answer: ").append(userAnswers[i]).append("\n");
            result.append("Correct Answer: ").append(correctAnswers[i]).append("\n\n");
        }
        resultTextArea.setText(result.toString());

        getContentPane().add(scrollPane);

        setVisible(true);
    }

    public static void main(String[] args) {
        // Sample data for demonstration
        String[] questions = {"What is the capital of France?", "Who wrote Hamlet?", "What is the chemical symbol for water?"};
        String[] userAnswers = {"Paris", "Shakespeare", "H2O"};
        String[] correctAnswers = {"Paris", "Shakespeare", "H2O"};

        SwingUtilities.invokeLater(() -> new QuizResultFrame(questions, userAnswers, correctAnswers));
    }
}
