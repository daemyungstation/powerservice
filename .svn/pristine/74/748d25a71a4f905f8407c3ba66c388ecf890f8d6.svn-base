package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface AlowService {

    public int totComptAlowGrp(XPlatformMapDTO xpDto) throws Exception;

    public List<Map<String, Object>> selectAtdnList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectTotComptGrpList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectPerComptGrpList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectPayAgencyList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectPayAgencyByExcelList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectTotComptAtdnList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectPerComptAtdnList(Map<String, Object> pmParam) throws Exception;

    public int perComptAlow(XPlatformMapDTO xpDto) throws Exception;

    public int totComptAlow(XPlatformMapDTO xpDto) throws Exception;

    public int comptAlowBasisEnrg(XPlatformMapDTO xpDto) throws Exception;

    public List<Map<String, Object>> selectPayRollList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectPayRollListGrp(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectPayRollListProxy(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectPayAlowGrpJoinList(Map<String, Object> pmParam) throws Exception;

    public String isAlowClose(Map<String, Object> pmParam) throws Exception;

    public int alowClose(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectB2BSaleTypeList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectAlowListForF12(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectAlowListForExtr(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectAlowListForAccntSum(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectAlowListForAccnt(Map<String, Object> pmParam) throws Exception;

    public int callUPAlowAmt(XPlatformMapDTO xpDto) throws Exception;

    public int deleteAlowAmt(XPlatformMapDTO xpDto) throws Exception;

    public List<Map<String, Object>> selectAlowMngForAccnt(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectPayRollAgencyList(Map<String, Object> pmParam) throws Exception;

    public int insertAlowDaDtl(XPlatformMapDTO xpDto) throws Exception;

    public List<Map<String, Object>> selectTotStatus(Map<String, Object> pmParam) throws Exception;
    
    public List<Map<String, Object>> selectTotStatusAudit(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectAlowCdList(Map<String, Object> pmParam) throws Exception;

    public int mergeAlowCd(XPlatformMapDTO xpDto) throws Exception;

    public List<Map<String, Object>> selectAlowCdDataList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectAlowListForEmpleNo(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getB2bCompFeeList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectAccntAlowList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getHcodeList(Map<String, Object> pmParam) throws Exception;

    public int saveAlowBaseData(XPlatformMapDTO xpDto) throws Exception;

    public int alowCalcAgency(XPlatformMapDTO xpDto) throws Exception;

    public int deleteAlowAgency(XPlatformMapDTO xpDto) throws Exception;

    public List<Map<String, Object>> selectAlowMngForMemInfo(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getT33B2bCompList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectB2BSaleTypeDtl(Map<String, Object> pmParam) throws Exception;

    public int mergeB2BSaleTypeMng(XPlatformMapDTO xpDto, Map<String, Object> mResult) throws Exception;

    public List<Map<String, Object>> selectMonthAlowList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectPayRollAgencySum(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectPayAgencySum(Map<String, Object> pmParam) throws Exception;

    public int saveAlowSetSub(XPlatformMapDTO xpDto, Map<String, Object> mResult) throws Exception;

    public List<Map<String, Object>> selectAlowMagam(Map<String, Object> pmParam) throws Exception; // 수당&수수료 마감여부 조회

    public int saveAlowMagam(Map<String, Object> pmParam) throws Exception; // 수당&수수료 마감처리

    public int saveIpendMagam(Map<String, Object> pmParam) throws Exception; // 입금마감처리

    public List<Map<String, Object>> selectpopAlowdtllist(Map<String, Object> pmParam) throws Exception; // 회원별 수당조회 - 수수료/해약&연체환수 팝업 상세 조회

    public int updatepopAlowDtl(Map<String, Object> pmParam) throws Exception; // 회원별 수당조회 - 수수료/해약&연체환수 팝업 상세 수정

    public int insertpopAlowDtl(Map<String, Object> pmParam) throws Exception; // 회원별 수당조회 - 수수료/해약&연체환수 팝업 상세 신규

    public int deletepopAlowDtl(Map<String, Object> pmParam) throws Exception; // 회원별 수당조회 - 수수료/해약&연체환수 팝업 상세 삭제

    public int alowdaDtlMerge(Map<String, Object> pmParam) throws Exception; // 회원별 수당조회 - 팝업 수정에 따른 수당상세내역 신규 및 수정
}