package com.njfu.service;

import com.njfu.dto.ServiceInfoDTO;
import com.njfu.entity.Machine001;

/**机器记录对象Machine001服务层接口
 * Created by lvxz on 2018/6/2.
 */

public interface Machine001Service {

    /**
     * 新增Machine001一条记录
     * @param machine001
     * @return ServiceInfoDTO<Machine001>提示信息
     */
    ServiceInfoDTO<Machine001> insertMachine001One(Machine001 machine001);
}
