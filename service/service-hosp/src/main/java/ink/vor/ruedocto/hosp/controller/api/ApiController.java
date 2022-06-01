package ink.vor.ruedocto.hosp.controller.api;

import ink.vor.ruedocto.common.exception.RueDoctoException;
import ink.vor.ruedocto.common.helper.HttpRequestHelper;
import ink.vor.ruedocto.common.result.Result;
import ink.vor.ruedocto.common.result.ResultCodeEnum;
import ink.vor.ruedocto.common.util.MD5;
import ink.vor.ruedocto.hosp.service.DepartmentService;
import ink.vor.ruedocto.hosp.service.HospitalService;
import ink.vor.ruedocto.hosp.service.HospitalSetService;
import ink.vor.ruedocto.hosp.service.ScheduleService;
import ink.vor.ruedocto.model.hosp.Department;
import ink.vor.ruedocto.model.hosp.Hospital;
import ink.vor.ruedocto.model.hosp.Schedule;
import ink.vor.ruedocto.vo.hosp.DepartmentQueryVo;
import ink.vor.ruedocto.vo.hosp.ScheduleQueryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author muquanrui
 * @date 2022/4/27 08:44
 */

@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalSetService hospitalSetService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;

    // 查询医院接口
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request) {
        // 获取传递过来的医院信息
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(parameterMap);
        String hoscode = (String) paramMap.get("hoscode");
        // 1. 获取医院系统传递过来的签名
        String hospSign = (String) paramMap.get("sign");
        // 2. 根据传递过来的医院编码查询数据库得到签名
        String signKey = hospitalSetService.getSignKey(hoscode);
        // 3. 把数据库查询过来的签名进行MD5加密
        String signKeyMD5 = MD5.encrypt(signKey);
        // 4. 判断签名是否一致
        if (hospSign.equals(signKeyMD5)) {
            throw new RueDoctoException(ResultCodeEnum.SIGN_ERROR);
        }
        // 调用service方法实现根据医院编号查询
        Hospital hospital = hospitalService.getByHoscode(hoscode);
        return Result.ok(hospital);
    }

    // 上传医院接口
    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request) {
        // 获取传递过来的医院信息
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(parameterMap);
        // 1. 获取医院系统传递过来的签名
        String hospSign = (String) paramMap.get("sign");
        // 2. 根据传递过来的医院编码查询数据库得到签名
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        // 3. 把数据库查询过来的签名进行MD5加密
        String signKeyMD5 = MD5.encrypt(signKey);
        // 4. 判断签名是否一致
        if (hospSign.equals(signKeyMD5)) {
            throw new RueDoctoException(ResultCodeEnum.SIGN_ERROR);
        }

        // 传输过程中base64格式的图片"+"转换为了" "，需要转换回来
        String logoData = (String) paramMap.get("logoData");
        logoData = logoData.replaceAll(" ", "+");
        paramMap.put("logoData", logoData);
        // 调用service方法
        hospitalService.save(paramMap);
        return Result.ok();
    }

    // 上传科室接口
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request) {
        // 获取传递过来的科室信息
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(parameterMap);
        // 1. 获取医院编号
        String hospSign = (String) paramMap.get("hoscode");
        // 2. 根据传递过来的医院编码查询数据库得到签名
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        // 3. 把数据库查询过来的签名进行MD5加密
        String signKeyMD5 = MD5.encrypt(signKey);
        // 4. 判断签名是否一致
        if (hospSign.equals(signKeyMD5)) {
            throw new RueDoctoException(ResultCodeEnum.SIGN_ERROR);
        }
        // 调用service方法
        departmentService.save(paramMap);
        return Result.ok();
    }

    // 查询科室接口
    @PostMapping("department/list")
    public Result findDepartment(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String)paramMap.get("hoscode");
        //非必填
        String depcode = (String)paramMap.get("depcode");
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 10 : Integer.parseInt((String)paramMap.get("limit"));
        if(StringUtils.isEmpty(hoscode)) {
            throw new RueDoctoException(ResultCodeEnum.PARAM_ERROR);
        }
        //签名校验
        if(!HttpRequestHelper.isSignEquals(paramMap, hospitalSetService.getSignKey(hoscode))) {
            throw new RueDoctoException(ResultCodeEnum.SIGN_ERROR);
        }
        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        departmentQueryVo.setDepcode(depcode);
        Page<Department> pageModel = departmentService.findPageDepartment(page, limit, departmentQueryVo);
        return Result.ok(pageModel);
    }

    // 删除科室接口
    @ApiOperation(value = "删除科室")
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        // 必须参数校验 略
        String hoscode = (String)paramMap.get("hoscode");
        // 必填
        String depcode = (String)paramMap.get("depcode");
        if(StringUtils.isEmpty(hoscode)) {
            throw new RueDoctoException(ResultCodeEnum.PARAM_ERROR);
        }
        // 签名校验
        if(!HttpRequestHelper.isSignEquals(paramMap, hospitalSetService.getSignKey(hoscode))) {
            throw new RueDoctoException(ResultCodeEnum.SIGN_ERROR);
        }
        departmentService.removeDepartment(hoscode, depcode);
        return Result.ok();
    }

    // 上传排班接口
    @ApiOperation(value = "上传排班")
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        String hoscode = (String)paramMap.get("hoscode");
        if(StringUtils.isEmpty(hoscode)) {
            throw new RueDoctoException(ResultCodeEnum.PARAM_ERROR);
        }
        if(!HttpRequestHelper.isSignEquals(paramMap, hospitalSetService.getSignKey(hoscode))) {
            throw new RueDoctoException(ResultCodeEnum.SIGN_ERROR);
        }
        scheduleService.save(paramMap);
        return Result.ok();
    }

    @ApiOperation(value = "获取排班分页列表")
    @PostMapping("schedule/list")
    public Result schedule(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        String hoscode = (String)paramMap.get("hoscode");
        String depcode = (String)paramMap.get("depcode");
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 10 : Integer.parseInt((String)paramMap.get("limit"));

        if(StringUtils.isEmpty(hoscode)) {
            throw new RueDoctoException(ResultCodeEnum.PARAM_ERROR);
        }
        if(!HttpRequestHelper.isSignEquals(paramMap, hospitalSetService.getSignKey(hoscode))) {
            throw new RueDoctoException(ResultCodeEnum.SIGN_ERROR);
        }

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);
        Page<Schedule> pageModel = scheduleService.selectPage(page , limit, scheduleQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "删除科室")
    @PostMapping("schedule/remove")
    public Result removeSchedule(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        String hoscode = (String)paramMap.get("hoscode");
        String hosScheduleId = (String)paramMap.get("hosScheduleId");
        if(StringUtils.isEmpty(hoscode)) {
            throw new RueDoctoException(ResultCodeEnum.PARAM_ERROR);
        }
        if(!HttpRequestHelper.isSignEquals(paramMap, hospitalSetService.getSignKey(hoscode))) {
            throw new RueDoctoException(ResultCodeEnum.SIGN_ERROR);
        }

        scheduleService.remove(hoscode, hosScheduleId);
        return Result.ok();
    }
}
