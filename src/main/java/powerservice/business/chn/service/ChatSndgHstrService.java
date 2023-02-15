package powerservice.business.chn.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;

public interface ChatSndgHstrService {

    public int getChatSndgHstrCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getChatSndgHstrList(Map<String, ?> param) throws Exception;

    public List<Map<String, Object>> getChatSndgHstr(String psId) throws Exception;

    public String insertChatSndgHstr(Map<String, Object> pmParam) throws Exception;

    public int mergeChatSndgHstrExcelList(DataSetMap pmInDsList, Map<String, Object> pmParam) throws Exception;

    public String insertSend(Map<String, Object> pmParam) throws Exception;
    
    public String insertSysoneSmsSend(Map<String, Object> pmParam) throws Exception;

    public String insertSendDetail(Map<String, Object> pmParam) throws Exception;
    
    public String insertSysoneSendDetail(Map<String, Object> pmParam) throws Exception;

    public void updateMsgStatus(Map<String, Object> pmParam) throws Exception;

    public void updateChatSndgHstrResl() throws Exception;

    public List<Map<String, Object>> getInfobankSmsRejectList(Map <String, DataSetMap> mapInDs) throws Exception;

    //cj올리브네트웍스 문자전송
    public String insertnewSend(Map<String, Object> pmParam) throws Exception;

    public String insertChatnewSndgHstr(Map<String, Object> pmParam) throws Exception;

    // CJ 일괄 문자 발송
    public void insertnewallsend(DataSetMap listInDs,DataSetMap listInDs2,String sServiceType) throws Exception;

    /* ================================================================
     * 날짜 : 20180906
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상 개수
     * ================================================================
     * */
    public int getSmsVrtlSendListTotalCnt(Map<String, ?> pmParam) throws Exception;

    /* ================================================================
     * 날짜 : 20180906
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상 리스트
     * ================================================================
     * */
    public List<Map<String, Object>> getSmsVrtlSendList(Map<String, ?> pmParam) throws Exception;

    /* ================================================================
     * 날짜 : 20180907
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자 가전송
     * ================================================================
     * */
    public int insertVrtlMsSend(Map<String, ?> pmParam);

    /* ================================================================
     * 날짜 : 20180910
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상 개수
     * ================================================================
     * */
    public int getSmsRealSendListTotalCnt(Map<String, ?> pmParam) throws Exception;

    /* ================================================================
     * 날짜 : 20180910
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상 리스트
     * ================================================================
     * */
    public List<Map<String, Object>> getSmsRealSendList(Map<String, ?> pmParam) throws Exception;

    /* ================================================================
     * 날짜 : 20180910
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자 진전송(SMS)
     * ================================================================
     * */
    public int insertRealMsSmsSend(Map<String, ?> pmParam);

    /* ================================================================
     * 날짜 : 20180910
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자 진전송(LMS)
     * ================================================================
     * */
    public int insertRealMsLmsSend(Map<String, ?> pmParam);
    
    /* ================================================================
     * 날짜 : 20190312
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자 진전송(카카오)
     * ================================================================
     * */
    public int insertRealMsKaKaoSend(Map<String, ?> pmParam);

    /* ================================================================
     * 날짜 : 20180910
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자 진전송 히스토리
     * ================================================================
     * */
    public int insertChatMultiSndgHstr(Map<String, ?> pmParam);

    /* ================================================================
     * 날짜 : 20180910
     * 이름 : 송준빈
     * 추가내용 : 문자 일괄발송 대상자 가접수테이블 업데이트
     * ================================================================
     * */
    public int updateChatMultiSndg(Map<String, Object> pmParam) throws Exception;

    /* ================================================================
     * 날짜 : 20180921
     * 이름 : 송준빈
     * 추가내용 : SMS일괄 엑셀 업로드 건별
     * ================================================================
     * */
    public void uploadToSms(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception;
    
    /** ================================================================
     * 날짜 : 20190307
     * 이름 : 송준빈
     * 추가내용 : 배치문자전송일등록 조회
     * 대상 테이블 : LF_DMUSER.TB_MSG_EXT_SCHED
     * ================================================================
     * */
    public List<Map<String, Object>> getMsgExtSched(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20190307
     * 이름 : 송준빈
     * 추가내용 : 배치문자전송일등록 등록유효성체크
     * 대상 테이블 : LF_DMUSER.TB_MSG_EXT_SCHED
     * ================================================================
     * */
    public int chkMsgExtSched(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20190307
     * 이름 : 송준빈
     * 추가내용 : 배치문자전송일등록 등록
     * 대상 테이블 : LF_DMUSER.TB_MSG_EXT_SCHED
     * ================================================================
     * */
    public void insertMsgExtSched(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20190307
     * 이름 : 송준빈
     * 추가내용 : 배치문자전송일등록 삭제
     * 대상 테이블 : LF_DMUSER.TB_MSG_EXT_SCHED
     * ================================================================
     * */
    public void deleteMsgExtSched(Map<String, ?> pmParam) throws Exception;
}