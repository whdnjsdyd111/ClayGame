package main.single;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import main.MainFrame;
import main.common.LabelBtn;
import main.common.MouseShape;

public class SingleGamePane extends JLayeredPane implements MouseShape {
	
	MainFrame frame = null;
	
	public SingleGamePane(MainFrame frame) {
		setLayout(null);
		this.frame = frame;
		
		JLabel game_title = new JLabel("클레어 사격 게임");		// 게임 타이틀 라벨
		game_title.setBounds(450, 100, 300, 50);
		game_title.setFont(new Font("휴먼편지체", Font.BOLD, 40));
		add(game_title);
		
		JLabel infinity = new LabelBtn(125, 250, "무한 모드", new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.card.show(frame.getContentPane(), "infinity");
				frame.infinityMode.startGame();
			}
		});
		infinity.setBounds(125, 250, 250, 100);
		infinity.setFont(new Font("휴먼옛체", Font.BOLD, 35));
		add(infinity);
		
		JLabel time = new LabelBtn(485, 250, "시간 제한 모드", new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.card.show(frame.getContentPane(), "time");
				frame.time.startGame();
			}
		});
		time.setSize(300, 100);
		time.setLocation(450, 250);
		time.setFont(new Font("휴먼옛체", Font.BOLD, 35));
		add(time);
		
		JLabel reload = new LabelBtn(875, 250, "장전 모드", new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.card.show(frame.getContentPane(), "reload");
				frame.reload.startGame();
			}
		});
		reload.setFont(new Font("휴먼옛체", Font.BOLD, 35));
		add(reload);
		
		JLabel to_main = new LabelBtn(285, 480, "메인으로", new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.card.show(frame.getContentPane(), "main");
			}
		});
		to_main.setFont(new Font("휴먼옛체", Font.PLAIN, 35));
		add(to_main);
		
		JLabel end = new LabelBtn(680, 480, "끝내기", new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		end.setFont(new Font("휴먼옛체", Font.BOLD, 35));
		add(end);
	}
	
}
