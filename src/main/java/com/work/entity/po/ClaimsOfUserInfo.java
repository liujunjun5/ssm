package com.work.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;


//ljz
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimsOfUserInfo {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 0:女 1:男 2:未知
     */
    private Integer sex;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 个人简介
     */
    private String personIntroduction;

    /**
     * 公告
     */
    private String noticeInfo;

    public ClaimsOfUserInfo addUserInfo(ClaimsOfUserInfo claimsOfUserInfo){

        this.nickName=claimsOfUserInfo.getNickName()!=null?claimsOfUserInfo.getNickName():null;
        this.avatar=claimsOfUserInfo.getAvatar()!=null?claimsOfUserInfo.getAvatar():null;
        this.sex=claimsOfUserInfo.getSex()!=null?claimsOfUserInfo.getSex():null;
        this.birthday=claimsOfUserInfo.getBirthday()!=null?claimsOfUserInfo.getBirthday():null;
        this.personIntroduction=claimsOfUserInfo.getPersonIntroduction()!=null?claimsOfUserInfo.getPersonIntroduction():null;
        this.noticeInfo=claimsOfUserInfo.getNoticeInfo()!=null?claimsOfUserInfo.getNoticeInfo():null;

        return this;

    }

}
