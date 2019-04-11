package com.example.demo;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

@Component
public class Console {

	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		do {
			playHumanVsAlgorithmGame();
//			playHumanVsHuman();
//			playAlgorithmVsAlgorithmGame();
			System.out.println("Do you want to play another game? [y/n]");
		} while (scanner.next().toLowerCase().equals("y"));
	}

	private static void playHumanVsHuman() {
		Game game = new Game(new HumanPlayer(), new HumanPlayer());
		game.play();

	}

	private static void playAlgorithmVsAlgorithmGame() {
		Game game = new Game(new AlgorithmPlayer(), new AlgorithmPlayer());
		game.play();

	}

	private static void playHumanVsAlgorithmGame() {
		Stack<Player> twoPlayers = new Stack<>();
		twoPlayers.add(new HumanPlayer());
		twoPlayers.add(new AlgorithmPlayer());
		Collections.shuffle(twoPlayers);
		Game game = new Game(twoPlayers.pop(), twoPlayers.pop());
		game.play();
	}

}
