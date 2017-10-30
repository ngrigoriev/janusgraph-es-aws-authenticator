janusgraph-es-aws-authenticator
===

AWS ES service authenticator for JanusGraph ES indexing backend based on aws-signing-request-interceptor

A custom authenticator for [JanusGraph](https://github.com/JanusGraph/janusgraph) ES indexing backend created to support AWS' [Elasticsearch Service](https://aws.amazon.com/elasticsearch-service/).

Usage
-----

This authenticator plugs into the custom authenticator chain using the following configuration options:

```
index.<index-name>.elasticsearch.http.auth.type=custom
index.<index-name>.elasticsearch.http.auth.custom.authenticator-class=com.newforma.janusgraph.es.awsauth.AWSV4AuthHttpClientConfigCallback
index.<index-name>.elasticsearch.elasticsearch.http.auth.custom.authenticator-args=
```
This authenticator does not require any arguments. It relies on both AWS default credentials provider chain (http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html) and default region provider chain (http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/java-dg-region-selection.html). So it may be useful in the environments like AWS Elastic Container Service etc.


JanusGraph version note
-----------------------

For now this module is based on an unreleased (and also unmerged) set of changes extending JanusGraph ES REST API support to support the custom authenticators. This README will be updated once this support is available.
