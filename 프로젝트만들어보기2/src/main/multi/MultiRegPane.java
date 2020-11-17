package main.multi;

import java.awt.Font;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import main.MainFrame;
import main.common.MyButton;
import main.common.MyTextField;
import main.databases.MemberDAO;

public class MultiRegPane extends JLayeredPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MainFrame frame = null;
	
	public MultiRegPane(MainFrame frame) {
		this.frame = frame;
		setLayout(null);
		
		JLabel reg_label = new JLabel("Sign Up");	// 회원가입 라벨
		reg_label.setFont(new Font("Consolas", Font.BOLD, 45));
		reg_label.setBounds(525, 100, 320, 60);
		add(reg_label);
		
		MyTextField tf_id = new MyTextField(450, 200);	// id 받는 텍스트 필드
		add(tf_id);
		
		MyTextField tf_nick = new MyTextField(450, 300);	// 닉네임을 받는 텍스트 필드
		add(tf_nick);
		
		MyTextField tf_pw = new MyTextField(450, 400);	// 패스워드를 받는 텍스트 필드
		add(tf_pw);
		
		JLabel id_label = new JLabel("ID");	// id 라벨
		id_label.setFont(new Font("Consolas", Font.PLAIN, 35));
		id_label.setBounds(250, 200, 150, 50);
		add(id_label);
		
		JLabel nick_label = new JLabel("Nick Name");	// 닉네임 라벨
		nick_label.setFont(new Font("Consolas", Font.PLAIN, 35));
		nick_label.setBounds(250, 300, 199, 50);
		add(nick_label);
		
		JLabel pw_label = new JLabel("PW");	// 패스워드 라벨
		pw_label.setFont(new Font("Consolas", Font.PLAIN, 35));
		pw_label.setBounds(250, 400, 150, 50);
		add(pw_label);
		
		MyButton regBtn = new MyButton(650, 550, "Sign Up", e -> {	// 회원 가입을 하는 버튼
			if(tf_id.getText().equals("") || tf_pw.getText().equals("") || tf_nick.getText().equals("")) {	// 모두 입력했는지 확인
				new AlertDialog(frame, AlertDialog.MSG_EMPTY);
				return;
			}

			String id = tf_id.getText();
			String pw = tf_pw.getText();
			String nick = tf_nick.getText();
			int check_id = MemberDAO.getInstance().checkId(id);	// 각각의 정보를 가져와서 체크

			if(check_id != 0) {		// MemberDAO에서 체크한 숫자로 id가 제대로 됐는지 확인
				if(check_id == 1) {
					new AlertDialog(frame, AlertDialog.MSG1);
				} else if(check_id == 2) {
					new AlertDialog(frame, AlertDialog.MSG2);
				} else {
					new AlertDialog(frame, AlertDialog.MSG3);
				}
				return;
			}
			
			if(nick.length() < 2 || nick.length() > 10) {	// 닉네임은 2 ~ 10 글자만 가능하도록
				new AlertDialog(frame, AlertDialog.MSG_NICK);
				return;
			}
			
			if(!Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,}$").matcher(pw).matches()) { // 비밀번호 정규식
				new AlertDialog(frame, AlertDialog.MSG_PW);
				return;
			}
			
			MemberDAO.getInstance().insert(id, pw, nick);	// 모두 넘어갔다면 회원 정보 인서트 하기
			frame.card.show(frame.getContentPane(), "multiLogin");	// 로그인 화면으로 보내주기
		});
		add(regBtn);
		
		MyButton back_btn = new MyButton(250, 550, "Back", e -> {	// 뒤로  가는 버튼 초기화
			frame.card.show(frame.getContentPane(), "multiLogin");
		});
		add(back_btn);
		
	}
}
