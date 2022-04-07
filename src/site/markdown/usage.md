# Reporting

This plugin supports the generation of several reports:

-   tldDoc documentation
-   a tag library validation report
-   a tag reference page for each tld

In order to enable generation of taglib reports you only need to add the
following to your pom.xml:

```xml
  <reporting>
      <plugin>
        <groupId>net.sourceforge.maven-taglib</groupId>
        <artifactId>maven-taglib-plugin</artifactId>
      </plugin>
    </plugins>
  </reporting>
```

See the [taglib:validate](validate-mojo.html),
[taglib:tagreference](tagreference-mojo.html) and
[taglib:taglibdoc](taglibdoc-mojo.html) pages for supported
configuration parameters.

By default the plugin will look for taglibs in the
src/main/resources/META-INF folder (or subfolder), you can change this
and other configuration parameters by adding a configuration tag to the
plugin element:

```xml
  <reporting>
      <plugin>
        <groupId>net.sourceforge.maven-taglib</groupId>
        <artifactId>maven-taglib-plugin</artifactId>
        <configuration>
          <taglib.src.dir>src/main/resources/META-INF</taglib.src.dir>
        </configuration>
      </plugin>
    </plugins>
  </reporting>
```

## Tlddoc

You can configure the plugin in order to produce a jar with taglib
documentation that will be installed to the local repository (and
deployed to remote ones). In order to do so you need to bind the
taglib:taglibdocjar goal to the package lifecycle phase in you pom:

```xml
  <build>
    <plugins>
      <plugin>
        <groupId>net.sourceforge.maven-taglib</groupId>
        <artifactId>maven-taglib-plugin</artifactId>
        <configuration>
          <taglibs>
            <taglib>
              <description>A test tld that contains functions</description>
              <shortName>test</shortName>
              <uri>testuri</uri>
              <outputname>testtaglib</outputname>
              <functionClasses>
                <functionClass>org.apache.commons.lang.StringUtils</functionClass>
              </functionClasses>
              <tagdir>src/tagfiles</tagdir>
            </taglib>
          </taglibs>
        </configuration>
        <executions>
          <execution>
            <goals>
              <goal>taglibdocjar</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
```

## Generating tag library descriptors from tag files

According to the jsp 2.0 specifications, you don't need a tld if you
bundle your custom tag files in the WEB-INF/tags directory of your web
application. However, if you want to package the tag files in order to
reuse them across multiple webapps a tld file is required.

The [taglib:tldgenerate](tldgenerate-mojo.html) goal processes one or
more directories containing tag files and produces a simple tld. Tag
file must be stored in the META-INF/tags directory of the final jar, so
the plugin will look for any directory contained in the
src/main/resources/META-INF/tags source directory by default.

Generated tld files must be stored in the /META-INF directory and they
will inherit the same name as the directory containing the tag files.

```xml
  <build>
    <plugins>
      <plugin>
        <groupId>net.sourceforge.maven-taglib</groupId>
        <artifactId>maven-taglib-plugin</artifactId>
        <executions>
          <execution>
            <goals>
              <goal>tldgenerate</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
```
