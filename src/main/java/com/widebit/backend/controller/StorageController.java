package com.widebit.backend.controller;

import com.widebit.backend.service.IStorageService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class StorageController {

    @Autowired
    private IStorageService storageService;

    @PostMapping(value = "/laboratory/storage/addition/fridge")
    public JSONObject addFridge(@RequestParam("fridge_id") String fridgeId, @RequestParam("environment") String environment, @RequestParam("drawer_count") int drawerCount, @RequestParam("dishx_count") int dishxCount, @RequestParam("dishy_count") char dishyCount){
        return storageService.addFridge(fridgeId,environment,drawerCount,dishxCount,dishyCount-'A'+1);
    }

    @PostMapping(value = "/laboratory/storage/addition/drawer")
    public JSONObject addDrawer(@RequestParam("id")int id,@RequestParam("drawer")int drawer){
        return storageService.addDrawer(id,drawer);
    }

    @PostMapping(value = "/laboratory/storage/fridges/")
    public JSONObject findFridges(@RequestParam("search_token")String search_token,@RequestParam("search_field")String search_field){
        if ("ALL".equals(search_field)&&"ALL".equals(search_token))
            return storageService.findAllFridges();
        return storageService.notFoundData();
    }

    @PostMapping(value = "/laboratory/storage/drawers/")
    public JSONObject findDrawers(@RequestParam("fridge_id")String fridgeId){
        return storageService.findDrawers(fridgeId);
    }

    @PostMapping(value = "/laboratory/storage/dishes/")
    public JSONObject findDishes(@RequestParam("fridge_id")String fridge_id,@RequestParam("drawer_id")int drawer_id){
        return storageService.findDishesByFridgeIDAndDrawerId(fridge_id,drawer_id);
    }
}
