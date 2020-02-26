package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.entity.UserEntity;
import com.example.mybatisplus.vo.req.UserReq;
import com.example.mybatisplus.vo.resp.UserResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generatorF
 * @since 2019-11-14
 */
public interface UserMapper extends BaseMapper<User> {

    Page<UserResp> getUserList(Page page,@Param("userReq") UserReq userReq);

    void insertBatch(@Param("users") List<UserEntity> users);
}
