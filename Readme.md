# Markdowner

[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Markdowner&color=informational)](https://github.com/jesperancinha/markdowner)
[![Maven Central](https://img.shields.io/maven-central/v/org.jesperancinha.parser/markdowner)](https://search.maven.org/search?q=parser.markdowner)
[![GitHub release](https://img.shields.io/github/release-pre/jesperancinha/markdowner.svg)](https://github.com/jesperancinha/markdowner/releases)

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c423c85288eb45c883e2f721bb611a3f)](https://www.codacy.com/manual/jofisaes/markdowner?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jesperancinha/markdowner&amp;utm_campaign=Badge_Grade)
[![CircleCI](https://circleci.com/gh/jesperancinha/markdowner.svg?style=svg)](https://circleci.com/gh/jesperancinha/markdowner)
[![Build Status](https://travis-ci.org/jesperancinha/markdowner.svg?branch=master)](https://travis-ci.org/jesperancinha/markdowner)
[![codebeat badge](https://codebeat.co/badges/b6f714fa-6632-473e-9eb5-c481c776d415)](https://codebeat.co/projects/github-com-jesperancinha-markdowner-master)
[![BCH compliance](https://bettercodehub.com/edge/badge/jesperancinha/markdowner?branch=master)](https://bettercodehub.com/)
[![Build status](https://ci.appveyor.com/api/projects/status/kuedmakr9bbne46q/branch/master?svg=true)](https://ci.appveyor.com/project/jesperancinha/markdowner/branch/master)

[![GitHub language count](https://img.shields.io/github/languages/count/jesperancinha/markdowner.svg)]()
[![GitHub top language](https://img.shields.io/github/languages/top/jesperancinha/markdowner.svg)]()
[![GitHub top language](https://img.shields.io/github/languages/code-size/jesperancinha/markdowner.svg)]()

## Description

An extra complicated markdown parsing library with extra special functions.

Look into the javadocs for more info.

Major highlights include:

-   Paragraph parser (supports inner paragraphs #).
-   Read of .md content stream with (#) paragraphs removed (this one does consider whole paragraphs and the tree).
-   Read of a complete .md content stream or text auto-creation according to filter chain, determined by project type and its automated packaging system
-   Merge of a readme file to a parsed template content

## Maven

```xml
<dependency>
  <groupId>org.jesperancinha.parser</groupId>
  <artifactId>markdowner</artifactId>
  <version>2.0.1</version>
  <type>pom</type>
</dependency>
```
## Tools

```text
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

## About me

[![Twitter Follow](https://img.shields.io/twitter/follow/joaofse?label=João%20Esperancinha&style=social)](https://twitter.com/joaofse)
[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=jesperancinha&style=social)](https://github.com/jesperancinha)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=joaofilipesabinoesperancinha.nl&color=6495ED)](http://joaofilipesabinoesperancinha.nl)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=Time%20Disruption%20Studios&color=6495ED)](http://tds.joaofilipesabinoesperancinha.nl/)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=Image%20Train%20Filters&color=6495ED)](http://itf.joaofilipesabinoesperancinha.nl/)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=MancalaJE&color=6495ED)](http://mancalaje.joaofilipesabinoesperancinha.nl/)
[![Generic badge](https://img.shields.io/static/v1.svg?label=DEV&message=Profile&color=green)](https://dev.to/jofisaes)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Medium&message=@jofisaes&color=green)](https://medium.com/@jofisaes)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Hackernoon&message=@jesperancinha&color=green)](https://hackernoon.com/@jesperancinha)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Free%20Code%20Camp&message=jofisaes&color=008000)](https://www.freecodecamp.org/jofisaes)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Hackerrank&message=jofisaes&color=008000)](https://www.hackerrank.com/jofisaes)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Acclaim%20Badges&message=joao-esperancinha&color=red)](https://www.youracclaim.com/users/joao-esperancinha/badges)
[![Generic badge](https://img.shields.io/static/v1.svg?label=All%20Badges&message=Badges&color=red)](https://github.com/jesperancinha/project-signer/blob/master/project-signer-templates/Badges.md)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Status&message=Project%20Status&color=red)](https://github.com/jesperancinha/project-signer/blob/master/project-signer-templates/Status.md)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Google%20Apps&message=Joao+Filipe+Sabino+Esperancinha&color=orange)](https://play.google.com/store/apps/developer?id=Joao+Filipe+Sabino+Esperancinha)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Code%20Pen&message=jesperancinha&color=orange)](https://codepen.io/jesperancinha)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=ITF%20Chartizate%20Android&color=yellow)](https://github.com/JEsperancinhaOrg/itf-chartizate-android)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=ITF%20Chartizate%20Java&color=yellow)](https://github.com/JEsperancinhaOrg/itf-chartizate-modules/tree/master/itf-chartizate-java)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=ITF%20Chartizate%20API&color=yellow)](https://github.com/JEsperancinhaOrg/itf-chartizate/tree/master/itf-chartizate-api)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Markdowner%20Core&color=yellow)](https://github.com/jesperancinha/markdowner/tree/master/markdowner-core)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Markdowner%20Filter&color=yellow)](https://github.com/jesperancinha/markdowner/tree/master/markdowner-filter)
