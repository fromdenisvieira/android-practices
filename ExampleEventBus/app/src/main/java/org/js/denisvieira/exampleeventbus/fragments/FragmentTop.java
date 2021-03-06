package org.js.denisvieira.exampleeventbus.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.js.denisvieira.exampleeventbus.R;
import org.js.denisvieira.exampleeventbus.eventbus.MessageEB;

/**
 * Created by denisvieira on 08/08/16.
 */

public class FragmentTop extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, null);

        Button btDataFragmentBottom = (Button) view.findViewById(R.id.btDataFragmentBottom);
        btDataFragmentBottom.setOnClickListener(FragmentTop.this);

        // EventBus register
        EventBus.getDefault().register(FragmentTop.this);

        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        // EventBus Unregister
        EventBus.getDefault().unregister(FragmentTop.this);
    }

    // Listeners
    @Override
    public void onClick(View v) {
        Log.i("LOG", "FragmentTop.this.onClick()");

        MessageEB m = new MessageEB();
        m.setClassTester(FragmentBottom.class+"");
        m.setText("This message came from FragmentTop");

        EventBus.getDefault().post(m);
    }

    @Subscribe
    public void onEvent(MessageEB mMessageEB){
        if(!mMessageEB.getClassTester().equalsIgnoreCase(FragmentTop.class+""))
            return;

        Log.i("LOG", "FragmentTop.this.onEvent()");

        Toast.makeText(getActivity(), mMessageEB.getText(), Toast.LENGTH_SHORT).show();
    }
}
