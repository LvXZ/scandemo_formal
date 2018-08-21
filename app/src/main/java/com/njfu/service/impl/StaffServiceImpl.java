package com.njfu.service.impl;

import com.njfu.dao.StaffDao;
import com.njfu.dao.impl.StaffDaoImpl;
import com.njfu.dto.ServiceInfoDTO;
import com.njfu.entity.MachineLog;
import com.njfu.entity.Staff;
import com.njfu.entity.Staff_operate;
import com.njfu.service.StaffService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**员工表Staff服务接口具体实现
 * Created by lvxz on 2018/5/13.
 */

public class StaffServiceImpl implements StaffService {

    private StaffDao dao = new StaffDaoImpl();

    @Override
    public ServiceInfoDTO<Staff> findUserIdAndTccom001AndTirou001ByAccount(String account) {

        ServiceInfoDTO serviceInfoDTO;
        Staff staff = dao.findUserIdAndTccom001AndTirou001ByAccount(account);
        if(staff == null){
            serviceInfoDTO = new ServiceInfoDTO(-1,"操作员不存在");
        }else{
            serviceInfoDTO = new ServiceInfoDTO(1,"成功",staff);
        }
        return serviceInfoDTO;
    }

    @Override
    public ServiceInfoDTO<Staff_operate> confirmStaffOperate(String prcd) {

        ServiceInfoDTO serviceInfoDTO;
        Staff_operate staffOperate;

        if(!prcd.equals("") && prcd!=null){

            if(prcd.equals("#")){
                staffOperate = new Staff_operate("权限全开",dao.findTipcs001_praa());
            }else if(prcd.contains(",")){

                staffOperate = new Staff_operate(Arrays.asList(prcd.split(",")));
                for(String str : staffOperate.getPrcd()){
                    if(staffOperate.getName() == null){
                        staffOperate.setName(dao.findTipcs001_praaByPrcd(str.trim()));
                    }else{
                        staffOperate.setName( staffOperate.getName() + dao.findTipcs001_praaByPrcd(str.trim()));
                    }
                    staffOperate.setName( staffOperate.getName() + "  ");
                }

            }else{
                List<String> list = new ArrayList<String>();
                list.add(prcd);
                staffOperate = new Staff_operate(dao.findTipcs001_praaByPrcd(prcd.trim()),list);
                //staff_oper_prcd.add(prcd);
                //staff_oper_name = staffService.findTipcs001_praaByPrcd(prcd.trim());
            }
            serviceInfoDTO = new ServiceInfoDTO(1,"成功",staffOperate);

        }else{
            serviceInfoDTO = new ServiceInfoDTO(-1,"该操作员无权限");
        }

        return serviceInfoDTO;

    }

    @Override
    public String findTipcs001_praaByPrcd(String prcd) {
        return dao.findTipcs001_praaByPrcd(prcd);
    }

    @Override
    public List<String> findTipcs001_praa() {
        return dao.findTipcs001_praa();
    }
}
