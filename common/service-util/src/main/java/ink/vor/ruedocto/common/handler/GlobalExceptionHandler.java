package ink.vor.ruedocto.common.handler;

/**
 * @author muquanrui
 * @date 2022/4/16 07:52
 */

import ink.vor.ruedocto.common.exception.RueDoctoException;
import ink.vor.ruedocto.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }

    /**
     * 自定义异常处理方法
     * @param e
     * @return
     */
    @ExceptionHandler(RueDoctoException.class)
    @ResponseBody
    public Result error(RueDoctoException e){
        return Result.build(e.getCode(), e.getMessage());
    }
}
