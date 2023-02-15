/*
 * (@)# XlsService.java
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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.impl.DlwCondDAO;
import powerservice.business.sys.service.XlsService;
import powerservice.common.util.excel.ExcelResultHandler;
import powerservice.core.util.excel.biggrid.BigGrid;
import powerservice.core.util.excel.biggrid.BigGridColumn;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 엑셀 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Xls
 */
@Service
public class XlsServiceImpl extends EgovAbstractServiceImpl implements XlsService {

    @Resource
    public XlsDAO xlsDAO;

    @Resource
    public DlwCondDAO dlwDAO;

    @Resource
    public XlsItemDAO xlsItemDAO;

    /**
     * 엑셀 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 엑셀 정보의 검색 수
     * @throws Exception
     */
    public int getXlsCount(Map<String, ?> pmParam) throws Exception {
        return xlsDAO.getXlsCount(pmParam);
    }

    /**
     * 엑셀 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 엑셀 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getXlsList(Map<String, ?> pmParam) throws Exception {
        return xlsDAO.getXlsList(pmParam);
    }

    /**
     * 엑셀 정보를 검색한다.
     *
     * @param psId 엑셀ID
     * @return 엑셀 1건
     * @throws Exception
     */
    public Map<String, Object> getXls(String psId) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("xls_id", psId);
        return xlsDAO.getXls(mParam);
    }

    /**
     * 엑셀 정보를 등록한다.
     *
     * @param pmParam 엑셀 정보
     * @throws Exception
     */
    public String insertXls(Map<String, ?> pmParam) throws Exception {
        String sKey = "";
        int nResult = xlsDAO.insertXls(pmParam);
        if (nResult > 0){
            sKey = (String) pmParam.get("xls_id");
        }
        return sKey;
    }

    /**
     * 엑셀 정보를 수정한다.
     *
     * @param pmParam 엑셀 정보
     * @throws Exception
     */
    public int updateXls(Map<String, ?> pmParam) throws Exception {
        return xlsDAO.updateXls(pmParam);
    }

    /**
     * 엑셀 정보를 삭제한다.
     *
     * @param pmParam 엑셀 정보
     * @throws Exception
     */
    public int deleteXls(Map<String, ?> pmParam) throws Exception {
        xlsItemDAO.deleteXlsItem(pmParam);
        return xlsDAO.deleteXls(pmParam);
    }


    /**
     * 엑셀 데이터의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 엑셀 정보의 검색 수
     * @throws Exception
     */
    public int getXlsDataCount(Map<String, ?> pmParam) throws Exception {
        String sCscntQuryId = StringUtils.defaultString((String) pmParam.get("cscnt_qury_id"));

        SqlSession sqSession = xlsDAO.getSqlSession();
        if ("Y".equals(pmParam.get("ms_sql_yn"))) {
            sqSession = dlwDAO.getSqlSession();
        }
        return sqSession.selectOne(sCscntQuryId, pmParam);
    }

    /**
     * 쿼리 결과 엑셀 파일 생성
     *
     * @param pmParam 엑셀 다운로드 정보
     * @throws Exception
     */
    public void downloadXls(Map<String, Object> pmParam) throws Exception {
        String sListQuryId = StringUtils.defaultString((String) pmParam.get("list_qury_id"));

        xlsDAO.getSqlSession().select(sListQuryId, pmParam, new ExcelResultHandler(pmParam));
    }

    /**
     * 쿼리 결과 엑셀 파일 생성 (대용량)
     *
     * @param pmParam 엑셀 다운로드 정보
     * @throws Exception
     */
    public void downloadXlsBigGrid(Map<String, Object> pmParam, List<Map<String, Object>> lmColumn) throws Exception {
        String sXlsId = StringUtils.defaultString((String) pmParam.get("xls_id"));

        // 1. 엑셀 항목 리스트 조회
        Map<String, Object> mParam = new HashMap<String, Object>();
        mParam.put("xls_id", sXlsId);

        // 2. 엑셀 컬럼 리스트 작성
        LinkedHashMap<String, BigGridColumn> mColumnList = new LinkedHashMap<String, BigGridColumn>();
        if (lmColumn != null) {
            for (Map<String, Object> mXlsItem : lmColumn) {
                String sXlsColmId = (String) mXlsItem.get("xls_colm_id");
                String sXlsColmNm = (String) mXlsItem.get("xls_colm_nm");
                String sXlsColmTypeCd = (String) mXlsItem.get("xls_colm_type_cd");
                if (sXlsColmNm == null) {
                    sXlsColmNm = "";
                }

                if ("20".equals(sXlsColmTypeCd)) { // 숫자
                    mColumnList.put(sXlsColmId, new BigGridColumn(sXlsColmNm, BigGridColumn.STYLE_NUMBER));
                } else if ("30".equals(sXlsColmTypeCd)) { // 비율
                    mColumnList.put(sXlsColmId, new BigGridColumn(sXlsColmNm, BigGridColumn.STYLE_PERCENT));
                } else if ("40".equals(sXlsColmTypeCd)) { // 일시
                    mColumnList.put(sXlsColmId, new BigGridColumn(sXlsColmNm, BigGridColumn.STYLE_DTTM));
                } else if ("50".equals(sXlsColmTypeCd)) { // 일자
                    mColumnList.put(sXlsColmId, new BigGridColumn(sXlsColmNm, BigGridColumn.STYLE_DATE));
                } else if ("60".equals(sXlsColmTypeCd)) { // 시간
                    mColumnList.put(sXlsColmId, new BigGridColumn(sXlsColmNm, BigGridColumn.STYLE_TIME));
                } else if ("70".equals(sXlsColmTypeCd)) { // 우편번호
                    mColumnList.put(sXlsColmId, new BigGridColumn(sXlsColmNm, BigGridColumn.STYLE_POST));
                } else { // 일반
                    mColumnList.put(sXlsColmId, new BigGridColumn(sXlsColmNm, BigGridColumn.STYLE_GENERAL));
                }
            }
        }

        // 3. 엑셀 파일 생성
        SqlSession sqSession = xlsDAO.getSqlSession();
        if ("Y".equals(pmParam.get("ms_sql_yn"))) {
            sqSession = dlwDAO.getSqlSession();
        }
        BigGrid.makeExcelFile(sqSession, pmParam, mColumnList);
    }
}
