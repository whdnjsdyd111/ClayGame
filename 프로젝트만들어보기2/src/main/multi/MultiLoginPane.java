package main.multi;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import main.MainFrame;
import main.common.MyButton;
import main.common.MyTextField;
import main.databases.MemberDAO;

public class MultiLoginPane extends JLayeredPane {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MainFrame frame = null;
	
	public MultiLoginPane(MainFrame frame) {
		this.frame = frame;
		setLayout(null);
		
		JLabel multi_label = new JLabel("Login For MultiPlay");		// 멀티 로그인 라벨
		multi_label.setFont(new Font("Consolas", Font.BOLD, 45));
		multi_label.setBounds(375, 100, 500, 60);
		add(multi_label);
		
		MyTextField tf_id = new MyTextField(450, 250);	// 아이디 입력 란
		tf_id.setText("whdnjsdyd111");
		add(tf_id);
		
		MyTextField tf_pw = new MyTextField(450, 400);	// 비밀 번호 입력 란
		tf_pw.setText("jsh6881@");
		add(tf_pw);
		
		JLabel id_label = new JLabel("ID");	// ID 라벨
		id_label.setFont(new Font("Consolas", Font.PLAIN, 35));
		id_label.setBounds(250, 250, 150, 50);
		add(id_label);
		
		JLabel pw_label = new JLabel("PW");	// PW 라벨
		pw_label.setFont(new Font("Consolas", Font.PLAIN, 35));
		pw_label.setBounds(250, 400, 150, 50);
		add(pw_label);
		
		MyButton loginBtn = new MyButton(450, 550, "Login", e -> {	// 로그인하는 버튼
			String id = tf_id.getText();
			String pw = tf_pw.getText();
			
			if(id.equals("") || pw.equals("")) {	// 아무 입력을 하지 않으면 경고창 띄우기
				new AlertDialog(frame, AlertDialog.MSG_EMPTY);
			}
			
			String nickname = MemberDAO.getInstance().checkAll(id, pw);	// 아이디가 맞는 지 확인
			
			if(nickname != null) {	// 아이디가 맞다면 멀티 게임 화면으로 보내주기
				frame.add("multi", new MultiGamePane(frame, nickname, id));
				frame.card.show(frame.getContentPane(), "multi");
			} else {
				new AlertDialog(frame, AlertDialog.MSG_FAIL);	// 아이디가 맞지 않다면 경고창 띄우기
			}
			
		});
		add(loginBtn);
		
		MyButton regBtn = new MyButton(100, 550, "Sign Up", e -> {
			frame.card.show(frame.getContentPane(), "multiReg");
		});
		add(regBtn);
		
		MyButton findBtn = new MyButton(800, 550, "Find Password", e -> {
			frame.card.show(frame.getContentPane(), "findPasswd");
		});
		add(findBtn);
		
		MyButton to_main = new MyButton(450, 650, "Main", e -> {
			frame.card.show(frame.getContentPane(), "main");
		});
		add(to_main);
	}
}
