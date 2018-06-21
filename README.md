# FLUX STARWARS
*Copyright (C) 2018 Valtoni Boaventura*

[![Build Status](https://travis-ci.org/valtoni/flux-starwars.svg?branch=master)](https://travis-ci.org/valtoni/flux-starwars)

## Introduction

**Flux Starwars** is a program created in spring-webflux to demonstrate the 
cabability of spring use of project reactor (https://projectreactor.io), exposing 
non-blocking and backpressure ready microservice solution to access swapi, an public 
starwars api.

## Functionality

This project contains 3 entities: Climate, Terrain and Planet. Planet contains
Climate and Terrain, in simple reference manner. The api focuses querying
planet, doing CRUD operations. The POST (exposed in API section) will write
planet document in local mongo. Any GET operation will query
public swapi API counting films witch this planet has participation.


## Running

This microservice will run in a straightforward way, it's using embedabble maven
to run. We must bring up an mongodb too. Steps:
* Run mongodb oficial docker container: docker run -p 27017:27017 mongo
* Run springboot maven wrapper: ./mvnw spring-boot:run

Wow! It´s surprising up!

## API
Exposed urls:

1) GET /planets - lista all planets
2) GET /planets/name/{name} - list all planets with this name
3) GET /planets/id/{id} - get planet with this id
4) POST /planets - create new planet
5) DELETE /planets/{id} - delete planet with this id
 

