//
// Copyright 2017 Amazon.com, Inc. or its affiliates (Amazon). All Rights Reserved.
//
// Code generated by AWS Mobile Hub. Amazon gives unlimited permission to 
// copy, distribute and modify it.
//
// Source code generated from template: aws-my-sample-app-android v0.16
//
package com.amazonaws.mobile;

import com.amazonaws.mobilehelper.config.AWSMobileHelperConfiguration;
import com.amazonaws.regions.Regions;

/**
 * This class defines constants for the developer's resource
 * identifiers and API keys. This configuration should not
 * be shared or posted to any public source code repository.
 */
public class AWSConfiguration {
    // AWS MobileHub user agent string
    public static final String AWS_MOBILEHUB_USER_AGENT =
        "MobileHub 07abd4b4-c641-4a12-ac39-9fbf240f030e aws-my-sample-app-android-v0.16";
    // AMAZON COGNITO
    public static final Regions AMAZON_COGNITO_REGION =
      Regions.fromName("us-east-1");
    public static final String  AMAZON_COGNITO_IDENTITY_POOL_ID =
        "us-east-1:bac67b0e-d748-459a-88e9-000169a11db3";
    public static final String AMAZON_CONTENT_DELIVERY_S3_BUCKET =
        "mbcall-contentdelivery-mobilehub-1771628001";
    public static final Regions AMAZON_CONTENT_DELIVERY_S3_REGION =
       Regions.fromName("us-east-1");
    public static final String AMAZON_CLOUD_FRONT_DISTRIBUTION_DOMAIN =
        "d1wlt1cimyx509.cloudfront.net";
    public static final Regions AMAZON_DYNAMODB_REGION =
       Regions.fromName("us-east-1");
    public static final String AMAZON_COGNITO_USER_POOL_ID =
        "us-east-1_KBSTL3U0Q";
    public static final String AMAZON_COGNITO_USER_POOL_CLIENT_ID =
        "5es875hjj71m6movsc7elqkm66";
    public static final String AMAZON_COGNITO_USER_POOL_CLIENT_SECRET =
        "1rrfimb8avng1c466k5q9gdce216cg50t97mkc9uhu4qgt63p6rp";

    private static final AWSMobileHelperConfiguration helperConfiguration = new AWSMobileHelperConfiguration.Builder()
        .withCognitoRegion(AMAZON_COGNITO_REGION)
        .withCognitoIdentityPoolId(AMAZON_COGNITO_IDENTITY_POOL_ID)
        .withCognitoUserPool(AMAZON_COGNITO_USER_POOL_ID,
            AMAZON_COGNITO_USER_POOL_CLIENT_ID, AMAZON_COGNITO_USER_POOL_CLIENT_SECRET)
        .build();
    /**
     * @return the configuration for AWSKit.
     */
    public static AWSMobileHelperConfiguration getAWSMobileHelperConfiguration() {
        return helperConfiguration;
    }
}