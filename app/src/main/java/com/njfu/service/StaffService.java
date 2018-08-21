package com.njfu.service;

import com.njfu.dto.ServiceInfoDTO;
import com.njfu.entity.Staff;
import com.njfu.entity.Staff_operate;

import java.util.List;

/**员工表Staff服务接口
 * Created by lvxz on 2018/5/13.
 */

public interface StaffService {

    /**
     * 通过员工账号account,查找UserId、Tccom001、Tirou001员工信息表
     * @param account
     * @return ServiceInfoDTO<Staff>
     */
    ServiceInfoDTO<Staff> findUserIdAndTccom001AndTirou001ByAccount(String account);

    /**
     * 确认报完工功能的操作员是否有数据权限,根据prcd确定权限，并分解，获取name
     * @param prcd
     * @return ServiceInfoDTO<Staff_operate>
     */
    ServiceInfoDTO<Staff_operate> confirmStaffOperate(String prcd);

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
