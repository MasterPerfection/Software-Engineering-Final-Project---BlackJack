//Abdoul Bachir Zabeirou Oumarou
//Alibek Zeinolla
//Bea Uwamahoro
//Manuel Peralta
//BlackJack Project

package blackjack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ServerSettings extends JDialog implements ActionListener
{
    private AdminPanel parentWindow;
    private JTextField portField;
    private JTextField playersField;
    
    public ServerSettings(final AdminPanel parentWindow) {
        super(parentWindow, true);
        this.setTitle("Server Settings");
        this.setSize(300, 150);
        this.setLayout(new GridLayout(3, 2));
        this.setLocationRelativeTo(null);
        this.parentWindow = parentWindow;
        this.portField = new JTextField();
        this.playersField = new JTextField();
        this.add(new JLabel("Port"));
        this.add(this.portField);
        this.add(new JLabel("No. of Players"));
        this.add(this.playersField);
        this.portField.setText(parentWindow.getPort() + "");
        this.playersField.setText(parentWindow.getNumberOfPlayers() + "");
        this.add(new JLabel());
        final JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        this.add(saveButton);
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        int port = 0;
        int numberOfPlayers = 0;
        try {
            port = Integer.parseInt(this.portField.getText());
            numberOfPlayers = Integer.parseInt(this.playersField.getText());
        }
        catch (Exception error) {
            JOptionPane.showMessageDialog(this, "Please make sure that the port and number of players are numeric.");
            return;
        }
        if (port <= 0 || numberOfPlayers <= 0) {
            JOptionPane.showMessageDialog(this, "The port and number of players should be greater than 0.");
            return;
        }
        this.parentWindow.setPort(port);
        this.parentWindow.setNumberOfPlayers(numberOfPlayers);
        this.setVisible(false);
    }
}
