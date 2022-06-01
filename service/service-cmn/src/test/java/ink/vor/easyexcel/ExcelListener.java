package ink.vor.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @author muquanrui
 * @date 2022/4/22 09:16
 */
public class ExcelListener extends AnalysisEventListener<UserData> {

    @Override
    public void invoke(UserData userData, AnalysisContext analysisContext) {
        System.out.println(userData);
    }

    @Override
    public void invokeHeadMap(Map<Integer,String> headMap, AnalysisContext context) {
        System.out.println("表头信息" + headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
