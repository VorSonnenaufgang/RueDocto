package ink.vor.easyexcel;

import com.alibaba.excel.EasyExcel;

/**
 * @author muquanrui
 * @date 2022/4/22 09:18
 */
public class TestRead {
    public static void main(String[] args) {
        // 设置excel文件路径和文件名称
        String fileName = "/Users/muquanrui/Documents/RueDocto/excel/01.xlsx";
        EasyExcel.read(fileName, UserData.class, new ExcelListener()).sheet().doRead();
    }
}
