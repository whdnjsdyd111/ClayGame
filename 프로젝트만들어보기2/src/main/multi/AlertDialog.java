package main.multi;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import main.common.MyButton;

public class AlertDialog extends Dialog {
	
	// ���â ���̾˷α�
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String MSG1 = "�̹� �����ϴ� ���̵��Դϴ�.";
	public static final String MSG2 = "���̵�� ����� ���ڸ� �����մϴ�.";
	public static final String MSG3 = "���̵�� 6�� �̻󿡼� 18�� ���ϸ� �����մϴ�.";
	public static final String MSG_EMPTY = "�Է����� ���� ������ �ֽ��ϴ�.";
	public static final String MSG_PW = "<html><body>��ȣ�� �ּ� �ϳ��� ����, ���� �� Ư�����ڷ�<br>8�� �̻� �Է����ֽʽÿ�.</body></html>";
	public static final String MSG_NICK = "�г����� 2�� �̻󿡼� 10�� ���ϸ� �����մϴ�.";
	public static final String MSG_ID = "�������� �ʴ� ���̵��Դϴ�.";
	public static final String MSG_NOT_MATCH = "�� ��й�ȣ�� ��ġ�ؾ� �մϴ�.";
	public static final String MSG_FAIL = "���̵� �Ǵ� ��й�ȣ�� Ʋ�Ƚ��ϴ�.";
	public static final String MSG_NET = "����� �ȵǰų� ������ �������ϴ�.";
	public static final String MSG_NET1 = "������ �������ϴ�.";
	public static final String MSG_ADDR = "�̹� ��� ���� IP �ּ��Դϴ�.";
	
	public AlertDialog(JFrame frame, String msg) {
		super(frame, "���â", true);
		setBounds(0, 0, 450, 350);
		setResizable(false);
		setLayout(null);
		
		Dimension frameSize = this.getSize(); // ������ ������
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // ����� ������
		
		this.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2); // ȭ�� �߾�
		
		JLabel msgLabel = new JLabel(msg);	// �޽��� �� �ʱ�ȭ
		msgLabel.setFont(new Font("����", Font.BOLD, 15));
		msgLabel.setBounds(10, 100, 430, 50);
		msgLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(msgLabel);
		
		MyButton btn = new MyButton(75, 275, "OK", e -> {	// ���â ���� ��ư
			dispose();
		});
		add(btn);
		
		setVisible(true);
	}
}
