package com.ntx.demo.service;

import com.ntx.demo.mapper.DemoMapper;
import com.ntx.demo.vo.TestBodyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class DemoService {

    @Autowired
    private DemoMapper demoMapper;

    @Transactional
    public int save(TestBodyVo testBodyVo){
        int result = this.demoMapper.save(testBodyVo);
        return result;
    }

}
