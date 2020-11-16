package main.multi;

import java.awt.Font;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import main.MainFrame;
import main.common.MyButton;
import main.common.MyTextField;
import main.databases.MemberDAO;

public class FindPasswdPane extends JLayeredPane {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MainFrame frame = null;
	
	public FindPasswdPane(MainFrame frame) {
		this.frame = frame;
		setLayout(null);
		
		JLabel reg_label = new JLabel("Change Password");
		reg_label.setFont(new Font("Consolas", Font.BOLD, 45));
		reg_label.setBounds(425, 100, 375, 60);
		add(reg_label);
		
		MyTextField tf_id = new MyTextField(450, 200);
		add(tf_id);

		MyTextField tf_pw = new MyTextField(450, 300);
		add(tf_pw);
		
		MyTextField tf_re_pw = new MyTextField(450, 400);
		add(tf_re_pw);
		
		JLabel id_label = new JLabel("ID");
		id_label.setFont(new Font("Consolas", Font.PLAIN, 35));
		id_label.setBounds(250, 200, 150, 50);
		add(id_label);
		
		JLabel nick_label = new JLabel("PW ");
		nick_label.setFont(new Font("Consolas", Font.PLAIN, 35));
		nick_label.setBounds(250, 300, 199, 50);
		add(nick_label);
		
		JLabel pw_label = new JLabel("Re-PW");
		pw_label.setFont(new Font("Consolas", Font.PLAIN, 35));
		pw_label.setBounds(250, 400, 186, 50);
		add(pw_label);
		
		MyButton regBtn = new MyButton(650, 550, "Change Password", e -> {
			if(tf_id.getText().equals("") || tf_re_pw.getText().equals("") || tf_pw.getText().equals("")) {
				new AlertDialog(frame, AlertDialog.MSG_EMPTY);
				return;
			}

			String id = tf_id.getText();
			String pw = tf_re_pw.getText();
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
		
		MyButton back_btn = new MyButton(250, 550, "Back", e -> {
			frame.card.show(frame.getContentPane(), "multiLogin");
		});
		add(back_btn);
		
	}
}
