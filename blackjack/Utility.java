//Abdoul Bachir Zabeirou Oumarou
//Alibek Zeinolla
//Bea Uwamahoro
//Manuel Peralta
//BlackJack Project

package blackjack;

import java.security.MessageDigest;
import java.io.*;
import java.util.*;

public class Utility
{
    private static Account administrator;
    private static List<Account> players;
    
    public static Account getAdministrator() {
        return Utility.administrator;
    }
    
    public static List<Account> getPlayers() {
        return Utility.players;
    }
    
    public static void saveAccounts() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("players.dat"));
            pw.println(Utility.administrator.getPassword());
            pw.flush();
            pw.close();
            pw = new PrintWriter(new FileWriter("players.dat"));
            for (int i = 0; i < Utility.players.size(); ++i) {
                final Player player = (Player) Utility.players.get(i);
                pw.println(player);
            }
            pw.flush();
            pw.close();
        }
        catch (Exception error) {
            System.out.println("Failed to save to accounts.");
            System.exit(0);
        }
    }
    
    public static Account findPlayer(final String username) {
        for (int i = 0; i < Utility.players.size(); ++i) {
            if (Utility.players.get(i).getUsername().equals(username)) {
                return Utility.players.get(i);
            }
        }
        return null;
    }
    
    public static void initializeAccounts() {
        try {
            Scanner scanner = new Scanner(new File("admin.dat"));
            Utility.administrator = new Administrator(scanner.nextLine());
            scanner.close();
            scanner = new Scanner(new File("players.dat"));
            while (scanner.hasNextLine()) {
                final String[] tokens = scanner.nextLine().split("\\|");
                final Player player = new Player(tokens[0], tokens[1], Integer.parseInt(tokens[2]));
                Utility.players.add(player);
            }
            scanner.close();
        }
        catch (Exception error) {
            error.printStackTrace();
            System.out.println("Failed to load accounts.");
            System.exit(0);
        }
    }
    
    public static String hashPassword(String password) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            final byte[] mdBytes = md.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mdBytes.length; ++i) {
                sb.append(Integer.toHexString(0xFF & mdBytes[i]));
            }
            password = sb.toString();
        }
        catch (Exception ex) {}
        return password;
    }
    
    static {
        Utility.players = new ArrayList<Account>();
    }
}
