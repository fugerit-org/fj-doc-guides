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

## Some use cases

### Select the needed Venus Extensions

For instance, if PDF, CSV and XLSX output are needed : 

`-Dextensions=base,freemarker,mod-fop,mod-csv,mod-poi`

Here is the complete command : 

```shell
mvn org.fugerit.java:fj-doc-maven-plugin:init \
-DgroupId=org.example.doc \
-DartifactId=fugerit-demo \
-Dextensions=base,freemarker,mod-fop,mod-opencsv,mod-poi
```

### Flavoured project

With the 'flavour' parameter, it is possible to create specific project type (among the supported ones).

I.e. to create a quarkus 3 project : 

`-Dflavour=quarkus-3`

Here is the full command : 

```shell
mvn org.fugerit.java:fj-doc-maven-plugin:init \
-DgroupId=org.example.doc \
-DartifactId=fugerit-demo-quarkus-3 \
-Dflavour=quarkus-3 \
-Dextensions=base,freemarker,mod-fop,mod-opencsv,mod-poi
```

See [fj-doc-maven-plugin init goal documentation for further info](https://github.com/fugerit-org/fj-doc/blob/main/fj-doc-maven-plugin/README.md#init-fugerit-venus-parameters)


