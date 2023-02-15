/*
 * (@)# UserSession.java
 *
 * @author 김경희
 * @version 1.0
 * @date 2013. 3. 1.
 * Copyright (c) 2013 by Inwoo tech inc, KOREA. All Rights Reserved.
 *
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary
 * information of
 * Inwoo Tech Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms
 * of the license agreement you
 * entered into with Inwoo Tech Inc.
 *
 */
package powerservice.business.bean;

import java.io.Serializable;

import powerservice.core.bean.UserSessionCore;


/**
 * 사용자 정보
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 김경희
 * @version 1.0
 * @date 2009. 4. 9.
 * @프로그램ID <<프로그램 아이디>>
 */

public class UserSession implements UserSessionCore, Serializable {
    private static final long serialVersionUID = 2134420861343212194L;

    private String userid = null;
    private String loginid = null;
    private String usernm = null;
    private String authoritycd = null;
    private String employeeid = null;
    private String ext = null;
    private String ctiid = null;
    private String acdgroupno = null;
    private String ptygroupno = null;
    private String ctiusefg = null;
    private String centercd = null;
    private String teamcd = null;
    private String hgrnteamcd = null;
    private String messengerusefg = null;
    private String centernm = null;
    private String recip = null;
    private String dualmonitorfg = null;
    private String singleid = null;
    private String singlemail = null;
    private String centerabusefg = null;
    private String centerabgusefg = null;
    private String leftctrl = null;
    private String mainusefg = null;
    private String channeltype = null;
    private String autoreadyfg = null;
    private String ctiip = null;
    private String ctiport = null;
    private String centerrecip = null;
    private String logfg = null;
    private String residentnofg = null;
    private String autosavefg = null;
    private String obcounselfg = null;
    private String loginstatus = null;
    private String productcd = null;
    private String jobtypecd = null;
    private String jobchnltypecd = null;
    private String multiid = null;
    private String multiautofg = null;
    private String teamnm = null;
    private String bzptdiv = null;
    private String fileid = null;
    private String logindt = null;
    private boolean hasUserAuth = false;
    private boolean hasAdminAuth = false;
    /**
     * 변경일자 : 2015-11-11
     * 변 경 자 : 정      훈
     * 변경이유 : 제품코드에따른 업무 추가
     * 추후이슈 : 제품관리와의 연계후 하드코딩 추후 수정
     * 요 청 자 : 우달식 이사님
     */
    private boolean hasN2consAdminAuth = false;
    private boolean hasWebAdminAuth = false;
    private String encodt = null;

    private String roleList = null;			// 역할관리 - 팀 또는 사용자에 따른 역할을 관리.

    private String tempCode = null; //부가서비스 쿠폰,우편번호 업로드시 사용함
    private String tempMsg = null;  //부가서비스 쿠폰,우편번호 업로드시 사용함

    // ==========================================
    // getter 함수
    // ==========================================



    public String getUserid() {
        return this.userid;
    }


    public String getLoginid() {
        return this.loginid;
    }

    public String getUsernm() {
        return this.usernm;
    }

    public String getAuthoritycd() {
        return this.authoritycd;
    }

    public String getEmployeeid() {
        return this.employeeid;
    }

    public String getCtiid() {
        return this.ctiid;
    }

    public String getExt() {
        return this.ext;
    }

    public String getAcdgroup() {
        return this.acdgroupno;
    }

    public String getPtygroup() {
        return this.ptygroupno;
    }

    public String getCtiusefg() {
        return this.ctiusefg;
    }

    public String getCentercd() {
        return this.centercd;
    }

    public String getTeamcd() {
        return this.teamcd;
    }

    public String getHgrnteamcd() {
        return this.hgrnteamcd;
    }

    public String getMessengerusefg() {
        return this.messengerusefg;
    }

    public String getCenternm() {
        return this.centernm;
    }

    public String getRecip() {
        return this.recip;
    }

    public String getDualmonitorfg() {
        return dualmonitorfg;
    }

    public String getSingleid(){
        return this.singleid;
    }

    public String getSinglemail(){
        return this.singlemail;
    }

    public String getCenterabusefg() {
        return centerabusefg;
    }

    public String getCenterabgusefg() {
        return centerabgusefg;
    }

    public String getLeftctrl() {
        return leftctrl;
    }

    public String getMainusefg(){
        return this.mainusefg;
    }

    public String getChanneltype() {
        return channeltype;
    }

    public String getAutoreadyfg() {
        return autoreadyfg;
    }

    public String getCtiip() {
        return ctiip;
    }

    public String getCtiport() {
        return ctiport;
    }

    public String getCenterrecip() {
        return centerrecip;
    }

    public String getLogfg() {
        return logfg;
    }

    public String getResidentnofg() {
        return residentnofg;
    }

    public String getAutosavefg() {
        return autosavefg;
    }

    public String getObcounselfg() {
        return obcounselfg;
    }

    public String getLoginstatus() {
        return loginstatus;
    }

    public String getProductcd() {
        return productcd;
    }

    public String getJobtypecd() {
        return jobtypecd;
    }

    public String getJobchnltypecd() {
        return jobchnltypecd;
    }

    public String getMultiid() {
        return multiid;
    }

    public String getMultiautofg() {
        return multiautofg;
    }

    public String getTeamnm(){
        return this.teamnm;
    }

    public String getBzptdiv(){
        return this.bzptdiv;
    }

    public String getFileid() {
        return fileid;
    }

    public boolean isHasUserAuth() {
        return hasUserAuth;
    }

    public boolean isHasAdminAuth() {
        return hasAdminAuth;
    }

    public boolean isHasN2consAdminAuth() {
        return hasN2consAdminAuth; //2차상담
    }

    public boolean isHasWebAdminAuth() {
        return hasWebAdminAuth; //웹상담
    }

    public String getLogindt() {
        return logindt;
    }

    public String getRoleList() {
        return roleList;
    }

    public String getEncodt() {
        return encodt;
    }

    // ==========================================
    // setter 함수
    // ==========================================
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public void setUsernm(String usernm) {
        this.usernm =  usernm;
    }

    public void setAuthoritycd(String authoritycd) {
        this.authoritycd =  authoritycd;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid =  employeeid;
    }

    public void setCtiid(String ctiid) {
        this.ctiid =  ctiid;
    }

    public void setExt(String ext) {
        this.ext =  ext;
    }

    public void setAcdgroup(String acdgroupno) {
        this.acdgroupno =  acdgroupno;

    }

    public void setPtygroup(String ptygroupno) {
        this.ptygroupno =  ptygroupno;

    }

    public void setCtiusefg(String ctiusefg) {
        this.ctiusefg =  ctiusefg;

    }

    public void setCentercd(String centercd) {
        this.centercd =  centercd;

    }

    public void setTeamcd(String teamcd) {
        this.teamcd =  teamcd;

    }

    public void setHgrnteamcd(String hgrnteamcd) {
        this.hgrnteamcd =  hgrnteamcd;
    }

    public void setMessengerusefg(String messengerusefg) {
        this.messengerusefg =  messengerusefg;
    }

    public void setCenternm(String centernm) {
        this.centernm =  centernm;
    }

    public void setRecip(String recip) {
        this.recip = recip;
    }

    public void setDualmonitorfg(String dualmonitorfg) {
        this.dualmonitorfg = dualmonitorfg;
    }

    public void setSingleid(String singleid){
        this.singleid = singleid;
    }

    public void setSinglemail(String singlemail){
        this.singlemail = singlemail;
    }

    public void setCenterabusefg(String centerabusefg) {
        this.centerabusefg = centerabusefg;
    }

    public void setCenterabgusefg(String centerabgusefg) {
        this.centerabgusefg = centerabgusefg;
    }

    public void setLeftctrl(String leftctrl) {
        this.leftctrl = leftctrl;
    }

    public void setMainusefg(String mainusefg) {
        this.mainusefg = mainusefg;
    }

    public void setChanneltype(String channeltype) {
        this.channeltype = channeltype;
    }

    public void setAutoreadyfg(String autoreadyfg) {
        this.autoreadyfg = autoreadyfg;
    }

    public void setCtiip(String ctiip) {
        this.ctiip = ctiip;
    }

    public void setCtiport(String ctiport) {
        this.ctiport = ctiport;
    }

    public void setCenterrecip(String centerrecip) {
        this.centerrecip = centerrecip;
    }

    public void setLogfg(String logfg) {
        this.logfg = logfg;
    }

    public void setResidentnofg(String residentnofg) {
        this.residentnofg = residentnofg;
    }

    public void setAutosavefg(String autosavefg) {
        this.autosavefg = autosavefg;
    }

    public void setObcounselfg(String obcounselfg) {
        this.obcounselfg = obcounselfg;
    }

    public void setLoginstatus(String loginstatus) {
        this.loginstatus = loginstatus;
    }

    public void setProductcd(String productcd) {
        this.productcd = productcd;
    }

    public void setJobtypecd(String jobtypecd) {
        this.jobtypecd = jobtypecd;
    }

    public void setJobchnltypecd(String jobchnltypecd) {
        this.jobchnltypecd = jobchnltypecd;
    }

    public void setMultiid(String multiid) {
        this.multiid = multiid;
    }

    public void setMultiautofg(String multiautofg) {
        this.multiautofg = multiautofg;
    }

    public void setTeamnm(String teamnm) {
        this.teamnm = teamnm;
    }

    public void setBzptdiv(String bzptdiv) {
        this.bzptdiv = bzptdiv;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public void setHasUserAuth(boolean hasUserAuth) {
        this.hasUserAuth = hasUserAuth;
    }

    public void setHasAdminAuth(boolean hasAdminAuth) {
        this.hasAdminAuth = hasAdminAuth;
    }

    public void setHasN2consAdminAuth(boolean hasN2consAdminAuth) {
        this.hasN2consAdminAuth = hasN2consAdminAuth; //2차상담
    }

    public void setHasWebAdminAuth(boolean hasWebAdminAuth) {
        this.hasWebAdminAuth = hasWebAdminAuth;   //웹상담
    }

    public void setLogindt(String logindt) {
        this.logindt = logindt;
    }

    public void setRoleList(String roleList) {
        this.roleList = roleList;
    }

    public void setEncodt(String encodt) {
        this.encodt = encodt;
    }


    public String getTempCode() {
        return tempCode;
    }

    public void setTempCode(String tempCode) {
        this.tempCode = tempCode;
    }

    public String getTempMsg() {
        return tempMsg;
    }

    public void setTempMsg(String tempMsg) {
        this.tempMsg = tempMsg;
    }

}
