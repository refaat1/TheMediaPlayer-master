package dv106.refaat.themediaplayer2.listadapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dv106.refaat.themediaplayer2.R;
import dv106.refaat.themediaplayer2.pojo.Video;
import dv106.refaat.themediaplayer2.preferences.PreferencesActivity;
import dv106.refaat.themediaplayer2.util.MediaPlayerTimeUtil;

public class VideoListAdapter extends ArrayAdapter<Video> {

	private ArrayList<Video> allVideos;
	private SharedPreferences settings;

	public VideoListAdapter(Context context, ArrayList<Video> videos) {
		super(context, R.layout.video_list_item, videos);
		this.allVideos = videos;
		settings = context.getSharedPreferences("videoThumbnail", 0);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/**
		 * Method re-used from the lecture slides to save battery
		 */
		View row;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			row = inflater.inflate(R.layout.video_list_item, null);
		} else {
			row = convertView;
		}
		
		Video tmpVideo = allVideos.get(position);

		String TITLE = tmpVideo.getTITLE();
		long vidDur = Long.valueOf(tmpVideo.getDURATION());
		String DURATION = MediaPlayerTimeUtil.formatMillisecond(vidDur);

		String filepath = settings.getString(tmpVideo.getDATA(), "");

		TextView tvTitle = (TextView) row
				.findViewById(R.id.tvListItemVideoTitle);
		TextView tvDuration = (TextView) row
				.findViewById(R.id.tvListItemVideoDuration);
		ImageView ivThumbnail = (ImageView) row
				.findViewById(R.id.ivListItemVideoThumbnail);
		
		tvTitle.setText(TITLE);
		tvDuration.setText(DURATION);
		ivThumbnail.setImageURI(Uri.parse(filepath));
		
		/* prefs */
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
		String txtSize = sharedPref.getString(PreferencesActivity.KEY_PREF_TXT_SIZE, "");
		int txtColor = sharedPref.getInt(PreferencesActivity.KEY_PREF_TXT_COLOR, 1);
		tvTitle.setTextSize(Float.parseFloat(txtSize));
		tvTitle.setTextColor(txtColor);
		tvDuration.setTextSize(Float.parseFloat(txtSize));
		tvDuration.setTextColor(txtColor);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.push_left_in);
        animation.setDuration(500);
        row.startAnimation(animation);

        return row;
	}

}
