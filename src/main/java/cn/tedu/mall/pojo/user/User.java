package cn.tedu.mall.pojo.user;

import cn.tedu.mall.pojo.domain.UserAuthority;
import cn.tedu.mall.utils.ConstUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName User
 * @Version 1.0
 * @Description 寫入資料庫的用戶資料
 * @Date 2022/12/21、下午9:35
 */
@Data
public class User {

    @ApiModelProperty(value = "用戶id")
    private Long id;

    @ApiModelProperty(value = "用戶名稱")
    private String username;

    @ApiModelProperty(value = "用戶密碼")
    private String password;

    @ApiModelProperty(value = "用戶暱稱")
    private String nickname;

    @ApiModelProperty(value = "用戶電子信箱")
    private String email;

    @ApiModelProperty(value = "用戶手機")
    private String phone;

    @ApiModelProperty(value = "用戶性別")
    private Character sex;

    @ApiModelProperty(value = "用戶生日")
    private LocalDate bod;

    @ApiModelProperty(value = "是否啟用")
    private Integer isEnable;

    @ApiModelProperty(value = "紅利積分")
    private Integer rewardPoint;

    @ApiModelProperty(value = "用戶角色")
    private Integer roleId;

    @ApiModelProperty(value = "修改日期")
    private LocalDateTime gmtModified;

    @ApiModelProperty(value = "最後一次登入時間")
    private LocalDateTime gmtLastLogin;

    public User() {
        //預設帳號啟用
        this.isEnable = ConstUtils.IS_ENABLE;
        //初始化積分
        this.rewardPoint=0;
        //設定權限
        this.roleId = ConstUtils.AUTHORITY_USER_ID ;
    }
}
