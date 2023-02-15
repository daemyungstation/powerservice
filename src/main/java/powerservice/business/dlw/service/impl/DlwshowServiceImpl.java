/*
 * (@)# DlwEmplServiceImpl.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/02
 * Copyright (c) 2016 by Inwoo tech inc, KOREA. All Rights Reserved.
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

package powerservice.business.dlw.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwshowService;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 사원 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/02
 * @프로그램ID DlwEmpl
 */
@Service
public class DlwshowServiceImpl extends EgovAbstractServiceImpl implements DlwshowService {

     @Resource
        public DlwshowDAO dlwshowDAO;

        /**
         * 사원 정보의 검색 수를 반환한다.
         *
         * @param pmParam 검색 조건
         * @return 사원 정보의 검색 건수
         * @throws Exception
         */
        public int getDlwshowCount(Map<String, ?> pmParam) throws Exception {
            return (Integer) dlwshowDAO.getDlwshowCount(pmParam);
        }
        public int getDlwmagazineCount(Map<String, ?> pmParam) throws Exception {
            return (Integer) dlwshowDAO.getDlwmagazineCount(pmParam);
        }
        

        public List<Map<String, Object>> getDlwshowList(Map<String, ?> pmParam) throws Exception {
            return dlwshowDAO.getDlwshowList(pmParam);
        }
        public List<Map<String, Object>> getDlwmagazineList(Map<String, ?> pmParam) throws Exception {
            return dlwshowDAO.getDlwmagazineList(pmParam);
        }

        
        public int getDlwshownmCount(Map<String, ?> pmParam) throws Exception {
            return (Integer) dlwshowDAO.getDlwshowCount(pmParam);
        }
        public List<Map<String, Object>> getDlwshownmList(Map<String, ?> pmParam) throws Exception {
            return dlwshowDAO.getDlwshownmList(pmParam);
        }
        public int insertshow(Map<String, ?> pmParam) throws Exception {
            return dlwshowDAO.insertshow(pmParam);
        }
        public List<Map<String, Object>> getDlwshowselect(Map<String, ?> pmParam) throws Exception {
            return dlwshowDAO.getDlwshowselect(pmParam);


        }
        public int showupdate(Map<String, ?> pmParam) throws Exception {
            return dlwshowDAO.showupdate(pmParam);
        }


        public int getshowChk1(Map<String, ?> pmParam) throws Exception {
            return dlwshowDAO.getshowChk1(pmParam);
        }
        public int getshowChk2(Map<String, ?> pmParam) throws Exception {
            return dlwshowDAO.getshowChk2(pmParam);
        }
        public int getshowChk3(Map<String, ?> pmParam) throws Exception {
            return dlwshowDAO.getshowChk3(pmParam);
        }
        public int getshowChk4(Map<String, ?> pmParam) throws Exception {
            return dlwshowDAO.getshowChk4(pmParam);
        }
        public int getshowChk5(Map<String, ?> pmParam) throws Exception {
            return dlwshowDAO.getshowChk5(pmParam);
        }
        public int getshowChk6(Map<String, ?> pmParam) throws Exception {
            return dlwshowDAO.getshowChk6(pmParam);
        }
        public int getshowChk7(Map<String, ?> pmParam) throws Exception {
            return dlwshowDAO.getshowChk7(pmParam);
        }

      //  public int updateshowsave(Map<String, ?> pmParam) throws Exception {
      //      return dlwshowDAO.updateshowsave(pmParam);
      //  }


        public void updateshowsave(Map <String, DataSetMap> mapInDs) {

            Map<String, Object> hmParam = new HashMap<String, Object>();

            try {
                DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

         //       log.debug("listInDs.size() : " + listInDs.size());

               for(int i = 0; i < listInDs.size() ; i++){


                    hmParam = listInDs.get(i);


                    //세션
                    ParamUtils.addSaveParam(hmParam);
                    hmParam.put("emple_no", hmParam.get("rgsr_id"));
                    hmParam.put("reg_man", hmParam.get("rgsr_id"));
                    hmParam.put("upd_man", hmParam.get("rgsr_id"));

                    dlwshowDAO.updateshowsave(hmParam);          ///// 업데이트 확정


                 }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        
        public void insertmagazinesave(Map<String, ?> pmParam) throws Exception {
             dlwshowDAO.insertmagazinesave(pmParam);
        }
        
        
        public void del_magazine_all(Map<String, ?> pmParam) throws Exception {
            dlwshowDAO.del_magazine_all(pmParam);
       }
        
        public void del_magazine_cnt(Map <String, DataSetMap> mapInDs) {

            Map<String, Object> hmParam = new HashMap<String, Object>();

            try {
                DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
         
               for(int i = 0; i < listInDs.size() ; i++){


                    hmParam = listInDs.get(i);
                    //세션
                    ParamUtils.addSaveParam(hmParam);
                    hmParam.put("emple_no", hmParam.get("rgsr_id"));
                    hmParam.put("reg_man", hmParam.get("rgsr_id"));
                    hmParam.put("upd_man", hmParam.get("rgsr_id"));

                    dlwshowDAO.del_magazine_all(hmParam);          ///// 업데이트 확정


                 }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
        
        
        
        public void updatemagazine_magam(Map <String, ?> pmParam) throws Exception {
            dlwshowDAO.updatemagazine_magam(pmParam);          ///// 업데이트 확정
        }
        

}
