# Project Signer

[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Project%20Signer&color=informational)](https://github.com/jesperancinha/project-signer)

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d423415df34f42bf821ae13a078094c9)](https://www.codacy.com/app/jofisaes/project-signer?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jesperancinha/project-signer&amp;utm_campaign=Badge_Grade)

## Description

This project serves the purpose of automatically signing all projects from the root

The idea is to use something like:

-   Short switches
```text
java -jar project-signer.jar -t <template> <tag1>...<tagn> -d <root_path> -ne <no empty>
```
-   Long switches
```text
java -jar project-signer.jar --template-location <template> <tag1>...<tagn> --root-directory <root_path> --no-empty <no empty>
```

To summarize, this program will scan all your readme files and standardize name. You may want a generic signature in all your projects and this little runner will do just that!

This command line runner will complete several boiler plate tasks:

*   Creates all Readme.md files missing - Wherever there is a pom or a package.json file, there should be a Readme.md to explain the purpose of your project. Title will be calculated according to your architecture. The title string is to be extracted from the build files. If multiple build files are present, it will find the name of the project by prioritizing the name of the project in detriment of the artifact name. In order of priority it will look for a title according to the following priority list: Maven, Gradle, SBT and finally NPM.
*   All indicated paragraph which start on a certain tag will be removed - We consider a whole paragraph according to the '#' notation of the markdown.
*   Once all Readme.md files have been created or updated, we will finally add the template signature AS IS to the footer of all found cases.

All tags are case sensitive, which means you do need to add extra tags in case you have issues with word casing.
Once you finally run the above command, please make sure to double check the given signature before committing and pushing to your repos.

### Usage in an IDE

The most common form of using this plugin would be something like this as command line parameters:

```text
-t "../project-signer-templates/Readme.md" License "About Me" -d ../../ 
```

Remember to user profiles dev/prod. No difference at the moment between the different profiles.

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


