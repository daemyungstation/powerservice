package powerservice.business.sys.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
//2017.12.04 로그 추가
import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DlwCustService;
import powerservice.business.sys.service.DmWebSenderService;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

@Controller
@RequestMapping(value = "/sys/dm_sender")
public class DmWebSenderController {

    private final Logger log = LoggerFactory.getLogger(DmWebSenderController.class);

    @Resource
    private DmWebSenderService dmWebSenderService;

    @Resource
    private DlwCustService dlwCustService;

    @Resource
    private CommonService commonService;


    /**
     * APP푸쉬알림
     * 임동진 20190327
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */

    @RequestMapping(value = "/pushalert")
    public ModelAndView NewEvtMngrins(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            Map<String, Object> hmParam = new HashMap<String, Object>();
            Map<String, Object> mList = new HashMap<String, Object>();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");

            for (int i = 0; i < srchInDs.size(); i++) {
                hmParam = srchInDs.get(i);
            }

            List<Map<String, Object>> listSinfo = dmWebSenderService.getSendPushInfo(hmParam);
            mList= listSinfo.get(0);

            System.out.println("xxxxxxxxxxxxxxxxxxxx> : " + mList.get("SEND_MSG"));
            System.out.println("xxxxxxxxxxxxxxxxxxxx> : " + mList.get("REAL_EVT_MNGR_CD"));
            System.out.println("xxxxxxxxxxxxxxxxxxxx> : " + mList.get("MNG_BRANCH_CD"));

        	JSONObject objList = new JSONObject();
        	JSONArray arrList = new JSONArray();

        	//String[] arrTarget = {"2019000062","2019000062"};

        	String strMsg = mList.get("SEND_MSG").toString();

        	arrList.put("2019000062");

        	objList.put("message", strMsg);
        	objList.put("target", arrList);

        	URL url = new URL("http://61.39.175.212:8080/api/send_push.jsp");
        	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        	conn.setDoOutput(true);
        	conn.setRequestMethod("POST");
        	conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

	        String param = "reqData="+ URLEncoder.encode(objList.toString(), "UTF-8");

        	//전송
        	OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
    		osw.write(param);
    		osw.flush();

    		System.out.println("xxxxxxxxxxxxxxxxxxxxxxx> : " + param);

    		//응답
    		BufferedReader br = null;
    		br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));

    		String line = null;
    		while ((line = br.readLine()) != null){
    			System.out.println(line);
    		}

    		osw.close();
    		br.close();
    		conn.disconnect();

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,xpDto.getOutDataSetMap());
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