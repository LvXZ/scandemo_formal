package com.njfu.dao.impl;

import com.njfu.dao.ItemDao;
import com.njfu.entity.InvInventory;
import com.njfu.entity.InvMoveOrder;
import com.njfu.entity.InvMoveOrderView;
import com.njfu.entity.Item;
import com.njfu.utils.JdbcUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.njfu.dto.InvInventoryDto;

/**Item数据库接口具体操作
 * Created by lvxz on 2018/5/11.
 */

public class ItemDaoImpl implements ItemDao{

    @Override
    public Item queryOne(String itemId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Item item = null;
        try{
            conn = JdbcUtil.getConnection();
            stmt = conn.prepareStatement("select t.item,t.dsca,nvl(v.quantity,0)" +
                    "  from tiitm001 t,(select item,sum(quantity) quantity from inv_inventory where item like ? group by item)v" +
                    "  where t.item like ? and  t.item = v.item(+)");
            stmt.setString(1, itemId);
            stmt.setString(2, itemId);
            rs = stmt.executeQuery();
            if(rs.next()){
                //构造对象
                item = new Item(rs.getString(1), rs.getString(2), "众多库房都有", String.valueOf(rs.getDouble(3)), 0,"");
                System.out.println("name" + item.getItemID());
                return item;
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
        return item;
    }

    @Override
    public List<Item> queryOut(String pdnos) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Item> itemList =null;
        try{
            conn = JdbcUtil.getConnection();
            stmt = conn.prepareStatement("select tiitm001.item,tiitm001.dsca,"
                    + "inv_move_suggest.su_quantity - inv_move_suggest.execed_quantity qsts,"
                    + "inv_move_suggest.source_wh_id cwar "
                    + "from inv_move_suggest,tiitm001 "
                    + "where to_number(order_number) = ("
                    + "select pdno from tisfc001 where pdnos = ?) and "
                    + "inv_move_suggest.su_quantity - inv_move_suggest.execed_quantity > 0 "
                    + "and tiitm001.item = inv_move_suggest.item");
            stmt.setString(1, pdnos);
            rs = stmt.executeQuery();
            itemList = new ArrayList<>();
            while(rs.next()){
                //构造对象
                Item item = new Item(rs.getString("item"), rs.getString("dsca"), rs.getString("cwar"), String.valueOf(rs.getDouble(3)), rs.getDouble(3),"出库");
                //System.out.println("name" + item.getName_item());
                itemList.add(item);
            }
            if(itemList.size() == 0){
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
            return itemList;
        }
    }

    @Override
    public List<Item> queryOutAll(String pdnos) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Item> itemList =null;
        try{
            conn = JdbcUtil.getConnection();
            stmt = conn.prepareStatement("select tiitm001.item,tiitm001.dsca,"
                    + "inv_move_suggest.su_quantity - inv_move_suggest.execed_quantity qsts,"
                    + "inv_move_suggest.source_wh_id cwar "
                    + "from inv_move_suggest,tiitm001 "
                    + "where to_number(order_number) = ("
                    + "select pdno from tisfc001 where pdnos = ?) "
                    + "and tiitm001.item = inv_move_suggest.item");
            stmt.setString(1, pdnos);
            rs = stmt.executeQuery();
            itemList = new ArrayList<>();
            while(rs.next()){
                //构造对象
                Item item = new Item(rs.getString("item"), rs.getString("dsca"), rs.getString("cwar"), String.valueOf(rs.getDouble(3)), rs.getDouble(3),"出库");
                //System.out.println("name" + item.getName_item());
                itemList.add(item);
            }
            if(itemList.size() == 0){
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
            return itemList;
        }
    }

    @Override
    public List<Item> queryIn(String pdnos) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Item> itemList =null;
        try{
            conn = JdbcUtil.getConnection();
            stmt = conn.prepareStatement("select t2.item,t3.dsca,t2.cwar,t2.oqua,t2.dqua " +
                    "from tdpur040 t1, tdpur041 t2, tiitm001 t3 " +
                    "where t2.orno = t1.orno and  t1.orno = ? and t2.item = t3.item and t2.bqua > 0");
            stmt.setString(1, pdnos);
            rs = stmt.executeQuery();
            itemList = new ArrayList<>();
            while(rs.next()){
                //构造对象
                Item item = new Item(rs.getString("item"), rs.getString("dsca"), rs.getString("cwar"), String.valueOf(rs.getDouble(4)), rs.getDouble(5),"入库");

                //System.out.println("name" + item.getName_item());
                itemList.add(item);
            }
            if(itemList.size() == 0){
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
            return itemList;
        }
    }

    @Override
    public List<Item> queryInAll(String orno) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Item> itemList =null;
        try{
            conn = JdbcUtil.getConnection();
            stmt = conn.prepareStatement("select t2.item,t3.dsca,t2.cwar,t2.oqua,t2.dqua " +
                    "from tdpur040 t1, tdpur041 t2, tiitm001 t3 " +
                    "where t2.orno = t1.orno and  t1.orno = ? and t2.item = t3.item");

            stmt.setInt(1, Integer.valueOf(orno));
            rs = stmt.executeQuery();
            itemList = new ArrayList<>();
            while(rs.next()){
                //构造对象
                Item item = new Item(rs.getString("item"), rs.getString("dsca"), rs.getString("cwar"), String.valueOf(rs.getDouble(4)), rs.getDouble(5),"入库");

                itemList.add(item);
            }
            if(itemList.size() == 0){
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
            return itemList;
        }
    }

    @Override
    public int executeOut(String getTable, String getID, Connection conn) {
        Statement stmt = null;
        ResultSet rs = null;

        InvInventoryDto invRow = new InvInventoryDto();
        try{
            stmt = conn.createStatement();

            String sql = "select t.line_number,t.su_quantity - t.execed_quantity,t.source_wh_id,t.source_loca_id "
                    + "from inv_move_suggest t,tisfc001 v "
                    + "where t.order_number = to_char(v.pdno) "
                    + "and v.pdnos = ? and t.item = ? "
                    + "and t.su_quantity - t.execed_quantity > 0 ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, getTable);
            ps.setString(2, getID);
            rs = ps.executeQuery();
            if(rs.next()) {

                invRow.setOrderNumber(getTable);
                invRow.setLineNumber(rs.getString(1));
                invRow.setItem(getID);
                invRow.setExecQuantity(rs.getDouble(2));
                invRow.setWhId(rs.getString(3));
                invRow.setLocaId(rs.getString(4));
                /*invRow.orderNumber = getTable;
                invRow.lineNumber = rs.getString(1);
                invRow.item = getID;
                invRow.execQuantity = rs.getDouble(2);
                invRow.whId = rs.getString(3);
                invRow.locaId = rs.getString(4);*/

            }else {
                return -1;//错误
            }

            //修改inv_move_suggest
            sql = "update inv_move_suggest set execed_quantity = execed_quantity + ? "
                    + "where order_number = ? and item = ?";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, invRow.getExecQuantity());
            ps.setString(2, invRow.getOrderNumber());
            ps.setString(3, invRow.getItem());
            /*ps.setDouble(1, invRow.execQuantity);
            ps.setString(2, invRow.orderNumber);
            ps.setString(3, invRow.item);*/

            ps.executeUpdate();
            //修改tisfc001
            sql = "update tisfc001 set osta = 'A' "
                    + "where pdnos = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, getTable);
            ps.executeUpdate();
            //修改inv_inventory(包括数量和已分配)
            sql = "update inv_inventory set quantity = quantity - ?,assign_quantity = assign_quantity - ?"
                    + "where item = ? and wh_id = ? and loca_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1,invRow.getExecQuantity());
            ps.setDouble(2,invRow.getExecQuantity());
            ps.setString(3,invRow.getItem());
            ps.setString(4,invRow.getWhId());
            ps.setString(5,invRow.getLocaId());
            /*ps.setDouble(1,invRow.execQuantity);
            ps.setDouble(2,invRow.execQuantity);
            ps.setString(3,invRow.item);
            ps.setString(4,invRow.whId);
            ps.setString(5,invRow.locaId);*/

            ps.executeUpdate();
            //修改tdinv150
            sql = "delete from tdinv150 where orno = ? and pono = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,Integer.parseInt(invRow.getOrderNumber()));
            ps.setInt(2,Integer.parseInt(invRow.getLineNumber()));
            /*ps.setInt(1,Integer.parseInt(invRow.orderNumber));
            ps.setInt(2,Integer.parseInt(invRow.lineNumber));*/

            ps.executeUpdate();

        }catch(Exception e){
            System.out.println("error..."+e.toString());
            return -1;//错误
        }finally{
            if(stmt!=null){
                try{
                    stmt.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return 1;//正确
    }

    @Override
    public int executeIn(String getTable, String getID, Connection conn) {

        //用queryAll
        PreparedStatement stmt = null;
        // 0 错误， 1成功
        int flag = 0;

        try{
            conn = JdbcUtil.getConnection();
            stmt = conn.prepareStatement("update tdpur041 " +
                    "set dqua = oqua, bqua = 0 " +
                    "where item = ? and orno = (select orno from tdpur040 where orno = ?) ");


            stmt.setString(1, getID);
            stmt.setString(2, getTable);


            System.out.println("---1---");
            flag = stmt.executeUpdate();
            System.out.println("---2---");

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
            return flag;
        }

    }


    /*********************新本dao***********************/
    @Override
    public InvMoveOrder getInvMoveOrderById(String orderId){

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Item item = null;

        InvMoveOrder invMoveOrder = null;
        try{
            conn = JdbcUtil.getConnection();
            stmt = conn.prepareStatement("select " +
                    "ORDER_ID, ORDER_STATUS, TRANS_ID, ITEM_NUMBER, BILL_LIST_ID, SOURCE_WH_ID, SOURCE_LOCA_ID, " +
                    "SOURCE_ORDER_NUMBER, SOURCE_ORG_ID, DEST_WH_ID, DEST_LOCA_ID, DEST_ORDER_NUMBER, " +
                    "DEST_ORG_ID, ORDER_QUANTITY, EXEC_QUANTITY, LOT_NUMBER, SERIAL_NUMBER, OPERATE_DEPA, " +
                    "OPERATE_BY, OPERATE_DATE, PROCESS_DEPA, PROCESS_BY, PROCESS_DATE, ATTRIBUTE, EXEC_BY, " +
                    "EXEC_DATE, EXEC_ERR_MSG, CREATE_BY, CREATE_DATE, MODIFY_BY, MODIFY_DATE, ITEM_SPEC, " +
                    "ORDER_TYPE, COPR, SU_ID, IS_FREE, SU_TYPE, PROJECT_NO " +
                    "form INV_MOVE_ORDER where ORDER_ID = ?");
            stmt.setString(1, orderId);
            rs = stmt.executeQuery();
            if(rs.next()){
                //构造对象
                invMoveOrder = new InvMoveOrder(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getShort(14),rs.getShort(15));
                System.out.println("status" + invMoveOrder.getOrderStatus());
                invMoveOrder.setFlagCode(1);
                return invMoveOrder;
            }else{
                invMoveOrder.setFlagCode(100);
                return invMoveOrder;
            }
        }catch(Exception e){
            System.out.println("error..."+e.toString());
            invMoveOrder.setFlagCode(-1);
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
        return invMoveOrder;


    }


    @Override
    public InvMoveOrderView getInvMoveOrderViewById(String orderId){

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Item item = null;

        InvMoveOrderView invMoveOrderView = null;
        try{
            conn = JdbcUtil.getConnection();
            stmt = conn.prepareStatement("select trans_type, trans_code, trans_name, trans_flow, user_define_flag, enable_flag " +
                    "from V_INV_MOVE_ORDER where ORDER_ID = ?");
            stmt.setString(1, orderId);
            rs = stmt.executeQuery();
            if(rs.next()){
                //构造对象
                invMoveOrderView = new InvMoveOrderView(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
                System.out.println("TransName" + invMoveOrderView.getTransName());
                invMoveOrderView.setFlagCode(1);
                return invMoveOrderView;
            }else{
                invMoveOrderView.setFlagCode(100);
                return invMoveOrderView;
            }
        }catch(Exception e){
            System.out.println("error..."+e.toString());
            invMoveOrderView.setFlagCode(-1);
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
        return invMoveOrderView;


    }



    @Override
    public String getTiitm001_KLTCByItemNumber(String itemNumber){

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            conn = JdbcUtil.getConnection();
            stmt = conn.prepareStatement("select kltc from tiitm001 where item = ?");
            stmt.setString(1, itemNumber);
            rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getString(1);
            }else{
                return "-99";
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
        return "-100";


    }


    @Override
    public BigDecimal getAvailable(InvInventory invInventory){

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        BigDecimal returnNum = null;

        try{
            conn = JdbcUtil.getConnection();
            stmt = conn.prepareStatement("select quantity - nvl(assign_quantity,0) - nvl(blocked_quantity,0) from inv_inventory_loca " +
                    "where item = ? and NvL(item_spec,'#') = NvL(?,'#') and wh_id = ? and loca_id = ?");
            stmt.setString(1, invInventory.getItem());
            stmt.setString(2, invInventory.getItemSpec());
            stmt.setString(3, invInventory.getWhId());
            stmt.setString(4, invInventory.getLocaId());
            rs = stmt.executeQuery();

            if(rs.next()){
                return rs.getBigDecimal(1);
            }else{
                returnNum = BigDecimal.valueOf(-100);
            }
        }catch(Exception e){
            System.out.println("error..."+e.toString());
            returnNum = BigDecimal.valueOf(-1);
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
        return returnNum;


    }

}
