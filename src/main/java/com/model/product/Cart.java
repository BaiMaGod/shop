package com.model.product;

import lombok.Data;

@Data
public class Cart {
    private Integer id;//主键id

    private Integer userId;//用户id

    private String goodsId;//商品id

    private Goods goods;//商品对象

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }
}