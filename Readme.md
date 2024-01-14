# Markdowner

[![Twitter URL](https://img.shields.io/twitter/url?logoColor=blue&style=social&url=https%3A%2F%2Fimg.shields.io%2Ftwitter%2Furl%3Fstyle%3Dsocial)](https://twitter.com/intent/tweet?text=%20Checkout%20this%20%40github%20repo%20by%20%40joaofse%20%F0%9F%91%A8%F0%9F%8F%BD%E2%80%8D%F0%9F%92%BB%3A%20https%3A//github.com/jesperancinha/markdowner)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Markdowner&color=informational)](https://github.com/jesperancinha/markdowner)

[![GitHub release](https://img.shields.io/github/release/jesperancinha/markdowner.svg)](https://github.com/jesperancinha/markdowner/releases)
[![Maven Central](https://img.shields.io/maven-central/v/org.jesperancinha.parser/markdowner)](https://mvnrepository.com/artifact/org.jesperancinha.parser/markdowner)
[![Sonatype Nexus](https://img.shields.io/nexus/r/https/oss.sonatype.org/org.jesperancinha.parser/markdowner.svg)](https://search.maven.org/artifact/org.jesperancinha.parser/markdowner)

[![javadoc](https://javadoc.io/badge2/org.jesperancinha.parser/markdowner/javadoc.svg)](https://javadoc.io/doc/org.jesperancinha.parser/markdowner)
[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

[![CircleCI](https://circleci.com/gh/jesperancinha/markdowner.svg?style=svg)](https://circleci.com/gh/jesperancinha/markdowner)
[![Build status](https://ci.appveyor.com/api/projects/status/kuedmakr9bbne46q/branch/master?svg=true)](https://ci.appveyor.com/project/jesperancinha/markdowner/branch/master)
[![markdowner](https://github.com/jesperancinha/markdowner/actions/workflows/markdowner.yml/badge.svg)](https://github.com/jesperancinha/markdowner/actions/workflows/markdowner.yml)

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c423c85288eb45c883e2f721bb611a3f)](https://www.codacy.com/manual/jofisaes/markdowner?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jesperancinha/markdowner&amp;utm_campaign=Badge_Grade)
[![codebeat badge](https://codebeat.co/badges/b6f714fa-6632-473e-9eb5-c481c776d415)](https://codebeat.co/projects/github-com-jesperancinha-markdowner-master)

[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/c423c85288eb45c883e2f721bb611a3f)](https://www.codacy.com/gh/jesperancinha/markdowner/dashboard?utm_source=github.com&utm_medium=referral&utm_content=jesperancinha/markdowner&utm_campaign=Badge_Coverage)
[![codecov](https://codecov.io/gh/jesperancinha/markdowner/branch/master/graph/badge.svg?token=BREGsxyj5M)](https://codecov.io/gh/jesperancinha/markdowner)
[![Coverage Status](https://coveralls.io/repos/github/jesperancinha/markdowner/badge.svg?branch=master)](https://coveralls.io/github/jesperancinha/markdowner?branch=master)

[![GitHub language count](https://img.shields.io/github/languages/count/jesperancinha/markdowner.svg)]()
[![GitHub top language](https://img.shields.io/github/languages/top/jesperancinha/markdowner.svg)]()
[![GitHub top language](https://img.shields.io/github/languages/code-size/jesperancinha/markdowner.svg)]()

---

## Technologies used

Please check the [TechStack.md](TechStack.md) file for details.

## Description

An extra complicated markdown parsing library with extra special functions.

Please Look into the javadocs for more info or check our [reference documentation](http://jesperancinha.github.io/markdowner/) for more details.

Check all the release notes [here](./ReleaseNotes.md).

## Maven

```xml
<dependency>
  <groupId>org.jesperancinha.parser</groupId>
  <artifactId>markdowner</artifactId>
  <version>4.0.0</version>
  <type>pom</type>
</dependency>
```
## Tools

### Use Java 11

```bash
sdk install java 11.0.11.hs-adpt
sdk use java 11.0.11.hs-adpt
```

### Sign to Nexus

```bash
gpg --keyserver hkp://keyserver.ubuntu.com --send-keys <your GPG key>
gpg --list-keys
export GPG_TTY=$(tty)
mvn clean deploy -Prelease
mvn nexus-staging:release  -Prelease
```
```xml
<settings>
    <servers>
        <server>
            <id>ossrh</id>
            <username>your-jira-id</username>
            <password>your-jira-pwd</password>
        </server>
    </servers>
</settings>
```

## References

-   [Nexus Repository Manager -Sonatype Nexus](https://oss.sonatype.org/)
-   [Sonatype The Central Repository](https://search.maven.org/)
-   [nexus-staging-maven-plugin not working with java 16](https://issues.sonatype.org/browse/NEXUS-27902)

## About me

[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
