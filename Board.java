package solitaire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import java.util.Iterator;

public class Board {
    Stack<Cards> topH;
    Stack<Cards> topD;
    Stack<Cards> topS;
    Stack<Cards> topC;
    
    //numbers apply left to right
    ArrayList<Stack<Cards>> mainPiles;
    
    Stack<Cards> discard; //already looked at
    Stack<Cards> stock; //cards to look at
    
    public Board () {
        Stack<Cards> deck = new Stack<Cards>();
        for (int i = 1; i < 14; i++) {
            Cards h = new Cards ("heart", i, true);
            Cards d = new Cards ("diamond", i, true);
            Cards s = new Cards ("spade", i, true);
            Cards c = new Cards ("club", i, true);
            deck.push(h);
            deck.push(d);
            deck.push(s);
            deck.push(c);
        }
        Collections.shuffle(deck); 
        
        topH = new Stack<Cards>();
        topD = new Stack<Cards>();
        topS = new Stack<Cards>();
        topC = new Stack<Cards>();
        discard = new Stack<Cards>();
        
        mainPiles = new ArrayList<Stack<Cards>>(7);
        
        for (int i = 0; i < 7; i++) {
            //number times through piles
            for (int j = i; j < 7; j++) {
                //pile
                if (mainPiles.size() <= j) {
                    Stack<Cards> p = new Stack<Cards>();
                    p.push((Cards)deck.pop());
                    mainPiles.add(p);
                } else {
                    (mainPiles.get(j)).push((Cards)deck.pop());
                }
            } 
        }
        
        stock = new Stack<Cards> ();
        stock.addAll(deck);
    }
    
    @Override
    public String toString() {
        String ret = String.format("%15s %15s %15s %15s \n", "Hearts", "Clubs", "Diamonds", "Spades");
        int i = 0;
        for (Stack<Cards> a: mainPiles) {
            if (!a.empty()) {
                a.peek().makeVisible();
            }
        }
        Iterator<Cards> h = topH.iterator();
        Iterator<Cards> c = topC.iterator();
        Iterator<Cards> d = topD.iterator();
        Iterator<Cards> s = topS.iterator();
        while (i == 0) {
            i = 1;
            if (h.hasNext()) {
                i = 0;
                ret += String.format("%15s", h.next().toString());
            } else {
                ret += String.format("%15s", "");
            }
            if (c.hasNext()) {
                i = 0;
                ret += String.format("%15s", c.next().toString());
            } else {
                ret += String.format("%15s", "");
            }
            if (d.hasNext()) {
                i = 0;
                ret += String.format("%15s", d.next().toString());
            } else {
                ret += String.format("%15s", "");
            }
            if (s.hasNext()) {
                i = 0;
                ret += String.format("%15s", s.next().toString());
            } else {
                ret += String.format("%15s", "");
            }
            ret += "\n";
        }
        
        ret += String.format("%10s%10s%10s%10s%10s%10s%10s \n", "1", "2", "3", "4", "5", "6", "7");
        
        i = 0;
        int j = 0;
        Iterator<Cards> zero = mainPiles.get(0).iterator();
        Iterator<Cards> one = mainPiles.get(1).iterator();
        Iterator<Cards> two = mainPiles.get(2).iterator();
        Iterator<Cards> three = mainPiles.get(3).iterator();
        Iterator<Cards> four = mainPiles.get(4).iterator();
        Iterator<Cards> five = mainPiles.get(5).iterator();
        Iterator<Cards> six = mainPiles.get(6).iterator();
        while (i == 0) {
            i = 1;
            ret += String.format("%3s", Integer.toString(j+1));
            j++;
            if (zero.hasNext()) {
                i = 0;
                ret += String.format("%10s", zero.next().toString());
            } else {
                ret += String.format("%10s", "");
            }
            if (one.hasNext()) {
                i = 0;
                ret += String.format("%10s", one.next().toString());
            } else {
                ret += String.format("%10s", "");
            }
            if (two.hasNext()) {
                i = 0;
                ret += String.format("%10s", two.next().toString());
            } else {
                ret += String.format("%10s", "");
            }
            if (three.hasNext()) {
                i = 0;
                ret += String.format("%10s", three.next().toString());
            } else {
                ret += String.format("%10s", "");
            }
            if (four.hasNext()) {
                i = 0;
                ret += String.format("%10s", four.next().toString());
            } else {
                ret += String.format("%10s", "");
            }
            if (five.hasNext()) {
                i = 0;
                ret += String.format("%10s", five.next().toString());
            } else {
                ret += String.format("%10s", "");
            }
            if (six.hasNext()) {
                i = 0;
                ret += String.format("%10s", six.next().toString());
            } else {
                ret += String.format("%10s", "");
            }
            ret += "\n";
        }
        
        ret += "Discard: ";
        if (!discard.empty()) {
            ret += discard.peek().toString();
            discard.peek().makeVisible();
        }
        ret += "\n";
        
        return ret;
    }
    
    //possible moves:
    //1. add card from discard to main pile
    //2. add card from discard to top pile
    //3. add card from main pile to top pile
    //4. move card from main pile to another main pile
    //5. get three more cards from stock
    //6. return discard to stock
    
    public void addDiscardToMain (int p) {
        //p is from 1 to 7, the pile we are adding the card to
        if (discard.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Cards add = discard.peek();
        if (mainPiles.get(p-1).empty()) {
            if (add.getValue() == 13) {
                Cards move = discard.pop();
                mainPiles.get(p-1).push(move);
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            Cards last = mainPiles.get(p-1).peek();
            if (last.isNextAlternate(add)) {
                Cards move = discard.pop();
                mainPiles.get(p-1).push(move);
            } else {
                throw new IllegalArgumentException();
            }
        }
    }
    
    public void addDiscardToTop () {
        if (discard.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Cards add = discard.peek();
        String s = add.getSuit();
        if (s.equals("heart")) {
            if (topH.isEmpty()) {
                if (add.getValue() == 1) {
                    Cards move = discard.pop();
                    move.makeVisible();
                    topH.push(move);
                }
            } else {
                Cards prev = topH.peek();
                if (add.isNextSame(prev)) {
                    Cards move = discard.pop();
                    move.makeVisible();
                    topH.push(move);
                }
            }
        } else if (s.equals("diamond")) {
            if (topD.isEmpty()) {
                if (add.getValue() == 1) {
                    Cards move = discard.pop();
                    move.makeVisible();
                    topD.push(move);
                }
            } else {
                Cards prev = topD.peek();
                if (add.isNextSame(prev)) {
                    Cards move = discard.pop();
                    move.makeVisible();
                    topD.push(move);
                }
            }
        } else if (s.equals("spade")) {
            if (topS.isEmpty()) {
                if (add.getValue() == 1) {
                    Cards move = discard.pop();
                    move.makeVisible();
                    topS.push(move);
                }
            } else {
                Cards prev = topS.peek();
                if (add.isNextSame(prev)) {
                    Cards move = discard.pop();
                    move.makeVisible();
                    topS.push(move);
                }
            }
        } else if (s.equals("club")) {
            if (topC.isEmpty()) {
                if (add.getValue() == 1) {
                    Cards move = discard.pop();
                    move.makeVisible();
                    topC.push(move);
                }
            } else {
                Cards prev = topC.peek();
                if (add.isNextSame(prev)) {
                    Cards move = discard.pop();
                    move.makeVisible();
                    topC.push(move);
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    public void addMainToTop (int p) {
        //p is main pile
        if (mainPiles.get(p-1).isEmpty()) {
            throw new IllegalArgumentException();
        }
        Cards add = mainPiles.get(p-1).peek();
        String s = add.getSuit();
        if (s.equals("heart")) {
            if (topH.isEmpty()) {
                if (add.getValue() == 1) {
                    Cards move = mainPiles.get(p-1).pop();
                    move.makeVisible();
                    topH.push(move);
                }
            } else {
                Cards prev = topH.peek();
                if (add.isNextSame(prev)) {
                    Cards move = mainPiles.get(p-1).pop();
                    move.makeVisible();
                    topH.push(move);
                }
            }
        } else if (s.equals("diamond")) {
            if (topD.isEmpty()) {
                if (add.getValue() == 1) {
                    Cards move = mainPiles.get(p-1).pop();
                    move.makeVisible();
                    topD.push(move);
                }
            } else {
                Cards prev = topD.peek();
                if (add.isNextSame(prev)) {
                    Cards move = mainPiles.get(p-1).pop();
                    move.makeVisible();
                    topD.push(move);
                }
            }
        } else if (s.equals("spade")) {
            if (topS.isEmpty()) {
                if (add.getValue() == 1) {
                    Cards move = mainPiles.get(p-1).pop();
                    move.makeVisible();
                    topS.push(move);
                }
            } else {
                Cards prev = topS.peek();
                if (add.isNextSame(prev)) {
                    Cards move = mainPiles.get(p-1).pop();
                    move.makeVisible();
                    topS.push(move);
                }
            }
        } else if (s.equals("club")) {
            if (topC.isEmpty()) {
                if (add.getValue() == 1) {
                    Cards move = mainPiles.get(p-1).pop();
                    move.makeVisible();
                    topC.push(move);
                }
            } else {
                Cards prev = topC.peek();
                if (add.isNextSame(prev)) {
                    Cards move = mainPiles.get(p-1).pop();
                    move.makeVisible();
                    topC.push(move);
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    public void moveDiscard() {
        if (stock.empty() && discard.empty()) {
            throw new IllegalArgumentException();
        } else if (stock.empty()) {
            resetDiscard();
            return;
        }
        for (int i = 0; i < 3; i++) {
            Cards one = stock.pop();
            discard.push(one);
            one.makeVisible();
            if (stock.isEmpty()) {
                return;
            }
        }
    }
    
    public void resetDiscard() {
        if (discard.isEmpty()) {
            throw new IllegalArgumentException();
        }
        while (!discard.isEmpty()) {
            Cards c = discard.pop();
            stock.push(c);
        }
    }
    
    public void moveMain (int f, int t, int c) {
        int index = c-1; //c is the user index starting with 1
        int from = f-1;
        int to = t-1;
        Stack<Cards> temp = new Stack<Cards>();
        if (mainPiles.get(to).empty()) {
            if (mainPiles.get(from).get(index).getValue() == 13) {
                while (mainPiles.get(from).size() > index) {
                    temp.push(mainPiles.get(from).pop());
                }
                while (!temp.empty()) {
                    mainPiles.get(to).push(temp.pop());
                }
            } else {
                throw new IllegalArgumentException();
            }
        } else if (mainPiles.get(from).size() < c){
            throw new IllegalArgumentException();
        } else if (mainPiles.get(to).peek().isNextAlternate (mainPiles.get(from).get(index))) {
            while (mainPiles.get(from).size() > index) {
                temp.push(mainPiles.get(from).pop());
            }
            while (!temp.empty()) {
                mainPiles.get(to).push(temp.pop());
            }
        } else {
            throw new IllegalArgumentException();
        }
        System.out.println(discard.size());
    }
    
    public boolean gameOver() {
        return ((topH.size() == 13) && (topD.size() == 13) && (topC.size() == 13) && (topS.size() == 13));
    }
}
