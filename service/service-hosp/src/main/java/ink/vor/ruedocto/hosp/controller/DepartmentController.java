package ink.vor.ruedocto.hosp.controller;

import ink.vor.ruedocto.common.result.Result;
import ink.vor.ruedocto.hosp.service.DepartmentService;
import ink.vor.ruedocto.vo.hosp.DepartmentVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author muquanrui
 * @date 2022/6/20 10:24
 */

@RestController
@RequestMapping("/admin/hosp/department")
// @CrossOrigin
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    // 根据医院编号，查询医院所有科室列表
    @ApiOperation(value = "查询医院所有科室列表")
    @GetMapping("getDeptList/{hoscode}")
    public Result getDeptList(@PathVariable String hoscode) {
        List<DepartmentVo> list = departmentService.findDeptTree(hoscode);
        return Result.ok(list);
    }
}
