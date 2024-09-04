# Add venus configuration and sample to an existing maven project

[Index](index.md)

It is possible to use the `org.fugerit.java:fj-doc-maven-plugin:add` to Venus configuration to an existing maven project.

Just run this command inside the directory where the `pom.xml` is located : 

```shell
mvn org.fugerit.java:fj-doc-maven-plugin:add \
-Dextensions=base,freemarker,mod-fop
```

For additional options see [documentation](https://github.com/fugerit-org/fj-doc/blob/main/fj-doc-maven-plugin/README.md#goal--add).