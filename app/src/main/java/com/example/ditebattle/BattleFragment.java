package com.example.ditebattle;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Timer;


public class BattleFragment extends Fragment {
    ProgressBar battleFragProgressLeft, battleFragProgressRight;
    ImageView battleFragPlayerLeft;
    ImageView battleFragPlayerRight;
    TextView battleFragTimerTv, battleFragRightName , battleFragLeftName, battleFragHpTvMaster, battleFragHpTvGuest;
    ArrayList<String> user;
    ArrayList<String> battle;
    Thread thread;
    FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
    private Handler mHandler = new Handler(Looper.getMainLooper());
    public BattleFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_battle, container, false);
        // Inflate the layout for this fragment
        battleFragPlayerLeft = view.findViewById(R.id.battleFragPlayerLeft);
        battleFragPlayerRight = view.findViewById(R.id.battleFragPlayerRight);
        battleFragProgressLeft = view.findViewById(R.id.battleFragProgressLeft);
        battleFragProgressRight = view.findViewById(R.id.battleFragProgressRight);
        battleFragTimerTv = view.findViewById(R.id.battleFragTimerTv);
        battleFragHpTvMaster = view.findViewById(R.id.battleFragHpTvMaster);
        battleFragHpTvGuest = view.findViewById(R.id.battleFragHpTvGuest);
        battleFragRightName = view.findViewById(R.id.battleFragRightName);
        battleFragLeftName = view.findViewById(R.id.battleFragLeftName);

        battleFragProgressRight.setRotation(180);
        Glide.with(this).load(R.drawable.battle_player_left).into(battleFragPlayerLeft);
        Glide.with(this).load(R.drawable.battle_player_right).into(battleFragPlayerRight);

        thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        battle=((BattleRoom)getActivity()).deliverBattle();
                        user=((BattleRoom)getActivity()).deliverUser();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Calendario para obtener fecha & hora
                                long t = System.currentTimeMillis() / 1000;
                                long totalSec = Long.parseLong(battle.get(2)) - t;
                                long day = totalSec / (60 * 60 * 24);
                                long hour = (totalSec - day * 60 * 60 * 24) / (60 * 60);
                                long minute = (totalSec - day * 60 * 60 * 24 - hour * 3600) / 60;
                                long second = totalSec % 60;
                                battleFragTimerTv.setText(day + "일 " + hour + "시간 " + minute + "분 " + second + "초");
                                battleFragProgressLeft.setProgress(Integer.parseInt(battle.get(3)));
                                battleFragProgressRight.setProgress(Integer.parseInt(battle.get(4)));
                                battleFragHpTvMaster.setText((battle.get(3))+"/500");
                                battleFragHpTvGuest.setText((battle.get(4))+"/500");
                                if (!uid.getUid().equals(battle.get(1))) {
                                    battleFragLeftName.setText(user.get(1));
                                }else{
                                    battleFragRightName.setText(user.get(1));
                                }
                                if(totalSec<=0){
                                    Toast.makeText(getActivity().getApplicationContext(),"대결이 종료되었습니다.",Toast.LENGTH_SHORT);
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    Log.v("InterruptedException", e.getMessage());
                }
            }
        };
        thread.start();
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        thread.interrupt();
    }
}