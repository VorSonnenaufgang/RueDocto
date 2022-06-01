package ink.vor.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author muquanrui
 * @date 2022/4/22 08:37
 */

@Data
public class UserData {
    @ExcelProperty(value = "用户编号", index = 0)
    private int uid;
    @ExcelProperty(value = "用户名称", index = 1)
    private String username;
}
