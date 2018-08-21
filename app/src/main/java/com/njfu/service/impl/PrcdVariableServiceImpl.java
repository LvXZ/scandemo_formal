package com.njfu.service.impl;

import android.util.Log;
import com.njfu.dao.PrcdVariableDao;
import com.njfu.dao.impl.PrcdVariableDaoImpl;
import com.njfu.dto.ServiceInfoDTO;
import com.njfu.entity.EndOrder;
import com.njfu.entity.PrcdVariable;
import com.njfu.service.PrcdVariableService;
import com.njfu.utils.Constant;
import com.njfu.utils.JdbcUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**PrcdVariable服务层接口具体实现
 * Created by lvxz on 2018/5/11.
 */

public class PrcdVariableServiceImpl implements PrcdVariableService {

    private PrcdVariableDao dao = new PrcdVariableDaoImpl();

    @Override
    public ServiceInfoDTO<EndOrder> queryEndQueryItem(String getTable) {

        ServiceInfoDTO serviceInfoDTO;
        PrcdVariable pV = dao.queryEndQueryItem(getTable);

        switch (pV.getErrorNo()){
            case 1:{//成功但是未开始报完工
                if(pV.prcdPos == -1){
                    pV.prcdPos = pV.prcdPos + 1;
                }
                Log.i(Constant.TAG, "<----------1当前位点：--------->"+pV.prcdName[pV.prcdPos]);
                EndOrder endOrder = new EndOrder(pV.orderNo, pV.itemName, pV.itemNo, String.valueOf(pV.itemNum),pV.itemUnit, pV.prcdName,pV.prcdPos, pV.errorText);
                serviceInfoDTO = new ServiceInfoDTO(1,endOrder.getState_o(),endOrder);
            }break;

            case 0:{//成功
                Log.i(Constant.TAG, "<----------0当前位点：--------->"+pV.prcdName[pV.prcdPos]);
                EndOrder endOrder = new EndOrder(pV.orderNo, pV.itemName, pV.itemNo, String.valueOf(pV.itemNum),pV.itemUnit, pV.prcdName,pV.prcdPos, "已完成");
                serviceInfoDTO = new ServiceInfoDTO(0,"查询报完工成功",endOrder);
            }break;

            case -1:{//失败
                EndOrder endOrder = new EndOrder(pV.orderNo, pV.itemName, pV.itemNo, String.valueOf(pV.itemNum),pV.itemUnit, pV.prcdName, pV.prcdName.length-1, pV.errorText);
                serviceInfoDTO = new ServiceInfoDTO(-1,endOrder.getState_o(),endOrder);
            }break;

            case -2:{//失败//无符合条件订单
                //数据库为空，或者不存在数据，返回提示
                serviceInfoDTO = new ServiceInfoDTO(-2,"无符合条件订单");
            }break;

            default:{
                serviceInfoDTO = new ServiceInfoDTO(-3,"发生未知错误，等待恢复");
            }

        }
        return serviceInfoDTO;
    }

    @Override
    public ServiceInfoDTO<EndOrder> endQueryItem(String getTable,List<String> staff_oper_prcd) {

        ServiceInfoDTO serviceInfoDTO;

        PrcdVariable pVquery = dao.queryEndQueryItem(getTable);
        if(pVquery.getErrorNo() >= 0){//成功  包括未开始报完工


            List<String> list= staff_oper_prcd;
            if( list.contains( pVquery.prcdAll[pVquery.prcdPos+1] ) ){//是否包含有该数据操作
                Log.i(Constant.TAG, "<----------当前报完工执行位点------->"+pVquery.prcdAll[pVquery.prcdPos+1]+" "+pVquery.prcdName[pVquery.prcdPos+1]);

                PrcdVariable pV = dao.prcdIssue(getTable);//可以执行
                System.out.print(pV.toString());
                EndOrder endOrder = new EndOrder(pV.orderNo, pV.itemName, pV.itemNo, String.valueOf(pV.itemNum),pV.itemUnit, pV.prcdName,pV.prcdPos, "已完成");

                if(pV.prcdName[pV.prcdPos+1]==null ){//报完工最后一道工序之后/*20180312*/
                    Log.i(Constant.TAG, "<-------------endQueryItem CompleteIn execute----------->");
                    dao.completeIn(getTable);//打包最后一道工序函数
                }
                serviceInfoDTO = new ServiceInfoDTO(0,"报完工成功",endOrder);


            }else{
                EndOrder endOrder = new EndOrder(null, pVquery.prcdName[pVquery.prcdPos+1], null,null, null, null,pVquery.prcdPos+2, "未拥有该节点权限");
                serviceInfoDTO = new ServiceInfoDTO(-11,endOrder.getState_o(),endOrder);
            }

        }else if(pVquery.getErrorNo() == -1){//失败
            EndOrder endOrder = new EndOrder(pVquery.orderNo, pVquery.itemName, pVquery.itemNo, String.valueOf(pVquery.itemNum),pVquery.itemUnit, pVquery.prcdName, pVquery.prcdName.length-1, pVquery.errorText);
            serviceInfoDTO = new ServiceInfoDTO(-1,endOrder.getState_o(),endOrder);
        }else if(pVquery.getErrorNo() == -2){
            //数据库为空，或者不存在数据，返回提示
            serviceInfoDTO = new ServiceInfoDTO(-2,"无符合条件订单");
        }else{
            serviceInfoDTO = new ServiceInfoDTO(-3,"发生未知错误，等待恢复");
        }

        return serviceInfoDTO;
    }
}
