package com.py.exception;

import com.py.util.Msg;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * @作者: zgy
 * @描述: 全局异常处理器
 */
@RestController
@RestControllerAdvice
public class ExceptionHandlers {


    /**
     * @作者 zgy
     * @描述 空指针异常处理
     */
    @ExceptionHandler(NullPointerException.class)
    public Msg nullException() {
        return Msg.fail("参数有空值或查询对象不存在");
    }

    /**
     * 获取其它异常。包括500
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public Msg defaultErrorHandler(Exception e) {
        e.printStackTrace();
        return Msg.fail("未知错误");
    }

    /**
     * @作者 zgy
     * @描述 类型转换异常处理
     */
    @ExceptionHandler(value = ClassCastException.class)
    public Msg classNotfound() {
        return Msg.fail("参数类型有误");
    }

    /**
     * @作者 zgy
     * @描述 操作数据库异常
     */
    @ExceptionHandler(value = SQLException.class)
    public Msg exception() {
        return Msg.fail("操作数据库异常");
    }

    /**
     * @作者 zgy
     * @描述 数字格式异常。当试图将一个String转换为指定的数字类型，而该字符串确不满足数字类型要求的格式时，抛出该异常。
     */
    @ExceptionHandler(value = NumberFormatException.class)
    public Msg exception2() {
        return Msg.fail("数字格式异常。当试图将一个String转换为指定的数字类型，而该字符串确不满足数字类型要求的格式时，抛出该异常。");
    }

    /**
     * @作者 zgy
     * @描述 算术条件异常。譬如：整数除零等。
     */
    @ExceptionHandler(value = ArithmeticException.class)
    public Msg exception3() {
        return Msg.fail("算术条件异常。譬如：整数除零等。");
    }


}
