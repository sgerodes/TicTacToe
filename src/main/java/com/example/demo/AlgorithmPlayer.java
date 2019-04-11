package com.example.demo;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AlgorithmPlayer extends Player {

	//private String[] movesPool = {"a1", "a2", "a3", "b1", "b2", "b3", "c1", "c2", "c3"};
	private Point2D[] movesPool = {
			new Point2D(0,0), new Point2D(0,1),new Point2D(0,2),
			new Point2D(1,0),new Point2D(1,1),new Point2D(1,2),
			new Point2D(2,0),new Point2D(2,1),new Point2D(2,2)};

	private Board board;
	private Square center;

	@Override
	public void makeTurn(Board board) {
		this.board = board;
		center = board.getSquareByPoint(new Point2D(1,1));

		if (winIfPossible()){
			return;
		}

		if (preventOpponentsWinIfPossible()){
			return;
		}

		if (occupyCenterIfEmpty()){
			return;
		}

		if (board.getGame().getPly() <= 4){
			occupyACorner();
			return;
		}

		if (occupySquareWithMaximumPotentialIfPresent()){
			return;
		}

		makeRandomMove();

	}

	private boolean occupySquareWithMaximumPotentialIfPresent() {
		for (Square sq: board.getAllSquares()) {
			if (sq.isEmpty() && isAHighPotentialSquare(sq)){
				sq.markWith(this.getMark());
				return true;
			}
		}
		return false;
	}

	private boolean isAHighPotentialSquare(Square sq) {
		int potentiabilityIndex = 0;
		int row = sq.getCoordinates().getRow();
		int col = sq.getCoordinates().getColumn();
		if (isPotentialLine(board.getRow(row))){
			potentiabilityIndex++;
		}
		if (isPotentialLine(board.getColumn(col))){
			potentiabilityIndex++;
		}
		if (onRisingDiagonal(row, col) && isPotentialLine(board.getDiagonal(true))){
			potentiabilityIndex++;
		}
		if (onFallingDiagonal(row, col) && isPotentialLine(board.getDiagonal(false))){
			potentiabilityIndex++;
		}
		return potentiabilityIndex >= 2;
	}

	private boolean onFallingDiagonal(int row, int col) {
		return row + col == 2;
	}

	private boolean onRisingDiagonal(int row, int col) {
		return row - col == 0;
	}

	private boolean isPotentialLine(List<Square> line) {
		int markedByMe = 0;
		int empty = 0;
		for (Square sq : line) {
			if (sq.getMark() == this.getMark()) {
				markedByMe++;
			}
			if (sq.getMark() == Mark.EMPTY) {
				empty++;
			}
		}
		return markedByMe == 1 && empty == 2;
	}


	private void occupyACorner() {
		final List<Point2D> corners = new ArrayList<Point2D>(Arrays.asList(new Point2D(0,0), new Point2D(0,2),
				new Point2D(2,0), new Point2D(2,2)));
		Collections.shuffle(corners);
		for (Point2D p: corners) {
			if(board.isPossibleMove(p)){
				board.markWith(p, this.getMark());
				return;
			}
		}
	}

	private void makeRandomMove() {
		Random random = new Random();
		while (true){
			Point2D move = movesPool[random.nextInt(movesPool.length)];
			if (board.isPossibleMove(move)){
				board.markWith(move, this.getMark());
				return;
			}
		}
	}

	private boolean occupyCenterIfEmpty() {

		if (center.isEmpty()){
			center.markWith(this.getMark());
			return true;
		}
		return false;
	}

	private boolean preventOpponentsWinIfPossible() {
		final List<List<Square>> allLines = board.getAllLines();
		for (List<Square> line : allLines) {
			if (isWinningForMark(line, getOpponentsMark())) {
				markIfOnlyOnePossibility(line);
				return true;
			}
		}
		return false;
	}

	private boolean winIfPossible() {
		final List<List<Square>> allLines = board.getAllLines();
		for (List<Square> line : allLines) {
			if (isWinningForMark(line, this.getMark())) {
				markIfOnlyOnePossibility(line);
				return true;
			}
		}
		return false;
	}

	private boolean isWinningForMark(List<Square> line, Mark m) {
		int markedBySearchedMark = 0;
		int empty = 0;
		for (Square sq : line) {
			if (sq.getMark() == m) {
				markedBySearchedMark++;
			}
			if (sq.getMark() == Mark.EMPTY) {
				empty++;
			}
		}
		return markedBySearchedMark == 2 && empty == 1;
	}


	private void markIfOnlyOnePossibility(List<Square> line) {
		for (Square sq : line) {
			if (sq.isEmpty()){
				sq.markWith(this.getMark());
				return;
			}
		}
	}


	private Mark getOpponentsMark(){
		return this.getMark() == Mark.CROSS ? Mark.NOUGHT : Mark.CROSS;
	}
}
