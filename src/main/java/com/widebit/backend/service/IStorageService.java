package com.widebit.backend.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

public interface IStorageService {

    JSONObject addFridge(int id, String environment, int drawerCount, int dishxCount, int dishyCount);

    JSONObject addDrawer(int id,int drawer);

    JSONObject findAllFridges();

    JSONObject notFoundData();

    JSONObject findDrawers(int fridgeId);

    JSONObject findDishesByFridgeIDAndDrawerId(int fridgeId,int drawerId);
}
