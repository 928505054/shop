package com.bj.util;

import java.util.List;

public class Pager<T> {

    private String page;
    private Long total;
    private List<T> item;

    public Pager() {
    }

    public Pager(Long total, List<T> item) {
        this.total = total;
        this.item = item;
    }

    public Pager(String page, Long total, List<T> item) {
        this.page = page;
        this.total = total;
        this.item = item;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getItem() {
        return item;
    }

    public void setItem(List<T> item) {
        this.item = item;
    }
}
