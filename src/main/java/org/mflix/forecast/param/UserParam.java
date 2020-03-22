package org.mflix.forecast.param;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
/*{
        "id": "用户唯一识别码",
        "nickname": "用户昵称",
        "email": "用户邮箱",
        "avatar": "用户头像地址，可以仅文件名，前台拼接全路径",
        "role": "用户角色",
        "sex": "用户性别",
        "status": "用户状态：未激活，已激活",
        "score": 0  //用户积分
}*/

@Data
public class UserParam {
    @JSONField(serialzeFeatures= {SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty})
    private String id;
    @JSONField(serialzeFeatures= {SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty})
    private String nickname;
    @JSONField(serialzeFeatures= {SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty})
    private String email;
    @JSONField(serialzeFeatures= {SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty})
    private String avatar;
    @JSONField(serialzeFeatures= {SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty})
    private String role;
    @JSONField(serialzeFeatures= {SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty})
    private String sex;
    @JSONField(serialzeFeatures= {SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty})
    private String status;
    @JSONField(serialzeFeatures= {SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty})
    private Long score;
}
