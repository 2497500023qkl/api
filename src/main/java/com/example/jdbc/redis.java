package com.example.jdbc;

import com.example.jdbc.User.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class redis {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String start="2019-12-17 10:57:0";
    private String end="2019-12-17 10:39:0";
    private user User=null;
    private Date dat=null;
    private Date dat2=null;
    private Jedis jedis = new Jedis();
    private Long long3=0L;
    private Map<String,Double> c=new Hashtable<>();
    @Autowired
    JD JD;
    @RequestMapping("/zeng")
    public String zeng(HttpServletRequest request) throws ParseException {
        Date a =new Date();
        long3=jedis.zcard("商品1");
        dat=df.parse(start);
        dat2=df.parse(end);
        if(!a.before(dat)){
            return "超时";
        }else if(a.before(dat2)){
            return "没开始";
        }
        if(long3==4){
            return "售欣";
        }
        long3++;
        if(jedis.zrank("商品1",request.getParameter("id"))!=null){
            return "存在";
        }
        c = new Hashtable();
        c.put(request.getParameter("id"),1.0);
        User=new user(request.getParameter("id"));
        try{
            JD.increase(User);
            jedis.zadd("商品1",c);
        }catch (Exception e){
            e.printStackTrace();
            return "错误";
        }
        c.clear();
        return "成功";
    }
    @RequestMapping("/cha")
    public String cha(HttpServletRequest request) {
        String c=4-jedis.zcard("商品1")+"";
        return c;
    }
    @RequestMapping("/cha1")
    public String cha1(HttpServletRequest request) throws ParseException {
        Date d =new Date();
        long long1= d.getTime();
        dat=df.parse(start);
        dat2=df.parse(end);
        long long2=dat.getTime();
        long long3=dat2.getTime();
        if(long3>long1){
            return (long3-long1)/1000+".开始";
        }else if(long2>long1){
            return (long2-long1)/1000+".结束";
        }
        return "0.以结束";
    }

}
