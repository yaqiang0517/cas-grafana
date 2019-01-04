package com.fosun.esutil.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName BizException
 * @Description TODO
 * @Author zhangyq
 * @Date 2019/1/3 10:49
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BizException extends RuntimeException {

    private int code;
    private String message;

}
