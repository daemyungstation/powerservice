package powerservice.business.dlw.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.EmpService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

//2017.12.21 로그 추가
import powerservice.business.common.service.CommonService;
/**
 * 기타관리
 *
 * @author 정출연
 * @version 1.0
 * @date 2016.12.13
 */
@Controller
public class EmpController {

    private final Logger log = LoggerFactory.getLogger(EmpController.class);

    @Resource
    private EmpService empService;

    @Resource
    private CommonService commonService;

    /**
     * resinCancel
     *
     * @param xpDto
     *            XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/emp/resinCancel")
    public ModelAndView resinCancel(XPlatformMapDTO xpDto) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            int iCudCnt = empService.resinCancel(xpDto);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());

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
     * 사원 등록/수정
     *
     * @param xpDto
     *            XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/emp/mergeEmployee")
    public ModelAndView mergeEmployee(XPlatformMapDTO xpDto) throws Exception {

        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        Map<String, Object> mResult = new HashMap<String, Object>();

        try {
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();

            int iCudCnt = empService.mergeEmployee(xpDto, mResult);
            if (iCudCnt < 0) {
                throw new Exception("중복된 그룹사번입니다.");
            }

            String empleNo = CommonUtils.nvls((String) mResult.get("emple_no"));
            mapOutVar.put("fv_emple_no2", empleNo);

            //2017.12.27 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());

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
     *
     * 사원목록 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/emp/selectEmpleList")
    public ModelAndView selectEmpleList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            UserSession sess = (UserSession) SessionUtils.getLoginUser();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                hmParam.put("param_emple_no", sess.getEmployeeid());
                hmParam.put("param_as", "EMP");

                String dataAuthQry = empService.createDataAuthQry(hmParam);
                hmParam.put("data_auth_qry", dataAuthQry);

                List<Map<String, Object>> mList = empService
                        .selectEmpleList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2017.12.04 접속 로그////////////////////////////////////////////////////////////////////////////////

            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            /////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
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
     *
     * 사원목록 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/emp/selectEmpleListToIdnNo")
    public ModelAndView selectEmpleListToIdnNo(XPlatformMapDTO xpDto,
            Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            UserSession sess = (UserSession) SessionUtils.getLoginUser();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                List<Map<String, Object>> mList = empService
                        .selectEmpleList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
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
     *
     * selectEmpleMemChangeList
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/emp/selectEmpleMemChangeList")
    public ModelAndView selectEmpleMemChangeList(XPlatformMapDTO xpDto,
            Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                List<Map<String, Object>> mList = empService.selectEmpleMemChangeList(hmParam);
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

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
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
     * 담당사원 변경
     *
     * @param xpDto
     *            XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/emp/insertMstrChgInf")
    public ModelAndView insertMstrChgInf(XPlatformMapDTO xpDto)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        Map<String, Object> mResult = new HashMap<String, Object>();

        try {
            int iCudCnt = empService.insertMstrChgInf(xpDto);

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
     * 퇴사처리
     *
     * @param xpDto
     *            XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/emp/updateEmployeeResin")
    public ModelAndView updateEmployeeResin(XPlatformMapDTO xpDto) throws Exception {

        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        Map<String, Object> mResult = new HashMap<String, Object>();

        try {
            int iCudCnt = empService.updateEmployeeResin(xpDto);

            String empleNo = CommonUtils.nvls((String) mResult.get("emple_no"));

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());

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
     * 직위/직책 변경
     *
     * @param xpDto
     *            XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/emp/insertHistPosnEmp")
    public ModelAndView insertHistPosnEmp(XPlatformMapDTO xpDto) throws Exception {

        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        Map<String, Object> mResult = new HashMap<String, Object>();

        try {
            int iCudCnt = empService.insertHistPosnEmp(xpDto);

            String empleNo = CommonUtils.nvls((String) mResult.get("emple_no"));

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());

        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }



    /* 부가서비스 ------------------------------------------------- */

    /**
     *
     * 할인우대권 번호 관리
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/emp/selectDiscntPassMstlist")
    public ModelAndView selectDiscntPassMstlist(XPlatformMapDTO xpDto,
            Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            UserSession sess = (UserSession) SessionUtils.getLoginUser();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                List<Map<String, Object>> mList = empService.selectDiscntPassMstlist(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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
     *
     * 생성된 난수 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/emp/selectRandNumList")
    public ModelAndView selectRandNumList(XPlatformMapDTO xpDto,
            Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            UserSession sess = (UserSession) SessionUtils.getLoginUser();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                List<Map<String, Object>> mList = empService.selectRandNumList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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
     * 할인우대권 번호 비고 변경
     *
     * @param xpDto
     *            XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/emp/noteSave")
    public ModelAndView noteSave(XPlatformMapDTO xpDto)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        Map<String, Object> mResult = new HashMap<String, Object>();

        try {
            int iCudCnt = empService.noteSave(xpDto);

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
     *
     * 회원 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/emp/selectMemberList")
    public ModelAndView selectMemberList(XPlatformMapDTO xpDto,
            Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            UserSession sess = (UserSession) SessionUtils.getLoginUser();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                List<Map<String, Object>> mList = empService.selectMemberList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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
     *
     * 회원 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/emp/selectMemberListByEmpleNo")
    public ModelAndView selectMemberListByEmpleNo(XPlatformMapDTO xpDto,
            Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            UserSession sess = (UserSession) SessionUtils.getLoginUser();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                List<Map<String, Object>> mList = empService.selectMemberListByEmpleNo(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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
}