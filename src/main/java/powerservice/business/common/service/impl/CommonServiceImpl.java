package powerservice.business.common.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DlwVatSvcService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.DateUtil;
import powerservice.common.util.IDGenerator;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 공통 기능
 *
 * @author 정출연
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Common
 */
@Service
public class CommonServiceImpl extends EgovAbstractServiceImpl implements CommonService {

    private final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Resource
    public CommonDAO commonDAO;

    @Resource
    private DlwVatSvcService dlwVatSvcService;

    /**
     * 지부 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getBranchList(Map<String, Object> pmParam) throws Exception {
        return commonDAO.getBranchList(pmParam);
    }

    /**
     * 공통콤보 행사관리 창고조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getWhCdList(Map<String, Object> pmParam) throws Exception {
        return commonDAO.getWhCdList(pmParam);
    }

    /**
     * 거래처 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCustomerCdList(Map<String, Object> pmParam) throws Exception {
        return commonDAO.getCustomerCdList(pmParam);
    }

    /**
     * 품목코드 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectGoodsComList(Map<String, Object> pmParam) throws Exception {
        return commonDAO.selectGoodsComList(pmParam);
    }

    /**
     * CP 정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectCpInfo(Map<String, Object> pmParam) throws Exception {
        return commonDAO.selectCpInfo(pmParam);
    }

    /**
     * 사내조직도 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectOrgChartMap(Map<String, Object> pmParam) throws Exception {
        return commonDAO.selectOrgChartMap(pmParam);
    }

    /**
     * 영업형태 분류 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectStGrpCdList(Map<String, Object> pmParam) throws Exception {
        return commonDAO.selectStGrpCdList(pmParam);
    }


    /**
     * 부가서비스 상세 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectOptSvcList(Map<String, Object> pmParam) throws Exception {
        return commonDAO.selectOptSvcList(pmParam);
    }

    /**
     * 엑셀다운로드 이력 생성
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int insertExcelDownHist(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        int iCudCnt = 0;
        Map mRow = null;
        List<Map<String, Object>> lstAtnd = null;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            String chk = "";
            if (listInDs != null) {
                for (int i=0; i<listInDs.size(); ++i) {
                    mRow = listInDs.get(i);
                    ParamUtils.addSaveParam(mRow);
                    commonDAO.insertExcelDownHist(mRow);

                }
            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }


    public void insertSMS(Map<String, Object> pmParam) throws Exception
    {
        Calendar rightNow = Calendar.getInstance();
        String NowDate = new String();
        String msg = new String();
        String msg1 = new String();
        String msg2 = (String)pmParam.get("msg2");
        String mode = (String)pmParam.get("mode");
        String useYn = new String();

        Map<String, Object> smsMap = new HashMap<String, Object>();

        if (String.valueOf(pmParam.get("mode2")).equals("moniter"))
        {
            ArrayList chaArr = (ArrayList)pmParam.get("paramArr");

            Map<String, Object> paramMap = null;
            for (int i = 0; i < chaArr.size(); i++)
            {
                paramMap = (HashMap)chaArr.get(i);

                pmParam.put("accnt_no", paramMap.get("accnt_no"));
                pmParam.put("emple_no", paramMap.get("emple_no"));
                pmParam.put("phone", paramMap.get("phone"));
                pmParam.put("recv_name", paramMap.get("mem_nm"));
                pmParam.put("callback", pmParam.get("corp_cell"));
                pmParam.put("send_name", pmParam.get("corp_nm"));

                msg = "";
                msg1 = "";

                if (String.valueOf(pmParam.get("name_chk")).equals("N"))
                {
                    if (String.valueOf(pmParam.get("mode")).equals("yenChePreList")) {
                        msg = pmParam.get("msg").toString();
                        msg1 = pmParam.get("msg").toString();

                        if (msg.indexOf("**") >= 0) {
                            msg1 = msg1.replace("**", (String)paramMap.get("mem_nm"));
                        }
                        if (msg.indexOf("@@") >= 0) {
                            msg1 = msg1.replace("@@", (String)paramMap.get("yenCh"));
                        }
                        if (msg.indexOf("XX") >= 0) {
                            int noPlus = 4;
                            msg1 = msg1.replace("XX", String.valueOf(noPlus));
                        }
                        msg = (String)paramMap.get("mem_nm") + "님" + msg1 + "-" + pmParam.get("corp_nm") + "-";
                    }

                    if (String.valueOf(pmParam.get("mode")).equals("payCondList")) {
                        msg1 = (String)pmParam.get("msg").toString().replace("**", (String)paramMap.get("total"));
                        msg = (String)paramMap.get("mem_nm") + "님" + msg1 + "-" + (String)pmParam.get("corp_nm") + "-";
                    }

                    if (String.valueOf(pmParam.get("mode")).equals("resnListSelect")) {
                        msg = (String)paramMap.get("mem_nm") + "님" + (String)pmParam.get("msg") + "-" + (String)pmParam.get("corp_nm") + "-";
                    }

                    if (String.valueOf(pmParam.get("mode")).equals("alowPayRoll")) {
                        pmParam.put("accnt_no", paramMap.get("emple_no"));
                        pmParam.put("phone", paramMap.get("phone"));
                        pmParam.put("recv_name", paramMap.get("empleNm"));
                        msg1 = pmParam.get("msg").toString().replace("**", (String)pmParam.get("month"));
                        msg = (String)paramMap.get("empleNm") + "님" + msg1 + "-" + (String)pmParam.get("corp_nm") + "-";
                    }
                }

                if (pmParam.get("name_chk").equals("Y")) {
                    msg = (String)pmParam.get("msg") + "-" + pmParam.get("corp_nm") + "-";
                }

                pmParam.put("msg", msg);
                pmParam.put("reg_dm", "");
                pmParam.put("site_id", pmParam.get("site_id"));
                pmParam.put("user_id", pmParam.get("user_id"));
                pmParam.put("gubun", pmParam.get("gubun"));
            }

        }

        NowDate = "";

        if (pmParam.get("reqdate") != null)
        {
            NowDate = pmParam.get("reqdate").toString().replace("/", "-");
        }
        else
        {
            NowDate = String.valueOf(rightNow.get(1)) + '-' + getMonth(rightNow.get(2) + 1) + '-';
            NowDate = NowDate + getMonth(rightNow.get(5)) + ' ' + String.valueOf(rightNow.get(11)) + ':' + String.valueOf(rightNow.get(12)) + ':' + String.valueOf(rightNow.get(13)) + '.' + String.valueOf(rightNow.get(14));
        }

        pmParam.put("reqdate", NowDate);


        smsMap.put("accnt_no"	,pmParam.get("accnt_no"));
        smsMap.put("emple_no"	,pmParam.get("emple_no"));
        smsMap.put("phone"		,pmParam.get("phone"));
        smsMap.put("recv_name"	,pmParam.get("recv_name"));
        smsMap.put("callback"	,pmParam.get("callback"));
        smsMap.put("send_name"	,pmParam.get("send_name"));
        smsMap.put("status"		,"1");
        smsMap.put("reqdate"	,pmParam.get("reqdate"));
        smsMap.put("rslt"		,"");
        smsMap.put("msg"		,pmParam.get("msg"));
        smsMap.put("type"		,"0");
        smsMap.put("sendcnt"	,"0");
        smsMap.put("reg_man"	,pmParam.get("reg_man"));
        smsMap.put("site_id"	,pmParam.get("site_id"));
        smsMap.put("user_id"	,pmParam.get("user_id"));
        smsMap.put("gubun"		,pmParam.get("gubun"));

        String message = (String)pmParam.get("msg");

        int msgLen = message.getBytes("euc-kr").length;
        if (msgLen <= 80) {
            smsMap.put("msg_cl"		,"SMS");
        } else {
            smsMap.put("msg_cl"		,"MMS");
        }

        smsMap.put("mappingKey"	,commonDAO.insertSMS(smsMap));


        if (!"".equals((String)smsMap.get("mem_no"))) {
            insertCnsl((String)pmParam.get("sms_sep"), smsMap);
        }

        commonDAO.insertSMSReal(smsMap);
    }



    public void insertCnsl(String paramSmsSep, Map<String, Object> smsMap) throws Exception
    {
        String smsSep = "";
        StringBuffer cnslMngCon = new StringBuffer();
        StringBuffer cnslDtlCon = new StringBuffer();

        if ((paramSmsSep == null) || ("".equals(paramSmsSep))){
            smsSep = "09";
        } else {
            smsSep = paramSmsSep;
        }
        List<Map<String, Object>> commList = commonDAO.selectCboCommCd("110");
        String cdNm = "";
        Map<String, Object> commMap = null;
        for (int i = 0; i < commList.size(); i++) {
            commMap = commList.get(i);
            if(smsSep.equals((String)commMap.get("com_cd")) ){
                cdNm = (String)commMap.get("cd_nm");
            }
        }

        if ("".equals((String)smsMap.get("mem_no"))) {
            smsMap.put("mem_no", commonDAO.selectMemNo((String)smsMap.get("accnt_no")) );
        }

        cnslMngCon.append(cdNm + " SMS전송");
        cnslDtlCon.append("[구분:" + cdNm + "][회원명:" + (String)smsMap.get("recv_name") + "][연락처:" + (String)smsMap.get("phone") + "][문자내용:" + (String)smsMap.get("msg") + "]");


        if ((Boolean.valueOf((String)smsMap.get("emp_chk")) != null) && (Boolean.valueOf((String)smsMap.get("emp_chk")))   ) {
            String cell = (String)smsMap.get("cell");
            if (!"".equals(cell)) {
                cnslDtlCon.append("[담당자전송:" + (String)smsMap.get("emple_no") + "(" + cell + ")" + "]");
    //			cnslDtlCon.append("[담당자전송:" + (String)smsMap.get("emple_no") + "(" + cell + ")" + "]");
            }
        }

        Map<String, Object> conMap = new HashMap<String, Object>();

        conMap.put("accnt_no"		,smsMap.get("accnt_no"));
        conMap.put("mem_no"			,smsMap.get("mem_no"));
        conMap.put("cnsl_mng_con"	,cnslMngCon.toString());
        conMap.put("cnsl_dtl_con"	,cnslDtlCon.toString());
        conMap.put("gubn"			,"110");
        conMap.put("cnsl_cd"		,smsSep);

        conMap.put("reg_man"		,(String)smsMap.get("reg_man"));
        conMap.put("upd_man"		,(String)smsMap.get("reg_man"));
        conMap.put("cnsl_man"		,(String)smsMap.get("reg_man"));

        dlwVatSvcService.insertConsulEtc(conMap);
    }


    private String getMonth(int value) {
        String _value = "";
        if (value >= 10) {
            _value = String.valueOf(value);
        } else {
            _value = "0" + value;
        }
        return _value;
    }


    /**
     * 자료권한 그룹 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectDataAuthGrpList(Map<String, Object> pmParam) throws Exception {
        return commonDAO.selectDataAuthGrpList(pmParam);
    }

    /**
     * 종합현황 조회 권한
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectReportAuthGrpList(Map<String, Object> pmParam) throws Exception {
        return commonDAO.selectReportAuthGrpList(pmParam);
    }

    /**
     * 파일다운로드 키 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertFileDown(Map<String, Object> hmParam) throws Exception
    {
        int iCudCnt = 0;

        String sKey = DateUtil.getCurrDthms() + UUID.randomUUID().toString().replace("-", "");
        String emple_no = CommonUtils.nvls((String)hmParam.get("emple_no"));
        if (!"".equals(emple_no)) {
            sKey = emple_no + "_" + sKey.substring(0,20);
        } else {
            sKey = sKey.substring(0,30);
        }

        hmParam.put("download_key", sKey);
        iCudCnt += commonDAO.insertFileDown(hmParam);

        return iCudCnt;
    }

    /**
     * 파일다운로드 일시분초 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateFileDownDthms(Map<String, Object> hmParam) throws Exception
    {
        int iCudCnt = 0;
        iCudCnt += commonDAO.updateFileDownDthms(hmParam);
        return iCudCnt;
    }

    /**
     * 파일다운로드 키 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectFileDown(Map<String, Object> pmParam) throws Exception {
        return commonDAO.selectFileDown(pmParam);
    }

    /**
     * 콤보 분류에 따른 영업형태
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectSaleTypeByStGrp(Map<String, Object> pmParam) throws Exception {
        return commonDAO.selectSaleTypeByStGrp(pmParam);
    }

    /**
     * 상품코드 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectProdCdList(Map<String, Object> pmParam) throws Exception {
        return commonDAO.selectProdCdList(pmParam);
    }

    /**
     * B2B 코드 목록 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getB2bList(Map<String, Object> pmParam) throws Exception {
        return commonDAO.getB2bList(pmParam);
    }






    /**
     * 접속 로그 2017.11.30
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void insertLog(Map<String, Object> pmParam) throws Exception	{

        commonDAO.insertLog(pmParam);

    }

    /**
     * 접속 로그카운트 조회 2017.12.08 정승철
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getLogCount(Map<String, ?> pmParam) throws Exception {
        return commonDAO.getLogCount(pmParam);
    }

    /**
     * 접속 로그 검색 2017.12.08 정승철
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getLogList(Map<String, ?> pmParam) throws Exception {
        return commonDAO.getLogList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200616
     * 이름 : 송준빈
     * 추가내용 : 엑셀다운로드 사유기입
     * 대상 테이블 : LF_DMUSER.MEM_MENU_LOG
     * ================================================================
     */
    public int insertExcelDownloadReason(Map<String, Object> pmParam) throws Exception	{
        return commonDAO.insertExcelDownloadReason(pmParam);
    }
}
