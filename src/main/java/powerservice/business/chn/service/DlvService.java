/*
 * (@)# DlvService.java
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/06/28
 * Copyright (c) 2015 by Inwoo tech inc, KOREA. All Rights Reserved.
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
package powerservice.business.chn.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 물류 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/06/28
 * @프로그램ID PS450400
 *
 */
public interface DlvService {

    public String saveDlv(Map<String, Object> pmParam) throws Exception;


    public int getDlvCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlvList(Map<String, ?> pmParam) throws Exception;

    public int deleteDlv(Map<String, ?> pmParam) throws Exception;


   // public int getDlvDtlCount(Map<String, ?> pmParam) throws Exception;

   //public List<Map<String, Object>> getDlvDtlList(Map<String, ?> pmParam) throws Exception;

    public int deleteDlvDtl(Map<String, ?> pmParam) throws Exception;

    public void uploadToNas(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception;

}
