package uk.gov.ons.ctp.response.action.scheduled.plan;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.ons.ctp.response.action.domain.model.ActionRule;
import uk.gov.ons.ctp.response.action.domain.repository.ActionRuleRepository;
import uk.gov.ons.ctp.response.action.service.ActionService;

@Component
public class ActionPlanProcessor {
  private final ActionRuleRepository actionRuleRepo;
  private final ActionService actionSvc;

  public ActionPlanProcessor(ActionRuleRepository actionRuleRepo, ActionService actionSvc) {
    this.actionRuleRepo = actionRuleRepo;
    this.actionSvc = actionSvc;
  }

  @Transactional
  public void processActionPlans() {
    List<ActionRule> triggeredActionRules =
        actionRuleRepo.findByTriggerDateTimeBeforeAndHasTriggeredIsFalse(OffsetDateTime.now());
    for (ActionRule triggeredActionRule : triggeredActionRules) {
      actionSvc.createScheduledActions(triggeredActionRule);
      triggeredActionRule.setHasTriggered(true);
      actionRuleRepo.saveAndFlush(triggeredActionRule);
    }
  }
}
