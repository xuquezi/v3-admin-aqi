package com.aqi.common.mybatis.entity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseEntityWrapper<E, V> {
    public BaseEntityWrapper() {
    }

    public abstract V entityVO(E entity);

    public List<V> listVO(List<E> list) {
        return list.stream().map(this::entityVO).collect(Collectors.toList());
    }

    public Page<V> pageVO(Page<E> pages) {
        List<V> records = this.listVO(pages.getRecords());
        Page<V> pageVo = new Page(pages.getCurrent(), pages.getSize(), pages.getTotal());
        pageVo.setRecords(records);
        return pageVo;
    }
}
