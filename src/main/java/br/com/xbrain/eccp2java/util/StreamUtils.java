package br.com.xbrain.eccp2java.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.ArrayUtils;

/**
 * StreamUtils
 *
 * @author joaomassan@xbrain.com.br
 * @date Aug 8, 2014
 * @version 1.0
 */
public class StreamUtils {
    
    private static final Logger LOG = Logger.getLogger(StreamUtils.class.getName());

    public static <T extends OutputStream> boolean flushAndClose(T outputStream) {
        try {
            outputStream.flush();
            close((Closeable) outputStream);
            return true;
        } catch(IOException ex) {
            LOG.warning(ex.getMessage());
            return false;
        }
    }
    
    public static <T extends OutputStream> boolean flushAndClose(OutputStream... streams) {
        boolean noErrors = true;
        if(!ArrayUtils.isEmpty(streams)) {
            for(OutputStream stream : streams) {
                noErrors &= flushAndClose(stream);
            }
        }
        return noErrors;
    }
    
    public static void close(Closeable toClose) {
        if(toClose != null) {
            try {
                toClose.close();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void close(Closeable... toCloseArr) {
        if(!ArrayUtils.isEmpty(toCloseArr)) {
            for(Closeable toClose : toCloseArr) {
                close(toClose);
            }
        }
    }
    
    public static void copy(InputStream in, OutputStream out) throws IOException {
        int b;
        while((b = in.read()) >= 0) {
            out.write(b);
        }
    }
    
    public static void copyAndClose(InputStream in, OutputStream out) throws IOException {
        copy(in, out);
        out.flush();
        close(in, out);
    }
    
    public static void copyAndClose(InputStream in, OutputStream out, int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        while(in.read(buffer) >= 0) {
            out.write(buffer);
        }
    }
}
