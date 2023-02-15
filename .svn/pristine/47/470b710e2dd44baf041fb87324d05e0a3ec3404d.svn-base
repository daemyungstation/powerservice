/*
 * (@)# DlwCustServiceImpl.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/04
 * Copyright (c) 2016 by Inwoo tech inc, KOREA. All Rights Reserved.
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

package powerservice.business.gongje.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import powerservice.business.gongje.service.GongjeService;
import powerservice.common.util.CommonUtils;
import powerservice.core.bean.UserSessionCore;
import powerservice.core.util.SessionUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
/**
 * 대명고객 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/04
 * @프로그램ID DlwCust
 */
@Service
public class GongjeServiceImpl extends EgovAbstractServiceImpl implements GongjeService {

    @Resource
    public GongjeDAO gongjeDAO;

    private final Logger log = LoggerFactory.getLogger(GongjeServiceImpl.class);

    /**
     * 공제 - 임시테이블 삭제
     *
     * @param pmParam 검색 조건
     * @return Temp테이블 삭제
     * @throws Exception
     */
    public int getGongjeTempDelete() throws Exception {
        return gongjeDAO.getGongjeTempDelete();
    }

    /**
     * 공제-엑셀 생성 후 미전송 데이터 체크
     *
     * @param pmParam 검색 조건
     * @return 미전송 데이터 건수
     * @throws Exception
     */
    public int getGongjeExcelchkCount() throws Exception {
        return (Integer) gongjeDAO.getGongjeExcelchkCount();
    }

    /**
     * 공제 - 엑셀취소(미전송) 데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return Temp테이블 삭제
     * @throws Exception
     */
    public int getGongjeExcelcancel() throws Exception {
        return gongjeDAO.getGongjeExcelcancel();
    }

    /**
     * 공제-신규 조회시 Temp Table INSERT
     *
     * @param pmParam 검색 조건
     * @return 공제-신규 정보 목록
     * @throws Exception
     */
    public int getGongjeNewInsert_Temp(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeNewInsert_Temp(pmParam);
    }

    /**
     * 공제-신규 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-신규 정보의 검색 건수
     * @throws Exception
     */
    public int getGongjeNewCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) gongjeDAO.getGongjeNewCount(pmParam);
    }

    /**
     * 공제-신규 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-신규 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeNewList(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeNewList(pmParam);
    }

    /**
     * 공제-신규 엑셀반출시 히스토리 INSERT
     *
     * @param pmParam 검색 조건
     * @return 공제-신규 정보 목록
     * @throws Exception
     */
    public int getGongjeNewInsert(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeNewInsert(pmParam);
    }

    /**
     * 공제-해약 조회시 Temp Table INSERT
     *
     * @param pmParam 검색 조건
     * @return 공제-해약 정보 목록
     * @throws Exception
     */
    public int getGongjeRessInsert_Temp(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeRessInsert_Temp(pmParam);
    }

    /**
     * 공제-해약 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-해약 정보의 검색 건수
     * @throws Exception
     */
    public int getGongjeRessCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) gongjeDAO.getGongjeRessCount(pmParam);
    }

    /**
     * 공제-해약 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-해약 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeRessList(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeRessList(pmParam);
    }

    /**
     * 공제-해약 엑셀반출시 히스토리 INSERT
     *
     * @param pmParam 검색 조건
     * @return 공제-해약 정보 목록
     * @throws Exception
     */
    public int getGongjeRessInsert(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeRessInsert(pmParam);
    }

    /**
     * 공제-소비자정보 변경 조회시 Temp Table INSERT
     *
     * @param pmParam 검색 조건
     * @return 공제-신규 정보 목록
     * @throws Exception
     */
    public int getGongjeChngInsert_Temp(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeChngInsert_Temp(pmParam);
    }

    /**
     * 공제-소비자정보 변경 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-소비자정보 변경 검색 건수
     * @throws Exception
     */
    public int getGongjeChngCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) gongjeDAO.getGongjeChngCount(pmParam);
    }

    /**
     * 공제-소비자정보 변경 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-소비자정보 변경 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeChngList(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeChngList(pmParam);
    }

    /**
     * 공제-소비자정보 변경 엑셀반출시 히스토리 INSERT
     *
     * @param pmParam 검색 조건
     * @return 공제-신규 정보 목록
     * @throws Exception
     */
    public int getGongjeChngInsert(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeChngInsert(pmParam);
    }

    /**
     * 공제-양도양수 조회시 Temp Table INSERT
     *
     * @param pmParam 검색 조건
     * @return 공제-해약 정보 목록
     * @throws Exception
     */
    public int getGongjeYdsInsert_Temp(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeYdsInsert_Temp(pmParam);
    }

    /**
     * 공제-양도양수 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-양도양수 검색 건수
     * @throws Exception
     */
    public int getGongjeYdsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) gongjeDAO.getGongjeYdsCount(pmParam);
    }

    /**
     * 공제-양도양수 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-양도양수 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeYdsList(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeYdsList(pmParam);
    }

    /**
     * 공제-양도양수 엑셀반출시 히스토리 INSERT
     *
     * @param pmParam 검색 조건
     * @return 공제-해약 정보 목록
     * @throws Exception
     */
    public int getGongjeYdsInsert(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeYdsInsert(pmParam);
    }

    /**
     * 공제-행사 조회시 Temp Table INSERT
     *
     * @param pmParam 검색 조건
     * @return 공제-행사 정보 목록
     * @throws Exception
     */
    public int getGongjeHangsaInsert_Temp(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeHangsaInsert_Temp(pmParam);
    }

    /**
     * 공제-행사 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-행사 검색 건수
     * @throws Exception
     */
    public int getGongjeHangsaCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) gongjeDAO.getGongjeHangsaCount(pmParam);
    }

    /**
     * 공제-행사 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-행사 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeHangsaList(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeHangsaList(pmParam);
    }

    /**
     * 공제-행사 엑셀반출시 히스토리 INSERT
     *
     * @param pmParam 검색 조건
     * @return 공제-행사 정보 목록
     * @throws Exception
     */
    public int getGongjeHangsaInsert(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeHangsaInsert(pmParam);
    }

    /**
     * 공제-선수금납입 조회시 Temp Table INSERT
     *
     * @param pmParam 검색 조건
     * @return 공제-선수금납입 정보 목록
     * @throws Exception
     */
    public int getGongjePayInsert_Temp(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjePayInsert_Temp(pmParam);
    }

    /**
     * 공제-선수금납입 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-선수금납입 검색 건수
     * @throws Exception
     */
    public int getGongjePayCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) gongjeDAO.getGongjePayCount(pmParam);
    }

    /**
     * 공제-선수금납입 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-선수금납입 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjePayList(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjePayList(pmParam);
    }

    /**
     * 공제-선수금납입 엑셀반출시 히스토리 INSERT
     *
     * @param pmParam 검색 조건
     * @return 공제-선수금납입 정보 목록
     * @throws Exception
     */
    public int getGongjePayInsert(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjePayInsert(pmParam);
    }

    /**
     * 공제-선수금취소 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-선수금납입 검색 건수
     * @throws Exception
     */
    public int getGongjePay_CnclCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) gongjeDAO.getGongjePay_CnclCount(pmParam);
    }

    /**
     * 공제-선수금취소 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-선수금납입 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjePay_CnclList(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjePay_CnclList(pmParam);
    }

    /**
     * 공제-선수금취소 엑셀반출시 히스토리 INSERT
     *
     * @param pmParam 검색 조건
     * @return 공제-선수금납입 정보 목록
     * @throws Exception
     */
    public int getGongjePay_CnclInsert(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjePay_CnclInsert(pmParam);
    }

    /**
     * 공제-해약취소 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-선수금납입 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeRess_CnclList(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeRess_CnclList(pmParam);
    }

    /**
     * 공제-해약취소 엑셀반출시 히스토리 INSERT
     *
     * @param pmParam 검색 조건
     * @return 공제-선수금납입 정보 목록
     * @throws Exception
     */
    public int getGongjeRess_CnclInsert(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeRess_CnclInsert(pmParam);
    }

    /**
     * 공제-행사취소 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-선수금납입 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeHangsa_CnclList(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeHangsa_CnclList(pmParam);
    }

    /**
     * 공제-행사취소 엑셀반출시 히스토리 INSERT
     *
     * @param pmParam 검색 조건
     * @return 공제-선수금납입 정보 목록
     * @throws Exception
     */
    public int getGongjeHangsa_CnclInsert(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeHangsa_CnclInsert(pmParam);
    }

    /**
     * 공제 - (신규/해약/정보변경/양도양수/행사) 전송처리
     *
     * @param pmParam 검색 조건
     * @return 공제 - (신규/해약/정보변경/양도양수/행사) 전송처리
     * @throws Exception
     */
    public int getGongjeNewUpdate(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeNewUpdate(pmParam);
    }

    /**
     * 공제 - (선수금) 전송처리
     *
     * @param pmParam 검색 조건
     * @return 공제 - (선수금) 전송처리
     * @throws Exception
     */
    public int getGongjePayUpdate(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjePayUpdate(pmParam);
    }

    /**
     * 공제 - (선수금취소) 전송처리
     *
     * @param pmParam 검색 조건
     * @return 공제 - (선수금) 전송처리
     * @throws Exception
     */
    public int getGongjePayCnclUpdate(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjePayCnclUpdate(pmParam);
    }

    /**
     * 공제-전송이력 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-소비자정보 변경 검색 건수
     * @throws Exception
     */
    public int getGongjeHistCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) gongjeDAO.getGongjeHistCount(pmParam);
    }

    /**
     * 공제-전송이력 변경 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-소비자정보 변경 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeHistList(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeHistList(pmParam);
    }

    /**
     * 공제-(신규/해약/정보변경/양도양수/행사) 결과처리 데이터 건수 체크
     *
     * @param pmParam 검색 조건
     * @return 결과처리 데이터 건수
     * @throws Exception
     */
    public int getGongjeResultchkCount() throws Exception {
        return (Integer) gongjeDAO.getGongjeResultchkCount();
    }

    /**
     * 공제 - (신규/해약/정보변경/양도양수/행사) 결과처리
     *
     * @param pmParam 검색 조건
     * @return 공제 - (신규/해약/정보변경/양도양수/행사) 결과처리
     * @throws Exception
     */
    public int getGongjeBaseResult() throws Exception {
        return gongjeDAO.getGongjeBaseResult();
    }

    /**
     * 공제 - (신규/해약/정보변경/양도양수/행사) 결과처리
     *
     * @param pmParam 검색 조건
     * @return 공제 - (신규/해약/정보변경/양도양수/행사) 결과처리
     * @throws Exception
     */
    public int getGongjeBaseResult_Pay() throws Exception {
        return gongjeDAO.getGongjeBaseResult_Pay();
    }

    /**
     * (신규/해약/정보변경/양도양수/행사) 각 테이블 결과처리
     *
     * @param pmParam 검색 조건
     * @return 공제 - (신규/해약/정보변경/양도양수/행사) 결과처리
     * @throws Exception
     */
    // 신규
    public int getGongjeSinResult() throws Exception {
        return gongjeDAO.getGongjeSinResult();
    }
    // 해약
    public int getGongjeRessResult() throws Exception {
        return gongjeDAO.getGongjeRessResult();
    }
    // 정보변경
    public int getGongjeChngResult() throws Exception {
        return gongjeDAO.getGongjeChngResult();
    }
    // 행사
    public int getGongjeHangsaResult() throws Exception {
        return gongjeDAO.getGongjeHangsaResult();
    }
    // 해약/행사 성공시 회원정보 업데이트
    public int getGongjeMemProdUpdate() throws Exception {
        return gongjeDAO.getGongjeMemProdUpdate();
    }
    // 양도양수
    public int getGongjeYdsResult() throws Exception {
        return gongjeDAO.getGongjeYdsResult();
    }
    // 양도양수 신고회원 공제번호 업데이트
    public int getGongjeYds_New_Result() throws Exception {
        return gongjeDAO.getGongjeYds_New_Result();
    }
    // 공제전송내역 결과처리 비트 업데이트
    public int getGongjeHistResult() throws Exception {
        return gongjeDAO.getGongjeHistResult();
    }
    // 공제결과내역 결과처리 비트 업데이트
    public int getGongjeLastResult() throws Exception {
        return gongjeDAO.getGongjeLastResult();
    }

    /**
     * 입금 테이블 결과반영
     *
     * @param pmParam 검색 조건
     * @return 공제신고비트::NEW_YN (Y -> N)
     * @throws Exception
     */
    // 입금 테이블
    public int getGongjePayResult() throws Exception {
        return gongjeDAO.getGongjePayResult();
    }

    /**
     * 업로드전 미처리 파일 체크
     * @see powerservice.business.gongje.service.GongjeService#getGongjeResultchkCount(java.util.Map)
     */
    public int getGongjeUploadchkCount() throws Exception {
        return (Integer) gongjeDAO.getGongjeUploadchkCount();
    }

    /**
     * 업로드 이후 공제진행내역 비트 업데이트(전송->결과처리진행중)
     */
    public int getGongjeUploadResult(Map<String, ?> hmParam) throws Exception {
        return gongjeDAO.getGongjeUploadResult(hmParam);
    }

    /**
     * XML 업로드(신규/해약/정보변경/양도양수/행사) - 데이터 삽입
     * @param hmParam 파일 정보
     * @throws Exception
     */
    public void insertFile(Map<String, ?> hmParam) throws Exception {

        String sInFile = CommonUtils.nvls((String)hmParam.get("fileNm"));
        String fileName = CommonUtils.nvls((String)hmParam.get("fileName"));
        long lFileSize = (Long)hmParam.get("fileSize");

        log.info("fileName:"+fileName);
        log.info("sInFile:"+sInFile);
        log.info("lFileSize:"+lFileSize);

        if (null == sInFile || lFileSize < 1) {
            return;
        }

        log.info("------------1");

        File file = new File(sInFile);
        if (!file.exists()) {
            return;
        }
        log.info("------------2");

/*        BufferedReader ib = new BufferedReader(new FileReader(file));
        String TotData = null;
        int Line = 0;

        while ((TotData = ib.readLine()) != null) {
            if(TotData.length() > 3){
                //String ExtraData = "";
                //TotData = ExtraData + TotData;
                CommonUtils.printLog("LinetData:"+TotData);
                Line ++;
            }
        }

        String St = String.valueOf(Line);
        CommonUtils.printLog("Line:"+St);*/

        DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = fact.newDocumentBuilder();
        Document doc = builder.parse(sInFile);

        log.info("------------3");

        Element rootEl = doc.getDocumentElement();
        rootEl.normalize();
        System.out.println("Root element " + rootEl.getNodeName());

        NodeList lstNode = doc.getElementsByTagName("data");
        System.out.println("########## Information of the students ###########");

        UserSessionCore oLoginUser = SessionUtils.getLoginUser();

        int iVal = 0;
        int seq = 0;
        String str = "";
        Element el = null;
        Element txtEl = null;
        Node txtNode = null;
        List<Map<String, Object>> lstRows = new ArrayList<Map<String, Object>>();

        log.info("------------4");
        log.info("lstNode.getLength() : " + lstNode.getLength());

        for (int i=0; i<lstNode.getLength(); ++i) {
            Map<String, Object> hmRow = new HashMap<String, Object>();

            Node node = lstNode.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                hmRow.put("fileName", fileName);

                el = (Element) node;
                txtEl = (Element) el.getElementsByTagName("code").item(0);
                if (null != txtEl && txtEl.getChildNodes().getLength() > 0) {
                    txtNode = txtEl.getChildNodes().item(0);
                    hmRow.put("code", txtNode.getNodeValue());
                }

                txtEl = (Element) el.getElementsByTagName("content").item(0);
                if (null != txtEl && txtEl.getChildNodes().getLength() > 0) {
                    txtNode = txtEl.getChildNodes().item(0);
                    hmRow.put("content", txtNode.getNodeValue());
                }

                txtEl = (Element) el.getElementsByTagName("gubun").item(0);
                if (null != txtEl && txtEl.getChildNodes().getLength() > 0) {
                    txtNode = txtEl.getChildNodes().item(0);
                    hmRow.put("gubun", txtNode.getNodeValue());
                }


                txtEl = (Element) el.getElementsByTagName("memb_no").item(0);
                if (null != txtEl && txtEl.getChildNodes().getLength() > 0) {
                    txtNode = txtEl.getChildNodes().item(0);
                    hmRow.put("memb_no", txtNode.getNodeValue());
                }


                txtEl = (Element) el.getElementsByTagName("rc_dt").item(0);
                if (null != txtEl && txtEl.getChildNodes().getLength() > 0) {
                    txtNode = txtEl.getChildNodes().item(0);
                    hmRow.put("rc_dt", txtNode.getNodeValue());
                }

                txtEl = (Element) el.getElementsByTagName("cons_no").item(0);
                if (null != txtEl && txtEl.getChildNodes().getLength() > 0) {
                    txtNode = txtEl.getChildNodes().item(0);
                    hmRow.put("cons_no", txtNode.getNodeValue());
                }

                txtEl = (Element) el.getElementsByTagName("guar_no").item(0);
                if (null != txtEl && txtEl.getChildNodes().getLength() > 0) {
                    txtNode = txtEl.getChildNodes().item(0);
                    hmRow.put("guar_no", txtNode.getNodeValue());
                }

                txtEl = (Element) el.getElementsByTagName("guar_dt").item(0);
                if (null != txtEl && txtEl.getChildNodes().getLength() > 0) {
                    txtNode = txtEl.getChildNodes().item(0);
                    hmRow.put("guar_dt", txtNode.getNodeValue());
                }

                txtEl = (Element) el.getElementsByTagName("work_result").item(0);
                if (null != txtEl && txtEl.getChildNodes().getLength() > 0) {
                    txtNode = txtEl.getChildNodes().item(0);
                    hmRow.put("work_result", txtNode.getNodeValue());
                }

                txtEl = (Element) el.getElementsByTagName("seq_no").item(0);
                if (null != txtEl && txtEl.getChildNodes().getLength() > 0) {
                    txtNode = txtEl.getChildNodes().item(0);
                    hmRow.put("seq_no", txtNode.getNodeValue());
                }else{
                    hmRow.put("seq_no", "");
                }

                txtEl = (Element) el.getElementsByTagName("adv_pay_num").item(0);
                if (null != txtEl && txtEl.getChildNodes().getLength() > 0) {
                    txtNode = txtEl.getChildNodes().item(0);
                    hmRow.put("adv_pay_num", txtNode.getNodeValue());
                }else{
                    hmRow.put("adv_pay_num", "");
                }

                txtEl = (Element) el.getElementsByTagName("pay_type").item(0);
                if (null != txtEl && txtEl.getChildNodes().getLength() > 0) {
                    txtNode = txtEl.getChildNodes().item(0);
                    hmRow.put("pay_type", txtNode.getNodeValue());
                }else{
                    hmRow.put("pay_type", "");
                }

                hmRow.put("rgsr_id", oLoginUser.getUserid());

                CommonUtils.printLog(i);

            }

            lstRows.add(hmRow);

            // 여기서 dao 호출하면 됨
            if (lstRows.size() >= 80) {

                CommonUtils.printLog(lstRows.get(0));

                int nResult = gongjeDAO.insertFile(lstRows);
                lstRows.clear();
            }

        }

        if (lstRows.size() > 0) {
            int nResult = gongjeDAO.insertFile(lstRows);
        }

    }

    /**
     * 선수금 엑셀 다운리스트 조회
     *
     * @param pmParam 검색 조건
     * @return 선수금 엑셀 다운리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjePayExcel(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjePayExcel(pmParam);
    }

    /**
     * 월별보고서 마감자료 선수금 조회
     *
     * @param pmParam 검색 조건
     * @return 선수금 엑셀 다운리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeMagamPay(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeMagamPay(pmParam);
    }

    /**
     * 월별보고서 마감자료 회원 조회
     *
     * @param pmParam 검색 조건
     * @return 선수금 엑셀 다운리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeMagamMem(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeMagamMem(pmParam);
    }

    /**
     * 월별 보고서 신규내역 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-신규 정보의 검색 건수
     * @throws Exception
     */
    public int getReportNewCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) gongjeDAO.getReportNewCount(pmParam);
    }

    /**
     * 월별 보고서 신규내역 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-신규 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getReportNewList(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getReportNewList(pmParam);
    }

    /**
     * 월별 보고서 해약내역 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-신규 정보의 검색 건수
     * @throws Exception
     */
    public int getReportResCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) gongjeDAO.getReportResCount(pmParam);
    }

    /**
     * 월별 보고서 해약내역 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-신규 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getReportResList(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getReportResList(pmParam);
    }

    /**
     * 월별 보고서 행사내역 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-신규 정보의 검색 건수
     * @throws Exception
     */
    public int getReportEvntCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) gongjeDAO.getReportEvntCount(pmParam);
    }

    /**
     * 월별 보고서 행사내역 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-신규 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getReportEvntList(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getReportEvntList(pmParam);
    }

    /**
     * 월별 보고서 기타 환불내역 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-신규 정보의 검색 건수
     * @throws Exception
     */
    public int getReportCnclCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) gongjeDAO.getReportCnclCount(pmParam);
    }

    /**
     * 월별 보고서 기타 환불내역 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-신규 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getReportCnclList(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getReportCnclList(pmParam);
    }

    /**
     * 월별 보고서 CMS 이체내역 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-신규 정보의 검색 건수
     * @throws Exception
     */
    public int getGongjeReportCmsPayCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) gongjeDAO.getGongjeReportCmsPayCount(pmParam);
    }

    /**
     * 월별 보고서 CMS 이체내역 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-신규 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeReportCmsPayList(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeReportCmsPayList(pmParam);
    }

    /**
     * 월별 보고서 현금 등 기타내역 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-신규 정보의 검색 건수
     * @throws Exception
     */
    public int getGongjeReportOtPayCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) gongjeDAO.getGongjeReportOtPayCount(pmParam);
    }

    /**
     * 월별 보고서 현금 등 기타내역 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-신규 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeReportOtPayList(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeReportOtPayList(pmParam);
    }

    /**
     * 월별 보고서 누적 선수금내역 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-신규 정보의 검색 건수
     * @throws Exception
     */
    public int getGongjeReportTotalPayCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) gongjeDAO.getGongjeReportTotalPayCount(pmParam);
    }

    /**
     * 월별 보고서 누적 선수금내역 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 공제-신규 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getGongjeReportTotalPayList(Map<String, ?> pmParam) throws Exception {
        return gongjeDAO.getGongjeReportTotalPayList(pmParam);
    }

}
