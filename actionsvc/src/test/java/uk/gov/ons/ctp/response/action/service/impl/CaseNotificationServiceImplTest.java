package uk.gov.ons.ctp.response.action.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.gov.ons.ctp.common.FixtureHelper;
import uk.gov.ons.ctp.response.action.domain.model.ActionCase;
import uk.gov.ons.ctp.response.action.domain.model.ActionPlan;
import uk.gov.ons.ctp.response.action.domain.repository.ActionCaseRepository;
import uk.gov.ons.ctp.response.action.domain.repository.ActionPlanRepository;
import uk.gov.ons.ctp.response.action.service.ActionService;
import uk.gov.ons.ctp.response.action.service.CaseSvcClientService;
import uk.gov.ons.ctp.response.action.service.CollectionExerciseClientService;
import uk.gov.ons.ctp.response.casesvc.message.notification.CaseNotification;
import uk.gov.ons.ctp.response.casesvc.message.notification.NotificationType;
import uk.gov.ons.ctp.response.casesvc.representation.CaseDTO;
import uk.gov.ons.ctp.response.casesvc.representation.CaseGroupDTO;
import uk.gov.ons.ctp.response.collection.exercise.representation.CollectionExerciseDTO;

/**
 * Tests for the CaseNotificationServiceImpl
 */
@RunWith(MockitoJUnitRunner.class)
public class CaseNotificationServiceImplTest {

  private static final String DUMMY_UUID = "7bc5d41b-0549-40b3-ba76-42f6d4cf3991";

  @Mock
  private ActionCaseRepository actionCaseRepo;

  @Mock
  private ActionPlanRepository actionPlanRepo;
  
  @Mock 
  private ActionService actionService;
  
  @Mock
  private CaseSvcClientService caseService;
  
  @Mock
  private CaseSvcClientService caseSvcClientServiceImpl;
 
  @Mock
  private CollectionExerciseClientService collectionSvcClientServiceImpl;
  
  @Mock
  private CaseDTO caseDTO;
  
  @Mock
  private CaseGroupDTO caseGroupDTO;
  
  @Mock
  private CollectionExerciseDTO collectionExerciseDTO;
  
  @InjectMocks
  private CaseNotificationServiceImpl caseNotificationService;


  
  /**
   * Test calls repository correctly
   */
  @Test
  public void testAcceptNotification() throws Exception {
	  CaseNotification caseNotification = new CaseNotification();
	  caseNotification.setActionPlanId(DUMMY_UUID);  
	  caseNotification.setCaseId(DUMMY_UUID);
	  caseNotification.setNotificationType(NotificationType.ACTIVATED);
	  
	  ActionPlan actionPlan = new ActionPlan();
	  actionPlan.setActionPlanPK(1);
	  
	  when(actionPlanRepo.findById(any())).thenReturn(actionPlan);
	  
	  List<CaseNotification> notification = Arrays.asList(caseNotification);
	  
	  List<CaseDTO> caseJson = FixtureHelper.loadClassFixtures(CaseDTO[].class);
	  List<CaseGroupDTO> caseGroupJson = FixtureHelper.loadClassFixtures(CaseGroupDTO[].class);
	  List<CollectionExerciseDTO> collectionExerciseJson = FixtureHelper.loadClassFixtures(CollectionExerciseDTO[].class);
	  
	  
	  when(caseSvcClientServiceImpl.getCase(UUID.fromString(DUMMY_UUID))).thenReturn(caseJson.get(0));
	  when(caseSvcClientServiceImpl.getCaseGroup(caseJson.get(0).getCaseGroupId())).thenReturn(caseGroupJson.get(0));
	  when(collectionSvcClientServiceImpl.getCollectionExercise(caseGroupJson.get(0).getCollectionExerciseId())).thenReturn(collectionExerciseJson.get(0));
	  
	  caseNotificationService.acceptNotification(notification);
	  
	  
	  ArgumentCaptor <ActionCase> actionCase = ArgumentCaptor.forClass(ActionCase.class);
	  
	  verify(actionCaseRepo, times(1)).save(actionCase.capture());
	  
	  List<ActionCase> caze = actionCase.getAllValues();
	  
	  verify(actionCaseRepo, times(1)).flush();
	  
	  assertEquals(UUID.fromString(DUMMY_UUID) , caze.get(0).getActionPlanId());
	  assertTrue(caze.get(0).getActionPlanStartDate()!=null);
	  assertTrue(caze.get(0).getActionPlanEndDate()!=null);
	  assertEquals(UUID.fromString(DUMMY_UUID) , caze.get(0).getId());
	  
  }
}
