package main.common;

import java.util.ConcurrentModificationException;
import java.util.Set;

import javax.swing.JLabel;

public interface GameScene {
	
	default public int removeClay(int x, int y, Set<Plate> claies, JLabel game_score, int score) {	// 해당 자리의 플레이트를 없애는 메소드 시간 제한 모드용 메소드
		try {
			for(JLabel label : claies) {	// 해당 해쉬셋의 라벨들을 모두 비교하여 
				if(checkTarget(x, y, label)) { // 해당 자리에 라벨이 있다면 없애기
					label.setVisible(false);
					game_score.setText("Score " + ++score);	// 매개변수로 얻은 게임 스코어 라벨의 텍스트 바꿔주기
				}
			}
		} catch(ConcurrentModificationException e) {	// 최근 수정되었을 때의 예외를 무시
			
		}
		
		return score;	// 숫자가 올라간 점수를 리턴
	}
	
	default public int removeClay(int x, int y, Set<Plate> claies, JLabel game_score, int score, int round) {	// 장전 모드용 메소드
		try {
			for(JLabel label : claies) {
				if(checkTarget(x, y, label)) {
					label.setVisible(false);
					game_score.setText("Score " + (score += round));	// 라운드 수만큼 점수 플러스
				}
			}
		} catch(ConcurrentModificationException e) {
			
		}
		
		return score;
	}
	
	default public void removeClay(int x, int y, Set<Plate> claies, JLabel game_score) {	// 무한 모드용
		try {
			for(JLabel label : claies) {
				if(checkTarget(x, y, label)) {
					label.setVisible(false);
				}
			}
		} catch(ConcurrentModificationException e) {
			
		}
	}
	
	default boolean checkTarget(int mouseX, int mouseY, JLabel label) {	// 마우스 x, y 좌표로 해당 라벨의 위치를 비교하여 없애는 메소드
		int x1 = label.getX();
		int x2 = label.getX() + label.getWidth();
		int y1 = label.getY();
		int y2 = label.getY() + label.getHeight();

		return ((x1 <= mouseX) && (mouseX <= x2)) && ((y1 <= mouseY) && (mouseY <= y2));
	}
	
	default int getHeight(int x, int y) {	// 플레이트의 위치를 바꿀 높이 리턴
		return (int) ((x - 600) * (x - 600) / (7200) + y);
	}
	
	default int getHeight(float x, int y) {
		return (int) ((x - 600) * (x - 600) / (7200) + y);
	}
	
	
	public void startGame();
	public void showMenu();
	public void hideMenu();
}
