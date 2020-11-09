package main.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InfinityDAO {
	private static InfinityDAO instance = new InfinityDAO();
	
	private InfinityDAO() {}
	
	public static InfinityDAO getInstance() {
		return instance;
	}
	
	private Connection getConnection() throws Exception {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "clay_game";
		String password = "clay_game";
		Connection conn = null;
		
		conn = DriverManager.getConnection(url, id, password);	
		
		return conn;
	}
	
	public String[][] getInfinity(String score, int[] my_index) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[][] str = new String[21][4];
		int index = 0;
		
		try {
			conn = getConnection();
			String sql = "WITH " + 
					"total_rank AS (SELECT inf_name, inf_score, to_char(inf_time, 'rr-mm-dd hh24:mi:ss') as inf_time, " +
					"DENSE_RANK () over (order by inf_score DESC) AS tot_rank FROM infinity), " + 
					"near_rank AS (SELECT tot_rank, inf_time, inf_name, inf_score FROM total_rank WHERE inf_score > ? ORDER BY ROWNUM DESC) " + 
					"SELECT * from near_rank WHERE ROWNUM <= 10 ORDER BY inf_score DESC";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, score);
			
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
					"total_rank AS (SELECT inf_name, inf_score, to_char(inf_time, 'rr-mm-dd hh24:mi:ss') as inf_time, " +
					"DENSE_RANK () over (order by inf_score DESC) AS tot_rank FROM infinity), " + 
					"near_rank AS (SELECT tot_rank, inf_time, inf_name, inf_score FROM total_rank WHERE inf_score <= ? ORDER BY ROWNUM ASC) " + 
					"SELECT * from near_rank WHERE ROWNUM <= 10 ORDER BY inf_score DESC";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, score);
			
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
	
	public void insert(String name, String score) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "INSERT INTO infinity(inf_id, inf_name, inf_score) VALUES(infinity_seq.NEXTVAL, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, score);
			
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
