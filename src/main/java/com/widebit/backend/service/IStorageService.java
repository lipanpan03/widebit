package com.widebit.backend.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

public interface IStorageService {

    JSONObject addFridge(String fridgeId, String environment, int drawerCount, int dishxCount, int dishyCount);

    JSONObject addDrawer(int id,int drawer);

    JSONObject findAllFridges();

    JSONObject notFoundData();

    JSONObject findDrawers(String fridgeId);

    JSONObject findDishesByFridgeIDAndDrawerId(String fridgeId,int drawerId);
}
