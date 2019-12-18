package com.example.jdbc.link;

import com.example.jdbc.Database.database;
import com.example.jdbc.Database.order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public
interface JD {
    @Select("select * from commodity where status=2")
    List<database> lookup();

    @Insert("insert into orders(uid,sid,pop)values(#{uid},#{sid},#{pop})")
    void deposit(order O);
}
