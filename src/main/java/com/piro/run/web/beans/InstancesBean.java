package com.piro.run.web.beans;

import com.piro.run.dto.*;
import com.piro.run.service.CompetitionService;
import com.piro.run.service.InstanceService;
import com.piro.run.service.ResultService;
import com.piro.run.service.impl.CompetitionServiceImpl;
import com.piro.run.service.impl.InstanceServiceImpl;
import com.piro.run.service.impl.ResultServiceImpl;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.util.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ppirovski on 12/18/14. In Code we trust
 */
public class InstancesBean implements Serializable {

    private transient InstanceService instanceService;
    private transient CompetitionService competitionService;
    private transient ResultService resultService;

    private CompetitionDto competitionDto;
    private List<InstanceDto> instances;
    private InstanceDto forCreate;

    //Public part
    private TreeNode root;
    private TreeNode selected;
    private List<ParticipantResultDto> results;
    private List<String> columns;

    public InstancesBean(CompetitionService competitionService, InstanceService instanceService, ResultService resultService){
        this.competitionService = competitionService;
        this.instanceService = instanceService;
        this.resultService = resultService;


        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();
        String competitionIdStr = parameterMap.get("competitionId");

        Long competitionId = -1L;
        if(!StringUtils.isEmpty(competitionIdStr)){
            try {
                competitionId = Long.valueOf(competitionIdStr);
            }
            catch (NumberFormatException e){}
        }

        if(competitionId != -1) {
            competitionDto = competitionService.getById(competitionId);
        }else{
            throw new IllegalArgumentException("cannot find competition");
        }

        forCreate = new InstanceDto();
        forCreate.setCompetitionId(competitionId);
        columns = new ArrayList<>();
    }

    public List<InstanceDto> getInstances() {
        if(instances == null){
            instances = instanceService.listByCompetition(getCompetitionDto());
        }
        return instances;
    }

    public void setInstances(List<InstanceDto> instances) {
        this.instances = instances;
    }

    public CompetitionDto getCompetitionDto() {
        return competitionDto;
    }

    public void setCompetitionDto(CompetitionDto competitionDto) {
        this.competitionDto = competitionDto;
    }

    public void onRowEdit(RowEditEvent event) {
        InstanceDto toUpdate = (InstanceDto)event.getObject();
        instanceService.update(toUpdate);
        FacesMessage msg = new FacesMessage("Instance Edited", "" );
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void delete(InstanceDto forDelete){
        if(forDelete.getResultsCount() > 0){
            return; //shouldn't delete instance that has results
        }
        instanceService.delete(forDelete.getId());
        instances.remove(forDelete);
        FacesMessage msg = new FacesMessage("Instance deleted", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void createNewInstance(){
        if(forCreate != null){
            InstanceDto created = instanceService.createNew(forCreate);
            instances.add(created);
            forCreate = new InstanceDto();
            forCreate.setCompetitionId(competitionDto.getId());
        }
        FacesMessage msg = new FacesMessage("New instance created", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        RequestContext.getCurrentInstance().execute("PF('createDialog').hide();");

    }

    public InstanceDto getForCreate() {
        return forCreate;
    }

    public void setForCreate(InstanceDto forCreate) {
        this.forCreate = forCreate;
    }

    public void redirectEdit(String id) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("legsCRUD.jsf?instanceId="+id);
    }

    // Public part

    public TreeNode getTree(){
        if(root == null){
            this.initTree();
        }
        return root;
    }

    private void initTree(){
        root = new DefaultTreeNode();
        boolean expanded = true;
        for(InstanceDto instanceDto : competitionDto.getInstances()){
            TreeNode instanceNode = new DefaultTreeNode(instanceDto, root);
            instanceNode.setExpanded(expanded);
            expanded = false;
            root.getChildren().add(instanceNode);
            for(LegDto legDto : instanceDto.getLegs()){
                TreeNode legNode = new DefaultTreeNode(legDto, instanceNode);
                instanceNode.getChildren().add(legNode);
            }
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        if(event.getTreeNode().getChildren().size() == 0) {
            selected = event.getTreeNode();
        }
    }

    public TreeNode getSelected() {
        return selected;
    }

    public void setSelected(TreeNode selected) {
        if(selected != null && (selected.getChildren() == null || selected.getChildren().size() == 0)) {
            LegDto legDto = (LegDto)selected.getData();
            LegDto oldSelected = null;
            if(this.selected != null) {
               oldSelected = (LegDto) this.selected.getData();
            }

            if(oldSelected == null || oldSelected.getId() != legDto.getId()) {
                this.selected = selected;
                this.initResultsAndColumns(legDto);
            }
        }
    }

    public List<ParticipantResultDto> getResults() {
        return results;
    }

    public List<String> getColumns() {
        return columns;
    }

    private void initResultsAndColumns(LegDto legDto){
        results = resultService.getResultsByLegGroupByParticipant(legDto);

        columns = new ArrayList<>();
        for(CheckPointDto checkPoint : legDto.getCheckPoints()) {
            String key = checkPoint.getName();
            columns.add(key);
        }
    }

    public boolean isLeaf(Object node){
        boolean result = node instanceof LegDto;
        return result;
    }

    public List<SelectItem> getSexes(){
        List<SelectItem> result = new ArrayList<>();
        result.add(new SelectItem(false,"Ж"));
        result.add(new SelectItem(true,"М") );
        return result;
    }
}
