package com.example.demo;

import lombok.Data;


@Data
public class Game {

	Player crossPlayer;
	Player noughtPlayer;
	Board board;
	int ply = 0;

	public Game(Player p1, Player p2) {

		this.crossPlayer = p1;
		crossPlayer.setMark(Mark.CROSS);

		this.noughtPlayer = p2;
		noughtPlayer.setMark(Mark.NOUGHT);

		board = new Board(this);
	}


	public void play() {
		System.out.println("Game started. Possible moves are A1, A2, A3, B1, B2, B3, C1, C2, C3.");
		System.out.println(board);
		boolean crossTurn = true;
		while (!board.isGameOver()) {
			ply++;
			if (crossTurn) {
				crossPlayer.makeTurn(board);
			} else {
				noughtPlayer.makeTurn(board);
			}
			crossTurn = !crossTurn;
			System.out.println("Turn " + ply);
			System.out.println(board);
		}
		if (board.isFilled()) {
			System.out.println("\n\n Game Over! It is a tie.");
		} else {
			System.out.println("\n\nPlayer '" + board.whoWon() + "' won. Game is Over. Thank you for playing.");
		}
		System.out.println(board);
	}


}
