package main.multi;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import main.MainFrame;
import main.common.MyButton;
import main.databases.MemberDAO;

public class PVPDialog extends Dialog {
	
	// ���� â
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PVPDialog(MainFrame frame, String id) {
		super(frame, "���� â", true);
		setBounds(0, 0, 450, 350);
		setResizable(false);
		setLayout(null);
		
		Dimension frameSize = this.getSize(); // ������ ������
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // ����� ������
		
		this.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2); // ȭ�� �߾�
		
		JLabel msgLabel = new JLabel(MemberDAO.getInstance().getPVP());
		msgLabel.setFont(new Font("2D Coding", Font.BOLD, 25));
		msgLabel.setBounds(10, 100, 430, 50);
		msgLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(msgLabel);
		
		MyButton btn = new MyButton(75, 275, "OK", e -> {	// ���� â�� ���� ��ư �ʱ�ȭ
			dispose();
		});
		add(btn);
		
		setVisible(true);
	}
}
