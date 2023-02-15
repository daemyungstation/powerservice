/*
 * (@)# FcbkServiceImpl.java
 *
 * @author 유오성
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

package powerservice.business.chn.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.chn.service.FcbkService;
import powerservice.business.chn.web.FcbkController;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Comment;
import com.restfb.types.Photo;
import com.restfb.types.Post;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 페이스북 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 유오성
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Fcbk
 */
@Service
public class FcbkServiceImpl extends EgovAbstractServiceImpl implements FcbkService {

    private final Logger LOGGER = LoggerFactory.getLogger(FcbkServiceImpl.class);

    @Resource
    public FcbkDAO fcbkDAO;

    /**
     * 페이스북 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 페이스북 정보의 검색 건수
     * @throws Exception
     */
    public int getFcbkCount(Map<String, ?> pmParam) throws Exception {
        return fcbkDAO.getFcbkCount(pmParam);
    }

    /**
     * 페이스북 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 페이스북 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getFcbkList(Map<String, ?> pmParam) throws Exception {
        return fcbkDAO.getFcbkList(pmParam);
    }

    /**
     * 페이스북 정보(1건)를 검색한다.
     *
     * @param 페이스북 ID
     * @return 페이스북 정보
     * @throws Exception
     */
    public Map<String, Object> getFcbk(String psId) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("fcbk_id", psId);
        return fcbkDAO.getFcbk(mParam);
    }

    /**
     * 페이스북 댓글 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 페이스북 댓글 정보의 검색 건수
     * @throws Exception
     */
    public int getFcbkCmmnCount(Map<String, ?> pmParam) throws Exception {
        return fcbkDAO.getFcbkCmmnCount(pmParam);
    }

    /**
     * 페이스북 댓글 댓글 정보(1건)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 페이스북 댓글 목록
     * @throws Exception
     */
    public Map<String, Object> getFcbkCmmn(Map<String, ?> pmParam) throws Exception {
        return fcbkDAO.getFcbkCmmn(pmParam);
    }

    /**
     * 페이스북 댓글 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 페이스북 댓글 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getFcbkCmmnList(Map<String, ?> pmParam) throws Exception {
        return fcbkDAO.getFcbkCmmnList(pmParam);
    }

    /**
     * 페이스북 사진 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 페이스북 사진 정보의 검색 건수
     * @throws Exception
     */
    public int getFcbkPhtoCount(Map<String, ?> pmParam) throws Exception {
        return fcbkDAO.getFcbkPhtoCount(pmParam);
    }

    /**
     * 페이스북 사진 댓글 정보(1건)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 페이스북 사진 목록
     * @throws Exception
     */
    public Map<String, Object> getFcbkPhto(Map<String, ?> pmParam) throws Exception {
        return fcbkDAO.getFcbkPhto(pmParam);
    }

    /**
     * 페이스북 사진 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 페이스북 사진 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getFcbkPhtoList(Map<String, ?> pmParam) throws Exception {
        return fcbkDAO.getFcbkPhtoList(pmParam);
    }

    /**
     * 게시판 댓글 정보를 등록한다.
     *
     * @param pmParam 게시판 댓글 정보
     * @throws Exception
     */
    public String insertFcbkCmmn(Map<String, ?> pmParam) throws Exception {
        String sKey = "";
        int sResult = fcbkDAO.insertFcbkCmmn(pmParam);
        if (sResult > 0) {
            sKey = (String) pmParam.get("cmmn_id");
        }
        return sKey;
    }

    /**
     * 페이스북 댓글 정보를 수정한다.
     *
     * @param pmParam 게시판 댓글 정보
     * @throws Exception
     */
    public int updateFcbkCmmn(Map<String, ?> pmParam) throws Exception {
        return fcbkDAO.updateFcbkCmmn(pmParam);
    }

    /**
     * 페이스북 정보(포스트,댓글,사진)를 조회하여 등록한다.
     *
     * @throws Exception
     */
    public int insertFcbk() throws Exception {
        int nResult = 0;

        long nSttTm = System.currentTimeMillis();
        System.out.println("@@@ insertFcbk start");

        String sAppSecret = FcbkController.APP_SECRET;
        String sAccessToken = FcbkController.ACCESS_TOKEN;
        String sUserId = FcbkController.USER_ID;

        System.out.println("@@@ insertFcbk APP_SECRET [" + sAppSecret + "]");
        System.out.println("@@@ insertFcbk ACCESS_TOKEN [" + sAccessToken + "]");
        System.out.println("@@@ insertFcbk USER_ID [" + sUserId + "]");

        FacebookClient oFcbkClient = new DefaultFacebookClient(sAccessToken, sAppSecret);
        Connection<Post> posts = oFcbkClient.fetchConnection(sUserId + "/feed", Post.class);//id=app-user-id

        Map<String, Object> pmParam = new HashMap<String, Object>();
        Map<String, Object> mPhotoParam = new HashMap<String, Object>();
        Map<String, Object> mCommentParam = new HashMap<String, Object>();
        SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);

        List<Post> oPostList = posts.getData();
        for (Post oPost : oPostList) {
            pmParam.clear();
            pmParam.put("fcbk_id", oPost.getId());
            pmParam.put("fcbk_cntn", oPost.getMessage());
            pmParam.put("cntr_cd", "CCA");
            pmParam.put("fcbk_rgsr_id", oPost.getFrom().getId());
            pmParam.put("fcbk_rgsr_nm", oPost.getFrom().getName());
            pmParam.put("fcbk_rgsn_dttm", oDateFormat.format(oPost.getCreatedTime()));
            pmParam.put("fcbk_amnd_id", oPost.getFrom().getId());
            pmParam.put("fcbk_amnd_nm", oPost.getFrom().getName());
            pmParam.put("fcbk_amnt_dttm", oDateFormat.format(oPost.getUpdatedTime()));

            if ("photo".equals(oPost.getType())) {
                Photo oPhoto = oFcbkClient.fetchObject(oPost.getObjectId(), Photo.class);

                mPhotoParam.clear();
                mPhotoParam.put("phto_id", oPhoto.getId());
                mPhotoParam.put("fcbk_id", oPost.getId());
                mPhotoParam.put("phto_url", oPhoto.getSource());
                mPhotoParam.put("thum_url", oPhoto.getPicture());
                mPhotoParam.put("cntr_cd", "CCA");
                mPhotoParam.put("rgsr_id", oPost.getFrom().getId());
                mPhotoParam.put("rgsr_nm", oPost.getFrom().getName());
                mPhotoParam.put("rgsn_dttm", oDateFormat.format(oPost.getCreatedTime()));
                mPhotoParam.put("amnd_id", oPost.getFrom().getId());
                mPhotoParam.put("amnd_nm", oPost.getFrom().getName());
                mPhotoParam.put("amnt_dttm", oDateFormat.format(oPost.getUpdatedTime()));

                fcbkDAO.mergeFcbkPhto(mPhotoParam);
            }

            if (!sUserId.equals(oPost.getFrom().getId())) {

                if (oPost.getComments() != null) {
                    List<Comment> oCommentList = oPost.getComments().getData();
                    oPost.getComments().getData();

                    for (Comment oComment : oCommentList) {
                        mCommentParam.clear();
                        mCommentParam.put("cmmn_id", oComment.getId());
                        mCommentParam.put("fcbk_id", oPost.getId());
                        mCommentParam.put("cmmn_cntn", oComment.getMessage());
                        mCommentParam.put("cntr_cd", "CCA");
                        mCommentParam.put("rgsr_id", oComment.getFrom().getId());
                        mCommentParam.put("rgsr_nm", oComment.getFrom().getName());
                        mCommentParam.put("rgsn_dttm", oDateFormat.format(oPost.getCreatedTime()));

                        fcbkDAO.mergeFaceCmmnbook(mCommentParam);
                    }
                }

                nResult += fcbkDAO.mergeFcbk(pmParam);
            }
        }

        long nJobTm = System.currentTimeMillis() - nSttTm;
        System.out.println("@@@ insertFcbk end [" + nResult + "] (" + (nJobTm < 1000 ? (nJobTm + "ms") : (((long) (nJobTm / 1000)) + "s")) + ")");

        return nResult;
    }

}
