package com.example.kartik.boulangerie.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kartik.boulangerie.objects.Step;
import com.example.kartik.boulangerie.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.ButterKnife;


public class StepDetailFragment extends Fragment {

    Step step;
    TextView description, notAvailable;
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    ImageView thumbnail;
    Uri videoUri = null;
    BandwidthMeter bandwidthMeter;

    long position = C.POSITION_UNSET;
    boolean playerState = true;
    boolean fromSavedInstance = false;

    String userAgent;
    String stepObject = "STEP_OBJECT";
    String currentPosition = "POSITION";
    String currentState = "CURRENT_STATE";

    public StepDetailFragment(  ) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //variables initialization
        if(savedInstanceState!=null){
            step =  savedInstanceState.getParcelable(stepObject);
            position = savedInstanceState.getLong(currentPosition);
            playerState = savedInstanceState.getBoolean(currentState);
            fromSavedInstance = true;
        }else{
            step = getArguments().getParcelable("step");
            position = C.POSITION_UNSET;
            playerState = true;
            fromSavedInstance = false;
        }
        videoUri = Uri.parse(step.getVideoURL());
        bandwidthMeter = new DefaultBandwidthMeter();

        //view initialization
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        description = ButterKnife.findById(rootView, R.id.item_detail);
        userAgent = Util.getUserAgent(getContext(), "Boulangerie");
        thumbnail = ButterKnife.findById(rootView, R.id.thumbnail);
        notAvailable = ButterKnife.findById(rootView, R.id.not_available);
        simpleExoPlayerView = ButterKnife.findById(rootView, R.id.player_view);


        //set up ui
        description.setText(step.getDescription());
        if(!step.getVideoURL().startsWith("h")){
            if(step.getThumbnailURL().startsWith("h")){
                simpleExoPlayerView.setVisibility(View.GONE);
                thumbnail.setVisibility(View.VISIBLE);
                notAvailable.setVisibility(View.GONE);
                Glide.with(getContext())
                        .load(Uri.parse(step.getThumbnailURL()))
                        .into(thumbnail);
            }else{
                simpleExoPlayerView.setVisibility(View.GONE);
                thumbnail.setVisibility(View.GONE);
                notAvailable.setVisibility(View.VISIBLE);
            }
        }else{
            simpleExoPlayerView.setVisibility(View.VISIBLE);
            thumbnail.setVisibility(View.GONE);
            notAvailable.setVisibility(View.GONE);
            if(!fromSavedInstance){
                initializePlayer();
            }
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(stepObject, step);
        outState.putLong(currentPosition, position);
        outState.putBoolean(currentState, playerState);
    }

    public void initializePlayer(){
        if(player == null){
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();

            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(player);

            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(step.getVideoURL()), new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            player.seekTo(position);
            player.prepare(mediaSource);
            player.setPlayWhenReady(playerState);
        }

    }
    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        releasePlayer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    public static StepDetailFragment newInstance(Step step){
        StepDetailFragment detailFragment = new StepDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("step", step);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23) {
            initializePlayer();
        }
    }

    void releasePlayer(){
        if (player!=null) {
            position = player.getCurrentPosition();
            playerState = player.getPlayWhenReady();
            player.stop();
            player.release();
            player = null;
        }
    }

}
