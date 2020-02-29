# Getting Started

### Project structure
I am used to project organization that separates components in functional layers.
For MVC app it would be a separate package for:
* controllers
* services
* model (aka. domain, persistence)

For this project i decided to steer away from this convention and group the components based on a purpose.
That can be understood as a single aggregate in the identified domain.

Example:
    
    my.package.feature_x
      FeatureXController
      FeatureXService
      ModelX
    my.package.feature_y
      FeatureYController
      FeatureYService
      ModelY

Since this project has simple responsibility and simple domain, all the components will inevitably end up in a same package.
I don't see it as a problem, it can be an advantage. 

In the simple project the difference in the project layout is not that apparent.

I believe that more complex applications would benefit from this style of organization:
* it's possible to simplify naming of classes because implementation of a feature exists in a dedicated package that acts as a namespace.
* it's easier to analyze the app by new developer - components related to the same feature are grouped together
* it promotes single responsibility, decreases proliferation of GOD-class components that manage multiple abstractions across the layer
* it's potentially easier to notice that app has too many responsibility - in such case it's easier to carve out a package as a new standalone application 


### Technology choice - Rationale

#### Spring Boot
One of the best things that has happened to JVM enterprise software development in recent years.

#### Build system
Maven was requested.
Otherwise I would prefer Gradle for a number of reasons.

#### Swagger and Swagger-UI
Provides pleasant and powerful way to document APIs and lowers the effort to test them in development phase. 

#### Lombok
Usually I am not that fond of code-generation tools.
I favour explicit over implicit.
Lombok is the most controversial thing that I've decided to use.

While I am reluctant to some of its features I appreciate it for syntactic sugar for hiding boiler plate.
Abstracting Logger and making it more declarative is also a good thing from visual point of view.

In short - I use Lombok to compensate for the missing features that I like in languages like Groovy or Kotlin. 

#### Testing framework
I have used Spock for tests.
Nothing is wrong with JUnit or TestNG, but I believe that Spock is superior in every aspect that matters in Tests:

Readability:
* Groovy is more expressive than any other JVM language while being 90% Java syntax compatible (gentle learning curve).
* Test method names can have spaces and be proper sentences - that is just awesome and helps to show the intent of the test.
* Very compact and meaningful fixtures - short test is a test that other developers will be keen to read and learn from.
* Assertions are trivial even without additional libraries like AssertJ, Hamcrest or FEST.
* Clean and visual separation of: fixture setup, test action and assertions.

Test doubles:
* Mocking & Stubbing is as powerful as in the best specialized libraries.
* Built-in Stubs and Mocks are lenient out-of-the box. That helps a Mock to be **proper** drop-in replacement for complex dependency.
* Matching and capturing of parameters in method calls is trivial.

Data driven tests:
* Other testing frameworks are doing it wrong. Period.
* See: http://spockframework.org/spock/docs/1.3/data_driven_testing.html

Superior report of test result:
* See this error report and try not to love it:

    Condition not satisfied:
    
    stack.size() == 2
          |      |
          1      false
    


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/maven-plugin/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/reference/htmlsingle/#using-boot-devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/reference/htmlsingle/#production-ready)
* [Spock Framework](http://spockframework.org/spock/docs/1.3/)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

