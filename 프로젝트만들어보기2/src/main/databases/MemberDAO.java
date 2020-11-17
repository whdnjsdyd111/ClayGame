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
	
	public int checkId(String id) {		// 아이디를 체크하는 메소드
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int check = 0;
		
		boolean check_num_eng = Pattern.matches("^[a-zA-Z0-9]*$", id);	// 숫자와 영어가 들어갔는지 체크
		
		if(!check_num_eng)
			return 2;	// 정규식 영어, 숫자를 만족하지 않을 시 2
		if(id.length() < 6 || id.length() > 18)
			return 3;	// 길이 불만족 3
		
		try {
			conn = getConnection();
			String sql = "SELECT mem_id FROM member WHERE mem_id = ?";	// 디비에 이미 아이디가 있는지 보기위해 Select
			
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
	
	public String checkAll(String id, String pw) {	// 아이디와 비밀번호로 모두 체크
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String nickname = null;
		try {
			conn = getConnection();
			String sql = "SELECT mem_pw, mem_nickname FROM member WHERE mem_id = ?";	// 해당 아이디로 패스워드와 닉네임 가져오기
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
				if(BCrypt.checkpw(SHA256.getInstance().getSha256(pw), rs.getString(1)))	// 비크립트, Sha256으로 암호화 비밀번호 체크
					nickname = rs.getString(2);	// 비밀번호가 맞다면 nickname에 닉네임 저장
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return nickname;	// 닉네임 리턴
	}
	
	public void insert(String id, String pw, String nick) {	// 회원가입 시의 정보들을 디비에 저장하는 메소드
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "INSERT INTO member(mem_id, mem_pw, mem_nickname) VALUES(?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, BCrypt.hashpw(SHA256.getInstance().getSha256(pw), BCrypt.gensalt()));	// 암호화해서 비밀번호 저장
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
	
	public void changePw(String id, String pw) {	// 비밀번호를 바꾸는 메소드
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "UPDATE member SET mem_pw = ? WHERE mem_id = ?";	// 해당 아이디의 비밀번호를 바꾸기
			
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
	
	public String updatePVP(String kind) {	// 해당 플레이어의 전적 업데이트
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
			
			sql = "SELECT win, draw, lose FROM member WHERE mem_id = ?";	// 전적을 업데이트하고 보여주기 위해서 SELECT
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
	
	public String getPVP() {	// PVP 전적 가져오는 메소드
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String str = "";	// 전적의 정보를 저장할 String 초기화
		
		try {
			conn = getConnection();
			String sql = "SELECT win, draw, lose FROM member WHERE mem_id = ?";
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
