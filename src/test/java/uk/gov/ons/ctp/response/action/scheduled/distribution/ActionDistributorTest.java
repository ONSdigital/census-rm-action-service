package uk.gov.ons.ctp.response.action.scheduled.distribution;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import uk.gov.ons.ctp.common.FixtureHelper;
import uk.gov.ons.ctp.common.error.CTPException;
import uk.gov.ons.ctp.response.action.config.ActionDistribution;
import uk.gov.ons.ctp.response.action.config.AppConfig;
import uk.gov.ons.ctp.response.action.config.DataGrid;
import uk.gov.ons.ctp.response.action.domain.model.Action;
import uk.gov.ons.ctp.response.action.domain.model.ActionCase;
import uk.gov.ons.ctp.response.action.domain.model.ActionType;
import uk.gov.ons.ctp.response.action.domain.repository.ActionCaseRepository;
import uk.gov.ons.ctp.response.action.domain.repository.ActionRepository;
import uk.gov.ons.ctp.response.action.domain.repository.ActionTypeRepository;
import uk.gov.ons.ctp.response.action.service.ActionProcessingService;

@RunWith(MockitoJUnitRunner.class)
public class ActionDistributorTest {

  private static final String ICL1E = "ICL1E";

  private List<ActionType> actionTypes;
  private ActionCase hActionCase;
  private RLock lock;

  @Mock private AppConfig appConfig;

  @Mock private RedissonClient redissonClient;

  @Mock private ActionRepository actionRepo;

  @Mock private ActionTypeRepository actionTypeRepo;

  @Mock(name = "censusActionProcessingService")
  private ActionProcessingService censusActionProcessingService;

  @Mock private ActionCaseRepository actionCaseRepo;

  @InjectMocks private ActionDistributor actionDistributor;

  /** Initialises Mockito and loads Class Fixtures */
  @Before
  public void setUp() throws Exception {
    actionTypes = FixtureHelper.loadClassFixtures(ActionType[].class);
    Stream<Action> censusActions = FixtureHelper.loadClassFixtures(Action[].class).stream();
    hActionCase = new ActionCase();
    hActionCase.setSampleUnitType("H");

    MockitoAnnotations.initMocks(this);
    DataGrid dataGrid = new DataGrid();
    dataGrid.setLockTimeToLiveSeconds(30);
    dataGrid.setLockTimeToWaitSeconds(600);
    when(appConfig.getDataGrid()).thenReturn(dataGrid);
    ActionDistribution actionDistribution = new ActionDistribution();
    when(appConfig.getActionDistribution()).thenReturn(actionDistribution);

    lock = mock(RLock.class);
    when(redissonClient.getFairLock(any())).thenReturn(lock);
    when(lock.tryLock(anyInt(), eq(TimeUnit.SECONDS))).thenReturn(true);
    when(actionCaseRepo.findById(any())).thenReturn(hActionCase);

    // Test census action types (ICL1E)
    when(actionTypeRepo.findAll()).thenReturn(actionTypes);

    for (ActionType actionType : actionTypes) {
      if (actionType.getName().equals(ICL1E)) {
        when(actionRepo.findByActionTypeAndStateIn(eq(actionType), any()))
            .thenReturn(censusActions);
      }
    }
  }

  /** Happy Path with 2 ActionRequests and 2 ActionCancels for a H case */
  @Test
  public void testHappyPathHCase() throws Exception {
    // Given
    when(actionTypeRepo.findAll()).thenReturn(actionTypes.subList(0, 1));
    when(actionCaseRepo.findById(any())).thenReturn(hActionCase);

    // When
    actionDistributor.distribute();

    // Then
    verify(censusActionProcessingService, times(1)).processActionRequests(any());
    verify(censusActionProcessingService, times(1)).processActionCancel(any());

    verify(lock, times(1)).unlock();
  }

  @Test
  public void testProcessActionRequestsThrowsCTPException() throws Exception {
    // Given
    when(actionTypeRepo.findAll()).thenReturn(Collections.singletonList(actionTypes.get(0)));
    doThrow(CTPException.class).when(censusActionProcessingService).processActionRequests(any());

    // When
    actionDistributor.distribute();

    // Then
    verify(lock, times(1)).unlock();
  }

  @Test
  public void testNoCaseWithSampleUnitTypeH() throws Exception {
    // Given setUp
    when(actionTypeRepo.findAll()).thenReturn(Collections.singletonList(actionTypes.get(0)));
    when(actionCaseRepo.findById(any())).thenReturn(null);

    // When
    actionDistributor.distribute();

    // Then
    verify(censusActionProcessingService, never()).processActionRequests(any());
    verify(censusActionProcessingService, never()).processActionCancel(any());

    verify(lock, times(1)).unlock();
  }
}
