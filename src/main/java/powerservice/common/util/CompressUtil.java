package powerservice.common.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Stack;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

public class CompressUtil {
    private static boolean debug = true;

    public void unzip(File zippedFile) throws IOException {
        unzip(zippedFile, Charset.defaultCharset().name());
    }

    public void unzip(File zippedFile, String charsetName ) throws IOException {
        unzip(zippedFile, zippedFile.getParentFile(), charsetName);
    }

    public void unzip(File zippedFile, File destDir) throws IOException {
        unzip(new FileInputStream(zippedFile), destDir, Charset.defaultCharset().name());
    }

    public void unzip(File zippedFile, File destDir, String charsetName) throws IOException {
        unzip(new FileInputStream(zippedFile), destDir, charsetName);
    }

    public void unzip(InputStream is, File destDir) throws IOException {
        unzip(is, destDir, Charset.defaultCharset().name());
    }

    public void unzip( InputStream is, File destDir, String charsetName) throws IOException {
        ZipArchiveInputStream zis ;
        ZipArchiveEntry entry ;
        String name ;
        File target ;
        int nWritten = 0;
        BufferedOutputStream bos ;
        byte [] buf = new byte[1024 * 8];

        zis = new ZipArchiveInputStream(is, charsetName, false);
        try {
            while ( (entry = zis.getNextZipEntry()) != null ) {
                name = entry.getName();
                target = new File (destDir, name);
                if ( entry.isDirectory() ) {
                    target.mkdirs(); /*  does it always work? */
                    debug ("dir  : " + name);
                } else {
                    target.createNewFile();
                    bos = new BufferedOutputStream(new FileOutputStream(target));
                    try {
                        while ((nWritten = zis.read(buf)) >= 0 ) {
                            bos.write(buf, 0, nWritten);
                        }
                    } catch (Exception exception) {
                        throw exception;
                    } finally {
                        bos.close();
                    }
                    debug ("File : " + name);
                }
            }
        } catch (Exception exception) {
            throw exception;
        } finally {
            zis.close();
        }
    }

    /**
     * compresses the given File(or dir) and creates new File under the same directory.
     * @param src File or directory
     * @throws IOException
     */
    public void zip(File src) throws IOException {
        zip(src, Charset.defaultCharset().name(), true);
    }

    /**
     * zips the given File(or dir) and create
     * @param src File or directory to compress
     * @param includeSrc if true and src is directory, then src is not included in the compression. if false, src is included.
     * @throws IOException
     */
    public void zip(File src, boolean includeSrc) throws IOException {
        zip(src, Charset.defaultCharset().name(), includeSrc);
    }

    /**
     * compresses the given src File (or directory) with the given encoding
     * @param src
     * @param charSetName
     * @param includeSrc
     * @throws IOException
     */
    public void zip(File src, String charSetName, boolean includeSrc) throws IOException {
        zip( src, src.getParentFile(), charSetName, includeSrc);
    }

    /**
     * compresses the given src File(or directory) and writes to the given output stream.
     * @param src
     * @param os
     * @throws IOException
     */
    public void zip(File src, OutputStream os) throws IOException {
        zip(src, os, Charset.defaultCharset().name(), true);
    }

    /**
     * compresses the given src File(or directory) and create the compressed File under the given destDir.
     * @param src
     * @param destDir
     * @param charSetName
     * @param includeSrc
     * @throws IOException
     */
    public void zip(File src, File destDir, String charSetName, boolean includeSrc) throws IOException {
        String FileName = src.getName();
        if ( !src.isDirectory() ) {
            int pos = FileName.lastIndexOf(".");
            if ( pos >  0) {
                FileName = FileName.substring(0, pos);
            }
        }
        FileName += ".zip";

        File zippedFile = new File ( destDir, FileName);
        if ( !zippedFile.exists() ) zippedFile.createNewFile();
        zip(src, new FileOutputStream(zippedFile), charSetName, includeSrc);
    }

    public void zip(File src, OutputStream os, String charsetName, boolean includeSrc) throws IOException {
        ZipArchiveOutputStream zos = new ZipArchiveOutputStream(os);
        try {
            zos.setEncoding(charsetName);
            FileInputStream fis ;

            int length ;
            ZipArchiveEntry ze ;
            byte [] buf = new byte[8 * 1024];
            String name ;

            Stack<File> stack = new Stack<File>();
            File root ;
            if ( src.isDirectory() ) {
                if( includeSrc ) {
                    stack.push(src);
                    root = src.getParentFile();
                }
                else {
                    File [] fs = src.listFiles();
                    for (int i = 0; i < fs.length; i++) {
                        stack.push(fs[i]);
                    }
                    root = src;
                }
            } else {
                stack.push(src);
                root = src.getParentFile();
            }

            while ( !stack.isEmpty() ) {
                File f = stack.pop();
                name = toPath(root, f);
                if ( f.isDirectory()) {
                    debug ("dir  : " + name);
                    File [] fs = f.listFiles();
                    for (int i = 0; i < fs.length; i++) {
                        if ( fs[i].isDirectory() ) stack.push(fs[i]);
                        else stack.add(0, fs[i]);
                    }
                } else {
                    debug("File : " + name);
                    ze = new ZipArchiveEntry(name);
                    zos.putArchiveEntry(ze);
                    fis = new FileInputStream(f);
                    try {
                        while ( (length = fis.read(buf, 0, buf.length)) >= 0 ) {
                            zos.write(buf, 0, length);
                        }
                    } catch (Exception exception) {
                        throw exception;
                    } finally {
                        fis.close();
                    }
                    zos.closeArchiveEntry();
                }
            }
        } catch (Exception exception) {
            throw exception;
        } finally {
            zos.close();
        }
    }

    private String toPath(File root, File dir) {
        String path = dir.getAbsolutePath();
        System.out.println("[" + path + "]-[" + root.getAbsolutePath() + "]");
        path = path.substring(root.getAbsolutePath().length()).replace(File.separatorChar, '/');
        if ( path.startsWith("/")) path = path.substring(1);
        if ( dir.isDirectory() && !path.endsWith("/")) path += "/" ;
        return path ;
    }

    private static void debug(String msg) {
        if( debug ) System.out.println(msg);
    }
}