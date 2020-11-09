package main.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TimeDAO {
	private static TimeDAO instance = new TimeDAO();
	
	private TimeDAO() {}
	
	public static TimeDAO getInstance() {
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
	
	public String[][] getTime(String score, int[] my_index) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[][] str = new String[21][3];
		int index = 0;
		int parse_score = Integer.parseInt(score);
		try {
			conn = getConnection();
			String sql = "WITH " + 
					"total_rank AS (SELECT time_name, time_score, DENSE_RANK () over (order by time_score DESC) AS tot_rank FROM time), " + 
					"near_rank AS (SELECT tot_rank, time_name, time_score FROM total_rank WHERE time_score > ? ORDER BY ROWNUM DESC) " + 
					"SELECT * from near_rank WHERE ROWNUM <= 10 ORDER BY time_score DESC";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, parse_score);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				for (int i = 0; i < 3; i++) {
					str[index][i] = rs.getString(i + 1);
				}
				index++;
			}
			my_index[0] = index;
			str[index][0] = "";
			str[index][1] = "???";
			str[index++][2] = score;
			
			rs.close();
			pstmt.close();
			sql = "WITH " + 
					"total_rank AS (SELECT time_name, time_score, DENSE_RANK () over (order by time_score DESC) AS tot_rank FROM time), " + 
					"near_rank AS (SELECT tot_rank, time_name, time_score FROM total_rank WHERE time_score <= ? ORDER BY ROWNUM ASC) " + 
					"SELECT * from near_rank WHERE ROWNUM <= 10 ORDER BY time_score DESC";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, parse_score);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				for (int i = 0; i < 3; i++) {
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
			String sql = "INSERT INTO time VALUES(infinity_seq.NEXTVAL, ?, ?)";
			
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
