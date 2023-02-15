/*
 * (@)# NmsgDtlService.java
 *
 * @author 김은지
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.sys.service.NmsgDtlService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 서버 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 김은지
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID NmsgDtl
 */
@Service
public class NmsgDtlServiceImpl extends EgovAbstractServiceImpl implements NmsgDtlService {

    @Resource
    public NmsgDtlDAO nmsgDtlDAO;

    @Resource
    public FileDAO fileDAO;

    /**
     * 쪽지 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 쪽지 정보의 검색 건수
     * @throws Exception
     */
    public int getNmsgDtlCount(Map<String, ?> pmParam) throws Exception {
        return nmsgDtlDAO.getNmsgDtlCount(pmParam);
    }

    /**
     * 쪽지 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 쪽지 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNmsgDtlList(Map<String, ?> pmParam) throws Exception {
        return nmsgDtlDAO.getNmsgDtlList(pmParam);
    }

    /**
     * 쪽지 정보를 등록한다.
     *
     * @param pmParam 쪽지정보
     * @throws Exception
     */
    public String insertNmsgDtl(Map<String, Object> pmParam) throws Exception {
        String sKey = "";
        String sNmsgGropId = "";
        String sNmsgIds = "";

        String sRecnIds = (String) pmParam.get("recn_ids");
        String[] sRecnId = sRecnIds.split(",");

        // 발신쪽지 등록
        pmParam.put("nmsg_typ_cd", "20"); // 쪽지 유형 코드
        pmParam.put("recn_id", sRecnId[0]); // 수신자ID

        int nResult = nmsgDtlDAO.insertNmsgDtl(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("nmsg_id");
            sNmsgGropId = (String) pmParam.get("nmsg_id");
            sNmsgIds = sKey;
        }


        // 수신쪽지 등록
        for (int i = 0; i < sRecnId.length ; i++) {
            String sId = sRecnId[i];

            pmParam.put("nmsg_grop_id", sNmsgGropId);
            pmParam.put("nmsg_typ_cd", "10");
            pmParam.put("recn_id", sId);

            nResult = nmsgDtlDAO.insertNmsgDtl(pmParam);

            if (nResult > 0) {
                sKey = (String) pmParam.get("nmsg_id");
                sNmsgIds = sNmsgIds + "," + sKey;
            }
        }

        // 첨부파일 업데이트
        updateFile(pmParam, sNmsgIds);

        return sNmsgGropId;
    }

    /**
     * 첨부파일에 쪽지 아이디를 업데이트 한다.
     *
     * @param pmParam 쪽지 정보
     * @throws Exception
     */
    private void updateFile(Map<String, Object> pmParam, String psNmsgIds) throws Exception {
        String[] sNmsgId = psNmsgIds.split(",");

        pmParam.put("rltn_item_id", sNmsgId[0]);

        // 첨부파일 업데이트
        @SuppressWarnings("unchecked")
        List<String> sdatafileIdsList = (ArrayList<String>) pmParam.get("fileIds");

        if (sdatafileIdsList != null && sdatafileIdsList.size() > 0) {
            HashMap<String, Object> mParam = new HashMap<String, Object>();

            mParam.put("file_id", sdatafileIdsList.get(0));
            Map<String, Object> mFile = fileDAO.getFile(mParam);

            if (mFile.get("rltn_item_id") == null || "".equals(mFile.get("rltn_item_id"))) {
                fileDAO.updateFile(pmParam);

                for (int i=1; i<sNmsgId.length; i++) {
                    pmParam.put("rltn_item_id", sNmsgId[0]);
                    pmParam.put("new_rltn_item_id", sNmsgId[i]);
                    pmParam.put("atch_typ_cd", "10");

                    fileDAO.copyFile(pmParam);
                }
            } else {
                for (int i=0; i<sNmsgId.length; i++) {
                    pmParam.put("rltn_item_id", mFile.get("rltn_item_id"));
                    pmParam.put("new_rltn_item_id", sNmsgId[i]);
                    pmParam.put("atch_typ_cd", "10");

                    fileDAO.copyFile(pmParam);
                }
            }
        }
    }

    /**
     * 쪽지 정보를 삭제한다.
     *
     * @param pmParam 쪽지 정보
     * @throws Exception
     */
    public int deleteNmsgDtl(Map<String, ?> pmParam) throws Exception {
        // 쪽지 삭제
        int nResult = nmsgDtlDAO.deleteNmsgDtl(pmParam);
        // 첨부파일 삭제
        if (nResult > 0) {
            fileDAO.deleteFile(pmParam);
        }
        return nResult;
    }

    /**
     * 쪽지 정보를 보관한다.
     *
     * @param pmParam 쪽지 정보
     * @throws Exception
     */
    public int updateNmsgDtlKpngYn(Map<String, ?> pmParam) throws Exception {
        return nmsgDtlDAO.updateNmsgDtlKpngYn(pmParam);
    }

    /**
     * 쪽지 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 쪽지 정보
     * @throws Exception
     */
    public Map<String, Object> getNmsgDtl(Map<String, ?> pmParam) throws Exception {
        return nmsgDtlDAO.getNmsgDtl(pmParam);
    }

    /**
    * 쪽지 받은 날짜, 시간을 저장한다.
    *
    * @param pmParam 쪽지 정보
    * @throws Exception
    */
   public int updateNmsgRecpDttm(Map<String, ?> pmParam) throws Exception {
       return nmsgDtlDAO.updateNmsgRecpDttm(pmParam);
   }

   /**
    * 쪽지 정보를 검색한다.
    *
    * @param sId 쪽지 ID
    * @return 쪽지 정보(1건)
    * @throws Exception
    */
   public Map<String, Object> getNmsgDtl(String sId) throws Exception {
       Map<String, String> mParam = new HashMap<String, String>();
       mParam.put("nmsg_id", sId);
       return nmsgDtlDAO.getNmsgDtl(mParam);
   }

   /**
    * 쪽지 받은 날짜, 시간을 저장한다.
    *
    * @param pmParam 쪽지 정보
    * @throws Exception
    */
   public int updateNmsgImpnYn(Map<String, ?> pmParam) throws Exception {
       return nmsgDtlDAO.updateNmsgImpnYn(pmParam);
   }

   /**
    * 쪽지 수신 정보를 검색한다.
    *
    * @param pmParam 검색 조건
    * @return 쪽지 목록
    * @throws Exception
    */
   public List<Map<String, Object>> getRecnList(Map<String, ?> pmParam) throws Exception {
       return nmsgDtlDAO.getRecnList(pmParam);
   }

   /**
    * 전광판에서 사용할 새 쪽지 카운트를 가져온다.
    *
    * @return 쪽지 리스트
    * @throws Exception
    */
   public List<Map<String, Object>> getNewNmsgDtlCount() throws Exception {
       return nmsgDtlDAO.getNewNmsgDtlCount();
   }

}
