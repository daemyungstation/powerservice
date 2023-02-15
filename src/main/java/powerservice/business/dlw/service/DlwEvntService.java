package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface DlwEvntService {

    public int getEventCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEventList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEventStats(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDetailEvent(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvtMngrRegList(Map<String, ?> pmParam) throws Exception;

    public String getCpGubun(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvtRptDirMst(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvtRptDirGdsList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvtRptDirGdsListB(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvtRptDirGdsListC(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvtRptDirGdsListD(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvtRptDirGdsListE(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvtRptDirAddSale1List(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvtRptDirAddSale2List(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvtRptDirNormlCostList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvtRptDirPayList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvtRptDirPayInfo(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvtRptOutMst(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvtRptOutDtl1List(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvtRptOutDtl2List(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvtRptOutDtl4List(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvtRptOutDtl5List(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvtRptOutPayInfo(Map<String, ?> pmParam) throws Exception;

    public String getWhMvYn(Map<String, ?> pmParam) throws Exception;

    public int getPayAmtCash(Map<String, ?> pmParam) throws Exception;

    public int getFunrlHallCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getFunrlHallList(Map<String, ?> pmParam) throws Exception;

    public int insertEvent(Map<String, ?> pmParam) throws Exception;

    public int updateEvent(Map<String, ?> pmParam) throws Exception;

    public String getCloseDataSaveYnChk(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvtSeq(Map<String, ?> pmParam) throws Exception;

    public int insertCnclReg(Map<String, ?> pmParam) throws Exception;

    public int getEventCheck(Map<String, ?> pmParam) throws Exception;

    public int getResnCheck(Map<String, ?> pmParam) throws Exception;

    public int getTaxtCheck(Map<String, ?> pmParam) throws Exception;

    public int getCmsCheck(Map<String, ?> pmParam) throws Exception;

    public int getCmsReqCnt(Map<String, ?> pmParam) throws Exception;

    public String getProdCl(Map<String, ?> pmParam) throws Exception;

    public String getJoinType(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getAccntSearch(Map<String, ?> pmParam) throws Exception;

    public int deleteEvent(Map<String, ?> pmParam) throws Exception;

    public String getEvtNewYnChk(Map<String, ?> pmParam) throws Exception;

    public String getIsEvtRptGen(Map<String, ?> pmParam) throws Exception;

    public String getCheckB2bPay(Map<String, ?> pmParam) throws Exception;

    public int getDlwManrCount(Map<String, ?> pmParam) throws Exception; // 행사자 건수

    public List<Map<String, Object>> getDlwMngrList(Map<String, ?> pmParam) throws Exception; // 행사자 팝업 리스트

    public int getFunrCount(Map<String, ?> pmParam) throws Exception; // 거래처 장례식장 팝업 조회 건수

    public List<Map<String, Object>> getFunrList(Map<String, ?> pmParam) throws Exception; // 거래처 장례식장 팝업 조회 리스트

    public int getCustmrPopCount(Map<String, ?> pmParam) throws Exception; // 거래처 매입업체 팝업 조회 건수

    public List<Map<String, Object>> getCustmrPopList(Map<String, ?> pmParam) throws Exception; // 거래처 매입업체 팝업 조회 리스트

    public int getCustmrOutCount(Map<String, ?> pmParam) throws Exception; // 거래처 행사외주업체 팝업 조회 건수

    public List<Map<String, Object>> getCustmrOutList(Map<String, ?> pmParam) throws Exception; // 거래처 행사외주업체 팝업 조회 리스트

    public int updateevntMngr(Map<String, ?> pmParam) throws Exception; // 행사자 수정

    public int insertevntMngr(Map<String, ?> pmParam) throws Exception; // 행사자 등록

    public List<Map<String, Object>> getEvntMngrList(Map<String, ?> pmParam) throws Exception; // 행사자 리스트

    public List<Map<String, Object>> getEvntMngrPayList(Map<String, ?> pmParam) throws Exception; // 행사자 수당 기초자료 조회

    public int deleteRowMngr(Map<String, ?> pmParam) throws Exception; // 행사자 삭제

    public int getGoodsCount(Map<String, ?> pmParam) throws Exception; // 품목 팝업 조회 건수

    public List<Map<String, Object>> getGoodsList(Map<String, ?> pmParam) throws Exception; // 품목 팝업 조회 리스트

    public List<Map<String, Object>> getTab1JikList(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 첫번째TAB (장례용품) 조회 리스트

    public int updateJiktab1(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 첫번째TAB (장례용품) 수정

    public int insertJiktab1(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 첫번째TAB (장례용품) 등록

    public List<Map<String, Object>> getTab2JikList(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 두번째TAB (현장발주) 조회 리스트

    public int updateJiktab2(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 두번째TAB (현장발주) 수정

    public int insertJiktab2(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 두번째TAB (현장발주) 등록

    public List<Map<String, Object>> getTab3JikList(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 세번째TAB (장례식장) 조회 리스트

    public int updateJiktab3(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 세번째TAB (장례식장) 수정

    public int insertJiktab3(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 세번째TAB (장례식장) 등록

    public List<Map<String, Object>> getTab4JikList(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 네번째TAB (협력업체) 조회 리스트

    public int updateJiktab4(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 네번째TAB (협력업체) 수정

    public int insertJiktab4(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 네번째TAB (협력업체) 등록

    public List<Map<String, Object>> getTab5JikList(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 다섯번째TAB (기타 용품/입력) 조회 리스트

    public int updateJiktab5(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 다섯번째TAB (기타 용품/입력) 수정

    public int insertJiktab5(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 다섯번째TAB (기타 용품/입력) 등록

    public List<Map<String, Object>> getTab6JikList(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 여섯번째TAB 일반경비 조회 리스트

    public int updateJiktab6(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 여섯번째TAB 일반경비 수정

    public int insertJiktab6(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 여섯번째TAB 일반경비 등록

    public List<Map<String, Object>> getTab7JikList(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 일곱번째TAB 추가매출1 조회 리스트

    public int updateJiktab7(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 일곱번째TAB 추가매출1 수정

    public int insertJiktab7(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 일곱번째TAB 추가매출1 등록

    public List<Map<String, Object>> getTab8JikList(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 여덟번째TAB 추가매출2 조회 리스트

    public int updateJiktab8(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 여덟번째TAB 추가매출2 수정

    public int insertJiktab8(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 여덟번째TAB 추가매출2 등록

    public List<Map<String, Object>> getTab9JikList(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 아홉번째TAB 결제정보 조회 리스트

    public int updateJiktab9(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 아홉번째TAB 결제정보 수정

    public int insertJiktab9(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 아홉번째TAB 결제정보 등록

    public int deleteGoodsTab1(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 첫번째TAB (장례용품) 삭제

    public int deleteGoodsTab2(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 두번째TAB (현장발주) 삭제

    public int deleteGoodsTab3(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 세번째TAB (장례식장) 삭제

    public int deleteGoodsTab4(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 네번째TAB (협력업체) 삭제

    public int deleteGoodsTab5(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 다섯번째TAB (기타용품) 삭제

    public int deleteGoodsTab6(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 여섯번째TAB (일반경비) 삭제

    public int deleteGoodsTab7(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 일곱번째TAB (추가매출1) 삭제

    public int deleteGoodsTab8(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 여덟번째TAB (추가매출2) 삭제

    public int deleteGoodsTab9(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 아홉번째TAB (결제정보관련) 삭제

    public List<Map<String, Object>> getCardFeeList(Map<String, ?> pmParam) throws Exception; // 카드수수료 조회 리스트

    public List<Map<String, Object>> getOutsrc_1(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 제공용품1 조회 리스트

    public List<Map<String, Object>> getOutsrc_2(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 추가매출1 조회 리스트

    public List<Map<String, Object>> getOutsrc_3(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 제공용품2 및 추가매출2 조회 리스트

    public List<Map<String, Object>> getOutsrc_4(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 결제정보 관련 조회 리스트

    public int updateOutsrc_1(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 제공용품1 수정

    public int insertOutsrc_1(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 제공용품1 등록

    public int updateOutsrc_2(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 추가매출1 수정

    public int insertOutsrc_2(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 추가매출1등록

    public int updateOutsrc_3(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 제공용품2 및 추가매출2 수정

    public int insertOutsrc_3(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 제공용품2 및 추가매출2 등록

    public int updateOutsrc_4(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 결제정보 수정

    public int insertOutsrc_4(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 결제정보 등록

    public int deleteGoodsOutds1(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 제공용품1 삭제

    public int deleteGoodsOutds2(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 추가매출1 삭제

    public int deleteGoodsOutds3(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 제공용품2 및 추가매출2 삭제

    public int deleteGoodsOutds4(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 결제정보 삭제

    public int getAnnlCount(Map<String, ?> pmParam) throws Exception; // 행사현황 및 분석 조회(메인) 건수

    public List<Map<String, Object>> getAnnlList(Map<String, ?> pmParam) throws Exception; // 행사현황 및 분석 조회(메인) 리스트

    public List<Map<String, Object>> getBranchList(Map<String, ?> pmParam) throws Exception; // 지부 콤보박스 리스트

    public List<Map<String, Object>> getEvntPayData(Map<String, ?> pmParam) throws Exception; // 행사정산 입금정보 조회

    public List<Map<String, Object>> getEvntPayDtl(Map<String, ?> pmParam) throws Exception; // 입금회차에 따른 입금상세정보 조회

    public List<Map<String, Object>> getEvntPayDtlCnt(Map<String, ?> pmParam) throws Exception; // 입금회차에 따른 입금상세정보 조회(회차별)

    public List<Map<String, Object>> getEvntRptDtl(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) 상세 조회

    public int insertEvntRptDtl(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 등록

    public int updateEvntRptDtl(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 수정

    public int updateEvntRptMagam(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 마감저장

    public int deleteEvntRptDtl(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 삭제

    public List<Map<String, Object>> getEvntOutRptDtl(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) 상세 조회

    public int insertEvntOutRptDtl(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 등록

    public int updateEvntOutRptDtl(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 수정

    public int updateEvntOutRptDtlMagam(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 마감저장

    public int deleteEvntOutRptDtl(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 삭제

    public int insertUpdBitHis(Map<String, ?> pmParam) throws Exception; // 회원상태 변경이력 등록(행사등록 시)

    public int updateMemprodKstbit(Map<String, ?> pmParam) throws Exception; // 회원상태 변경(행사등록 시)

    public int updateMemprodKstbit_cncl(Map<String, ?> pmParam) throws Exception; // 회원상태 변경(행사취소 시)

    public int insertUpdBitHis_cncl(Map<String, ?> pmParam) throws Exception;     // 회원상태 변경이력 등록(행사취소 시)

    public List<Map<String, Object>> getMoveGoodsList(Map<String, ?> pmParam) throws Exception; // 사업장내 창고이동 물품리스트 조회

    public List<Map<String, Object>> getMoveGoodsMst(Map<String, ?> pmParam) throws Exception; // 사업장내 창고이동 마스터 조회

    public String getWhmvNo(Map<String, ?> pmParam) throws Exception;     // 창고번호 가져오기

    public String getWhinNo(Map<String, ?> pmParam) throws Exception;     // 입고번호 가져오기

    public String getWhcd(Map<String, ?> pmParam) throws Exception;     // 출고창고 가져오기

    public String getWhoutNo(Map<String, ?> pmParam) throws Exception;     // 출고번호 가져오기

    public String getSinWhmvNo(Map<String, ?> pmParam) throws Exception;     // 창고번호 신규생성

    public String getSinWhinNo(Map<String, ?> pmParam) throws Exception;     // 입고번호 신규생성

    public String getSinWhoutNo(Map<String, ?> pmParam) throws Exception;     // 출고번호 신규생성

    public int insertWhOutMst(Map<String, ?> pmParam) throws Exception; // 출고 마스터 저장

    public int insertWhinMst(Map<String, ?> pmParam) throws Exception; // 입고 마스터 저장

    public int insertWhmvMst(Map<String, ?> pmParam) throws Exception; // 창고이동 마스터 저장

    public int insertWhOutDtl(Map<String, ?> pmParam) throws Exception; // 출고 디테일 저장

    public int insertWhinDtl(Map<String, ?> pmParam) throws Exception; // 입고 디테일 저장

    public int insertWhmvDtl(Map<String, ?> pmParam) throws Exception; // 창고이동 디테일 저장

    public int deleteWhmvMst(Map<String, ?> pmParam) throws Exception; // 창고이동 마스터 삭제비트 변경

    public int deleteWhmvDtl(Map<String, ?> pmParam) throws Exception; // 창고이동 디테일 데이터 삭제

    public int deleteWhOutMst(Map<String, ?> pmParam) throws Exception; // 출고 마스터 삭제비트 변경

    public int deleteWhOutDtl(Map<String, ?> pmParam) throws Exception; // 출고 디테일 데이터 삭제

    public int deleteWhinMst(Map<String, ?> pmParam) throws Exception; // 입고 마스터 삭제비트 변경

    public int deleteWhinDtl(Map<String, ?> pmParam) throws Exception; // 입고 디테일 데이터 삭제

    public List<Map<String, Object>> getPayTotalList(Map<String, ?> pmParam) throws Exception; // 입금마감 팝업 리스트 조회

    public List<Map<String, Object>> getEvntAnal1(Map<String, ?> pmParam) throws Exception; // 장법 분석현황

    public List<Map<String, Object>> getEvntAnal2(Map<String, ?> pmParam) throws Exception; // 신규유지 분석현황

    public List<Map<String, Object>> getEvntAnal3(Map<String, ?> pmParam) throws Exception; // 지부 분석현황

    public List<Map<String, Object>> getEvntAnal4(Map<String, ?> pmParam) throws Exception; // 지역 분석현황

    public List<Map<String, Object>> getEvntAnal5(Map<String, ?> pmParam) throws Exception; // 직영CP 분석현황

    public List<Map<String, Object>> getEvntAnal6(Map<String, ?> pmParam) throws Exception; // 종교 분석현황

    public int getAnnlCnclCount(Map<String, ?> pmParam) throws Exception; // 행사취소현황 및 분석(취소현황 조회건수)

    public List<Map<String, Object>> getAnnlCnclList(Map<String, ?> pmParam) throws Exception; // 행사취소현황 및 분석(취소현황 조회리스트)

    public List<Map<String, Object>> getEvntCncl1(Map<String, ?> pmParam) throws Exception; // 지부별 취소분석

    public List<Map<String, Object>> getEvntCncl2(Map<String, ?> pmParam) throws Exception; // 취소사유별 취소분석

    public List<Map<String, Object>> getEvntCncl3(Map<String, ?> pmParam) throws Exception; // 소요시간별 취소분석

    public List<Map<String, Object>> getEventSrvProd1(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스제공현황 조회 - 상복

    public int getEvntCpChkCount(Map<String, ?> pmParam) throws Exception; // 등록된 CP 여부 체크

    public int insertEvntPaymng(Map<String, ?> pmParam) throws Exception; // 입금마감 - 상조부금

    public int insertEvntPaymngDtl(Map<String, ?> pmParam) throws Exception; // 입금마감 - 결합금

    public int insertEvntPaymngDtl1(Map<String, ?> pmParam) throws Exception; // 입금마감 - 추가금

    public List<Map<String, Object>> getEvntPicSort(Map<String, Object> pmParam) throws Exception; // 의전행사 사진 분류 - 행사회원 조회

    public List<Map<String, Object>> getEvntPicList(Map<String, Object> pmParam) throws Exception; // 의전행사 사진 분류 - 분류/미분류회원 조회

    public int disconncectAccnt(XPlatformMapDTO xpDto) throws Exception; // 선택파일 미분류로 이동

    public int conncectAccnt(XPlatformMapDTO xpDto) throws Exception; // 선택파일 회원연결

    public int deleteEvtPic(XPlatformMapDTO xpDto) throws Exception; // 행사사진 삭제

    public List<Map<String, Object>> evtPicBatchDownload(XPlatformMapDTO xpDto) throws Exception; // 행사사진 다운로드 파일 압축

    public int insertEvtPicInfo(XPlatformMapDTO xpDto) throws Exception; // 의전행사 사진 등록
    
    public void saveEvenPhotoUpLoad(HttpServletRequest request,HttpServletResponse response) throws Exception; 

    public List<Map<String, Object>> getEvntBrchAnal1(Map<String, ?> pmParam) throws Exception; // 행사분석(상품분류명/지부별) - 헤더

    public String getbranchQury(Map<String, ?> pmParam) throws Exception; // 상품분류명/지부별 쿼리 불러오기

    public List<Map<String, Object>> getEvntBrchAnal2(Map<String, ?> pmParam) throws Exception; // 행사분석(상품분류명/지부별) - 리스트

    public List<Map<String, Object>> getEvntEmplAnal1(Map<String, ?> pmParam) throws Exception; // 행사분석(CP별/상품분류별) - 헤더

    public String getEmplQury(Map<String, ?> pmParam) throws Exception; // 행사분석(CP별/상품분류별) 쿼리 불러오기

    public List<Map<String, Object>> getEvntEmplAnal2(Map<String, ?> pmParam) throws Exception; // 행사분석(CP별/상품분류별) - 리스트

    public List<Map<String, Object>> getEvntBrchCncl1(Map<String, ?> pmParam) throws Exception; // 행사취소분석(상품분류명/지부별) - 헤더

    public String getbranchQuryCncl(Map<String, ?> pmParam) throws Exception; // 행사취소분석 상품분류명/지부별 쿼리 불러오기

    public List<Map<String, Object>> getEvntBrchCncl2(Map<String, ?> pmParam) throws Exception; // 행사취소분석(상품분류명/지부별) - 리스트

    public List<Map<String, Object>> getEvntEmplCncl1(Map<String, ?> pmParam) throws Exception; // 행사취소분석(CP별/상품분류별) - 헤더

    public String getEmplQuryCncl(Map<String, ?> pmParam) throws Exception; // 행사취소분석(CP별/상품분류별) 쿼리 불러오기

    public List<Map<String, Object>> getEvntEmplCncl2(Map<String, ?> pmParam) throws Exception; // 행사취소분석(CP별/상품분류별) - 리스트

    public int getMngrIpCount(Map<String, ?> pmParam) throws Exception; // 외부 일용직 입금대장 조회 건수

    public List<Map<String, Object>> getMngrIpList(Map<String, ?> pmParam) throws Exception; // 외부 일용직 입금대장 조회 리스트

    public int getStockClCount(Map<String, ?> pmParam) throws Exception; // 장례용품 재고현황 조회 건수

    public List<Map<String, Object>> getStockClList(Map<String, ?> pmParam) throws Exception; // 장례용품 재고현황 조회 리스트

    public List<Map<String, Object>> getAddSalesInList(Map<String, ?> pmParam) throws Exception; // 추가매출 - 직영 조회 리스트

    public List<Map<String, Object>> getAddSalesOutList(Map<String, ?> pmParam) throws Exception; // 추가매출 - 외주 조회 리스트
    
    public List<Map<String, Object>> getFuneralList(Map<String, ?> pmParam) throws Exception; // 추가매출 - 장지 조회 리스트

    public int getOrderAnalCount(Map<String, ?> pmParam) throws Exception; // 일자별 발주현황 조회 건수

    public List<Map<String, Object>> getOrderAnalList(Map<String, ?> pmParam) throws Exception; // 일자별 발주현황 조회 리스트

    public List<Map<String, Object>> getEvntRemainAmtList(Map<String, ?> pmParam) throws Exception; // 행사잔금처리현황 조회 리스트

    public int RemainBigoUpdate(Map<String, ?> pmParam) throws Exception; // 잔금처리현황 비고 수정

    public int getStockDayCount(Map<String, ?> pmParam) throws Exception; // 일자별 입출고 내역 조회 건수

    public List<Map<String, Object>> getStockDayList(Map<String, ?> pmParam) throws Exception; // 일자별 입출고 내역 조회 리스트

    public List<Map<String, Object>> getsmsPayMent(Map<String, ?> pmParam) throws Exception; // SMS 전송 시 결제정보 조회(현금영수증 정보)

    public List<Map<String, Object>> getSrvProdList1(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스 제공현황 - 상복

    public List<Map<String, Object>> getSrvProdList2(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스 제공현황 - 제단

    public List<Map<String, Object>> getSrvProdList3(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스 제공현황 - 장의차량

    public List<Map<String, Object>> getSrvProdList4(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스 제공현황 - 앰블런스

    public List<Map<String, Object>> getSrvProdList5(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스 제공현황 - 유골함

    public List<Map<String, Object>> getSrvProdList_Head(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스 제공현황 - 직영행사물품 헤더

    public List<Map<String, Object>> getSrvProdList_func(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스 제공현황 - 직영행사물품 펑션 실행

    public List<Map<String, Object>> getSrvProdList_func2(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스 제공현황 - 장례식장(금액)

    public String getSrvProdList_qry(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스 제공현황 - 직영행사물품 쿼리 가져오기

    public List<Map<String, Object>> getSrvProdListIn(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스 제공현황 - 직영행사물품 조회

    public List<Map<String, Object>> getSrvProdAnal1(Map<String, ?> pmParam) throws Exception; // 물품 제공현황 분석- 상복

    public List<Map<String, Object>> getSrvProdAnal2(Map<String, ?> pmParam) throws Exception; // 물품 제공현황 분석 - 제단

    public List<Map<String, Object>> getSrvProdAnal3(Map<String, ?> pmParam) throws Exception; // 물품 제공현황 분석 - 장의차량

    public List<Map<String, Object>> getSrvProdAnal4(Map<String, ?> pmParam) throws Exception; // 물품 제공현황 분석 - 장례식장(CP)

    public List<Map<String, Object>> getSrvProdAnal5(Map<String, ?> pmParam) throws Exception; // 물품 제공현황 분석 - 월별분석

    public List<Map<String, Object>> getMonthBrList(Map<String, ?> pmParam) throws Exception; // 월별 지부별 장의용품 재고현황

    public List<Map<String, Object>> getMonthWhList(Map<String, ?> pmParam) throws Exception; // 월별 창고별 장의용품 재고현황

    public List<Map<String, Object>> getGdsIpList_Head(Map<String, ?> pmParam) throws Exception; // 일자별 지부별 물품 입고 조회 - 헤더

    public List<Map<String, Object>> getGdsIpList_func(Map<String, ?> pmParam) throws Exception; //  일자별 지부별 물품 입고 조회 - 펑션 실행

    public String getGdsIpList_qry(Map<String, ?> pmParam) throws Exception; //  일자별 지부별 물품 입고 조회 - 쿼리 가져오기

    public List<Map<String, Object>> getGdsIpList(Map<String, ?> pmParam) throws Exception; //  일자별 지부별 물품 입고 조회 - 조회

    public List<Map<String, Object>> getGdsUseList_Head(Map<String, ?> pmParam) throws Exception; // 일자별 지부별 물품 사용내역 조회 - 헤더

    public List<Map<String, Object>> getGdsUseList_func(Map<String, ?> pmParam) throws Exception; //  일자별 지부별 물품 사용내역 조회 - 펑션 실행

    public String getGdsUseList_qry(Map<String, ?> pmParam) throws Exception; //  일자별 지부별 물품 사용내역 조회 - 쿼리 가져오기

    public List<Map<String, Object>> getGdsUseList(Map<String, ?> pmParam) throws Exception; //  일자별 지부별 물품 사용내역 조회 - 조회

    public List<Map<String, Object>> getCostList(Map<String, ?> pmParam) throws Exception; //  CP별/일반경비별 현황 조회

    public List<Map<String, Object>> getCostMemList(Map<String, ?> pmParam) throws Exception; //  회원별 일반경비별 현황 조회

    public List<Map<String, Object>> getBenefitList1(Map<String, ?> pmParam) throws Exception; //  손익분석집계표 - 행사별

    public List<Map<String, Object>> getBenefitList2(Map<String, ?> pmParam) throws Exception; //  손익분석집계표 - 월별

    public List<Map<String, Object>> getBenefitList3(Map<String, ?> pmParam) throws Exception; //  손익분석집계표 - 지부별

    public List<Map<String, Object>> getBenefitList4(Map<String, ?> pmParam) throws Exception; //  손익분석집계표 - CP별

    public List<Map<String, Object>> getBenefitList5(Map<String, ?> pmParam) throws Exception; //  손익분석집계표 - 외주업체별

    public List<Map<String, Object>> getStockGoodsList(Map<String, ?> pmParam) throws Exception; //  재고마감 - 품목별 마감 조회

    public List<Map<String, Object>> getStockWhList(Map<String, ?> pmParam) throws Exception; //  재고마감 - 창고별 마감 조회

    public int deleteStockMg(Map<String, ?> pmParam) throws Exception; // 마감취소 - 재고관리 데이터 삭제

    public int insertStockMg(Map<String, ?> pmParam) throws Exception; // 마감저장 - 재고관리 프로시저 호출

    public String getOrdNo(Map<String, ?> pmParam) throws Exception;     // 발주번호 가져오기

    public String getSinOrdNo(Map<String, ?> pmParam) throws Exception;     // 발주번호 신규생성

    public int deleteOrdMst(Map<String, ?> pmParam) throws Exception; // 발주 마스터 삭제비트 변경

    public int deleteOrdDtl(Map<String, ?> pmParam) throws Exception; // 발주 디테일 데이터 삭제

    public int insertOrdMst(Map<String, ?> pmParam) throws Exception; // 발주 마스터 저장

    public int insertOrdDtl(Map<String, ?> pmParam) throws Exception; // 발주 디테일 저장

    public int updateRepGoods(Map<String, ?> pmParam) throws Exception; // 대체용품 수정

    public int insertRepGoods(Map<String, ?> pmParam) throws Exception; // 대체용품 입력

    public int deleteRepGoods(Map<String, ?> pmParam) throws Exception; // 대체용품 삭제

    public List<Map<String, Object>> getEvtRepList(Map<String, ?> pmParam) throws Exception; //  대체용품 회원 조회

    public int getRepGoodsCount(Map<String, ?> pmParam) throws Exception; // 대체용품현황 조회 건수

    public List<Map<String, Object>> getRepGoodsList(Map<String, ?> pmParam) throws Exception; // 대체용품현황 조회 리스트

    public List<Map<String, Object>> getEvtUjiList(Map<String, ?> pmParam) throws Exception; // 행사유지현황 리스트

    public List<Map<String, Object>> getEvntChatInfo(Map<String, ?> pmParam) throws Exception; // 행사문자 전송시 회원정보 조회

    /*************************************************************************
     *  2017.07.27 김준호
     *  관리업무>행사조회>모니터링>모니터링 결과 보고서
     *  중복 입력시 오류건으로 인해 저장전 회원번호로 EVENT TABLE확인후 저장
     ***********************************************************************/
    public int countEvent(Map<String, Object> hmParam) throws Exception;// 행사 등록시 해당 회원번호로 행사테이블 조회

    public List<Map<String, Object>> getEvntcjChatInfo(Map<String, ?> pmParam) throws Exception; //문자 전송시 회원정보 조회

    /*************************************************************************
     *  2017.09.26 김준호
     *  알림톡 구동시 가상계좌 검색
     ***********************************************************************/
    public List<Map<String, Object>> getEventcjChatVrtlInfo(Map<String, Object> hmParam);

    /* ================================================================
     * 날짜 : 20171226
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰 연동 Table에 해당 고객의 상태값을 행사로 바꿈
     * ================================================================
     * */
    public int updateEventMemberState(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20181114
     * 이름 : 송준빈
     * 추가내용 : 청구가 존재하는 회원인지 확인
     * ================================================================
     * */
	public List<Map<String, Object>> getWdrwReqConfirm(Map pmParam);
	
	public int existsRptDtlConfirm(Map<String, ?> pmParam) throws Exception;
	
	public int existsOutRptDtlConfirm(Map<String, ?> pmParam) throws Exception;
}