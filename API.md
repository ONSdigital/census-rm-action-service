# Action Service API
This page documents the Action service API endpoints. Apart from the Service Information endpoint, all these endpoints are secured using HTTP basic authentication. All endpoints return an `HTTP 200 OK` status code except where noted otherwise.

## Service Information
* `GET /info` will return information about this service, collated from when it was last built.

### Example JSON Response
```json
{
  "name": "actionsvc",
  "version": "10.42.0",
  "origin": "git@github.com:ONSdigital/rm-action-service.git",
  "commit": "06752afbf05f27c923ddf42d3cd2ec9eeafd3362",
  "branch": "master",
  "built": "2017-07-12T08:38:34Z"
}
```

## List Actions
* `GET /actions` will return a list of all actions, most recent first.

*Optional parameters:* `actionType` as an action type to filter the list by, `state` as an action state to filter the list by.

### Example JSON Response
```json
[
  {
    "id": "d24b3f17-bbf8-4c71-b2f0-a4334125d78d",
    "caseId": "7bc5d41b-0549-40b3-ba76-42f6d4cf3fdb",
    "actionPlanId": "5381731e-e386-41a1-8462-26373744db86",
    "actionRuleId": 120,
    "actionTypeName": "HouseholdCreateVisit",
    "createdBy": "SYSTEM",
    "manuallyCreated": false,
    "priority": 3,
    "situation": null,
    "state": "SUBMITTED",
    "createdDateTime": "2017-05-15T10:00:00Z",
    "updatedDateTime": "2017-05-15T10:00:00Z"
  }
]
```

An `HTTP 204 No Content` status code is returned if there are no actions.

## List Actions for Case
* `GET /actions/case/{caseId}` will return a list of actions for the case with the given ID.

### Example JSON Response
```json
[
  {
    "id": "d24b3f17-bbf8-4c71-b2f0-a4334125d78d",
    "caseId": "7bc5d41b-0549-40b3-ba76-42f6d4cf3fdb",
    "actionPlanId": "5381731e-e386-41a1-8462-26373744db86",
    "actionRuleId": 120,
    "actionTypeName": "HouseholdCreateVisit",
    "createdBy": "SYSTEM",
    "manuallyCreated": false,
    "priority": 3,
    "situation": null,
    "state": "SUBMITTED",
    "createdDateTime": "2017-05-15T10:00:00Z",
    "updatedDateTime": "2017-05-15T10:00:00Z"
  }
]
```

An `HTTP 404 Not Found` status code is returned if the case with the specified ID could not be found. An `HTTP 204 No Content` status code is returned if there are no actions.

## Cancel Actions for Case
* `PUT /actions/case/{caseId}/cancel` will cancel all actions for the case with the given ID.

### Example JSON Response
```json
[
  {
    "id": "d24b3f17-bbf8-4c71-b2f0-a4334125d78d",
    "caseId": "7bc5d41b-0549-40b3-ba76-42f6d4cf3fdb",
    "actionPlanId": "5381731e-e386-41a1-8462-26373744db86",
    "actionRuleId": 120,
    "actionTypeName": "HouseholdCreateVisit",
    "createdBy": "SYSTEM",
    "manuallyCreated": false,
    "priority": 3,
    "situation": null,
    "state": "SUBMITTED",
    "createdDateTime": "2017-05-15T10:00:00Z",
    "updatedDateTime": "2017-05-15T10:00:00Z"
  }
]
```

A 'HTTP 204 No Content' status code is returned when the aborted action with the specified ID has been rescheduled.
A 'HTTP 404 Not Found' status code is returned when a invalid ID is specified.
A 'HTTP 400 Bad Request' status code is returned when trying to rerun an action in a non aborted state.

## Aborted Actions can be Rerun
* `PUT /actions/rerun?actionId={actionId}` will rerun the action specified for the given ID.


An `HTTP 404 Not Found` status code is returned if the case with the specified ID could not be found. An `HTTP 204 No Content` status code is returned if there are no actions to be cancelled.

## Get Action
* `GET /actions/{actionId}` will return the details of the action with the given ID.

### Example JSON Response
```json
{
  "id": "d24b3f17-bbf8-4c71-b2f0-a4334125d78d",
  "caseId": "7bc5d41b-0549-40b3-ba76-42f6d4cf3fdb",
  "actionPlanId": "5381731e-e386-41a1-8462-26373744db86",
  "actionRuleId": 120,
  "actionTypeName": "HouseholdCreateVisit",
  "createdBy": "SYSTEM",
  "manuallyCreated": false,
  "priority": 3,
  "situation": null,
  "state": "SUBMITTED",
  "createdDateTime": "2017-05-15T10:00:00Z",
  "updatedDateTime": "2017-05-15T10:00:00Z"
}
```

An `HTTP 404 Not Found` status code is returned if the action with the specified ID could not be found.

## Create Action
* `POST /actions` will create an action.

**Required parameters**: `caseId` as the ID of the case the action is for, `actionTypeName` as the name of the action type the action is for and `createdBy` as the creator of the action.

*Optional parameters:* `priority` as the action priority (1 = highest, 5 = lowest) as passed to the remote handler.

### Example JSON Response
```json
{
  "id": "d24b3f17-bbf8-4c71-b2f0-a4334125d78d",
  "caseId": "7bc5d41b-0549-40b3-ba76-42f6d4cf3fdb",
  "actionPlanId": null,
  "actionRuleId": null,
  "actionTypeName": "BSREM",
  "createdBy": "SYSTEM",
  "manuallyCreated": true,
  "priority": null,
  "situation": null,
  "state": "SUBMITTED",
  "createdDateTime": "2017-10-05T14:37:30.014+0100",
  "updatedDateTime": null
}
```

An `HTTP 201 Created` status code is returned if the action creation was a success. An `HTTP 400 Bad Request` is returned if any of the required parameters are missing.


## Update Action
* `PUT /actions/{actionId}` will update the details of the action with the given ID.

*Optional parameters:* `priority` as the action priority (1 = highest, 5 = lowest) as passed to the remote handler, `situation` as the action status as recorded by the remote handler.

### Example JSON Response
```json
{
  "actionId": "d24b3f17-bbf8-4c71-b2f0-a4334125d78d",
  "caseId": "7bc5d41b-0549-40b3-ba76-42f6d4cf3fdb",
  "actionPlanId": "5381731e-e386-41a1-8462-26373744db86",
  "actionRuleId": 120,
  "actionTypeName": "HouseholdCreateVisit",
  "createdBy": "SYSTEM",
  "manuallyCreated": false,
  "priority": 1,
  "situation": null,
  "state": "SUBMITTED",
  "createdDateTime": "2017-05-15T10:00:00Z",
  "updatedDateTime": "2017-05-15T10:00:00Z"
}
```

An `HTTP 404 Not Found` status code is returned if the action with the specified ID could not be found. An `HTTP 400 Bad Request` status code is returned if any of the parameters are invalid.

## Update Action Feedback
* `PUT /actions/{actionId}/feedback` will transition the state of the action with a given ID.

**Required parameters**: `situation` as the action status as recorded by the remote handler, `outcome` as the outcome of the action within the context of the remote handler.

### Example JSON Response
```json
{
  "id": "d24b3f17-bbf8-4c71-b2f0-a4334125d78d",
  "caseId": "7bc5d41b-0549-40b3-ba76-42f6d4cf3fdb",
  "actionPlanId": "5381731e-e386-41a1-8462-26373744db86",
  "actionRuleId": 120,
  "actionTypeName": "HouseholdCreateVisit",
  "createdBy": "SYSTEM",
  "manuallyCreated": false,
  "priority": 1,
  "situation": null,
  "state": "SUBMITTED",
  "createdDateTime": "2017-05-15T10:00:00Z",
  "updatedDateTime": "2017-05-15T10:00:00Z"
}
```

An `HTTP 404 Not Found` status code is returned if the action with the specified ID could not be found. An `HTTP 400 Bad Request` status code is returned if any of the parameters are invalid.

## List Action Plans
* `GET /actionplans?selector1=selector1&selector2=selector2...` will return a list of all action plans for the given optional selectors, most recent first.

### Example JSON Response
```json
[
  {
    "id": "5381731e-e386-41a1-8462-26373744db86",
    "name": "C1O331D10E",
    "description": "Component 1 - England/online/field day ten/three reminders",
    "createdBy": "SYSTEM",
    "lastRunDateTime": "2017-06-15T10:00:00Z"
  }
]
```

An `HTTP 204 No Content` status code is returned if there are no action plans.

## Get Action Plan
* `GET /actionplans/{actionplanId}` will return the details of the action plan with the given ID.

### Example JSON Response
```json
{
  "id": "5381731e-e386-41a1-8462-26373744db86",
  "name": "C1O331D10E",
  "description": "Component 1 - England/online/field day ten/three reminders",
  "createdBy": "SYSTEM",
  "lastRunDateTime": "2017-06-15T10:00:00Z"
}
```

An `HTTP 404 Not Found` status code is returned if the action plan with the specified ID could not be found.

## Create Action Plan
* `POST /actionplans` will create an action plan.
* Returns 201 Created if the resource is created
* Returns 400 Bad Request
* Returns 409 Conflict
### Example JSON POST
```json
{
	"name": "notification",
	"description": "bres enrolment notification",
	"createdBy": "SYSTEM",
    "selectors": {
      "selector1": "selector1",
      "selector2": "selector2"
    }
}
```

### Example JSON Response
```json
{
  "id": "5381731e-e386-41a1-8462-26373744dc55",
  "name": "notification",
  "description": "bres enrolment notification",
  "createdBy": "SYSTEM",
  "lastRunDateTime": "2017-06-15T10:00:00Z"
}
```

An `HTTP 201 Created` status code is returned if the action creation was a success. An `HTTP 400 Bad Request` is returned if any of the required parameters are missing.


## Update Action Plan
* `PUT /actionplans/{actionplanId}` will update the details of the action plan with the given ID.

*Optional parameters:* `description` as the action plan description, `lastRunDateTime` as the date/time the action plan was last successfully run, `selectors` dictionary of action plan selectors

### Example JSON Response
```json
{
  "id": "5381731e-e386-41a1-8462-26373744db86",
  "name": "C1O331D10E",
  "description": "Component 1 - England/online/field day ten/three reminders",
  "createdBy": "SYSTEM",
  "lastRunDateTime": "2017-06-15T10:00:00Z"
}
```

An `HTTP 404 Not Found` status code is returned if the action plan with the specified ID could not be found. An `HTTP 400 Bad Request` status code is returned if any of the parameters are invalid.

## List Action Plan Jobs
* `GET /actionplans/{actionplanId}/jobs` will return a list of action plan jobs (most recent first) for the action plan with given ID.

### Example JSON Response
```json
[
  {
    "id": "714356ba-7236-4179-8007-f09190eed323",
    "actionPlanId": "5381731e-e386-41a1-8462-26373744db86",
    "createdBy": "SYSTEM",
    "state": "SUBMITTED",
    "createdDateTime": "2017-05-15T10:00:00Z",
    "updatedDateTime": "2017-05-15T10:00:00Z"
  }
]
```

An `HTTP 404 Not Found` status code is returned if the action plan with the specified ID could not be found. An `HTTP 204 No Content` status code is returned if there are no action plan jobs for the action plan.

## Get Action Plan Job
* `GET /actionplans/jobs/{actionplanId} will return the details of the action plan job with the given ID.

### Example JSON Response
```json
{
  "id": "714356ba-7236-4179-8007-f09190eed323",
  "actionPlanId": "5381731e-e386-41a1-8462-26373744db86",
  "createdBy": "SYSTEM",
  "state": "SUBMITTED",
  "createdDateTime": "2017-05-15T10:00:00Z",
  "updatedDateTime": "2017-05-15T10:00:00Z"
}
```

An `HTTP 404 Not Found` status code is returned if the action plan job with the specified ID could not be found.

## Create Action Plan Job
* `POST /actionplans/{actionplanId}/jobs` will create an action plan job (i.e. execute the action plan) for the action plan with the given ID.

**Required parameters**: `createdBy` as the creator of the action plan job.

### Example JSON Response
```json
{
  "id": "714356ba-7236-4179-8007-f09190eed323",
  "actionPlanId": "5381731e-e386-41a1-8462-26373744db86",
  "createdBy": "SYSTEM",
  "state": "SUBMITTED",
  "createdDateTime": "2017-05-15T10:00:00Z",
  "updatedDateTime": "2017-05-15T10:00:00Z"
}
```

An `HTTP 201 Created` status code is returned if the action plan job creation was a success. An `HTTP 404 Not Found` status code is returned if the action plan with the specified ID could not be found. An `HTTP 400 Bad Request` is returned if any of the required parameters are missing.

## List Action Rules for Action Plan
* `GET /actionrules/actionplan/{actionPlanId}` will return a list of action rules for the action plan with the given ID.

### Example JSON Response
```json
[
  {
    "id": "35fe06af-d16b-47b0-92a3-099805c65c9d",
    "name": "BSREM+45",
    "description": "Enrolment Reminder Letter(+45 days)",
    "triggerDateTime": "2017-05-15T10:00:00Z",
    "priority": 3,
    "actionTypeName": "BSREM"
  }
]
```

An `HTTP 404 Not Found` status code is returned if an action plan with the specified ID could not be found.

## Create Action Rule
* `POST /actionrules` will create an action rule.

### Example JSON Request
```json
{
  "actionPlanId": "714356ba-7236-4179-8007-f09190eed323",
  "actionTypeName": "BSREM",
  "name": "BSREM+45",
  "description": "Enrolment Reminder Letter(+45 days)",
  "triggerDateTime": "2017-05-15T10:00:00Z",
  "priority": 3
}
```

* An `HTTP 201 Created` status code is returned if the action rule creation was a success.
* An `HTTP 404 Not Found` status code is returned if the action plan or action type with the specified ID could not be found.
* An `HTTP 400 Bad Request` is returned if any of the required parameters are missing.

## Update Action Rule
* `PUT /actionrules/{actionRuleId}` will update the details of the action rule with the given ID.

*Optional parameters:* 
* `priority` the action priority (1 = highest, 5 = lowest) as passed to the remote handler
* `name` the name of the action rule.
* `description` description for the action rule.
* `triggerDateTime` action rule trigger datetime in iso 8601.

### Example JSON Response
```json
{
  "actionPlanId": "714356ba-7236-4179-8007-f09190eed323",
  "actionTypeName": "BSREM",
  "name": "BSREM+45",
  "description": "Enrolment Reminder Letter(+45 days)",
  "triggerDateTime": "2017-05-15T10:00:00Z",
  "priority": 3
}
```

An `HTTP 404 Not Found` status code is returned if the action rule with the specified ID could not be found.
An `HTTP 400 Bad Request` status code is returned if any of the parameters are invalid.
