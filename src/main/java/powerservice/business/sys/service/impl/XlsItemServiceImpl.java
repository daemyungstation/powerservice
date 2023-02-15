/*
 * (@)# XlsItemService.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.stereotype.Service;

import powerservice.business.sys.service.XlsItemService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 엑셀 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID XlsItem
 */
@Service
public class XlsItemServiceImpl extends EgovAbstractServiceImpl implements XlsItemService {

    @Resource
    public XlsItemDAO xlsItemDAO;

    /**
     * 엑셀 항목 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 엑셀 항목 정보의 검색 수
     * @throws Exception
     */
    public int getXlsItemCount(Map<String, ?> pmParam) throws Exception {
        return xlsItemDAO.getXlsItemCount(pmParam);
    }

    /**
     * 엑셀 항목 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 엑셀 항목 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getXlsItemList(Map<String, ?> pmParam) throws Exception {
        return xlsItemDAO.getXlsItemList(pmParam);
    }

    /**
     * 엑셀 항목 정보를 등록한다.
     *
     * @param pmParam 엑셀 항목 정보
     * @throws Exception
     */
    public int insertXlsItem(Map<String, ?> pmParam) throws Exception {
        return xlsItemDAO.insertXlsItem(pmParam);
    }

    /**
     * 엑셀 항목 정보를 수정한다.
     *
     * @param pmParam 엑셀 항목 정보
     * @throws Exception
     */
    public int updateXlsItem(Map<String, ?> pmParam) throws Exception {
        return xlsItemDAO.updateXlsItem(pmParam);
    }

    /**
     * 엑셀 항목 정보를 삭제한다.
     *
     * @param pmParam 엑셀 항목 리스트
     * @throws Exception
     */
    public int deleteXlsItem(Map<String, ?> pmParam) throws Exception {
        return xlsItemDAO.deleteXlsItem(pmParam);
    }


    /**
     * 엑셀 항목 정보를 수정한다.(팝업)
     *
     * @param mModelList 엑셀 항목 리스트
     * @param pmParam 엑셀 항목 정보
     * @throws Exception
     */
    public int updateXlsItemPopup(List<Map<String, Object>> mModelList, Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        for (Map<String, Object> mModel : mModelList) {
            mModel.put("amnd_id", pmParam.get("amnd_id"));

            nResult += xlsItemDAO.updateXlsItemPopup(mModel);
        }
        return nResult;
    }

    /**
     * 엑셀 항목 정보를 등록한다.(팝업)
     *
     * @param pmParam 엑셀 항목 정보
     * @throws Exception
     */
    public int insertXlsItemPopup(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        String[] sXlsColmIdList = (String[]) pmParam.get("sXlsColmIdList");
        if (sXlsColmIdList != null) {
            for (String sXlsColmId : sXlsColmIdList) {
                pmParam.put("xls_colm_id", sXlsColmId);

                nResult += xlsItemDAO.insertXlsItemPopup(pmParam);
            }
        }
        return nResult;
    }

    /**
     * 엑셀 항목 정보를 삭제한다.(팝업)
     *
     * @param pmParam 엑셀 항목 정보
     * @throws Exception
     */
    public int deleteXlsItemPopup(Map<String, Object> pmParam) throws Exception {
        return xlsItemDAO.update("XlsItemMap.deleteXlsItemPopup", pmParam);
    }


    /**
     * 엑셀 항목을 등록하기 위해 엑셀 항목 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @param pmXlsItemList 엑셀 항목 리스트
     * @return 엑셀 항목 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getXlsItemListGetColumns(Map<String, Object> pmParam, List<Map<String, Object>> pmXlsItemList) throws Exception {
        ArrayList<Map<String, Object>> mResultList = new ArrayList<Map<String, Object>>();

        String sQuery = "";
        String sTempQuery = ""; // 쿼리 처리용 변수
        String[] sColumnList = null; // 쿼리 컬럼명 리스트

        int nBeginIndex = 0;
        int nEndIndex = 0;

        try {
            // 목록 조회 쿼리 가져오기
            String sStatementID = (String) pmParam.get("list_qury_id");
            MappedStatement oMappedStatement = xlsItemDAO.getSqlSession().getConfiguration().getMappedStatement(sStatementID);
            sQuery = oMappedStatement.getBoundSql(null).getSql();

            // 컬럼명 추출을 위한 불필요 문자 제거 및 소문자화
            sQuery = sQuery.toLowerCase();
            sQuery = sQuery.replaceAll("\t", "");
            sQuery = sQuery.replaceAll("\n", "");
            sQuery = sQuery.replaceAll(" as ", " ");

            // 쿼리에서 컬럼명 리스트 추출

            nBeginIndex = sQuery.indexOf("select") + 6;
            nEndIndex = sQuery.indexOf("from");

            sTempQuery = sQuery.substring(nBeginIndex, nEndIndex);
            sTempQuery = sTempQuery.trim();
            sTempQuery = sTempQuery.replaceAll(" ", "");

            // 쿼리의 "/* 쿼리명 */" 주석 제거
            if (sTempQuery.startsWith("/*")) {
                sTempQuery = sTempQuery.substring(sTempQuery.indexOf("*/") + 2);
                sTempQuery = sTempQuery.replaceAll(" ", "");
            }

            // "SELECT *" 인 경우 서브쿼리의 SELECT 절의 컬럼명 리스트 추출
            if (sTempQuery.startsWith("*")) {
                nBeginIndex = sQuery.indexOf("select", nEndIndex) + 6;
                nEndIndex = sQuery.indexOf("from", nEndIndex + 4);

                sTempQuery = sQuery.substring(nBeginIndex, nEndIndex);
                sTempQuery = sTempQuery.replaceAll(" ", "");
            }

            // 쿼리와 DB의 컬럼명 비교
            sColumnList = sTempQuery.split(",");
            for (int i = 0; i < sColumnList.length; i++) {
                String sXlsColmId = "";

                if (sColumnList[i].startsWith("rownum")) {
                    // "ROWNUM" 인 경우
                    if (sColumnList[i].length() > 6) {
                        sXlsColmId = sColumnList[i].substring(6);
                    }
                } else if (sColumnList[i].indexOf(")") != -1) {
                    // 함수를 사용한 경우의 결과값이 저장되는 컬럼 이름만 추출
                    sXlsColmId = sColumnList[i].substring(sColumnList[i].lastIndexOf(")") + 1);
                } else if ((sColumnList[i].indexOf("(") == -1)) {
                    sXlsColmId = sColumnList[i];
                }

                if (!"".equals(sXlsColmId)) {
                    boolean bExist = false;

                    for (int j = 0; j < pmXlsItemList.size(); j++) {
                        Map<String, Object> mXlsItem = (Map<String, Object>) pmXlsItemList.get(j);

                        if (sXlsColmId.equalsIgnoreCase(mXlsItem.get("xls_colm_id").toString())) {
                            bExist = true;
                        }
                    }

                    if (!bExist) {
                        HashMap<String, Object> mResult = new HashMap<String, Object>();
                        mResult.put("xls_colm_id", sXlsColmId);
                        mResultList.add(mResult);
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return mResultList;
    }

    /**
     * 다운로드를 위한 엑셀 항목 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @param pmXlsItemList 엑셀 항목 리스트
     * @return 엑셀 항목 필터 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getXlsItemListForDownload(Map<String, Object> pmParam, List<Map<String, Object>> pmXlsItemList) throws Exception {
        ArrayList<Map<String, Object>> mResultList = new ArrayList<Map<String, Object>>();

        String sQuery = ""; // 쿼리
        String sTempQuery = ""; // 쿼리 처리용 변수
        String[] sColumnList = null; // 쿼리 컬럼명 리스트

        int nBeginIndex = 0;
        int nEndIndex = 0;

        try {
            // 목록 조회 쿼리 가져오기
            String sStatementID = (String) pmParam.get("list_qury_id");
            MappedStatement oMappedStatement = xlsItemDAO.getSqlSession().getConfiguration().getMappedStatement(sStatementID);
            sQuery = oMappedStatement.getBoundSql(null).getSql();

            // 컬럼명 추출을 위한 불필요 문자 제거 및 소문자화
            sQuery = sQuery.replaceAll("\t", "");
            sQuery = sQuery.replaceAll("\n", "");
            sQuery = sQuery.toLowerCase();

            // 쿼리에서 컬럼명 리스트 추출

            nBeginIndex = sQuery.indexOf("select", nEndIndex) + 6;
            nEndIndex = sQuery.indexOf("from");

            sTempQuery = sQuery.substring(nBeginIndex, nEndIndex);
            sTempQuery = sTempQuery.trim();
            sTempQuery = sTempQuery.replaceAll(" ", "");

            // "SELECT *" 인 경우 서브쿼리의 SELECT 절의 컬럼명 리스트 추출
            if (sTempQuery.startsWith("*")) {
                nBeginIndex = sQuery.indexOf("select", nEndIndex) + 6;
                nEndIndex = sQuery.indexOf("from", nEndIndex + 4);

                sTempQuery = sQuery.substring(nBeginIndex, nEndIndex);
                sTempQuery = sTempQuery.replaceAll(" ", "");
            }

            // 쿼리의 "/* 쿼리명 */" 주석 제거
            if (sTempQuery.startsWith("/*")) {
                sTempQuery = sTempQuery.substring(sTempQuery.indexOf("*/") + 2);
                sTempQuery = sTempQuery.replaceAll(" ", "");
            }

            // 쿼리와 DB의 컬럼명 비교
            sColumnList = sTempQuery.split(",");
            for (int i = 0; i < pmXlsItemList.size(); i++) {
                Map<String, Object> mXlsItem = (Map<String, Object>) pmXlsItemList.get(i);
                Map<String, Object> mResult = new HashMap<String, Object>();

                boolean bCheck = false;

                for (int j = 0; j < sColumnList.length; j++) {
                    String sXlsColmId = "";

                    if (sColumnList[j].startsWith("rownum") || (sColumnList[j].indexOf("(") != -1)) {
                        // "ROWNUM"과 함수를 사용하는 경우는 패스
                        continue;
                    } else if (sColumnList[j].indexOf(")") != -1) {
                        // 함수를 사용한 경우의 결과값이 저장되는 컬럼 이름만 추출
                        sXlsColmId = sColumnList[j].substring(sColumnList[j].indexOf(")") + 1);
                        if (sXlsColmId.equals(((String) mXlsItem.get("xls_colm_id")).toLowerCase())) {
                            mResult.put("xls_colm_id", sXlsColmId);
                            mResult.put("xls_colm_nm", mXlsItem.get("xls_colm_nm"));

                            bCheck = true;
                        }

                        continue;
                    }

                    sXlsColmId = sColumnList[j];
                    if (sXlsColmId.equals(((String) mXlsItem.get("xls_colm_id")).toLowerCase())) {
                        mResult.put("xls_colm_id", sXlsColmId);
                        mResult.put("xls_colm_nm", mXlsItem.get("xls_colm_nm"));

                        bCheck = true;
                    }
                }

                if (bCheck) {
                    mResultList.add(mResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mResultList;
    }

}
