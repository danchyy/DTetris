package tetris.sample.attempt.shapes;

import java.awt.Color;

public class TetrisIShape extends TetrisShape {
	public static final int I = 0;
	public static final Color COLOR = Color.CYAN;

	public TetrisIShape() {
		super(Color.CYAN);
		init();
	}

	@Override
	protected void init() {
		int coordinates[][] = new int[][] {
				{ 0, 0, 0, 0 }, 
				{ 1, 0, -1, -2 } 
		};
		setCoordinates(coordinates);
	}


	@Override
	public int getID() {
		return I;
	}
}
