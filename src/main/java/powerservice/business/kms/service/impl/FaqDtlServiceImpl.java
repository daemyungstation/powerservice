/*
 * (@)# FaqDtlServiceImpl.java
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
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.kms.service.FaqDtlService;
import powerservice.business.sys.service.impl.FileDAO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * FAQ 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/14
 * @프로그램ID FaqDtl
 */

@Service
public class FaqDtlServiceImpl extends EgovAbstractServiceImpl implements FaqDtlService {

    @Resource
    public FaqDtlDAO faqDtlDAO;

    @Resource
    public FaqHstrDAO faqHstrDAO;

    @Resource
    public FileDAO fileDAO;

    /**
     * FAQ 정보를 등록한다.
     *
     * @param pmParam FAQ 정보
     * @throws Exception
     */
    public String insertFaq(Map<String, Object> pmParam) throws Exception {
        String sKey = "";

        // FAQ 등록
        int nResult = faqDtlDAO.insertFaq(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("faq_id");

            // FAQ 이력 등록
            faqHstrDAO.insertFaqHstr(pmParam);

            // 첨부파일 업데이트
            // updateFile(pmParam);
        }
        return sKey;
    }

    /**
     * FAQ 정보를 수정한다.
     *
     * @param pmParam FAQ 정보
     * @throws Exception
     */
    public int updateFaq(Map<String, Object> pmParam) throws Exception {
        // FAQ 수정
        int nResult = faqDtlDAO.updateFaq(pmParam);

        // FAQ 이력 등록
        faqHstrDAO.insertFaqHstr(pmParam);

        // 첨부파일 업데이트
        // updateFile(pmParam);

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
        pmParam.put("rltn_item_id", pmParam.get("faq_id"));

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
     * FAQ 조회수를 업데이트한다.
     *
     * @param FAQ ID
     * @throws Exception
     */
    public int updateInqCnt(String faq_id) throws Exception {
        return faqDtlDAO.updateInqCnt(faq_id);
    }

    /**
     * FAQ 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return FAQ 정보의 검색 수
     * @throws Exception
     */
    public int getFaqCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) faqDtlDAO.getFaqCount(pmParam);
    }

    /**
     * FAQ 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return FAQ 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getFaqList(Map<String, ?> pmParam) throws Exception {
        return faqDtlDAO.getFaqList(pmParam);
    }

    /**
     * FAQ 정보를 검색한다.
     *
     * @param FAQ ID
     * @return FAQ 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getFaq(Map<String, ?> pmParam) throws Exception {
        return faqDtlDAO.getFaq(pmParam);
    }

    /**
     * FAQ 정보를 삭제한다.
     *
     * @param pmParam FAQ ID
     * @throws Exception
     */
    public void deleteFaq(Map<String, ?> pmParam) throws Exception {
        // FAQ 삭제
        faqDtlDAO.deleteFaq(pmParam);

        // FAQ 이력 삭제
        faqHstrDAO.deleteFaqHstr(pmParam);

        // 첨부파일 삭제
        fileDAO.deleteFile(pmParam);
    }

}
