# taglib-maven-plugin

Clone of [maven-taglib-plugin](https://sourceforge.net/projects/maven-taglib/) from SourceForge.

Renamed from `maven-taglib-plugin` to `taglib-maven-plugin`

Full [CHANGELOG](CHANGELOG.md)

For documentation see [https://weblegacy.github.io/taglib-maven-plugin](https://weblegacy.github.io/taglib-maven-plugin)

## Building `taglib-maven-plugin`

### Prerequesits

* Apache Maven 3.5.4\+
* JDK 8\+

### MAVEN-Profiles

* *assembly*
  * Create assemblies for distribution
* *release*
  * Signs all of the project's attached artifacts with GnuPG
  * Add JavaDoc-artifact for deployment to central-repo
  * Add Source-artifact for deployment to central-repo

### Building-Steps

1. Clean full project  
   `mvn clean`
2. Build and test project  
   `mvn verify`
3. Generate documentation  
   `mvn site`
4. Publish site-documentation  
   `mvn site-deploy`
5. Generate source- and javadoc-artifacts  
   `mvn package`
6. Generate assemblies  
   `mvn -Passembly package`
7. Deploy all artifacts to `Central-Repo`  
   * `mvn clean deploy` for SNAPSHOTs
   * `mvn -Prelease clean deploy` for releases

### Support runs

* Set version number
  `mvn versions:set -DnewVersion=...`
* Dependency Report
  `mvn -Passembly,release versions:display-dependency-updates versions:display-plugin-updates versions:display-property-updates`
