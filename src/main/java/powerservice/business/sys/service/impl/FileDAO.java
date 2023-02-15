/*
 * (@)# FileDAO.java
 *
 * @author 김은지
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

package powerservice.business.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 파일 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID File
 */
@Repository
public class FileDAO extends EgovAbstractMapper {

    /**
     * 파일 정보를 입력한다.
     *
     * @param pmParam 파일 정보
     * @throws Exception
     */
    public int insertFile(Map<String, ?> pmParam) throws Exception {
        return insert("FileMap.insertFile", pmParam);
    }

    /**
     * 파일 정보를 수정한다.
     *
     * @param pmParam 파일 정보
     * @throws Exception
     */
    public int updateFile(Map<String, ?> pmParam) throws Exception {
        return update("FileMap.updateFile", pmParam);
    }

    /**
     * 파일 정보를 삭제한다.
     *
     * @param psRltnItemId 파일 정보
     * @throws Exception
     */
    public int deleteFile(String psRltnItemId) throws Exception {
        return delete("FileMap.deleteFile", psRltnItemId);
    }

    /**
     * 파일 정보를 삭제한다.
     *
     * @param psRltnItemId 파일 정보
     * @throws Exception
     */
    public int deleteFileByFileId(String sFileId) throws Exception {
        return delete("FileMap.deleteFileByFileId", sFileId);
    }

    /**
     * 파일 정보를 삭제한다.
     *
     * @param pmParam 파일 정보
     * @throws Exception
     */
    public int deleteFile(Map<String, ?> pmParam) throws Exception {
        return delete("FileMap.deleteFile", pmParam);
    }

    /**
     * 파일 정보를 검색한다.
     *
     * @param psRelItemId 검색 조건
     * @return 파일 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getFileList(String psRelItemId) throws Exception {
        return selectList("FileMap.getFileList", psRelItemId);
    }

    /**
     * 파일 정보를 검색한다
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getFileList(Map<String, ?> pmParam) throws Exception {
        return selectList("FileMap.getFileListByMap", pmParam);
    }
    
    /**
     * 파일 정보를 검색한다
     *  (ATCH_TYP_CD 30인 것도 조회)
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getFileListAllByMap(Map<String, ?> pmParam) throws Exception {
        return selectList("FileMap.getFileListAllByMap", pmParam);
    }

    /**
     * 파일 정보를 상세검색한다.
     *
     * @param pmParam 파일 ID
     * @return 파일 정보
     * @throws Exception
     */
    public Map<String, Object> getFile(Map<String, ?> pmParam) throws Exception {
        return selectOne("FileMap.getFile", pmParam);
    }

    /**
     * 파일 정보를 복사한다.
     *
     * @param pmParam 파일 정보
     * @throws Exception
     */
    public int copyFile(Map<String, ?> pmParam) throws Exception {
        return insert("FileMap.copyFile", pmParam);
    }
    
    /**
     * 파일 정보를 검색한다. - BLOB데이터 포함
     *
     * @param param 검색 조건
     * @return 파일 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getFileListWithBLOB(Map<String, Object> param) throws Exception {
        return selectList("FileMap.getFileListWithBLOB", param);
    }

}
