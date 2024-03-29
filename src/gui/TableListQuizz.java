package gui;

import dao.QuizzDAO;
import database.Database;
import entity.Quizz;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;



public class TableListQuizz {

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(TableListQuizz::createAndShowGUI);
//    }
    public TableListQuizz(){
        createAndShowGUI();
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Quiz Table Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Center the frame on screen

        // Create table model
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Số thứ tự", "Tên Quizz", "Tên người tạo"}, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Fetch quiz data and populate table
        List<Quizz> quizzList = getQuizzList();
        int index = 1;
        for (Quizz q : quizzList) {
            model.addRow(new Object[]{
                    index++,
                    q.getName(),
                    q.getUserId()
            });
        }

        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
        table.setFont(font);


        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);


        // Center align column headers
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Make column headers bold
        Font headerFont = table.getTableHeader().getFont();
        table.getTableHeader().setFont(new Font(headerFont.getFontName(), Font.BOLD,16));

        // Add components to content pane
        Container contentPane = frame.getContentPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(cancelButton);

        // Add button panel to content pane
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit the application
                frame.setVisible(false);
            }
        });

        // Display the frame
        frame.setVisible(true);
    }


    public static List<Quizz> getQuizzList() {
        try {
            Connection connection = Database.getConnection();
            QuizzDAO quizzDAO = new QuizzDAO(connection);
            return quizzDAO.getAllQuizzes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
