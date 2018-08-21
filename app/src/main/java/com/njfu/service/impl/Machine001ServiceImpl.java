package com.njfu.service.impl;

import com.njfu.dao.Machine001Dao;
import com.njfu.dao.impl.Machine001DaoImpl;
import com.njfu.dto.ServiceInfoDTO;
import com.njfu.entity.Machine001;
import com.njfu.service.Machine001Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**机器记录对象Machine001Service具体实现
 * Created by lvxz on 2018/6/2.
 */

public class Machine001ServiceImpl implements Machine001Service{

    Machine001Dao dao = new Machine001DaoImpl();

    @Override
    public ServiceInfoDTO<Machine001> insertMachine001One(Machine001 machine001) {

        Date nowDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(nowDate);
        machine001.setAdd_date(strDate);

        ServiceInfoDTO serviceInfoDTO = null;

        int flag = dao.insertMachine001One(machine001);
        if(flag == 1){
            serviceInfoDTO = new ServiceInfoDTO(1,"成功",null);
        }else{
            serviceInfoDTO = new ServiceInfoDTO(-1,"失败");
        }
        return serviceInfoDTO;
    }
}
