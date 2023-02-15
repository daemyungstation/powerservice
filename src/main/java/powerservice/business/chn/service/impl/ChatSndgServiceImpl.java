/*
 * (@)# ChatSndgServiceImpl.java
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

package powerservice.business.chn.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import powerservice.business.chn.service.ChatSndgService;

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
public class ChatSndgServiceImpl extends EgovAbstractServiceImpl implements ChatSndgService {

    @Resource
    public ChatSndgDAO chatSndgDAO;

    /**
     * 문자메세지 유형정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 문자메세지 유형정보의 검색 건수
     * @throws Exception
     */
    public int getChatCntsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)chatSndgDAO.getChatCntsCount(pmParam);
    }

    /**
     * 문자메세지 유형정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 문자메세지 유형목록
     * @throws Exception
     */
    public List<Map<String, Object>> getChatCntsList(Map<String, ?> pmParam) throws Exception {
        return chatSndgDAO.getChatCntsList(pmParam);
    }

    /**
     * 문자메세지 유형정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 문자메세지 유형정보(1건)
     * @throws Exception
     */
    public List<Map<String, Object>> getChatCnts(Map<String, ?> pmParam) throws Exception {
        return chatSndgDAO.getChatCntsList(pmParam);
    }

    /**
     * 문자메세지 유형정보를 등록한다.
     *
     * @param pmParam 문자메세지 유형정보
     * @throws Exception
     */
    public String insertChatCnts(Map<String, Object> pmParam) throws Exception {
        String sKey = "";

        int nResult = chatSndgDAO.insertChatCnts(pmParam);

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
    public void updateChatCnts(Map<String, Object> pmParam) throws Exception {
        int nResult = chatSndgDAO.updateChatCnts(pmParam);

        if (nResult > 0) {

            // 첨부파일 업데이트
            updateFile(pmParam);
        }
    }

    /**
     * 문자메세지 유형정보를 삭제한다.
     *
     * @param pmParam 문자메세지 유형정보
     * @throws Exception
     */
    public void deleteChatCnts(Map<String, ?> pmParam) throws Exception {
        chatSndgDAO.deleteChatCnts(pmParam);

        HashMap<String, String> mParam = new HashMap<String, String>();
        mParam.put("rltn_item_id", (String) pmParam.get("chat_cnts_id"));

        // 첨부파일 삭제
        chatSndgDAO.deleteFile(mParam);
    }

    /**
     * 첨부파일에 문자메세지 ID를 업데이트 한다.
     *
     * @param pmParam 문자메세지 정보
     * @throws Exception
     */
    public void updateFile(Map<String, Object> pmParam) throws Exception {
        pmParam.put("rltn_item_id", pmParam.get("chat_cnts_id"));

        // 파일 삭제
        chatSndgDAO.deleteFile(pmParam);

        // 첨부파일 업데이트
        @SuppressWarnings("unchecked")
        List<String> fileList = (ArrayList<String>) pmParam.get("fileIds");

        if (fileList != null && fileList.size() > 0) {
            chatSndgDAO.updateFile(pmParam);
        }
    }
}
