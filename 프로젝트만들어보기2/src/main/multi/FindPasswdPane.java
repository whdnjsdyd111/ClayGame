package main.multi;

import java.awt.Font;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

import main.MainFrame;
import main.common.Buttons;
import main.databases.MemberDAO;

public class FindPasswdPane extends JLayeredPane {
	
	MainFrame frame = null;
	
	public FindPasswdPane(MainFrame frame) {
		this.frame = frame;
		setLayout(null);
		
		JLabel reg_label = new JLabel("비밀번호 바꾸기");
		reg_label.setFont(new Font("휴먼편지체", Font.BOLD, 35));
		reg_label.setBounds(480, 100, 320, 60);
		add(reg_label);
		
		JTextField tf_id = new JTextField();
		tf_id.setBounds(450, 200, 400, 50);
		add(tf_id);
		tf_id.setColumns(10);

		JTextField tf_pw = new JTextField();
		tf_pw.setColumns(10);
		tf_pw.setBounds(450, 300, 400, 50);
		add(tf_pw);
		
		JTextField tf_pw2 = new JTextField();
		tf_pw2.setColumns(10);
		tf_pw2.setBounds(450, 400, 400, 50);
		add(tf_pw2);
		
		JLabel id_label = new JLabel("ID 입력");
		id_label.setFont(new Font("바탕", Font.PLAIN, 35));
		id_label.setBounds(250, 200, 150, 50);
		add(id_label);
		
		JLabel nick_label = new JLabel("PW 입력 ");
		nick_label.setFont(new Font("바탕", Font.PLAIN, 35));
		nick_label.setBounds(250, 300, 199, 50);
		add(nick_label);
		
		JLabel pw_label = new JLabel("PW 재입력");
		pw_label.setFont(new Font("바탕", Font.PLAIN, 35));
		pw_label.setBounds(250, 400, 186, 50);
		add(pw_label);
		
		Buttons regBtn = new Buttons(650, 550, "Change Password", e -> {
			if(tf_id.getText().equals("") || tf_pw2.getText().equals("") || tf_pw.getText().equals("")) {
				new AlertDialog(frame, AlertDialog.MSG_EMPTY);
				return;
			}

			String id = tf_id.getText();
			String pw = tf_pw2.getText();
			String pw2 = tf_pw.getText();

			if(MemberDAO.getInstance().checkId(id) != 1) {
				new AlertDialog(frame, AlertDialog.MSG_ID);
				return;
			}
			
			
			if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", pw)) {
				new AlertDialog(frame, AlertDialog.MSG_PW);
				return;
			}
			
			if(!pw.equals(pw2)) {
				new AlertDialog(frame, AlertDialog.MSG_NOT_MATCH);
				return;
			}
			
			
			MemberDAO.getInstance().changePw(id, pw);
			frame.card.show(frame.getContentPane(), "multiLogin");
		});
		add(regBtn);
		
		Buttons back_btn = new Buttons(250, 550, "Back", e -> {
			frame.card.show(frame.getContentPane(), "multiLogin");
		});
		add(back_btn);
		
	}
}
