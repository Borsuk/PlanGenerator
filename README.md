# Getting Started

### Quickstart Guide
Run the application with Maven:
    
    $ mvn spring-boot:run
    
Run Jar-packaged application:
    
    $ mvn clean package
    $ java -jar target/PlanGenerator-0.0.1-SNAPSHOT.jar
    
Run application with Docker:

    $ docker build --file=Dockerfile --tag=plan-generator:latest --rm=true .
    $ docker run --name=plan-generator --publish=8080:8080 --rm  plan-generator:latest

Call the endpoint to generate repayment plan (cURL):

    $ curl --location --request POST 'localhost:8080/generate-plan' \
      --header 'Content-Type: application/json' \
      --data-raw '{
        "duration": 24,
        "loanAmount": 5000,
        "nominalRate": 5,
        "startDate": "2020-02-28T22:09:08.697Z"
      }'

Run Tests:

    $ mvn clean test      

Measure Tests coverage:

    $ mvn jacoco:report
    $ x-www-browser target/site/jacoco/index.html
    
### Configuration
See application.yml:
```
    repayment_schedule:
      days_in_month: 30
      days_in_year: 360
```
These settings can be used to change values of the constants used for computations.  

Overview of the settings is provided with `spring-boot-configuration-processor` - IDEs compatible with its metadata format are able to autocomplete names of configuration keys and offer hints on possible values.

Settings can be externalized with the [standard Spring mechanisms](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config).

### API documentation
Documentation for the API is provided by Swagger2 and is visualised by Springfox Swagger-UI.  
Please run the application and navigate to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
    
### Project structure
I am used to project organization that separates components according to functional layers.
For the MVC app it would be a separate package for:
* controllers
* services
* model (aka. domain, persistence)

For this project i decided to steer away from this convention and group the components based on a purpose.
In other words it is one package per aggregate (DDD) or per feature.

Example:
--------
    
    my.package.feature_x
      FeatureXController
      FeatureXService
      ModelX_1
      ModelX_2
    my.package.feature_y
      FeatureYController
      FeatureYService
      ModelY

In the simple application the difference in the project layout is not that apparent.  
Since this project has narrow responsibility and singular focused domain, all the components will inevitably end up in a same package.  
I don't see it as a problem, it can be an advantage.  

I believe that more complex applications would benefit from this style of organization:
* it simplifies naming of classes because a feature-dedicated package acts as a namespace
* it's easier to analyze the app by new developers - components of different layers but related to the same feature are grouped together
* it promotes single responsibility by decreased proliferation of GOD-class components that manage multiple abstractions across the layer
* it's easier to notice that the app has too many responsibilities - and in such case it's easier to carve out a package to be a new standalone application 

### Technology choice - Rationale

#### Spring Boot
One of the best things that has happened to JVM enterprise software in recent years.

#### Build system
Maven was requested.  
Otherwise I would prefer Gradle for a number of reasons.

#### Swagger and Swagger-UI
Provides pleasant and powerful way to document APIs.  
Enables low effort manual tests in development phase. 

#### Lombok
Usually I am not that fond of code-generation tools.
I favour explicit over implicit.
Lombok is the most controversial thing that I've decided to use.

While I am reluctant to some of its features I appreciate it for syntactic sugar that hides boiler-plate code.
Abstracting the Logger and making it more declarative is also a good thing.

In short - I use Lombok to compensate for the Java missing features that I like in languages like Groovy or Kotlin. 

#### Testing framework
I have used Spock for tests.  
Sadly it's less popular than it deserves.  

Nothing is wrong with JUnit or TestNG, but I believe that Spock is superior in every aspect that matters in Tests:

Readability:
------------
* Groovy is more expressive than any other JVM language while being 90% Java syntax compatible (gentle learning curve).
* Test method names can have spaces and be proper sentences - that is just awesome and helps to show the intent of the test.
* Very compact and meaningful fixtures - short test is a test that other developers will be keen to read and learn from.
* Assertions are trivial even without additional libraries like AssertJ, Hamcrest or FEST.
* Clean and visual separation of: fixture setup, test action and assertions.

Test doubles:
-------------
* Mocking & Stubbing is as powerful as in the best specialized libraries (Mockito, PowerMock).
* Built-in Stubs and Mocks are lenient out-of-the box. That helps a Mock to be **proper** drop-in replacement for complex dependency.
* It's easy to treat a Mock as a Spy (partial mock)
* It's easy to configure a Mock to return specific responses for specific inputs
* It's easy to configure a Mock to return specific responses for a range of inputs (wildcard matching)

Test as a documentation:
----------------------
* All elements of test are very human-readable
* It's easy to write a test that has a nice name and tels the story
* Test is more compact - it's easier to comprehend

Data driven tests:
------------------
* Other testing frameworks are doing it wrong. Period.
* See: http://spockframework.org/spock/docs/1.3/data_driven_testing.html

Superior report of test result:
-------------------------------
* See this error report and try not to love it:
```
    Condition not satisfied:
    
    stack.size() == 2
          |      |
          1      false
```

### Message to the reviewer
Below are additional explanations and clarification on controversies.

#### Date format, time is truncated
In the examples, all dates in the response have the time part truncated to 00:00:00 - even when the request had non-midnight time in the startDate.  
Based on this observation I have assumed that rounding the date to zero-time is expected. 

#### Illegal JSON in instructions
In the example from the instructions, the response body has illegal JSON format:
```
    {
        [                <-- HERE, an unlabeld array
            {
                "borrowerPaymentAmount": ...
                ...    
            },
            {...},
            ...
            {...}
        ]
    }
```
I have assumed that it's a mistake, and decided to continue with simpler format that has an array as a root of the JSON document:
```
    [                <-- ROOT
        {
            "borrowerPaymentAmount": ...
            ...    
        },
        {...},
        ...
        {...}
    ]
```

#### Tests
It's not mistake that my assertions use `==` operator.
Spock tests use the Groovy language, and `==` operator in Groovy has the same meaning as `Object.equals()` in Java.  
JUnit test would require `assertEquals` construct for the same effect.
 
#### Getters and Setters
I avoid Getters and Setters.  
In my opinion they not only obfuscate the class by making it unnaturally long but also are a "fake insurance policy".  
In **most** of the cases a `private` field with accessor and mutator is effectively no different than `public` field, so there is no benefit in using them.  
Responsible use of Getters and Setters requires use of sophisticated techniques like defensive copying, which are not always possible and might be expensive.    
I am in favor of using immutable classes instead.  

#### Validation
I decided to have validation at the level of controller only.  
In my opinion it's sufficient for this application.
My understanding is that it's a kind of microservice and validation of request prevents all illegal combinations of values in the deeper layers.

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

