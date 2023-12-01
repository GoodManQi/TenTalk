package top.tentalk.business.pojo;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * 用户表
 */
@Entity
@Data
public class UserInfo implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 分布式ID
     */
    private Long distributeId;

    /**
     * 用户登录名
     */
    private String userAccount;

    /**
     * 用户登录密码
     */
    private String userPassword;

    /**
     * 盐
     */
    private String salt;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 用户状态 0:待验证 1:正常
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除 0:未删除 1:已删除
     */
    private Integer deleted;
}