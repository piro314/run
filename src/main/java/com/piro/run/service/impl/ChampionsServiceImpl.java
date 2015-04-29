package com.piro.run.service.impl;

import com.piro.run.dao.ResultRepository;
import com.piro.run.dto.ChampionsTreeDto;
import com.piro.run.service.ChampionsService;
import com.piro.run.utils.TimeUtils;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Required;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by ppirovski on 4/29/15. In Code we trust
 */
public class ChampionsServiceImpl implements ChampionsService {

    private ResultRepository resultRepository;


    @Override
    public TreeNode getChampionsTree() {
        List<Object[]> rawData = resultRepository.getChampionsData();

        TreeNode root = new DefaultTreeNode();
        TreeNode competitionNode = new DefaultTreeNode(null);
        TreeNode instanceNode = new DefaultTreeNode(null);
        TreeNode legNode = new DefaultTreeNode(null);

        for(Object[] row : rawData){
            ChampionsTreeDto competition = new ChampionsTreeDto((String)row[0], String.valueOf((BigInteger)row[1]), "competition");
            ChampionsTreeDto instance = new ChampionsTreeDto((String)row[2], String.valueOf((BigInteger)row[3]), "instance");
            ChampionsTreeDto leg = new ChampionsTreeDto((String)row[4], String.valueOf((BigInteger)row[5]), "leg");
            Integer legType = (Integer)row[11];
            leg.setData(legType == 1 ? "RUN" : "BIKE");

            String sex = ((Boolean)row[6]) ?  "M" : "F";
            String catStr = row[7] == null ? "" : (String)row[7];
            String name = (String)row[8];
            String username = (String)row[9];
            Long time = ((BigInteger)row[10]).longValue();
            ChampionsTreeDto data = new ChampionsTreeDto(sex+catStr, username, "data");
            data.setTime(time);
            data.setData(name);

            if(!competition.equals((ChampionsTreeDto)competitionNode.getData())){
                competitionNode = new DefaultTreeNode(competition, root);
            }
            if(!instance.equals(instanceNode.getData())){
                instanceNode = new DefaultTreeNode(instance, competitionNode);
            }
            if(!leg.equals(legNode.getData())){
                legNode = new DefaultTreeNode(leg, instanceNode);
            }
            TreeNode dataNode = new DefaultTreeNode(data, legNode);
        }


        return root;
    }


    @Required
    public void setResultRepository(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }
}
