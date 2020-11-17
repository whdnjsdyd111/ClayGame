package main.multi;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;

import main.MainFrame;
import main.common.MyButton;
import main.common.MyTextField;

public class MultiGamePane extends JLayeredPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MainFrame frame = null;
	public static String id = null;
	
	public MultiGamePane(MainFrame frame, String nickname, String id) {
		this.frame = frame;
		MultiGamePane.id = id;
		setLayout(null);
		
		MyButton pvpBtn = new MyButton(450, 25, "My Record", e -> {	// ������ Ȯ���ϴ� ��ư
			new PVPDialog(frame, MultiGamePane.id);	// ���� â ����
		});
		add(pvpBtn);
		
		JLabel ip_label = new JLabel("IP");		// IP ��
		ip_label.setFont(new Font("Consolas", Font.BOLD, 25));
		ip_label.setBounds(350, 100, 100, 50);
		add(ip_label);
		
		JLabel nick_label = new JLabel("Your NickName");	// Your NickName ��
		nick_label.setFont(new Font("Consolas", Font.BOLD, 25));
		nick_label.setBounds(350, 200, 200, 50);
		add(nick_label);
		
		
		MyTextField tf_ip = new MyTextField(550, 115);	// IP �Է¶�
		tf_ip.setSize(200, 50);
		tf_ip.setText("127.0.0.1");
		tf_ip.setFont(new Font("Consolas", Font.BOLD, 15));
		add(tf_ip);
		
		JLabel nick_label_1 = new JLabel(nickname, SwingConstants.CENTER);	// �г��� ��
		nick_label_1.setFont(new Font("2D Coding", Font.BOLD, 15));
		nick_label_1.setBounds(550, 200, 200, 50);
		add(nick_label_1);
		
		MyButton create_room = new MyButton(450, 350, "Create Room", e -> {	// ���� ����� ��ư
			CreatedRoom create = new CreatedRoom(frame, nickname);
			if(create.server.serverSocketChannel.isOpen() == true) {
				frame.add("createdRoom", create);
				frame.card.show(frame.getContentPane(), "createdRoom");
			} else
				new AlertDialog(frame, AlertDialog.MSG_ADDR);
		});
		add(create_room);
		
		MyButton join_room = new MyButton(450, 450, "Find Room", e -> {		// �濡 �����ϴ� ��ư
			JoinedRoom join = new JoinedRoom(frame, tf_ip.getText(), nickname);
			if(join.client.socketChannel.isConnected() == true) {
				frame.add("joinedRoom", join);
				frame.card.show(frame.getContentPane(), "joinedRoom");
			} else
				new AlertDialog(frame, AlertDialog.MSG_NET);
		});
		add(join_room);
		
		MyButton to_main = new MyButton(450, 550, "Main", e -> {	// �������� ���ư��� ��ư
			frame.card.show(frame.getContentPane(), "main");
			frame.remove(this);
		});
		add(to_main);
	}
}
