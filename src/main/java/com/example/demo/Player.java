package com.example.demo;

import lombok.Data;

@Data
public abstract class Player {

	public Mark mark;

	abstract public void makeTurn(Board board);


}
