# Markdowner Release notes

## Release 4.0.0 - 2023/01/10 (to be released..)

- Kotlin Version

## Release 3.0.7 - 2022/02/03

-  Sort badges List. Order by Badges Count / Title parse (if any) / Title Project

## Release 3.0.6 - 2022/01/13

-   All Icons Upgrade
-   Spring Boot upgrade to 2.6.2

## Release 3.0.5 - 2021/12/12

-   Spring Boot upgrade to 2.6.1

## Release 3.0.4 - 2021/10/31

-   Spring Boot upgrade to 2.5.5
-   Emoji handling for badges

## Release 3.0.3 - 2021/08/26

-   Link content filter for badges

> Delay because of  [nexus-staging-maven-plugin not working with java 16](https://issues.sonatype.org/browse/NEXUS-27902)
> Not released under JDK 16

## Release 3.0.2 - 2021/06/04

-   Accepts @ in the URL path

## Release 3.0.1 - 2021/05/24

-   Adds title as an optional parameter

## Release 3.0.0 - 2021/05/15

-   Breaking changes
-   Configurable report types. Enum is not necessary anymore

## Release 2.0.3 - 2021/05/14

-   Badge Parsing
-   Creates Badge maps and listings for build, content, coverage, info and quality

## Release 2.0.2 - 2020/04/05

-   Paragraph parser (supports inner paragraphs #).
-   Read of .md content stream with (#) paragraphs removed (this one does consider whole paragraphs and the tree).
-   Read of a complete .md content stream or text auto-creation according to filter chain, determined by project type and its automated packaging system
-   Merge of a readme file to a parsed template content
