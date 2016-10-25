package com.daejong.seoulpharm.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.model.constant.ConversationKorConst;
import com.daejong.seoulpharm.util.TreeNode;
import com.daejong.seoulpharm.view.ConversationItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyunwoo on 2016. 10. 22..
 */
public class ConversationListAdatper extends BaseAdapter {

    List<TreeNode<String>> items = new ArrayList<>();

    TreeNode<String> root = new TreeNode<String>(ConversationKorConst.ROOT);
    {
        TreeNode<String> currentSymptom = root.addChild(ConversationKorConst.KOR_CURRENT_SYMPTOM);
        {
            TreeNode<String> head = currentSymptom.addChild(ConversationKorConst.KOR_D1_HEAD);
            {
                TreeNode<String> scalp = head.addChild(ConversationKorConst.KOR_D2_SCALP);
                {
                    TreeNode<String> bleeding = scalp.addChild(ConversationKorConst.KOR_DSYMP_BLEEDING);
                    TreeNode<String> bruise = scalp.addChild(ConversationKorConst.KOR_DSYMP_BRUISE);
                    TreeNode<String> itching = scalp.addChild(ConversationKorConst.KOR_DSYMP_ITCHING);
                    TreeNode<String> burn = scalp.addChild(ConversationKorConst.KOR_DSYMP_BURN);
                    TreeNode<String> lump = scalp.addChild(ConversationKorConst.KOR_DSYMP_LUMP);
                    TreeNode<String> numbness = scalp.addChild(ConversationKorConst.KOR_DSYMP_NUMBLESS);
                    TreeNode<String> swelling = scalp.addChild(ConversationKorConst.KOR_DSYMP_SWELLING);
                }
                TreeNode<String> eye = currentSymptom.addChild(ConversationKorConst.KOR_D2_EYES);
                {
                    TreeNode<String> bleeding = eye.addChild(ConversationKorConst.KOR_DSYMP_BLEEDING);
                    TreeNode<String> blind_spot = eye.addChild(ConversationKorConst.KOR_DSYMP_BLIND_SPOT);
                    TreeNode<String> blurred_vision = eye.addChild(ConversationKorConst.KOR_DSYMP_BLURRED_VISION);
                    TreeNode<String> bruise = eye.addChild(ConversationKorConst.KOR_DSYMP_BRUISE);
                    TreeNode<String> cloudy_vision = eye.addChild(ConversationKorConst.KOR_DSYMP_CLOUDY_VISION);
                    TreeNode<String> bulging_eyes = eye.addChild(ConversationKorConst.KOR_DSYMP_BULGING_EYES);
                    TreeNode<String> mucus = eye.addChild(ConversationKorConst.KOR_DSYMP_MUCUS);
                    TreeNode<String> double_vision = eye.addChild(ConversationKorConst.KOR_DSYMP_DOUBLE_VISION);
                    TreeNode<String> pus = eye.addChild(ConversationKorConst.KOR_DSYMP_PUS);
                    TreeNode<String> dry = eye.addChild(ConversationKorConst.KOR_DSYMP_DRY);
                    TreeNode<String> irritation = eye.addChild(ConversationKorConst.KOR_DSYMP_IRRITATION);
                    TreeNode<String> redness = eye.addChild(ConversationKorConst.KOR_DSYMP_REDNESS);
                    TreeNode<String> pain = eye.addChild(ConversationKorConst.KOR_DSYMP_PAIN);
                    TreeNode<String> puffy = eye.addChild(ConversationKorConst.KOR_DSYMP_PUFFY);
                    TreeNode<String> sth_in_the_eye = eye.addChild(ConversationKorConst.KOR_DSYMP_STH_IN_THE_EYE);
                    TreeNode<String> watery = eye.addChild(ConversationKorConst.KOR_DSYMP_WATERY);
                }
                TreeNode<String> nose = currentSymptom.addChild(ConversationKorConst.KOR_D2_NOSE);
                {
                    // TreeNode<String>
                }
            }
            TreeNode<String> neck = currentSymptom.addChild("목");
            TreeNode<String> arm = currentSymptom.addChild("팔");
            TreeNode<String> chest = currentSymptom.addChild("흉부");
        }
        TreeNode<String> chronicDisease = root.addChild(ConversationKorConst.KOR_CHRONIC_DISEASE);
    }


    // CURRENT NODE (현재 보여지고 있는 화면)
    private TreeNode<String> currentNode = root;

    public TreeNode<String> getCurrentNode() {
        return currentNode;
    }

    public boolean currentNodeIsRoot() {
        return (currentNode==root) ? true : false;
    }

    // 상위 계층으로
    public void currentNodeToParent() {
        currentNode = currentNode.getParent();
        showCurrentList();
    }

    // 하위 계층으로
    public void currentNodeToChild(String selectedChildName) {
        for (TreeNode<String> child : currentNode.getChildren()) {
            // 선택된 child item을 탐색
            if (child.toString().equals(selectedChildName)) {
                // 선택된 child가 마지막 노드라면
                if (child.isLastChild()) {
                    return;             // 하위 노드로 이동하지 않는다.
                }
                // 선택된 child가 마지막 노드가 아니라면
                currentNode = child;    // 현재 노드를 child 노드로 바꾸고
                showCurrentList();      // 리스트에 뿌려준다.
                return;
            }
        }
    }

    // current node의 children 리스트를 items에 담는다.
    public void showCurrentList() {
        items.clear();
        for (TreeNode<String> child : currentNode.getChildren()) {
            items.add(child);
        }
        notifyDataSetChanged();
    }

    // 선택된 노드 정보를 가져온다.
    public TreeNode<String> getSelectedNode(String selectedChildName) {
        for (TreeNode<String> child : currentNode.getChildren()) {
            if (child.toString().equals(selectedChildName)) {
                return child;
            }
        }
        return null;
    }

    // Node의 selected 상태를 바꿈
    public void setNodeSelected(int position, boolean isSelected) {
        items.get(position).setSelected(isSelected);
        notifyDataSetChanged();
    }
    // Node의 selected 상태를 반환
    public boolean getNodeSelected(int position) {
        return items.get(position).getSelected();
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConversationItemView view;
        if (convertView != null) {
            view = (ConversationItemView) convertView;
        } else {
            view = new ConversationItemView(parent.getContext());
        }
        if (items.get(position).getSelected()) {
            view.findViewById(R.id.list_item).setBackgroundColor(parent.getContext().getResources().getColor(R.color.colorPrimary));
            Log.d("SELECTED!!!!", "POSITION"+position+"  BG"+view.getBackground());
        } else {
            view.findViewById(R.id.list_item).setBackgroundColor(parent.getContext().getResources().getColor(R.color.color_efefef));
            Log.d("????????????", "POSITION"+position+"  BG"+view.getBackground());
        }
        view.setItemText(items.get(position).toString());
        return view;
    }

}
