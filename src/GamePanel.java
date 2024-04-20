package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
	private static final int SCREEN_WIDTH = 600;
	private static final int SCREEN_HEIGHT = 600;
	 static final int UNIT_SIZE = 25;
	private static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	private static int DELAY = 200;
     final int snakeX[] = new int[GAME_UNITS]; // x cordinate of body of the snake
	 final int snakeY[] = new int[GAME_UNITS]; // y coordinate of body of the snake
	int snakeBodyParts = 4;
	int score = 0;
	int mouseX;
	int mouseY; // random position of apple
	int bigMouseX ;
	int bigMouseY;
	int countMouse=0;
	char direction = 'R'; // snake begin to flow in right direction!
	boolean running = false;
	Timer timer;
	Random random; // To generate random number
	BigMouse bgMouse;
	NormalMouse nrMouse;

	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.BLACK); // To set BGC
		this.setFocusable(true); // By default , It is false we have set true
		this.addKeyListener(new MyKeyAdapter());// For keyBoardInput
		startGame();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	// Method for draw Snake.
	public void draw(Graphics g) {

		if (running) {
			if(countMouse==5) {
				 bgMouse = new BigMouse(this);
				 bgMouse.paintComponent(g);
			
			}
			else {
				 nrMouse = new NormalMouse(this);
				 nrMouse.paintComponent(g);
				 
			}

			

			for (int i = 0; i < snakeBodyParts; i++) {
				g.setColor(Color.green);
				g.fillOval(snakeX[i], snakeY[i], UNIT_SIZE, UNIT_SIZE); // For Shape of Snake
			}
			g.setColor(Color.CYAN);
			g.fillOval(snakeX[0], snakeY[0], UNIT_SIZE, UNIT_SIZE);

			g.setColor(Color.blue);
			g.setFont(new Font("Times New Roman", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont()); // provides dimensions of that font

			g.drawString("Score: " + score, (SCREEN_WIDTH - metrics.stringWidth("Score: " + score)) / 2,
					SCREEN_HEIGHT / 10);
		} else {
			gameOver(g);
		}
	}
	public void startGame() {
		newBigMouse();
		newSmallMouse();
		running = true; // To move snake , Starting of the Game
		timer = new Timer(DELAY, this); // Manage speed WRT to time
		timer.start();
	}
	public void newBigMouse() {
		
		bigMouseX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		bigMouseY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	public void newSmallMouse() {
		mouseX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		mouseY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	}
	public void moveSnake() {
		for (int i = snakeBodyParts; i > 0; i--) {
			snakeX[i] = snakeX[i - 1];
			snakeY[i] = snakeY[i - 1];
		}
		switch (direction) {
			case 'U':
				snakeY[0] = snakeY[0] - UNIT_SIZE;
				break;

			case 'D':
				snakeY[0] = snakeY[0] + UNIT_SIZE;
				break;

			case 'L':
				snakeX[0] = snakeX[0] - UNIT_SIZE;
				break;

			case 'R':
				snakeX[0] = snakeX[0] + UNIT_SIZE;
				break;
		}
	}
	public void checkCollisions() {
		// head touches body of the snake!
		for (int i = snakeBodyParts; i > 0; i--) {
			if ((snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i])) {
				running = false;
			}
		}
		if (snakeX[0] < 0) {
			//running = false;
			snakeX[0]=SCREEN_WIDTH;
		}
		if (snakeX[0] > SCREEN_WIDTH) {
			snakeX[0]=0;
			//running = false;
		}
		if (snakeY[0] < 0) {
			snakeY[0]=SCREEN_WIDTH;
			//running = false;
		}
		if (snakeY[0] > SCREEN_HEIGHT) {
			snakeY[0]=0;
			//running = false;
		}

		if (!running) {
			timer.stop(); // stops sending action to the event listener
		}
	}
	public void gameOver(Graphics g) {
		// score text
		g.setColor(Color.blue);
		g.setFont(new Font("Times New Roman", Font.BOLD, 40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score: " + score, (SCREEN_WIDTH - metrics.stringWidth("Score: " + score)) / 2,
				SCREEN_HEIGHT / 10);

		// game over text
		g.setColor(Color.blue);
		g.setFont(new Font("Times New Roman", Font.BOLD, 75));
		g.drawString("Game Over", SCREEN_WIDTH / 5, SCREEN_HEIGHT / 2);
	}
	public void actionPerformed(ActionEvent e) {

		if (running) {
			moveSnake();
			if(countMouse==5) {
				bgMouse.checkMouse();				
			}
			else {
				nrMouse.checkMouse();			
			}
			
			checkCollisions();
		}
		repaint();
	}

	public class MyKeyAdapter extends KeyAdapter {
		// MyKeyAdapter to handle keyBoard Input

		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if (direction != 'R') {
						direction = 'L';
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (direction != 'L') {
						direction = 'R';
					}
					break;
				case KeyEvent.VK_UP:
					if (direction != 'D') {
						direction = 'U';
					}
					break;
				case KeyEvent.VK_DOWN:
					if (direction != 'U') {
						direction = 'D';
					}
					break;
			}
		}
	}
}
