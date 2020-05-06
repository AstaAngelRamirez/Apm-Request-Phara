# apm-request-android ![Request](https://img.shields.io/badge/request-1.1.1-green.svg)

Android module to make request to ASTA web services in known format. 


# Download

Because we are using a private GitHub repository, we need to configure the company repository access into project for use this module:

In `gradle.properties` add your GitHub collaborator credentials:

```groovy
  gitUser = GitHubUsername
  gitPass = GitHubPassword
```
*Note: is important that you are an active collaborator of project for make use of this module.*

Next, add the following into `build.gradle`:

```groovy
  maven {
    credentials {
      username gitUser
      password gitPass
    }
    url "https://raw.githubusercontent.com/astadevelopers/apm-request-android/master/aar"
    authentication {
      basic(BasicAuthentication)
    }
  }
```

And, the next steps are the usual:

With Gradle:

```groovy
implementation 'com.apm:request:X.X.X'
```

or Maven:

```xml
<dependency>
  <groupId>com.apm</groupId>
  <artifactId>request</artifactId>
  <version>X.X.X</version>
  <type>pom</type>
</dependency>
```

Where **X.X.X** is the current version of each library

Usage
--------

Please visit [Wiki](https://github.com/astadevelopers/apm-request-android/wiki) for more instructions of how to implement Request module
