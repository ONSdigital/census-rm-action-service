[![Build Status](https://travis-ci.com/ONSdigital/census-rm-action-service.svg?branch=master)](https://travis-ci.com/ONSdigital/census-rm-action-service)
[![codecov](https://codecov.io/gh/ONSdigital/census-rm-action-service/branch/master/graph/badge.svg)](https://codecov.io/gh/ONSdigital/census-rm-action-service)

# Census Action Service
This repository contains the Census Action service, forked from [RM Action Service](https://github.com/ONSdigital/rm-action-service). 

This microservice is a RESTful web service implemented using [Spring Boot](http://projects.spring.io/spring-boot/).
It receives actionLifeCycle event messages via RabbitMQ from the action Service, which indicates what has happened to a action ie activation, deactivation etc
The action service will execute an action plan for each action that is actionable, off of which actions are created.
Each action follows a state transition model or path, which involves distribution of the actions to handlers, and for some types of actions, the service will expect
feedback messages indicating successful downstream processing of the action or otherwise by the handler.

The action service is agnostic of what any given handler will actually do with the action sent to it, and as such, will send the same format of ActionInstruction message to each handler.
It is upto the handler to pick out what information is relevant to it from the instruction sent to it by this service.

## Running

There are two ways of running this service

* The easiest way is via docker [Census RM Docker Dev](https://github.com/ONSdigital/cenus-rm-docker-dev)
* Alternatively running the service up in isolation
    ```bash
    cp .maven.settings.xml ~/.m2/settings.xml  # This only needs to be done once to set up mavens settings file
    mvn clean install
    mvn spring-boot:run
    ```

## API
See [API.md](https://github.com/ONSdigital/census-rm-action-service/blob/master/API.md) for API documentation.

## Swagger Specifications
To view the Swagger Specifications for the Action Service, run the service and navigate to http://localhost:8151/swagger-ui.html.

## Copyright
Copyright (C) 2017 Crown Copyright (Office for National Statistics)
