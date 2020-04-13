package com.pauldaniv.template.service;

import com.pauldaniv.template.api.TestOneService;
import com.pauldaniv.template.model.TestOne;
import org.springframework.stereotype.Service;

@Service
public class TestOneServiceImpl implements TestOneService {
    @Override
    public TestOne getIt() {
        throw new RuntimeException();
    }
}
