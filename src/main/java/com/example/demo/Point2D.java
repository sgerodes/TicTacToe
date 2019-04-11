package com.example.demo;

import lombok.Data;

@Data
public class Point2D {

	private int row;
	private int column;

	public Point2D(int row, int column) {
		this.row = row;
		this.column = column;
	}
}
