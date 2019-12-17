package com.example.jdbc;

import com.example.jdbc.User.user;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
interface JD {
    @Insert("INSERT INTO redis (name) VALUES (#{name})")
    void increase(user a);
}
