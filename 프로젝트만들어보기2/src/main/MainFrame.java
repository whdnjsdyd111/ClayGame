package main;

import java.awt.CardLayout;

import javax.swing.JFrame;

import main.mainPane.MainPane;
import main.multi.MultiGamePane;
import main.single.InfinityMode;
import main.single.ReloadMode;
import main.single.SingleGamePane;
import main.single.TimeMode;

public class MainFrame extends JFrame {
	
	public CardLayout card = null;
	
	public MainFrame() {
		setTitle("클레이 사격 게임");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 800);
		
		card = new CardLayout(0, 0);
		
		setLayout(card);
		
		add("main", new MainPane(this));
		add("single", new SingleGamePane(this));
		add("multi", new MultiGamePane(this));
		add("infinity", new InfinityMode(this));
		add("reload", new ReloadMode(this));
		add("time", new TimeMode(this));
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
	}
}
