//Abdoul Bachir Zabeirou Oumarou
//Alibek Zeinolla
//Bea Uwamahoro
//Manuel Peralta
//BlackJack Project

package blackjack;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Deck
{
    private List<Card> cards;
    
    public Deck() {
        this.cards = new ArrayList<Card>();
        this.initializeCards();
    }
    
    private void initializeCards() {
        for (int i = 1; i <= 13; ++i) {
            this.cards.add(new Card(i, 0));
            this.cards.add(new Card(i, 1));
            this.cards.add(new Card(i, 2));
            this.cards.add(new Card(i, 3));
        }
    }
    
    public Card getCard() {
        if (this.cards.isEmpty()) {
            this.initializeCards();
        }
        final Random random = new Random();
        final Card card = this.cards.remove(random.nextInt(this.cards.size()));
        return card;
    }
}
