package main.databases;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldLimit extends PlainDocument {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int limit;
	
	public JTextFieldLimit(int limit) {	// 텍스트 입력 수 제한
		super();
		this.limit = limit;
	}
	
	@Override
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if(str == null)
			return;
		
		if(getLength() + str.length() <= limit)	// 제한 수가 넘어가더라도 원래 글자만 지정되도록 설정
			super.insertString(offset, str, attr);
	}
}
