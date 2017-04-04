package com.amazonaws.mobile;
//
// Copyright 2017 Amazon.com, Inc. or its affiliates (Amazon). All Rights Reserved.
//
// Code generated by AWS Mobile Hub. Amazon gives unlimited permission to 
// copy, distribute and modify it.
//
// Source code generated from template: aws-my-sample-app-android v0.16
//

import android.content.Context;
import android.util.Log;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.mobilehelper.auth.IdentityManager;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.mobile.content.ContentManager;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobile.user.signin.FacebookSignInProvider;
import com.amazonaws.mobilehelper.auth.signin.CognitoUserPoolsSignInProvider;

/**
 * The AWS Mobile Client bootstraps the application to make calls to AWS 
 * services. It creates clients which can be used to call services backing the
 * features you selected in your project.
 */
public class AWSMobileClient {

    private static final String LOG_TAG = AWSMobileClient.class.getSimpleName();

    private static volatile AWSMobileClient instance;

    private ClientConfiguration clientConfiguration;
    private IdentityManager identityManager;
    private AmazonDynamoDBClient dynamoDBClient;
    private DynamoDBMapper dynamoDBMapper;

    /**
     * Build class used to create the AWS mobile client.
     */
    public static class Builder {

        private Context applicationContext;
        private String  cognitoIdentityPoolID;
        private Regions cognitoRegion;
        private ClientConfiguration clientConfiguration;
        private IdentityManager identityManager;

	/**
	 * Constructor.
	 * @param context Android context.
	 */
        public Builder(final Context context) {
            this.applicationContext = context.getApplicationContext();
        };

	/**
	 * Provides the Amazon Cognito Identity Pool ID.
	 * @param cognitoIdentityPoolID identity pool ID
	 * @return builder
	 */
        public Builder withCognitoIdentityPoolID(final String cognitoIdentityPoolID) {
            this.cognitoIdentityPoolID = cognitoIdentityPoolID;
            return this;
        };
        
	/**
	 * Provides the Amazon Cognito service region.
	 * @param cognitoRegion service region
	 * @return builder
	 */
        public Builder withCognitoRegion(final Regions cognitoRegion) {
            this.cognitoRegion = cognitoRegion;
            return this;
        }

        /**
         * Provides the identity manager.
	 * @param identityManager identity manager
	 * @return builder
	 */
        public Builder withIdentityManager(final IdentityManager identityManager) {
            this.identityManager = identityManager;
            return this;
        }

        /**
         * Provides the client configuration
         * @param clientConfiguration client configuration
         * @return builder
         */
        public Builder withClientConfiguration(final ClientConfiguration clientConfiguration) {
            this.clientConfiguration = clientConfiguration;
            return this;
        }

	/**
	 * Creates the AWS mobile client instance and initializes it.
	 * @return AWS mobile client
	 */
        public AWSMobileClient build() {
            return
                new AWSMobileClient(applicationContext,
                                    cognitoIdentityPoolID,
                                    cognitoRegion,
                                    identityManager,
                                    clientConfiguration);
        }
    }

    private AWSMobileClient(final Context context,
                            final String  cognitoIdentityPoolID,
                            final Regions cognitoRegion,
                            final IdentityManager identityManager,
                            final ClientConfiguration clientConfiguration) {

        this.identityManager = identityManager;
        this.clientConfiguration = clientConfiguration;

        this.dynamoDBClient = new AmazonDynamoDBClient(identityManager.getCredentialsProvider(), clientConfiguration);
        this.dynamoDBClient.setRegion(Region.getRegion(AWSConfiguration.AMAZON_DYNAMODB_REGION));
        this.dynamoDBMapper = new DynamoDBMapper(dynamoDBClient);
    }

    /**
     * Sets the singleton instance of the AWS mobile client.
     * @param client client instance
     */
    public static void setDefaultMobileClient(AWSMobileClient client) {
        instance = client;
    }

    /**
     * Gets the default singleton instance of the AWS mobile client.
     * @return client
     */
    public static AWSMobileClient defaultMobileClient() {
        return instance;
    }

    /**
     * Gets the identity manager.
     * @return identity manager
     */
    public IdentityManager getIdentityManager() {
        return this.identityManager;
    }


    private static void addSignInProviders(final Context context, final IdentityManager identityManager) {
        // Add Facebook as an Identity Provider.
        identityManager.addIdentityProvider(FacebookSignInProvider.class);

        // Add Cognito User Pools as an Identity Provider.
        identityManager.addIdentityProvider(CognitoUserPoolsSignInProvider.class);
    }

    /**
     * Creates and initialize the default AWSMobileClient if it doesn't already
     * exist using configuration constants from {@link AWSConfiguration}.
     *
     * @param context an application context.
     */
    public static void initializeMobileClientIfNecessary(final Context context) {
        if (AWSMobileClient.defaultMobileClient() == null) {
            Log.d(LOG_TAG, "Initializing AWS Mobile Client...");
            final ClientConfiguration clientConfiguration = new ClientConfiguration();
            clientConfiguration.setUserAgent(AWSConfiguration.AWS_MOBILEHUB_USER_AGENT);
            final IdentityManager identityManager = new IdentityManager(context, clientConfiguration,
                AWSConfiguration.getAWSMobileHelperConfiguration());

            addSignInProviders(context, identityManager);

            final AWSMobileClient awsClient =
                new AWSMobileClient.Builder(context)
                    .withCognitoRegion(AWSConfiguration.AMAZON_COGNITO_REGION)
                    .withCognitoIdentityPoolID(AWSConfiguration.AMAZON_COGNITO_IDENTITY_POOL_ID)
                    .withIdentityManager(identityManager)
                    .withClientConfiguration(clientConfiguration)
                    .build();

            AWSMobileClient.setDefaultMobileClient(awsClient);
        }
        Log.d(LOG_TAG, "AWS Mobile Client is OK");
    }

    /**
     * Gets the DynamoDB Client, which allows accessing Amazon DynamoDB tables.
     * @return the DynamoDB client instance.
     */
    public AmazonDynamoDBClient getDynamoDBClient() {
        return dynamoDBClient;
    }

    /**
     * Gets the Dynamo DB Object Mapper, which allows accessing DynamoDB tables using annotated
     * data object classes to represent your data using POJOs (Plain Old Java Objects).
     * @return the DynamoDB Object Mapper instance.
     */
    public DynamoDBMapper getDynamoDBMapper() {
        return dynamoDBMapper;
    }

    /**
     * Creates the default Content Manager, which allows files to be downloaded from
     * the Amazon S3 (Simple Storage Service) bucket associated with the App Content
     * Delivery feature (optionally through Amazon CloudFront if Multi-Region CDN option
     * was selected).
     * @param context context.
     * @param resultHandler handles the resulting ContentManager instance
     */
    public void createDefaultContentManager(final Context context,
                                            final ContentManager.BuilderResultHandler resultHandler) {
        new ContentManager.Builder()
            .withContext(context)
            .withIdentityManager(identityManager)
            .withS3Bucket(AWSConfiguration.AMAZON_CONTENT_DELIVERY_S3_BUCKET)
            .withLocalBasePath(context.getFilesDir().getAbsolutePath())
            .withRegion(AWSConfiguration.AMAZON_CONTENT_DELIVERY_S3_REGION)
            .withCloudFrontDomainName(AWSConfiguration.AMAZON_CLOUD_FRONT_DISTRIBUTION_DOMAIN)
            .withClientConfiguration(clientConfiguration)
            .build(resultHandler);
    }

}
