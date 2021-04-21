package com.wallfacer5.giproject.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wallfacer5.giproject.entity.Player;
import com.wallfacer5.giproject.utils.SourceAPI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerService {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    SourceAPI sourceAPI;

    public String getPlayerInfoByUid(String uid){
        return sourceAPI.getPlayerInfo(uid);
        // return GenshinImpact.getPlayerInfoByUid(uid, headerConfig);
    }

    public Player getPlayerInfoByUid(Long uid){
        JSONObject data = JSON.parseObject(sourceAPI.getPlayerInfo(Long.toString(uid))).getJSONObject("data");
        
        List<Long> characterIds = new ArrayList<>();
        JSONArray characterArr = data.getJSONArray("avatars");
        characterArr.forEach(charater -> {
            characterIds.add(((JSONObject)charater).getLongValue("id"));
        });
        
        Player.Stats stats = JSON.parseObject(data.getString("stats"), Player.Stats.class);

        Player player = new Player(uid, characterIds, stats);
        return player;
    }
}
