package br.com.xbrain.eccp2java.entity.xml;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <request id="6">
 *   <getcampaigninfo>
 *       <campaign_type>outgoing</campaign_type>
 *       <campaign_id>4</campaign_id>
 *   </getcampaigninfo>
 * </request>
 * 
 * @author joaomassan@xbrain.com.br
 */
@XmlRootElement(name = "getcampaigninfo")
@XmlAccessorType(XmlAccessType.FIELD) 
@EqualsAndHashCode(callSuper = true)
@ToString
public class EccpGetCampaignInfoRequest extends EccpAbstractRequest {
    
    public static final String TYPE_INCOMING = "incoming";
    
    public static final String TYPE_OUTGOING = "outgoing";

    public static EccpGetCampaignInfoRequest create(String campaignType, Integer campaignId) {
        return new EccpGetCampaignInfoRequest(campaignType, campaignId);
    }

    @Getter
    @Setter
    @XmlElement(name = "campaign_type")
    private String campaignType;
    
    @Getter
    @Setter
    @XmlElement(name = "campaign_id")
    private Integer campaignId;

    private EccpGetCampaignInfoRequest(String campaignType, Integer campaignId) {
        this.campaignType = campaignType;
        this.campaignId = campaignId;
    }

    public EccpGetCampaignInfoRequest() {
    }

    @Override
    public Class<? extends IEccpResponse> expectedResponseType() {
        return EccpGetCampaignInfoResponse.class;
    }
}