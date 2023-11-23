# TurnerTech Tuples

A basic java 9+ Module implementing Tuples in both a Typed, and Untyped manner. Zero dependencies, uses Java Modules (as in has a module-info.java).

This implementation treats a Tuple as a Java Collection, applying the rules and restrictions of Tuples as per [my intensive research](https://en.wikipedia.org/wiki/Tuple). The aim is to provide a balance between the common need for a basic typed pair/triple object (etc. for return types), and a Tuple class which follows the rules of immutability etc.

# Packages

The packages are stored in Maven Central under the following coordinates. Search [maven central](https://central.sonatype.com/) for the latest version.

```
<dependency>
    <groupId>de.turnertech</groupId>
    <artifactId>tuples</artifactId>
    <version>...</version>
</dependency>
```

# Where To Start

Use the [Maven Site](https://liturner.github.io/tuples/) to get any information you need. It contains the [Javadoc](https://liturner.github.io/tuples/apidocs/de.turnertech.tuples/module-summary.html) and a few usefull examples for kickstarting your use.

# Building

The build process is "pure maven". You will need an OpenPGP key set up and ready to use for anything beyound ```package```.

## Module

- ```mvn clean verify``` (local)

The ```clean``` prevents bugs, the ```verify``` triggers a build with all tests.

## Site

- ```mvn clean verify site``` (local)
- ```mvn clean verify site-deploy``` (deploy to GitHub)

The ```clean``` prevents bugs, the ```verify``` triggers JaCoCo for the site report, and ```site-deploy``` builds, then deploys the site to the site branch of this repository.

# See Also

- [javatuples](https://github.com/javatuples/javatuples), another very mature tuples implementation. Our implementation specifics differ slightly so just choose the implementation which suits you!