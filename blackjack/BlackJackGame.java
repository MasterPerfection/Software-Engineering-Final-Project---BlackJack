//Abdoul Bachir Zabeirou Oumarou
//Alibek Zeinolla
//Bea Uwamahoro
//Manuel Peralta
//BlackJack Project

package blackjack;


import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.net.Socket;


public class BlackJackGame extends JDialog implements ActionListener, Runnable
{
	private static final long serialVersionUID = 1L;
	private Socket socket;
    private PlayerPanel dealerPanel;
    private ArrayList<PlayerPanel> playerPanels;
    private JLabel waitingLabel;
    private String username;
    
    public BlackJackGame(final PlayerLogin login, final Socket socket, final String username) {
        super(login, true);
        this.setTitle("Black Jack: " + username);
        this.setLayout(new FlowLayout());
        this.setSize(400, 50);
        this.setLocationRelativeTo(login);
        this.add("Center", this.waitingLabel = new JLabel("Waiting for other players..."));
        this.playerPanels = new ArrayList<PlayerPanel>();
        this.socket = socket;
        this.username = username;
        final Thread thread = new Thread(this);
        thread.start();
    }
    
    private PlayerPanel findPlayerPanel(final String name) {
        for (int i = 0; i < this.playerPanels.size(); ++i) {
            if (this.playerPanels.get(i).getName().equals(name)) {
                return this.playerPanels.get(i);
            }
        }
        return null;
    }
    
    @Override
    public void run() {
        try {
            final BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            final PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);
            while (true) {
                final String command = br.readLine();
                if (command.equals("START GAME")) {
                    this.remove(this.waitingLabel);
                    final int numberOfPlayers = Integer.parseInt(br.readLine());
                    this.setSize(800, 130 * (numberOfPlayers + 1));
                    this.setLayout(new GridLayout(numberOfPlayers + 1, 1));
                    this.dealerPanel = new PlayerPanel("Dealer");
                    this.playerPanels.add(this.dealerPanel);
                    this.add(this.dealerPanel);
                    for (int i = 0; i < numberOfPlayers; ++i) {
                        final PlayerPanel playerPanel = new PlayerPanel(br.readLine());
                        this.playerPanels.add(playerPanel);
                        this.add(playerPanel);
                    }
                    this.repaint();
                    this.revalidate();
                }
                else if (command.equals("GAME OVER")) {
                    this.dealerPanel.openClosedCard();
                    for (int j = 0; j < this.playerPanels.size(); ++j) {
                        this.playerPanels.get(j).openClosedCard();
                    }
                }
                else if (command.equals("DISPLAY CLOSED CARD")) {
                    final String name = br.readLine();
                    final String closedCardPath = br.readLine();
                    final JLabel cardLabel = new JLabel();
                    cardLabel.setIcon(new ImageIcon("others/closed.png"));
                    if (name.equals("DEALER")) {
                        this.dealerPanel.add(cardLabel);
                        this.dealerPanel.setClosedCardPath(cardLabel, closedCardPath);
                    }
                    else {
                        final PlayerPanel playerPanel2 = this.findPlayerPanel(name);
                        playerPanel2.add(cardLabel);
                        playerPanel2.setClosedCardPath(cardLabel, closedCardPath);
                    }
                    this.repaint();
                    this.revalidate();
                }
                else if (command.equals("DISPLAY OPEN CARD")) {
                    final String name = br.readLine();
                    final String cardPath = br.readLine();
                    final JLabel cardLabel = new JLabel();
                    cardLabel.setIcon(new ImageIcon(cardPath));
                    if (name.equals("DEALER")) {
                        this.dealerPanel.add(cardLabel);
                    }
                    else {
                        final PlayerPanel playerPanel2 = this.findPlayerPanel(name);
                        playerPanel2.add(cardLabel);
                    }
                    this.repaint();
                    this.revalidate();
                }
                else if (command.equals("DISPLAY OPEN CARD ONLY TO")) {
                    final String name = br.readLine();
                    final String cardPath = br.readLine();
                    if (!this.username.equals(name)) {
                        continue;
                    }
                    final JLabel cardLabel = new JLabel();
                    cardLabel.setIcon(new ImageIcon(cardPath));
                    final PlayerPanel playerPanel2 = this.findPlayerPanel(name);
                    playerPanel2.add(cardLabel);
                }
                else if (command.equals("DISPLAY SCORE")) {
                    final String name = br.readLine();
                    final String score = br.readLine();
                    if (name.equals("DEALER")) {
                        this.dealerPanel.updateScore(score);
                    }
                    else {
                        final PlayerPanel playerPanel = this.findPlayerPanel(name);
                        playerPanel.updateScore(score);
                    }
                }
                else if (command.equals("NOTIFY TURN")) {
                    final String name = br.readLine();
                    final PlayerPanel playerPanel3 = this.findPlayerPanel(name);
                    playerPanel3.updateStatus("Current Turn");
                    if (!name.equals(this.username)) {
                        continue;
                    }
                    final String[] options = { "Hit", "Stand" };
                    final int option = JOptionPane.showOptionDialog(this, "What would you like to do?", "Your Turn", -1, 3, null, options, options[0]);
                    if (option == 0) {
                        pw.println("Hit");
                    }
                    else {
                        pw.println("Stand");
                    }
                }
                else if (command.equals("NOTIFY FINISHED")) {
                    final String name = br.readLine();
                    final PlayerPanel playerPanel3 = this.findPlayerPanel(name);
                    playerPanel3.updateStatus("Finished");
                }
                else {
                    if (command.equals("NOTIFY RESULT")) {
                        break;
                    }
                    continue;
                }
            }
            JOptionPane.showMessageDialog(this, br.readLine());
            this.socket.close();
            this.setVisible(false);
        }
        catch (Exception error) {
            error.printStackTrace();
            System.exit(0);
        }
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
