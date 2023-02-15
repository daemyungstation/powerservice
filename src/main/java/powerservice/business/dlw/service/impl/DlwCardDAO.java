/*
 * (@)# DlwCardDAO.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/02
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

package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

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
@Repository
public class DlwCardDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 대명라이프웨이 Card 고객정보의 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객정보 건수
     * @throws Exception
     */
    public int getDlwCardCsmmCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCardMap.getDlwCardCsmmCount", pmParam);
    }

    public int getCardWdrwReqcount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCardMap.getCardWdrwReqcount", pmParam);
    }

    public int getReadFileNiceCardcount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCardMap.getReadFileNiceCardcount", pmParam);
    }


    /**
     * 대명라이프웨이 Cms 고객정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCardCsmm(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCardMap.getDlwCardCsmm", pmParam);
    }

    public int getNiceCARDMemberByIchaeDt(Map<String, ?> pmParam) throws Exception {
        return update("DlwCardMap.getNiceCARDMemberByIchaeDt", pmParam);
    }


    public int insertCARDErrorInfo(List<Map<String, Object>> pmParam) throws Exception {
     //   CommonUtils.printLog(pmParam);
       // System.out.println("aaaaaaaaaaaaaaaaaaa");

        return update("DlwCardMap.insertCARDErrorInfo", pmParam);
    }

    /**
     * 대명라이프웨이 회원별 Card 신청내역의 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 신청내역 건수
     * @throws Exception
     */
    public int getDlwCardRgsnHstrCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCardMap.getDlwCardRgsnHstrCount", pmParam);
    }

    /**
     * 대명라이프웨이 회원별 Card 신청내역 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 신청내역 건수
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCardRgsnHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCardMap.getDlwCardRgsnHstrList", pmParam);
    }




    public int CARDMemberByIchaeCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCardMap.CARDMemberByIchaeCount", pmParam);
    }

    /**
     * 대명라이프웨이 회원별 Card 출금의뢰내역의 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 출금의뢰내역 건수
     * @throws Exception
     */
    public int getDlwCardWdrwHstrCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCardMap.getDlwCardWdrwHstrCount", pmParam);
    }

    /**
     * 대명라이프웨이 회원별 Card 출금의뢰내역 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 출금의뢰내역
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCardWdrwHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCardMap.getDlwCardWdrwHstrList", pmParam);
    }

    /**
     * 대명라이프웨이의 Card 산출중 여부 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 산출 여부 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCardWdrwChk(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCardMap.getDlwCardWdrwChk", pmParam);
    }

    /**
     * 대명라이프웨이의 Card 중복 여부 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 중복 여부 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCardDplcChk(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCardMap.getDlwCardDplcChk", pmParam);
    }

    /**
     * 대명라이프웨이 Card 이체정보를 수정한다.
     *
     * @param pmParam 검색 조건
     * @return 수정결과
     * @throws Exception
     */
    public int updateCardTranInfo(Map<String, ?> pmParam) throws Exception {
        return update("DlwCardMap.updateCardTranInfo", pmParam);
    }

    /**
     * 대명라이프웨이 Card 금일 등록이력 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 등록이력 정보 건수
     * @throws Exception
     */
    public int getDlwCardAplcDtlCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCardMap.getDlwCardAplcDtlCount", pmParam);
    }

    /**
     * 대명라이프웨이 Card 금일 등록이력을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 등록이력 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCardAplcDtl(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCardMap.getDlwCardAplcDtl", pmParam);
    }

    /**
     * 대명라이프웨이 Card 해당구좌 Card회원여부 검사
     *
     * @param pmParam 검색 조건
     * @return 등록이력 정보
     * @throws Exception
     */
    public String getDltnFlagCardMmbr(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCardMap.getDltnFlagCardMmbr", pmParam);
    }

    /**
     * 대명라이프웨이 LG베스트구분 조회
     *
     * @param pmParam 검색 조건
     * @return 등록이력 정보
     * @throws Exception
     */
    public String getLgBestDiv(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCardMap.getLgBestDiv", pmParam);
    }

    /**
     * NicePay 빌키생성 정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getBillKeyCrtnInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCardMap.getBillKeyCrtnInfo", pmParam);
    }

    /**
     * 대명라이프웨이 Card 신규/해지 신청
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertDlwCardAnxtSrvc(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCardMap.insertDlwCardAnxtSrvc", pmParam);
    }

    /**
     * 대명라이프웨이 Card 회원 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertDlwCardMmbr(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCardMap.insertDlwCardMmbr", pmParam);
    }

    /**
     * 대명라이프웨이 Card 회원 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateDlwCardMmbr(Map<String, ?> pmParam) throws Exception {
        return update("DlwCardMap.updateDlwCardMmbr", pmParam);
    }

    public int card_wdrw_update_stat(Map<String, ?> pmParam) throws Exception {
        return update("DlwCardMap.card_wdrw_update_stat", pmParam);
    }


    /**
    * * 대명라이프웨이 Card 회원 삭제
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
    public int deleteDlwCardMmbr(Map<String, ?> pmParam) throws Exception {
        return update("DlwCardMap.deleteDlwCardMmbr", pmParam);
    }


    /**
     * 대명라이프웨이 Card 납입방법 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateDlwCardPymtMthd(Map<String, ?> pmParam) throws Exception {
        return update("DlwCardMap.updateDlwCardPymtMthd", pmParam);
    }

    /**
     * 대명라이프웨이 대리납 확인 및 해피콜 상태 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateDlwCardRprvPymt(Map<String, ?> pmParam) throws Exception {
        return update("DlwCardMap.updateDlwCardRprvPymt", pmParam);
    }

    /**
     *     * 대명라이프웨이 여신 대비 카드 결제 로그조회
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보의 건수
     * @throws Exception
     */
    public int getDlwRltmYeosinLogCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCardMap.getDlwRltmYeosinLogCount", pmParam);
    }

    /**
     * 대명라이프웨이 여신 대비 카드 결제 로그조회
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwRltmYeosinLogList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCardMap.getDlwRltmYeosinLogList", pmParam);
    }


    /**
     * 대명라이프웨이 실시간카드결제 로그정보의 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보의 건수
     * @throws Exception
     */
    public int getDlwRltmCardLogCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCardMap.getDlwRltmCardLogCount", pmParam);
    }

    /**
     * 대명라이프웨이 실시간카드결제 로그정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwRltmCardLogList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCardMap.getDlwRltmCardLogList", pmParam);
    }

    /**
     * 대명라이프웨이 파일결제 입금일 확인
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getDlwCardWdrwReqInfo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCardMap.getDlwCardWdrwReqInfo", pmParam);
    }
    /**
     * 대명라이프웨이 실시간 카드결제 - 승인취소 로그 기록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateDlwCardAckdCancLog(Map<String, ?> pmParam) throws Exception {
        return update("DlwCardMap.updateDlwCardAckdCancLog", pmParam);
    }
    /**
     * 대명라이프웨이 카드 배치에 따른 입금 완료 처리
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateDlwCardWdrwReqCanc(Map<String, ?> pmParam) throws Exception {
        return update("DlwCardMap.updateDlwCardWdrwReqCanc", pmParam);
    }

    /**
     * 대명라이프웨이 Card EB21 산출내역 삭제
     *
     * @param pmParam 검색 조건
     * @return 삭제 여부
     * @throws Exception
     */
    public int deleteCardWdrwReq(Map<String, ?> pmParam) throws Exception {
        return update("DlwCardMap.deleteCardWdrwReq", pmParam);
    }

    /**
     * 대명라이프웨이 청구테이블에 고객만족센터 데이터 삽입
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int insertCardWdrwReqByCall(Map<String, ?> pmParam) throws Exception {
        return update("DlwCardMap.insertCardWdrwReqByCall", pmParam);
    }

    /**
     * 대명라이프웨이 빌키있는 회원인지 조회
     *
     * @param pmParam 검색 조건
     * @return 빌키 여부
     * @throws Exception
     */
    public String getChkBid(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCardMap.getChkBid", pmParam);
    }

    /**
     * 대명라이프웨이 구좌별 Card 정보조회
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보
     * @throws Exception
     */
    public List<Map<String, Object>> getCardInfoByAccnt(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCardMap.getCardInfoByAccnt", pmParam);
    }

    /**
     * 대명라이프웨이 실시간 카드결제 - 대상회원 상태 조회
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보
     * @throws Exception
     */
    public List<Map<String, Object>> getAccntStat(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCardMap.getAccntStat", pmParam);
    }

    /**
     * 빌키 MID 와 KEY를 가져오는 함수
     * 임동진_20180806
     * @param pmParam 검색 조건
     * @return 카드 MID / 카드 KEY
     * @throws Exception
     */
    public String getAccntMid(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCardMap.getAccntMid", pmParam);
    }

    /**
     * 대명라이프웨이 실시간 카드결제 - 신청 로그 기록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertRTCardPayLog(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCardMap.insertRTCardPayLog", pmParam);
    }

    /**
     * 대명라이프웨이 실시간 카드결제 - 결제 성공여부 로그 기록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateRTCardPayResult(Map<String, ?> pmParam) throws Exception {
        return update("DlwCardMap.updateRTCardPayResult", pmParam);
    }

    /**
     * 대명라이프웨이 실시간 카드결제 (자유결제) - 대상회원 상태 조회
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보
     * @throws Exception
     */
    public List<Map<String, Object>> getAccntStatByFreeCard(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCardMap.getAccntStatByFreeCard", pmParam);
    }

    /**
     * 대명라이프웨이 Card 신청내역조회
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보
     * @throws Exception
     */
    public List<Map<String, Object>> getCardWdrwReqList(Map<String, ?> pmParam) throws Exception {
        //CommonUtils.printLog(pmParam);
        return selectList("DlwCardMap.getCardWdrwReqList", pmParam);
    }

    public List<Map<String, Object>> getCardWdrwReqList_sum(Map<String, ?> pmParam) throws Exception {
       // CommonUtils.printLog(pmParam);
        return selectList("DlwCardMap.getCardWdrwReqList_sum", pmParam);
    }


    public List<Map<String, Object>> getCardWdrwReqList1(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCardMap.getCardWdrwReqList1", pmParam);
    }
    /**
     * 대명라이프웨이 할부개월수 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateInstallPeriodForCard(Map<String, ?> pmParam) throws Exception {
        return update("DlwCardMap.updateInstallPeriodForCard", pmParam);
    }

    /**
     * 대명라이프웨이 청구 취소
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteAllCardWdrwTemp(Map<String, ?> pmParam) throws Exception {
        return delete("DlwCardMap.deleteAllCardWdrwTemp", pmParam);
    }

    /**
     * 대명라이프웨이 카드 배치에 따른 입금 완료 처리
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateCardWdrwReq(Map<String, ?> pmParam) throws Exception {
        return update("DlwCardMap.updateCardWdrwReq", pmParam);
    }

    /**
     * 대명라이프웨이 중복체크
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보
     * @throws Exception
     */
    public List<Map<String, Object>> getCardCallDupWdrw(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCardMap.getCardCallDupWdrw", pmParam);
    }

    /**
     * 대명라이프웨이 중복체크
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보
     * @throws Exception
     */
    public int getCallCardWdrwReqCheck(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCardMap.getCallCardWdrwReqCheck", pmParam);
    }

    /**
     * 대명라이프웨이 실시간 카드결제 - 신청 로그 기록 (대량건)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertRTCardPayLogLrqnt(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCardMap.insertRTCardPayLogLrqnt", pmParam);
    }

    /**
     * 대명라이프웨이 카드 출금이체의뢰 내역 신청상태 변경 (대량건)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateCardAppStateLrqnt(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCardMap.updateCardAppStateLrqnt", pmParam);
    }

    /**
     * 대명라이프웨이 NICE 카드 결과 파일명 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getReadNiceCardResultFileName(Map<String, ?> pmParam) throws Exception {
        List<Map<String, Object>> test =selectList("DlwCardMap.getReadNiceCardResultFileName", pmParam);
        return test;
    }

    /**
     * 대명라이프웨이 NICE 파일변환 가능 유무 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getIsTransByCardNice(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCardMap.getIsTransByCardNice", pmParam);
    }

    /**
     * 대명라이프웨이 NICE 카드 결과 파일 정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getReadFileNiceCard(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCardMap.getReadFileNiceCard", pmParam);
    }


    public void getCARDAppIsTransEb22(Map<String, ?> pmParam) throws Exception {
     //   CommonUtils.printLog(pmParam);
        selectOne("DlwCardMap.getCARDAppIsTransEb22", pmParam);
    }
    /**
     * 대명라이프웨이 NICE 카드 결과 업데이트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> niceCardResultProc(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCardMap.niceCardResultProc", pmParam);
    }





    /***************************************************************************/
    /***************************************************************************/
    /***************************************************************************/
    /***************************************************************************/


    /**
     * 대명라이프웨이 해당구좌의 prod_cl 조회
     *
     * @param pmParam 검색 조건
     * @return prod_cl
     * @throws Exception
     */
    public String getProdClByAccntNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCardMap.getProdClByAccntNo", pmParam);
    }

    /**
     * 대명라이프웨이 부가정보 조회
     *
     * @param pmParam 검색 조건
     * @return  부가정보
     * @throws Exception
     */
    public List<Map<String, Object>> getBugaInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCardMap.getBugaInfo", pmParam);
    }

    /**
     * 대명라이프웨이 입금 newYn 체크
     *
     * @param pmParam 검색 조건
     * @return prod_cl
     * @throws Exception
     */
    public String getpayNewYnChk(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCardMap.getpayNewYnChk", pmParam);
    }

    /**
     * 대명라이프웨이 입금전표 등록 / 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int mergePayMng(Map<String, ?> pmParam) throws Exception {
        if ("insert".equals(pmParam.get("mode"))) {
            return insert("DlwCardMap.insertPayMng", pmParam);
        } else {
            return update("DlwCardMap.updatePayMng", pmParam);
        }
    }

    /**
     * 대명라이프웨이 변경삭제내역 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertReqUpdDelInf(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCardMap.insertReqUpdDelInf", pmParam);
    }

    /**
     * 대명라이프웨이 납입일자가 같은 전표 갯수 조회
     *
     * @param pmParam 검색 조건
     * @return 전표 갯수
     * @throws Exception
     */
    public List<Map<String, Object>> getDCAmtCnt(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCardMap.getDCAmtCnt", pmParam);
    }

    /**
    * * 대명라이프웨이 DC금 seq 삭제
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
    public int deleteDCAmt(Map<String, ?> pmParam) throws Exception {
        return delete("DlwCardMap.deleteDCAmt", pmParam);
    }

    /**
     * 대명라이프웨이 가입일자 - 1회입금일자로  수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateJoinDt(Map<String, ?> pmParam) throws Exception {
        return update("DlwCardMap.updateJoinDt", pmParam);
    }

    /**
     * 대명라이프웨이 입금전표 상태 조회
     *
     * @param pmParam 검색 조건
     * @return 전표 갯수
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngStat(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCardMap.getPayMngStat", pmParam);
    }

    /**
     * 대명라이프웨이 DC금 seq 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateDCAmtSeq(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCardMap.updateDCAmtSeq", pmParam);
    }

    /**
     * 대명라이프웨이 입금전표 등록 / 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int mergePayMngDtl(Map<String, ?> pmParam) throws Exception {
        if ("insert".equals(pmParam.get("mode"))) {
            return insert("DlwCardMap.insertPayMngDtl", pmParam);
        } else {
            return update("DlwCardMap.updatePayMngDtl", pmParam);
        }
    }
    /**
     * 대명라이프웨이 입금전표 등록 / 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int mergePayMngDtl1(Map<String, ?> pmParam) throws Exception {
        if ("insert".equals(pmParam.get("mode"))) {
            return insert("DlwCardMap.insertPayMngDtl1", pmParam);
        } else {
            return update("DlwCardMap.updatePayMngDtl1", pmParam);
        }
    }

    /**
     * 카드일자/회원생일 조회
     *
     * @param pmParam 검색 조건
     * @return 카드일자/회원생일
     * @throws Exception
     */
    public Map<String, Object> getCardAndMemBrth(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCardMap.getCardAndMemBrth", pmParam);
    }

    /**
     * 해피콜 건수 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getHpCallCnt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCardMap.getHpCallCnt", pmParam);
    }

    /**
     * 해피콜 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateHpCall(Map<String, ?> pmParam) throws Exception {
        return update("DlwCardMap.updateHpCall", pmParam);
    }

    /**
     * 해피콜 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertHpCall(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCardMap.insertHpCall", pmParam);
    }

    /**
     * 해피콜 이력 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertHpCallHist(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCardMap.insertHpCallHist", pmParam);
    }

    /**
     * 대명라이프웨이 Card 신청내역 기초데이터 건수 조회
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보
     * @throws Exception
     */
    public int getCardWdrwReqcount_Basic(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCardMap.getCardWdrwReqcount_Basic", pmParam);
    }

    /**
     * 대명라이프웨이 Card 신청내역 기초데이터 조회
     *
     * @param pmParam 검색 조건
     * @return 실시간카드결제 로그정보
     * @throws Exception
     */
    public List<Map<String, Object>> getCardWdrwReqList_Basic(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCardMap.getCardWdrwReqList_Basic", pmParam);
    }

    /**
     * 대명라이프웨이 Card 산출 임시건 등록
     *
     * @param pmParam 검색 조건
     * @return 취소 건수
     * @throws Exception
     */
    public int getCardimsiIchaeDt_Basic(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.getCardimsiIchaeDt_Basic", pmParam);
    }

    /**
     * 대명라이프웨이 산출 기초데이터 Card 확정
     *
     * @param pmParam 검색 조건
     * @return 취소 건수
     * @throws Exception
     */
    public int getBasicWdrwCard_Fix(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.getBasicWdrwCard_Fix", pmParam);
    }

}
