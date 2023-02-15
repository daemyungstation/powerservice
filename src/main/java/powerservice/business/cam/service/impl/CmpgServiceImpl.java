/*
 * (@)# CmpgServiceImpl.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.cam.service.CmpgService;
import powerservice.business.sys.service.impl.FileDAO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 캠페인 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/30
 * @프로그램ID Cmpg
 */

@Service
public class CmpgServiceImpl extends EgovAbstractServiceImpl implements CmpgService {

    @Resource
    public CmpgDAO cmpgDAO;

    @Resource
    public TrgtListDAO trgtListDAO;

    @Resource
    public TrgtCustAlctDAO trgtCustAlctDAO;

    @Resource
    public CmpgAlctDAO cmpgAlctDAO;
    @Resource
    public ExtcMstTrgtDAO extcMstTrgtDAO;

    @Resource
    public FileDAO fileDAO;


    @Resource
    public ScrtDAO scrtDAO;

    /**
     * 캠페인 정보의 검색 수를 반환한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 캠페인 정보의 검색 수
     * @throws Exception
     */
    public int getCmpgCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) cmpgDAO.getCmpgCount(pmParam);
    }

    /**
     * 캠페인 정보를 검색한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 캠페인 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCmpgList(Map<String, ?> pmParam) throws Exception {
        return cmpgDAO.getCmpgList(pmParam);
    }

    /**
     * 캠페인 정보를 등록한다. 사용
     *
     * @param param 캠페인 정보
     * @throws Exception
     */
    public String insertCmpg(Map<String, Object> pmParam) throws Exception {
        String sKey = "";
        Map<String, Object> mParam = new HashMap<String, Object>();
        mParam.put("trgt_list_id", pmParam.get("trgt_list_id"));
        mParam.put("cntr_cd", pmParam.get("cntr_cd"));
        mParam.put("alct_yn", "N");
        int nCnt = (Integer) trgtListDAO.getTrgtListCount(mParam);

        if (nCnt >0) {
            int nResult = cmpgDAO.insertCmpg(pmParam);

            if (nResult > 0) {
                sKey = (String) pmParam.get("cmpg_id");

                // 할당여부 업데이트
                updateCmpgAssigninfo(pmParam);

            }
        } else {
            sKey = "error";
        }

        return sKey;
    }

    /**
     * 캠페인 정보를 수정한다. 사용
     *
     * @param pmParam 캠페인 정보
     * @throws Exception
     */
    public String updateCmpg(Map<String, Object> pmParam) throws Exception {
           Map<String, Object> mParam = new HashMap<String, Object>();
           String oldTrgtListId = (String)pmParam.get("old_trgt_list_id");
           String trgtListChngFg = (String)pmParam.get("trgt_list_chng_fg");

           if(oldTrgtListId != null && trgtListChngFg.equals("Y")){
               mParam.put("trgt_list_id",oldTrgtListId);
               mParam.put("cntr_cd", pmParam.get("cntr_cd"));
               mParam.put("cmpg_id",pmParam.get("cmpg_id"));

               cmpgDAO.deleteCmpgAlctbyCmpgId(mParam); // 캠페인 할당정보 삭제
               cmpgDAO.updateCmpg(pmParam);
               updateCmpgAssigninfo(mParam); //기존꺼 할당변경
               updateCmpgAssigninfo(pmParam); //신규꺼 할당변경

           }else{
               cmpgDAO.updateCmpg(pmParam);
           }

           String sResultMsg = pmParam.get("cmpg_id").toString();

           String sCmpgDivCd = StringUtils.defaultString((String) pmParam.get("cmpg_div_cd"));
           if(sCmpgDivCd.equals("2000") || sCmpgDivCd.equals("3000")){ //미납
               pmParam.put("updateOption","cmpg_update");
               extcMstTrgtDAO.updateUnpy(pmParam);
           }
        return sResultMsg;
    }

    /**
     * 캠페인 정보를 검색한다.
     *
     * @param String 캠페인 ID
     * @return 캠페인 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCmpg(String psId) throws Exception {
        Map<String, Object> mParam = new HashMap<String, Object>();
        mParam.put("cmpg_id", psId);
        return cmpgDAO.getCmpg(mParam);
    }

    /**
     * 사용자별 캠페인 정보를 검색한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 캠페인 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCmpgListByUserId(Map<String, ?> pmParam) throws Exception {
        return cmpgDAO.getCmpgListByUserId(pmParam);
    }

    /**
     * 캠페인 정보를 삭제한다. 사용
     *
     * @param pmParam 캠페인 정보
     * @throws Exception
     */
    public int deleteCmpg(Map<String, Object> pmParam) throws Exception {
        //pmParam.put("cmpg_prgr_stat_cd", "10"); // 대기
        int nCnt = (Integer) cmpgDAO.getCmpgCount(pmParam);; // 캠페인 정보 조회

        if (nCnt == 1) {
            cmpgDAO.deleteCmpg(pmParam); // 캠페인 정보 삭제
            cmpgDAO.deleteCmpgAlctbyCmpgId(pmParam); // 캠페인 할당정보 삭제
            String sCmpgDivCd = StringUtils.defaultString((String) pmParam.get("cmpg_div_cd"));
            if(sCmpgDivCd.equals("2000") || sCmpgDivCd.equals("3000")){ //미납
                pmParam.put("updateOption","cmpg_del");
                extcMstTrgtDAO.updateUnpy(pmParam);
            }
            cmpgDAO.deleteTgtCustAlctByCmpgId(pmParam); // 대상고객할당 삭제

            updateCmpgAssigninfo(pmParam);

        }
        return nCnt;
    }


    /**
     * 캠페인의 설문지/스크립트/대상리스트 할당정보를 수정한다.(ajax) 사용
     *
     * @param pmParam 대상리스트 정보
     * @throws Exception
     */
    public int updateCmpgAssigninfo(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        Map<String, Object> mParam = new HashMap<String, Object>();

        mParam.put("updateOption", "assign");
        mParam.put("cntr_cd", pmParam.get("cntr_cd"));
        mParam.put("amnd_id", pmParam.get("amnd_id"));

        mParam.put("trgt_list_id", pmParam.get("trgt_list_id"));
        nResult += trgtListDAO.updateTrgtList(mParam); // 대상리스트 할당여부 수정

        return nResult;
    }

    /**
     * 첨부파일에 릴레이션 아이디를 업데이트 한다.
     *
     * @param pmParam FAQ 요청 정보
     * @throws Exception
     */
    public int updateFile(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        String sCmpgPranChnlCd = (String) pmParam.get("cmpg_pran_chnl_cd");

        // 첨부파일을 사용하느 게시판인 경우에만 첨부파일 정보 업데이트
        if ("40".equals(sCmpgPranChnlCd)) {
            pmParam.put("rltn_item_id", pmParam.get("cmpg_id"));
            pmParam.put("rltn_tbl_nm", "TB_CMPG_BASI");

            // 파일 삭제
            nResult += fileDAO.deleteFile(pmParam);

            // 첨부파일 업데이트
            @SuppressWarnings("unchecked")
            List<String> fileList = (ArrayList<String>) pmParam.get("fileIds");

            if (fileList != null && fileList.size() > 0) {
                nResult += fileDAO.updateFile(pmParam);
            }
        }

        return nResult;
    }





    /**
     * 캠페인 콤보박스 리스트를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> searchCmpgList(Map<String, ?> pmParam) throws Exception {
        return cmpgDAO.searchCmpgList(pmParam);
    }

}
