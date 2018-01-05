package br.com.xbrain.elastix;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class DialerCampaignTest {

    private List<DialerAgent> agents;

    private List<Contact> contacts;

    private Date today, todayPlus15;

    public DialerCampaignTest() {
    }

    @Before
    public void setUp() {
        Calendar calendar = Calendar.getInstance();
        today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 15);
        todayPlus15 = calendar.getTime();

        agents = Arrays.asList(
                DialerAgent.create("Agent/8002", 1L),
                DialerAgent.create("Agent/8003", 2L));

        contacts = Arrays.asList(Contact.create("4391036116", "98876541"),
                Contact.create("4396286516", "98876542"),
                Contact.create("8008", "76543"),
                Contact.create("8008", "76544"),
                Contact.create("8006", "76545"),
                Contact.create("8006", "76546"),
                Contact.create("8005", "76547"),
                Contact.create("8005", "76548"));
    }

    @Test
    public void deveCriarUmaCampanha() {
        DialerCampaign dialerCampaign = DialerCampaign.builder()
                .identifiedBy(1)
                .named("Campanha teste")
                .from(today)
                .to(todayPlus15)
                .startingAt("08:00")
                .endingAt("19:00")
                .dialingTo(contacts)
                .answeringWith(agents)
                .retrying(5)
                .build();
        Assert.assertNotNull(dialerCampaign);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = IllegalArgumentException.class)
    public void deveTerPeloMenos1Agent() {
        DialerCampaign.builder()
                .identifiedBy(1)
                .named("Campanha teste")
                .from(today)
                .to(todayPlus15)
                .startingAt("08:00")
                .endingAt("19:00")
                .dialingTo(contacts)
                .answeringWith(Collections.EMPTY_LIST)
                .retrying(5)
                .build();
    }

    @SuppressWarnings("unchecked")
    @Test(expected = IllegalArgumentException.class)
    public void deveTerPeloMenos1Contato() {
        DialerCampaign.builder()
                .identifiedBy(1)
                .named("Campanha teste")
                .from(today)
                .to(todayPlus15)
                .startingAt("08:00")
                .endingAt("19:00")
                .dialingTo(Collections.EMPTY_LIST)
                .answeringWith(agents)
                .retrying(5)
                .build();
    }
}