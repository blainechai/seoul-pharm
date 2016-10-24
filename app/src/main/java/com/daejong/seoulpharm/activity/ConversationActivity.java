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

public class ConversationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView conversationListView;
    ConversationListAdatper mAdatper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        conversationListView = (ListView) findViewById(R.id.conversation_list);
        mAdatper = new ConversationListAdatper();
        conversationListView.setAdapter(mAdatper);
        mAdatper.showCurrentList();

        conversationListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (mAdatper.getCurrentNode().isLastParent()) {
            if (mAdatper.getNodeSelected(position)) {
                mAdatper.setNodeSelected(position, false);
            } else {
                mAdatper.setNodeSelected(position, true);
            }
        }
        else {
            String selectedNodeName = ((ConversationItemView)view).getItemText();
            mAdatper.currentNodeToChild(selectedNodeName);
        }
    }

    /*
    public void pushSymptomFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SymptomFragment()).addToBackStack(null).commit();
    }

    public void popFragment() {
        getSupportFragmentManager().popBackStack();
    }
    */

    @Override
    public void onBackPressed() {
        if (mAdatper.currentNodeIsRoot()) {
            // 현재 노드가 최상위 노드일 때
            super.onBackPressed();
        } else {
            // 현재 노드가 하위 노드일 떄
            mAdatper.currentNodeToParent();
        }
    }
}
