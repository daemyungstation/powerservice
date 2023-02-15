/*
 * (@)# WrdDicController.java
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/03/19
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

import powerservice.business.mta.service.WrdDicService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;

/**
 * 단어사전 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/03/19
 * @프로그램ID WrdDic
 */
@Controller
@RequestMapping(value = "/metasystem/word-dict")
public class WrdDicController {

    @Resource
    private WrdDicService wrdDicService;

    /**
     * 단어 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getWrdDicList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = wrdDicService.getWrdDicCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = wrdDicService.getWrdDicList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 단어 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveWrdDic(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        String word_id = StringUtils.defaultString((String) pmParam.get("word_id"));	// 단어ID

        ParamUtils.addSaveParam(pmParam);

        if("".equals(word_id)){
            // 등록자가 없으면 삽입
            word_id = wrdDicService.insertWrdDic(pmParam);

        } else {
            // 등록자가 있으면 수정
            wrdDicService.updateWrdDic(pmParam);
        }

        oResult.setData(wrdDicService.getWrdDic(word_id));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 단어 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ModelAndView deleteWrdDic(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        Map<String, Object> tempParam = new HashMap<String, Object>();
        ActionList oData = new ActionList();

        int nTotal = 0;

        // 1건일때
        String word_id = StringUtils.defaultString((String) pmParam.get("word_id"));
        pmParam.put("word_id", word_id);

        String selectcheck = StringUtils.defaultString((String) pmParam.get("selectcheck"));
        pmParam.put("selectcheck", selectcheck.split(","));

        String[] split_ck = selectcheck.split(","); // 입력받은 선택박스를 split을 사용하여 ','로 구분하여 배열에 저장

        for(int i=0; i<split_ck.length; i++){
            String com_id = split_ck[i];
            tempParam.put("word_id", com_id);

            nTotal += wrdDicService.getWrdDicIdCount(tempParam);
        }

        if(nTotal == 0) {
            wrdDicService.deleteWrdDic(pmParam);
        }

        oData.setTotal(nTotal);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 단어명 중복체크를 한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value  ="/check")
    @ResponseBody
    public ModelAndView checkWrdDic(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = wrdDicService.checkWrdDic(pmParam);

        oData.setTotal(nTotal);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
}