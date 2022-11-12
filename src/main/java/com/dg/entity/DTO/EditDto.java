package com.dg.entity.DTO;

import com.dg.entity.Request.EditReq;
import lombok.Data;

/**
 * @Author TheFool
 */
@Data
public class EditDto extends EditReq {
    private String primaryKey;
}
