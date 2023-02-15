/*
 * (@)# FaqDmndServiceImpl.java
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

package powerservice.business.kms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.kms.service.FaqDmndService;
import powerservice.business.sys.service.impl.FileDAO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * FAQ 요청 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/13
 * @프로그램ID FaqDmnd
 */

@Service
public class FaqDmndServiceImpl extends EgovAbstractServiceImpl implements FaqDmndService {

    @Resource
    public FaqDmndDAO faqDmndDAO;

    @Resource
    public FaqDtlDAO faqDtlDAO;

    @Resource
    public FileDAO fileDAO;

    @Resource
    public FaqHstrDAO faqHstrDAO;

    /**
     * FAQ 요청 정보를 등록한다.
     *
     * @param pmParam FAQ 요청 정보
     * @throws Exception
     *
     */
    public String insertFaqDmnd(Map<String, Object> pmParam) throws Exception {
        String sKey = "";

        int nResult = faqDmndDAO.insertFaqDmnd(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("faq_dmnd_id");

            // 첨부파일 업데이트
            //updateFile(pmParam);
        }
        return sKey;
    }

    /**
     * FAQ 요청 정보를 수정한다.
     *
     * @param pmParam FAQ 요청 정보
     * @throws Exception
     */
    public int updateFaqDmnd(Map<String, Object> pmParam) throws Exception {
        int nResult = faqDmndDAO.updateFaqDmnd(pmParam);

        // 첨부파일 업데이트
        //updateFile(pmParam);

        // 관리자가 승인한경우에는 FAQ 테이블로 COPY
        String sPathType = (String) pmParam.get("pathType");
        String sFaq_dsps_stat_cd = (String) pmParam.get("faq_dsps_stat_cd");

        if ("admin".equals(sPathType) && "40".equals(sFaq_dsps_stat_cd)) {
            int nFaqResult = faqDtlDAO.insertFaqFromFaqDmnd(pmParam);

            if (nFaqResult > 0) {
                // 첨부파일 COPY
                String sFaq_id = (String) pmParam.get("faq_id");
                String sFaq_dmnd_id = (String) pmParam.get("faq_dmnd_id");

                pmParam.put("rltn_item_id", sFaq_dmnd_id);
                pmParam.put("new_rltn_item_id", sFaq_id);
                pmParam.put("rltn_tbl_nm", "TB_FAQ_DTL");
                pmParam.put("atch_typ_cd", "FAQ");

                fileDAO.copyFile(pmParam);

                // History 등록
                pmParam.put("faq_chng_cntn", "신규등록");
                faqHstrDAO.insertFaqHstr(pmParam);
            }
        }

        return nResult;
    }

    /**
     * 첨부파일에 FAQ요청 아이디를 업데이트 한다.
     *
     * @param pmParam FAQ 요청 정보
     * @throws Exception
     */
    public int updateFile(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        // 관계항목ID 저장
        pmParam.put("rltn_item_id", pmParam.get("faq_dmnd_id"));

        // 첨부파일 리스트 가져오기
        @SuppressWarnings("unchecked")
        List<String> sFileIdList = (ArrayList<String>) pmParam.get("fileIds");
        if (sFileIdList == null) {
            sFileIdList = new ArrayList<String>();
        }

        // 첨부파일 리스트에 에디터 이미지 파일ID 추가
        // 첨부파일 리스트에 포함되지 않은 이미지 파일 삭제 처리
        String sFaqAnsrHtmlCntn = (String) pmParam.get("faq_ansr_html_cntn");
        if (sFaqAnsrHtmlCntn != null) {
            // 에디터 내용에서 이미지 파일ID 추출
            Pattern rPattern = Pattern.compile("file_id=[A-Z0-9]+\"");
            Matcher oMacher = rPattern.matcher(sFaqAnsrHtmlCntn);
            while (oMacher.find()) {
                sFileIdList.add(oMacher.group().replace("file_id=", "").replace("\"", ""));
            }
        }

        // 파일 삭제
        fileDAO.deleteFile(pmParam);

        // 관계항목ID 수정
        if (sFileIdList != null && sFileIdList.size() > 0) {
            pmParam.put("fileIds", sFileIdList);
            nResult += fileDAO.updateFile(pmParam);
        }

        return nResult;
    }

    /**
     * FAQ 요청 정보 상태를 업데이트한다.
     *
     * @param pmParam FAQ 요청 정보
     * @throws Exception
     */
    public int updateFaqDmndStat(Map<String, ?> pmParam) throws Exception {
        return faqDmndDAO.updateFaqDmndStat(pmParam);
    }

    /**
     * FAQ 요청 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return FAQ 요청 정보 검색 수
     * @throws Exception
     */
    public int getFaqDmndCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) faqDmndDAO.getFaqDmndCount(pmParam);
    }

    /**
     * FAQ 요청 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return FAQ 요청 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getFaqDmndList(Map<String, Object> pmParam) throws Exception {
        return faqDmndDAO.getFaqDmndList(pmParam);
    }

    /**
     * FAQ 요청 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return FAQ 요청 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getFaqDmnd(Map<String, ?> pmParam) throws Exception {
        return faqDmndDAO.getFaqDmnd(pmParam);
    }

    /**
     * FAQ 요청 정보를 삭제한다.
     *
     * @param FAQ 요청 ID
     * @throws Exception
     */
    public int deleteFaqDmnd(String id) throws Exception {
        int nResult = faqDmndDAO.deleteFaqDmnd(id);

        // 파일 삭제
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("rltn_item_id", id);

        fileDAO.deleteFile(mParam);

        return nResult;
    }

}
