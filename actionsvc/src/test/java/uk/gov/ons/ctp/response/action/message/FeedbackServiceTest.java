package uk.gov.ons.ctp.response.action.message;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.integration.test.matcher.HeaderMatcher.hasHeaderKey;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

// TODO using test integration-context change to use live version
@ContextConfiguration 
@RunWith(SpringJUnit4ClassRunner.class)
public class FeedbackServiceTest {

  @Before
  public void setUp() throws Exception {
    // TODO mock ActiveMQ or embedded ActiveMQ required
  }

  @After
  public void tearDown() throws Exception {
  }

  @Autowired
  MessageChannel feedback_xml;

  @Autowired
  QueueChannel feedback_transformed;

  @Test
  public void testOne() {
    try {
      
      String testMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "<p:actionFeedback xmlns:p=\"http://ons.gov.uk/ctp/response/action/message/feedback\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://ons.gov.uk/ctp/response/action/message/feedback inboundMessage.xsd \">" +
        "<actionId>1</actionId>" +
        "<situation>situation</situation>" + 
        "<isComplete>true</isComplete>" +
        "<isFailed>true</isFailed>" + 
        "<notes>notes</notes>" + 
        "</p:actionFeedback>";
      
      feedback_xml.send(MessageBuilder.withPayload(testMessage).build());
      Message<?> outMessage = feedback_transformed.receive(0);
      assertNotNull("outMessage should not be null", outMessage);
      boolean payLoadContainsAdaptor = outMessage.getPayload().toString().contains("uk.gov.ons.ctp.response.action.message.feedback.ActionFeedback");
      assertTrue("Payload does not contain reference to ActionFeedback adaptor", payLoadContainsAdaptor);
      assertThat(outMessage, hasHeaderKey("timestamp"));
      assertThat(outMessage, hasHeaderKey("id"));
      outMessage = feedback_transformed.receive(0);
      assertNull("Only one message expected", outMessage);  
      

    }
    catch (Exception ex) {
      fail("testOne has failed " + ex.getMessage());
    }
    
  }

}