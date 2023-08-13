# Venus Guidelines : From XML to JSON or YAML source


[Venus Guides index](../../../README.md)

## Version : 1 (2023-08-13)

## Description : Information on how to use JSON or YAML as source.

Refer to [Venus DOC Format](doc_format_summary.md) for information on how to write the source XML Document.

It is possible to use JSON or YAML as source. It is just needed to follow some conversion rules.

As the inherent differences between XML and JSON/YAML the conversion rules are as follow : 
* xml meta information are translated as top level property (xmlns etc.)
* Every JSON/YAML object contains the information of a XML tag
* "_t" special property contains the tag name (for example "_t" : "metadata")
* "_v" special property contains the text content of an element
* "_e" special property contains a list of child elements
* any other xml attribute is mapped as a JSON/YAML property (for example "name" : "margins")

```
{
	"xmlns" : "http://javacoredoc.fugerit.org",
	"xsi:schemaLocation" : "http://javacoredoc.fugerit.org https://www.fugerit.org/data/java/doc/xsd/doc-2-0.xsd",
	"xmlns:xsi" : "http://www.w3.org/2001/XMLSchema-instance",
	"_t" : "doc",
	"_e" : [ {
		"_t" : "metadata",
		"_e" : [ {
			"name" : "margins",
			"_t" : "info",
			"_v" : "10;10;10;30"
		}, {
			"name" : "doc-title",
			"_t" : "info",
			"_v" : "Hello World"
		}, {
			"name" : "doc-author",
			"_t" : "info",
			"_v" : "fugerit79"
		}, {
			"name" : "doc-language",
			"_t" : "info",
			"_v" : "en"
		} ]
	}, {
		"_t" : "body",
		"_e" : [ {
			"_t" : "para",
			"_v" : "Hello World!"
		} ]
	} ]
}
```

NOTES :
1. As it is possible to directly convert JSON and YAML, rules for YAML are the same as for JSON format.
2. All XML comments are ignored