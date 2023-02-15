package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface DlwBasiDataMngService {


    public  List<Map<String, Object>> get_funrl_code(Map<String, ?> pmParam)  throws Exception;  //장례식장 code

    public  List<Map<String, Object>> get_branch_code(Map<String, ?> pmParam)  throws Exception;  //지부코드 code

    public int saveFunrl(XPlatformMapDTO xpDto, Map<String, Object> mOut)  throws Exception;  //거래처 등록/수정/삭제

    public  int branch_insert(Map<String, ?> pmParam)  throws Exception;  //지부 insert저장

    public List<Map<String, Object>> getbranchList(Map<String, ?> pmParam) throws Exception; //지부 관리 리스트

    public List<Map<String, Object>> funrl_select (Map<String, ?> pmParam) throws Exception; //장례식장 조회

    public List<Map<String, Object>> branch_select (Map<String, ?> pmParam) throws Exception; //지부장조회1 조회

    public List<Map<String, Object>> getjangList(Map<String, ?> pmParam) throws Exception; //장례식장 관리 리스트

    public int branch_update(Map<String, ?> pmParam)  throws Exception;  //지부 update

    public int branch_delete(Map<String, ?> pmParam)  throws Exception;  //지부 delete

    public List<Map<String, Object>> getCustmrMngList(Map<String, ?> pmParam) throws Exception; //거래처 관리 리스트

    public  List<Map<String, Object>> getCustmrCd(Map<String, ?> pmParam)  throws Exception;  //거래처 code

    public int saveCustmr(XPlatformMapDTO xpDto, Map<String, Object> mOut)  throws Exception;  //거래처 등록/수정/삭제

     public List<Map<String, Object>> getWHMngList(Map<String, ?> pmParam) throws Exception; //창고 관리 리스트

     public int saveWarehouse(XPlatformMapDTO xpDto, Map<String, Object> mOut)  throws Exception;  //창고 등록/수정/삭제

     public  List<Map<String, Object>> getWHCd(Map<String, ?> pmParam)  throws Exception;  //창고 code

     public List<Map<String, Object>> getEvtManaMngList(Map<String, ?> pmParam) throws Exception; //행사자 관리 리스트

     public  List<Map<String, Object>> getEvtManaMngCd(Map<String, ?> pmParam)  throws Exception;  //행사자 code

     public String  insrtEvtManaMng(Map<String, Object> pmParam) throws Exception; //행사자 등록

     public int deleteEvtManaMng(Map<String, ?> pmParam)  throws Exception;  //행사자 삭제

     public int updateEvtManaMng(Map<String, Object> pmParam)  throws Exception;  //행사자 수정

     public int chkEvtManaMngCnt(Map<String, ?> pmParam) throws Exception; // 지부장 등록건수 조회



     public List<Map<String, Object>> selectEventGoodsList(Map<String, ?> pmParam) throws Exception;  //물품 관리 리스트

     public List<Map<String, Object>> selectBuyingList(Map<String, Object> pmParam) throws Exception; // 행사물품관리 상세 조회

     public int insertEvntGoodsMst(Map<String, ?> pmParam) throws Exception; // 행사물품 마스터 입력

     public int updateEvntGoodsMst(Map<String, ?> pmParam) throws Exception; // 행사물품 마스터 저장

     public int updateEvntGoodsDtl(Map<String, ?> pmParam) throws Exception; // 행사물품 상세 정보 수정

     public int insertEvntGoodsDtl(Map<String, ?> pmParam) throws Exception; // 행사물품 상제 정보 등록

     public int saveEvtMngrAlow(XPlatformMapDTO xpDto, Map<String, Object> mOut)  throws Exception; //행사자 수당 기초 등록/수정/삭제

     public List<Map<String, Object>> getEvtMngrList(Map<String, Object> pmParam) throws Exception; // 행사자 수당 기초 리스트
}
