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
	
	// 전적 창
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PVPDialog(MainFrame frame, String id) {
		super(frame, "전적 창", true);
		setBounds(0, 0, 450, 350);
		setResizable(false);
		setLayout(null);
		
		Dimension frameSize = this.getSize(); // 프레임 사이즈
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 모니터 사이즈
		
		this.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2); // 화면 중앙
		
		JLabel msgLabel = new JLabel(MemberDAO.getInstance().getPVP());
		msgLabel.setFont(new Font("2D Coding", Font.BOLD, 25));
		msgLabel.setBounds(10, 100, 430, 50);
		msgLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(msgLabel);
		
		MyButton btn = new MyButton(75, 275, "OK", e -> {	// 전적 창을 끄는 버튼 초기화
			dispose();
		});
		add(btn);
		
		setVisible(true);
	}
}
