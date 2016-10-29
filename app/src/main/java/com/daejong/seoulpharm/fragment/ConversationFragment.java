package com.daejong.seoulpharm.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.activity.ConversationActivity;
import com.daejong.seoulpharm.adapter.ConversationListAdapter;
import com.daejong.seoulpharm.model.ConversationListItem;
import com.daejong.seoulpharm.view.ConversationHeaderView;

import java.util.ArrayList;
import java.util.List;

public class ConversationFragment extends Fragment implements ConversationActivity.OnBackKeyPressedListener {

    // FRAGMENT TYPE
    public static final String KEY_FRAGMENT_TYPE = "KEY_FRAGMENT_TYPE";
    public static final String TYPE_SKIN = "TYPE_SKIN";
    public static final String TYPE_RESPIRATORY = "TYPE_RESPIRATORY";
    public static final String TYPE_GASTROINTESTINAL = "TYPE_GASTROINTESTINAL";
    public static final String TYPE_CARDIOVASCULAR = "TYPE_CARDIOVASCULAR";
    public static final String TYPE_NEUROLOGICAL = "TYPE_NEUROLOGICAL";

    private static final String MODE_LIST = "MODE_LIST";
    private static final String MODE_RESULT = "MODE_RESULT";


    private String type;    // fragment type
    private String mode;    // list or result mode

    // VIEW
    ListView listView;
    ConversationListAdapter mAdapter;
    ConversationHeaderView headerView;

    Button confirmBtn;


    public ConversationFragment() {
        // Required empty public constructor
    }

    public static ConversationFragment newInstance(String type) {
        ConversationFragment fragment = new ConversationFragment();
        Bundle args = new Bundle();
        args.putString(KEY_FRAGMENT_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(KEY_FRAGMENT_TYPE);
        }
    }

    @Override
    public void onPause() {
        selectedItems.clear();
        super.onPause();
    }

    List<ConversationListItem> selectedItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);

        // mode setting
        mode = MODE_LIST;

        confirmBtn = (Button) view.findViewById(R.id.btn_confirm);
        confirmBtn.setVisibility(View.GONE);

        listView = (ListView) view.findViewById(R.id.listView);
        mAdapter = new ConversationListAdapter();
        listView.setAdapter(mAdapter);
        headerView = new ConversationHeaderView(getContext());
        listView.addHeaderView(headerView);

        switch (type) {

            case TYPE_SKIN :
                headerView.setHeaderImage(R.drawable.ic_conversation_sample_1);
                mAdapter.add(new ConversationListItem("베임", "Cut", "割破"));
                mAdapter.add(new ConversationListItem("찰과상", "Abrasion", "擦破皮"));
                mAdapter.add(new ConversationListItem("화상", "Burn", "火伤"));
                mAdapter.add(new ConversationListItem("멍", "Bruise", "青肿"));
                mAdapter.add(new ConversationListItem("부어오름", "Swelling", "肿"));
                mAdapter.add(new ConversationListItem("두드러기", "Hives", "风疹块"));
                mAdapter.add(new ConversationListItem("가려움", "Itching", "瘙痒"));
                mAdapter.add(new ConversationListItem("발진", "Rash", "疹"));
                mAdapter.add(new ConversationListItem("물집", "Blisters", "疱"));
                mAdapter.add(new ConversationListItem("염증", "Inflammation", "炎症"));

                break;

            case TYPE_RESPIRATORY :
                headerView.setHeaderImage(R.drawable.ic_conversation_sample_2);
                mAdapter.add(new ConversationListItem("기침", "Cough", "感冒"));
                mAdapter.add(new ConversationListItem("쌕쌕거림", "Wheezing", "喘息"));
                mAdapter.add(new ConversationListItem("숨 참", "Shortness of breath", "气喘"));
                mAdapter.add(new ConversationListItem("코감기", "Nasal congestion", "伤风"));
                mAdapter.add(new ConversationListItem("재채기", "Sneezing", "喷嚏"));
                mAdapter.add(new ConversationListItem("가슴 통증", "Chest pain", "胸痛"));
                mAdapter.add(new ConversationListItem("호흡곤란", "Dyspnoea", "喉急"));

                break;

            case TYPE_GASTROINTESTINAL :
                headerView.setHeaderImage(R.drawable.ic_conversation_sample_3);
                mAdapter.add(new ConversationListItem("메스꺼움", "Nausea", "恶心"));
                mAdapter.add(new ConversationListItem("소화불량", "Indigestion", "消化不良"));
                mAdapter.add(new ConversationListItem("복통", "Stomach pain", "腹痛"));
                mAdapter.add(new ConversationListItem("위 경련", "Stomach cramp", "胃痉挛"));
                mAdapter.add(new ConversationListItem("구토", "Vomiting", "呕吐"));
                mAdapter.add(new ConversationListItem("설사", "Diarrhea", "腹泻"));
                mAdapter.add(new ConversationListItem("변비", "Constipation", "便秘"));

                break;

            case TYPE_CARDIOVASCULAR :
                headerView.setHeaderImage(R.drawable.ic_conversation_sample_4);
                mAdapter.add(new ConversationListItem("빈혈", "Anemia", "贫血"));
                mAdapter.add(new ConversationListItem("어지럼증", "Dizziness", "眩晕症"));
                mAdapter.add(new ConversationListItem("창백함", "Paleness", "苍白"));
                mAdapter.add(new ConversationListItem("약한 맥박", "Weak pulse", "脉搏弱"));

                break;

            case TYPE_NEUROLOGICAL :
                headerView.setHeaderImage(R.drawable.ic_conversation_sample_5);
                mAdapter.add(new ConversationListItem("두통", "Headache", "头痛"));
                mAdapter.add(new ConversationListItem("편두통", "Migrate", "偏头痛"));
                mAdapter.add(new ConversationListItem("지끈거림", "Throbing", "岑岑"));
                mAdapter.add(new ConversationListItem("불안증", "Anxiety", "焦虑症"));
                mAdapter.add(new ConversationListItem("공황장애", "Panic disorder", "惊恐症"));

                break;
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (mode.equals(MODE_LIST)) {
                    int clickedPosition = position - listView.getHeaderViewsCount();
                    if (clickedPosition >= 0) {
                        if (mAdapter.getItemIsSelected(clickedPosition)) {
                            // 선택되있다면 >> 선택 해제
                            mAdapter.setItemSelect(clickedPosition, false);
                            selectedItems.remove(mAdapter.getItem(clickedPosition));
                        } else {
                            // 선택되있지 않다면 >> 선택
                            mAdapter.setItemSelect(clickedPosition, true);
                            selectedItems.add((ConversationListItem) mAdapter.getItem(clickedPosition));
                        }

                        // Confirm Btn Visibility setting
                        if (selectedItems.size() == 0) {
                            // 선택된 아이템들이 없다면
                            Animation animDisappearToBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.disappear_to_bottom);
                            confirmBtn.setAnimation(animDisappearToBottom);
                            confirmBtn.setVisibility(View.GONE);
                        } else {
                            // 선택된 아이템들이 있다면
                            if (confirmBtn.getVisibility() != View.VISIBLE) {
                                confirmBtn.setVisibility(View.VISIBLE);
                                Animation animAppearFromBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.appear_from_bottom);
                                confirmBtn.setAnimation(animAppearFromBottom);
                            }
                        }
                    }

                    listView.smoothScrollToPosition(position);

                }
            }
        });


        // Click Confirm Button
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // mode settings
                mode = MODE_RESULT;

                if (selectedItems.size() != 0) {
                    mAdapter.setSelectedItems(selectedItems);
                    listView.removeHeaderView(headerView);
                    Animation animDisappearToBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.disappear_to_bottom);
                    confirmBtn.setAnimation(animDisappearToBottom);
                    confirmBtn.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }





    @Override
    public void onBackPressed() {
        getActivity().finish();
        /*
        if (selectedItems.size() != 0) {
            // 선택된 아이템들이 있다면
            Animation animDisappearToBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.disappear_to_bottom);
            confirmBtn.setAnimation(animDisappearToBottom);
            confirmBtn.setVisibility(View.GONE);
            mAdapter.setAllItemsNonSelected();
//            Toast.makeText(getActivity(), "SIZE NOT 0"+selectedItems.size(), Toast.LENGTH_SHORT).show();
//            selectedItems.clear();
        } else {
            // 선택된 아이템들이 없다면
//            getActivity().finish();
//            Toast.makeText(getActivity(), "SIZE0 "+selectedItems.size(), Toast.LENGTH_SHORT).show();
        }
        */
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((ConversationActivity)context).setOnBackKeyPressedListener(this);
    }
}
