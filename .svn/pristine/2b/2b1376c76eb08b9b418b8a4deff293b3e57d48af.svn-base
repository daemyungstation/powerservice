/*
 * (@)# BizBasiService.java
 *
 * @author 배윤정
 * @version 1.0
 * @date 2015/07/30
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

package powerservice.business.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.biz.service.BizBasiService;
import powerservice.business.sys.service.impl.SrvrInfoDAO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 사업원장 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 배윤정
 * @version 1.0
 * @date 2015/07/30
 * @프로그램ID BizBasi
 */
@Service
public class BizBasiServiceImpl extends EgovAbstractServiceImpl implements BizBasiService {

    @Resource
    public BizBasiDAO bizBasiDAO;

    @Resource
    public SrvrInfoDAO srvrInfoDAO;

    /**
     * 사업원장 정보를 등록한다.
     *
     * @param pmParam 사업원장 정보
     * @throws Exception
     */
    public String insertBizBasi(Map<String, ?> pmParam) throws Exception {
		String sKey = "";
		int nResult = bizBasiDAO.insertBizBasi(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("biz_id");
            //사업기본이력 저장
            bizBasiDAO.insertBizBasiHstr(pmParam);
        }
        return sKey;
    }

    /**
     * 사업원장 정보를 수정한다.
     *
     * @param pmParam 사업원장 정보
     * @throws Exception
     */
    public int updateBizBasi(Map<String, ?> pmParam) throws Exception {
    	int nResult = bizBasiDAO.updateBizBasi(pmParam);
        if (nResult > 0) {
        	//사업기본이력 저장
        	bizBasiDAO.insertBizBasiHstr(pmParam);
        }
        return nResult;
    }
    
    /**
     * 사업원장의 담당자수, 영업활동건수, 영업이슈건수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 사업원장의 담당자수, 영업활동건수, 영업이슈건수
     * @throws Exception
     */
    public int checkInfoCnt(Map<String, ?> pmParam) throws Exception {
        return (Integer) bizBasiDAO.checkInfoCnt(pmParam);
    }
    
    /**
     * 사업원장 정보를 삭제한다.
     *
     * @param pmParam 사업원장 정보
     * @throws Exception
     */
    public int deleteBizBasi(Map<String, Object> pmParam) throws Exception {
        return bizBasiDAO.deleteBizBasi(pmParam);
    }


    /**
     * 사업원장 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 사업원장 정보의 검색 건수
     * @throws Exception
     */
    public int getBizBasiCount(Map<String, ?> pmParam) throws Exception {
        return bizBasiDAO.getBizBasiCount(pmParam);
    }

    /**
     * 사업원장 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사업원장 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getBizBasiList(Map<String, ?> pmParam) throws Exception {
        return bizBasiDAO.getBizBasiList(pmParam);
    }

    /**
     * 사업원장 정보를 검색한다.
     *
     * @param sId 사업원장ID
     * @param sCntrCd 센터코드
     * @return 사업원장 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getBizBasi(String psId) throws Exception {
    	Map<String, String> pmParam = new HashMap<String, String>();
    	pmParam.put("biz_id", psId);

        return bizBasiDAO.getBizBasi(pmParam);
    }
    
    /**
     * 관심사업 정보를 저장한다.
     *
     * @param pmParam 사업원장 정보
     * @return 
     * @throws Exception
     */
    public void mergeCncrBizDtl(Map<String, ?> pmParam) throws Exception {
        bizBasiDAO.mergeCncrBizDtl(pmParam);
    }
    
    /**
     * 관심사업 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 사업원장 정보의 검색 건수
     * @throws Exception
     */
    public int getCncrBizCount(Map<String, ?> pmParam) throws Exception {
        return bizBasiDAO.getCncrBizCount(pmParam);
    }

    /**
     * 관심사업 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사업원장 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getCncrBizList(Map<String, ?> pmParam) throws Exception {
        return bizBasiDAO.getCncrBizList(pmParam);
    }
    
    /**
     * 관심사업 정보를 삭제한다.
     *
     * @param pmParam 사업 ID
     * @throws Exception
     */
    public void deleteCncrBiz(Map<String, ?> pmParam) throws Exception {
    	bizBasiDAO.deleteCncrBiz(pmParam);
    }

}
