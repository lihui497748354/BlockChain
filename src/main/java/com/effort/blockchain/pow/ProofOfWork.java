package com.effort.blockchain.pow;


import com.effort.blockchain.block.Block;
import com.effort.blockchain.utils.ByteUtils;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;

/**
 * 工作量证明
 *
 * @author lihui
 * @date 2018/04/07
 */

@Data
public class ProofOfWork {

    /**
     * 难度目标位
     */
    public static final int TARGET_BITS = 20;

    /**
     * 区块
     */
    private Block block;
    /**
     * 难度目标值
     */
    private BigInteger target;

    private ProofOfWork(Block block, BigInteger target) {
        this.block = block;
        this.target = target;
    }

    /**
     * 创建新的工作量 证明，设定难度目标值
     * 对1进行移位运算，将1向左移动 (256 - TARGET_BITS) 位，得到我们的难度目标值
     */

    public static ProofOfWork newProofOfWork(Block block) {
        BigInteger targetValue = BigInteger.valueOf(1).shiftLeft((256 - TARGET_BITS));
        return new ProofOfWork(block, targetValue);
    }

    /**
     * 运行工作量证明，开始挖矿，找到小于难度目标值的Hash
     * @return
     */
    public PowResult run(){
        long nonce = 0;
        String shaHex = "";
        System.out.printf("Mining the block containing：%s \n", getBlock().getData());
        long startTime = System.currentTimeMillis();
        while (nonce < Long.MAX_VALUE){
            byte[] data = prepareData(nonce);
            shaHex = DigestUtils.sha256Hex(data);
            if (new BigInteger(shaHex, 16).compareTo(target) == -1) {
                System.out.printf("Elapsed Time: %s seconds \n", (float) (System.currentTimeMillis() - startTime) / 1000);
                System.out.printf("correct hash Hex: %s \n\n", shaHex);
                break;
            } else {
                nonce++;
            }
        }
        return new PowResult(nonce, shaHex);
    }

    /**
     * 准备数据
     *
     * 注意：在准备区块数据时，一定要从原始数据类型转化为byte[]，不能直接从字符串进行转换
     *
     */
    private byte[] prepareData(long nonce){
        byte[] prevBlockHashBytes = {};
        if (StringUtils.isNoneBlank(getBlock().getPrevBlockHash())){
            prevBlockHashBytes = new BigInteger(getBlock().getPrevBlockHash(),16).toByteArray();
        }
        return  ByteUtils.merge(
                prevBlockHashBytes,
                getBlock().getData().getBytes(),
                ByteUtils.toBytes(getBlock().getTimeStamp()),
                ByteUtils.toBytes(TARGET_BITS),
                ByteUtils.toBytes(nonce)
        );
    }

    /**
     * 验证区块是否有效
     *
     * @return
     */
    public boolean validate() {
        byte[] data = prepareData(getBlock().getNonce());
        return new BigInteger(DigestUtils.sha256Hex(data), 16).compareTo(target) == -1;
    }



}
