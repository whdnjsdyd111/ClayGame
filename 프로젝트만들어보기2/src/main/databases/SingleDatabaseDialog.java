package main.databases;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import main.common.Buttons;


public abstract class SingleDatabaseDialog extends Dialog {
	
	protected static final String[] HEADERS = {"Rank", "Date" , "Name", "Score"};
	protected int[] my_rank = new int[] {0};
	
	protected final JTextField textField = new JTextField();
	protected final Buttons button = new Buttons(350, 550, "Upload Rank", null);
	
	public SingleDatabaseDialog(JFrame frame, String name, String score) {
		super(frame, name, true);
		setLayout(null);
		setBounds(0, 0, 600, 600);
		setResizable(false);
		
		Dimension frameSize = this.getSize(); // 프레임 사이즈
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 모니터 사이즈
		
		this.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2); // 화면 중앙
		
		button.setBounds(new Rectangle(350, 550, 200, 30));
		add(button);
		
		textField.setBounds(new Rectangle(30, 550, 300, 30));
		textField.setDocument(new JTextFieldLimit(10));
		textField.setText("???");
		add(textField);
		
		getRank(score);
		insertRank(score);
		
		setVisible(true);
	}
	
	protected abstract void getRank(String score);
	protected abstract void insertRank(String score);
}
