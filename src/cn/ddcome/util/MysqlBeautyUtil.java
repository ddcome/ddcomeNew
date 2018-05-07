package cn.ddcome.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

/**
 * 改进版本的MySQL工具类
 * 含有线程池的技术
 *
 * @author ddcome
 */
public class MysqlBeautyUtil {
    private static Logger logger = Logger.getLogger(MysqlBeautyUtil.class);

    //数据库连接池
    private static BasicDataSource dbcp;
    //为不同线程管理连接
    private static ThreadLocal<Connection> tl;

    //通过配置文件来获取数据库参数
    static {
        try {
            System.out.println("init ... ");
            Properties prop
                    = new Properties();

            InputStream is
                    = MysqlBeautyUtil.class.getClassLoader()
                    .getResourceAsStream(
                            "jdbc.properties");

            prop.load(is);
            is.close();

            //一、初始化连接池
            dbcp = new BasicDataSource();

            //test
            System.out.println(prop.getProperty("username"));

            //设置驱动 (Class.forName())
            dbcp.setDriverClassName(prop.getProperty("driverName"));
            //设置url
            dbcp.setUrl(prop.getProperty("url"));
            //设置数据库用户名
            dbcp.setUsername(prop.getProperty("username"));
            //设置数据库密码
            dbcp.setPassword(prop.getProperty("password"));
            //初始连接数量
            dbcp.setInitialSize(
                    Integer.parseInt(
                            prop.getProperty("initsize")
                    )
            );
            //连接池允许的最大连接数
            dbcp.setMaxActive(
                    Integer.parseInt(
                            prop.getProperty("maxactive")
                    )
            );
            //设置最大等待时间
            dbcp.setMaxWait(
                    Integer.parseInt(
                            prop.getProperty("maxwait")
                    )
            );
            //设置最小空闲数
            dbcp.setMinIdle(
                    Integer.parseInt(
                            prop.getProperty("minidle")
                    )
            );
            //设置最大空闲数
            dbcp.setMaxIdle(
                    Integer.parseInt(
                            prop.getProperty("maxidle")
                    )
            );
            //初始化线程本地
            tl = new ThreadLocal<Connection>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() {
        /*
         * 通过连接池获取一个空闲连接
         */
        Connection conn = null;
        try {
            conn = dbcp.getConnection();
            tl.set(conn);
        } catch (SQLException e) {
            logger.error("SQLException Message:" + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     */
    public static void close() {
        try {
            Connection conn = tl.get();
            if (conn != null) {
                /*
                 * 通过连接池获取的Connection
                 * 的close()方法实际上并没有将
                 * 连接关闭，而是将该链接归还。
                 */
                conn.close();
                tl.remove();
            }
        } catch (Exception e) {
            logger.error("SQLException Message:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 将resultSet转化为JSONArray对象
     *
     * @param rs
     * @return
     * @throws SQLException
     * @throws JSONException
     */
    public static JSONArray resultSetToJSONArray(ResultSet rs) throws SQLException, JSONException {
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
                String columnName = metaData.getColumnLabel(i);
                String value = rs.getString(columnName);
                jsonObj.put(columnName, value);
            }
            array.add(jsonObj);
        }
        return array;
    }

    public static void main(String[] args) {
        System.out.println(MysqlBeautyUtil.getConnection());
    }


}
