/*
 * (@)# EmilFxfrServiceImpl.java
 *
 * @author 차건호
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

import powerservice.business.kms.service.EmilFxfrService;
import powerservice.business.sys.service.impl.FileDAO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 이메일 템플릿 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/23
 * @프로그램ID EmilFxfr
 */

@Service
public class EmilFxfrServiceImpl extends EgovAbstractServiceImpl implements EmilFxfrService {

    @Resource
    public EmilFxfrDAO emilFxfrDAO;

    @Resource
    public FileDAO fileDAO;

    /**
     * 이메일템플릿을 입력한다.
     *
     * @param pmParam 이메일템플릿 정보
     * @throws Exception
     */
    public String insertEmilFxfr(Map<String, Object> pmParam) throws Exception {
        String sKey = "";

        int nResult = emilFxfrDAO.insertEmilFxfr(pmParam);

       if (nResult > 0) {
            sKey = (String) pmParam.get("emil_fxfr_id");
        }
        return sKey;
    }

    /**
     * 이메일템플릿을 수정한다.
     *
     * @param pmParam 이메일템플릿 정보
     * @throws Exception
     */
    public int updateEmilFxfr(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        nResult += emilFxfrDAO.updateEmilFxfr(pmParam);

        return nResult;
    }

    /**
     * 첨부파일에 요청 아이디를 업데이트 한다.
     *
     * @param pmParam 요청 정보
     * @throws Exception
     */
    public int updateFile(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        // 관계항목ID 저장
        pmParam.put("rltn_item_id", pmParam.get("emil_fxfr_id"));

        // 첨부파일 리스트 가져오기
        @SuppressWarnings("unchecked")
        List<String> sFileIdList = (ArrayList<String>) pmParam.get("fileIds");
        if (sFileIdList == null) {
            sFileIdList = new ArrayList<String>();
        }

        // 첨부파일 리스트에 에디터 이미지 파일ID 추가
        // 첨부파일 리스트에 포함되지 않은 이미지 파일 삭제 처리
        String sEmilHtmlCntn = (String) pmParam.get("emil_html_cntn");
        if (sEmilHtmlCntn != null) {
            // 에디터 내용에서 이미지 파일ID 추출
            Pattern rPattern = Pattern.compile("file_id=[A-Z0-9]+\"");
            Matcher oMacher = rPattern.matcher(sEmilHtmlCntn);
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
     * 이메일템플릿의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 공지사항 정보의 검색 수
     * @throws Exception
     */
    public int getEmilFxfrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) emilFxfrDAO.getEmilFxfrCount(pmParam);
    }

    /**
     * 이메일템플릿을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공지사항 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEmilFxfrList(Map<String, ?> pmParam) throws Exception {
        return emilFxfrDAO.getEmilFxfrList(pmParam);
    }

    /**
     * 이메일템플릿을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공지사항 정보
     * @throws Exception
     */
    public Map<String, Object> getEmilFxfr(Map<String, ?> pmParam) throws Exception {
        return emilFxfrDAO.getEmilFxfr(pmParam);
    }

    /**
     * 이메일템플릿을 삭제한다.
     *
     * @param pmParam FAQ 정보
     * @throws Exception
     */
    public int deleteEmilFxfr(Map<String, ?> pmParam) throws Exception {
        int nResult = 0;
        // 공지사항 삭제
        nResult += emilFxfrDAO.deleteEmilFxfr(pmParam);

        // 첨부파일 삭제
        Map<String, Object> mParam = new HashMap<String, Object>();
        mParam.put("rltn_tbl_nm", "TB_EMIL_FXFR_ADMN");
        mParam.put("rltn_item_id", pmParam.get("emil_fxfr_id"));
        mParam.put("selectcheck", pmParam.get("selectcheck"));
        fileDAO.deleteFile(mParam);

        return nResult;
    }

    /**
     * 이메일템플릿 발신구분 체크
     *
     * @param pmParam 검색 조건
     * @return 공지사항 정보의 검색 수
     * @throws Exception
     */
    public int getEmilFxfrDpmsDivCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) emilFxfrDAO.getEmilFxfrDpmsDivCount(pmParam);
    }
}
