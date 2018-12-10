janusgraph-es-aws-authenticator
===

AWS ES service authenticator for JanusGraph ES indexing backend based on aws-signing-request-interceptor

A custom authenticator for [JanusGraph](https://github.com/JanusGraph/janusgraph) ES indexing backend created to support AWS' [Elasticsearch Service](https://aws.amazon.com/elasticsearch-service/).

Building
--------

Run `mvn clean package` and use the ZIP file from target/ directory. This ZIP file will contain all JARs needed to be made available to JanusGraph in its "lib" directory".

Usage
-----

This authenticator plugs into the custom authenticator chain using the following configuration options:

```
index.<index-name>.elasticsearch.http.auth.type=custom
index.<index-name>.elasticsearch.http.auth.custom.authenticator-class=com.newforma.janusgraph.es.awsauth.AWSV4AuthHttpClientConfigCallback
index.<index-name>.elasticsearch.elasticsearch.http.auth.custom.authenticator-args=
```
This authenticator does not require any arguments. It relies on both AWS default credentials provider chain (http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html) and default region provider chain (http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/java-dg-region-selection.html). So it may be useful in the environments like AWS Elastic Container Service etc.


JanusGraph minimal version
--------------------------

The functionality this module depends on is available in JanusGraph as of version 0.3.0.
