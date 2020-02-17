# ARSW-Lab04

## PART I
- Integrate to the base project supplied the Beans developed in the previous exercise. Just copy the classes, NOT the configuration files. Rectify that the dependency injection scheme is correctly configured with the @Service and @Autowired annotations.
- Modify the persistence bean InMemoryBlueprintPersistence so that by default it is initialized with at least three other planes, and with two associated with the same author.
- Configure your application to offer the resource /blueprints, so that when a GET request is made, return in JSON format - all the drawings. For this:
     - Modify the BlueprintAPIController class taking into account the following example of a REST controller made with SpringMVC/SpringBoot
     - Have the BlueprintServices type bean injected into this class (which, in turn, will be injected with its persistence and point filtering dependencies).
