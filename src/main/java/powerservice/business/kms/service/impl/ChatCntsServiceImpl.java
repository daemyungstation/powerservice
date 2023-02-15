/*
 * (@)# ChatCntsServiceImpl.java
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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.kms.service.ChatCntsService;
import powerservice.business.sys.service.impl.FileDAO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 문자메세지 유형관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/22
 * @프로그램ID ChatCnts
 */

@Service
public class ChatCntsServiceImpl extends EgovAbstractServiceImpl implements ChatCntsService {

    @Resource
    public ChatCntsDAO chatCntsDAO;

    @Resource
    public FileDAO fileDAO;
    /**
     * 문자메세지 유형정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 문자메세지 유형정보의 검색 건수
     * @throws Exception
     */
    public int getChatCntsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) chatCntsDAO.getChatCntsCount(pmParam);
    }
    
    /**
     * cj문자메세지 유형정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 문자메세지 유형정보의 검색 건수
     * @throws Exception
     */
    public int getcjChatCntsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) chatCntsDAO.getcjChatCntsCount(pmParam);
    }

    /**
     * 문자메세지 유형정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 문자메세지 유형목록
     * @throws Exception
     */
    public List<Map<String, Object>> getChatCntsList(Map<String, ?> pmParam) throws Exception {
        return chatCntsDAO.getChatCntsList(pmParam);
    }
    /**
     * cj문자메세지 유형정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 문자메세지 유형목록
     * @throws Exception
     */
    public List<Map<String, Object>> getcjChatCntsList(Map<String, ?> pmParam) throws Exception {
        return chatCntsDAO.getcjChatCntsList(pmParam);
    }

    /**
     * 문자메세지 유형정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 문자메세지 유형정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getChatCnts(Map<String, ?> pmParam) throws Exception {
        return chatCntsDAO.getChatCnts(pmParam);
    }

    /**
     * 문자메세지 유형정보를 등록한다.
     *
     * @param pmParam 문자메세지 유형정보
     * @throws Exception
     */
    public String insertChatCnts(Map<String, Object> pmParam) throws Exception {
        String sKey = "";

        int nResult = chatCntsDAO.insertChatCnts(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("chat_cnts_id");

            // 첨부파일 업데이트
            updateFile(pmParam);
        }

        return sKey;
    }

    /**
     * 문자메세지 유형정보를 수정한다.
     *
     * @param pmParam 문자메세지 유형정보
     * @throws Exception
     */
    public int updateChatCnts(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        nResult += chatCntsDAO.updateChatCnts(pmParam);

        if (nResult > 0) {
            // 첨부파일 업데이트
            updateFile(pmParam);
        }
        return nResult;
    }

    /**
     * 문자메세지 유형정보를 삭제한다.
     *
     * @param pmParam 문자메세지 유형정보
     * @throws Exception
     */
    public int deleteChatCnts(Map<String, ?> pmParam) throws Exception {
        int nResult = 0;
        nResult += chatCntsDAO.deleteChatCnts(pmParam);

        HashMap<String, String> mParam = new HashMap<String, String>();
        mParam.put("rltn_item_id", (String) pmParam.get("chat_cnts_id"));

        // 첨부파일 삭제
        fileDAO.deleteFile(mParam);

        return nResult;
    }

    /**
     * 첨부파일에 문자메세지 ID를 업데이트 한다.
     *
     * @param pmParam 문자메세지 정보
     * @throws Exception
     */
    public int updateFile(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        pmParam.put("rltn_item_id", pmParam.get("chat_cnts_id"));

        // 파일 삭제
        fileDAO.deleteFile(pmParam);

        // 첨부파일 업데이트
        @SuppressWarnings("unchecked")
        List<String> fileList = (ArrayList<String>) pmParam.get("fileIds");

        if (fileList != null && fileList.size() > 0) {
            nResult += fileDAO.updateFile(pmParam);
        }

        return nResult;
    }

}
