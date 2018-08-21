package com.njfu.service.impl;

import com.njfu.dao.MachineLogDao;
import com.njfu.dao.impl.MachineLogDaoImpl;
import com.njfu.dto.ServiceInfoDTO;
import com.njfu.entity.MachineLog;
import com.njfu.entity.Staff;
import com.njfu.service.MachineLogService;

import java.util.List;

/**MachineLog服务层具体实现
 * Created by lvxz on 2018/6/1.
 */

public class MachineLogServiceImpl implements MachineLogService{

    private MachineLogDao dao = new MachineLogDaoImpl();

    @Override
    public ServiceInfoDTO<MachineLog> queryMachineLogOne(Staff staff_now, String machId, List<String> staff_oper_prcd) {

        ServiceInfoDTO serviceInfoDTO;
        if (staff_now == null) {
            serviceInfoDTO = new ServiceInfoDTO(-3,"操作员未登录");
        }else{

            MachineLog machineLog = dao.queryMachineLogOne(machId);
            if (machineLog == null) {
                serviceInfoDTO = new ServiceInfoDTO(-1,"不存在该机器码！");
            }else{
                //检查该机器码是否属于该组的工序
                if(staff_oper_prcd.contains(machineLog.getPrcd())){
                    //执行成功之处
                    serviceInfoDTO = new ServiceInfoDTO(1,"成功",machineLog);
                }else{
                    serviceInfoDTO = new ServiceInfoDTO(-2,"该组未配置此工序机器！");
                }
            }
        }
        return serviceInfoDTO;
    }

}
