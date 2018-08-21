package com.njfu.scandemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.njfu.dao.MachineLogDao;
import com.njfu.dto.ServiceInfoDTO;
import com.njfu.entity.EndOrder;
import com.njfu.entity.Item;
import com.njfu.entity.Machine001;
import com.njfu.entity.MachineLog;
import com.njfu.entity.Staff;
import com.njfu.entity.Staff_operate;
import com.njfu.helper.MenuHelper;
import com.njfu.helper.NetworkBroadcast;
import com.njfu.service.ItemService;
import com.njfu.service.Machine001Service;
import com.njfu.service.MachineLogService;
import com.njfu.service.PrcdVariableService;
import com.njfu.service.StaffService;
import com.njfu.service.impl.ItemServiceImpl;
import com.njfu.service.impl.Machine001ServiceImpl;
import com.njfu.service.impl.MachineLogServiceImpl;
import com.njfu.service.impl.PrcdVariableServiceImpl;
import com.njfu.service.impl.StaffServiceImpl;
import com.njfu.utils.Constant;
import com.njfu.utils.Notice;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.widget.PopupMenu;
import android.view.MenuInflater;
import android.widget.PopupMenu.OnMenuItemClickListener;

/**
 * 工程说明：
 * 		1,该开发包是基于iScan开发，需要预装iScan4.2.0以上版本。
 *      2,基本设置功能如下：左、右、中间扫描黄键扫码
 */

public class MainActivity extends Activity {

    public String Change_IP = "139.196.88.74";
    public String DB_URL = "jdbc:oracle:thin:@"+Change_IP+":1521:orcl";

    int i;
    public TextView tvScanResult;
    public TextView tvLoginResult;
    public TextView tvMethodResult;
    public ScannerInterface scanner;
    public IntentFilter intentFilter;
    public BroadcastReceiver scanReceiver;

    /**
     * item     获取货物对象
     * userId   获取用户对象
     * Staff    获取扫描对象
     * getTable 获取单子对象
     * endOrder 获取报完工对象--显示工序节点
     */
    //public Item item;
    public Staff staff;
    public Staff_operate staffOperate;
    public MachineLog machineLog;

    /**getID 标志识别认证
     * ^15080[0-9]{4}$  管理员识别
     * ^15070[0-9]{4}$  操作员识别
     */
    public String getID;
    private String getTable;

    /**默认查询单个物料
     * flagScan
     * 1001         查询 单个物料
     * 1002         查询 出库单
     * 1003         查询 入库单
     * 1005         查询 报完工单
     * 2001         出库
     * 3001         入库
     *
     * 5001         报完工
     */
    private int flagScan = 1;

    /**
     * 010100 出库管理员
     * 001100 入库
     * 100100 报完工
     * 011100 出库、入库管理员
     * 110100 出库、报完工
     * 101100 入库、报完工
     * 111100 小组长--出库、入库、报完工管理员
     * 888888 测试员
     * 999999 OS软件管理员
     * 100100
     * 000000 操作员
     */

    private String signState;
    private String signPrcd;

    /**
     * -1       操作员未登录
     * 0
     * 1        全部已登录--可以使用扫描器
     * 2        准备修改登录栏目--添加管理员
     * 22       准备修改登录栏目--添加机器码
     * 3        执行 管理员操作员登录操作
     * 4        执行 机器码登录操作
     */
    private int loginScan = -1;


    /**是否清空上次输出后再输出
     * 0    清空上次输出
     * 1    紧接上次继续输出
     * moreShow     tvScanResult显示处理
     * methodShow   tvMethodResult显示处理
     */
    private int moreShow = 0;
    private int methodShow = 0;

    /**是否清空所有显示结果
     * 0 不清空
     * 1 清空
     */
    private int flag=0;
    private int special_flag=0;

    /**功能BUG错误显示
     * -1001    订单BUG
     * -2001    物料BUG
     * -5001    报完工BUG
     */
    private int error_flag = 0;

    /**
     * 登陆显示字符串
     * 功能显示字符串
     * 提示显示字符串
     */
    private String login_text;
    private String method_text;
    private String toast_string;

    private static final String RES_ACTION = "android.intent.action.SCANRESULT";


    /**
     * 检查网络
     */
    private IntentFilter networkIntentFilter;

    private NetworkBroadcast networkBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScanResult = (TextView)findViewById(R.id.tv_scan_result);
        tvLoginResult = (TextView)findViewById(R.id.login_result);
        tvMethodResult = (TextView)findViewById(R.id.method_result);


        //网络连接
        networkIntentFilter = new IntentFilter();
        networkIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkBroadcastReceiver = new NetworkBroadcast(MainActivity.this);
        registerReceiver(networkBroadcastReceiver,networkIntentFilter);


        initScanner();
    }

    /**扫码器硬件初始化**/
    private void initScanner(){
        i=0;
        scanner = new ScannerInterface(this);
        scanner.open();	//打开扫描头上电   scanner.close();//打开扫描头下电
        scanner.enablePlayBeep(true);//是否允许蜂鸣反馈
        scanner.enableFailurePlayBeep(false);//扫描失败蜂鸣反馈
        scanner.enablePlayVibrate(false);//是否允许震动反馈
        scanner.enableAddKeyValue(1);/**附加无、回车、Teble、换行*/
        scanner.timeOutSet(10);//设置扫描延时10秒
        scanner.intervalSet(1000); //设置连续扫描间隔时间
        scanner.lightSet(false);//关闭右上角扫描指示灯
        scanner.enablePower(true);//省电模式
        scanner.setMaxMultireadCount(2);//设置一次最多解码2个
        //		scanner.addPrefix("AAA");//添加前缀
        //		scanner.addSuffix("BBB");//添加后缀
        //		scanner.interceptTrimleft(2); //截取条码左边字符
        //		scanner.interceptTrimright(3);//截取条码右边字符
        //		scanner.filterCharacter("R");//过滤特定字符
        scanner.SetErrorBroadCast(true);//扫描错误换行
        //scanner.resultScan();//恢复iScan默认设置

        //		scanner.lockScanKey();
        //锁定设备的扫描按键,通过iScan定义扫描键扫描，用户也可以自定义按键。
        scanner.unlockScanKey();
        //释放扫描按键的锁定，释放后iScan无法控制扫描按键，用户可自定义按键扫描。

        /**设置扫描结果的输出模式，参数为0和1：
         * 0为模拟输出（在光标停留的地方输出扫描结果）；
         * 1为广播输出（由应用程序编写广播接收者来获得扫描结果，并在指定的控件上显示扫描结果）
         * 这里采用接收扫描结果广播并在TextView中显示*/
        scanner.setOutputMode(1);

        /**黄键139中间黄键,140右间黄键,141左间黄键按下来触发扫描*/
        //激活所有黄键扫描 0表示扫描，1不做任何操作
        scanner.scanKeySet(139,0);    //中间黄键
        scanner.scanKeySet(140,0);    //右间黄键
        scanner.scanKeySet(141,0);    //左间黄键

        //扫描结果的意图过滤器的动作一定要使用"android.intent.action.SCANRESULT"
        intentFilter = new IntentFilter(RES_ACTION);
        //注册广播接受者
        scanReceiver = new ScannerResultReceiver();
        registerReceiver(scanReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finishScanner();
        scanner.lockScanKey();//锁定iScan扫描按键
    }

    /** 结束扫描**/
    private void finishScanner(){
        scanner.scan_stop();
//		scanner.close();	//关闭iscan  非正常关闭会造成iScan异常退出
        unregisterReceiver(scanReceiver);	//反注册广播接收者
        scanner.continceScan(false);
    }

    /**指定只能按键键值为139的物理按键（中间黄色按键）,140右间黄键,141左间黄键按下来触发扫描**/
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //避免连续扫码，扫完即停
        if (keyCode == 139&&event.getRepeatCount()==0){
            scanner.scan_start();
        }
        if (keyCode == 140&&event.getRepeatCount()==0){
            scanner.scan_start();
        }
        if (keyCode == 141 &&event.getRepeatCount()==0){
            scanner.scan_start();
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == 139 || keyCode == 140 || keyCode == 141 ){	/**按键弹起，停止扫描*/
            scanner.scan_stop();
        }
        return super.onKeyUp(keyCode, event);
    }


    /**
     * 使用一个Handler来启动一个线程，当该线程不再使用就删除，保证线程不会重复创建
     * Handler是与UI线程下的默认Looper绑定的,Looper是用于实现消息队列和消息循环机制的
     * 将运行数据显示到textView界面
     */
    public Handler myHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);


            if(flag==1){
                tvScanResult.setText("");
                flag = 0;
            }
            if(error_flag == 0){
                if(methodShow == 1){
                    tvMethodResult.setText(method_text+"单号: "+getTable);
                    methodShow = 0;
                }
                if(methodShow == 2){
                    tvMethodResult.setText(method_text);
                    methodShow = 0;
                }
            }else{
                switch(error_flag){//出现错误，功能栏不变，显示框清空，并显示错误提示
                    case -1001://订单
                        tvMethodResult.setText(method_text);
                        methodShow = 0;
                        Toast.makeText(MainActivity.this, toast_string, Toast.LENGTH_SHORT).show();
                        break;

                    case -2001://物料
                        tvMethodResult.setText(method_text);
                        methodShow = 0;
                        Toast.makeText(MainActivity.this, toast_string, Toast.LENGTH_SHORT).show();
                        break;

                    case -5001://报完工
                        tvMethodResult.setText(method_text);
                        methodShow = 0;
                        Toast.makeText(MainActivity.this, toast_string, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            switch (loginScan){
                case -1:{
                    Toast.makeText(MainActivity.this, Notice.Login_Request_1, Toast.LENGTH_SHORT).show();
                }break;

                case 1: {//全部登陆成功，执行扫描器
                    //System.out.println("---------获取数据---------" + msg.obj);
                    //tvScanResult.setText(item.toString());

                    switch(moreShow){
                        case 0:{
                            tvScanResult.setText("" + msg.obj);
                        }break;

                        case 5:{
                            //String source = "这只是一个测试，<font color='red'><u><strong>红色字</strong></u></font>的格式";
                            tvScanResult.setText(Html.fromHtml("" + msg.obj));
                        }break;

                        case 1:{
                            tvScanResult.append("" + msg.obj);
                        }break;
                    }

                } break;

                //tvLoginResult.setText("普瑞迪云采集    ["+staff.getAccount()+"]  "+staff.getName_user());
                //tvLoginResult.append("     ["+staff.getName_user()+"]");
                case 2: {
                    System.out.println("--------管理员账号--------" + staff.toString());

                    login_text = " 普云智造  ["+ staff.getDsca() +":"+staff.getLoginid()+"]";
                    signState = staff.getRight();
                    signPrcd = staff.getPrcd();
                    tvLoginResult.setText(login_text);
                    tvMethodResult.setText(R.string.method);
                    //System.out.println("------------------"+staff.toString()+"------------------");
                    //loginScan = 0;
                    setVisible_Select(staff.getRight(),staff.getPrcd());

                    loginScan = 1;//操作员登陆成功--可以执行扫描器
                    //功能激活
                    tvMethodResult.setText(Notice.Display_Default);
                    method_text = " "+Notice.Display_QueryOneItem;

                    flagScan = 1001;
                    Toast.makeText(MainActivity.this, Notice.Login_Success, Toast.LENGTH_SHORT).show();//提示登陆成功

                } break;

                case 22: {
                    System.out.println("--------机器--------" + machineLog.toString());
                    tvLoginResult.setText(login_text + " [工序:"+machineLog.getMachName()+"]");
                    loginScan = 1;//操作员登陆成功--可以执行扫描器

                    Toast.makeText(MainActivity.this, "机器码已输入", Toast.LENGTH_SHORT).show();//提示登陆成功
                } break;

                default:
                    tvMethodResult.setText(R.string.method);
                    break;
            }

            //获取的数据为空
            if(special_flag == 1){
                Log.i(Constant.TAG,"-------------special_flag--------------");
                tvScanResult.setText("");
                special_flag = 0;
            }

        }

    };

    /**setVisible_Select() 对主菜单进行激活操作选择**/
    private void setVisible_Select(String right,String prcd) {//按钮的选择性隐藏

        Button bt_SingleButton = (Button)findViewById(R.id.SingleButton);
        Button bt_Finished = (Button)findViewById(R.id.Finished);
        Button bt_Functions = (Button)findViewById(R.id.Functions);
        Button bt[] = {bt_Finished, bt_Functions ,bt_SingleButton};

        bt_SingleButton.setEnabled(true);//单个物料查询按钮激活

        switch(right){
            case "1,0":{//出库激活
                bt_Functions.setEnabled(true);
                bt_Functions.setText("出库");
            }break;

            case "0,1":{//入库激活
                bt_Functions.setEnabled(true);
                bt_Functions.setText("入库");
            }break;

            case "0,0":{//出入库--不激活
                bt_Functions.setText("出/入库");
                bt_Functions.setEnabled(false);
            }break;

            case "1,1":{//出入库--激活
                bt_Functions.setEnabled(true);
                bt_Functions.setText("出/入库");
            }break;

            case "#":{//admin//全部激活
                bt_Functions.setEnabled(true);
                bt_Functions.setText("功能选择");
            }break;

            default:{//全部不激活
                bt_SingleButton.setEnabled(false);
                bt_Functions.setText("出/入库");
                bt_Functions.setEnabled(false);
            }break;
        }

        if( prcd != null && !"".equals(prcd) ){
            bt_Finished.setEnabled(true);//报完工激

        }else{
            bt_Finished.setEnabled(false);//报完工不激活
        }
    }


    /**扫描结果的广播接收者**/
    private class ScannerResultReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(RES_ACTION)){
                //获取扫描结果
                final String scanResult = intent.getStringExtra("value");
                //int barocodelen=intent.getIntExtra("length",0);
                //int type =intent.getIntExtra("type", 0);
                //String myType =  String.format("%c", type);
                //tvScanResult.append("第"+i+"个CodeBar："+scanResult);
                //tvScanResult.append("Length："+barocodelen+"  第"+i+"个CodeBar："+scanResult);
                //i++;
                //执行进程
                if(scanResult!=null){

                    String pattern1 = "^150[0-9]04[1|2][0-9]{2}$";//管理员-操作员识别

                    if(scanResult.matches(pattern1)){//线程执行登录操作
                        Log.i(Constant.TAG, "<----------scanResult.matches1--------->");
                        loginScan = 3;
                    }

                    if(loginScan == -1){
                        Toast.makeText(MainActivity.this, Notice.Login_Request_1, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String pattern0 = "^MH-[A-Z]{2}-0[0-9]{2}$";//机器码识别
                    if(scanResult.matches(pattern0)){//线程执行登录操作
                        Log.i(Constant.TAG, "<----------scanResult.matches0--------->");
                        loginScan = 4;
                    }

                    //识别二维码后显示
                    getID = scanResult;
                    new Thread(runnable).start();

                }
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**按钮点击响应下拉菜单1**/
    public void functions1(View view){showPopMenu1(view);}

    /**下拉菜单1选择键点击**/
    public void showPopMenu1(View view){

        PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
        popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // TODO Auto-generated method stub
                switch (item.getItemId()) {
                    case R.id.query_one_item:
                        queryOneItem(view);//单个物料查询
                        break;

                    case R.id.query_out_list:
                        queryOutList(view);//出库单查询
                        break;

                    case R.id.query_in_list:
                        queryInList(view);//入库单查询
                        break;

                    case R.id.query_finish_list:
                        queryFinishList(view);//报完工单查询
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_query, popupMenu.getMenu());
        MenuHelper.setVisible_Select_Left(popupMenu,signState,signPrcd);//左菜单选择性显示函数
        popupMenu.show();

    }

    /**单个物料-查询按钮的响应**/
    public void queryOneItem(View view){confirm_Functions( 1001, Notice.Display_QueryOneItem , Notice.Display_QueryOneItem + Notice.Activation);}

    /**出库单-查询按钮的响应**/
    public void queryOutList(View view){confirm_Functions( 1002, Notice.Display_QueryOutList , Notice.Display_QueryOutList + Notice.Activation);}

    /**入库单-查询按钮的响应**/
    public void queryInList(View view){confirm_Functions( 1003, Notice.Display_QueryInList , Notice.Display_QueryInList + Notice.Activation);}

    /**报完工单-查询按钮的响应**/
    public void queryFinishList(View view){confirm_Functions( 1005, Notice.Display_QueryFinishList , Notice.Display_QueryFinishList + Notice.Activation);}

    /**按钮点击响应下拉菜单2**/
    public void functions2(View view){
        Button bt_Functions = (Button)findViewById(R.id.Functions);
        String str = bt_Functions.getText().toString();
        switch ( str ){
            case "出库":{outputDatabase(view);}break;
            case "入库":{inputDatabase(view);}break;
            case "出/入库":{showPopMenu2(view);}break;
            case "功能选择":{showPopMenu2(view);}break;
        }
    }

    /**下拉菜单2选择键点击**/
    public void showPopMenu2(View view){

        PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
        popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // TODO Auto-generated method stub
                        switch (item.getItemId()) {
                            case R.id.output_item:
                                outputDatabase(view);
                                break;

                            case R.id.input_item:
                                inputDatabase(view);
                                break;

                            case R.id.IP_item:
                                IP_address(view);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, popupMenu.getMenu());
        MenuHelper.setVisible_Select_Right(popupMenu,signState);
        popupMenu.show();

    }

    /**出库选择键响应**/
    public void outputDatabase(View view){confirm_Functions( 2001, Notice.Display_OutputDatabase , Notice.Display_OutputDatabase + Notice.Preparation);}

    /**入库选择键响应**/
    public void inputDatabase(View view) {confirm_Functions( 3001, Notice.Display_InputDatabase , Notice.Display_InputDatabase + Notice.Preparation);}

    /**改变IP选择键响应**/
    public void IP_address(View view){
        AlertDialog.Builder builder = new  AlertDialog.Builder(this);
        builder.setTitle("IP地址"+Change_IP+" 需要改变吗？");
        builder.setIcon(android.R.drawable.ic_dialog_info);
        EditText editText = new EditText(MainActivity.this);
        builder.setView(editText);
        builder.setPositiveButton("确定" ,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Change_IP = editText.getText().toString();
                        DB_URL = "jdbc:oracle:thin:@"+Change_IP+":1521:orcl?characterEncoding=utf8&useSSL=true";
                    }
                })
                .setNegativeButton("取消" , null)
                .show();
    }

    /**报完工按钮**/
    public void finished(View view){

        switch (loginScan){
            case -1:
                Toast.makeText(MainActivity.this, Notice.Login_Request_1, Toast.LENGTH_SHORT).show();
                break;

            case 1:
                if(staffOperate.getPrcd().size() != 0){
                    flagScan = 5001;
                    showResult( Notice.Display_Finished );
                    Toast.makeText(MainActivity.this, staffOperate.getName() +"  "+ Notice.Display_Finished + Notice.Preparation , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "无报完工数据权限！" , Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }
    }

    /**
     * 确认功能按钮
     * @param Aa_number_flagScan 对应功能按钮的扫描确认标示数
     * @param Bb_string_function 对应功能按钮的扫描功能栏文字信息的改变
     * @param Cc_string_toast    对应功能按钮的扫描确认后的提示
     */
    public void confirm_Functions(int Aa_number_flagScan, String Bb_string_function, String Cc_string_toast){
        switch (loginScan){
            case -1:
                Toast.makeText(MainActivity.this, Notice.Login_Request_1, Toast.LENGTH_SHORT).show();
                break;

            case 1:
                flagScan = Aa_number_flagScan;
                showResult( Bb_string_function );
                Toast.makeText(MainActivity.this, Cc_string_toast , Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**对操作栏、显示模块进行刷新**/
    public void showResult(String string){
        string = " "+string+"    ";
        tvMethodResult.setText(string);
        method_text = string;
        tvScanResult.setText("");
    }

    /**
     * Android4.0之后，不允许在主线程中进行比较耗时的操作（连接数据库就属于比较耗时的操作），
     * 需要开一个新的线程来处理这种耗时的操作，我之前用HttpURLConnection的方法获取网站数据，
     * 也是这个原因，没新线程时，一直就是程序直接退出，开了一个新线程处理直接，就没问题了
     *
     * flagScan
     * 1001         查询 单个物料
     * 1002         查询 出库单
     * 1003         查询 入库单
     * 1004         查询 报完工单
     * 2001         出库
     * 3001         入库
     *
     * 5001         报完工
     */
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            /*item = null;
            Message msg = myHandler.obtainMessage();
            msg.obj = item;*/

            error_flag = 0;             //默认无错误
            moreShow = 0;               //清空上次输出，再输出本次结果

            if(loginScan == 3){

                staff = new Staff();
                getStaff(getID);//管理员操作员数据库获取信息

            }else if(loginScan == 4){

                machineLog = new MachineLog();
                getMachine(staff,getID,staffOperate.getPrcd()); //机器码获取登录信息

            } else if(getID != null && !"".equals(getID) && loginScan ==1){         //具体功能实现

                switch(flagScan){
                    case 1001: {
                        flag = 1;//每次查询清空所有显示
                        methodShow = 2;//物料查询，不需要输出单号
                        method_text = " "+Notice.Display_QueryOneItem;

                        getOneQueryItem(getID);
                    }break;

                    case 1002: {//查询出库单
                        flag=1;//每次查询清空所有显示
                        moreShow = 1;
                        //getTable = getID.substring(0,getID.length()-2);
                        getTable = getID;
                        method_text = " 查询    " + Notice.Display_OutputDatabase;
                        methodShow = 1;
                        getQueryItem("all_out_query",getTable);

                    }break;

                    case 1003: {//查询入库单
                        flag=1;//每次查询清空所有显示
                        moreShow = 1;
                        getTable = getID;
                        method_text = " 查询    " + Notice.Display_InputDatabase;
                        methodShow = 1;

                        getQueryItem("all_in_query",getTable);
                    }break;

                    case 1005: {//查询报完工单
                        flag = 1;
                        moreShow = 5;
                        getTable = getID;
                        method_text = " 查询    " + Notice.Display_Finished;
                        methodShow = 1;
                        queryEndQueryItem(getTable);
                    }break;

                    case 2001: {//出库单
                        flag = 1;
                        moreShow = 1;
                        getTable = getID;
                        methodShow = 1;

                        getQueryItem("special_out_query",getTable);
                        if(error_flag == 0){//无错误
                            flagScan = 2002; //开始单个出库
                            notice_toast(Notice.Display_OutputDatabase + Notice.Activation);//出库已激活
                        }
                    }break;

                    case 2002: {//单物品出库
                        outOne_getQueryItem(getTable,getID);
                    }break;

                    case 3001: {//入库单
                        /*flag = 1;
                        moreShow = 1;
                        getTable = getID;
                        methodShow = 1;*/
                        inOne_getQueryItem(getTable,getID);
                        //getQueryItem("special_in_query",getTable);
                        /*if(error_flag == 0){//无错误
                            flagScan = 3002; //开始单个入库
                            notice_toast(Notice.Display_InputDatabase + Notice.Activation);//入库已激活
                        }*/
                    }break;

                    case 3002: {//单物品入库
                        inOne_getQueryItem(getTable,getID);
                    }break;

                    case 5001:{
                        flag = 1;
                        moreShow = 5;
                        getTable = getID;
                        methodShow = 1;

                        endQueryItem(getTable,staff,staffOperate,machineLog);
                        if(error_flag == 0){//无错误
                            notice_toast(Notice.Display_Finished + Notice.Display_Over);//报完工已完成
                        }
                    }break;
                }
            }
        }

        /**员工信息调取函数--登陆操作调用**/
        void getStaff(String getID_now){

            StaffService staffService = new StaffServiceImpl();
            ServiceInfoDTO<Staff> staffServiceInfoDTO = staffService.findUserIdAndTccom001AndTirou001ByAccount(getID_now);

            switch (staffServiceInfoDTO.getCode()){
                case 1://成功
                    Log.i(Constant.TAG,"------------getStaff OK---------");
                    staff = staffServiceInfoDTO.getData(); //构造对象

                    //检查该操作员是否拥有报完工数据权限
                    staffOperate = null;
                    Log.i(Constant.TAG,"------------prepare confirm_staff---------");
                    confirm_staff(staff);
                    Log.i(Constant.TAG,"------------end confirm_staff---------");
                    break;

                case -1://失败--操作员不存在
                    notice_toast(staffServiceInfoDTO.getMsg());//提示输出
                    loginScan = -1;//恢复无法登陆状态
                    break;

                default:
                    notice_toast(Notice.DB_Exception);
                    break;
            }
        }

        /**确认报完工功能的操作员是否有数据权限**/
        void confirm_staff(Staff staff_now){

            StaffService staffService = new StaffServiceImpl();
            ServiceInfoDTO<Staff_operate> staffOperateServiceInfoDTO = staffService.confirmStaffOperate(staff_now.getPrcd());

            switch (staffOperateServiceInfoDTO.getCode()){
                case 1://成功
                    Log.i(Constant.TAG,"------------confirm_staff OK---------");
                    staffOperate = staffOperateServiceInfoDTO.getData();

                    loginScan = 2;
                    special_flag = 1;

                    /**显示框处理函数--staff显示**/
                    machineLog = null;
                    display_objectMessage(staff_now);
                    break;

                case -1://失败--操作员无权限
                    notice_toast(staffOperateServiceInfoDTO.getMsg());
                    break;

                default:
                    notice_toast(Notice.DB_Exception);
                    break;
            }
        }

        /**机器信息调取函数**/
        void getMachine(Staff staff_now, String getID_now, List<String> oper_prcd){

            MachineLogService service = new MachineLogServiceImpl();
            ServiceInfoDTO<MachineLog> machineLogServiceInfoDTO = service.queryMachineLogOne(staff_now,getID_now,oper_prcd);

            switch (machineLogServiceInfoDTO.getCode()){
                case 1://成功
                    Log.i(Constant.TAG,"------------getMachine OK---------");
                    loginScan = 22;     //刷新登录栏
                    special_flag = 1;   //清空显示文本
                    machineLog = machineLogServiceInfoDTO.getData();
                    display_objectMessage(machineLog);
                    break;

                case -1://失败--数据库不存在该机器码
                    loginScan = 1;      //还原登录状态
                    notice_toast(machineLogServiceInfoDTO.getMsg());
                    break;

                case -2://失败--该工序组未配置此工序机器
                    notice_toast(machineLogServiceInfoDTO.getMsg());
                    break;

                case -3://失败--操作员还未登录
                    loginScan = -1;
                    notice_toast(machineLogServiceInfoDTO.getMsg());
                    break;

                default:
                    notice_toast(Notice.DB_Exception);
                    break;
            }
        }

        /**获取单个物品信息函数--查询单个物料调用**/
        void getOneQueryItem(String getID_now){

            ItemService itemService = new ItemServiceImpl();
            ServiceInfoDTO<Item> itemServiceInfoDTO = itemService.queryOne(getID_now);

            switch (itemServiceInfoDTO.getCode()) {
                case 1://成功
                    display_objectMessage(itemServiceInfoDTO.getData());
                    break;

                case -2001://失败--无此物料
                    oracle_databases_toast( -2001 , itemServiceInfoDTO.getMsg() , 885);
                    break;

                default:
                    notice_toast(Notice.DB_Exception);
                    break;
            }
        }

        /**获取货物单信息函数--出入库、查询货单调用**/
        void getQueryItem(String Ss_flag, String getTable_now){
            Log.i(Constant.TAG,"------------getQueryItem Start---------");
            ItemService itemService = new ItemServiceImpl();
            ServiceInfoDTO<List<Item>> serviceInfoDTO;

            switch(Ss_flag){
                case "special_out_query":{//特殊出库查询--排除已出库
                    Log.i(Constant.TAG,"------------itemService.queryOut Start---------");
                    serviceInfoDTO = itemService.queryOut(getTable_now);
                }break;

                case "special_in_query":{//特殊入库查询--排除已入库
                    Log.i(Constant.TAG,"------------itemService.queryIn Start---------");
                    serviceInfoDTO = itemService.queryIn(getTable_now);
                }break;

                case "all_out_query":{//一般出库查询
                    Log.i(Constant.TAG,"------------itemService.queryOutAll Start---------");
                    serviceInfoDTO = itemService.queryOutAll(getTable_now);
                }break;

                case "all_in_query":{//一般入库查询
                    Log.i(Constant.TAG,"------------itemService.queryInAll Start---------");
                    serviceInfoDTO = itemService.queryInAll(getTable_now);
                }break;

                default:
                    return;
            }

            switch(serviceInfoDTO.getCode()){
                case 1://成功
                    List<Item> list = serviceInfoDTO.getData();
                    for(Item it: list){
                        display_objectMessage(it);//显示框处理函数--item显示
                    }
                    break;

                case -1://失败--数据库为空，或者不存在数据
                    oracle_databases_toast( -1001 , serviceInfoDTO.getMsg() , 935);
                    break;

                default:
                    notice_toast(Notice.DB_Exception);
                    return;
            }
        }

        /**货物逐个出库函数--出库调用**/
        void outOne_getQueryItem(String getTable_now,String getID_now){

            ItemService itemService = new ItemServiceImpl();
            ServiceInfoDTO serviceInfoDTO = itemService.executeOut(getTable_now,getID_now);
            //int processReturn = itemService.executeOut(getTable,getID);

            if(serviceInfoDTO.getCode() == 1){//ok
                flag = 1;
                moreShow = 1;
                getQueryItem("special_out_query",getTable_now);
                notice_toast(serviceInfoDTO.getMsg());//出库已完成
            }else{
                notice_toast(serviceInfoDTO.getMsg());//出库错误
            }

        }

        /**货物逐个入库函数--入库调用**/
        void inOne_getQueryItem(String getTable_now,String getID_now){
            /*flag = 1;
            moreShow = 1;
            getQueryItem("special_in_query");
            notice_toast(Notice.Display_InputDatabase + Notice.Display_Over);//提示输出*/
            notice_toast("暂未实现功能");
        }

        /**货物数据更新函数--报完工查询调用**/
        void queryEndQueryItem(String getTable_now){

            PrcdVariableService prcdVariableService = new PrcdVariableServiceImpl();
            ServiceInfoDTO<EndOrder> serviceInfoDTO = prcdVariableService.queryEndQueryItem(getTable_now);

            switch (serviceInfoDTO.getCode()){
                case 1:{//成功--未开时报完工
                    Log.i(Constant.TAG,"------------endQueryItem 1: ---------"+serviceInfoDTO.getMsg());
                    display_objectMessage(serviceInfoDTO.getData());
                }break;

                case 0:{//成功
                    Log.i(Constant.TAG,"------------endQueryItem 0: ---------"+serviceInfoDTO.getMsg());
                    display_objectMessage(serviceInfoDTO.getData());
                }break;

                case -1:{//失败--查询其中工序存在错误
                    Log.i(Constant.TAG,"------------endQueryItem -1: ---------"+serviceInfoDTO.getMsg());
                    display_objectMessage(serviceInfoDTO.getData());
                }break;

                default:{//失败
                    Log.i(Constant.TAG,"------------endQueryItem default: ---------"+serviceInfoDTO.getMsg());
                    oracle_databases_toast( -5001 , serviceInfoDTO.getMsg() , 990);
                }
            }

        }

        /**货物数据更新函数--报完工调用**/
        void endQueryItem(String getTable_now,Staff staff_now,Staff_operate staffOperate_now,MachineLog machineLog_now){

            PrcdVariableService prcdVariableService = new PrcdVariableServiceImpl();
            ServiceInfoDTO<EndOrder> serviceInfoDTO = prcdVariableService.endQueryItem(getTable_now,staffOperate_now.getPrcd());

            switch (serviceInfoDTO.getCode()){
                case 0:{//成功
                    Log.i(Constant.TAG,"------------endQueryItem 0: ---------"+serviceInfoDTO.getMsg());
                    if(machineLog_now != null){//执行机器记录
                        Machine001Service machine001Service = new Machine001ServiceImpl();
                        Machine001 machine001 = new Machine001(machineLog_now.getMachId(),getTable_now,staff_now.getAccount());
                        machine001Service.insertMachine001One(machine001);
                    }
                    display_objectMessage(serviceInfoDTO.getData());
                }break;

                case -11:{//失败--未拥有权限
                    Log.i(Constant.TAG,"------------endQueryItem -11: ---------"+serviceInfoDTO.getMsg());
                    oracle_databases_toast( -5001 , "请先执行第"+ serviceInfoDTO.getData().getPos_o() +"工序: "+ serviceInfoDTO.getData().getName_o() , 1015);
                }break;

                case -1:{//失败--工序失败
                    Log.i(Constant.TAG,"------------endQueryItem -1: ---------"+serviceInfoDTO.getMsg());
                    display_objectMessage(serviceInfoDTO.getData());
                }break;

                default:{//失败
                    Log.i(Constant.TAG,"------------endQueryItem default: ---------"+serviceInfoDTO.getMsg());
                    oracle_databases_toast( -5001 , serviceInfoDTO.getMsg() , 1024);
                }
            }

        }



        /**
         * 数据库为空，或者不存在数据，返回提示
         * 扫描错误对象，返回提示
         *
         * @param Xx_number_errorFlag   扫描连接数据库，数据库为空，或者不存在数据，返回错误标示数
         * @param Yy_string_errorHit    错误标示数下的返回提示
         * @param address_call          错误出现第几行的返回提示
         */
        void oracle_databases_toast(int Xx_number_errorFlag, String Yy_string_errorHit, int address_call){
            //日志跟踪
            Log.i(Constant.TAG,"-----------第"+address_call+"行----数据库错误标志码："+Xx_number_errorFlag+"----"+Yy_string_errorHit+"----");
            special_flag = 1;//清空显示
            error_flag = Xx_number_errorFlag;
            toast_string = Yy_string_errorHit;

            display_nullMessage();//显示框处理函数--空值显示
        }

        /**显示框处理函数--空值显示**/
        void display_nullMessage(){
            Message msg = myHandler.obtainMessage();
            msg.obj = null;
            myHandler.sendMessage(msg);
        }

        /**显示框处理函数--对象输出显示**/
        void display_objectMessage(Object display_object){
            Message msg = myHandler.obtainMessage();
            msg.obj = display_object;
            myHandler.sendMessage(msg);
        }

        /**Toast 提示 一般提示**/
        void notice_toast(String str){
            Looper.prepare();
            Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }

    };

}
