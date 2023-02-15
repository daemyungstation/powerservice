/*
 * (@)# WklyRprgBasiController.java
 *
 * @author 박상수
 * @version 1.0
 * @date 2015/04/01
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

package powerservice.business.wkly.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.sys.service.FileService;
import powerservice.business.wkly.service.WklyRprgBasiService;
import powerservice.business.wkly.service.WklyRprgEtcService;
import powerservice.common.util.DateUtil;
import powerservice.common.util.converter.ajax.ObjectMapperFactory;
import powerservice.common.util.excel.AutoMergColumnRowProcessor;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 주간보고 관리를 한다.
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 박상수
 * @version 1.0
 * @date 2015/01/05
 * @프로그램ID WklyRprgBasiController
 */
@Controller
@RequestMapping(value = "/wkly/wkly")
public class WklyRprgBasiController {

    @Resource
    private WklyRprgBasiService wklyRprgBasiService;

    @Resource
    private WklyRprgEtcService wklyRprgEtcService;

    @Resource
    private FileService fileService;

    @Resource
    private ServletContext ctx;

    /**
     * 주간보고 대상자를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/user-list")
    public ModelAndView getWklyUserList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = wklyRprgBasiService.getWklyUserCount(pmParam);

        ParamUtils.addPagingParam(pmParam);

        List<Map<String, Object>> mList = wklyRprgBasiService.getWklyUserList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 전주 주간보고의 차주 활동내역을 불러온다.
     *
     * @pmParam request HttpServletRequest
     * @pmParam response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/wklybswr")
    public ModelAndView getWklybswrList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        oResult.setData(wklyRprgBasiService.getWklybswrList(pmParam));
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 주간보고를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveWklyReport(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        String sWkly_rprg_id = StringUtils.defaultString((String) pmParam.get("wkly_rprg_id"));

        ParamUtils.addSaveParam(pmParam);

        if ("".equals(sWkly_rprg_id)) {
            sWkly_rprg_id = wklyRprgBasiService.insertWklyReport(pmParam);
        }

        oResult.setData(sWkly_rprg_id);
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 주간보고를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/commit")
    public ModelAndView commitWklyReport(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        ParamUtils.addSaveParam(pmParam);

        wklyRprgBasiService.commitWklyReport(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 주간보고 대상자를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/result")
    public ModelAndView getWklyReportResult(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = wklyRprgBasiService.getWklyReportResultCount(pmParam);

        List<Map<String, Object>> mList = wklyRprgBasiService.getWklyReportResult(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 주간보고 대상자를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/result/file")
    public ModelAndView getWklyResultFile(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        List<Map<String, Object>> mList = wklyRprgBasiService.getWklyFile(pmParam);

        oResult.setData(mList);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     *  엑셀 다운로드를 한다.
     * @pmParam pmParam Map<String, Object>
     * @pmParam response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/excel-down")
    public ModelAndView excelDown(@RequestParam Map<String, Object> pmParam, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("common/excel-down-result");

        File transFile = null; // 전송 파일
        File[] fileList = null; // 생성 파일 리스트
        File targetPathFile = null; // 작업 폴더
        FileInputStream fin = null;
        ServletOutputStream fout = null;

        // 세션 확인
        UserSession userSession = (UserSession) SessionUtils.getLoginUser();
        if (userSession == null) {
            modelAndView.addObject("result", "error_session");
            return modelAndView;
        }
        String user_id = userSession.getUserid();
        String realPath = ctx.getRealPath("");
        String templateFileName = realPath + "/WEB-INF/xls/WklyReport.xls"; // 템플릿 파일
        String targetPath = realPath + "/WEB-INF/temp/" + user_id; // 사용자 작업 임시 폴더

        System.out.println(templateFileName);
        String targetFileName = "주간보고_" + DateUtil.currentTimeToString(DateUtil.SDF_YYYYMMDDHHMM) + ".xls"; // 생성 엑셀 파일

        // 템플릿 파일 확인
        if (!(new File(templateFileName)).exists()) {
            modelAndView.addObject("result", "error_tempfile");
            return modelAndView;
        }

        // 사용자 작업 임시 폴더 생성
        targetPathFile = new File(targetPath);
        if (!targetPathFile.getParentFile().exists()) {
            targetPathFile.getParentFile().mkdir();
        }
        if (!targetPathFile.exists()) {
            targetPathFile.mkdir();
        }

        // 이전 생성 엑셀 파일 삭제
        fileList = targetPathFile.listFiles();
        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                fileList[i].delete();
            }
        }

        // 엑셀 파라미터 처리
        String sXlsParam = StringUtils.defaultString((String) pmParam.get("xls_param"));
        ObjectMapper oJsonMapper = ObjectMapperFactory.getInstance();
        Map<String, Object> mExcelParam = oJsonMapper.readValue(sXlsParam, new TypeReference<Map<String, Object>>() {});

        // 엑셀 데이터 처리
        List<Map<String, Object>> itemlist = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> headerlist = null;
        List<Map<String, Object>> iteamuserlist = null;

        Map<String, Float> userdatasummap = new HashMap<String, Float>();
        Map<String, Float> usertotalsummap = new HashMap<String, Float>();

        List<Map<String, Object>> mList = wklyRprgBasiService.getWklyReportResult(mExcelParam);
        List<Map<String, Object>> templist = new ArrayList<Map<String, Object>>();
        if (mList != null) {
            headerlist = wklyRprgBasiService.getExcelUserList(mExcelParam);

            String wkly_bswr_typ_cd = "";
            String wkly_bswr_typ_nm = "";
            String wkly_bswr_dtls_cd = "";
            String wkly_bswr_dtls_nm = "";
            String bfwk_plan_cntn = "";
            String thwk_acrs_cntn = "";
            String ntwk_plan_cntn = "";

            if (mList.size() > 0) {
                Map<String, Object> temp = mList.get(0);
                if (temp != null) {
                    wkly_bswr_typ_cd = (String) temp.get("wkly_bswr_typ_cd");
                    wkly_bswr_dtls_cd = (String) temp.get("wkly_bswr_dtls_cd");
                    wkly_bswr_typ_nm = (String) temp.get("wkly_bswr_typ_nm");
                    wkly_bswr_dtls_nm = (String) temp.get("wkly_bswr_dtls_nm");

                }
            }

            for (Map<String, Object> row : mList) {
                if ((!wkly_bswr_typ_cd.equals(row.get("wkly_bswr_typ_cd")) || !wkly_bswr_dtls_cd.equals(row.get("wkly_bswr_dtls_cd"))) && templist.size() > 0) {
                    iteamuserlist = new ArrayList<Map<String, Object>>();
                    for (Map<String, Object> header : headerlist) {
                        Object wkly_bswr_wgvl_vl2 = "";
                        for (Map<String, Object> temp : templist) {
                            if (temp.get("rptr_id").equals(header.get("user_id"))) {
                                wkly_bswr_wgvl_vl2 = temp.get("wkly_bswr_wgvl_vl") != null ? temp.get("wkly_bswr_wgvl_vl") : "";
                                break;
                            }
                        }
                        Map<String, Object> userdata = new HashMap<String, Object>();
                        Float userdatasum = userdatasummap.get((String)header.get("user_id"));
                        Float usertotalsum = usertotalsummap.get((String)header.get("user_id"));
                        if (userdatasum == null) {
                            userdatasum = 0.0f;
                        }
                        if (usertotalsum == null) {
                            usertotalsum = 0.0f;
                        }
                        userdatasum += (wkly_bswr_wgvl_vl2 != null ? Float.parseFloat(!"".equals(String.valueOf(wkly_bswr_wgvl_vl2)) ? String.valueOf(wkly_bswr_wgvl_vl2) : "0.0") : 0.0f);
                        usertotalsum += (wkly_bswr_wgvl_vl2 != null ? Float.parseFloat(!"".equals(String.valueOf(wkly_bswr_wgvl_vl2)) ? String.valueOf(wkly_bswr_wgvl_vl2) : "0.0") : 0.0f);
                        userdatasummap.put((String)header.get("user_id"), userdatasum);
                        usertotalsummap.put((String)header.get("user_id"), usertotalsum);


                        userdata.put("wkly_bswr_wgvl_vl", wkly_bswr_wgvl_vl2);
                        userdata.put("sumdata", userdatasum);
                        userdata.put("totalsumdata", usertotalsum);
                        iteamuserlist.add(userdata);
                    }

                    Map<String, Object> iteamdata = new HashMap<String, Object>();
                    iteamdata.put("wkly_bswr_typ_nm", wkly_bswr_typ_nm);
                    iteamdata.put("wkly_bswr_dtls_nm", wkly_bswr_dtls_nm);
                    iteamdata.put("bfwk_plan_cntn", bfwk_plan_cntn);
                    iteamdata.put("thwk_acrs_cntn", thwk_acrs_cntn);
                    iteamdata.put("ntwk_plan_cntn", ntwk_plan_cntn);
                    iteamdata.put("datalist", iteamuserlist);
                    itemlist.add(iteamdata);

                    if (!wkly_bswr_typ_cd.equals(row.get("wkly_bswr_typ_cd")) && templist.size() > 0) {
                        userdatasummap.clear();
                    }

                    wkly_bswr_typ_cd = (String) row.get("wkly_bswr_typ_cd");
                    wkly_bswr_dtls_cd = (String) row.get("wkly_bswr_dtls_cd");
                    wkly_bswr_typ_nm = (String) row.get("wkly_bswr_typ_nm");
                    wkly_bswr_dtls_nm = (String) row.get("wkly_bswr_dtls_nm");
                    bfwk_plan_cntn = "";
                    thwk_acrs_cntn = "";
                    ntwk_plan_cntn = "";

                    templist.clear();
                }

                if (row.get("bfwk_plan_cntn") != null && !"".equals(row.get("bfwk_plan_cntn"))) {
                    if (!"".equals(bfwk_plan_cntn)) {
                        bfwk_plan_cntn += "\r\n\r\n";
                    }
                    bfwk_plan_cntn += "[" + row.get("rptr_nm") + "]\r\n" + row.get("bfwk_plan_cntn");
                }
                if (row.get("thwk_acrs_cntn") != null && !"".equals(row.get("thwk_acrs_cntn"))) {
                    if (!"".equals(thwk_acrs_cntn)) {
                        thwk_acrs_cntn += "\r\n\r\n";
                    }
                    thwk_acrs_cntn += "[" + row.get("rptr_nm") + "]\r\n" + row.get("thwk_acrs_cntn");
                }
                if (row.get("ntwk_plan_cntn") != null && !"".equals(row.get("ntwk_plan_cntn"))) {
                    if (!"".equals(ntwk_plan_cntn)) {
                        ntwk_plan_cntn += "\r\n\r\n";
                    }
                    ntwk_plan_cntn += "[" + row.get("rptr_nm") + "]\r\n" + row.get("ntwk_plan_cntn");
                }

                templist.add(row);
            }
            if (templist.size() > 0) {
                iteamuserlist = new ArrayList<Map<String, Object>>();

                for (Map<String, Object> header : headerlist) {
                    Object wkly_bswr_wgvl_vl2 = "";
                    for (Map<String, Object> temp : templist) {
                        if (temp.get("rptr_id").equals(header.get("user_id"))) {
                            wkly_bswr_wgvl_vl2 = temp.get("wkly_bswr_wgvl_vl") != null ? temp.get("wkly_bswr_wgvl_vl") : "";
                            break;
                        }
                    }
                    Map<String, Object> userdata = new HashMap<String, Object>();
                    Float userdatasum = userdatasummap.get((String)header.get("user_id"));
                    Float usertotalsum = usertotalsummap.get((String)header.get("user_id"));
                    if (userdatasum == null) {
                        userdatasum = 0.0f;
                    }
                    if (usertotalsum == null) {
                        usertotalsum = 0.0f;
                    }
                    userdatasum += (wkly_bswr_wgvl_vl2 != null ? Float.parseFloat(!"".equals(String.valueOf(wkly_bswr_wgvl_vl2)) ? String.valueOf(wkly_bswr_wgvl_vl2) : "0.0") : 0.0f);
                    usertotalsum += (wkly_bswr_wgvl_vl2 != null ? Float.parseFloat(!"".equals(String.valueOf(wkly_bswr_wgvl_vl2)) ? String.valueOf(wkly_bswr_wgvl_vl2) : "0.0") : 0.0f);

                    userdata.put("wkly_bswr_wgvl_vl", wkly_bswr_wgvl_vl2);
                    userdata.put("sumdata", userdatasum);
                    userdata.put("totalsumdata", usertotalsum);

                    iteamuserlist.add(userdata);
                }

                Map<String, Object> iteamdata = new HashMap<String, Object>();
                iteamdata.put("wkly_bswr_typ_nm", wkly_bswr_typ_nm);
                iteamdata.put("wkly_bswr_dtls_nm", wkly_bswr_dtls_nm);
                iteamdata.put("bfwk_plan_cntn", bfwk_plan_cntn);
                iteamdata.put("thwk_acrs_cntn", thwk_acrs_cntn);
                iteamdata.put("ntwk_plan_cntn", ntwk_plan_cntn);
                iteamdata.put("datalist", iteamuserlist);
                itemlist.add(iteamdata);
                templist = null;
            }
        }

        //기타사항
        List<Map<String, Object>> mEtcList = wklyRprgEtcService.getEtcResult(mExcelParam); //데이터

        Map<String, Object> mCommonData = new HashMap<String, Object>();
        String startdt = StringUtils.defaultString((String) pmParam.get("startdt"));
        String enddt = StringUtils.defaultString((String) pmParam.get("enddt"));
        mCommonData.put("rptr_nm", userSession.getUsernm()); 	// 보고자
        mCommonData.put("team_nm", userSession.getTeamnm());	// 보고자 팀명
        mCommonData.put("startdt", startdt); 					// 시작일시
        mCommonData.put("enddt", enddt);						// 종료일시


        Map<String, Object> exceldata = new HashMap<String, Object>();
        exceldata.put("commonData", mCommonData);
        exceldata.put("itemlist", itemlist);
        exceldata.put("headerlist", headerlist);
        exceldata.put("etclist", mEtcList);

        try {
            // 엑셀 파일 생성
            XLSTransformer transformer = new XLSTransformer();
            // transformer.transformXLS(templateFileName, exceldata, targetPath + "/" + targetFileName);

            fin = new FileInputStream(templateFileName);
            short[] mergeColumnIndex = {0}; // 사업유형 열 MERGE
            AutoMergColumnRowProcessor autoMergColumnRowProcessor = new AutoMergColumnRowProcessor(mergeColumnIndex);
            transformer.registerRowProcessor(autoMergColumnRowProcessor);

            Workbook workbook = transformer.transformXLS(fin, exceldata);
            autoMergColumnRowProcessor.mergeCells();
            workbook.write(new FileOutputStream(targetPath + "/" + targetFileName));


            // 생성 엑셀 파일 확인
            transFile = new File(targetPath + "/" + targetFileName);
            if (transFile != null && transFile.exists()) {
                // 헤더 설정
                StringBuffer contentDisposition = new StringBuffer();
                contentDisposition.append("attachment;fileName=\"");
                contentDisposition.append(URLEncoder.encode(targetFileName, "UTF-8").replaceAll("\\+", "%20"));
                contentDisposition.append("\";");
                response.setHeader("Content-Disposition", contentDisposition.toString());
                response.setContentType("application/x-msexcel");
                response.setContentLength(((Long) transFile.length()).intValue());

                // 파일 전송
                fin = new FileInputStream(transFile);
                fout = response.getOutputStream();
                int readBytes = 0;
                byte[] bytes = new byte[2048];
                while ((readBytes = fin.read(bytes)) != -1) {
                    fout.write(bytes, 0, readBytes);
                }
                fout.flush();
                fout.close();
                fin.close();
            }

        } catch (Exception exception) {
            exception.printStackTrace();

            modelAndView.addObject("result", "error");
            modelAndView.addObject("resultMessage", exception.getMessage());
        } finally {
            if (fout != null) {
                fout.close();
            }
            if (fin != null) {
                fin.close();
            }

            // 작업 파일 삭제
            if (transFile != null && transFile.exists()) {
                transFile.delete();
            }
            // 작업 폴더 삭제
            if (targetPathFile != null && targetPathFile.exists()) {
                fileList = targetPathFile.listFiles();
                if (fileList != null) {
                    for (int i = 0; i < fileList.length; i++) {
                        fileList[i].delete();
                    }
                }

                targetPathFile.delete();
            }
        }

        if (transFile != null) {
            return null;
        } else {
            return modelAndView;
        }
    }

}