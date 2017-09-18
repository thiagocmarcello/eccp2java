package br.com.xbrain.eccp2java.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.MockingDetails;
import org.mockito.Mockito;

/**
 *
 * @author xbrain
 */
public class StreamUtilsTest {
    
    public StreamUtilsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testFlushAndClose_GenericType() throws IOException {
        System.out.println("flushAndClose");
        OutputStream outputStream = Mockito.mock(OutputStream.class);
        StreamUtils.close(outputStream);
        Mockito.verify(outputStream).close();
    }

    @Test
    public void testFlushAndClose_OutputStreamArr() throws IOException {
        System.out.println("flushAndClose varargs");
        OutputStream stream = Mockito.mock(OutputStream.class);
        StreamUtils.flushAndClose(stream, stream, stream);
        Mockito.verify(stream, Mockito.times(3)).close();
    }

    @Test
    public void testClose_Closeable() throws IOException {
        System.out.println("close");
        Closeable toClose = Mockito.mock(Closeable.class);
        StreamUtils.close(toClose);
        Mockito.verify(toClose).close();
    }

    @Test
    public void testClose_CloseableArr() throws IOException {
        System.out.println("close varargs");
        Closeable toClose = Mockito.mock(Closeable.class);
        StreamUtils.close(toClose, toClose, toClose);
        Mockito.verify(toClose, Mockito.times(3)).close();
    }

    @Test
    public void testCopy() throws Exception {
        System.out.println("copy");
        String stringToBeCopied = "Um teste de copia de buffer";
        InputStream in = new ByteArrayInputStream(stringToBeCopied.getBytes());
        OutputStream out = new ByteArrayOutputStream();
        StreamUtils.copy(in, out);
        assertEquals(stringToBeCopied, out.toString());
    }
}
