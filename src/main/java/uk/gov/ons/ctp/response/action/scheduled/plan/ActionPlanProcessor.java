package uk.gov.ons.ctp.response.action.scheduled.plan;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.ons.ctp.response.action.domain.model.ActionRule;
import uk.gov.ons.ctp.response.action.domain.repository.ActionRuleRepository;
import uk.gov.ons.ctp.response.action.service.ActionService;

@Component
public class ActionPlanProcessor {
  private ActionRuleRepository actionRuleRepo;
  private ActionService actionSvc;

  public ActionPlanProcessor(ActionRuleRepository actionRuleRepo, ActionService actionSvc) {
    this.actionRuleRepo = actionRuleRepo;
    this.actionSvc = actionSvc;
  }

  @Transactional
  public void processActionPlans() {
    List<ActionRule> triggeredActionRules =
        actionRuleRepo.findByTriggerDateTimeBeforeAndHasTriggeredIsFalse(OffsetDateTime.now());
    Set<Integer> actionPlans = new HashSet<>();

    for (ActionRule triggeredActionRule : triggeredActionRules) {
      Integer actionPlanFk = triggeredActionRule.getActionPlanFK();
      if (actionPlanFk != null && !actionPlans.contains(actionPlanFk)) {
        actionSvc.createScheduledActions(actionPlanFk);
        actionPlans.add(actionPlanFk);
      }

      triggeredActionRule.setHasTriggered(true);
      actionRuleRepo.saveAndFlush(triggeredActionRule);
    }
  }
}
