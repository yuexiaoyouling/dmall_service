package com.dg.entity.VO;

import com.dg.entity.Domain.User;
import lombok.Data;

/**
 * @Author TheFool
 */
@Data
public class UserVO extends User {
    private String token;
}
