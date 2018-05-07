package cn.ddcome.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cn.ddcome.daoImpl.TableDaoImpl;
import cn.ddcome.data.JSONArrayResults;
import cn.ddcome.util.MysqlBeautyUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * article表操作类
 * 
 * @author ddcome
 *
 */
public class ArticlesDao implements Serializable, TableDaoImpl {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ArticlesDao.class);

	public JSONObject search(String sqlWhere) {
		String sql = "SELECT "
				+ "id,title,author,file,file_size,content,publish_time,browse_times,share_times,good_idea,type,status,sign "
				+ "FROM articles WHERE "
				+ "id IN (SELECT aid FROM articles_tags WHERE tid IN (SELECT id FROM tags "+ sqlWhere+") AND status=1) AND status=1 ORDER BY publish_time DESC";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		JSONArray result = null;
		System.out.println(sql);

		try {
			conn = MysqlBeautyUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			result = MysqlBeautyUtil.resultSetToJSONArray(rs);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			MysqlBeautyUtil.close();
		}
		return new JSONArrayResults(1, result).values();
	}
	
	@Override
	public JSONObject selectAll() {
		String sql = "SELECT "
				+ "a.id,"
				+ "a.title,"
				+ "a.author,"
				+ "a.file,"
				+ "a.file_size,"
				+ "a.content,"
				+ "a.publish_time,"
				+ "a.browse_times,"
				+ "(SELECT COUNT(*) FROM comments c WHERE c.aid=a.id) comment_times,"
				+ "a.share_times,"
				+ "a.good_idea,"
				+ "a.type,"
				+ "a.status,"
				+ "a.sign "
				+ "FROM articles a "
				+ "WHERE a.status=1 ORDER BY a.publish_time DESC";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		JSONArray result = null;
		System.out.println(sql);

		try {
			conn = MysqlBeautyUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			result = MysqlBeautyUtil.resultSetToJSONArray(rs);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			MysqlBeautyUtil.close();
		}
		return new JSONArrayResults(1, result).values();
	}
	
	public JSONObject selectAllPDF() {
		String sql = "SELECT id,title,author,file,file_size,content,publish_time,browse_times,share_times,good_idea,type,status,sign FROM articles WHERE status=1 AND type='pdf'";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		JSONArray result = null;
		System.out.println(sql);

		try {
			conn = MysqlBeautyUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			result = MysqlBeautyUtil.resultSetToJSONArray(rs);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			MysqlBeautyUtil.close();
		}
		return new JSONArrayResults(1, result).values();
	}
	
	public JSONObject selectAllDefault() {
		String sql = "SELECT id,title,author,file,file_size,content,publish_time,browse_times,share_times,good_idea,type,status,sign FROM articles WHERE status=1 AND type='text'";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		JSONArray result = null;
		System.out.println(sql);

		try {
			conn = MysqlBeautyUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			result = MysqlBeautyUtil.resultSetToJSONArray(rs);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			MysqlBeautyUtil.close();
		}
		return new JSONArrayResults(1, result).values();
	}

	@Override
	public JSONObject selectOne(JSONObject condition) {
		int id = condition.getInt("id");

		String sql = "SELECT " 
				+ "id," 
				+ "title," 
				+ "author," 
				+ "file," 
				+ "file_size," 
				+ "content," 
				+ "publish_time,"
				+ "browse_times," 
				+ "share_times," 
				+ "good_idea," 
				+ "status," 
				+ "type," 
				+ "sign"
				+ " FROM articles WHERE status=1 AND id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		JSONArray result = null;
		System.out.println(sql);

		try {
			conn = MysqlBeautyUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);;
			rs = pstmt.executeQuery();
			result = MysqlBeautyUtil.resultSetToJSONArray(rs);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			MysqlBeautyUtil.close();
		}
		System.out.println(result);
		return new JSONArrayResults(1, result).values();
	}
	
	/**
	 * 有限制条数的查询
	 * 
	 * @param limitNum
	 * @return
	 */
	public JSONObject selectSome(int limitNum) {

		String sql = "SELECT " 
				+ "a.id," 
				+ "a.title," 
				+ "a.author," 
				+ "a.file," 
				+ "a.file_size," 
				+ "a.content," 
				+ "a.publish_time,"
				+ "a.browse_times," 
				+ "(SELECT COUNT(*) FROM comments c WHERE c.aid=a.id) comment_times,"
				+ "a.share_times," 
				+ "a.good_idea," 
				+ "a.status," 
				+ "a.type," 
				+ "a.sign"
				+ " FROM articles a WHERE a.status=1 ORDER BY a.publish_time DESC LIMIT "+limitNum;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		JSONArray result = null;
		System.out.println(sql);

		try {
			conn = MysqlBeautyUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			result = MysqlBeautyUtil.resultSetToJSONArray(rs);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			MysqlBeautyUtil.close();
		}
		System.out.println(result);
		return new JSONArrayResults(1, result).values();
	}
	
	@Override
	public JSONObject selectSome(JSONObject condition) {
		int sign = condition.getInt("sign");

		String sql = "SELECT " 
				+ "id," 
				+ "title," 
				+ "author," 
				+ "file," 
				+ "file_size," 
				+ "content," 
				+ "publish_time,"
				+ "browse_times," 
				+ "share_times," 
				+ "good_idea," 
				+ "status," 
				+ "type," 
				+ "sign"
				+ " FROM articles WHERE status=1 AND sign=" + sign;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		JSONArray result = null;
		System.out.println(sql);

		try {
			conn = MysqlBeautyUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			result = MysqlBeautyUtil.resultSetToJSONArray(rs);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			MysqlBeautyUtil.close();
		}
		System.out.println(result);
		return new JSONArrayResults(1, result).values();
	}

	@Override
	public JSONObject insertSome(JSONObject condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject insertOne(JSONObject condition) {
		String sql = "INSERT INTO articles (" 
				+ "title," 
				+ "file," 
				+ "file_size," 
				+ "author," 
				+ "content," 
				+ "publish_time,"
				+ "browse_times," 
				+ "share_times," 
				+ "good_idea," 
				+ "type," 
				+ "sign" 
				+ ") " 
				+ "values (?,?,?,?,?,now(),?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = MysqlBeautyUtil.getConnection();
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, condition.getString("title"));
			pstmt.setString(2, condition.getString("file"));
			pstmt.setString(3, condition.getString("file_size"));
			pstmt.setString(4, condition.getString("author"));
			pstmt.setString(5, condition.getString("content"));
			pstmt.setString(6, condition.getString("browse_times"));
			pstmt.setString(7, condition.getString("share_times"));
			pstmt.setString(8, condition.getString("good_idea"));
			pstmt.setString(9, condition.getString("type"));
			pstmt.setString(10, condition.getString("sign"));

			pstmt.executeUpdate();
			conn.commit();// 提交事务
			return new JSONArrayResults(1, null).values();// 成功返回1
		} catch (SQLException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
			// 事务回滚
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// 记录日志
				e1.printStackTrace();
				logger.error(e.getMessage());
				// 抛出异常
				throw new RuntimeException("事务回滚失败", e);
			}
		} finally {
			MysqlBeautyUtil.close();
		}
		return new JSONArrayResults(0, null).values();// 失败
	}

	@Override
	public JSONObject updateOne(JSONObject condition) {
		String sql = null;

		JSONArray tags = null;
		int sign = 0;
		String mark = condition.getString("mark");
		if ("...".equals(mark)) {//TODO 需要修改

		} else if ("updateOne".equals(mark)) { // 根据tag更新sign
			sql = "UPDATE articles SET sign=0 WHERE sign=777";
		}

		//System.out.println(tags.toString());

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = MysqlBeautyUtil.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			conn.commit();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			// 事务回滚
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// 记录日志
				e1.printStackTrace();
				// 抛出异常
				throw new RuntimeException("事务回滚失败", e);
			}
		}
		return null;
	}

	@Override
	public JSONObject updateSome(JSONObject condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject deleteOne(JSONObject condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject deleteSome(JSONObject condition) {
		// TODO Auto-generated method stub
		return null;
	}

}
