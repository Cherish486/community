package com.nowcoder.community.entity;

/*
* 封装分页相关的信息
* */
public class Page {

    //当前页码
    private int current = 1;

    //显示上限
    private int limit = 10;

    //数据总页数（用于计算总页数）
    private int rows;

    //查询路径（用于复用分页链接）
    private String path;

    /**
     * 获取当前页的起始行
     * @return
     */
    public int getOffset(){

        // current * limit - limit
        // 当前页数*每页显示的数据（即当前页的最后一行数据）- 每页显示的数据 ==>就是起始行
        return (current - 1) * limit;

    }

    /**
     * 获取总的页数
     * @return
     */
    public int getTotal(){
        // 如果总行数能整除每页显示的数据，那么总页数就是rows / limit ；如果不能整除，就加1
        if(rows % limit == 0){
            return rows / limit;
        }else{
            return rows / limit + 1;
        }
    }

    /**
     * 获取起始页码
     * @return
     */
    public int getFrom(){
        int from = current - 2;
        return from < 1 ? 1 : from;
    }

    /**
     * 获取结束页码
     * @return
     */
    public int getTo(){
        int to = current + 2;
        int total = getTotal();
        return to > total ? total : to;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if(current >= 1){
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if(limit >= 1 && limit <= 100) {
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if(rows >= 0) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Page{" +
                "current=" + current +
                ", limit=" + limit +
                ", rows=" + rows +
                ", path='" + path + '\'' +
                '}';
    }
}
