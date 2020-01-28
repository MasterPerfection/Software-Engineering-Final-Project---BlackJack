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

public class IssueChips extends JDialog implements ActionListener
{
    private JComboBox usernamesField;
    private JTextField chipsField;
    
    public IssueChips(final JDialog parentWindow) {
        super(parentWindow, true);
        this.setTitle("Black Jack Issue Chips");
        this.setSize(300, 150);
        this.setLocationRelativeTo(parentWindow);
        this.setLayout(new GridLayout(3, 2));
        this.add(new JLabel("Username"));
        this.add(this.usernamesField = new JComboBox());
        this.add(new JLabel("Chips"));
        this.add(this.chipsField = new JTextField());
        this.add(new JLabel());
        final JButton addChipsButton = new JButton("Add Chips");
        addChipsButton.addActionListener(this);
        this.add(addChipsButton);
        final List<Account> accounts = Utility.getPlayers();
        for (int i = 0; i < accounts.size(); ++i) {
            this.usernamesField.addItem(accounts.get(i).getUsername());
        }
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (this.usernamesField.getSelectedIndex() == -1) {
            return;
        }
        final Account account = Utility.findPlayer(this.usernamesField.getSelectedItem().toString());
        final String tempChips = this.chipsField.getText().trim();
        int chips = 0;
        try {
            chips = Integer.parseInt(tempChips);
            if (chips < 0) {
                JOptionPane.showMessageDialog(this, "Chips should be greater than 0.", "Notification", 0);
                return;
            }
        }
        catch (Exception error) {
            JOptionPane.showMessageDialog(this, "Please provide a valid value for the chips.", "Notification", 0);
        }
        final Player player = (Player)account;
        player.addChips(chips);
        JOptionPane.showMessageDialog(this, "Chips Issued", "Notification", 1);
        this.setVisible(false);
    }
}
