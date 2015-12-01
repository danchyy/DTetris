package tetris.sample.attempt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class TetrisScoreCounter {
	
	/**
	 * Score of the game
	 */
	private int score;
	
	public static final int COUNTER_WIDTH = 5 * TetrisBoard.BLOCK_WIDTH;
	public static final int COUNTER_HEIGHT = 2 * TetrisBoard.BLOCK_HEIGHT;
	
	public static final int BOARD_DISTANCE = TetrisBoard.BLOCK_WIDTH * 2;
	
	public static final int NEXT_SHAPE_DISTANCE = TetrisBoard.BLOCK_HEIGHT * 2;
	
	public static final int FONT_SIZE = 20;
	
	public TetrisScoreCounter() {
		score = 0;
	}
	
	
	public void paint(int x, int y, Graphics2D g) {
		g.setColor(Color.YELLOW);
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, FONT_SIZE));
		g.drawString("Score: " + score, x, y + NEXT_SHAPE_DISTANCE + TetrisNextShape.FRAME_HEIGHT * 2);
	}
	
	public void increment(int number) {
		score += number;
	}
	
	public void reset() {
		score = 0;
	}
	

}
