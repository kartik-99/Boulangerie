package com.example.kartik.boulangerie;

import android.app.Activity;
import android.media.Image;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kartik.boulangerie.Objects.Step;
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


public class StepDetailFragment extends Fragment {

    Step step;
    TextView description, notAvailable;
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    ImageView thumbnail;

    public static StepDetailFragment newInstance(Step step){
        StepDetailFragment detailFragment = new StepDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("step", step);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    public StepDetailFragment(  ) {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        step = getArguments().getParcelable("step");
        description = (TextView) rootView.findViewById(R.id.item_detail);
        description.setText(step.getDescription());


        thumbnail = (ImageView) rootView.findViewById(R.id.thumbnail);
        notAvailable = (TextView) rootView.findViewById(R.id.not_available);


        // 1. Create a default TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

// 2. Create a default LoadControl
        LoadControl loadControl = new DefaultLoadControl();

// 3. Create the player
        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        simpleExoPlayerView = new SimpleExoPlayerView(getContext());
        simpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.player_view);


//Set media controller
        simpleExoPlayerView.setUseController(true);
        simpleExoPlayerView.requestFocus();

// Bind the player to the view.
        simpleExoPlayerView.setPlayer(player);

        String userAgent = Util.getUserAgent(getContext(), "Boulangerie");





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



            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(step.getVideoURL()), new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        player.stop();
        player.release();
        player = null;
        step = null;
    }
}
