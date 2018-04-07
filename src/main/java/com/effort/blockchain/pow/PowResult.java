package com.effort.blockchain.pow;


import lombok.Data;

/**
 * 工作量计算结果
 *
 * @author lihui
 *
 * @data 2018/04/07
 *
 */

@Data
public class PowResult {

    /**
     * 计数器
     */
    private long nonce;

    /**
     * hash值
     */
    private String hash;

    public PowResult(long nonce, String hash) {
        this.nonce = nonce;
        this.hash = hash;
    }

}
