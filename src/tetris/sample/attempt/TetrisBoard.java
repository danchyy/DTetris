package tetris.sample.attempt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import tetris.sample.attempt.shapes.TetrisIShape;
import tetris.sample.attempt.shapes.TetrisJShape;
import tetris.sample.attempt.shapes.TetrisLShape;
import tetris.sample.attempt.shapes.TetrisOShape;
import tetris.sample.attempt.shapes.TetrisSShape;
import tetris.sample.attempt.shapes.TetrisShape;
import tetris.sample.attempt.shapes.TetrisTShape;
import tetris.sample.attempt.shapes.TetrisZShape;

public class TetrisBoard extends JPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Width of the board
	 */
	public static final int WIDTH = 200;
	/**
	 * Height of the board
	 */
	public static final int HEIGHT = 400;
	/**
	 * Width of the block
	 */
	public static final int BLOCK_WIDTH = 20;
	/**
	 * height of the block
	 */
	public static final int BLOCK_HEIGHT = 20;

	/**
	 * Frame that contains this board
	 */
	private TetrisFrame frame;

	public static final Map<Integer, Color> shapeColors = new HashMap<Integer, Color>();
	static {
		shapeColors.put(TetrisIShape.I, TetrisIShape.COLOR);
		shapeColors.put(TetrisJShape.J, TetrisJShape.COLOR);
		shapeColors.put(TetrisLShape.L, TetrisLShape.COLOR);
		shapeColors.put(TetrisOShape.O, TetrisOShape.COLOR);
		shapeColors.put(TetrisSShape.S, TetrisSShape.COLOR);
		shapeColors.put(TetrisTShape.T, TetrisTShape.COLOR);
		shapeColors.put(TetrisZShape.Z, TetrisZShape.COLOR);
	}

	/**
	 * Grid that stores informations about the shapes that are placed on the
	 * grid already.
	 */
	private int grid[][];

	/**
	 * Starting x coordinate for frame.
	 */
	public static int startingX = 5;
	/**
	 * Starting y coordinate for frame.
	 */
	public static int startingY = 5;

	private static final int xShapeStart = startingX + BLOCK_WIDTH * 4;

	private static final int yShapeStart = startingY;

	/**
	 * Starting x coordinate of shapes that will land in our board.
	 */
	private volatile int xShape = xShapeStart;
	/**
	 * Starting y coordinate of shapes that will land in our board.
	 */
	private volatile int yShape = yShapeStart;

	/**
	 * Variable which tells us when the thread shuts down, and therefore the
	 * game ends.
	 */
	private volatile boolean shutdown = false;

	/**
	 * Variable which tells us when the game is running.
	 */
	private volatile boolean running = true;

	/**
	 * Shape which is displayed on the screen at the moment.
	 */
	private TetrisShape currentShape = null;
	
	/**
	 * Shape which will be displayed on the screen.
	 */
	private TetrisShape nextShape = null;
	/**
	 * Second shape that follows
	 */
	private TetrisShape secondShape;

	/**
	 * Overall size of our board horizontally
	 */
	public static final int BOARD_END_HORIZONTAL = 200;

	/**
	 * Overall size of our board vertically
	 */
	public static final int BOARD_END_VERTICAL = 401;
	/**
	 * Generator of our shapes.
	 */
	private TetrisShapeGenerator shapeGenerator;
	/**
	 * Color of the grid
	 */
	public static final Color GRID_COLOR = new Color(61, 91, 161);
	/**
	 * Frame which shows what is the next shape
	 */
	private TetrisNextShape nextShapeFrame;
	/**
	 * Frame which shows what is the score
	 */
	private TetrisScoreCounter scoreCounter;
	/**
	 * Thread which is in charge of animations
	 */
	private Thread thread;
	/**
	 * If the game is restarted.
	 */
	private volatile boolean isReset;
	
	/**
	 * Instructions for the game.
	 */
	private TetrisInstructions instructions;
	
	
	public TetrisBoard(TetrisFrame parent) {
		this.frame = parent;
		shapeGenerator = new TetrisShapeGenerator();
		addKeyListener(new TetrisInputListener());
		setFocusable(true);
		grid = new int[20][10];
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				grid[i][j] = -1;
			}
		}
		nextShapeFrame = new TetrisNextShape();
		scoreCounter = new TetrisScoreCounter();
		isReset = false;
		instructions = new TetrisInstructions();
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int widthCount = WIDTH / BLOCK_WIDTH;
		int heightCount = HEIGHT / BLOCK_HEIGHT;

		g2d.setColor(Color.BLACK);
		g2d.setBackground(Color.BLACK);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setColor(GRID_COLOR);
		for (int i = 0; i <= widthCount; i++) {
			g2d.drawLine(i * BLOCK_WIDTH + startingX, startingY, i * BLOCK_WIDTH + startingX, startingY
					+ HEIGHT);
		}
		for (int j = 0; j <= heightCount; j++) {
			g2d.drawLine(startingX, j * BLOCK_HEIGHT + startingY, startingX + WIDTH, j * BLOCK_HEIGHT
					+ startingY);
		}
		nextShapeFrame.paint(2 * startingX + WIDTH + TetrisNextShape.BOARD_DISTANCE, startingY, g2d, nextShape, secondShape);
		scoreCounter.paint(startingX + WIDTH + TetrisScoreCounter.BOARD_DISTANCE , startingY, g2d);
		instructions.paint(g2d, startingX + WIDTH + TetrisInstructions.BOARD_DISTANCE, startingY);
		draw(g2d);
		currentShape.draw(g2d, xShape, yShape);

	}
	
	/**
	 * Method which draws the board and shapes that already fell into their final spot
	 * @param g2d
	 */
	private void draw(Graphics2D g2d) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == -1) {
					continue;
				}
				g2d.setColor(shapeColors.get(grid[i][j]));
				g2d.fillRect(j * BLOCK_WIDTH + startingX + 1, i * BLOCK_HEIGHT + startingY + 1,
						BLOCK_WIDTH - 1, BLOCK_HEIGHT - 1);
			}
		}
	}

	@Override
	public void addNotify() {
		super.addNotify();
		/*
		 * Creates a new thread which is in charge of keeping the flow of the
		 * game. That thread is started immediately.
		 */
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		/*
		 * Condition which keeps the thread alive.
		 */
		while (!shutdown) {
			/*
			 * Condition which keeps the flow of the game.
			 */
			while (running) {
				repaint();
				/*
				 * Creation of new shape
				 */
				if (currentShape == null) {
					shapeGenerator.generateShape();
					currentShape = shapeGenerator.getCurrent();
					shapeGenerator.generateShape();
					nextShape = shapeGenerator.getCurrent();
					shapeGenerator.generateShape();
					secondShape = shapeGenerator.getCurrent();
				}

				/*
				 * Delay of block "drop"
				 */
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					System.out.println("Interrupted: " + e.getMessage());
				}

				// Checks if the shape conflicts with other shapes bellow him
				if (currentShape.checkOtherShapes(xShape, yShape + BLOCK_HEIGHT, grid)
						|| currentShape.checkBottomOfBoard(xShape, yShape)) {
					if (isReset) {
						isReset = false;
					}
					if (currentShape.setFinalCoordinatesAndCheckEnd(xShape, yShape, grid,
							currentShape.getID())) {
						gameOver();
						break;
					}
//					currentShape = nextShape;
//					shapeGenerator.generateShape();
//					nextShape = secondShape;
//					secondShape = shapeGenerator.getCurrent();
//					xShape = xShapeStart;
//					yShape = yShapeStart - 2 * BLOCK_HEIGHT;
//					boolean cleared = checkAndCleanBoard();
//					while (cleared) {
//						cleared = checkAndCleanBoard();
//					}
//					if (checkIfBoardIsFull()) {
//						gameOver();
//					}
					initializeNew();
				} else {
					// If shape is in valid position it is moved one block down.
					yShape += BLOCK_HEIGHT;
				}
			}
		}
	}

	private boolean checkIfBoardIsFull() {
		int startX = (xShapeStart - startingX) / BLOCK_WIDTH;
		if (grid[0][startX] == -1) {
			return false;
		}
		return true;
	}

	private boolean checkAndCleanBoard() {
		boolean fullRow = true;
		int rowsCleared = 0;
		for (int i = grid.length - 1; i > 0; i--) {
			fullRow = true;
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == -1) {
					fullRow = false;
					break;
				}
			}
			if (fullRow) {
				rowsCleared++;
				for (int k = i; k > 0; k--) {
					for (int z = 0; z < grid[k].length; z++) {
						grid[k][z] = grid[k - 1][z];
					}
				}
				i++;
			}
		}
		repaint();
		scoreCounter.increment(rowsCleared);
		return fullRow;
	}

	/**
	 * Stopping the thread in charge of animations.
	 */
	public void onExit() {
		shutdown = true;
		running = false;
	}

	/**
	 * Moves the current shape by one block to the right
	 */
	public void moveRight() {
		// Checks if there is a shape to the right
		if (currentShape.checkOtherShapes(xShape + BLOCK_WIDTH, yShape, grid)) {
			return;
		}
		// Checks the end of the board
		if (currentShape.checkRightOfBoard(xShape, yShape) || !running) {
			return;
		}
		xShape += BLOCK_WIDTH;
	}

	/**
	 * Moves the current shape by one block to the left
	 */
	public void moveLeft() {
		// Checks if there is a shape to the left
		if (currentShape.checkOtherShapes(xShape - BLOCK_WIDTH, yShape, grid)) {
			return;
		}
		// Checks if there is the end of the board to the left
		if (currentShape.checkLeftOfBoard(xShape, yShape) || !running) {
			return;
		}
		xShape -= BLOCK_WIDTH;
	}

	/**
	 * Moves the current shape by one block down
	 */
	public void moveDown() {
		// Checks if there is another shape bellow
		if (currentShape.checkOtherShapes(xShape, yShape + BLOCK_HEIGHT, grid)) {
			if (currentShape.setFinalCoordinatesAndCheckEnd(xShape, yShape, grid, currentShape.getID())) {
				gameOver();
				return;
			}
//			currentShape = nextShape;
//			shapeGenerator.generateShape();
//			nextShape = secondShape;
//			secondShape = shapeGenerator.getCurrent();
//			xShape = xShapeStart;
//			yShape = yShapeStart - 2 * BLOCK_HEIGHT;
//			boolean cleared = checkAndCleanBoard();
//			while (cleared) {
//				cleared = checkAndCleanBoard();
//			}
//			if (checkIfBoardIsFull()) {
//				gameOver();
//			}
			initializeNew();
			return;
		}
		// Checks for the bottom of the board
		if (currentShape.checkBottomOfBoard(xShape, yShape) || !running) {
			return;
		}
		yShape += BLOCK_HEIGHT;
	}
	
	private void initializeNew() {
		currentShape = nextShape;
		shapeGenerator.generateShape();
		nextShape = secondShape;
		secondShape = shapeGenerator.getCurrent();
		xShape = xShapeStart;
		yShape = yShapeStart - 2 * BLOCK_HEIGHT;
		boolean cleared = checkAndCleanBoard();
		while (cleared) {
			cleared = checkAndCleanBoard();
		}
		if (checkIfBoardIsFull()) {
			gameOver();
		}
	}

	/**
	 * Listener which checks for the user input through the keyboard.
	 * 
	 * @author Daniel Bratulić
	 *
	 */
	private class TetrisInputListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			switch (keyCode) {
			case KeyEvent.VK_RIGHT:
				moveRight();
				break;
			case KeyEvent.VK_LEFT:
				moveLeft();
				break;
			case KeyEvent.VK_M:
				if (!running) {
					return;
				}
				currentShape.rotateRight(xShape, yShape, grid);
				break;
			case KeyEvent.VK_N:
				if (!running) {
					return;
				}
				currentShape.rotateLeft(xShape, yShape, grid);
				break;
			case KeyEvent.VK_DOWN:
				moveDown();
				break;
			case KeyEvent.VK_P:
				if (running) {
					running = false;
				} else {
					running = true;
				}
				break;
			default:
				break;
			}
			if (running) {
				repaint();
			}
		}
	}

	public synchronized void gameOver() {
		if (isReset) {
			return;
		}
		running = false;
		int response = JOptionPane.showConfirmDialog(this, "Would you like to play again?", "Game over!",
				JOptionPane.YES_NO_OPTION);
		if (response != JOptionPane.YES_OPTION) {
			shutdown = true;
			frame.dispose();
		} else {
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					grid[i][j] = -1;
				}
			}
			currentShape = null;
			scoreCounter.reset();
			running = true;
		}
		isReset = true;
	}

}
