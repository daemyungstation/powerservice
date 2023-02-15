/*
 * (@)# EmilRecpServiceImpl.java
 *
 * @author 정용재
 * @version 1.0
 * @date 2015/06/16
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

package powerservice.business.mta.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.mta.service.EmilRecpService;
import powerservice.business.sys.service.impl.FileDAO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 컬럼 기본값 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2015/06/16
 * @프로그램ID EmilRecp
 */
@Service
public class EmilRecpServiceImpl extends EgovAbstractServiceImpl implements EmilRecpService {

    @Resource
    public EmilRecpDAO emilRecpDAO;

    @Resource
    public FileDAO fileDAO;


    /**
     * 이메일 수신 정보의 검색 수를 반환한다.
     *
     * @param pmParamHash 검색 조건
     * @return 컬럼 기본값 관리 정보의 검색 건수
     * @throws Exception
     */
    public int getEmilRecpCount(Map<String, ?> pmParam) throws Exception {
        return emilRecpDAO.getEmilRecpCount(pmParam);
    }

    /**
     * 이메일 수신 정보를 검색한다.
     *
     * @param pmParamHash 검색 조건
     * @return 컬럼 기본값 관리 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getEmilRecpList(Map<String, ?> pmParam) throws Exception {
        return emilRecpDAO.getEmilRecpList(pmParam);
    }

    /**
     * 이메일 수신 정보를 검색한다.(1건)
     *
     * @param id 컬럼 기본값
     * @return 컬럼 기본값 관리 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getEmilRecp(String id) throws Exception {
        Map<String, String> pmParam = new HashMap<String, String>();
        pmParam.put("eml_hstr_id", id);

        return emilRecpDAO.getEmilRecp(pmParam);
    }

    /**
     * 이메일 수신 정보를 등록한다. (15.04.15)
     *
     * @param pmParam 컬럼 기본값 관리 정보
     * @throws Exception
     */

    public String insertEmilRecp(Map<String, Object> pmParam) throws Exception {
        String sKey = "";
        Map<String, Object> searchParam = new HashMap<String, Object>();
        searchParam.put("emilmsg_id", pmParam.get("emilmsg_id"));
        int nCnt = emilRecpDAO.getEmilRecpCount(searchParam);

        if (nCnt == 0) {
            int nResult = emilRecpDAO.insertEmilRecp(pmParam);

            if(nResult > 0){
                sKey = (String) pmParam.get("emil_recp_id");

                // 파일이 존재할 경우 처리구문
                String _FILE_DOWN_URL = "/powerservice/file/download?file_id=";
                String emil_html_cntn = StringUtils.defaultString((String) pmParam.get("emil_html_cntn"));
                Boolean cntn_modify_fg = false;
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> fileList = (List<Map<String, Object>>)pmParam.get("fileList");
                if (fileList != null) {
                    for (Map<String, Object> fileMap : fileList) {
                        // 파일과 이메일과의 관계 파라미터 입력
                        fileMap.put("rltn_tbl_nm", "TB_EML_RECV_HSTR");		// 관계 테이블
                        fileMap.put("rltn_item_id", sKey);      			// 관계 아이디
                        fileMap.put("cntr_cd", pmParam.get("cntr_cd"));		// 센터코드
                        fileMap.put("rgsr_id", pmParam.get("rgsr_id"));		// 등록자 아이디
                        fileMap.put("amnd_id", pmParam.get("amnd_id"));		// 수정자 아이디

                        // Thumnail 생성 시작
                        File image = null;
                        OutputStream os = null;
                        InputStream is = null;

                        try {
                            image = File.createTempFile(fileMap.get("rltn_tbl_nm").toString() + "_" +fileMap.get("rltn_item_id").toString(), ".png");
                            os = new FileOutputStream(image);
                            is = new ByteArrayInputStream((byte[])fileMap.get("file_cntn"));
                            Thumbnails.of(is).size(160, 120).outputFormat("png").outputQuality(0.8f).toOutputStream(os);

                            is = new FileInputStream(image);
                            byte[] imagecnts = new byte[(int)image.length()];
                            is.read(imagecnts);

                            fileMap.put("imagethumbnail", imagecnts); // 썸네일내용
                        } catch (Exception e) {
                            // e.printStackTrace();
                        } finally {
                            if (is != null) {
                                is.close();
                            }
                            if (os != null) {
                                os.close();
                            }
                            if (image != null && image.exists()) {
                                image.delete();
                            }
                        }
                        // Thumnail 생성 종료

                        int nFileResult = fileDAO.insertFile(fileMap);

                        // 파일 Insert가 정상적으로 작동했을 경우 이메일HTML에서 content-id를 치환
                        if (nFileResult > 0) {
                            String sFileId = (String) fileMap.get("file_id");
                            if (!fileMap.get("content_id").equals("") && emil_html_cntn.indexOf("cid:"+fileMap.get("content_id")) >= 0) {
                                emil_html_cntn = emil_html_cntn.replaceAll("cid:"+fileMap.get("content_id"), _FILE_DOWN_URL + sFileId);
                                cntn_modify_fg = true;
                            }
                        }

                    }
                }

                // 파일 유형 중 inline 이미지가 있을 경우 내용을 업데이트
                if (true == cntn_modify_fg) {
                    pmParam.put("emil_html_cntn", emil_html_cntn);

                    emilRecpDAO.updateEmilRecpCntn(pmParam);
                }
            }
        }

        return sKey;
    }

    /**
     * 이메일 수신 정보를 수정한다.
     *
     * @param pmParam 컬럼 기본값 관리 정보
     * @throws Exception
     */
    public int updateEmilRecp(Map<String, ?> pmParam) throws Exception {
        return emilRecpDAO.updateEmilRecp(pmParam);
    }
}
