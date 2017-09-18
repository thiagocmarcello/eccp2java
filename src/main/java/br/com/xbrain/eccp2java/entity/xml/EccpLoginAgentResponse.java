package br.com.xbrain.eccp2java.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <response id="2">
 *   <loginagent_response>
 *       <status>logging</status>
 *   </loginagent_response>
 * </response>
 * 
 * @author joaomassan@xbrain.com.br
 */
@XmlRootElement(name = "loginagent_response")
@XmlAccessorType(XmlAccessType.FIELD) 
@EqualsAndHashCode(callSuper = true)
@ToString
public class EccpLoginAgentResponse extends EccpAbstractResponse {}
