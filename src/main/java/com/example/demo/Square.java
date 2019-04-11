package com.example.demo;

import lombok.Data;

@Data
public class Square {

	Point2D coordinates;
	Mark mark = Mark.EMPTY;

	public Square(Point2D coordinates) {
		this.coordinates = coordinates;
	}

	public Square( Point2D coordinates, Mark mark) {
		this.coordinates = coordinates;
		this.mark = mark;
	}

	public boolean isEmpty(){
		return this.mark == Mark.EMPTY;
	}

	public void markWith(Mark m){
		this.mark = m;
	}
}
