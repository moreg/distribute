package com.jdsw.distribute.util;

import java.util.HashMap;

public class Message extends HashMap<String,Object> {
    private static final long serialVersionUID = 1L;

    public static final String CODE_TAG = "code";

    public static final String MSG_TAG = "msg";

    public static final String DATA_TAG = "data";
    /**
     * 状态类型
     */
    public enum Type
    {
        /** 成功 */
        SUCCESS(0),
        /**失败*/
        FAIL(1),
        /** 警告 */
        WARN(301),
        /** 错误 */
        ERROR(500);
        private final int value;

        Type(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }
    /** 状态类型 */
    private Type type;

    /** 状态码 */
    private int code;

    /** 返回内容 */
    private String msg;

    /** 数据对象 */
    private Object data;
    /**
     * 初始化一个新创建的 Message 对象，使其表示一个空消息。
     */
    public Message(){}
    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param type 状态类型
     * @param msg 返回内容
     */
    public Message(Type type, String msg)
    {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
    }
    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param  code 状态类型
     * @param msg 返回内容
     * @param data 数据对象
     */
    public Message(Integer code, String msg, Object data)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        super.put(DATA_TAG, data);
    }
    public Message( String msg, Object data)
    {
        super.put(CODE_TAG, 0);
        super.put(MSG_TAG, msg);
        super.put(DATA_TAG, data);
    }
    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static Message success()
    {
        return Message.success("操作成功");
    }
    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static Message success(String msg)
    {
        return Message.success(msg, null);
    }
    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static Message success(String msg, Object data)
    {
        return new Message(msg, data);
    }
    public static Message success(String msg, Integer code)
    {
        return new Message(msg, code);
    }
    public static Message success(String msg, Object data,Integer code)
    {
        return new Message(code,msg,data);
    }
    /**
     * 返回失败消息
     *
     * @return 成功消息
     */
    public static Message fail()
    {
        return Message.fail("操作失败");
    }
    /**
     * 返回失败消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static Message fail(String msg)
    {
        return Message.fail(msg, null);
    }
    /**
     * 返回失败消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static Message fail(String msg, Object data)
    {
        return new Message(-1, msg, data);
    }
    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static Message warn(String msg)
    {
        return Message.warn(msg, null);
    }
    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static Message warn(String msg, Object data)
    {
        return new Message(301, msg, data);
    }
    /**
     * 返回错误消息
     *
     * @return
     */
    public static Message error()
    {
        return Message.error("操作失败");
    }
    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static Message error(String msg)
    {
        return Message.error(msg, null);
    }
    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static Message error(String msg, Object data)
    {
        return new Message(500, msg, data);
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

}
