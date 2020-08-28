package com.widebit.backend.service;

import net.sf.json.JSONObject;

public interface ILibraryService {

    JSONObject errorResult(String error,String errorDescription);
    JSONObject getProjectInformation(int id);
}
