package gui;

import dao.QuizzDAO;
import database.Database;
import entity.ListQuizz;
import entity.Quizz;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowListQuizz {

    private List<ListQuizz> listQuizzes = new ArrayList<>(); // Initialize here

    public ShowListQuizz(int userId){
        createAndShowGUI(userId);
    }

    private void createAndShowGUI(int userId) {
        JFrame frame = new JFrame("Quiz Table Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Center the frame on screen

//         Create table model
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Số thứ tự", "Tên Quizz", "Tên người tạo"}, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        table.setAutoCreateRowSorter(true);
        table.setGridColor(Color.BLACK);
        table.setBorder(new LineBorder(Color.BLACK));

        // Fetch quiz data and populate table
        getQuizzList();

        int index = 1;
        for (ListQuizz q : listQuizzes) {
            model.addRow(new Object[]{
                    index++,
                    q.getNameQuizz(), q.getNameAuthor()
            });
        }

        table.setAutoCreateRowSorter(true);
        table.setGridColor(Color.BLACK);
        table.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Set border

        // Increase font size for column headers
        Font headerFont = table.getTableHeader().getFont();
        Font newHeaderFont = headerFont.deriveFont(Font.BOLD, 14f); // Set font size to 14
        table.getTableHeader().setFont(newHeaderFont);

        // Increase font size for table values
        Font valueFont = table.getFont();
        Font newValueFont = valueFont.deriveFont(14f); // Set font size to 14
        table.setFont(newValueFont);

        // Center align column headers
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton exitButton = new JButton("Thoát");
        buttonPanel.add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the frame
                AdminFrame adminFrame = new AdminFrame(userId);
            }
        });


        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);


        // Display the frame
        frame.setVisible(true);
    }

    // Lấy danh sách đề quizz
    public List<ListQuizz> getQuizzList() {
        List<ListQuizz> list = new ArrayList<>(); // Initialize here
        List<Quizz> quizzes = null;
        try {
            Connection connection = Database.getConnection();
            QuizzDAO quizzDAO = new QuizzDAO(connection);

            quizzes = quizzDAO.getAllQuizzes();

            for (Quizz quizz : quizzes) {
                ListQuizz listQuizz = new ListQuizz(quizz.getName(), getNameAuthor(quizz.getUserId()));
                list.add(listQuizz);
            }
            this.listQuizzes = list; // Assign to instance variable
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCellsAlignment(JTable table, int alignment) {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(alignment);

        TableModel tableModel = table.getModel();

        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
            table.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
        }
    }

    public String getNameAuthor(int userId) {
        String sql = "Select * from users where id = ?";
        String nameAuthor = "";
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nameAuthor = rs.getString("username");
            }
            return nameAuthor;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
//        ShowListQuizz showListQuizz = new ShowListQuizz(1);
    }
}
