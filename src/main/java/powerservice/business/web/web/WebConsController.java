/*
 * (@)# WebConsController.java
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/10/14
 * Copyright (c) 2015 by Inwoo tech inc, KOREA. All Rights Reserved.
 *
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary
 * information of
 * Inwoo Tech Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms
 * of the license agreement you
 * entered into with Inwoo Tech Inc.
 *
 */

package powerservice.business.web.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.web.service.WebConsService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

/**
 * 웹상담 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/10/20
 * @프로그램ID WebCons
 */

@Controller
@RequestMapping(value="/web/cons")
public class WebConsController {

    @Resource
    private WebConsService webConsService;

    @Autowired
    private ServletContext ctx;



    /**
     * 웹상담 상담대상 목록을 조회한다.
     *
     * @param  pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/trgt/list/{pagetype}")
    @ResponseBody
    public ModelAndView getWebConsList(@PathVariable("pagetype") String psPathType, @RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();
        UserSession oUser = (UserSession) SessionUtils.getLoginUser();

     // 웹상담처리(담당자) - 본인 담당 웹상담만 조회
        if ("usr".equals(psPathType)) {
            pmParam.put("chpr_id", oUser.getUserid());
            pmParam.put("pagetype", "usr");
        }

        int nTotal = webConsService.getWebConsCount(pmParam);


        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mWebConsList = webConsService.getWebConsList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mWebConsList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

     /**
     * 상담사별 웹 상담 처리현황을 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dsps/psct")
    @ResponseBody
    public ModelAndView getDspsPsct(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        Map<String, Object> oData = null;

        oData = webConsService.getDspsPsct(pmParam);
        oResult.setData(oData);
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     *  웹상담을 저장한다.(save_flag에 따른 처리방법 다름)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/zzim/update")
    @ResponseBody
    public ModelAndView updateWebConsChpr(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ParamUtils.addSaveParam(pmParam);

        Map<String,Object> param = new HashMap<String, Object>();

        if(pmParam.get("save_flag").equals("Y")){
            pmParam.put("dsps_stat_cd", "");
            Map<String,Object> mParam = webConsService.getWebConsTrgt(pmParam);
            if(mParam.get("chpr_id") == null){
               UserSession userSession = (UserSession) SessionUtils.getLoginUser();
               String sUserId = userSession.getUserid();
               pmParam.put("chpr_id", sUserId);
               pmParam.put("dssr_id", sUserId);

               webConsService.updateWebConsChpr(pmParam);
               param = webConsService.getWebConsTrgt(pmParam);
               oResult.setData(param);

               modelAndView.addObject(oResult);
               return modelAndView;
            }
               oResult.setResult(ActionResultType.ERROR);
               oResult.setErrMsg("찜이 이미 다른 담당자에게 되어있습니다.");
               oResult.setData(param);
               modelAndView.addObject(oResult);
               return modelAndView;
        }else {
               UserSession userSession = (UserSession) SessionUtils.getLoginUser();
               String sUserId = userSession.getUserid();
               pmParam.put("dssr_id", sUserId);
               webConsService.updateWebConsChpr(pmParam);
               param = webConsService.getWebConsTrgt(pmParam);
               oResult.setData(param);

               modelAndView.addObject(oResult);
               return modelAndView;
        }
    }

    /**
     * 템플릿파일을 다운로드한다.
     *
     * @param pmParam Map<String, Object>
     * @param response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/download")
    public void downLoadTemplateFile(@RequestParam Map<String, String> pmParam, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");

        FileInputStream fileInputStream = null;
        ServletOutputStream servletOutputStream = null;

        String sFileNm = StringUtils.defaultString((String) pmParam.get("file_nm"));

        if (sFileNm.equals("")) {
            return;
        }

        try {
            File oTemplateFile = new File(ctx.getRealPath("") + "/common/web-file/" + sFileNm);

            fileInputStream = new FileInputStream(oTemplateFile);
            byte[] aTemplateFileCntn = new byte[(int) oTemplateFile.length()];
            fileInputStream.read(aTemplateFileCntn);

            response.setHeader("Content-Disposition", "attachment;filename=" + sFileNm + ";");
            response.setContentType("Content-type: application/octet-stream");
            servletOutputStream = response.getOutputStream();
            response.setContentLength((int) oTemplateFile.length());
            servletOutputStream.write(aTemplateFileCntn);
        } catch (Exception except) {
            String sExceptNm = except.getClass().getName();

            if ("org.apache.catalina.connector.ClientAbortException".equals(sExceptNm)) {
                //톰캣으로 실행시 Exception에러남 서버에서는 이상없음
            } else {
                except.printStackTrace();
                throw except;
            }
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (servletOutputStream != null) {
                try {
                    servletOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 웹상담 차트 데이터를 검색한다.
     *
     * @param psPathType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/chart-list")
    public ModelAndView getTrctConsChartWeeksList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        oResult.setData(webConsService.getWebConsChartWeeksList(pmParam));

        modelAndView.addObject(oResult);
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
    @RequestMapping(value = "/cons/view")
    public ModelAndView getCons(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        oResult.setData(webConsService.getCons(pmParam));
        modelAndView.addObject(oResult);

        return modelAndView;
    }

}