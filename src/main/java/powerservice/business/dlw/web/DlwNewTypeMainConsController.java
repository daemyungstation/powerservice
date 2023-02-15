 package powerservice.business.dlw.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; 

import powerservice.business.bean.UserSession;
import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.business.dlw.service.DlwAltrHistService;
import powerservice.business.dlw.service.DlwNewTypeMainConsService;
import powerservice.business.dlw.service.DlwOthersService;
import powerservice.business.sys.service.BasVlService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 대명 상담 메인 화면을 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/03
 * @프로그램ID DlwCust
 */
@Controller
@RequestMapping(value="/dlw/newTypeCons")
public class DlwNewTypeMainConsController {
	
	@Resource
	private DlwNewTypeMainConsService dlwNewTypeMainConsService;

    @Resource
    private DataAthrQuryService dataAthrQuryService;
    
    @Resource
    private BasVlService basVlService;
    
    @Resource
    private CommonService commonService;
    
    @Resource
    private DlwOthersService  dlwOthersService;
    
    @Resource
	private DlwAltrHistService dlwAltrHistService;
    
    final static String niceOperation  = "real"; // NICE 환경 dev : 개발계. real : 운영계. 개발 = 개발 운영 = 운영
	
	/**
     * 고객-상품 상세정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/memberlist")
    public ModelAndView getDlwCustMemList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
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
                ParamUtils.addCenterParam(hmParam);
                
                Map<String, Object> tmpMap = dlwNewTypeMainConsService.getDlwCustMemList(hmParam);
                if (tmpMap != null) {
                    dtptMap.setRowMaps(tmpMap);
                    mapOutDs.put("ds_output", dtptMap);
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }
        
        return modelAndView;
    }
        
    /**
     * 고객 개인정보 활용동의 상세 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/view")
    public ModelAndView getPersInfoPcusCnsn(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
        DataSetMap srctDtptMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sMemNo = StringUtils.defaultString((String) mapInVar.get("mem_no"));
            String sScrtYn = StringUtils.defaultString((String) mapInVar.get("scrt_yn"));	// 개인정보활용동의 스크립트

            if ("".equals(sMemNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원아이디가 없습니다.");

                return modelAndView;
            }

            Map<String, Object> mData = dlwNewTypeMainConsService.getPersInfoPcusCnsn(sMemNo);

            if (mData != null) {
                dtptMap.setRowMaps(mData);
                mapOutDs.put("ds_output", dtptMap);
            }

            if ("Y".equals(sScrtYn)) {
                Map<String, Object> mScrtData = dlwNewTypeMainConsService.getPersInfoPcusSrctDtpt();

                if (mScrtData != null) {
                    srctDtptMap.setRowMaps(mScrtData);
                    mapOutDs.put("ds_scrt_output", srctDtptMap);
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
     * 고객조회로그 정보를 등록한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cust-inq-log/insert")
    public ModelAndView insertCustInqLog(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);

                String sMemNo = StringUtils.defaultString((String) hmParam.get("mem_no"));
                if ("".equals(sMemNo)) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원아이디가 없습니다.");

                    return modelAndView;
                }

                ParamUtils.addSaveParam(hmParam);

                // 고객조회대상코드가 없는 경우 기본값 "상담이력" 입력
                String sCustInqTrgtCd = StringUtils.defaultString((String) hmParam.get("cust_inq_trgt_cd"));
                if ("".equals(sCustInqTrgtCd)) {
                    hmParam.put("cust_inq_trgt_cd", "20");
                }

                dlwNewTypeMainConsService.insertCustInqLog(hmParam);
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
     * 고객 특이메모 정보를 검색한다.
     *
     * @param psPageType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/exsc-cnt")
    @ResponseBody
    public ModelAndView getCustUnusMemoCnt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sMemNo = StringUtils.defaultString((String) mapInVar.get("mem_no"));
            String sBzptDivYn = StringUtils.defaultString((String) mapInVar.get("bzpt_div_yn"));

            if ("".equals(sMemNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "회원의 고유번호가 없습니다.");

                return modelAndView;
            }

            hmParam.put("mem_no", sMemNo);

            if ("Y".equals(sBzptDivYn)) {
                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                String sAthrCd = oLoginUser.getAuthoritycd();
                String sTeamCd = oLoginUser.getTeamcd();
                if (sAthrCd.contains("TM")) {
                    hmParam.put("bzpt_divs", "'999999','"+sTeamCd+"'");
                }
            }

            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            hmParam.put("user_id", oLoginUser.getUserid());

            ParamUtils.addCenterParam(hmParam);

            int nTotal = dlwNewTypeMainConsService.getCustUnusMemoCnt(hmParam); //dlwNewTypeMainConsService
            mapOutVar.put("tc_cust_memo_cnt", nTotal);

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
     * 대명라이프웨이 OCB 상품 정보 목록을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/chk")
    public ModelAndView getOcbChk(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
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
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                Map<String, Object> OCBinfo = new HashMap<String, Object>();
                String sCiVal = StringUtils.defaultString((String) hmParam.get("ci_val"));

                int HistCnt = dlwNewTypeMainConsService.getOcbTransHistCnt();

                OCBinfo.put("ocbGubun", "W400");

                Date now = new Date();
                SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat format2 = new SimpleDateFormat("HHmmss");
                String doc_no = "W400";
                String doc_code = "7075";
                String YYYYMMDD = format1.format(now);
                String HHMMSS = format2.format(now);
                String seq_num = Integer.toString(HistCnt);
                String doc_gubun = "ON";
                String doc_length = "0650";
                String response1 = "";
                String response2 = "";
                String filler = "";

                String grid_cnt = "001";
                String mem_prog_id = "A";
                String mem_service = "A1";
                String point_gunbun = "O";
                String point_kind = "";
                String point_accept = "";
                String member_store_code = "";
                String Member_store_idn_no = "30031802";
                String accept_gubun = "WB";
                String member_gubun = "1";
                String card_no = "";
                String card_code = "";

                String ci = sCiVal;
                String mem_id = "";
                String store_member_id = "";
                String password = "";
                String store_member_ip = "";
                String store_member_appect = "";
                String save_yn = "";
                String use_yn = "";
                String bussiness_tot_amt = "0";
                String point_gubun1 = "";
                String take_point1 = "";
                String use_point1 = "";
                String save_point1 = "";
                String point_gubun2 = "";
                String take_point2 = "";
                String use_point2 = "";
                String save_point2 = "";
                String service = "";
                String filler2 = "";
                String message1 = "";
                String message2 = "";
                String message3 = "";
                String message4 = "";
                String web_service = "N";
                String tmp = "";

                tmp = (new StringBuilder(String.valueOf(CommonUtils.fillEmpVal(4, doc_no, "L", " ")))).append(CommonUtils.fillEmpVal(4, doc_code, "L", " "))
                        .append(CommonUtils.fillEmpVal(8, YYYYMMDD, "L", " ")).append(CommonUtils.fillEmpVal(6, HHMMSS, "L", " ")).append(CommonUtils.fillEmpVal(10, seq_num, "R", "0"))
                        .append(CommonUtils.fillEmpVal(2, doc_gubun, "L", " ")).append(CommonUtils.fillEmpVal(4, doc_length, "L", " ")).append(CommonUtils.fillEmpVal(2, response1, "L", " "))
                        .append(CommonUtils.fillEmpVal(2, response2, "L", " ")).append(CommonUtils.fillEmpVal(8, filler, "L", " ")).append(CommonUtils.fillEmpVal(3, grid_cnt, "L", " "))
                        .append(CommonUtils.fillEmpVal(1, mem_prog_id, "L", " ")).append(CommonUtils.fillEmpVal(2, mem_service, "L", " ")).append(CommonUtils.fillEmpVal(1, point_gunbun, "L", " "))
                        .append(CommonUtils.fillEmpVal(3, point_kind, "L", " ")).append(CommonUtils.fillEmpVal(1, point_accept, "L", " ")).append(CommonUtils.fillEmpVal(6, member_store_code, "L", " "))
                        .append(CommonUtils.fillEmpVal(15, Member_store_idn_no, "L", " ")) .append(CommonUtils.fillEmpVal(2, accept_gubun, "L", " ")).append(CommonUtils.fillEmpVal(1, member_gubun, "L", " "))
                        .append(CommonUtils.fillEmpVal(16, card_no, "L", " ")).append(CommonUtils.fillEmpVal(4, card_code, "L", " ")).append(CommonUtils.fillEmpVal(88, ci, "L", " "))
                        .append(CommonUtils.fillEmpVal(9, mem_id, "L", " ")).append(CommonUtils.fillEmpVal(13, store_member_id, "L", " ")).append(CommonUtils.fillEmpVal(16, password, "R", " "))
                        .append(CommonUtils.fillEmpVal(1, save_yn, "L", " ")).append(CommonUtils.fillEmpVal(1, use_yn, "L", " ")).append(CommonUtils.fillEmpVal(10, bussiness_tot_amt, "R", "0"))
                        .append(CommonUtils.fillEmpVal(10, point_gubun1, "R", " ")).append(CommonUtils.fillEmpVal(10, take_point1, "R", "0")).append(CommonUtils.fillEmpVal(10, use_point1, "R", "0"))
                        .append(CommonUtils.fillEmpVal(10, save_point1, "R", "0")).append(CommonUtils.fillEmpVal(10, point_gubun2, "R", " ")).append(CommonUtils.fillEmpVal(10, take_point2, "R", "0"))
                        .append(CommonUtils.fillEmpVal(10, use_point2, "R", "0")).append(CommonUtils.fillEmpVal(10, save_point2, "R", "0")).append(CommonUtils.fillEmpVal(3, service, "R", " "))
                        .append(CommonUtils.fillEmpVal(117, filler2, "L", " ")).append(CommonUtils.fillEmpVal(64, message1, "L", " ")).append(CommonUtils.fillEmpVal(64, message2, "L", " "))
                        .append(CommonUtils.fillEmpVal(64, message3, "L", " ")).append(CommonUtils.fillEmpVal(64, message4, "L", " ")).append(CommonUtils.fillEmpVal(1, web_service, "L", " ")).toString();
                OCBinfo.put("OCBinfo", tmp);

                Map<String, Object> resultMap = socketOCBSend(OCBinfo);

                hmParam.put("rslMsg", resultMap.get("result"));
                dtptMap.setRowMaps(hmParam);
                mapOutDs.put("ds_output", dtptMap);
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
     * 대명라이프웨이 OCB정보 소켓전송
     *
     * @param pmParam Map<String, Object>
     * @return Map
     * @throws Exception
     */
    public Map<String, Object> socketOCBSend(Map<String, Object> pmParam) throws Exception {
        Socket socket = null;
        BufferedWriter bw = null;
        String recvRecord = "";
        String result = "";
        String message = "";
        String code = "";
        String acceptNo = "";
        String OCB = (String)pmParam.get("OCBinfo");

        String sOcbIp = basVlService.getBasVlAsString("ocb_ip");
        String sOCbPort = basVlService.getBasVlAsString("ocb_port");

        try
        {
            socket = new Socket(sOcbIp, Integer.parseInt(sOCbPort));
            System.out.println("socket : xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx> " + socket);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            pmParam.put("result", "connect");
            pmParam.put("message", "연결오류");
            return pmParam;
        }
        try
        {
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            bw.write(OCB);

            System.out.println("socket.isBound : xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx> " + socket.isBound());

            if(socket.isBound())
            {
                bw.flush();
                System.out.println("BufferedWriter_CHECK_1 : xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>");

                bw.newLine();
                System.out.println("BufferedWriter_CHECK_2 : xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>");

                InputStream in = null;
                byte buffer[] = new byte[10000];
                in = socket.getInputStream();
                System.out.println("BufferedWriter_CHECK_3 : xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>");

                int bytesRead = in.read(buffer);
                System.out.println("BufferedWriter_CHECK_4 : xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>");

                socket.close();
                System.out.println("bytesRead : xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx> " + bytesRead);

                recvRecord = new String(buffer, 0, bytesRead);
                code = recvRecord.substring(0, 4);
                result = recvRecord.substring(38, 42);

                if ("K101".equals(code)) {
                    acceptNo = recvRecord.substring(309, 318);
                    message = recvRecord.substring(318, 382);
                } else if("K111".equals(code)) {
                    message = recvRecord.substring(318, 382);
                } else if("K401".equals(code)) {
                    acceptNo = recvRecord.substring(309, 318);
                    message = recvRecord.substring(318, 382);
                } else if("K411".equals(code)) {
                    message = recvRecord.substring(318, 382);
                } else if("W401".equals(code)) {
                    message = recvRecord.substring(442, 507);
                }

                pmParam.put("recvRecord", recvRecord);
                pmParam.put("code", code);
                pmParam.put("resultcode", result);
                pmParam.put("message", recvRecord);
                pmParam.put("accept_no", acceptNo);

                if("0000".equals(result)) {
                    pmParam.put("result", "success");
                } else {
                    pmParam.put("result", "fail");
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
            pmParam.put("result", "getInputStream");
            pmParam.put("message", "getInputStream 타임오버 오류");
            return pmParam;
        }
        return pmParam;
    }
    
    /**
     * 고객 OCB 정보를 수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/update-ocb")
    public ModelAndView updateMemberOCB(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sMemNo = StringUtils.defaultString((String) mapInVar.get("mem_no"));
            String sOcbYn = StringUtils.defaultString((String) mapInVar.get("ocb_yn"));

            if ("".equals(sMemNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원아이디가 없습니다.");

                return modelAndView;
            }

            hmParam.put("mem_no", sMemNo);
            hmParam.put("ocb_yn", sOcbYn);

            ParamUtils.addSaveParam(hmParam);
            dlwNewTypeMainConsService.updateMemberOCB(hmParam);

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
     * 고객 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/memsave")
    public ModelAndView saveCustMemSave(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
                              
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
                                    
            if (listInDs.size() > 0) {
            	
            	Map<String, Object> hmParam = new HashMap<String, Object>();      
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                
                //고유번호
                String sMemNo = StringUtils.defaultString((String) hmParam.get("mem_no"));
                
                // 주소 등록 / 변경 여부 (주소관리등록 여부)                
                String sAddrYn = "N";                   
                
                //[HISTORY] 변경정보 등록 시 신규인지 업데이트인지 확인 (신규의 경우 회원변경이력은 x)                
                String sNewYn = "";
                
                //[HISTORY] 변경정보 등록 시 변경될 TABLE 확인 (MEMBER, MPA, CARD_MEM, CMS_MEM 등등)                
                String sInsertTbl = "MEMBER_DAMO";
                
                //[HISTORY] 필드명 배열생성       
                String sArrFieldNm = "";
                
                //[HISTORY] ASIS값 배열 생성                
                String sArrAsisVal = "";
                
                //[HISTORY] TOBE값 배열 생성                
                String sArrTobeVal = "";      
                
                if ("".equals(sMemNo)) {                	
                	// 신규체크 
                	sNewYn = "Y";
                	
                	// 주소 등록 여부 
                    if(!hmParam.get("home_zip").equals("")){
                    	sAddrYn = "Y";
                    }  
                    hmParam.put("AddrChgYn", sAddrYn);
                	
                    sMemNo = dlwNewTypeMainConsService.insertCustMemSave(hmParam);
                    
                    //신규 회원 mem_no 전달 
                    mapOutVar.put("r_mem_no", sMemNo);
                } else {
                	
                	Map<String, Object> marketParam = new HashMap<String, Object>();           
                	
                	// 수정등록
                	sNewYn = "N";
                	
                 	// 변경전 데이터SET
                	Map<String, Object> chbfProdParam = new HashMap<String, Object>();           
                	DataSetMap chbfProdDs = (DataSetMap)mapInDs.get("ds_old_input");
                	
                	// 변경후 필드 배열
                	String strUpdData =  mapInVar.get("pUpdData").toString();
                	String strSendYn =  mapInVar.get("pSendYn").toString();
                	String[] arrUpdData = strUpdData.split("\\|");
                	String updDataYn = ""; //변경된 필드만 업데이트 하기 위해 강제로 필드 생성(필드명_chk) 후 넘김
                	
                	// 변경 정보 이력 초기화
                	Map<String, Object> mChngAccntInfo = new HashMap<String, Object>();        
                	UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                	chbfProdParam = chbfProdDs.get(0);  
                	                	
                	for (int i = 0; i < arrUpdData.length; i++) {
                		  
                		// 변경된 필드를 알리기 위한 강제 필드 생성 
                		updDataYn = arrUpdData[i].toString() + "_chk";
                		hmParam.put(updDataYn, "Y");
                		  
                		// MAIN주소가 변경 되었는지 여부 확인
                		if(arrUpdData[i].equals("home_zip") || arrUpdData[i].equals("home_addr2")){
                			sAddrYn = "Y";                			  
                		}
                		  
                		hmParam.put("AddrChgYn", sAddrYn);
                		sArrAsisVal =  StringUtils.defaultString((String) chbfProdParam.get(arrUpdData[i])) + "&&|" + sArrAsisVal;		// ASIS DATA
                		sArrTobeVal = StringUtils.defaultString((String) hmParam.get(arrUpdData[i])) + "&&|" + sArrTobeVal;			// TOBE DATA
                		sArrFieldNm =  StringUtils.defaultString((String) arrUpdData[i]) + "&&|" + sArrFieldNm; 							// 필드명   
                	}
                	  
                	//member save
                	dlwNewTypeMainConsService.updateCustMemSave(hmParam);
                	  
                	if("Y".equals(strSendYn)) {
                		//마케팅 데이터 조회 
                		marketParam = dlwNewTypeMainConsService.getDlwMarketGroup(hmParam);
                		  
                		//마케팅 데이터 비교 후 틀리면 머지문 사용
                		String strMktYn = "";
                		String strDmYn = "";
                		String strEmYn = "";
                		  
                		if(hmParam.get("markt_agr_yn") == null) {
                			strMktYn = "N";
                		} else {
                			strMktYn = (String)hmParam.get("markt_agr_yn");
                		}
                		if(hmParam.get("dm_yn") == null) {
                			strDmYn = "N";
                		} else {
                			strDmYn = (String)hmParam.get("dm_yn");
                		}
                		if(hmParam.get("email_yn") == null) {
                			strEmYn = "N";
                		} else {
                			strEmYn = (String)hmParam.get("email_yn");
                		}
                		
                		if(marketParam != null && marketParam.size() > 0) {
                			
                			if(!marketParam.get("mkt_yn").equals(strMktYn)) {
                    			hmParam.put("mkt_gubun", "01");
                    			dlwNewTypeMainConsService.mergeMktInfo(hmParam);  
                    		} else if("Y".equals(marketParam.get("mkt_yn")) && "Y".equals(strMktYn)) {
                    			hmParam.put("gubun", "01");
                    			dlwNewTypeMainConsService.updateMktInfoRe(hmParam);
                    		}
                    		  
                    		if(!marketParam.get("dm_yn").equals(strDmYn)) {
                    			hmParam.put("mkt_gubun", "02");
                    			dlwNewTypeMainConsService.mergeMktInfo(hmParam);
                    		} else if("Y".equals(marketParam.get("dm_yn")) && "Y".equals(strDmYn)){
                    			hmParam.put("gubun", "02");
                    			dlwNewTypeMainConsService.updateMktInfoRe(hmParam);
                    		}
                    		  
                    		if(!marketParam.get("em_yn").equals(strEmYn)) {
                    			hmParam.put("mkt_gubun", "03");
                    			dlwNewTypeMainConsService.mergeMktInfo(hmParam);
                    		} else if("Y".equals(marketParam.get("em_yn")) && "Y".equals(strEmYn)){
                    			hmParam.put("gubun", "03");
                    			dlwNewTypeMainConsService.updateMktInfoRe(hmParam);
                    		}
                		} else {
                			if("Y".equals(hmParam.get("markt_agr_yn"))) {
                				hmParam.put("mkt_gubun", "01");
                    			dlwNewTypeMainConsService.mergeMktInfo(hmParam);    
                			}
                			  
							if("Y".equals(hmParam.get("dm_yn"))) {
								hmParam.put("mkt_gubun", "02");
                    			dlwNewTypeMainConsService.mergeMktInfo(hmParam);              				  
							}
							if("Y".equals(hmParam.get("email_yn"))) {
								hmParam.put("mkt_gubun", "03");
                    			dlwNewTypeMainConsService.mergeMktInfo(hmParam);  
							}
                		}
                	}
                	  
                	//member log save            		  
            		mChngAccntInfo.put("mem_no", sMemNo);
            		mChngAccntInfo.put("accnt_no", hmParam.get("accnt_no"));
            		mChngAccntInfo.put("loc", sInsertTbl);
            		mChngAccntInfo.put("newyn", sNewYn);                      
                    mChngAccntInfo.put("fieldnm", sArrFieldNm);
                    mChngAccntInfo.put("asisval", sArrAsisVal);
                    mChngAccntInfo.put("tobeval", sArrTobeVal);
                    mChngAccntInfo.put("emple_no", hmParam.get("rgsr_id"));
                    mChngAccntInfo.put("team_cd", oLoginUser.getTeamcd());      
                      
                    System.out.println("mChngAccntInfo 데이터 : " + mChngAccntInfo.toString());
                                                                                  	  
                	dlwNewTypeMainConsService.insertMemLogSave(mChngAccntInfo);
                	dlwAltrHistService.insertMemAccntAltrHistList(mChngAccntInfo);
                }
                
                //고객정보 변경 건중 주소의 경우 별도 주소관리를 해야하기 때문에 별도 MERGE-INSERT 프로세스 도입
                if(sAddrYn.equals("Y")){
                	dlwNewTypeMainConsService.updateAddrChageData(hmParam);
                }
          	  	                
                //2017.11.30 접속 로그////////////////////////////////////////////////////////////////////////////////
                DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

                if (listLogIn.size() > 0) {
                    hmParam = listLogIn.get(0);
                    commonService.insertLog(hmParam);
                }
                //////////////////////////////////////////////////////////////////////////////////////////////////////////            
            }
            
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
            
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }

        return modelAndView;
    }
    
    /**
     * 상담 그룹 정보를 검색한다.
     *
     * @param psConsnoGropNo String
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/group/{consnogropno}")
    public ModelAndView getConsGroup(XPlatformMapDTO xpDto, Model model, @PathVariable("consnogropno") String psConsnoGropNo) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            List<Map<String, Object>> mList = dlwNewTypeMainConsService.getConsGroup(psConsnoGropNo);
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
     * 상담 정보를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list/{pagetype}")
    public ModelAndView getConsList(@PathVariable("pagetype") String psPathType, XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
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

                String sDeptYn = StringUtils.defaultString((String) hmParam.get("dept_yn"));

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

                // 상담관리(상담사용) - 본인 담당인 상담만 조회
                if ("usr".equals(psPathType)) {
                    hmParam.put("user_typ", "chpr_id");
                    hmParam.put("user_id", oLoginUser.getUserid());
                }
                // 상담업무 > 상담현황 - 당일 접수된 본인 담당인 상담만 조회
                else if ("today".equals(psPathType)) {
                    hmParam.put("user_typ", "chpr_id");
                    hmParam.put("user_id", oLoginUser.getUserid());

                    hmParam.put("dt_typ", "today");
                }

                // 경로 구분 저장
                hmParam.put("path_typ", psPathType);

                if ("Y".equals(sDeptYn)) {
                    hmParam.put("paramEmpleNo", oLoginUser.getUserid());
                    hmParam.put("paramAs", "A");
                    String dataAthrQury = StringUtils.defaultString((String) dataAthrQuryService.getDataAthrQury(hmParam));
                    if (!" ".equals(dataAthrQury)) {
                        dataAthrQury = dataAthrQury.replace("AND", "AND (");
                        dataAthrQury += "OR A.DEPT_CD IS NULL)";
                        hmParam.put("dataAthrQury", dataAthrQury);
                    }
                }

                ParamUtils.addCenterParam(hmParam);

                String excel_fg = (String)mapInVar.get("excel_fg");
                if (!"Y".equals(excel_fg)) {
                    int nTotal = dlwNewTypeMainConsService.getConsCount(hmParam);

                    mapOutVar.put("tc_cons", nTotal);

//                    List<Map<String, Object>> mList = consService.getConsList(hmParam);
//
//                    if ("hstr-sngl".equals(psPathType)) {
//                        listMap.setRowMaps(mList.get(0));
//                    }else{
//                        listMap.setRowMaps(mList);
//                    }
                    mapOutDs.put("ds_output", listMap);
                }

                if (null == hmParam.get("dataAthrQury")) {
                    hmParam.put("dataAthrQury", "");
                }
                listMap2.setRowMaps(hmParam);
                mapOutDs.put("ds_output_excel", listMap2);
           }

            // 2017.12.15 관리업무 - 상담이력관리(상담사용, 관리자용) 화면에서 조회한 경우만 로그기록
            if ("usr".equals(psPathType) || "admin".equals(psPathType)) {

                //2017.12.11 접속 로그////////////////////////////////////////////////////////////////////////////////
                DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

                if (listLogIn.size() > 0) {
                    hmParam = listLogIn.get(0);
                    commonService.insertLog(hmParam);
                }
                //////////////////////////////////////////////////////////////////////////////////////////////////////
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
     * 캠페인발신이력 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/ib/list")
    @ResponseBody
    public ModelAndView getIbDpmsReslHstrList(XPlatformMapDTO xpDto, Model mode) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            //조회조건
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

            ParamUtils.addCenterParam(hmParam);
            int nTotal = dlwNewTypeMainConsService.getIbDpmsReslHstrCount(hmParam);

            List<Map<String, Object>> mDpmsReslHstrList = dlwNewTypeMainConsService.getIbDpmsReslHstrList(hmParam);

            listMap.setRowMaps(mDpmsReslHstrList);
            mapOutVar.put("total_count", nTotal);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());

        }catch(Exception e){
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /**
     * VOC 정보를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list2/{pageType}")
    public ModelAndView getVocDtlList(@PathVariable("pageType") String psPathType, XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            //조회조건
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

            ParamUtils.addCenterParam(hmParam);

            // VOC 해피콜 처리 이력 조회인 경우
            if ("happycall".equals(psPathType)) {
                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                hmParam.put("hpcl_trgt_yn", "Y"); // 해피콜 대상 여부 : Y
                hmParam.put("hpcl_dssr_id", oLoginUser.getUserid()); // 해피콜 처리자 : 본인
                hmParam.put("hpcl_dsps_stat_cd_list", "'10','20'"); // 해피콜 처리상태 : 접수, 진행중
                hmParam.put("voc_dsps_stat_cd", "30"); // VOC 처리 상태 코드 : 처리완료
            }

            // 호출 경로 구분 저장
            hmParam.put("path_typ", psPathType);
            hmParam.put("excel_fg", (String)mapInVar.get("excel_fg"));

            int nTotal = dlwNewTypeMainConsService.getVocDtlCount(hmParam);
            mapOutVar.put("tc_voc", nTotal);

            List<Map<String, Object>> mList = dlwNewTypeMainConsService.getVocDtlList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2017.12.14 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());

        }catch(Exception e){
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /**
     * SMS 전송 이력 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/smsList")
    public ModelAndView readSmsSendAllExcel(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            //조회조건
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                // 엑셀 여부 확인
                String sExcelYn = "N";
                if (mapInVar != null) {
                    sExcelYn = StringUtils.defaultString((String)mapInVar.get("excel_yn"));
                }

                // 페이징 정보
                if (!"Y".equals(sExcelYn)) {
                    DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                    if (listInDs != null && listInDs.size() > 0) {
                        Map pMap = listInDs.get(0);
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                ParamUtils.addCenterParam(hmParam);

                int nTotal = dlwNewTypeMainConsService.getChatSndgHstrCount(hmParam);
                mapOutVar.put("tc_chat_sndg_hstr", nTotal);

                List<Map<String, Object>> mList = dlwNewTypeMainConsService.getChatSndgHstrList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2017.12.14 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());

        }catch(Exception e){
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 상담 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dlw-cons-list")
    public ModelAndView getDlwConsList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                String sMemNo = StringUtils.defaultString((String) hmParam.get("mem_no"));
                if ("".equals(sMemNo)) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "엔컴 상담 조회대상 고객번호가 없습니다.");

                    return modelAndView;
                }

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                ParamUtils.addCenterParam(hmParam);
                int nTotal = dlwNewTypeMainConsService.getDlwConsCount(hmParam);

                mapOutVar.put("tc_cons", nTotal);

                List<Map<String, Object>> mList = dlwNewTypeMainConsService.getDlwConsList(hmParam);

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
     * 홈페이지 웹  회원 정보 수정 이력을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dlw-lifewey-list")
    public ModelAndView getDlwlifeweyList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                String sMemNo = StringUtils.defaultString((String) hmParam.get("mem_no"));
                if ("".equals(sMemNo)) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "고객번호가 없습니다.");
                    return modelAndView;
                }

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                ParamUtils.addCenterParam(hmParam);
                int nTotal = dlwNewTypeMainConsService.getDlwlifeweyCount(hmParam);

                mapOutVar.put("tc_cons", nTotal);

                List<Map<String, Object>> mList = dlwNewTypeMainConsService.getDlwlifeweyList(hmParam);

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
     * 상담-상품 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getDlwConsProdList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                List<Map<String, Object>> mList = dlwNewTypeMainConsService.getDlwConsProdList(hmParam);

                if ("Y".equals(hmParam.get("etc_yn"))) { // 기타 문의 추가 여부
                    Map<String, Object> mTmp = new HashMap<String, Object>();

                    mTmp.put("accnt_no", "00000");
                    mTmp.put("prod_nm", "기타문의");

                    mList.add(mTmp);
                }

                listMap.setRowMaps(mList);

                mapOutVar.put("tc_consProd", mList.size());
                mapOutDs.put("ds_output", listMap);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }

        return modelAndView;
    }
    
    /**
     * 상담-상품 DTL정보
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list_dtl")
    public ModelAndView getDlwConsProdListDtl(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        Map<String, Object> authParam = new HashMap<String, Object>();

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

                List<Map<String, Object>> mList = dlwNewTypeMainConsService.getDlwConsProdListDtl(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
                                               
                //전자서명 대상자 확인 
                String strNice_no = ""; 
                String strAuth_01 = "";
                String strAuth_02 = "";
                
                authParam = listMap.get(0);                        
                Map<String, Object> listNiceAuth = dlwNewTypeMainConsService.getNiceAuthStat(authParam);
                
                if (listNiceAuth != null){
                    strNice_no = String.valueOf(listNiceAuth.get("NICE_NO"));
                    strAuth_01 = String.valueOf(listNiceAuth.get("AUTH_01"));
                    strAuth_02 = String.valueOf(listNiceAuth.get("AUTH_02"));
                }

                mapOutVar.put("gv_niceNo", strNice_no);
                mapOutVar.put("gv_Auth01", strAuth_01);
                mapOutVar.put("gv_Auth02", strAuth_02);                
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }

        return modelAndView;
    }    
    
    /**
     * 해약금 정보 조회.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/resn-amt")
    public ModelAndView getResnPayInq(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
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

                int exprno = 0;
                int resnAmt = 0;
                int nmCount = 0;

                double accmPay = 0.0D;
                double mskPay = 0.0D;

                String sAccntNo = StringUtils.defaultString((String) hmParam.get("accnt_no"));
                String resnGubn = StringUtils.defaultString((String) hmParam.get("r_gubn"));

                Map <String, Object> mDtpt = dlwNewTypeMainConsService.getProdInfoInqNew(hmParam); // 회원 상품정보 조회 2018.01.30 김찬영 getProdInfoInq => getProdInfoInqNew 변경

                String sProdCd = StringUtils.defaultString((String) mDtpt.get("prod_cd"));

                int exprNo = Integer.parseInt(mDtpt.get("expr_no") + "");
                int prodAmt = Integer.parseInt(mDtpt.get("prod_amt") + "");
                int payAmt = Integer.parseInt(mDtpt.get("pay_amt") + "");
                int payCount = Integer.parseInt(mDtpt.get("pay_amt_count") + "");
                int dcAmt = Integer.parseInt(mDtpt.get("dc_amt") + "");

                String sJoinDt = (String)mDtpt.get("join_dt");

                payAmt -= dcAmt;

                nmCount = dlwNewTypeMainConsService.getNowMonthCount(sAccntNo); // 현재 회차 조회
                resnAmt = dlwNewTypeMainConsService.getResnAmt(sProdCd, sAccntNo, nmCount, sJoinDt); // 해약환급금

                if(resnAmt < 0) {
                    if(payCount <= 60) {
                        accmPay = Math.round((double)payAmt - (double)payAmt * 0.10000000000000001D);
                        if(exprNo >= 60) exprno = 60;
                        else exprno = exprNo;

                        double temp1 = (exprno - payCount) + 1;
                        double temp2 = temp1 / (double)exprno;
                        double temp3 = (double)prodAmt * 0.153D;
                        mskPay = Math.round((temp2 * temp3) / 10.0D) * 10L;
                        double temp4 = Math.round((accmPay - mskPay) * 0.90000000000000002D);
                        resnAmt = (int)temp4;
                        resnAmt = (resnAmt / 1000) * 1000;
                        if(resnAmt <= 0) resnAmt = 0;
                    } else {
                        int x = payCount - 60;
                        double temp6 = 80.5D + (double)x * 0.316D;
                        double temp8 = ((double)payAmt * temp6) / 100D;
                        resnAmt = (int)Math.floor(temp8);
                        resnAmt = (resnAmt / 1000) * 1000;
                        if(resnAmt <= 0) resnAmt = 0;
                    }
                } else {
                    resnAmt = (int)Math.floor(resnAmt);
                }

                int chk = dlwNewTypeMainConsService.getResnGubn(hmParam);

                if(chk == 1 || "02".equals(resnGubn)) {
                    hmParam.put("resn_amt", Integer.valueOf(payAmt));
                    hmParam.put("resn_gubn", "02");
                } else {
                    hmParam.put("resn_amt", Integer.valueOf(resnAmt));
                    hmParam.put("resn_gubn", "01");
                }

                System.out.println("XXXXXLIMDONGJINXXXXX" +  hmParam);

                //전자서명 인증값 넣어준다
                String strNice_no = "";
                String strAuth_01 = "";
                String strAuth_02 = "";

                Map<String, Object> listNiceAuth = dlwNewTypeMainConsService.getNiceAuthStat(hmParam);
                if (listNiceAuth != null){
                    strNice_no = String.valueOf(listNiceAuth.get("NICE_NO"));
                    strAuth_01 = String.valueOf(listNiceAuth.get("AUTH_01"));
                    strAuth_02 = String.valueOf(listNiceAuth.get("AUTH_02"));
                }

                dtptMap.setRowMaps(hmParam);
                mapOutDs.put("ds_output", dtptMap);

                mapOutVar.put("gv_niceNo", strNice_no);
                mapOutVar.put("gv_Auth01", strAuth_01);
                mapOutVar.put("gv_Auth02", strAuth_02);
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
     * 상품-리조트회원에 따른 부가서비스 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/srvc-list")
    public ModelAndView getProdSrvcMstList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                String sGubun = StringUtils.defaultString((String) hmParam.get("gubun"));
                String sPayGubun = StringUtils.defaultString((String) hmParam.get("pay_gubun"));

                String isChgPayGubun = "Y";
                if (!"".equals(sGubun) && !"inq".equals(sGubun)) {
                    if (dlwNewTypeMainConsService.getProdSrvcHistForMPAS(hmParam) > 0) {
                        isChgPayGubun = "N";
                        mapOutVar.put("isChgPayGubun", isChgPayGubun);

                        modelAndView.addObject(XPlatformConstant.ERROR_CODE, "0");
                        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "부가서비스 정보가 없습니다.");

                        return modelAndView;
                    }
                }

                if("00".equals(sPayGubun)) hmParam.put("mem_cl", "L");
                else if(!"".equals(sPayGubun)) hmParam.put("mem_cl", "T");

                List<Map<String, Object>> mList = dlwNewTypeMainConsService.getProdSrvcMstList(hmParam);

                listMap.setRowMaps(mList);

                mapOutVar.put("isChgPayGubun", isChgPayGubun);
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
     * 할인우대권 체크
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/chk-rand-num")
    public ModelAndView getRandNum(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sRandNum = StringUtils.defaultString((String) mapInVar.get("rand_num"));

            if ("".equals(sRandNum)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "할인우대권번호가 없습니다.");

                return modelAndView;
            }

            String sRslYn = dlwNewTypeMainConsService.validateDiscntPassNo(sRandNum); // 할인우대권 체크
            if ("Y".equals(sRslYn)) {
                String sNewChanGunsu = dlwNewTypeMainConsService.selectNewChanGunsuDPM(sRandNum); // 특별할인
                hmParam.put("new_chan_gunsu", sNewChanGunsu);
                hmParam.put("resultMsg", "success");
            } else {
                hmParam.put("resultMsg", "fail");
            }

            dtptMap.setRowMaps(hmParam);
            mapOutDs.put("ds_output", dtptMap);

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
     * 상담-상품 정보 선택에 따른 모델분류/모델명/상품종류 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/modl-list/{pagetype}")
    public ModelAndView getDlwConsProdModlList(@PathVariable("pagetype") String psPathType, XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap sublistMap = new DataSetMap();
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> mSubList = new ArrayList<Map<String, Object>>();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            hmParam.put("prod_cd", StringUtils.defaultString((String) mapInVar.get("prod_cd")));
            hmParam.put("prod_model_kind", StringUtils.defaultString((String) mapInVar.get("prod_kind")));
            hmParam.put("continued", StringUtils.defaultString((String) mapInVar.get("continued")));

            if ("modlTyp".equals(psPathType)) {
                mList = dlwNewTypeMainConsService.getDlwModlMstInfo(hmParam);

                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            } else if ("modlNm".equals(psPathType)) {
                mList = dlwNewTypeMainConsService.getDlwModlDtlInfo(hmParam);
                mSubList = dlwNewTypeMainConsService.getDlwProdKindList(hmParam);

                listMap.setRowMaps(mList);
                sublistMap.setRowMaps(mSubList);
                mapOutDs.put("ds_output", listMap);
                mapOutDs.put("ds_subOutput", sublistMap);
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
     * 회원 상품 정보 수정 시 체크 해야 할 사항 (청구중 및 등등)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/accnt_chk")
    public ModelAndView getAccntCheck(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));

            Map<String, Object> mDtpt = dlwNewTypeMainConsService.getAccntCheck(sAccntNo);
            if (mDtpt != null) {
                dtptMap.setRowMaps(mDtpt);
                mapOutDs.put("ds_output", dtptMap);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }

        return modelAndView;
    }
    
    /**
     * 리조트 정보 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/resort-info")
    public ModelAndView getResortInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sMemNo = StringUtils.defaultString((String) mapInVar.get("mem_no"));

            if ("".equals(sMemNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "회원의 고유번호가 없습니다.");

                return modelAndView;
            }

            Map<String, Object> mDtpt = dlwNewTypeMainConsService.getResortInfo(sMemNo);
            if (mDtpt != null) {
                dtptMap.setRowMaps(mDtpt);
                mapOutDs.put("ds_output", dtptMap);
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
     * 2구좌 가입 제한을 체크한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/no-sale-accnt")
    public ModelAndView getNoSaleAccnt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
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

                String sResultFlag = dlwNewTypeMainConsService.getNoSaleAccnt(hmParam);
                hmParam.put("result_flag", sResultFlag);
                dtptMap.setRowMaps(hmParam);
                mapOutDs.put("ds_output", dtptMap);
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
     * 상품정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/prod_save")
    public @ResponseBody ModelAndView saveMemProdAccnt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap subListInDs = (DataSetMap)mapInDs.get("ds_subInput");

            if (listInDs != null && listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);
                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                String sMemNo = StringUtils.defaultString((String) hmParam.get("mem_no"));
                String sAccntNo = StringUtils.defaultString((String) hmParam.get("accnt_no"));
                String sProdCd = StringUtils.defaultString((String) hmParam.get("prod_cd"));
                String sCtiCrncDtlId = StringUtils.defaultString((String) hmParam.get("cti_crnc_dtl_id"));
                String sDspsTypCd = "";
                
                String sInsertCnt = StringUtils.defaultString((String) hmParam.get("insert_cnt"));
                int iInsertCnt = 0;
                
                if(!"".equals(sInsertCnt)) 
                {
                	iInsertCnt = Integer.parseInt(sInsertCnt);
                }

                ParamUtils.addSaveParam(hmParam);

                if ("".equals(sAccntNo)) { 
                    sDspsTypCd = "I";  // 신규 등록
//                    String strMarktYn =  mapInVar.get("markt_agr_yn").toString();
//                    
//                    hmParam.put("markt_agr_yn", strMarktYn);
                    
                    if(iInsertCnt > 0)
                    {
                    	for(int idx = 0; idx < iInsertCnt; idx++) 
                    	{
                    		// 회원번호 발행
                    		sAccntNo = dlwNewTypeMainConsService.createAccntNo(sProdCd);
                    		hmParam.put("accnt_no", sAccntNo);
                        
                    		// 회원_상품_계좌 등록
                    		dlwNewTypeMainConsService.insertMemProdAccnt(hmParam);
                    	}
                    }
                    else
                    {
                    	// 회원번호 발행
                    	sAccntNo = dlwNewTypeMainConsService.createAccntNo(sProdCd);
                		hmParam.put("accnt_no", sAccntNo);
                    
                		// 회원_상품_계좌 등록
                		dlwNewTypeMainConsService.insertMemProdAccnt(hmParam);
                    }
                    
//                    String sMtPr = dlwNewTypeMainConsService.insertSmsSend(hmParam);
//                    hmParam.put("sndg_no", sMtPr);
//                    hmParam.put("sndg_sqnc", 0);
//                    dlwNewTypeMainConsService.insertChatSndgHstr(hmParam);
                                                                                
                    /*
                    if(iInsertCnt > 0){
                        for(int k=0; k<iInsertCnt; k++) { //단체건수

                            hmParam.put("membership", dlwNewTypeMainConsService.getCardCode(sProdCd));   // 카드코드 조회

                            sAccntNo = dlwNewTypeMainConsService.createAccntNo(sProdCd);
                            hmParam.put("accnt_no", sAccntNo);

                            dlwNewTypeMainConsService.insertMemProdAccnt(hmParam);            // 회원_상품_계좌 등록

                            if ("S7".equals(sProdCd) || "S8".equals(sProdCd) || "S9".equals(sProdCd) || "T0".equals(sProdCd) || "X5".equals(sProdCd) || "X6".equals(sProdCd)) {
                                hmParam.put("issue_stat", "0001");
                                hmParam.put("cnsl_stat", "0001");
                                dlwNewTypeMainConsService.insertSmartLifeCnslMng(hmParam);    // 스마트라이프 상담등록
                            }

                            if (subListInDs != null && subListInDs.size() > 0) {
                                for (int i = 0; i < subListInDs.size(); i++) {
                                    Map subHmParam = subListInDs.get(i);
                                    String sChk = StringUtils.defaultString((String) subHmParam.get("_chk"));

                                    if ("1".equals(sChk)) {
                                        ParamUtils.addSaveParam(subHmParam);

                                        subHmParam.put("accnt_no", sAccntNo);
                                        dlwNewTypeMainConsService.insertMemProdAccntSvc(subHmParam); // 부가서비스 등록
                                    }
                                }
                            }
							*/
                            /* 20181105 수정사항
                             * 두구좌 상품인 경우 자동으로 구좌상품 하나를 더 등록하도록 함.
                             * 회원번호, 모델분류, 모델명, 상품종류를 제외한 나머지 값들은 모두 동일하게 저장됨.
                             */
                    	/*
                            if("EI".equals(sProdCd))
                            {
                                Map<String, Object> saveTwinAccntParam = new HashMap<String, Object>();
                                ParamUtils.addSaveParam(saveTwinAccntParam);
                                saveTwinAccntParam.put("mem_no", sMemNo);
                                saveTwinAccntParam.put("twin_accnt_no1", sAccntNo);
                                saveTwinAccntParam.put("prod_cd1", "EI");

                                hmParam.put("prod_cd", "EJ"); // EI상품일경우 두구좌의 해당 상품인 EJ를 자동으로 등록하도록 함.
                                sProdCd = StringUtils.defaultString((String) hmParam.get("prod_cd"));

                                hmParam.put("membership", dlwNewTypeMainConsService.getCardCode(sProdCd));   // EJ 상품 카드코드 조회 (멤버쉽)
                                sAccntNo = dlwNewTypeMainConsService.createAccntNo(sProdCd);                 // 해당 등록상품의 EJ 회원번호 추출
                                hmParam.put("accnt_no", sAccntNo);
                                hmParam.put("prod_model_kind", "0800");
                                hmParam.put("prod_model_cd", "");
                                hmParam.put("prod_kind", "");
                                dlwNewTypeMainConsService.insertMemProdAccnt(hmParam);

                                if (subListInDs != null && subListInDs.size() > 0) {
                                    for (int i = 0; i < subListInDs.size(); i++) {
                                        Map subHmParam = subListInDs.get(i);
                                        String sChk = StringUtils.defaultString((String) subHmParam.get("_chk"));

                                        if ("1".equals(sChk)) {
                                            ParamUtils.addSaveParam(subHmParam);

                                            subHmParam.put("accnt_no", sAccntNo);
                                            dlwNewTypeMainConsService.insertMemProdAccntSvc(subHmParam); // 부가서비스 등록
                                        }
                                    }
                                }

                                saveTwinAccntParam.put("twin_accnt_no2", sAccntNo);
                                saveTwinAccntParam.put("prod_cd2", sProdCd);
                                dlwNewTypeMainConsService.insertMemTwinAccnt(saveTwinAccntParam);
                            }
                        }
                    } else {
                        hmParam.put("membership", dlwNewTypeMainConsService.getCardCode(sProdCd));   // 카드코드 조회

                        sAccntNo = dlwNewTypeMainConsService.createAccntNo(sProdCd);
                        hmParam.put("accnt_no", sAccntNo);

                        //pay_perd                : 납입주기 사용안함
                        //post_mtr_rcv            : 우편수령 사용안함
                        //pay_stat_1no            : 1회분 납입상태 사용안함
                        //before_dc_cnt           : 선할인 사용안함(추가시 기본 0 설정)

                        //ded_no update 어디에서 하는지 확인
                        //resort_mem_gubun 값 들어오는지 확인

                        dlwNewTypeMainConsService.insertMemProdAccnt(hmParam);            // 회원_상품_계좌 등록

                        if ("S7".equals(sProdCd) || "S8".equals(sProdCd) || "S9".equals(sProdCd) || "T0".equals(sProdCd) || "X5".equals(sProdCd) || "X6".equals(sProdCd)) {
                            hmParam.put("issue_stat", "0001");
                            hmParam.put("cnsl_stat", "0001");
                            dlwNewTypeMainConsService.insertSmartLifeCnslMng(hmParam);    // 스마트라이프 상담등록
                        }

                        if (subListInDs != null && subListInDs.size() > 0) {
                            for (int i = 0; i < subListInDs.size(); i++) {
                                Map subHmParam = subListInDs.get(i);
                                String sChk = StringUtils.defaultString((String) subHmParam.get("_chk"));

                                if ("1".equals(sChk)) {
                                    ParamUtils.addSaveParam(subHmParam);

                                    subHmParam.put("accnt_no", sAccntNo);
                                    dlwNewTypeMainConsService.insertMemProdAccntSvc(subHmParam); // 부가서비스 등록
                                }
                            }
                        }
						*/
                        /* 20181105 수정사항
                         * 두구좌 상품인 경우 자동으로 구좌상품 하나를 더 등록하도록 함.
                         * 회원번호, 모델분류, 모델명, 상품종류를 제외한 나머지 값들은 모두 동일하게 저장됨.
                         */
                    	/*
                        if("EI".equals(sProdCd))
                        {
                            Map<String, Object> saveTwinAccntParam = new HashMap<String, Object>();
                            ParamUtils.addSaveParam(saveTwinAccntParam);
                            saveTwinAccntParam.put("mem_no", sMemNo);
                            saveTwinAccntParam.put("twin_accnt_no1", sAccntNo);
                            saveTwinAccntParam.put("prod_cd1", sProdCd);

                            hmParam.put("prod_cd", "EJ"); // EI상품일경우 두구좌의 해당 상품인 EJ를 자동으로 등록하도록 함.
                            sProdCd = StringUtils.defaultString((String) hmParam.get("prod_cd"));

                            hmParam.put("membership", dlwNewTypeMainConsService.getCardCode(sProdCd));   // EJ 상품 카드코드 조회 (멤버쉽)
                            sAccntNo = dlwNewTypeMainConsService.createAccntNo(sProdCd);                 // 해당 등록상품의 EJ 회원번호 추출
                            hmParam.put("accnt_no", sAccntNo);
                            hmParam.put("prod_model_kind", "0800");
                            hmParam.put("prod_model_cd", "");
                            hmParam.put("prod_kind", "");
                            dlwNewTypeMainConsService.insertMemProdAccnt(hmParam);

                            if (subListInDs != null && subListInDs.size() > 0) {
                                for (int i = 0; i < subListInDs.size(); i++) {
                                    Map subHmParam = subListInDs.get(i);
                                    String sChk = StringUtils.defaultString((String) subHmParam.get("_chk"));

                                    if ("1".equals(sChk)) {
                                        ParamUtils.addSaveParam(subHmParam);

                                        subHmParam.put("accnt_no", sAccntNo);
                                        dlwNewTypeMainConsService.insertMemProdAccntSvc(subHmParam); // 부가서비스 등록
                                    }
                                }
                            }

                            saveTwinAccntParam.put("twin_accnt_no2", sAccntNo);
                            saveTwinAccntParam.put("prod_cd2", sProdCd);
                            dlwNewTypeMainConsService.insertMemTwinAccnt(saveTwinAccntParam);
                        }
                    }
                    */
                } else {                   
                	// 수정
                	String sAddrYn = "N"; 			// 주소 변경 여부
                	String sArrAsisVal = "";			// 변경 전 데이터 
                	String sArrTobeVal = "";		// 변경 후 데이터
                	String sArrFieldNm = "";		// 필드명
                	
                	String sAuthChk = "N";		// 상품 승인 체크 (상품 승인 값 변경 시 승인 저장 프로세스 구동)
                	String sAuthVal = "";		// 상품 승인 값
                	
                	String sDlvChk = "N";		// 발주 정보 수정 체크(설치 주소 변경 시 발주 테이블 주소 수정)
                	
                    //[HISTORY] 변경정보 등록 시 변경될 TABLE 확인 (MEMBER, MPA, CARD_MEM, CMS_MEM 등등)                
                    String sInsertTbl = "MEM_PROD_ACCNT_DAMO";
                	
                    sDspsTypCd = "U";
                    
                	// 변경전 데이터SET
                    Map<String, Object> chbfProdParam = new HashMap<String, Object>();      
                    DataSetMap chbfProdDs = (DataSetMap)mapInDs.get("ds_prod_old_input");
                	
                	// 변경후 필드 배열
                	String strUpdData =  mapInVar.get("pUpdData").toString();
                	String[] arrUpdData = strUpdData.split("\\|");
                	String updDataYn = ""; //변경된 필드만 업데이트 하기 위해 강제로 필드 생성(필드명_chk) 후 넘김
                	
                	// 변경 정보 이력 초기화                	
                	Map<String, Object> mChngAccntInfo = new HashMap<String, Object>();                	                
                	chbfProdParam = chbfProdDs.get(0);  
                	                	
                	for (int i = 0; i < arrUpdData.length; i++) {					  
                		// 변경된 필드를 알리기 위한 강제 필드 생성 
                		updDataYn = arrUpdData[i].toString() + "_chk";
                		hmParam.put(updDataYn, "Y");
				  
                		hmParam.put("AddrChgYn", sAddrYn);
                		sArrAsisVal =  StringUtils.defaultString((String) chbfProdParam.get(arrUpdData[i])) + "&&|" + sArrAsisVal;		// ASIS DATA
                		sArrTobeVal = StringUtils.defaultString((String) hmParam.get(arrUpdData[i])) + "&&|" + sArrTobeVal;			// TOBE DATA
                		sArrFieldNm =  StringUtils.defaultString((String) arrUpdData[i]) + "&&|" + sArrFieldNm; 							// 필드명   
                		
                		//상품 승인(구 해피콜) 값이 변경되었으면 승인 변경 체크 
                		if (StringUtils.defaultString((String) arrUpdData[i]).equals("hpcl_stat_cd")){
                			sAuthChk = "Y";
                			sAuthVal =  StringUtils.defaultString((String) hmParam.get(arrUpdData[i]));                			 
                		}
                		
                		// 발주 정보 수정 체크(설치 주소 변경 시 발주 테이블 주소 수정)
                		if (StringUtils.defaultString((String) arrUpdData[i]).equals("inspl_zip") || StringUtils.defaultString((String) arrUpdData[i]).equals("inspl_addr2")){
                			sDlvChk = "Y";                			            			 
                		}                		
                	}
                	
                	// 상품 승인(구 해피콜) 값이 변경여부 파라미터
                	hmParam.put("auth_chk", sAuthChk);
                	hmParam.put("hpcl_stat_cd", sAuthVal);
                	                	             					  
                	// ***************회원_상품_계좌 수정*********************************************************
                	dlwNewTypeMainConsService.updateMemProdAccnt(hmParam);    
                	// ********************************************************************************************
				  
                	// *****************************MEMBER LOG SAVE*******************************************
                	mChngAccntInfo.put("mem_no", sMemNo);						// 고유번호
                	mChngAccntInfo.put("accnt_no", sAccntNo);					// 회원번호
                	mChngAccntInfo.put("loc", sInsertTbl);							// 자동상담등록위치(회원OR상품)
                	mChngAccntInfo.put("newyn", "N");              				// 입력 OR 업데이트
                	mChngAccntInfo.put("fieldnm", sArrFieldNm);					// 필드
                	mChngAccntInfo.put("asisval", sArrAsisVal);						// 이전값
                	mChngAccntInfo.put("tobeval", sArrTobeVal);					// 변경값
                	mChngAccntInfo.put("emple_no", hmParam.get("rgsr_id"));
                	mChngAccntInfo.put("team_cd", oLoginUser.getTeamcd());        
                	
                	System.out.println("mChngAccntInfo 데이터 : " + mChngAccntInfo.toString());
                	
                	dlwNewTypeMainConsService.insertMemLogSave(mChngAccntInfo);       
                	dlwAltrHistService.insertMemAccntAltrHistList(mChngAccntInfo);
                	// *********************************************************************************************
                	
                	// ***********발주 정보 수정 체크(설치 주소 변경 시 발주 테이블 주소 수정)********************
                    if ("Y".equals(sDlvChk)) {
                        String scaddr=  "";
                        scaddr =  (new StringBuilder((String)hmParam.get("inspl_zip"))).append(")").append((String)hmParam.get("inspl_addr")).append(" ").append((String)hmParam.get("inspl_addr2")).toString();
                        hmParam.put("scaddr", scaddr);       // 설치장소변경데이터 넣구

                        // 있는지 체크함  있으면 업데이트 없으면 노업데이트 ㅋ
                        int nTotal = dlwNewTypeMainConsService.getdeliveryCnt(hmParam);

                        if (nTotal  == 1) {   /// 건수가 1건이면 업데이트(동기화)  그외것들은  패스 (발주목록은 각회원번호데이터가 무조건 1건이다)
                            dlwNewTypeMainConsService.updatedelivery(hmParam);             // 발주테이블.. 설치주소 업데이트
                        }
                    }
                    //**********************************************************************************************
                	
                	
                	/*
                    dlwNewTypeMainConsService.deleteMemProdAccntSvc(sAccntNo);         // 부가서비스 삭제

                    if (subListInDs != null && subListInDs.size() > 0) {
                        for (int i = 0; i < subListInDs.size(); i++) {
                            Map subHmParam = subListInDs.get(i);
                            String sChk = StringUtils.defaultString((String) subHmParam.get("_chk"));

                            if ("1".equals(sChk)) {
                                ParamUtils.addSaveParam(subHmParam);

                                subHmParam.put("accnt_no", sAccntNo);
                                dlwNewTypeMainConsService.insertMemProdAccntSvc(subHmParam); // 부가서비스 등록
                            }
                        }
                    }

                    String[] arrAccntCol = {"emple_no", "bef_emple_cd", "prod_model_kind", "prod_model_cd", "prod_kind"
                                          , "bank_accnt_no", "pay_mthd", "join_dt", "join_cl", "new_chan_gunsu"
                                          , "b2b_comp_cd", "b2b_emple_no", "sm_shop_info", "inspl_zip", "inspl_addr"
                                          , "inspl_addr2", "inspl_phone", "pay_gubun", "resort_mem_no", "resort_mem_nm"
                                          , "id_no", "kb_no", "rand_num", "order_num", "ja_subscrpt_yn"
                                          , "ocb_reg_yn", "hpcl_stat_nm", "main_contract_nm"};

                    String[] arrAccntColNm = {"담당사원", "모집사원", "모델분류", "모델명", "상품종류"
                                            , "계좌번호", "납입방법", "가입일자", "가입구분", "특별할인"
                                            , "B2B회사", "B2B사원코드", "매장정보", "설치장소우편번호", "설치장소주소"
                                            , "설치장소상세주소", "설치장소연락처", "리조트구분", "리조트번호", "리조트회원명"
                                            , "ID NO", "KB NO", "할인우대권", "주문번호", "구독여부"
                                            , "OCB여부", "해피콜상태", "주계약"};

                    String sCnslCon = "";

                    if (chbfProdDs != null && chbfProdDs.size() > 0) {
                        Map chbfProdParam = chbfProdDs.get(0);
                        Map<String, Object> mChngAccntInfo = new HashMap<String, Object>();
                        ParamUtils.addSaveParam(mChngAccntInfo);
                        String tgubun = "";
                        for (int i = 0; i < arrAccntCol.length; i++) {
                            String sChbfProdInfo = StringUtils.defaultString((String) chbfProdParam.get(arrAccntCol[i]));
                            String sProdInfo = StringUtils.defaultString((String)hmParam.get(arrAccntCol[i]));

                            if (!sChbfProdInfo.equals(sProdInfo)) {
                                mChngAccntInfo.put("mem_no", sMemNo);
                                mChngAccntInfo.put("accnt_no", sAccntNo);
                                mChngAccntInfo.put("cl1", "U");
                                mChngAccntInfo.put("dat1", arrAccntColNm[i]);                  // 필드명

                                String sDat2 = StringUtils.defaultString((String)chbfProdParam.get(arrAccntCol[i]));
                                if ("".equals(sDat2)) {
                                    sDat2 = "빈값";
                                }
                                String sDat3 = StringUtils.defaultString((String)hmParam.get(arrAccntCol[i]));
                                if ("".equals(sDat3)) {
                                    sDat3 = "빈값";
                                }
             
                                if ("설치장소우편번호".equals(arrAccntColNm[i])  || "설치장소주소".equals(arrAccntColNm[i]) || "설치장소상세주소".equals(arrAccntColNm[i]) ) {
                                      String sc_data = StringUtils.defaultString((String) chbfProdParam.get(arrAccntCol[i]));
                                      String sc_data_new = StringUtils.defaultString((String)hmParam.get(arrAccntCol[i]));
                                      if (!sc_data.equals(sc_data_new)) {
                                          tgubun = "1";
                                      }
                                }

                                mChngAccntInfo.put("dat2", sDat2); // 변경전값
                                mChngAccntInfo.put("dat3", sDat3);       // 변경후값

                                sCnslCon = (new StringBuilder(String.valueOf(sCnslCon))).append((String)mChngAccntInfo.get("dat1")).append(": ").append(sDat2).append(" -> ").append(sDat3).append("\n").toString();
                                dlwNewTypeMainConsService.insertReqUpdDelInf(mChngAccntInfo);         // 변경 삭제 내역 등록
                            }
                        }
     
                        if ("1".equals(tgubun)) {
                             String scaddr=  "";
                             scaddr =  (new StringBuilder((String)hmParam.get("inspl_zip"))).append(")").append((String)hmParam.get("inspl_addr")).append(" ").append((String)hmParam.get("inspl_addr2")).toString();
                             hmParam.put("scaddr", scaddr);       // 설치장소변경데이터 넣구

                             // 있는지 체크함  있으면 업데이트 없으면 노업데이트 ㅋ
                             int nTotal = dlwNewTypeMainConsService.getdeliveryCnt(hmParam);

                             if (nTotal  == 1) {   /// 건수가 1건이면 업데이트(동기화)  그외것들은  패스 (발주목록은 각회원번호데이터가 무조건 1건이다)
                                 dlwNewTypeMainConsService.updatedelivery(hmParam);             // 발주테이블.. 설치주소 업데이트
                             }

                        }

                        if (!"".equals(sCnslCon)) {
                            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

                            mChngAccntInfo.put("mem_no", sMemNo);
                            mChngAccntInfo.put("accnt_no", sAccntNo);
                            mChngAccntInfo.put("cons_memo_tit", "회원정보수정 정보");
                            mChngAccntInfo.put("cons_memo_cntn", sCnslCon);
                            mChngAccntInfo.put("gubn", "80");
                            mChngAccntInfo.put("cnsl_cd", "01");
                            mChngAccntInfo.put("cnsl_man", oLoginUser.getUserid());

                            mChngAccntInfo.put("consno_sqno", "1");                       // 순번
                            mChngAccntInfo.put("actp_id", oLoginUser.getUserid());        // 접수자ID
                            mChngAccntInfo.put("cons_stat_cd", "30");                     // 처리상태
                            mChngAccntInfo.put("cons_dspsmddl_dtpt_cd", "10");            // 처리방법
                            mChngAccntInfo.put("rsps_dept_cd", oLoginUser.getTeamcd());   // 담당부서
                            mChngAccntInfo.put("chpr_id", oLoginUser.getUserid());        // 담당자ID

                            mChngAccntInfo.put("acpg_chnl_cd", "99");             		  // 접수채널 - 기타

                            String sAutoConsTyp3Cd = basVlService.getBasVlAsString("auto_cons_typ3_cd");
                            if ("".equals(sAutoConsTyp3Cd)) {
                                sAutoConsTyp3Cd = "CT01010201";
                            }
                            String sConsTyp1Cd = sAutoConsTyp3Cd.substring(0, 6) + "0000";
                            String sConsTyp2Cd = sAutoConsTyp3Cd.substring(0, 8) + "00";

                            mChngAccntInfo.put("cons_typ1_cd", sConsTyp1Cd);
                            mChngAccntInfo.put("cons_typ2_cd", sConsTyp2Cd);
                            mChngAccntInfo.put("cons_typ3_cd", sAutoConsTyp3Cd);
                            //mChngAccntInfo.put("cons_typ1_cd", "CT01010000");             // 상담유형1 - 회원관리
                            //mChngAccntInfo.put("cons_typ2_cd", "CT01010200");             // 상담유형2 - 회원관리정보변경
                            //mChngAccntInfo.put("cons_typ3_cd", "CT01010201");             // 상담유형3 - 기본정보변경

                            dlwNewTypeMainConsService.insertCons(mChngAccntInfo);                       // 엔컴 마스터 상담 및 상세 상담 등록, PS 상담등록
                        }
                    }
                    */
                } 
                
                // 녹취이력 저장
                if (!"".equals(sCtiCrncDtlId) && !"".equals(sAccntNo)) {
                    hmParam.put("dsps_typ_cd", sDspsTypCd);
                    dlwNewTypeMainConsService.mergeRecProdDtl(hmParam);
                }                
                mapOutVar.put("gv_accnt_no", sAccntNo);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }
        
        return modelAndView;
    }
    
    /**
     * 상품정보 삭제를 체크한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-req-srch")
    public ModelAndView getWdrwReqSrch(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));
            String result = "";
            String payResult = "";
            String cmsResult = "";
            String cardResult = "";

            cmsResult = dlwNewTypeMainConsService.getCmsWdrwReqChk(sAccntNo);
            cardResult = dlwNewTypeMainConsService.getCardWdrwReqChk(sAccntNo);

            if (("".equals(cmsResult) || cmsResult == null) && ("".equals(cardResult) || cardResult == null)) {
                result = "Y";
            } else {
                result = "N";
            }

            payResult = dlwNewTypeMainConsService.getPayChk(sAccntNo);

            if("N".equals(payResult) && "Y".equals(result)) {
                result = "F";
            }

            mapOutVar.put("wdrwReqSrchFlag", result);

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
     * 상품정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteMemProdAccnt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
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

                String sMemNo = StringUtils.defaultString((String) hmParam.get("mem_no"));
                String sAccntNo = StringUtils.defaultString((String) hmParam.get("accnt_no"));
                String sCtiCrncDtlId = StringUtils.defaultString((String) hmParam.get("cti_crnc_dtl_id"));
                String sEmpleNo = StringUtils.defaultString((String) hmParam.get("emple_no"));

                ParamUtils.addSaveParam(hmParam);
                hmParam.put("dat1", sMemNo);
                hmParam.put("cl1", "D");
                hmParam.put("emple_no", hmParam.get("amnd_id"));

                if ("".equals(sAccntNo)) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                    return modelAndView;
                }

                dlwNewTypeMainConsService.insertReqUpdDelInf(hmParam); // 변경 삭제 내역 등록

                // 녹취이력 저장
                if (!"".equals(sCtiCrncDtlId) && !"".equals(sAccntNo)) {
                    hmParam.put("dsps_typ_cd", "D");
                    hmParam.put("emple_no", sEmpleNo);
                    dlwNewTypeMainConsService.mergeRecProdDtl(hmParam);
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
     * 부가정보를 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/buga-info")
    public ModelAndView getBugaInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));
            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                return modelAndView;
            }

            hmParam.put("accnt_no", sAccntNo);
            List<Map<String, Object>> mList = dlwNewTypeMainConsService.getBugaInfo(hmParam);
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
     * 상품정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/hist/insert")
    public ModelAndView insertSearchHist(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

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
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("accnt_no", sAccntNo);

            //dlwConsProdService.insertSearchHist(hmParam);  조회이력은  안들어가게 처리함..

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
     * 납입정보(부가서비스)등록 회원을 체크한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/buga-srvc/chk")
    public ModelAndView getBugaSrvcMemChk(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));

            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  "회원번호가 없습니다.");

                return modelAndView;
            }

            hmParam.put("accnt_no", sAccntNo);

            UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();
            hmParam.put("paramEmpleNo", oUserSession.getUserid()); // emple_no
            hmParam.put("paramAs", "EMP");
            String dataAthrQury = dataAthrQuryService.getDataAthrQury(hmParam);
            hmParam.put("dataAthrQury", dataAthrQury);

            /*if ("1602".equals(oUserSession.getTeamcd())) {         // dept_cd
                hmParam.put("nsfg", "Y");
            } else {
                hmParam.put("nsfg", "N");
            }*/
            Map<String, Object> mEmpl = dlwNewTypeMainConsService.getDlwEmplDtpt(oUserSession.getUserid());
            if (mEmpl != null) {
                String sDeptCd = StringUtils.defaultString((String)mEmpl.get("dept_cd"));

                if ("1602".equals(sDeptCd)) {
                    hmParam.put("nsfg", "Y");
                } else {
                    hmParam.put("nsfg", "N");
                }

                hmParam.put("cmsfg", "Y");
                Map<String, Object> cmsMap = dlwNewTypeMainConsService.getBugaSrvcMemChk(hmParam);
                if (cmsMap != null) {
                    mapOutVar.put("gv_bugaCmsFg", "Y");
                } else {
                    mapOutVar.put("gv_bugaCmsFg", "N");
                }

                hmParam.put("cmsfg", "");
                hmParam.put("cardfg", "Y");
                Map<String, Object> cardMap = dlwNewTypeMainConsService.getBugaSrvcMemChk(hmParam);
                if (cmsMap != null) {
                    mapOutVar.put("gv_bugaCardFg", "Y");
                } else {
                    mapOutVar.put("gv_bugaCardFg", "N");
                }
            } else {
                mapOutVar.put("gv_bugaCmsFg", "N");
                mapOutVar.put("gv_bugaCardFg", "N");
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
    
    /* ================================================================
     * 날짜 : 20180809
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금정보 전체 조회
     * ================================================================
     * */
    @RequestMapping(value = "/getAcuonLatelyAuth")
    public ModelAndView getAcuonLatelyAuth(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input1");

            if (srchInDs != null && srchInDs.size() > 0)
            {
                hmParam = srchInDs.get(0);
            }

            List<Map<String, Object>> mList01 = dlwNewTypeMainConsService.getAcuonLatelyAuth(hmParam);
            listMap01.setRowMaps(mList01);
            mapOutDs.put("ds_output", listMap01);

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
     * 상담 저장시에 부가서비스와 키 동기화 시켜 주는 로직 수행
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/updateCallCenterVatSvcHist")
    public ModelAndView updateCallCenterVatSvcHist(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            ParamUtils.addCenterParam(hmParam);
            CommonUtils.printLog(hmParam);

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
                dlwNewTypeMainConsService.updateCallCenterVatSvcHist(hmParam);

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
    
    /* ================================================================
     * 날짜 : 20180220
     * 이름 : 송준빈
     * 추가내용 : Nice2차 전송(여기서는 고객의 증서정보와 해약금에 대한 정보도 함께 전송해야함.)
     * ================================================================
     * */
    @RequestMapping(value = "/insertNiceInfomation2")
    public ModelAndView insertNiceInfomation2(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Nccs.HttpConnect httpConnect = new Nccs.HttpConnect();
        Socket socket = null;

        DataSetMap listMap1 = new DataSetMap();
        Map<String, Object> hmParam1 = new HashMap<String, Object>(); // ds_member          // 고객증서정보
        Map<String, String> hmParam2 = new HashMap<String, String>(); // nice 전송 파라미터

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {

            System.out.println("NICE 전문 송신을 위한 연결을 시도합니다.");
            //socket = new Socket("172.28.220.42", 80);    // 운영계 real 일때
            //socket = new Socket("172.28.220.43", 80); // 개발계 dev 일때
            System.out.println("NICE 전문 송신을 위한 연결이 맺어졌습니다.");

            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs1 = (DataSetMap)mapInDs.get("ds_input1"); // 회원번호,고객타켓리스트번호
            if (srchInDs1 != null && srchInDs1.size() > 0)
            {
                hmParam1 = srchInDs1.get(0);
            }

            ParamUtils.addSaveParam(hmParam1);

            System.out.println("고객 기본정보 ::: " + hmParam1);
            System.out.println("------------------------------------------");
            System.out.println("------------------------------------------");

            //List<Map<String, Object>> mList1 = webNiceSenderService.getMemberNiceInfo(hmParam1); // 고객 타켓리스트번호(NICE 전문 식별값) 을 가지고 오기 위함임.
            //Map<String, Object> oApp_no = mList1.get(0);

            List<Map<String, Object>> mList2 = dlwNewTypeMainConsService.getCertfMng(hmParam1); // 고객의 증서 내용을 가지고 오기 위함임.
            Map<String, Object> hmParam3 = mList2.get(0);

            List<Map<String, Object>> mList3 = dlwNewTypeMainConsService.getResnAmt(hmParam1); // 고객의 해약금에 대한 정보를 가지고 오기 위함임.


            /* 접수 공통부 파라미터 값이 없는 항목들은 모두 " " 로 처리 CommonUtils.nvl(value, str) */
            hmParam2.put("data_type"       , CommonUtils.nvl("SICINF01", " ")); // 전문코드(접수)
            hmParam2.put("corporation_code", CommonUtils.nvl("DMLF01", " ")); // 대명DLCC
            //hmParam2.put("app_no"          , (String)oApp_no.get("trgt_cust_dtpt_id")); // 고객유일식별값(협의)
            hmParam2.put("app_no"          , CommonUtils.nvl((String)hmParam3.get("accnt_no"), " ")); // 고객유일식별값(2차는 회원번호로 구분)
            hmParam2.put("app_type"        , CommonUtils.nvl("01", " ")); // 접수구분값 (신규접수 01)
            hmParam2.put("member_type"	   , CommonUtils.nvl("01", " ")); // 징구주체값 (본인 01)
            hmParam2.put("app_reason"      , CommonUtils.nvl("01", " ")); // 접수사유값 (01로 고정)
            hmParam2.put("contract_type"   , CommonUtils.nvl("02", " ")); // 계약구분값 (01 : 1차접수, 02 : 2차접수 여기를 이용하여 1차와 2차를 구분하겠습니다)

            /* 접수 개별부 파라미터 */
            String tel  = (String)hmParam3.get("cell_full");
            String brth = (String)hmParam3.get("brth_mon_day");

            hmParam2.put("name",  CommonUtils.nvl((String)hmParam3.get("mem_nm"), " ")); // 고객명

            if(tel.length() == 13)
            {
                hmParam2.put("ph21",  CommonUtils.nvl(tel.substring(0, 3), " "));     // 고객 휴대폰번호 앞자리 3
                hmParam2.put("ph22",  CommonUtils.nvl(tel.substring(4, 8), " "));     // 고객 휴대폰번호 가운데자리 4
                hmParam2.put("ph23",  CommonUtils.nvl(tel.substring(9, 13), " "));    // 고객 휴대폰번호 끝자리 4
            }
            else if(tel.length() == 12)
            {
                hmParam2.put("ph21",  CommonUtils.nvl(tel.substring(0, 3), " "));     // 고객 휴대폰번호 앞자리 3
                hmParam2.put("ph22",  CommonUtils.nvl(tel.substring(4, 7), " "));     // 고객 휴대폰번호 가운데자리 3
                hmParam2.put("ph23",  CommonUtils.nvl(tel.substring(8, 12), " "));    // 고객 휴대폰번호 끝자리 4
            }
            else
            {

            }

            hmParam2.put("ssn11"           , CommonUtils.nvl(brth.substring(2, 8), " "));    // 고객 생년월일 6자리

            hmParam2.put("member_no"         , CommonUtils.nvl((String)hmParam3.get("mem_no"          ), " ")); // 고객고유번호
            hmParam2.put("contract_code"     , CommonUtils.nvl((String)hmParam3.get("accnt_no"        ), " ")); // 고객회원번호
            hmParam2.put("contract_date"     , CommonUtils.nvl((String)hmParam3.get("join_dt"         ), " ")); // 가입일자
            //hmParam2.put("zip21"       	 	 , (String)hmParam3.get("home_zip"        )); // 우편번호
            String sZip = (String)hmParam3.get("home_zip"        );
            hmParam2.put("zip21"       	 	 , CommonUtils.nvl(sZip.substring(0, 3), " ")); // 우편번호 앞자리
            hmParam2.put("zip22"       	 	 , CommonUtils.nvl(sZip.substring(3, 5), " ")); // 우편번호 뒷자리
            hmParam2.put("addr21"       	 , CommonUtils.nvl((String)hmParam3.get("home_addr"       ), " ")); // 주소1
            hmParam2.put("addr22"      	     , CommonUtils.nvl((String)hmParam3.get("home_addr2"      ), " ")); // 주소2
            hmParam2.put("goods_key"         , CommonUtils.nvl((String)hmParam3.get("prod_cd"         ), " ")); // 고객상품코드
            hmParam2.put("loan_name"         , CommonUtils.nvl((String)hmParam3.get("prod_nm"         ), " ")); // 상품명(상품정보1)
            hmParam2.put("etc_01"        	 , CommonUtils.nvl((String)hmParam3.get("prod_amt"        ), " ")); // 상품금액
            hmParam2.put("etc_02"     		 , CommonUtils.nvl((String)hmParam3.get("mon_pay_amt"     ), " ")); // 회차별납입액
            hmParam2.put("etc_03"         	 , CommonUtils.nvl(hmParam3.get("expr_no"      ).toString(), " ")); // 만기회차
            hmParam2.put("loan_name2"		 , CommonUtils.nvl((String)hmParam3.get("prod_join_prt_nm"), " ")); // 상품명(상품정보2)
            hmParam2.put("etc_04"  			 , CommonUtils.nvl((String)hmParam3.get("prod_buga_val2"  ), " ")); // 계약금액
            hmParam2.put("etc_05"  			 , CommonUtils.nvl((String)hmParam3.get("prod_buga_val3"  ), " ")); // 월납입금
            hmParam2.put("etc_06"  			 , CommonUtils.nvl((String)hmParam3.get("prod_buga_val4"  ), " ")); // 계약유형
            hmParam2.put("hope_contract_day" , CommonUtils.nvl((String)hmParam3.get("ichae_dt"        ), " ")); // 결제일자
            hmParam2.put("invo_corp"         , CommonUtils.nvl((String)hmParam3.get("bank_nm"         ), " ")); // 카드사 / 은행명
            hmParam2.put("invo_no"           , CommonUtils.nvl((String)hmParam3.get("bank_accnt_no"   ), " ")); // 카드번호 / 계좌번호

            if(hmParam3.get("pay_mthd").equals("04"))
            {
                hmParam2.put("bank_owner_name"   ,  CommonUtils.nvl((String)hmParam3.get("depr"            ), " ")); // 예금주
            }
            else if (hmParam3.get("pay_mthd").equals("06"))
            {
                hmParam2.put("bank_owner_name"   , CommonUtils.nvl(" ", " ")); // 예금주
            }

            if(hmParam3.get("pay_mthd").equals("04"))
            {
                hmParam2.put("invo_ca"     		 , CommonUtils.nvl(" ", " ")); // 유효기간
            }
            else if (hmParam3.get("pay_mthd").equals("06"))
            {
                hmParam2.put("invo_ca"           , CommonUtils.nvl((String)hmParam3.get("expire_date"     ), " ")); // 유효기간
            }

            hmParam2.put("info_01"           , CommonUtils.nvl((String)hmParam3.get("coffin1"         ), " ")); // 매장
            hmParam2.put("info_02"           , CommonUtils.nvl((String)hmParam3.get("coffin2"         ), " ")); // 화장/탈관
            hmParam2.put("info_03"           , CommonUtils.nvl((String)hmParam3.get("shroud_m1"       ), " ")); // 수의 1
            hmParam2.put("info_04"       	 , CommonUtils.nvl((String)hmParam3.get("shroud_d1"       ), " ")); // 수의 1 내용
            hmParam2.put("info_05"       	 , CommonUtils.nvl((String)hmParam3.get("shroud_m2"       ), " ")); // 수의 2
            hmParam2.put("info_06"       	 , CommonUtils.nvl((String)hmParam3.get("shroud_d2"       ), " ")); // 수의 2 내용
            hmParam2.put("info_07"     		 , CommonUtils.nvl((String)hmParam3.get("coffin_gds1"     ), " ")); // 입관/수시용품1
            hmParam2.put("info_08"     		 , CommonUtils.nvl((String)hmParam3.get("coffin_gds2"     ), " ")); // 입관/수시용품2
            hmParam2.put("info_09"   		 , CommonUtils.nvl((String)hmParam3.get("mortuary_gds1"   ), " ")); // 빈소/기타용품1
            hmParam2.put("info_10"   		 , CommonUtils.nvl((String)hmParam3.get("mortuary_gds2"   ), " ")); // 빈소/기타용품2
            hmParam2.put("info_11"           , CommonUtils.nvl((String)hmParam3.get("car1"            ), " ")); // 이송차량
            hmParam2.put("info_12"           , CommonUtils.nvl((String)hmParam3.get("car2"            ), " ")); // 리무진
            hmParam2.put("info_13"           , CommonUtils.nvl((String)hmParam3.get("car3"            ), " ")); // 유족버스
            hmParam2.put("info_14"       	 , CommonUtils.nvl((String)hmParam3.get("flower_m1"       ), " ")); // 제단 장식 1
            hmParam2.put("info_15"       	 , CommonUtils.nvl((String)hmParam3.get("flower_d1"       ), " ")); // 제단 장식 1 내용
            hmParam2.put("info_16"       	 , CommonUtils.nvl((String)hmParam3.get("flower_m2"       ), " ")); // 제단 장식 2
            hmParam2.put("info_17"       	 , CommonUtils.nvl((String)hmParam3.get("flower_d2"       ), " ")); // 제단 장식 2 내용
            hmParam2.put("info_18"       	 , CommonUtils.nvl((String)hmParam3.get("flower_m3"       ), " ")); // 제단 장식 3
            hmParam2.put("info_19"       	 , CommonUtils.nvl((String)hmParam3.get("flower_d3"       ), " ")); // 제단 장식 3 내용
            hmParam2.put("info_20"			 , CommonUtils.nvl((String)hmParam3.get("funeral_clothes1"), " ")); // 현대식
            hmParam2.put("info_21"			 , CommonUtils.nvl((String)hmParam3.get("funeral_clothes2"), " ")); // 전통식 1
            hmParam2.put("info_22"			 , CommonUtils.nvl((String)hmParam3.get("funeral_clothes3"), " ")); // 전통식 2
            hmParam2.put("info_23"       	 , CommonUtils.nvl((String)hmParam3.get("helper_m1"       ), " ")); // 인력 1
            hmParam2.put("info_24"       	 , CommonUtils.nvl((String)hmParam3.get("helper_d1"       ), " ")); // 인력 1 내용
            hmParam2.put("info_25"       	 , CommonUtils.nvl((String)hmParam3.get("helper_m2"       ), " ")); // 인력 2
            hmParam2.put("info_26"       	 , CommonUtils.nvl((String)hmParam3.get("helper_d2"       ), " ")); // 인력 2 내용
            hmParam2.put("info_27"       	 , CommonUtils.nvl((String)hmParam3.get("helper_m3"       ), " ")); // 인력 3
            hmParam2.put("info_28"       	 , CommonUtils.nvl((String)hmParam3.get("helper_d3"       ), " ")); // 인력 3 내용
            hmParam2.put("info_29"           , CommonUtils.nvl((String)hmParam3.get("cmetc"           ), " ")); // 비고
            hmParam2.put("info_30"           , CommonUtils.nvl((String)hmParam3.get("etc_val"         ), " ")); // 비고내용
            hmParam2.put("info_31"       	 , CommonUtils.nvl((String)hmParam3.get("terms_dtl"       ), " ")); // 장의상품 상세정보
            hmParam2.put("info_32"        	 , CommonUtils.nvl((String)hmParam3.get("consm_nt"        ), " ")); // 소비자 유의사항
            hmParam2.put("info_33"       	 , CommonUtils.nvl((String)hmParam3.get("refund_nt"       ), " ")); // 환급기준 및 환급시기
            hmParam2.put("info_34"      	 , CommonUtils.nvl((String)hmParam3.get("refund_amt"      ), " ")); // 총 고객환급의무액
            hmParam2.put("info_35"           , CommonUtils.nvl((String)hmParam3.get("asset"           ), " ")); // 상조관련 자산
            hmParam2.put("info_36"   		 , CommonUtils.nvl((String)hmParam3.get("consm_amt_mng"   ), " ")); // 고객 불입금 관리방법

            String sResn_amt = "";

            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            System.out.println(mList3.toString());
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            for(int idx = 0; idx < mList3.size(); idx++)
            {
                sResn_amt = sResn_amt + mList3.get(idx).get("no") + "^";
                sResn_amt = sResn_amt + mList3.get(idx).get("pct") + "^";
                sResn_amt = sResn_amt + mList3.get(idx).get("resn_amt") + ";";
            }

            hmParam2.put("resn_amt"   , CommonUtils.nvl(sResn_amt, " ")); // 해약환금율
            hmParam2.put("info_37"    , CommonUtils.nvl((String)hmParam3.get("refund_math1"    ), " ")); // 해약환급금 산식(정기형)
            hmParam2.put("info_38"    , CommonUtils.nvl((String)hmParam3.get("refund_math2"    ), " ")); // 해약환급금 산식(부정기형)
            hmParam2.put("info_39"    , CommonUtils.nvl((String)hmParam3.get("refund_math3"    ), " ")); // 해약환급금 산식(부정기형) 밑에 내용
            hmParam2.put("info_40"    , CommonUtils.nvl((String)hmParam3.get("pay_mthd"        ), " ")); // 납입방식 04 : CMS, 06: Card

            hmParam2.put("info_41"    , CommonUtils.nvl((String)hmParam3.get("terms_svc"    	), " ")); // 2018.05.10 추가 토탈라이프서비스
            hmParam2.put("info_42"    , CommonUtils.nvl((String)hmParam3.get("resn_amt_info"    ), " ")); // 2018.05.10 추가 실 해약환급내용 추가

            hmParam1.putAll(hmParam2);
            hmParam1.put("brth_mon_day",  brth);                 // 고객 생년월일
            hmParam1.put("clph_tlno",  tel.replaceAll("-", "")); // 고객 전화번호
            hmParam1.put("cust_nm",  (String)hmParam3.get("mem_nm"));   // 고객명
            hmParam1.put("mem_no",   (String)hmParam3.get("mem_no"));   // 고객고유번호
            hmParam1.put("accnt_no", (String)hmParam3.get("accnt_no")); // 고객회원번호
            hmParam1.put("prod_cd",  (String)hmParam3.get("prod_cd"));  // 상품코드

            System.out.println("-----------------------------------");
            System.out.println("2차접수값 :: " + hmParam1.toString());
            System.out.println("-----------------------------------");
            System.out.println(tel.substring(0, 3) + "::" + tel.substring(4, 8) + "::" + tel.substring(9, 13) );
            System.out.println("-----------------------------------");
            System.out.println("2차접수값 :: " + sResn_amt);
            System.out.println("-----------------------------------");

            int result4 = dlwNewTypeMainConsService.insertMemberNiceInfoSecond(hmParam1);

            System.out.println("나이스 전송 파라미터1 ::: " + hmParam1);
            System.out.println("나이스 전송 파라미터2 ::: " + hmParam2);

            //int result3 = webNiceSenderService.insertMemberNiceInfoHist(hmParam1); // NICE신용정보 전자서명 테이블 히스토리 인서트
            /* 보고를 따로 드려야함 */

            httpConnect.makeParam(hmParam2, niceOperation, "euc-kr", 1000*30); // NICE 정보발송 "dev" 개발계 "real" 운영계

            //int result1 = webNiceSenderService.updateMemberNiceInfo2(hmParam1);    // 고객 타켓리스트 테이블 업데이트 (고객고유번호, 고객회원번호)
            //int result2 = webNiceSenderService.updateMemberNiceInfo3(hmParam1);    // NICE신용정보 전자서명 테이블 업데이트 (고객고유번호, 고객회원번호)

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        }
        catch (IOException ioe)
        {
            System.out.println("NICE 전문 송신을 위한 연결이 실패하였습니다.");
            szErrorCode = "-1";
            szErrorMsg  = ioe.getMessage() + " ::: NICE 전문 송신을 위한 연결이 실패하였습니다.";
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
    
    @RequestMapping(value = "/resendKaKao2")
    public ModelAndView resendKaKao2(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        Nccs.HttpConnect httpConnect = new Nccs.HttpConnect();
        Map<String, Object> hmParam1 = new HashMap<String, Object>();
        Map<String, String> hmParam3 = new HashMap<String, String>();	//전문 값을 담을 Map 생성

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs1 = (DataSetMap)mapInDs.get("ds_input1"); // ds_list_trgtCust     (고객타겟리스트 [캠페인])

            if (srchInDs1 != null && srchInDs1.size() > 0)
            {
                hmParam1 = srchInDs1.get(0);
            }

            ParamUtils.addSaveParam(hmParam1);

            /**
                고객사 AP에서 DB값을 불러와 취소할 계약건의 app_no, nice_no, cancel_reason, cancel_manager, memo
                값을 map에 세팅 - 고객사 구현 구간
            */

            List<Map<String, Object>> mList = dlwNewTypeMainConsService.getMemberNiceRetrieve2(hmParam1);
            Map<String, Object> map = mList.get(0);

            //접수 공통부
            hmParam3.put("corporation_code", "DMLF01");						   		     // 고객사 업체 코드

            if((map.get("upp_tx_type")).equals("04") == true || (map.get("upp_tx_type")).equals("05") == true)
            {
                hmParam3.put("nice_no"         , (String)map.get("nice_no2"));				 // NICE계약건 번호
            }
            else
            {
            	System.out.println("재전송할수 있는 대상이 아닙니다");
            }

            //함수 호출
            //httpConnect.makeParam(hmParam2, "dev", "euc-kr");

            System.out.println("hmParam3 ::: " + hmParam3.toString());
            httpConnect.resendKakao(hmParam3, niceOperation, "euc-kr");

            listMap01.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap01);

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
    
    /* ================================================================
     * 날짜 : 20180220
     * 이름 : 송준빈
     * 추가내용 : 고객 증서 파일 미리보기시 사전 점검 사항.
     * - 파일이 있으면 있는 파일을 미리보기 해줌
     * - 파일이 없으면 다시 다운로드 요청뒤 미리보기 해줌
     * ================================================================
     * */
    @RequestMapping(value = "/getNicePdfFile")
    public ModelAndView getNicePdfFile(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        Nccs.HttpConnect httpConnect = new Nccs.HttpConnect();
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

            if (srchInDs != null && srchInDs.size() > 0)
            {
                hmParam = srchInDs.get(0);
            }

            List<Map<String, Object>> mList01 = dlwNewTypeMainConsService.getMemberNiceRetrieve2(hmParam);
            Map<String, Object> map1 = mList01.get(0);

            //System.out.println("map의값 ::: " + map.toString());
            //List<Map<String, Object>> mList02 = webNiceSenderService.getMemberNiceJoinDt(map);
            //Map<String, Object> map1 = mList02.get(0);
            //System.out.println("map1의값 ::: " + map1.toString());
            //String join_dt = (String)map1.get("join_dt");

            String img_file_path = "";
            String img_file_name = "";
            String trgt_cust_dtpt_id = "";
            String join_dt = "";

            if (mList01.size() <= 0)
            {
                System.out.println("2차전송이 되지 않은 회원입니다.");
            }
            else
            {
                img_file_path = (String)map1.get("img_file_path");
                img_file_name = (String)map1.get("img_file_nm");
                trgt_cust_dtpt_id = (String)map1.get("trgt_cust_dtpt_id");
                join_dt = (String)hmParam.get("join_dt");

                if(img_file_path == null || img_file_path.equals(""))
                {
                    System.out.println("회원가입완료가 되지 않은건입니다.");
                }
                else
                {
                    //File file = new File("http://61.39.175.227:8080/powerservice/common/imgFile/"+"TAR201802190610556.pdf");
                    File file = new File(img_file_path);
                    if (file.exists() == true)
                    {
                        System.out.println("있는파일입니다.");
                    }
                    else
                    {
                        System.out.println("없는파일이므로 다운로드를 다시 수행합니다.");

                        String nice_no2 = (String)map1.get("nice_no2");

                        String img_file = "";
                        //String downPath	= "/app/powerservice/common/imgFile/";
                        String downPath =  "/app/nice/" + join_dt + "/";

                        File downDirectory = new File(downPath);

                        if (downDirectory.exists() == false)
                        {
                            downDirectory.mkdir();
                        }

                        String[] fileArray	= img_file.split("\\|");

                        for(int i=0;i< fileArray.length; i++)
                        {
                            //httpConnect.fileDownload("dev", fileArray[i], downPath, nice_no2); // 운영계. ("real", fileArray[i], downPath, nice_no2)
                            httpConnect.fileDownload(niceOperation, fileArray[i], downPath, nice_no2); // 운영계. ("real", fileArray[i], downPath, nice_no2)
                        }

                        hmParam.put("img_file_nm", img_file);              // 이미지 파일명
                        hmParam.put("img_file_path", downPath + img_file); // 이미지가 존재하는 filepath
                    }
                }
            }

            listMap01.setRowMaps(mList01);
            mapOutDs.put("ds_output", listMap01);

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
                int nAccntCnt = dlwNewTypeMainConsService.getWdrwReqAccntConfirm(accnt_no);
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
                mList = dlwNewTypeMainConsService.getWdrwReqAccntEventConfirm(accnt_no);

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

            List<Map<String, Object>> mList = dlwNewTypeMainConsService.getMemberMangiExtConfirmList(hmParam);
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
     * 대명라이프웨이 Card 고객정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/card-csmm/list")
    public ModelAndView getDlwCardCsmmList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int nTotal = dlwNewTypeMainConsService.getDlwCardCsmmCount(hmParam);

            mapOutVar.put("tc_cardCsmm", nTotal);

            List<Map<String, Object>> mList = dlwNewTypeMainConsService.getDlwCardCsmm(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.01.12 접속 로그//////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////

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
     * 대명라이프웨이 Cms 고객정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cms-csmm/list")
    public ModelAndView getDlwCmsCsmmList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int nTotal = dlwNewTypeMainConsService.getDlwCmsCsmmCount(hmParam);

            mapOutVar.put("tc_cmsCsmm", nTotal);

            List<Map<String, Object>> mList = dlwNewTypeMainConsService.getDlwCmsCsmm(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.01.05 접속 로그//////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////

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
     * 발주정보를 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/odrg-info")
    public ModelAndView getOdrgInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
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

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));

            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                return modelAndView;
            }

            hmParam.put("accnt_no", sAccntNo);
            ParamUtils.addCenterParam(hmParam);

            Map<String, Object> mDtpt = dlwNewTypeMainConsService.getOdrgInfo(hmParam);
            if (mDtpt != null) {
                dtptMap.setRowMaps(mDtpt);
                mapOutDs.put("ds_output", dtptMap);
            }

            List<Map<String, Object>> mList = dlwNewTypeMainConsService.getDlvList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output_list", listMap);
            
            List<Map<String, Object>> mList1 = dlwOthersService.getPostSendList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output_list1", listMap);

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
     * 납입이력 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/pay-mng/list")
    public ModelAndView getDlwPayMngList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            String sPyinTyp = StringUtils.defaultString((String) mapInVar.get("pyin_typ"));

            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                return modelAndView;
            }

            hmParam.put("accnt_no", sAccntNo);

            if ("1".equals(sPyinTyp)) {			// 상조부금
                mList = dlwNewTypeMainConsService.getDlwPayMngList(hmParam);
            } else if ("2".equals(sPyinTyp)) {	// 결합상품할부원금
                mList = dlwNewTypeMainConsService.getDlwPayMngDtlList(hmParam);
            } else {							// 결합상품추가부담금
                mList = dlwNewTypeMainConsService.getDlwPayMngDtl1List(hmParam);
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
    
    /**
     * 납입이력 정보를 검색한다.(상조부금 + 결합상품할부원금 + 결합상품추가부담금)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/pay-mng/all-list")
    public ModelAndView getDlwPayMngAllList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            mList = dlwNewTypeMainConsService.getDlwPayMngAllList(hmParam);

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
     * 해약정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cnct/list")
    public ModelAndView getDlwCnctList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
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

                Map<String, Object> mDtpt = dlwNewTypeMainConsService.getDlwResnAmtList(hmParam); // 적용일자 및 SEQ 조회

                if (mDtpt != null) {
                    hmParam.put("mst_seq", mDtpt.get("seq"));

                    List<Map<String, Object>> mList = dlwNewTypeMainConsService.getResnAmtDetailList(hmParam);

                    List<Map<String, Object>> mRntmList = dlwNewTypeMainConsService.getDlwPayMngList(hmParam);
                    if (mRntmList.size() > 0) {
                        mapOutVar.put("gv_rntm_cnt", mRntmList.get(0).get("no")); // 회차
                    } else {
                        mapOutVar.put("gv_rntm_cnt", 0);
                    }

                    dtptMap.setRowMaps(mDtpt);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output_dtpt", dtptMap);
                    mapOutDs.put("ds_output_list", listMap);
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
     * 결제정보를 검색한다. (개별목록 신청내역 CMS+Card)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/pymn-hstr/list")
    public ModelAndView getDlwPymnHstrList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            mList = dlwNewTypeMainConsService.getDlwPymnHstrList(hmParam);
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
     * 청구내역을 검색한다. (개별목록 청구내역 CMS+Card)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/ask-hstr/list")
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
            mList = dlwNewTypeMainConsService.getDlwAskHstrList(hmParam);
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
     * 회원고객정보 탭 (청구이력)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/srchWdrwLog")
    public ModelAndView getMainWdrwLogList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            mList = dlwNewTypeMainConsService.getMainWdrwLogList(hmParam);
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
     * 상품 검색한다. (NEWTYPE)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/product_list")
    public ModelAndView getDlwProductList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
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
                ParamUtils.addCenterParam(hmParam);
                
                List<Map<String, Object>> tmpMap = dlwNewTypeMainConsService.getDlwProductList(hmParam);
                if (tmpMap != null) {
                    dtptMap.setRowMaps(tmpMap);
                    mapOutDs.put("ds_output", dtptMap);
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }
        
        return modelAndView;
    }
    
	/**
     * 상품 검색한다. (NEWTYPE)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/jusoApi")
    public ModelAndView getDlwJusoApiList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
         // OPEN API        	
            String keyword  = mapInVar.get("keyword").toString();
            //String confmKey  = "devU01TX0FVVEgyMDIxMTIxNTEwMzQxNTExMjAzMjE=";  //개발
            String confmKey  = "U01TX0FVVEgyMDIxMTIxNTE0NDEwOTExMjAzMzg=";  //운영
                        
            String currentPage = "1";
            String countPerPage = "100";
            String apiUrl = "https://www.juso.go.kr/addrlink/addrLinkApi.do?currentPage="+currentPage+"&countPerPage="+countPerPage+"&keyword="+URLEncoder.encode(keyword,"UTF-8")+"&confmKey="+confmKey+"&resultType="+"XML";
            
         	/*XML 파싱하여 Dataset에 추가*/
         	DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
         	DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
         	Document doc = dBuilder.parse(apiUrl);

         	//root tag (확인용)
         	doc.getDocumentElement().normalize();

        	//파싱할 tag 목록
         	NodeList nList = doc.getElementsByTagName("juso");         	
         	
         	System.out.println("xxxxxxxxxxxxxxxx> : " +apiUrl); 
         	
         	List<Map<String, Object>> tmpListMap = new ArrayList<>();         	         	
        	//xml의 tag정보를 사용하여 데이터 입력
        	int row = 0;
        	
        	String strZipno = "";
        	String strRoadAddr = "";
        	String strjibunAddr = "";
        	
         	for(int temp = 0; temp < nList.getLength(); temp++){
        		Node nNode = nList.item(temp);
        		if(nNode.getNodeType() == Node.ELEMENT_NODE){
        			
        			Element eElement = (Element) nNode;

                	strZipno = getTagValue("zipNo", eElement);
                	strRoadAddr = getTagValue("roadAddr", eElement);
                	strjibunAddr = getTagValue("jibunAddr", eElement);

                	Map<String, Object> tmpMap = new HashMap<String, Object>();
        			tmpMap.put("postcode",  strZipno);
        			tmpMap.put("roadaddress",  strRoadAddr);
        			tmpMap.put("jibunaddress",  strjibunAddr);
        		
        			tmpListMap.add(tmpMap);        									        			        	   	
        		}
        	}
         	         	
            if (tmpListMap != null) {
                dtptMap.setRowMaps(tmpListMap);
                mapOutDs.put("ds_output", dtptMap);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }
        
        return modelAndView;
    }
    
    public String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue == null) 
            return null;
        return nValue.getNodeValue();
    }
    
	/**
     * 회원 정보 중 마케팅 정보 조회
     * TB_MEMBER_MKT_INFO
     */
    @RequestMapping(value = "/marketinginfo")
    public ModelAndView getDlwMarketingInfoList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
        DataSetMap dtptMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            String sMemNo = StringUtils.defaultString((String) mapInVar.get("mem_no"));
            hmParam.put("mem_no", sMemNo);
            
            Map<String, Object> tmpMap = dlwNewTypeMainConsService.getDlwMarketingInfoList(hmParam); 
            if (tmpMap != null) {
                dtptMap.setRowMaps(tmpMap);
                mapOutDs.put("ds_output", dtptMap);
            }
            
            List<Map<String, Object>> historyMap = dlwNewTypeMainConsService.getDlwMarketinghstrList(hmParam);
            if (historyMap != null) {
            	dtptMap2.setRowMaps(historyMap);
                mapOutDs.put("ds_output2", dtptMap2);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }
        
        return modelAndView;
    }
    
	/**
     * 회원 정보 중 마케팅 정보 등록
     * TB_MEMBER_MKT_INFO
     */
    @RequestMapping(value = "/insertmarketing")
    public ModelAndView insertDlwMarketingInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            String sMemNo = StringUtils.defaultString((String) mapInVar.get("mem_no"));
            String sFieldName = StringUtils.defaultString((String) mapInVar.get("gubun"));
            String sFieldValue = StringUtils.defaultString((String) mapInVar.get("gubun_val"));                        
            
            hmParam.put("mem_no", sMemNo);
            hmParam.put("gubun", sFieldName);                                               
            ParamUtils.addSaveParam(hmParam);
                        
            if (sFieldValue.equals("N")){
            	dlwNewTypeMainConsService.updateMktInfo(hmParam);
            } else {
            	dlwNewTypeMainConsService.insertMktInfo(hmParam);            	            	
            }           
                       
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }
        
        return modelAndView;
    }    

}
