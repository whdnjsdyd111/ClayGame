package main.multi;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

import main.MainFrame;
import main.common.Buttons;

public class MultiLoginPane extends JLayeredPane {
	
	MainFrame frame = null;
	private JTextField textField;
	private JTextField textField_1;
	
	public MultiLoginPane(MainFrame frame) {
		this.frame = frame;
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("멀티 플레이 로그인");
		lblNewLabel.setFont(new Font("휴먼편지체", Font.BOLD, 35));
		lblNewLabel.setBounds(440, 100, 320, 60);
		add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(450, 250, 400, 50);
		add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(450, 400, 400, 50);
		add(textField_1);
		
		JLabel lblNewLabel_1 = new JLabel("ID 입력");
		lblNewLabel_1.setFont(new Font("바탕", Font.PLAIN, 35));
		lblNewLabel_1.setBounds(250, 250, 150, 50);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("PW 입력");
		lblNewLabel_2.setFont(new Font("바탕", Font.PLAIN, 35));
		lblNewLabel_2.setBounds(250, 400, 150, 50);
		add(lblNewLabel_2);
		
		Buttons loginBtn = new Buttons(450, 550, "로그인", e -> {
			
		});
		add(loginBtn);
		
		Buttons regBtn = new Buttons(100, 550, "회원 가입", e -> {
			
		});
		add(regBtn);
		
		Buttons findBtn = new Buttons(800, 550, "비번 찾기", e -> {
			
		});
		add(findBtn);
	}
}
