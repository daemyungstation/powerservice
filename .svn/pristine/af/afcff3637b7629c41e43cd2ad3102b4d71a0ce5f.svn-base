package powerservice.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileZipCompress {
	List<HashMap<String,String>> fileList;
	
	private String zipFilePath = "";
	
	public FileZipCompress() {
		fileList = new ArrayList<>();
	}
	
	public int getFileCount() {
		return fileList.size();
	}
	
	public String getZipFilePath() {
		return zipFilePath;
	}
	
	public void setZipFilePath(String zipFilePath) {
		this.zipFilePath = zipFilePath;
	}
	
	private String getRandZipFileNm(String saveDir, String prefix) {
		String sRand = DateUtil.getCurrDthms() + UUID.randomUUID().toString().replace("-", "");
		
		if (!"".equals(prefix)) {
			sRand = prefix.substring(0,10) + "_" + sRand.substring(0, 20);
		} else {
			sRand = sRand.substring(0, 30);
		}
		
		if (saveDir.endsWith("/")) {
			return saveDir + sRand + ".zip";
		} else {
			return saveDir + "/" + sRand + ".zip";
		}
	}
	
	// zipIt 을 대체 - jcy
	public boolean zip(String saveDir, String fileNmPrefix) throws Exception {
		
		byte[] buffer = new byte[4096];
		
		try {		
			
			if ("".equals(zipFilePath)) {
				zipFilePath = getRandZipFileNm(saveDir, fileNmPrefix);
			}
			
			System.out.println("zipFilePath : " + zipFilePath);
			
			File file = new File(zipFilePath);
			String dirNm = file.getParent();			
			String fileNm = file.getName();
			
			System.out.println("dirNm : " + dirNm);
			System.out.println("fileNm : " + fileNm);
			
			file = new File(dirNm);
			if (!file.isDirectory()) {
				file.mkdirs();
			}
			
			FileOutputStream fos = new FileOutputStream(zipFilePath);
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (HashMap<String,String> fileMap : fileList) {
				fileNm = (String) fileMap.get("file_nm");
				String path = (String) fileMap.get("full_path");
				ZipEntry ze = new ZipEntry(fileNm);
				zos.putNextEntry(ze);

				FileInputStream in = new FileInputStream(path);
				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
			}

			zos.closeEntry();
			zos.close();
			fos.close();
		} catch (IOException ex) {
			throw ex;
		}

		return true;
	}	

	// generateFileList
	public boolean append(String fullPath) {
		File tmpFile = new File(fullPath);
		if (tmpFile.isFile()) {
			HashMap<String,String> mParam = new HashMap<>();
			mParam.put("full_path"	, tmpFile.getAbsoluteFile().toString());
			mParam.put("file_nm"	, tmpFile.getName());
			fileList.add(mParam);
			
			return true;
		}
		return false;
	}
}
