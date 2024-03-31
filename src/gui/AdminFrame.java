package gui;

import dao.QuizzDAO;
import database.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdminFrame extends JFrame {
    public static int quizzIdCurrent = 0;
    public AdminFrame(int userId) {
        setTitle("Admin Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JButton btnViewQuizzes = new JButton("Xem Danh Sách Đề");
        JButton btnAddQuiz = new JButton("Thêm Đề");
        JButton btnViewUsers = new JButton("Xem Danh Sách Người Dùng");
        JButton btnViewResults = new JButton("Xem Kết Quả Người Dùng");

        btnViewQuizzes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                TableListQuizz tableListQuizz = new TableListQuizz();
            }
        });

        btnAddQuiz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = quizzTitle();
                addQuizz(title, userId);
                AddQuizz addQuizz = new AddQuizz(quizzIdCurrent);
            }
        });

        btnViewUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý khi người dùng nhấn nút "Xem Danh Sách Người Dùng"
                // Code để hiển thị danh sách người dùng
            }
        });

        btnViewResults.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý khi người dùng nhấn nút "Xem Kết Quả Người Dùng"
                // Code để hiển thị kết quả của người dùng
            }
        });

        panel.add(btnViewQuizzes);
        panel.add(btnAddQuiz);
        panel.add(btnViewUsers);
        panel.add(btnViewResults);

        add(panel);
        setVisible(true);
    }
    private static String quizzTitle() {
        String title = null;
        boolean validInput = false;

        while (!validInput) {
            // Tạo một JTextField để người dùng nhập tên đề
            JTextField textField = new JTextField(20);

            // Tạo một JPanel để chứa các thành phần giao diện
            JPanel panel = new JPanel();
            panel.add(new JLabel("Nhập tên đề:"));
            panel.add(textField);

            // Hiển thị hộp thoại nhập
            int result = JOptionPane.showConfirmDialog(null, panel, "Nhập tên đề", JOptionPane.OK_CANCEL_OPTION);

            // Nếu người dùng nhấn OK
            if (result == JOptionPane.OK_OPTION) {
                title = textField.getText();
                if (!title.isEmpty()) {
                    // Nếu tiêu đề không rỗng, đặt validInput thành true để thoát khỏi vòng lặp
//                    insertQuizz(title, userId);
                    validInput = true;
                } else {
                    // Nếu tiêu đề rỗng, hiển thị thông báo lỗi
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên đề.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Nếu người dùng hủy hoặc đóng hộp thoại, thoát khỏi vòng lặp
                break;
            }
        }
        return title;
    }

    public static void addQuizz(String name, int quizzId){
        try {
            QuizzDAO quizzDAO = new QuizzDAO(Database.getConnection());
            quizzIdCurrent = quizzDAO.insertQuizz(name, quizzId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        // Tạo một đối tượng AdminFrame khi ứng dụng được khởi chạy
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminFrame(1);
            }
        });
    }
}
