package tetris.sample.attempt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class TetrisFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Instance of a board which holds all blocks etc.
	 */
	private TetrisBoard board;
//	private static TetrisScoreCounter score;

	/**
	 * Constructor of our frame for the game.
	 */
	public TetrisFrame() {

		initGui();
	}

	/**
	 * Initializes the interface of tetris.
	 */
	private void initGui() {
		setLayout(new BorderLayout());
		setCloseOperations();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		board = new TetrisBoard(this);
		setTitle("Tetris");
		setLocationRelativeTo(null);
		getContentPane().add(board, BorderLayout.CENTER);
//		score = new TetrisScoreCounter();
//		getContentPane().add(score, BorderLayout.PAGE_END);
		
	}

	/**
	 * Handling the closing of a window.
	 */
	public void setCloseOperations() {
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				board.onExit();
			}

		});
	}

	/**
	 * Main method which starts the game.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				TetrisFrame frame = new TetrisFrame();
				frame.setVisible(true);
				Insets insets = frame.getInsets();
				int horizontalInsets = insets.left + insets.right;
				int verticalInsets = insets.top + insets.bottom;
				frame.setMinimumSize(
						new Dimension(TetrisBoard.WIDTH + horizontalInsets 
						+ TetrisNextShape.BOARD_DISTANCE + TetrisNextShape.FRAME_WIDTH + TetrisNextShape.END_DISTANCE
						, TetrisBoard.HEIGHT + verticalInsets));
				frame.setSize(frame.getMinimumSize());
				frame.setResizable(false);
			}
		});
	}

}
