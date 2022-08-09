package com.github.industrialcraft.betterwithplugins.items;

import com.google.common.collect.HashBiMap;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomModelDataAssigner {
    private HashBiMap<String,Integer> customModelDatas;
    public CustomModelDataAssigner() {
        this.customModelDatas = HashBiMap.create();
    }
    public int getFor(CustomItem customItem){
        String id = customItem.getKey().toString();
        Integer customModelData = customModelDatas.get(id);
        if(customModelData != null)
            return customModelData;
        int i = 1;
        while (true){
            if(!customModelDatas.containsValue(i)){
                customModelDatas.put(id, i);
                return i;
            }
            i++;
        }
    }
    public void load(FileConfiguration config){
        customModelDatas.clear();
        List<String> cmd = config.getStringList("customModelDatas");
        if(cmd == null)
            return;
        for(String str : cmd){
            String[] split = str.split("-");
            if(split.length != 2)
                continue;
            NamespacedKey key = NamespacedKey.fromString(split[0]);
            if(key == null)
                continue;
            int cmdi;
            try{
                cmdi = Integer.parseInt(split[1]);
            } catch (Exception e){continue;}
            customModelDatas.put(key.toString(), cmdi);
        }
    }
    public void save(Configuration config){
        List<String> cmd = new ArrayList<>();
        for(Map.Entry<String,Integer> e : customModelDatas.entrySet()){
            cmd.add(e.getKey() + "-" + e.getValue());
        }
        config.set("customModelDatas", cmd);
    }
}
