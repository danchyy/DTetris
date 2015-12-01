package tetris.sample.attempt.shapes;

import java.awt.Color;

public class TetrisSShape extends TetrisShape{
	public static final int S = 4;
	public static final Color COLOR = Color.GREEN;
	
	public TetrisSShape() {
		super(Color.GREEN);
		init();
	}

	@Override
	protected void init() {
		int coordinates[][] = new int[][]{
				{ 1, 0, 0, -1}, 
				{ 0, 0, -1, -1}
			};
		setCoordinates(coordinates);
	}
	
	@Override
	public int getID() {
		return S;
	}

}
