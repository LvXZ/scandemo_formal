package com.njfu.dao;

import com.njfu.entity.Machine001;

/**机器记录对象Machine001数据库接口
 * Created by lvxz on 2018/6/2.
 */

public interface Machine001Dao {

    /**
     * 新增Machine001一条记录
     * @param machine001
     * @return 0失败，1成功
     */
    int insertMachine001One(Machine001 machine001);
}
