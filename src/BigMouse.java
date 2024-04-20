package Main;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class BigMouse extends JPanel implements DrawMouse {

	   GamePanel game;
	   BigMouse( GamePanel game){
		 
		   this.game = game;
	   }
	  
	   public void paintComponent(Graphics g) {
			super.paintComponent(g);
			draw(g);
		}
	   
		
		public void draw(Graphics g) {
			// draw big mouse and small mouse 
			g.setColor(Color.PINK);
			g.fillOval(game.mouseX, game.mouseY, game.UNIT_SIZE, game.UNIT_SIZE);
			g.setColor(Color.RED);
			g.fillOval(game.bigMouseX, game.bigMouseY, game.UNIT_SIZE+4, game.UNIT_SIZE+4);
			
		}

		
		public void checkMouse() {
			if ((game.snakeX[0] == game.mouseX) && (game.snakeY[0]== game.mouseY)) {
				if(game.countMouse==5)
				  game.countMouse=0;
				
				game.snakeBodyParts++;
				game.score++;
				game.countMouse++;
				game.newSmallMouse();
			}
			else if((game.countMouse==5)&&(game.snakeX[0] == game.bigMouseX) && (game.snakeY[0]== game.bigMouseY)) {
				  game.countMouse=0;
					game.snakeBodyParts++;
					game.score+=4;
					game.newBigMouse();
			}
			
		}
		
		

}
