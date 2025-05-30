package com.dsl.vpp.weight.service;

import com.dsl.vpp.weight.value.WeightInfo;

import java.util.List;

public interface WeightService {
    List<WeightInfo> getLatestWeightsByWindowSize(String derId, Integer windowSize);
}
