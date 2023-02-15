/*
 * (@)# ConsTrctHstrController.java
 *
 * @author 박의준
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

package powerservice.business.cns.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.cns.service.ConsTrctHstrService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

/**
 * VOC 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 박의준
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID ConsTrctHstr
 */
@Controller
@RequestMapping(value = "/cons/cons-trct-hstr")
public class ConsTrctHstrController {

    @Resource
    private ConsTrctHstrService consTrctHstrService;

    /**
     * 상담이관 TO-DO 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/todo-list")
    public ModelAndView getConsTrctHstrTodoList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        UserSession oUser = (UserSession) SessionUtils.getLoginUser();
        pmParam.put("user_id", oUser.getUserid());

        ParamUtils.addCenterParam(pmParam);
        ParamUtils.addPagingParam(pmParam);

        int nTotal = 0;
        List<Map<String, Object>> mList = consTrctHstrService.getConsTrctHstrTodoList(pmParam);
        if (mList != null) {
            nTotal = mList.size();
        }

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 상담이관 정보를 수정한다.
     *
     * @param psOperType String
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save/{operType}")
    public ModelAndView saveConsTrctHstr(@PathVariable("operType") String psOperType, @RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        String sConsno = StringUtils.defaultString((String) pmParam.get("consno"));
        int nTrctSqno = Integer.parseInt(pmParam.get("trct_sqno").toString());

        // 호출 작업 구분 저장
        pmParam.put("oper_typ", psOperType);

        // 상담이관 정보 확인
        Map<String, Object> mConsTrctHstr = consTrctHstrService.getConsTrctHstr(sConsno, nTrctSqno);
        if (mConsTrctHstr == null) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("상담이관 정보가 없습니다.");

            modelAndView.addObject(oResult);
            return modelAndView;
        }

        // 처리담당자 등록
        if ("process".equals(psOperType)) {
            // 상담이관 처리담당자 확인
            String sTrctChprId = StringUtils.defaultString((String) mConsTrctHstr.get("trct_chpr_id"));
            if (!"".equals(sTrctChprId)) {
                oResult.setResult(ActionResultType.ERROR);
                oResult.setErrMsg("이미 처리된 상담이관입니다.");

                modelAndView.addObject(oResult);
                return modelAndView;
            }
            mConsTrctHstr = null;

            // 상담이관 처리담당자 저장
            UserSession oUser = (UserSession) SessionUtils.getLoginUser();
            String sTeamCd = oUser.getTeamcd();
            String sUserId = oUser.getUserid();
            pmParam.put("trct_rsps_dept_cd", sTeamCd);
            pmParam.put("trct_chpr_id", sUserId);

            // 상담 담당자 저장
            pmParam.put("cons_stat_cd", "20"); // 상담 상태 코드 : 처리중
            pmParam.put("cons_dspsmddl_dtpt_cd", "10"); // 상담 처리중 상세 코드 : 일반
            pmParam.put("rsps_dept_cd", sTeamCd); // 담당 부서 코드
            pmParam.put("chpr_id", sUserId); // 담당자 ID
        }

        // 상담이관 저장
        ParamUtils.addSaveParam(pmParam);
        consTrctHstrService.updateConsTrctHstr(pmParam);

        oResult.setData(consTrctHstrService.getConsTrctHstr(sConsno, nTrctSqno));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

}