package gui;

import com.mysql.cj.xdevapi.PreparableStatement;
import dao.QuizzDAO;
import dao.ResultDAO;
import database.Database;
import entity.ListQuizz;
import entity.Quizz;
import entity.Result;
import entity.ResultUser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowResultUser {
    private List<String> quizName;
    private List<Integer> quizzId;
    private List<ResultUser> resultUsers = new ArrayList<>();
    private JFrame jFrame;

    public ShowResultUser(int adminId) {
        jFrame = new JFrame("KẾT QUẢ NGƯỜI DÙNG");
        jFrame.setSize(800, 600);
        jFrame.setLocationRelativeTo(null);

        jFrame.setLayout(new BorderLayout());
        JPanel panelTop = new JPanel();

        quizName = getQuizzName();
        quizzId = getQuizzId();

        JLabel jLabel = new JLabel("Chọn đề:");
        jLabel.setBounds(50, 50, 80, 20);
        panelTop.add(jLabel);

//        Lựa chọn đề
        JComboBox<String> cb = new JComboBox<>(quizName.toArray(new String[0]));
        cb.setBounds(140, 50, 150, 20);
        panelTop.add(cb);

//        Nút chọn đề
        JButton btnSelect = new JButton("Select");
        btnSelect.setBounds(300, 50, 80, 20);
        panelTop.add(btnSelect);

        JPanel panelCenter = new JPanel();
        panelCenter.setBounds(50, 100, 700, 400);
        panelCenter.setLayout(new BorderLayout());

        String[] columnNames = {"STT", "Username", "Đáp án đúng", "Số câu hỏi", "Ngày thi"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panelCenter.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel();
        JButton btnCancel = new JButton("Thoát");
        btnCancel.setBounds(300, 50, 80, 20);
        panelBottom.add(btnCancel);


        jFrame.add(panelTop, BorderLayout.NORTH);
        jFrame.add(panelCenter, BorderLayout.CENTER);
        jFrame.add(panelBottom, BorderLayout.SOUTH);
        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int selecIndex = cb.getSelectedIndex();
                int id = quizzId.get(selecIndex);
                System.out.println(id);

                System.out.println(getResultUsers(id));
                displayResults(getResultUsers(id), model);
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jFrame.dispose();
                AdminFrame adminFrame = new AdminFrame(adminId);
            }
        });

        jFrame.setVisible(true);
    }

    // Get all quizzes
    public List<Quizz> getAllQuizz() {
        List<Quizz> quizzList;
        try {
            QuizzDAO quizzDAO = new QuizzDAO(Database.getConnection());
            quizzList = quizzDAO.getAllQuizzes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quizzList;
    }

    // Get the names of all quizzes
    public List<String> getQuizzName() {
        List<String> quizzName = new ArrayList<>();
        for (Quizz q : getAllQuizz()) {
            quizzName.add(q.getName());
        }
        return quizzName;
    }

    // Get all quiz IDs
    public List<Integer> getQuizzId() {
        List<Integer> quizzId = new ArrayList<>();
        for (Quizz q : getAllQuizz()) {
            quizzId.add(q.getId());
        }
        return quizzId;
    }

    public List<ResultUser> getResultUsers(int quizzId) {
        List<ResultUser> listResult = new ArrayList<>();
        String sql = "SELECT " +
                "    username, " +
                "    SUM(IF(rd.is_true = 1, 1, 0)) as quantity_answer, " +
                "    COUNT(q.quizz_id) as quantity_question, " +
                "    r.`time` " +
                "FROM " +
                "    users u " +
                "    INNER JOIN results r ON u.id = r.user_id " +
                "    INNER JOIN result_details rd ON rd.result_id = r.id " +
                "    INNER JOIN questions q ON q.id = rd.question_id " +
                "WHERE " +
                "    r.quiz_id = ?  " +
                "GROUP BY " +
                "    username, r.`time`;";
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, quizzId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                int answerCorrect = rs.getInt("quantity_answer");
                int countQuestion = rs.getInt("quantity_question");
                String date = rs.getString("time");
                ResultUser resultUser = new ResultUser(username, answerCorrect, countQuestion, date);
                listResult.add(resultUser);
            }
            connection.close(); // Đóng kết nối sau khi sử dụng xong
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listResult;
    }


    // Display results for a selected quiz

    private void displayResults(List<ResultUser> resultUsers, DefaultTableModel model) {
        // Xóa tất cả các hàng hiện có trong model
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }

        if (resultUsers.size() == 0){
            JOptionPane.showMessageDialog(null, "Đề thi này chưa có ai thi!");
        }
        // Thêm từng hàng mới từ danh sách resultUsers vào model
        for (int i = 0; i < resultUsers.size(); i++) {
            ResultUser ru = resultUsers.get(i);
            model.addRow(new Object[]{
                    i + 1, // STT được tính từ 1
                    ru.getUsername(),
                    ru.getQuantityCorrect(),
                    ru.getQuantityQuestion(),
                    ru.getDate()
            });
        }
    }
}
