package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.entity.xml.IEccpEvent;
import br.com.xbrain.eccp2java.exception.EccpException;
import org.junit.*;
import org.mockito.Mockito;

/**
 *
 * @author xbrain
 */
public class EccpTest {
    
    private static final String AGENT_NUMBER = "Agent/1000";
    private static final String PASSWORD = "1000";
    private static final Integer EXTENSION = 1000;

    public EccpTest() {
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
    public void shouldStartListenerThread() {
        
    }

    @Test
    public void shouldAddEventListener() {
        EccpClient eccp = Mockito.mock(EccpClient.class);
        IEccpEventListener event = Mockito.mock(IEccpEventListener.class);
        eccp.addEventListener(null, event);
        Mockito.verify(eccp).addEventListener( null, event);
    }
    
    @Test
    public void shouldCreateAgentConsole() throws EccpException {
        EccpClient eccp = Mockito.mock(EccpClient.class);
        Mockito.when(eccp.createAgentConsole(AGENT_NUMBER, PASSWORD, EXTENSION))
                .thenReturn(new AgentConsole(eccp, AGENT_NUMBER, PASSWORD, EXTENSION, "APP_COOKIE"));
        AgentConsole console = eccp.createAgentConsole(AGENT_NUMBER, PASSWORD, EXTENSION);
        Mockito.verify(eccp).createAgentConsole(AGENT_NUMBER, PASSWORD, EXTENSION);
        Assert.assertNotNull(console);
    }
}
