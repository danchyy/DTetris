package tetris.sample.attempt;

import java.awt.Color;
import java.awt.Graphics2D;

import tetris.sample.attempt.shapes.TetrisShape;

public class TetrisNextShape {

	public static final int FRAME_WIDTH = TetrisBoard.BLOCK_WIDTH * 4;
	public static final int FRAME_HEIGHT = TetrisBoard.BLOCK_HEIGHT * 4;

	public static final int BOARD_DISTANCE = TetrisBoard.BLOCK_WIDTH * 2;
	public static final int END_DISTANCE = TetrisBoard.BLOCK_WIDTH * 2;

	public void paint(int x, int y, Graphics2D g, TetrisShape first, TetrisShape second) {

		int counter = 0;
		g.setColor(TetrisBoard.GRID_COLOR);
		g.drawRect(x, y, FRAME_HEIGHT, FRAME_HEIGHT);
		int xStart = x + 2 * TetrisBoard.BLOCK_WIDTH;
		int yStart = y + 1 * TetrisBoard.BLOCK_HEIGHT;
		int widthCount = FRAME_WIDTH / TetrisBoard.BLOCK_WIDTH;
		int heightCount = FRAME_HEIGHT / TetrisBoard.BLOCK_HEIGHT;
		
		while (counter < 2) {
			g.setColor(TetrisBoard.GRID_COLOR);
			g.drawRect(x, y, FRAME_HEIGHT, FRAME_HEIGHT);
			for (int i = 0; i <= widthCount; i++) {
				g.drawLine(i * TetrisBoard.BLOCK_WIDTH + x, y, i * TetrisBoard.BLOCK_WIDTH + x, 
						y + FRAME_HEIGHT);
			}
			for (int j = 0; j <= heightCount; j++) {
				if (j == 0 && counter == 1) {
					g.setColor(Color.WHITE);
					g.drawLine(x, j * TetrisBoard.BLOCK_HEIGHT + y, x + FRAME_WIDTH, 
							j * TetrisBoard.BLOCK_HEIGHT + y);
					g.setColor(TetrisBoard.GRID_COLOR);
				} else {
					g.drawLine(x, j * TetrisBoard.BLOCK_HEIGHT + y, x + FRAME_WIDTH, 
							j* TetrisBoard.BLOCK_HEIGHT + y);
				}

			}
			TetrisShape currentShape = null;
			if (counter == 0) {
				currentShape = first;
			} else {
				currentShape = second;
			}
			Color currentColor = TetrisBoard.shapeColors.get(currentShape.getID());
			g.setColor(currentColor);
			currentShape.drawShape(g, xStart, yStart + counter * FRAME_HEIGHT, currentColor);
			y += FRAME_HEIGHT;
			counter++;
		}
	}

}
