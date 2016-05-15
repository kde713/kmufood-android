package net.sproutlab.kmufood.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.sproutlab.kmufood.R;


public class FragmentGuide extends DialogFragment {

    public static FragmentGuide newInstance() {
        FragmentGuide fragment = new FragmentGuide();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // implement either onCreateView or onCreateDialog
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            AlertDialog dialog = new AlertDialog.Builder(getActivity(), getTheme())
//                    .setIcon(R.drawable.ic_launcher)
//                    .setTitle("Hello")
//                    .setPositiveButton("OK", null)
//                    .create();
//            dialog.getWindow().getAttributes().height = WindowManager.LayoutParams.WRAP_CONTENT;
//            return dialog;
//        }
//        // customize the content of the dialog by overriding onCreateView
//        return super.onCreateDialog(savedInstanceState);
//    }

    // implement either onCreateView or onCreateDialog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_guide, container, false);
        return v;
    }

}