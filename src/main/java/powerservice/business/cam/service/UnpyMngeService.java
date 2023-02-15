/*
 * (@)# UnpyMngeService.java
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/06/09
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
package powerservice.business.cam.service;

import java.util.List;
import java.util.Map;

/**
 * 원장테이블
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2016/07/14
 * @프로그램ID UnpyMnge
 */

public interface UnpyMngeService {

    public int getUnpyMngeCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getUnpyMngeList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getUnpyMngeReportWithIchaeDtCnt(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getUnpyMngeReportWithIchaeDtPrice(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getUnpyMngeReportWithTotal(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getUnpyMngeReportWithCnsr(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getUnpyMngeReportWithProdGbnCdTotal(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getUnpyMngeReportWithProdGbnCd(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getUnpyMngeReportWithPayState(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getUnpyMngeReportWithPayState2(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getUnpyMngeReportWithSection2(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getUnpyMngeReportWithCamptype(Map<String, ?> pmParam) throws Exception;

}
