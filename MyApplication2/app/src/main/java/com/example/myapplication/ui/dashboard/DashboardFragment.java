package com.example.myapplication.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.OneBenefitActivity;
import com.example.myapplication.R;
import com.example.myapplication.SubscriptionBenefitsActivity;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        Button subscriptionBenefit = (Button) root.findViewById(R.id.benefitsBtn);
        Button oneBenefit = (Button) root.findViewById(R.id.oneBenefitBtn);

        subscriptionBenefit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SubscriptionBenefitsActivity.class);
                startActivity(intent);
            }
        });

        oneBenefit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OneBenefitActivity.class);
                startActivity(intent);
            }
        });

        return root;

    }
}