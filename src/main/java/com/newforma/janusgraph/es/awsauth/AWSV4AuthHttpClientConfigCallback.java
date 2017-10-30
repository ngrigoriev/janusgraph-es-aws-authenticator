package com.newforma.janusgraph.es.awsauth;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.apache.http.HttpRequestInterceptor;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.janusgraph.diskstorage.es.rest.util.RestClientAuthenticatorBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.DefaultAwsRegionProviderChain;
import com.google.common.base.Supplier;

import vc.inreach.aws.request.AWSSigner;
import vc.inreach.aws.request.AWSSigningRequestInterceptor;

/**
 * <p>
 * ES REST HTTP(S) client callback implementing AWS request signing.
 * </p>
 * <p>
 * The signer is based on AWS SDK default provider chain, allowing multiple options for providing
 * the caller credentials. See {@link DefaultAWSCredentialsProviderChain} documentation for the details.
 * </p>
 */
public class AWSV4AuthHttpClientConfigCallback extends RestClientAuthenticatorBase {

    private static final Logger log = LoggerFactory.getLogger(AWSV4AuthHttpClientConfigCallback.class);

    private static final String AWS_SERVICE_NAME = "es";
    private final HttpRequestInterceptor awsSigningInterceptor;

    public AWSV4AuthHttpClientConfigCallback(final String[] args) {
        DefaultAWSCredentialsProviderChain awsCredentialsProvider = new DefaultAWSCredentialsProviderChain();
        final Supplier<LocalDateTime> clock = () -> LocalDateTime.now(ZoneOffset.UTC);

        // using default region provider chain
        // (http://docs.aws.amazon.com/sdk-for-java/v2/developer-guide/java-dg-region-selection.html)
        DefaultAwsRegionProviderChain regionProviderChain = new DefaultAwsRegionProviderChain();
        final String awsRegion = regionProviderChain.getRegion();

        final AWSSigner awsSigner = new AWSSigner(awsCredentialsProvider, awsRegion, AWS_SERVICE_NAME, clock);
        this.awsSigningInterceptor = new AWSSigningRequestInterceptor(awsSigner);
        log.info("Initialized AWSV4AuthHttpClientConfigCallback for region {}", awsRegion);
    }

    @Override
    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
        httpClientBuilder.addInterceptorLast(awsSigningInterceptor);
        return httpClientBuilder;
    }
}
