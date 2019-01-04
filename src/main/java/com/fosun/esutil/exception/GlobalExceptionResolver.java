package com.fosun.esutil.exception;

import com.fosun.esutil.bean.CommResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName BizException
 * @Description TODO
 * @Author zhangyq
 * @Date 2019/1/2 16:51
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionResolver {

    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public CommResult catchBizException(BizException e){
        log.info("catchBizException : " + e.getMessage());
        return new CommResult(-1,e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommResult catchException(Exception e){
        log.info("catchException : " + e.getMessage());
        e.printStackTrace();
        return new CommResult(-1,e.getMessage());
    }
}
