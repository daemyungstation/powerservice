package powerservice.business.acn.web;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.acn.service.AcnRecService;
import powerservice.core.util.ParamUtils;

import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.data.VariableList;
import com.tobesoft.xplatform.tx.HttpPlatformResponse;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 나이스 평가정보 신용등급 조회
 *
 * @author 정출연
 * @version 1.0
 * @date 2015/09/01
 * @프로그램ID NiceCredit
 */
@Controller
public class AcnRecController {

    private final Logger log = LoggerFactory.getLogger(AcnRecController.class);

    @Resource
    private AcnRecService acnRecService;


    @RequestMapping(value = "/acn/uploadAcnRecFile")
    public void uploadAcnRecFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        log.debug("--------^^--------");

        try {
            log.debug("uploadAcnRecFile 컨트롤러 - 1");
            acnRecService.uploadAcnRecFile(request, response);
            log.debug("uploadAcnRecFile 컨트롤러 - 2");

        } catch (Exception e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg", e.getMessage());
            e.printStackTrace();
        }

        resVarList.add("ErrorCode"	, szErrorCode);
        resVarList.add("ErrorMsg"	, szErrorMsg);

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();

    }


    /**
     * 고객 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/acn/getAcuonRecHistList")
    public ModelAndView getAcuonRecHistList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap ds_cond = (DataSetMap)mapInDs.get("ds_input");
            if (ds_cond != null && ds_cond.size() > 0) {
                hmParam = ds_cond.get(0);

                ParamUtils.addCenterParam(hmParam);

                List<Map<String, Object>> mList = acnRecService.getAcuonRecHistList(hmParam);
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
     *
     * 파일 다운로드
     *
     * @param
     * @return
     * @throws
     */
    @RequestMapping(value = "/acn/downloadAcnRecFile")
    public void downloadFile(@RequestParam Map<String, Object> pmParam, HttpServletResponse response) throws Exception {
        ServletOutputStream servletOutputStream = null;

        try {
            Map<String, Object> fileContentsMap = acnRecService.getAcuonRecHist(pmParam);

            if (fileContentsMap != null) {
                String sFileNm = (String) fileContentsMap.get("file_nm");

                sFileNm = URLEncoder.encode(sFileNm, "utf-8").replaceAll("\\+", "%20");

                response.setHeader("Content-Disposition", "attachment;filename=" + sFileNm + ";");
                response.setContentType("Content-type: application/octet-stream");

                byte[] aFileCntn = (byte[]) fileContentsMap.get("file_cntn");
                int nFileSize = aFileCntn.length;

                servletOutputStream = response.getOutputStream();
                response.setContentLength(nFileSize);
                servletOutputStream.write(aFileCntn, 0, nFileSize);
                servletOutputStream.flush();
            }
        } catch(Exception except) {
            String sExceptNm = except.getClass().getName();

            if ("org.apache.catalina.connector.ClientAbortException".equals(sExceptNm)) {
                //톰캣으로 실행시 Exception에러남 서버에서는 이상없음
            } else {
                except.printStackTrace();
                throw except;
            }
        } finally {
            if (servletOutputStream != null) {
                servletOutputStream.close();
            }
        }
    }

    /**
     * 녹취파일 수정/삭제
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/acn/deleteAcuonRecList")
    public void deleteAcuonRecList(XPlatformMapDTO xpDto, Model mode, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            acnRecService.saveAcuonRecList(mapInDs);

        }catch(Exception e){
            e.printStackTrace();
        }

        resVarList.add("ErrorCode"	, szErrorCode);
        resVarList.add("ErrorMsg"	, szErrorMsg);

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();
    }

    /**
     * 녹취파일 애큐온전송
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/acn/passAcuonRecList")
    public void passAcuonRecList(XPlatformMapDTO xpDto, Model mode, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            acnRecService.passAcuonRecList(mapInDs);

        }catch(Exception e){
            e.printStackTrace();
        }

        resVarList.add("ErrorCode"	, szErrorCode);
        resVarList.add("ErrorMsg"	, szErrorMsg);

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();
    }

    /**
     * Acuon FTP에서 업로드된 녹취 파일목록을 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/acn/getAcuonRecFtpFileList")
    public ModelAndView getAcuonRecFtpFileList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap ds_cond = (DataSetMap)mapInDs.get("ds_input");
            if (ds_cond != null && ds_cond.size() > 0) {
                hmParam = ds_cond.get(0);

                ParamUtils.addCenterParam(hmParam);

                List<Map<String, Object>> mList = acnRecService.getAcuonRecFtpFileList(hmParam);
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
     * 기존 Acuon 녹취 등록 파일을 삭제하고 FTP에서 조회한 정보를 ACUON 녹취이력으로 등록한다.(모두 재등록)
     *  - DB의 데이터만 삭제
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/acn/deleteAllAndSaveAcuonRec")
    public ModelAndView deleteAllAndSaveAcuonRec(XPlatformMapDTO xpDto) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            acnRecService.deleteAllAndSaveAcuonRec(xpDto);

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
     * Acuon 녹취 등록 파일 삭제
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/acn/deleteAcuonRec")
    public ModelAndView deleteAcuonRec(XPlatformMapDTO xpDto) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            acnRecService.deleteAcuonRec(xpDto);

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
     * 기존 Acuon 녹취 등록 파일을 삭제하고 FTP에서 조회한 정보를 ACUON 녹취이력으로 등록한다.
     *  - 선택등록 버튼 클릭
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/acn/deleteAndSaveAcuonRec")
    public ModelAndView deleteAndSaveAcuonRec(XPlatformMapDTO xpDto) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            acnRecService.deleteAndSaveAcuonRec(xpDto);

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
     * MG전송파일업로드
    */

    @RequestMapping(value = "/acn/uploadMgTransFile")
    public void uploadMgRecFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        log.debug("--------^^--------");

        try {
            log.debug("uploadAcnRecFile 컨트롤러 - 1");
            acnRecService.uploadMgTransFile(request, response);
            log.debug("uploadAcnRecFile 컨트롤러 - 2");

        } catch (Exception e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg", e.getMessage());
            e.printStackTrace();
        }

        resVarList.add("ErrorCode"	, szErrorCode);
        resVarList.add("ErrorMsg"	, szErrorMsg);

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();
    }

    /**
     * MG업로드 파일 삭제
     */
    @RequestMapping(value = "/acn/deleteMgTransList")
    public void deleteMgTransList(XPlatformMapDTO xpDto, Model mode, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            acnRecService.saveMgTransList(mapInDs);

        }catch(Exception e){
            e.printStackTrace();
        }

        resVarList.add("ErrorCode"	, szErrorCode);
        resVarList.add("ErrorMsg"	, szErrorMsg);

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();
    }

    /**
     * MG회원 파일 FTP전송
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/acn/passMgTransList")
    public void passMgTransList(XPlatformMapDTO xpDto, Model mode, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            acnRecService.passMgTransList(mapInDs);

        }catch(Exception e){
            e.printStackTrace();
        }

        resVarList.add("ErrorCode"	, szErrorCode);
        resVarList.add("ErrorMsg"	, szErrorMsg);

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();
    }

    /**
     * MG 월별 파일 전송 리스트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/acn/getMgFileTransList")
    public ModelAndView getMgFileTransList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap ds_cond = (DataSetMap)mapInDs.get("ds_input");
            if (ds_cond != null && ds_cond.size() > 0) {
                hmParam = ds_cond.get(0);

                ParamUtils.addCenterParam(hmParam);

                List<Map<String, Object>> mList = acnRecService.getMgFileTransList(hmParam);
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

}