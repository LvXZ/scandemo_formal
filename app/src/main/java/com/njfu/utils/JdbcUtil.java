package com.njfu.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**数据库启动工具类
 * Created by lvxz on 2018/5/11.
 */
public class JdbcUtil {

    private static String driver = null;
    private static String url = null;
    private static String username = null;
    private static String password = null;

    static{
        try{
            //读取db.properties文件中的数据库连接信息
            /*InputStream in = JdbcUtil.class.getClassLoader().getResourceAsStream("assets/db.properties");
            Properties prop = new Properties();

            prop.load(in);*/


            //获取数据库连接驱动
            //driver = prop.getProperty("driver");
            driver = "oracle.jdbc.driver.OracleDriver";
            //获取数据库连接URL地址
            //url = prop.getProperty("url");
            url = "jdbc:oracle:thin:@139.199.126.58:1521:orcl";
            //获取数据库连接用户名
            //username = prop.getProperty("username");
            username = "ddcmrp";
            //获取数据库连接密码
            //password = prop.getProperty("password");
            password = "ddcmrp";

            //加载数据库驱动
            Class.forName(driver);


        }catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.print("Class");
        }
    }

    /**
     * @Method: getConnection
     * @Description: 获取数据库连接对象
     * @return Connection数据库连接对象
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, username,password);
    }

    /**
     * @Method: release
     * @Description: 释放资源，要释放的资源包括Connection数据库连接对象，负责执行SQL命令的Statement对象，存储查询结果的ResultSet对象
     * @param conn
     * @param st
     * @param rs
     */
    public static void release(Connection conn,Statement st,ResultSet rs){
        if(rs!=null){
            try{
                //关闭存储查询结果的ResultSet对象
                rs.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if(st!=null){
            try{
                //关闭负责执行SQL命令的Statement对象
                st.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(conn!=null){
            try{
                //关闭Connection数据库连接对象
                conn.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void release(ResultSet rs,Statement stat,Connection conn){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if(stat!=null){
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stat = null;
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn = null;
        }
    }

    /**
     * @Method: release
     * @Description: 释放资源，要释放的资源包括Connection数据库连接对象，负责执行SQL命令的Statement对象，存储查询结果的ResultSet对象
     * @param conn
     * @param st
     */
    public static void release(Connection conn,Statement st,Connection conn1){

        if(st!=null){
            try{
                //关闭负责执行SQL命令的Statement对象
                st.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(conn!=null){
            try{
                //关闭Connection数据库连接对象
                conn.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(conn1!=null){
            try{
                //关闭Connection数据库连接对象
                conn1.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}