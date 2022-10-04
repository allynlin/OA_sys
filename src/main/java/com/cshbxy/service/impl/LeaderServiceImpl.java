package com.cshbxy.service.impl;

import com.cshbxy.mapper.LeaderMapper;
import com.cshbxy.service.LeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("LeaderService")
public class LeaderServiceImpl implements LeaderService {

    @Autowired
    private LeaderMapper leaderMapper;

    @Override
    public String findRealeName(String uid) {
        return leaderMapper.findRealeName(uid);
    }
}
