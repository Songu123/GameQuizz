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

public class LoginAdmin extends JFrame {

    private static List<User> userList = new ArrayList<>();
    private JTextField usernameAdmin;
    private JPasswordField passwordAdmin;
    private JPanel containerAdmin;
    private JButton logInButton;
    private JCheckBox showPasswordCheckBox;
    private JLabel loginLabel;

    public LoginAdmin(){
        setContentPane(containerAdmin);
        setTitle("Login Admin");
        setSize(400,300);
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
        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (showPasswordCheckBox.isSelected()){
                    passwordAdmin.setEchoChar((char)0);
                }else {
                    passwordAdmin.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
                }
            }
        });
    }

    public static boolean checkLogin(String username, String password) {
        try {
            boolean flag = false;
            Connection conn = Database.getConnection();
            UserDAO userDAO = new UserDAO(conn);
            userList = userDAO.getAllUsers();

            boolean found = false; // Biến để kiểm tra xem người dùng có được tìm thấy không

            for (User u : userList) {
                // Kiểm tra xem thông tin đăng nhập có khớp với người dùng hiện tại không
                if (u.getName().equals(username) && u.getPassword().equals(password)) {
                    int isTrue = (u.getRole()) ? 1 : 0;

                    if (isTrue == 1) {
                        AdminFrame adminFrame = new AdminFrame(u.getId());
                        flag = true;
                        found = true;
                        break;
                    }
                }
            }

            // Hiển thị thông báo nếu không tìm thấy người dùng hoặc không phù hợp với vai trò admin
            if (!found) {
                JOptionPane.showMessageDialog(null, "Mật khẩu sai! Vui lòng nhập lại!");
            }

            return flag;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginAdmin::new);
    }
}
