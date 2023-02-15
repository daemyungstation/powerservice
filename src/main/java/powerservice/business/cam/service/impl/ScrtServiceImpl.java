/*
 * (@)# ScrtServiceImpl.java
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.cam.service.ScrtService;
import powerservice.business.sys.service.impl.FileDAO;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 스크립트 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/28
 * @프로그램ID Scrt
 */

@Service
public class ScrtServiceImpl extends EgovAbstractServiceImpl implements ScrtService {

    @Resource
    public ScrtDAO scrtDAO;

    @Resource
    public FileDAO fileDAO;

    /**
     * 스크립트 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 스크립트 정보의 검색 수
     * @throws Exception
     */
    public int getScrtCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)scrtDAO.getScrtCount(pmParam);
    }

    /**
     * 스크립트 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 스크립트 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getScrtList(Map<String, ?> pmParam) throws Exception {
        List<Map<String, Object>> mScrtList = scrtDAO.getScrtList(pmParam);

        return mScrtList;
    }

    /**
     * 스크립트 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 스크립트 리스트
     * @throws Exception
     */
    public Map<String, Object> getScrtView(Map<String, ?> pmParam) throws Exception {
        return scrtDAO.getScrtView(pmParam);
    }

    /**
     * 스크립트 정보를 등록한다.
     *
     * @param pmParam 스크립트 정보
     * @throws Exception
     */
    public String insertScrt(Map<String, Object> pmParam) throws Exception {
        String sKey = "";

        int nResult = scrtDAO.insertScrt(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("scrt_id");

            // 첨부파일 업데이트
            updateFile(pmParam);
        }
        return sKey;
    }

    /**
     * 스크립트 정보를 수정한다.
     *
     * @param pmParam 스크립트 정보
     * @throws Exception
     */
    public int updateScrt(Map<String, Object> pmParam) throws Exception {
        int nResult = scrtDAO.updateScrt(pmParam);

        if (nResult > 0) {
            // 첨부파일 업데이트
            updateFile(pmParam);
        }

        return nResult;
    }

    /**
     * 스크립트 정보를 검색한다.
     *
     * @param String 스크립트 ID
     * @return 스크립트 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getScrt(String id) throws Exception {
        Map<String, Object> mParam = new HashMap<String, Object>();
        mParam.put("scrt_id", id);

        return scrtDAO.getScrt(mParam);
    }

    /**
     * 스크립트 정보를 삭제한다.
     *
     * @param param 스크립트 정보
     * @throws Exception
     */
    public ActionResult deleteScrt(Map<String, Object> pmParam) throws Exception {
        ActionResult oResult = new ActionResult();

        Map<String, Object> mParam = scrtDAO.getScrtPreview(pmParam);
        if (mParam != null) {
            if (mParam.get("alct_yn").equals("Y")) {
                oResult.setResult(ActionResultType.ERROR);
                oResult.setErrMsg("할당된 스크립트는 삭제하지 못합니다.");
            } else {
                scrtDAO.deleteScrt(pmParam);

                // 첨부파일 삭제
                Map<String, Object> mFileParam = new HashMap<String, Object>();
                mFileParam.put("rltn_tbl_nm", "TB_SCRT_BASI");
                mFileParam.put("rltn_item_id", pmParam.get("scrt_id"));
                mFileParam.put("selectcheck", pmParam.get("selectcheck"));
                fileDAO.deleteFile(mFileParam);
            }
        } else {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("이미 삭제된 스크립트 입니다.");
        }

        return oResult;
    }

    /**
     * 스크립트 미리보기정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 스크립트 리스트
     * @throws Exception
     */
    public Map<String, Object> getScrtPreview(Map<String, ?> pmParam) throws Exception {
        return scrtDAO.getScrtPreview(pmParam);
    }

    /**
     * 첨부파일에 릴레이션 아이디를 업데이트 한다.
     *
     * @param pmParam FAQ 요청 정보
     * @throws Exception
     */
    public int updateFile(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        // 관계항목 정보 저장
        pmParam.put("rltn_item_id", pmParam.get("scrt_id"));
        pmParam.put("rltn_tbl_nm", "TB_SCRT_BASI");

        // 첨부파일 리스트 가져오기
        @SuppressWarnings("unchecked")
        List<String> sFileIdList = (ArrayList<String>) pmParam.get("fileIds");
        if (sFileIdList == null) {
            sFileIdList = new ArrayList<String>();
        }

        // 첨부파일 리스트에 에디터 이미지 파일ID 추가
        // 첨부파일 리스트에 포함되지 않은 이미지 파일 삭제 처리
        String sScrtCntn = (String) pmParam.get("scrt_cntn");

        System.out.println("scrt_cntn" + sScrtCntn);
        if (sScrtCntn != null) {
            // 에디터 내용에서 이미지 파일ID 추출
            Pattern rPattern = Pattern.compile("file_id=[A-Z0-9]+\"");
            Matcher oMacher = rPattern.matcher(sScrtCntn);
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

}
