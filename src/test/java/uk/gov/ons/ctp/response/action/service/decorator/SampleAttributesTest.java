package uk.gov.ons.ctp.response.action.service.decorator;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.ons.ctp.common.FixtureHelper;
import uk.gov.ons.ctp.response.action.domain.model.Action;
import uk.gov.ons.ctp.response.action.domain.model.ActionType;
import uk.gov.ons.ctp.response.action.message.instruction.ActionRequest;
import uk.gov.ons.ctp.response.action.service.decorator.context.ActionRequestContext;
import uk.gov.ons.ctp.response.sample.representation.SampleAttributesDTO;
import uk.gov.ons.ctp.response.sample.representation.SampleUnitDTO;

@RunWith(MockitoJUnitRunner.class)
public class SampleAttributesTest {

  @InjectMocks private SampleAttributes sampleAttributesDecorator;

  private ActionRequestContext context;
  private SampleAttributesDTO censusSampleAttributes;

  private ActionRequestContext createContext(SampleAttributesDTO sampleAttributes) {
    context = new ActionRequestContext();
    context.setSampleUnitType(SampleUnitDTO.SampleUnitType.H);
    context.setAction(createContextAction("Printer"));
    context.setSampleAttributes(sampleAttributes);
    return context;
  }

  private Action createContextAction(String handler) {
    Action contextAction = new Action();
    contextAction.setActionType(ActionType.builder().actionTypePK(1).handler(handler).build());
    return contextAction;
  }

  @Before
  public void setUp() throws Exception {
    List<SampleAttributesDTO> sampleAttributesDTO =
        FixtureHelper.loadClassFixtures(SampleAttributesDTO[].class);
    censusSampleAttributes = sampleAttributesDTO.get(0);
  }

  @Test
  public void testDecorateCensusActionRequest() {
    // Given
    ActionRequest actionRequest = new ActionRequest();
    context = createContext(censusSampleAttributes);

    // When
    sampleAttributesDecorator.decorateActionRequest(actionRequest, context);

    // Then
    assertEquals(
        context.getSampleAttributes().getAttributes().get("ADDRESS_LINE1"),
        actionRequest.getAddress().getLine1());
    assertEquals(
        context.getSampleAttributes().getAttributes().get("ADDRESS_LINE2"),
        actionRequest.getAddress().getLine2());
    assertEquals(
        context.getSampleAttributes().getAttributes().get("ADDRESS_LINE3"),
        actionRequest.getAddress().getLine3());
    assertEquals(
        context.getSampleAttributes().getAttributes().get("POSTCODE"),
        actionRequest.getAddress().getPostcode());
    assertEquals(
        context.getSampleAttributes().getAttributes().get("TOWN_NAME"),
        actionRequest.getAddress().getTownName());
  }
}
