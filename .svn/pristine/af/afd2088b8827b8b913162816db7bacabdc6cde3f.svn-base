package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface DlwDeductionService {

    /** ================================================================
     * 날짜 : 20190124
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송 기준데이터 조회수 (신규/타사 페이지)
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public int getGongjeDayExtCount(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190124
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송 기준데이터 리스트 (신규/타사 페이지)
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeDayExtList(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190124
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송 기준데이터 산출
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    //public int insertGongjeDayExt(Map<String, ?> pmParam) throws Exception;
    public int insertGongjeDayExt() throws Exception;

    /** ================================================================
     * 날짜 : 20190130
     * 이름 : 정승철
     * 추가내용 : 공제 일반데이터 엑셀생성
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public void createExcel() throws Exception;

    /** ================================================================
     * 날짜 : 20190130
     * 이름 : 정승철
     * 추가내용 : 공제 선수금데이터 엑셀생성
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    //public void createPayExcel() throws Exception;

    /** ================================================================
     * 날짜 : 20190130
     * 이름 : 정승철
     * 추가내용 : 공제 산출 스케줄 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_EXT_SCHED
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeExtDt(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190207
     * 이름 : 정승철
     * 추가내용 : 공제 전송여부 카운트조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public int getGongjeSendCnt(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190208
     * 이름 : 정승철
     * 추가내용 : 공제산출스케줄 입력유효성체크
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_EXT_SCHED
     * ================================================================
     * */
    public int chkGongjeExtSched(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190408
     * 이름 : 정승철
     * 추가내용 : 공제산출스케줄 입력유효성체크 (** 공제결과 미처리 조회)
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_EXT_SCHED
     * ================================================================
     * */
    public int chkGongjeExtSched_rsProcYn() throws Exception;

    /** ================================================================
     * 날짜 : 20190208
     * 이름 : 정승철
     * 추가내용 : 공제산출스케줄 저장
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_EXT_SCHED
     * ================================================================
     * */
    public void saveGongjeExtSched(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190208
     * 이름 : 정승철
     * 추가내용 : 공제산출스케줄 삭제
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_EXT_SCHED
     * ================================================================
     * */
    public void delGongjeExtSched(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190208
     * 이름 : 정승철
     * 추가내용 : 진행중인 공제데이터 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeSendingList(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190208
     * 이름 : 정승철
     * 추가내용 : 공제구분별 최종결과반영 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeTypeLastResultList(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190207
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 결과 반영 (DMLIFE_PAY 파일)
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT
     * ================================================================
     * @throws IOException
     * */
    public void insertGongjeDmlifePayFileUpload(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResult) throws Exception;

    /** ================================================================
     * 날짜 : 20190207
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 결과 반영 (DMLIFE 파일)
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT
     * ================================================================
     * @throws IOException
     * */
    public void insertGongjeDmlifeFileUpload(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResult) throws Exception;

    /** ================================================================
     * 날짜 : 20190213
     * 이름 : 임동진
     * 추가내용 : 공제 결과 업데이트
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT
     * ================================================================
     * */
    public int uptGongResultStat(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190214
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송이력 조회수
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT, LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT
     * ================================================================
     * */
    public int getGongjeDayExtSendHistCount(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190214
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송이력 리스트
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT, LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeDayExtSendHist(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190219
     * 이름 : 송준빈
     * 추가내용 : 공제 신고 현황 데이터 (ASIS) 조회수
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    public int getGongjeAsisDataCount(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190219
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송이력 (ASIS) 리스트
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeAsisDataList(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190313
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송이력 (ASIS) 리스트 정상고객 총금액
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeAsisDataListSummary(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190219
     * 이름 : 송준빈
     * 추가내용 :  공제 데이터 전송이력 (ASIS) 추가
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    public int insertGongjeAsisDataList(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190219
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송이력 (ASIS) 수정
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    public int updateGongjeAsisDataList(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190219
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송이력 (ASIS) 삭제
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    public int deleteGongjeAsisDataList(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190220
     * 이름 : 정승철
     * 추가내용 : 공제 기존 누적 선수금 조회
     * 대상 테이블 : LF_DMUSER.GONGJE_MG
     * ================================================================
     * */
    public String getGongjeBefTotalAmt(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190220
     * 이름 : 정승철
     * 추가내용 : 공제 월별 보고서 선수금 및 구분별 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeMonthReport(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190220
     * 이름 : 정승철
     * 추가내용 : 공제 월별 보고서 구분별 카운트조회
     * ================================================================
     * */
    public List<Map<String, Object>> getCntGongjeMonthReport(Map<String, ?> pmParam) throws Exception;


    /** ================================================================
     * 날짜 : 20190311
     * 이름 : 정승철
     * 추가내용 : 공제 월별 보고서 마감처리
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_CLOSE
     * ================================================================
     * */
    public int insertGongjeClose(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190312
     * 이름 : 정승철
     * 추가내용 : 공제 마감처리할 정산년월 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_CLOSE
     * ================================================================
     * */
    public String getNextGongjeCloseYYYYMM() throws Exception;

    /** ================================================================
     * 날짜 : 20190319
     * 이름 : 정승철
     * 추가내용 : 공제데이터산출 및 엑셀생성
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public void ProcessGongjeSche() throws Exception;

    /** ================================================================
     * 날짜 : 20190321
     * 이름 : 정승철
     * 추가내용 : 정상적인 공제 ASIS-DATA 총 금액 및 건수 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    public List<Map<String, Object>> getSumGongjeAsisData() throws Exception;

    /** ================================================================
     * 날짜 : 20190403
     * 이름 : 송준빈
     * 추가내용 : 공제 마감 처리후 ASIS의 정보를 ORIGIN으로 이관
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ORIGIN
     * ================================================================
     * */
    public int insertGongjeAsisToOrigin(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190410
     * 이름 : 정승철
     * 추가내용 : 공제결과 삭제
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT
     * ================================================================
     * */
    public void deleteGongjeResult() throws Exception;
    
    /** ================================================================
     * 날짜 : 20190621
     * 이름 : 송준빈
     * 추가내용 : 공제데이터산출 및 엑셀생성 (배치 수정판 :: Test시 버튼 누르는것과 같음)
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public void getGongjeExtBatchProcess() throws Exception;

}