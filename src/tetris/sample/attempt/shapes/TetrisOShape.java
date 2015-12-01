package tetris.sample.attempt.shapes;

import java.awt.Color;

public class TetrisOShape extends TetrisShape{
	public static final int O = 3;
	public static final Color COLOR = Color.YELLOW;
	
	public TetrisOShape() {
		super(Color.YELLOW);
		init();
	}

	@Override
	protected void init() {
		int coordinates[][] = new int[][]{
				{ 0, 0, 1, 1}, 
				{ 0, -1, 0, -1}
			};
		setCoordinates(coordinates);
	}

	@Override
	public void rotateRight(int xShape, int yShape, int[][] grid) {
		//nothing happens
	}
	
	@Override
	public void rotateLeft(int xShape, int yShape, int[][] grid) {
		//nothing happens
	}
	
	@Override
	public int getID() {
		return O;
	}
}
