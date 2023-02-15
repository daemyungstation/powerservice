package powerservice.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ClearBom {

       /**
       * @param args
       */
       public static void main(String[] args) {

            System. out.println("[START] - fncBomDelete" );

            File directory = new File("D:\\02.Product\\PowerServiceEnterprise\\Develop\\PSE_DV2.0.0\\powerservice\\src\\java\\inwoo\\powerservice\\" );
             fncBomDelete(directory);

            System. out.println("[EfND] - fncBomDelete" );

      }

       public static void fncBomDelete(File directory) {
             try {
                   for (int i = 0; i < directory.listFiles().length; i++) {
                        File tempFile = directory.listFiles()[i];
                         if (tempFile.isFile()) {
                              FileInputStream fs = new FileInputStream(tempFile);
                               byte[] bom = new byte[3];
                              fs.read(bom, 0, 3);
                               if (byteToHex(bom).equals( "EFBBBF")) {
                                     byte b[] = new byte[( int) tempFile.length()];
                                     int leng = 0;
                                     if ((leng = fs.read(b)) > 0) {
                                          FileOutputStream os = new FileOutputStream(
                                                      tempFile.getAbsolutePath());
                                          os.write(b, 0, leng);
                                          os.close();
                                    }
                              }
                              fs.close();
                        } else if (tempFile.isDirectory()) {
                               fncBomDelete(tempFile);
                        }
                  }
            } catch (Exception e) {
                  e.fillInStackTrace();
            }
      }

       private static synchronized String byteToHex(byte[] data) {
            StringBuffer buf = new StringBuffer();
             for (int i = 0; i < data.length; i++) {
                  buf.append( byteToHex(data[i]).toUpperCase());
            }
             return buf.toString();
      }

       private static synchronized String byteToHex(byte data) {
            StringBuffer buf = new StringBuffer();
            buf.append( toHexChar((data >>> 4) & 0x0F));
            buf.append( toHexChar(data & 0x0F));
             return buf.toString();
      }

       private static synchronized char toHexChar( int i) {
             if ((i >= 0) && (i <= 9)) {
                   return (char ) ('0' + i);
            } else {
                   return (char ) ('a' + (i - 10));
            }
      }

}
