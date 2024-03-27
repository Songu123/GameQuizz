package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminFrame extends JFrame {
    public AdminFrame() {
        setTitle("Admin Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JButton btnViewQuizzes = new JButton("Xem Danh Sách Đề");
        JButton btnAddQuiz = new JButton("Thêm Đề");
        JButton btnViewUsers = new JButton("Xem Danh Sách Người Dùng");
        JButton btnViewResults = new JButton("Xem Kết Quả Người Dùng");

        btnViewQuizzes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý khi người dùng nhấn nút "Xem Danh Sách Đề"
                // Code để hiển thị danh sách các đề
            }
        });

        btnAddQuiz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý khi người dùng nhấn nút "Thêm Đề"
                // Code để mở cửa sổ hoặc giao diện để thêm đề mới
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

    public static void main(String[] args) {
        // Tạo một đối tượng AdminFrame khi ứng dụng được khởi chạy
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminFrame();
            }
        });
    }
}
