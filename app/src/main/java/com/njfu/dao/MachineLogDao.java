package com.njfu.dao;

import com.njfu.entity.MachineLog;

/** 机器工序码MachineLog数据库接口
 * Created by lvxz on 2018/6/1.
 */

public interface MachineLogDao {

    /**
     * 查找对象信息
     * @param machId
     * @return MachineLog
     */
    MachineLog queryMachineLogOne(String machId);

}
