/*
 * (@)# ExccConsExmpServiceImpl.java
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

package powerservice.business.cns.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.cns.service.ExccConsExmpService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 상담 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID MoRecpDtl
 */
@Service
public class ExccConsExmpServiceImpl extends EgovAbstractServiceImpl implements ExccConsExmpService {

    @Resource
    public ExccConsExmpDAO exccConsExmpDAO;

    /**
     * 우수상담사례 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 우수상담사례 정보의 검색 수
     * @throws Exception
     */
    public int getExccConsExmpCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) exccConsExmpDAO.getExccConsExmpCount(pmParam);
    }

    /**
     * 우수상담사례 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 우수상담사례 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExccConsExmpList(Map<String, Object> pmParam) throws Exception {
        return exccConsExmpDAO.getExccConsExmpList(pmParam);
    }

    /**
     * 우수상담사례 정보(1건)를 검색한다.
     *
     * @param 우수 상담 ID
     * @return 우수상담사례 정보
     * @throws Exception
     */
    public Map<String, Object> getExccConsExmp(String id) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("excc_cons_id", id);

        return exccConsExmpDAO.getExccConsExmp(mParam);

    }

    /**
     * 우수상담사례 정보를 등록한다.
     *
     * @param pmParam 우수상담사례 정보
     * @throws Exception
     */
    public String insertExccConsExmp(Map<String, Object> pmParam) throws Exception {
        String sKey = "";

        int nResult = exccConsExmpDAO.insertExccConsExmp(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("excc_cons_id");
        }
        return sKey;
    }

    /**
     * 우수상담사례 정보를 수정한다.
     *
     * @param pmParam 우수상담사례 정보
     * @throws Exception
     */
    public int updateExccConsExmp(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        nResult += exccConsExmpDAO.updateExccConsExmp(pmParam);

        return nResult;
    }

    /**
     * 첨부파일에 게시판 아이디를 업데이트 한다.
     *
     * @param pmParam 우수상담사례 정보
     * @throws Exception
     */
    public int updateFile(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        // 관계항목ID 저장
        pmParam.put("rltn_item_id", pmParam.get("excc_cons_id"));

        // 첨부파일 리스트 가져오기
        @SuppressWarnings("unchecked")
        List<String> sFileIdList = (ArrayList<String>) pmParam.get("fileIds");
        if (sFileIdList == null) {
            sFileIdList = new ArrayList<String>();
        }

        // 첨부파일 리스트에 에디터 이미지 파일ID 추가
        // 첨부파일 리스트에 포함되지 않은 이미지 파일 삭제 처리
        String sExccConsHtmlCntn = (String) pmParam.get("excc_cons_html_cntn");
        if (sExccConsHtmlCntn != null) {
            // 에디터 내용에서 이미지 파일ID 추출
            Pattern rPattern = Pattern.compile("file_id=[A-Z0-9]+\"");
            Matcher oMacher = rPattern.matcher(sExccConsHtmlCntn);
            while (oMacher.find()) {
                sFileIdList.add(oMacher.group().replace("file_id=", "").replace("\"", ""));
            }
        }

        // 파일 삭제
        exccConsExmpDAO.deleteFile(pmParam);

        // 관계항목ID 수정
        if (sFileIdList != null && sFileIdList.size() > 0) {
            pmParam.put("fileIds", sFileIdList);
            nResult += exccConsExmpDAO.updateFile(pmParam);
        }

        return nResult;
    }

    /**
     * 우수상담사례 정보를 삭제한다.
     *
     * @param pmParam 우수상담사례 정보
     * @throws Exception
     */
    public int deleteExccConsExmp(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        // 우수상담사례 삭제
        nResult += exccConsExmpDAO.deleteExccConsExmp(pmParam);

        // 첨부파일 삭제
        exccConsExmpDAO.deleteFile(pmParam);
        return nResult;
    }

    /**
     * QA추천콜-우수상담사례 정보를 등록한다.
     *
     * @param pmParam 우수상담사례 정보
     * @throws Exception
     */
    public int insertEvltExccConsExmp(Map<String, Object> pmParam) throws Exception {
        return exccConsExmpDAO.insertEvltExccConsExmp(pmParam);
    }


}
