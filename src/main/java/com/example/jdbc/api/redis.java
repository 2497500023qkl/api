package com.example.jdbc.api;

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
@CrossOrigin(origins = "*", maxAge = 3600)
public class redis {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String start = "2019-12-18 23:30:00";
    private String end = "2019-12-17 19:25:00";
    private Date dat = null;
    private Date dat2 = null;
    private Jedis jedis = new Jedis();
    private Map<String, Double> c = new Hashtable<>();

    @RequestMapping("/zeng")
    public String zeng(HttpServletRequest request) throws ParseException {
        Date a = new Date();
        dat = df.parse(start);
        dat2 = df.parse(end);
        if (!a.before(dat)) {
            return "超时";
        } else if (a.before(dat2)) {
            return "没开始";
        }
        String commodity2 = "";
        commodity2 = request.getParameter("name");
        if (jedis.keys(request.getParameter("name")).size() == 0) {
            return "断货";
        }
        if (jedis.zrank(commodity2 + "售", request.getParameter("id")) != null) {
            return "存在";
        }
        String commodity = jedis.lpop(commodity2);
        if (commodity == null) {
            return "断货";
        }
        c = new Hashtable();
        c.put(request.getParameter("id"), 1.0);
        try {
            jedis.zadd(commodity2 + "售", c);
            jedis.rpush("pop", request.getParameter("id") + "/" + commodity);
        } catch (Exception e) {
            e.printStackTrace();
            return "错误";
        }
        return "成功";
    }

    @RequestMapping("/cha")
    public String cha(HttpServletRequest request) {
        String c = 4 - jedis.zcard("商品1") + "";
        return c;
    }

    @RequestMapping("/cha1")
    public String cha1(HttpServletRequest request) throws ParseException {
        Date d = new Date();
        long long1 = d.getTime();
        dat = df.parse(start);
        dat2 = df.parse(end);
        long long2 = dat.getTime();
        long long3 = dat2.getTime();
        if (long3 > long1) {
            return (long3 - long1) / 1000 + ".开始";
        } else if (long2 > long1) {
            return (long2 - long1) / 1000 + ".结束";
        }
        return "0.以结束";
    }

}
