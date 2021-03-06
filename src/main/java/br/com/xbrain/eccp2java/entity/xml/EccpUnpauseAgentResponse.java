package br.com.xbrain.eccp2java.entity.xml;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "unpauseagent_response")
public class EccpUnpauseAgentResponse extends EccpAbstractResponse {}
