package uk.gov.ons.ctp.response.action.scheduled.plan;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.ons.ctp.response.action.domain.model.ActionRule;
import uk.gov.ons.ctp.response.action.domain.repository.ActionRuleRepository;
import uk.gov.ons.ctp.response.action.service.ActionService;

@RunWith(MockitoJUnitRunner.class)
public class ActionPlanProcessorTest {
  @InjectMocks ActionPlanProcessor underTest;
  @Mock private ActionRuleRepository actionRuleRepo;
  @Mock private ActionService actionSvc;

  @Test
  public void testHappyPath() {
    // Given
    ActionRule actionRule = new ActionRule();
    actionRule.setActionPlanFK(666);
    List<ActionRule> actionRules = Collections.singletonList(actionRule);
    when(actionRuleRepo.findByTriggerDateTimeBeforeAndHasTriggeredIsFalse(any()))
        .thenReturn(actionRules);

    // When
    underTest.processActionPlans();

    // Then
    verify(actionSvc).createScheduledActions(eq(666));
    verify(actionRuleRepo).saveAndFlush(eq(actionRule));
    assertTrue(actionRule.getHasTriggered());
  }
}
