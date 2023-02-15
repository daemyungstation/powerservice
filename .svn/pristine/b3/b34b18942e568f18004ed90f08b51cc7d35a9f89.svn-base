/*
 * (@)# AncmMtrDtlService.java
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

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.kms.service.AncmMtrDtlService;
import powerservice.business.sys.service.impl.FileDAO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 공지사항 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID AncmMtrDtl
 */
@Service
public class AncmMtrDtlServiceImpl extends EgovAbstractServiceImpl implements AncmMtrDtlService {

    @Resource
    public AncmMtrDtlDAO ancmMtrDtlDAO;

    @Resource
    public AncmTrgtAthrDAO ancmTrgtAthrDAO;

    @Resource
    public AncmTrgtTeamDAO ancmTrgtTeamDAO;

    @Resource
    public FileDAO fileDAO;

    /**
     *  공지사항 정보를 입력한다.
     *
     * @param pmParam 공지사항 정보
     * @throws Exception
     */
    public String insertAncmMtrDtl(Map<String, Object> pmParam) throws Exception {
        String sKey = "";

        int nResult = ancmMtrDtlDAO.insertAncmMtrDtl(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("ancm_mtr_id");
            pmParam.put("ancm_mtr_id", sKey);
            // 권한정보 저장
            insertAncmTrgtAthr(pmParam);

            // 상담그룹정보 저장
            insertAncmTrgtTeam(pmParam);
        }

        return sKey;
    }

    /**
     *  공지사항 정보를 수정한다.
     *
     * @param pmParam 공지사항 정보
     * @throws Exception
     */
    public int updateAncmMtrDtl(Map<String, Object> pmParam) throws Exception {
        int nResult = ancmMtrDtlDAO.updateAncmMtrDtl(pmParam);
        if (nResult > 0) {
            // 권한정보 저장
            insertAncmTrgtAthr(pmParam);

            // 상담그룹정보 저장
            insertAncmTrgtTeam(pmParam);

        }
        return nResult;
    }

    /**
     * 첨부파일에 공지사항 아이디를 업데이트 한다.
     *
     * @param pmParam 공지사항 정보
     * @throws Exception
     */
    private void updateFile(Map<String, Object> pmParam) throws Exception {
        // 관계항목ID 저장
        pmParam.put("rltn_item_id", pmParam.get("ancm_mtr_id"));

        // 첨부파일 리스트 가져오기
        @SuppressWarnings("unchecked")
        List<String> sFileIdList = (ArrayList<String>) pmParam.get("fileIds");
        if (sFileIdList == null) {
            sFileIdList = new ArrayList<String>();
        }

        // 첨부파일 리스트에 에디터 이미지 파일ID 추가
        // 첨부파일 리스트에 포함되지 않은 이미지 파일 삭제 처리
        String sAncmMtrHtmlCntn = (String) pmParam.get("ancm_mtr_html_cntn");
        if (sAncmMtrHtmlCntn != null) {
            // 에디터 내용에서 이미지 파일ID 추출
            Pattern rPattern = Pattern.compile("file_id=[A-Z0-9]+\"");
            Matcher oMacher = rPattern.matcher(sAncmMtrHtmlCntn);
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

    /**
     * 공지사항 조회수를 업데이트한다.
     *
     * @param 공지사항 ID
     * @throws Exception
     */
    public int updateAncmMtrDtlViewCnt(String psAncmMtrId) throws Exception {
        return ancmMtrDtlDAO.updateAncmMtrDtlViewCnt(psAncmMtrId);
    }

    /**
     * 공지사항 권한정보를 저장한다.
     *
     * @param pmParam 공지사항 정보
     * @throws Exception
     */
    private void insertAncmTrgtAthr(Map<String, ?> pmParam) throws Exception {
        ancmTrgtAthrDAO.deleteAncmTrgtAthr(pmParam);

        @SuppressWarnings("unchecked")
        String[] sAthrList = StringUtils.defaultString((String) pmParam.get("athr_list")).split(",");

        HashMap<String, String> mParam = new HashMap<String, String>();
        mParam.put("ancm_mtr_id", (String) pmParam.get("ancm_mtr_id"));
        mParam.put("cntr_cd", (String) pmParam.get("cntr_cd"));

        for (int i = 0; sAthrList.length > i; i++ ) {
            mParam.put("athr_cd", sAthrList[i]);
            ancmTrgtAthrDAO.insertAncmTrgtAthr(mParam);
        }
    }

    /**
     * 공지사항 센터정보를 저장한다.
     *
     * @param pmParam 공지사항 정보
     * @throws Exception
     */
    private void insertAncmTrgtTeam(Map<String, ?> pmParam) throws Exception {
        ancmTrgtTeamDAO.deleteAncmTrgtTeam(pmParam);

        @SuppressWarnings("unchecked")
        String[] sTeamList = StringUtils.defaultString((String) pmParam.get("teams")).split(",");

        HashMap<String, String> mParam = new HashMap<String, String>();
        mParam.put("ancm_mtr_id", (String) pmParam.get("ancm_mtr_id"));
        mParam.put("cntr_cd", (String) pmParam.get("cntr_cd"));

        for (int i = 0; sTeamList.length > i; i++ ) {
            mParam.put("team_cd", sTeamList[i]);
            ancmTrgtTeamDAO.insertAncmTrgtTeam(mParam);
        }
    }

    /**
     * 공지사항 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 공지사항 정보의 검색 수
     * @throws Exception
     */
    public int getAncmMtrDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) ancmMtrDtlDAO.getAncmMtrDtlCount(pmParam);
    }

    /**
     * 공지사항 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공지사항 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getAncmMtrDtlList(Map<String, ?> pmParam) throws Exception {
        return ancmMtrDtlDAO.getAncmMtrDtlList(pmParam);
    }

    /**
     * 공지사항 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공지사항 정보
     * @throws Exception
     */
    public Map<String, Object> getAncmMtrDtl(Map<String, ?> pmParam) throws Exception {
        return ancmMtrDtlDAO.getAncmMtrDtl(pmParam);
    }

    /**
     * 공지사항 정보를 삭제한다.(1건)
     *
     * @param pmParam 공지사항 ID
     * @throws Exception
     */
    public int deleteAncmMtrDtl(Map<String, ?> pmParam) throws Exception {
        // 공지사항 삭제
        int nResult = ancmMtrDtlDAO.deleteAncmMtrDtl(pmParam);
        if (nResult > 0) {
            // 공지범위(권한) 삭제
            ancmTrgtAthrDAO.deleteAncmTrgtAthr(pmParam);

            // 공지범위(상담그룹) 삭제
            ancmTrgtTeamDAO.deleteAncmTrgtTeam(pmParam);

            // 첨부파일 삭제
            fileDAO.deleteFile(pmParam);
        }
        return nResult;
    }

}
