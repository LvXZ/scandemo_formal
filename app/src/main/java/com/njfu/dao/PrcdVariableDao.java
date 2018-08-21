package com.njfu.dao;

import com.njfu.entity.PrcdVariable;

import java.sql.Connection;

/**PrcdVariable数据库接口
 * Created by lvxz on 2018/5/11.
 */

public interface PrcdVariableDao {

    /**
     * 通过getTable查询报完工单节点信息  执行报完工查询
     * @param getTable
     * @return
     */
    PrcdVariable queryEndQueryItem(String getTable);

    /**
     * 执行节点
     * @param getTable
     * @return
     */
    PrcdVariable prcdIssue(String getTable);

    /**
     * 完成结果
     * @param getTable
     * @return
     */
    void completeIn(String getTable);
}
