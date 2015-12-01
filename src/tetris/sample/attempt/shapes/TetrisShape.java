package tetris.sample.attempt.shapes;

import java.awt.Color;
import java.awt.Graphics2D;

import tetris.sample.attempt.TetrisBoard;

public abstract class TetrisShape {

	private int coordinates[][];
	private Color color;
	private int finalCoordinates[][];

	public TetrisShape(Color color) {
		super();
		this.color = color;
		finalCoordinates = new int[2][4];
	}

	protected abstract void init();
	
	public abstract int getID();

	public int[][] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(int[][] coordinates) {
		this.coordinates = coordinates;
	}

	public Color getColor() {
		return color;
	}

	public void draw(Graphics2D g, int startX, int startY) {
		g.setColor(color);
		for (int i = 0; i < coordinates[0].length; i++) {
			int dx = coordinates[0][i];
			int dy = coordinates[1][i];
			int blockX = startX + dx * TetrisBoard.BLOCK_WIDTH;
			int blockY = startY - dy * TetrisBoard.BLOCK_HEIGHT;
			if (blockX < TetrisBoard.startingX || blockY < TetrisBoard.startingY) {
				continue;
			}
			if (blockX > TetrisBoard.startingX + TetrisBoard.BLOCK_WIDTH * 9
					|| blockY > TetrisBoard.startingY + TetrisBoard.BLOCK_HEIGHT * 19) {
				continue;
			}
			g.fillRect(blockX + 1, blockY + 1, TetrisBoard.BLOCK_WIDTH - 1, TetrisBoard.BLOCK_HEIGHT - 1);
		}
	}
	
	public void drawShape(Graphics2D g, int x, int y, Color color) {
		for (int i = 0; i < coordinates[0].length; i++) {
			int dx = coordinates[0][i];
			int dy = coordinates[1][i];
			int blockX = x + dx * TetrisBoard.BLOCK_WIDTH;
			int blockY = y - dy * TetrisBoard.BLOCK_HEIGHT;
			g.fillRect(blockX + 1, blockY + 1, TetrisBoard.BLOCK_WIDTH - 1, TetrisBoard.BLOCK_HEIGHT - 1);
		}
	}


	public synchronized boolean setFinalCoordinatesAndCheckEnd(int xShape, int yShape, int[][] grid, int shapeID) {
		for (int i = 0; i < coordinates[0].length; i++) {
			int dx = coordinates[0][i];
			int dy = coordinates[1][i];
			int blockX = xShape + dx * TetrisBoard.BLOCK_WIDTH - TetrisBoard.startingX;
			int blockY = yShape - dy * TetrisBoard.BLOCK_HEIGHT - TetrisBoard.startingY;
			int col = blockX / TetrisBoard.BLOCK_WIDTH;
			int row = blockY / TetrisBoard.BLOCK_HEIGHT;
			if (row < 0 || col < 0) {
				return true;
			}
			grid[row][col] = shapeID;
			finalCoordinates[0][i] = row;
			finalCoordinates[1][i] = col;
		}
		return false;
	}


	public void rotateRight(int xShape, int yShape, int[][] grid) {
		int originals[][] = new int[4][4];
		for (int i = 0; i < coordinates[0].length; i++) {
			originals[0][i] = coordinates[0][i];
			originals[1][i] = coordinates[1][i];
		}
		for (int i = 0; i < coordinates[0].length; i++) {
			int x = coordinates[0][i] * -1;
			int y = coordinates[1][i];
			coordinates[0][i] = y;
			coordinates[1][i] = x;
			if (checkLeftEndForRotation(xShape, yShape) || checkRightEndForRotation(xShape, yShape)
					|| checkOtherShapes(xShape, yShape, grid) || checkBottomOfBoard(xShape, yShape)) {
				coordinates[0][i] = x * -1;
				coordinates[1][i] = y;
				coordinates = originals;
				break;
			}
		}
	}
	
	public void rotateLeft(int xShape, int yShape, int[][] grid) {
		int originals[][] = new int[4][4];
		for (int i = 0; i < coordinates[0].length; i++) {
			originals[0][i] = coordinates[0][i];
			originals[1][i] = coordinates[1][i];
		}
		for (int i = 0; i < coordinates[0].length; i++) {
			int x = coordinates[0][i];
			int y = coordinates[1][i] * -1;
			coordinates[0][i] = y;
			coordinates[1][i] = x;
			if (checkLeftEndForRotation(xShape, yShape) || checkRightEndForRotation(xShape, yShape)
					|| checkOtherShapes(xShape, yShape, grid) || checkBottomOfBoard(xShape, yShape)) {
				coordinates[0][i] = x;
				coordinates[1][i] = y * -1;
				coordinates = originals;
				break;
			}
		}
	}

	public boolean checkBottomOfBoard(int x, int y) {
		for (int i = 0; i < coordinates[0].length; i++) {
			int dy = coordinates[1][i];
			int blockY = y - dy * TetrisBoard.BLOCK_HEIGHT;
			if (blockY >= TetrisBoard.startingY + TetrisBoard.BLOCK_HEIGHT * 19) {
				return true;
			}
		}
		return false;
	}

	public boolean checkLeftOfBoard(int x, int y) {
		for (int i = 0; i < coordinates[0].length; i++) {
			int dx = coordinates[0][i];
			int blockX = x + dx * TetrisBoard.BLOCK_WIDTH;
			if (blockX <= TetrisBoard.startingX) {
				return true;
			}
		}
		return false;
	}

	public boolean checkRightOfBoard(int x, int y) {
		for (int i = 0; i < coordinates[0].length; i++) {
			int dx = coordinates[0][i];
			int blockX = x + dx * TetrisBoard.BLOCK_WIDTH;
			if (blockX >= TetrisBoard.startingX + TetrisBoard.BLOCK_WIDTH * 9) {
				return true;
			}
		}
		return false;
	}

	private boolean checkRightEndForRotation(int x, int y) {
		for (int i = 0; i < coordinates[0].length; i++) {
			int dx = coordinates[0][i];
			int blockX = x + dx * TetrisBoard.BLOCK_WIDTH;
			if (blockX > TetrisBoard.startingX + TetrisBoard.BLOCK_WIDTH * 9) {
				return true;
			}
		}
		return false;
	}

	private boolean checkLeftEndForRotation(int x, int y) {
		for (int i = 0; i < coordinates[0].length; i++) {
			int dx = coordinates[0][i];
			int blockX = x + dx * TetrisBoard.BLOCK_WIDTH;
			if (blockX < TetrisBoard.startingX) {
				return true;
			}
		}
		return false;
	}

	public synchronized boolean checkOtherShapes(int xShape, int yShape, int[][] grid) {

		for (int i = 0; i < coordinates[0].length; i++) {
			int x = coordinates[0][i];
			int y = coordinates[1][i];
			int currentX = (xShape - TetrisBoard.startingX) / TetrisBoard.BLOCK_WIDTH;
			int currentY = (yShape - TetrisBoard.startingY) / TetrisBoard.BLOCK_HEIGHT;
			int col = currentX + x;
			int row = currentY - y;
			if (col < 0 || row < 0 || row > 19 || col > 9) {
				continue;
			}
			if (grid[row][col] != -1) {
				return true;
			}
		}
		return false;
	}
	
}
