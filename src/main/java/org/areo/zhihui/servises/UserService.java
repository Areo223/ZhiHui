package org.areo.zhihui.servises;

import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.vo.LoginVO;
import org.areo.zhihui.pojo.vo.UserVO;
import org.areo.zhihui.utils.enums.RoleEnum;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Result<LoginVO> login(String identifier, String password);
    Result<Void> addUser(String identifier, String password,String name, RoleEnum role);

    Result<Void> deleteUser(Integer id);

    Result<Void> updateUser(Integer id, String name, String role, String identifier, String password);

    Result<UserVO> getUser(Integer id);

    Result<Object> getOwnUserInfo(Integer id);

}
