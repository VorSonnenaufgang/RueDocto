package ink.vor.ruedocto.cmn.controller;

import ink.vor.ruedocto.cmn.service.DictService;
import ink.vor.ruedocto.common.result.Result;
import ink.vor.ruedocto.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author muquanrui
 * @date 2022/4/22 07:45
 */

@Api(value = "数据字典接口")
@RestController
@RequestMapping("/admin/cmn/dict")
// @CrossOrigin
public class DictController {
    @Autowired
    private DictService dictService;

    // 导入数据字典
    @PostMapping("importData")
    public Result importDict(MultipartFile file) {
        dictService.importDict(file);
        return Result.ok();
    }

    // 导出数据字典接口
    @GetMapping("exportData")
    public Result exportDict(HttpServletResponse response) {
        dictService.exportDict(response);
        return Result.ok();
    }

    //根据数据id查询子数据列表
    @ApiOperation(value = "根据数据id查询子数据列表")
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id) {
        List<Dict> childData = dictService.findChildData(id);
        return Result.ok(childData);
    }

    @ApiOperation(value = "获取数据字典名称")
    @GetMapping(value = "/getName/{parentDictCode}/{value}")
    public String getName(
            @ApiParam(name = "parentDictCode", value = "上级编码", required = true)
            @PathVariable("parentDictCode") String parentDictCode,

            @ApiParam(name = "value", value = "值", required = true)
            @PathVariable("value") String value) {
        return dictService.getNameByParentDictCodeAndValue(parentDictCode, value);
    }

    @ApiOperation(value = "获取数据字典名称")
    @ApiImplicitParam(name = "value", value = "值", required = true, dataType = "Long", paramType = "path")
    @GetMapping(value = "/getName/{value}")
    public String getName(
            @ApiParam(name = "value", value = "值", required = true)
            @PathVariable("value") String value) {
        return dictService.getNameByParentDictCodeAndValue("", value);
    }

    @ApiOperation(value = "根据dictCode获取下级节点")
    @GetMapping(value = "/findByDictCode/{dictCode}")
    public Result<List<Dict>> findByDictCode(
            @ApiParam(name = "dictCode", value = "节点编码", required = true)
            @PathVariable String dictCode) {
        List<Dict> list = dictService.findByDictCode(dictCode);
        return Result.ok(list);
    }
}
