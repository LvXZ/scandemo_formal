package com.njfu.service.impl;

import android.util.Log;
import com.njfu.dao.ItemDao;
import com.njfu.dao.impl.ItemDaoImpl;
import com.njfu.dto.ServiceInfoDTO;
import com.njfu.entity.InvInventory;
import com.njfu.entity.InvMoveOrder;
import com.njfu.entity.InvMoveOrderView;
import com.njfu.entity.Item;
import com.njfu.entity.MachineLog;
import com.njfu.service.ItemService;
import com.njfu.utils.Constant;
import com.njfu.utils.JdbcUtil;
import com.njfu.utils.Notice;

import java.math.BigDecimal;
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

        if(itemList == null || itemList.size() == 0  ){
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

    @Override
    public ServiceInfoDTO executeIn(String getTable, String getID) {

        ServiceInfoDTO serviceInfoDTO;
        Connection connection = null;
        try {
            connection = JdbcUtil.getConnection();

            int processReturn = dao.executeIn(getTable, getID, connection);
            switch(processReturn) {
                case 1:{
                    connection.commit();
                    serviceInfoDTO = new ServiceInfoDTO(1,Notice.Display_InputDatabase + Notice.Display_Over,1);
                    Log.i(Constant.TAG, "<-------------All done----------->");
                }break;

                //错误
                case 0:{
                    connection.rollback();
                    serviceInfoDTO = new ServiceInfoDTO(-1,"入库发生错误");
                    Log.i(Constant.TAG, "<-------------Sql error----------->");
                }break;

                default:{
                    connection.rollback();
                    serviceInfoDTO = new ServiceInfoDTO(-1,"入库未知错误");
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


    /****************最新出库*******************/


    public ServiceInfoDTO transcationMoveOrder(String orderId){

        if(!checkString(orderId)){
            return new ServiceInfoDTO(-1,"搬运单号为空，请检查");
        }

        InvMoveOrder istr_mo = dao.getInvMoveOrderById(orderId);
        if(istr_mo.getFlagCode()<0){
            return new ServiceInfoDTO(-1,"查询搬运单时出错");
        }else if(istr_mo.getFlagCode()==100){
            return new ServiceInfoDTO(-1,"查无此搬运单");
        }else{
        }

        InvMoveOrderView istr_mo_view = dao.getInvMoveOrderViewById(orderId);
        if(istr_mo_view.getFlagCode() < 0){
            return new ServiceInfoDTO(-1,"查询搬运单事务出错");
        }else if(istr_mo_view.getFlagCode()==100){
            return new ServiceInfoDTO(-1,"查无此搬运单事务");
        }else{
        }


        if(!istr_mo.getOrderStatus().equals("N")){
            return new ServiceInfoDTO(-1,"检查未通过-搬运单状态不符");
        }
        //String ls_enable_flag = dao.getInvMoveOrderViewEnableFlagById(orderId);
        if(!istr_mo_view.getEnableFlag().equals("Y")){
            return new ServiceInfoDTO(-1,"检查未通过-搬运单事务未生效");
        }
        if(istr_mo.getExecQuantity().intValue() <=0 ){
            return new ServiceInfoDTO(-1,"检查未通过-搬运单数量需要大于0");
        }


        ServiceInfoDTO itemCheckDTO = itemCheck(istr_mo);

        if(itemCheckDTO.getCode() != 0){
            return itemCheckDTO;
        }


        ServiceInfoDTO outDTO = invProcOut(istr_mo_view,istr_mo);
        ServiceInfoDTO inDTO = invProcIn(istr_mo_view);

        return null;

    }

    //判断字符串是否为空
    public boolean checkString(String str){
        if(str!=null && str.length()>0){
            return true;
        }
        return false;
    }



    public ServiceInfoDTO itemCheck(InvMoveOrder istr_mo){

        if(!checkString(istr_mo.getItemNumber())){
            return new ServiceInfoDTO(-1,"检查未通过-搬运单物料编码为空");
        }


        String ls_kltc = dao.getTiitm001_KLTCByItemNumber(istr_mo.getItemNumber());
        if(ls_kltc.equals("-99")){
            return new ServiceInfoDTO(-1,"检索基础物料信息时出错");
        }else if(ls_kltc.equals("-100")){
            return new ServiceInfoDTO(-1,"检索基础物料信息时出错，检查物料是否存在");
        }


        switch(ls_kltc){

            case "3":{
                if(!checkString(istr_mo.getLotNumber()) && !checkString(istr_mo.getSerialNumber())){//两个都为空
                    return new ServiceInfoDTO(-1,"检查未通过-搬运单物料未启用批/序列号，不得填写该二字段");
                }
            }break;
            case "1":{
                if(!checkString(istr_mo.getLotNumber())){//为空
                    return new ServiceInfoDTO(-1,"检查未通过-搬运单物料启用批号控制，必须填写批号");
                }
                if(checkString(istr_mo.getSerialNumber())){//不为空
                    return new ServiceInfoDTO(-1,"检查未通过-搬运单物料启用批号控制，不得填写序列号");
                }

            }break;
            case "2":{
                if(!checkString(istr_mo.getSerialNumber())){//为空
                    return new ServiceInfoDTO(-1,"检查未通过-搬运单物料启用序列号控制，必须填写序列号");
                }
                if(checkString(istr_mo.getLotNumber())){//不为空
                    return new ServiceInfoDTO(-1,"检查未通过-搬运单物料启用序列号控制，不得填写批号");
                }
            }break;

        }
        return null;

    }

    //
    public ServiceInfoDTO invProcOut(InvMoveOrderView istr_mo_view, InvMoveOrder istr_mo){

        if(istr_mo_view.getTransFlow().equals("I")){

        }else{

            InvInventory invInventory = new InvInventory(istr_mo.getItemNumber(), istr_mo.getSourceWhId(), istr_mo.getSourceLocaId(), istr_mo.getItemSpec());

            ServiceInfoDTO<BigDecimal> gfDTO = gfInvAvailableQuery(invInventory);
            if(gfDTO.getCode() != 0){
                return new ServiceInfoDTO(-1,"");
            }

            if(istr_mo.getExecQuantity().subtract(gfDTO.getData()).intValue() >= 0){
                return new ServiceInfoDTO(-1,"库房执行数目不足");
            }

            invInventory.setQuantity(BigDecimal.valueOf(-1).multiply(istr_mo.getExecQuantity()));
            invInventory.setLotNumber(istr_mo.getLotNumber());
            invInventory.setSerialNumber(istr_mo.getSerialNumber());
            invInventory.setCopr(istr_mo.getCopr());
            //istr_mo.getIsFree();istr_mo.getProjectNo();运用时，借用istr_mo的字段

            /*if(invInventoryUpdate() !=0 ){
                return new ServiceInfoDTO(-1,"");
            }*/

            return new ServiceInfoDTO(0,"");
        }
        return null;

    }

    //查询库位库存可用量
    public ServiceInfoDTO<BigDecimal> gfInvAvailableQuery(InvInventory invInventory){

        if(!checkString(invInventory.getItem())){
            return new ServiceInfoDTO(1,"可用量信息不足，物料为空");
        }

        if(!checkString(invInventory.getWhId())){
            return new ServiceInfoDTO(1,"可用量信息不足，库房为空");
        }

        if(!checkString(invInventory.getLocaId())){
            return new ServiceInfoDTO(1,"可用量信息不足，库位为空");
        }

        invInventory.setAvailable(dao.getAvailable(invInventory));
        if( invInventory.getAvailable().compareTo(BigDecimal.valueOf(-1))==0 ){
            return new ServiceInfoDTO(1,"查询已分配表时出现数据库错误");
        }else if(invInventory.getAvailable().compareTo(BigDecimal.valueOf(-100))==0){
            invInventory.setAvailable(BigDecimal.valueOf(0));
        }

        return new ServiceInfoDTO(0,"",invInventory.getAssignQuantity());

    }



    public ServiceInfoDTO invProcIn(InvMoveOrderView istr_mo_view){
        return null;
    }
}
