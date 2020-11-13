package main.multi;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

import main.MainFrame;
import main.common.Buttons;
import main.databases.MemberDAO;

public class MultiLoginPane extends JLayeredPane {
	
	MainFrame frame = null;
	private JTextField tf_id;
	private JTextField tf_pw;
	
	public MultiLoginPane(MainFrame frame) {
		this.frame = frame;
		setLayout(null);
		
		JLabel multi_label = new JLabel("��Ƽ �÷��� �α���");
		multi_label.setFont(new Font("�޸�����ü", Font.BOLD, 35));
		multi_label.setBounds(440, 100, 320, 60);
		add(multi_label);
		
		tf_id = new JTextField();
		tf_id.setBounds(450, 250, 400, 50);
		tf_id.setText("whdnjsdyd111");
		add(tf_id);
		tf_id.setColumns(10);
		
		tf_pw = new JTextField();
		tf_pw.setBounds(450, 400, 400, 50);
		tf_pw.setText("jsh688123");
		add(tf_pw);
		
		JLabel id_label = new JLabel("ID �Է�");
		id_label.setFont(new Font("����", Font.PLAIN, 35));
		id_label.setBounds(250, 250, 150, 50);
		add(id_label);
		
		JLabel pw_label = new JLabel("PW �Է�");
		pw_label.setFont(new Font("����", Font.PLAIN, 35));
		pw_label.setBounds(250, 400, 150, 50);
		add(pw_label);
		
		Buttons loginBtn = new Buttons(450, 550, "�α���", e -> {
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
		
		Buttons regBtn = new Buttons(100, 550, "ȸ�� ����", e -> {
			frame.card.show(frame.getContentPane(), "multiReg");
		});
		add(regBtn);
		
		Buttons findBtn = new Buttons(800, 550, "��� ã��", e -> {
			frame.card.show(frame.getContentPane(), "findPasswd");
		});
		add(findBtn);
		
		Buttons to_main = new Buttons(450, 650, "��������", e -> {
			frame.card.show(frame.getContentPane(), "main");
		});
		add(to_main);
	}
}
