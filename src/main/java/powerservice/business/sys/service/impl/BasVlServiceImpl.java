/*
 * (@)# BasVlService.java
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

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import powerservice.business.sys.service.BasVlService;
import powerservice.common.util.CommonUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 기준값 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID BasVl
 */
@Service
public class BasVlServiceImpl extends EgovAbstractServiceImpl implements BasVlService {

    @Resource
    public BasVlDAO basVlDAO;

    @Resource
    public SrvrInfoDAO srvrInfoDAO;

    /**
     * 기준값 정보를 등록한다.
     *
     * @param pmParam 기준값 정보
     * @throws Exception
     */
    public String insertBasVl(Map<String, ?> pmParam) throws Exception {
        String sKey = "";
        int nResult = basVlDAO.insertBasVl(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("bas_vl_id");
        }
        return sKey;
    }

    /**
     * 기준값 정보를 수정한다.
     *
     * @param pmParam 기준값 정보
     * @throws Exception
     */
    public void updateBasVl(Map<String, ?> pmParam) throws Exception {
        basVlDAO.updateBasVl(pmParam);
    }

    /**
     * 기준값 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 기준값 정보의 검색 건수
     * @throws Exception
     */
    public int getBasVlCount(Map<String, ?> pmParam) throws Exception {
        return basVlDAO.getBasVlCount(pmParam);
    }

    /**
     * 기준값 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 기준값 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getBasVlList(Map<String, ?> pmParam) throws Exception {
        return basVlDAO.getBasVlList(pmParam);
    }

    /**
     * 기준값 필터 목록에 따라 기준값 정보를 검색한다.
     *
     * @param psItemList 검색 조건
     * @return 기준값 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getBasVlList(String[] psItemList) throws Exception {
        Map<String, Object> mParam = new HashMap<String, Object>();
        mParam.put("srvr_id", getServerId());
        mParam.put("sItemList", psItemList);

        return basVlDAO.getBasVlListByStringArray(mParam);
    }

    /**
     * 기준값 정보를 검색한다.
     *
     * @param sId 기준값ID
     * @param sCntrCd 센터코드
     * @return 기준값 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getBasVl(String psId, String psCntrCd) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("bas_vl_id", psId);
        mParam.put("cntr_cd", psCntrCd);

        return basVlDAO.getBasVl(mParam);
    }

    /**
     * 기준값 정보를 검색한다.
     *
     * @param sId 기준값ID
     * @return 기준값 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getBasVl(String psId) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("bas_vl_id", psId);

        return basVlDAO.getBasVl(mParam);
    }

    /**
     * 기준값명으로 기준값을 조회한다
     *
     * @param 기준값명, 센터코드
     * @return 기준값
     * @throws Exception
     */
    public String getBasVlAsString(String psBasVlNm, String psCntrCd) throws Exception {
        String sResult = "";

        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("srvr_id", getServerId());
        mParam.put("bas_vl_nm_full", psBasVlNm);
        mParam.put("cntr_cd", psCntrCd);
        mParam.put("use_yn", "Y");

        Map<String, Object> mData = basVlDAO.getBasVl(mParam);

        CommonUtils.printLog(mData);

        if (mData != null) {
            sResult = (String) mData.get("bas_vl");
        }
        return sResult;
    }

    /**
     * 기준값명으로 기준값을 조회한다
     *
     * @param 기준값명
     * @return 기준값
     * @throws Exception
     */
    public String getBasVlAsString(String psBasVlNm) throws Exception {
        String sResult = "";

        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("srvr_id", getServerId());
        mParam.put("bas_vl_nm_full", psBasVlNm);
        mParam.put("cntr_cd", "CCA");
        mParam.put("use_yn", "Y");

        Map<String, Object> mData = basVlDAO.getBasVl(mParam);
        if (mData != null) {
            sResult = (String) mData.get("bas_vl");
        }

        System.out.println("IP-[" + mParam.get("srvr_id") + "] BAS_VL_NM-[" + psBasVlNm + "] BAS_VL-[" + sResult + "]");

        return sResult;
    }

    /**
     * 기준값명으로 기준값을 조회한다
     *
     * @param 기준값명
     * @return 기준값
     * @throws Exception
     */
    private String getServerId() throws Exception {
        String sSrvrId = "";

        InetAddress oInetAddress = InetAddress.getLocalHost();

        Map<String, Object> mParam = new HashMap<String, Object>();
        mParam.put("srvr_ip_addr", oInetAddress.getHostAddress());

        Map<String, Object> mData = srvrInfoDAO.getSrvrInfoBySrvrIpAddr(mParam);
        if (mData != null) {
            sSrvrId = (String) mData.get("srvr_id");
        } else {
            sSrvrId = "Z";
        }

        return sSrvrId;
    }

}
