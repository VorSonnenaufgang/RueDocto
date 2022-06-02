package ink.vor.ruedocto.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ink.vor.ruedocto.cmn.listener.DictListener;
import ink.vor.ruedocto.cmn.mapper.DictMapper;
import ink.vor.ruedocto.cmn.service.DictService;
import ink.vor.ruedocto.model.cmn.Dict;
import ink.vor.ruedocto.model.hosp.HospitalSet;
import ink.vor.ruedocto.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author muquanrui
 * @date 2022/4/21 17:30
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    @Override
    @Cacheable(value = "dict", keyGenerator = "keyGenerator")
    public List<Dict> findChildData(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        List<Dict> dicts = baseMapper.selectList(wrapper);
        for (Dict dict : dicts) {
            dict.setHasChildren(hasChildren(dict.getId()));
        }
        return dicts;
    }

    @Override
    public void exportDict(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            // String fileName = URLEncoder.encode("数据字典", "UTF-8");
            String fileName = "dict";
            response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");

            // 查询数据库
            List<Dict> dictList = baseMapper.selectList(null);
            List<DictEeVo> dictVoList = new ArrayList<>(dictList.size());
            for(Dict dict : dictList) {
                DictEeVo dictVo = new DictEeVo();
                BeanUtils.copyProperties(dict, dictVo, DictEeVo.class);
                dictVoList.add(dictVo);
            }
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("dict").doWrite(dictVoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @CacheEvict(value = "dict", allEntries = true)
    public void importDict(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DictEeVo.class, new DictListener(baseMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 判断ID下是否有子数据
    private boolean hasChildren(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }

    @Cacheable(value = "dict",keyGenerator = "keyGenerator")
    @Override
    public String getNameByParentDictCodeAndValue(String parentDictCode, String value) {
    // 如果value能唯一定位数据字典，parentDictCode可以传空，例如：省市区的value值能够唯一确定
        if(StringUtils.isEmpty(parentDictCode)) {
            Dict dict = baseMapper.selectOne(new QueryWrapper<Dict>().eq("value", value));
            if(null != dict) {
                return dict.getName();
            }
        } else {
            Dict parentDict = this.getByDictsCode(parentDictCode);
            if(null == parentDict) return "";
            Dict dict = baseMapper.selectOne(new QueryWrapper<Dict>().eq("parent_id", parentDict.getId()).eq("value", value));
            if(null != dict) {
                return dict.getName();
            }
        }
        return "";
    }

    private Dict getByDictsCode(String dictCode) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_code", dictCode);
        Dict codeDict = baseMapper.selectOne(wrapper);
        return codeDict;
    }

    @Override
    public List<Dict> findByDictCode(String dictCode) {
        Dict codeDict = this.getByDictsCode(dictCode);
        if(null == codeDict) return null;
        return this.findChildData(codeDict.getId());
    }
}