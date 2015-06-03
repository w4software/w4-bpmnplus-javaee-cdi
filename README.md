w4-bpmnplus-javaee-cdi
======================

This project contains bindings for [Context And Dependency Injection (CDI)](http://cdi-spec.org/) technology defined in [JSR-346](https://jcp.org/en/jsr/detail?id=346).

It contains the factories that provide the ability to inject W4 BPMN+ Services in any CDI managed bean.


Download
--------

You can download the prebuild JAR file from the following location
 
 - [bpmnplus-cdi-9.3.0.0.jar](http://maven.w4store.com/repository/contrib/eu/w4/contrib/bpmnplus-cdi/9.3.0.0/bpmnplus-cdi-9.3.0.0.jar) 

If using maven, you can use the following dependency snippet in your POM.

    <dependency>
      <groupId>eu.w4.contrib</groupId>
      <artifactId>bpmnplus-cdi</artifactId>
      <version>9.3.0.0</version>
    </dependency>

If not already done, you'll also need to reference our [maven repository](https://maven.w4store.com).


Usage
-----

### Configuration

Create a resource file named `w4bpmnplus.properties` at the root of your classpath, that will provide
the connection parameters.

Each property in the file `w4bpmnplus.properties` may either be

- a parameter defined in the enum class [NetworkConfigurationParameter](http://docs.w4store.com/9300/API/javadocs/engineClient/eu/w4/engine/client/configuration/NetworkConfigurationParameter.html)
- a parameter defined by the [w4-bpmnplus-java-api-extra](https://github.com/w4software/w4-bpmnplus-java-api-extra) project

At final, a working example for this configuration file can be the following snippet.

    RMI__REGISTRY_HOST=localhost
    RMI__REGISTRY_PORT=7707
    AUTO_RECOVERY=true
    FACTORY_CACHE=true

If the file is not found at runtime, W4 BPMN+ Engine on the default port of the local host will be used.

In a web-application, this file must be located in WEF-INF/classes directory of the WAR file (or webapplication folder).

In the source tree of of Maven project, this file must be in `src/main/resources`.



### Service Injection

In any CDI managed bean, you can use the following syntax

    public class MyBean {

        /* you can inject any W4 service */
        @Inject
        private ActivityService activityService;

        /* the object factory is also managed */
        @Inject
        private ObjectFactory objectFactory;

        /* here should lie the business code using the injected services */
    }


License
-------

Copyright (c) 2015, W4 S.A. 

This project is licensed under the terms of the MIT License (see LICENSE file)

Ce projet est licencié sous les termes de la licence MIT (voir le fichier LICENSE)
