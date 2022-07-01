# taglib-maven-plugin

Clone of [maven-taglib-plugin](https://sourceforge.net/projects/maven-taglib/) from SourceForge.

Renamed from `maven-taglib-plugin` to `taglib-maven-plugin`

Full [CHANGELOG](CHANGELOG.md)

# Building `taglib-maven-plugin`

## Prerequesits

* Apache Maven 3.5.4\+
* JDK 8\+

## MAVEN-Profiles

* *assembly*
  * Create assemblies for distribution

## Building-Steps

1. Clean full project  
   `mvn clean`
2. Build and test project  
   `mvn verify`
3. Generate documentation  
   `mvn site`
4. Generate source- and javadoc-artifacts and assemblies  
   `mvn package`
5. Generate assemblies  
   `mvn -Passembly package`

## Support runs

* Set version number
  `mvn versions:set -DnewVersion=...`

* Dependency Report
  `mvn -Passembly versions:display-dependency-updates versions:display-plugin-updates versions:display-property-updates`