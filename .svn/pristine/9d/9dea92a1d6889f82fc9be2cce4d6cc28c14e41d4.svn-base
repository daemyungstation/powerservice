/*    */ package powerservice.common.util;
/*    */ 
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.KeyGenerator;
/*    */ import javax.crypto.spec.SecretKeySpec;
/*    */ 
/*    */ public class AesCrypto
/*    */ {
/*    */   private static final String TRANSFORM = "AES/ECB/PKCS5Padding";
/*    */ 
/*    */   public static String encrypt(String plainText, String key)
/*    */     throws Exception
/*    */   {
/* 13 */     KeyGenerator kgen = KeyGenerator.getInstance("AES");
/* 14 */     kgen.init(128);
/*    */ 
/* 16 */     byte[] raw = key.getBytes();
/* 17 */     SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
/* 18 */     Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
/*    */ 
/* 20 */     cipher.init(1, skeySpec);
/* 21 */     byte[] encrypted = cipher.doFinal(plainText.getBytes());
/* 22 */     return asHex(encrypted);
/*    */   }
/*    */ 
/*    */   public static String decrypt(String cipherText, String key) throws Exception {
/* 26 */     KeyGenerator kgen = KeyGenerator.getInstance("AES");
/* 27 */     kgen.init(128);
/*    */ 
/* 29 */     byte[] raw = key.getBytes();
/* 30 */     SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
/* 31 */     Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
/*    */ 
/* 33 */     cipher.init(2, skeySpec);
/* 34 */     byte[] original = cipher.doFinal(fromString(cipherText));
/* 35 */     String originalString = new String(original);
/* 36 */     return originalString;
/*    */   }
/*    */ 
/*    */   private static String asHex(byte[] buf) {
/* 40 */     StringBuffer strbuf = new StringBuffer(buf.length * 2);
/*    */ 
/* 43 */     for (int i = 0; i < buf.length; i++) {
/* 44 */       if ((buf[i] & 0xFF) < 16) {
/* 45 */         strbuf.append("0");
/*    */       }
/* 47 */       strbuf.append(Long.toString(buf[i] & 0xFF, 16));
/*    */     }
/*    */ 
/* 50 */     return strbuf.toString();
/*    */   }
/*    */ 
/*    */   private static byte[] fromString(String hex) {
/* 54 */     int len = hex.length();
/* 55 */     byte[] buf = new byte[(len + 1) / 2];
/*    */ 
/* 57 */     int i = 0; int j = 0;
/* 58 */     if (len % 2 == 1) {
/* 59 */       buf[(j++)] = ((byte)fromDigit(hex.charAt(i++)));
/*    */     }
/* 61 */     while (i < len) {
/* 62 */       buf[(j++)] = ((byte)(fromDigit(hex.charAt(i++)) << 4 | fromDigit(
/* 63 */         hex.charAt(i++))));
/*    */     }
/* 65 */     return buf;
/*    */   }
/*    */ 
/*    */   private static int fromDigit(char ch) {
/* 69 */     if ((ch >= '0') && (ch <= '9'))
/* 70 */       return ch - '0';
/* 71 */     if ((ch >= 'A') && (ch <= 'F'))
/* 72 */       return ch - 'A' + 10;
/* 73 */     if ((ch >= 'a') && (ch <= 'f')) {
/* 74 */       return ch - 'a' + 10;
/*    */     }
/* 76 */     throw new IllegalArgumentException("invalid hex digit '" + ch + "'");
/*    */   }
/*    */ }

/* Location:           D:\project\encom_flex\dmlife\WEB-INF\classes\
 * Qualified Name:     com.common.AesCrypto
 * JD-Core Version:    0.6.2
 */