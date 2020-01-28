//Abdoul Bachir Zabeirou Oumarou
//Alibek Zeinolla
//Bea Uwamahoro
//Manuel Peralta
//BlackJack Project

package blackjack;

import ocsf.client.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class PlayerLogin extends JFrame implements ActionListener
{
    private JTextField usernameField;
    private JTextField portField;
    private JTextField ipField;
    private JPasswordField passwordField;
    
    public PlayerLogin() {
        this.setTitle("Player Login");
        this.setSize(300, 250);
        this.setLayout(new GridLayout(5, 2));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
        this.add(new JLabel("Port"));
        (this.portField = new JTextField()).setText("8080");
        this.add(this.portField);
        this.add(new JLabel("IP Address"));
        (this.ipField = new JTextField()).setText("localhost");
        this.add(this.ipField);
        this.add(new JLabel("Username"));
        this.add(this.usernameField = new JTextField());
        this.add(new JLabel("Password"));
        this.add(this.passwordField = new JPasswordField());
        this.add(new JLabel());
        final JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        this.add(loginButton);
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        int port = 8080;
        try {
            port = Integer.parseInt(this.portField.getText());
        }
        catch (Exception error) {
            JOptionPane.showMessageDialog(this, "Please provide a valid number for the port.");
            return;
        }
        try {
            final Socket socket = new Socket(this.ipField.getText(), port);
            final BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            final PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            pw.println(this.usernameField.getText());
            pw.println(this.passwordField.getText());
            if (br.readLine().equals("FAIL")) {
                JOptionPane.showMessageDialog(this, br.readLine());
                return;
            }
            final int chips = Integer.parseInt(br.readLine());
            int bet = 0;
            while (true) {
                try {
                    bet = Integer.parseInt(JOptionPane.showInputDialog(this, "You have " + chips + " chips. How much to bet?"));
                }
                catch (Exception e2) {
                    JOptionPane.showMessageDialog(this, "Please provide a valid number.");
                    continue;
                }
                if (bet > 0 && bet <= chips) {
                    break;
                }
                JOptionPane.showMessageDialog(this, "Please enter a bet greater than 0 but less than " + chips);
            }
            pw.println(bet);
            new BlackJackGame(this, socket, this.usernameField.getText()).setVisible(true);
        }
        catch (Exception error) {
            error.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to the server.");
        }
    }
    
    public static void main(final String[] args) {
        new PlayerLogin().setVisible(true);
    }
}
