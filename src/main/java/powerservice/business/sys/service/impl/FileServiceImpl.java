/*
 * (@)# FileService.java
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

package powerservice.business.sys.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.sys.service.FileService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

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
@Service
public class FileServiceImpl extends EgovAbstractServiceImpl implements FileService {

    @Resource
    public FileDAO fileDAO;

    /**
     * 파일 정보를 입력한다.
     *
     * @param hmParam 파일 정보
     * @throws Exception
     */
    public String insertFile(Map<String, ?> hmParam) throws Exception {
        String sKey = "";
        int nResult = fileDAO.insertFile(hmParam);
        if (nResult > 0) {
            sKey = (String) hmParam.get("file_id");
        }
        return sKey;
    }

    /**
     * 파일 정보를 삭제한다.
     *
     * @param sFileId 파일 정보
     * @throws Exception
     */
    public int deleteFile(String sFileId) throws Exception {
        return fileDAO.deleteFileByFileId(sFileId);
    }

    /**
     * 파일 정보를 검색한다.
     *
     * @param sRelItemId 검색 조건
     * @return 파일 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getFileList(String sRelItemId) throws Exception {
        return fileDAO.getFileList(sRelItemId);
    }

    /**
     * 파일 정보를 검색한다.
     *
     * @param sRelItemId 검색 조건
     * @return 파일 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getFileList(Map<String,?> pmParam) throws Exception {
        return fileDAO.getFileList(pmParam);
    }

    /**
     * 파일 정보를 상세검색한다.
     *
     * @param hmParam 파일 ID
     * @return 파일 정보
     * @throws Exception
     */
    public Map<String, Object> getFile(Map<String, ?> hmParam) throws Exception {
        return fileDAO.getFile(hmParam);
    }
    
    /**
     * 파일 정보를 검색한다. (ATCH_TYP_CD 30인 것도 조회)
     *
     * @param sRelItemId 검색 조건
     * @return 파일 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getFileListAllByMap(Map<String,?> pmParam) throws Exception {
        return fileDAO.getFileListAllByMap(pmParam);
    }
    
    /**
     * 파일 정보를 검색한다. - BLOB데이터 포함
     *
     * @param param 검색 조건
     * @return 파일 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getFileListWithBLOB(Map<String, Object> param) throws Exception {
        return fileDAO.selectList("FileMap.getFileListWithBLOB", param);
    }

}
