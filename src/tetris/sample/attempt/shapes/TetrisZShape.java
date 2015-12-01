package tetris.sample.attempt.shapes;

import java.awt.Color;

public class TetrisZShape extends TetrisShape{
	public static final int Z = 6;
	public static final Color COLOR = Color.RED;
	
	public TetrisZShape() {
		super(Color.RED);
		init();
	}

	@Override
	protected void init() {
		int coordinates[][] = new int[][]{
				{ -1, 0, 0, 1}, 
				{ 0, 0, -1, -1}
			};
		setCoordinates(coordinates);
	}
	
	@Override
	public int getID() {
		return Z;
	}
}
