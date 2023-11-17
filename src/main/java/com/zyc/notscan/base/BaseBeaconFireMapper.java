package com.zyc.notscan.base;

import com.zyc.notscan.BaseMapper;

public interface BaseBeaconFireMapper<T> extends BaseMapper<T> {
    @Override
    default String getTable(){
        return "beacon_fire_info";
    }
}