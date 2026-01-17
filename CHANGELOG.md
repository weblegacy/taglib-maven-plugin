# Change-Log

## 3.0.0 / YYYY-MM-DD

* Bump `maven-jxr-plugin` from 3.3.2 to 3.6.0
* Bump `maven-javadoc-plugin` from 3.6.3 to 3.12.0
* Bump `maven-jar-plugin` from 3.3.0 to 3.5.0
* Bump `maven-install-plugin` from 3.1.1 to 3.1.4
* Bump `maven-gpg-plugin` from 3.1.0 to 3.2.8
* Bump `maven-enforcer-plugin` from 3.4.1 to 3.6.2
* Bump `maven-deploy-plugin` from 3.1.1 to 3.1.4
* Bump `maven-dependency-plugin` from 3.6.1 to 3.9.0 with config-adaptions, report-optimization and more analysis
* Bump `maven-compiler-plugin` from 3.12.1 to 3.14.1 with some config-adaptions
* Bump `maven-clean-plugin` from 3.3.2 to 3.5.0
* Bump `checkstyle` from 12.3.0 to 13.0.0 and update `checkstyle.xml`
* Bump `maven-assembly-plugin` from 3.6.0 to 3.8.0
* Bump `maven-antrun-plugin` from 3.1.0 to 3.2.0
* Set `prerequisites` in POM for maven-plugin
* Better config for `license-maven-plugin`
* Update Copyright to 2026
* Correct old issue-links to `sourceForge`
* Optimize and add check for javax- and jakarta-namespace
* Remove unused methods
* Bump `maven-fluido-skin` from 1.12.0 to 2.1.0 and move version to POM
* Bump `maven-site-plugin` from 3.12.1 to 3.21.0
* Bump `maven-changes-plugin` from 2.12.1 to 3.0.0-M3 with adaptations
* Resolve checkstyle-messages
* New version of `checksytle.xml`
* Bump `checkstyle` from 10.13.0 to 12.3.0
* Bump `maven-checkstyle-plugin` from 3.3.1 to 3.6.0
* Use JDK11-toArray
* Update Copyright to 2025
* Bump `tlddoc` from 1.4 to 2.0.0-SNAPSHOT
* Change minimum Java-Version to JDK11
* Tipfix message
* Resolve all CPD and PMD messages
* Reformat sources and resolve all checkstyle messages
* Rewrite and reformat testcases
* Use own `checkstyle.xml` (from tlddoc)
* Add missing `maven-resolver-api`
* Rename POM-property `encoding` to `sourceEncoding`
* Set minimum MAVEN-Plugin-Version from 3.2.5 to 3.8.1
* Correct testcases because downgraded `doxia`- and `maven-reporting`-versions
* Replace `commons-logging` with `jcl-over-slf4j`
* Set version of `commons-digester` to 2.1
* Set version of `commons-chain` to 1.2
* Exclude dependency `plexus-container-default`
* Add missing `org.eclipse.sisu.plexus`
* Add missing `slf4j-api`-dependency and bump from 1.7.5 to 1.7.36
* Add missing test-dependencies for artifact-http-transfer
* Change `maven-compat`-scope from `provided` to `test`
* Change `javax.servlet.jsp-api`-scope from `compile` to `test`
* Resort and group dependencies
* Bump `jsp-api` 2.0 to `javax.servlet.jsp-api` 2.3.3
* Bump `plexus-utils` from 3.4.2 to 4.0.0 and add `plexus-xml` 3.0.0
* Bump `plexus-archiver` from 4.4.0 to 4.9.1
* Bump `commons-lang3` from 3.12.0 to 3.14.0
* Add missing `maven-surefire-report-plugin`
* Add missing `maven-plugin-report-plugin`
* Bump `maven-plugin-annotations` from 3.6.4 to 3.11.0
* Downgrade `maven-reporting`-plugins from 4.0.0-M1 to 3.2.0 / 3.1.1
* Downgrade `doxia`-plguins from 2.0.0-M3 to 1.12.0
* Set minimum MAVEN-Plugin-Version from 3.8.6 to 3.2.5
* Add plugin `maven-model`
* Remove unused dependency `servlet-api`
* Move `maven-assembly-plug`-configuration from `pluginManagement` to `plugin`
* Bump `maven-gpg-plugin` from 3.0.1 to 3.1.0
* Set minimum MAVEN-Version from 3.5.4 to 3.6.3
* Bump `maven-fluido-skin` from 1.11.0 to 1.12.0
* Bump `build-helper-maven-plugin` from 3.3.0 to 3.5.0
* Bump `license-maven-plugin` from 4.2.rc3 to 4.3
* Bump `spotbugs-maven-plugin` from 4.7.1.0 to 4.8.3.1
* Bump `maven-surefire-plugin` from 3.0.0-M7 to 3.2.5
* Bump `maven-source-plugin` from 3.2.1 to 3.3.0
* Bump `maven-site-plugin` from 3.12.0 to 3.12.1
* Bump `maven-scm-publish-plugin` from 3.1.0 to 3.2.1
* Bump `maven-resources-plugin` from 3.2.0 to 3.3.1
* Bump `maven-release-plugin` from 3.0.0-M6 to 3.0.1
* Bump `maven-project-info-reports-plugin` from 3.3.0 to 3.5.0
* Bump `maven-pmd-plugin` from 3.17.0 to 3.21.2
* Bump `maven-plugin-plugin` from 3.6.4 to 3.11.0
* Bump `maven-jxr-plugin` from 3.2.0 to 3.3.2
* Bump `maven-javadoc-plugin` from 3.4.0 to 3.6.3
* Bump `maven-jar-plugin` from 3.2.2 to 3.3.0
* Bump `maven-install-plugin` from 3.0.0-M1 to 3.1.1
* Bump `maven-enforcer-plugin` from 3.1.0 to 3.4.1
* Bump `maven-deploy-plugin` from 3.0.0-M2 to 3.1.1
* Bump `maven-dependency-plugin` from 3.3.0 to 3.6.1
* Bump `maven-compiler-plugin` from 3.10.1 to 3.12.1
* Bump `maven-clean-plugin` from 3.2.0 to 3.3.2
* Bump `checkstyle` from 9.3 to 10.13.0
* Bump `maven-checkstyle-plugin` from 3.1.2 to 3.3.1
* Add missing plugin (assembly)
* Add missing plugin (antrun)
* Add `versions-maven-plugin` with reports
* Set version to 3.0.0-SNAPSHOT

## 2.6 / 2022-07-19

* Set Version to 2.6
* Bump `tlddoc` from 1.4-SNAPSHOT to 1.4
* Unescape HTML in tag-description-list when parse-html is on.
* A few embellishments to the Tag-Reference-Report
* Typo in properties
* Use varargs for `MessageFormat.format`
* Lint markdown-files
* Some Report-corrections
* JDK8: Use For-Each-Loops
* Remove debug-option from `maven-javadoc-plugin`
* Add link to documentation in README.md
* Bump `jdom` (new `jdom2`) from 2.0.2 to 2.0.6.1
* Bump `plexus-archiver` from 4.2.7 to 4.4.0
* Bump `spotbugs-maven-plugin` from 4.7.0.0 to 4.7.1.0
* Bump `maven-surefire-plugin` from 3.0.0-M6 to 3.0.0-M7
* Bump `maven-release-plugin` from 3.0.0-M5 to 3.0.0-M6
* Bump `maven-pmd-plugin` from 3.16.0 to 3.17.0
* Revert downgrade `doxia` dependencies from 2.0.0-M2 to 1.11.1 because MPLUGIN-403
* Revert downgrade `maven-reporting-api` and `maven-reporting-impl` from 4.0.0-M1 to 3.1.0
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

## 2.5 / 2022-04-19

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

## 2.4 / 2022-04-08

* Rename LICENSE-file
* Add CHANGELOG.md
* Add README.md
* Update .gitignore
* Clone of maven-taglib-plugin from SourceForge
