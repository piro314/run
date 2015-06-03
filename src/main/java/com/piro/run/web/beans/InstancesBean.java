package com.piro.run.web.beans;

import com.piro.run.dto.*;
import com.piro.run.entity.Instance;
import com.piro.run.exception.ResourceNotFoundException;
import com.piro.run.service.CompetitionService;
import com.piro.run.service.InstanceService;
import com.piro.run.service.ResultService;
import com.piro.run.service.impl.CompetitionServiceImpl;
import com.piro.run.service.impl.InstanceServiceImpl;
import com.piro.run.service.impl.ResultServiceImpl;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.data.SortEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.SortMeta;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.*;
import org.springframework.util.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

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

    private LineChartModel legGraphModel;

    private ListDataModel savedModel;

    public InstancesBean(CompetitionService competitionService, InstanceService instanceService, ResultService resultService) throws ResourceNotFoundException {
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
            catch (NumberFormatException e){
                throw new ResourceNotFoundException();
            }
        }

        if(competitionId != -1) {
            competitionDto = competitionService.getById(competitionId);
            if(competitionDto == null){
                throw new ResourceNotFoundException();
            }
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

    public LineChartModel getLegGraphModel() {
        return legGraphModel;
    }

    private void initTree(){
        root = new DefaultTreeNode();
        boolean expanded = true;
        List<InstanceDto> instances = competitionDto.getInstances();
        Collections.sort(instances);
        for(InstanceDto instanceDto : instances){
            TreeNode instanceNode = new DefaultTreeNode(instanceDto, root);
            instanceNode.setExpanded(expanded);
            expanded = false;
            root.getChildren().add(instanceNode);
            List<LegDto> legs = instanceDto.getLegs();
            Collections.sort(legs);
            for(LegDto legDto : legs){
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
        Collections.sort(results);

        getSexes();
        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("instancesForm:resultsTable");
        dataTable.reset();

        legGraphModel = new LineChartModel();
        LineChartSeries series = new LineChartSeries();
        series.setFill(true);
        legGraphModel.setShowPointLabels(true);
        legGraphModel.getAxes().put(AxisType.X, new CategoryAxis("Междинни точки"));
        Axis yAxis = legGraphModel.getAxis(AxisType.Y);
        yAxis.setLabel("Височина (м)");

        columns = new ArrayList<>();
        for(CheckPointDto checkPoint : legDto.getCheckPoints()) {
            String key = checkPoint.getName();
            columns.add(key);

            String checkPointName = checkPoint.getName();
            int distanceKm = checkPoint.getDistanceFromStart()/1000;
            series.set(distanceKm+"км "+checkPointName, checkPoint.getAltitude());
        }
        legGraphModel.addSeries(series);

    }

    public boolean isLeaf(Object node){
        boolean result = node instanceof LegDto;
        return result;
    }

    public List<SelectItem> getSexes(){
        List<SelectItem> result = new ArrayList<>();
        result.add(new SelectItem(null," "));
        result.add(new SelectItem(false,"Ж"));
        result.add(new SelectItem(true,"М") );
        return result;
    }

}
