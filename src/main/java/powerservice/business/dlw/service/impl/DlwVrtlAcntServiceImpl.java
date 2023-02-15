/*
 * (@)# DlwVrtlAcntDAO.java
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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwVrtlAcntService;
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
public class DlwVrtlAcntServiceImpl extends EgovAbstractServiceImpl implements DlwVrtlAcntService {

    @Resource
    public DlwVrtlAcntDAO DlwVrtlAcntDAO;

    @Resource
    public DlwPayDAO DlwPayDAO;

    /**
     * 대명라이프웨이 가상계좌 정보 산출이력 건수 조회
     *
     * @param pmParam 검색 조건
     * @return 가상계좌 정보 산출 건수
     * @throws Exception
     */
    public int getDlwVrtlAcntClctCount(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntDAO.getDlwVrtlAcntClctCount(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 정보 산출이력 조회
     *
     * @param pmParam 검색 조건
     * @return 가상계좌 정보 산출정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwVrtlAcntCsmm(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntDAO.getDlwVrtlAcntClctList(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 전송 데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return 가상계좌 정보 산출정보
     * @throws Exception
     */
    public int deleteDlwVrtlAcntClct(Map<String, Object> pmParam) throws Exception {

    	Map<String, Object> hmTime = DlwVrtlAcntDAO.getCurrDthms(pmParam);
    	pmParam.put("yyyymmdd", (String)hmTime.get("yyyymmdd"));
    	pmParam.put("hhmmss", (String)hmTime.get("hhmmss"));

        return DlwVrtlAcntDAO.deleteDlwVrtlAcntClct(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 산출 리스트 조회
     *
     * @param pmParam 검색 조건
     * @return 가상계좌 정보 산출정보
     * @throws Exception
     */
    public List<Map<String, Object>> getVrtlAccntWdrwReqList(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntDAO.getVrtlAccntWdrwReqList(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 산출 대상자 리스트 조회
     *
     * @param pmParam 검색 조건
     * @return 가상계좌 산출 대상자 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getVrtlAccntTargetList(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntDAO.getVrtlAccntTargetList(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 산출 청구금액
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getInvAmt(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntDAO.getInvAmt(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 산출 임시저장데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteTempVrtlAccntWdrwReq(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntDAO.deleteTempVrtlAccntWdrwReq(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 산출데이터 임시저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertTempVrtlAccntWdrwReq(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntDAO.insertTempVrtlAccntWdrwReq(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 산출데이터 임시저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertVrtlAccntWdrwReq(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntDAO.insertVrtlAccntWdrwReq(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 산출 데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteNiceVrtlAccntWdrwList(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntDAO.deleteNiceVrtlAccntWdrwList(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 NICE전송 결과 반영
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateNiceVrtlAccntWdrwReq(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntDAO.updateNiceVrtlAccntWdrwReq(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 정보 리스트 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 가상계좌  정보
     * @throws Exception
     */
    public int getVrtlAccntInfoCount(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntDAO.getVrtlAccntInfoCount(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 정보 리스트를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 가상계좌  정보
     * @throws Exception
     */
    public List<Map<String, Object>> getVrtlAccntInfo(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntDAO.getVrtlAccntInfo(pmParam);
    }

    /**
     * 대명라이프웨이 가상계좌 복원 처리
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateVrtlAccntRecoverProc(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntDAO.updateVrtlAccntRecoverProc(pmParam);
    }

   /** 가상계좌 NICE전송 결과 반영
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int updateVrtlAccntPayApp(Map<String, ?> pmParam) throws Exception {
        return DlwVrtlAcntDAO.updateVrtlAccntPayApp(pmParam);
   }
   /**
    * 가상계좌 CARD연체 체크
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int updateCardYenCheChk(Map<String, ?> pmParam) throws Exception {
       return DlwVrtlAcntDAO.updateCardYenCheChk(pmParam);
   }
   /**
    * 가상계좌 CMS연체 체크
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int updateCmsYenCheChk(Map<String, ?> pmParam) throws Exception {
       return DlwVrtlAcntDAO.updateCmsYenCheChk(pmParam);
   }
   /**
    * 가상계좌-실입금 1회차 입금일자를 회원가입일자로 수정
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int updateVrtlAccntJoinDt(Map<String, ?> pmParam) throws Exception {
       return DlwVrtlAcntDAO.updateVrtlAccntJoinDt(pmParam);
   }
   /**
    * EB22 출금의뢰 성공본 - 입금처리
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int insertPayMngByCMS(Map<String, ?> pmParam) throws Exception {
       return DlwVrtlAcntDAO.insertPayMngByCMS(pmParam);
   }
   /**
    * EB22 출금의뢰 성공본 - 결합상품금액 입금처리
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int insertPayMngDtlByCMS(Map<String, ?> pmParam) throws Exception {
       return DlwVrtlAcntDAO.insertPayMngDtlByCMS(pmParam);
   }
   /**
    * EB22 출금의뢰 성공본 - 추가부담금 입금처리
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int insertPayMngDtl1ByCMS(Map<String, ?> pmParam) throws Exception {
       return DlwVrtlAcntDAO.insertPayMngDtl1ByCMS(pmParam);
   }

   /**
    * 가수금 등록
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int saveGasuPayProc(Map<String, ?> pmParam) throws Exception {
       return DlwVrtlAcntDAO.saveGasuPayProc(pmParam);
   }
   /**
    * 가수금 등록 (결합?)
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int saveGasuPayDtlProc(Map<String, Object> pmParam) throws Exception {
	   String sIuGubun = DlwVrtlAcntDAO.getPayNo(pmParam);
	   pmParam.put("iu_gubun", sIuGubun);
       return DlwVrtlAcntDAO.saveGasuPayDtlProc(pmParam);
   }
   /**
    * 가수금 등록 (추가?)
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int saveGasuPayDtl1Proc(Map<String, Object> pmParam) throws Exception {
	   String sIuGubun = DlwVrtlAcntDAO.getPayNo(pmParam);
	   pmParam.put("iu_gubun", sIuGubun);
       return DlwVrtlAcntDAO.saveGasuPayDtl1Proc(pmParam);
   }

   /**
    * 가상계좌 입금 성공본 - 조회(실입금처리 대상)
    *
    * @param pmParam 검색 조건
    * @return 가상계좌  정보
    * @throws Exception
    */
   public List<Map<String, Object>> getVrtlAccntPayAppComplete(Map<String, ?> pmParam) throws Exception {
       return DlwVrtlAcntDAO.getVrtlAccntPayAppComplete(pmParam);
   }

   /**
    * 연체입금구분
    *
    * @param pmParam 검색 조건
    * @return 가상계좌  정보
    * @throws Exception
    */
   public String getYenCheByPayGubun(Map<String, ?> pmParam) throws Exception {
       return DlwVrtlAcntDAO.getYenCheByPayGubun(pmParam);
   }
   /**
    * 연체체크
    *
    * @param pmParam 검색 조건
    * @return 가상계좌  정보
    * @throws Exception
    */
   public String getYenCheChk(Map<String, ?> pmParam) throws Exception {
       return DlwVrtlAcntDAO.getYenCheChk(pmParam);
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

           	//result1 = DlwVrtlAcntDAO.updateVrtlAccntPayApp(hmParam);

           	result1 = DlwPayDAO.updateVirtualDirectInsert(hmParam);

           	System.out.println("xxxxxxxxxxxxxxxxxxxxx> : " + result1);
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
       return DlwVrtlAcntDAO.insertNiceVacctNoti(pmParam);
   }
}
