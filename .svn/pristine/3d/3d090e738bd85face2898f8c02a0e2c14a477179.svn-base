package powerservice.business.dlw.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwMemOcbService;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class DlwMemOcbServiceImpl extends EgovAbstractServiceImpl implements DlwMemOcbService {

    @Resource public DlwMemOcbDao dlwMemOcbDao;



    public List<Map<String, Object>> getSrchOcbCardCntList(Map<String, ?> pmParam)throws Exception{

        System.out.println("=========Enter ServiceImple");

        return dlwMemOcbDao.getSrchOcbCardCntList(pmParam);
    }


 /****    OCB 카드 코드 관리 조회   ****/
    public List<Map<String, Object>> getSrchOcbCardCodeList(Map<String, ?> pmParam)throws Exception{

        System.out.println("=========Enter ServiceImple");

        return dlwMemOcbDao.getSrchOcbCardCodeList(pmParam);
    }


  /****    OCB 카드 코드 관리 등록   ****/


    public String insrtOcbCardCode(Map<String, ?> pmParam) throws Exception {

        int iCnt = dlwMemOcbDao.getOcbCardCodeSeq(pmParam);

        if (iCnt > 0) {
            return "중복";
        }

        String rslt = "";
        dlwMemOcbDao.insrtOcbCardCode(pmParam);
        return "";
    }

    ////ocb산출insert
    public String insrtOcbpoint(Map<String, ?> pmParam) throws Exception {
        dlwMemOcbDao.insrtOcbpoint(pmParam);
        return "";
    }

    /* 카드 코드 삭제   */
    public int deleteCardCode(Map<String, ?> pmParam) throws Exception {
        return dlwMemOcbDao.deleteCardCode(pmParam);
    }
    /* ocb  삭제   */
    public int deleteocb(Map<String, ?> pmParam) throws Exception {

      //  CommonUtils.printLog(pmParam);
        return dlwMemOcbDao.deleteocb(pmParam);
    }

    public List<Map<String, Object>> getSrchMemYHCardList(Map<String, ?> pmParam)throws Exception{

        System.out.println("=========Enter ServiceImple");

        return dlwMemOcbDao.getSrchMemYHCardList(pmParam);
    }



// ocb 산출이력 조회
    public List<Map<String, Object>> getsrchOcbPointhist(Map<String, ?> pmParam)throws Exception{

        System.out.println("=========Enter ServiceImple");

        return dlwMemOcbDao.getsrchOcbPointhist(pmParam);
    }


    public List<Map<String, Object>> getSrchMemCrtFileList(Map<String, ?> pmParam)throws Exception{

        System.out.println("=========Enter ServiceImple");

        return dlwMemOcbDao.getSrchMemCrtFileList(pmParam);
    }

    // OCB 포인트 산출
    public List<Map<String, Object>> getsrchOcbPointList(Map<String, ?> pmParam)throws Exception{

        System.out.println("=========Enter ServiceImple");

        return dlwMemOcbDao.getsrchOcbPointList(pmParam);
    }

    public int getOcbPointCount(Map<String, ?> pmParam) throws Exception {
        return dlwMemOcbDao.getOcbPointCount(pmParam);
    }

    public int getsrchOcbPointcount(Map<String, ?> pmParam) throws Exception {
        return dlwMemOcbDao.getsrchOcbPointcount(pmParam);
    }

    public int insertOcbpointcnt(Map<String, ?> pmParam) throws Exception {
        return dlwMemOcbDao.insertOcbpointcnt(pmParam);
    }




    public List<Map<String, Object>> getnewList(Map<String, ?> pmParam)throws Exception{
       return dlwMemOcbDao.getnewList(pmParam);
    }

    public List<Map<String, Object>> getOCBList(Map<String, ?> pmParam)throws Exception{



        return dlwMemOcbDao.getOCBList(pmParam);
    }

    public List<Map<String, Object>> getocb_rejectList(Map<String, ?> pmParam)throws Exception{

        System.out.println("=========Enter ServiceImple");

        return dlwMemOcbDao.getocb_rejectList(pmParam);
    }


    public void ocb_save_start(DataSetMap ocbsave , String c_gubun) throws Exception {

        Map<String, Object> hmParam = new HashMap<String, Object>();



        for (int i=0; i<ocbsave.size(); ++i) {
            hmParam =(Map<String, Object>) ocbsave.get(i);

            hmParam.put("c_gubun", c_gubun);

            ParamUtils.addSaveParam(hmParam);

            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));



            if ("01".equals(c_gubun)) {
                dlwMemOcbDao.ocb_save_start(hmParam);       //// insert  발급
            } else {
               dlwMemOcbDao.ocb_save_start_re(hmParam);       //// insert  재발급
            }
        }

    }

    public void ocb_update_start(Map<String, Object> hmParam ) throws Exception {

     //   CommonUtils.printLog(hmParam);
        dlwMemOcbDao.ocb_update_start(hmParam);

       }






    public void ocb_point_update_start(Map<String, Object> hmParam ) throws Exception {

        //   CommonUtils.printLog(hmParam);
           dlwMemOcbDao.ocb_point_update_start(hmParam);

          }



    public int getOcbPointHistCount(Map<String, ?> pmParam) throws Exception {
        return dlwMemOcbDao.getOcbPointHistCount(pmParam);
    }




    @Override
    public List<Map<String, Object>> getsrchOcbPointHisList(
            Map<String, ?> pmParam) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void ocb_update_hist(Map<String, Object> srchInDs) throws Exception {
        // TODO Auto-generated method stub

    }


    @Override
    public String insertOcbPointCal(Map<String, ?> pmParam) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    /// 모바일멤버쉽 업데이트
    public void ocb_m_update(DataSetMap ocbmupdate) throws Exception {

        Map<String, Object> hmParam = new HashMap<String, Object>();
        for (int i=0; i<ocbmupdate.size(); ++i) {
            hmParam =(Map<String, Object>) ocbmupdate.get(i);


           // CommonUtils.printLog(hmParam);

               dlwMemOcbDao.ocb_m_update(hmParam);       //// 모바일 멤버쉽 업데이트 발급
        }

    }


    /* ocb  삭제   */
    public void delete_ocb_result(DataSetMap ocbmupdate) throws Exception {

        Map<String, Object> hmParam = new HashMap<String, Object>();
        for (int i=0; i<ocbmupdate.size(); ++i) {
            hmParam =(Map<String, Object>) ocbmupdate.get(i);


           // CommonUtils.printLog(hmParam);

               dlwMemOcbDao.delete_ocb_result(hmParam);       //// 모바일 멤버쉽 업데이트 발급
        }

    }


    /* OCB,멤버쉽 카드발급신청 엑셀업로드 */
    public int uploadIssueExcelList(DataSetMap pmInDsList, Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        for (int i = 0; i <  pmInDsList.size(); i++) {
            Map<String, Object> mInDs = pmInDsList.get(i);
            mInDs.put("reg_man", pmParam.get("rgsr_id"));
            nResult += dlwMemOcbDao.uploadIssueExcelList(mInDs);
        }

        return nResult;
    }

    /* OCB,멤버쉽 카드발급신청 카운트조회 */
    public int getIssueListCnt(Map<String, ?> pmParam) throws Exception {
        return dlwMemOcbDao.getIssueListCnt(pmParam);
    }

    /* OCB,멤버쉽 카드발급신청 조회 */
    public List<Map<String, Object>> getIssueList(Map<String, ?> pmParam)throws Exception{
        return dlwMemOcbDao.getIssueList(pmParam);
    }

    /* OCB,멤버쉽카드 반송처리 상담기록 */
    public void saveConsRetrunCard(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        System.out.println("OCB,멤버쉽카드 반송처리 상담기록 : " + pmParam);

        // 상담 저장
        nResult = dlwMemOcbDao.saveConsRetrunCard(pmParam);

        // 상담이력 저장
        if (nResult > 0) {
            dlwMemOcbDao.saveConsHstrRetrunCard(pmParam);
        }
    }

    /* OCB,멤버쉽 카드요청 삭제처리 */
    public void delRequestIssue(Map<String, Object> hmParam) throws Exception {
        dlwMemOcbDao.delRequestIssue(hmParam);
    }

    /* OCB,멤버쉽 발급진행중인 회원 체크 */
    public int chkIssuingStat(Map<String, ?> pmParam) throws Exception {
        return dlwMemOcbDao.chkIssuingStat(pmParam);
    }

    /* OCB,멤버쉽 카드 반송처리시 반송일자 UPDATE */
    public void updateReturnDt(Map<String, Object> hmParam) throws Exception {
        dlwMemOcbDao.updateReturnDt(hmParam);
    }

}
