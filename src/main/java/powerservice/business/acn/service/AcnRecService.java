package powerservice.business.acn.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface AcnRecService {

    /**
     * 녹취 파일업로드
     *
     * @return List<HashMap<String,Object>> 업로드 파일목록
     */
    public void uploadAcnRecFile(HttpServletRequest request, HttpServletResponse response) throws Exception;



    /**
     * 녹취이력 삭제/수정
     *
     * @return void
     */
    public void saveAcuonRecList(Map <String, DataSetMap> mapInDs);

    /**
     * 녹취이력 조회
     *
     * @return void
     */
    public Map<String, Object> getAcuonRecHist(Map<String,Object> mapCond) throws Exception;

    /**
     * 녹취이력 목록 조회
     *
     * @return void
     */
    public List<Map<String, Object>> getAcuonRecHistList(Map<String,Object> mapCond) throws Exception;

    /**
     * 녹취파일 애큐온 전송
     *
     * @return void
     */
    public void passAcuonRecList(Map <String, DataSetMap> mapInDs);

    /**
     * Acuon FTP에서 업로드된 녹취 파일목록을 조회한다.
     *
     * @return void
     */
    public List<Map<String, Object>> getAcuonRecFtpFileList(Map<String,Object> mapCond) throws Exception;

    /**
     * 기존 Acuon 녹취 등록 파일을 삭제하고 FTP에서 조회한 정보를 ACUON 녹취이력으로 등록한다.(모두 재등록)
     *
     * @return void
     */
    public void deleteAllAndSaveAcuonRec(XPlatformMapDTO xpDto);

    /**
     * 기존 Acuon 녹취 등록 파일을 삭제하고 FTP에서 조회한 정보를 ACUON 녹취이력으로 등록한다.
     *
     * @return void
     */
    public void deleteAndSaveAcuonRec(XPlatformMapDTO xpDto);

    /**
     * Acuon 녹취 등록 파일 삭제
     *
     * @return void
     */
    public void deleteAcuonRec(XPlatformMapDTO xpDto);


    /**
     * 파일업로드(MG)
     *
     * @return List<HashMap<String,Object>> 업로드 파일목록
     */
    public void uploadMgTransFile(HttpServletRequest request, HttpServletResponse response) throws Exception;


    /**
     * MG전송 파일 관리
     *
     * @return void
     */
    public void saveMgTransList(Map <String, DataSetMap> mapInDs);

    /**
     * MG FTP 파일 전송
     *
     * @return void
     */
    public void passMgTransList(Map <String, DataSetMap> mapInDs);

    /**
     * MG월별 파일 전송 리스트
     *
     * @return void
     */
    public List<Map<String, Object>> getMgFileTransList(Map<String,Object> mapCond) throws Exception;


}