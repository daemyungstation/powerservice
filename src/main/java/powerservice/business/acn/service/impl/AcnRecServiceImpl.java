package powerservice.business.acn.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.acn.service.AcnRecService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.SimpleFtpClient;
import powerservice.core.util.ParamUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.oreilly.servlet.MultipartRequest;
import com.tobesoft.xplatform.data.DataSet;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;



/**
 * sss
 *
 * @author
 * @version
 * @date
 * @프로그램ID
 */
@Service
public class AcnRecServiceImpl extends EgovAbstractServiceImpl implements AcnRecService {

    private final Logger log = LoggerFactory.getLogger(AcnRecServiceImpl.class);

	Session session = null;
	Channel channel = null;
	ChannelSftp channelSftp  = null;


    @Resource
    public AcnRecDAO acnRecDAO;


    public void uploadAcnRecFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<HashMap<String, Object>> lstRet = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> mapRet = null;


        String sdate = CommonUtils.nvls(request.getParameter("sdate"));
        log.debug(">> sdate : " + sdate);



     /*   if (!"".equals(sdate)) {

        }
*/
        String localDataDir	= EgovProperties.getProperty("acuon.life.rec.dataPath");
        String tempDir = System.getProperty("java.io.tmpdir");

        log.debug("---uploadAcnRecFile 서비스 - 1");

        // MultipartRequest multi = new MultipartRequest(request, localDataDir, 1024*1024*20, "utf-8", new DefaultFileRenamePolicy());
        MultipartRequest multipartRequest = new MultipartRequest(request, tempDir, 1024*1024*20);

        Enumeration enm = multipartRequest.getFileNames();
        String sKey = "";


        BufferedOutputStream fos = null;
        String sServerFileUrl = "";
        long lSize = -1;
        File dir;

        String sCurrDthms 	=  sdate;           ////파일일자셋팅!!
        String sAccntNo 	= "";
        String sSdate 		= "";
        String sTmp 		= "";
        int bytesRead 		= -1;
        byte[] buf 			= new byte[1024];

        try {
            while (enm.hasMoreElements()) {
                sKey = (String)enm.nextElement();
                log.debug("sKey : " + sKey);
                mapRet = new HashMap<String, Object>();
                File upfile = multipartRequest.getFile(sKey);

                String sOrigFn = upfile.getName();

                log.debug("---uploadAcnRecFile 서비스 - 2");

                sServerFileUrl = localDataDir;

                dir = new File(sServerFileUrl);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                log.debug("---uploadAcnRecFile 서비스 - 3");
                System.out.println(">>> 파일저장경로 : " + sServerFileUrl + sOrigFn);


                String[] sArrTok = sOrigFn.split("\\.");
                sArrTok = sArrTok[0].split("_");
                if (sArrTok.length < 2) {
                    throw new Exception("파일명이 잘못되었습니다.");
                }
                sSdate 		= sCurrDthms.substring(0,8);
                sAccntNo 	= sArrTok[0];

                log.debug("sAccntNo : " + sAccntNo);
                log.debug("sSdate : " + sSdate);

                log.debug("---uploadAcnRecFile 서비스 - 4");

                /* 파일업로드 결과 정보 설정 */

                ParamUtils.addSaveParam(mapRet);

                mapRet.put("sdate"		, sSdate);
                mapRet.put("accnt_no"	, sAccntNo);
                mapRet.put("filename"	, sOrigFn);
                // mapRet.put("reg_dm"		, "");
                mapRet.put("agubun"		, "1");

                Map<String, Object> mOld = acnRecDAO.getAcuonRecHist(mapRet);
                if (null != mOld) {
                    String agubun = CommonUtils.nvls((String)mOld.get("agubun"));
                    if ("2".equals(agubun)) {
                        throw new Exception(sOrigFn + " 파일은 애큐온에 전송된 파일이므로 업로드되지 않았습니다.");
                    }
                    else {
                        acnRecDAO.deleteAcuonRecHist(mapRet);
                    }
                }
                acnRecDAO.insertAcuonRecHist(mapRet);

                // 기존에 파일이 존재하면 삭제
                File tgtDir = new File(sServerFileUrl + sSdate);
                if ( !tgtDir.exists() ) {
                    log.debug(">" + sServerFileUrl + sSdate + "폴더 없음");
                    tgtDir.mkdir();
                } else {
                    log.debug(">" + sServerFileUrl + sSdate + "폴더 있음");
                }

                // 기존에 파일이 존재하면 삭제
                File oldFile = new File(sServerFileUrl + sSdate + "/" + sOrigFn);
                if (oldFile.exists()) {
                    oldFile.delete();
                }

//        		log.debug("upfile.getAbsolutePath() : " + upfile.getAbsolutePath());
//        		FileInputStream fis = new FileInputStream(upfile.getAbsolutePath());
//
//        		// 파일을 저장 경로로 이동
//        		fos = new BufferedOutputStream(new FileOutputStream(sServerFileUrl + "/" + sSdate + "/" + sOrigFn));
//        		fos.write((int)upfile.length());
//        		 while ((bytesRead = fis.read(buf)) > 0) {
//        			 fos.write(buf, 0, bytesRead);
//        		}
//        		fos.close();
//        		fis.close();

                File saveAs = new File(sServerFileUrl + sSdate + "/" + sOrigFn);
                copySingleFile(upfile, saveAs);

            }

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void saveAcuonRecList(Map <String, DataSetMap> mapInDs) {

        Map<String, Object> hmParam = new HashMap<String, Object>();

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            log.debug("listInDs.size() : " + listInDs.size());

            for(int i = 0; i < listInDs.size() ; i++){


                hmParam = listInDs.get(i);

                log.debug("i = " + i);

                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                log.debug("rowType = " + rowType);

                ParamUtils.addCenterParam(hmParam);
                if(rowType ==DataSet.ROW_TYPE_DELETED){
                    acnRecDAO.deleteAcuonRecHist(hmParam);
                }
                else if(rowType ==DataSet.ROW_TYPE_UPDATED){
                    acnRecDAO.updateAcuonRecHist(hmParam);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> getAcuonRecHistList(Map<String,Object> mapCond) throws Exception {
        return acnRecDAO.getAcuonRecHistList(mapCond);
    }

    public Map<String, Object> getAcuonRecHist(Map<String,Object> mapCond) throws Exception {
        return acnRecDAO.getAcuonRecHist(mapCond);
    }

    public void passAcuonRecList(Map <String, DataSetMap> mapInDs) {

        Map<String, Object> hmParam = new HashMap<String, Object>();

        String serverIp 	= EgovProperties.getProperty("acuon.life.ftp.ip");
        int port 			= Integer.valueOf(EgovProperties.getProperty("acuon.life.ftp.port"));
        String user 		= EgovProperties.getProperty("acuon.life.ftp.user");
        String pw 			= EgovProperties.getProperty("acuon.life.ftp.pw");
        String localDataDir	= EgovProperties.getProperty("acuon.life.rec.dataPath"); // 녹취파일 업로드 경로
        String ftpRootDir	= EgovProperties.getProperty("acuon.life.ftp.rec.dataPath"); // 애큐온 FTP 서버 경로
        String ftpDateDir	= "";
        boolean isSuccess 	= false;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            log.debug("listInDs.size() : " + listInDs.size());

            try {
                SimpleFtpClient ftp = new SimpleFtpClient(serverIp, port, user, pw);
                ftp.setDebugMode(true);
                ftp.connect();
                ftp.setPassiveMode();
                ftp.setFileType(FTP.BINARY_FILE_TYPE);

                /********************************************************/
                for(int i = 0; i < listInDs.size() ; i++){

                    hmParam = listInDs.get(i);

                    String sChk 		= CommonUtils.nvls((String)hmParam.get("chk"));
                    String sFilename 	= CommonUtils.nvls((String)hmParam.get("filename"));
                    String sAgubun 		= CommonUtils.nvls((String)hmParam.get("filename"));
                    String sAccntNo 	= CommonUtils.nvls((String)hmParam.get("accnt_no"));
                    String sSdate 		= CommonUtils.nvls((String)hmParam.get("sdate"));

                    String sFtpFile 	= ftpRootDir 	+ sSdate + "/" + sFilename;
                    String sLocalFile 	= localDataDir 	+ sSdate + "/" + sFilename;

                    log.debug(">sFtpFile : " + sFtpFile);
                    log.debug(">sLocalFile : " + sLocalFile);

                    log.debug("sChk = " + sChk);

                    // 화면에서 체크 한건만 처리한다.
                    if (!"1".equals(sChk)) {
                        continue;
                    }

                    // ROOT 경로로 이동
                    ftp.chdir(ftpRootDir);

                    // 년월일 디렉토리가 존재하는지 체크, 디렉토리가 없으면 새엇ㅇ
                    if ( !ftp.hasFile(ftpRootDir + sSdate) ) {
                         ftp.mkDir(ftpRootDir + sSdate);
                        //ftp.mkDir(sSdate);
                        log.debug("FTP 서버에 폴더생성 완료 - " + ftpRootDir + sSdate);
                    }

                    // 업로드할 파일과 동일한 파일이 존재하는지 체크
                    if ( ftp.hasFile(sFtpFile) ) {
                        ftp.deleteFile(sFtpFile);
                        log.debug("파일이 있어서 삭제 - " + sFtpFile);
                    }
                    else {
                        log.debug("파일이 없음 - " + sFtpFile);
                    }

                    // 경로 이동
                    ftp.chdir("/");
                    ftp.chdir(ftpRootDir.substring(1) + sSdate);

                    // 파일 업로드
                    File file = new File(sLocalFile);
                    if (file.exists())
                    {
                        log.debug("> file.getPath() : " + file.getPath());
                        log.debug("> file.getName() : " + file.getName());

                        isSuccess = ftp.upload(file.getPath(), sFilename);

                        if (isSuccess)
                        {
                            log.debug("[" + file.getPath() + "] 파일 업로드 성공!");

                            hmParam.put("agubun", "2");
                            ParamUtils.addCenterParam(hmParam);
                            acnRecDAO.updateAcuonRecHist(hmParam);
                        }
                        else {
                            log.debug("[" + file.getPath() + "] 파일 업로드 실패!");
                        }
                    }
                }
                /********************************************************/

                ftp.close();

            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copySingleFile(File sourceFile, File destFile)
            throws IOException {
        log.debug("COPY FILE: " + sourceFile.getAbsolutePath() + " TO: " + destFile.getAbsolutePath());
        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel sourceChannel = null;
        FileChannel destChannel = null;

        try {
            sourceChannel = new FileInputStream(sourceFile).getChannel();
            destChannel = new FileOutputStream(destFile).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(), destChannel);
        } finally {
            if (sourceChannel != null) {
                sourceChannel.close();
            }
            if (destChannel != null) {
                destChannel.close();
            }
        }
    }

    public List<Map<String, Object>> getAcuonRecFtpFileList(Map<String,Object> mapCond) throws Exception {

        List<String> lstFile 			= new ArrayList<>();
        List<Map<String,Object>> lstRet = new ArrayList<>();

        Map<String, Object> mRow = new HashMap<String, Object>();

        String serverIp 	= EgovProperties.getProperty("acuon.life.ftp.ip");
        int port 			= Integer.valueOf(EgovProperties.getProperty("acuon.life.ftp.port"));
        String user 		= EgovProperties.getProperty("acuon.life.ftp.user");
        String pw 			= EgovProperties.getProperty("acuon.life.ftp.pw");
        String ftpRootDir	= EgovProperties.getProperty("acuon.life.ftp.rec.dataPath"); // 애큐온 FTP 서버 경로
        String sdate		= CommonUtils.nvls((String)mapCond.get("sdate"));
        boolean isSuccess 	= false;

        SimpleFtpClient ftp = null;

        try {
            ftp = new SimpleFtpClient(serverIp, port, user, pw);
            ftp.setDebugMode(true);
            ftp.connect();
            ftp.setPassiveMode();
            ftp.setFileType(FTP.BINARY_FILE_TYPE);

            isSuccess = ftp.chdir(ftpRootDir + sdate);

            if (isSuccess) {
                lstFile = ftp.getFileNameList();

                for (int i=0; i<lstFile.size(); ++i) {
                    mRow = new HashMap<String, Object>();
                    mRow.put("filename", lstFile.get(i));
                    mRow.put("accnt_no", lstFile.get(i).substring(0,12));
                    mRow.put("sdate", sdate);
                    mRow.put("stat", "");
                    mRow.put("chk", "");
                    lstRet.add(mRow);

                }
            } else {
                log.info("FTP 경로 [" + ftpRootDir + sdate + "] 이 존재하지 않음");
            }

            ftp.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return lstRet;
    }

    public void deleteAllAndSaveAcuonRec(XPlatformMapDTO xpDto) {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        try {

            DataSetMap ds_list = (DataSetMap)mapInDs.get("ds_list2");

            Map<String, Object> hmParam = new HashMap<String, Object>();

            String chk = "";

            if ( null != ds_list && ds_list.size() > 0) {

                hmParam = ds_list.get(0);
                acnRecDAO.deleteAcuonRecHistBySdate(hmParam);

                for(int i = 0; i < ds_list.size() ; i++)
                {
                    hmParam = ds_list.get(i);
                    ParamUtils.addSaveParam(hmParam);
                    ParamUtils.addCenterParam(hmParam);

                    chk = CommonUtils.nvls((String)hmParam.get("chk"));

                    int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                    if( rowType ==DataSet.ROW_TYPE_UPDATED && "1".equals(chk)){

                        /// 애큐온에있는 파일.
                        hmParam.put("agubun","2");
                        acnRecDAO.insertAcuonRecHist(hmParam);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAndSaveAcuonRec(XPlatformMapDTO xpDto) {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        try {

            DataSetMap ds_list = (DataSetMap)mapInDs.get("ds_list2");
            Map<String, Object> hmParam = new HashMap<String, Object>();
            String chk = "";

            if ( null != ds_list && ds_list.size() > 0) {

                for(int i = 0; i < ds_list.size() ; i++)
                {
                    hmParam = ds_list.get(i);
                    ParamUtils.addSaveParam(hmParam);
                    ParamUtils.addCenterParam(hmParam);

                    chk = CommonUtils.nvls((String)hmParam.get("chk"));

                    int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                    if( rowType ==DataSet.ROW_TYPE_UPDATED && "1".equals(chk)){
                        hmParam.put("agubun", "2");
                        acnRecDAO.deleteAcuonRecHist(hmParam);

                        acnRecDAO.insertAcuonRecHist(hmParam);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAcuonRec(XPlatformMapDTO xpDto) {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        try {
            DataSetMap ds_list = (DataSetMap)mapInDs.get("ds_input");
            Map<String, Object> hmParam = new HashMap<String, Object>();
            String chk = "";

            log.debug("ds_list.size() : " + ds_list.size());

            if ( null != ds_list && ds_list.size() > 0) {

                for(int i = 0; i < ds_list.size() ; i++)
                {
                    hmParam = ds_list.get(i);

                    chk = CommonUtils.nvls((String)hmParam.get("chk"));

                    int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                    if( rowType ==DataSet.ROW_TYPE_UPDATED && "1".equals(chk)){
                        acnRecDAO.deleteAcuonRecHist(hmParam);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * MG 파일 업로드
     * */
    public void uploadMgTransFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<HashMap<String, Object>> lstRet = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> mapRet = null;

        String sdate = CommonUtils.nvls(request.getParameter("sdate"));
        log.debug(">> sdate : " + sdate);

        String localDataDir	= EgovProperties.getProperty("mg.life.rec.dataPath");
        String tempDir = System.getProperty("java.io.tmpdir");

        log.debug("---uploadAcnRecFile 서비스 - 1");

        // MultipartRequest multi = new MultipartRequest(request, localDataDir, 1024*1024*20, "utf-8", new DefaultFileRenamePolicy());
        MultipartRequest multipartRequest = new MultipartRequest(request, tempDir, 1024*1024*20);

        Enumeration enm = multipartRequest.getFileNames();
        String sKey = "";


        BufferedOutputStream fos = null;
        String sServerFileUrl = "";
        long lSize = -1;
        File dir;

        String sCurrDthms 	=  sdate;           ////파일일자셋팅!!
        String sAccntNo 	= "";
        String sSdate 		= "";
        String sTmp 		= "";
        int bytesRead 		= -1;
        byte[] buf 			= new byte[1024];

        try {
            while (enm.hasMoreElements()) {
                sKey = (String)enm.nextElement();
                log.debug("sKey : " + sKey);
                mapRet = new HashMap<String, Object>();
                File upfile = multipartRequest.getFile(sKey);

                String sOrigFn = upfile.getName();

                log.debug("---uploadAcnRecFile 서비스 - 2");

                sServerFileUrl = localDataDir;

                dir = new File(sServerFileUrl);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                log.debug("---uploadAcnRecFile 서비스 - 3");
                System.out.println(">>> 파일저장경로 : " + sServerFileUrl + sOrigFn);


                String[] sArrTok = sOrigFn.split("\\.");
                sArrTok = sArrTok[0].split("_");
                if (sArrTok.length < 2) {
                    throw new Exception("파일명이 잘못되었습니다.");
                }
                sSdate 		= sCurrDthms.substring(0,8);
                sAccntNo 	= sArrTok[0];

                log.debug("sAccntNo : " + sAccntNo);
                log.debug("sSdate : " + sSdate);

                log.debug("---uploadAcnRecFile 서비스 - 4");

                /* 파일업로드 결과 정보 설정 */

                ParamUtils.addSaveParam(mapRet);

                mapRet.put("sdate"		, sSdate);
                mapRet.put("accnt_no"	, sAccntNo);
                mapRet.put("filename"	, sOrigFn);
                // mapRet.put("reg_dm"		, "");
                mapRet.put("agubun"		, "M");

                /*
                Map<String, Object> mOld = acnRecDAO.getMgFileTransList(mapRet);
                if (null != mOld) {
                    String agubun = CommonUtils.nvls((String)mOld.get("agubun"));
                    if ("2".equals(agubun)) {
                        throw new Exception(sOrigFn + " 파일은 애큐온에 전송된 파일이므로 업로드되지 않았습니다.");
                    }
                    else {
                        acnRecDAO.deleteAcuonRecHist(mapRet);
                    }
                }
                */
                acnRecDAO.insertMgFileTransList(mapRet);

                // 기존에 파일이 존재하면 삭제
                File tgtDir = new File(sServerFileUrl + sSdate);
                if ( !tgtDir.exists() ) {
                    log.debug(">" + sServerFileUrl + sSdate + "폴더 없음");
                    tgtDir.mkdir();
                } else {
                    log.debug(">" + sServerFileUrl + sSdate + "폴더 있음");
                }

                // 기존에 파일이 존재하면 삭제
                File oldFile = new File(sServerFileUrl + sSdate + "/" + sOrigFn);
                if (oldFile.exists()) {
                    oldFile.delete();
                }

//        		log.debug("upfile.getAbsolutePath() : " + upfile.getAbsolutePath());
//        		FileInputStream fis = new FileInputStream(upfile.getAbsolutePath());
//
//        		// 파일을 저장 경로로 이동
//        		fos = new BufferedOutputStream(new FileOutputStream(sServerFileUrl + "/" + sSdate + "/" + sOrigFn));
//        		fos.write((int)upfile.length());
//        		 while ((bytesRead = fis.read(buf)) > 0) {
//        			 fos.write(buf, 0, bytesRead);
//        		}
//        		fos.close();
//        		fis.close();

                File saveAs = new File(sServerFileUrl + sSdate + "/" + sOrigFn);
                copySingleFile(upfile, saveAs);

            }

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    /*
     * MG업로드 파일 관리
     * */
    public void saveMgTransList(Map <String, DataSetMap> mapInDs) {

        Map<String, Object> hmParam = new HashMap<String, Object>();

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            log.debug("listInDs.size() : " + listInDs.size());

            for(int i = 0; i < listInDs.size() ; i++){


                hmParam = listInDs.get(i);

                log.debug("i = " + i);

                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                log.debug("rowType = " + rowType);

                ParamUtils.addCenterParam(hmParam);

                if(rowType ==DataSet.ROW_TYPE_DELETED){

                	String localDataDir	= EgovProperties.getProperty("mg.life.rec.dataPath");
                	String UploadDt = (String)hmParam.get("sdate");
                	String fileNm = (String)hmParam.get("filename");

                	System.out.println("xxxxxxxxxxxxxxxxxxxx> " + localDataDir  + UploadDt + "/" + fileNm);

                    // 기존에 파일이 존재하면 삭제
                    File oldFile = new File(localDataDir + UploadDt + "/" + fileNm);
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }

                    acnRecDAO.deleteAcuonRecHist(hmParam);
                }
                else if(rowType ==DataSet.ROW_TYPE_UPDATED){
                    acnRecDAO.updateAcuonRecHist(hmParam);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
     * MG FTP전송
     * */
    public void passMgTransList(Map <String, DataSetMap> mapInDs) {

        Map<String, Object> hmParam = new HashMap<String, Object>();

        boolean isSuccess 	= false;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            log.debug("listInDs.size() : " + listInDs.size());

            String localDataDir	= EgovProperties.getProperty("mg.life.rec.dataPath"); // 녹취파일 업로드 경로
            String ftpRootDir	= EgovProperties.getProperty("mg.life.ftp.join.dataPath"); // 애큐온 FTP 서버 경로
            String ftpDateDir	= "";


            try {
				//sftp connection
				Connect();

				// 8. 채널을 FTP용 채널 객체로 캐스팅한다.
				channelSftp = (ChannelSftp) channel;
				SftpATTRS attrs = null;

            	  for(int i = 0; i < listInDs.size() ; i++){

                      hmParam = listInDs.get(i);

                      String sChk 		= CommonUtils.nvls((String)hmParam.get("chk"));
                      String sFilename 	= CommonUtils.nvls((String)hmParam.get("filename"));
                      String sAgubun 		= CommonUtils.nvls((String)hmParam.get("filename"));
                      String sAccntNo 	= CommonUtils.nvls((String)hmParam.get("accnt_no"));
                      String sSdate 		= CommonUtils.nvls((String)hmParam.get("sdate"));

                      FileInputStream fis = null;

                      String sFtpFile 	= ftpRootDir 	+ sSdate + "/" + sFilename; //ftp 업로드 할 파일
                      String sLocalFile 	= localDataDir 	+ sSdate + "/" + sFilename; //로컬 파일 파일

                      // 화면에서 체크 한건만 처리한다.
                      if (!"1".equals(sChk)) {
                          continue;
                      }

                  	// file upload
                    // ftp폴더로 이동 (기본은 /로 되어 있음 user생성 폴더와 상관없이)
      				//channelSftp.cd(ftpRootDir);
      				//System.out.println(channelSftp.pwd());

      				try{
          				attrs = channelSftp.stat(ftpRootDir + "/" + sSdate);
      				}catch (Exception e){

      				}
      				System.out.println(attrs);

      			    //오늘 날짜의 디렉터리 파일이 없으면 폴더를 만든다.
      				if(attrs == null){
      					channelSftp.mkdir(ftpRootDir + "/" + sSdate);
      				}

      				// 폴더 생성 후 디렉터리로 이동
      				channelSftp.cd(ftpRootDir + "/" + sSdate);
      				System.out.println(channelSftp.pwd());

      				// file upload

      	            File file = new File(sLocalFile);
      	            // 입력 파일을 가져온다.
      	            fis = new FileInputStream(file);
      	            // 파일을 업로드한다.

	      	      	try{
	      	      		channelSftp.put(fis, file.getName());
	  				}catch (Exception e){
	  					System.out.println("file NOT upload...");
	  				}

      	            fis.close();
      	            System.out.println("file upload...");

                    hmParam.put("agubun", "G");
                    ParamUtils.addCenterParam(hmParam);
                    acnRecDAO.updateAcuonRecHist(hmParam);

            	  }

            	disconnect();

	            } catch (Exception e) {
	                System.out.println(e.getMessage());
	                e.printStackTrace();
	            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*MG월별 파일 전송 리스트*/
    public List<Map<String, Object>> getMgFileTransList(Map<String,Object> mapCond) throws Exception {
        return acnRecDAO.getMgFileTransList(mapCond);
    }


    /*MG SFTP전송 시스템 로직*/
    public void Connect() {
        String serverIp 	= EgovProperties.getProperty("mg.life.ftp.ip");
        int port 			= Integer.valueOf(EgovProperties.getProperty("mg.life.ftp.port"));
        String user 		= EgovProperties.getProperty("mg.life.ftp.user");
        String pw 			= EgovProperties.getProperty("mg.life.ftp.pw");

		System.out.println("==> Connecting to " + serverIp);

	   	 // 1. JSch 객체를 생성한다.
	   	 JSch jsch = new JSch();

    	 try {
    	 // 2. 세션 객체를 생성한다(사용자 이름, 접속할 호스트, 포트를 인자로 전달한다.)
    	 session = jsch.getSession(user, serverIp, port);

    	 // 3. 패스워드를 설정한다.
    	 session.setPassword(pw);

    	 // 4. 세션과 관련된 정보를 설정한다.
    	 java.util.Properties config = new java.util.Properties();

    	 // 4-1. 호스트 정보를 검사하지 않는다.
    	 config.put("StrictHostKeyChecking", "no");
    	 session.setConfig(config);

    	 // 5. 접속한다.
    	 session.connect();

    	 // 6. sftp 채널을 연다.
    	 channel = session.openChannel("sftp");

    	 // 7. 채널에 연결한다.
    	 channel.connect();
    	 } catch (JSchException e) {
    		 e.printStackTrace();
    	 }

    	 System.out.println("==> Connected to " + serverIp);
    }

    public void disconnect() {
        if(session.isConnected()){
            System.out.println("disconnecting...");
            channelSftp.disconnect();
            channel.disconnect();
            session.disconnect();
        }
    }

    /*
    private void connect() {
    	/*
    	 System.out.println("==> Connecting to " + host);
    	 Session session = null;
    	 Channel channel = null;

    	 // 1. JSch 객체를 생성한다.
    	 JSch jsch = new JSch();
    	 try {
    	 // 2. 세션 객체를 생성한다(사용자 이름, 접속할 호스트, 포트를 인자로 전달한다.)
    	 session = jsch.getSession(username, host, port);

    	 // 3. 패스워드를 설정한다.
    	 session.setPassword(password);

    	 // 4. 세션과 관련된 정보를 설정한다.
    	 java.util.Properties config = new java.util.Properties();
    	 // 4-1. 호스트 정보를 검사하지 않는다.
    	 config.put("StrictHostKeyChecking", "no");
    	 session.setConfig(config);

    	 // 5. 접속한다.
    	 session.connect();

    	 // 6. sftp 채널을 연다.
    	 channel = session.openChannel("sftp");

    	 // 7. 채널에 연결한다.
    	 channel.connect();
    	 } catch (JSchException e) {
    	 e.printStackTrace();
    	 }

    	 // 8. 채널을 FTP용 채널 객체로 캐스팅한다.
    	 channelSftp = (ChannelSftp) channel;
    	 System.out.println("==> Connected to " + host);
    	}

    	public void upload(String catalinaHome, File file) throws SocketException, IOException {
    // 앞서 만든 접속 메서드를 사용해 접속한다.
    connect();
    System.out.println("==> Uploading: " + file.getPath() + " at " + host);
    FileInputStream in = null;
    try {
        // 입력 파일을 가져온다.
        in = new FileInputStream(file);

        // 업로드하려는 위치르 디렉토리를 변경한다.
        channelSftp.cd(catalinaHome + "/webapps");

        // 파일을 업로드한다.
        channelSftp.put(in, file.getName());
    } catch (SftpException e) {
        e.printStackTrace();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } finally {
        try {
            // 업로드된 파일을 닫는다.
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    System.out.println("==> Uploaded: " + file.getPath() + " at " + host);
}


    public void download(String fileName, String localDir) throws Exception{
        byte[] buffer = new byte[1024];
        BufferedInputStream bis;
        connect();
        try {
            // Change to output directory
            String cdDir = fileName.substring(0, fileName.lastIndexOf("/") + 1);
            sftpChannel.cd(cdDir);

            File file = new File(fileName);
            bis = new BufferedInputStream(sftpChannel.get(file.getName()));

            File newFile = new File(localDir + "/" + file.getName());

            // Download file
            OutputStream os = new FileOutputStream(newFile);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            int readCount;
            while ((readCount = bis.read(buffer)) > 0) {
                bos.write(buffer, 0, readCount);
            }
            bis.close();
            bos.close();
            System.out.println("File downloaded successfully - "+ file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
    }

    	*/
}
