/*
 * (@)# TodoController.java
 *
 * @author 이희철
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

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.cns.service.TodoService;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.SessionUtils;

/**
 * TO-DO 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Todo
 */
@Controller
@RequestMapping(value = "/main")
public class TodoController {

    @Resource
    private TodoService todoService;

    /**
     * TO-DO 건수를 조회한다
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/todo")
    public ModelAndView getTodo(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        // 사용자 정보 추가
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
        if (oLoginUser == null) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("로그인 사용자 정보가 없습니다.");

            modelAndView.addObject(oResult);
            return modelAndView;
        }

        pmParam.put("cntr_cd", oLoginUser.getCentercd());
        pmParam.put("user_id", oLoginUser.getUserid());
        oResult.setData(todoService.getTodoCount(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

}