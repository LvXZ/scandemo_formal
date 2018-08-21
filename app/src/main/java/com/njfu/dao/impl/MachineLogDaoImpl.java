package com.njfu.dao.impl;

import com.njfu.dao.MachineLogDao;
import com.njfu.entity.MachineLog;
import com.njfu.utils.JdbcUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**MachineLog数据库接口具体操作
 * Created by lvxz on 2018/6/1.
 */

public class MachineLogDaoImpl implements MachineLogDao{

    @Override
    public MachineLog queryMachineLogOne(String machId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        MachineLog machineLog = null;
        try{
            conn = JdbcUtil.getConnection();
            stmt = conn.prepareStatement("select m.prcd, m.mach_id, m.mach_name from machine_log m where m.mach_id = ?");
            stmt.setString(1, machId);
            rs = stmt.executeQuery();
            if(rs.next()){
                machineLog = new MachineLog(rs.getString(1),rs.getString(2),rs.getString(3));

                System.out.println("name" + machineLog.getMachName());
                return machineLog;
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
        return machineLog;
    }
}
