package solitaire;

public class Cards {
    //represents one card of the deck
    private String suit;
    private int val;
    private boolean hidden; //true - user can't see it
    
    //instantiate given card
    Cards (String s, int v, boolean h){ 
        suit = s;
        val = v;
        hidden = h;
    }
    
    //help print out a card
    @Override
    public String toString () {
        String ret = "";
        if (hidden) {
            return "X";
        }
        if (suit.equals("heart")) {
            ret = "H";
        } else if (suit.equals("diamond")) {
            ret = "D";
        } else if (suit.equals("club")) {
            ret = "C";
        } else if (suit.equals("spade")) {
            ret = "S";
        } else {
            throw new IllegalArgumentException();
        }
        
        ret += val;
        return ret;
    }
    
    //return the card value
    int getValue () {
        return this.val;
    }
    
    //return suit color, 0 = red, 1 = black
    int getColor() {
        if (suit.equals("heart") || suit.equals("diamond")) {
            return 0;
        } else if (suit.equals("spade") || suit.equals("club")) {
            return 1;
        } else {
           throw new IllegalArgumentException(); 
        }
    }
    
    //returns the suit itself
    String getSuit() {
        return suit;
    }
    
    //tells if c goes on top of this for main piles
    boolean isNextAlternate(Cards c) {
        if (this.val - 1 == c.getValue()) {
            if (this.getColor() != c.getColor()) {
                return true;
            }
        }
        
        return false;
    }
    
    //tells if this is the next card after c for the top piles
    boolean isNextSame(Cards c) {
        if (this.val - 1 == c.getValue()) {
            if (suit.equals(c.getSuit())) {
                return true;
            }
        }
        
        return false;
    }
    
    void makeVisible() {
        hidden = false;
    }
}
