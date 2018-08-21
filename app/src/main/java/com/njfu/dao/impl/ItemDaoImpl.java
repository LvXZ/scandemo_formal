package com.njfu.dao.impl;

import com.njfu.dao.ItemDao;
import com.njfu.entity.Item;
import com.njfu.utils.JdbcUtil;
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

}
