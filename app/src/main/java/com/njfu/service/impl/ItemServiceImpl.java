package com.njfu.service.impl;

import android.util.Log;
import com.njfu.dao.ItemDao;
import com.njfu.dao.impl.ItemDaoImpl;
import com.njfu.dto.ServiceInfoDTO;
import com.njfu.entity.Item;
import com.njfu.entity.MachineLog;
import com.njfu.service.ItemService;
import com.njfu.utils.Constant;
import com.njfu.utils.JdbcUtil;
import com.njfu.utils.Notice;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**Item服务层接口具体操作实现
 * Created by lvxz on 2018/5/11.
 */

public class ItemServiceImpl implements ItemService {

    private ItemDao dao = new ItemDaoImpl();

    @Override
    public ServiceInfoDTO<Item> queryOne(String itemId) {

        ServiceInfoDTO serviceInfoDTO;
        Item item = dao.queryOne(itemId);
        if(item != null){
            serviceInfoDTO = new ServiceInfoDTO(1,"成功",item);
        }else{
            serviceInfoDTO = new ServiceInfoDTO(-2001,Notice.Get_DB_OneItem_Failure);
        }
        return serviceInfoDTO;
    }

    @Override
    public ServiceInfoDTO<List<Item>> queryOut(String pdnos) {

        ServiceInfoDTO serviceInfoDTO;
        List<Item> itemList = dao.queryOut(pdnos);
        if(itemList.size() == 0){
            serviceInfoDTO = new ServiceInfoDTO(-1,Notice.Get_DB_Items_Failure);
        }else{
            serviceInfoDTO = new ServiceInfoDTO(1,"成功",itemList);
        }
        return serviceInfoDTO;
    }

    @Override
    public ServiceInfoDTO<List<Item>> queryOutAll(String pdnos) {

        ServiceInfoDTO serviceInfoDTO;
        List<Item> itemList = dao.queryOutAll(pdnos);
        if(itemList.size() == 0){
            serviceInfoDTO = new ServiceInfoDTO(-1,Notice.Get_DB_Items_Failure);
        }else{
            serviceInfoDTO = new ServiceInfoDTO(1,"成功",itemList);
        }
        return serviceInfoDTO;
    }

    @Override
    public ServiceInfoDTO<List<Item>> queryIn(String pdnos) {

        ServiceInfoDTO serviceInfoDTO;
        List<Item> itemList = dao.queryIn(pdnos);
        if(itemList.size() == 0){
            serviceInfoDTO = new ServiceInfoDTO(-1,Notice.Get_DB_Items_Failure);
        }else{
            serviceInfoDTO = new ServiceInfoDTO(1,"成功",itemList);
        }
        return serviceInfoDTO;

    }

    @Override
    public ServiceInfoDTO<List<Item>> queryInAll(String pdnos) {

        ServiceInfoDTO serviceInfoDTO;
        List<Item> itemList = dao.queryInAll(pdnos);
        if(itemList.size() == 0){
            serviceInfoDTO = new ServiceInfoDTO(-1,Notice.Get_DB_Items_Failure);
        }else{
            serviceInfoDTO = new ServiceInfoDTO(1,"成功",itemList);
        }
        return serviceInfoDTO;
    }

    @Override
    public ServiceInfoDTO executeOut(String getTable, String getID) {

        ServiceInfoDTO serviceInfoDTO;
        Connection connection = null;
        try {
            connection = JdbcUtil.getConnection();

            int processReturn = dao.executeOut(getTable, getID, connection);
            switch(processReturn) {
                case 1:{
                    connection.commit();
                    serviceInfoDTO = new ServiceInfoDTO(1,Notice.Display_OutputDatabase + Notice.Display_Over,1);
                    Log.i(Constant.TAG, "<-------------All done----------->");
                }break;

                case -1:{
                    connection.rollback();
                    serviceInfoDTO = new ServiceInfoDTO(-1,"出库发生错误");
                    Log.i(Constant.TAG, "<-------------Sql error----------->");
                }break;

                default:{
                    connection.rollback();
                    serviceInfoDTO = new ServiceInfoDTO(-1,"出库未知错误");
                }

            }


        } catch (SQLException e) {
            e.printStackTrace();
            serviceInfoDTO = new ServiceInfoDTO(-1,"数据库连接错误");
        }finally {
            if(connection!=null){
                try{
                    connection.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return serviceInfoDTO;
    }
}
