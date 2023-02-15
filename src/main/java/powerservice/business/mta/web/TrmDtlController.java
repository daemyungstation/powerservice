/*
 * (@)# TrmDtlController.java
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/03/26
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

package powerservice.business.mta.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.mta.service.TrmDtlService;
import powerservice.business.mta.service.WrdDicService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;

/**
 * 용어사전 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/03/26
 * @프로그램ID TrmDtl
 */

@Controller
@RequestMapping(value = "/metasystem/term-dtl")
public class TrmDtlController {

    @Resource
    private TrmDtlService trmDtlService;

    @Resource
    private WrdDicService wrdDicService;

    /**
     * 용어 정보를 검색한다. (15.03.26)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getTrmDtlList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = trmDtlService.getTrmDtlCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = trmDtlService.getTrmDtlList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 용어 정보를 등록/수정한다. (15.03.26, 31)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveTrmDtl(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        Map<String, Object> tempParam = new HashMap<String, Object>();

        String term_id = StringUtils.defaultString((String) pmParam.get("term_id"));	// 용어 ID
        String term_nm = StringUtils.defaultString((String) pmParam.get("term_nm"));	// 용어 명

        String[] split_nm = term_nm.split(" ");	// 입력받은 영문명을 split을 사용하여 ' '로 구분하여 배열에 저장

        ParamUtils.addSaveParam(pmParam);

        tempParam.put("rgsr_id", pmParam.get("rgsr_id"));
        tempParam.put("amnd_id", pmParam.get("amnd_id"));

        if("".equals(term_id)){	// 등록자가 없으면 삽입 (15.03.26, 31)
            for(int i=0; i<split_nm.length; i++){
                String word_id = "";
                String word_nm = split_nm[i];
                tempParam.put("word_nm", word_nm);

                if(i == 0){	// 용어내역 테이블에 내용 삽입 후 용어ID 값을 받아온다.
                    term_id = trmDtlService.insertTrmDtl(pmParam);
                    tempParam.put("term_id", term_id);
                }

                word_id = wrdDicService.getWrdDicId(tempParam);	// 단어 사전 테이블에서 단어ID 값을 받아온다.
                tempParam.put("word_id", word_id);

                trmDtlService.insertCommondiction(tempParam);	// 용어 구성 내역 테이블에 내용을 삽입한다.
            }

        } else { // 등록자가 있으면 수정 (15.03.26, 31)
            tempParam.put("relitemid", term_id); // 삭제할 데이터가 한개 일 때
            tempParam.put("term_id", term_id);
            String nm = trmDtlService.getTrmDtlnm(tempParam); // 용어ID에 해당하는 용어명을 받아온다.

            if(nm.equals(term_nm)){ // 용어명이 변경되지 않았을 때 바로 수정
                trmDtlService.updateTrmDtl(pmParam);

            } else { //용어명이 변경되었을 때 용어ID삭제 후 다시 삽입
                tempParam.put("selectcheck", term_id);

                trmDtlService.deleteTrmDtl(tempParam);	// 용어 내역 테이블과 용어 구성 내역에 있는 데이터 삭제 (기준 : 용어ID)

                for(int i=0; i<split_nm.length; i++){
                    String word_id = "";
                    String word_nm = split_nm[i];
                    tempParam.put("word_nm", word_nm);

                    if(i == 0){	// 용어내역 테이블에 내용 삽입 후 용어ID 값을 받아온다.
                        term_id = trmDtlService.insertTrmDtl(pmParam);
                        tempParam.put("term_id", term_id);
                    }

                    word_id = wrdDicService.getWrdDicId(tempParam);	// 단어 사전 테이블에서 단어ID 값을 받아온다.
                    tempParam.put("word_id", word_id);

                    trmDtlService.insertCommondiction(tempParam);	// 용어 구성 내역 테이블에 내용을 삽입한다.
                }
            }
        }

        oResult.setData(trmDtlService.getTrmDtl(term_id));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 용어 정보를 삭제한다. (15.03.26)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ModelAndView deleteTrmDtl(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        Map<String, Object> tempParam = new HashMap<String, Object>();
        ActionList oData = new ActionList();

        int nTotal = 0;

        String selectcheck = StringUtils.defaultString((String) pmParam.get("selectcheck"));
        pmParam.put("selectcheck", selectcheck.split(","));

        String[] split_ck = selectcheck.split(","); // 입력받은 선택박스를 split을 사용하여 ','로 구분하여 배열에 저장

        for(int i=0; i<split_ck.length; i++){
            String term_id = split_ck[i];
            tempParam.put("term_id", term_id);

            nTotal += trmDtlService.getRefercolmCount(tempParam);
        }

        if(nTotal == 0) {
            trmDtlService.deleteTrmDtl(pmParam);
        }

        oData.setTotal(nTotal);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 용어명 중복체크를 한다. (15.03.27)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value  ="/check")
    @ResponseBody
    public ModelAndView checkTrmDtl(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = trmDtlService.checkTrmDtl(pmParam);

        oData.setTotal(nTotal);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 용어명이 중복이 아닐 때 받아온 용어명으로 용어영문명을 구분 한다. (15.03.27, 31 // 15.04.01)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/checkdupl")
    @ResponseBody
    public ModelAndView CheckDuplTrmDtl(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();
        ArrayList<String> list = new ArrayList<String>();

        String term_nm = StringUtils.defaultString((String) pmParam.get("term_nm")); // 입력받은 용어명
        String[] split_nm = term_nm.split(" ");	// 입력받은 용어명을 split을 사용하여 띄어쓰기로 구분하여 배열에 저장

        int nTotal = 0; // 입력받은 데이터와 받아온 데이터의 값이 있으면 1씩 증가

        for(int i=0; i<split_nm.length; i++){
            pmParam.put("dateType", "correct");
            pmParam.put("word_nm", split_nm[i]);

            nTotal += wrdDicService.getWrdDicCount(pmParam);

            String eng_nm = "";	// 받아온 데이터의 영어명을 저장되는 변수
            eng_nm = trmDtlService.getWrdDicCheck(split_nm[i]);

            list.add(eng_nm);
        }

        if(nTotal == (split_nm.length)){	// 입력된 용어명의 값이 DB테이블에 모두 있으면 -> 0
            nTotal = 0;
        } else {							// 입력된 용어명의 값이 DB테이블에 하나라도 없으면 -> 구분된 용어명의 갯수
            nTotal = split_nm.length;
        }

        oData.setTotal(nTotal);
        oData.setList(list);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
}