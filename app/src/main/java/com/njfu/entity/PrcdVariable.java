package com.njfu.entity;

import java.util.Arrays;

public class PrcdVariable {
	/*if errorText is not null, there must be some error*/
	/**
	 * errorText
	 * orderNo		订货单号
	 * itemNo		产品编号
	 * itemName		产品名称
	 * itemNum		产品数量
	 * itemUnit		计量单位
	 * nodeIdno
	 * prcdName		所有节点名
	 * nodeStat		节点状态
	 * prcdPos		当前节点位置
	 *
	 * pdno
	 */
	public String errorText = null;

	public String orderNo;//production order no
	public String itemNo;//product no
	public String itemName;//product name
	public double itemNum;//number
	public String itemUnit;//product unit

	public String[] nodeIdno;
	public String[] prcdName;//process node name
	public String[] nodeStat;//process node status
	public int prcdPos;//position of current node

	public String[] prcdAll;

	/*some inner variables*/
	public long pdno;
	/*errorNo != 0 ==> error
         *errorText refers to error details
         *errorNo == -1 ==> statement error
         *errorNo == 100 ==> no result */

	public int errorNo = 0;



	public void writeError(int errorNo,String errorText) {
		this.errorNo = errorNo;
		this.errorText = errorText;
	}

	public int getErrorNo() {
		return errorNo;
	}

	public void setErrorNo(int errorNo) {
		this.errorNo = errorNo;
	}

	@Override
	public String toString() {
		return "PrcdVariable{" +
				"errorText='" + errorText + '\'' +
				", orderNo='" + orderNo + '\'' +
				", itemNo='" + itemNo + '\'' +
				", itemName='" + itemName + '\'' +
				", itemNum=" + itemNum +
				", itemUnit='" + itemUnit + '\'' +
				", nodeIdno=" + Arrays.toString(nodeIdno) +
				", prcdName=" + Arrays.toString(prcdName) +
				", nodeStat=" + Arrays.toString(nodeStat) +
				", prcdPos=" + prcdPos +
				", prcdAll=" + Arrays.toString(prcdAll) +
				", pdno=" + pdno +
				", errorNo=" + errorNo +
				'}';
	}
}
