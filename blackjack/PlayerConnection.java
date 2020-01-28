//Abdoul Bachir Zabeirou Oumarou
//Alibek Zeinolla
//Bea Uwamahoro
//Manuel Peralta
//BlackJack Project

package blackjack;

import javax.swing.JTextField;
import java.net.Socket;

public class PlayerConnection
{
    private int betChips;
    private Player player;
    private Socket socket;
    private JTextField statusField;
    
    public PlayerConnection(final int betChips, final Player player, final Socket socket, final JTextField statusField) {
        this.betChips = betChips;
        this.player = player;
        this.socket = socket;
        this.statusField = statusField;
    }
    
    public int getBetChips() {
        return this.betChips;
    }
    
    public Socket getSocket() {
        return this.socket;
    }
    
    public Player getPlayer() {
        return this.player;
    }
}
