package main.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MyButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String COLOR_HEX[][] = {			// ��ư ���� �������� �����ϱ� ���� ������ hex�� �̸� ��
			{"#00BFF", "#0AC9FF", "#14D3FF"},	// ���� �ϴ�
			{"#96A5FF", "#A0AFFF", "#AAB9FF"},	// �ſ� ���� ����
			{"#7B68EE", "#8572EE", "#8F7CEE"},	// ���� ����
			{"#3DFF92", "#47FF9C", "#51FFA3"},	// ���� û��
			{"#80E12A", "#8AE634", "#94EB3E"},	// ���� ���
			{"#52E4DC", "#57E9E1", "#5CEEE6"},	// ���� �ϴ�
			{"#006400", "#0A6E0A", "#147814"},	// ���
			{"#FF5675", "#FF607F", "#FF6A89"},	// ���
			{"#FF1493", "#FF1E9D", "#FF2847"},	// ��ȫ
			{"#CD1039", "#CD1F48", "#CD2E57"},	// ȫ��
			{"#FFC81E", "#FFD228", "#FFD732"},	// ����
			{"#FF0000", "#FF3232", "#FF4646"},	// ����
			{"#828282", "#8C8C8C", "#969696"},	// �� ȸ��
			{"#505050", "#5A5A5A", "#646464"}	// �� ȸ��
	};

	public MyButton(int x, int y, String name, ActionListener i) {
		setText(name);		// ��ư �̸� ����
		setSize(300, 60);	// ������ 300, 60
		setLocation(x, y);	// ��ư ��ġ ����
		setFont(new Font("D2 Coding", Font.BOLD, 30));	// ��Ʈ ����
		addActionListener(i);	// �Ű� ������ ���� �׼� ������ ����ϱ�
	}

	protected void paintComponent(Graphics g) {	// ��ư�� �ٹ̱� ���� ����Ʈ������Ʈ
		int width = getWidth();		// ���� ���� ���� ���
		int height = getHeight();
		
		Graphics2D graphics = (Graphics2D) g;	// 2D �׷��� ���
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	// �ܰ����� �ε巴�� �����ϱ�

		if (getModel().isArmed()) {		// �ش� ��ư�� hover�Ǿ��� ��, ���콺�� ������ ��, �ƴҶ��� ������ �������� �����ϱ�
			graphics.setColor(Color.decode(RandomColor()));
		} else if (getModel().isRollover()) {
			graphics.setColor(Color.decode(RandomColor()));
		} else {
			graphics.setColor(Color.decode(RandomColor()));
		}
		
		graphics.fillRoundRect(0, 0, width, height, 10, 10);	// �ش� �׷������� fillRect�� �׸� ����ä�� �׸���

		FontMetrics fontMetrics = graphics.getFontMetrics();	// ������ ��Ʈ�� �׷��� ���

		Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();
		int textX = (width - stringBounds.width) / 2;
		int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();

		graphics.setColor(getForeground());
		graphics.setFont(getFont());
		graphics.drawString(getText(), textX, textY);
		graphics.dispose();		// �׷��� �׸��� �ؽ�Ʈ�� �׸� ��Ʈ ���ֱ�
		super.paintComponent(g);
	}

	private String RandomColor() {	// ���� �������� ��� �޼ҵ�
		return COLOR_HEX[(int) (Math.random() * 14)][(int) (Math.random() * 3)];
	}
}
