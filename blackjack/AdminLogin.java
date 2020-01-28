//Abdoul Bachir Zabeirou Oumarou
//Alibek Zeinolla
//Bea Uwamahoro
//Manuel Peralta
//BlackJack Project

package blackjack;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class AdminLogin extends JFrame implements ActionListener, WindowListener
{
    private JPasswordField passwordField;
    
    public AdminLogin() {
        this.setSize(300, 100);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
        this.setTitle("Black Jack Admin");
        this.setLayout(new GridLayout(2, 2));
        this.add(new JLabel("Password"));
        this.add(this.passwordField = new JPasswordField());
        this.add(new JLabel());
        final JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        this.add(loginButton);
        Utility.initializeAccounts();
        this.addWindowListener(this);
        
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        final String password = Utility.hashPassword(this.passwordField.getText().trim());
        if (!password.equals(Utility.getAdministrator().getPassword())) {
            JOptionPane.showMessageDialog(this, "The password is invalid.", "Notification", 0);
            return;
        }
        new AdminPanel(this).setVisible(true);
    }
    
    @Override
    public void windowClosing(final WindowEvent e) {
        Utility.saveAccounts();
    }
      
    @Override
    public void windowOpened(final WindowEvent e) {
    }
    
    @Override
    public void windowClosed(final WindowEvent e) {
    }
    
    @Override
    public void windowIconified(final WindowEvent e) {
    }
    
    @Override
    public void windowDeiconified(final WindowEvent e) {
    }
    
    @Override
    public void windowActivated(final WindowEvent e) {
    }
    
    @Override
    public void windowDeactivated(final WindowEvent e) {
    }
    
    public static void main(String[] args) {
        new AdminLogin();
    }
}
