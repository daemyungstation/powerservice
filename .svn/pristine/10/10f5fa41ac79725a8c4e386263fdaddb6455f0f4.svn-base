/*
 * (@)# DlwVrtlAcntTestDAO.java
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwVrtlAcntTestService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 대명라이프웨이 가상계좌 정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/02
 * @프로그램ID DlwVrtlAcnt
 */
@Service
public class DlwVrtlAcntServiceTestImpl extends EgovAbstractServiceImpl implements DlwVrtlAcntTestService {

    @Resource
    public DlwVrtlAcntTestDAO DlwVrtlAcntTestDAO;

    /**
     * 대명라이프웨이 가상계좌 정보 산출이력 건수 조회
     *
     * @param pmParam 검색 조건
     * @return 가상계좌 정보 산출 건수
     * @throws Exception
     */
    public int getDlwVrtlAcntClctCount(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntTestDAO.getDlwVrtlAcntClctCount(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 정보 산출이력 조회
     *
     * @param pmParam 검색 조건
     * @return 가상계좌 정보 산출정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwVrtlAcntCsmm(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntTestDAO.getDlwVrtlAcntClctList(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 전송 데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return 가상계좌 정보 산출정보
     * @throws Exception
     */
    public int deleteDlwVrtlAcntClct(Map<String, Object> pmParam) throws Exception {
        
    	Map<String, Object> hmTime = DlwVrtlAcntTestDAO.getCurrDthms(pmParam);
    	pmParam.put("yyyymmdd", (String)hmTime.get("yyyymmdd"));
    	pmParam.put("hhmmss", (String)hmTime.get("hhmmss"));
    	
        return DlwVrtlAcntTestDAO.deleteDlwVrtlAcntClct(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 산출 리스트 조회
     *
     * @param pmParam 검색 조건
     * @return 가상계좌 정보 산출정보
     * @throws Exception
     */
    public List<Map<String, Object>> getVrtlAccntWdrwReqList(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntTestDAO.getVrtlAccntWdrwReqList(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 산출 대상자 리스트 조회
     *
     * @param pmParam 검색 조건
     * @return 가상계좌 산출 대상자 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getVrtlAccntTargetList(Map<String, Object> pmParam) throws Exception {
    	System.out.println("인풋 데이터 서비스 ::: " + pmParam.toString());
        return DlwVrtlAcntTestDAO.getVrtlAccntTargetList(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 산출 청구금액
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getInvAmt(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntTestDAO.getInvAmt(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 산출 임시저장데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteTempVrtlAccntWdrwReq(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntTestDAO.deleteTempVrtlAccntWdrwReq(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 산출데이터 임시저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertTempVrtlAccntWdrwReq(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntTestDAO.insertTempVrtlAccntWdrwReq(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 산출데이터 임시저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertVrtlAccntWdrwReq(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntTestDAO.insertVrtlAccntWdrwReq(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 산출 데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNiceVrtlAccntWdrwList(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntTestDAO.deleteNiceVrtlAccntWdrwList(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 NICE전송 결과 반영
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNiceVrtlAccntWdrwReq(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntTestDAO.updateNiceVrtlAccntWdrwReq(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 정보 리스트 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 가상계좌  정보
     * @throws Exception
     */
    public int getVrtlAccntInfoCount(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntTestDAO.getVrtlAccntInfoCount(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 정보 리스트를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 가상계좌  정보
     * @throws Exception
     */
    public List<Map<String, Object>> getVrtlAccntInfo(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntTestDAO.getVrtlAccntInfo(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 복원 처리
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateVrtlAccntRecoverProc(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntTestDAO.updateVrtlAccntRecoverProc(pmParam);
    }

   /** 가상계좌 NICE전송 결과 반영
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int updateVrtlAccntPayApp(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntTestDAO.updateVrtlAccntPayApp(pmParam);
   }
   /**
    * 가상계좌 CARD연체 체크
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int updateCardYenCheChk(Map<String, ?> pmParam) throws Exception {
       return DlwVrtlAcntTestDAO.updateCardYenCheChk(pmParam);
   }
   /**
    * 가상계좌 CMS연체 체크
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int updateCmsYenCheChk(Map<String, ?> pmParam) throws Exception {
       return DlwVrtlAcntTestDAO.updateCmsYenCheChk(pmParam);
   }
   /**
    * 가상계좌-실입금 1회차 입금일자를 회원가입일자로 수정
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int updateVrtlAccntJoinDt(Map<String, ?> pmParam) throws Exception {
       return DlwVrtlAcntTestDAO.updateVrtlAccntJoinDt(pmParam);
   }
   /**
    * EB22 출금의뢰 성공본 - 입금처리
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int insertPayMngByCMS(Map<String, ?> pmParam) throws Exception {
       return DlwVrtlAcntTestDAO.insertPayMngByCMS(pmParam);
   }
   /**
    * EB22 출금의뢰 성공본 - 결합상품금액 입금처리
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int insertPayMngDtlByCMS(Map<String, ?> pmParam) throws Exception {
       return DlwVrtlAcntTestDAO.insertPayMngDtlByCMS(pmParam);
   }
   /**
    * EB22 출금의뢰 성공본 - 추가부담금 입금처리
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int insertPayMngDtl1ByCMS(Map<String, ?> pmParam) throws Exception {
       return DlwVrtlAcntTestDAO.insertPayMngDtl1ByCMS(pmParam);
   }

   /**
    * 가수금 등록
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int saveGasuPayProc(Map<String, ?> pmParam) throws Exception {
       return DlwVrtlAcntTestDAO.saveGasuPayProc(pmParam);
   }
   /**
    * 가수금 등록 (결합?)
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int saveGasuPayDtlProc(Map<String, Object> pmParam) throws Exception {
	   String sIuGubun = DlwVrtlAcntTestDAO.getPayNo(pmParam);
	   pmParam.put("iu_gubun", sIuGubun);
       return DlwVrtlAcntTestDAO.saveGasuPayDtlProc(pmParam);
   }
   /**
    * 가수금 등록 (추가?)
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int saveGasuPayDtl1Proc(Map<String, Object> pmParam) throws Exception {
	   String sIuGubun = DlwVrtlAcntTestDAO.getPayNo(pmParam);
	   pmParam.put("iu_gubun", sIuGubun);
       return DlwVrtlAcntTestDAO.saveGasuPayDtl1Proc(pmParam);
   }

   /**
    * 가상계좌 입금 성공본 - 조회(실입금처리 대상)
    *
    * @param pmParam 검색 조건
    * @return 가상계좌  정보
    * @throws Exception
    */
   public List<Map<String, Object>> getVrtlAccntPayAppComplete(Map<String, ?> pmParam) throws Exception {
       return DlwVrtlAcntTestDAO.getVrtlAccntPayAppComplete(pmParam);
   }

   /**
    * 연체입금구분
    *
    * @param pmParam 검색 조건
    * @return 가상계좌  정보
    * @throws Exception
    */
   public String getYenCheByPayGubun(Map<String, ?> pmParam) throws Exception {
       return DlwVrtlAcntTestDAO.getYenCheByPayGubun(pmParam);
   }
   /**
    * 연체체크
    *
    * @param pmParam 검색 조건
    * @return 가상계좌  정보
    * @throws Exception
    */
   public String getYenCheChk(Map<String, ?> pmParam) throws Exception {
       return DlwVrtlAcntTestDAO.getYenCheChk(pmParam);
   }


   /**
    * 연체체크
    *
    * @param pmParam 검색 조건
    * @return 가상계좌  정보
    * @throws Exception
    */
   public String vrtlAcntPayByAdmin(Map<String, Object> hmParam) throws Exception {
       String result = "";
       int result1;
       String err_pay_proc = (String)hmParam.get("err_pay_proc");
       if("Y".equals(err_pay_proc)) {
           hmParam.put("upd_man", hmParam.get("rgsr_id"));
           result1 = DlwVrtlAcntTestDAO.updateVrtlAccntPayApp(hmParam);
           if (result1 < 1) {
        	   result ="false";
        	   
           }else {
        	   result ="true";
        	   
           }
           

       }
       return result;
   }

   /**
    * 나이스 가상계좌 리턴결과 등록
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   
   public int insertNiceVacctNoti(Map<String, Object> pmParam) throws Exception {
       return DlwVrtlAcntTestDAO.insertNiceVacctNoti(pmParam);
   }
}
