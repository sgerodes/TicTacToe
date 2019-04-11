package com.example.demo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


@Data
public class Board {

	private final Square[][] state;
	private List<Square> allSquares;
	private final String VALID_TURN_PATTERN = "[ABCabc][123]";
	private Game game;
	private HashMap<String, Integer> reprToIndexes = new HashMap<String, Integer>() {{
		put("a", 0);
		put("b", 1);
		put("c", 2);
		put("1", 0);
		put("2", 1);
		put("3", 2);
	}};

	public Board(Game game) {
		this.game = game;
		this.allSquares = new ArrayList<>();
		state = new Square[3][3];
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				Square sq = new Square(new Point2D(row, col));
				state[row][col] = sq;
				allSquares.add(sq);
			}
		}
	}

	public List<List<Square>> getAllLines() {
		List<List<Square>> allLines = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			allLines.add(getRow(i));
			allLines.add(getColumn(i));
		}
		allLines.add(getDiagonal(true));
		allLines.add(getDiagonal(false));
		return allLines;
	}

	public List<Square> getRow(int row) {
		return new ArrayList<>(Arrays.asList(state[row]));
	}

	public List<Square> getColumn(int col) {
		return new ArrayList<>(Arrays.asList(state[0][col], state[1][col], state[2][col]));
	}

	public List<Square> getDiagonal(boolean rising) {
		return rising
				? new ArrayList<>(Arrays.asList(state[0][2], state[1][1], state[2][0]))
				: new ArrayList<>(Arrays.asList(state[0][0], state[1][1], state[2][2]));
	}

	public boolean isValidMoveRepresentation(String unparsedInput) {
		return unparsedInput.matches(VALID_TURN_PATTERN);
	}

	public boolean isPossibleMove(Point2D p) {
		return getSquareByPoint(p).isEmpty();
	}

	public Point2D parseStringToPoint(String input) {
		int row = reprToIndexes.get(input.substring(0, 1));
		int col = reprToIndexes.get(input.substring(1));
		return new Point2D(row, col);
	}

	public void markWith(Point2D p, Mark m) {
		if (!isPossibleMove(p)) {
			throw new IllegalArgumentException(
					String.format("(%d,%d) is now a possible move", p.getRow(), p.getColumn()));
		}
		getSquareByPoint(p).markWith(m);
	}

	public Square getSquareByPoint(Point2D p) {
		return this.state[p.getRow()][p.getColumn()];
	}

	public boolean isGameOver() {
		return this.isWin() || isFilled();
	}

	public boolean isWin() {
		for (List<Square> line : getAllLines()) {
			Square first = line.get(0);
			if (!first.isEmpty() && line.stream().map(Square::getMark).distinct().count() == 1) {
				return true;
			}
		}
		return false;
	}

	public Mark whoWon() {
		for (List<Square> line : getAllLines()) {
			Square first = line.get(0);
			if (!first.isEmpty() && line.stream().map(Square::getMark).distinct().count() == 1) {
				return line.stream().map(Square::getMark).distinct().findFirst().get();
			}
		}
		return Mark.EMPTY;
	}

	public boolean isFilled() {
		for (List<Square> line : getAllLines()) {
			for (Square sq : line) {
				if (sq.isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public String toString() {
		String rowWithMarks = " %s | %s | %s";
		String rowWithDashes = "  ---------- ";
		return String.format(
				String.format("  1   2   3\nA%s\n%s\nB%s\n%s\nC%s\n", rowWithMarks, rowWithDashes, rowWithMarks,
						rowWithDashes, rowWithMarks), state[0][0].getMark(), state[0][1].getMark(),
				state[0][2].getMark(), state[1][0].getMark(), state[1][1].getMark(), state[1][2].getMark(),
				state[2][0].getMark(), state[2][1].getMark(), state[2][2].getMark());

	}

}
