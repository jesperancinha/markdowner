# Project Signer

[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Project%20Signer&color=informational)](https://github.com/jesperancinha/project-signer)

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d423415df34f42bf821ae13a078094c9)](https://www.codacy.com/app/jofisaes/project-signer?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jesperancinha/project-signer&amp;utm_campaign=Badge_Grade)

This project serves the purpose of automatically signing all projects from the root

The idea is to use something like:


```text
java -jar project-signer.jar -p <root_path> -l <label_text_to_replace>
```

To summarize, this program will scan all your readme files and standardize name. You may want a generic signature in all your projects and this little runner will do just that!

Stay tuned for more

## Description

This command line runner will complete several boiler plate tasks:

*   Creates all Readme.md files missing - Wherever there is a pom or a package.json file, there should be a Readme.md to explain the purpose of your project. Title will be calculated according to your architecture. The title string is to be extracted from the build files. If multiple build files are present, it will find the name of the project by prioritizing the name of the project in detriment of the artifact name. In order of priority it will look for a title according to the following priority list: Maven, Gradle, SBT and finally NPM.
*   All indicated paragraph which start on a certain tag will be removed - We consider a whole paragraph according to the '#' notation of the markdown.
*   Once all Readme.md files have been created or updated, we will finally add the template signature AS IS to the footer of all found cases.

All tags are case sensitive, which means you do need to add extra tags in case you have issues with word casing.
Once you finally run the above command, please make sure to double check the given signature before committing and pushing to your repos.

## Status

... Under development ...
