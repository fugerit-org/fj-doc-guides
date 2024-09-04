# Creates a new project already configured for Venus

[Index](index.md)

It is possible to use the `org.fugerit.java:fj-doc-maven-plugin:init` maven plugin in order initialize a new project, it is as simple as running : 

```shell
mvn org.fugerit.java:fj-doc-maven-plugin:init \
-DgroupId=org.example.doc \
-DartifactId=fugerit-demo
```

A new folder `$artifactId` folder will be created, containing the maven project configured for using Venus.

For additional options see [documentation](https://github.com/fugerit-org/fj-doc/blob/main/fj-doc-maven-plugin/README.md#goal--init).