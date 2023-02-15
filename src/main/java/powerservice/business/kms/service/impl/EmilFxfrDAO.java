/*
 * (@)# EmilFxfrService.java
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

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 이메일 템플릿 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/23
 * @프로그램ID EmilFxfr
 */

@Repository
public class EmilFxfrDAO extends EgovAbstractMapper {

    /**
     * 이메일템플릿을 입력한다.
     *
     * @param pmParam 이메일템플릿 정보
     * @throws Exception
     */
    public int insertEmilFxfr(Map<String, Object> pmParam) throws Exception {
        return insert("EmilFxfrMap.insertEmilFxfr", pmParam);
    }

    /**
     * 이메일템플릿을 수정한다.
     *
     * @param pmParam 이메일템플릿 정보
     * @throws Exception
     */
    public int updateEmilFxfr(Map<String, Object> pmParam) throws Exception {
        return update("EmilFxfrMap.updateEmilFxfr", pmParam);
    }

    /**
     * 첨부파일에 요청 아이디를 업데이트 한다.
     *
     * @param pmParam 요청 정보
     * @throws Exception
     */
    public int updateFile(Map<String, Object> pmParam) throws Exception {
        return update("FileMap.updateFile", pmParam);
    }

    /**
     * 첨부파일을 삭제한다.
     *
     * @param pmParam 문자메세지 정보
     * @throws Exception
     */
    public int deleteFile(Map<String, Object> pmParam) throws Exception {
        return delete("FileMap.deleteFile", pmParam);
    }

    /**
     * 이메일템플릿의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 공지사항 정보의 검색 수
     * @throws Exception
     */
    public int getEmilFxfrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("EmilFxfrMap.getEmilFxfrCount", pmParam);
    }

    /**
     * 이메일템플릿을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공지사항 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEmilFxfrList(Map<String, ?> pmParam) throws Exception {
        return selectList("EmilFxfrMap.getEmilFxfrList", pmParam);
    }

    /**
     * 이메일템플릿을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공지사항 정보
     * @throws Exception
     */
    public Map<String, Object> getEmilFxfr(Map<String, ?> pmParam) throws Exception {
        return selectOne("EmilFxfrMap.getEmilFxfrView", pmParam);
    }

    /**
     * 이메일템플릿을 삭제한다.
     *
     * @param pmParam FAQ 정보
     * @throws Exception
     */
    public int deleteEmilFxfr(Map<String, ?> pmParam) throws Exception {
        return delete("EmilFxfrMap.deleteEmilFxfr", pmParam);
    }

    /**
     * 이메일템플릿 발신구분 체크
     *
     * @param pmParam 검색 조건
     * @return 공지사항 정보의 검색 수
     * @throws Exception
     */
    public int getEmilFxfrDpmsDivCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("EmilFxfrMap.getEmilFxfrDpmsDivCount", pmParam);
    }
}
