package com.njfu.helper;

import android.app.Activity;
import android.widget.PopupMenu;

import com.njfu.scandemo.R;

/**
 * Created by lvxz on 2018/1/16.
 */

public class MenuHelper extends Activity {

    /**
     * setVisible_Select() 对主菜单进行激活操作选择
     * setVisible_Select_Left() 对左子菜单进行显示操作选择
     * setVisible_Select_Right() 对右子菜单进行显示操作选择
     */
    public static void setVisible_Select_Left(PopupMenu popupMenu, String right, String prcd){//左菜单的选择性隐藏

        popupMenu.getMenu().findItem(R.id.query_one_item).setVisible(true);//菜单栏-物料码可显示

        if( prcd != null && !"".equals(prcd) ){
            popupMenu.getMenu().findItem(R.id.query_finish_list).setVisible(true);//报完工激活
        }else{
            popupMenu.getMenu().findItem(R.id.query_finish_list).setVisible(false);;//报完工不激活
        }

        switch(right){
            case "1,0":{
                popupMenu.getMenu().findItem(R.id.query_out_list).setVisible(true);//菜单栏-出库单可显示
            }break;

            case "0,1":{
                popupMenu.getMenu().findItem(R.id.query_in_list).setVisible(true);//菜单栏-入库单可显示
            }break;

            case "#":{}
            case "1,1":{
                popupMenu.getMenu().findItem(R.id.query_out_list).setVisible(true);
                popupMenu.getMenu().findItem(R.id.query_in_list).setVisible(true);
            }break;

            case "0,0":{
                popupMenu.getMenu().findItem(R.id.query_out_list).setVisible(false);
                popupMenu.getMenu().findItem(R.id.query_in_list).setVisible(false);
            }break;
        }


    }

    public static void setVisible_Select_Right( PopupMenu popupMenu, String state){//右菜单的选择性隐藏
        switch(state){

            case "1,0":{
                popupMenu.getMenu().findItem(R.id.output_item).setVisible(true);//菜单栏-出库单可显示
            }break;

            case "0,1":{
                popupMenu.getMenu().findItem(R.id.input_item).setVisible(true);//菜单栏-入库单可显示
            }break;

            case "#":{
                popupMenu.getMenu().findItem(R.id.IP_item).setVisible(true);//菜单栏-IP单可显示
            }

            case "1,1":{
                popupMenu.getMenu().findItem(R.id.output_item).setVisible(true);
                popupMenu.getMenu().findItem(R.id.input_item).setVisible(true);
            }break;

            case "0,0":{
                popupMenu.getMenu().findItem(R.id.output_item).setVisible(false);
                popupMenu.getMenu().findItem(R.id.input_item).setVisible(false);
            }break;
        }
    }
}
