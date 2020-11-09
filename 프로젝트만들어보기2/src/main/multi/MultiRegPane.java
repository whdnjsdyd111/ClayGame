package main.multi;

import java.awt.Font;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

import main.MainFrame;
import main.common.Buttons;
import main.databases.MemberDAO;
import javax.swing.JButton;

public class MultiRegPane extends JLayeredPane {
	MainFrame frame = null;
	
	public MultiRegPane(MainFrame frame) {
		this.frame = frame;
		setLayout(null);
		
		JLabel reg_label = new JLabel("회원가입");
		reg_label.setFont(new Font("휴먼편지체", Font.BOLD, 35));
		reg_label.setBounds(480, 100, 320, 60);
		add(reg_label);
		
		JTextField tf_id = new JTextField();
		tf_id.setBounds(450, 200, 400, 50);
		add(tf_id);
		tf_id.setColumns(10);
		
		JTextField tf_pw = new JTextField();
		tf_pw.setColumns(10);
		tf_pw.setBounds(450, 400, 400, 50);
		add(tf_pw);
		
		JTextField tf_nick = new JTextField();
		tf_nick.setColumns(10);
		tf_nick.setBounds(450, 300, 400, 50);
		add(tf_nick);
		
		JLabel id_label = new JLabel("ID 입력");
		id_label.setFont(new Font("바탕", Font.PLAIN, 35));
		id_label.setBounds(250, 200, 150, 50);
		add(id_label);
		
		JLabel nick_label = new JLabel("닉네임 입력 ");
		nick_label.setFont(new Font("바탕", Font.PLAIN, 35));
		nick_label.setBounds(250, 300, 199, 50);
		add(nick_label);
		
		JLabel pw_label = new JLabel("PW 입력");
		pw_label.setFont(new Font("바탕", Font.PLAIN, 35));
		pw_label.setBounds(250, 400, 150, 50);
		add(pw_label);
		
		Buttons regBtn = new Buttons(100, 550, "회원 가입", e -> {
			if(tf_id.getText().equals("") || tf_pw.getText().equals("") || tf_nick.getText().equals("")) {
				new AlertDialog(frame, AlertDialog.MSG_EMPTY);
				return;
			}

			String id = tf_id.getText();
			String pw = tf_pw.getText();
			String nick = tf_nick.getText();
			int check_id = MemberDAO.getInstance().checkId(id);

			if(check_id != 0) {
				if(check_id == 1) {
					new AlertDialog(frame, AlertDialog.MSG1);
				} else if(check_id == 2) {
					new AlertDialog(frame, AlertDialog.MSG2);
				} else {
					new AlertDialog(frame, AlertDialog.MSG3);
				}
				return;
			}
			
			if(nick.length() < 2 || nick.length() > 10) {
				new AlertDialog(frame, AlertDialog.MSG_NICK);
				return;
			}
			
			if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", pw)) {
				new AlertDialog(frame, AlertDialog.MSG_PW);
				return;
			}
			
			MemberDAO.getInstance().insert(id, pw, nick);
			frame.card.show(frame.getContentPane(), "multiLogin");
		});
		
		regBtn.setLocation(500, 550);
		add(regBtn);
		
		JButton back_btn = new JButton("뒤로 가기");
		back_btn.setBounds(270, 550, 100, 60);
		back_btn.addActionListener(e -> {
			frame.card.show(frame.getContentPane(), "multiLogin");
		});
		add(back_btn);
		
	}
}
