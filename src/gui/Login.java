package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class Login extends JFrame {
    private JRadioButton adminRadioButton;
    private JRadioButton userRadioButton;
    private ButtonGroup buttonGroup = new ButtonGroup();
    private JPanel chooseLogin;
    private JButton OKButton;

    public Login(){
        setContentPane(chooseLogin);
        setTitle("Login");
        setSize(300,250);
        setLocationRelativeTo(null);
        setVisible(true);

        buttonGroup.add(adminRadioButton);
        buttonGroup.add(userRadioButton);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String contentButton = null;
                boolean buttonSelected = false;

                for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
                    AbstractButton button = buttons.nextElement();

                    if (button.isSelected()) {
                        buttonSelected = true;
                        contentButton = button.getText();
                        if (contentButton.equals("Admin")) {
                            // Handle action for Admin selected
                            LoginAdmin loginAdmin = new LoginAdmin();
                        } else if (contentButton.equals("User")) {
                            // Handle action for User selected
                            LoginUser loginUser = new LoginUser();
                        }
                    }
                }

                if (!buttonSelected) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn!"); // Display completion message
                }

            }
        });
    }

    public static void main(String[] args) {
        Login login = new Login();
    }
}
