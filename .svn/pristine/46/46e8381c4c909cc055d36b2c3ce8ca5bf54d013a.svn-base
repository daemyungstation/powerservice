package csv;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import com.opencsv.CSVReader;

public class CsvTest2 {
	
	public static void main(String[] args) {		
		
		String infile = "D:/project/workspace/TestApp/input/member.txt";
		String outfile = "D:/project/workspace/TestApp/output/member.html";

		try {
			
			FileWriter out = new FileWriter(outfile, false);
			
			out.write("<html>");
			out.write("<head>");
			out.write("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />");
			out.write("<title>회원목록</title>");
			out.write("</head>");			
			out.write("<body leftmargin=\"0\" topmargin=\"0\">");			
			
			out.write("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#eeeeee\" border=\"1\" style=\"border-collapse: collapse;\">");
			
			InputStream is = new FileInputStream(new File(infile));
			InputStreamReader isr = new InputStreamReader(is, "EUC-KR");
			BufferedReader br = new BufferedReader(isr);    
			HashMap<String,String> mRow = new HashMap<>();
            String sLine;
            
            try {
                CSVReader reader = new CSVReader(br, '\t');                
                
                String[] sArr;
                String colId = "";
                while ((sArr = reader.readNext()) != null) {
                	
        			out.write("	<tr>");
        			
        			System.out.println("sArr.length : " + sArr.length);
                	
                	for (int i=0; i<sArr.length; ++i) {
                		sArr[i] = sArr[i].trim();
                		// System.out.println(sArr[i]);
                		out.write("<td>" + sArr[i] + "</td>");
                	}
                	
                	// System.out.println("================================================");
                	
        			out.write("	</tr>");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }            

			out.write("</table>");			
			out.write("</body>");
			out.write("</html>");
            
            br.close();
            isr.close();
            is.close();
            
            out.close();
	        
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
