package com.example.aeon.service;


import com.example.aeon.dao.KaryawanTrainingRequest;

import java.util.Map;

public interface KaryawanTrainingService {

    public Map insert(KaryawanTrainingRequest obj);

    public Map update(KaryawanTrainingRequest obj);

    public Map delete(Long obj);
}
