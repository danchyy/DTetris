package tetris.sample.attempt;

import java.util.Random;

import tetris.sample.attempt.shapes.TetrisIShape;
import tetris.sample.attempt.shapes.TetrisJShape;
import tetris.sample.attempt.shapes.TetrisLShape;
import tetris.sample.attempt.shapes.TetrisSShape;
import tetris.sample.attempt.shapes.TetrisShape;
import tetris.sample.attempt.shapes.TetrisOShape;
import tetris.sample.attempt.shapes.TetrisTShape;
import tetris.sample.attempt.shapes.TetrisZShape;

public class TetrisShapeGenerator {

	/**
	 * Random generator
	 */
	private Random rand;

	private TetrisShape current;

	public TetrisShapeGenerator() {
		rand = new Random();
	}

	public void generateShape() {
		int number = rand.nextInt(7);
		switch (number) {
		case TetrisIShape.I:
			current = new TetrisIShape();
			break;
		case TetrisJShape.J:
			current = new TetrisJShape();
			break;
		case TetrisLShape.L:
			current = new TetrisLShape();
			break;
		case TetrisOShape.O:
			current = new TetrisOShape();
			break;
		case TetrisSShape.S:
			current = new TetrisSShape();
			break;
		case TetrisTShape.T:
			current = new TetrisTShape();
			break;
		case TetrisZShape.Z:
			current = new TetrisZShape();
			break;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	public TetrisShape getCurrent() {
		if (current == null) {
			generateShape();
		}
		return current;
	}
	

}
