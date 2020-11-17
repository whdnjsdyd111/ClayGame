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
	
	public JTextFieldLimit(int limit) {	// �ؽ�Ʈ �Է� �� ����
		super();
		this.limit = limit;
	}
	
	@Override
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if(str == null)
			return;
		
		if(getLength() + str.length() <= limit)	// ���� ���� �Ѿ���� ���� ���ڸ� �����ǵ��� ����
			super.insertString(offset, str, attr);
	}
}
