package im.heart.core.plugins.sqlite;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Preconditions;

public class SqliteHelper {
	protected static final Logger logger = LoggerFactory.getLogger(SqliteHelper.class);
	/**
	 * 
	 * @功能说明：获取数据库连接
	 * @param path
	 * @return
	 */
	public static Connection getConnection(String path){
		Preconditions.checkNotNull(path);
		try {
			Class.forName("org.sqlite.JDBC");//加载驱动
			Connection conn= DriverManager.getConnection("jdbc:sqlite:"+path);
			return conn;
		} catch (ClassNotFoundException e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		} catch (SQLException e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		}
		return null;
	}
	/**
	 * 
	 * @功能说明：判断表是否存在
	 * @param path
	 * @param tableName
	 * @return
	 */
	public static  boolean existsTable(String path,String tableName){
		logger.info("existsTable:{} ......",tableName);
		ResultSet rs = executeQuery(path,"select count(*) total from sqlite_master where type='table' and name='"+tableName+"' ;");
		try {
			int total=0;
			if(rs.next()){
				total=rs.getInt("total");
				if(total==1){
					return true;
				}
			}
		} catch (SQLException e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		}
		return false;
	}
	
	
	public static  int dropTable(String path,String tableName){
		logger.info("dropTable :{} ......",tableName);
		Connection conn=null;
		Statement state=null;
		int tow=-1;
		try {
			conn=getConnection(path);
			state = conn.createStatement();
			tow=state.executeUpdate("drop table "+tableName+" ;");
			state.executeUpdate("vacuum table "+tableName+" ;");
		} catch (SQLException e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		}finally{
			closeQuietly(state);
			closeQuietly(conn);
		}
		return tow;
	}
	
	public static  ResultSet executeQuery(String path,String sql){
		logger.info("executeQuery sql :{}......",sql);
		Connection conn=null;
		Statement state=null;
		try {
			conn=getConnection(path);
			state = conn.createStatement();
			ResultSet rs = state.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		}finally{
			closeQuietly(state);
			closeQuietly(conn);
		}
		return null;
	}
	/**
	 * 
	 * @功能说明：统计表记录数
	 * @param path
	 * @param tableName
	 * @return
	 */
	public static  int countTable(String path,String tableName){	
		ResultSet rs = executeQuery(path,"select count(*) total from "+tableName+" ;");
		int total=0;
		try {
			if(rs.next()){
				total=rs.getInt("total");
			}
		} catch (SQLException e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		}
		return total;
	}
	/**
	 * 
	 * @功能说明：清空表记录
	 * @param path
	 * @param tableName
	 * @return
	 */
	public static  int deleteTable(String path,String tableName){
		logger.info("deleteTable : {} ......",tableName);
		Connection conn=null;
		Statement state=null;
		int tow=-1;
		try {
			conn=getConnection(path);
			state = conn.createStatement();
			tow=state.executeUpdate("delete table "+tableName+" ;");
			state.executeUpdate("vacuum table "+tableName+" ;");
		} catch (SQLException e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		}finally{
			closeQuietly(state);
			closeQuietly(conn);
		}
		return tow;
	}
	
	/**
	 * 
	 * @功能说明：与integrity_check相像，但是略去了对索引内容与表内容匹配的校验。
	 * @param path
	 * @return
	 */
	public static  boolean quickCheck(String path){
		logger.info("pragma quick_check ......");
		ResultSet rs = executeQuery(path,"pragma quick_check ;");
		try {
			if(rs.next()){
				String check=rs.getString("integrity_check");
				if("ok".equalsIgnoreCase(check)){
					return true;
				}
			}
		} catch (SQLException e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		}
		return false;
	}
	/**
	 * 
	 * @功能说明：执行整个库的完全性检查，会查看错序的记录、丢失的页，毁坏的索引等。
	 * @param path
	 * @return
	 */
	public static  boolean integrityCheck(String path){
		logger.info("pragma integrity_check  ......");
		ResultSet rs = executeQuery(path,"pragma integrity_check ;");
		try {
			if(rs.next()){
				String check=rs.getString("integrity_check");
				if("ok".equalsIgnoreCase(check)){
					return true;
				}
			}
		} catch (SQLException e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		}
		return false;
	}
	
	/**
	 * 
	 * @功能说明：关闭连接
	 * @param conn
	 */
	public static void  closeQuietly(Connection conn){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				conn=null;
			}
		}
	}
	/**
	 * 
	 * @功能说明：关闭Statement;
	 * @param state
	 */
	public static void  closeQuietly(Statement state){
		if(state!=null){
			try {
				state.close();
			} catch (SQLException e) {
				state=null;
			}
		}
	}
}
