# 2.6 / YYYY-MM-DD

* Workaround MJAVADOC-700
* Generate JavaDoc/JXR-Report without duplicate execution
* Add/Correct JavaDocs to remove JavaDoc-Generation-Errors and Warnings
* Bump `maven-site-plugin` from 3.11.0 to 3.12.0
* Bump `maven-javadoc-plugin` from 3.3.2 to 3.4.0
* Corr: Remove `closeReport` in `TagreferenceMojo`
* Some Code-optimization in `TagreferenceRenderer`
* Set version to 2.6-SNAPSHOT

# 2.5 / 2022-04-19

* Rename from `maven-taglib-plugin` to `taglib-maven-plugin`
* Set version to 2.5
* Generate bin- and src-assemblies
* Generate javadoc- and source-jar
* Bump maven-shared-utils from 3.2.0 to 3.3.4
* Remove unnecessary cast
* Set plugin-extractors to only java-annotations
* Add test-dependencies slf4j-simple
* Generate mojo-descriptor
* Upgrade deprecated plexus-plugins
* maven-plugin-dependencies-version thru property
* Remove uncommented dependencies
* Correct JUnit-tests
* Remove duplicate code
* Upgrade site-documentation
* Migrate from Plugin-JavaDoc to Plugin-Annotations
* Set scope of maven-plugin-builder dependencies to provided
* Upgrade dependencies
* Resolve java-warnings
* Remove unused imports
* Resolve deprecated methods
* Add generics
* Upgrade to JDK 8
* Upgrade MAVEN-Plugins
* Upgrade `license-maven-plugin` and upgrade license-text (no license-change)
* Remove retired `maven-eclipse-plugin`
* Add new POM-Properties and check maven-version thru enforcer-plugin
* Update to latest commit [r218 - MAVENTAGLIB-18](https://sourceforge.net/p/maven-taglib/code/218/)

# 2.4 / 2022-04-08

* Rename LICENSE-file
* Add CHANGELOG.md
* Add README.md
* Update .gitignore
* Clone of maven-taglib-plugin from SourceForge