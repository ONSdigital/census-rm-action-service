package uk.gov.ons.ctp.response.action.representation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Domain model object for representation.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ActionRuleDTO {

  private UUID actionPlanId;
  private Integer priority;
  private Integer daysOffset;

  private String actionTypeName;
  private String name;
  private String description;
}
