//Abdoul Bachir Zabeirou Oumarou
//Alibek Zeinolla
//Bea Uwamahoro
//Manuel Peralta
//BlackJack Project

package blackjack;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class CreatePlayer extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
    private JTextField passwordField;
    private JTextField chipsField;
    
    public CreatePlayer(final JDialog dialog) {
        super(dialog, true);
        this.setTitle("Black Jack Create Player");
        this.setLayout(new GridLayout(4, 2));
        this.setSize(300, 200);
        this.setLocationRelativeTo(dialog);
        this.usernameField = new JTextField();
        this.add(new JLabel("Username"));
        this.add(this.usernameField);
        this.passwordField = new JTextField();
        this.add(new JLabel("Password"));
        this.add(this.passwordField);
        this.chipsField = new JTextField();
        this.add(new JLabel("Chips"));
        this.add(this.chipsField);
        this.add(new JLabel());
        final JButton addButton = new JButton("Add");
        addButton.addActionListener(this);
        this.add(addButton);
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        final String username = this.usernameField.getText().trim();
        final String password = this.passwordField.getText().trim();
        final String tempChips = this.chipsField.getText().trim();
        if (username.isEmpty() || password.isEmpty() || tempChips.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Provide a username, password, and chips.", "Notification", 0);
            return;
        }
        int chips = 0;
        try {
            chips = Integer.parseInt(tempChips);
            if (chips < 0) {
                JOptionPane.showMessageDialog(this, "Chips should be greater than or equal to 0.", "Notification", 0);
                return;
            }
        }
        catch (Exception error) {
            JOptionPane.showMessageDialog(this, "The chips should be numeric.", "Notification", 0);
            return;
        }
        if (Utility.findPlayer(username) != null) {
            JOptionPane.showMessageDialog(this, "The username is taken by another player.", "Notification", 0);
            return;
        }
        final Player player = new Player(username, Utility.hashPassword(password), chips);
        Utility.getPlayers().add(player);
        JOptionPane.showMessageDialog(this, "The player has been registered.", "Notification", 1);
        this.setVisible(false);
    }
}
