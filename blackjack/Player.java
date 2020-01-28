//Abdoul Bachir Zabeirou Oumarou
//Alibek Zeinolla
//Bea Uwamahoro
//Manuel Peralta
//BlackJack Project

package blackjack;

import java.util.ArrayList;
import java.util.List;

public class Player extends Account
{
    private List<Card> cards;
    private int chips;
    
    public Player(final String username, final String password, final int chips) {
        super(username, password);
        this.chips = chips;
        this.cards = new ArrayList<Card>();
    }
    
    public void addChips(final int chips) {
        this.chips += chips;
    }
    
    public void removeChips(int chips) {
        this.chips -= chips;
        if (chips < 0) {
            chips = 0;
        }
    }
    
    public int getChips() {
        return this.chips;
    }
    
    public void addCard(final Card card) {
        this.cards.add(card);
    }
    
    public void resetCards() {
        this.cards.clear();
    }
    
    public Card getCard(final int index) {
        return this.cards.get(index);
    }
    
    public int getCardsSum() {
        int numberOfAces = 0;
        int nonAceSum = 0;
        for (int i = 0; i < this.cards.size(); ++i) {
            if (this.cards.get(i).getValue() == 1) {
                ++numberOfAces;
            }
            else if (this.cards.get(i).getValue() > 10) {
                nonAceSum += 10;
            }
            else {
                nonAceSum += this.cards.get(i).getValue();
            }
        }
        if (numberOfAces <= 0) {
            return nonAceSum;
        }
        int testWith11 = nonAceSum + 11;
        for (int j = 1; j < numberOfAces; ++j) {
            ++testWith11;
        }
        final int testWithout11 = nonAceSum + numberOfAces;
        if (testWith11 > 21) {
            return testWithout11;
        }
        if (testWithout11 > 21) {
            return testWith11;
        }
        if (testWith11 > testWithout11) {
            return testWith11;
        }
        return testWithout11;
    }
    
    public boolean isBusted() {
        return this.getCardsSum() > 21;
    }
    
    public String getPrintableCards() {
        String string = "";
        for (int i = 0; i < this.cards.size(); ++i) {
            string = string + this.cards.get(i) + " ";
        }
        return string;
    }
    
    public String getPrintableCardsExceptFirstCard() {
        String string = "<HIDDEN CARD> ";
        for (int i = 1; i < this.cards.size(); ++i) {
            string = string + this.cards.get(i) + " ";
        }
        return string;
    }
    
    @Override
    public String toString() {
        return this.getUsername() + "|" + this.getPassword() + "|" + this.getChips();
    }
}
