package ink.vor.ruedocto.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ink.vor.ruedocto.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author muquanrui
 * @date 2022/4/21 17:30
 */
public interface DictService extends IService<Dict> {
    List<Dict> findChildData(Long id);

    void exportDict(HttpServletResponse response);

    void importDict(MultipartFile file);

    /**
     * 根据上级编码与值获取数据字典名称
     * @param parentDictCode
     * @param value
     * @return
     */
    String getNameByParentDictCodeAndValue(String parentDictCode, String value);

    List<Dict> findByDictCode(String dictCode);
}
