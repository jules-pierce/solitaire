# solitaire
general rules: https://www.bicyclecards.com/how-to-play/solitaire/

Terminal based solitaire game
How to run code: type java -jar solitaire.jar
	for face cards, A=1, J=11, Q=12, K=13

Solitaire.java handles the user interaction, basically just printing to the terminal and reading user input. 
Board.java handles the overall board, and has the majority of the code in it. It stores stacks to represent 
the stock pile, discard pile, main piles, and top piles, and has the methods for various moves. It also has a toString()
method that is used to actually print the output. 
Cards.java is just a class to represent cards. It stores the value, suit, and whether or not the card is visible, 
and contains basic functions for adjusting these values. It also has a toString() method that is called in boards' toString()
method. 

