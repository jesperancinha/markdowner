# Markdowner

[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Markdowner&color=informational)](https://github.com/jesperancinha/markdowner)
[![Maven Central](https://img.shields.io/maven-central/v/org.jesperancinha.parser/markdowner)](https://search.maven.org/search?q=a:parser)

[![Build Status](https://travis-ci.org/jesperancinha/markdowner.svg?branch=master)](https://travis-ci.org/jesperancinha/markdowner)
[![BCH compliance](https://bettercodehub.com/edge/badge/jesperancinha/markdowner?branch=master)](https://bettercodehub.com/)

[![GitHub language count](https://img.shields.io/github/languages/count/jesperancinha/markdowner.svg)]()
[![GitHub top language](https://img.shields.io/github/languages/top/jesperancinha/markdowner.svg)]()
[![GitHub top language](https://img.shields.io/github/languages/code-size/jesperancinha/markdowner.svg)]()

## Description

An extra complicated markdown parsing library with extra special functions.

Look into the javadocs for more info.

Major highlights include:

-   Paragraph parser (current supports only start with #).
-   Read of .md content stream with (#) paragraphs removed (this one does consider whole paragraphs and the tree).
-   Read of a complete .md content stream or text auto-creation according to filter chain, determined by project type and its automated packaging system
-   Merge of a readme file to a parsed template content

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

## License

```text
Copyright 2016-2019 João Esperancinha (jesperancinha)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## About me

-   [![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=joaofilipesabinoesperancinha&color=informational)](http://joaofilipesabinoesperancinha.nl)

-   [![Twitter Follow](https://img.shields.io/twitter/follow/jofisaes.svg?label=%40jofisaes&style=social)](https://twitter.com/intent/follow?screen_name=jofisaes)

-   [![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=jesperancinha&style=social)](https://github.com/jesperancinha)

-   Free Code Camp [jofisaes](https://www.freecodecamp.org/jofisaes)

-   Hackerrank [jofisaes](https://www.hackerrank.com/jofisaes)

-   Acclaim Badges [joao-esperancinha](https://www.youracclaim.com/users/joao-esperancinha/badges)

-   Projects:

    -   [![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=Time%20Disruption%20Studios&color=informational)](http://tds.joaofilipesabinoesperancinha.nl/)
    -   [![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=Image%20Train%20Filters&color=informational)](http://itf.joaofilipesabinoesperancinha.nl/)
    -   [![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=MancalaJE&color=informational)](http://mancalaje.joaofilipesabinoesperancinha.nl/)
    -   [![Generic badge](https://img.shields.io/static/v1.svg?label=Google%20Apps&message=Joao+Filipe+Sabino+Esperancinha&color=informational)](https://play.google.com/store/apps/developer?id=Joao+Filipe+Sabino+Esperancinha)

-   Releases:
    -   itf-chartizate-android:   
        [![Maven Central](https://img.shields.io/maven-central/v/org.jesperancinha.itf/itf-chartizate-android)](https://search.maven.org/search?q=a:itf-chartizate-android) 
        [![Download](https://api.bintray.com/packages/jesperancinha/maven/itf-chartizate-android/images/download.svg)](https://bintray.com/jesperancinha/maven/itf-chartizate-android/_latestVersion)

    -   itf-chartizate-java:   
        [![Maven Central](https://img.shields.io/maven-central/v/org.jesperancinha.itf/itf-chartizate)](https://search.maven.org/search?q=a:itf-chartizate)

    -   itf-chartizate-api:  
        [![Maven Central](https://img.shields.io/maven-central/v/org.jesperancinha.itf/itf-chartizate-api)](https://search.maven.org/search?q=a:itf-chartizate-api)
