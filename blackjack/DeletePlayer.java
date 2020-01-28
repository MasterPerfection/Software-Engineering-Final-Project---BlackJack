//Abdoul Bachir Zabeirou Oumarou
//Alibek Zeinolla
//Bea Uwamahoro
//Manuel Peralta
//BlackJack Project

package blackjack;

import java.util.List;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class DeletePlayer extends JDialog implements ActionListener
{
  
	private static final long serialVersionUID = 1L;

	private JComboBox usernamesField;
    
    public DeletePlayer(final JDialog dialog) {
        super(dialog, true);
        this.setSize(300, 100);
        this.setLocationRelativeTo(dialog);
        this.setLayout(new GridLayout(2, 2));
        this.setTitle("Black Jack Delete Player");
        this.add(new JLabel("Username"));
        this.add(this.usernamesField = new JComboBox());
        this.add(new JLabel());
        final JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        this.add(deleteButton);
        final List<Account> players = Utility.getPlayers();
        for (int i = 0; i < players.size(); ++i) {
            this.usernamesField.addItem(players.get(i).getUsername());
        }
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (this.usernamesField.getSelectedIndex() == -1) {
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the player?", "Confirmation", 0) != 0) {
            return;
        }
        final Account account = Utility.findPlayer(this.usernamesField.getSelectedItem().toString());
        Utility.getPlayers().remove(account);
        JOptionPane.showMessageDialog(this, "Player deleted.", "Notification", 1);
        this.setVisible(false);
    }
}
