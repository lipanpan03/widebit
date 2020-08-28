package com.widebit.backend.service;

import com.widebit.backend.entity.ProjectDishEntity;
import com.widebit.backend.entity.ProjectDrawerEntity;
import com.widebit.backend.entity.ProjectFridgeEntity;
import com.widebit.backend.repository.DishRepository;
import com.widebit.backend.repository.DrawerRepository;
import com.widebit.backend.repository.FridgeRepository;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Service
public class StorageService implements IStorageService {

    @Autowired
    private FridgeRepository fridgeRepository;

    @Autowired
    private DrawerRepository drawerRepository;

    @Autowired
    private DishRepository dishRepository;

    @Override
    public JSONObject addFridge(String fridgeId, String environment, int drawerCount, int dishxCount, int dishyCount) {
        JSONObject result = new JSONObject();
        if (fridgeRepository.findByFridgeId(fridgeId)!=null){
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.put("code","DUPLICATE_ERROR");
            JSONArray error_description = new JSONArray();
            error_description.add("冰箱id已存在");
            result.put("error_description",error_description);
            return result;
        }
        ProjectFridgeEntity fridgeEntity = new ProjectFridgeEntity();
        fridgeEntity.setFridgeId(fridgeId);
        fridgeEntity.setEnvironment(environment);
        fridgeEntity.setDrawerCount(drawerCount);
        fridgeEntity.setDishxCount(dishxCount);
        fridgeEntity.setDishyCount(dishyCount);
        fridgeRepository.save(fridgeEntity);
        result.put("code","SUCCESS");
        JSONArray data = new JSONArray();
        JSONObject dataItem = new JSONObject();
        dataItem.put("id",fridgeId);
        dataItem.put("environment",environment);
        dataItem.put("drawer_count",drawerCount);
        dataItem.put("occupancy",0);
        data.add(dataItem);
        result.put("data",data);
        return result;
    }

    @Override
    public JSONObject addDrawer(int id, int drawer) {
        JSONObject result = new JSONObject();
        Optional<ProjectFridgeEntity> fridgeRepositoryById = fridgeRepository.findById(id);
        if (!fridgeRepositoryById.isPresent()){
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.put("code","NOT_FOUND");
            JSONArray error_description = new JSONArray();
            error_description.add("冰箱未找到");
            result.put("error_description",error_description);
            return result;
        }
        int drawerCount = fridgeRepositoryById.get().getDrawerCount();
        int drawerOrder = drawerRepository.findAllByFridgeId(id).size();
        result.put("code","SUCCESS");
        JSONArray data = new JSONArray();
        for (int i=1;i<=drawer;i++){
            ProjectDrawerEntity drawerEntity = new ProjectDrawerEntity();
            drawerEntity.setId(id);
            drawerEntity.setDrawerOrder(drawerOrder+i);
            drawerRepository.save(drawerEntity);
            JSONObject dataItem = new JSONObject();
            dataItem.put("id",drawerOrder+i);
            dataItem.put("drawer_count",drawerCount);
            dataItem.put("occupancy",0);
            data.add(dataItem);
        }
        result.put("data",data);
        return result;
    }

    @Override
    public JSONObject findAllFridges() {
        List<ProjectFridgeEntity> fridgeEntities = fridgeRepository.findAll();
        JSONObject result = new JSONObject();
        result.put("code","SUCCESS");
        JSONArray data = new JSONArray();
        for (ProjectFridgeEntity fridgeEntity:fridgeEntities){
            JSONObject dataItem = new JSONObject();
            dataItem.put("id",fridgeEntity.getId());
            dataItem.put("environment",fridgeEntity.getEnvironment());
            List<ProjectDrawerEntity> drawerEntityList = drawerRepository.findAllByFridgeId(fridgeEntity.getId());
            dataItem.put("drawer_count",drawerEntityList.size());
            if (drawerEntityList.size()!=0){
                int count = 0;
                for (ProjectDrawerEntity drawerEntity:drawerEntityList){
                    count += dishRepository.findAllByDrawerId(drawerEntity.getId()).size();
                }
                DecimalFormat df = new DecimalFormat("0.00");
                dataItem.put("occupancy",df.format(count/(drawerEntityList.size()*fridgeEntity.getDrawerCount()*fridgeEntity.getDishxCount()*fridgeEntity.getDishyCount()*1.0)));
            }else {
                dataItem.put("occupancy",0);
            }
            data.add(dataItem);
        }
        result.put("data",data);
        return result;
    }

    @Override
    public JSONObject notFoundData(){
        JSONObject result = new JSONObject();
        result.put("data",new JSONObject());
        result.put("code","INTERNAL_ERROR");
        JSONArray errorDescription = new JSONArray();
        errorDescription.add("NOT FOUND");
        result.put("error_description",errorDescription);
        return result;
    }

    @Override
    public JSONObject findDrawers(String fridgeId) {
        ProjectFridgeEntity fridgeEntity = fridgeRepository.findByFridgeId(fridgeId);
        if (fridgeEntity==null) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return notFoundData();
        }
        int drawCount = fridgeEntity.getDrawerCount();
        int dishxCount = fridgeEntity.getDishxCount();
        int dishyCount = fridgeEntity.getDishyCount();
        List<ProjectDrawerEntity> allByFridgeId = drawerRepository.findAllByFridgeId(fridgeEntity.getId());
        JSONObject result = new JSONObject();
        result.put("code","SUCCESS");
        JSONArray data = new JSONArray();
        for (ProjectDrawerEntity drawerEntity:allByFridgeId){
            JSONObject dataItem = new JSONObject();
            dataItem.put("id",drawerEntity.getDrawerOrder());
            dataItem.put("drawer_count",drawCount);
            dataItem.put("dishx_count",dishxCount);
            dataItem.put("dishy_count",dishyCount);
            List<ProjectDishEntity> allByDrawerId = dishRepository.findAllByDrawerId(drawerEntity.getId());
            DecimalFormat df = new DecimalFormat("0.00");
            dataItem.put("occupancy",df.format(allByDrawerId.size()*1.0/(fridgeEntity.getDrawerCount()*fridgeEntity.getDishxCount()*fridgeEntity.getDishyCount())));
            data.add(dataItem);
        }
        result.put("data",data);
        return result;
    }

    @Override
    public JSONObject findDishesByFridgeIDAndDrawerId(String fridgeId, int drawerId) {
        ProjectFridgeEntity fridgeEntity = fridgeRepository.findByFridgeId(fridgeId);
        if (fridgeEntity==null) return notFoundData();
        ProjectDrawerEntity byFridgeIdAndDrawerOrder = drawerRepository.findByFridgeIdAndDrawerOrder(fridgeEntity.getId(), drawerId);
        if (byFridgeIdAndDrawerOrder==null) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return notFoundData();
        }
        List<ProjectDishEntity> dishEntities = dishRepository.findAllByDrawerId(byFridgeIdAndDrawerOrder.getId());
        JSONObject result = new JSONObject();
        result.put("code","SUCCESS");
        JSONArray data = new JSONArray();
        for (ProjectDishEntity dishEntity:dishEntities){
            JSONObject dataItem = new JSONObject();
            dataItem.put("id",dishEntity.getDishOrder());
            dataItem.put("dishx",dishEntity.getDishx());
            dataItem.put("dishy",dishEntity.getDishy());
            data.add(dataItem);
        }
        result.put("data",data);
        return result;
    }
}
