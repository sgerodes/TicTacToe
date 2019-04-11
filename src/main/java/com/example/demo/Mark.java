package com.example.demo;

enum Mark {
	CROSS, NOUGHT, EMPTY;

	@Override
	public String toString() {
		switch(this) {
			case EMPTY: return " ";
			case CROSS: return "X";
			case NOUGHT: return "O";
			default: throw new IllegalArgumentException();
		}
	}

	public String representation(){
		switch(this) {
			case EMPTY: return "EMPTY";
			case CROSS: return "CROSS";
			case NOUGHT: return "NOUGHT";
			default: throw new IllegalArgumentException();
		}
	}
}
