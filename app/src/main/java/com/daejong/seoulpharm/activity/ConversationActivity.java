package com.daejong.seoulpharm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daejong.seoulpharm.adapter.ConversationListAdapter;
import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.view.ConversationItemView;

public class ConversationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView conversationListView;
    ConversationListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        conversationListView = (ListView) findViewById(R.id.conversation_list);
        mAdapter = new ConversationListAdapter();
        conversationListView.setAdapter(mAdapter);
        mAdapter.showCurrentList();

        conversationListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (mAdapter.getCurrentNode().isLastParent()) {
            if (mAdapter.getNodeSelected(position)) {
                mAdapter.setNodeSelected(position, false);
            } else {
                mAdapter.setNodeSelected(position, true);
            }
        }
        else {
            String selectedNodeName = ((ConversationItemView)view).getItemText();
            mAdapter.currentNodeToChild(selectedNodeName);
        }
    }


    @Override
    public void onBackPressed() {
        if (mAdapter.currentNodeIsRoot()) {
            // 현재 노드가 최상위 노드일 때
            super.onBackPressed();
        } else {
            // 현재 노드가 하위 노드일 떄
            mAdapter.currentNodeToParent();
        }
    }
}
