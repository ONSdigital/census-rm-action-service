package uk.gov.ons.ctp.response.action.scheduled.plan;

import java.util.concurrent.TimeUnit;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ActionPlanScheduler {
  private static final String GLOBAL_ACTION_PLAN_LOCK = "GLOBALACTIONPLANLOCK";

  private final RedissonClient redissonClient;
  private final ActionPlanProcessor actionPlanProcessor;

  public ActionPlanScheduler(
      RedissonClient redissonClient, ActionPlanProcessor actionPlanProcessor) {
    this.redissonClient = redissonClient;
    this.actionPlanProcessor = actionPlanProcessor;
  }

  @Scheduled(fixedDelayString = "#{appConfig.planExecution.delayMilliSeconds}")
  public void processActionPlans() {
    RLock lock = redissonClient.getFairLock(GLOBAL_ACTION_PLAN_LOCK);

    try {
      // Get a lock. Automatically unlock after a certain amount of time to prevent issues
      // when lock holder crashes or Redis crashes causing permanent lockout
      if (lock.tryLock(3600, TimeUnit.SECONDS)) {
        try {
          actionPlanProcessor.processActionPlans();
        } finally {
          // Always unlock the distributed lock
          lock.unlock();
        }
      }
    } catch (InterruptedException e) {
      // Ignored - process stopped while waiting for lock
    }
  }
}
