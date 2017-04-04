package com.mysampleapp.demo.nosql;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amazonaws.AmazonClientException;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.models.nosql.VideoNameDO;
import com.amazonaws.models.nosql.UserVoteDO;


import java.util.Set;

public class DemoNoSQLVideoNameResult implements DemoNoSQLResult {
    private static final int KEY_TEXT_COLOR = 0xFF333333;
    private final VideoNameDO result;

    DemoNoSQLVideoNameResult(final VideoNameDO result) {
        this.result = result;
    }
    @Override
    public void updateItem() {
        final DynamoDBMapper mapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
        final double originalValue = result.getUpvote();
        UserVoteDO user_entry = mapper.load(UserVoteDO.class, AWSMobileClient.defaultMobileClient().getIdentityManager().getCachedUserID(), result.getVideoName());
        if(user_entry != null) {
            boolean vote = user_entry.getUpvote();
            if(vote == false){
                user_entry.setUpvote(true);
                result.setDownvote(result.getDownvote() - 1);
                result.setUpvote(originalValue + 1);
                try {
                    mapper.save(user_entry);
                    mapper.save(result);
                } catch (final AmazonClientException ex) {
                    result.setUpvote(originalValue);
                    throw ex;
                }


            }

        }
        else {
            result.setUpvote(originalValue + 1);
            user_entry = new UserVoteDO();
            user_entry.setUserId(AWSMobileClient.defaultMobileClient().getIdentityManager().getCachedUserID());
            user_entry.setVideoName(result.getVideoName());
            user_entry.setUpvote(true);
            try {
                mapper.save(result);
                mapper.save(user_entry);
            } catch (final AmazonClientException ex) {
                // Restore original data if save fails, and re-throw.
                result.setUpvote(originalValue);
                throw ex;
            }

        }

    }

    @Override
    public void deleteItem() {
        final DynamoDBMapper mapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
        final double originalValue = result.getDownvote();
        UserVoteDO user_entry = mapper.load(UserVoteDO.class, AWSMobileClient.defaultMobileClient().getIdentityManager().getCachedUserID(), result.getVideoName());
        if(user_entry != null) {
            boolean vote = user_entry.getUpvote();
            if(vote == true){
                user_entry.setUpvote(false);
                result.setUpvote(result.getUpvote() - 1);
                result.setDownvote(originalValue + 1);
                try {
                    mapper.save(user_entry);
                    mapper.save(result);
                } catch (final AmazonClientException ex) {
                    result.setDownvote(originalValue);
                    throw ex;
                }


            }

        }
        else {
            result.setDownvote(originalValue + 1);
            user_entry = new UserVoteDO();
            user_entry.setUserId(AWSMobileClient.defaultMobileClient().getIdentityManager().getCachedUserID());
            user_entry.setVideoName(result.getVideoName());
            user_entry.setUpvote(false);
            try {
                mapper.save(result);
                mapper.save(user_entry);
            } catch (final AmazonClientException ex) {
                // Restore original data if save fails, and re-throw.
                result.setDownvote(originalValue);
                throw ex;
            }

        }

    }

    private void setKeyTextViewStyle(final TextView textView) {
        textView.setTextColor(KEY_TEXT_COLOR);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(dp(5), dp(2), dp(5), 0);
        textView.setLayoutParams(layoutParams);
    }

    /**
     * @param dp number of design pixels.
     * @return number of pixels corresponding to the desired design pixels.
     */
    private int dp(int dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
    private void setValueTextViewStyle(final TextView textView) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(dp(15), 0, dp(15), dp(2));
        textView.setLayoutParams(layoutParams);
    }

    private void setKeyAndValueTextViewStyles(final TextView keyTextView, final TextView valueTextView) {
        setKeyTextViewStyle(keyTextView);
        setValueTextViewStyle(valueTextView);
    }

    private static String bytesToHexString(byte[] bytes) {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.format("%02X", bytes[0]));
        for(int index = 1; index < bytes.length; index++) {
            builder.append(String.format(" %02X", bytes[index]));
        }
        return builder.toString();
    }

    private static String byteSetsToHexStrings(Set<byte[]> bytesSet) {
        final StringBuilder builder = new StringBuilder();
        int index = 0;
        for (byte[] bytes : bytesSet) {
            builder.append(String.format("%d: ", ++index));
            builder.append(bytesToHexString(bytes));
            if (index < bytesSet.size()) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    @Override
    public View getView(final Context context, final View convertView, int position) {
        final LinearLayout layout;
        final TextView resultNumberTextView;
        final TextView videoNameKeyTextView;
        final TextView videoNameValueTextView;
        final TextView downvoteKeyTextView;
        final TextView downvoteValueTextView;
        final TextView upvoteKeyTextView;
        final TextView upvoteValueTextView;
        final TextView videoLinkKeyTextView;
        final TextView videoLinkValueTextView;
        final TextView videoLink360KeyTextView;
        final TextView videoLink360ValueTextView;
        final TextView videoLink480KeyTextView;
        final TextView videoLink480ValueTextView;
        final TextView videoLink720KeyTextView;
        final TextView videoLink720ValueTextView;
        if (convertView == null) {
            layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            resultNumberTextView = new TextView(context);
            resultNumberTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            layout.addView(resultNumberTextView);


            videoNameKeyTextView = new TextView(context);
            videoNameValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(videoNameKeyTextView, videoNameValueTextView);
            layout.addView(videoNameKeyTextView);
            layout.addView(videoNameValueTextView);

            downvoteKeyTextView = new TextView(context);
            downvoteValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(downvoteKeyTextView, downvoteValueTextView);
            layout.addView(downvoteKeyTextView);
            layout.addView(downvoteValueTextView);

            upvoteKeyTextView = new TextView(context);
            upvoteValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(upvoteKeyTextView, upvoteValueTextView);
            layout.addView(upvoteKeyTextView);
            layout.addView(upvoteValueTextView);

            videoLinkKeyTextView = new TextView(context);
            videoLinkValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(videoLinkKeyTextView, videoLinkValueTextView);
            layout.addView(videoLinkKeyTextView);
            layout.addView(videoLinkValueTextView);

            videoLink360KeyTextView = new TextView(context);
            videoLink360ValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(videoLink360KeyTextView, videoLink360ValueTextView);
            layout.addView(videoLink360KeyTextView);
            layout.addView(videoLink360ValueTextView);

            videoLink480KeyTextView = new TextView(context);
            videoLink480ValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(videoLink480KeyTextView, videoLink480ValueTextView);
            layout.addView(videoLink480KeyTextView);
            layout.addView(videoLink480ValueTextView);

            videoLink720KeyTextView = new TextView(context);
            videoLink720ValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(videoLink720KeyTextView, videoLink720ValueTextView);
            layout.addView(videoLink720KeyTextView);
            layout.addView(videoLink720ValueTextView);
        } else {
            layout = (LinearLayout) convertView;
            resultNumberTextView = (TextView) layout.getChildAt(0);

            videoNameKeyTextView = (TextView) layout.getChildAt(1);
            videoNameValueTextView = (TextView) layout.getChildAt(2);

            downvoteKeyTextView = (TextView) layout.getChildAt(3);
            downvoteValueTextView = (TextView) layout.getChildAt(4);

            upvoteKeyTextView = (TextView) layout.getChildAt(5);
            upvoteValueTextView = (TextView) layout.getChildAt(6);

            videoLinkKeyTextView = (TextView) layout.getChildAt(7);
            videoLinkValueTextView = (TextView) layout.getChildAt(8);

            videoLink360KeyTextView = (TextView) layout.getChildAt(9);
            videoLink360ValueTextView = (TextView) layout.getChildAt(10);

            videoLink480KeyTextView = (TextView) layout.getChildAt(11);
            videoLink480ValueTextView = (TextView) layout.getChildAt(12);

            videoLink720KeyTextView = (TextView) layout.getChildAt(13);
            videoLink720ValueTextView = (TextView) layout.getChildAt(14);
        }

        resultNumberTextView.setText(String.format("#%d", + position+1));
        videoNameKeyTextView.setText("VideoName");
        videoNameValueTextView.setText(result.getVideoName());
        downvoteKeyTextView.setText("Downvote");
        downvoteValueTextView.setText("" + result.getDownvote().longValue());
        upvoteKeyTextView.setText("Upvote");
        upvoteValueTextView.setText("" + result.getUpvote().longValue());
        videoLinkKeyTextView.setText("VideoLink");
        videoLinkValueTextView.setText(result.getVideoLink());
        videoLink360KeyTextView.setText("VideoLink-360");
        videoLink360ValueTextView.setText(result.getVideoLink360());
        videoLink480KeyTextView.setText("VideoLink-480");
        videoLink480ValueTextView.setText(result.getVideoLink480());
        videoLink720KeyTextView.setText("VideoLink-720");
        videoLink720ValueTextView.setText(result.getVideoLink720());
        return layout;
    }
}
