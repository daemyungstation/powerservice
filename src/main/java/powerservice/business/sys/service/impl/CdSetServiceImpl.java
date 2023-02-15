/*
 * (@)# CdSetService.java
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
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

package powerservice.business.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.sys.service.CdSetService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 코드셋 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID CdSet
 */
@Service
public class CdSetServiceImpl extends EgovAbstractServiceImpl implements CdSetService {

    @Resource
    public CdSetDAO cdSetDAO;

    /**
     * 대명 기초 코드셋 정보를 등록한다.
     *
     * @param pmParam 코드셋 정보
     * @throws Exception
     */
    public String insertComCdSet(Map<String, ?> pmParam) throws Exception {
        String sKey = "";

        int nResult = cdSetDAO.insertComCdSet(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("cd_set_cd");
        }

        return sKey;
    }

    /**
     * 코드셋 정보를 등록한다.
     *
     * @param pmParam 코드셋 정보
     * @throws Exception
     */
    public String insertCdSet(Map<String, ?> pmParam) throws Exception {
        String sKey = "";

        int nResult = cdSetDAO.insertCdSet(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("cd_set_cd");
        }

        return sKey;
    }

    /**
     * 코드셋 정보를 수정한다.
     *
     * @param pmParam 코드셋 정보
     * @throws Exception
     */
    public int updateComCdSet(Map<String, ?> pmParam) throws Exception {
        return cdSetDAO.updateComCdSet(pmParam);
    }


    /**
     * 코드셋 정보를 수정한다.
     *
     * @param pmParam 코드셋 정보
     * @throws Exception
     */
    public int updateCdSet(Map<String, ?> pmParam) throws Exception {
        return cdSetDAO.updateCdSet(pmParam);
    }

    /**
     * 코드셋 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 코드셋 정보의 검색 건수
     * @throws Exception
     */
    public int getComCdSetCount(Map<String, ?> pmParam) throws Exception {
        return cdSetDAO.getComCdSetCount(pmParam);
    }

    /**
     * 코드셋 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 코드셋 정보의 검색 건수
     * @throws Exception
     */
    public int getCdSetCount(Map<String, ?> pmParam) throws Exception {
        return cdSetDAO.getCdSetCount(pmParam);
    }

    /**
     * 코드셋 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 코드셋 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getCdSetList(Map<String, ?> pmParam) throws Exception {
        String sType_Cd = StringUtils.defaultString((String) pmParam.get("type_cd"));

        List<Map<String, Object>> getCdSet = new ArrayList<Map<String,Object>>();

        if(sType_Cd.equals("counsel")){//상담코드
        	getCdSet = cdSetDAO.getCdSetList(pmParam);
        }
        else{
        	getCdSet = cdSetDAO.getCdSetMemList(pmParam);
        }

        //System.out.println("==>" + sType_Cd);

        return  getCdSet;

    }

    /**
     * 코드셋 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 코드셋 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCdSet(Map<String, ?> pmParam) throws Exception {
        return cdSetDAO.getCdSet(pmParam);
    }

}
