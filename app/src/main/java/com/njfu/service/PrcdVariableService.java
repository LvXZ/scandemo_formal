package com.njfu.service;

import com.njfu.dto.ServiceInfoDTO;
import com.njfu.entity.EndOrder;
import com.njfu.entity.PrcdVariable;

import java.sql.Connection;
import java.util.List;

/**报完工节点PrcdVariable服务层接口
 * Created by lvxz on 2018/5/11.
 */

public interface PrcdVariableService {

    /**
     * 通过getTable查询报完工单节点信息
     * @param getTable
     * @return ServiceInfoDTO<EndOrder>
     */
    ServiceInfoDTO<EndOrder> queryEndQueryItem(String getTable);

    /**
     * 执行报完工
     * @param getTable
     * @param staff_oper_prcd
     * @return
     */
    ServiceInfoDTO<EndOrder> endQueryItem(String getTable, List<String> staff_oper_prcd);
}
