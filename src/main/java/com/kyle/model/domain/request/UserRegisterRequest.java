package com.kyle.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户注册请求体
 */
@Data
public class UserRegisterRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 232637130855708549L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String planteCode;
}
