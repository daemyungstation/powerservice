/*
 * (@)# TrgtListServiceImpl.java
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

package powerservice.business.cam.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.cam.service.TrgtListService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 캠페인 대상리스트 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/29
 * @프로그램ID TrgtList
 */

@Service
public class TrgtListServiceImpl extends EgovAbstractServiceImpl implements TrgtListService {

    @Resource
    public TrgtListDAO trgtListDAO;

    @Resource
    public TrgtCustDAO trgtCustDAO;

    @Resource
    public DfctDataDAO dfctDataDAO;

    @Resource
    public SubTrgtListDAO subTrgtListDAO;

    @Resource
    public ExtcMstTrgtDAO extcMstTrgtDAO;




    /**
     * 대상리스트 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 대상리스트 정보의 검색 수
     * @throws Exception
     */
    public int getTrgtListCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)trgtListDAO.getTrgtListCount(pmParam);
    }

    /**
     * 대상리스트 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상리스트 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTrgtListList(Map<String, ?> pmParam) throws Exception {
        return trgtListDAO.getTrgtListList(pmParam);
    }

    /**
     * 대상리스트 정보를 등록한다.
     *
     * @param pmParam 대상리스트 정보
     * @throws Exception
     */
    public String insertTrgtList(Map<String, ?> pmParam) throws Exception {
        String sKey = "";

        int nResult = trgtListDAO.insertTrgtList(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("trgt_list_id");
        }
        return sKey;
    }

    /**
     * 대상리스트 정보(1건)를 검색한다.
     *
     * @param String 대상리스트 ID
     * @return 대상리스트 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getTrgtList(String psId) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("trgt_list_id", psId);
        return trgtListDAO.getTrgtList(mParam);
    }

    /**
     * 대상리스트 정보를 수정한다.
     *
     * @param pmParam 대상리스트 정보
     * @throws Exception
     */
    public int updateTrgtList(Map<String, Object> pmParam) throws Exception {

        int nCnt = trgtListDAO.updateTrgtList(pmParam);

        if(nCnt> 0 ){
            String sCmpgDivCd = StringUtils.defaultString((String)pmParam.get("hgrn_hrch_cd"));
            String sCmpgTypCd = StringUtils.defaultString((String)pmParam.get("cmpg_typ_cd"));
            if(sCmpgDivCd.equals("2000") || sCmpgDivCd.equals("3000")){ //미납
                if(sCmpgTypCd.equals("2100")||sCmpgTypCd.equals("2200")||sCmpgTypCd.equals("2300")||sCmpgTypCd.equals("2400")
                        ||sCmpgTypCd.equals("3100")||sCmpgTypCd.equals("3200")||sCmpgTypCd.equals("3400")){
                    pmParam.put("updateOption", "trgt_update");
                    extcMstTrgtDAO.updateUnpy(pmParam);
                }
            }
        }
        return nCnt;
    }

    /**
     * 대상리스트 정보를 삭제한다. >>
     *
     * @param pmParam 대상리스트 정보
     * @throws Exception
     */
    public int deleteTrgtList(Map<String, Object> pmParam) throws Exception {
        Map<String, Object> mParam = new HashMap<String, Object>();
        mParam.put("trgt_list_id", pmParam.get("trgt_list_id"));
        mParam.put("cntr_cd", pmParam.get("cntr_cd"));
        mParam.put("alct_yn","N");
        String sCmpgDivCd = StringUtils.defaultString((String)pmParam.get("hgrn_hrch_cd"));
        String sCmpgTypCd = StringUtils.defaultString((String)pmParam.get("cmpg_typ_cd"));

        int nCnt = (Integer)trgtListDAO.getTrgtListCount(mParam);

        if(nCnt >= 1){
            subTrgtListDAO.deleteSubTrgtList(pmParam); //서브타겟리스트 삭제
            dfctDataDAO.deleteDfctDataByTrgtList(pmParam); // ERRORDATA 삭제
            trgtListDAO.deleteTrgtList(pmParam); // 대상리스트 삭제
            if(sCmpgDivCd.equals("2000") || sCmpgDivCd.equals("3000")){ //미납
                if(sCmpgTypCd.equals("2100")||sCmpgTypCd.equals("2200")||sCmpgTypCd.equals("2300")||sCmpgTypCd.equals("2400")
                        ||sCmpgTypCd.equals("3100")||sCmpgTypCd.equals("3200")||sCmpgTypCd.equals("3400")){
                    pmParam.put("updateOption", "trgt_del");
                    extcMstTrgtDAO.updateUnpy(pmParam);
                }
            }
            subTrgtListDAO.deleteTrgtCustByTrgtList(pmParam);//대상고객삭제
        }
        return nCnt;
    }

}


