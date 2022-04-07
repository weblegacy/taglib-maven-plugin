
The Taglib Plug-in is a Maven plugin for jsp tag libraries developers.
It eases the generation, documentation and testing of jsp tag libraries.


## Basic configuration


Adds the following to your pom.xml to enable generation of taglib
reports:

```xml
  <reporting>
      <plugin>
        <groupId>net.sourceforge.maven-taglib</groupId>
        <artifactId>maven-taglib-plugin</artifactId>
        <version>2.5</version>
      </plugin>
    </plugins>
  </reporting>
```

This will trigger the generation of:

-   tldDoc documentation
-   a tag library validation report
-   a tag reference page

For advanced usage see the [usage](usage.html) section.

