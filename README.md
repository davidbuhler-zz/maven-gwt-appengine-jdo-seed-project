#Plaid-Suit: A Conventional Seed Project for Google-Stack Developers; that doesn't need any IDE Configuration

##About
[Plaid Suit](http://plaid-suit.appspot.com/) is a fully functional CRUD application. It's a way to get started building an HTML application that works across browsers, leveraging the power of Java. It uses Google Web Toolkit with a pure Maven configuration for developers who want to build on top of Google App Engine, using Google's HRD, Schemaless Datastore *without adding framework configuration or plugins to the IDE*. Instead of an example app, this project tries to bridge the gap between a seed app and an example application by providing assets you'll need to configure or utilize before going to production, and those assets considered best-practice.

## This project uses the following stack
* Google Web Toolkit 2.7.0
* Google App Engine 1.9.18
* Datanucleus
* JSR 303: Bean Validation
* Java Data Objects (JDO) 3.0
* Maven

## 1st Tier-Conventions
* Fault tolerant exception handling
* Constants for Message fragments used on the client and server
* Constants for validation values used on the client and server
* Explicit inclusion of Google App Engine Configuration files (DoS Protection, CRON, etc.)
* Explicit inclusion of Boilerplate assets for webroot artifacts (robots.txt, humans.txt, favicons, etc.)
* Explicit inclusion and configuration of user-friendly error-pages for Google App Engine errors (over quota, default, etc.)
* RemoteLoggingServiceUtil is already added to the deployment descriptor

## 2nd Tier-Conventions
* Alphabetized dependencies in the Maven configuration (except for order-dependent libraries)
* Services exist in a Singleton for added control over management of CSRF Tokens , Windows & Service Calls 
* An Observer for Error messages. Error Messages can be dispatched anywhere in the application.
* Error pages reference common styles and contain meta mark-up for search-engine behaviors
* Robots.txt disallows spiders from scouring the directory containing error pages
* Styles are omitted; You can begin begin developing out-of-the-box, rather than deconstructing style names.

## Installation
```
#clone the project

#open a console or "Maven Projects View"

#install the dependencies
mvn install

#launch DevMode 
mvn gwt:run
```
####Understanding GWTs Super Dev Mode
Developers who have not used GWT's Super Dev Mode should consider reading [Getting Started with GWT's Super Dev Mode](http://www.gwtproject.org/articles/superdevmode.html#Launch).

##Uploading
In an attempt to keep the Maven Configuration as light as possible, the Maven Dependency for App Engine is removed. The anticipation is for developers to use Terminal to commit the project to Google App Engine. Before committing the project, please see if [Google's Authentication] (https://cloud.google.com/appengine/docs/java/tools/uploadinganapp#Passwordless_Login_with_OAuth2)  for AppCfg "update" applies to your own Google security settings.

## Goals
Getting the application up and running can be done in a matter of minutes. As you advance in development, using [goals](http://mojo.codehaus.org/gwt-maven-plugin/run-mojo.html) from the gwt-maven mojo may be beneficial. 

## Contributing
We welcome contributions or suggestions for improvements, as well as defect logging.
