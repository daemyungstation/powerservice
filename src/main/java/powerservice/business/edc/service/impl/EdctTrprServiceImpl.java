/*
 * (@)# EdctTrprServiceImpl.java
 *
 * @author 박상수
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

package powerservice.business.edc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.bean.UserSession;
import powerservice.business.edc.service.EdctTrprService;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 교육평가대상자 관리를 한다.
 * Copyright (c) 2014 Company Inwoo Tech Inc.
 *
 * @author 박상수
 * @version 1.0
 * @date 2014/04/01
 * @프로그램ID EdctTrprServiceImpl
 */
@Service
public class EdctTrprServiceImpl extends EgovAbstractServiceImpl implements EdctTrprService {

    @Resource
    public EdctTrprDAO edctTrprDAO;

    /**
     * 교육평가대상자 정보의 검색 수를 반환한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 교육평가대상자 정보의 검색 건수
     * @throws Exception
     */
    public int getEdctTrprcount(Map<String, ?> pmParam) throws Exception {
        return (Integer) edctTrprDAO.getEdctTrprcount(pmParam);
    }

    /**
     * 고육평가 정보를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 교육평가대상자 목록
     * @throws Exception
     */
    public List<Map<String, Object>> searchEdctTrprList(Map<String, ?> pmParam) throws Exception {
        List<Map<String, Object>> nList = edctTrprDAO.searchEdctTrprList(pmParam);

        /*for (Map<String, Object> resultMap : nList) {
            if (resultMap.get("edct_atnc_yn").equals("Y")) {
                resultMap.put("edct_atnc_yn", true);
            } else if (resultMap.get("edct_atnc_yn").equals("N")) {
                resultMap.put("edct_atnc_yn", false);
            }
        }*/
        return nList;
    }

    /**
     * 교육평가대상자를 검색한다.
     *
     * @pmParam id 교육평가대상자 ID
     * @return 교육평가대상자 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getEduUser(String id) throws Exception {
        Map<String, String> pmParam = new HashMap<String, String>();
        pmParam.put("EduUserid", id);

        return edctTrprDAO.getEduUser(pmParam);
    }

    /**
     * 상담사 정보의 검색 수를 반환한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 상담사 정보의 검색 수
     * @throws Exception
     */
    public int getUserCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) edctTrprDAO.getUserCount(pmParam);
    }

    /**
     * 상담사 정보(목록)를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 상담사 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> searchUserList(Map<String, ?> pmParam) throws Exception {
        return edctTrprDAO.searchUserList(pmParam);
    }

    /**
     * 교육대상자 정보를 등록한다.
     *
     * @pmParam pmParam 교육대상자 정보
     * @throws Exception
     */
    public void insertEdctTrpr(DataSetMap dmParam) throws Exception {
        for(int i =0; dmParam.size() > i; i++){
            Map<String, Object> pmParam = dmParam.get(i);

            if (!"1".equals(pmParam.get("rowCheck")))
                continue;

            // 업체구분
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            pmParam.put("bzpt_div", oLoginUser.getBzptdiv());
            ParamUtils.addSaveParam(pmParam);

            edctTrprDAO.insertEdctTrpr(pmParam);
        }

    }

    /**
     * 교육대상자 정보를 삭제한다.
     *
     * @pmParam pmParam 교육대상자 정보
     * @throws Exception
     */
    public int deleteEdctTrpr(Map<String, Object> pmParam) throws Exception {
        return edctTrprDAO.deleteEdctTrpr(pmParam);
    }

    /**
     * 교육대상자 정보를 수정한다.
     *
     * @pmParam pmParam 교육 정보
     * @throws Exception
     */
    public int updateEdctTrpr(Map<String, Object> pmParam) throws Exception {
        return edctTrprDAO.updateEdctTrpr(pmParam);
    }

    /**
     * 교육대상자의 평가점수, 참여여부를 수정한다.(평가)
     *
     * @pmParam pmParam 교육평가 정보
     * @throws Exception
     */
    public void saveEdctTrpr(Map<String, Object> pmParam) throws Exception {

        edctTrprDAO.updateEdctTrpr(pmParam);

    }
    /*
    public int saveEdctTrpr(List<Map<String, Object>> models, Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        for (Map<String, Object> model : models) {

            String oEdct_atnc_hr = (String) model.get("edct_atnc_hr");

            if(oEdct_atnc_hr != null) {
                int strLength = oEdct_atnc_hr.getBytes().length;
                if(strLength == 4) {
                    model.put("edct_atnc_hr", oEdct_atnc_hr);
                }else {
                    SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    oDateFormat.setTimeZone(TimeZone.getTimeZone("KR"));

                    Date edct_atnc_hr = oDateFormat.parse(oEdct_atnc_hr);
                    oDateFormat = new SimpleDateFormat("HHmm");
                    model.put("edct_atnc_hr", oDateFormat.format(edct_atnc_hr));
                }
            }
            model.putAll(pmParam);
            nResult += edctTrprDAO.updateEdctTrpr(model);
        }
        return nResult;
    }
    */
    /**
     * 고육평가 정보를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 교육평가대상자 목록
     * @throws Exception
     */
    public List<Map<String, Object>> searchEdctTrprCardList(Map<String, ?> pmParam) throws Exception {
        return edctTrprDAO.searchEdctTrprCardList(pmParam);
    }

    /**
     * 월간 교육훈련 결과(상담사별) 정보의 검색 수를 반환한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 월간교육훈련 결과 정보의 검색 건수
     * @throws Exception
     */
    public int getedctTrprResultCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) edctTrprDAO.edctTrprResultCount(pmParam);
    }

    /**
     * 월간 교육훈련 결과(상담사별) 정보를 검색 한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 월간교육훈련 결과 정보
     * @throws Exception
     */
    public List<Map<String, Object>> searchEdctTrprResult(Map<String, ?> pmParam) throws Exception {
        return edctTrprDAO.edctTrprResult(pmParam);
    }
}
