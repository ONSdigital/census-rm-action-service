package uk.gov.ons.ctp.response.action;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import uk.gov.ons.ctp.response.action.domain.model.Action;
import uk.gov.ons.ctp.response.action.domain.model.ActionPlan;
import uk.gov.ons.ctp.response.action.message.feedback.ActionFeedback;
import uk.gov.ons.ctp.response.action.representation.ActionDTO;
import uk.gov.ons.ctp.response.action.representation.ActionFeedbackDTO;
import uk.gov.ons.ctp.response.action.representation.ActionPlanDTO;
import uk.gov.ons.ctp.response.action.representation.ActionPostRequestDTO;

/** The bean mapper to go from Entity objects to Presentation objects. */
@Component
@Primary
public class ActionBeanMapper extends ConfigurableMapper {

  /**
   * This method configures the bean mapper.
   *
   * @param factory the mapper factory
   */
  @Override
  protected final void configure(final MapperFactory factory) {
    factory
        .classMap(Action.class, ActionDTO.class)
        .field("actionType.name", "actionTypeName")
        .byDefault()
        .register();

    factory
        .classMap(ActionPlan.class, ActionPlanDTO.class)
        .field("lastRunDateTime", "lastRunDateTime")
        .byDefault()
        .register();

    factory.classMap(ActionFeedback.class, ActionFeedbackDTO.class).byDefault().register();

    factory
        .classMap(ActionPostRequestDTO.class, Action.class)
        .field("actionTypeName", "actionType.name")
        .byDefault()
        .register();
  }
}
