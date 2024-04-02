package gui;

import dao.UserDAO;
import database.Database;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ShowUserList {
    private int id = 0;
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;

    public ShowUserList(int userId) {
        initializeUI(userId);
        id = userId;
    }

    private void initializeUI(int userId) {
        frame = new JFrame("Bảng danh sách người dùng");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);

        createTable();
        createCancelButton();

        frame.setVisible(true);
    }

    private void createTable() {
        String[] columns = {"STT", "Username", "Email"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);

        table.setGridColor(Color.BLACK);
        table.setShowGrid(true);

        Font headerFont = table.getTableHeader().getFont();
        Font newHeaderFont = headerFont.deriveFont(Font.BOLD, 14f); // Set font size to 14
        table.getTableHeader().setFont(newHeaderFont);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        populateTable();
    }

    private void populateTable() {
        List<User> userList = getListUser();
        int index = 1;
        for (User u : userList) {
            Object[] row = {index, u.getName(), u.getPassword()};
            model.addRow(row);
            index++;
        }
    }

    private void createCancelButton() {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                AdminFrame adminFrame = new AdminFrame(id);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(cancelButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    private List<User> getListUser() {
        List<User> users = null;
        try {
            UserDAO userDAO = new UserDAO(Database.getConnection());
            users = userDAO.getAllUsers();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Không thể truy cập cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return users;
    }

//    public static void main(String[] args) {
//        new ShowUserList();
//    }
}
