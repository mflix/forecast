package org.mflix.forecast.core;

import lombok.*;
import lombok.experimental.Accessors;
import org.mflix.forecast.component.CommonConstants;

import java.io.Serializable;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BaseRespones<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    //value = "返回标记：成功标记=0，失败标记=1")
    private int code;

    @Getter
    @Setter
    //value = "返回信息"
    private String message;


    @Getter
    @Setter
    //value = "数据"
    private T object;

    public static <T> BaseRespones<T> ok() {
        return restResult(null, CommonConstants.SUCCESS, null);
    }

    public static <T> BaseRespones<T> ok(T data) {
        return restResult(data, CommonConstants.SUCCESS, null);
    }

    public static <T> BaseRespones<T> ok(T data, String msg) {
        return restResult(data, CommonConstants.SUCCESS, msg);
    }

    public static <T> BaseRespones<T> failed() {
        return restResult(null, CommonConstants.FAIL, null);
    }

    public static <T> BaseRespones<T> failed(String msg) {
        return restResult(null, CommonConstants.FAIL, msg);
    }

    public static <T> BaseRespones<T> failed(T data) {
        return restResult(data, CommonConstants.FAIL, null);
    }

    public static <T> BaseRespones<T> failed(T data, String msg) {
        return restResult(data, CommonConstants.FAIL, msg);
    }

    private static <T> BaseRespones<T> restResult(T data, int code, String msg) {
        BaseRespones<T> apiResult = new BaseRespones<>();
        apiResult.setCode(code);
        apiResult.setObject(data);
        apiResult.setMessage(msg);
        return apiResult;
    }
}
