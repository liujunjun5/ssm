package com.work.enums;

public enum MessageTypeEnum {

    SYS(1, "系统消息"),
    COMMENT(2, "评论");

    private Integer type;
    private String desc;

    MessageTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static MessageTypeEnum getByType(Integer type) {
        for (MessageTypeEnum statusEnum : MessageTypeEnum.values()) {
            if (statusEnum.getType().equals(type)) {
                return statusEnum;
            }
        }
        return null;
    }
}
