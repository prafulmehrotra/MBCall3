package com.amazonaws.models.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "mbcall-mobilehub-1771628001-UserVote")

public class UserVoteDO {
    private String _userId;
    private String _videoName;
    private Boolean _upvote;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBRangeKey(attributeName = "VideoName")
    @DynamoDBAttribute(attributeName = "VideoName")
    public String getVideoName() {
        return _videoName;
    }

    public void setVideoName(final String _videoName) {
        this._videoName = _videoName;
    }
    @DynamoDBAttribute(attributeName = "Upvote")
    public Boolean getUpvote() {
        return _upvote;
    }

    public void setUpvote(final Boolean _upvote) {
        this._upvote = _upvote;
    }

}
