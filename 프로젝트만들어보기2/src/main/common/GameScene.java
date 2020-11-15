package main.common;

import java.util.ConcurrentModificationException;
import java.util.Set;

import javax.swing.JLabel;

public interface GameScene {
	
	default public int removeClay(int x, int y, Set<Plate> claies, JLabel game_score, int score) {
		try {
			for(JLabel label : claies) {
				if(checkTarget(x, y, label)) {
					label.setVisible(false);
					game_score.setText("Score " + ++score);
				}
			}
		} catch(ConcurrentModificationException e) {
			
		}
		
		return score;
	}
	
	default public int removeClay(int x, int y, Set<Plate> claies, JLabel game_score, int score, int round) {
		try {
			for(JLabel label : claies) {
				if(checkTarget(x, y, label)) {
					label.setVisible(false);
					game_score.setText("Score " + (score += round));
				}
			}
		} catch(ConcurrentModificationException e) {
			
		}
		
		return score;
	}
	
	default public void removeClay(int x, int y, Set<Plate> claies, JLabel game_score) {
		try {
			for(JLabel label : claies) {
				if(checkTarget(x, y, label)) {
					label.setVisible(false);
				}
			}
		} catch(ConcurrentModificationException e) {
			
		}
	}
	
	default boolean checkTarget(int mouseX, int mouseY, JLabel label) {
		int x1 = label.getX();
		int x2 = label.getX() + label.getWidth();
		int y1 = label.getY();
		int y2 = label.getY() + label.getHeight();

		return ((x1 <= mouseX) && (mouseX <= x2)) && ((y1 <= mouseY) && (mouseY <= y2));
	}
	
	default int getHeight(int x, int y) {
		return (int) ((x - 600) * (x - 600) / (7200) + y);
	}
	
	default int getHeight(float x, int y) {
		return (int) ((x - 600) * (x - 600) / (7200) + y);
	}
	
	
	public void startGame();
	public void showMenu();
	public void hideMenu();
}
