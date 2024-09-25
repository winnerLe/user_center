package com.kyle.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -1113382795337684803L;
    private String useraccount;
    private String userpassword;
}
