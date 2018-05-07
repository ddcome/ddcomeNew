package cn.ddcome.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * MySQL工具类
 * 
 * @author ddcome
 *
 */
public class MysqlUtil {
	private static Logger logger = Logger.getLogger(MysqlUtil.class); 
	private static MysqlUtil mysqlUtil = null;
	private static String driverName;
	private static String url;
	private static String username;
	private static String password;
	
	/**
	 * 单例模式
	 * 获取MysqlUtil类对象
	 * 
	 * @return mysqlUtil 对象
	 */
	public static MysqlUtil getInstance() {
		if(mysqlUtil==null) {
			mysqlUtil = new MysqlUtil();
			return mysqlUtil;
		}
		return mysqlUtil;
	}

	/**
	 * 获取Connection对象
	 * 
	 * @return conn
	 */
	public Connection getConnection() {
		Connection conn = null;
		ResourceBundle rb;
		try {
			//获取配置文件
			rb = ResourceBundle.getBundle("jdbc");
			//获取配置值
			driverName = rb.getString("driverName");
			url = rb.getString("url");
			username = rb.getString("username");
			password = rb.getString("password");
			//加载驱动
			Class.forName(driverName);
			conn = DriverManager.getConnection(url,username,password);
		} catch (SQLException ex) {
			// 记录日志
			logger.error("SQLException Message:"+ex.getMessage()); 
			logger.error("SQLState:"+ex.getSQLState()); 
			logger.error("VendorError:"+ex.getErrorCode()); 
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException Message:"+e.getMessage());
			e.printStackTrace();
		} 
		return conn;
	}
	
	/**
	 * �ر���ԴConnection
	 * 
	 * @param conn
	 */
	public void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			// ��¼��־
			e.printStackTrace();
			throw new RuntimeException("�ر���Դʧ��", e);
		}
	}

	/**
	 * �ر���ԴConnection��Statement
	 * 
	 * @param conn
	 * @param stmt
	 */
	public void close(Connection conn, Statement stmt) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			// ��¼��־
			e.printStackTrace();
			// �׳��쳣
			throw new RuntimeException("�ر���Դʧ��", e);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				// ��¼��־
				e.printStackTrace();
				// �׳��쳣
				throw new RuntimeException("�ر���Դʧ��", e);
			}
		}
	}
	
	/**
	 * �ر���ԴConnection��Statement��ResultSet
	 * 
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			// ��¼��־
			e.printStackTrace();
			// �׳��쳣
			throw new RuntimeException("�ر���Դʧ��", e);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				// ��¼��־
				e.printStackTrace();
				// �׳��쳣
				throw new RuntimeException("�ر���Դʧ��", e);
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e) {
					// ��¼��־
					e.printStackTrace();
					// �׳��쳣
					throw new RuntimeException("��Դ�ر�ʧ��", e);
				}
			}
		}
	}
	
	/**
     * 将resultSet转化为JSONArray对象
     * @param rs
     * @return
     * @throws SQLException
     * @throws JSONException
     */
    public JSONArray resultSetToJSONArray(ResultSet rs) throws SQLException,JSONException
    {
        // json数组
        JSONArray array = new JSONArray();
        // 获取列数
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        // 遍历ResultSet中的每条数据
        while (rs.next()) {
            JSONObject jsonObj = new JSONObject();
            // 遍历每一列
            for (int i = 1; i <= columnCount; i++) {
                String columnName =metaData.getColumnLabel(i);
                String value = rs.getString(columnName);
                jsonObj.put(columnName, value);
            }
            array.add(jsonObj);
        }
        return array;
    }



}
