/*
 * (@)# TrgtExcpService.java
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/07/07
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
package powerservice.business.cns.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;

/**
 * 연체대상제외 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/07/07
 * @프로그램ID PS030700
 *
 */

public interface TrgtExcpService {

    public int getTrgtExcpCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getTrgtExcpList(Map<String, ?> pmParam) throws Exception;

    public String saveTrgtExcp(Map<String, Object> pmParam) throws Exception;

    public void saveallTrgtExcp(Map<String, DataSetMap> mapInDs) throws Exception;

    /** ================================================================
     * 날짜 : 20190416
     * 이름 : 정승철
     * 추가내용 : 연체대상제외 정보삭제
     * 대상 테이블 : PS_WILLVI.TB_TRGT_EXCP
     * ================================================================
     * */
    public void deleteTrgtExcp(Map<String, Object> pmParam) throws Exception;

}
