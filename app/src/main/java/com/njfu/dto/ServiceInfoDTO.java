package com.njfu.dto;

/**
 * Introduction: service层返回类型定义类
 * Created by lvxz on 2018/6/3.
 *
 * controller层访问service返回的最外层对象
 * code        提示码
 * msg         提示信息
 * data        具体对象内容
 */

public class ServiceInfoDTO<T> extends MessageInfoDTO {

    private T data;

    public ServiceInfoDTO(int code, String msg) {
        super(code, msg);
    }

    public ServiceInfoDTO(int code, String msg, T data) {
        super(code, msg);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
