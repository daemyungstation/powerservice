/*
 * (@)# DlwCardService.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/02
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

package powerservice.business.dlw.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.nicepay.module.lite.NicePayWebConnector;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwCardService;
import powerservice.business.sys.service.BasVlService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.NicePayProcess;
import powerservice.core.util.ParamUtils;
import au.com.bytecode.opencsv.CSVReader;

import com.oreilly.servlet.MultipartRequest;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;
/**
 * 대명라이프웨이 Card 정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/02
 * @프로그램ID DlwCard
 */
@Service
public class DlwCardServiceImpl extends EgovAbstractServiceImpl implements DlwCardService {

    private final Logger log = LoggerFactory.getLogger(DlwCardServiceImpl.class);

    @Resource
    public DlwCardDAO DlwCardDAO;

    @Resource
    public DlwCmsDAO DlwCmsDAO;

    @Resource
    public DlwConsProdDAO dlwConsProdDAO;

    @Resource
    public BasVlService basVlService;


    public void insertCARDErrorInfo(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResult) throws Exception {

        List<Map<String, Object>> lstRows = new ArrayList<Map<String, Object>>();
        String sFileKey = "";
        String tempDir = System.getProperty("java.io.tmpdir");

        log.info("---insertCARDErrorInfo 서비스 - 1");
        MultipartRequest multipartRequest = new MultipartRequest(request, tempDir, 1024*1024*200);
        Enumeration enm = multipartRequest.getFileNames();
        File upfile = null;

        InputStream is 			= null;
        InputStreamReader isr 	= null;
        BufferedReader br 		= null;

        try {
            log.info("---insertCARDErrorInfo 서비스 - 2");

            // 실제로는 단건만 처리함
            while (enm.hasMoreElements())
            {
                sFileKey = (String)enm.nextElement();
                log.info("sFileKey : " + sFileKey);
                upfile = multipartRequest.getFile(sFileKey);
                String sOrigFn = upfile.getName();
                mResult.put("orig_file_nm", sOrigFn);

                log.info("upfile.getPath() : " + upfile.getPath());

                is = new FileInputStream(upfile);
                isr = new InputStreamReader(is, "EUC-KR");
                br = new BufferedReader(isr);
                HashMap<String,Object> mRow = null;
                String sLine;

                CSVReader reader = new CSVReader(br);

                String[] sArr;
                String colId = "";
                int lineNo=0, j=0;
                String biky = "";

                log.info("---insertCARDErrorInfo 서비스 - 3");
                while ((sArr = reader.readNext()) != null) {
                    lineNo++;
                    if (lineNo < 2) {
                        continue;
                    }

                    mRow = new HashMap<>();

                    biky = "";

                    for (j=0; j<sArr.length; ++j) {
                        sArr[j] = sArr[j].trim();

                        colId = "Col" + CommonUtils.lpad((j+1)+"", 2, "0");

                        if ("Col16".equals(colId)) {
                            mRow.put(colId, sArr[j].substring(0,1));
                        }
                        else if ("Col02".equals(colId)) {
                            biky = CommonUtils.nvls(sArr[j]);
                            mRow.put(colId, biky);
                        }
                        else {
                            mRow.put(colId, CommonUtils.nvls(sArr[j]));
                        }
                    }

                    mRow.put("filename",CommonUtils.nvls((String)mResult.get("filename")));
                    ParamUtils.addSaveParam(mRow);

                    if (lineNo>1 && lineNo%100 == 0) {
                        log.info("---insertCARDErrorInfo, lineNo : " + lineNo);
                        if (log.isDebugEnabled()) {
                         //   CommonUtils.printLog(mRow);
                            log.debug("================================================");
                        }
                    }

                    if (null == biky || "".equals(biky)) {
                        continue;
                    }

                    lstRows.add(mRow);

                    /* 여기서 dao 호출하면 됨 */
                    if (lstRows.size() >= 80) {
                        int nResult =  DlwCardDAO.insertCARDErrorInfo(lstRows);
                        lstRows.clear();
                    }
                } // end while

                log.info("---insertCARDErrorInfo 서비스 - 4");

                if (lstRows.size() > 0) {
                    log.info("---insertCARDErrorInfo 서비스 - 5");
                    int nResult =  DlwCardDAO.insertCARDErrorInfo(lstRows);
                }

                log.info("---insertCARDErrorInfo 서비스 - 6");

                if (null != mRow) {
                    log.info("---insertCARDErrorInfo 서비스 - 7");
                    /* 결과 비트 업데이트 stat =05 */
                    DlwCardDAO.card_wdrw_update_stat(mRow);
                }

                log.info("---insertCARDErrorInfo 서비스 - 8");

                break; // 파일은 1개만 업로드 한다.
            }
        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (IOException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (null != br) br.close();
            if (null != isr) isr.close();
            if (null != is) is.close();

            if (null != upfile && upfile.exists()) {
                upfile.delete();
            }
        }

        log.info("---insertCARDErrorInfo 서비스 - 실행완료");
    }

    /**
     * 대명라이프웨이의 Card 회원정보의 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 건수
     * @throws Exception
     */
    public int getDlwCardCsmmCount(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getDlwCardCsmmCount(pmParam);
    }


    public int getCardWdrwReqcount(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getCardWdrwReqcount(pmParam);
    }

    public int getReadFileNiceCardcount(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getReadFileNiceCardcount(pmParam);
    }


    /**
     * 대명라이프웨이의 Card 회원정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCardCsmm(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getDlwCardCsmm(pmParam);
    }

    /**
     * 대명라이프웨이의 회원별 Card 신청내역의 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 신청내역 정보 건수
     * @throws Exception
     */
    public int getDlwCardRgsnHstrCount(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getDlwCardRgsnHstrCount(pmParam);
    }

    /**
     * 대명라이프웨이의 회원별 Card 신청내역 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 신청내역 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCardRgsnHstrList(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getDlwCardRgsnHstrList(pmParam);
    }

    /**
     * 대명라이프웨이의 회원별 Card 출금의뢰내역의 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 출금의뢰내역 정보 건수
     * @throws Exception
     */
    public int getDlwCardWdrwHstrCount(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getDlwCardWdrwHstrCount(pmParam);
    }

    /**
     * 대명라이프웨이의 회원별 Card 출금의뢰내역 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 출금의뢰내역 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCardWdrwHstrList(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getDlwCardWdrwHstrList(pmParam);
    }

    /**
     * 대명라이프웨이의 Card 산출중 여부 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 산출 여부 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCardWdrwChk(Map<String, Object> pmParam) throws Exception {
        return DlwCardDAO.getDlwCardWdrwChk(pmParam);
    }

    /**
     * 대명라이프웨이의 Card 중복 여부 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 중복 여부 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCardDplcChk(Map<String, Object> pmParam) throws Exception {
        return DlwCardDAO.getDlwCardDplcChk(pmParam);
    }


    /**
     * 대명라이프웨이 Card 이체정보를 수정한다.
     *
     * @param pmParam 검색 조건
     * @return 수정결과
     * @throws Exception
     */
    public int updateCardTranInfo(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.updateCardTranInfo(pmParam);
    }

    /**
     * 대명라이프웨이의 Card 금일 등록이력 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 등록이력 정보 건수
     * @throws Exception
     */
    public int getDlwCardAplcDtlCount(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getDlwCardAplcDtlCount(pmParam);
    }

    /**
     * 대명라이프웨이의 Card 금일 등록이력을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 등록이력 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCardAplcDtl(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getDlwCardAplcDtl(pmParam);
    }

    /**
     * 대명라이프웨이의 Card(신규,해지)를 등록한다.
     *
     * @param pmParam 검색 조건
     * @return 등록결과
     * @throws Exception
     */
    public String insertDlwCardAnxtSrvc(Map<String, Object> pmParam) throws Exception {
        String rslt = "";

        //Card 회원여부 검사
        	//20180719 필요없어서 수정(신규 카드로 이미 등록하지 않은 회원으로 들어온 경우임)
        String dltnFlag = DlwCardDAO.getDltnFlagCardMmbr(pmParam);
        //String dltnFlag = "O";

        //베스트라이프 상품 검사
        	//20180719 필요없어서 수정(베스트라이프 상품의 경우 현재 사용 안하는거나 마찬가지)
        	//String lgBestDiv =  DlwCardDAO.getLgBestDiv(pmParam);
        String lgBestDiv =  "N";

        //카드 신규 등록 (1 => 신규, 3 => 해지)
        String app_cl = CommonUtils.checkNull((String)(pmParam.get("app_cl")));//처리구분
        String billKeyArr[] = new String[5];//빌키

        String idn_no = CommonUtils.checkNull((String)pmParam.get("idn_no"));
        String email = CommonUtils.checkNull((String)pmParam.get("email"));
        String cell = CommonUtils.checkNull((String)pmParam.get("cell")).replaceAll("-", "");
        String prdt_cd = CommonUtils.checkNull((String)pmParam.get("prdt_cd"));
        String prdt_nm = CommonUtils.checkNull((String)pmParam.get("prdt_nm"));
        String mem_nm = CommonUtils.checkNull((String)pmParam.get("mem_nm"));

        if ("1".equals(app_cl)) { // 신규일경우
            //NicePay 모듈연동 관련 요소확인
            if("".equals(idn_no) || "".equals(email) || "".equals(cell)
                                || "".equals(prdt_nm) || "".equals(mem_nm)) {
                //빈값 있을경우 생성정보 조회

            	//과거 주민번호 가지고 오는 로직 (20180719)
                List<Map<String, Object>> tmpList = DlwCardDAO.getBillKeyCrtnInfo(pmParam);
                Map<String, Object> tmpMap = (Map<String, Object>)tmpList.get(0);

                //위의값 비어있을경우 생성정보조회 값으로 채움
                if ("".equals(idn_no)) {
                    pmParam.put("idn_no", String.valueOf(tmpMap.get("idn_no")));
                }
                if ("".equals(email)) {
                    pmParam.put("email", String.valueOf(tmpMap.get("email")));
                }
                if ("".equals(cell)) {
                    pmParam.put("cell", String.valueOf(tmpMap.get("cell")));
                }
                pmParam.put("prod_nm", String.valueOf(tmpMap.get("prod_nm")));
                pmParam.put("prod_cd", String.valueOf(tmpMap.get("prod_cd")));
                pmParam.put("mem_nm", String.valueOf(tmpMap.get("mem_nm")));

                /******************************************************/
                /* 카드 부가서비스 등록 시 본인 확인 체크 부분*/
                /******************************************************/
                //Nicepay 객체에 값 set
                NicePayProcess nicepay = new NicePayProcess();

                ParamUtils.addCenterParam(pmParam);

                String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", (String) pmParam.get("cntr_cd"));

                nicepay.setNicePayHome(sPayFilePath+"/web_site/nicelog");
                nicepay.setGoodsName((String)pmParam.get("prod_cd"));
                nicepay.setMoid((String)pmParam.get("accnt_no"));
                nicepay.setBuyerName((String)pmParam.get("mem_nm"));
                nicepay.setBuyerTel((String)pmParam.get("cell"));
                nicepay.setBuyerEmail((String)pmParam.get("email"));
                nicepay.setCardNumber((String)pmParam.get("card_no"));
                nicepay.setExpYear((String)pmParam.get("exp_year"));
                nicepay.setExpMonth((String)pmParam.get("exp_mon"));
                nicepay.setIdno((String)pmParam.get("idn_no"));

                //승인요청
                NicePayWebConnector connector = nicepay.requestCardMemAuth();
                String resultCode = connector.getResultData("ResultCode");
                String resultMsg = connector.getResultData("ResultMsg");
                String cardCode = connector.getResultData("CardCode");
                String cardName = connector.getResultData("CardName");

                System.out.println("resultCode : " + resultCode);
                System.out.println("resultMsg : " + resultMsg);
                System.out.println("cardCode : " + cardCode);
                System.out.println("cardName : " + cardName);


                //정상여부
                if(!"0000".equals(resultCode) && (!"5012".equals(resultCode) || !"Y".equals(lgBestDiv))) {
                    //정상결과 아닐경우 결과코드 출력
                    rslt = resultMsg;
                } else {
                    if("N".equals(dltnFlag)) {
                        //이미 등록된 회원입니다.
                        rslt = "overlap";
                    } else {
                        //app_cl >> 1
                        //acpt_mthd >> 06
                        billKeyArr = billKeyCreate(pmParam);
                        pmParam.put("bidRtCd", billKeyArr[0]);
                        pmParam.put("bid", billKeyArr[1]);
                        pmParam.put("bidMsg", billKeyArr[2]);

                        if("F100".equals(billKeyArr[0])) {
                            if("O".equals(dltnFlag)) {
                                DlwCardDAO.insertDlwCardMmbr(pmParam); // Card 회원 신규등록
                            } else if ("Y".equals(dltnFlag)) {
                                DlwCardDAO.updateDlwCardMmbr(pmParam); // Card 기존 회원정보 수정
                            }
                            DlwCardDAO.insertDlwCardAnxtSrvc(pmParam); // Card 정보등록
                            DlwCardDAO.updateDlwCardPymtMthd(pmParam); // Card 납입방법 수정 - 엔컴
                            //MemProdAccntDAO.updateCardPymtMthd(pmParam); // Card 납입방법 수정 - 인우
                            rslt = "success";

                            /* DlwCardDAO.updateDlwCardRprvPymt()를 4개의 메소드로 분리함
                             * 2016.10.12 - 정출연
                            int result = DlwCardDAO.updateDlwCardRprvPymt(pmParam);
                            if(result > 0) {
                                rslt = "proxy";
                            }
                            */
                            Map<String, Object> mBrth = DlwCardDAO.getCardAndMemBrth(pmParam);
                            if (null != mBrth) {
                                String cardBirth 	= CommonUtils.nvls((String)mBrth.get("card_birth"));
                                String memBirth 	= CommonUtils.nvls((String)mBrth.get("mem_birth"));
                                if (!cardBirth.equals(memBirth)) {
                                    int hpCallCnt = DlwCardDAO.getHpCallCnt(pmParam);
                                    if (hpCallCnt > 0) {
                                        DlwCardDAO.updateHpCall(pmParam);
                                    } else {
                                        DlwCardDAO.insertHpCall(pmParam);
                                    }

                                    int result = DlwCardDAO.insertHpCallHist(pmParam);
                                    if(result > 0) {
                                        rslt = "proxy";
                                    }
                                }
                            }

                        } else {
                            rslt = billKeyArr[2];
                        }
                    }
                }
            }
        } else { //해지일 경우
            if("N".equals(dltnFlag)) {
                //app_cl >> 3
                //acpt_mthd >> 01
                DlwCardDAO.insertDlwCardAnxtSrvc(pmParam); // Card 정보등록
                DlwCardDAO.deleteDlwCardMmbr(pmParam);	   // Card 회원 삭제
                DlwCardDAO.updateDlwCardPymtMthd(pmParam); // Card 납입방법 수정 - 엔컴
                //MemProdAccntDAO.updateCardPymtMthd(pmParam); // Card 납입방법 수정 - 인우
                rslt = "success";
            } else {
                rslt = "empty";
            }
        }
        return rslt;
    }

    /**
     * 대명라이프웨이 여신 로그정보의 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 여신 로그정보의 건수
     * @throws Exception
     */
    public int getDlwRltmYeosinLogCount(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getDlwRltmYeosinLogCount(pmParam);
    }

    /**
     * 대명라이프웨이 여신 로그정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 여신 로그정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwRltmYeosinLogList(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getDlwRltmYeosinLogList(pmParam);
    }


    /**
     * 대명라이프웨이 실시간카드결제 로그정보의 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보의 건수
     * @throws Exception
     */
    public int getDlwRltmCardLogCount(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getDlwRltmCardLogCount(pmParam);
    }

    /**
     * 대명라이프웨이 실시간카드결제 로그정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwRltmCardLogList(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getDlwRltmCardLogList(pmParam);
    }

    /**
     * 대명라이프웨이 나이스페이 실시간결제 취소
     *
     * @param pmParam 검색 조건
     * @return 취소여부
     * @throws Exception
     */
    public Map<String, Object> updateDlwPymnCancNicepay(Map<String, Object> pmParam) throws Exception {
        String result_code = "";
        String result_msg = "";
        String tmp_pay_no = String.valueOf((Integer.parseInt(String.valueOf(pmParam.get("pay_no"))) + Integer.parseInt(String.valueOf(pmParam.get("pay_cnt")))) - 1);
        String menu_gbn = String.valueOf(pmParam.get("menu_gbn"));
        String filePayChkFg = "Y";

        if(!"Y".equals(filePayChkFg)) {
            pmParam.put("pay_result_msg", "파일결제의 입금일이 잘못되어 취소할 수 없습니다.");
        } else {
            Map<String, Object> cancResult = new HashMap();
            cancResult = cancelBillPay(pmParam);

            result_code = String.valueOf(cancResult.get("result_code"));
            result_msg = String.valueOf(cancResult.get("result_msg"));
            String cncl_day = String.valueOf(cancResult.get("cncl_day"));
            String cncl_tm = String.valueOf(cancResult.get("cncl_tm"));
            String uip = String.valueOf(cancResult.get("uip"));
            if("2001".equals(result_code))
            {
                pmParam.put("result_code", "03");
                "0002".equals(menu_gbn);
                pmParam.put("uip", uip);
                pmParam.put("result_msg", result_msg);
                pmParam.put("cncl_day", cncl_day);
                pmParam.put("cncl_tm", cncl_tm);
                pmParam.put("cncl_day", cncl_day);
                pmParam.put("type", "cancel");
                DlwCardDAO.updateDlwCardAckdCancLog(pmParam);
                if("0003".equals(menu_gbn) || "0004".equals(menu_gbn))
                {
                    pmParam.put("pay_no", tmp_pay_no);
                    DlwCardDAO.updateDlwCardWdrwReqCanc(pmParam);
                }
            } else{
                pmParam.put("result_code", "01");
                pmParam.put("result_msg", result_msg);
                pmParam.put("type", "cancel_fail");
            }
        }
        return pmParam;
    }

    /**
     * 대명라이프웨이 빌키있는 회원인지 조회
     *
     * @param pmParam 검색 조건
     * @return 빌키 여부
     * @throws Exception
     */
    public String getChkBid(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getChkBid(pmParam);
    }

    /**
     * 대명라이프웨이 구좌별 Card 정보조회
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보
     * @throws Exception
     */
    public List<Map<String, Object>> getCardInfoByAccnt(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getCardInfoByAccnt(pmParam);
    }

    /**
     * 대명라이프웨이 실시간 카드결제 - 대상회원 상태 조회
     *
     * @param pmParam 검색 조건
     * @return  대상회원 상태 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getAccntStat(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getAccntStat(pmParam);
    }

    /**
     * 대명라이프웨이 실시간 카드결제 - 신청 로그 기록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertRTCardPayLog(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.insertRTCardPayLog(pmParam);
    }

    /**
     * 대명라이프웨이 실시간 카드결제 - 결제 성공여부 로그 기록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateRTCardPayResult(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.updateRTCardPayResult(pmParam);
    }

    /**
     * 대명라이프웨이 실시간 카드결제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public Map<String, Object> insertRealTimeNicePay(Map<String, Object> pmParam) throws Exception {
        String resultCode = "99";
        String resultMsg = "fail";
        String accntNo = (String)pmParam.get("accnt_no");
        String payAmt = (String)pmParam.get("pay_amt");
        String memNm = (String)pmParam.get("mem_nm");
        String email = (String)pmParam.get("email");
        String cell = (String)pmParam.get("cell");
        String prodNm = (String)pmParam.get("prod_nm");
        String cardQuota = (String)pmParam.get("card_quota");
        String cardNumber = (String)pmParam.get("card_no");
        String expYear = (String)pmParam.get("exp_year");
        String exprMonth = (String)pmParam.get("exp_mon");
        String cardIdNo = (String)pmParam.get("card_idn_no");
        String cardPw = (String)pmParam.get("card_pw");
        String cardcd = (String)pmParam.get("card_cd");
        NicePayProcess nicepay = new NicePayProcess();
        try
        {
            nicepay = niceBillSetting(pmParam,"Real_Key");
            nicepay.setAmt(payAmt);
            nicepay.setMoid(accntNo);
            nicepay.setBuyerName(memNm);
            nicepay.setBuyerEmail(email);
            nicepay.setBuyerTel(cell);
            nicepay.setGoodsName(prodNm);
            nicepay.setCardNumber(cardNumber);
            nicepay.setExpYear(expYear);
            nicepay.setExpMonth(exprMonth);
            nicepay.setCardIdNo(cardIdNo);
            nicepay.setCardPw(cardPw);
            nicepay.setCardQuota(cardQuota);
            NicePayWebConnector result = nicepay.doPay();
            resultCode = result.getResultData("ResultCode");
            resultMsg = result.getResultData("ResultMsg");
            String tid = result.getResultData("TID");
            String authDate = result.getResultData("AuthDate");
            String authCode = result.getResultData("AuthCode");
            String cardcode = result.getResultData("CardCode");
            InetAddress inet = InetAddress.getLocalHost();
            String uip = inet.getHostAddress();
            pmParam.put("accnt_no", accntNo);
            pmParam.put("uip", uip);
            pmParam.put("result_cd", resultCode);
            pmParam.put("result_msg", resultMsg);
            pmParam.put("tid", tid);
            pmParam.put("auth_dt", authDate);
            pmParam.put("auth_cd", authCode);
            pmParam.put("card_cd", cardcode);
            //pmParam.put("upd_man", cmf.getSession("EMPLE_NO"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return pmParam;
    }

    /**
     * 대명라이프웨이 실시간 카드결제 (자유결제) - 대상회원 상태 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getAccntStatByFreeCard(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getAccntStatByFreeCard(pmParam);
    }

    /**
     * 대명라이프웨이 Card 신청내역조회
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보
     * @throws Exception
     */
    public List<Map<String, Object>> getCardWdrwReqList(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getCardWdrwReqList(pmParam);
    }

    public List<Map<String, Object>> getCardWdrwReqList_sum(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getCardWdrwReqList_sum(pmParam);
    }



    public List<Map<String, Object>> getCardWdrwReqList1(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getCardWdrwReqList1(pmParam);
    }


    /**
     * 대명라이프웨이 할부개월수 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateInstallPeriodForCard(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.updateInstallPeriodForCard(pmParam);
    }

    /**
     * 대명라이프웨이 청구 취소
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteAllCardWdrwTemp(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.deleteAllCardWdrwTemp(pmParam);
    }

    /**
     * 대명라이프웨이 카드 배치에 따른 입금 완료 처리
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateCardWdrwReq(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.updateCardWdrwReq(pmParam);
    }

    /**
     * 대명라이프웨이 중복체크
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보
     * @throws Exception
     */
    public List<Map<String, Object>> getCardCallDupWdrw(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getCardCallDupWdrw(pmParam);
    }

    /**
     * 대명라이프웨이 중복체크
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보
     * @throws Exception
     */
    public int getCallCardWdrwReqCheck(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getCallCardWdrwReqCheck(pmParam);
    }

    /**
     * 대명라이프웨이 실시간 카드결제 - 신청 로그 기록 (대량건)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertRTCardPayLogLrqnt(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.insertRTCardPayLogLrqnt(pmParam);
    }

    /**
     * 대명라이프웨이 카드 출금이체의뢰 내역 신청상태 변경 (대량건)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateCardAppStateLrqnt(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.updateCardAppStateLrqnt(pmParam);
    }

    /**
     * 대명라이프웨이 NICE 카드 결과 파일명 조회
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보
     * @throws Exception
     */
    public List<Map<String, Object>> getReadNiceCardResultFileName(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getReadNiceCardResultFileName(pmParam);
    }

    /**
     * 대명라이프웨이 NICE 파일변환 가능 유무 체크
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보
     * @throws Exception
     */
    public String getIsTransByCardNice(Map<String, Object> pmParam) throws Exception{
        return DlwCardDAO.getIsTransByCardNice(pmParam);
    }

    /**
     * 대명라이프웨이 NICE 카드 결과 파일 정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getReadFileNiceCard(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getReadFileNiceCard(pmParam);
    }


    public void getCARDAppIsTransEb22(Map<String, ?> pmParam) throws Exception {

        DlwCardDAO.getCARDAppIsTransEb22(pmParam);
    }


    /**
     * 대명라이프웨이 NICE 카드 결과 업데이트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> niceCardResultProc(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.niceCardResultProc(pmParam);
    }




    /************************************
     * NICEPAY 결제관련 함수
    ************************************/

    /**
     * NicePay Card 빌키 생성
     *
     * @param pmParam 검색 조건
     * @return 빌키생성 정보
     * @throws Exception
     */
    public String[] billKeyCreate(Map<String, Object> pmParam) throws Exception {
        String rtVal[] = new String[5];
        NicePayProcess nicepay = new NicePayProcess();
        String prod_cd = (String)pmParam.get("prod_cd");
        String menu_gbn = (String)pmParam.get("menu_gbn");
        String email = "lifeway@daemyung.com";
        String tmpEmail = CommonUtils.checkNull((String)pmParam.get("email"));

        /*20180723 37회 SDP카드변경 이슈로 인한 추가*/
        String pay_No = (String)pmParam.get("pay_no");

        if("".equals(tmpEmail)) {
            email = tmpEmail;
        }

        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("prod_cd", prod_cd);

        /*20180723 37회 SDP카드변경 이슈로 인한 추가*/
        hm.put("pay_no", pay_No);

        if(menu_gbn != null) {
            hm.put("menu_gbn", menu_gbn);
        } else {
            hm.put("menu_gbn", "");
        }

        //nicepay = niceBillSetting(hm);

        nicepay = niceBillSetting(hm,"billKeyCreate");
        nicepay.setMoid((String)pmParam.get("accnt_no"));
        nicepay.setBuyerName((String)pmParam.get("mem_nm"));
        nicepay.setBuyerEmail(email);
        nicepay.setBuyerTel((String)pmParam.get("cell"));
        nicepay.setGoodsName((String)pmParam.get("prod_nm"));
        nicepay.setCardNumber((String)pmParam.get("card_no"));
        nicepay.setExpYear((String)pmParam.get("exp_year"));
        nicepay.setExpMonth((String)pmParam.get("exp_mon"));
        nicepay.setCardIdNo("");
        nicepay.setCardPw("");
        nicepay.setIdno((String)pmParam.get("idn_no"));

        NicePayWebConnector result = nicepay.requestBillKey();
        for(int i = 0; i < 5; i++) {
            rtVal[i] = "";
        }
        rtVal[0] = result.getResultData("ResultCode");
        rtVal[1] = result.getResultData("BID");
        rtVal[2] = result.getResultData("ResultMsg");
        rtVal[3] = result.getResultData("CardCode");
        rtVal[4] = result.getResultData("CardName");
        return rtVal;
    }

    /**
     * NicePay Card 빌키 생성전 설정
     *
     * @param pmParam 검색 조건
     * @return 빌키 생성 설정
     * @throws Exception
     */
    //public NicePayProcess niceBillSetting(Map hm) throws Exception {

    // 2017.11.17 김찬영 CV(dmlife010m), CU(dmlife011m)추가
    // 2017.12.08 정승철 CR(dmlife013m), CS(dmlife014m)추가 (신규)
    // 2018.05.29 정승철 EC(dmlife017m), EE(dmlife018m), EG(dmlife019m)추가 (신규)
    // 2018.06.14 정승철 EK(dmlife014m)  추가
    public NicePayProcess niceBillSetting(Map<String, Object> pmParam, String gbn) throws Exception {
        NicePayProcess nicepay = new NicePayProcess();

        ParamUtils.addCenterParam(pmParam);

        String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", (String) pmParam.get("cntr_cd"));

        nicepay.setNicePayHome(sPayFilePath+"/web_site/nicelog");

        String menu_gbn = String.valueOf(pmParam.get("menu_gbn"));
        String prod_cd = String.valueOf(pmParam.get("prod_cd"));
        String bid = String.valueOf(pmParam.get("bid"));

        int pay_No = Integer.valueOf(String.valueOf(pmParam.get("pay_no"))).intValue();

        //MID 및 KEY값 DB에서 가져오기
        String strMidKey = DlwCardDAO.getAccntMid(pmParam);
        String mid = strMidKey.substring(0,10);  //mid
        String key = strMidKey.substring(10);	 //key

        System.out.println("pay_no   xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>  :" + pay_No);
        System.out.println("bid      xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>  :" + bid);
        System.out.println("menu_gbn xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>  :" + menu_gbn);
        System.out.println("mid      xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>  :" + mid);
        System.out.println("key      xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>  :" + key);

        //String mid = "dmlife001m";
        //String key = "Zq7E5qjYbqPMZWHvlZtt1HbC8jJIPfAkVkkGOvf7gRoKfweOMCiHu/VPob5uGrIDyYQyplxnknTXxX2D8F+emA==";

        /*
        if(!"0005".equals(menu_gbn)) {
        	//SDP 또는 포인트 지급 상품의 경우 37회부터는 카드사 변경이 가능하도록 처리(가맹번호변경 필수)
        	if (pay_No < 36) {
	            if("F4".equals(prod_cd) || "M2".equals(prod_cd) || "AO".equals(prod_cd) || "CO".equals(prod_cd) || "CP".equals(prod_cd))
	            {
	                mid = "dmlife002m";
	                key = "+hI3VjJVIXUcZT4Y3k8rePsCCrbp36dcVbaH895nB6qrYMeTKVDZBdZwLQ0FSNk93PqqHnmsJqEqG91aZTAfaw==";
	            } else if("M5".equals(prod_cd)) {
	                mid = "dmlife003m";
	                key = "ox+eWu3qTK19ZSCPo8sU0W7+h2fKz7qHQu1HSDR4RNnKRfm2c+LaPJ6b1BqSPlhWjGVCBe6Zjm5SBUtNQM67sA==";
	            } else if("S7".equals(prod_cd)) {
	                mid = "dmlife005m";
	                key = "XTsER/pjuy4AFPwWz1P5xXMmu7JmyEA6bobVFesNozl4AGWbyhOG7PgQDdhXWx61MJUnCL0OHKC3jalZZ89khQ==";
	            } else if("S8".equals(prod_cd)) {
	                mid = "dmlife006m";
	                key = "k4JtQkTfLNTz/cB6QnUV+CGcKrFsU6fxROBu0v64ZphQ/w+uf8SvpkXVDdWFuFzMyOnp/o89Z+OxoRBnkXlqyg==";
	            } else if("X5".equals(prod_cd)) {
	                mid = "dmlife007m";
	                key = "RKuYIPDVwOZoicuvtg8Qyh4qrHxqQd3PpMsjLaAh7GhBIjDvANcgHKm1N0CtI8xdaLeeU9hinc1R5agt81EExg==";
	            } else if("AN".equals(prod_cd)) {
	                mid = "dmlife008m";
	                key = "GmKDffUHpFezZrs9y0vnB+cDncdk0ZsaGScad+gB9IBXF6bvuxIjrFHuofVJfFTV5QgWVklQuFAKc//0hTktBg==";
	            } else if("CA".equals(prod_cd)) {
	                mid = "dmlife009m";
	                key = "ncU7t6i/R4lFGDAjw8tdonTmhPTC64tcCD7OW+HBBgWqJzMsHxJl8kcy8hyi48H7rCajKjIg4mVUn2Ne8KRAvw==";
	            } else if("CE".equals(prod_cd) || "CV".equals(prod_cd)) {
	                mid = "dmlife010m";
	                key = "oz3Z2fJw1wHaWtblc3q9CgXtGLApayO+WuckiTQyyIxNBhE8ffMwFHOUY0gsBxKoNUl7R52aBIsSXNPK6uOgSQ==";
	            } else if("CF".equals(prod_cd) || "CU".equals(prod_cd)) {
	                mid = "dmlife011m";
	                key = "9vA1ez75mgZml7qfk7woKk6asXc8zMn4Iu/dYB7RLcD0VrF/lRjQ7Or0LSxwVE7dUnaugieOu+6JPLrSu7RELQ==";
	            } else if("CM".equals(prod_cd)) {
	                mid = "dmlife012m";
	                key = "DDvdhX0xmzlQRw6gDdlardjxY6gYRDLwYVhUnYjk9OD0lDF9whkGEnKsbrs9IlzTxUIQec1kEM6yI45eqL7Kuw==";
	            } else if("CR".equals(prod_cd)) {
	                mid = "dmlife013m";
	                key = "fQgDLnvCBxAofixCWMvMOhKaKnOsFbV2bCH6EeweqIXdprAtfFcJYKfQzDvcz5xkqgot3LcZBdrtZkiEiBFCKw==";
	            } else if("CS".equals(prod_cd) || "EK".equals(prod_cd)) {
	                mid = "dmlife014m";
	                key = "HGzUK72ACbR4h2X6WCxdS6refkyti6o3Mf4tfE/Uv2IbmSOfxPrIqA7xv+CvV7opSBRDTvmI6kg6Tx3ILlnVnw==";
	            } else if("EC".equals(prod_cd)) {
	                mid = "dmlife017m";
	                key = "UBL/R7Tis7w9+MnAthWjX1cWiWAGIB+kmXTlO9gddUGHn7Jp1uH3wc2Nx80jgRfTTfipFm5cGfNEC7IcYMCSHw==";
	            } else if("EE".equals(prod_cd)) {
	                mid = "dmlife018m";
	                key = "TpO4UZ8zPeVkwFINFe2f7yeMNgXDmUa/pHdQMYS+kdARDQ2wUoVAI4G3+IsLOTkV7g6p+erd8sqRlAHpz9CrQw==";
	            } else if("EG".equals(prod_cd)) {
	                mid = "dmlife019m";
	                key = "4qCtBB8Bi3wwTUiwpqwfjLSsajZVnvQNj8MrZGfYKhpAf09r6BewAaK4v4iml/XMRxC1M8zsPY16uAfq/4lZPg==";
	            }
        	}
        }
        */

        /*
        if("Test_billKeyCreate".equals(gbn)) {
            //테스트 - 빌키생성 시
            mid = "nictest14m";
            key = "TPP4afX4e5US6FEl0MnoyRHT/yzTRZVrKGJVBmew66y8jSDOt5ZNigM0DM/WZdYbev7OV/lTUEewzhq5dqKygg==";
        }
        if("Test_KeyIn".equals(gbn)) {
            //테스트 - KEY-IN
            //         카드번호 + 유효기간 + 비밀번호 + 생년월일/사업자번호 >>> [주민번호 뒷 7자리 X]
            mid = "nictest04m";
            key = "b+zhZ4yOZ7FsH8pm5lhDfHZEb79tIwnjsdA0FBXh86yLc6BJeFVrZFXhAoJ3gEWgrWwN+lJMV0W4hvDdbe4Sjw==";
        }

        if("Test_KeyIn2".equals(gbn)) { // 자유결제시?
            //테스트 - KEY-IN
            //         카드번호 + 유효기간 + 생년월일
            mid = "nictest34m";
            key = "yO1t6MosWKmJxOAnD5/F50QqC5Lobf4MooTTSqqofZqbUxKKizTe5OWvbdtD8ybi3NEyK4EDxBQrgq6f3Y38Aw==";
        }
        if("Test_KeyIn3".equals(gbn)) {
            //테스트 - 일반결제용(결제창 사용)
            mid = "nictest00m";
            key = "33F49GnCMS1mFYlGXisbUDzVf2ATWCl9k3R++d5hDd3Frmuos/XLx8XhXpe+LDYAbpGKZYSwtlyyLOtS/8aD7A==";
        }
        if ("Real_Key".equals(gbn)) {
            mid = "dmlife001m";
            key = "Zq7E5qjYbqPMZWHvlZtt1HbC8jJIPfAkVkkGOvf7gRoKfweOMCiHu/VPob5uGrIDyYQyplxnknTXxX2D8F+emA==";
        }
        */

        nicepay.setMID(mid);
        nicepay.setMerchantKey(key);

        return nicepay;
    }

    /**
     * NicePay Card 빌링결제 (자유금액)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public Map<String, Object> billPayFreeProc(Map<String, Object> pmParam) throws Exception {
        //빌키생성정보 조회
        List<Map<String, Object>> tmpList = DlwCardDAO.getBillKeyCrtnInfo(pmParam);

        if(tmpList != null && tmpList.size() > 0) {
            Map<String, Object> tmpMap = (Map<String, Object>)tmpList.get(0);
            pmParam.put("email", String.valueOf(tmpMap.get("email")));
            pmParam.put("cell", String.valueOf(tmpMap.get("cell")));
            pmParam.put("prod_nm", String.valueOf(tmpMap.get("prod_nm")));
            pmParam.put("prod_cd", String.valueOf(tmpMap.get("prod_cd")));
            pmParam.put("mem_nm", String.valueOf(tmpMap.get("mem_nm")));
            try {
                String accnt_no = (String)pmParam.get("accnt_no");
                String mem_nm = (String)pmParam.get("mem_nm");
                String email = (String)pmParam.get("email");
                String cell = (String)pmParam.get("cell");
                String prod_nm = (String)pmParam.get("prod_nm");
                String card_no = (String)pmParam.get("card_no");
                String exp_year = (String)pmParam.get("exp_year");
                String exp_mon = (String)pmParam.get("exp_mon");
                String card_idn_no = (String)pmParam.get("card_idn_no");

                NicePayProcess nicepay = new NicePayProcess();

                ParamUtils.addCenterParam(pmParam);
                String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", (String) pmParam.get("cntr_cd"));

                nicepay.setNicePayHome(sPayFilePath+"/web_site/nicelog");
                nicepay.setGoodsName(prod_nm);
                nicepay.setMoid(accnt_no);
                nicepay.setBuyerName(mem_nm);
                nicepay.setBuyerTel(cell);
                nicepay.setBuyerEmail(email);
                nicepay.setCardNumber(card_no);
                nicepay.setExpYear(exp_year);
                nicepay.setExpMonth(exp_mon);
                nicepay.setIdno(card_idn_no);

              //임동진 수정
                nicepay.setMallreserved("00");

                NicePayWebConnector connector = nicepay.requestCardMemAuth();
                if(!"0000".equals(connector.getResultData("ResultCode"))) {
                    pmParam.put("result_cd", connector.getResultData("ResultCode"));
                    pmParam.put("result_msg", connector.getResultData("ResultMsg"));
                } else {
                    String billKeyArr[] = new String[5];
                    billKeyArr = billKeyCreate(pmParam);
                    if(billKeyArr[0].equals("F100")) {
                        pmParam.put("bid", billKeyArr[1]);
                        Map<String, Object> rsltParam = billPay(pmParam);

                        pmParam.put("uip", String.valueOf(rsltParam.get("uip")));
                        pmParam.put("result_cd", String.valueOf(rsltParam.get("result_cd")));
                        pmParam.put("result_msg", String.valueOf(rsltParam.get("result_msg")));
                        pmParam.put("tid", String.valueOf(rsltParam.get("tid")));
                        pmParam.put("auth_dt", String.valueOf(rsltParam.get("auth_dt")));
                        pmParam.put("auth_cd", String.valueOf(rsltParam.get("auth_cd")));
                        pmParam.put("card_cd", String.valueOf(rsltParam.get("card_cd")));
                    } else {
                        pmParam.put("result_cd", "01");
                        pmParam.put("result_msg", "빌키생성 실패");
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return pmParam;
    }

    /**
     * NicePay Card 빌링결제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public Map<String, Object> billPay(Map<String, Object> pmParam) throws Exception {
        InetAddress inet = InetAddress.getLocalHost();
        String uip = inet.getHostAddress();
        String accntNo = (String)pmParam.get("accnt_no");
        String payAmt = (String)pmParam.get("pay_amt");
        String memNm = (String)pmParam.get("mem_nm");
        String email = (String)pmParam.get("email");
        String cell = (String)pmParam.get("cell");
        String prodNm = (String)pmParam.get("prod_nm");
        String bid = (String)pmParam.get("bid");
        String cardQuota = (String)pmParam.get("card_quota");
        String billGubun = (String)pmParam.get("billGubun");


        System.out.println("BID xxxxxxxxxxxxxxxxxxx>>> " + bid);
        System.out.println("MENU_GBN xxxxxxxxxxxxxxxxxxx>>> " + (String)pmParam.get("menu_gbn"));


        if (billGubun == null) {
            billGubun = "00";
          }

        NicePayProcess nicepay = new NicePayProcess();

        System.out.println("거래구분 값(1) => "+billGubun);


        try {
            nicepay = niceBillSetting(pmParam, "Real_Key");
            nicepay.setAmt(payAmt);
            nicepay.setMoid(accntNo);
            nicepay.setBuyerName(memNm);
            nicepay.setBuyerEmail(email);
            nicepay.setBuyerTel(cell);
            nicepay.setGoodsName(prodNm);
            nicepay.setBillKey(bid);

            //임동진 수정
            nicepay.setMallreserved(billGubun);
            nicepay.setCardQuota(cardQuota);
            NicePayWebConnector result = nicepay.paybill();
            String resultCode = result.getResultData("ResultCode");
            String resultMsg = result.getResultData("ResultMsg");
            String tid = result.getResultData("TID");
            String authDate = result.getResultData("AuthDate");
            String authCode = result.getResultData("AuthCode");
            String cardCode = result.getResultData("CardCode");


            pmParam.put("uip", uip);
            pmParam.put("result_cd", resultCode);
            pmParam.put("result_msg", resultMsg);
            pmParam.put("tid", tid);
            pmParam.put("auth_dt", authDate);
            pmParam.put("auth_cd", authCode);
            pmParam.put("card_cd", cardCode);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return pmParam;
    }

    /**
     * NicePay Card 빌링결제 취소
     *
     * @param pmParam 검색 조건
     * @return 취소 여부
     * @throws Exception
     */
    public Map<String, Object> cancelBillPay(Map<String, Object> pmParam)
            throws Exception
        {
            String payAmt = String.valueOf(pmParam.get("pay_amt"));
            String tid = String.valueOf(pmParam.get("tid"));
            NicePayProcess nicepay = new NicePayProcess();
            try
            {
                nicepay = niceBillSetting(pmParam,"Real_Key");
                nicepay.setAmt(payAmt);
                nicepay.setTid(tid);
                nicepay.setCancelPwd("1111");     // 대명 취소 비밀번호
                //nicepay.setCancelPwd("123456"); //nicepay 테스트 취소 비밀번호
                nicepay.setCancelMsg((String)pmParam.get("emple_no"));

                NicePayWebConnector result = nicepay.doCancel();

                String resultCode = result.getResultData("ResultCode");
                String resultMsg = result.getResultData("ResultMsg");
                String cnclDay = result.getResultData("CancelDate");
                String cnclTm = result.getResultData("CancelTime");

                InetAddress inet = InetAddress.getLocalHost();
                String uip = inet.getHostAddress();
                pmParam.put("uip", uip);
                pmParam.put("result_code", resultCode);
                pmParam.put("result_msg", (new StringBuilder(String.valueOf(resultCode))).append(" : ").append(resultMsg).toString());
                pmParam.put("cncl_day", cnclDay);
                pmParam.put("cncl_tm", cnclTm);
                pmParam.put("cncl_day", cnclDay);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return pmParam;
        }

    /**
     * NicePay Card 빌링결제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public Map<String, Object> billPayBatchProc(Map<String, Object> pmParam) throws Exception {
        String result_cd = "";
        String result_msg = "";
        String tid = "";
        String auth_dt = "";
        String auth_cd = "";
        String uip = "";
        String card_cd = "";
        try
        {
            Map<String, Object> rsltParam = billPay(pmParam);
            result_cd = String.valueOf(rsltParam.get("result_cd"));
            result_msg = String.valueOf(rsltParam.get("result_msg"));
            tid = String.valueOf(rsltParam.get("tid"));
            auth_dt = String.valueOf(rsltParam.get("auth_dt"));
            auth_cd = String.valueOf(rsltParam.get("auth_cd"));
            uip = String.valueOf(rsltParam.get("uip"));
            card_cd = String.valueOf(rsltParam.get("card_cd"));
            pmParam.put("uip", uip);
            pmParam.put("result_cd", result_cd);
            pmParam.put("result_msg", result_msg);
            pmParam.put("tid", tid);
            pmParam.put("auth_dt", auth_dt);
            pmParam.put("auth_cd", auth_cd);
            pmParam.put("card_cd", card_cd);

            //이하 controller에서 적용함
            /*
            if(result_cd.equals("3001"))
            {
                pmParam.put("type", "card");
                //lifemngcontroller.mergePayMngAfterRTCard(pmParam); >> controller에서 적용
                pmParam.put("stat", "04");
            } else
            {
                pmParam.put("stat", "03");
            }
            pmParam.put("pay_no", String.valueOf(pmParam.get("inv_no")));
            //cmsdao.updateCardWdrwReq(pmParam); >> controller에서 적용
             */
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return pmParam;
    }


    /***************************************************************************/
    /***************************************************************************/
    /***************************************************************************/
    /***************************************************************************/

    /***********
     * 입금관련
    ************/

    /**
     * 대명라이프웨이 해당구좌의 prod_cl 조회
     *
     * @param pmParam 검색 조건
     * @return 빌키 여부
     * @throws Exception
     */
    public String getProdClByAccntNo(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getProdClByAccntNo(pmParam);
    }
    /**
     * 대명라이프웨이 부가정보 조회
     *
     * @param pmParam 검색 조건
     * @return  부가정보
     * @throws Exception
     */
    public List<Map<String, Object>> getBugaInfo(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getBugaInfo(pmParam);
    }
    /**
     * 대명라이프웨이 입금 newYn 체크
     *
     * @param pmParam 검색 조건
     * @return prod_cl
     * @throws Exception
     */
    public String getpayNewYnChk(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getpayNewYnChk(pmParam);

    }

    /**
     * 대명라이프웨이 입금점표 등록/수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void mergePayMng(Map<String, Object> pmParam) throws Exception {
        Map<String, Object> orgParam = new HashMap<String, Object>();
        orgParam = pmParam; // 원본

        Map<String, Object> paramMap = new HashMap<String, Object>();
        List<Map<String, Object>> accntNoList = new ArrayList();
        List<Map<String, Object>> statList = new ArrayList();

        String mode = (String)pmParam.get("mode");
        String dcAmtSeq = "0";
        String updateDcAmtSeq = "0";

        int monCnt = Integer.valueOf(String.valueOf(pmParam.get("mon_cnt"))).intValue();
        int no = Integer.valueOf(String.valueOf(pmParam.get("no"))).intValue();
        int exprNo = Integer.valueOf(String.valueOf(pmParam.get("expr_no"))).intValue();
        int sunNapCnt = 0;

        if(pmParam.get("paramHm") != null) {
            paramMap = (HashMap)pmParam.get("paramHm");
            if(paramMap.get("accntAC") != null) {
                accntNoList = (ArrayList)paramMap.get("accntAC");
            }
        } else if(pmParam.get("accntAC") != null) {
            accntNoList = (ArrayList)pmParam.get("accntAC");
        }
        if("update".equals(mode)) {
            if(!"Y".equals((String)pmParam.get("del_fg"))) {
                pmParam.put("pay_cancel_cd", "");
                pmParam.put("pay_cancel_note", "");
                pmParam.put("pay_cancel_day", "");
            }

            pmParam.put("mode", mode);
            DlwCardDAO.mergePayMng(pmParam);

            List<Map<String, Object>> chgList = (List<Map<String, Object>>) pmParam.get("chgAC");
            if (chgList != null && chgList.size() > 0) {
                for(int i = 0; i < chgList.size(); i++) {
                    Map<String, Object> chgParam = (Map<String, Object>)chgList.get(i);
                    pmParam.put("dat1", chgParam.get("dat1"));
                    pmParam.put("dat2", chgParam.get("dat2"));
                    pmParam.put("dat3", chgParam.get("dat3"));
                    DlwCardDAO.insertReqUpdDelInf(pmParam);
                }
            }
        }

        if(accntNoList == null || accntNoList.size() == 0) {
            List<Map<String, Object>> dcAmtCntList = DlwCardDAO.getDCAmtCnt(pmParam);
            if(dcAmtCntList.size() > 0) {
                Map<String, Object> dcAmtCntParam = (Map<String, Object>)dcAmtCntList.get(0);
                sunNapCnt = 0;
                if (null != dcAmtCntParam.get("dc_amt_cnt")) {
                    sunNapCnt =  Integer.parseInt(String.valueOf(dcAmtCntParam.get("dc_amt_cnt")));
                }
                dcAmtCntParam.put("accnt_no", pmParam.get("accnt_no"));
                dcAmtCntParam.put("pay_day", pmParam.get("pay_day"));
                DlwCardDAO.deleteDCAmt(dcAmtCntParam);
            }
        }
        for(int i = no; i < no + monCnt; i++) {
            Map<String, Object> payMngParam = new HashMap<String, Object>();
            if(i == 1) {
                pmParam.put("join_dt", pmParam.get("pay_day"));
                DlwCardDAO.updateJoinDt(pmParam);
                //MemProdAccntDAO.updateJoinDt(pmParam);
            }
            if(accntNoList.size() == 0) {
                payMngParam.put("accnt_no", (String)pmParam.get("accnt_no"));
            } else {
                HashMap accntNoMap = (HashMap)accntNoList.get(0);
                payMngParam.put("accnt_no", (String)accntNoMap.get("accnt_no"));
            }
            payMngParam.put("no", String.valueOf(i));
            payMngParam.put("pay_day", (String)pmParam.get("pay_day"));
            if("N".equals((String)pmParam.get("del_fg"))) {
                List<Map<String, Object>> payMngList = new ArrayList();
                payMngList = DlwCardDAO.getPayMngStat(payMngParam);
                statList.add(payMngList.get(0));
            }
        }

        for(int j = 0; j < statList.size(); j++) {
            Map<String, Object> statMap = statList.get(j);
            if(!"2".equals(statMap.get("stat"))) {
                sunNapCnt++;
            }
        }

        pmParam= orgParam;

        Map<String, Object> payMngParam = new HashMap<String, Object>();
        payMngParam.put("dc_amt_seq", dcAmtSeq);
        payMngParam.put("accnt_no", pmParam.get("accnt_no"));
        payMngParam.put("pay_amt", pmParam.get("pay_amt"));
        payMngParam.put("pay_day", pmParam.get("pay_day"));
        payMngParam.put("pay_mthd", pmParam.get("pay_mthd"));
        payMngParam.put("reg_man", pmParam.get("reg_man"));
        payMngParam.put("upd_man", pmParam.get("upd_man"));
        payMngParam.put("emple_no", pmParam.get("emple_no"));
        payMngParam.put("del_fg", pmParam.get("del_fg"));
        if(accntNoList.size() == 0) {
            DlwCardDAO.updateDCAmtSeq(payMngParam);
        }

        if("insert".equals(mode)) {
            if(accntNoList.size() == 0) {
                if(statList.size() > 0) {
                    for(int j = 0; j < statList.size(); j++) {
                        Map<String, Object> statMap = statList.get(j);
                        payMngParam.put("no", statMap.get("no"));
                        payMngParam.put("stat", statMap.get("stat"));

                        payMngParam.put("mode", mode);
                        DlwCardDAO.mergePayMng(payMngParam);
                    }

                }
            } else {
                for(int i = 0; i < accntNoList.size(); i++) {
                    HashMap accntNoMap = (HashMap)accntNoList.get(i);
                    payMngParam.put("accnt_no", (String)accntNoMap.get("accnt_no"));
                    for(int j = 0; j < statList.size(); j++) {
                        Map<String, Object> statMap = statList.get(j);
                        payMngParam.put("no", statMap.get("no"));
                        payMngParam.put("stat", statMap.get("stat"));

                        payMngParam.put("mode", mode);
                        DlwCardDAO.mergePayMng(payMngParam);
                    }
                }
            }
        }
    }

    /**
     * 대명라이프웨이 입금점표 등록/수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void mergePayMngDtl(Map<String, Object> pmParam) throws Exception {
        Map<String, Object> orgParam = new HashMap<String, Object>();
        orgParam = pmParam; // 원본

        String mode = (String)pmParam.get("mode");
        int monCnt = Integer.valueOf(String.valueOf(pmParam.get("mon_cnt"))).intValue();
        int no = Integer.valueOf(String.valueOf(pmParam.get("no"))).intValue();
        List<Map<String, Object>> statList = new ArrayList();
        List<Map<String, Object>> accntNoList = new ArrayList();
        if("update".equals(mode))
        {
            if(!"Y".equals((String)pmParam.get("del_fg")))
            {
                pmParam.put("pay_cancel_cd", "");
                pmParam.put("pay_cancel_note", "");
                pmParam.put("pay_cancel_day", "");
            }

            pmParam.put("mode", mode);
            DlwCardDAO.mergePayMngDtl(pmParam);

            List<Map<String, Object>> chgList = (List<Map<String, Object>>) pmParam.get("chgAC");
            if (chgList != null && chgList.size() > 0) {
                for(int i = 0; i < chgList.size(); i++) {
                    Map<String, Object> chgParam = (Map<String, Object>)chgList.get(i);

                    pmParam.put("dat1", chgParam.get("dat1"));
                    pmParam.put("dat2", chgParam.get("dat2"));
                    pmParam.put("dat3", chgParam.get("dat3"));
                    DlwCardDAO.insertReqUpdDelInf(pmParam);
                }
            }

        }
        for(int i = no; i < no + monCnt; i++)
        {
            Map<String, Object> payMngParam = new HashMap<String, Object>();
            payMngParam.put("accnt_no", pmParam.get("accnt_no"));
            payMngParam.put("no", String.valueOf(i));
            payMngParam.put("pay_day", pmParam.get("pay_day"));

            if("N".equals((String)pmParam.get("del_fg")))
            {
                List<Map<String, Object>> payMngList = new ArrayList();
                payMngList = DlwCardDAO.getPayMngStat(payMngParam);
                statList.add(payMngList.get(0));
            }
        }

        pmParam= orgParam;

        Map<String, Object> payMngParam = new HashMap<String, Object>();
        payMngParam.put("seq", pmParam.get("seq"));
        payMngParam.put("accnt_no", pmParam.get("accnt_no"));
        payMngParam.put("pay_amt", pmParam.get("pay_amt"));
        payMngParam.put("pay_day", pmParam.get("pay_day"));
        payMngParam.put("pay_mthd", pmParam.get("pay_mthd"));
        payMngParam.put("reg_man", pmParam.get("reg_man"));
        payMngParam.put("upd_man", pmParam.get("upd_man"));
        payMngParam.put("emple_no", pmParam.get("emple_no"));
        payMngParam.put("del_fg", pmParam.get("del_fg"));

        if("insert".equals(mode) && accntNoList.size() == 0)
        {
            for(int j = 0; j < statList.size(); j++)
            {
                Map<String, Object> statMap = new HashMap<String, Object>();
                statMap = statList.get(j);
                payMngParam.put("no", statMap.get("no"));
                payMngParam.put("stat", statMap.get("stat"));

                payMngParam.put("mode", mode);
                DlwCardDAO.mergePayMngDtl(payMngParam);
            }

        }
    }

    /**
     * 대명라이프웨이 입금점표 등록/수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void mergePayMngDtl1(Map<String, Object> pmParam) throws Exception {
        Map<String, Object> orgParam = new HashMap<String, Object>();
        orgParam = pmParam; // 원본

        String mode = (String)pmParam.get("mode");
        int monCnt = Integer.valueOf(String.valueOf(pmParam.get("mon_cnt"))).intValue();
        int no = Integer.valueOf(String.valueOf(pmParam.get("no"))).intValue();
        List<Map<String, Object>> statList = new ArrayList();
        List<Map<String, Object>> accntNoList = new ArrayList();
        if("update".equals(mode))
        {
            if(!"Y".equals((String)pmParam.get("del_fg")))
            {
                pmParam.put("pay_cancel_cd", "");
                pmParam.put("pay_cancel_note", "");
                pmParam.put("pay_cancel_day", "");
            }

            pmParam.put("mode", mode);
            DlwCardDAO.mergePayMngDtl1(pmParam);

            List<Map<String, Object>> chgList = (List<Map<String, Object>>) pmParam.get("chgAC");
            if (chgList != null && chgList.size() > 0) {
                for(int i = 0; i < chgList.size(); i++) {
                    Map<String, Object> chgParam = (Map<String, Object>)chgList.get(i);

                    pmParam.put("dat1", chgParam.get("dat1"));
                    pmParam.put("dat2", chgParam.get("dat2"));
                    pmParam.put("dat3", chgParam.get("dat3"));
                    DlwCardDAO.insertReqUpdDelInf(pmParam);
                }
            }
        }
        for(int i = no; i < no + monCnt; i++)
        {
            Map<String, Object> payMngParam = new HashMap<String, Object>();
            payMngParam.put("accnt_no", pmParam.get("accnt_no"));
            payMngParam.put("no", String.valueOf(i));
            payMngParam.put("pay_day", pmParam.get("pay_day"));

            if("N".equals((String)pmParam.get("del_fg")))
            {
                List<Map<String, Object>> payMngList = new ArrayList();
                payMngList = DlwCardDAO.getPayMngStat(payMngParam);
                statList.add(payMngList.get(0));
            }
        }

        pmParam= orgParam;

        Map<String, Object> payMngParam = new HashMap<String, Object>();
        payMngParam.put("seq", pmParam.get("seq"));
        payMngParam.put("accnt_no", pmParam.get("accnt_no"));
        payMngParam.put("pay_amt", pmParam.get("pay_amt"));
        payMngParam.put("pay_day", pmParam.get("pay_day"));
        payMngParam.put("pay_mthd", pmParam.get("pay_mthd"));
        payMngParam.put("reg_man", pmParam.get("reg_man"));
        payMngParam.put("upd_man", pmParam.get("upd_man"));
        payMngParam.put("emple_no", pmParam.get("emple_no"));
        payMngParam.put("del_fg", pmParam.get("del_fg"));

        if("insert".equals(mode) && accntNoList.size() == 0)
        {
            for(int j = 0; j < statList.size(); j++)
            {
                Map<String, Object> statMap = new HashMap<String, Object>();
                statMap = statList.get(j);
                payMngParam.put("no", statMap.get("no"));
                payMngParam.put("stat", statMap.get("stat"));

                payMngParam.put("mode", mode);
                DlwCardDAO.mergePayMngDtl1(payMngParam);
            }
        }
    }

    /**
     * 스마트라이프 카드 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String saveSmartLifeCard(Map<String, Object> pmParam) throws Exception {
        String billKeyArr[] = new String[5];//빌키
        String sIdnNo = CommonUtils.checkNull((String)pmParam.get("idn_no"));
        String sDtlFlag = CommonUtils.checkNull((String)pmParam.get("dtl_flag"));
        String sAccntNo = CommonUtils.checkNull((String)pmParam.get("accnt_no"));
        String msg = "";

        List<Map<String, Object>> tmpList = DlwCardDAO.getBillKeyCrtnInfo(pmParam); // 빌키생성정보 조회
        Map<String, Object> tmpMap = (Map<String, Object>)tmpList.get(0);

        pmParam.put("email", String.valueOf(tmpMap.get("email")));
        pmParam.put("prod_cd", String.valueOf(tmpMap.get("prod_cd")));
        pmParam.put("prod_nm", String.valueOf(tmpMap.get("prod_nm")));
        pmParam.put("mem_nm", String.valueOf(tmpMap.get("mem_nm")));

        if ("".equals(sIdnNo)) {
            pmParam.put("idn_no", String.valueOf(tmpMap.get("idn_no")));
        }

        if ("S".equals(sDtlFlag)) {
            //Nicepay 객체에 값 set
            NicePayProcess nicepay = new NicePayProcess();

            ParamUtils.addCenterParam(pmParam);
            String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", (String) pmParam.get("cntr_cd"));

            nicepay.setNicePayHome(sPayFilePath+"/web_site/nicelog");

            nicepay.setGoodsName(String.valueOf(pmParam.get("prod_cd")));
            nicepay.setMoid(String.valueOf(pmParam.get("accnt_no")));
            nicepay.setBuyerName(String.valueOf(pmParam.get("mem_nm")));
            nicepay.setBuyerTel(String.valueOf(pmParam.get("cell")));
            nicepay.setBuyerEmail(String.valueOf(pmParam.get("email")));
            nicepay.setCardNumber(String.valueOf(pmParam.get("card_no")));
            nicepay.setExpYear(String.valueOf(pmParam.get("expr_year")));
            nicepay.setExpMonth(String.valueOf(pmParam.get("expr_mon")));
            nicepay.setIdno(String.valueOf(pmParam.get("idn_no")));

            //승인요청
            NicePayWebConnector connector = nicepay.requestCardMemAuth();
            String resultCode = connector.getResultData("ResultCode");
            String resultMsg = connector.getResultData("ResultMsg");
            String cardCode = connector.getResultData("CardCode");
            String cardName = connector.getResultData("CardName");

            String delFlag = DlwCardDAO.getDltnFlagCardMmbr(pmParam);
            String lbBestCl = DlwCardDAO.getLgBestDiv(pmParam);

            if(!"0000".equals(resultCode) && (!"5012".equals(resultCode) || !"Y".equals(lbBestCl)))
                msg = (new StringBuilder(String.valueOf(msg))).append(resultMsg).toString();
            else if(delFlag.equals("N")) {
                msg = "overlap";
            } else {
                billKeyArr = billKeyCreate(pmParam);

                pmParam.put("bidRtCd", billKeyArr[0]);
                pmParam.put("bid", billKeyArr[1]);
                pmParam.put("bidMsg", billKeyArr[2]);

                if ("F100".equals(billKeyArr[0])) {
                    DlwCardDAO.insertDlwCardMmbr(pmParam); // Card 회원 신규등록

                    pmParam.put("app_cl", "1");
                    pmParam.put("pay_mthd", "06");
                    pmParam.put("acpt_mthd", "06");

                    DlwCardDAO.insertDlwCardAnxtSrvc(pmParam);
                    DlwCardDAO.updateDlwCardPymtMthd(pmParam); // Card 납입방법 수정 - 엔컴
                    //MemProdAccntDAO.updateCardPymtMthd(pmParam); // Card 납입방법 수정 - 인우

                    pmParam.put("sl_card_no", String.valueOf(pmParam.get("card_no")));
                    pmParam.put("note", "삼성카드 등록");
                    dlwConsProdDAO.insertSmartLifeCnslMng(pmParam);
                    msg = "success";
                } else {
                    msg = "빌키생성 오류";
                    pmParam.put("note", msg);
                    pmParam.put("issueStat", "0001");
                    pmParam.put("cnslStat", "0001");
                    dlwConsProdDAO.insertSmartLifeCnslMng(pmParam);
                }
            }
        } else if ("I".equals(sDtlFlag)) {
            //String dd = (String)pmParam.get("imsi_card_no");
            pmParam.put("note", "임시카드 번호 등록");
            dlwConsProdDAO.insertSmartLifeCnslMng(pmParam);
            pmParam.put("stat", "18");
            if (dlwConsProdDAO.getHpCallCount(sAccntNo) == 0) {
                dlwConsProdDAO.insertHpCallInfo(pmParam);
            }
            msg = "success";
        }

        return msg;
    }

    /**
     * 대명라이프웨이의 나이스페이 카드 유효성을 확인한다.
     *
     * @param pmParam 검색 조건
     * @return 등록결과
     * @throws Exception
     */
    public String getNiceVldInsp(Map<String, Object> pmParam) throws Exception {
        String rslt = "";

        String dltnFlag = DlwCardDAO.getDltnFlagCardMmbr(pmParam);
        String lgBestDiv =  DlwCardDAO.getLgBestDiv(pmParam);

        String app_cl = CommonUtils.checkNull((String)(pmParam.get("app_cl")));//처리구분
        String billKeyArr[] = new String[5];//빌키

        String idn_no = CommonUtils.checkNull((String)pmParam.get("idn_no"));
        String email = CommonUtils.checkNull((String)pmParam.get("email"));
        String cell = CommonUtils.checkNull((String)pmParam.get("cell")).replaceAll("-", "");
        String prdt_cd = CommonUtils.checkNull((String)pmParam.get("prdt_cd"));
        String prdt_nm = CommonUtils.checkNull((String)pmParam.get("prdt_nm"));
        String mem_nm = CommonUtils.checkNull((String)pmParam.get("mem_nm"));

        if ("1".equals(app_cl)) { // 신규일경우
            //NicePay 모듈연동 관련 요소확인
            if("".equals(idn_no) || "".equals(email) || "".equals(cell)
                                || "".equals(prdt_nm) || "".equals(mem_nm)) {
                //빈값 있을경우 생성정보 조회
                List<Map<String, Object>> tmpList = DlwCardDAO.getBillKeyCrtnInfo(pmParam);
                if (tmpList != null && !tmpList.isEmpty()) {
                    Map<String, Object> tmpMap = (Map<String, Object>)tmpList.get(0);

                    //위의값 비어있을경우 생성정보조회 값으로 채움
                    if ("".equals(idn_no)) {
                        pmParam.put("idn_no", String.valueOf(tmpMap.get("idn_no")));
                    }
                    if ("".equals(email)) {
                        pmParam.put("email", String.valueOf(tmpMap.get("email")));
                    }
                    if ("".equals(cell)) {
                        pmParam.put("cell", String.valueOf(tmpMap.get("cell")));
                    }
                    pmParam.put("prod_nm", String.valueOf(tmpMap.get("prod_nm")));
                    pmParam.put("prod_cd", String.valueOf(tmpMap.get("prod_cd")));
                    pmParam.put("mem_nm", String.valueOf(tmpMap.get("mem_nm")));

                    //Nicepay 객체에 값 set
                    NicePayProcess nicepay = new NicePayProcess();

                    ParamUtils.addCenterParam(pmParam);
                    String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", (String) pmParam.get("cntr_cd"));

                    nicepay.setNicePayHome(sPayFilePath+"/web_site/nicelog");
                    nicepay.setGoodsName((String)pmParam.get("prod_cd"));
                    nicepay.setMoid((String)pmParam.get("accnt_no"));
                    nicepay.setBuyerName((String)pmParam.get("mem_nm"));
                    nicepay.setBuyerTel((String)pmParam.get("cell"));
                    nicepay.setBuyerEmail((String)pmParam.get("email"));
                    nicepay.setCardNumber((String)pmParam.get("card_no"));
                    nicepay.setExpYear((String)pmParam.get("exp_year"));
                    nicepay.setExpMonth((String)pmParam.get("exp_mon"));
                    nicepay.setIdno((String)pmParam.get("idn_no"));

                    //승인요청
                    NicePayWebConnector connector = nicepay.requestCardMemAuth();
                    String resultCode = connector.getResultData("ResultCode");
                    String resultMsg = connector.getResultData("ResultMsg");
                    String cardCode = connector.getResultData("CardCode");
                    String cardName = connector.getResultData("CardName");

                    //정상여부
                    if(!"0000".equals(resultCode) && (!"5012".equals(resultCode) || !"Y".equals(lgBestDiv))) {
                        //정상결과 아닐경우 결과코드 출력
                        rslt = resultMsg;
                    } else {
                        rslt = "success";
                        if("N".equals(dltnFlag)) {
                            //이미 등록된 회원입니다.
                            rslt = "overlap";
                        }
                    }
                }
            }//if
        }
        return rslt;
    }

    public void uploadToYeosin(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception {

        String tempDir = System.getProperty("java.io.tmpdir");
        MultipartRequest multipartRequest = new MultipartRequest(request, tempDir, 1024*1024*20);
        Enumeration enm = multipartRequest.getFileNames();
        String sKey = "";
        int fileCnt			= 0;
        Map<String, Object> mParam = null;
        // 엑셀파일 워크북 객체 생성
        XSSFWorkbook workbook = null;
        // 시트 지정
        XSSFSheet sheet = null;
        // 로우
        XSSFRow xrow = null;
        // cell
        XSSFCell xcell = null;

        List<Map<String,Object>> lst = new ArrayList<>();
        Map<String,Object> mRow = new HashMap<>();

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        try {
            mParam = new HashMap<>();

            // 실제로는 단건만 처리함
            while (enm.hasMoreElements())
            {
                fileCnt++;
                sKey = (String)enm.nextElement();
                log.debug("sKey : " + sKey);
                File upfile = multipartRequest.getFile(sKey);
                log.debug("upfile.length() : " +upfile.length());
                if (upfile.exists()) {
                    log.debug("file exists");
                } else {
                    log.debug("file does not exists");
                }

                // 엑셀파일 워크북 객체 생성
                workbook = new XSSFWorkbook(new FileInputStream(upfile));

                sheet = workbook.getSheetAt(0);

                Row row;
                Cell cell;
                String value;
                int i=0, j;
                boolean essential = false;
                String str_essential = "";
                int iAccntCnt = 0 ;  //회원번호 여부 체크용

                int rows = sheet.getPhysicalNumberOfRows();

                for (i=0;i<rows;i++){
                    xrow = sheet.getRow(i);
                    int cells = xrow.getPhysicalNumberOfCells();

                    if (i < 5) continue;

                    for(j=0;j<cells;j++){
                        xcell = xrow.getCell(j);
                        value = "";

                        //System.out.println(xcell.getCellType());

                        switch (xcell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            value = "" + xcell.getBooleanCellValue();
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            value = "" + xcell.getNumericCellValue();
                            break;
                        case Cell.CELL_TYPE_STRING:
                            value = "" + xcell.getStringCellValue();
                            break;
                        case Cell.CELL_TYPE_BLANK:
                            value = " ";
                            break;
                        default:
                            value = "" + xcell.getStringCellValue();
                            break;
                        }

                        if (null != value) {
                            value = value.trim();
                        }

                        switch (j) {
                        case 0:
                            mRow.put("dlv_id", value);
                            break;
                        case 1:
                            mRow.put("mem_nm", value);
                            break;
                        case 2:
                            mRow.put("accnt_no", value);
                            break;
                        case 7:
                            mRow.put("dlv_no", value); // 송장번호
                            break;
                        case 8:
                            mRow.put("dlv_dsps_dt", value); // 송장일
                            break;
                        case 11:
                            mRow.put("sndg_kind_cd", value); //배송(발송)종류 > 증서 멤버십 등등
                            break;
                        case 12:
                            mRow.put("dlv_type_cd", value); // 배송(발송)유형 > 택배, 등기 듣등
                            break;
                        case 13:
                            mRow.put("dlv_stat_cd", value); // 진행상태(배송상태) 발솔 반송
                            break;
                        case 14:
                            mRow.put("dsps_rsn_cd", value); // 처리사유코드 > 이사 폐문 등등
                            break;
                        case 15:
                            mRow.put("dsps_mthd_cd", value); //처리방법코드> 해피콜 , 재발송 등 내부 처리방법
                            break;
                        case 16:
                            mRow.put("dlv_dsps_rsn_cd", value); // 처리결과 코드 (폐기)> 성명오기재, 수령완료, 재발급 완료
                            break;

                        default:
                            break;
                        }
                    }
                    lst.add(mRow);
                    listMap.setRowMaps(lst);
                    hmParam = listMap.get(0);
                    ParamUtils.addSaveParam(hmParam);
                    if (hmParam.get("dlv_id").equals("")){
                        //iAccntCnt = getDlvAccntCount(hmParam);
                        if (iAccntCnt > 0) {
                            throw new EgovBizException("회원번호가 중복 되었습니다. > " + hmParam.get("accnt_no"));
                        }
                    }
                    CommonUtils.printLog(hmParam);
                    //saveDlv(hmParam);
                }

                upfile.delete();
            }

            if (fileCnt < 1) {
                throw new EgovBizException("업로드된 파일이 없습니다.");
            }


        } catch (FileNotFoundException ex) {

            ex.printStackTrace();

            throw ex;
        } catch (IOException ex) {

            ex.printStackTrace();

            throw ex;
        } finally {
        }

    }

    /**
     * 대명라이프웨이 Card 신청내역 기초데이터 건수 조회
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보
     * @throws Exception
     */
    public int getCardWdrwReqcount_Basic(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getCardWdrwReqcount_Basic(pmParam);
    }

    /**
     * 대명라이프웨이 Card 신청내역조회 기초데이터 조회
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보
     * @throws Exception
     */
    public List<Map<String, Object>> getCardWdrwReqList_Basic(Map<String, ?> pmParam) throws Exception {
        return DlwCardDAO.getCardWdrwReqList_Basic(pmParam);
    }

}
