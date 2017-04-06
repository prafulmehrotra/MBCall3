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
import com.amazonaws.models.nosql.UserVoteDO;
import com.amazonaws.models.nosql.VideoNameDO;

import java.util.Set;

public class DemoNoSQLUserVoteResult implements DemoNoSQLResult {
    private static final int KEY_TEXT_COLOR = 0xFF333333;
    private final UserVoteDO result;

    DemoNoSQLUserVoteResult(final UserVoteDO result) {
        this.result = result;
    }
    @Override
    public void updateItem() {
        final DynamoDBMapper mapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
        boolean originalValue = result.getUpvote();
        if(originalValue == false){
            result.setUpvote(true);
            VideoNameDO video_entry = mapper.load(VideoNameDO.class, result.getVideoName());
            video_entry.setUpvote(video_entry.getUpvote() + 1);
            video_entry.setDownvote(video_entry.getDownvote() - 1);
            try {
                mapper.save(video_entry);
                mapper.save(result);
            } catch (final AmazonClientException ex) {
                result.setUpvote(false);
                throw ex;
            }

        }
    }

    @Override
    public void deleteItem() {
        final DynamoDBMapper mapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
        boolean originalValue = result.getUpvote();
        if(originalValue == true){
            result.setUpvote(false);
            VideoNameDO video_entry = mapper.load(VideoNameDO.class, result.getVideoName());
            video_entry.setUpvote(video_entry.getUpvote() - 1);
            video_entry.setDownvote(video_entry.getDownvote() + 1);
            try {
                mapper.save(video_entry);
                mapper.save(result);
            } catch (final AmazonClientException ex) {
                result.setUpvote(true);
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
        final TextView userIdKeyTextView;
        final TextView userIdValueTextView;
        final TextView videoNameKeyTextView;
        final TextView videoNameValueTextView;
        final TextView upvoteKeyTextView;
        final TextView upvoteValueTextView;
        if (convertView == null) {
            layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            resultNumberTextView = new TextView(context);
            resultNumberTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            layout.addView(resultNumberTextView);


            userIdKeyTextView = new TextView(context);
            userIdValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(userIdKeyTextView, userIdValueTextView);
            layout.addView(userIdKeyTextView);
            layout.addView(userIdValueTextView);

            videoNameKeyTextView = new TextView(context);
            videoNameValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(videoNameKeyTextView, videoNameValueTextView);
            layout.addView(videoNameKeyTextView);
            layout.addView(videoNameValueTextView);

            upvoteKeyTextView = new TextView(context);
            upvoteValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(upvoteKeyTextView, upvoteValueTextView);
            layout.addView(upvoteKeyTextView);
            layout.addView(upvoteValueTextView);
        } else {
            layout = (LinearLayout) convertView;
            resultNumberTextView = (TextView) layout.getChildAt(0);

            userIdKeyTextView = (TextView) layout.getChildAt(1);
            userIdValueTextView = (TextView) layout.getChildAt(2);

            videoNameKeyTextView = (TextView) layout.getChildAt(3);
            videoNameValueTextView = (TextView) layout.getChildAt(4);

            upvoteKeyTextView = (TextView) layout.getChildAt(5);
            upvoteValueTextView = (TextView) layout.getChildAt(6);
        }

        resultNumberTextView.setText(String.format("#%d", + position+1));
        userIdKeyTextView.setText("userId");
        userIdValueTextView.setText(result.getUserId());
        videoNameKeyTextView.setText("VideoName");
        videoNameValueTextView.setText(result.getVideoName());
        upvoteKeyTextView.setText("Upvote");
        upvoteValueTextView.setText("" + result.getUpvote());
        return layout;
    }
}
