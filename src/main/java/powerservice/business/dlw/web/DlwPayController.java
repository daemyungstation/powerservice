/*
 * (@)# DlwPayController.java
 */

package powerservice.business.dlw.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.cns.web.ConsController;
import powerservice.business.dlw.service.DlwPayService;
import powerservice.business.dlw.service.DlwWdrwService;
import powerservice.business.mall.service.DlwMallLinkedService;
import powerservice.business.sys.service.BasVlService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.SimpleFtpClient;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.data.VariableList;
import com.tobesoft.xplatform.tx.HttpPlatformResponse;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
//2018.01.05 로그 추가
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

@Controller
@RequestMapping(value = "/dlw/pay")
public class DlwPayController {

    @Resource
    private DlwPayService DlwPayService;

    @Resource
    private DlwWdrwService DlwWdrwService;

    @Resource
    private ConsController consController;

    @Resource
    private BasVlService basVlService;
    
    @Resource
    private DlwMallLinkedService DlwMallLinkedService;
    /*
    @Resource
    private CommonService commonService;
    */

    /**
     * 결과 데이터건수 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/srchReqResultCount")
    public ModelAndView getReqResultCount(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            if (listInDs != null &&listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }

            List<Map<String, Object>> mList = DlwPayService.getReqResultCount(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * Card/CMS 결과 DB 입력
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insertResultInfo")
    public ModelAndView insertResultInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String chkType = request.getHeader("Content-Type");
        if (null == chkType) {
            return modelAndView;
        }

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        Map<String, Object> mResult = new HashMap<>();
        String fileNm = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        try {
            DlwPayService.insertResultInfo(request, response, mResult);

        } catch (EgovBizException e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg", e.getMessage());
            e.printStackTrace();

        } catch (Exception e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg", e.getMessage());
            e.printStackTrace();
        }

        return modelAndView;
    }


    /**
     * 파일변환 (** 결과반영)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/convertFile")
    public ModelAndView convertFile(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            Map<String, Object> hmParam = new HashMap<String, Object>();

            //InetAddress inet = InetAddress.getLocalHost();
            //String uip = inet.getHostAddress();

            //세션
            hmParam.put("req_day", mapInVar.get("req_day")); // 청구일
            hmParam.put("pay_mthd", mapInVar.get("pay_mthd")); // 납입방법
            hmParam.put("pay_kind", "01"); // 결과 종류 (01 : 파일, 02 : 실시간 카드 결제)
            hmParam.put("deposit_day", mapInVar.get("deposit_day"));
            hmParam.put("auth_yn", mapInVar.get("auth_yn").toString());     // 유승인 무승인 

            ParamUtils.addSaveParam(hmParam);
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));
            hmParam.put("emple_no", hmParam.get("rgsr_id")); 

            System.out.println(hmParam);

        try{
        		System.out.println("변환 진행 중....");
                //입금등록 전에 청구 테이블 결과 값 업데이트
                DlwPayService.uptReqResultStat(hmParam); 

                //업데이트 완료 후 입금 등록 
                DlwWdrwService.insertMemPayData(hmParam);


                //파일변환 (** 결과반영)
           }  catch(Exception e) {
               e.printStackTrace();
           }

            //DlwPayService.convertFile(hmParam);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 출금이체의뢰내역(청구) 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getWdrwReqList")
    public ModelAndView getWdrwReqList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMapCard = new DataSetMap();
        DataSetMap listMapCardNauth = new DataSetMap();
        DataSetMap listMapCMS = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                int nTotal = DlwPayService.getWdrwReqListCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = DlwPayService.getWdrwReqList(hmParam);
                List<Map<String, Object>> mListCard = DlwPayService.getCalcAndChargeCardSummary(hmParam);
                List<Map<String, Object>> mListCardNauth = DlwPayService.getCalcAndChargeCardNauthSummary(hmParam);
                List<Map<String, Object>> mListCMS = DlwPayService.getCalcAndChargeCMSSummary(hmParam);

                listMap.setRowMaps(mList);
                listMapCard.setRowMaps(mListCard);
                listMapCardNauth.setRowMaps(mListCardNauth);
                listMapCMS.setRowMaps(mListCMS);

                mapOutDs.put("ds_output", listMap);
                mapOutDs.put("ds_outputCard", listMapCard);
                mapOutDs.put("ds_outputCardNauth", listMapCardNauth);
                mapOutDs.put("ds_outputCMS", listMapCMS);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }


    /**
     * 특수회원 조회
     * 정승철
     * 20181018
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/srchSpecialAcntList")
    public ModelAndView getSpecialAcntList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();


            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            String sExcelYn = StringUtils.defaultString((String) mapInVar.get("excel_yn"));

            if (!"Y".equals(sExcelYn)) {
                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
            }

            int nTotal = DlwPayService.getCntSpecialAcntList(hmParam);

            List<Map<String, Object>> mList = DlwPayService.getSpecialAcntList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            mapOutVar.put("tc_list", nTotal);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }


    /**
     * 산출특수회원 입력/수정/삭제
     * 정승철
     * 20181019
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/extSpecial/{crudType}")
    public ModelAndView crudExtSpecial(XPlatformMapDTO xpDto, Model model, @PathVariable("crudType") String crudTyp) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                ParamUtils.addSaveParam(hmParam);

                //System.out.println("==================crudTyp : " + crudTyp);

                if("insert".equals(crudTyp)){
                    // 기존 특수회원인지 체크
                    int chkCnt = DlwPayService.getChkSpecialAcntList(hmParam);

                    if(chkCnt == 0) {
                        hmParam.put("reg_man", hmParam.get("rgsr_id"));
                        DlwPayService.insertExtSpecial(hmParam);
                    }
                    else {
                        mapOutVar.put("g_reason_msg", "해당 회원은 이미 특수회원으로 등록되어 있습니다.");  // 실패 메시지 return
                    }
                }
                else if("update".equals(crudTyp)){
                    hmParam.put("upd_man", hmParam.get("rgsr_id"));
                    DlwPayService.updateExtSpecial(hmParam);
                }
                else if("delete".equals(crudTyp)){
                    hmParam.put("upd_man", hmParam.get("rgsr_id"));
                    DlwPayService.deleteExtSpecial(hmParam);
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 출금이체의뢰내역(청구) 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getWdrwReqAccntConfirm")
    public ModelAndView getWdrwReqAccntConfirm(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String accnt_no = (String)mapInVar.get("accnt_no");

            if (accnt_no != null || accnt_no != "" || !accnt_no.equalsIgnoreCase(""))
            {
                int nAccntCnt = DlwPayService.getWdrwReqAccntConfirm(accnt_no);
                mapOutVar.put("gv_wdrw_req_accnt", nAccntCnt); //tc_mem=전체건수
            }
            else
            {
                szErrorCode = "-1";
                szErrorMsg = "회원번호가 공백입니다. 청구 판정이 불가능합니다.";
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                return modelAndView;
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 출금이체의뢰내역(산출, 청구(행사, 해약)) 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getWdrwReqAccntEventConfirm")
    public ModelAndView getWdrwReqAccntEventConfirm(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String accnt_no = (String)mapInVar.get("accnt_no");

            if (accnt_no != null || accnt_no != "" || !accnt_no.equalsIgnoreCase(""))
            {
                mList = DlwPayService.getWdrwReqAccntEventConfirm(accnt_no);

                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap); //tc_mem=전체건수
            }
            else
            {
                szErrorCode = "-1";
                szErrorMsg = "회원번호가 공백입니다. 청구 판정이 불가능합니다.";
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                return modelAndView;
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명스테이션 Card File List 수신리스트 다운로드 및 리스트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     * 접속정보 >  ip:121.133.126.8  port:22
     * 계정&비번 > dmlife000g / eoaudqlffld1!
     */

    @RequestMapping(value = "/recvCardFileList")
    public ModelAndView recvfilelist(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> mapInVar = xpDto.getInVariableMap();     // 화면에서 받은 값
        Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();   // 화면에서 보내온 DS
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();   // 화면에 보낼 값
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap(); // 화면에 보낼 DS

        Map<String, Object> hmParam = new HashMap<String, Object>(); // DS에서 한건씩 꺼내서 쓸 맵
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> mList1 = new ArrayList<Map<String, Object>>();

        // 현재 날짜
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        // 조회월을 받음.
        String stt_month = (String) mapInVar.get("stt_month").toString().substring(0,6);
        String stt_dt    = (String) mapInVar.get("stt_dt").toString();
        System.out.println("송신월 ::: " + stt_month + " 산출기준일 ::: " + stt_dt);

        String sFTPFileDirectoryPath  = "/home/dmlife000g/billrslt/" + stt_month + "/";
        //String sRecvFileDirectoryPath = "C:\\app\\dmlife000g\\billrslt\\" + stt_month + "\\"; // 로컬에서 테스트 수행시
        String sRecvFileDirectoryPath = "/app/dmlife000g/billrslt/" + stt_month + "/"; // 서버에서 수행시

        Session session = null;
        JSch jsch = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;

        InputStream ftpIn = null;
        InputStreamReader ftpIsr= null;
        BufferedReader ftpBr = null;

        LineNumberReader reader  = null;

        String sNicePaymentFtpIp = "121.133.126.8";   // 실제 FTP 서버 IP
        int iNicePaymentFtpPort = 22;                 // 실제 FTP PORT
        String sFTPLoginId = basVlService.getBasVlAsString("nice_ftp_access_id"); // 실제 접속 Id (dmlife000g)
        String sFTPLoginPw = basVlService.getBasVlAsString("nice_ftp_access_pw"); // 실제 접속 PassWord (eoaudqlffld1!)

        System.out.println("sFTPLoginId ::: " + sFTPLoginId + " sFTPLoginPw ::: " + sFTPLoginPw);
        File fileRecvFileDirectoryPath = new File(sRecvFileDirectoryPath);

        if(fileRecvFileDirectoryPath.exists() == false)
        {
            fileRecvFileDirectoryPath.mkdirs();
        }

        String[] asRecvFileList = fileRecvFileDirectoryPath.list();
        Vector<String> vRecvFileList = new Vector<String>();

        for(int idx = 0; idx < asRecvFileList.length; idx++)
        {
            vRecvFileList.add(asRecvFileList[idx]);
        }

        /* 이 부분에서 본서버(192.168.10.6X) 로 받아오지 않은 파일을 모두 받아옴 */
        try
        {
            jsch = new JSch();

            session = jsch.getSession(sFTPLoginId, sNicePaymentFtpIp, iNicePaymentFtpPort);
            session.setPassword(sFTPLoginPw);

            java.util.Properties config = new java.util.Properties();

            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            // 6. sftp 채널을 연다.
            channel = session.openChannel("sftp");

            channel.connect();
            channelSftp = (ChannelSftp)channel;
            //channelSftp.connect();

            //수신 디렉토리 접근
            channelSftp.cd(sFTPFileDirectoryPath);

            //디렉토리 안의 목록 조회
            Vector<ChannelSftp.LsEntry> sendFileList = channelSftp.ls(sFTPFileDirectoryPath);

            System.out.println("받은 파일 내역 : " + vRecvFileList.toString());
            System.out.println("FTP 파일 내역 : " + sendFileList.toString());
            for(ChannelSftp.LsEntry entry : sendFileList)
            {
                String ftpFileName = entry.getFilename();
                if(!ftpFileName.equals(".") && !ftpFileName.equals("..") && ftpFileName.indexOf(".") != 0)
                {
                    if(vRecvFileList.contains(ftpFileName) == false)
                    {
                        Map<String, Object> hmFileBasicInfo = new HashMap<String, Object>();
                        int nFileLineCnt = 0;

                        System.out.println("없는 파일이니 파일을 쓰겠습니다.");
                        StringBuffer sb = new StringBuffer();
                        ftpIn = channelSftp.get(ftpFileName);
                        ftpIsr = new InputStreamReader(ftpIn, "EUC-KR");
                        ftpBr = new BufferedReader(ftpIsr);

                        System.out.println("파일의 경로와이름은 : " + sRecvFileDirectoryPath + ftpFileName);
                        //ftpOut = new FileOutputStream(new File(sRecvFileDirectoryPath + ftpFileName));
                        //ftpOsw = new OutputStreamWriter(ftpOut);
                        //ftpBw = new BufferedWriter(ftpOsw);

                        String ftpLine = "";
                        System.out.println("파일을 쓰고 있습니다.");

                        while ((ftpLine = ftpBr.readLine()) != null)
                        {
                            //ftpBw.write(ftpLine);
                            //ftpBw.newLine();
                            sb.append(String.format("%s", ftpLine));
                            sb.append("\n");
                            nFileLineCnt++;
                        }

                        hmFileBasicInfo.put("req_month", stt_month);
                        hmFileBasicInfo.put("date", ftpFileName.substring(19,29).replaceAll("_", ""));
                        hmFileBasicInfo.put("file_name", ftpFileName);
                        hmFileBasicInfo.put("count", nFileLineCnt - 1);
                        hmFileBasicInfo.put("rslt_yn", "미반영");
                        hmFileBasicInfo.put("pay_mthd_auth", "Y");
                        DlwPayService.insertCardRecvFileList(hmFileBasicInfo);

                        CommonUtils.writeTextFile(sRecvFileDirectoryPath + ftpFileName, sb.toString(), "EUC-KR"); // MS949 / MS932 / UTF-8
                        System.out.println("파일을 모두 작성하였습니다.");
                        //ftpBw.flush();
                    }
                    else
                    {
                        System.out.println("이미 존재하는 파일입니다. 다운로드를 하지 않습니다.");
                    }
                }
                else
                {
                    System.out.println("기준에 맞지 않는데이터 입니다.");
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            String exMessage = e.getMessage();
            System.out.println("에러메시지 ::: " + exMessage);
            if(exMessage.equals("No such file"))
            {
                szErrorMsg = "조회된 데이터가 없습니다.";
            }
            else
            {
                szErrorMsg = exMessage;
            }
        }
        finally
        {
            if(ftpBr != null)
            {
                ftpBr.close();
            }
            if(ftpIsr != null)
            {
                ftpIsr.close();
            }
            if(ftpIn != null)
            {
                ftpIn.close();
            }
            if(channelSftp != null)
            {
                channelSftp.quit();
            }
        }

        /* 본서버에 있는 파일을 읽어옴 */
        //File files = new File(sRecvFileDirectoryPath);
        //String[] aRecvFiles = files.list();

        try
        {
            hmParam.put("req_month", stt_month);
            hmParam.put("pay_mthd_auth", "Y");
            //hmParam.put("pay_mthd", "06");
            //hmParam.put("pay_kind", "01");

            mList = DlwPayService.getCardRecvFileList(hmParam);


            for(int idx = 0; idx < mList.size(); idx++)
            {
                Map<String,Object> rowData = mList.get(idx);
                String sFileMakeDay = (String)rowData.get("date1");
                String sRsltYn = (String)rowData.get("rslt_yn");

                if(sRsltYn.equals("미반영"))
                {
                    if(!sFileMakeDay.equals(stt_dt))
                    {
                        rowData.put("rslt_yn", "반영불가");
                    }
                }

                mList1.add(rowData);
            }
            /*
            for(int idx = 0; idx < aRecvFiles.length; idx++)
            {
                String fileName = aRecvFiles[idx];
                String fileMakeDate = fileName.substring(19,29).replaceAll("_", "");

                Map<String,Object> rowData = new HashMap<String, Object>();
                reader  = new LineNumberReader(new FileReader(sRecvFileDirectoryPath + fileName));
                int lineCnt = 0;
                String readLine = "";

                while ((readLine = reader.readLine()) != null)
                {

                }

                lineCnt = reader.getLineNumber() - 1;

                reader.close();

                rowData.put("file_name", fileName);
                rowData.put("count", lineCnt);
                rowData.put("date", fileMakeDate);

                hmParam.put("req_day", fileMakeDate);
                hmParam.put("pay_mthd", "06");
                hmParam.put("pay_kind", "01");

                int isFileExistCnt = DlwPayService.getWdrwResultCount(hmParam);

                if(isFileExistCnt <= 0)
                {
                    if(stt_dt.equals(fileMakeDate))
                    {
                        rowData.put("rslt_yn", "미반영");
                    }
                    else
                    {
                        rowData.put("rslt_yn", "반영불가");
                    }
                }
                else
                {
                    rowData.put("rslt_yn", "반영완료");
                }

                if(lineCnt >= 1)
                {
                    mList.add(rowData);
                }
            }
            */
        }
        catch(Exception ioe)
        {
            ioe.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = ioe.getMessage();
        }
        finally
        {
            if(reader != null)
            {
                reader.close();
            }
        }

        System.out.println(mList1.toString());
        listMap.setRowMaps(mList1);
        mapOutDs.put("ds_output", listMap);

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 대명스테이션 Card File List(카드 무승인) 수신리스트 다운로드 및 리스트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     * 접속정보 >  ip:121.133.126.8  port:22
     * 계정&비번 > dmlife000g / eoaudqlffld1!
     */

    @RequestMapping(value = "/recvCardNauthFileList")
    public ModelAndView recvCardNauthFileList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> mapInVar = xpDto.getInVariableMap();     // 화면에서 받은 값
        Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();   // 화면에서 보내온 DS
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();   // 화면에 보낼 값
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap(); // 화면에 보낼 DS

        Map<String, Object> hmParam = new HashMap<String, Object>(); // DS에서 한건씩 꺼내서 쓸 맵
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> mList1 = new ArrayList<Map<String, Object>>();

        // 현재 날짜
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        // 조회월을 받음.
        String stt_month = (String) mapInVar.get("stt_month").toString().substring(0,6);
        String stt_dt    = (String) mapInVar.get("stt_dt").toString();
        String reqCount  = (String) mapInVar.get("req_count").toString();
        int iReqCount     = Integer.parseInt(reqCount);
        
        hmParam.put("req_month", stt_month);
        hmParam.put("pay_mthd_auth", "N");
        System.out.println("송신월 ::: " + stt_month + " 산출기준일 ::: " + stt_dt);

        //String sRecvFileDirectoryPath = "C://app/CARD/NICENAUTH/RECV/"; // 로컬에서 테스트 수행시
        String sRecvFileDirectoryPath = "/sftp_home/nicevan/recv/"; //서버에서 수행시

        /*
        InputStream ftpIn = null;
        InputStreamReader ftpIsr= null;
        BufferedReader ftpBr = null;
        */
        RandomAccessFile rafile = null;
        
        File fileRecvFileDirectoryPath = new File(sRecvFileDirectoryPath);

        if(fileRecvFileDirectoryPath.exists() == false)
        {
            fileRecvFileDirectoryPath.mkdirs();
        }
        
        /* 이 부분에서 본서버(192.168.10.6X) 로 받아오지 않은 파일을 모두 받아옴 */
        try
        {
        	String[] arrRecvFileList = fileRecvFileDirectoryPath.list();
        	
        	for(int idx = 0; idx < arrRecvFileList.length; idx++)
        	{
        		String sFileName = arrRecvFileList[idx];

        		Map<String,Object> rowData = new HashMap<String, Object>();
        		rowData.put("req_month", stt_month);
                rowData.put("file_name", sFileName);
        		int iExist = DlwPayService.getCardNauthRecvFileExist(rowData);
        			
        		if(iExist <= 0)
        		{
        			rafile = new RandomAccessFile(sRecvFileDirectoryPath + sFileName, "r"); // r : 읽기, w : 쓰기, rw : 읽기,쓰기
        			long pos = rafile.length() - 2;
        				
        			while(true)
       				{
        				rafile.seek(pos);

        				if (rafile.readByte() == '\n')
        				{
        					break;
        				}
            			
        				pos--;
        			}
            		
        			rafile.seek(pos + 1);
            		
        			String sLastLine  = rafile.readLine();
        			String sReqConut  = sLastLine.substring(2, 8);
        			int    iReqConut  = Integer.parseInt(sReqConut);
        			
        			String recvMMDD = sFileName.substring(8, 12); // 해당파일의 월일
        			Integer recvYYYY = Integer.parseInt(mapInVar.get("stt_dt").toString().substring(0, 4)); // 해당파일의 년도
        			Integer recvNextYYYY = recvYYYY + 1; // 다음해의 년도
        			        			
        			String sRecvDt = mapInVar.get("stt_dt").toString().substring(0, 4) + sFileName.substring(8, 12);
                    Calendar calRecvDt = Calendar.getInstance();
                    SimpleDateFormat sdfRecvDt = new SimpleDateFormat("yyyyMMdd");
                    Date dateFileNameDay = sdfRecvDt.parse(sRecvDt);
                    calRecvDt.setTime(dateFileNameDay);
                    
                    calRecvDt.add(Calendar.DAY_OF_MONTH, -1);
                    String sRecvDay = sdfRecvDt.format(calRecvDt.getTime());

        			rafile.close();
        			
        			rowData.put("count", iReqConut);
        			rowData.put("date", sRecvDay);
        			rowData.put("pay_mthd_auth", "N");  
        			rowData.put("rslt_yn", "미반영");  
        			
        		    System.out.println(">>>>>>>>>>>>>>>>>>" + rowData);
        			
        			DlwPayService.insertCardRecvFileList(rowData);
        		}
        	}
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            String exMessage = e.getMessage();
            System.out.println("에러메시지 ::: " + exMessage);
        }
        finally
        {
        	if(rafile != null)
        	{
        		rafile.close();
        	}
        }
        
        try
        {
            mList = DlwPayService.getCardRecvFileList(hmParam);

            for(int idx = 0; idx < mList.size(); idx++)
            {
                Map<String,Object> rowData = mList.get(idx);
                String sFileMakeDay = (String)rowData.get("date1");
                
                int iRecvCount = Integer.parseInt(rowData.get("count").toString());
                String sRsltYn = (String)rowData.get("rslt_yn");
                //String sTargetDate = (String)rowData.get("target_date");

                if(sRsltYn.equals("미반영"))
                {
                	/*
                    if( (iRecvCount != iReqCount) || !stt_dt.equals(sTargetDate) )
                    {
                        rowData.put("rslt_yn", "반영불가");
                    }
                    */
                	
                	if( (iRecvCount != iReqCount) || !stt_dt.equals(sFileMakeDay) )
                    {
                        rowData.put("rslt_yn", "반영불가");
                    }
                }

                mList1.add(rowData);
            }
        }
        catch(Exception ioe)
        {
            ioe.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = ioe.getMessage();
        }
        finally
        {
        	
        }

        listMap.setRowMaps(mList1);
        mapOutDs.put("ds_output", listMap);

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명스테이션 NICEPAYMENT FTP File 전송리스트 및 수신리스트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     * 접속정보 >  ip:121.133.126.8  port:22
     * 계정&비번 > dmlife000g / eoaudqlffld1! << 패스워드는 3달에 한번씩 변경된다.
     */
    @RequestMapping(value = "/insertCardFileList")
    public ModelAndView insertAcquResultData(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        /**********************************************************************************
         * 기본적인 변수들 선언
         * ********************************************************************************/
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> mapInVar = xpDto.getInVariableMap();     // 화면에서 받은 값
        Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();   // 화면에서 보내온 DS
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();   // 화면에 보낼 값
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap(); // 화면에 보낼 DS

        Map<String, Object> hmParam = new HashMap<String, Object>(); // DS에서 한건씩 꺼내서 쓸 맵
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        // 현재 날짜
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        // 조회월을 받음.
        String sSearchAuthDt = (String) mapInVar.get("auth_dt").toString();
        String sFileName = (String) mapInVar.get("file_name").toString();
        hmParam.put("req_day", sSearchAuthDt);
        hmParam.put("pay_mthd", "06");
        hmParam.put("auth_yn", "Y");
        hmParam.put("pay_mthd_auth", "Y");
        hmParam.put("pay_kind", "01");
        
        //System.out.println("승인날짜 ::: " + sSearchAuthDt + ", 파일이름 ::: " + sFileName);

        //String sRecvFileDirectoryPath = "C:/app/dmlife000g/billrslt/" + sSearchAuthDt.substring(0, 6) + "/"; // 로컬에서 테스트 수행시
        String sRecvFileDirectoryPath = "/app/dmlife000g/billrslt/" + sSearchAuthDt.substring(0, 6) + "/"; // 서버에서 수행시

        File files = new File(sRecvFileDirectoryPath, sFileName);
        //FileReader fr = null;
        //BufferedReader br = null;

        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null ;

        String sAccessClassName = EgovProperties.getProperty("Globals.Fix.Database.Access.DriverClassName");
        String sAccessIp        = EgovProperties.getProperty("Globals.Fix.Database.Access.Ip");
        String sAccessUserName  = EgovProperties.getProperty("Globals.Fix.Database.Access.UserName");
        String sAccessPassword  = EgovProperties.getProperty("Globals.Fix.Database.Access.Password");

         /*System.out.println("프로퍼티[1] :: " + sAccessClassName + "," + "프로퍼티[2] :: " + sAccessIp + "," +
                            "프로퍼티[3] :: " + sAccessUserName + "," + "프로퍼티[4] :: " + sAccessPassword);*/

        String sqlStatement = "INSERT INTO LF_DMUSER.TB_MEMBER_WDRW_RESULT(ACCNT_NO, REQ_DAY, REQ_NO, PAY_KIND,";
        sqlStatement += "RESULT_KEY, RESULT_CD, RESULT_MSG, PAY_MTHD, AUTH_DT, AUTH_CD, ICHAE_CD, ETC, REG_DM,REG_MAN, TID, AUTH_YN)";
        sqlStatement += "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,'CARD_AUTO',?, 'Y')";

        int iDataIdx = 0;
        Class.forName(sAccessClassName);
        connection = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement(sqlStatement) ;

        try
        {
            int isFileExist = DlwPayService.getWdrwResultCount(hmParam);
            if(isFileExist <= 0)
            {
                is  = new FileInputStream(files);
                isr = new InputStreamReader(is, "EUC-KR");
                br  = new BufferedReader(isr);

                String sLine = "";

                while ((sLine = br.readLine()) != null)
                {
                    //System.out.println("라인 내용 ::: " + sLine);
                    //String sRecordGb = sLine.substring(0, 2);
                    sLine = sLine.replaceAll("\"", "");
                    String[] parseLine = sLine.split(",");

                    if(iDataIdx == 0)
                    {

                    }
                    else
                    {
                        Map<String,Object> rowDataDtl = new HashMap<String, Object>();

                        ParamUtils.addSaveParam(rowDataDtl);

                        String sAccntNo   = parseLine[7];                     // 회원번호 :: 7
                        String sReqDay    = parseLine[5].replaceAll("/", ""); // 청구/납입일자 :: 3
                        String sReqNo     = "1";                               // 납입회차 :: 파트장님 문의 (일단 1로 설정 함)
                        String sPayKind   = "01";                             // 01:파일, 02:실시간, 03:자유, 04:기타 (이 로직은 파일로 인한 반영임.)
                        String sTid = parseLine[2];                     // TID :: 2
                        String sResultCd  = parseLine[11];                    // 결과코드 :: 11
                        String sResultMsg = parseLine[12];                    // 결과메시지 :: 12
                        String sPayMthd   = "06";                             // 04:CMS, 06:Card (이로직은 카드로 인한 반영임.)
                        String sAuthDt    = parseLine[5].replaceAll("-", ""); // 승인일자 :: 5
                        String sAuthCd    = parseLine[6];                     // 승인번호 :: 6
                        String sIchaeCd   = parseLine[14];                    // 매입사 :: 14
                        String sResultKey   = ("0000000" + parseLine[16]).substring(("0000000" + parseLine[16]).length() -8,("0000000" + parseLine[16]).length());                    // 결과키 :: 16
                        String sEtc       = "";                               // 결과이슈사항(통장기재내역등)

                        preparedStatement.setString(1, sAccntNo.trim());
                        preparedStatement.setString(2, sReqDay.trim());
                        preparedStatement.setString(3, sReqNo.trim());
                        preparedStatement.setString(4, sPayKind.trim());
                        preparedStatement.setString(5, sResultKey.trim());
                        preparedStatement.setString(6, sResultCd.trim());
                        preparedStatement.setString(7, sResultMsg.trim());
                        preparedStatement.setString(8, sPayMthd.trim());
                        preparedStatement.setString(9, sAuthDt.trim());
                        preparedStatement.setString(10, sAuthCd.trim());
                        preparedStatement.setString(11, sIchaeCd.trim());
                        preparedStatement.setString(12, sEtc.trim());
                        preparedStatement.setString(13, sTid.trim());

                        //System.out.println("sReqDay ::: " + parseLine[5]);

                        // addBatch에 담기
                        preparedStatement.addBatch();

                        // 파라미터 Clear
                        preparedStatement.clearParameters() ;


                        // OutOfMemory를 고려하여 만건 단위로 커밋
                        if( (iDataIdx % 10000) == 0)
                        {
                            // Batch 실행
                            preparedStatement.executeBatch();

                            // Batch 초기화
                            preparedStatement.clearBatch();

                            // 커밋
                            connection.commit();
                        }
                    }
                    iDataIdx++;
                }

                preparedStatement.executeBatch() ;
                connection.commit() ;

                br.close();

                DlwPayService.updateCardRecvFileList(hmParam);
            }
            else
            {
                szErrorCode = "2";
                szErrorMsg = "이미 산출되어있는 수수료데이터 입니다. 중복산출은 불가능합니다.";
            }
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
        }
        catch(SQLException sqle)
        {
            if(connection != null)
            {
                connection.rollback() ;
            }
            szErrorCode = "-1";
            szErrorMsg  = sqle.getMessage();
            //System.out.println("iDataIdx :::: " + iDataIdx);
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = ioe.getMessage();
        }
        finally
        {
            if(preparedStatement != null)
            {
                preparedStatement.close();
            }
            if(connection != null)
            {
                connection.close();
            }
            if(br != null)
            {
                br.close();
            }
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 대명스테이션 NICEPAYMENT FTP File 전송리스트 및 수신리스트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     * 접속정보 >  ip:121.133.126.8  port:22
     * 계정&비번 > dmlife000g / eoaudqlffld1! << 패스워드는 3달에 한번씩 변경된다.
     */
    @RequestMapping(value = "/insertCardNauthFileList")
    public ModelAndView insertCardNauthFileList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        /**********************************************************************************
         * 기본적인 변수들 선언
         * ********************************************************************************/
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> mapInVar = xpDto.getInVariableMap();     // 화면에서 받은 값
        Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();   // 화면에서 보내온 DS
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();   // 화면에 보낼 값
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap(); // 화면에 보낼 DS

        Map<String, Object> hmParam = new HashMap<String, Object>(); // DS에서 한건씩 꺼내서 쓸 맵
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        // 현재 날짜
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        // 조회월을 받음.
        String sSearchAuthDt = (String) mapInVar.get("auth_dt").toString();
        String sFileName = (String) mapInVar.get("file_name").toString();
        hmParam.put("req_day", sSearchAuthDt);
        hmParam.put("pay_mthd", "06");
        hmParam.put("auth_yn", "N");
        hmParam.put("pay_kind", "01");
        hmParam.put("pay_mthd_auth", "N");
        
        //System.out.println("승인날짜 ::: " + sSearchAuthDt + ", 파일이름 ::: " + sFileName);

        //String sRecvFileDirectoryPath = "c://app/CARD/NICENAUTH/RECV/"; // 로컬에서 테스트 수행시
        String sRecvFileDirectoryPath = "/sftp_home/nicevan/recv/"; // 서버에서 수행시

        File files = new File(sRecvFileDirectoryPath, sFileName);
        //FileReader fr = null;
        //BufferedReader br = null;

        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null ;

        String sAccessClassName = EgovProperties.getProperty("Globals.Fix.Database.Access.DriverClassName");
        String sAccessIp        = EgovProperties.getProperty("Globals.Fix.Database.Access.Ip");
        String sAccessUserName  = EgovProperties.getProperty("Globals.Fix.Database.Access.UserName");
        String sAccessPassword  = EgovProperties.getProperty("Globals.Fix.Database.Access.Password");

         /*System.out.println("프로퍼티[1] :: " + sAccessClassName + "," + "프로퍼티[2] :: " + sAccessIp + "," +
                            "프로퍼티[3] :: " + sAccessUserName + "," + "프로퍼티[4] :: " + sAccessPassword);*/

        String sqlStatement = "INSERT INTO LF_DMUSER.TB_MEMBER_WDRW_RESULT(ACCNT_NO, REQ_DAY, REQ_NO, PAY_KIND, RESULT_KEY, RESULT_CD, RESULT_MSG, ";
        sqlStatement += "PAY_MTHD, AUTH_DT, AUTH_CD, ICHAE_CD, ETC, REG_DM,REG_MAN, TID, AUTH_YN, BAS_CMIS_AMT, FRC_CMIS_AMT, ETC_CMIS_AMT, CUR_PAY_AMT, CANCEL_YN)";
        sqlStatement += "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,'CARD_AUTO',?, 'N', TO_NUMBER(?), TO_NUMBER(?), TO_NUMBER(?), TO_NUMBER(?),?)";

        int iDataIdx = 0;
        Class.forName(sAccessClassName);
        connection = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement(sqlStatement) ;

        try
        {
            int isFileExist = DlwPayService.getWdrwResultCount(hmParam);
            if(isFileExist <= 0)
            {
                is  = new FileInputStream(files);
                isr = new InputStreamReader(is, "EUC-KR");
                br  = new BufferedReader(isr);

                String sLine = "";

                while ((sLine = br.readLine()) != null)
                {
                    String sRecordGbn = sLine.substring(0, 2);
                    
                    if(sRecordGbn.equals("60") || sRecordGbn.equals("61") || sRecordGbn.equals("62") || sRecordGbn.equals("63") || 
                       sRecordGbn.equals("64") || sRecordGbn.equals("65") || sRecordGbn.equals("66") || sRecordGbn.equals("67"))
                    {
                    	String sAccntNo   = sLine.substring(90, 102);      // 회원번호
                        String sReqDay    = "20" + sLine.substring(12, 18); // 청구/납입일자
                        String sReqNo     = "1";                           // 납입회차
                        String sPayKind   = "01";                          // 01:파일, 02:실시간, 03:자유, 04:기타 (이 로직은 파일로 인한 반영임.)
                        String sTid       = "00000000";                    // TID
                        String sResultCd  = sLine.substring(53, 55);       // 결과코드
                        String sResultMsg = "";                            // 결과메시지
                        String sPayMthd   = "06";                          // 04:CMS, 06:Card (이로직은 카드로 인한 반영임.)
                        String sAuthDt    = "20" + sLine.substring(12, 18);       // 승인일자
                        String sAuthCd    = sLine.substring(138, 148);     // 승인번호
                        String sIchaeCd   = "";                            // 매입사
                        //String sResultKey   = ("0000000" + sLine.substring(90, 98)).substring(("0000000" + sLine.substring(90, 98)).length() -8,("0000000" + sLine.substring(90, 98)).length()); // 결과키
                        String sResultKey = sLine.substring(102, 112); 			// 결과키
                        String sEtc       = "";                            // 결과이슈사항(통장기재내역등)
                        String sBasCmisAmt = sLine.substring(78, 90);
                        String sFrcCmisAmt = "0";
                        String sEtcCmisAmt = sLine.substring(68, 78);
                        String sCurPayAmt  = sLine.substring(151, 161);
                        String sCancelYn = "N";										// 결과 값이 취소인지 여부값 
                        if (sRecordGbn.equals("67")){
                        	sCancelYn = "Y";
                        }
                         
                        preparedStatement.setString(1, sAccntNo.trim());
                        preparedStatement.setString(2, sReqDay.trim());
                        preparedStatement.setString(3, sReqNo.trim());
                        preparedStatement.setString(4, sPayKind.trim());
                        preparedStatement.setString(5, sResultKey.trim());
                        preparedStatement.setString(6, sResultCd.trim());
                        preparedStatement.setString(7, sResultMsg.trim());
                        preparedStatement.setString(8, sPayMthd.trim());
                        preparedStatement.setString(9, sAuthDt.trim());
                        preparedStatement.setString(10, sAuthCd.trim());
                        preparedStatement.setString(11, sIchaeCd.trim());
                        preparedStatement.setString(12, sEtc.trim());
                        preparedStatement.setString(13, sTid.trim());
                        preparedStatement.setString(14, sBasCmisAmt.trim());
                        preparedStatement.setString(15, sFrcCmisAmt.trim());
                        preparedStatement.setString(16, sEtcCmisAmt.trim());
                        preparedStatement.setString(17, sCurPayAmt.trim());
                        preparedStatement.setString(18, sCancelYn.trim());
                        
                        // addBatch에 담기
                        preparedStatement.addBatch();

                        // 파라미터 Clear
                        preparedStatement.clearParameters() ;


                        // OutOfMemory를 고려하여 만건 단위로 커밋
                        if( (iDataIdx % 10000) == 0)
                        {
                            // Batch 실행
                            preparedStatement.executeBatch();

                            // Batch 초기화
                            preparedStatement.clearBatch();

                            // 커밋
                            connection.commit();
                        }
                    }
                    iDataIdx++;
                }

                preparedStatement.executeBatch() ;
                connection.commit() ;

                br.close();
                
                DlwPayService.updateNauthResult(hmParam);
                DlwPayService.updateCardRecvFileList(hmParam);
            }
            else
            {
                szErrorCode = "2";
                szErrorMsg = "이미 산출되어있는 수수료데이터 입니다. 중복산출은 불가능합니다.";
            }
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
        }
        catch(SQLException sqle)
        {
            if(connection != null)
            {
                connection.rollback() ;
            }
            szErrorCode = "-1";
            szErrorMsg  = sqle.getMessage();
            //System.out.println("iDataIdx :::: " + iDataIdx);
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = ioe.getMessage();
        }
        finally
        {
            if(preparedStatement != null)
            {
                preparedStatement.close();
            }
            if(connection != null)
            {
                connection.close();
            }
            if(br != null)
            {
                br.close();
            }
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 카드결과로그 조회
     * 정승철
     * 20181029
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/nicepay-rltm-card-log/list")
    public ModelAndView getDlwRltmCardLogList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            String excel_fg = (String)mapInVar.get("excel_fg");
            hmParam.put("excel_fg", excel_fg);

            // 페이징 정보
            DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable"); 
            if (listInGds != null && listInGds.size() > 0) {
                Map pMap = listInGds.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            if (!"Y".equals(excel_fg)) {
                int nTotal = DlwPayService.getDlwRltmCardLogCount(hmParam);

                mapOutVar.put("tc_log", nTotal);
                List<Map<String, Object>> mList = DlwPayService.getDlwRltmCardLogList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);

                //2018.01.11 접속 로그////////////////////////////////////////////////////////////////////////////////
                /*DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

                if (listLogIn.size() > 0) {
                    hmParam = listLogIn.get(0);
                    commonService.insertLog(hmParam);
                }*/
                //////////////////////////////////////////////////////////////////////////////////////////////////////
            }
            listMap2.setRowMaps(hmParam);
            mapOutDs.put("ds_output_excel", listMap2);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }


    /**
     * 자유 결제 (실시간 카드) (NEW)
     * 정승철
     * 20181031
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/free-realtime")
    public ModelAndView Req_FreeRealtimeWdrw(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            Map<String, Object> hmParam = new HashMap<String, Object>();
            Map<String, Object> resultMap = new HashMap<String, Object>();
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            //String menu_gbn = "0005"; // 자유
            String pmResultCd = "";
            String pmResultMsg = "";
            //hmParam.put("menu_gbn", menu_gbn);

            hmParam.put("pay_mthd", "06");  // 카드
            hmParam.put("pay_kind", "03");  // 자유 (01:파일, 02:실시간, 03:자유,04:기타)

            try
            {
                // 자유금액 결제시도
                resultMap = DlwPayService.billPayFreeProc(hmParam);

                String result_cd = (String)resultMap.get("result_cd");
                String result_msg = (String)resultMap.get("result_msg");
                String tid = (String)resultMap.get("tid");
                String auth_dt = (String)resultMap.get("auth_dt");
                String auth_cd = (String)resultMap.get("auth_cd");
                String card_cd = (String)resultMap.get("card_cd");

                hmParam.put("result_key", tid);
                hmParam.put("result_cd", result_cd);
                hmParam.put("result_msg", result_msg);
                hmParam.put("auth_cd", auth_cd);
                hmParam.put("auth_dt", auth_dt);
                hmParam.put("card_cd", card_cd);

                System.out.println("result_cdxxxxxxxxxxxxxxxxxxxxxxxxx" + result_cd);
                System.out.println("result_cdxxxxxxxxxxxxxxxxxxxxxxxxx" + result_msg);

                if("3001".equals(result_cd))
                {
                    hmParam.put("del_fg", "C");
                    pmResultCd = "00";

                    DlwPayService.insertFreeCardResult(hmParam);
                }
                else
                {
                    hmParam.put("del_fg", "F");
                    pmResultCd = "01";
                }

                resultMap.put("result_msg", (new StringBuilder(String.valueOf(result_cd))).append(" : ").append(result_msg).toString());
                resultMap.put("result_cd", result_cd);
                pmResultMsg = result_msg;

                //System.out.println("2. 자유결제 파라미터 맵 : " + hmParam);
                //System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::");
            }
            catch(Exception e)
            {
                resultMap.put("result_cd", "01");
                resultMap.put("result_msg", "[Exception]개발자문의");
                pmResultCd = "01";
                pmResultMsg = (String)resultMap.get("result_msg");
            }

            mapOutVar.put("result_cd", pmResultCd);
            mapOutVar.put("result_msg", pmResultMsg);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 두구좌 상품관리 현황
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getDoubleAccntList")
    public ModelAndView getDoubleAccntList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMapCard = new DataSetMap();
        DataSetMap listMapCMS = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                int nTotal = DlwPayService.getDoubleAccntListCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = DlwPayService.getDoubleAccntList(hmParam);

                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 입금 완료 된 실시간카드결제 내역을 취소한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cancel-realtime")
    public ModelAndView updateRealtimeCardCancel(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            hmParam.put("paramEmpleNo", hmParam.get("rgsr_id"));
            hmParam.put("paramAs", "EMP");

            hmParam.put("pay_kind", "02");  // 자유 (01:파일, 02:실시간, 03:자유,04:기타)

            Map <String, Object> result = new HashMap();
            result = DlwPayService.updateRealtimeCardCancel(hmParam);

            if(result.get("result_code").equals("03")){
                //실시간 결제 관련 상담 등록
               // saveConsDlwRtCard(result);
            }

            mapOutVar.put("rslt_cd", result.get("result_code"));
            mapOutVar.put("rslt_nm", result.get("result_msg"));

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 상담 정보를 등록/수정한다. (대명 - 카드결제 시)
     *
     * @param pmParam Map<String, Object>
     * @return pmParam Map<String, Object>
     * @throws Exception
     */
    public Map <String, Object> saveConsDlwRtCard( Map <String, Object> pmParam) throws Exception {
        String cnslMngCon = "[실시간] 카드 결제";
        String cnslDtlCon = "";

        String type = (String)pmParam.get("type");

        if ("card".equals((String)pmParam.get("type"))) {
            cnslDtlCon = (new StringBuilder("[실시간]카드결제일 : ")).append((String)pmParam.get("pay_day")).append("  카드번호 : ").append((String)pmParam.get("card_no")).append("  결제금액 : ").append((String)pmParam.get("pay_amt")).toString();
        } else if ("card_fail".equals(type)){
            cnslMngCon = (new StringBuilder(String.valueOf(cnslMngCon))).append(" 실패").toString();
        } else if ("card_err".equals(type)) {
            cnslMngCon = (new StringBuilder(String.valueOf(cnslMngCon))).append(" 오류").toString();
        } else if ("cancel".equals(type)) {
            cnslMngCon = (new StringBuilder(String.valueOf(cnslMngCon))).append(" 취소").toString();
        } else if("cancel_fail".equals(type)) {
            cnslMngCon = (new StringBuilder(String.valueOf(cnslMngCon))).append(" 취소 실패").toString();
        } else if("cancel_err".equals(type)) {
            cnslMngCon = (new StringBuilder(String.valueOf(cnslMngCon))).append(" 취소 오류").toString();
        } else {
            cnslMngCon = (new StringBuilder(String.valueOf(cnslMngCon))).append(" 기타 오류").toString();
        }

        pmParam.put("cons_memo", cnslMngCon);
        pmParam.put("cnsl_con", cnslDtlCon);

        consController.saveConsdlw(pmParam);

        return pmParam;
    }

    /**
     * 회원고객정보 탭 (청구이력)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/srchWdrwLog")
    public ModelAndView getDlwAskHstrList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));

            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                return modelAndView;
            }

            hmParam.put("accnt_no", sAccntNo);
            mList = DlwPayService.getMainWdrwLogList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }


    /**
     * 입금 완료 된 결제 내역을 취소한다.
     * 정승철
     * 20181114
     */
    @RequestMapping(value = "/cancel-pay")
    public ModelAndView updatePayCancel(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            hmParam.put("paramEmpleNo", hmParam.get("rgsr_id"));
            hmParam.put("paramAs", "EMP");

            DlwPayService.updatePayCancel(hmParam);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 카드 무승인 취소시 취소대상 등록
     * 20191209
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cancel-nauthpay")
    public ModelAndView insertNauthPayCancel(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));

            DlwPayService.insertNauthPayCancel(hmParam);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 카드 무승인 취소 리스트 조회
     * 20191209
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cancel-nauthpay-list")
    public ModelAndView getNauthPayCancel(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);

            List<Map<String, Object>> mList = DlwPayService.getNauthPayCancel(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 고객청구환불관리 리스트검색(메인화면 로직)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getMemberRefundList")
    public ModelAndView getGasuMngList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            // 페이징 정보
            DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInGds != null && listInGds.size() > 0) {
                Map pMap = listInGds.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            int nTotal = DlwPayService.getMemberRefundListCount(hmParam);

            mapOutVar.put("nTotalListCnt", nTotal);

            List<Map<String, Object>> mList = DlwPayService.getMemberRefundList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 고객-구좌 정보를 검색한다.(메인화면 로직)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getDlwCustAccntList")
    public ModelAndView getDlwCustAcntList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                String popup_yn = CommonUtils.checkNull((String)hmParam.get("popup_yn"));

                /* 임의등록에서 회원정보검색시 해피콜 조인구분 변수 추가 - 2018.02.13 */
                hmParam.put("hp_join_gb", mapInVar.get("hp_join_gb"));

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                Map<String, Object> mEmpl = DlwPayService.getEmplDtptList((String)hmParam.get("rgsr_id"));
                if (mEmpl != null) {
                    String sDeptCd = StringUtils.defaultString((String)mEmpl.get("dept_cd"));

                    if ("1602".equals(sDeptCd)) {
                        hmParam.put("ns_yn", "Y");
                    } else {
                        hmParam.put("ns_yn", "N");
                    }
                }

                UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();

                hmParam.put("paramEmpleNo", oUserSession.getUserid()); // emple_no
                hmParam.put("paramAs", "EMP");

                String dataAthrQury = DlwPayService.getDataAthrFunc(hmParam);
                hmParam.put("dataAthrQury", dataAthrQury);

                int nTotal = DlwPayService.getDlwCustAccntListCount(hmParam);
                mapOutVar.put("tc_custAcnt", nTotal);

                if ((!"N".equals(popup_yn) || ("N".equals(popup_yn) && nTotal == 1))) {
                    List<Map<String, Object>> mList = DlwPayService.getDlwCustAccntList(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명라이프웨이 청구환불 상세정보를 검색한다.(팝업화면)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getMemberRefundDetail")
    public ModelAndView getGasuDetailList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            hmParam.put("seq", mapInVar.get("seq"));

            List<Map<String, Object>> mList = DlwPayService.getMemberRefundList(hmParam);
            listMap.setRowMaps(mList);
            List<Map<String, Object>> mDtlList = DlwPayService.getMemberRefundDetail(hmParam);
            listMap2.setRowMaps(mDtlList);
            mapOutDs.put("ds_output", listMap);
            mapOutDs.put("ds_outputDtl", listMap2);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명라이프웨이 청구환불 환불정보를 저장한다.(팝업화면)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mergeMemberRefundDtl")
    public ModelAndView mergeGasuDetail(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                String mode = (String)hmParam.get("mode");

                System.out.println("mode :: " + mode);
                if ("update".equals(mode)) {
                    DlwPayService.updateMemberRefundDtl(hmParam);
                } else {
                    DlwPayService.insertMemberRefundDtl(hmParam);
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명라이프웨이 청구환불 환불정보를 삭제한다.(팝업화면)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/deleteMemberRefundDtl")
    public ModelAndView deleteGasuDetail(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            hmParam.put("gasu_seq", mapInVar.get("gasu_seq"));
            hmParam.put("seq", mapInVar.get("seq"));

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            DlwPayService.deleteMemberRefundDtl(hmParam);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명라이프웨이 청구환불 환불정보를 저장한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mergeMemberRefundMng")
    public ModelAndView mergeGasuMng(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                String mode = (String)hmParam.get("mode");

                System.out.println("mode : " + mode);

                if ("update".equals(mode)) {
                    //DlwPayService.updateMemberRefundMng(hmParam);
                } else {
                    //DlwPayService.insertMemberRefundMng(hmParam);
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명라이프웨이 청구환불 환불정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/deleteMemberRefundMng")
    public ModelAndView deleteGasuMng(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                DlwPayService.deleteMemberRefundMng(hmParam);

            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 코드 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getDlwCodeList")
    public ModelAndView getDlwCdList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            int nTotal = DlwPayService.getDlwCodeListCount(hmParam);

            String svcid = (String) mapInVar.get("svcid");

            if("selectBankList".equals(svcid)) {
                mapOutVar.put("tc_bank", nTotal);
            } else if ("selectCremationList".equals(svcid)) {
                mapOutVar.put("tc_cremation", nTotal);
            } else if ("selectPurList".equals(svcid)) {
                mapOutVar.put("tc_pur", nTotal);
            }

            List<Map<String, Object>> mList = DlwPayService.getDlwCodeList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 회원의 환불회차정보를 검색한다.
     * 정승철
     * 20181127
     */
    @RequestMapping(value = "/getRefundReqNoOfAccnt")
    public ModelAndView getRefundReqNoOfAccnt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                List<Map<String, Object>> mList = DlwPayService.getRefundReqNoOfAccnt(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * (CMS) 환불반영
     * 정승철
     * 20181127
     */
    @RequestMapping(value = "/updateByCmsRefundProcess")
    public ModelAndView updatePayByCmsRefundProcess(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));

                DlwPayService.updateByCmsRefundProcess(hmParam);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 카드파일 다운로드
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/carddownloadFile")
    public void carddownloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = null;
        Map<String, Object> mRow = null;
        String upload_cl = "";
        String file_nm = "";
        String reg_man = "";
        String reg_dm = "";

        try {
            String yymmdd = CommonUtils.nvls(request.getParameter("yymmdd"));
            hmParam = new HashMap<>();
            hmParam.put("yymmdd", yymmdd);

            String  successPath	= "";

            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("windows") >= 0) {
                successPath = EgovProperties.getProperty("card.down.file.windows.successPath");
            } else {
                successPath = EgovProperties.getProperty("card.down.file.unix.successPath");
            }

            List<Map<String, Object>> mList = DlwPayService.getCardSendMakeFileDt(hmParam);
            String sExtWriteCardDt = (String)mList.get(0).get("ext_write_card");

            String srcFilePath = successPath +"dmlife000g_"+ sExtWriteCardDt +".REQ";

            if (osName.indexOf("windows") >= 0) {
                srcFilePath = srcFilePath.replaceAll("/", "\\\\");
            }


            System.out.println(srcFilePath);

        //    log.debug("srcFilePath : " + srcFilePath);

            File xDownFile = new File(srcFilePath);
            if (!xDownFile.exists()) {
                throw new EgovBizException("디스크에 실제 파일이 존재하지 않습니다.");
            }

            String contentType = request.getContentType();


            System.out.println("contentType : " + contentType);
  //          log.debug("contentType : " + contentType);

            if (contentType == null) {
                if (request.getHeader("user-agent").indexOf("MSIE 5.5") != -1)
                    response.setContentType("doesn/matter;");
                else
                    response.setContentType("application/octet-stream");
            } else {
                response.setContentType(contentType);
            }

            response.setHeader("Content-Transfer-Encoding:", "binary");

            // String sDisplayFileName = "상품목록.xlsx";
            // response.setHeader("Content-Disposition", "attachment;filename=" + sDisplayFileName + ";");

            response.setHeader("Content-Length", "" + xDownFile.length());
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");

            byte b[] = new byte[1024*4];
            BufferedInputStream fin = new BufferedInputStream(new FileInputStream(xDownFile));
            BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

            try{
                int read = 0;
                while ((read = fin.read(b)) != -1)
                {
                    outs.write(b,0,read);
                }

            }catch (Exception e){
            }finally{
                if (outs!=null) outs.close();
                if (fin!=null) fin.close();
            }

            outs.flush();
            outs.close();

        } catch (Exception e) {
            e.printStackTrace();
            ServletOutputStream os = response.getOutputStream();
            os.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            os.println("<Root xmlns=\"http://www.tobesoft.com/platform/dataset\" ver=\"5000\">");
            os.println("<Parameters>");
            os.println("<Parameter id=\"ErrorCode\" type=\"string\">-1</Parameter>");
            os.println("<Parameter id=\"ErrorMsg\" type=\"string\">" + e.getMessage() + "</Parameter>");
            os.println("</Parameters>");
            os.println("</Root>");
            os.close();

        }
    }
    
    /**
     * 카드파일 다운로드
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/carddownloadNauthFile")
    public void carddownloadNauthFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = null;
        Map<String, Object> mRow = null;
        String upload_cl = "";
        String file_nm = "";
        String reg_man = "";
        String reg_dm = "";

        try {
            String yymmdd = CommonUtils.nvls(request.getParameter("yymmdd"));
            hmParam = new HashMap<>();
            hmParam.put("yymmdd", yymmdd);

            String  successPath	= "";

            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("windows") >= 0) {
                successPath = "C:/sftp_home/nicevan/send/";
            } else {
                successPath = "/sftp_home/nicevan/send/";
            }

            //List<Map<String, Object>> mList = DlwPayService.getCardNauthSendMakeFileDt(hmParam);
            //String sExtWriteCardDt = (String)mList.get(0).get("ext_write_card_nauth");

            //String srcFilePath = successPath + "DMSTATION_" + sExtWriteCardDt.substring(4, 8) + ".txt";
            String srcFilePath = successPath + "DMSTATION_" + yymmdd.substring(4, 8) + ".txt";

            if (osName.indexOf("windows") >= 0) {
                srcFilePath = srcFilePath.replaceAll("/", "\\\\");
            }


            System.out.println(srcFilePath);

        //    log.debug("srcFilePath : " + srcFilePath);

            File xDownFile = new File(srcFilePath);
            if (!xDownFile.exists()) {
                throw new EgovBizException("디스크에 실제 파일이 존재하지 않습니다.");
            }

            String contentType = request.getContentType();


            System.out.println("contentType : " + contentType);
  //          log.debug("contentType : " + contentType);

            if (contentType == null) {
                if (request.getHeader("user-agent").indexOf("MSIE 5.5") != -1)
                    response.setContentType("doesn/matter;");
                else
                    response.setContentType("application/octet-stream");
            } else {
                response.setContentType(contentType);
            }

            response.setHeader("Content-Transfer-Encoding:", "binary");

            // String sDisplayFileName = "상품목록.xlsx";
            // response.setHeader("Content-Disposition", "attachment;filename=" + sDisplayFileName + ";");

            response.setHeader("Content-Length", "" + xDownFile.length());
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");

            byte b[] = new byte[1024*4];
            BufferedInputStream fin = new BufferedInputStream(new FileInputStream(xDownFile));
            BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

            try{
                int read = 0;
                while ((read = fin.read(b)) != -1)
                {
                    outs.write(b,0,read);
                }

            }catch (Exception e){
            }finally{
                if (outs!=null) outs.close();
                if (fin!=null) fin.close();
            }

            outs.flush();
            outs.close();

        } catch (Exception e) {
            e.printStackTrace();
            ServletOutputStream os = response.getOutputStream();
            os.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            os.println("<Root xmlns=\"http://www.tobesoft.com/platform/dataset\" ver=\"5000\">");
            os.println("<Parameters>");
            os.println("<Parameter id=\"ErrorCode\" type=\"string\">-1</Parameter>");
            os.println("<Parameter id=\"ErrorMsg\" type=\"string\">" + e.getMessage() + "</Parameter>");
            os.println("</Parameters>");
            os.println("</Root>");
            os.close();

        }
    }

    /**
     * 일일청구기준데이터 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getMbidCsvFileList")
    public ModelAndView getMbidCsvFileList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                int nTotal = DlwPayService.getMbidCsvFileListCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = DlwPayService.getMbidCsvFileList(hmParam);
                listMap.setRowMaps(mList);

                mapOutDs.put("ds_output", listMap);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 일일청구 기준데이터 다운로드
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mbidDownLoadFile")
    public void mbidDownLoadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = new HashMap<String, Object>();

        try
        {
            String sExtDt    = CommonUtils.nvls(request.getParameter("ext_dt"));
            String sFileType = CommonUtils.nvls(request.getParameter("file_type"));
            String sFilePath = CommonUtils.nvls(request.getParameter("file_path"));

            hmParam.put("ext_dt", sExtDt);
            hmParam.put("file_type", sFileType);
            hmParam.put("file_path", sFilePath);
            ParamUtils.addSaveParam(hmParam);
            System.out.println(hmParam.toString());

            File xDownFile = new File(sFilePath);
            if (!xDownFile.exists())
            {
                throw new EgovBizException("디스크에 실제 파일이 존재하지 않습니다.");
            }

            String contentType = request.getContentType();

            System.out.println("contentType : " + contentType);

            if (contentType == null)
            {
                if (request.getHeader("user-agent").indexOf("MSIE 5.5") != -1)
                {
                    response.setContentType("doesn/matter;");
                }
                else
                {
                    response.setContentType("application/octet-stream");
                }
            }
            else
            {
                response.setContentType(contentType);
            }

            response.setHeader("Content-Transfer-Encoding:", "binary");
            response.setHeader("Content-Length", "" + xDownFile.length());
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");

            byte b[] = new byte[1024*4];
            BufferedInputStream fin = new BufferedInputStream(new FileInputStream(xDownFile));
            BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

            try
            {
                int read = 0;
                while ((read = fin.read(b)) != -1)
                {
                    outs.write(b,0,read);
                }

                DlwPayService.updateDownLoadEmplFileList(hmParam);
                DlwPayService.insertDownLoadEmplHist(hmParam);
            }
            catch (Exception e)
            {

            }
            finally
            {
                if (outs != null)
                {
                    outs.close();
                }
                if (fin != null)
                {
                    fin.close();
                }
            }

            outs.flush();
            outs.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ServletOutputStream os = response.getOutputStream();
            os.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            os.println("<Root xmlns=\"http://www.tobesoft.com/platform/dataset\" ver=\"5000\">");
            os.println("<Parameters>");
            os.println("<Parameter id=\"ErrorCode\" type=\"string\">-1</Parameter>");
            os.println("<Parameter id=\"ErrorMsg\" type=\"string\">" + e.getMessage() + "</Parameter>");
            os.println("</Parameters>");
            os.println("</Root>");
            os.close();

        }
    }

    /**
     * 일일청구기준데이터 처리일자 조회
     *
     * 20181213
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getCsvFileBatchDay")
    public ModelAndView getCsvFileBatchDay(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            List<Map<String, Object>> mList = DlwPayService.getCsvFileBatchDay(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }


    /**
     * 일일청구기준데이터 처리일자 등록
     *
     * 20181213
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insertCsvFileBatchDay")
    public ModelAndView insertCsvFileBatchDay(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();


            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            ParamUtils.addSaveParam(hmParam);
            hmParam.put("reg_man", hmParam.get("rgsr_id"));

            // 산출마감 입력유효성체크
            int chkExtReqDt = DlwPayService.validationInsertCsvFile(hmParam);

            if(chkExtReqDt == 0)
            {
                DlwPayService.insertCsvFileBatchDay(hmParam); // 산출마감체크 등록
            }
            else if(chkExtReqDt > 0)
            {
                mapOutVar.put("xInsertFailMsg", "해당 파일은 이미 등록되어 있습니다.");  // 실패 메시지 return
            }

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }


    /**
     * 일일청구기준데이터 처리일자 삭제
     *
     * 20181213
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/deleteCsvFileBatchDay")
    public ModelAndView deleteCsvFileBatchDay(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            // 산출마감체크 삭제
            DlwPayService.deleteCsvFileBatchDay(hmParam);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }

    /**
     * 대명스테이션 U+ 선납전송관리 산출 로직
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/prepayment/insertUplusPrepaymentData")
    public ModelAndView insertUplusPrepaymentData(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                
                int iCnt = DlwPayService.getPreMonthResultReflect(hmParam);
                
                if(iCnt <= 0)
                {
                	szErrorCode = "-1";
                    szErrorMsg = "이전달의 결과 반영이 완료되지 않았습니다. 이전달의 결과반영을 완료해 주십시오.";
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                    return modelAndView;
                }

                DlwPayService.updateInitUplusPrepaymentData(hmParam);
                DlwPayService.insertUplusPrepaymentData(hmParam);
            }
            
            /*
            List<Map<String, Object>> mHoldReleaseList = DlwPayService.getUplusHoldReleaseDataList(hmParam);
            
            String sHoldKbNo = "";
            String sTrueKbNo = "";
            String sHoldDtlNo = "";
            String sTrueDtlNo = "";
            int iHoldDtlNo = 0;
            int iTrueDtlNo = 0;
            Integer iDtlNoInsertIndex;

            for(int mIdx = 0; mIdx < mHoldReleaseList.size(); mIdx++)
            {
            	Map<String, Object> HoldReleaseListRow = new HashMap<String, Object>();
            	
            	HoldReleaseListRow = mHoldReleaseList.get(mIdx);
            	ParamUtils.addSaveParam(HoldReleaseListRow);
            	HoldReleaseListRow.put("upd_man", HoldReleaseListRow.get("rgsr_id"));
            	
            	sHoldKbNo = (String)HoldReleaseListRow.get("hold_kb_no");
            	sTrueKbNo = (String)HoldReleaseListRow.get("true_kb_no");
            	sHoldDtlNo = String.valueOf(HoldReleaseListRow.get("hold_dtl_no"));
            	sTrueDtlNo = String.valueOf(HoldReleaseListRow.get("true_dtl_no"));
            	iHoldDtlNo = Integer.parseInt(sHoldDtlNo) + 1;
            	iTrueDtlNo = Integer.parseInt(sTrueDtlNo) + 1;
            	
            	if(sHoldKbNo.equalsIgnoreCase(sTrueKbNo) == false)
            	{
            		for(int dIdx = iHoldDtlNo; dIdx < iTrueDtlNo; dIdx++)
            		{
            			iDtlNoInsertIndex = dIdx;
            			System.out.println("등록될 회차는 " + dIdx + " 회차 입니다.");
            			HoldReleaseListRow.put("dtl_no_insert", iDtlNoInsertIndex.toString());
            			DlwPayService.insertUplusHoldPrepaymentMemberCalc(HoldReleaseListRow); // 인서트 구문
            		}
            	}
            	DlwPayService.deleteUplusPrepayHoldDataList(HoldReleaseListRow);
            }
            */
            
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명스테이션 U+ 선납전송관리 산출 현황 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/prepayment/getUplusPrepaymentDataList")
    public ModelAndView getUplusPrepaymentDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                int nTotal = DlwPayService.getUplusPrepaymentDataListCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = DlwPayService.getUplusPrepaymentDataList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명스테이션 U+ 선납전송관리 산출 현황 FILE 만들기 및 전송
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/prepayment/sendUplusPrepaymentDataList")
    public ModelAndView sendUplusPrepaymentDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        Map<String, Object> mapInVar = xpDto.getInVariableMap();// 화면에서 받은 값
        Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap(); // 화면에서 보내온 DS
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();// 화면에 보낼 값
        DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
        Map<String, Object> hmParam = new HashMap<String, Object>(); // DS에서 한건씩 꺼내서 쓸 맵

        if(listInDs.size() > 0)
        {
            hmParam = listInDs.get(0);
        }

        // 현재 날짜
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");                                // 파일 날짜 여부 확인

        // 세션
        ParamUtils.addSaveParam(hmParam);

        // 저장할 파일 위치
        String sSendFileDirectoryPath = "/app/LGUPLUS/send/";                                   // 파일을 만들 파일 path 여부 확인
        File sendFileDirectory = new File(sSendFileDirectoryPath);
        //String pcDataDir ="/sftp_home/sftpuser/send/";

        File file = new File(sSendFileDirectoryPath + "ULIF_" + sdf.format(d) + ".rcv");        // 파일명 여부 확인

        if (file.exists())                                                                      // 파일 존재 여부 validation 여부 확인
        {
            szErrorCode = "-1";
            szErrorMsg = "현재날짜로 작성 및 전송한 파일이 존재합니다. (LGUPLUS 할인대상자 추출 파일은 하루에 한번만 전송할수 있습니다.";
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
            return modelAndView;
        }

        List<Map<String, Object>> sendUplusPrepaymentDataDataList = DlwPayService.getUplusPrepaymentDataListCalc(hmParam); // 파일을 만들 기준 데이터 여부 확인

        if(sendUplusPrepaymentDataDataList.size() > 0)
        {
            StringBuffer resultBuffer = new StringBuffer();
            Map<String, Object> insertMstData = new HashMap<String, Object>();

            insertMstData.put("calc_month", hmParam.get("stt_reg_dt"));                           // 헤더 정보 insert 데이터 여부 확인
            insertMstData.put("file_no", sdf.format(d));
            insertMstData.put("file_name", "ULIF_" + sdf.format(d) + ".rcv");
            insertMstData.put("type", "1");
            insertMstData.put("count", sendUplusPrepaymentDataDataList.size());
            insertMstData.put("returnval", "N");
            insertMstData.put("reg_man", hmParam.get("rgsr_id"));

            DlwPayService.insertUplusPrepaymentMst(insertMstData); // 파일 생성 정보 insert 여부 확인

            // Data부를 전문결과에 담는다.
            for(int idx = 0; idx < sendUplusPrepaymentDataDataList.size(); idx++)
            {
                String[] dtlArr = new String[5];                                                // 데이터 기준 여부 확인
                Map<String, Object> rowData = sendUplusPrepaymentDataDataList.get(idx);

                String sKbNo       = (String)rowData.get("kb_no");
                //String[] sCell     = (String)rowData.get("cell").split("-");
                //String sBrthMonDay = (String)rowData.get("brth_mon_day"); //sBrthMonDay.substring(2,8)
                String sAccntNo    = (String)rowData.get("accnt_no");
                Object oDtlNo      = rowData.get("dtl_no");
                Object oDtlPayAmt  = rowData.get("dtl_pay_amt");

                /*
                sCell[0] = CommonUtils.filler_blank_right(CommonUtils.nvls(sCell[0]), 4, "0");
                sCell[1] = CommonUtils.filler_blank_left(CommonUtils.nvls(sCell[1]), 4, "0");
                sCell[2] = CommonUtils.filler_blank_left(CommonUtils.nvls(sCell[2]), 4, "0");
                */

                /*
                sBrthMonDay = CommonUtils.nvls(sBrthMonDay);

                if(sBrthMonDay.length() != 8)
                {
                    sBrthMonDay = "00000000";
                }
                */

                /*
                System.out.println("sKbNo ::: " + sKbNo + "," +
                           "sCell[0] + sCell[1] + sCell[2] ::: " + sCell[0] + sCell[1] + sCell[2] + "," +
                           "sBrthMonDay ::: " + sBrthMonDay + "," +
                           "sAccntNo ::: " + sAccntNo + "," +
                           "oDtlNo ::: " + oDtlNo.toString() + "," +
                           "oDtlPayAmt ::: " + oDtlPayAmt.toString());
                */

                dtlArr[0] = CommonUtils.filler_blank_left(CommonUtils.nvls(sKbNo), 12, "0");
                dtlArr[1] = CommonUtils.filler_blank_left(CommonUtils.nvls("000000"), 6, "0");
                dtlArr[2] = CommonUtils.filler_blank_left(CommonUtils.nvls(sAccntNo), 12, "0");
                dtlArr[3] = CommonUtils.filler_blank_left(CommonUtils.nvls(oDtlNo.toString()), 4, "0");
                dtlArr[4] = CommonUtils.filler_blank_left(CommonUtils.nvls(oDtlPayAmt.toString()), 8, "0");

                /*
                dtlArr[1] = CommonUtils.filler_blank_right(CommonUtils.nvls(sCell[0] + sCell[1] + sCell[2]), 12, " ");
                dtlArr[2] = CommonUtils.filler_blank_left(CommonUtils.nvls(sBrthMonDay.substring(2,8)), 6, "0");
                dtlArr[3] = CommonUtils.filler_blank_left(CommonUtils.nvls(sAccntNo), 12, "0");
                dtlArr[4] = CommonUtils.filler_blank_left(CommonUtils.nvls(oDtlNo.toString()), 4, "0");
                dtlArr[5] = CommonUtils.filler_blank_left(CommonUtils.nvls(oDtlPayAmt.toString()), 8, "0");
                */

                // 데이터를 전문결과에 담는다.
                for (int j = 0; j < dtlArr.length; ++j)                                         // 데이터 Row 생성 여부 확인
                {
                    resultBuffer.append(String.format("%s", dtlArr[j]));
                }
                resultBuffer.append("\r\n"); // 한줄 엔터
            }

            if(sendFileDirectory.exists() == false)                                             // 파일이 생성될 위치에 폴더 생성 여부 확인
            {
                sendFileDirectory.mkdirs();
            }

            CommonUtils.writeTextFile(file.getPath(), resultBuffer.toString(), "MS949"); // 파일 생성 여부

            String sServerIp 	= EgovProperties.getProperty("Globals.Lguplus.Prepayment.Access.Ip"); // nidw1 (운영) : 172.30.12.11, nidgw3 (통시) : 172.30.12.30
            int sPort 			= 21;
            String sUserId 		= "arc_ppay";
            String sPassword 	= "Ekdzhd!1";

            System.out.println("연결 ip :: " + sServerIp + ", port :: " +  sPort + ", id :: " + sUserId);
            FTPClient ftp = new FTPClient();

            //ftp 파이명으로 인한 인코딩 변경
            ftp.setControlEncoding("euc-kr");

            // 파일 업로드
            if (file.exists())
            {
                FileInputStream inputStream = new FileInputStream(file);

                //ftp서버 접속
                ftp.connect(sServerIp,sPort);

                int reply = ftp.getReplyCode();
                if (FTPReply.isPositiveCompletion(reply) == false) {
                    ftp.disconnect();
                    throw new Exception("FTP 서버에 로그인 할 수 없습니다.");
                } else {
                    System.out.print(ftp.getReplyString()); // 응답 메세지를 찍어봅시다
                }

                ftp.setSoTimeout(30000);

                boolean isLogin = ftp.login(sUserId, sPassword);

                if (isLogin == false){
                    inputStream.close();
                    ftp.disconnect();
                    throw new Exception("FTP 서버에 로그인 할 수 없습니다.");
                }

                ftp.enterLocalPassiveMode();

                //업로드할 위치
                String ftpRemote = "ULIFE";

                ftp.changeWorkingDirectory(ftpRemote);
                ftp.setFileType(FTP.BINARY_FILE_TYPE);

                System.out.println("ftpRemot  :"  + ftpRemote);

                //boolean isSuccess = ftp.upload(file.getPath(), file.getName()); // sSendFileDirectoryPath + "LGUPLUS_" + sdf.format(d) + ".txt" , ftp.upload(file.getPath(), sFilename);
                boolean result = ftp.storeFile(file.getName(), inputStream);
                ftp.sendSiteCommand("chmod " + "755 " + file.getName());

                System.out.println("result  :"  + result);

                if (result)
                {
                    DlwPayService.updateUplusPrepaymentDataList(insertMstData);
                    System.out.println("[" + file.getPath() + "] 파일 업로드 성공!");
                    mapOutVar.put("result", "File 정상");
                }
                else
                {
                    System.out.println("[" + file.getPath() + "] 파일 업로드 실패!");
                    mapOutVar.put("result", "File 실패");
                }

                inputStream.close();
                ftp.disconnect();
            }
        }
        else
        {
            System.out.println("작성할 LGUPLUS 할인대상자 추출 데이터가 존재하지 않습니다.");
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명스테이션 U+ 선납전송관리 받은 파일 체크 로직
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/prepayment/lgUplusFileCheckFile")
    public ModelAndView lgUplusFileCheckFile(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";
        Map<String, Object> mapInVar = xpDto.getInVariableMap();// 화면에서 받은 값
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap(); // 결과를 화면으로 보내줄 변수
        Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
        DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");

        Map<String, Object> hmParam = new HashMap<String, Object>(); // 검색조건 변수 담는 map
        try
        {
            if(listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
            }
            /************************************************************
             * 검색 날짜를 받는다
             ***********************************************************/
            String sSttRegDt  = (String)hmParam.get("stt_reg_dt");// (String) mapInVar.get("stt_dt").toString().substring(0,6);
            String sRecvFileDirectoryPath ="/app/LGUPLUS/recv/";

            try
            {
                /************************************************************
                 * 결과를 받을 파일 경로가 있는지 확인 없으면 생성
                 ***********************************************************/
                File recvFiles = new File(sRecvFileDirectoryPath);
                if(recvFiles.exists() == false)
                {
                    recvFiles.mkdirs();
                }

                /************************************************************
                 * FTP 기본 정보 입력
                 ***********************************************************/
                String sServerIp 	= EgovProperties.getProperty("Globals.Lguplus.Prepayment.Access.Ip"); // nidw1 (운영) : 172.30.12.11, nidgw3 (통시) : 172.30.12.30
                int sPort 			= 21;
                String sUserId 		= "arc_ppay";
                String sPassword 	= "Ekdzhd!1";

                System.out.println("연결 ip :: " + sServerIp + ", port :: " +  sPort + ", id :: " + sUserId);
                SimpleFtpClient ftp = new SimpleFtpClient(sServerIp, sPort, sUserId, sPassword);
                ftp.setDebugMode(true);
                ftp.connect();
                ftp.setPassiveMode();
                ftp.chdir("/home/arc_ppay/ULIFE/");

                /**************************************************************
                 * 해당 위치에 파일들의 목록을 가져온다.
                 **************************************************************/
                List<Map<String, Object>> fileList = DlwPayService.getSendFileUplusPrepaymentList(hmParam);

                /* 다운받아야 할 파일들이 FTP 내부에 존재하는지 확인함. FTP 내부에 존재하면 다운로드 받고 MST파일의 상태를 바꿈. 그렇지 않으면 무동작 */
                for(int sIdx = 0; sIdx < fileList.size(); sIdx++)
                {
                    String sSendFileName = (String)fileList.get(sIdx).get("file_name");
                    String sRecvFileName = ((String)fileList.get(sIdx).get("file_name")).substring(0, 13) + ".snd";

                    System.out.println("보낸 파일명 :: " + sSendFileName + " 받아야 하는 파일명 :: " + sRecvFileName);
                    List<String> listFTPFileNameList = ftp.getFileNameList();

                    for(int fIdx = 0; fIdx < listFTPFileNameList.size(); fIdx++)
                    {
                        String sExistFileName = listFTPFileNameList.get(fIdx);

                        System.out.println("내부 파일명 ::: " + sExistFileName + " 다운로드 대상파일명 :: " + sRecvFileName);
                        if(sExistFileName.equals(sRecvFileName))
                        {
                            ftp.download(sRecvFileName, sRecvFileDirectoryPath + sRecvFileName); // 해당 데이터 대명 본서버로 다운로드.
                            DlwPayService.sendUplusPrepaymentFileUpdate(fileList.get(sIdx));     // U+ 선납전송관리 수신된 날짜의 데이터의 수신상태를 수신완료상태로 전환
                        }
                    }
                }

                // 1. 보낸 파일목록중, 결과파일이 오지 않은 목록, 해당 월에 맞는 것만 가져온다.
                //List<Map<String, Object>> fileList = DlwPayService.getSendFileUplusPrepaymentList(hmParam);

                String[] recvFileList = recvFiles.list();

                // 2. 해당 리스트와, 결과 파일 이름을 매칭해보고 맞는게 있으면 받고, 결과파일을 입력한다.
                for (String recvFile : recvFileList)
                {
                    for (int i = 0; i < fileList.size(); i ++)
                    {
                        Map<String, Object> fileName = fileList.get(i);

                            if (fileName.get("file_name").toString().substring(0,13).equals(recvFile.toString().substring(0,13)))
                            {
                                Map<String, Object> insertMstData = new HashMap<String, Object>();
                                insertMstData.put("calc_month", fileName.get("calc_month"));
                                insertMstData.put("file_no", fileName.get("file_no"));
                                insertMstData.put("file_name", recvFile.toString()); // 파일명
                                insertMstData.put("type", "2"); // 타입
                                insertMstData.put("returnval", "N");
                                insertMstData.put("reg_man", hmParam.get("rgsr_id")); // 입력자

                                //String count ="";
                                Integer count = 0;
                                String line;
                                FileReader reader = new FileReader(sRecvFileDirectoryPath + recvFile.toString()); // 해당 파일을 읽어온다.
                                BufferedReader bufferedReader = new BufferedReader(reader);

                                while ((line = bufferedReader.readLine()) != null)
                                {
                                	if(line.length() == 45)
                                	{
                                		count++;
                                	}
                                }
                                bufferedReader.close();

                                //count = line.substring(21, 31); // 헤더에서 결과 갯수
                                insertMstData.put("count", count); // 결과 개수
                                DlwPayService.insertUplusPrepaymentRecvLog(insertMstData);
                                DlwPayService.sendUplusPrepaymentFileUpdate(insertMstData);
                            }
                            else
                            {
                                System.out.println("파일이 없음");
                                mapOutVar.put("result", "서버에 파일이 없습니다.");
                            }
                        }
                    }
                }
                catch (Exception ex)
                {
                    throw new Exception(ex.getMessage());
                }

            // 해당 날짜의 모든 파일을 가져온다. 화면에서 필터로 보여줌
            List<Map<String, Object>> mList = DlwPayService.getAllUplusPrepaymentList(hmParam);
            DataSetMap listMap = new DataSetMap();
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
        return modelAndView;
    }

    /**
     * 대명스테이션 U+ 선납전송관리 받은 파일 결과 반영
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/prepayment/lgUplusFileReflection")
    public ModelAndView lgUplusFileReflection(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        
        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";
        Map<String, Object> mapInVar = xpDto.getInVariableMap();// 화면에서 받은 값
        Map<String, Object> hmParam = new HashMap<String, Object>(); // 검색조건 변수 담는 map
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
        try
        {
            /************************************************************
             * 선택한 파일 이름을 받는다.
             ***********************************************************/
             String file_name = (String) mapInVar.get("file_name").toString();
             hmParam.put("file_name", file_name);
             ParamUtils.addSaveParam(hmParam);
             
             List<Map<String, Object>> mList = DlwPayService.getUplusTargetCalcMonth(hmParam);
             Map<String, Object> mapMonth = mList.get(0);
             hmParam.putAll(mapMonth);

             // 결과 파일 위치
             String sSendFileDirectoryPath ="/app/LGUPLUS/recv/";

             Connection connection = null;
             PreparedStatement preparedStatement = null;
             
             try
             {

                /************************************************************
                 * 파일 DB에 입력
                 ************************************************************/
                Map<String, Object> insertMstData = new HashMap<String, Object>(); // 마스터 hRow
                Map<String, Object> insertDtlData = new HashMap<String, Object>(); // 디테일 dRow
                
                insertDtlData.put("file_name", file_name); // 파일명
                insertDtlData.put("file_no", file_name.substring(5,13)); // 파일명
                insertDtlData.put("reg_man", hmParam.get("rgsr_id")); // 입력자
                insertDtlData.put("type", "2"); // 타입
                File file = new File(sSendFileDirectoryPath + file_name); // 해당 파일을 읽어온다.
                
                
                System.out.println("file 이름 : " + file.toString());
                
                if (file.exists())
                {
                    String sAccessClassName = EgovProperties.getProperty("Globals.Fix.Database.Access.DriverClassName");
                    String sAccessIp        = EgovProperties.getProperty("Globals.Fix.Database.Access.Ip");
                    String sAccessUserName  = EgovProperties.getProperty("Globals.Fix.Database.Access.UserName");
                    String sAccessPassword  = EgovProperties.getProperty("Globals.Fix.Database.Access.Password");
                    
                    String line;
                    FileReader reader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    
                    String sqlStatement = "INSERT INTO LF_DMUSER.TB_UPLUS_PREPAY_RESULT \n";
                          sqlStatement += "(CALC_MONTH, FILE_NO, ACCNT_NO, KB_NO, DTL_NO, DTL_PAY_AMT, PREPAY_YN, PREPAY_REASON, RSLT_CD, RSLT_REG_DTTM, REG_MAN, REG_DM, DEL_FG) \n";
                    	  sqlStatement += "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYYMMDD'), ?, SYSDATE, 'N')";
                    
                    int iDataIdx = 0;
                    Class.forName(sAccessClassName);
                    connection = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
                    connection.setAutoCommit(false);
                    preparedStatement = connection.prepareStatement(sqlStatement) ;
                    
                    while ((line = bufferedReader.readLine()) != null)
                    {
                    	System.out.println("데이터 라인 : " + line + "    iDataIdx :: " + iDataIdx);
                    	
                    	if(line.length() == 45)
                    	{
                    		String sKbNo         = line.substring(0, 12);
                            String sBrthMonDay   = line.substring(12 ,18);
                            String sAccntNo      = line.substring(18, 30);
                            String sDtlNo        = line.substring(30, 34);
                            String sDtlPayAmt    = line.substring(34, 42);
                            String sPrepayYn     = line.substring(42, 43);
                            String sPrepayReason = line.substring(43, 45);
                            String sRsltCd       = "E";
                            
                            preparedStatement.setString(1, (String)hmParam.get("calc_month"));
                            preparedStatement.setString(2, file_name.substring(5,13));
                            preparedStatement.setString(3, sAccntNo);
                            preparedStatement.setString(4, sKbNo);
                            preparedStatement.setString(5, sDtlNo);
                            preparedStatement.setString(6, sDtlPayAmt);
                            preparedStatement.setString(7, sPrepayYn);
                            preparedStatement.setString(8, sPrepayReason);
                            preparedStatement.setString(9, sRsltCd);
                            preparedStatement.setString(10, (String)hmParam.get("rgsr_id"));
                            
                            // addBatch에 담기
                            preparedStatement.addBatch();

                            // 파라미터 Clear
                            preparedStatement.clearParameters() ;
                    	} 
                        
                    	/*
                        if(line.length() == 45)
                        {
                            sRsltCd = "E";
                        }
                        else
                        {
                            sRsltCd = "F";
                        }
                        */
                        
                        /*
                        insertDtlData.put("kb_no",         sKbNo);
                        insertDtlData.put("cell",          sCell);
                        insertDtlData.put("brth_mon_day",  sBrthMonDay);
                        insertDtlData.put("accnt_no",      sAccntNo);
                        insertDtlData.put("dtl_no",        sDtlNo);
                        insertDtlData.put("dtl_pay_amt",   sDtlPayAmt);
                        insertDtlData.put("prepay_yn",     sPrepayYn);
                        insertDtlData.put("prepay_reason", sPrepayReason);
                        insertDtlData.put("rslt_cd",       sRsltCd);
                        */

                        // OutOfMemory를 고려하여 만건 단위로 커밋
                        if( (iDataIdx % 1000) == 0)
                        {
                            // Batch 실행
                            preparedStatement.executeBatch();

                            // Batch 초기화
                            preparedStatement.clearBatch();

                            // 커밋
                            connection.commit();
                        }
                        
                        iDataIdx++;
                    }
                    

                    /*System.out.println("프로퍼티[1] :: " + sAccessClassName + "," + "프로퍼티[2] :: " + sAccessIp + "," +
                                        "프로퍼티[3] :: " + sAccessUserName + "," + "프로퍼티[4] :: " + sAccessPassword);*/
                    
                    /*
                    String sqlStatement = "UPDATE LF_DMUSER.TB_UPLUS_PREPAY_LIST ";
                    sqlStatement += "SET RSLT_CD = ? ";
                    sqlStatement +=    ",PREPAY_YN = ? ";
                    sqlStatement +=    ",PREPAY_REASON = ? ";
                    sqlStatement +=    ",RSLT_REG_DTTM = TO_CHAR(SYSDATE, 'YYYYMMDD') ";
                    sqlStatement +=    ",UPD_MAN = ? ";
                    sqlStatement +=    ",UPD_DM = SYSDATE ";
                    sqlStatement += "WHERE FILE_NO = ?";
                    sqlStatement +=   "AND ACCNT_NO = ?";
                    sqlStatement +=   "AND LPAD(TRIM(KB_NO), 12, '0') = ?";
                    sqlStatement +=   "AND RSLT_CD IN ('S', 'R')";
                    
                    int iDataIdx = 0;
                    Class.forName(sAccessClassName);
                    connection = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
                    connection.setAutoCommit(false);
                    preparedStatement = connection.prepareStatement(sqlStatement) ;
                    
                    while ((line = bufferedReader.readLine()) != null)
                    {
                        String sKbNo         = line.substring(0, 12);
                        String sBrthMonDay   = line.substring(12 ,18);
                        String sAccntNo      = line.substring(18, 30);
                        String sDtlNo        = line.substring(30, 34);
                        String sDtlPayAmt    = line.substring(34, 42);
                        String sPrepayYn     = line.substring(42, 43);
                        String sPrepayReason = line.substring(43, 45);
                        String sRsltCd       = "";

                        if(line.length() == 45)
                        {
                            sRsltCd = "E";
                        }
                        else
                        {
                            sRsltCd = "F";
                        }

                        insertDtlData.put("kb_no",         sKbNo);
                        insertDtlData.put("brth_mon_day",  sBrthMonDay);
                        insertDtlData.put("accnt_no",      sAccntNo);
                        insertDtlData.put("dtl_no",        sDtlNo);
                        insertDtlData.put("dtl_pay_amt",   sDtlPayAmt);
                        insertDtlData.put("prepay_yn",     sPrepayYn);
                        insertDtlData.put("prepay_reason", sPrepayReason);
                        insertDtlData.put("rslt_cd",       sRsltCd);
                        
//                        DlwPayService.resultUplusPrepaymentDtlUpdate(insertDtlData); //20190708 수정 preparedStatement 사용으로 인한 주석처리
                        
                        preparedStatement.setString(1, sRsltCd);
                        preparedStatement.setString(2, sPrepayYn);
                        preparedStatement.setString(3, sPrepayReason);
                        preparedStatement.setString(4, insertDtlData.get("reg_man").toString());
                        preparedStatement.setString(5, insertDtlData.get("file_no").toString());
                        preparedStatement.setString(6, sAccntNo);
                        preparedStatement.setString(7, sKbNo);

                        //System.out.println("sReqDay ::: " + parseLine[5]);

                        // addBatch에 담기
                        preparedStatement.addBatch();

                        // 파라미터 Clear
                        preparedStatement.clearParameters() ;


                        // OutOfMemory를 고려하여 만건 단위로 커밋
                        if( (iDataIdx % 10000) == 0)
                        {
                            // Batch 실행
                            preparedStatement.executeBatch();

                            // Batch 초기화
                            preparedStatement.clearBatch();

                            // 커밋
                            connection.commit();
                        }
                        
                        iDataIdx++;
                    }
                    */
                    
                    preparedStatement.executeBatch() ;
                    connection.commit() ;
                    
                    bufferedReader.close();
                    preparedStatement.close();
                    connection.close();

                    // 결과 입력후, 결과파일 결과 값을 Y로 바꾼다.
                    insertMstData.put("calc_month", (String)hmParam.get("calc_month"));
                    insertMstData.put("file_no", file_name.substring(5,13)); // 파일번호
                    insertMstData.put("returnval", "Y");
                    insertMstData.put("reg_man", hmParam.get("rgsr_id")); // 입력자
                    DlwPayService.resultUplusPrepaymentMstUpdate(insertMstData);

                    // 결과 입력후 해당 충전월에 송신으로 된 사람 ( 파일전송하였으나 결과가 도착하지 않은 사람 )을 R로 바꿔준다.
                    //DlwPayService.noResultUplusPrepaymentFileChange(insertMstData);
                    DlwPayService.updateResultReflectionRequestData(insertMstData);
                    
                    // 결과반영뒤 결과가 실패, 미송신이거나 선납여부가 미선납인건은 자동으로 보류 데이터로 등록한다.
                    DlwPayService.insertFailUplusHoldData(insertMstData);
                }
                else
                {

                }
            }
            catch (IOException ex)
            {
                throw new Exception(ex.getMessage());
            }
            finally
            {
            	if(connection != null)
            	{
            		connection.close();
                }
            	if(preparedStatement != null)
            	{
            		preparedStatement.close();
            	}
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
        return modelAndView;
    }

    /**
     * 대명스테이션 U+ 선납전송관리 미선납회원 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/prepayment/getUplusNonPrepaymentMember")
    public ModelAndView getUplusNonPrepaymentMember(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");

            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                List<Map<String, Object>> mList = DlwPayService.getUplusNonPrepaymentMember(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명스테이션 U+ 선납전송관리 미선납회원 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/prepayment/insertUplusNonPrepaymentMember")
    public ModelAndView sendResendKakaoJoin(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap dsUplusNonPrepaymentMember = (DataSetMap)mapInDs.get("ds_input");
            String sParentCalcMonth = (String) mapInVar.get("parentCalcMonth").toString();

            for(int idx = 0; idx < dsUplusNonPrepaymentMember.size(); idx++)
            {
                Map<String,Object> rowData = dsUplusNonPrepaymentMember.get(idx);
                rowData.put("parent_calc_month", sParentCalcMonth);
                ParamUtils.addSaveParam(rowData);

                if(rowData.get("chk").equals("1"))
                {
                    //String sCalcKbNo = (String)rowData.get("kb_no");
                    List<Map<String, Object>> mKbNo = DlwPayService.getCurrntKbNo(rowData);
                    String sCurrntKbNo = (String)mKbNo.get(0).get("kb_no");

                    rowData.put("kb_no", sCurrntKbNo);

                    System.out.println("kb_no :: " + rowData.get("kb_no"));

                    DlwPayService.insertUplusNonPrepaymentMember(rowData);
                    DlwPayService.updateUplusNonPrepaymentMember(rowData);
                }
            }

            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명스테이션 고객미납관리(NEW) 현황 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getMemberYencheMngDataList")
    public ModelAndView getMemberYencheMngDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                int nTotal = DlwPayService.getMemberYencheMngDataListCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = DlwPayService.getMemberYencheMngDataList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명스테이션 고객미납관리(NEW) 청구실행
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insertMemberYencheMngDataList")
    public ModelAndView insertMemberYencheMngDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);

                int mList = DlwPayService.insertMemberYencheMngDataList(hmParam);
                //listMap.setRowMaps(mList);
                //mapOutDs.put("ds_output", listMap);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명스테이션 고객미납이력(NEW) 현황 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getMemberYencheHstrDataList")
    public ModelAndView getMemberYencheHstrDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                int nTotal = DlwPayService.getMemberYencheHstrDataListCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = DlwPayService.getMemberYencheHstrDataList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명스테이션 만기연장 관리회원 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getMemberMangiExtDataList")
    public ModelAndView getMemberMangiExtDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                int nTotal = DlwPayService.getMemberMangiExtDataListCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = DlwPayService.getMemberMangiExtDataList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명스테이션 만기연장 관리회원 엑셀업로드
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/userExcelUpload")
    public void uploadToNas(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map<String, Object> mResult = new HashMap<>();
        String fileNm = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        try
        {
            DlwPayService.userExcelUpload(request, response, mResult);

            resVarList.add("ErrorCode"	, szErrorCode);
            resVarList.add("ErrorMsg"	, szErrorMsg);
        } catch (EgovBizException e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg","업로드 중 오류가 발생하였습니다.");
            e.printStackTrace();
        }

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();

    }

    /**
     * 대명스테이션 만기연장 상품등록 팝업 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getMemberMangiGiftset")
    public ModelAndView getMemberMangiGiftset(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            List<Map<String, Object>> mList = DlwPayService.getMemberMangiGiftset(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }


    /**
     * 대명스테이션 만기연장 상품등록 팝업 등록
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insertMemberMangiGiftset")
    public ModelAndView insertMemberMangiGiftset(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();


            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            ParamUtils.addSaveParam(hmParam);

            DlwPayService.insertMemberMangiGiftset(hmParam); // 산출마감체크 등록

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }


    /**
     * 대명스테이션 만기연장 상품등록 팝업 삭제
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/deleteMemberMangiGiftset")
    public ModelAndView deleteMemberMangiGiftset(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            ParamUtils.addSaveParam(hmParam);
            DlwPayService.deleteMemberMangiGiftset(hmParam);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }

    /**
     * 대명스테이션 만기연장 관리회원정보 수정
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/updateMemberMangiExtDataList")
    public ModelAndView updateMemberMangiExtDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            ParamUtils.addSaveParam(hmParam);

            DlwPayService.updateMemberMangiExtDataList(hmParam);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }

    /**
     * 대명스테이션 만기연장 상품등록 팝업 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getMemberMangiExtConfirmList")
    public ModelAndView getMemberMangiExtConfirmList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            List<Map<String, Object>> mList = DlwPayService.getMemberMangiExtConfirmList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }

    /**
     * 대명스테이션 고객 만기연장 상품리스트 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getMemberMangiProdDetail")
    public ModelAndView getMemberMangiProdDetail(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            List<Map<String, Object>> mList = DlwPayService.getMemberMangiProdDetail(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }

    @RequestMapping(value = "/insertMemberMangiProdDetail")
    public ModelAndView insertMemberMangiProdDetail(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            DlwPayService.insertMemberMangiProdDetail(hmParam);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }

    @RequestMapping(value = "/updateMemberMangiProdDetail")
    public ModelAndView updateMemberMangiProdDetail(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            DlwPayService.updateMemberMangiProdDetail(hmParam);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }

    @RequestMapping(value = "/deleteMemberMangiProdDetail")
    public ModelAndView deleteMemberMangiProdDetail(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();
        Map<String, Object> hmParam2 = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap listInDs2 = (DataSetMap)mapInDs.get("ds_input2");
            if(listInDs2 != null && listInDs2.size() > 0)
            {
                hmParam2 = listInDs2.get(0);
            }
            DlwPayService.updateMemberMangiExtDataList(hmParam2); // 산출마감체크 등록
            
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            DlwPayService.deleteMemberMangiProdDetail(hmParam);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }

    /** ================================================================
     * 날짜 : 20190222
     * 이름 : 송준빈
     * 추가내용 : 장례이행보증현황 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    @RequestMapping(value = "/perf/getPerfMainDataList")
    public ModelAndView getGongjeAsisDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                int nTotal = DlwPayService.getPerfMainDataCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = DlwPayService.getPerfMainDataList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }


    /** ================================================================
     * 날짜 : 20190222
     * 이름 : 송준빈
     * 추가내용 : 장례이행보증현황 입력,수정,삭제
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    @RequestMapping(value = "/perf/{crudType}")
    public ModelAndView saveGongjeAsisDataList(XPlatformMapDTO xpDto, Model model, @PathVariable("crudType") String crudTyp) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
                hmParam.put("orderBy", "ACCNT_NO");
                hmParam.put("orderDirection", "ASC");
                ParamUtils.addSaveParam(hmParam);

                //System.out.println("==================crudTyp : " + crudTyp);

                if("insertPerfMainDataList".equals(crudTyp))
                {
                    // 기존에 등록되어 있던 회원인지 체크
                    int chkCnt = DlwPayService.getPerfMainDataCount(hmParam);

                    if(chkCnt <= 0)
                    {
                        DlwPayService.insertPerfMainDataList(hmParam);
                    }
                    else
                    {
                        mapOutVar.put("xSaveReturnMsg", "해당 회원은 이미 장례이행보증회원 데이터로 등록되어있는 회원입니다.");  // 실패 메시지 return
                    }
                }
                else if("updatePerfMainDataList".equals(crudTyp))
                {
                    DlwPayService.updatePerfMainDataList(hmParam);
                }
                else if("deletePerfMainDataList".equals(crudTyp))
                {
                    DlwPayService.deletePerfMainDataList(hmParam);
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 장례이행보증현황 팝업 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/perf/getPerfMainConfirmList")
    public ModelAndView getPerfMainConfirmList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            List<Map<String, Object>> mList = DlwPayService.getPerfMainConfirmList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }

    /**
     * 장례이행보증현황 (해약, 행사) 실지급금액 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/perf/getPerfMainPayAmt")
    public ModelAndView getPerfMainPayAmt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String accnt_no = (String)mapInVar.get("accnt_no");

            if (accnt_no != null || accnt_no != "" || !accnt_no.equalsIgnoreCase(""))
            {
                mList = DlwPayService.getPerfMainPayAmt(accnt_no);

                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap); //tc_mem=전체건수
            }
            else
            {
                szErrorCode = "-1";
                szErrorMsg = "회원번호가 공백입니다. 청구 판정이 불가능합니다.";
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                return modelAndView;
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 증서출력-일괄출력 팝업 조회조건 Data 가져오기
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getCodeClCdDataset")
    public ModelAndView getCodeClCdDataset(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            List<Map<String, Object>> mList = DlwPayService.getCodeClCdDataset(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }

    /**
     * 증서출력-일괄출력 증서상품등록 팝업 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getClCondData")
    public ModelAndView getClCondData(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            List<Map<String, Object>> mList = DlwPayService.getClCondData(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }


    /**
     * 증서출력-일괄출력 증서상품등록 팝업 등록
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insertClCondData")
    public ModelAndView insertClCondData(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();


            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            ParamUtils.addSaveParam(hmParam);

            DlwPayService.insertClCondData(hmParam); // 산출마감체크 등록

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }


    /**
     * 증서출력-일괄출력 증서상품등록 팝업 삭제
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/deleteClCondData")
    public ModelAndView deleteClCondData(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            ParamUtils.addSaveParam(hmParam);
            DlwPayService.deleteClCondData(hmParam);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }

    /**
     * 회원 입금정보 리스트 업로드
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/memberUploadList")
    public void memberUploadList(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map<String, Object> mResult = new HashMap<>();
        String fileNm = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        try
        {
            DlwPayService.memberUploadList(request, response, mResult);

            resVarList.add("ErrorCode"	, szErrorCode);
            resVarList.add("ErrorMsg"	, szErrorMsg);
        } catch (EgovBizException e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg","업로드 중 오류가 발생하였습니다.");
            e.printStackTrace();
        }

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();
    }

    /** ================================================================
     * 날짜 : 20190222
     * 이름 : 송준빈
     * 추가내용 : 회원 입금 정보 업로드 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_UPLOAD_TEMP
     * ================================================================
     * */
    @RequestMapping(value = "/getMemberUploadDataList")
    public ModelAndView getMemberUploadDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("reg_man", hmParam.get("rgsr_id"));

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                int nTotal = DlwPayService.getMemberUploadDataListCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = DlwPayService.getMemberUploadDataList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /** ================================================================
     * 날짜 : 20190410
     * 이름 : 송준빈
     * 추가내용 : 고객 미납 월마감 전체 집계
     * 대상 테이블 : LF_DMUSER.TB_CLOSE_MEMBER_MON
     * ================================================================
     * */
    @RequestMapping(value = "/getTotalMemberList")
    public ModelAndView getTotalMemberList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap1 = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);

                List<Map<String, Object>> mList1 = DlwPayService.getTotalMemberList(hmParam);
                List<Map<String, Object>> mList2 = DlwPayService.getRelMemberList(hmParam);

                listMap1.setRowMaps(mList1);
                listMap2.setRowMaps(mList2);

                mapOutDs.put("ds_output1", listMap1);
                mapOutDs.put("ds_output2", listMap2);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /** ================================================================
     * 날짜 : 20190416
     * 이름 : 송준빈
     * 추가내용 : 수수료 산출 집계 조회
     * 대상 테이블 : LF_DMUSER.TB_ALOW_BASIC_INFO
     * ================================================================
     * */
    @RequestMapping(value = "/fee/getAlowBasicInfo")
    public ModelAndView getAlowBasicInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap1 = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);

                List<Map<String, Object>> mList1 = DlwPayService.getAlowBasicInfo(hmParam);
                List<Map<String, Object>> mList2 = DlwPayService.getAlowBasicInfoSummary(hmParam);

                listMap1.setRowMaps(mList1);
                listMap2.setRowMaps(mList2);

                mapOutDs.put("ds_output1", listMap1);
                mapOutDs.put("ds_output2", listMap2);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /** ================================================================
     * 날짜 : 20190416
     * 이름 : 송준빈
     * 추가내용 : 수수료 산출 집계 상세 내용 조회
     * 대상 테이블 : LF_DMUSER.TB_ALOW_BASIC_INFO
     * ================================================================
     * */
    @RequestMapping(value = "/fee/getAlowBasicInfoDetail")
    public ModelAndView getAlowBasicInfoDetail(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap1 = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                int nTotal = DlwPayService.getMonthAlowCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList1 = DlwPayService.getMonthAlowList(hmParam);
                List<Map<String, Object>> mList2 = DlwPayService.getMonthAlowListSummary(hmParam);

                listMap1.setRowMaps(mList1);
                listMap2.setRowMaps(mList2);

                mapOutDs.put("ds_output1", listMap1);
                mapOutDs.put("ds_output2", listMap2);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /** ================================================================
     * 날짜 : 20190424
     * 이름 : 송준빈
     * 추가내용 : 수수료 산출 집계 상세 내용 조회
     * 대상 테이블 : LF_DMUSER.TB_ALOW_BASIC_INFO
     * ================================================================
     * */
    @RequestMapping(value = "/fee/getMonthAlowList")
    public ModelAndView getMonthAlowList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap1 = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                int nTotal = DlwPayService.getMonthAlowCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList1 = DlwPayService.getMonthAlowList(hmParam);
                List<Map<String, Object>> mList2 = DlwPayService.getMonthAlowListSummary(hmParam);

                listMap1.setRowMaps(mList1);
                listMap2.setRowMaps(mList2);

                mapOutDs.put("ds_output1", listMap1);
                mapOutDs.put("ds_output2", listMap2);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /** ================================================================
     * 날짜 : 20190424
     * 이름 : 송준빈
     * 추가내용 : 수수료 산출 집계 상세 내용 조회
     * 대상 테이블 : LF_DMUSER.TB_ALOW_BASIC_INFO
     * ================================================================
     * */
    @RequestMapping(value = "/fee/getAlowDetailList")
    public ModelAndView getAlowDetailList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap1 = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");

            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" + listInDs.size());

            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);

                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" + hmParam);

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }


                int nTotal = DlwPayService.getAlowDetailCount(hmParam);
                mapOutVar.put("nTotalListCnt1", nTotal);

                List<Map<String, Object>> mList1 = DlwPayService.getAlowDetailList(hmParam);
                listMap1.setRowMaps(mList1);
                mapOutDs.put("ds_output1", listMap1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }



    /** ================================================================
     * 날짜 : 20190123
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송 기준데이터 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    @RequestMapping(value = "/list/getFeesSynthesisInfo")
    public ModelAndView getFeesSynthesisInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMapSum = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                String sSvcId = (String)hmParam.get("svc_id");

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                if(sSvcId.equals("tp_memberSpecFees"))
                {
                    int nTotal1 = DlwPayService.getMemberSpecFeesCount(hmParam);
                    mapOutVar.put("nTotalListCnt1", nTotal1);

                    List<Map<String, Object>> mList1 = DlwPayService.getMemberSpecFeesList(hmParam);
                    listMap.setRowMaps(mList1);
                    mapOutDs.put("ds_output1", listMap);

                    List<Map<String, Object>> mList1s = DlwPayService.getMemberSpecFeesSummary(hmParam);
                    listMapSum.setRowMaps(mList1s);
                    mapOutDs.put("ds_output1s", listMapSum);

                }
                else if(sSvcId.equals("tp_emplSpecFees"))
                {
                    int nTotal2 = DlwPayService.getEmplSpecFeesCount(hmParam);
                    mapOutVar.put("nTotalListCnt2", nTotal2);

                    List<Map<String, Object>> mList2 = DlwPayService.getEmplSpecFeesList(hmParam);
                    listMap.setRowMaps(mList2);
                    mapOutDs.put("ds_output2", listMap);

                    List<Map<String, Object>> mList2s = DlwPayService.getEmplSpecFeesSummary(hmParam);
                    listMapSum.setRowMaps(mList2s);
                    mapOutDs.put("ds_output2s", listMapSum);
                }
                else if(sSvcId.equals("tp_carryOverRefund"))
                {
                    int nTotal3 = DlwPayService.getCarryOverRefundCount(hmParam);
                    mapOutVar.put("nTotalListCnt3", nTotal3);

                    List<Map<String, Object>> mList3 = DlwPayService.getCarryOverRefundList(hmParam);
                    listMap.setRowMaps(mList3);
                    mapOutDs.put("ds_output3", listMap);

                    List<Map<String, Object>> mList3s = DlwPayService.getCarryOverRefundSummary(hmParam);
                    listMapSum.setRowMaps(mList3s);
                    mapOutDs.put("ds_output3s", listMapSum);

                }
                else if(sSvcId.equals("tb_financialStatus"))
                {
                    int nTotal4 = DlwPayService.getFinancialStatusCount(hmParam);
                    mapOutVar.put("nTotalListCnt4", nTotal4);

                    List<Map<String, Object>> mList4 = DlwPayService.getFinancialStatusList(hmParam);
                    listMap.setRowMaps(mList4);
                    mapOutDs.put("ds_output4", listMap);

                }
                else
                {
                    szErrorCode = "-1";
                    szErrorMsg = "할당되지 아니한 조회(업무) 영역입니다.";
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                    return modelAndView;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /** ================================================================
     * 날짜 : 20190503
     * 이름 : 송준빈
     * 추가내용 : 수당/수수료 배치 CRUD
     * 대상 테이블 : LF_DMUSER.TB_FEES_CALC_BATCH_DAY
     * ================================================================
     * */
    @RequestMapping(value = "/feesbatch/{crudType}")
    public ModelAndView crudFeesBatch(XPlatformMapDTO xpDto, Model model, @PathVariable("crudType") String crudTyp) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0)
            {
                hmParam = srchInDs.get(0);

                ParamUtils.addSaveParam(hmParam);

                if("getFeesCalcBatchDay".equals(crudTyp))
                {
                    List<Map<String, Object>> mList = DlwPayService.getFeesCalcBatchDay(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);
                }
                else if("insertFeesCalcBatchDay".equals(crudTyp))
                {
                    // 기존 등록된 배치일자인지
                    int chkCnt = DlwPayService.getChkFeesCalcBatchDay(hmParam);

                    if(chkCnt == 0)
                    {
                        hmParam.put("reg_man", hmParam.get("rgsr_id"));
                        DlwPayService.insertFeesCalcBatchDay(hmParam);
                    }
                    else
                    {
                        mapOutVar.put("xReasonMsg", "해당일자는 이미 등록되어 있는 배치처리 일자입니다.");  // 실패 메시지 return
                    }
                }
                else if("deleteFeesCalcBatchDay".equals(crudTyp))
                {
                    hmParam.put("upd_man", hmParam.get("rgsr_id"));
                    DlwPayService.deleteFeesCalcBatchDay(hmParam);
                }
                else
                {

                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 재무현황 배치일자 조회
     *
     * 20191101
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getFinanceCalcBatchDay")
    public ModelAndView getFinanceCalcBatchDay(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            List<Map<String, Object>> mList = DlwPayService.getFinanceCalcBatchDay(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }
    
    /**
     * 일일청구기준데이터 처리일자 등록
     *
     * 20181213
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insertFinanceCalcBatchDay")
    public ModelAndView insertFinanceCalcBatchDay(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();


            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            ParamUtils.addSaveParam(hmParam);
            hmParam.put("reg_man", hmParam.get("rgsr_id"));

            // 입력유효성체크
            int chkExtReqDt = DlwPayService.validationInsertFinanceCalcBatch(hmParam);

            if(chkExtReqDt == 0)
            {
                DlwPayService.insertFinanceCalcBatchDay(hmParam); // 산출마감체크 등록
            }
            else if(chkExtReqDt > 0)
            {
                mapOutVar.put("xInsertFailMsg", "해당 파일은 이미 등록되어 있습니다.");  // 실패 메시지 return
            }

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }
    
    /**
     * 재무현황 배치파일 다운로드
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/financeCalcBatchFile")
    public void eb21downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = null;
        Map<String, Object> mRow = null;
        String upload_cl = "";
        String file_nm = "";
        String reg_man = "";
        String reg_dm = "";
        
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String sYYYYMMDD = sdf.format(d);
        String sYYYYMM   = sYYYYMMDD.substring(0, 6);

        try {
            String yymmdd = CommonUtils.nvls(request.getParameter("yymmdd"));
            hmParam = new HashMap<>();
            hmParam.put("yymmdd", yymmdd);



            String  successPath	= "";

            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("windows") >= 0) {
                successPath = EgovProperties.getProperty("cms.file.windows.successPath");
            } else {
                successPath = EgovProperties.getProperty("cms.file.unix.successPath");
            }



//            String srcFilePath = successPath +"EB21/" +"EB21"+ yymmdd.substring(4,8);
//
//
//            if (osName.indexOf("windows") >= 0) {
//                srcFilePath = srcFilePath.replaceAll("/", "\\\\");
//            }
            
            String sMbidFileParentFixPath;
            String sMbidFileFullPath;
            
            if (osName.indexOf("windows") >= 0)
            {
           	 sMbidFileParentFixPath = "C:\\media\\FINANCE\\"; // 이건 예제임. 실제 PATH가 아님. (rootPath : C:/)
           	 sMbidFileFullPath = sMbidFileParentFixPath + sYYYYMM + "\\" + "재무현황"+yymmdd + "_A.xlsx";
            }
            else
            {
           	 sMbidFileParentFixPath = "/media/FINANCE/"; // 이건 예제임. 실제 PATH가 아님. (rootPath : /app/)
           	 sMbidFileFullPath = sMbidFileParentFixPath + sYYYYMM + "/" + "재무현황"+sYYYYMMDD + "_A.xlsx";
            }


            System.out.println(sMbidFileFullPath);

        //    log.debug("srcFilePath : " + srcFilePath);

            File xDownFile = new File(sMbidFileFullPath);
            if (!xDownFile.exists()) {
                throw new EgovBizException("디스크에 실제 파일이 존재하지 않습니다.");


            }

            String contentType = request.getContentType();


            System.out.println("contentType1 : " + contentType);
  //          log.debug("contentType : " + contentType);

            if (contentType == null) {
                if (request.getHeader("user-agent").indexOf("MSIE 5.5") != -1)
                    response.setContentType("doesn/matter;");
                else
                    response.setContentType("application/octet-stream");
            } else {
                response.setContentType(contentType);
            }

            response.setHeader("Content-Transfer-Encoding:", "binary");

            // String sDisplayFileName = "상품목록.xlsx";
            // response.setHeader("Content-Disposition", "attachment;filename=" + sDisplayFileName + ";");

            response.setHeader("Content-Length", "" + xDownFile.length());
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");

            byte b[] = new byte[1024*4];
            BufferedInputStream fin = new BufferedInputStream(new FileInputStream(xDownFile));
            BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

            try{
                int read = 0;
                while ((read = fin.read(b)) != -1)
                {
                    outs.write(b,0,read);
                }

            }catch (Exception e){
            }finally{
                if (outs!=null) outs.close();
                if (fin!=null) fin.close();
            }

            outs.flush();
            outs.close();

        } catch (Exception e) {
            e.printStackTrace();

            ServletOutputStream os = response.getOutputStream();
            os.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            os.println("<Root xmlns=\"http://www.tobesoft.com/platform/dataset\" ver=\"5000\">");
            os.println("<Parameters>");
            os.println("<Parameter id=\"ErrorCode\" type=\"string\">-1</Parameter>");
            os.println("<Parameter id=\"ErrorMsg\" type=\"string\">" + e.getMessage() + "</Parameter>");
            os.println("</Parameters>");
            os.println("</Root>");
            os.close();

        }
    }

    /**
     * 일일청구기준데이터 처리일자 삭제
     *
     * 20181213
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/deleteFinanceCalcBatchDay")
    public ModelAndView deleteFinanceCalcBatchDay(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            // 산출마감체크 삭제
            DlwPayService.deleteFinanceCalcBatchDay(hmParam);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }

    /** ================================================================
     * 날짜 : 20190522
     * 이름 : 김주용
     * 추가내용 : 대명스테이션 만기연장 상품조회 팝업 조회
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_GIFTSET
     * ================================================================
     * */
    @RequestMapping(value = "/getMemberMangiGiftsetList")
    public ModelAndView getMemberMangiGiftsetList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }

            List<Map<String, Object>> mList = DlwPayService.getMemberMangiGiftsetList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }

    /** ================================================================
     * 날짜 : 20190508
     * 이름 : 정승철
     * 추가내용 : 수당수수료산출 상세데이터 CRUD
     * 대상 테이블 : LF_DMUSER.TB_ALOW_BASIC_INFO
     *               LF_DMUSER.TB_ALOW_BASIC_INFO_HISTORY
     * =================================================================
     * */
    @RequestMapping(value = "/fee/setAlowBasicInfo")
    public ModelAndView setAlowBasicInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
                hmParam.put("reg_man", oLoginUser.getUserid());

                // 마감여부체크
                String input_YYYYMM = hmParam.get("alow_dt").toString(); // 입력한 수당년월
                String close_YYYYMM = DlwPayService.getAlowLastCloseYyyymm(hmParam); // 수당수수료 최종마감년월 조회

                CommonUtils.printLog("@@@@@@@@@@@ 수당수수료 상세데이터 : " + hmParam);

                System.out.println("==== 입력한 수당년월         : " + input_YYYYMM);
                System.out.println("==== 수당수수료 최종마감년월 : " + close_YYYYMM);

                if (input_YYYYMM.compareTo(close_YYYYMM) > 0) {

                    String flag = StringUtils.defaultString((String) mapInVar.get("flag"));
                    hmParam.put("flag", flag);
                    mapOutVar.put("result_msg", "success");

                    if("insert".equals(flag)) { // 입력
                        String chkFlag = DlwPayService.chkAlowDupl(hmParam);  // 수기입력여부 체크 (** 중복체크)

                        if("T".equals(chkFlag)) {
                            DlwPayService.insertAlowBasicInfo(hmParam);
                            DlwPayService.insertAlowBasicInfoHistory(hmParam); // 수당수수료 히스토리 저장
                        }
                        else if("F".equals(chkFlag)) {
                            mapOutVar.put("result_msg", "이미 등록되어 있습니다. 확인해주세요.");
                        }
                    }
                    else if("update".equals(flag)) {  // 수정
                        DlwPayService.updateAlowBasicInfo(hmParam);
                        DlwPayService.insertAlowBasicInfoHistory(hmParam); // 수당수수료 히스토리 저장
                    }
                    else if("delete".equals(flag)) {  // 삭제
                        DlwPayService.deleteAlowBasicInfo(hmParam);
                        DlwPayService.insertAlowBasicInfoHistory(hmParam); // 수당수수료 히스토리 저장
                    }

                    mapOutDs.put("ds_output", listMap);
                }
                else {
                    mapOutVar.put("result_msg", "이미 마감되었습니다. 확인해주세요.");
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /**
     * U플러스 보류 고객 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/prepayment/getUplusPrepayHoldDataList")
    public ModelAndView getUplusPrepayHoldDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                int nTotal = DlwPayService.getUplusPrepayHoldDataListCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = DlwPayService.getUplusPrepayHoldDataList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * U플러스 보류 고객 등록
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/prepayment/insertUplusPrepayHoldData")
    public ModelAndView insertUplusPrepayHoldData(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = null;

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                
                hmParam.put("reg_man", (String)hmParam.get("rgsr_id"));
                hmParam.put("orderBy", "ACCNT_NO");
                hmParam.put("orderDirection", "ASC");
                
                mList = DlwPayService.getUplusPrepayHoldDataList(hmParam);
                
                if(mList.size() <= 0)
                {
                	int iCnt = DlwPayService.insertUplusPrepayHoldData(hmParam);
                }
                else
                {
                	throw new EgovBizException("이미 등록된 보류 고객입니다. 등록된 보류 데이터를 삭제후 저장하여 주십시오.");
                }
                
                //listMap.setRowMaps(mList);
                //mapOutDs.put("ds_output", listMap);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * U플러스 보류 고객 삭제
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/prepayment/deleteUplusPrepayHoldDataList")
    public ModelAndView deleteNiceJoinData(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listMapInDs = (DataSetMap)mapInDs.get("ds_input");
            String sHoldKbNo = "";
            String sTrueKbNo = "";
            String sHoldDtlNo = "";
            String sTrueDtlNo = "";
            int iHoldDtlNo = 0;
            int iTrueDtlNo = 0;
            Integer iDtlNoInsertIndex;

            for(int mIdx = 0; mIdx < listMapInDs.size(); mIdx++)
            {
            	hmParam = listMapInDs.get(mIdx);
            	ParamUtils.addSaveParam(hmParam);
            	hmParam.put("upd_man", hmParam.get("rgsr_id"));
            	
            	sHoldKbNo = (String)hmParam.get("hold_kb_no");
            	sTrueKbNo = (String)hmParam.get("true_kb_no");
            	sHoldDtlNo = String.valueOf(hmParam.get("hold_dtl_no"));
            	sTrueDtlNo = String.valueOf(hmParam.get("true_dtl_no"));
            	iHoldDtlNo = Integer.parseInt(sHoldDtlNo) + 1;
            	iTrueDtlNo = Integer.parseInt(sTrueDtlNo) + 1;
            	
            	if(iHoldDtlNo < iTrueDtlNo)
            	{
            		for(int dIdx = iHoldDtlNo; dIdx < iTrueDtlNo; dIdx++)
            		{
            			iDtlNoInsertIndex = dIdx;
            			System.out.println("등록될 회차는 " + dIdx + " 회차 입니다.");
            			hmParam.put("dtl_no_insert", iDtlNoInsertIndex.toString());
            			DlwPayService.insertUplusHoldPrepaymentMember(hmParam); // 인서트 구문
            		}
            	}
            	DlwPayService.deleteUplusPrepayHoldDataList(hmParam);
            }
            
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /**
     * U플러스 보류 고객 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/prepayment/getUplusPrepayHoldMember")
    public ModelAndView getUplusPrepayHoldMember(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                List<Map<String, Object>> mList = DlwPayService.getUplusPrepayHoldDataList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 대명스테이션 만기연장 수수료 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getMemberMangiAmtDataList")
    public ModelAndView getMemberMangiAmtDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                int nTotal = DlwPayService.getMemberMangiAmtDataListCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = DlwPayService.getMemberMangiAmtDataList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 만기연장 수수료 산출
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insertMemberMangiAmtDataList")
    public ModelAndView insertMemberMangiAmtDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();
        
        Map<String,Object> rowData = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                
                //파라미터로 받은 월에 대해 해약건이 있을시 만기연장 취소 처리
                List<Map<String, Object>> mList1 = DlwPayService.getMangiResnList(hmParam); //만기연장 회원에 대한 해약건 조회
                
                for(int idx=0; idx<mList1.size(); idx++) {
                	rowData = mList1.get(idx);
                	DlwPayService.updateMemberMangiDataList(rowData); //해약건이 있을시 만기연장 취소처리
                }
                
                
                DlwPayService.updateMemberMangiAmtDataList(hmParam);
                DlwPayService.insertMemberMangiAmtDataList(hmParam);
            }

            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * U플러스 보류 고객 엑셀 등록
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/uplusHoldExcelUpload")
    public void uplusHoldExcelUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map<String, Object> mResult = new HashMap<>();
        String fileNm = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        try
        {
            DlwPayService.uplusHoldExcelUpload(request, response, mResult);

            resVarList.add("ErrorCode"	, szErrorCode);
            resVarList.add("ErrorMsg"	, szErrorMsg);
        } catch (EgovBizException e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg","업로드 중 오류가 발생하였습니다.");
            e.printStackTrace();
        }

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();

    }
    
    /** ================================================================
     * 날짜 : 20190527
     * 이름 : 송준빈
     * 추가내용 : 사원별 수당관리 마스터 데이터, 사원별 수당관리 디테일 데이터 (담당고객)
     * 대상 테이블 : 
     * ================================================================
     * */
    @RequestMapping(value = "/alow/getAlowEmpleList")
    public ModelAndView getAlowEmpleList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap1 = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) 
            {
                hmParam = srchInDs.get(0);
                ParamUtils.addSaveParam(hmParam);

                String sTabGubun = CommonUtils.nvls((String)hmParam.get("tab_gubun"));
                
                if("tp_emplSpecFees".equals(sTabGubun))
                {
                	List<Map<String, Object>> mList1 = DlwPayService.getAlowEmpleMstList(hmParam);
                	listMap1.setRowMaps(mList1);
                    mapOutDs.put("ds_output1", listMap1);
                    
                    List<Map<String, Object>> mList2 = DlwPayService.getAlowEmpleDtlList(hmParam);
                    listMap2.setRowMaps(mList2);
                    mapOutDs.put("ds_output2", listMap2);
                }
                else
                {
                	
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /** ================================================================
     * 날짜 : 20190527
     * 이름 : 송준빈
     * 추가내용 : 회원별 수당관리 마스터 데이터, 회원별 수당관리 디테일 데이터
     * 대상 테이블 : 
     * ================================================================
     * */
    @RequestMapping(value = "/alow/getAlowAccntNoList")
    public ModelAndView getAlowAccntNoList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap1 = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) 
            {
                hmParam = srchInDs.get(0);
                

                List<Map<String, Object>> mList1 = DlwPayService.getAlowAccntNoMstList(hmParam);
                listMap1.setRowMaps(mList1);
                mapOutDs.put("ds_output1", listMap1);
                    
                List<Map<String, Object>> mList2 = DlwPayService.getAlowAccntNoDtlList(hmParam);
                listMap2.setRowMaps(mList2);
                mapOutDs.put("ds_output2", listMap2);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /** ================================================================
     * 날짜 : 20190618
     * 이름 : 김주용
     * 추가내용 : 만기연장 회원 삭제
     * 대상 테이블 : 
     * ================================================================
     * */
    @RequestMapping(value = "/deleteMemberMangiExtDataList")
    public ModelAndView deleteMemberMangiExtDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();
        Map<String, Object> hmParam2 = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap listInDs2 = (DataSetMap)mapInDs.get("ds_input2");
            
            //마스터 디테일 데이터셋 저장시 디테일에 없다면 마스터 데이터로면 DEL_FG를 업데이트한다.
            if(listInDs2 != null && listInDs2.size() > 0)
            {
                hmParam = listInDs2.get(0);
            }
            
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
            }
            
            DlwPayService.deleteMemberMangiExtDataList(hmParam);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }
    
    /**
     * 대명스테이션 만기연장 관리회원 배송 엑셀업로드
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/userExcelDeliveryUpload")
    public void userExcelDeliveryUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map<String, Object> mResult = new HashMap<>();
        String fileNm = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        try
        {
            DlwPayService.userExcelDeliveryUpload(request, response, mResult);

            resVarList.add("ErrorCode"	, szErrorCode);
            resVarList.add("ErrorMsg"	, szErrorMsg);
        } catch (EgovBizException e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg","업로드 중 오류가 발생하였습니다.");
            e.printStackTrace();
        }

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();

    }
    
    /**
     * 대명스테이션 수수료산출 상세 데이터2 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/fee/getAlowDetail2")
    public ModelAndView getAlowDetail2(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                int nTotal = DlwPayService.getAlowDetail2Count(hmParam);
                mapOutVar.put("nTotalListCnt2", nTotal);

                List<Map<String, Object>> mList = DlwPayService.getAlowDetail2(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output1", listMap);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 무승인 취소를 취소한다.
     * 김주용
     * 20191216
     */
    @RequestMapping(value = "/cancel-nauthpay-delete")
    public ModelAndView cancelNauthpayDelete(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            hmParam.put("paramEmpleNo", hmParam.get("rgsr_id"));
            hmParam.put("paramAs", "EMP");

            DlwPayService.cancelNauthpayDelete(hmParam);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 무승인 환불처리를 한다
     * 김주용
     * 20191216
     */
    @RequestMapping(value = "/cancel-nauthpay-refund")
    public ModelAndView cancelNauthpayRefund(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs.size() > 0)
            {
            	for (int idx = 0; idx < listInDs.size(); idx ++){                         
                    hmParam = listInDs.get(idx);
                    
                    if (hmParam.get("del_fg").toString().equals("C")){
                    	
        	            //세션
        	            ParamUtils.addSaveParam(hmParam);
        	            hmParam.put("emple_no", hmParam.get("rgsr_id"));
        	            hmParam.put("reg_man", hmParam.get("rgsr_id"));
        	            hmParam.put("upd_man", hmParam.get("rgsr_id"));         
        	            
        	            DlwPayService.cancelNauthpayRefund(hmParam);
                    }                                      	                                
                    System.out.println("xxxxxxxxxxxxxxxx>" + hmParam);            
            	}            	
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 회원별 카드,CMS 결과데이터 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/nicepay-rltm-card-log/getWdrwAccntLogList")
    public ModelAndView getWdrwAccntLogList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                
                List<Map<String, Object>> mList = DlwPayService.getWdrwAccntLogList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 대명스테이션 고객 만기연장 회원 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getMemberMangiInfo")
    public ModelAndView getMemberMangiInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            String strAccntNo = mapInVar.get("accnt_no").toString();
                        
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if(listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }
            
            hmParam.put("accnt_no", strAccntNo);
            
            System.out.println(hmParam);
                       
            List<Map<String, Object>> mList = DlwPayService.getMemberMangiInfo(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }
    
    /**
     * 대명스테이션 고객 만기연장 회원 등록 후 회원 상태값 변경
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    
    @RequestMapping(value = "/updateResnInfo")
    public ModelAndView updateResnInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0){
                hmParam = listInDs.get(0);
            }

            DlwPayService.updateResnInfo(hmParam);
            
            /* ================================================================
             * 날짜 : 20171226
             * 이름 : 송준빈
             * 추가내용 : 쇼핑몰 연동 Table 에 고객의 상태를 유효로 변경 (해약취소시)
             * ================================================================
             * */
            hmParam.put("P_MEM_NO", hmParam.get("mem_no"));
            hmParam.put("P_ACCNT_NO", hmParam.get("accnt_no"));
            hmParam.put("P_STATE", "1");

            System.out.println("DELETE인풋데이터 :: " +hmParam );
            //dlwResnService.updateResnMemberState(hmParam);
            DlwMallLinkedService.updateResnMallState(hmParam);
            
            hmParam.put("cons_memo", "만기연장해약취소");
            hmParam.put("cnsl_con", "만기연장 해약취소처리");

            consController.saveConsdlw(hmParam);

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }
    
    /**
     * U플러스 보류 고객 엑셀 등록
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/uplusHoldExcelNewUpload")
    public void uplusHoldExcelNewUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map<String, Object> mResult = new HashMap<>();
        String fileNm = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        try
        {
            DlwPayService.uplusHoldExcelNewUpload(request, response, mResult);

            resVarList.add("ErrorCode"	, szErrorCode);
            resVarList.add("ErrorMsg"	, szErrorMsg);
        } catch (EgovBizException e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg","업로드 중 오류가 발생하였습니다.");
            e.printStackTrace();
        }

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();

    }
    
    /**
     * 대명스테이션 캐시백 산출 현황 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/prepayment/getUplusCashbackDataList")
    public ModelAndView getUplusCashbackDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                int nTotal = DlwPayService.getUplusCashbackDataListCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = DlwPayService.getUplusCashbackDataList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 대명스테이션 U+ 캐시백 산출 로직
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/prepayment/insertUplusCashBackData")
    public ModelAndView insertUplusCashBackData(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                
//                int iCnt = DlwPayService.getPreMonthResultReflect(hmParam);
//                
//                if(iCnt <= 0)
//                {
//                	szErrorCode = "-1";
//                    szErrorMsg = "이전달의 결과 반영이 완료되지 않았습니다. 이전달의 결과반영을 완료해 주십시오.";
//                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
//                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
//                    return modelAndView;
//                }

                DlwPayService.updateInitUplusCashbackData(hmParam);
                DlwPayService.insertUplusCashbackData(hmParam);
            }
            
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 캐시백 보류 고객 엑셀 등록
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cashbackHoldExcelNewUpload")
    public void cashbackHoldExcelNewUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map<String, Object> mResult = new HashMap<>();
        String fileNm = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        try
        {
            DlwPayService.cashbackHoldExcelNewUpload(request, response, mResult);

            resVarList.add("ErrorCode"	, szErrorCode);
            resVarList.add("ErrorMsg"	, szErrorMsg);
        } catch (EgovBizException e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg","업로드 중 오류가 발생하였습니다.");
            e.printStackTrace();
        }

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();

    }
    
    /**
     * U플러스 보류 고객 삭제
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/prepayment/deleteUplusCashbackHoldDataList")
    public ModelAndView deleteUplusCashbackHoldDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listMapInDs = (DataSetMap)mapInDs.get("ds_input");
            String sHoldKbNo = "";
            String sTrueKbNo = "";
            String sHoldDtlNo = "";
            String sTrueDtlNo = "";
            int iHoldDtlNo = 0;
            int iTrueDtlNo = 0;
            Integer iDtlNoInsertIndex;

            for(int mIdx = 0; mIdx < listMapInDs.size(); mIdx++)
            {
            	hmParam = listMapInDs.get(mIdx);
            	ParamUtils.addSaveParam(hmParam);
            	hmParam.put("upd_man", hmParam.get("rgsr_id"));
            	
            	sHoldKbNo = (String)hmParam.get("hold_kb_no");
            	sTrueKbNo = (String)hmParam.get("true_kb_no");
            	sHoldDtlNo = String.valueOf(hmParam.get("hold_dtl_no"));
            	sTrueDtlNo = String.valueOf(hmParam.get("true_dtl_no"));
            	iHoldDtlNo = Integer.parseInt(sHoldDtlNo) + 1;
            	iTrueDtlNo = Integer.parseInt(sTrueDtlNo) + 1;
            	
            	if(iHoldDtlNo < iTrueDtlNo)
            	{
            		for(int dIdx = iHoldDtlNo; dIdx < iTrueDtlNo; dIdx++)
            		{
            			iDtlNoInsertIndex = dIdx;
            			System.out.println("등록될 회차는 " + dIdx + " 회차 입니다.");
            			hmParam.put("dtl_no_insert", iDtlNoInsertIndex.toString());
            			DlwPayService.insertUplusHoldCashbackMember(hmParam); // 인서트 구문
            		}
            	}
            	DlwPayService.deleteUplusCashbackHoldDataList(hmParam);
            }
            
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /**
     * U플러스 보류 고객 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/prepayment/getUplusCashbackHoldDataList")
    public ModelAndView getUplusCashbackHoldDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                List<Map<String, Object>> mList = DlwPayService.getUplusCashbackHoldDataList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
}