package com.njfu.dao.impl;

import com.njfu.dao.Machine001Dao;
import com.njfu.entity.Machine001;
import com.njfu.entity.Staff;
import com.njfu.utils.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**机器记录对象Machine001数据库具体实现
 * Created by lvxz on 2018/6/2.
 */

public class Machine001DaoImpl implements Machine001Dao{

    @Override
    public int insertMachine001One(Machine001 machine001) {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            conn = JdbcUtil.getConnection();
            stmt = conn.prepareStatement("insert into machine001(mach_id, pdnos, account, add_date) values(?,?,?,?)");
            stmt.setString(1, machine001.getMach_id());
            stmt.setString(2, machine001.getPdnos());
            stmt.setString(3, machine001.getAccount());
            stmt.setString(4, machine001.getAdd_date());
            stmt.executeQuery();

        }catch(Exception e){
            System.out.println("error..."+e.toString());
            return 0;
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
        return 1;
    }
}
