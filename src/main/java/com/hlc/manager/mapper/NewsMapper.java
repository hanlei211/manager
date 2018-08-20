package com.hlc.manager.mapper;

import com.hlc.manager.entity.News;

import java.util.List;

/**
 * @Author rjyx
 * @Description
 * @Date create in 2018/8/20
 * @Modify by
 */
public interface NewsMapper {

    News findNewsByName(String name);

    List<News> getNews();
}
