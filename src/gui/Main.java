package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class Main extends JFrame {
    private JRadioButton adminRadioButton;
    private JRadioButton userRadioButton;
    private ButtonGroup buttonGroup = new ButtonGroup();
    private JPanel chooseLogin;
    private JButton ADMINButton;
    private JButton USERButton;
    private JButton CANCELButton;
    private JButton OKButton;

    public Main(){
        setContentPane(chooseLogin);
        setTitle("Login");
        setSize(469,200);
        setLocationRelativeTo(null);
        setVisible(true);

        ADMINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoginAdmin loginAdmin = new LoginAdmin();
            }
        });
        USERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoginUser loginUser = new LoginUser();
            }
        });
        CANCELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        Main login = new Main();
    }
}
