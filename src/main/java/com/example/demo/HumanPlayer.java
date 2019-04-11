package com.example.demo;

import java.util.Scanner;

public class HumanPlayer extends Player {

	private Scanner sc = new Scanner(System.in);
	private String NOT_VALID_INPUT = "Input is not valid. Only [A1, A2, A3, B1, B2, B3, C1, C2, C3] is accepted.";

	@Override
	public void makeTurn(Board board) {
		Point2D move = readTurnFromConsole(board);
		board.markWith(move, this.mark);
	}


	public Point2D readTurnFromConsole(Board board) {
		String input;
		Point2D move;
		System.out.println("Please make a turn");
		while (true) {
			input = sc.next().toLowerCase();
			if (!board.isValidMoveRepresentation(input)) {
				System.out.println(NOT_VALID_INPUT);
			} else {
				move = board.parseStringToPoint(input);
				if (board.isPossibleMove(move)) {
					return move;
				} else {
					System.out.println(String.format("The Square %s is already taken", input));
				}
			}
		}
	}

}
