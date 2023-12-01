package top.tentalk.business.pojo.query;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import top.tentalk.common.annotation.FieldName;


@Data
public class AccountSignQuery {

    @NotNull(message = "{Common.ParamNotNull}")
    @NotBlank(message = "{Common.ParamNotNull}")
    @FieldName(fieldName = "账号")
    private String userAccount;

    private String userPassword;

    private String userName;

    private String email;

}
