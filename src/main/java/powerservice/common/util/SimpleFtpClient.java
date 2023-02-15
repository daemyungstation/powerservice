package powerservice.common.util;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * SimpleFtpClient
 * 
 * @author 정출연
 * @version 1.0
 * @date 2016/09/07
 * 
 *       간단한 FTP Client
 */
public class SimpleFtpClient {
	FTPClient ftpClient = null;
	boolean debugMode = false;

	private String serverIp = "";
	private int port = -1;

	private String userId = "";
	private String pwd = "";

	public SimpleFtpClient(String psIp, int piPort, String psUser, String psPwd) {
		serverIp = psIp;
		port = piPort;
		userId = psUser;
		pwd = psPwd;
		ftpClient = new FTPClient();
	}

	public void setDebugMode(boolean pbDebugMode) {
		debugMode = pbDebugMode;
	}

	public void setActiveMode() {
		ftpClient.enterLocalActiveMode();
	}

	public void setPassiveMode() {
		ftpClient.enterLocalPassiveMode();
	}

	public void setFileType(int iFileType) throws Exception {
		try {
			ftpClient.setFileType(iFileType);
		} catch (IOException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public boolean connect() {

		try {
			ftpClient.connect(serverIp, port);
			ftpClient.login(userId, pwd);

		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
			return false;
		}

		return true;
	}

	public void close() {

		try {
			if (ftpClient.isConnected()) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();

		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				System.out.println("Error: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 파일을 서버로 업로드 한다.
	 * 
	 * @param src
	 *            서버 파일 경로
	 * @param dst
	 *            로컬 파일 경로
	 * @return 업로드 성공 여부
	 */
	public boolean upload(String src, String dst) throws Exception {
		boolean done = false;

		if (debugMode) {
			System.out.println("파일 업로드 시작...");
		}

		try {
			File file = new File(src);
			InputStream is = new FileInputStream(file);
			done = ftpClient.storeFile(dst, is);
			is.close();

			if (debugMode) {
				if (done) {
					System.out.println("[" + src + "] 파일 업로드 성공!");
				} else {
					System.out.println("[" + src + "] 파일 업로드 실패!");
				}
			}

		} catch (IOException ex) {
			throw new Exception(ex.getMessage());
		}

		if (debugMode) {
			System.out.println("파일 업로드 완료...");
		}

		return done;
	}
	
	/**
	 * 디렉토리 생성
	 * 
	 * @param src
	 *            서버 파일 경로
	 * @param dst
	 *            로컬 파일 경로
	 * @return 업로드 성공 여부
	 */
	public boolean mkDir(String sDir) throws Exception {
		
//		String[] pathElements = dirPath.split("/");
//		
//        if (pathElements != null && pathElements.length > 0) 
//        {
//            for (String singleDir : pathElements) 
//            {
//                boolean existed = ftpClient.changeWorkingDirectory(singleDir);
//                if (!existed) 
//                {
//                    boolean created = ftpClient.makeDirectory(singleDir);
//                    if (created) {
//                        System.out.println("CREATED directory: " + singleDir);
//                        ftpClient.changeWorkingDirectory(singleDir);
//                    } else {
//                        System.out.println("COULD NOT create directory: " + singleDir);
//                        return false;
//                    }
//                }
//            }
//        }
        
        boolean created = ftpClient.makeDirectory(sDir);
        
        return created;
	}
	
	
	
	/**
	 * 파일삭제
	 * 
	 * @param delTgtFileNm
	 *            삭제할 파일 경로
	 * @return void
	 */
    public void deleteFile(String delTgtFileNm) throws Exception {
 
        try {
 
            boolean deleted = ftpClient.deleteFile(delTgtFileNm);
            if (deleted) {
            	if (debugMode) {
        			System.out.println("파일 삭제 완료.");
        		}
            } else {
            	if (debugMode) {
        			System.out.println("파일 삭제 실패!");
        		}
            }
 
        } catch (IOException ex) {
        	throw new Exception(ex.getMessage());
        }
    }

	// public boolean upload(String sSrcFile, String sDstFile) throws Exception
	// {
	// boolean done = false;
	//
	// try {
	// // APPROACH #2: uploads second file using an OutputStream
	// File secondLocalFile = new File("E:/Test/Report.doc");
	// String secondRemoteFile = "test/Report.doc";
	// InputStream inputStream = new FileInputStream(secondLocalFile);
	//
	// System.out.println("Start uploading second file");
	// OutputStream outputStream = ftpClient
	// .storeFileStream(secondRemoteFile);
	//
	//
	// byte[] bytesIn = new byte[4096];
	// int read = 0;
	//
	// while ((read = inputStream.read(bytesIn)) != -1) {
	// outputStream.write(bytesIn, 0, read);
	// }
	// inputStream.close();
	// outputStream.close();
	//
	// boolean completed = ftpClient.completePendingCommand();
	// if (completed) {
	// System.out.println("The second file is uploaded successfully.");
	// }
	//
	// } catch (IOException ex) {
	// throw new Exception(ex.getMessage());
	// }
	//
	// return done;
	// }

	/**
	 * FTP 서버에서 파일을 다운로드 한다.
	 * 
	 * @param src
	 *            서버 파일 경로
	 * @param dst
	 *            로컬 파일 경로
	 * @return 업로드 성공 여부
	 */
	public boolean download(String src, String dst) throws Exception {
		boolean done = false;

		try {
			File downloadFile1 = new File(dst);
			OutputStream outputStream1 = new BufferedOutputStream(
					new FileOutputStream(downloadFile1));
			boolean success = ftpClient.retrieveFile(src, outputStream1);
			outputStream1.close();

			if (success) {
				System.out.println("File #1 has been downloaded 성공!");
			} else {
				System.out.println("File #1 has been downloaded 실패!");
			}

		} catch (IOException ex) {
			throw new Exception(ex.getMessage());
		}

		return done;
	}

	/**
	 * 서버 경로를 변경한다.
	 * 
	 * @param src
	 *            서버 파일 경로
	 * @param dst
	 *            로컬 파일 경로
	 * @return 업로드 성공 여부
	 */
	public boolean chdir(String sPath) throws Exception {
		boolean done = false;

		try {
			done = ftpClient.changeWorkingDirectory(sPath);

			String sRep = getServerReply();

			if (debugMode) {
				if (done) {
					System.out.println("서버의 경로를 [" + sPath + "]로 변경 성공!");
				} else {
					System.out.println(sRep);
				}
			}

		} catch (IOException ex) {
			throw new Exception(ex.getMessage());
		}

		return done;
	}	
	

	// public boolean download(String src, String dst) throws Exception {
	// boolean done = false;
	//
	// // APPROACH #2: using InputStream retrieveFileStream(String)
	// String remoteFile2 = "/test/song.mp3";
	// File downloadFile2 = new File("D:/Downloads/song.mp3");
	// OutputStream outputStream2 = new BufferedOutputStream(new
	// FileOutputStream(downloadFile2));
	// InputStream inputStream = ftpClient.retrieveFileStream(remoteFile2);
	// byte[] bytesArray = new byte[4096];
	// int bytesRead = -1;
	// while ((bytesRead = inputStream.read(bytesArray)) != -1) {
	// outputStream2.write(bytesArray, 0, bytesRead);
	// }
	//
	// success = ftpClient.completePendingCommand();
	// if (success) {
	// System.out.println("File #2 has been downloaded successfully.");
	// }
	// outputStream2.close();
	// inputStream.close();
	//
	// return done;
	// }

	public List<String> getFtpFileList() {
		List<String> sFileList = new ArrayList<String>();
		
		DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			FTPFile[] files = ftpClient.listFiles();

			for (FTPFile file : files) {
				String details = file.getName();
				if (file.isDirectory()) {
					details = "[" + details + "]";
				}
				details += "\t\t" + file.getSize();
				details += "\t\t"
						+ dateFormater.format(file.getTimestamp().getTime());
				System.out.println(details);
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());

		}

		return sFileList;
	}
	
	public List<String> getFileNameList() {
		List<String> sFileList = new ArrayList<String>();

		try {
			String[] files = ftpClient.listNames();

			for (String file : files) {
				sFileList.add(file);
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());

		}

		return sFileList;
	}
	
	

	public String getServerReply() {
		StringBuffer sb = new StringBuffer();

		String[] replies = ftpClient.getReplyStrings();
		if (replies != null && replies.length > 0) {
			for (String aReply : replies) {
				sb.append(aReply);
			}
		}

		return sb.toString();
	}
	
	public boolean hasFile(String sPath) {
		
		boolean hasFile = false;
		
		try {
			FTPFile[] files = ftpClient.listFiles(sPath);

//			for (FTPFile file : files) {
//				String details = file.getName();
//				System.out.println(details);
//			}
			
			if (files.length > 0) {
				hasFile = true;
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		return hasFile;
		
	}
	
	public static void main(String[] args) {

		SimpleFtpClient.execSample1();
		
		SimpleFtpClient.execSampleUpload();
		SimpleFtpClient.execSampleDownload();
	}
	
	public static void execSample1() {
		String serverIp = "127.0.0.1";
		int port = 21;
		String user = "user";
		String pass = "pass";

		try {
			SimpleFtpClient ftp = new SimpleFtpClient(serverIp, port, user,pass);
			ftp.setDebugMode(true);
			ftp.connect();
			ftp.setPassiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);

			// ftp.upload("F:/jcy/PS020411.xfdl", "PS020411.xfdl"); // pass
			// ftp.upload("F:/jcy/PS020411.xfdl", "test/PS020411.xfdl"); // pass
			// ftp.upload("F:/jcy/PS020411.xfdl",
			// "/level_1/level_2/level_3/PS020411.xfdl"); // pass
			// ftp.upload("F:/jcy/PS020411.xfdl", "333.xfdl"); // pass

			// ftp.download("PS020411.xfdl", "F:/jcy_Download/PS020411.xfdl");
			// //

			// ftp.chdir("/level_1/level_2/level_3"); // pass
			// ftp.chdir("evel_3"); //
			
			// ftp.getFtpFileList();
			// ftp.getFileNameList();
			
			if ( ftp.hasFile("PS020411.xfdl") ) {
				System.out.println("file exists~~~");
			} else {
				System.out.println("No such file or directory!");
			}

			ftp.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void execSampleUpload() {
		String serverIp = "192.168.102.56";
		int port = 50000;
		String user = "user";
		String pass = "pass";

		try {
			SimpleFtpClient ftp = new SimpleFtpClient(serverIp, port, user,pass);
			ftp.setDebugMode(true);
			ftp.connect();
			ftp.setPassiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);

			// 경로 이동
			ftp.chdir("/Test");
						
			List<String> lst = ftp.getFtpFileList();
			for (int i=0; i<lst.size(); ++i) {
				System.out.println((String)lst.get(i));	
			}
						
			String sFile = "commons-net-3.3.jar";
						
			if ( ftp.hasFile(sFile) ) {
				System.out.println("파일이 이미 있음");
			} else {
				System.out.println("파일이 없음");
				
				ftp.upload(sFile, "commons-net-3.3.jar");
			}

			ftp.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void execSampleDownload() {
		String serverIp = "192.168.102.56";
		int port = 50000;
		String user = "user";
		String pass = "pass";

		try {
			SimpleFtpClient ftp = new SimpleFtpClient(serverIp, port, user,pass);
			ftp.setDebugMode(true);
			ftp.connect();
			ftp.setPassiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);

			// 경로 이동
			ftp.chdir("/Test");
						
			String sFile = "commons-net-3.3.jar";
						
			if ( ftp.hasFile(sFile) ) {
				System.out.println("파일이 있음");
				
				ftp.download(sFile, "dw_commons-net-3.3.jar");				
			} 
			else {
				System.out.println("파일이 없음");
			}

			ftp.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
