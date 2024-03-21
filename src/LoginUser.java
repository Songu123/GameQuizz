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

public class LoginUser extends JFrame {

//    public static Database database = new Database();
    public static List<User> userList = new ArrayList<>();
    private JTextField jUsername;
    private JPasswordField jPassword;
    private JLabel username;
    private JLabel password;
    private JPanel containerLogin;
    private JButton logInButton;

    public LoginUser(){
        setContentPane(containerLogin);
        setTitle("Log In");
        setSize(300,250);
        setLocationRelativeTo(null);
        setVisible(true);
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String username = jUsername.getText();
                String password = new String(jPassword.getPassword()); // Use getPassword() to retrieve the password securely
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
    public static void main(String[] args) {
        LoginUser login = new LoginUser();
    }
}
