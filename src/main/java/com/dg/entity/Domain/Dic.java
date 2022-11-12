package com.dg.entity.Domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author TheFool
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_dic")
public class Dic {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 字典类型
     */
    private String dicType;
    /**
     * 字典标签
     */
    private String dicText;
    /**
     * 字典值
     */
    private String dicValue;
    /**
     * 排序
     */
    private String dicOrder;

    public static Dic fromObject(Object o) {
        Dic sysDic = new Dic();
        sysDic.setDicText(String.valueOf(o));
        return sysDic;
    }
}
