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
		
		JLabel reg_label = new JLabel("Sign Up");
		reg_label.setFont(new Font("Consolas", Font.BOLD, 45));
		reg_label.setBounds(525, 100, 320, 60);
		add(reg_label);
		
		MyTextField tf_id = new MyTextField(450, 200);
		add(tf_id);
		
		MyTextField tf_nick = new MyTextField(450, 300);
		add(tf_nick);
		
		MyTextField tf_pw = new MyTextField(450, 400);
		add(tf_pw);
		
		JLabel id_label = new JLabel("ID");
		id_label.setFont(new Font("Consolas", Font.PLAIN, 35));
		id_label.setBounds(250, 200, 150, 50);
		add(id_label);
		
		JLabel nick_label = new JLabel("Nick Name");
		nick_label.setFont(new Font("Consolas", Font.PLAIN, 35));
		nick_label.setBounds(250, 300, 199, 50);
		add(nick_label);
		
		JLabel pw_label = new JLabel("PW");
		pw_label.setFont(new Font("Consolas", Font.PLAIN, 35));
		pw_label.setBounds(250, 400, 150, 50);
		add(pw_label);
		
		MyButton regBtn = new MyButton(650, 550, "Sign Up", e -> {
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
			
			if(!Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,}$").matcher(pw).matches()) { // TODO
				new AlertDialog(frame, AlertDialog.MSG_PW);
				return;
			}
			
			MemberDAO.getInstance().insert(id, pw, nick);
			frame.card.show(frame.getContentPane(), "multiLogin");
		});
		add(regBtn);
		
		MyButton back_btn = new MyButton(250, 550, "Back", e -> {
			frame.card.show(frame.getContentPane(), "multiLogin");
		});
		add(back_btn);
		
	}
}
