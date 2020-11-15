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
		
		JLabel multi_label = new JLabel("Login For MultiPlay");
		multi_label.setFont(new Font("Consolas", Font.BOLD, 45));
		multi_label.setBounds(375, 100, 500, 60);
		add(multi_label);
		
		MyTextField tf_id = new MyTextField(450, 250);
		tf_id.setText("whdnjsdyd111");
		add(tf_id);
		
		MyTextField tf_pw = new MyTextField(450, 400);
		tf_pw.setText("jsh688123");
		add(tf_pw);
		
		JLabel id_label = new JLabel("ID");
		id_label.setFont(new Font("Consolas", Font.PLAIN, 35));
		id_label.setBounds(250, 250, 150, 50);
		add(id_label);
		
		JLabel pw_label = new JLabel("PW");
		pw_label.setFont(new Font("Consolas", Font.PLAIN, 35));
		pw_label.setBounds(250, 400, 150, 50);
		add(pw_label);
		
		MyButton loginBtn = new MyButton(450, 550, "Login", e -> {
			String id = tf_id.getText();
			String pw = tf_pw.getText();
			
			if(id.equals("") || pw.equals("")) {
				new AlertDialog(frame, AlertDialog.MSG_EMPTY);
			}
			
			String nickname = MemberDAO.getInstance().checkAll(id, pw);
			
			if(nickname != null) {
				frame.add("multi", new MultiGamePane(frame, nickname));
				frame.card.show(frame.getContentPane(), "multi");
			} else {
				new AlertDialog(frame, AlertDialog.MSG_FAIL);
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
