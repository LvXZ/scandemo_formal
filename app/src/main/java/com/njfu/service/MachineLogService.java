package com.njfu.service;

import com.njfu.dto.ServiceInfoDTO;
import com.njfu.entity.MachineLog;
import com.njfu.entity.Staff;

import java.util.List;

/**机器码MachineLog服务层接口
 * Created by lvxz on 2018/6/1.
 */

public interface MachineLogService {

    /**
     * 查找对象信息
     * @param machId 机器码
     * @param staff_oper_prcd 该工序组的工序权限
     * @return ServiceInfoDTO<MachineLog> service层返回类
     */
    ServiceInfoDTO<MachineLog> queryMachineLogOne(Staff staff,String machId, List<String> staff_oper_prcd);
}
