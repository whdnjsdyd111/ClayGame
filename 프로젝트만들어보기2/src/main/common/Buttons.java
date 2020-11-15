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

public class Buttons extends JButton {

	public static final String color_hex[][] = {
			{"#00BFF", "#0AC9FF", "#14D3FF"},	// ¹àÀº ÇÏ´Ã
			{"#96A5FF", "#A0AFFF", "#AAB9FF"},	// ¸Å¿ì ¿¬ÇÑ ³²»ö
			{"#7B68EE", "#8572EE", "#8F7CEE"},	// ¿¬ÇÑ ³²»ö
			{"#3DFF92", "#47FF9C", "#51FFA3"},	// ¿¬ÇÑ Ã»³ì
			{"#80E12A", "#8AE634", "#94EB3E"},	// ¿¬ÇÑ ³ì»ö
			{"#52E4DC", "#57E9E1", "#5CEEE6"},	// ¿¬ÇÑ ÇÏ´Ã
			{"#006400", "#0A6E0A", "#147814"},	// ³ì»ö
			{"#FF5675", "#FF607F", "#FF6A89"},	// »ì»ö
			{"#FF1493", "#FF1E9D", "#FF2847"},	// ºÐÈ«
			{"#CD1039", "#CD1F48", "#CD2E57"},	// È«»ö
			{"#FFC81E", "#FFD228", "#FFD732"},	// ¿¬³ë
			{"#FF0000", "#FF3232", "#FF4646"},	// »¡°£
			{"#828282", "#8C8C8C", "#969696"},	// ¿¬ È¸»ö
			{"#505050", "#5A5A5A", "#646464"}	// Áø È¸»ö
	};

//	private final int BRIGHT_SKY = 0;
//	private final int VERY_LIGHT_INDIGO = 1;
//	private final int LIGHT_INDIGO = 2;
//	private final int VERY_LIGHT_GREEN = 3;
//	private final int LIGHT_GREEN = 4;
//	private final int LIGHT_SKY = 5;
//	private final int GREEN = 6;
//	private final int SKIN = 7;
//	private final int PINK = 8;
//	private final int SCARLET = 9;
//	private final int LIGHT_YELLOW = 10;
//	private final int RED = 11;
//	private final int LIGHT_GRAY = 12;
//	private final int DARK_GRAY = 13;
	
	public Buttons(int x, int y, String name, ActionListener i) {
		setText(name);
		setSize(300, 60);
		setLocation(x, y);
		setFont(new Font("Consolas", Font.BOLD, 30));
		this.addActionListener(i);
	}

	protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (getModel().isArmed()) {
			graphics.setColor(Color.decode(RandomColor()));
		} else if (getModel().isRollover()) {
			graphics.setColor(Color.decode(RandomColor()));
		} else {
			graphics.setColor(Color.decode(RandomColor()));
		}
		graphics.fillRoundRect(0, 0, width, height, 10, 10);

		FontMetrics fontMetrics = graphics.getFontMetrics();

		Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();
		int textX = (width - stringBounds.width) / 2;
		int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();

		graphics.setColor(getForeground());
		graphics.setFont(getFont());
		graphics.drawString(getText(), textX, textY);
		graphics.dispose();
		super.paintComponent(g);
	}

	private String RandomColor() {
		return color_hex[(int) (Math.random() * 14)][(int) (Math.random() * 3)];
	}
}
