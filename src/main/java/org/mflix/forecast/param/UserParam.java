package org.mflix.forecast.param;

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
    private String id;
    private String nickname;
    private String email;
    private String avatar;
    private String role;
    private String sex;
    private String status;
    private Long score;
}
