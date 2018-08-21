package com.njfu.dao;

import com.njfu.entity.Staff;

import java.util.List;

/**员工表Staff数据库接口
 * Created by lvxz on 2018/5/13.
 */

public interface StaffDao {

    /**
     * 通过员工账号account,查找UserId、Tccom001、Tirou001员工信息表
     * @param account
     * @return Staff
     */
    Staff findUserIdAndTccom001AndTirou001ByAccount(String account);

    /**
     * 通过prcd查找节点名称
     * @param prcd
     * @return String
     */
    String findTipcs001_praaByPrcd(String prcd);

    /**
     * 查找所有节点
     * @return List<String>
     */
    List<String> findTipcs001_praa();

}
