//Abdoul Bachir Zabeirou Oumarou
//Alibek Zeinolla
//Bea Uwamahoro
//Manuel Peralta
//BlackJack Project

package blackjack;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JDialog implements ActionListener
{

	private static final long serialVersionUID = 1L;
	private int port;
    private int numberOfPlayers;
    
    public AdminPanel(final JFrame parentWindow) {
        super(parentWindow, true);
        this.setTitle("Black Jack Admin");
        this.setSize(500, 500);
        this.setLocationRelativeTo(parentWindow);
        this.setLayout(new GridLayout(9, 1));
        this.add(new JLabel("Blackjack Admin Main Menu"));
        final JButton createPlayerButton = new JButton("Create Player");
        createPlayerButton.addActionListener(this);
        this.add(createPlayerButton);
        final JButton deletePlayerButton = new JButton("Delete Player");
        deletePlayerButton.addActionListener(this);
        this.add(deletePlayerButton);
        final JButton topUpPlayerButton = new JButton("Top up Player's chips");
        topUpPlayerButton.addActionListener(this);
        this.add(topUpPlayerButton);
        final JButton resetPlayerPasswordButton = new JButton("Reset Player's password");
        resetPlayerPasswordButton.addActionListener(this);
        this.add(resetPlayerPasswordButton);
        final JButton changeAdminPasswordButton = new JButton("Change Admin's password");
        changeAdminPasswordButton.addActionListener(this);
        this.add(changeAdminPasswordButton);
        final JButton serverSettingsButton = new JButton("Configure Server settings");
        serverSettingsButton.addActionListener(this);
        this.add(serverSettingsButton);
        final JButton startServerButton = new JButton("Start Server");
        startServerButton.addActionListener(this);
        this.add(startServerButton);
        final JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);
        this.add(logoutButton);
        this.port = 4444;
        this.numberOfPlayers = 1;
    }
    
    public void setPort(final int port) {
        this.port = port;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public void setNumberOfPlayers(final int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
    
    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        final String command = e.getActionCommand();
        if (command.equalsIgnoreCase("Logout")) {
            this.setVisible(false);
        }
        else if (command.equalsIgnoreCase("Create Player")) {
            new CreatePlayer(this).setVisible(true);
        }
        else if (command.equalsIgnoreCase("Delete Player")) {
            new DeletePlayer(this).setVisible(true);
        }
        else if (command.equalsIgnoreCase("Top up Player's chips")) {
            new IssueChips(this).setVisible(true);
        }
        else if (command.equalsIgnoreCase("Reset Player's password") || command.equalsIgnoreCase("Change Admin's Password")) {
            new ResetPassword(this).setVisible(true);
        }
        else if (command.equalsIgnoreCase("Configure Server settings")) {
            new ServerSettings(this).setVisible(true);
        }
        else if (command.equalsIgnoreCase("Start server")) {
            new GameServer(this).setVisible(true);
        }
    }
}
