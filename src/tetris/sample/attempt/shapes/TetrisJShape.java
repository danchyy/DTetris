package tetris.sample.attempt.shapes;

import java.awt.Color;

public class TetrisJShape extends TetrisShape {
	public static final int J = 1;
	public static final Color COLOR = Color.BLUE;

	public TetrisJShape() {
		super(Color.BLUE);
		init();
	}

	@Override
	protected void init() {
		int coordinates[][] = new int[][]{
				{ 0, 0, 0, -1}, 
				{ 1, 0, -1, -1}
			};
		setCoordinates(coordinates);
	}

	@Override
	public int getID() {
		return J;
	}
}
