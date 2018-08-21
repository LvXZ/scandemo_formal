package com.njfu.entity;

public class EndOrder {

    /**
     * orderID		订货单号
     * code_o		产品编号
     * name_o		产品名称
     * amount_o		产品数量
     * unit_o		计量单位
     * prcdName		所有节点名
     * state_o		节点状态
     * pos_o		当前节点位置
     */
    private String orderID;
    private String name_o;
    private String code_o;
    private String amount_o;
    private String unit_o;

    private String[] prcdName;
    private int pos_o;
    private String state_o;

    public EndOrder() {
    }

    public EndOrder(String orderID, String name_o, String code_o, String amount_o, String unit_o, String[] prcdName, int pos_o, String state_o) {
        this.orderID = orderID;
        this.name_o = name_o;
        this.code_o = code_o;
        this.amount_o = amount_o;
        this.unit_o = unit_o;
        this.prcdName = prcdName;
        this.pos_o = pos_o;
        this.state_o = state_o;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getName_o() {
        return name_o;
    }

    public void setName_o(String name_o) {
        this.name_o = name_o;
    }

    public String getCode_o() {
        return code_o;
    }

    public void setCode_o(String code_o) {
        this.code_o = code_o;
    }

    public String getAmount_o() {
        return amount_o;
    }

    public void setAmount_o(String amount_o) {
        this.amount_o = amount_o;
    }

    public String getUnit_o() {
        return unit_o;
    }

    public void setUnit_o(String unit_o) {
        this.unit_o = unit_o;
    }

    public String[] getPrcdName() {
        return prcdName;
    }

    public void setPrcdName(String[] prcdName) {
        this.prcdName = prcdName;
    }

    public int getPos_o() {
        return pos_o;
    }

    public void setPos_o(int pos_o) {
        this.pos_o = pos_o;
    }

    public String getState_o() {
        return state_o;
    }

    public void setState_o(String state_o) {
        this.state_o = state_o;
    }

    public String showPosition(){
        String head = "<h3>&ensp;&ensp;&ensp;&ensp;<font color='#ADD8E6'>";    //h2号字体 &ensp;空格键
        String change1 = "</font><font color='red'><strong>";     //红色下划线加粗再加大号
        String change2 = "</strong></font>";
        String tail = "</h3>";

        String count = "";
        String style = "<br>&ensp;&ensp;";

        String position = head;
        for(int i=0; i<prcdName.length; i++){

            if(prcdName[i]!=null && !prcdName[i].equals("")){

                if(pos_o == i){
                    position = position + change1;
                    position += prcdName[i] ;
                    position = position + change2;
                }else{
                    position += prcdName[i] ;
                }

                if(i<prcdName.length-1 && prcdName[i+1]!=null && !prcdName[i+1].equals("")){
                    position += "--->";
                }

            }
            /*count += prcdName[i] ;
            if(count.length() >= 8 ){
                position += style ;
                count ="";
            }*/

        }
        position = position + tail;


        return position;
    }

    @Override
    public String toString() {
        return
                "| 产品名称: " + name_o + "<br>" +
                "| 产品编号: " + code_o + "<br>" +
                "| 数量: " + amount_o + " (" +unit_o+") <br>" +
                "| 当前工艺节点: <font color='red'><u><strong>" + prcdName[pos_o] + "</strong></u></font><br>" +
                "| 当前状态: " + state_o + "<br>" +
                        showPosition() + "<br>"
                ;
    }
}
