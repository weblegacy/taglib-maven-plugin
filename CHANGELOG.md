# 2.6 / YYYY-MM-DD

* Bump `maven-enforcer-plugin` from 3.0.0 to 3.1.0
* Bump `maven-plugins` from 3.8.5 to 3.8.6
* Set reporting-output-encoding to UTF-8
* Upgrade to `doxia.xhtml5`
* Correct FAQs
* Bump `maven-assembly-plugin` from 3.3.0 to 3.4.1
* Downgrade `maven-reporting-api` and `maven-reporting-impl` from 4.0.0-M1 to 3.1.0
* Downgrade `doxia` dependencies from 2.0.0-M2 to 1.11.1 because MPLUGIN-403
* Add more infos to `README.md`
* Add `lifecycle-mapping` for eclipse
* Correct `sourcepath` of `maven-javadoc-plugin`
* Normalize all the line endings
* Correct some SpotBugs
* Remove unused resources
* Add `compareTo`, `equals` and `hashCode` to checker-classes
* Downgrade `maven-site-plugin` from 4.0.0-M1 to 3.12.0 because MPLUGIN-403
* Correct site-distribution
* Change distribution to central-repo
* Correct function-class in `taglib-maven-plugin-test-project3`
* Correct group-id to `io.github.weblegacy` in tests
* Add new profile `release` to deploy to central-repo
* Update copyright
* Update configs for `license-maven-plugin` and add `build-helper-maven-plugin` to get current year
* Add encoding-property
* Add new profile `assembly` to generate assemblies
* Suppress timestamp at javadoc-files
* Change group-id from `net.sourceforge.maven-taglib` to `io.github.weblegacy`
* Use new `io.github.weblegacy:tlddoc` dependency
* Reformat POM
* Remove all compiler-warnings
* Remove unnecessary setters in `TagreferenceMojo`
* Remove `PMD`-Warnings and exclude generated classes form PMD-report
* Use parameter `locale` for generating localized reports
* Add/remove dependencies because `Dependency Analysis`
* Upgrade from `commons-lang` to `commons-lang3`
* Update `changes.xml`
* Update `site.xml`
* Add `maven-plugin-plugin` to reporting-section
* Reformat `xsl`-files
* Remove double used template `info` at `tld1_1-tld1_2.xsl`
* Small POM-changes: license-name, remove property, configurations for javadoc- and compiler-plugin
* Add plugins/reports: checkstyle, dependency, gpg, pmd, release, scm-publish, spotbugs
* Set new home-url
* Bump `tlddoc` from 1.3 to 1.4-SNAPSHOT
* Bump `maven-reporting-impl` from 3.1.0 to 4.0.0-M1
* Bump `maven-project-info-reports-plugin` from 3.2.2 to 3.3.0
* Bump `maven-site-plugin` from 3.12.0 to 4.0.0-M1
* Bump `license-maven-plugin` from 4.1 to 4.2.rc3
* Reformat POM
* Use current `web-jsptaglibrary*.dtd` from jboss-project
* Correct description- and example-output with HTML-Tags
* Adaption for JDK 9+
* Add links for JavaDoc
* Workaround Test-JavaDoc-Report missing dependencies
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