//Abdoul Bachir Zabeirou Oumarou
//Alibek Zeinolla
//Bea Uwamahoro
//Manuel Peralta
//BlackJack Project

package blackjack;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer extends JDialog implements Runnable
{
    private AdminPanel parentWindow;
    private ArrayList<PlayerConnection> connections;
    
    public GameServer(final AdminPanel parentWindow) {
        super(parentWindow, true);
        this.setTitle("Black Jack Server");
        this.setSize(300, 50 + 50 * parentWindow.getNumberOfPlayers());
        this.setLocationRelativeTo(parentWindow);
        this.setLayout(new GridLayout(1 + parentWindow.getNumberOfPlayers(), 2));
        this.add(new JLabel("Game Server Started"));
        this.add(new JLabel("Listening to port " + parentWindow.getPort()));
        this.parentWindow = parentWindow;
        this.connections = new ArrayList<PlayerConnection>();
        final Thread thread = new Thread(this);
        thread.start();
    }
    
    @Override
    public void run() {
        try {
            final ServerSocket serverSocket = new ServerSocket(this.parentWindow.getPort());
            int connectedClients = 0;
            while (connectedClients < this.parentWindow.getNumberOfPlayers()) {
                final Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                final BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                final PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                final String username = br.readLine();
                final String password = br.readLine();
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);
                final Account account = Utility.findPlayer(username);
                if (account == null) {
                    pw.println("FAIL");
                    pw.println("The account does not exist.");
                    System.out.println("Failed login account does not exist");
                }
                else {
                    final Player player = (Player)account;
                    player.resetCards();
                    if (!player.getPassword().equals(Utility.hashPassword(password))) {
                        pw.println("FAIL");
                        pw.println("The password is invalid.");
                        System.out.println("Failed login password is invalid");
                        System.out.println("Password should be " + player.getPassword());
                    }
                    else if (player.getChips() == 0) {
                        pw.println("FAIL");
                        pw.println("You do not have enough chips to play.");
                        System.out.println("Failed login not enough chips");
                    }
                    else {
                        pw.println("SUCCESS");
                        pw.println(player.getChips());
                        final int betChips = Integer.parseInt(br.readLine());
                        final JTextField statusField = new JTextField();
                        statusField.setText(betChips + " chips placed");
                        final PlayerConnection connection = new PlayerConnection(betChips, player, socket, statusField);
                        this.connections.add(connection);
                        this.add(new JLabel(player.getUsername()));
                        this.add(statusField);
                        this.repaint();
                        this.validate();
                        ++connectedClients;
                    }
                }
            }
            System.out.println("All players complete, game started");
            final Deck deck = new Deck();
            final Player dealer = new Player("", "", 0);
            for (int i = 0; i < this.connections.size(); ++i) {
                final PlayerConnection connection2 = this.connections.get(i);
                final PrintWriter pw2 = new PrintWriter(new OutputStreamWriter(connection2.getSocket().getOutputStream()), true);
                pw2.println("START GAME");
                pw2.println(this.connections.size());
                for (int j = 0; j < this.connections.size(); ++j) {
                    pw2.println(this.connections.get(j).getPlayer().getUsername());
                }
            }
            Card card = deck.getCard();
            dealer.addCard(card);
            for (int k = 0; k < this.connections.size(); ++k) {
                final PlayerConnection connection3 = this.connections.get(k);
                final PrintWriter pw3 = new PrintWriter(new OutputStreamWriter(connection3.getSocket().getOutputStream()), true);
                pw3.println("DISPLAY CLOSED CARD");
                pw3.println("DEALER");
                pw3.println(card.getImageName());
            }
            card = deck.getCard();
            dealer.addCard(card);
            for (int k = 0; k < this.connections.size(); ++k) {
                final PlayerConnection connection3 = this.connections.get(k);
                final PrintWriter pw3 = new PrintWriter(new OutputStreamWriter(connection3.getSocket().getOutputStream()), true);
                pw3.println("DISPLAY OPEN CARD");
                pw3.println("DEALER");
                pw3.println(card.getImageName());
            }
            for (int k = 0; k < this.connections.size(); ++k) {
                final PlayerConnection connection3 = this.connections.get(k);
                final Player player2 = connection3.getPlayer();
                card = deck.getCard();
                player2.addCard(card);
                PrintWriter pw4 = new PrintWriter(new OutputStreamWriter(connection3.getSocket().getOutputStream()), true);
                pw4.println("DISPLAY OPEN CARD ONLY TO");
                pw4.println(player2.getUsername());
                pw4.println(card.getImageName());
                for (int l = 0; l < this.connections.size(); ++l) {
                    if (l != k) {
                        final PlayerConnection connectionOther = this.connections.get(l);
                        pw4 = new PrintWriter(new OutputStreamWriter(connectionOther.getSocket().getOutputStream()), true);
                        pw4.println("DISPLAY CLOSED CARD");
                        pw4.println(player2.getUsername());
                        pw4.println(card.getImageName());
                    }
                }
                card = deck.getCard();
                player2.addCard(card);
                for (int l = 0; l < this.connections.size(); ++l) {
                    final PlayerConnection connectionOther = this.connections.get(l);
                    pw4 = new PrintWriter(new OutputStreamWriter(connectionOther.getSocket().getOutputStream()), true);
                    pw4.println("DISPLAY OPEN CARD");
                    pw4.println(player2.getUsername());
                    pw4.println(card.getImageName());
                }
            }
            for (int k = 0; k < this.connections.size(); ++k) {
                final PlayerConnection connection3 = this.connections.get(k);
                final Player player2 = connection3.getPlayer();
                final PrintWriter pw4 = new PrintWriter(new OutputStreamWriter(connection3.getSocket().getOutputStream()), true);
                pw4.println("DISPLAY SCORE");
                pw4.println(player2.getUsername());
                pw4.println(player2.getCardsSum());
            }
            for (int k = 0; k < this.connections.size(); ++k) {
                final PlayerConnection connection3 = this.connections.get(k);
                final Player player2 = connection3.getPlayer();
                while (true) {
                    for (int m = 0; m < this.connections.size(); ++m) {
                        final PlayerConnection connectionOther2 = this.connections.get(m);
                        final PrintWriter pw5 = new PrintWriter(new OutputStreamWriter(connectionOther2.getSocket().getOutputStream()), true);
                        pw5.println("NOTIFY TURN");
                        pw5.println(player2.getUsername());
                    }
                    final BufferedReader br2 = new BufferedReader(new InputStreamReader(connection3.getSocket().getInputStream()));
                    final String move = br2.readLine();
                    if (move.equals("Hit")) {
                        card = deck.getCard();
                        player2.addCard(card);
                        for (int j2 = 0; j2 < this.connections.size(); ++j2) {
                            final PlayerConnection connectionOther3 = this.connections.get(j2);
                            final PrintWriter pw6 = new PrintWriter(new OutputStreamWriter(connectionOther3.getSocket().getOutputStream()), true);
                            pw6.println("DISPLAY OPEN CARD");
                            pw6.println(player2.getUsername());
                            pw6.println(card.getImageName());
                        }
                        if (player2.isBusted()) {
                            final PrintWriter pw5 = new PrintWriter(new OutputStreamWriter(connection3.getSocket().getOutputStream()), true);
                            pw5.println("DISPLAY SCORE");
                            pw5.println(player2.getUsername());
                            pw5.println(player2.getCardsSum());
                            break;
                        }
                    }
                    else if (move.equals("Stand")) {
                        final PrintWriter pw5 = new PrintWriter(new OutputStreamWriter(connection3.getSocket().getOutputStream()), true);
                        pw5.println("DISPLAY SCORE");
                        pw5.println(player2.getUsername());
                        pw5.println(player2.getCardsSum());
                        break;
                    }
                    final PrintWriter pw5 = new PrintWriter(new OutputStreamWriter(connection3.getSocket().getOutputStream()), true);
                    pw5.println("DISPLAY SCORE");
                    pw5.println(player2.getUsername());
                    pw5.println(player2.getCardsSum());
                }
                for (int m = 0; m < this.connections.size(); ++m) {
                    final PlayerConnection connectionOther2 = this.connections.get(m);
                    final PrintWriter pw5 = new PrintWriter(new OutputStreamWriter(connectionOther2.getSocket().getOutputStream()), true);
                    pw5.println("NOTIFY FINISHED");
                    pw5.println(player2.getUsername());
                }
            }
            while (dealer.getCardsSum() < 16 && !dealer.isBusted()) {
                card = deck.getCard();
                dealer.addCard(card);
                for (int k = 0; k < this.connections.size(); ++k) {
                    final PlayerConnection connection3 = this.connections.get(k);
                    final PrintWriter pw3 = new PrintWriter(new OutputStreamWriter(connection3.getSocket().getOutputStream()), true);
                    pw3.println("DISPLAY OPEN CARD");
                    pw3.println("DEALER");
                    pw3.println(card.getImageName());
                }
            }
            for (int k = 0; k < this.connections.size(); ++k) {
                final PlayerConnection connection3 = this.connections.get(k);
                final PrintWriter pw3 = new PrintWriter(new OutputStreamWriter(connection3.getSocket().getOutputStream()), true);
                pw3.println("GAME OVER");
            }
            for (int k = 0; k < this.connections.size(); ++k) {
                final PlayerConnection connection3 = this.connections.get(k);
                final PrintWriter pw3 = new PrintWriter(new OutputStreamWriter(connection3.getSocket().getOutputStream()), true);
                pw3.println("DISPLAY SCORE");
                pw3.println("DEALER");
                pw3.println(dealer.getCardsSum());
            }
            for (int k = 0; k < this.connections.size(); ++k) {
                final Player player3 = this.connections.get(k).getPlayer();
                for (int j = 0; j < this.connections.size(); ++j) {
                    final PlayerConnection connection4 = this.connections.get(k);
                    final PrintWriter pw7 = new PrintWriter(new OutputStreamWriter(connection4.getSocket().getOutputStream()), true);
                    pw7.println("DISPLAY SCORE");
                    pw7.println(player3.getUsername());
                    pw7.println(player3.getCardsSum());
                }
            }
            for (int k = 0; k < this.connections.size(); ++k) {
                final PlayerConnection connection3 = this.connections.get(k);
                final Player player2 = connection3.getPlayer();
                final PrintWriter pw4 = new PrintWriter(new OutputStreamWriter(connection3.getSocket().getOutputStream()), true);
                pw4.println("NOTIFY RESULT");
                if (player2.isBusted()) {
                    player2.removeChips(connection3.getBetChips());
                    pw4.println("BUSTED! You lost. " + connection3.getBetChips() + " chips. You now have a total of " + player2.getChips() + " chips.");
                }
                else if (dealer.isBusted()) {
                    player2.addChips(connection3.getBetChips());
                    pw4.println("Dealer Busted! YOU WON " + connection3.getBetChips() + " CHIPS ! You now have a total of " + player2.getChips() + " chips.");
                }
                else if (player2.getCardsSum() < dealer.getCardsSum()) {
                    player2.removeChips(connection3.getBetChips());
                    pw4.println("Dealer is closer to 21. You lost " + connection3.getBetChips() + " chips. You now have a total of " + player2.getChips() + " chips.");
                }
                else if (player2.getCardsSum() > dealer.getCardsSum()) {
                    player2.addChips(connection3.getBetChips());
                    pw4.println("You are closer to 21. YOU WON !!! " + connection3.getBetChips() + " CHIPS ! You now have a total of " + player2.getChips() + " chips.");
                }
                else {
                    pw4.println("Its a tie. You still have a total of " + player2.getChips() + " chips.");
                }
                connection3.getSocket().close();
            }
            serverSocket.close();
            this.setVisible(false);
        }
        catch (Exception error) {
            error.printStackTrace();
        }
    }
}
