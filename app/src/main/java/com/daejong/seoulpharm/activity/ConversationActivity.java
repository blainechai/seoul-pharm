package com.daejong.seoulpharm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.daejong.seoulpharm.adapter.ConversationListAdatper;
import com.daejong.seoulpharm.fragment.MyConversationFragment;
import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.fragment.SymptomFragment;
import com.daejong.seoulpharm.model.ConversationConst;
import com.daejong.seoulpharm.view.ConversationItemView;

public class ConversationActivity extends AppCompatActivity {

    ListView conversationListView;
    ConversationListAdatper mAdatper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        conversationListView = (ListView) findViewById(R.id.conversation_list);
        mAdatper = new ConversationListAdatper();
        conversationListView.setAdapter(mAdatper);
        mAdatper.showCurrnetList();
        // mAdatper.setOnLastNodeListener(this);

        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
               String selectedNodeName = ((ConversationItemView)view).getItemText();
               if (mAdatper.getSelectedNode(selectedNodeName).isLastParent()) {
                   setMultipleSelectMode();
               }
               mAdatper.changeCurrentNode(selectedNodeName);
           }
        });
    }

    String testtt;


    public void setMultipleSelectMode() {
        conversationListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (mAdatper.getNodeSelected(position)) {
                    mAdatper.setNodeSelected(position, false);
                } else {
                    mAdatper.setNodeSelected(position, true);
                }
            }
        });
    }

    /*
    public void pushSymptomFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SymptomFragment()).addToBackStack(null).commit();
    }

    public void popFragment() {
        getSupportFragmentManager().popBackStack();
    }
    */
}
