//Abdoul Bachir Zabeirou Oumarou
//Alibek Zeinolla
//Bea Uwamahoro
//Manuel Peralta
//BlackJack Project

package blackjack;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ResetPassword extends JDialog implements ActionListener
{
    private JComboBox usernamesField;
    private JTextField passwordField;
    
    public ResetPassword(final JDialog parentWindow) {
        super(parentWindow, true);
        this.setTitle("Black Jack Reset Password");
        this.setSize(300, 150);
        this.setLocationRelativeTo(parentWindow);
        this.setLayout(new GridLayout(3, 2));
        this.add(new JLabel("Username"));
        this.add(this.usernamesField = new JComboBox());
        this.add(new JLabel("New Password"));
        this.add(this.passwordField = new JTextField());
        this.add(new JLabel());
        final JButton updatePasswordButton = new JButton("Set Password");
        updatePasswordButton.addActionListener(this);
        this.add(updatePasswordButton);
        final List<Account> accounts = Utility.getPlayers();
        this.usernamesField.addItem("Administrator");
        for (int i = 0; i < accounts.size(); ++i) {
            this.usernamesField.addItem(accounts.get(i).getUsername());
        }
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (this.usernamesField.getSelectedIndex() == -1) {
            return;
        }
        final String newPassword = this.passwordField.getText().trim();
        if (newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please provide a password.", "Notification", 0);
            return;
        }
        Account account = null;
        if (this.usernamesField.getSelectedItem().toString().equals("Administrator")) {
            account = Utility.getAdministrator();
        }
        else {
            account = Utility.findPlayer(this.usernamesField.getSelectedItem().toString());
        }
        account.setPassword(Utility.hashPassword(newPassword));
        JOptionPane.showMessageDialog(this, "Password Updated", "Notification", 1);
        this.setVisible(false);
    }
}
