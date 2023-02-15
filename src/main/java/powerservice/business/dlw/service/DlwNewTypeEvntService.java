package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface DlwNewTypeEvntService {

    public int getNewTypeEventCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeEventList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeEventStats(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeDetailEvent(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeEvtMngrRegList(Map<String, ?> pmParam) throws Exception;

    public String getNewTypeCpGubun(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeEvtRptDirMst(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeEvtRptDirGdsList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeEvtRptDirGdsListB(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeEvtRptDirGdsListC(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeEvtRptDirGdsListD(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeEvtRptDirGdsListE(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeEvtRptDirAddSale1List(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeEvtRptDirAddSale2List(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeEvtRptDirNormlCostList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeEvtRptDirPayList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeEvtRptDirPayInfo(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeEvtRptOutMst(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeEvtRptOutDtl1List(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeEvtRptOutDtl2List(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeEvtRptOutDtl4List(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeEvtRptOutDtl5List(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeEvtRptOutPayInfo(Map<String, ?> pmParam) throws Exception;

    public String getNewTypeWhMvYn(Map<String, ?> pmParam) throws Exception;

    public int getNewTypePayAmtCash(Map<String, ?> pmParam) throws Exception;

    public int getNewTypeFunrlHallCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeFunrlHallList(Map<String, ?> pmParam) throws Exception;

    public int insertNewTypeEvent(Map<String, ?> pmParam) throws Exception;

    public int updateNewTypeEvent(Map<String, ?> pmParam) throws Exception;

    public String getNewTypeCloseDataSaveYnChk(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeEvtSeq(Map<String, ?> pmParam) throws Exception;

    public int insertNewTypeCnclReg(Map<String, ?> pmParam) throws Exception;

    public int getNewTypeEventCheck(Map<String, ?> pmParam) throws Exception;

    public int getNewTypeResnCheck(Map<String, ?> pmParam) throws Exception;

    public int getNewTypeTaxtCheck(Map<String, ?> pmParam) throws Exception;

    public int getNewTypeCmsCheck(Map<String, ?> pmParam) throws Exception;

    public int getNewTypeCmsReqCnt(Map<String, ?> pmParam) throws Exception;

    public String getNewTypeProdCl(Map<String, ?> pmParam) throws Exception;

    public String getNewTypeJoinType(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeAccntSearch(Map<String, ?> pmParam) throws Exception;

    public int deleteNewTypeEvent(Map<String, ?> pmParam) throws Exception;

    public String getNewTypeEvtNewYnChk(Map<String, ?> pmParam) throws Exception;

    public String getNewTypeIsEvtRptGen(Map<String, ?> pmParam) throws Exception;

    public String getNewTypeCheckB2bPay(Map<String, ?> pmParam) throws Exception;

    public int getNewTypeDlwManrCount(Map<String, ?> pmParam) throws Exception; // 행사자 건수

    public List<Map<String, Object>> getNewTypeDlwMngrList(Map<String, ?> pmParam) throws Exception; // 행사자 팝업 리스트

    public int getNewTypeFunrCount(Map<String, ?> pmParam) throws Exception; // 거래처 장례식장 팝업 조회 건수

    public List<Map<String, Object>> getNewTypeFunrList(Map<String, ?> pmParam) throws Exception; // 거래처 장례식장 팝업 조회 리스트

    public int getNewTypeCustmrPopCount(Map<String, ?> pmParam) throws Exception; // 거래처 매입업체 팝업 조회 건수

    public List<Map<String, Object>> getNewTypeCustmrPopList(Map<String, ?> pmParam) throws Exception; // 거래처 매입업체 팝업 조회 리스트

    public int getNewTypeCustmrOutCount(Map<String, ?> pmParam) throws Exception; // 거래처 행사외주업체 팝업 조회 건수

    public List<Map<String, Object>> getNewTypeCustmrOutList(Map<String, ?> pmParam) throws Exception; // 거래처 행사외주업체 팝업 조회 리스트

    public int updateNewTypeevntMngr(Map<String, ?> pmParam) throws Exception; // 행사자 수정

    public int insertNewTypeevntMngr(Map<String, ?> pmParam) throws Exception; // 행사자 등록

    public List<Map<String, Object>> getNewTypeEvntMngrList(Map<String, ?> pmParam) throws Exception; // 행사자 리스트

    public List<Map<String, Object>> getNewTypeEvntMngrPayList(Map<String, ?> pmParam) throws Exception; // 행사자 수당 기초자료 조회

    public int deleteNewTypeRowMngr(Map<String, ?> pmParam) throws Exception; // 행사자 삭제

    public int getNewTypeGoodsCount(Map<String, ?> pmParam) throws Exception; // 품목 팝업 조회 건수

    public List<Map<String, Object>> getNewTypeGoodsList(Map<String, ?> pmParam) throws Exception; // 품목 팝업 조회 리스트

    public List<Map<String, Object>> getNewTypeTab1JikList(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 첫번째TAB (장례용품) 조회 리스트

    public int updateNewTypeJiktab1(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 첫번째TAB (장례용품) 수정

    public int insertNewTypeJiktab1(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 첫번째TAB (장례용품) 등록

    public List<Map<String, Object>> getNewTypeTab2JikList(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 두번째TAB (현장발주) 조회 리스트

    public int updateNewTypeJiktab2(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 두번째TAB (현장발주) 수정

    public int insertNewTypeJiktab2(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 두번째TAB (현장발주) 등록

    public List<Map<String, Object>> getNewTypeTab3JikList(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 세번째TAB (장례식장) 조회 리스트

    public int updateNewTypeJiktab3(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 세번째TAB (장례식장) 수정

    public int insertNewTypeJiktab3(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 세번째TAB (장례식장) 등록

    public List<Map<String, Object>> getNewTypeTab4JikList(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 네번째TAB (협력업체) 조회 리스트

    public int updateNewTypeJiktab4(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 네번째TAB (협력업체) 수정

    public int insertNewTypeJiktab4(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 네번째TAB (협력업체) 등록

    public List<Map<String, Object>> getNewTypeTab5JikList(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 다섯번째TAB (기타 용품/입력) 조회 리스트

    public int updateNewTypeJiktab5(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 다섯번째TAB (기타 용품/입력) 수정

    public int insertNewTypeJiktab5(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 다섯번째TAB (기타 용품/입력) 등록

    public List<Map<String, Object>> getNewTypeTab6JikList(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 여섯번째TAB 일반경비 조회 리스트

    public int updateNewTypeJiktab6(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 여섯번째TAB 일반경비 수정

    public int insertNewTypeJiktab6(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 여섯번째TAB 일반경비 등록

    public List<Map<String, Object>> getNewTypeTab7JikList(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 일곱번째TAB 추가매출1 조회 리스트

    public int updateNewTypeJiktab7(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 일곱번째TAB 추가매출1 수정

    public int insertNewTypeJiktab7(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 일곱번째TAB 추가매출1 등록

    public List<Map<String, Object>> getNewTypeTab8JikList(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 여덟번째TAB 추가매출2 조회 리스트

    public int updateNewTypeJiktab8(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 여덟번째TAB 추가매출2 수정

    public int insertNewTypeJiktab8(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 여덟번째TAB 추가매출2 등록

    public List<Map<String, Object>> getNewTypeTab9JikList(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 아홉번째TAB 결제정보 조회 리스트

    public int updateNewTypeJiktab9(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 아홉번째TAB 결제정보 수정

    public int insertNewTypeJiktab9(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 아홉번째TAB 결제정보 등록

    public int deleteNewTypeGoodsTab1(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 첫번째TAB (장례용품) 삭제

    public int deleteNewTypeGoodsTab2(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 두번째TAB (현장발주) 삭제

    public int deleteNewTypeGoodsTab3(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 세번째TAB (장례식장) 삭제

    public int deleteNewTypeGoodsTab4(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 네번째TAB (협력업체) 삭제

    public int deleteNewTypeGoodsTab5(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 다섯번째TAB (기타용품) 삭제

    public int deleteNewTypeGoodsTab6(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 여섯번째TAB (일반경비) 삭제

    public int deleteNewTypeGoodsTab7(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 일곱번째TAB (추가매출1) 삭제

    public int deleteNewTypeGoodsTab8(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 여덟번째TAB (추가매출2) 삭제

    public int deleteNewTypeGoodsTab9(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 아홉번째TAB (결제정보관련) 삭제

    public List<Map<String, Object>> getNewTypeCardFeeList(Map<String, ?> pmParam) throws Exception; // 카드수수료 조회 리스트

    public List<Map<String, Object>> getNewTypeOutsrc_1(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 제공용품1 조회 리스트

    public List<Map<String, Object>> getNewTypeOutsrc_2(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 추가매출1 조회 리스트

    public List<Map<String, Object>> getNewTypeOutsrc_3(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 제공용품2 및 추가매출2 조회 리스트

    public List<Map<String, Object>> getNewTypeOutsrc_4(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 결제정보 관련 조회 리스트

    public int updateNewTypeOutsrc_1(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 제공용품1 수정

    public int insertNewTypeOutsrc_1(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 제공용품1 등록

    public int updateNewTypeOutsrc_2(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 추가매출1 수정

    public int insertNewTypeOutsrc_2(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 추가매출1등록

    public int updateNewTypeOutsrc_3(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 제공용품2 및 추가매출2 수정

    public int insertNewTypeOutsrc_3(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 제공용품2 및 추가매출2 등록

    public int updateNewTypeOutsrc_4(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 결제정보 수정

    public int insertNewTypeOutsrc_4(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 결제정보 등록

    public int deleteNewTypeGoodsOutds1(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 제공용품1 삭제

    public int deleteNewTypeGoodsOutds2(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 추가매출1 삭제

    public int deleteNewTypeGoodsOutds3(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 제공용품2 및 추가매출2 삭제

    public int deleteNewTypeGoodsOutds4(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 결제정보 삭제

    public int getNewTypeAnnlCount(Map<String, ?> pmParam) throws Exception; // 행사현황 및 분석 조회(메인) 건수

    public List<Map<String, Object>> getNewTypeAnnlList(Map<String, ?> pmParam) throws Exception; // 행사현황 및 분석 조회(메인) 리스트

    public List<Map<String, Object>> getNewTypeBranchList(Map<String, ?> pmParam) throws Exception; // 지부 콤보박스 리스트

    public List<Map<String, Object>> getNewTypeEvntPayData(Map<String, ?> pmParam) throws Exception; // 행사정산 입금정보 조회

    public List<Map<String, Object>> getNewTypeEvntPayDtl(Map<String, ?> pmParam) throws Exception; // 입금회차에 따른 입금상세정보 조회

    public List<Map<String, Object>> getNewTypeEvntPayDtlCnt(Map<String, ?> pmParam) throws Exception; // 입금회차에 따른 입금상세정보 조회(회차별)

    public List<Map<String, Object>> getNewTypeEvntRptDtl(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) 상세 조회

    public int insertNewTypeEvntRptDtl(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 등록

    public int updateNewTypeEvntRptDtl(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 수정

    public int updateNewTypeEvntRptMagam(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 마감저장

    public int deleteNewTypeEvntRptDtl(Map<String, ?> pmParam) throws Exception; // 행사보고서(직영) - 삭제

    public List<Map<String, Object>> getNewTypeEvntOutRptDtl(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) 상세 조회

    public int insertNewTypeEvntOutRptDtl(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 등록

    public int updateNewTypeEvntOutRptDtl(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 수정

    public int updateNewTypeEvntOutRptDtlMagam(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 마감저장

    public int deleteNewTypeEvntOutRptDtl(Map<String, ?> pmParam) throws Exception; // 행사보고서(외주) - 삭제

    public int insertNewTypeUpdBitHis(Map<String, ?> pmParam) throws Exception; // 회원상태 변경이력 등록(행사등록 시)

    public int updateNewTypeMemprodKstbit(Map<String, ?> pmParam) throws Exception; // 회원상태 변경(행사등록 시)

    public int updateNewTypeMemprodKstbit_cncl(Map<String, ?> pmParam) throws Exception; // 회원상태 변경(행사취소 시)

    public int insertNewTypeUpdBitHis_cncl(Map<String, ?> pmParam) throws Exception;     // 회원상태 변경이력 등록(행사취소 시)

    public List<Map<String, Object>> getNewTypeMoveGoodsList(Map<String, ?> pmParam) throws Exception; // 사업장내 창고이동 물품리스트 조회

    public List<Map<String, Object>> getNewTypeMoveGoodsMst(Map<String, ?> pmParam) throws Exception; // 사업장내 창고이동 마스터 조회

    public String getNewTypeWhmvNo(Map<String, ?> pmParam) throws Exception;     // 창고번호 가져오기

    public String getNewTypeWhinNo(Map<String, ?> pmParam) throws Exception;     // 입고번호 가져오기

    public String getNewTypeWhcd(Map<String, ?> pmParam) throws Exception;     // 출고창고 가져오기

    public String getNewTypeWhoutNo(Map<String, ?> pmParam) throws Exception;     // 출고번호 가져오기

    public String getNewTypeSinWhmvNo(Map<String, ?> pmParam) throws Exception;     // 창고번호 신규생성

    public String getNewTypeSinWhinNo(Map<String, ?> pmParam) throws Exception;     // 입고번호 신규생성

    public String getNewTypeSinWhoutNo(Map<String, ?> pmParam) throws Exception;     // 출고번호 신규생성

    public int insertNewTypeWhOutMst(Map<String, ?> pmParam) throws Exception; // 출고 마스터 저장

    public int insertNewTypeWhinMst(Map<String, ?> pmParam) throws Exception; // 입고 마스터 저장

    public int insertNewTypeWhmvMst(Map<String, ?> pmParam) throws Exception; // 창고이동 마스터 저장

    public int insertNewTypeWhOutDtl(Map<String, ?> pmParam) throws Exception; // 출고 디테일 저장

    public int insertNewTypeWhinDtl(Map<String, ?> pmParam) throws Exception; // 입고 디테일 저장

    public int insertNewTypeWhmvDtl(Map<String, ?> pmParam) throws Exception; // 창고이동 디테일 저장

    public int deleteNewTypeWhmvMst(Map<String, ?> pmParam) throws Exception; // 창고이동 마스터 삭제비트 변경

    public int deleteNewTypeWhmvDtl(Map<String, ?> pmParam) throws Exception; // 창고이동 디테일 데이터 삭제

    public int deleteNewTypeWhOutMst(Map<String, ?> pmParam) throws Exception; // 출고 마스터 삭제비트 변경

    public int deleteNewTypeWhOutDtl(Map<String, ?> pmParam) throws Exception; // 출고 디테일 데이터 삭제

    public int deleteNewTypeWhinMst(Map<String, ?> pmParam) throws Exception; // 입고 마스터 삭제비트 변경

    public int deleteNewTypeWhinDtl(Map<String, ?> pmParam) throws Exception; // 입고 디테일 데이터 삭제

    public List<Map<String, Object>> getNewTypePayTotalList(Map<String, ?> pmParam) throws Exception; // 입금마감 팝업 리스트 조회

    public List<Map<String, Object>> getNewTypeEvntAnal1(Map<String, ?> pmParam) throws Exception; // 장법 분석현황

    public List<Map<String, Object>> getNewTypeEvntAnal2(Map<String, ?> pmParam) throws Exception; // 신규유지 분석현황

    public List<Map<String, Object>> getNewTypeEvntAnal3(Map<String, ?> pmParam) throws Exception; // 지부 분석현황

    public List<Map<String, Object>> getNewTypeEvntAnal4(Map<String, ?> pmParam) throws Exception; // 지역 분석현황

    public List<Map<String, Object>> getNewTypeEvntAnal5(Map<String, ?> pmParam) throws Exception; // 직영CP 분석현황

    public List<Map<String, Object>> getNewTypeEvntAnal6(Map<String, ?> pmParam) throws Exception; // 종교 분석현황

    public int getNewTypeAnnlCnclCount(Map<String, ?> pmParam) throws Exception; // 행사취소현황 및 분석(취소현황 조회건수)

    public List<Map<String, Object>> getNewTypeAnnlCnclList(Map<String, ?> pmParam) throws Exception; // 행사취소현황 및 분석(취소현황 조회리스트)

    public List<Map<String, Object>> getNewTypeEvntCncl1(Map<String, ?> pmParam) throws Exception; // 지부별 취소분석

    public List<Map<String, Object>> getNewTypeEvntCncl2(Map<String, ?> pmParam) throws Exception; // 취소사유별 취소분석

    public List<Map<String, Object>> getNewTypeEvntCncl3(Map<String, ?> pmParam) throws Exception; // 소요시간별 취소분석

    public List<Map<String, Object>> getNewTypeEventSrvProd1(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스제공현황 조회 - 상복

    public int getNewTypeEvntCpChkCount(Map<String, ?> pmParam) throws Exception; // 등록된 CP 여부 체크

    public int insertNewTypeEvntPaymng(Map<String, ?> pmParam) throws Exception; // 입금마감 - 상조부금

    public int insertNewTypeEvntPaymngDtl(Map<String, ?> pmParam) throws Exception; // 입금마감 - 결합금

    public int insertNewTypeEvntPaymngDtl1(Map<String, ?> pmParam) throws Exception; // 입금마감 - 추가금

    public List<Map<String, Object>> getNewTypeEvntPicSort(Map<String, Object> pmParam) throws Exception; // 의전행사 사진 분류 - 행사회원 조회

    public List<Map<String, Object>> getNewTypeEvntPicList(Map<String, Object> pmParam) throws Exception; // 의전행사 사진 분류 - 분류/미분류회원 조회

    public int disconncectNewTypeAccnt(XPlatformMapDTO xpDto) throws Exception; // 선택파일 미분류로 이동

    public int conncectNewTypeAccnt(XPlatformMapDTO xpDto) throws Exception; // 선택파일 회원연결

    public int deleteNewTypeEvtPic(XPlatformMapDTO xpDto) throws Exception; // 행사사진 삭제

    public List<Map<String, Object>> evtNewTypePicBatchDownload(XPlatformMapDTO xpDto) throws Exception; // 행사사진 다운로드 파일 압축

    public int insertNewTypeEvtPicInfo(XPlatformMapDTO xpDto) throws Exception; // 의전행사 사진 등록
    
    public void saveNewTypeEvenPhotoUpLoad(HttpServletRequest request,HttpServletResponse response) throws Exception; 

    public List<Map<String, Object>> getNewTypeEvntBrchAnal1(Map<String, ?> pmParam) throws Exception; // 행사분석(상품분류명/지부별) - 헤더

    public String getNewTypebranchQury(Map<String, ?> pmParam) throws Exception; // 상품분류명/지부별 쿼리 불러오기

    public List<Map<String, Object>> getNewTypeEvntBrchAnal2(Map<String, ?> pmParam) throws Exception; // 행사분석(상품분류명/지부별) - 리스트

    public List<Map<String, Object>> getNewTypeEvntEmplAnal1(Map<String, ?> pmParam) throws Exception; // 행사분석(CP별/상품분류별) - 헤더

    public String getNewTypeEmplQury(Map<String, ?> pmParam) throws Exception; // 행사분석(CP별/상품분류별) 쿼리 불러오기

    public List<Map<String, Object>> getNewTypeEvntEmplAnal2(Map<String, ?> pmParam) throws Exception; // 행사분석(CP별/상품분류별) - 리스트

    public List<Map<String, Object>> getNewTypeEvntBrchCncl1(Map<String, ?> pmParam) throws Exception; // 행사취소분석(상품분류명/지부별) - 헤더

    public String getNewTypebranchQuryCncl(Map<String, ?> pmParam) throws Exception; // 행사취소분석 상품분류명/지부별 쿼리 불러오기

    public List<Map<String, Object>> getNewTypeEvntBrchCncl2(Map<String, ?> pmParam) throws Exception; // 행사취소분석(상품분류명/지부별) - 리스트

    public List<Map<String, Object>> getNewTypeEvntEmplCncl1(Map<String, ?> pmParam) throws Exception; // 행사취소분석(CP별/상품분류별) - 헤더

    public String getNewTypeEmplQuryCncl(Map<String, ?> pmParam) throws Exception; // 행사취소분석(CP별/상품분류별) 쿼리 불러오기

    public List<Map<String, Object>> getNewTypeEvntEmplCncl2(Map<String, ?> pmParam) throws Exception; // 행사취소분석(CP별/상품분류별) - 리스트

    public int getNewTypeMngrIpCount(Map<String, ?> pmParam) throws Exception; // 외부 일용직 입금대장 조회 건수

    public List<Map<String, Object>> getNewTypeMngrIpList(Map<String, ?> pmParam) throws Exception; // 외부 일용직 입금대장 조회 리스트

    public int getNewTypeStockClCount(Map<String, ?> pmParam) throws Exception; // 장례용품 재고현황 조회 건수

    public List<Map<String, Object>> getNewTypeStockClList(Map<String, ?> pmParam) throws Exception; // 장례용품 재고현황 조회 리스트

    public List<Map<String, Object>> getNewTypeAddSalesInList(Map<String, ?> pmParam) throws Exception; // 추가매출 - 직영 조회 리스트

    public List<Map<String, Object>> getNewTypeAddSalesOutList(Map<String, ?> pmParam) throws Exception; // 추가매출 - 외주 조회 리스트

    public int getNewTypeOrderAnalCount(Map<String, ?> pmParam) throws Exception; // 일자별 발주현황 조회 건수

    public List<Map<String, Object>> getNewTypeOrderAnalList(Map<String, ?> pmParam) throws Exception; // 일자별 발주현황 조회 리스트

    public List<Map<String, Object>> getNewTypeEvntRemainAmtList(Map<String, ?> pmParam) throws Exception; // 행사잔금처리현황 조회 리스트

    public int RemainNewTypeBigoUpdate(Map<String, ?> pmParam) throws Exception; // 잔금처리현황 비고 수정

    public int getNewTypeStockDayCount(Map<String, ?> pmParam) throws Exception; // 일자별 입출고 내역 조회 건수

    public List<Map<String, Object>> getNewTypeStockDayList(Map<String, ?> pmParam) throws Exception; // 일자별 입출고 내역 조회 리스트

    public List<Map<String, Object>> getNewTypesmsPayMent(Map<String, ?> pmParam) throws Exception; // SMS 전송 시 결제정보 조회(현금영수증 정보)

    public List<Map<String, Object>> getNewTypeSrvProdList1(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스 제공현황 - 상복

    public List<Map<String, Object>> getNewTypeSrvProdList2(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스 제공현황 - 제단

    public List<Map<String, Object>> getNewTypeSrvProdList3(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스 제공현황 - 장의차량

    public List<Map<String, Object>> getNewTypeSrvProdList4(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스 제공현황 - 앰블런스

    public List<Map<String, Object>> getNewTypeSrvProdList5(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스 제공현황 - 유골함

    public List<Map<String, Object>> getNewTypeSrvProdList_Head(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스 제공현황 - 직영행사물품 헤더

    public List<Map<String, Object>> getNewTypeSrvProdList_func(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스 제공현황 - 직영행사물품 펑션 실행

    public List<Map<String, Object>> getNewTypeSrvProdList_func2(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스 제공현황 - 장례식장(금액)

    public String getNewTypeSrvProdList_qry(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스 제공현황 - 직영행사물품 쿼리 가져오기

    public List<Map<String, Object>> getNewTypeSrvProdListIn(Map<String, ?> pmParam) throws Exception; // 물품 및 서비스 제공현황 - 직영행사물품 조회

    public List<Map<String, Object>> getNewTypeSrvProdAnal1(Map<String, ?> pmParam) throws Exception; // 물품 제공현황 분석- 상복

    public List<Map<String, Object>> getNewTypeSrvProdAnal2(Map<String, ?> pmParam) throws Exception; // 물품 제공현황 분석 - 제단

    public List<Map<String, Object>> getNewTypeSrvProdAnal3(Map<String, ?> pmParam) throws Exception; // 물품 제공현황 분석 - 장의차량

    public List<Map<String, Object>> getNewTypeSrvProdAnal4(Map<String, ?> pmParam) throws Exception; // 물품 제공현황 분석 - 장례식장(CP)

    public List<Map<String, Object>> getNewTypeSrvProdAnal5(Map<String, ?> pmParam) throws Exception; // 물품 제공현황 분석 - 월별분석

    public List<Map<String, Object>> getNewTypeMonthBrList(Map<String, ?> pmParam) throws Exception; // 월별 지부별 장의용품 재고현황

    public List<Map<String, Object>> getNewTypeMonthWhList(Map<String, ?> pmParam) throws Exception; // 월별 창고별 장의용품 재고현황

    public List<Map<String, Object>> getNewTypeGdsIpList_Head(Map<String, ?> pmParam) throws Exception; // 일자별 지부별 물품 입고 조회 - 헤더

    public List<Map<String, Object>> getNewTypeGdsIpList_func(Map<String, ?> pmParam) throws Exception; //  일자별 지부별 물품 입고 조회 - 펑션 실행

    public String getNewTypeGdsIpList_qry(Map<String, ?> pmParam) throws Exception; //  일자별 지부별 물품 입고 조회 - 쿼리 가져오기

    public List<Map<String, Object>> getNewTypeGdsIpList(Map<String, ?> pmParam) throws Exception; //  일자별 지부별 물품 입고 조회 - 조회

    public List<Map<String, Object>> getNewTypeGdsUseList_Head(Map<String, ?> pmParam) throws Exception; // 일자별 지부별 물품 사용내역 조회 - 헤더

    public List<Map<String, Object>> getNewTypeGdsUseList_func(Map<String, ?> pmParam) throws Exception; //  일자별 지부별 물품 사용내역 조회 - 펑션 실행

    public String getNewTypeGdsUseList_qry(Map<String, ?> pmParam) throws Exception; //  일자별 지부별 물품 사용내역 조회 - 쿼리 가져오기

    public List<Map<String, Object>> getNewTypeGdsUseList(Map<String, ?> pmParam) throws Exception; //  일자별 지부별 물품 사용내역 조회 - 조회

    public List<Map<String, Object>> getNewTypeCostList(Map<String, ?> pmParam) throws Exception; //  CP별/일반경비별 현황 조회

    public List<Map<String, Object>> getNewTypeCostMemList(Map<String, ?> pmParam) throws Exception; //  회원별 일반경비별 현황 조회

    public List<Map<String, Object>> getNewTypeBenefitList1(Map<String, ?> pmParam) throws Exception; //  손익분석집계표 - 행사별

    public List<Map<String, Object>> getNewTypeBenefitList2(Map<String, ?> pmParam) throws Exception; //  손익분석집계표 - 월별

    public List<Map<String, Object>> getNewTypeBenefitList3(Map<String, ?> pmParam) throws Exception; //  손익분석집계표 - 지부별

    public List<Map<String, Object>> getNewTypeBenefitList4(Map<String, ?> pmParam) throws Exception; //  손익분석집계표 - CP별

    public List<Map<String, Object>> getNewTypeBenefitList5(Map<String, ?> pmParam) throws Exception; //  손익분석집계표 - 외주업체별

    public List<Map<String, Object>> getNewTypeStockGoodsList(Map<String, ?> pmParam) throws Exception; //  재고마감 - 품목별 마감 조회

    public List<Map<String, Object>> getNewTypeStockWhList(Map<String, ?> pmParam) throws Exception; //  재고마감 - 창고별 마감 조회

    public int deleteNewTypeStockMg(Map<String, ?> pmParam) throws Exception; // 마감취소 - 재고관리 데이터 삭제

    public int insertNewTypeStockMg(Map<String, ?> pmParam) throws Exception; // 마감저장 - 재고관리 프로시저 호출

    public String getNewTypeOrdNo(Map<String, ?> pmParam) throws Exception;     // 발주번호 가져오기

    public String getNewTypeSinOrdNo(Map<String, ?> pmParam) throws Exception;     // 발주번호 신규생성

    public int deleteNewTypeOrdMst(Map<String, ?> pmParam) throws Exception; // 발주 마스터 삭제비트 변경

    public int deleteNewTypeOrdDtl(Map<String, ?> pmParam) throws Exception; // 발주 디테일 데이터 삭제

    public int insertNewTypeOrdMst(Map<String, ?> pmParam) throws Exception; // 발주 마스터 저장

    public int insertNewTypeOrdDtl(Map<String, ?> pmParam) throws Exception; // 발주 디테일 저장

    public int updateNewTypeRepGoods(Map<String, ?> pmParam) throws Exception; // 대체용품 수정

    public int insertNewTypeRepGoods(Map<String, ?> pmParam) throws Exception; // 대체용품 입력

    public int deleteNewTypeRepGoods(Map<String, ?> pmParam) throws Exception; // 대체용품 삭제

    public List<Map<String, Object>> getNewTypeEvtRepList(Map<String, ?> pmParam) throws Exception; //  대체용품 회원 조회

    public int getNewTypeRepGoodsCount(Map<String, ?> pmParam) throws Exception; // 대체용품현황 조회 건수

    public List<Map<String, Object>> getNewTypeRepGoodsList(Map<String, ?> pmParam) throws Exception; // 대체용품현황 조회 리스트

    public List<Map<String, Object>> getNewTypeEvtUjiList(Map<String, ?> pmParam) throws Exception; // 행사유지현황 리스트

    public List<Map<String, Object>> getNewTypeEvntChatInfo(Map<String, ?> pmParam) throws Exception; // 행사문자 전송시 회원정보 조회

    /*************************************************************************
     *  2017.07.27 김준호
     *  관리업무>행사조회>모니터링>모니터링 결과 보고서
     *  중복 입력시 오류건으로 인해 저장전 회원번호로 EVENT TABLE확인후 저장
     ***********************************************************************/
    public int countNewTypeEvent(Map<String, Object> hmParam) throws Exception;// 행사 등록시 해당 회원번호로 행사테이블 조회

    public List<Map<String, Object>> getNewTypeEvntcjChatInfo(Map<String, ?> pmParam) throws Exception; //문자 전송시 회원정보 조회

    /*************************************************************************
     *  2017.09.26 김준호
     *  알림톡 구동시 가상계좌 검색
     ***********************************************************************/
    public List<Map<String, Object>> getNewTypeEventcjChatVrtlInfo(Map<String, Object> hmParam);

    /* ================================================================
     * 날짜 : 20171226
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰 연동 Table에 해당 고객의 상태값을 행사로 바꿈
     * ================================================================
     * */
    public int updateNewTypeEventMemberState(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20181114
     * 이름 : 송준빈
     * 추가내용 : 청구가 존재하는 회원인지 확인
     * ================================================================
     * */
	public List<Map<String, Object>> getNewTypeWdrwReqConfirm(Map pmParam);
}