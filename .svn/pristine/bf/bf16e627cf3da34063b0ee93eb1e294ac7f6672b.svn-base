/*
 * (@)# DlwProdServiceImpl.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/16
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwOcbListService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 사원 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/16
 * @프로그램ID DlwProd
 */
@Service
public class DlwOcbListServiceImpl extends EgovAbstractServiceImpl implements DlwOcbListService {

    @Resource
    public DlwOcbListDAO dlwOcbListDAO;

    /**
     * 조직정보 트리뷰
     *
     * @param pmParam 검색 조건
     * @return 조직도 트리
     * @throws Exception
     */
    public List<Map<String, Object>> getOcbTreeList(Map<String, ?> pmParam) throws Exception {
        return dlwOcbListDAO.getOcbTreeList(pmParam);
    }

    /**
     * 조직 소속 사원 조회
     *
     * @param pmParam 검색 조건
     * @return 조직도 트리
     * @throws Exception
     */
    public List<Map<String, Object>> getGrpEmpList(Map<String, ?> pmParam) throws Exception {
        return dlwOcbListDAO.getGrpEmpList(pmParam);
    }

    /**
     * 조직도 입력
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertGrpDept(Map<String, ?> pmParam) throws Exception {
        return dlwOcbListDAO.insertGrpDept(pmParam);
    }

    /**
     * 조직도 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateGrpDept(Map<String, ?> pmParam) throws Exception {
        return dlwOcbListDAO.updateGrpDept(pmParam);
    }

    /**
     * 조직도 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGrpDept(Map<String, ?> pmParam) throws Exception {
        return dlwOcbListDAO.deleteGrpDept(pmParam);
    }

    /**
     * OCB 연회비 산출 이력 조회
     *
     * @param pmParam 검색 조건
     * @return OCB 연회비 산출 이력 조회
     * @throws Exception
     */
    public List<Map<String, Object>> getOcbAnnHist(Map<String, ?> pmParam) throws Exception {
        return dlwOcbListDAO.getOcbAnnHist(pmParam);
    }

    /**
     * OCB 연회비 산출
     *
     * @param pmParam 검색 조건
     * @return OCB 연회비 산출
     * @throws Exception
     */
    public List<Map<String, Object>> getOcbAnnCac(Map<String, ?> pmParam) throws Exception {
        return dlwOcbListDAO.getOcbAnnCac(pmParam);
    }


    /**
     * 대명라이프웨이 EB21 산출 적용
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public void insertocbyearsave(Map<String, ?> mapInDs) throws Exception {
        //공통정보

        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");


        if (listInDs != null && listInDs.size() > 0) {

         for (int i = 0; i < listInDs.size(); i++) {
            hmParam = listInDs.get(i);
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            dlwOcbListDAO.insertocbyearsave(hmParam);
         }

        }



    }


}
