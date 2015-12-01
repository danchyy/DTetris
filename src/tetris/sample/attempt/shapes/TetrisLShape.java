package tetris.sample.attempt.shapes;

import java.awt.Color;

public class TetrisLShape extends TetrisShape{
	public static final int L = 2;
	public static final Color COLOR = Color.ORANGE;
	
	public TetrisLShape() {
		super(Color.ORANGE);
		init();
	}

	@Override
	protected void init() {
		int coordinates[][] = new int[][]{
			{ 0, 0, 0, 1}, 
			{ 1, 0, -1, -1}
		};
		setCoordinates(coordinates);
	}

	@Override
	public int getID() {
		return L;
	}
}
