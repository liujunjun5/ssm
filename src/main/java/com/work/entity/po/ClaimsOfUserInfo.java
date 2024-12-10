package com.work.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;


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

    public ClaimsOfUserInfo encryptOfMd5(ClaimsOfUserInfo claimsOfUserInfo){

        ClaimsOfUserInfo Md5ClaimsOfUserInfo = new ClaimsOfUserInfo();
        Md5ClaimsOfUserInfo.setUserId(claimsOfUserInfo.getUserId());
        Md5ClaimsOfUserInfo.setNickName(claimsOfUserInfo.getNickName());
        Md5ClaimsOfUserInfo.setAvatar(claimsOfUserInfo.getAvatar());
        Md5ClaimsOfUserInfo.setSex(claimsOfUserInfo.getSex());
        Md5ClaimsOfUserInfo.setBirthday(claimsOfUserInfo.getBirthday()!=null?DigestUtils.md5Hex(claimsOfUserInfo.getBirthday()):null);
        Md5ClaimsOfUserInfo.setPersonIntroduction(claimsOfUserInfo.getPersonIntroduction());
        Md5ClaimsOfUserInfo.setNoticeInfo(claimsOfUserInfo.getNoticeInfo());
        return Md5ClaimsOfUserInfo;
    }

}
