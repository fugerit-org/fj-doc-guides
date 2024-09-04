# Configuration migration from DocProcessConfig to FreemarkerDocProcessConfig

[Index](index.md)

This guide is needed only if the old configurations files `doc-handler-facade.xml` and/or the `doc-process-config.xml` are being used.

If you are already using the new [freemarker-doc-process-1-0.xsd](https://github.com/fugerit-org/fj-doc/blob/main/fj-doc-freemarker/src/main/resources/config_fm_xsd/freemarker-doc-process-1-0.xsd) you do not need to read more.

At the beginning, Venus was using the [DocProcessConfig](https://github.com/fugerit-org/fj-doc/blob/c458ba4e54477a6db650dcc9f71fb2dab4c63bef/fj-doc-base/src/main/java/org/fugerit/java/doc/base/process/DocProcessConfig.java) facade to handle the generation pipeline.

Now it is strongly recommended to use the new [FreemarkerDocProcessConfig](https://github.com/fugerit-org/fj-doc/blob/main/fj-doc-freemarker/src/main/java/org/fugerit/java/doc/freemarker/process/FreemarkerDocProcessConfig.java) facade.

It substitutes both the `doc-handler-facade.xml` and the `doc-process-config.xml`.

The needed steps depends on the specific project configuration, but can be summarized in : 

## 1. Convert the `doc-process-config.xml` and `doc-handler-facade.xml` in `freemarker-doc-process.xml`

You can use the [Doc Configuration Convert](https://docs.fugerit.org/fj-doc-playground/home) function from the online playground.

For instance starting from : 

```xml
<doc-process>

   <chain id="test-chain">
   	<step id="step-01" defaultBehaviour="CONTINUE"
   		description="Test for coverage" 
   		type="test.org.fugerit.java.doc.base.coverage.ProcessStepCoverage"
   		param01="TEST">
   		<properties xmlPath="coverage/xml/default_doc_alt.xml"/>
   	</step>
   </chain>

</doc-process>
```

```xml
<doc-handler-config user-catalog="default-complete">

	<factory id="default-complete">
		<data id="md-ext" info="md" type="org.fugerit.java.doc.base.typehandler.markdown.SimpleMarkdownExtTypeHandler" />
	</factory>
	
</doc-handler-config>
```

The resulting `freemarker-doc-process.xml` should be : 

```xml
<?xml version="1.0" encoding="utf-8"?>
<freemarker-doc-process-config
xmlns="https://freemarkerdocprocess.fugerit.org"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="https://freemarkerdocprocess.fugerit.org https://www.fugerit.org/data/java/doc/xsd/freemarker-doc-process-1-0.xsd" >

	<docHandlerConfig>
		<!-- Type handler for markdown format -->
		<docHandler id="md-ext" info="md" type="org.fugerit.java.doc.base.typehandler.markdown.SimpleMarkdownExtTypeHandler" />
        
	</docHandlerConfig>
    
	<docChain id="test-chain">
		<chainStep stepType="test.org.fugerit.java.doc.base.coverage.ProcessStepCoverage">
			<!-- custom step, additional configuration may be needed -->
		</chainStep>
	</docChain>
    
</freemarker-doc-process-config>
```

## 2. Create your FreemarkerDocProcessConfig instance

```java
FreemarkerDocProcessConfig config = FreemarkerDocProcessConfigFacade.loadConfigSafe("cl://path/to/freemarker-doc-process.xml" );
```
## 3. Use new fullProcess() functions

```java
String chainId = "test-chain";
DocProcessContext context = DocProcessContext.newContext( ... );
String type = "md";
FreemarkerDocProcessConfig config = FreemarkerDocProcessConfigFacade.loadConfigSafe("cl://path/to/freemarker-doc-process.xml" );
config.fullProcess( chainId, context, type, outputStream );
```
## 4. Any needed clean up

Remove your old `doc-process-config.xml` configuration and any obsolete file and method.
