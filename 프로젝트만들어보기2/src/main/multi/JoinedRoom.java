package main.multi;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import main.MainFrame;
import main.common.MyButton;

public class JoinedRoom extends JLayeredPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MainFrame frame = null;
	Client client;
	
	public JoinedRoom(MainFrame frame, String ip, String nickname) {
		this.frame = frame;
		setLayout(null);
		
		JTextArea textArea = new JTextArea();	// ä��â �ؽ�Ʈ ���
		textArea.setFont(new Font("2D Coding", Font.BOLD, 15));
		textArea.setEnabled(false);
		JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(50, 50, 700, 550);
		scroll.setBorder(new LineBorder(Color.DARK_GRAY, 3));	// ä���� ���ƾ����� ��ũ���� �����ϵ��� 
		add(scroll);
		
		JLabel master = new JLabel("Host");		// ���� ��
		master.setFont(new Font("Consolas", Font.BOLD, 30));
		master.setHorizontalAlignment(SwingConstants.CENTER);
		master.setBounds(875, 170, 200, 70);
		add(master);
		
		JLabel master_nickname = new JLabel();	// ���� �г��� ��
		master_nickname.setFont(new Font("����", Font.BOLD, 17));
		master_nickname.setHorizontalAlignment(SwingConstants.CENTER);
		master_nickname.setBounds(800, 250, 350, 20);
		add(master_nickname);
		
		JLabel oppo = new JLabel("You");	// ���� ����Ű�� ��
		oppo.setHorizontalAlignment(SwingConstants.CENTER);
		oppo.setFont(new Font("Consolas", Font.BOLD, 30));
		oppo.setBounds(875, 300, 200, 70);
		add(oppo);
		
		JLabel oppo_nickname = new JLabel(nickname);	// ���� �г��� ��
		oppo_nickname.setHorizontalAlignment(SwingConstants.CENTER);
		oppo_nickname.setFont(new Font("����", Font.BOLD, 17));
		oppo_nickname.setBounds(800, 380, 350, 20);
		add(oppo_nickname);
		
		JComboBox<String> comboBox = new JComboBox<>();	// ��� ���� �޺��ڽ�
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"�ð� ���� ���", "���� ���", "���� ���"}));
		comboBox.setBounds(825, 580, 300, 25);
		comboBox.setEnabled(false);
		add(comboBox);
		
		client = new Client(ip, nickname, master_nickname, textArea, frame, this, comboBox);	// Ŭ���̾�Ʈ ����
		
		
		JTextField tf_chat = new JTextField();	// ä�� �ؽ�Ʈ �ʵ�
		tf_chat.setBorder(new LineBorder(Color.BLACK, 3));
		tf_chat.setBounds(50, 600, 700, 30);
		tf_chat.addKeyListener(new KeyListener() {	// Ű ������ ���
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {	// Ű�� ������ ��
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {	// ���� Ű�� ���� �� ���
					if(tf_chat.getText().equals(""))	// �ƹ� ä���� ġ�� �ʾҴٸ� ����
						return;
					else {
						String chat = tf_chat.getText();	// ä�� ���� �����ͼ�
						textArea.append("\n[��] " + chat);	// �ؽ�Ʈ ��̿� �߰� ��
						client.send("[" + nickname + "] " + chat);	// ���濡�� ä�� ���� ������
						tf_chat.setText("");
						textArea.setCaretPosition(textArea.getDocument().getLength());	// ä��â ��ũ�� �� �Ʒ��� �̵�
					}
				}
			}
		});
		add(tf_chat);
		tf_chat.setColumns(10);
		
		MyButton to_multi = new MyButton(925, 50, "��", e -> {	// ���� ������ ��ư
			client.stopClient();
			frame.card.show(frame.getContentPane(), "multi");
			frame.remove(this);
		});
		to_multi.setFont(new Font("����", Font.PLAIN, 65));
		to_multi.setBounds(925, 50, 100, 100);
		add(to_multi);
		
		MyButton startBtn = new MyButton(825, 500, "Game Start", e -> {	// ���� ��ư ������ ���� ����
			
		});
		startBtn.setEnabled(false);
		add(startBtn);
	}
}
