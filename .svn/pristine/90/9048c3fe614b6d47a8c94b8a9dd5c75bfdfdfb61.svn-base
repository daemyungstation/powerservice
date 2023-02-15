/*
 * (@)# DlwRcrtCntrServiceImpl.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/14
 * Copyright (c) 2016 by Inwoo tech inc, KOREA. All Rights Reserved.
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

package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwRcrtCntrService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 대명라이프웨이 모집인계약서 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/14
 * @프로그램ID DlwRcrtCntrServiceImpl
 */
@Service
public class DlwRcrtCntrServiceImpl extends EgovAbstractServiceImpl implements DlwRcrtCntrService {

    @Resource
    public DlwRcrtCntrDAO DlwRcrtCntrDAO;

    /**
     * 모집인계약서 출력 정보 수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getRcrtCntrCount(Map<String, ?> pmParam) throws Exception {
        return DlwRcrtCntrDAO.getRcrtCntrCount(pmParam);
    }

    /**
     * 모집인계약서 출력 정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getRcrtCntrList(Map<String, ?> pmParam) throws Exception {
        return DlwRcrtCntrDAO.getRcrtCntrList(pmParam);
    }
    
    /**
     * 파일 정보를 입력한다.
     *
     * @param hmParam 파일 정보
     * @throws Exception
     */
    public String insertFile(Map<String, ?> hmParam) throws Exception {
        String sKey = "";
        int nResult = DlwRcrtCntrDAO.insertFile(hmParam);
        if (nResult > 0) {
            sKey = (String) hmParam.get("file_id");
        }
        return sKey;
    }
}
