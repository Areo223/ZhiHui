package org.areo.zhihui.services;

import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.College;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CollegeService {
    Result<List<College>> getAllCollege();
}
