package tetris.sample.attempt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class TetrisInstructions {
	public int down;
	public int left;
	public int right;
	public int rotateRight;
	public int rotateLeft;
	public int pause;

	public static final int BOARD_DISTANCE = TetrisBoard.BLOCK_WIDTH * 2;
	private static final int FONT_SIZE = 15;

	public TetrisInstructions() {
		this.down = KeyEvent.VK_DOWN;
		this.left = KeyEvent.VK_LEFT;
		this.right = KeyEvent.VK_RIGHT;
		this.rotateLeft = KeyEvent.VK_N;
		this.rotateRight = KeyEvent.VK_M;
		this.pause = KeyEvent.VK_P;
	}

	public void paint(Graphics2D g, int x, int y) {
		int yStart = (int) (y + TetrisNextShape.FRAME_HEIGHT * 2 + TetrisScoreCounter.NEXT_SHAPE_DISTANCE
				+ TetrisScoreCounter.FONT_SIZE + TetrisScoreCounter.FONT_SIZE * 0.5);
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, FONT_SIZE));
		g.drawString("Move Right: " + KeyEvent.getKeyText(right), x, yStart);
		yStart += FONT_SIZE * 2;
		g.drawString("Move Left: " + KeyEvent.getKeyText(left), x, yStart);
		yStart += FONT_SIZE * 2;
		g.drawString("Move Down: " + KeyEvent.getKeyText(down), x, yStart);
		yStart += FONT_SIZE * 2;
		g.drawString("Rotate Right: " + KeyEvent.getKeyText(rotateRight), x, yStart);
		yStart += FONT_SIZE * 2;
		g.drawString("Rotate Left: " + KeyEvent.getKeyText(rotateLeft), x, yStart);
		yStart += FONT_SIZE * 2;
		g.drawString("Pause: " + KeyEvent.getKeyText(pause), x, yStart);
	}

}
