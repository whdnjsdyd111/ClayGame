package main.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import main.multi.MultiGamePane;

public class MemberDAO {
	private static MemberDAO instance = new MemberDAO();
	
	private MemberDAO() {}
	
	public static MemberDAO getInstance() {
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
	
	public int checkId(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int check = 0;
		
		boolean check_num_eng = Pattern.matches("^[a-zA-Z0-9]*$", id);
		
		if(!check_num_eng)
			return 2;	// 정규식 영어, 숫자를 만족하지 않을 시 2
		if(id.length() < 6 || id.length() > 18)
			return 3;	// 길이 불만족 3
		
		try {
			conn = getConnection();
			String sql = "SELECT mem_id FROM member WHERE mem_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
				check = 1;
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
		
		return check;
	}
	
	public String checkAll(String id, String pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String nickname = null;
		try {
			conn = getConnection();
			String sql = "SELECT mem_pw, mem_nickname FROM member WHERE mem_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
				if(BCrypt.checkpw(SHA256.getInstance().getSha256(pw), rs.getString(1)))
					nickname = rs.getString(2);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return nickname;
	}
	
	public void insert(String id, String pw, String nick) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "INSERT INTO member(mem_id, mem_pw, mem_nickname) VALUES(?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, BCrypt.hashpw(SHA256.getInstance().getSha256(pw), BCrypt.gensalt()));
			pstmt.setString(3, nick);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null)
				try { pstmt.close(); } catch (SQLException e) {}
			if(conn != null)
				try { conn.close(); } catch(SQLException e) {}
		}
	}
	
	public void changePw(String id, String pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "UPDATE member SET mem_pw = ? WHERE mem_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, BCrypt.hashpw(SHA256.getInstance().getSha256(pw), BCrypt.gensalt()));
			pstmt.setString(2, id);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null)
				try { pstmt.close(); } catch (SQLException e) {}
			if(conn != null)
				try { conn.close(); } catch(SQLException e) {}
		}
	}
	
	public String updatePVP(String kind) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String str = "";
		
		try {
			conn = getConnection();
			String sql = "UPDATE member SET " + kind + " = " + kind + " + 1 WHERE mem_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, MultiGamePane.id);
			
			pstmt.executeUpdate();
			pstmt.close();
			
			sql = "SELECT win, draw, lose FROM member WHERE mem_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, MultiGamePane.id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int total = 0;
				
				for (int i = 0; i < 3; i++) {
					total += rs.getInt(i + 1);
				}
				
				str = total + "전 " + rs.getInt(1) + "승 " + rs.getInt(2) + "무 " + rs.getInt(3) + "패";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null)
				try { rs.close(); } catch(SQLException e) {}
			if(pstmt != null)
				try { pstmt.close(); } catch (SQLException e) {}
			if(conn != null)
				try { conn.close(); } catch(SQLException e) {}
		}
		
		return str;
	}
}
