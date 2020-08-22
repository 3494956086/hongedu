package com.hong.servicebase.handle;

import com.hong.commonutils.result.R;
import com.hong.servicebase.exception.HongEduException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HongEduException.class)
    @ResponseBody
    public R error(HongEduException e) {
        log.error(e.getMessage());

        return R.error().message(e.getMsg()).code(e.getCode());
    }
}
