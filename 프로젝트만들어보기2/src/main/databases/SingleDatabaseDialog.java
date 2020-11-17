package main.databases;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import main.common.MyButton;


public abstract class SingleDatabaseDialog extends Dialog {

	// 데이터베이스 등록할 다이알로그 추상 클래스
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static final String[] HEADERS = {"Rank", "Date" , "Name", "Score"};	// 다이얼로그 테이블의 제목
	protected int[] my_rank = new int[] {0};	// 플레이어의 랭킹를 저장할 배열 (매개변수로 넣어서 지정한 메소드에서 랭킹을 얻기 위해서)
	
	protected final JTextField textField = new JTextField();	// 랭킹에 이름을 넣을 텍스트 필드
	protected final MyButton button = new MyButton(350, 550, "Upload Rank", null);	// 랭킹 등록 버튼
	
	public SingleDatabaseDialog(JFrame frame, String name, String score) {
		super(frame, name, true);
		setLayout(null);
		setBounds(0, 0, 600, 600);
		setResizable(false);
		
		Dimension frameSize = this.getSize(); // 프레임 사이즈
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 모니터 사이즈
		
		this.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2); // 화면 중앙
		
		button.setBounds(new Rectangle(350, 550, 200, 30));
		add(button);
		
		textField.setBounds(new Rectangle(30, 550, 300, 30));
		textField.setDocument(new JTextFieldLimit(10));
		textField.setText("???");
		add(textField);
		
		getRank(score);
		insertRank(score);
		
		setVisible(true);
	}
	
	protected abstract void getRank(String score);
	protected abstract void insertRank(String score);
	protected void setTables(String[][] scores) {
		JTable table = new JTable(scores, HEADERS) {	// 테이블
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {	// 해당 테이블의 각 셀의 색깔을 하늘, 하얀색으로 번갈아 가면서 지정
				JComponent component = (JComponent) super.prepareRenderer(renderer, row, column);

				component.setBackground(row == my_rank[0] ? Color.CYAN : Color.WHITE);

				return component;
			}

			@Override
			public void setRowHeight(int rowHeight) {	// 셀의 높이를 23으로 지정
				super.setRowHeight(23);
			}
			
			@Override
			public boolean isCellEditable(int row, int column) {	// 셀을 고치지 못하도록 함
				return false;
			}
		};
		JScrollPane scroll = new JScrollPane(table);	// 테이블이 클 경우 스코롤 페인에 넣어 스크롤이 가능하도록 지정
		scroll.setBounds(new Rectangle(10, 40, 580, 508));
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		
		for (int i = 0; i < tcm.getColumnCount(); i++) {	// 테이블 컬럼 모델을 가져와서 셀의 글자를 중앙에 배치
			tcm.getColumn(i).setCellRenderer(dtcr);
		}
		
		add(scroll);
	}
}
