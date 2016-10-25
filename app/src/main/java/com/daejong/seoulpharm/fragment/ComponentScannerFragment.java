package com.daejong.seoulpharm.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.activity.MainActivity;
import com.daejong.seoulpharm.util.NetworkManager;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by blainechai on 2016. 10. 23..
 */

public class ComponentScannerFragment extends Fragment implements View.OnClickListener, ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    public ComponentScannerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_component_scanner, container, false);
        mScannerView = (ZXingScannerView) view.findViewById(R.id.scanner_view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void handleResult(Result rawResult) {
//        Toast.makeText(getActivity(), "Contents = " + rawResult.getText() +
//                ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();
        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        NetworkManager.getInstance().getComponentByBarcode(getActivity(), rawResult.getText(), new NetworkManager.OnResultListener<String>() {
            @Override
            public void onSuccess(String result) {
//                Toast.makeText(getActivity(), "" + result, Toast.LENGTH_SHORT).show();
                ComponentInfoFragment fragment = new ComponentInfoFragment();
                Bundle bundle = new Bundle();
                bundle.putString("medData", result);
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack(null).commit();
            }

            @Override
            public void onFail(int code, String responseString) {
                Log.d("REQTESTTTT", "error code:" + code + "\n" + responseString);
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(ComponentScannerFragment.this);
            }
        }, 2000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
}