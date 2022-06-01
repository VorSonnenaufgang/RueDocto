package ink.vor.ruedocto.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import ink.vor.ruedocto.cmn.mapper.DictMapper;
import ink.vor.ruedocto.model.cmn.Dict;
import ink.vor.ruedocto.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;

/**
 * @author muquanrui
 * @date 2022/4/22 09:39
 */
public class DictListener extends AnalysisEventListener<DictEeVo> {

    private DictMapper dictMapper;

    public DictListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo, dict);
        dictMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
