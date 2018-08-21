package com.njfu.dao.impl;

import android.util.Log;

import com.njfu.dao.PrcdVariableDao;
import com.njfu.dto.InvInventoryDto;
import com.njfu.entity.PrcdVariable;
import com.njfu.utils.Constant;
import com.njfu.utils.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**PrcdVariable数据库接口具体实现
 * Created by lvxz on 2018/5/11.
 */



public class PrcdVariableDaoImpl implements PrcdVariableDao{

    //查询报完工
    public PrcdVariable query(Statement stat, String orderNo, String loginId, String executerId){

        PrcdVariable pV = new PrcdVariable();
        try {
            /*Query part*/
            ResultSet rs = null;

            String sql = "select emno from tccom001 where loginid ='"+loginId+"'";
            rs = stat.executeQuery(sql);
            if(rs.next())
                loginId = rs.getString("emno");
            else {
                pV.writeError(-1, "无此登录人员");
                return pV;
            }
            sql = "select emno from tccom001 where loginid ='"+executerId+"'";
            rs = stat.executeQuery(sql);
            if(rs.next())
                executerId = rs.getString("emno");
            else {
                pV.writeError(-1, "无此操作人员");
                return pV;
            }

            sql = "select t.pdno,t.mitm,t.qutp,w.dsca name,u.dsca cuni "
                    + "from tisfc001 t,tiitm001 w,tcmcs001 u "
                    + "where t.pdnos = '"+orderNo+"' and "
                    + "t.mitm = w.item and w.cuni = u.cuni";
            rs = stat.executeQuery(sql);
            if(rs.next()) {
                pV.pdno = rs.getLong("pdno");
                pV.orderNo = orderNo;
                pV.itemNo = rs.getString("mitm");
                pV.itemName = rs.getString("name");
                pV.itemNum = rs.getDouble("qutp");
                pV.itemUnit = rs.getString("cuni");
            } else {
                pV.writeError(-2, "无符合条件订单");
                return pV;
            }

            sql = "select w.idno,u.praa,w.stat,u.prcd "
                    + "from tipcs004 t,tipcs005 w,tipcs001 u "
                    + "where t.pdno = "+pV.pdno+" and t.stat = '2' "
                    + "and t.idno = w.hid and w.prcd = u.prcd "
                    + "order by to_number(w.prno) asc";
            rs = stat.executeQuery(sql);

            int i = 0;
            if(rs.next()) {

                pV.nodeIdno = new String[40];
                pV.prcdName = new String[40];
                pV.nodeStat = new String[40];
                pV.prcdAll = new String[40];
                do {
                    pV.nodeIdno[i] = rs.getString("idno");
                    pV.prcdName[i] = rs.getString("praa");
                    pV.nodeStat[i] = rs.getString("stat");
                    pV.prcdAll[i] = rs.getString("prcd");
                    i ++;
                }while(rs.next());

            } else {
                pV.writeError(-1, "无符合条件工艺路线");
                return pV;
            }

            int j;
            for(j = 0;j< i ;j ++)
            {
                if( pV.nodeStat[j].equals("1") || pV.nodeStat[j].equals("2"))
                {
                    pV.prcdPos = j;
                    break;
                }
            }
            //标记未开始的报完工单
            if(j==0){
                pV.prcdPos = -1;
                pV.writeError(1,"该订单未开始报完工");
                return pV;
            }
            if(j==i) {
                pV.writeError(-1, "该订单全部已报完工");
                return pV;
            }

            pV.prcdPos--;//redirect to the last finished node (from the first undo node)


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            pV.writeError(-1, "查询工艺出错");
            return pV;
        }
        return pV;
    }

    @Override
    public PrcdVariable queryEndQueryItem(String getTable) {
        Connection connection = null;
        Statement stmt = null;

        PrcdVariable pV = null;
        try{
            connection = JdbcUtil.getConnection();
            stmt = connection.createStatement();
            pV = query(stmt, getTable, "admin", "admin");
            System.out.println("-------pV.getErrorNo() = "+pV.getErrorNo());


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
            if(connection!=null){
                try{
                    connection.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return pV;
    }



    @Override
    public PrcdVariable prcdIssue(String getTable) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        PrcdVariable pV = null;
        try{
            connection = JdbcUtil.getConnection();
            stmt = connection.createStatement();
            pV = issue(stmt, getTable, "admin", "admin");//可以执行
            connection.commit();


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
            if(connection!=null){
                try{
                    connection.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return pV;
    }

    //报完工执行函数
    public PrcdVariable issue(Statement stat, String orderNo, String loginId, String executerId){

        PrcdVariable pV = new PrcdVariable();
        String errorText = "执行工艺完工出错";
        try {
            /*Query part*/
            pV = query(stat, orderNo, loginId, executerId);

            if(pV.getErrorNo() < 0) return pV;

            pV.prcdPos ++;//redirect to the first undo node (from the last finished node)

            /*Execute part*/
            switch(pV.nodeStat[pV.prcdPos]) {
                case "1":{
                    if(fromFree(stat,pV.nodeIdno[pV.prcdPos]) == -1) {
                        pV.writeError(-1, errorText);
                        return pV;
                    }
                    if(fromStart(stat,pV.nodeIdno[pV.prcdPos],loginId,executerId) == -1) {
                        pV.writeError(-1, errorText);
                        return pV;
                    }
                    break;}
                case "2":{
                    if(fromStart(stat,pV.nodeIdno[pV.prcdPos],loginId,executerId) == -1) {
                        pV.writeError(-1, errorText);
                        return pV;
                    }
                    break;}
            }


            if(pV.nodeIdno.length > pV.prcdPos+1) {
                String sql = "update tipcs005 set stat = '1' "
                        + "where idno = '"+pV.nodeIdno[pV.prcdPos+1]+"'";
                stat.executeUpdate(sql);
            }
            else {
                /*针对当前已是最后的工艺节点的执行*/
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            pV.writeError(-1, errorText);
            return pV;
        }
        return pV;

    }

    /**
     *Execute
     */
    public int fromFree(Statement stat,String nodeIdno){
        try {
            Date nowDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            String strDate = sdf.format(nowDate);
            String sql = "update tipcs005 set stat = '2',"
                    + "sjsdt = to_date('"+strDate+"','yyyy-mm-dd hh24:mi:ss') "
                    + "where idno = '"+nodeIdno+"'";
            stat.executeUpdate(sql);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public int fromStart(Statement stat,String nodeIdno,String loginId,String executerId){
        try {
            String sql = "select pdno,gyno,tino,prno,jgsl,sjsdt "
                    + "from tipcs005 where idno = '"+nodeIdno+"'";
            ResultSet rs = stat.executeQuery(sql);
            if(!rs.next()) return -1;
            long pdno = rs.getLong("pdno");
            String gyno = rs.getString("gyno");
            String tino = rs.getString("tino");
            String prno = rs.getString("prno");
            Double jgsl = rs.getDouble("jgsl");
            Date sjsdt = rs.getDate("sjsdt");
            Date nowDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            String strDate = sdf.format(nowDate);

            sql = "SELECT to_char(seq_g_id.nextval) lid From dual";
            rs = stat.executeQuery(sql);
            if(!rs.next()) return -1;
            String tipcs006Idno = rs.getString("lid");


            sql = "insert into tipcs006(idno,lid,gyno,pdno,tino,prno,sera,"
                    + "jgsl,wgsl,bfsl,fgsl,wksdt,wkedt,"
                    + "wkid,bcgj,bcgs,bcdj,stat,trid,trdt) "
                    + "values('"+tipcs006Idno+"','"+nodeIdno+"','"+gyno+"',"
                    + pdno+",'"+tino+"','"+prno+"','1',"
                    + jgsl+","+jgsl+",0,0,"
                    + "to_date('"+sjsdt+"','yyyy-mm-dd hh24:mi:ss'),"
                    + "to_date('"+strDate+"','yyyy-mm-dd hh24:mi:ss'),"
                    + "'"+executerId+"',0,0,0,'F','"+loginId+"',"
                    + "to_date('"+strDate+"','yyyy-mm-dd hh24:mi:ss')"
                    + ")";
            stat.executeUpdate(sql);

            sql = "update tipcs005 set stat = '3',"
                    + "sjsdt = to_date('"+strDate+"','yyyy-mm-dd hh24:mi:ss') "
                    + "where idno = '"+nodeIdno+"'";
            stat.executeUpdate(sql);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return -1;
        }
        return 0;
    }



    //打包最后一道工序函数
    @Override
    public void completeIn(String getTable) {
        Connection conn = null;
        Statement stmt = null;
        try{
            conn = JdbcUtil.getConnection();
            stmt = conn.createStatement();
            int completeResult = execute(conn, getTable);

            switch(completeResult) {
                case 1:{
                    conn.commit();
                    Log.i(Constant.TAG, "<-------------completeIn All done----------->");
                }break;

                case -1:{
                    conn.rollback();
                    Log.i(Constant.TAG, "<-------------completeIn Sql error----------->");
                }break;
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
    }


    //数据库打包最后一道工序函数执行函数
    public int execute(Connection conn,String orderNo) {

        //1正确，-1错误
        InvInventoryDto invRow = new InvInventoryDto();
        try{
            String sql = "select mitm,qutp,cwar,pdno from tisfc001 where pdnos = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, orderNo);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                invRow.setOrderNumber(orderNo);
                invRow.setPdno(rs.getInt(4));
                invRow.setItem(rs.getString(1));
                invRow.setExecQuantity(rs.getDouble(2));
                invRow.setWhId(rs.getString(3));

            }else {
                return -1;
            }
            //获取库位
            sql = "select loca_id from inv_location where wh_id = ? and loca_type  = 'N' and rownum = 1";
            ps = conn.prepareStatement(sql);
            ps.setString(1, invRow.getWhId());
            //ps.setString(1, invRow.whId);
            rs = ps.executeQuery();
            if(rs.next()) {
                invRow.setLocaId(rs.getString(1));
            }else {
                return -1;
            }


            //修改inv_inventory
            sql = "update inv_inventory set quantity = quantity + ?"
                    + "where item = ? and wh_id = ? and loca_id = ?";
            ps = conn.prepareStatement(sql);

            ps.setDouble(1,invRow.getExecQuantity());
            ps.setString(2,invRow.getItem());
            ps.setString(3,invRow.getWhId());
            ps.setString(4,invRow.getLocaId());

            ps.executeUpdate();
            //修改tdinv150
            sql = "delete from tdinv150 where orno = ? and pono = 0";
            ps = conn.prepareStatement(sql);

            ps.setInt(1,Integer.parseInt(invRow.getOrderNumber()));
            //ps.setInt(1,Integer.parseInt(invRow.orderNumber));
            ps.executeUpdate();
            //修改tisfc001
            sql = "update tisfc001 set osta='7',qups = qups + ? where pdnos = ?";
            ps = conn.prepareStatement(sql);

            ps.setDouble(1,invRow.getExecQuantity());
            ps.setString(2,invRow.getOrderNumber());

            ps.executeUpdate();

        }catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 1;

    }
}




