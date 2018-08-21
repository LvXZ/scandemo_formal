package com.njfu.service;

import com.njfu.dto.ServiceInfoDTO;
import com.njfu.entity.Item;

import java.sql.Connection;
import java.util.List;

/**物料Item服务层接口
 * Created by lvxz on 2018/5/11.
 */

public interface ItemService {

    /**
     * 通过itemId查询单个物料信息
     * @param itemId
     * @return ServiceInfoDTO<Item>
     */
    ServiceInfoDTO<Item> queryOne(String itemId);

    /**
     * 执行出库操作，并返回处理数据后的Item
     * @param pdnos 对应getTable
     * @return ServiceInfoDTO<List<Item>>
     */
    ServiceInfoDTO<List<Item>> queryOut(String pdnos);

    /**
     * 查询出库数据，返回all已出库、未出库数据Item
     * @param pdnos
     * @return List<Item>
     */
    ServiceInfoDTO<List<Item>> queryOutAll(String pdnos);

    /**
     * 执行入库操作，并返回处理数据后的Item
     * @param pdnos 对应getTable
     * @return List<Item>
     */
    ServiceInfoDTO<List<Item>> queryIn(String pdnos);

    /**
     * 查询入库数据，返回all已入库、未入库数据Item
     * @param pdnos
     * @return List<Item>
     */
    ServiceInfoDTO<List<Item>> queryInAll(String pdnos);

    /**
     * 执行配套出库
     * @param getTable
     * @param getID
     * @return ServiceInfoDTO
     */
    ServiceInfoDTO executeOut(String getTable,String getID);

    /**
     * 执行配套入库
     * @param getTable
     * @param getID
     * @return ServiceInfoDTO
     */
    ServiceInfoDTO executeIn(String getTable,String getID);
}
