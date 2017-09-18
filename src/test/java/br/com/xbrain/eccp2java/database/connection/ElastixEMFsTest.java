package br.com.xbrain.eccp2java.database.connection;

import br.com.xbrain.eccp2java.database.CampaignContextEnum;
import br.com.xbrain.eccp2java.database.model.Campaign;
import br.com.xbrain.eccp2java.exception.ElastixIntegrationException;
import br.com.xbrain.eccp2java.util.DateUtils;
import br.com.xbrain.elastix.DialerAgent;
import br.com.xbrain.elastix.Contact;
import br.com.xbrain.elastix.DialerCampaign;
import br.com.xbrain.elastix.ElastixIntegration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
public class ElastixEMFsTest {

   @Test 
   public void deveSerializarElastixIntegration() {
       Assert.assertTrue(true);
   }

}