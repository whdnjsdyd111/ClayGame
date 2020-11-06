package main;

import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import main.mainPane.MainPane;
import main.multi.MultiGamePane;
import main.resource.Audios;
import main.single.InfinityMode;
import main.single.ReloadMode;
import main.single.SingleGamePane;
import main.single.TimeMode;

public class MainFrame extends JFrame {
	
	public CardLayout card = null;
	public MainPane main = null;
	public SingleGamePane single = null;
	public MultiGamePane multi = null;
	public InfinityMode infinityMode = null;
	public ReloadMode reload = null;
	public TimeMode time = null;
	public Cursor blankCursor = null;
	public Audios audio = new Audios();
	
	public MainFrame() {
		setTitle("클레이 사격 게임");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 800);
		
		card = new CardLayout(0, 0);
		
		setLayout(card);
		
		add("main", main = new MainPane(this));
		add("single", single = new SingleGamePane(this));
		add("multi", multi = new MultiGamePane(this));
		add("infinity", infinityMode =  new InfinityMode(this));
		add("reload", reload = new ReloadMode(this));
		add("time", time = new TimeMode(this));
		
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB); 
		blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				cursorImg, new Point(0, 0), "blank cursor"); 
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
	}
}
