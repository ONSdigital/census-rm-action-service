package uk.gov.ons.ctp.response.action.scheduled.plan;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

@RunWith(MockitoJUnitRunner.class)
public class ActionPlanSchedulerTest {

  @InjectMocks private ActionPlanScheduler actionPlanScheduler;

  @Mock private RedissonClient redissonClient;
  @Mock private ActionPlanProcessor actionPlanProcessor;

  @Test
  public void testHappyPath() throws InterruptedException {
    // Given
    RLock mockLock = mock(RLock.class);
    when(redissonClient.getFairLock(any())).thenReturn(mockLock);
    when(mockLock.tryLock(anyLong(), any(TimeUnit.class))).thenReturn(true);

    // When
    actionPlanScheduler.processActionPlans();

    // Then
    verify(actionPlanProcessor).processActionPlans();
  }

  @Test
  public void testAlwaysUnlock() throws InterruptedException {
    // Given
    RLock mockLock = mock(RLock.class);
    when(redissonClient.getFairLock(any())).thenReturn(mockLock);
    when(mockLock.tryLock(anyLong(), any(TimeUnit.class))).thenReturn(true);
    doThrow(RuntimeException.class).when(actionPlanProcessor).processActionPlans();

    // When
    RuntimeException actualException = null;
    try {
      actionPlanScheduler.processActionPlans();
    } catch (RuntimeException exception) {
      actualException = exception;
    }

    // Then
    verify(mockLock).unlock();
    assertNotNull(actualException);
  }

  @Test
  public void testCannotObtainLock() throws InterruptedException {
    // Given
    RLock mockLock = mock(RLock.class);
    when(redissonClient.getFairLock(any())).thenReturn(mockLock);
    when(mockLock.tryLock(anyLong(), any(TimeUnit.class))).thenReturn(false);

    // When
    actionPlanScheduler.processActionPlans();

    // Then
    verify(mockLock, never()).unlock();
    verify(actionPlanProcessor, never()).processActionPlans();
  }
}
