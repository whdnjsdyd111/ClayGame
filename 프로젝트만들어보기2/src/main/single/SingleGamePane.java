package main.single;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import main.MainFrame;
import main.common.Buttons;
import main.common.MouseShape;

public class SingleGamePane extends JLayeredPane implements MouseShape {
	
	MainFrame frame = null;
	
	public SingleGamePane(MainFrame frame) {
		setLayout(null);
		this.frame = frame;
		
		JLabel game_title = new JLabel("Clay Game");		// 게임 타이틀 라벨
		game_title.setBounds(500, 100, 200, 50);
		game_title.setFont(new Font("Consolas", Font.BOLD, 40));
		add(game_title);
		
		Buttons infinity = new Buttons(100, 250, "Infinity Mode", e -> {
			frame.card.show(frame.getContentPane(), "infinity");
			frame.infinityMode.startGame();
		});
		add(infinity);
		
		Buttons time = new Buttons(450, 250, "Time Mode", e -> {
			frame.card.show(frame.getContentPane(), "time");
			frame.time.startGame();
		});
		add(time);
		
		Buttons reload = new Buttons(800, 250, "Reload Mode", e -> {
			frame.card.show(frame.getContentPane(), "reload");
			frame.reload.startGame();
		});
		add(reload);
		
		Buttons to_main = new Buttons(275, 480, "Main", e -> {
			frame.card.show(frame.getContentPane(), "main");
		});
		add(to_main);
		
		Buttons end = new Buttons(625, 480, "End", e -> {
			frame.dispose();
		});
		add(end);
	}
	
}
