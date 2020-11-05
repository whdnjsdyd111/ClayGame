package main.single;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.border.LineBorder;

import main.MainFrame;
import main.common.Buttons;
import main.common.MouseShape;

public class TimeMode extends JLayeredPane implements MouseShape {
	
	MainFrame frame = null;
	Set<JLabel> clay = null;
	JLabel game_time = null;
	JLabel game_score = null;
	JButton to_main = null;
	JButton restart = null;
	int score = 0;
	
	boolean isEnd = false;
	
	Runnable time_start = () -> {
		for (int i = 5; i >= 0; i--) {
			int sec = i % 60;
			game_time.setText("0" + (i / 60) + ":" + (sec < 10 ? "0" + sec : sec));
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		isEnd = true;
	};
	
	ThreadGroup clay_group = null;
	
	Runnable create_clay = () -> {
		clay_group = new ThreadGroup("clay group");
		clay_group.setDaemon(true);
		
		while(!isEnd) {
			
			try {
				int ran = (int) (Math.random() * 5);
				Thread.sleep(200 * ran);
			} catch (Exception e) {
				e.printStackTrace();
			}
			new Thread(clay_group, () -> {
				
				int height = (int) (Math.random() * 300);
				
				JLabel clay_label = new JLabel("clay");
				clay_label.setBounds(0, height, 100, 50);
				clay_label.setBorder(new LineBorder(Color.BLUE, 3));
				clay.add(clay_label);
				add(clay_label);
				for (int i = 0; i < 1200; i+=2) {
					clay_label.setLocation(i, getHeight(i, height));
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						clay.remove(clay_label);
						remove(clay_label);
					}
				}
				clay.remove(clay_label);
				remove(clay_label);
				
			}).start();
		}
		clay_group.interrupt();
		
		repaint();
		to_main.setVisible(true);
		restart.setVisible(true);
		isEnd = false;
		score = 0;
	};
	
	public TimeMode(MainFrame frame) {
		
		this.frame = frame;
		
		setLayout(null);
		
		game_time = new JLabel("03:00");		// 게임 타이틀 라벨
		game_time.setBounds(400, 25, 100, 50);
		game_time.setFont(new Font("휴먼편지체", Font.BOLD, 40));
		add(game_time);
		
		game_score = new JLabel("Score " + score);
		game_score.setBounds(650, 25, 200, 50);
		game_score.setFont(new Font("휴먼편지체", Font.BOLD, 40));
		add(game_score);
		
		clay = new HashSet<>();
		
		setMouseShape(this);
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				removeClay(e.getX(), e.getY());
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
				
			}
		});
		
		to_main = new Buttons(450, 200, "메인으로", e -> {
			frame.card.show(frame.getContentPane(), "main");
			restart.setVisible(false);
			to_main.setVisible(false);
		});
		to_main.setLocation(250, 350);
		
		restart = new Buttons(600, 200, "재시작", e -> {
			startGrame();
			restart.setVisible(false);
			to_main.setVisible(false);
		});
		restart.setLocation(650, 350);
		add(to_main);
		add(restart);
		to_main.setVisible(false);
		restart.setVisible(false);
	}
	
	public void startGrame() {
		game_score.setText("Score 0");
		new Thread(time_start, "Time Start").start();
		new Thread(create_clay, "Create Clay").start();
	}
	
	private int getHeight(int x, int y) {
		return (int) ((x - 600) * (x - 600) / (7200) + y);
	}
	
	private void removeClay(int x, int y) {
		for(JLabel label : clay) {
			if(checkTarget(x, y, label)) {
				label.setVisible(false);
				game_score.setText("Score " + ++score);
			}
		}
	}
	
	private boolean checkTarget(int mouseX, int mouseY, JLabel label) {
		
		int x1 = label.getX();
		int x2 = label.getX() + label.getWidth();
		int y1 = label.getY();
		int y2 = label.getY() + label.getHeight();

		return ((x1 <= mouseX) && (mouseX <= x2)) && ((y1 <= mouseY) && (mouseY <= y2));
	}
}
