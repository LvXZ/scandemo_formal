package com.njfu.dto;

/** Introduction: 返回msg类,对service层信息进行补充
 * Created by lvxz on 2018/6/3.
 */

public class MessageInfoDTO {

    private int code;
    private String msg;

    public MessageInfoDTO() {
    }

    public MessageInfoDTO(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }
}
