//Abdoul Bachir Zabeirou Oumarou
//Alibek Zeinolla
//Bea Uwamahoro
//Manuel Peralta
//BlackJack Project

package blackjack;

import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JPanel
{
    private String name;
    private String score;
    private String status;
    private JLabel statusLabel;
    private JLabel closedCard;
    private String closedCardPath;
    
    public PlayerPanel(final String name) {
        this.name = name;
        this.status = "Waiting";
        this.score = "xx";
        this.setLayout(new FlowLayout(2));
        (this.statusLabel = new JLabel(name)).setForeground(Color.WHITE);
        this.add(this.statusLabel);
        this.setBackground(new Color(0, 100, 0));
    }
    
    public void setClosedCardPath(final JLabel closedCard, final String path) {
        this.closedCard = closedCard;
        this.closedCardPath = path;
    }
    
    public void openClosedCard() {
        if (this.closedCard != null) {
            this.closedCard.setIcon(new ImageIcon(this.closedCardPath));
            this.closedCard.updateUI();
        }
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public void updateScore(final String score) {
        this.score = score;
        this.statusLabel.setText(this.name + " (Card Value: " + score + ")(Status : " + this.status + ")");
    }
    
    public void updateStatus(final String status) {
        this.status = status;
        this.statusLabel.setText(this.name + " (Card Value: " + this.score + ")(Status : " + this.status + ")");
    }
    
    @Override
    public String getName() {
        return this.name;
    }
}
