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

public class FragmentBottom extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom, null);

        Button btDataFragmentTop = (Button) view.findViewById(R.id.btDataFragmentTop);
        btDataFragmentTop.setOnClickListener(FragmentBottom.this);

        // EventBus register
        EventBus.getDefault().register(FragmentBottom.this);

        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        // EventBus Unregister
        EventBus.getDefault().unregister(FragmentBottom.this);
    }


    // Listeners
    @Override
    public void onClick(View v) {
        Log.i("LOG", "FragmentBottom.this.onClick()");

        MessageEB m = new MessageEB();
        m.setClassTester(FragmentTop.class+"");
        m.setText("This message came from FragmentBottom");

        EventBus.getDefault().post(m);
    }

    @Subscribe
    public void onEvent(MessageEB mMessageEB){
        if(!mMessageEB.getClassTester().equalsIgnoreCase(FragmentBottom.class+""))
            return;

        Log.i("LOG", "FragmentBottom.this.onEvent()");

        Toast.makeText(getActivity(), mMessageEB.getText(), Toast.LENGTH_SHORT).show();
    }
}

