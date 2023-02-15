/*
 * (@)# TrgtExcpServiceImpl.java
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
package powerservice.business.cns.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.cns.service.TrgtExcpService;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

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

@Service
public class TrgtExcpServiceImpl extends EgovAbstractServiceImpl implements TrgtExcpService {

    @Resource
    public TrgtExcpDAO trgtExcpDAO;

    /**
     * 연체대상제외 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return TrgtExcp 정보의 검색 수
     * @throws Exception
     */
    public int getTrgtExcpCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) trgtExcpDAO.getTrgtExcpCount(pmParam);
    }

    /**
     * 연체대상제외 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 연체대상제외 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTrgtExcpList(Map<String, ?> pmParam) throws Exception {
        return trgtExcpDAO.getTrgtExcpList(pmParam);
    }

    /**
     * 연체대상제외 정보를 등록/수정한다.
     *
     * @param pmParam 연체대상제외 정보
     * @throws Exception
     */
    public String saveTrgtExcp(Map<String, Object> pmParam) throws Exception {
        String sKey = StringUtils.defaultString((String) pmParam.get("trgt_excp_id"));

        int nResult = 0;

        if ("".equals(sKey)) {
            nResult = trgtExcpDAO.insertTrgtExcp(pmParam);
        } else {
            nResult = trgtExcpDAO.updateTrgtExcp(pmParam);
        }

        if (nResult > 0) {
            sKey = (String) pmParam.get("trgtExcp_id");
        }

        return sKey;
    }

    public void saveallTrgtExcp(Map<String, DataSetMap> mapInDs) throws Exception {


        Map<String, Object> hmParam = new HashMap<String, Object>();

        DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

        String msg = "";

        for (int i = 0; i < listInDs.size(); i++) {

            hmParam = listInDs.get(i);

            ParamUtils.addSaveParam(hmParam);
            hmParam.put("rgsr_id", hmParam.get("rgsr_id"));
            hmParam.put("amnd_id", hmParam.get("rgsr_id"));
            hmParam.put("cntr_cd", "CCA");
            hmParam.put("use_yn", "Y");




/////// 첫번째행은 제외
            if ( i > 0)  {
                trgtExcpDAO.insertTrgtExcp(hmParam);

            }

        }

   }

    /** ================================================================
     * 날짜 : 20190416
     * 이름 : 정승철
     * 추가내용 : 연체대상제외 정보삭제
     * 대상 테이블 : PS_WILLVI.TB_TRGT_EXCP
     * ================================================================
     * */
    public void deleteTrgtExcp(Map<String, Object> pmParam) throws Exception {
        trgtExcpDAO.deleteTrgtExcp(pmParam);

   }

}
