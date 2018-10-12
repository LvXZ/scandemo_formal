package com.njfu.dao;

import com.njfu.entity.InvInventory;
import com.njfu.entity.InvMoveOrder;
import com.njfu.entity.InvMoveOrderView;
import com.njfu.entity.Item;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

/**物料信息Item数据库接口
 * Created by lvxz on 2018/5/11.
 */

public interface ItemDao {

    /**
     * 通过itemId查询单个物料信息
     * @param itemId
     * @return Item
     */
    Item queryOne(String itemId);

    /**
     * 执行出库操作，并返回处理数据后的Item
     * @param pdnos 对应getTable
     * @return List<Item>
     */
    List<Item> queryOut(String pdnos);

    /**
     * 查询出库数据，返回all已出库、未出库数据Item
     * @param pdnos
     * @return List<Item>
     */
    List<Item> queryOutAll(String pdnos);

    /**
     * 执行入库操作，并返回处理数据后的Item
     * @param pdnos 对应getTable
     * @return List<Item>
     */
    List<Item> queryIn(String pdnos);

    /**
     * 查询入库数据，返回all已入库、未入库数据Item
     * @param pdnos
     * @return List<Item>
     */
    List<Item> queryInAll(String pdnos);

    /**
     * 执行配套出库
     * @param getTable
     * @param getID
     * @param conn
     * @return int
     */
    int executeOut(String getTable,String getID,Connection conn);


    /**
     * 执行配套入库
     * @param getTable
     * @param getID
     * @param conn
     * @return int
     */
    int executeIn(String getTable,String getID,Connection conn);



    /*****************新版配套出库*****************/
    /**
     *
     * @param orderId
     * @return
     */
    InvMoveOrder getInvMoveOrderById(String orderId);


    InvMoveOrderView getInvMoveOrderViewById(String orderId);


    String getTiitm001_KLTCByItemNumber(String itemNumber);


    BigDecimal getAvailable(InvInventory invInventory);
}
