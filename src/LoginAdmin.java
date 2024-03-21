import dao.UserDAO;
import database.Database;
import entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginAdmin extends JFrame {

    private static List<User> userList = new ArrayList<>();
    private JTextField usernameAdmin;
    private JPasswordField passwordAdmin;
    private JPanel containerAdmin;
    private JButton logInButton;

    public LoginAdmin(){
        setContentPane(containerAdmin);
        setTitle("Login Admin");
        setSize(300,250);
        setLocationRelativeTo(null);
        setVisible(true);
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String username = usernameAdmin.getText();
                String password = new String(passwordAdmin.getPassword()); // Use getPassword() to retrieve the password securely
                boolean loginSuccess = checkLogin(username, password);
                if (loginSuccess) dispose();
            }
        });
    }

    public static boolean checkLogin(String username, String password){
        try {
            boolean flag = false;
            Connection conn = Database.getConnection();
            UserDAO userDAO = new UserDAO(conn);
            userList = userDAO.getAllUsers();
            for (User u : userList){
                if (u.getName().equals(username) && u.getPassword().equals(password)){
                    QuizTopicSelection quizTopicSelection = new QuizTopicSelection();
                    quizTopicSelection.setVisible(true);
                    flag = true;
                }else {
                    JOptionPane.showMessageDialog(null, "Incorrect username or password. Please try again.");
                }
            }
            return flag;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
