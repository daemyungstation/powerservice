/*
 * (@)# NobdService.java
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
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.kms.service.NobdService;
import powerservice.business.sys.service.impl.FileDAO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 게시판 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Nobd
 */
@Service
public class NobdServiceImpl extends EgovAbstractServiceImpl implements NobdService {

    @Resource
    public NobdDAO nobdDAO;

    @Resource
    public FileDAO fileDAO;

    /**
     * 게시판 정보를 등록한다.
     *
     * @param pmParam 게시판 정보
     * @throws Exception
     */
    public String insertNobd(Map<String, Object> pmParam) throws Exception {
        String skey = "";

        int nResult = nobdDAO.insertNobd(pmParam);
        if (nResult > 0) {
            skey = (String) pmParam.get("nobd_id");

            // 첨부파일 업데이트
            updateFile(pmParam);
        }

        return skey;
    }

    /**
     * 게시판 정보를 수정한다.
     *
     * @param pmParam 게시판 정보
     * @throws Exception
     */
    public int updateNobd(Map<String, Object> pmParam) throws Exception {
        int nResult = nobdDAO.updateNobd(pmParam);
        if (nResult > 0) {
            // 첨부파일 업데이트
            updateFile(pmParam);
        }
        return nResult;
    }

    /**
     * 첨부파일에 게시판 아이디를 업데이트 한다.
     *
     * @param pmParam FAQ 요청 정보
     * @throws Exception
     */
    private void updateFile(Map<String, Object> pmParam) throws Exception {
        String sNobdFileFg = (String) pmParam.get("nobd_typ_file_flag");

        // 첨부파일을 사용하는 게시판인 경우에만 첨부파일 정보 업데이트
        if ("Y".equals(sNobdFileFg)) {
            // 관계항목ID 저장
            pmParam.put("rltn_item_id", pmParam.get("nobd_id"));

            // 첨부파일 리스트 가져오기
            @SuppressWarnings("unchecked")
            List<String> sFileIdList = (ArrayList<String>) pmParam.get("fileIds");
            if (sFileIdList == null) {
                sFileIdList = new ArrayList<String>();
            }

            // 첨부파일 리스트에 에디터 이미지 파일ID 추가
            // 첨부파일 리스트에 포함되지 않은 이미지 파일 삭제 처리
            String sNobdHtmlCntn = (String) pmParam.get("nobd_html_cntn");
            if (sNobdHtmlCntn != null) {
                // 에디터 내용에서 이미지 파일ID 추출
                Pattern rPattern = Pattern.compile("file_id=[A-Z0-9]+\"");
                Matcher oMacher = rPattern.matcher(sNobdHtmlCntn);
                while (oMacher.find()) {
                    sFileIdList.add(oMacher.group().replace("file_id=", "").replace("\"", ""));
                }
            }

            // 파일 삭제
            fileDAO.deleteFile(pmParam);

            // 관계항목ID 수정
            if (sFileIdList != null && sFileIdList.size() > 0) {
                pmParam.put("fileIds", sFileIdList);
                fileDAO.updateFile(pmParam);
            }
        }
    }

    /**
     * 게시판 정보를 삭제한다.
     *
     * @param pmParam 게시판 정보
     * @throws Exception
     */
    public int deleteNobd(Map<String, ?> pmParam) throws Exception {
        // 게시물 삭제
        int nResult = nobdDAO.deleteNobd(pmParam);

        // 첨부파일 삭제
        fileDAO.deleteFile(pmParam);

        return nResult;
    }

    /**
     * 게시판 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 게시판 정보의 검색 수
     * @throws Exception
     */
    public int getNobdCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) nobdDAO.getNobdCount(pmParam);
    }

    /**
     * 게시판 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 게시판 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNobdList(Map<String, ?> pmParam) throws Exception {
        return nobdDAO.getNobdList(pmParam);
    }

    /**
     * 게시판 정보(1건)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 게시판 리스트 (1건)
     * @throws Exception
     */
    public Map<String, Object> getNobd(Map<String, ?> pmParam) throws Exception {
        return nobdDAO.getNobd(pmParam);
    }

}
