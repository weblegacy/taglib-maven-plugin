# maven-taglib-plugin

Clone of [maven-taglib-plugin](https://sourceforge.net/projects/maven-taglib/) from SourceForge.

Full [CHANGELOG](CHANGELOG.md)

# Building `maven-taglib-plugin`

## Prerequesits

* Apache Maven 3\+
* JDK 8\+

## Building-Steps

1. Clean full project
    `mvn clean`
2. Build and test project
    `mvn verify`
3. Generate documentation
    `mvn site`
4. Generate source- and javadoc-artifacts and assemblies
    `mvn package`

## Support runs

* Set version number
    `mvn versions:set -DnewVersion=...`

* Dependency Report
    `mvn versions:display-dependency-updates versions:display-plugin-updates versions:display-property-updates`