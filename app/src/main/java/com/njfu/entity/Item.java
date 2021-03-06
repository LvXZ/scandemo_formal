package com.njfu.entity;



/**映射android数据库info.db-item,类对象属性
 * id       :流水号
 * itemID   :物流编号
 * location :创库位置
 * amount   :剩余数量
 * amount_in:已经入库的数量（交货数量）
 * in_out 如果是出库，则代表源仓库中剩余物料的数量，如果是入库，则代表目标仓库已入库（交货)的数量
 */
public class Item {

    private String itemID;
    private String name_item;
    private String location;
    private String amount;


    private double in_out;
    private String select;

    public Item() {
    }

    public Item(String itemID, String name_item, String location, String amount, double in_out, String select) {
        this.itemID = itemID;
        this.name_item = name_item;
        this.location = location;
        this.amount = amount;
        this.in_out = in_out;
        this.select = select;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getName_item() {
        return name_item;
    }

    public void setName_item(String name_item) {
        this.name_item = name_item;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public double getIn_out() {
        return in_out;
    }

    public void setIn_out(double in_out) {
        this.in_out = in_out;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String showPosition2(){
        if(in_out ==0 )
            return "已全部出库";

        return "未出库";
    }

    public String showPosition3(double bqua) {

        if(bqua == 0){
            return "已全部入库";
        }else{
            return "未入库"+ bqua;
        }

    }

    public String selectShow(){
        if(select.equals("出库")) {
            return showPosition2();
        }else if(select.equals("入库")){
            return showPosition3(Double.valueOf(amount)-in_out);
        }else{
            return "";
        }
    }

    @Override
    public String toString() {
        return
                  "| 物料编码:" + itemID +
                "\n| 物料名称:" + name_item +
                "\n| 数量:" + amount +
                "\n| 库房:" + location +"                                        "+selectShow()+
                "\n-------------------------------------------------------------------------------\n"
                ;
    }

}
