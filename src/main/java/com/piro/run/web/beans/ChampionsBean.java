package com.piro.run.web.beans;

import com.piro.run.service.ChampionsService;
import com.piro.run.service.impl.ChampionsServiceImpl;
import org.primefaces.model.TreeNode;

import java.io.Serializable;

/**
 * Created by ppirovski on 4/29/15. In Code we trust
 */
public class ChampionsBean implements Serializable {

    public final long serialVersionUID = 7638423942826134228L;

    private transient ChampionsService championsService;

    private TreeNode root;

    public ChampionsBean(ChampionsService championService) {
        this.championsService = championService;
        initTree();
    }

    public TreeNode getRoot() {
        return root;
    }

    private void initTree(){
        root = championsService.getChampionsTree();
    }
}
