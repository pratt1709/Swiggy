package com.prateek.swiggy.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prateek.swiggy.R;
import com.prateek.swiggy.domain.ExclusionRules;
import com.prateek.swiggy.rest.Request.GroupChoice;
import com.prateek.swiggy.rest.Request.VariantGroup;
import com.prateek.swiggy.ui.adapter.VariantViewAdapter;

import java.util.ArrayList;

/**
 * Created by prateek.kesarwani on 22/10/16.
 */


public class MainFragment extends Fragment {

    private static final String VARIANT_GROUP = "variant_list";
    GetDataInterface sGetDataInterface;
    private VariantGroup mVariantGroup;
    private VariantViewAdapter mVariantViewAdapter;
    private RecyclerView mMainRecyclerView;

    public static MainFragment newInstance(VariantGroup variantGroup) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putParcelable(VARIANT_GROUP, variantGroup);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mVariantGroup = getArguments().getParcelable(VARIANT_GROUP);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        sGetDataInterface = (GetDataInterface) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.main_frag, container, false);

        mMainRecyclerView = (RecyclerView) layout.findViewById(R.id.frag_recycler_view);

        mMainRecyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        mMainRecyclerView.setLayoutManager(mLayoutManager);

        ExclusionRules exclusionRules = new ExclusionRules();
        mVariantGroup = exclusionRules.getVariationAfterExclusion(sGetDataInterface.getSelectedValues(), mVariantGroup, sGetDataInterface.getDataList());
        mVariantViewAdapter = new VariantViewAdapter(mVariantGroup.getVariations(), mVariantGroup.getGroupId());

        mMainRecyclerView.setAdapter(mVariantViewAdapter);

        return layout;
    }


    public interface GetDataInterface {
        ArrayList<ArrayList<GroupChoice>> getDataList();

        ArrayList<GroupChoice> getSelectedValues();
    }


}