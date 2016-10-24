package com.daejong.seoulpharm.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hyunwoo on 2016. 10. 23..
 */
public class TreeNode<T> {

    T data;
    TreeNode<T> parent;
    List<TreeNode<T>> children;

    boolean isSelected = false;

    public TreeNode(T data) {
        this.data = data;
        this.children = new LinkedList<TreeNode<T>>();
    }

    public TreeNode<T> addChild(T child) {
        TreeNode<T> childNode = new TreeNode<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }


    // other features ...
    public List<TreeNode<T>> getChildren() {
        return children;
    }
    public TreeNode<T> getParent() {
        return parent;
    }

    public boolean isLastParent() {
        int leafCount = 0;          // child들의 child 개수
        for (TreeNode<T> child : this.children) {
            leafCount += child.getChildren().size();
        }
        if (leafCount == 0 && this.children.size() != 0) {
            return true;
        }
        return false;

    }

    public boolean isLastChild() {
        if (this.children.size() == 0) {
            return true;
        }
        return false;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    public boolean getSelected() {
        return this.isSelected;
    }

    @Override
    public String toString() {
        return data.toString();
    }

}