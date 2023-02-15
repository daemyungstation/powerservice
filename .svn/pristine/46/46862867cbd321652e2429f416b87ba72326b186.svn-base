package powerservice.business.dlw.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DlwSdpService;
import powerservice.business.sys.service.BasVlService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.SimpleFtpClient;
import powerservice.core.util.ParamUtils;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

//2018.03.20 로그 추가
import powerservice.business.common.service.CommonService;
/**
 * 대명라이프웨이 삼디프 상품 정보를 관리한다
 *
 *
 * @author 임동진
 * @version 1.0
 * @date 2016/09/19
 * @프로그램ID DlwSdp
 */
@Controller
@RequestMapping(value = "/dlw/sdp")
public class DlwSdpController {

    @Resource
    private DlwSdpService DlwSdpService;

    @Resource
    private BasVlService basVlService;

    @Resource
    private CommonService commonService;

    /**
     * 대명스테이션 삼디프 시책금 현황물
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/reg-list")
    public ModelAndView getSdpList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0) {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg)) {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }

                }

                int nTotal = DlwSdpService.getSdpCount(hmParam);
                mapOutVar.put("tc_ocbProdReg", nTotal);

                List<Map<String, Object>> mList = DlwSdpService.getSdpList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2018.03.20 접속 로그//////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////

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
     * 대명스테이션 삼디프 다구좌 접수 현황물
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/many-list")
    public ModelAndView getSdpManyList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0) {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg)) {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }

                }

                int nTotal = DlwSdpService.getSdpManyCount(hmParam);
                mapOutVar.put("tc_ocbProdReg", nTotal);

                List<Map<String, Object>> mList = DlwSdpService.getSdpManyList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2018.03.20 접속 로그//////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////

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
     * 대명스테이션 삼디프 프로모션 현황물
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/promo-list")
    public ModelAndView getSdpPromoList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0) {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg)) {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }

                }

                int nTotal = DlwSdpService.getSdpPromoCount(hmParam);
                mapOutVar.put("tc_ocbProdReg", nTotal);

                List<Map<String, Object>> mList = DlwSdpService.getSdpPromoList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2018.03.20 접속 로그//////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////

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
     * 대명스테이션 삼디프 충전금 리스트
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/charge-list")
    public ModelAndView getSdpChargeList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0) {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg)) {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                int nTotal = DlwSdpService.getSdpChargeCount(hmParam);
                mapOutVar.put("tc_ocbProdReg", nTotal);

                List<Map<String, Object>> mList = DlwSdpService.getSdpChargeList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2018.03.20 접속 로그//////////////////////////////////////////////////////////////////////////////\
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////

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
     * 대명스테이션 삼디프 충전금 산출
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/charge/save")
    public ModelAndView saveChargeList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            Map<String, Object> hmParam = new HashMap<String, Object>();

            hmParam.put("stt_dt", mapInVar.get("stt_dt"));
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa   " + mapInVar.get("stt_dt"));
            // 세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            CommonUtils.printLog(hmParam);

            DlwSdpService.saveChargeList(hmParam);

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
     * 대명스테이션 삼디프 FTP File 작성 및 전송
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/ftp/send")
    public ModelAndView sendFile(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        /**********************************************************************************
         * 기본적인 변수들 선언
         * ********************************************************************************/
        Map<String, Object> mapInVar = xpDto.getInVariableMap();// 화면에서 받은 값
        Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap(); // 화면에서 보내온 DS
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();// 화면에 보낼 값
        Map<String, Object> hmParam = new HashMap<String, Object>(); // DS에서 한건씩 꺼내서 쓸 맵

        // 현재 날짜
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        // 송신월
        String stt_dt = (String) mapInVar.get("stt_dt").toString().substring(0,6);
        hmParam.put("charge_dt", stt_dt); // 송신월

        // 세션
        ParamUtils.addSaveParam(hmParam);
        String reg_man = (String) hmParam.get("rgsr_id"); // 현재 세션 접속자 사번

        // 저장할 파일 위치
        //String pcDataDir ="C://app/sdp/send/"; // 로컬테스트용
        String pcDataDir ="/sftp_home/sftpuser/send/";
/*
        // FTP 접속을 위한 기본 정보를 프로퍼티 파일에서 가져온다.
        String serverIp = EgovProperties.getProperty("sdp.send.ftp.ip");
        int port = Integer.valueOf(EgovProperties.getProperty("sdp.send.ftp.port"));
        String user = EgovProperties.getProperty("sdp.send.ftp.user");
        String pw = EgovProperties.getProperty("sdp.send.ftp.pw");
        String ftpDataDir = EgovProperties.getProperty("sdp.send.ftp.dataPath");
        String pcDataDir = EgovProperties.getProperty("sdp.send.pc.dataPath");
  */

        // 현재날짜로 작성/전송한 파일이 존재하면 return
        File file = new File(pcDataDir + "bp01_" + sdf.format(d) + ".txt"); // 해당 파일을 읽어온다.

        if (file.exists()) { // 파일이 있으면,

            mapOutVar.put("result", "현재날짜로 작성 및 전송한 파일이 존재합니다. (하루에 1번만 가능)");
            System.out.println(mapOutVar.get("result"));;
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
            return modelAndView;
        }

        // 해당 충전월에 아직 결과 적용이 되지 않은 파일이 있는지 확인해본다.
        int sendresult = DlwSdpService.sendresult(hmParam);

        if (sendresult == 0){
            // 해당 충전월로 파일 보낸 테이블에서 max(count(*)+1 해온다.)
           //String count = CommonUtils.lpad(DlwSdpService.selectSDPFileCount(hmParam)+"", 2, "0");

            // 전문 결과를 담을 변수
            StringBuffer resultBuffer = new StringBuffer();
            /**************************************************************************************
             *  파일 작성 시작
             **************************************************************************************/

            // 산출 데이터 리스트
            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input"); // ds_input 의 이름으로 보내어진 DS을 꺼내온다.
            // 가져온 데이터셋이 비어있지 않으면
            if (listInDs.size() > 0) {
                // 헤더 작성
                String[] headArr = new String[5];
                headArr[0] = CommonUtils.filler_blank_right("HH", 2, " "); // 헤더시작
                headArr[1] = CommonUtils.filler_blank_right("BP01", 11, " "); // 전문코드
                headArr[2] = CommonUtils.filler_blank_right(sdf.format(d), 8, " "); // 파일생성일자
                headArr[3] = CommonUtils.filler_blank_left(listInDs.size() + "", 10, "0"); // 전송데이터건수
                headArr[4] = CommonUtils.filler_blank_right("", 102, " "); // 공백

                // 헤더를 전문 결과에 담는다.
                for (int i = 0; i < headArr.length; ++i) {
                    resultBuffer.append(String.format("%s", headArr[i]));
                }
                resultBuffer.append("\n"); // 한줄 엔터

                // 1줄씩 전문작성한다. ( 1줄 = 1명의 데이터 )
                for (int i = 0; i < listInDs.size(); i++) {

                    hmParam = listInDs.get(i);
           
                    /* 실패 데이터 'R' 로 UPDATE 하는 부분은 추후 필요시 수정하여 반영필요 - 2017.12.22 정승철
                     ***************************************************************************************************
                    // 해당 날짜의 실패 데이터가 있으면 del_fg Y 로 update시킨다.
                    int file_no = Integer.parseInt(count)-1;
                    if (file_no > 0){
                        hmParam.put("charge_dt", stt_dt); // 충전일
                        hmParam.put("upd_man", reg_man); // 수정자
                        hmParam.put("file_no", Integer.parseInt(count)-1); // 파일번호
                        DlwSdpService.dtlFailFileSet(hmParam);
                    }
                    *****************************************************************************************************/

                    String[] dtlArr = new String[3];
                    dtlArr[0] = CommonUtils.filler_blank_right("DD", 2, " "); // 데이터시작
                    dtlArr[1] = CommonUtils.filler_blank_right(CommonUtils.nvls((String) hmParam.get("card_no")), 16, " "); // 카드번호
                    dtlArr[2] = CommonUtils.filler_blank_left(CommonUtils.nvls((String) hmParam.get("pay_amt")), 13, "0"); // 충전금액

                    // 데이터를 전문결과에 담는다.
                    for (int j = 0; j < dtlArr.length; ++j) {
                        resultBuffer.append(String.format("%s", dtlArr[j]));
                    }
                   resultBuffer.append("\n"); // 한줄 엔터
                   
                   ParamUtils.addSaveParam(hmParam);
                                     
                   hmParam.put("file_no", sdf.format(d)); // 파일 번호            
                   hmParam.put("cback_amt", hmParam.get("pay_amt")); // 충전금액
                   hmParam.put("rslt_cd", "N");
                   hmParam.put("reg_man", hmParam.get("rgsr_id")); // 현재 세션 접속자 사번
                   hmParam.put("charge_dt", sdf.format(d).substring(0, 6)); // 송신월
                   
                   DlwSdpService.resultDtlInsert(hmParam);
                }

                /******************************************
                 * 파일 작성
                 *  writeTextFile(String sOutFile, String sConetnt, String sCharset)
                 *  sOutFile : 파일명
                 *  sConetnt : 내용
                 *  sCharset : 캐릭터셋
                 ******************************************/
                //CommonUtils.writeTextFile(pcDataDir + stt_dt + count + ".txt", resultBuffer.toString(), "MS949"); //
                CommonUtils.writeTextFile(pcDataDir + "bp01_" + sdf.format(d) + ".txt", resultBuffer.toString(), "MS949"); // 송신파일명 앞에 'bp01_' 추가되었음 - 2017.12.18 정승철
                mapOutVar.put("result", "File 정상");
            } else {
                mapOutVar.put("result", "데이터가 없습니다");
            }
            
            /* 정상적으로 전문파일 생성 후 생성된 정보로 SDP파일전송 TB에 등록 */
            if (mapOutVar.get("result").equals("File 정상")){

                // 세션
                ParamUtils.addSaveParam(hmParam);

                hmParam.put("reg_man", hmParam.get("rgsr_id")); // 현재 세션 접속자 사번
                hmParam.put("send_count", listInDs.size()); // 데이터 갯수
                //hmParam.put("charge_dt", stt_dt); // 충전월
                hmParam.put("charge_dt", sdf.format(d).substring(0, 6)); // 송신월
                hmParam.put("type", "1"); // 1: 보낸파일 2: 결과파일
                //hmParam.put("file_name", stt_dt + count + ".txt"); // 파일 이름
                hmParam.put("file_no", sdf.format(d)); // 파일 번호
                hmParam.put("file_name", "bp01_" + sdf.format(d) + ".txt"); // 파일 이름
                hmParam.put("return", "N"); // 결과
                // DEV_IMSI_SDP_FILE_MST 테이블에 해당 파일 전송이력 남기기.
               if ( DlwSdpService.insertSDPSendLog(hmParam) <= 0 ){
            	   mapOutVar.put("result", "로그 실패");
               } else {
            	   mapOutVar.put("result", "정상 성공");
               }
            }

         /*   try {
                try {
                    boolean isSuccess = false; // 파일전송 성공 실패
                    File file = null; // 파일
                    SimpleFtpClient ftp = new SimpleFtpClient(serverIp, port, user, pw); // 해당 ftp를 접속 준비한다.

                    ftp.setDebugMode(true); // 디버그모드
                    ftp.connect(); // ftp 접속
                    ftp.setPassiveMode(); // 패시브모드
                    ftp.setFileType(FTP.BINARY_FILE_TYPE); // 파일 타입 설정

                    // 경로 이동 ( 파일을 저장할 경로로 이동한다 )
                    ftp.chdir(ftpDataDir);

                    file = new File(pcDataDir + stt_dt + count + ".txt"); // 해당 파일을 가져온다.
                    if (file.exists()) { // 해당 파일이 존재하면

                        isSuccess = ftp.upload(file.getPath(), ftpDataDir + file.getName()); // upload를 실행한다.
                        if (isSuccess) {
                            System.out.println("[" + file.getPath() + "] 파일 업로드 성공!");

                            // upload가 정상적으로 실행이 되었으면, 해당 파일의 1줄을 읽어서 저장을 한다. DB로 저장.
                            mapOutVar.put("result", "FTP 정상");
          */
          /* 20190705 수정 */
          /*
                            String line;
                            FileReader reader = new FileReader(pcDataDir + "bp01_" + sdf.format(d) + ".txt"); // 해당 파일을 읽어온다.

                            BufferedReader bufferedReader = new BufferedReader(reader);
                            while ((line = bufferedReader.readLine()) != null) { // 파일의 1줄을  읽는다.
                                if (line.substring(0,2).equals("DD")){
                                    //hmParam.put("file_no", count ); // 파일 번호
                                    hmParam.put("file_no", sdf.format(d)); // 파일 번호
                                    //hmParam.put("charge_dt", stt_dt); // 충전월
                                    hmParam.put("charge_dt", sdf.format(d).substring(0, 6)); // 송신월
                                    hmParam.put("card_no", line.substring(2, 18).trim()); // 카드번호
                                    hmParam.put("cback_amt", line.substring(18, 31)); // 충전금액
                                    // 데이터를 DTL 테이블에 담는다.
                                    hmParam.put("reg_man", reg_man); // 현재 세션 접속자 사번
                                    hmParam.put("rslt_cd", "N");
                                    //hmParam.put("file_name", stt_dt + count + ".txt");
                                    hmParam.put("file_name", sdf.format(d) + ".txt");

                                    // DTL테이블에 INSERT
                                    DlwSdpService.resultDtlInsert(hmParam);
                                }else{
                                }
                            }
                            bufferedReader.close();
           	*/
            
            /*
                        } else {
                            System.out.println("[" + file.getPath() + "] 파일 업로드 실패!");
                            mapOutVar.put("result", "FTP 전송 실패");
                        }
                    } else {
                        mapOutVar.put("result", "PC에 해당 파일이 없습니다.");
                    }
                    ftp.close(); // FTP 접속 종료
                } catch (IOException ex) {
                    throw new Exception(ex.getMessage());
                }

            } catch (Exception e) {
                e.printStackTrace();
                szErrorCode = "-1";
                szErrorMsg = e.getMessage();
            }
            */            
        } else {
            mapOutVar.put("result", "결과처리가 되지 않은 파일이 있습니다.");
        }
        System.out.println(mapOutVar.get("result"));
        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명스테이션 삼디프 FTP File 리스트 확인
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/ftp/checkFile")
    public ModelAndView checkFileList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";
        Map<String, Object> mapInVar = xpDto.getInVariableMap();// 화면에서 받은 값
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap(); // 결과를 화면으로 보내줄 변수
        Map<String, Object> hmParam = new HashMap<String, Object>(); // 검색조건 변수 담는 map
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
        try {
            /************************************************************
             * 검색 날짜를 받는다
             ***********************************************************/
             String stt_dt = (String) mapInVar.get("stt_dt").toString().substring(0,6);
             hmParam.put("charge_dt", stt_dt); // 송신월
             ParamUtils.addSaveParam(hmParam);
             hmParam.put("emple_no", hmParam.get("rgsr_id")); // 현재 세션 접속자 사번
             hmParam.put("reg_man", hmParam.get("rgsr_id")); // 현재 세션 접속자 사번
             hmParam.put("upd_man", hmParam.get("rgsr_id")); // 현재 세션 접속자 사번

             // 결과 파일 위치
             //String recvPcDataDir ="/home/dmstation/test/local/recv/";
             //String recvPcDataDir ="C:/egovframework/sdp/recv/"; // 로컬테스트용
             String recvPcDataDir ="/sftp_home/sftpuser/recv/";

            /************************************************************
             * FTP 기본 정보를 가져온다.
             ***********************************************************/
/*
            String serverIp = EgovProperties.getProperty("sdp.recv.ftp.ip");
            int port = Integer.valueOf(EgovProperties.getProperty("sdp.recv.ftp.port"));
            String user = EgovProperties.getProperty("sdp.recv.ftp.user");
            String pw = EgovProperties.getProperty("sdp.recv.ftp.pw");
            String ftpDataDir = EgovProperties.getProperty("sdp.recv.ftp.dataPath");
            String recvPcDataDir = EgovProperties.getProperty("sdp.recv.pc.dataPath");
            String file_name = (String) mapInVar.get("file_name"); // 받은 파일명
*/
            /**************************************************************
             * 해당 위치에 파일들의 목록을 가져온다.
             **************************************************************/
            try {
/*
                SimpleFtpClient ftp = new SimpleFtpClient(serverIp, port, user, pw); // 해당 FTP 접속 준비
                ftp.setDebugMode(true); // 디버그모드
                ftp.connect(); // 접속
                ftp.setPassiveMode(); // 패시브모드
                ftp.setFileType(FTP.BINARY_FILE_TYPE); // 파일타입 설정
                ftp.chdir(ftpDataDir); // 경로이동
*/
                // 1. 보낸 파일목록중, 결과파일이 오지 않은 목록, 해당 월에 맞는 것만 가져온다.
                List<Map<String, Object>> fileList = DlwSdpService.getSendFileList(hmParam);

              //  List<String> realfileList = ftp.getFileNameList(); // 해당 경로의 파일 리스트 작성
                File recvFiles = new File(recvPcDataDir);
                String[] recvfileList = recvFiles.list(); // 해당 경로의 파일 리스트 작성
                // 2. 해당 리스트와, 결과 파일 이름을 매칭해보고 맞는게 있으면 받고, 결과파일을 입력한다.
                    for (String recvFile : recvfileList){
                        for (int i = 0; i < fileList.size(); i ++){
                            Map<String, Object>fileName = fileList.get(i);

                            /////////////////////////////////////////////////////////////////////////////
                            // 2017.12.18 정승철 - 송수신파일명 규칙 변경됨
                            // EX) 11월 충전 List 파일명
                            // (bp01_ : 송신 / bp02_ : 수신)  + 파일생성날짜 + ".txt"
                            // 송신파일명 : bp01_20171218.txt
                            // 수신파일명 : bp02_20171218.txt
                            /////////////////////////////////////////////////////////////////////////////

                            //if (fileName.get("FILE_NAME").equals(recvFile)){
                            if (fileName.get("FILE_NAME").toString().substring(5,17).equals(recvFile.toString().substring(5,17))){

                                    /*********************************************************
                                    * 파일 DB에 입력
                                    ************************************************************/
                                   Map<String, Object> hRow = new HashMap<String, Object>();
                                   hRow.put("file_name", recvFile.toString()); // 파일명
                                   hRow.put("reg_man", hmParam.get("reg_man")); // 입력자
                                   hRow.put("type", "2"); // 타입
                                   String count ="";
                                   String line;
                                       FileReader reader = new FileReader(recvPcDataDir +recvFile.toString()); // 해당 파일을 읽어온다.
                                       BufferedReader bufferedReader = new BufferedReader(reader);
                                       while ((line = bufferedReader.readLine()) != null) { // 파일의 1줄을  읽는다.
                                           if (line.substring(0,2).equals("HH")){
                                               hRow.put("file_name", recvFile.toString()); // 파일이름
                                               //hRow.put("file_no", (recvFile.toString()).substring(11,13)); // 파일번호
                                               hRow.put("file_no", (recvFile.toString()).substring(5,13)); // 파일번호
                                               hRow.put("charge_dt", (recvFile.toString()).substring(5,11)); // 송신월
                                               count = line.substring(21, 31); // 헤더에서 결과 갯수
                                               hRow.put("send_count", count); // 결과 개수
                                               hRow.put("return", "N"); // 결과

                                               // 결과파일 MST INSERT
                                               DlwSdpService.insertSDPSendLog(hRow);

                                               // 송신파일 업데이트 (** RETURNVAL 'N' -> 'Y')
                                               DlwSdpService.sendFileUpdate(hRow);
                                           }
                                       }
                                       bufferedReader.close();
                                } else {
                                    System.out.println("파일이 없음");
                                    mapOutVar.put("result", "서버에 파일이 없습니다.");
                                }

                            }
                        }
            } catch (IOException ex) {
                throw new Exception(ex.getMessage());
            }

            // 해당 날짜의 모든 파일을 가져온다. 화면에서 필터로 보여줌
            List<Map<String, Object>> mList = DlwSdpService.getAllChargeDtList(stt_dt);
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
     * 대명스테이션 삼디프 FTP File 결과 적용
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/ftp/fileSelect")
    public ModelAndView fileSelect(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";
        Map<String, Object> mapInVar = xpDto.getInVariableMap();// 화면에서 받은 값
        Map<String, Object> hmParam = new HashMap<String, Object>(); // 검색조건 변수 담는 map
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
        try {
            /************************************************************
             * 선택한 파일 이름을 받는다.
             ***********************************************************/
             String file_name = (String) mapInVar.get("file_name").toString();
             hmParam.put("file_name", file_name); // 파일명
             ParamUtils.addSaveParam(hmParam);
             hmParam.put("emple_no", hmParam.get("rgsr_id")); // 현재 세션 접속자 사번
             hmParam.put("reg_man", hmParam.get("rgsr_id")); // 현재 세션 접속자 사번
             hmParam.put("upd_man", hmParam.get("rgsr_id")); // 현재 세션 접속자 사번

             // 결과 파일 위치
             //String recvPcDataDir ="/home/dmstation/test/local/recv/";
             //String recvPcDataDir ="C:/egovframework/sdp/recv/"; // 로컬테스트용
             String recvPcDataDir ="/sftp_home/sftpuser/recv/";

             try {

                /************************************************************
                 * 파일 DB에 입력
                 ************************************************************/
                Map<String, Object> hRow = new HashMap<String, Object>();
                Map<String, Object> dRow = new HashMap<String, Object>();

                dRow.put("file_name", file_name); // 파일명
                dRow.put("reg_man", hmParam.get("reg_man")); // 입력자
                dRow.put("type", "2"); // 타입
                File file = new File(recvPcDataDir + file_name); // 해당 파일을 읽어온다.

                if (file.exists()) { // 파일이 있으면,

                    String line;
                    FileReader reader = new FileReader(file); // 해당 파일을 읽어온다.
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    while ((line = bufferedReader.readLine()) != null) { // 파일의 1줄을  읽는다.
                        if (line.substring(0,2).equals("HH")){
                        }else if (line.substring(0,2).equals("DD")){
                            //dRow.put("file_no", file_name.substring(11,13)); // 파일번호
                            dRow.put("file_no", file_name.substring(5,13)); // 파일번호
                            dRow.put("charge_dt",  file_name.substring(5, 11)); // 충전월
                            dRow.put("card_no", line.substring(2, 18).trim()); // 카드번호
                            dRow.put("cback_amt", line.substring(18, 31)); // 충전금액
                            dRow.put("rslt_cd", line.substring(31, 32)); // 성공실패
                            DlwSdpService.resultDtlUpdate(dRow);

                        }else{
                        }
                    }

                    // 결과 입력후, 결과파일 결과 값을 Y로 바꾼다.
                    //hRow.put("file_no", file_name.substring(11,13)); // 파일번호
                    hRow.put("file_no", file_name.substring(5,13)); // 파일번호
                    hRow.put("charge_dt",  file_name.substring(5, 11)); // 충전월
                    hRow.put("return", "Y");
                    hRow.put("reg_man", hmParam.get("reg_man")); // 입력자
                    DlwSdpService.resultMstUpdate(hRow);

                    // 결과 입력후 해당 충전월에 송신으로 된 사람 ( 파일전송하였으나 결과가 도착하지 않은 사람 )을 R로 바꿔준다.
                    DlwSdpService.dtlNoResultFileChange(hRow);
                    bufferedReader.close();
                } else {
                }
            } catch (IOException ex) {
                throw new Exception(ex.getMessage());
            }
            // 해당 날짜의 모든 파일을 가져온다. 화면에서 필터로 보여줌
            List<Map<String, Object>> mList = DlwSdpService.getAllChargeDtList(file_name.substring(0,6));
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
     * 대명스테이션 삼디프 시책금 산출로직
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mes/insertSdpMesData")
    public ModelAndView insertSdpMesData(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                DlwSdpService.updateInitSdpMesData(hmParam);
                DlwSdpService.insertSdpMesData(hmParam);
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
     * 대명스테이션 삼디프 시책금 현황 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mes/getSdpMesDataList")
    public ModelAndView getSdpMesDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        DataSetMap listMap02 = new DataSetMap();
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

                int nTotal = DlwSdpService.getSdpMesDataListCount(hmParam);
                mapOutVar.put("tc_ocbProdReg", nTotal);

                List<Map<String, Object>> mList01 = DlwSdpService.getSdpMesDataList(hmParam);
                listMap01.setRowMaps(mList01);
                
                List<Map<String, Object>> mList02 = DlwSdpService.getSdpMesDataSummary(hmParam);
                listMap02.setRowMaps(mList02);
                
                mapOutDs.put("ds_output", listMap01);
                mapOutDs.put("ds_outputSummary", listMap02);
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
     * 대명스테이션 삼디프 시책금 FTP File 작성 및 전송
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mes/sendSdpMesDataList")
    public ModelAndView samsungB2bFileSend(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        /**********************************************************************************
         * 기본적인 변수들 선언
         * ********************************************************************************/
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        // 세션
        ParamUtils.addSaveParam(hmParam);
        String reg_man = (String) hmParam.get("rgsr_id"); // 현재 세션 접속자 사번

        // 저장할 파일 위치
        //String sSendFileDirectoryPath = "/app/SAMSUNG/send/"; // 로컬에서 테스트 수행시
        String sSendFileDirectoryPath = "/sftp_home/sftpuser/send/";
        File sendFileDirectory = new File(sSendFileDirectoryPath);
        //String pcDataDir ="/sftp_home/sftpuser/send/";

        File file = new File(sSendFileDirectoryPath + "bp03_" + sdf.format(d) + ".txt"); // 해당 파일을 읽어온다.

        if (file.exists()) // 파일이 있으면
        { 
        	
            szErrorCode = "-1";
            szErrorMsg = "현재날짜로 작성 및 전송한 파일이 존재합니다. (시책금 파일은 하루에 한번만 전송할수 있습니다.";
            //modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
            return modelAndView;
        }
        
        List<Map<String, Object>> sendMesDataList = DlwSdpService.getSdpMesDataList(hmParam);
        
        if(sendMesDataList.size() > 0)
        {
        	StringBuffer resultBuffer = new StringBuffer();
        	Map<String, Object> insertMstData = new HashMap<String, Object>();
        	String[] headArr = new String[5];
            headArr[0] = CommonUtils.filler_blank_right("HH", 2, " "); // 헤더시작
            headArr[1] = CommonUtils.filler_blank_right("bp03", 11, " "); // 전문코드
            headArr[2] = CommonUtils.filler_blank_right(sdf.format(d), 8, " "); // 파일생성일자
            headArr[3] = CommonUtils.filler_blank_left(sendMesDataList.size() + "", 10, "0"); // 전송데이터건수
            headArr[4] = CommonUtils.filler_blank_right("", 113, " "); // 공백

            // 헤더를 전문 결과에 담는다.
            for (int i = 0; i < headArr.length; ++i) {
                resultBuffer.append(String.format("%s", headArr[i]));
            }
            
            resultBuffer.append("\n"); // 한줄 엔터
            
            insertMstData.put("calc_day", hmParam.get("stt_reg_dt")); 
            insertMstData.put("file_no", sdf.format(d)); 
            insertMstData.put("file_name", "bp03_" + sdf.format(d) + ".txt"); 
            insertMstData.put("type", "1"); 
            insertMstData.put("count", sendMesDataList.size()); 
            insertMstData.put("returnval", "N"); 
            insertMstData.put("reg_man", reg_man); 
            
            DlwSdpService.insertSdpMesDataMst(insertMstData);
            
            // Data부를 전문결과에 담는다.
        	for(int idx = 0; idx < sendMesDataList.size(); idx++)
        	{
        		Map<String, Object> insertDtlData = new HashMap<String, Object>();
        		String[] dtlArr = new String[8];
        		Map<String, Object> rowData = sendMesDataList.get(idx);
        		String sProdCd = (String)rowData.get("prod_cd");
                
                dtlArr[0] = CommonUtils.filler_blank_right("DD", 2, " ");                                              // 데이터시작
                dtlArr[1] = CommonUtils.filler_blank_right(CommonUtils.nvls(sdf.format(d)), 8, " "); // 전문발신일자
                dtlArr[2] = CommonUtils.filler_blank_left(CommonUtils.nvls((String) rowData.get("accnt_no")), 12, " "); // 대명관리일련번호
                //dtlArr[3] = CommonUtils.filler_blank_left(CommonUtils.nvls((String) rowData.get("auth_dt")), 8, " ");  // 승인일자 
                dtlArr[3] = CommonUtils.filler_blank_left(CommonUtils.nvls("00000000"), 8, " ");  // 승인일자 
                //dtlArr[4] = CommonUtils.filler_blank_left(CommonUtils.nvls((String) rowData.get("auth_cd")), 8, " ");  // 승인번호
                dtlArr[4] = CommonUtils.filler_blank_left(CommonUtils.nvls("00000000"), 8, " ");  // 승인번호
                //dtlArr[5] = CommonUtils.filler_blank_left(CommonUtils.nvls((String) rowData.get("mrc_no")), 12, " "); // 가맹점번호
                dtlArr[5] = CommonUtils.filler_blank_left(CommonUtils.nvls("000000000000"), 12, " "); // 가맹점번호
                dtlArr[6] = CommonUtils.filler_blank_left(CommonUtils.nvls((String) rowData.get("b2b_emple_ci")), 88, " "); // B2B삼성직원CI값
                dtlArr[7] = CommonUtils.filler_blank_left(CommonUtils.nvls(rowData.get("cbck_amt").toString()), 6, "0");  // EC	스마트라이프Gold(상조할인)_R 시책금
        		
                /*
                if(sProdCd == "EC" || sProdCd.equalsIgnoreCase("EC"))
                {
                	dtlArr[7] = CommonUtils.filler_blank_left("70000", 6, "0");  // EC	스마트라이프Gold(상조할인)_R 시책금
                	insertDtlData.put("cbck_amt", 70000); 
                }
                else if(sProdCd == "ED" || sProdCd.equalsIgnoreCase("ED"))
                {
                	dtlArr[7] = CommonUtils.filler_blank_left("70000", 6, "0");  // ED	스마트라이프Gold_R 시책금
                	insertDtlData.put("cbck_amt", 70000); 
                }
                else if(sProdCd == "EE" || sProdCd.equalsIgnoreCase("EE"))
                {
                	dtlArr[7] = CommonUtils.filler_blank_left("50000", 6, "0");  // EE	스마트라이프429_R 시책금
                	insertDtlData.put("cbck_amt", 50000); 
                }
                else if(sProdCd == "EF" || sProdCd.equalsIgnoreCase("EF"))
                {
                	dtlArr[7] = CommonUtils.filler_blank_left("50000", 6, "0");  // EF	스마트라이프429(상조할인)_R 시책금
                	insertDtlData.put("cbck_amt", 50000); 
                }
                else if(sProdCd == "EG" || sProdCd.equalsIgnoreCase("EG"))
                {
                	dtlArr[7] = CommonUtils.filler_blank_left("30000", 6, "0");  // EG	스마트라이프297_R 시책금
                	insertDtlData.put("cbck_amt", 30000); 
                }
                else if(sProdCd == "EH" || sProdCd.equalsIgnoreCase("EH"))
                {
                	dtlArr[7] = CommonUtils.filler_blank_left("30000", 6, "0");  // EH	스마트라이프297(상조할인)_R 시책금
                	insertDtlData.put("cbck_amt", 30000); 
                }
                else if(sProdCd == "X5" || sProdCd.equalsIgnoreCase("X5"))
                {
                	dtlArr[7] = CommonUtils.filler_blank_left("80000", 6, "0");  // X5	스마트라이프Gold 시책금
                	insertDtlData.put("cbck_amt", 80000); 
                }
                else if(sProdCd == "X6" || sProdCd.equalsIgnoreCase("X6"))
                {
                	dtlArr[7] = CommonUtils.filler_blank_left("80000", 6, "0");  // X6	스마트라이프Gold(상조할인) 시책금
                	insertDtlData.put("cbck_amt", 80000); 
                }
                else
                {
                	dtlArr[7] = CommonUtils.filler_blank_left("0", 6, "0");      // 그 이외 시책금
                	insertDtlData.put("cbck_amt", 0); 
                }
                */
           
                // 데이터를 전문결과에 담는다.
                for (int j = 0; j < dtlArr.length; ++j) 
                {
                    resultBuffer.append(String.format("%s", dtlArr[j]));
                }
                
                resultBuffer.append("\n"); // 한줄 엔터
                
                /*
                insertDtlData.put("calc_day", hmParam.get("stt_reg_dt")); 
                insertDtlData.put("file_no", sdf.format(d)); 
                insertDtlData.put("mem_no", rowData.get("mem_no")); 
                insertDtlData.put("accnt_no", rowData.get("accnt_no")); 
                insertDtlData.put("prod_cd", sProdCd); 
                insertDtlData.put("b2b_emple_ci", rowData.get("b2b_emple_ci")); 
                insertDtlData.put("mrc_no", rowData.get("mrc_no")); 
                insertDtlData.put("tid", rowData.get("tid"));  
                insertDtlData.put("reg_man", reg_man);  
                
                DlwSdpService.insertSdpMesDataDtl(insertDtlData);
                */ 
        	}
        	if(sendFileDirectory.exists() == false)
        	{
        		sendFileDirectory.mkdirs();
        	}
        	CommonUtils.writeTextFile(sSendFileDirectoryPath + "bp03_" + sdf.format(d) + ".txt", resultBuffer.toString(), "MS949");
        	DlwSdpService.updateSdpMesDataList(insertMstData);
            mapOutVar.put("result", "File 정상");
        }
        else
        {
        	System.out.println("작성할 시책금 데이터가 존재하지 않습니다.");
        }
        

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 대명스테이션 삼디프 시책금 받은 파일 체크 로직
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mes/samsungMesFileCheckFile")
    public ModelAndView samsungMesFileCheckFile(XPlatformMapDTO xpDto, Model model) throws Exception {
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

             //String sSendFileDirectoryPath ="/app/SAMSUNG/recv/";
             String sSendFileDirectoryPath = "/sftp_home/sftpuser/recv/";

            /************************************************************
             * FTP 기본 정보를 가져온다.
             ***********************************************************/
            /**************************************************************
             * 해당 위치에 파일들의 목록을 가져온다.
             **************************************************************/
            try 
            {
                // 1. 보낸 파일목록중, 결과파일이 오지 않은 목록, 해당 월에 맞는 것만 가져온다.
                List<Map<String, Object>> fileList = DlwSdpService.getSendFileMesMstList(hmParam);

                File recvFiles = new File(sSendFileDirectoryPath);
                if(recvFiles.exists() == false)
                {
                	recvFiles.mkdirs();
                }
                
                String[] recvFileList = recvFiles.list();
                
                // 2. 해당 리스트와, 결과 파일 이름을 매칭해보고 맞는게 있으면 받고, 결과파일을 입력한다.
                for (String recvFile : recvFileList)
                {
                    for (int i = 0; i < fileList.size(); i ++)
                    {
                        Map<String, Object> fileName = fileList.get(i);

                            if (fileName.get("file_name").toString().substring(5,17).equals(recvFile.toString().substring(5,17)))
                            {

                                /*********************************************************
                                * 파일 DB에 입력
                                ************************************************************/
                                Map<String, Object> insertMstData = new HashMap<String, Object>();
                                insertMstData.put("calc_day", fileName.get("calc_day"));
                                insertMstData.put("file_no", fileName.get("file_no"));
                                insertMstData.put("file_name", recvFile.toString()); // 파일명
                                insertMstData.put("type", "2"); // 타입
                                insertMstData.put("returnval", "N");
                                insertMstData.put("reg_man", hmParam.get("rgsr_id")); // 입력자
                                
                                String count ="";
                                String line;
                                FileReader reader = new FileReader(sSendFileDirectoryPath + recvFile.toString()); // 해당 파일을 읽어온다.
                                BufferedReader bufferedReader = new BufferedReader(reader);
                                
                                while ((line = bufferedReader.readLine()) != null) 
                                { 
                                	if (line.substring(0,2).equals("HH"))
                                	{
                                        count = line.substring(21, 31); // 헤더에서 결과 갯수
                                        insertMstData.put("count", count); // 결과 개수
                                        DlwSdpService.insertSdpMesRecvLog(insertMstData);
                                        DlwSdpService.sendSdpMesFileUpdate(insertMstData);
                                    }
                                }
                                bufferedReader.close();
                            } 
                            else 
                            {
                                System.out.println("파일이 없음");
                                mapOutVar.put("result", "서버에 파일이 없습니다.");
                            }
                        }
                    }
                } 
                catch (IOException ex) 
                {
                    throw new Exception(ex.getMessage());
                }

            // 해당 날짜의 모든 파일을 가져온다. 화면에서 필터로 보여줌
            List<Map<String, Object>> mList = DlwSdpService.getAllSdpMesList(hmParam);
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
     * 대명스테이션 삼디프 시책금 받은 파일 결과 반영
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mes/samsungMesFileReflection")
    public ModelAndView samsungMesFileReflection(XPlatformMapDTO xpDto, Model model) throws Exception {
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
             
             // 결과 파일 위치
             //String sSendFileDirectoryPath ="/app/SAMSUNG/recv/";
             String sSendFileDirectoryPath = "/sftp_home/sftpuser/recv/";

             try 
             {

                /************************************************************
                 * 파일 DB에 입력
                 ************************************************************/
                Map<String, Object> insertMstData = new HashMap<String, Object>(); // 마스터 hRow
                Map<String, Object> insertDtlData = new HashMap<String, Object>(); // 디테일 dRow

                insertDtlData.put("file_name", file_name); // 파일명
                insertDtlData.put("reg_man", hmParam.get("rgsr_id")); // 입력자
                insertDtlData.put("type", "2"); // 타입
                File file = new File(sSendFileDirectoryPath + file_name); // 해당 파일을 읽어온다.

                if (file.exists()) 
                { 
                    String line;
                    FileReader reader = new FileReader(file); 
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    while ((line = bufferedReader.readLine()) != null) 
                    { 
                        if (line.substring(0,2).equals("HH"))
                        {
                        	
                        }
                        else if (line.substring(0,2).equals("DD"))
                        {
                        	insertDtlData.put("file_no", file_name.substring(5,13)); // 파일번호
                        	insertDtlData.put("accnt_no", line.substring(10, 22)); // 카드번호
                        	
                        	String sRsltCd = line.substring(28, 36);
                        	
                        	insertDtlData.put("auth_dt", sRsltCd); // 승인일자
                        	
                        	if(sRsltCd == "00000000" || sRsltCd.equals("00000000"))
                        	{
                        		sRsltCd = "E";
                        	}
                        	else
                        	{
                        		sRsltCd = "F";
                        	}
                        	insertDtlData.put("rslt_cd", sRsltCd);
                            DlwSdpService.resultSdpMesDtlUpdate(insertDtlData);
                        }
                        else
                        {
                        	
                        }
                    }

                    // 결과 입력후, 결과파일 결과 값을 Y로 바꾼다.
                    insertMstData.put("file_no", file_name.substring(5,13)); // 파일번호
                    insertMstData.put("returnval", "Y");
                    insertMstData.put("reg_man", hmParam.get("rgsr_id")); // 입력자
                    DlwSdpService.resultSdpMesMstUpdate(insertMstData);

                    // 결과 입력후 해당 충전월에 송신으로 된 사람 ( 파일전송하였으나 결과가 도착하지 않은 사람 )을 R로 바꿔준다.
                    DlwSdpService.noResultSdpMesFileChange(insertMstData); 
                    bufferedReader.close();
                } 
                else 
                {
                	
                }
            } 
            catch (IOException ex) 
            {
                throw new Exception(ex.getMessage());
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
     * 대명스테이션 삼디프 충전금 리스트 삭제
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/deleteSdpList")
    public ModelAndView deleteSdpList(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            for(int idx = 0; idx < listInDs.size(); idx++)
            {
            	hmParam = listInDs.get(idx);
            	DlwSdpService.deleteSdpList(hmParam);
            } 
            
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

}