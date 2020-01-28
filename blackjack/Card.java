//Abdoul Bachir Zabeirou Oumarou
//Alibek Zeinolla
//Bea Uwamahoro
//Manuel Peralta
//BlackJack Project

package blackjack;

public class Card
{
    public static final int CLOVER = 0;
    public static final int SPADE = 1;
    public static final int HEART = 2;
    public static final int DIAMOND = 3;
    private int value;
    private int type;
    
    public Card(final int value, final int type) {
        this.type = type;
        this.value = value;
    }
    
    public int getValue() {
        return this.value;
    }
    
    public int getType() {
        return this.type;
    }
    
    @Override
    public String toString() {
        String cardType = "";
        if (this.getType() == 0) {
            cardType = "Clover";
        }
        else if (this.getType() == 2) {
            cardType = "Heart";
        }
        else if (this.getType() == 1) {
            cardType = "Spade";
        }
        else {
            cardType = "Diamond";
        }
        return "<" + cardType + " " + this.getValue() + ">";
    }
    
    public String getImageName() {
        String cardType = "";
        if (this.getType() == 0) {
            cardType = "clover";
        }
        else if (this.getType() == 2) {
            cardType = "heart";
        }
        else if (this.getType() == 1) {
            cardType = "spade";
        }
        else {
            cardType = "diamond";
        }
        return cardType + "/" + this.getValue() + ".png";
    }
}
