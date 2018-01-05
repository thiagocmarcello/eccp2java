package br.com.xbrain.eccp2java.util;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class StreamUtilsTest {
    
    public StreamUtilsTest() {
    }
    
    @Test
    public void testFlushAndClose_GenericType() throws IOException {
        OutputStream outputStream = Mockito.mock(OutputStream.class);
        StreamUtils.close(outputStream);
        Mockito.verify(outputStream).close();
    }

    @Test
    public void testFlushAndClose_OutputStreamArr() throws IOException {
        OutputStream stream = Mockito.mock(OutputStream.class);
        StreamUtils.flushAndClose(stream, stream, stream);
        Mockito.verify(stream, Mockito.times(3)).close();
    }

    @Test
    public void testClose_Closeable() throws IOException {
        Closeable toClose = Mockito.mock(Closeable.class);
        StreamUtils.close(toClose);
        Mockito.verify(toClose).close();
    }

    @Test
    public void testClose_CloseableArr() throws IOException {
        Closeable toClose = Mockito.mock(Closeable.class);
        StreamUtils.close(toClose, toClose, toClose);
        Mockito.verify(toClose, Mockito.times(3)).close();
    }

    @Test
    public void testCopy() throws Exception {
        String stringToBeCopied = "Um teste de copia de buffer";
        InputStream in = new ByteArrayInputStream(stringToBeCopied.getBytes());
        OutputStream out = new ByteArrayOutputStream();
        StreamUtils.copy(in, out);
        assertEquals(stringToBeCopied, out.toString());
    }
}
