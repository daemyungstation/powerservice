/*
 * (@)# ChatCntsDao.java
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

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

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

@Repository
public class ChatCntsDAO extends EgovAbstractMapper {

     /**
     * 문자메세지 유형정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 문자메세지 유형정보의 검색 건수
     * @throws Exception
     */
    public int getChatCntsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ChatCntsMap.getChatCntsCount", pmParam);
    }
    
    /**
     * cj문자메세지 유형정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 문자메세지 유형정보의 검색 건수
     * @throws Exception
     */
    public int getcjChatCntsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ChatCntsMap.getcjChatCntsCount", pmParam);
    }

    /**
     * 문자메세지 유형정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 문자메세지 유형목록
     * @throws Exception
     */
    public List<Map<String, Object>> getChatCntsList(Map<String, ?> pmParam) throws Exception {
        return selectList("ChatCntsMap.getChatCntsList", pmParam);
    }
    
    /**
     *cj 문자메세지 유형정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 문자메세지 유형목록
     * @throws Exception
     */
    public List<Map<String, Object>> getcjChatCntsList(Map<String, ?> pmParam) throws Exception {
        return selectList("ChatCntsMap.getcjChatCntsList", pmParam);
    }


    /**
     * 문자메세지 유형정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 문자메세지 유형정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getChatCnts(Map<String, ?> pmParam) throws Exception {
        return selectOne("ChatCntsMap.getChatCntsList", pmParam);
    }

    /**
     * 문자메세지 유형정보를 등록한다.
     *
     * @param pmParam 문자메세지 유형정보
     * @throws Exception
     */
    public int insertChatCnts(Map<String, Object> pmParam) throws Exception {
        return insert("ChatCntsMap.insertChatCnts", pmParam);
    }

    /**
     * 문자메세지 유형정보를 수정한다.
     *
     * @param pmParam 문자메세지 유형정보
     * @throws Exception
     */
    public int updateChatCnts(Map<String, Object> pmParam) throws Exception {
        return update("ChatCntsMap.updateChatCnts", pmParam);
    }

    /**
     * 문자메세지 유형정보를 삭제한다.
     *
     * @param pmParam 문자메세지 유형정보
     * @throws Exception
     */
    public int deleteChatCnts(Map<String, ?> pmParam) throws Exception {
        return delete("ChatCntsMap.deleteChatCnts", pmParam);
    }

    /**
     * 첨부파일에 문자메세지 ID를 업데이트 한다.
     *
     * @param pmParam 문자메세지 정보
     * @throws Exception
     */
    public int updateFile(Map<String, Object> pmParam) throws Exception {
        return update("FileMap.updateFile", pmParam);
    }
}
