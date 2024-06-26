package gui;

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
    private JCheckBox showPasswordCheckBox;

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

        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (showPasswordCheckBox.isSelected()){
                    jPassword.setEchoChar((char)0);
                }else {
                    jPassword.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
                }
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
                    UserFrame userFrame = new UserFrame(u.getId());
                    flag = true;

                }
            }
            if (!flag){
                JOptionPane.showMessageDialog(null, "Username hoặc mật khẩu không chính xác! Vui lòng nhập lại!");
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
