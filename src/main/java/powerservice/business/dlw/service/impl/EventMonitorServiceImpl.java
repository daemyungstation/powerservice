package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.EventMonitorService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 행사 모니터링 정보 관리
 *
 * @author 정출연
 * @date 2016/11/01
 * @프로그램ID EventMonitor
 */
@Service
public class EventMonitorServiceImpl extends EgovAbstractServiceImpl implements EventMonitorService {

    private final Logger log = LoggerFactory.getLogger(EventMonitorServiceImpl.class);

    @Resource
    public EventMonitorDao EventMonitorDao;

    /**
     * 모니터링 질문지 조회
     *
     * @param pmParam 검색 조건
     * @return 모니터링 질문지
     * @throws Exception
     */
    public List<Map<String, Object>> getMonitorQuestList(Map<String, Object> pmParam) throws Exception {
        return EventMonitorDao.getMonitorQuestList(pmParam);
    }

    public int saveMonitorQuestList(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null) {
                for (int i=0; i<listInDs.size(); ++i) {
                    Map hmParam = listInDs.get(i);

                    ParamUtils.addSaveParam(hmParam);

                    int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                    if (rowType == DataSet.ROW_TYPE_INSERTED) {
                        iCudCnt += EventMonitorDao.insertMonitorQuest(hmParam);
                    }
                    else if (rowType == DataSet.ROW_TYPE_UPDATED) {
                        iCudCnt += EventMonitorDao.updateMonitorQuest(hmParam);
                    }
                    else if (rowType == DataSet.ROW_TYPE_DELETED) {
                        iCudCnt += EventMonitorDao.deleteMonitorQuest(hmParam);
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

    /**
     * 모니터링 결과보고서 조회
     *
     * @param pmParam 검색 조건
     * @return 모니터링결과보고서
     * @throws Exception
     */
    public List<Map<String, Object>> selectMontrRptList(Map<String, Object> pmParam) throws Exception {
        return EventMonitorDao.selectMontrRptList(pmParam);
    }

    /**
     * 모니터링 결과 보고서 등록/수정/삭제
     *
     * @param pmParam 검색 조건
     * @return 모니터링결과보고서
     * @throws Exception
     */
    public int saveMonitorResultReport(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;

        try {
            DataSetMap dsMaster = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap dsDetail = (DataSetMap)mapInDs.get("ds_input2");
            String sRptSeq 		= ""; // 모니터 결과보거서의 PK

            log.debug("dsMaster.size() : " + dsMaster.size());
            log.debug("dsDetail.size() : " + dsDetail.size());

            if (dsMaster != null) {
                Map hmParam = dsMaster.get(0);

                ParamUtils.addSaveParam(hmParam);
                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
                CommonUtils.printLog(hmParam);
                if (rowType == DataSet.ROW_TYPE_INSERTED) { // 결과보고서 등록
                    iCudCnt += EventMonitorDao.insertMontrRpt(hmParam);
                    sRptSeq = (String)hmParam.get("rpt_seq"); // selectKey 채번

                    for (int i=0; i<dsDetail.size(); ++i) {
                        Map mDetail = dsDetail.get(i);
                        mDetail.put("mst_seq", sRptSeq);
                        ParamUtils.addSaveParam(mDetail);
                        iCudCnt += EventMonitorDao.insertMontrRptDtl(mDetail);
                    }
                }
                else if (rowType == DataSet.ROW_TYPE_UPDATED) // 결과보고서 수정/삭제
                {
                    String sDelFg = (String)hmParam.get("del_fg");
                    iCudCnt += EventMonitorDao.updateMontrRpt(hmParam);
                    sRptSeq = (String)hmParam.get("seq");
                    hmParam.put("mst_seq", sRptSeq);

                    iCudCnt += EventMonitorDao.deleteMontrRptDtl(hmParam);

                    if (null == sDelFg || !"Y".equals(sDelFg)) {
                        for (int i=0; i<dsDetail.size(); ++i) {
                            Map mDetail = dsDetail.get(i);
                            ParamUtils.addSaveParam(mDetail);
                            iCudCnt += EventMonitorDao.insertMontrRptDtl(mDetail);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

    /**
     * 모니터링 결과보고서 상세 조회
     *
     * @param pmParam 검색 조건
     * @return 모니터링결과보고서상세
     * @throws Exception
     */
    public List<Map<String, Object>> selectMontrRptDtl(Map<String, Object> pmParam) throws Exception {
        return EventMonitorDao.selectMontrRptDtl(pmParam);
    }

    /**
     * 행사 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getEventInfo(Map<String, Object> pmParam) throws Exception {
        return EventMonitorDao.getEventInfo(pmParam);
    }

    /**
     * 행사회원 여부 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getEventAccntYn(Map<String, Object> pmParam) throws Exception {
        return EventMonitorDao.getEventAccntYn(pmParam);
    }

    /**
     * 지부별 평균 조회
     * 관련화면 : 모니터링 결과 분석
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectMontrRptAnalysisList(Map<String, Object> pmParam) throws Exception {
        return EventMonitorDao.selectMontrRptAnalysisList(pmParam);
    }

    /**
     * 지부별 건수 조회
     * 관련화면 : 모니터링 결과 분석
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectMontrRptAnalysisList1(Map<String, Object> pmParam) throws Exception {
        return EventMonitorDao.selectMontrRptAnalysisList1(pmParam);
    }

    /**
     * 행사자별 모니터링 현황 조회
     * 관련화면 : 모니터링 결과 분석
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectMontrRptAnalysisList2(Map<String, Object> pmParam) throws Exception {
        return EventMonitorDao.selectMontrRptAnalysisList2(pmParam);
    }

    /**
     * 상품분류별 모니터링 현황 조회
     * 관련화면 : 모니터링 결과 분석
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectMontrRptAnalysisList3(Map<String, Object> pmParam) throws Exception {
        return EventMonitorDao.selectMontrRptAnalysisList3(pmParam);
    }

    /**
     * 의전행사 모니터링 상세보고서 조회
     * 관련화면 : 모니터링 결과 분석
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getMontrRptDtlList(Map<String, Object> pmParam) throws Exception {
        return EventMonitorDao.getMontrRptDtlList(pmParam);
    }

    /**
     * 의전행사 모니터링 상세보고서 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateMontrRptDtlBigo(Map<String, ?> pmParam) throws Exception {
        return EventMonitorDao.updateMontrRptDtlBigo(pmParam);
    }

    /**
     * 의전행사 모니터링 상세보고서 신규저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertMontrRptDtlBigo(Map<String, ?> pmParam) throws Exception {
        return EventMonitorDao.insertMontrRptDtlBigo(pmParam);
    }

    /**
     * 의전행사 모니터링 상세보고서 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteMontrRptDtlBigo(Map<String, ?> pmParam) throws Exception {
        return EventMonitorDao.deleteMontrRptDtlBigo(pmParam);
    }
}
