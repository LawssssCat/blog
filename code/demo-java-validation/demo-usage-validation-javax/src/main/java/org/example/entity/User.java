package org.example.entity;

import lombok.Builder;
import lombok.Data;
import org.example.entity.validation.MyUrl;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class User {
    public static interface Insert {}
    public static interface Update {}

    @NotNull(groups = {
            Update.class, // 全部注解默认 Default 组。如果校验时指定组，则只执行对应组的校验。
    })
    private Long id;
    @NotBlank(groups = {
            Insert.class
    })
    private String username;
    @Min(value = 0, message = "年龄大于{value}岁")
    private Integer age;
    @PastOrPresent
    private LocalDateTime birthDay;
    @Email
    private String email;
    @Pattern(regexp = "^\\p{Print}+$", message = "手机号输入错误")
    private String phone;
    /**
     * 个人网站
     */
    private List<@MyUrl String> urls;
}
