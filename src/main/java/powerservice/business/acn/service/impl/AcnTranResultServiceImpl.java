package powerservice.business.acn.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import powerservice.business.acn.service.AcnTranResultService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.SimpleFtpClient;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Repository
public class AcnTranResultServiceImpl extends EgovAbstractServiceImpl implements
        AcnTranResultService {

    private final Logger log = LoggerFactory.getLogger(AcnTranServiceImpl.class);

    @Resource
    public AcnTranResultDao acnTranResultDao;

    /**
     * 전송월로 검색한 처리구분의 검색 수를 반환한다.
     *
     * @param pmParam
     *            검색 조건
     * @return 검색 건수
     * @throws Exception
     */
    public int getTranResulCount(Map<String, ?> pmParam) throws Exception {
        System.out.println("Enter AcnTranResultServiceImpl");
        return (Integer) acnTranResultDao.getTranResulCount(pmParam);

    }

    /**
     * 전송월로 검색한 처리구분 결과 목록
     *
     * @param pmParam
     *            검색 조건
     * @return 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTranResultList(Map<String, ?> pmParam)
            throws Exception {

        String sTabGubn = CommonUtils.nvls((String)pmParam.get("tab_gubun"));
        if (sTabGubn.equals("sunab")) {
            return acnTranResultDao.getTranResultListSunab(pmParam);
        } else {
            return acnTranResultDao.getTranResultList(pmParam);
        }
    }

    /**
     * 전송월로 검색한 처리구분 결과 목록
     *
     * @param pmParam
     *            검색 조건
     * @return 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTranMonResultList(Map<String, ?> pmParam)
            throws Exception {
        return acnTranResultDao.getTranMonResultList(pmParam);

    }
    
    public List<Map<String, Object>> getacoyencheResultList(Map<String, ?> pmParam)
            throws Exception {
        return acnTranResultDao.getacoyencheResultList(pmParam);

    }
    
    

    /**
     * ???
     *
     * @param param 시험답안 채점 정보
     * @throws Exception
     */
    public int convertTranMonMainResult(DataSetMap dsm) throws Exception {
        int nResult = 0;

        Map<String, Object> hmParam = dsm.get(0);
        String jdate = hmParam.get("jdate").toString(); // 전송일자
        String gdate = hmParam.get("gdate").toString(); // 기준일자

        String serverIp 	= EgovProperties.getProperty("acuon.life.ftp.ip");
        int port 			= Integer.valueOf(EgovProperties.getProperty("acuon.life.ftp.port"));
        String user 		= EgovProperties.getProperty("acuon.life.ftp.user");
        String pw 			= EgovProperties.getProperty("acuon.life.ftp.pw");
        String localDataDir	= EgovProperties.getProperty("acuon.life.join.dataPath");
        String sFilename	= "DML31_" + gdate + ".DAT";
        String ftpDataDir	= EgovProperties.getProperty("acuon.life.ftp.join.dataPath");

      //  System.out.println("vvvvvvvv");
       // System.out.println(sFilename);
        //System.out.println(ftpDataDir);

        log.debug("> gdate : " + gdate);
        log.debug("> sFilename : " + sFilename);
        log.debug("> ftpDataDir : " + ftpDataDir);

        try {
            SimpleFtpClient ftp = new SimpleFtpClient(serverIp, port, user, pw);
            ftp.setDebugMode(true);
            ftp.connect();
            ftp.setPassiveMode();
            ftp.setFileType(FTP.BINARY_FILE_TYPE);

            // 경로 이동
            ftp.chdir(ftpDataDir);




            boolean hasFile = false;
            if ( ftp.hasFile(sFilename) ) {
                hasFile = true;
                log.debug("파일이 있음");
                ftp.download(sFilename, localDataDir + sFilename);
            }
            else {
                log.debug("파일이 없음");
            }

            ftp.close();

            if (!hasFile) {
                log.debug("> 파일이 없음");
                return -1;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return -2;
        }

        try {
            File inFile = new File(localDataDir + sFilename);
            if (!inFile.exists()) {
                return -1;
            }

            String line;
            FileReader reader = new FileReader(inFile);
            BufferedReader bufferedReader = new BufferedReader(reader);

            List<Map<String,Object>> lstAcuonRslt = new ArrayList<Map<String,Object>>();

            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().length() < 1) {
                    continue;
                }

                // System.out.println(line);

                Map<String, Object> mRow = new HashMap<String, Object>();
                mRow.put("gdate", line.substring(0, 8)); // 기준일자
                mRow.put("accnt_no", line.substring(8, 38)); // 계약번호
                mRow.put("ggubun", line.substring(38, 40)); // 거래구분코드
                //mRow.put("3", line.substring(40, 55)); // KTC관리번호
                //mRow.put("4", line.substring(55, 62)); // KTC고객번호
                //mRow.put("5", line.substring(62, 70)); // 실행일자
                //mRow.put("6", line.substring(70, 80)); // 실행금액
                //mRow.put("7", line.substring(80, 90)); // 실지급금액
                //mRow.put("8", line.substring(90, 99)); // 상태
                mRow.put("hal_reject", line.substring(99, 108)); // 결과코드
                mRow.put("jdate", line.substring(108, 116)); // 전송일자
                // mRow.put("11", line.substring(116, line.length())); // 공란
                log.debug("jdate : " + mRow.get("jdate").toString());
                log.debug("기준일자 : " + mRow.get("gdate").toString());
                log.debug("계약번호 : " + mRow.get("accnt_no").toString());
                log.debug("전송일자 : " + mRow.get("gdate").toString());
                log.debug("결과코드 : " + mRow.get("hal_reject").toString());

                lstAcuonRslt.add(mRow);
                if (lstAcuonRslt.size() >= 100) {
                    acnTranResultDao.convertTranMonMainResult(lstAcuonRslt);
                    lstAcuonRslt.clear();
                }
            }

            reader.close();

            if (lstAcuonRslt.size() > 0) {
                acnTranResultDao.convertTranMonMainResult(lstAcuonRslt);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return nResult;
    }

    /**
     * ???
     *
     * @param param 시험답안 채점 정보
     * @throws Exception
     */
    public int processTranMonMainResult(Map<String, ?> pmParam) throws Exception {
        int nResult = acnTranResultDao.processTranMonMainResult(pmParam);
        return nResult;
    }
}
