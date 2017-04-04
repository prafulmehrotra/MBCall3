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

@DynamoDBTable(tableName = "mbcall-mobilehub-1771628001-VideoName")

public class VideoNameDO {
    private String _videoName;
    private Double _downvote;
    private Double _upvote;
    private String _videoLink;
    private String _videoLink360;
    private String _videoLink480;
    private String _videoLink720;

    @DynamoDBHashKey(attributeName = "VideoName")
    @DynamoDBAttribute(attributeName = "VideoName")
    public String getVideoName() {
        return _videoName;
    }

    public void setVideoName(final String _videoName) {
        this._videoName = _videoName;
    }
    @DynamoDBAttribute(attributeName = "Downvote")
    public Double getDownvote() {
        return _downvote;
    }

    public void setDownvote(final Double _downvote) {
        this._downvote = _downvote;
    }
    @DynamoDBAttribute(attributeName = "Upvote")
    public Double getUpvote() {
        return _upvote;
    }

    public void setUpvote(final Double _upvote) {
        this._upvote = _upvote;
    }
    @DynamoDBAttribute(attributeName = "VideoLink")
    public String getVideoLink() {
        return _videoLink;
    }

    public void setVideoLink(final String _videoLink) {
        this._videoLink = _videoLink;
    }
    @DynamoDBAttribute(attributeName = "VideoLink-360")
    public String getVideoLink360() {
        return _videoLink360;
    }

    public void setVideoLink360(final String _videoLink360) {
        this._videoLink360 = _videoLink360;
    }
    @DynamoDBAttribute(attributeName = "VideoLink-480")
    public String getVideoLink480() {
        return _videoLink480;
    }

    public void setVideoLink480(final String _videoLink480) {
        this._videoLink480 = _videoLink480;
    }
    @DynamoDBAttribute(attributeName = "VideoLink-720")
    public String getVideoLink720() {
        return _videoLink720;
    }

    public void setVideoLink720(final String _videoLink720) {
        this._videoLink720 = _videoLink720;
    }

}
