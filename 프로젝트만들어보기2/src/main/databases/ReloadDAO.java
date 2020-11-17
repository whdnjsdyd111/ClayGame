package main.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReloadDAO {
	private static ReloadDAO instance = new ReloadDAO();
	
	private ReloadDAO() {}
	
	public static ReloadDAO getInstance() {
		return instance;
	}
	
	private Connection getConnection() throws Exception {
		String url = "jdbc:oracle:thin:@net.yju.ac.kr:1521:orcl";
		String id = "s1702043";
		String password = "p1702043";
		Connection conn = null;
		
		conn = DriverManager.getConnection(url, id, password);	
		
		return conn;
	}
	
	public String[][] getReload(String score, int[] my_index) {		// 재장전 모드의 랭킹을 가져오는 메소드
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[][] str = new String[21][4];
		int index = 0;
		int parse_score = Integer.parseInt(score);
		try {
			conn = getConnection();
			String sql = "WITH " + 
					"total_rank AS (SELECT rel_name, rel_score, to_char(rel_time, 'rr-mm-dd hh24:mi:ss') as rel_time, " +
					"DENSE_RANK () over (order by rel_score DESC) AS tot_rank FROM reload), " + 
					"near_rank AS (SELECT tot_rank, rel_time, rel_name, rel_score FROM total_rank WHERE rel_score > ? ORDER BY ROWNUM DESC) " + 
					"SELECT * from near_rank WHERE ROWNUM <= 10 ORDER BY rel_score DESC";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, parse_score);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				for (int i = 0; i < 4; i++) {
					str[index][i] = rs.getString(i + 1);
				}
				index++;
			}
			
			Calendar calendar = Calendar.getInstance();
	        java.util.Date date = calendar.getTime();
	        String today = (new SimpleDateFormat("yy/MM/dd HH:mm:ss").format(date));
			
			my_index[0] = index;
			str[index][0] = "";
			str[index][1] = today;
			str[index][2] = "???";
			str[index++][3] = score;
			
			rs.close();
			pstmt.close();
			sql = "WITH " + 
					"total_rank AS (SELECT rel_name, rel_score, to_char(rel_time, 'rr-mm-dd hh24:mi:ss') as rel_time, " +
					"DENSE_RANK () over (order by rel_score DESC) AS tot_rank FROM reload), " + 
					"near_rank AS (SELECT tot_rank, rel_time, rel_name, rel_score FROM total_rank WHERE rel_score <= ? ORDER BY ROWNUM ASC) " + 
					"SELECT * from near_rank WHERE ROWNUM <= 10 ORDER BY rel_score DESC";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, parse_score);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				for (int i = 0; i < 4; i++) {
					str[index][i] = rs.getString(i + 1);
				}
				index++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null)
				try { rs.close(); } catch(SQLException e) {}
			if(pstmt != null)
				try { pstmt.close(); } catch(SQLException e) {}
			if(conn != null)
				try { conn.close(); } catch(SQLException e) {}
		}

		return str;
	}
	
	public void insert(String name, String score) {	// 랭킹 등록 메소드
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "INSERT INTO reload(rel_id, rel_name, rel_score) VALUES(rel_seq.NEXTVAL, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, Integer.parseInt(score));
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null)
				try { pstmt.close(); } catch(SQLException e) {}
			if(conn != null)
				try { conn.close(); } catch(SQLException e) {}
		}
	}
}
