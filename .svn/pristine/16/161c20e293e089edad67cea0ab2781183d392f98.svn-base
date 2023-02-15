package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

public interface DlwCustService {

    public List<Map<String, Object>> getDlwCustPrdtList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getDlwCustPrdtDtpt(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getDlwCustPrdtDtpt(String psParam) throws Exception;

    public int getDlwCustAcntCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwCustAcntList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwOnlineMemberCmsInfo(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwOnlineSSMemberCmsInfo(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwOnlineMemberCardInfo(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwOnlineSSMemberCardInfo(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getBugaSrvcMemChk(Map<String, ?> pmParam) throws Exception;

    public String getBugaSrvcAppCl(Map<String, ?> pmParam) throws Exception; // 부가서비스(CMS/CARD) 신청구분 체크 추가 - 2018.06.19

    public int updateMemberOCB(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getCustBasiConsInfo(Map<String, ?> pmParam) throws Exception;

    public int insertYdysMemTrans(Map<String, ?> pmParam) throws Exception;

    public int updateYdysMemProd(Map<String, ?> pmParam) throws Exception;

    public int updateYdysClsl(Map<String, ?> pmParam) throws Exception;

    public int updateYdysChng(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getAnalResultList(Map<String, ?> pmParam) throws Exception;

    /* ================================================================
     * 날짜 : 20180403
     * 이름 : 김찬영
     * 추가내용 : 쇼핑몰 연동 Table 에 고객의 상태를 양도로 변경
     * ================================================================
     * */
    public int updateResnMemberState(Map<String, ?> pmParam) throws Exception;

}