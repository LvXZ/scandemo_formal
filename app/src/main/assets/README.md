# 家具生产流水线移动扫码器开发文档
---
## 一、app红外线接口介绍
### 1. 扫码器硬件初始化
* **函数名称：** `initScanner()`
* **函数描述：** `扫码器硬件初始化`
* **功能说明：** `通过ScannerInterface函数接口文件，选择性调用接口函数，激活扫码器硬件`
* **传入参数：** 无
* **返回参数：** 无


### 2. ScannerInterface
* **接口文件：** `ScannerInterface.class`
* **接口描述：** `所有硬件接口函数集合，扫描接口常量定义文件`


## 二、app全局变量介绍

### 1. app控件对象变量
参数名称 | 类型 | 属性 | 备注
---|---|---|---
`tvScanResult` | TextView | public | 功能数据处理显示控件
`tvLoginResult` | TextView | public | 登录处理显示控件
`tvMethodResult` | TextView | public |  功能选择显示控件
`scanner` | ScannerInterface | public | 硬件接口文件定义对象
`intentFilter` | IntentFilter | public | 扫描结果过滤器
`scanReceiver` | BroadcastReceiver | public | 扫描结果广播接收者

### 2. 数据库类对象变量
参数名称 | 类型 | 属性 | 备注
---|---|---|---
`item` | Item | public | 货物对象
`staff` | Staff | public | 用户对象
`endOrder` | EndOrder | public |  获取报完工对象--显示工序节点
`staff_oper_acc` | String | public | 当前操作员账号
`staff_oper_prcd` | String[] | public | 保存当前操作员报完工所有数据权限

### 3. 程序运行控制函数变量
参数名称 | 类型 | 属性 | 备注
---|---|---|---
`getID` | String | public | 扫描字符串对象
`getTable` | String | public | 单子对象
`flagScan` | int | private | 锁定功能flag标志
`signState` | String | private | 登录职位标志
`loginScan` | int | private | 确定登录状态
`administratorLogin` | boolean | private | 确定管理员登录


**getID 标志识别认证**

职位 | 正则表达式
---|---
管理员 |`^15080[0-9]{4}$`
操作员 |`^15070[0-9]{4}$`


**flagScan 锁定功能flag标志认证**

序列号 | 功能
---|---
`1001`| 查询单个物料
`1002`| 查询出库单
`1003`| 查询入库单
`1005`| 查询报完工单
`2001`| 出库
`3001`| 入库
`5001`| 报完工

**signState 登录职位标志认证**

序列号 | 功能
---|---
`010100`| 出库管理员
`001100`| 入库管理员
`100100`| 报完工管理员
`011100`| 出库、入库管理员
`110100`| 出库、报完工管理员
`101100`| 入库、报完工管理员
`111100`| 小组长--出库、入库、报完工管理员
`888888`| 测试员
`999999`| OS软件管理员
`000000`| 操作员

**loginScan 确定登录状态**

序列号 | 功能
---|---
`-1`| 管理员未登录
`0`| 确定管理员登陆 操作员未登录
`1`| 全部已登录--可以使用扫描器
`2`| 准备修改登录栏目--添加管理员
`22`| 准备修改登录栏目--添加操作员
`3`| 执行 管理员操作员登录操作

### 4. 程序运行控制显示变量
参数名称 | 类型 | 属性 | 功能| 备注
---|---|---|---|---
`login_text` | String | private | `登陆显示字符串` | 无
`method_text` | String | private | `功能显示字符串` | 无
`toast_string` | String | private | `提示显示字符串` | 无
`moreShow` | int | private | `tvScanResult显示处理` | 0:清空上次输出,1:保留上次输出
`methodShow` | int | private | `tvMethodResult显示处理` | 0:清空上次输出,1:保留上次输出
`error_flag` | int | private | `功能BUG错误显示` | 无

**error_flag 显示BUG**

序列号 | 功能
---|---
`-1001`| 订单BUG
`-2001`| 物料BUG
`-5001`| 报完工BUG

## 三、app函数接口说明

### 1. 左侧菜单按钮函数
函数名称 | 函数说明 | 传入参数
---|--- | ---
`functions1(View view)` | 按钮点击响应下拉菜单1 | 视图
`showPopMenu1(View view)` | 下拉菜单1(左边菜单)选择键点击 | 视图
`queryOneItem(View view)` | 单个物料-查询按钮的响应 | 视图
`queryOutList(View view)` | 出库单-查询按钮的响应 | 视图
`queryInList(View view)` | 入库单-查询按钮的响应 | 视图
`queryFinishList(View view)` | 报完工单-查询按钮的响应 | 视图

### 2. 右侧菜单按钮函数
函数名称 | 函数说明 | 传入参数
--- | --- | ---
`functions2(View view)` | 按钮点击响应下拉菜单2 | 视图
`showPopMenu2(View view)` | 下拉菜单2(右侧菜单)选择键点击 | 视图
`outputDatabase(View view)` | 出库选择键响应 | 视图
`inputDatabase(View view)` | 入库选择键响应 | 视图
`IP_address(View view)` | 改变IP选择键响应 | 视图


### 3. 中间按钮函数
函数名称 | 函数说明 | 传入参数
--- | --- | ---
`finished(View view)` | 报完工按钮 | 视图


### 4. 菜单按钮选择函数
函数名称 | 函数说明 | 传入参数
--- | --- | ---
`setVisible_Select(String state)` | 对3个按钮进行激活操作选择 | 管理员职位权限
`setVisible_Select_Left(PopupMenu popupMenu, String state)` | 对左子菜单进行显示操作选择 | 左菜单对象 + 管理员职位权限
`setVisible_Select_Right(PopupMenu popupMenu, String state)` | 对右子菜单进行显示操作选择 | 左菜单对象 + 管理员职位权限


```
    /**
     * 确认功能按钮
     * @param Aa_number_flagScan 对应功能按钮的扫描确认标示数
     * @param Bb_string_function 对应功能按钮的扫描功能栏文字信息的改变
     * @param Cc_string_toast    对应功能按钮的扫描确认后的提示
     */
    public void confirm_Functions(int Aa_number_flagScan, String Bb_string_function, String Cc_string_toast){
    }
```

函数名称 | 函数说明 | 传入参数
--- | --- | ---
`showResult(String string)` | 对操作栏、显示模块进行刷新 | string
`confirm_staff(String Aa_account)` | 确认报完工功能的操作员是否有数据权限 | string
`getOneQueryItem()` | 获取单个物品信息函数--查询单个货物调用 | 无
`outOne_getQueryItem()` | 货物逐个出库函数--出库调用 | 无
`inOne_getQueryItem()` | 货物逐个入库函数--入库调用 | 无

函数名称 | 函数说明 | 传入参数
--- | --- | ---
`getStaff()` | 员工信息调取函数--登陆操作调用 | 无
`getQueryItem(String Ss_flag)` | 获取货物单信息函数--出入库、查询货单调用 | string
`queryendQueryItem()` | 货物数据更新函数--报完工查询调用 | 无
`endQueryItem()` | 货物数据更新函数--报完工调用 | 无


```
        /**
         * 数据库为空，或者不存在数据，返回提示
         * 扫描错误对象，返回提示
         *
         * @param Xx_number_errorFlag   扫描连接数据库，数据库为空，或者不存在数据，返回错误标示数
         * @param Yy_string_errorHit    错误标示数下的返回提示
         * @param address_call          错误出现第几行的返回提示
         */
        public void orcal_datebases_toast(int Xx_number_errorFlag, String Yy_string_errorHit, int address_call){
        }
```

### 显示处理函数
函数名称 | 函数说明 | 传入参数
--- | --- | ---
`display_nullMessage()` | 显示框处理函数--空值显示 | 无
`display_staffMessage(Staff display_userid)` | 显示框处理函数--staff显示 | Staff
`display_itemMessage(Item display_item)` | 显示框处理函数--item显示 | Item
`display_endOrderMessage(EndOrder display_endOrder)` | 显示框处理函数--endOrder显示 | EndOrder
`notice_toast(String str)` | Toast 提示 一般提示 | String


### 数据库连接函数
函数名称 | 函数说明 | 传入参数
--- | --- | ---
`openCSR(String DB_URL, String sql)` | 连接数据库各属性获取 | url + sql语言
`closeCSR()` | 关闭数据库各属性 | 无

### 数据库数据处理函数
函数名称 | 函数说明 | 传入参数
--- | --- | ---


# 开发人员
---
### Android界面
* **人员** `lvxz`

### Android后台
* **人员** `once + lvxz`

### orcal数据库开发
* **人员** `once`




