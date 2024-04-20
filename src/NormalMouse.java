package Main;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;


public class NormalMouse extends JPanel implements DrawMouse {
	int mouseX;
	int mouseY; // random position of mouse
   GamePanel game;
   NormalMouse( GamePanel game){	   
	   this.game = game;
   }
   public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	} 
	public void draw(Graphics g) {
		// draw normal mouse
		g.setColor(Color.PINK);
		g.fillOval(game.mouseX, game.mouseY, game.UNIT_SIZE, game.UNIT_SIZE);
		
	}	
	public void checkMouse() {
		if ((game.snakeX[0] == game.mouseX) && (game.snakeY[0] == game.mouseY)) {
			if(game.countMouse==5)
			  game.countMouse=0;
			game.snakeBodyParts++;
			game.score++;
			game.countMouse++;
			game.newSmallMouse();
		}
		
	}
	
	

}
