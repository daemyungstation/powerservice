package powerservice.business.dlw.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import powerservice.common.util.CommonUtils;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 수당 처리 관련 DAO
 *
 * @author 정출연
 * @version 1.0
 * @date 2016/12/13
 * @프로그램ID
 */
@Repository
public class AlowDAO extends EgovAbstractMapper {

    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 근태 마스터 삭제
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteAtdnMst(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.deleteAtdnMst", pmParam);
    }

    /**
     * 근태 마스터 등록
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertAtdnMstGrp(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.insertAtdnMstGrp", pmParam);
    }

    /**
     * 급여생성
     * - 수당산출(전사판매)-전체
     * @param pmParam
     * @throws Exception
     */
    public int comptAlowGrp(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.comptAlowGrp", pmParam);
    }

    /**
     * 급여 Master 확정여부 확인
     *
     * @param pmParam 검색조건
     * @throws Exception
     */
    public String isAlowClose(Map<String, Object> pmParam) throws Exception {
        return (String)selectOne("AlowMap.isAlowClose", pmParam);
    }

    /**
     * 근태리스트조회_신규생성자료 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAtdnNewList(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectAtdnNewList", pmParam);
    }

    /**
     * 근태리스트조회_기등록자료 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAtdnOldList(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectAtdnOldList", pmParam);
    }

    /**
     * 전사판매 급여산출_조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectTotComptGrpList(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectTotComptGrpList", pmParam);
    }

    /**
     * 수당산출(전사판매)-개별(전사) 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectPerComptGrpList(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectPerComptGrpList", pmParam);
    }

    /**
     * B2B업체별 수수료 현황
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectPayAgencyList(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectPayAgencyList", pmParam);
    }

    /**
     * B2B업체별 수수료 집계
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectPayAgencySum(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectPayAgencySum", pmParam);
    }


    /**
     * B2B수수료 업로드 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectPayAgencyByExcelList(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectPayAgencyByExcelList", pmParam);
    }

    /**
     * 수당 근거삭제:B2B 한정
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteALowDaDtl(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.deleteALowDaDtl", pmParam);
    }

    /**
     * B2B수수료 상세근거 삭제 TMP
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteAlowTmpDtlT33(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.deleteAlowTmpDtlT33", pmParam);
    }

    /**
     * B2B수수료 엑셀 업로드
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertTmpAlowDtlT33ByExcel(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.insertTmpAlowDtlT33ByExcel", pmParam);
    }

    /**
     * 수당근거 입력 : B2B수수료(T33)
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertAlowDtlForT33(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.insertAlowDtlForT33", pmParam);
    }

    /**
     * 급여산출_전체_근태내역조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectTotComptAtdnList(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectTotComptAtdnList", pmParam);
    }

    /**
     * 개별산출 대상 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectPerComptAtdnList(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectPerComptAtdnList", pmParam);
    }

    /**
     * 급여생성
     *  - 수당산출- 개별
     *  - 수당산출- 전체
     * @param pmParam
     * @throws Exception
     */
    public int comptAlow(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.comptAlow", pmParam);
    }

    /**
     * 해당월 제외한 신규내역 임시테이블 삭제
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteTempNewList(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.deleteTempNewList", pmParam);
    }

    /**
     * selectPayRollList
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectPayRollList(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectPayRollList", pmParam);
    }

    /**
     * selectPayRollListGrp
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectPayRollListGrp(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectPayRollListGrp", pmParam);
    }

    /**
     * selectPayRollListProxy
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectPayRollListProxy(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectPayRollListProxy", pmParam);
    }

    /**
     * selectPayAlowGrpJoinList
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectPayAlowGrpJoinList(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectPayAlowGrpJoinList", pmParam);
    }

    /**
     * 급여 Master 확정일자 update
     *
     * @param pmParam
     * @throws Exception
     */
    public int updatealowClose(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.updatealowCloseGrp", pmParam);
    }

    /**
     * 수당 근거자료 생성 - 사용하지 않기로함 - 2016-12-22
     *
     * @param pmParam
     * @throws Exception
     */
    public int makeBasedData(Map<String, Object> pmParam) throws Exception {

        String alow_dt = CommonUtils.nvls((String)pmParam.get("alow_dt"));
        String dept_kind = CommonUtils.nvls((String)pmParam.get("dept_kind"));

        pmParam.put("tab_mem_prod_accnt", "MEM_PROD_ACCNT" + alow_dt);
        pmParam.put("tab_event", "EVENT" + alow_dt);
        pmParam.put("tab_rescission", "RESCISSION" + alow_dt);
        pmParam.put("tab_alow_com_da", "ALOW_COM_DA" + alow_dt);
        pmParam.put("tab_taxtproc", "TAXT_PROC" + alow_dt);
        pmParam.put("tab_pay_mng", "PAY_MNG" + alow_dt);

        /* update("AlowMap.makeBasedData", pmParam); */

        update("AlowMap.makeBasedData_01", pmParam);
        update("AlowMap.makeBasedData_02", pmParam);
        update("AlowMap.makeBasedData_03", pmParam);
        update("AlowMap.makeBasedData_04", pmParam);
        update("AlowMap.makeBasedData_05", pmParam);
        update("AlowMap.makeBasedData_06", pmParam);

        Map<String, Object> pmParam2 = new HashMap<>();
        pmParam2.put("tab_nm"		, "MEM_PROD_ACCNT" + alow_dt);
        pmParam2.put("index_nm"		, "IX_MEM_PROD_ACCNT" + alow_dt);
        pmParam2.put("index_col"	, "ACCNT_NO ASC, DEL_FG ASC");
        update("AlowMap.createIndex", pmParam2);

        pmParam2 = new HashMap<>();
        pmParam2.put("tab_nm"		, "MEM_PROD_ACCNT" + alow_dt);
        pmParam2.put("index_nm"		, "MEM_PROD_ACCNT_DELFG_IDX" + alow_dt);
        pmParam2.put("index_col"	, "DEL_FG ASC");
        update("AlowMap.createIndex", pmParam2);

        pmParam2 = new HashMap<>();
        pmParam2.put("tab_nm"		, "PAY_MNG" + alow_dt);
        pmParam2.put("index_nm"		, "IX_PAY_MNG_ACCNT_NO" + alow_dt);
        pmParam2.put("index_col"	, "ACCNT_NO ASC, DEL_FG ASC");
        update("AlowMap.createIndex", pmParam2);

        pmParam2 = new HashMap<>();
        pmParam2.put("tab_nm"		, "PAY_MNG" + alow_dt);
        pmParam2.put("index_nm"		, "IX_PAY_MNG_DATE" + alow_dt);
        pmParam2.put("index_col"	, "PAY_DAY ASC, DEL_FG ASC");
        update("AlowMap.createIndex", pmParam2);

        pmParam2 = new HashMap<>();
        pmParam2.put("tab_nm"		, "PAY_MNG" + alow_dt);
        pmParam2.put("index_nm"		, "IX_PAY_MNG_NO" + alow_dt);
        pmParam2.put("index_col"	, "ACCNT_NO ASC, DEL_FG ASC, NO ASC");
        update("AlowMap.createIndex", pmParam2);

        pmParam2 = new HashMap<>();
        pmParam2.put("tab_nm"		, "PAY_MNG" + alow_dt);
        pmParam2.put("index_nm"		, "IX_PAY_MNG_DEL_FG" + alow_dt);
        pmParam2.put("index_col"	, "DEL_FG ASC");
        update("AlowMap.createIndex", pmParam2);

        pmParam2 = new HashMap<>();
        pmParam2.put("tab_nm"		, "PAY_MNG" + alow_dt);
        pmParam2.put("index_nm"		, "IX_PAY_MNG_PAY_DAY" + alow_dt);
        pmParam2.put("index_col"	, "PAY_DAY ASC");
        update("AlowMap.createIndex", pmParam2);

        return 1;
    }

    /**
     * B2B영업형태 분류 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectB2BSaleTypeList(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectB2BSaleTypeList", pmParam);
    }

    /**
     * selectAlowListForAccnt
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAlowListForAccnt(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectAlowListForAccnt", pmParam);
    }

    /**
     * selectAlowListForAccntSum
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAlowListForAccntSum(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectAlowListForAccntSum", pmParam);
    }

    /**
     * selectAlowListForExtr
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAlowListForExtr(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectAlowListForExtr", pmParam);
    }

    /**
     * selectAlowListForF12
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAlowListForF12(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectAlowListForF12", pmParam);
    }

    /**
     * selectAloMstForAccnt
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public int selectAloMstForAccnt(Map<String, Object> pmParam) throws Exception {
        return selectOne("AlowMap.selectAloMstForAccnt", pmParam);
    }

    /**
     * 마감 (회원별 수당)
     *
     * @param pmParam
     * @throws Exception
     */
    public int callUPAlowAmt(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.callUPAlowAmt", pmParam);
    }

    /**
     * selectAlowAmtAccnt
     *
     * @param pmParam
     * @throws Exception
     */
    public int selectAlowAmtAccnt(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.selectAlowAmtAccnt", pmParam);
    }

    /**
     * 마감해지 (회원별 수당)
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteAlowAmt(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.deleteAlowAmt", pmParam);
    }

    /**
     * selectAlowDaDtlCheck
     *
     * @param pmParam
     * @throws Exception
     */
    public int selectAlowDaDtlCheck(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.selectAlowDaDtlCheck", pmParam);
    }

    /**
     * insertAlowDaDtl
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertAlowDaDtl(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.insertAlowDaDtl", pmParam);
    }

    /**
     * updateAlowDaDtl
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateAlowDaDtl(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.updateAlowDaDtl", pmParam);
    }

    /**
     * callUpAlowDtlF12
     *
     * @param pmParam
     * @throws Exception
     */
    public int callUpAlowDtlF12(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.callUpAlowDtlF12", pmParam);
    }

    /**
     * updateAlowDtlF12
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateAlowDtlF12(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.updateAlowDtlF12", pmParam);
    }

    /**
     * 회원별 수당 관리(회원 개별 상세조회)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectAlowMngForAccnt(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectAlowMngForAccnt", pmParam);
    }

    /**
     * B2B업체 수수료  상세내역
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectPayRollAgencyList(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectPayRollAgencyList", pmParam);
    }

    /**
     * 종합현황(재무팀 전용)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectTotStatus(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectTotStatus", pmParam);
    }
    
    /**
     * 종합현황(재무팀 전용)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectTotStatusAudit(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectTotStatusAudit", pmParam);
    }

    /**
     * 수당코드조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectAlowCdList(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectAlowCdList", pmParam);
    }

    /**
     * 수당코드등록
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertAlowCd(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.insertAlowCd", pmParam);
    }

    /**
     * 수당코드수정
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateAlowCd(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.updateAlowCd", pmParam);
    }

    /**
     * 수당/수수료 기초데이터 마스터(ALOW_SET_MST) 등록
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertAlowSetMst(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.insertAlowSetMst", pmParam);
    }

    /**
     * 수당/수수료 기초데이터 서브(ALOW_SET_SUB) 등록
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertAlowSetSub(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.insertAlowSetSub", pmParam);
    }

    /**
     * 수당/수수료 기초데이터 마스터(ALOW_SET_MST) 수정
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateAlowSetMst(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.updateAlowSetMst", pmParam);
    }

    /**
     * 수당/수수료 기초데이터 마스터(ALOW_SET_SUB) 수정
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateAlowSetSub(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.updateAlowSetSub", pmParam);
    }

    /**
     * 수당코드 기초Data조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAlowCdDataList(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectAlowCdDataList", pmParam);
    }

    /**
     * 수당기초데이터 마스터 삭제
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteAlowSetMst(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.deleteAlowSetMst", pmParam);
    }

    /**
     * 수당기초데이터 서브 삭제
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteAlowSetSub(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.deleteAlowSetSub", pmParam);
    }

    /**
     * 사원별 수당 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAlowListForEmpleNo(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectAlowListForEmpleNo", pmParam);
    }

    /**
     * 수수료 현황 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> getB2bCompFeeList(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.getB2bCompFeeList", pmParam);
    }

    /**
     * 회원별 수당 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAccntAlowList(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectAccntAlowList", pmParam);
    }

    /**
     * 환수목록조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> getHcodeList(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.getHcodeList", pmParam);
    }

    /**
     * B2B수수료계산
     *
     * @param pmParam
     * @throws Exception
     */
    public int alowCalcAgency(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.alowCalcAgency", pmParam);
    }

    /**
     * B2B 업체별 수수료 삭제
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteAlowAgency(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.deleteAlowAgency", pmParam);
    }

    /**
     * 수당관련 회원 정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectAlowMngForMemInfo(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectAlowMngForMemInfo", pmParam);
    }

    /**
     * 알선수수료 발생 B2B업체목록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getT33B2bCompList(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.getT33B2bCompList", pmParam);
    }

    /**
     * B2B영업형태 상세조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectB2BSaleTypeDtl(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectB2BSaleTypeDtl", pmParam);
    }

    /**
     * 영업형태 분류 상세 중복등록 체크
     *
     * @param pmParam
     * @throws Exception
     */
    public int selectB2BSaleTypeDtlDupleChk(Map<String, Object> pmParam) throws Exception {
        return selectOne("AlowMap.selectB2BSaleTypeDtlDupleChk", pmParam);
    }

    /**
     * 영업형태 분류 마스터 입력
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertB2BSaleTypeMst(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.insertB2BSaleTypeMst", pmParam);
    }

    /**
     * 영업형태 분류 마스터 수정
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateB2BSaleTypeMst(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.updateB2BSaleTypeMst", pmParam);
    }

    /**
     * 영업형태 분류 상세 일괄삭제
     *
     * @param pmParam
     * @throws Exception
     */
    public int deleteB2BSaleTypeDtl(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.deleteB2BSaleTypeDtl", pmParam);
    }

    /**
     * 영업형태 분류 상세 입력
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertB2BSaleTypeDtl(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.insertB2BSaleTypeDtl", pmParam);
    }

    /**
     * 수당산출 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectMonthAlowList(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectMonthAlowList", pmParam);
    }

    /**
     * B2B업체 수수료 집계
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectPayRollAgencySum(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectPayRollAgencySum", pmParam);
    }

    /**
     * 환수코드 hcode 채번
     *
     * @param pmParam
     * @throws Exception
     */
    public String getNewHcode() throws Exception {
        return (String)selectOne("AlowMap.getNewHcode");
    }

    /**
     * 수당마감여부 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectAlowMagam(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectAlowMagam", pmParam);
    }

    /**
     * 수당&수수료 마감처리
     *
     * @param pmParam
     * @throws Exception
     */
    public int saveAlowMagam(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.saveAlowMagam", pmParam);
    }

    /**
     * 입금 마감처리
     *
     * @param pmParam
     * @throws Exception
     */
    public int saveIpendMagam(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.saveIpendMagam", pmParam);
    }

    /**
     * 회원별 수당조회 - 수수료/해약&연체환수 팝업 상세 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectpopAlowdtllist(Map<String, Object> pmParam) throws Exception {
        return selectList("AlowMap.selectpopAlowdtllist", pmParam);
    }

    /**
     * 회원별 수당조회 - 수수료/해약&연체환수 팝업 상세 수정
     *
     * @param pmParam
     * @throws Exception
     */
    public int updatepopAlowDtl(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.updatepopAlowDtl", pmParam);
    }

    /**
     * 회원별 수당조회 - 수수료/해약&연체환수 팝업 상세 신규
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertpopAlowDtl(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.insertpopAlowDtl", pmParam);
    }

    /**
     * 회원별 수당조회 - 수수료/해약&연체환수 팝업 상세 삭제
     *
     * @param pmParam
     * @throws Exception
     */
    public int deletepopAlowDtl(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.deletepopAlowDtl", pmParam);
    }

    /**
     * 회원별 수당조회 - 팝업 수정에 따른 수당상세내역 신규 및 수정
     *
     * @param pmParam
     * @throws Exception
     */
    public int alowdaDtlMerge(Map<String, Object> pmParam) throws Exception {
        return update("AlowMap.alowdaDtlMerge", pmParam);
    }
}

