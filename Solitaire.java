package solitaire;

import java.util.Scanner;

public class Solitaire {
    public static void main (String[] args) {
        Board b = new Board();
        System.out.println(b);
        
        while (!b.gameOver()) {
            nextMove(b);
            System.out.println(b);
        }
        
    }
    
    public static void nextMove(Board b) {
        Scanner s = new Scanner(System.in);
        
        System.out.println("1. add card from discard to main pile");
        System.out.println("2. add card from discard to top pile");
        System.out.println("3. add card from main pile to top pile");
        System.out.println("4. move card from main pile to another main pile");
        System.out.println("5. rotate discard pile");
        System.out.println("Enter number for your move:");
        
        int move = s.nextInt();
        if (move < 1 || move > 6) {
            System.out.println("Illegal number");
            return;
        } 
        
        try {
            switch (move) {
            case 1: 
                System.out.println("Enter main pile number from 1 to 7:");
                int pile = s.nextInt();
                if (pile < 1 || pile > 7) {
                    System.out.println("Illegal number");
                    return;
                }
                b.addDiscardToMain(pile);
                break;
            case 2:
                b.addDiscardToTop();
                break;
            case 3:
                System.out.println("Enter main pile number from 1 to 7:");
                int pile1 = s.nextInt();
                if (pile1 < 1 || pile1 > 7) {
                    System.out.println("Illegal number");
                    return;
                }
                b.addMainToTop(pile1);
                break;
            case 4:
                System.out.println("Enter main pile to move from:");
                int from = s.nextInt();
                System.out.println("Enter card index to move (#s on left):");
                int card = s.nextInt();
                System.out.println("Enter main pile to move to:");
                int to = s.nextInt();
                b.moveMain(from, to, card);
                break;
            case 5:
                b.moveDiscard();
                break;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal Move");
            return;
        }
            
    }
}
 