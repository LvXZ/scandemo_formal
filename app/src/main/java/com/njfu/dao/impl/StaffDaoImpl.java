package com.njfu.dao.impl;

import com.njfu.dao.StaffDao;
import com.njfu.entity.Staff;
import com.njfu.utils.JdbcUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**Staff数据库接口具体操作
 * Created by lvxz on 2018/5/13.
 */

public class StaffDaoImpl implements StaffDao {

    @Override
    public Staff findUserIdAndTccom001AndTirou001ByAccount(String account) {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Staff staff = null;
        try{
            conn = JdbcUtil.getConnection();
            stmt = conn.prepareStatement("select u.loginid, u.account, u.passwd, t1.cwoc, t2.dsca, t2.prcd, t2.inv_permission from userid u, tccom001 t1, tirou001 t2 where t1.loginid = u.loginid and t2.cwoc = t1.cwoc and u.account = ?");
            stmt.setString(1, account);
            rs = stmt.executeQuery();
            if(rs.next()){
                staff = new Staff(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));

                System.out.println("name" + staff.getLoginid());
                return staff;
            }else{
                return null;
            }
        }catch(Exception e){
            System.out.println("error..."+e.toString());
        }finally{
            if(stmt!=null){
                try{
                    stmt.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(conn!=null){
                try{
                    conn.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return staff;
    }

    @Override
    public String findTipcs001_praaByPrcd(String prcd) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String str = null;
        try{
            conn = JdbcUtil.getConnection();
            stmt = conn.prepareStatement("select t.praa from tipcs001 t where prcd = ?");
            stmt.setString(1, prcd);
            rs = stmt.executeQuery();
            if(rs.next()){
                str = rs.getString(1);
                System.out.println("name" + str);
                return str;
            }else{
                return null;
            }
        }catch(Exception e){
            System.out.println("error..."+e.toString());
        }finally{
            if(stmt!=null){
                try{
                    stmt.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(conn!=null){
                try{
                    conn.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return str;
    }

    @Override
    public List<String> findTipcs001_praa() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<String> stringList = null;
        try{
            conn = JdbcUtil.getConnection();
            stmt = conn.prepareStatement("select prcd from tipcs001");
            rs = stmt.executeQuery();

            stringList = new ArrayList<>();
            while(rs.next()){
                stringList.add(rs.getString("prcd"));
            }
            if(stringList.size() == 0){
                return null;
            }
        }catch(Exception e){
            System.out.println("error..."+e.toString());
        }finally{
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                rs = null;
            }

            if(stmt!=null){
                try{
                    stmt.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(conn!=null){
                try{
                    conn.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return stringList;
        }
    }
}
