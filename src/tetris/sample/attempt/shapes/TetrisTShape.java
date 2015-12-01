package tetris.sample.attempt.shapes;

import java.awt.Color;

import tetris.sample.attempt.TetrisBoard;

public class TetrisTShape extends TetrisShape{
	public static final int T = 5;
	public static final Color COLOR = Color.MAGENTA;
	
	public TetrisTShape() {
		super(Color.MAGENTA);
		init();
	}
	
	@Override
	protected void init() {
		int coordinates[][] = new int[][]{
				{ 0, -1, 0, 1}, 
				{ 1, 0, 0, 0}
			};
		setCoordinates(coordinates);
	}
	
	@Override
	public boolean checkBottomOfBoard(int x, int y) {
		int coordinates[][] = getCoordinates();
		for (int i = 0; i < coordinates[0].length; i++) {
			int dy = coordinates[1][i];
			int blockY = y - dy * TetrisBoard.BLOCK_HEIGHT;
			if (blockY >= TetrisBoard.startingY + TetrisBoard.BLOCK_HEIGHT * 19){
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public int getID() {
		return T;
	}

}
