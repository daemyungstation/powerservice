
package powerservice.business.dlw.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.AlowService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

/**
 * 수당관리
 *
 * @author 정출연
 * @version 1.0
 * @date 2016.12.13
 */
@Service
public class AlowServiceImpl extends EgovAbstractServiceImpl implements AlowService {

    private final Logger log = LoggerFactory.getLogger(AlowServiceImpl.class);

    @Resource
    public AlowDAO alowDAO;

    /**
     * 수당계산
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int totComptAlowGrp(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        Map <String, Object> mapInVar     = xpDto.getInVariableMap();

        int iCudCnt = 0;
        Map mParam = null;
        Map mCond = null;
        String alow_dt = "";

        try {
            DataSetMap ds_cond = (DataSetMap)mapInDs.get("ds_cond");


            if (ds_cond != null) {

                mCond = ds_cond.get(0);
                alow_dt = CommonUtils.nvls((String)mCond.get("alow_dt"));

                log.debug("alow_dt : " + alow_dt);

                mParam = new HashMap<>();
                mParam.put("alow_dt", alow_dt);
                mParam.put("dept_kind", "02");

                String closeYn = alowDAO.isAlowClose(mParam);
                if ("Y".equals(closeYn)) {
                    return -1;
                }

                mParam = new HashMap<String,Object>();
                ParamUtils.addSaveParam(mParam);
                String rgsr_id = CommonUtils.nvls((String)mParam.get("rgsr_id"));

                mParam.put("alow_dt", alow_dt);
                mParam.put("emple_no", rgsr_id);
                alowDAO.comptAlowGrp(mParam);
            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

    /**
     * 근태조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAtdnList(Map<String, Object> pmParam) throws Exception {
        List<Map<String, Object>> lst = null;
        String atdn_flag= CommonUtils.nvls((String)pmParam.get("atdn_flag"));

        if ("N".equals(atdn_flag)) {
            lst = alowDAO.selectAtdnNewList(pmParam);
        }
        else {
            lst = alowDAO.selectAtdnOldList(pmParam);
        }

        return lst;
    }

    /**
     * okComptAlowGrp
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int okComptAlowGrp(Map<String, Object> pmParam) throws Exception {
        int iCudCnt = 0;

        Map<String, Object> mParam = new HashMap<>();

        mParam.put("alow_dt", pmParam.get("alow_dt"));
        mParam.put("brach_dept_cd", pmParam.get("brach_dept_cd"));
        ParamUtils.addSaveParam(mParam);

        // 근태Master 삭제
        iCudCnt += alowDAO.deleteAtdnMst(mParam);

        // 지점별 급여생성여부 등록(전사판매)
        iCudCnt += alowDAO.insertAtdnMstGrp(mParam);

        return iCudCnt;
    }

    /**
     * 전사판매 급여산출_조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectTotComptGrpList(Map<String, Object> pmParam) throws Exception {
        String alow_dt = CommonUtils.nvls((String)pmParam.get("alow_dt"));
        String alow_date_cast = alow_dt.substring(0,4) + "-" + alow_dt.substring(4,6) + "-01";
        pmParam.put("alow_date_cast",  alow_date_cast);

        log.debug(">>> selectTotComptGrpList");
        CommonUtils.printLog(pmParam);

        return alowDAO.selectTotComptGrpList(pmParam);
    }

    /**
     * 수당산출(전사판매)-개별(전사) 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectPerComptGrpList(Map<String, Object> pmParam) throws Exception {

        log.debug(">>> selectPerComptGrpList");
        CommonUtils.printLog(pmParam);

        return alowDAO.selectPerComptGrpList(pmParam);
    }

    /**
     * B2B업체별 수수료 현황
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectPayAgencyList(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectPayAgencyList(pmParam);
    }

    /**
     * B2B업체별 수수료 집계
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectPayAgencySum(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectPayAgencySum(pmParam);
    }

    /**
     * B2B수수료 업로드 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectPayAgencyByExcelList(Map<String, Object> pmParam) throws Exception {

        log.debug(">>> selectPayAgencyByExcelList");
        CommonUtils.printLog(pmParam);

        return alowDAO.selectPayAgencyByExcelList(pmParam);
    }

    /**
     * 급여산출_전체_근태내역조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectTotComptAtdnList(Map<String, Object> pmParam) throws Exception {

        log.debug(">>> selectTotComptAtdnList");
        CommonUtils.printLog(pmParam);

        return alowDAO.selectTotComptAtdnList(pmParam);
    }

    /**
     * 개별산출 대상 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectPerComptAtdnList(Map<String, Object> pmParam) throws Exception {

        log.debug(">>> selectPerComptAtdnList");
        CommonUtils.printLog(pmParam);

        return alowDAO.selectPerComptAtdnList(pmParam);
    }

    /**
     * 급여생성
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int perComptAlow(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        int iCudCnt = 0;
        Map mRow = null;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            String chk = "";
            if (listInDs != null) {
                for (int i=0; i<listInDs.size(); ++i) {
                    mRow = listInDs.get(i);
                    chk = CommonUtils.nvls((String)mRow.get("chk"));
                    if ("1".equals(chk)) {
                        alowDAO.comptAlow(mRow);
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

    /**
     * 급여생성
     *  - 수당산출-전체
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int totComptAlow(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        int iCudCnt = 0;
        Map mRow = null;
        Map mAtnd = null;
        String alowDt = "";
        List<Map<String, Object>> lstAtnd = null;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            String chk = "";
            if (listInDs != null) {
                for (int i=0; i<listInDs.size(); ++i) {
                    mRow = listInDs.get(i);
                    chk = CommonUtils.nvls((String)mRow.get("chk"));

                    if (!"1".equals(chk)) {
                        continue;
                    }

                    alowDt = (String)mRow.get("alow_dt");

                    HashMap hmObjDtl = new HashMap();
                    ParamUtils.addSaveParam(hmObjDtl);
                    hmObjDtl.put("alow_dt", alowDt);
                    hmObjDtl.put("brach_dept_cd", (String)mRow.get("brach_dept_cd"));
                    hmObjDtl.put("atdn_flag", "N");
                    hmObjDtl.put("deptKind", "01");

                    lstAtnd = selectAtdnList(hmObjDtl);

                    for (int j = 0; j < lstAtnd.size(); j++) {
                        mAtnd = lstAtnd.get(j);
                        ParamUtils.addSaveParam(mAtnd);
                        alowDAO.comptAlow(mAtnd);
                    }

                    // this.payservice.okComptAlow(hmObjDtl); - 서비스 따로 안만들고 아래 두개의 메소드 실행 - deleteAtdnMst, insertAtdnMstGrp
                    alowDAO.deleteAtdnMst(hmObjDtl);
                    alowDAO.insertAtdnMstGrp(hmObjDtl);

                }

                mRow = listInDs.get(0);
                alowDAO.deleteTempNewList(mRow); // alow_dt 조건만으로 삭제
            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

    /**
     * 지원수당재계산
     *  - 수당산출-전체
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int comptAlowBasisEnrg(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        int iCudCnt = 0;
        Map mRow = null;
        Map mAtnd = null;
        String alowDt = "";
        List<Map<String, Object>> lstAtnd = null;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            String chk = "";
            if (listInDs != null) {
                for (int i=0; i<listInDs.size(); ++i) {
                    mRow = listInDs.get(i);
                    chk = CommonUtils.nvls((String)mRow.get("chk"));

                    if (!"1".equals(chk)) {
                        continue;
                    }

                    alowDt = (String)mRow.get("alow_dt");

                    HashMap hmObjDtl = new HashMap();
                    ParamUtils.addSaveParam(hmObjDtl);
                    hmObjDtl.put("alow_dt", alowDt);
                    hmObjDtl.put("brach_dept_cd", (String)mRow.get("brach_dept_cd"));
                    hmObjDtl.put("atdn_flag", "C");
                    hmObjDtl.put("deptKind", "01");

                    lstAtnd = selectAtdnList(hmObjDtl);

                    for (int j = 0; j < lstAtnd.size(); j++) {
                        mAtnd = lstAtnd.get(j);
                        ParamUtils.addSaveParam(mAtnd);
                        alowDAO.comptAlow(mAtnd);
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

    /**
     * selectPayRollList
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectPayRollList(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectPayRollList(pmParam);
    }

    /**
     * selectPayRollListGrp
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectPayRollListGrp(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectPayRollListGrp(pmParam);
    }

    /**
     * selectPayRollListProxy
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectPayRollListProxy(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectPayRollListProxy(pmParam);
    }

    /**
     * selectPayAlowGrpJoinList
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectPayAlowGrpJoinList(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectPayAlowGrpJoinList(pmParam);
    }

    /**
     * B2B업체별 수수료 현황
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public String isAlowClose(Map<String, Object> pmParam) throws Exception {
        return alowDAO.isAlowClose(pmParam);
    }

    /**
     * 수당확정
     *- 수당지급대장(전사판매)
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int alowClose(Map<String, Object> pmParam) throws Exception {
        int iCudCnt = 0;
        try {

            // 수당 근거자료 생성 - TOBE 에서는 백업을 하지 않음
            // alowDAO.makeBasedData(pmParam);

            // 급여 Master 확정일자 update
            iCudCnt = alowDAO.updatealowClose(pmParam);

        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

    /**
     * B2B영업형태 분류 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectB2BSaleTypeList(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectB2BSaleTypeList(pmParam);
    }

    /**
     * selectAlowListForAccnt
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAlowListForAccnt(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectAlowListForAccnt(pmParam);
    }

    /**
     * selectAlowListForAccntSum
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAlowListForAccntSum(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectAlowListForAccntSum(pmParam);
    }

    /**
     * selectAlowListForExtr
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAlowListForExtr(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectAlowListForExtr(pmParam);
    }

    /**
     * selectAlowListForF12
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAlowListForF12(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectAlowListForF12(pmParam);
    }


    /**
     * 마감 (회원별 수당)
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int callUPAlowAmt(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        int iCudCnt = 0;
        Map mRow = null;
        int chkCnt;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            String chk = "";
            if (listInDs != null) {
                mRow = listInDs.get(0);

                chkCnt = alowDAO.selectAloMstForAccnt(mRow);
                log.debug(">chkCnt : " + chkCnt);

                if (chkCnt > 0) {
                    return -1;
                } else {
                    iCudCnt = alowDAO.callUPAlowAmt(mRow);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

    /**
     * 마감해지 (회원별 수당)
     *  -
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int deleteAlowAmt(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        int iCudCnt = 0;
        Map<String,Object> mRow = null;
        int chkCnt;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null) {
                mRow = listInDs.get(0);

                chkCnt = alowDAO.selectAlowAmtAccnt(mRow);

                if (chkCnt > 0) {
                    iCudCnt = alowDAO.deleteAlowAmt(mRow);
                } else {
                    throw new EgovBizException("삭제 할 마감 데이터가 없습니다.");
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

    /**
     * 수당추가입력
     *  - 회원별 수당관리(DL470900P01.xfdl) 화면에서 추가입력 시 사용
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int insertAlowDaDtl(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        int iCudCnt = 0;
        Map mRow = null;
        int chkCnt;
        String alow_cd = "";
        String sAmt = "";

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            String chk = "";
            if (listInDs != null) {

                for (int i=0; i<listInDs.size(); ++i) {

                    mRow = listInDs.get(i);

                    ParamUtils.addSaveParam(mRow);

                    sAmt = CommonUtils.nvls((String)mRow.get("amt"));
                    alow_cd = CommonUtils.nvls((String)mRow.get("alow_cd"));
                    sAmt = sAmt.replaceAll(",", "");
                    sAmt = sAmt.replaceAll(" ", "");
                    if ("".equals(sAmt)) {
                        sAmt = "0";
                    }
                    mRow.put("amt", sAmt);

                    if (!"0".equals(sAmt)) {

                        if ("999999".equals(sAmt)) {
                            sAmt = "0";
                            mRow.put("amt", sAmt);
                        }
                        chkCnt = alowDAO.selectAlowDaDtlCheck(mRow);

                        if (chkCnt < 2) {
                            alowDAO.insertAlowDaDtl(mRow);
                        }
                        else
                        {
                            alowDAO.updateAlowDaDtl(mRow);
                            alowDAO.insertAlowDaDtl(mRow);
                        }

                        if ("F12".equals(alow_cd)) {
                            alowDAO.updateAlowDtlF12(mRow);
                        }
                    }
                }

                //alowDAO.callUpAlowDtlF12(mRow);
            }

        } catch (Exception e) {
            throw e;
        }

        return iCudCnt;
    }

    /**
     * 회원별 수당 관리(회원 개별 상세조회)
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAlowMngForAccnt(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectAlowMngForAccnt(pmParam);
    }

    /**
     * B2B업체 수수료  상세내역
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectPayRollAgencyList(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectPayRollAgencyList(pmParam);
    }

    /**
     * 종합현황(재무팀 전용)
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectTotStatusAudit(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectTotStatusAudit(pmParam);
    }
    
    /**
     * 종합현황(재무팀 전용)
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectTotStatus(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectTotStatus(pmParam);
    }

    /**
     * 수당코드조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAlowCdList(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectAlowCdList(pmParam);
    }

    /**
     * 수당코드 등록/수정
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int mergeAlowCd(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        int iCudCnt = 0;
        Map mRow = null;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            String chk = "";
            if (listInDs != null) {

                for (int i=0; i<listInDs.size(); ++i) {
                    mRow = listInDs.get(i);

                    ParamUtils.addSaveParam(mRow);
                    CommonUtils.printLog(mRow);


                    int rowType = ((Integer) mRow.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                    if (rowType == DataSet.ROW_TYPE_INSERTED) {
                        iCudCnt += alowDAO.insertAlowCd(mRow);
                    }
                    else if (rowType == DataSet.ROW_TYPE_UPDATED) {
                        iCudCnt += alowDAO.updateAlowCd(mRow);
                    }

                }
            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

    /**
     * 수당코드 기초Data조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAlowCdDataList(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectAlowCdDataList(pmParam);
    }

    /**
     * 환수코드 등록/수정/삭제
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int saveAlowSetSub(XPlatformMapDTO xpDto, Map<String, Object> mResult) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        int iCudCnt = 0;
        Map mRow = null;
        String hcode = "";
        List<Map<String, Object>> mList = null;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            String chk = "";
            if (listInDs != null) {

                mRow = listInDs.get(0);
                hcode = CommonUtils.nvls((String)mRow.get("hcode"));
                if ("".equals(hcode)) {
                    hcode = alowDAO.getNewHcode();
                }

                mResult.put("hcode", hcode);

                for (int i=0; i<listInDs.size(); ++i) {
                    mRow = listInDs.get(i);

                    ParamUtils.addSaveParam(mRow);

                    int rowType = ((Integer) mRow.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                    if (rowType == DataSet.ROW_TYPE_INSERTED)
                    {
                        if (null == mRow.get("hcode") || "".equals(mRow.get("hcode").toString())) {
                            mRow.put("hcode", hcode);
                        }
                        mList = alowDAO.getHcodeList(mRow);
                        if ( null != mList && mList.size()>0 ) {
                            log.debug("saveAlowSetSub> 등록시 키값 중복------------->");
                            continue;
                        }

                        iCudCnt += alowDAO.insertAlowSetSub(mRow);
                    }
                    else if (rowType == DataSet.ROW_TYPE_UPDATED)
                    {
                        iCudCnt += alowDAO.updateAlowSetSub(mRow);
                    }
                    else if (rowType == DataSet.ROW_TYPE_DELETED)
                    {
                        iCudCnt += alowDAO.deleteAlowSetSub(mRow);
                    }
                    else {
                        //
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

    /**
     * 사원별 수당 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAlowListForEmpleNo(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectAlowListForEmpleNo(pmParam);
    }

    /**
     * 수수료 현황 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> getB2bCompFeeList(Map<String, Object> pmParam) throws Exception {
        return alowDAO.getB2bCompFeeList(pmParam);
    }

    /**
     * 회원별 수당 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAccntAlowList(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectAccntAlowList(pmParam);
    }

    /**
     * 환수목록조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> getHcodeList(Map<String, Object> pmParam) throws Exception {
        return alowDAO.getHcodeList(pmParam);
    }

    /**
     * 수당기초데이터 저장
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int saveAlowBaseData(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        int iCudCnt = 0;
        Map mRow = null;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            String chk = "";
            if (listInDs != null) {

                for (int i=0; i<listInDs.size(); ++i) {
                    mRow = listInDs.get(i);

                    ParamUtils.addSaveParam(mRow);
                    CommonUtils.printLog(mRow);

                    int rowType = ((Integer) mRow.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                    if (rowType == DataSet.ROW_TYPE_INSERTED) {
                        iCudCnt += alowDAO.insertAlowSetMst(mRow);
                    }
                    else if (rowType == DataSet.ROW_TYPE_UPDATED) {
                        iCudCnt += alowDAO.updateAlowSetMst(mRow);
                    }
                    else if (rowType == DataSet.ROW_TYPE_DELETED) {
                        iCudCnt += alowDAO.deleteAlowSetMst(mRow);
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

    /**
     * B2B수수료계산
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int alowCalcAgency(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        int iCudCnt = 0;
        Map mRow = null;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null) {
                mRow = listInDs.get(0);
                ParamUtils.addSaveParam(mRow);
                iCudCnt = alowDAO.alowCalcAgency(mRow);
            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

    /**
     * B2B 업체별 수수료 삭제
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int deleteAlowAgency(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        int iCudCnt = 0;
        Map mRow = null;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null) {
                mRow = listInDs.get(0);
                ParamUtils.addSaveParam(mRow);
                iCudCnt = alowDAO.deleteAlowAgency(mRow);
            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

    /**
     * 수당관련 회원 정보 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAlowMngForMemInfo(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectAlowMngForMemInfo(pmParam);
    }

    /**
     * 알선수수료 발생 B2B업체목록
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> getT33B2bCompList(Map<String, Object> pmParam) throws Exception {
        return alowDAO.getT33B2bCompList(pmParam);
    }

    /**
     * B2B영업형태 상세조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectB2BSaleTypeDtl(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectB2BSaleTypeDtl(pmParam);
    }

    /**
     * B2B영업형태 분류 저장
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int mergeB2BSaleTypeMng(XPlatformMapDTO xpDto, Map<String, Object> mResult) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        List<Map<String, Object>> lst = null;
        int iCudCnt = 0;
        Map mRow = null;
        Map<String,Object> mRow2 = null;
        String seq = "";
        String pk = "";
        String st_grp_cd = "";
        int dtlDupleCheck = 0;
        int iCnt = 0;

        try {

            DataSetMap lstMst = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap lstDtl = (DataSetMap)mapInDs.get("ds_input2");

            String chk = "";

            if (lstMst != null) {

                mRow = lstMst.get(0);
                ParamUtils.addSaveParam(mRow);
                seq = CommonUtils.nvls((String)mRow.get("seq"));
                st_grp_cd = CommonUtils.nvls((String)mRow.get("st_grp_cd"));

                log.debug("seq : " + seq);
                log.debug("st_grp_cd : " + st_grp_cd);

                if (!"".equals(seq) || !"".equals(st_grp_cd)) // update
                {
                    iCudCnt += alowDAO.updateB2BSaleTypeMst(mRow);
                }
                else // insert
                {
                    iCudCnt += alowDAO.insertB2BSaleTypeMst(mRow);

                    pk				= CommonUtils.nvls((String)mRow.get("pk"));
                    String[] sArr 	= pk.split("#");
                    seq 			= sArr[0].trim();
                    st_grp_cd 		= sArr[1].trim();
                }

                log.debug("2.seq : " + seq);
                log.debug("2.st_grp_cd : " + st_grp_cd);

                mRow2 = new HashMap<>();
                mRow2.put("st_grp_cd", st_grp_cd);
                iCudCnt += alowDAO.deleteB2BSaleTypeDtl(mRow2);

                for (int i=0; i<lstDtl.size(); ++i) {
                    mRow = lstDtl.get(i);

                    ParamUtils.addSaveParam(mRow);
                    CommonUtils.printLog(mRow);

                    // pk : st_grp_cd, sale_type

                    if (null == mRow.get("st_grp_cd")) {
                        mRow.put("st_grp_cd", st_grp_cd);
                    }

                    iCnt = alowDAO.selectB2BSaleTypeDtlDupleChk(mRow);
                    if (iCnt > 0) {
                        dtlDupleCheck++;
                    } else {
                        alowDAO.insertB2BSaleTypeDtl(mRow);
                    }
                }
            }

            mResult.put("dup_cnt", String.valueOf(dtlDupleCheck));
            mResult.put("st_grp_cd", st_grp_cd);
            mResult.put("seq", seq);

        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

    /**
     * 수당산출 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectMonthAlowList(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectMonthAlowList(pmParam);
    }

    /**
     * B2B업체 수수료 집계
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectPayRollAgencySum(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectPayRollAgencySum(pmParam);
    }

    /**
     * 수당마감여부 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAlowMagam(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectAlowMagam(pmParam);
    }

    /**
     * 수당&수수료 마감처리
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int saveAlowMagam(Map<String, Object> pmParam) throws Exception {
        return alowDAO.saveAlowMagam(pmParam);
    }

    /**
     * 입금 마감처리
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int saveIpendMagam(Map<String, Object> pmParam) throws Exception {
        return alowDAO.saveIpendMagam(pmParam);
    }

    /**
     * 회원별 수당조회 - 수수료/해약&연체환수 팝업 상세 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectpopAlowdtllist(Map<String, Object> pmParam) throws Exception {
        return alowDAO.selectpopAlowdtllist(pmParam);
    }

    /**
     * 회원별 수당조회 - 수수료/해약&연체환수 팝업 상세 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updatepopAlowDtl(Map<String, Object> pmParam) throws Exception {
        return alowDAO.updatepopAlowDtl(pmParam);
    }

    /**
     * 회원별 수당조회 - 수수료/해약&연체환수 팝업 상세 신규
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertpopAlowDtl(Map<String, Object> pmParam) throws Exception {
        return alowDAO.insertpopAlowDtl(pmParam);
    }

    /**
     * 회원별 수당조회 - 수수료/해약&연체환수 팝업 상세 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deletepopAlowDtl(Map<String, Object> pmParam) throws Exception {
        return alowDAO.deletepopAlowDtl(pmParam);
    }

    /**
     * 회원별 수당조회 - 팝업 수정에 따른 수당상세내역 신규 및 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int alowdaDtlMerge(Map<String, Object> pmParam) throws Exception {
        return alowDAO.alowdaDtlMerge(pmParam);
    }

}
