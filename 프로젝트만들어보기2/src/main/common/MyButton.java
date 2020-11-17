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
	
	public static final String COLOR_HEX[][] = {			// 버튼 색을 랜덤으로 지정하기 위해 각각의 hex를 미리 둠
			{"#00BFF", "#0AC9FF", "#14D3FF"},	// 밝은 하늘
			{"#96A5FF", "#A0AFFF", "#AAB9FF"},	// 매우 연한 남색
			{"#7B68EE", "#8572EE", "#8F7CEE"},	// 연한 남색
			{"#3DFF92", "#47FF9C", "#51FFA3"},	// 연한 청녹
			{"#80E12A", "#8AE634", "#94EB3E"},	// 연한 녹색
			{"#52E4DC", "#57E9E1", "#5CEEE6"},	// 연한 하늘
			{"#006400", "#0A6E0A", "#147814"},	// 녹색
			{"#FF5675", "#FF607F", "#FF6A89"},	// 살색
			{"#FF1493", "#FF1E9D", "#FF2847"},	// 분홍
			{"#CD1039", "#CD1F48", "#CD2E57"},	// 홍색
			{"#FFC81E", "#FFD228", "#FFD732"},	// 연노
			{"#FF0000", "#FF3232", "#FF4646"},	// 빨간
			{"#828282", "#8C8C8C", "#969696"},	// 연 회색
			{"#505050", "#5A5A5A", "#646464"}	// 진 회색
	};

	public MyButton(int x, int y, String name, ActionListener i) {
		setText(name);		// 버튼 이름 지정
		setSize(300, 60);	// 사이즈 300, 60
		setLocation(x, y);	// 버튼 위치 지정
		setFont(new Font("D2 Coding", Font.BOLD, 30));	// 폰트 지정
		addActionListener(i);	// 매개 변수로 받은 액션 리스너 등록하기
	}

	protected void paintComponent(Graphics g) {	// 버튼을 꾸미기 위해 페인트컴포넌트
		int width = getWidth();		// 가로 세로 길이 얻기
		int height = getHeight();
		
		Graphics2D graphics = (Graphics2D) g;	// 2D 그래픽 얻기
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	// 외각선이 부드럽게 지정하기

		if (getModel().isArmed()) {		// 해당 버튼에 hover되었을 때, 마우스를 눌렀을 때, 아닐때에 색깔을 랜덤으로 지정하기
			graphics.setColor(Color.decode(RandomColor()));
		} else if (getModel().isRollover()) {
			graphics.setColor(Color.decode(RandomColor()));
		} else {
			graphics.setColor(Color.decode(RandomColor()));
		}
		
		graphics.fillRoundRect(0, 0, width, height, 10, 10);	// 해당 그래픽으로 fillRect로 네모 가득채워 그리기

		FontMetrics fontMetrics = graphics.getFontMetrics();	// 지정된 폰트의 그래픽 얻기

		Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();
		int textX = (width - stringBounds.width) / 2;
		int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();

		graphics.setColor(getForeground());
		graphics.setFont(getFont());
		graphics.drawString(getText(), textX, textY);
		graphics.dispose();		// 그래픽 그리고 텍스트의 네모난 렉트 없애기
		super.paintComponent(g);
	}

	private String RandomColor() {	// 색깔 랜덤으로 얻는 메소드
		return COLOR_HEX[(int) (Math.random() * 14)][(int) (Math.random() * 3)];
	}
}
