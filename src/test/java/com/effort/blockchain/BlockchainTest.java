package com.effort.blockchain;

import com.effort.blockchain.block.Block;
import com.effort.blockchain.block.Blockchain;
import com.effort.blockchain.pow.ProofOfWork;

/**
 * 测试
 */
public class BlockchainTest {
    public static void main(String[] args) {
        Blockchain blockchain = Blockchain.newBlockchain();

        blockchain.addBlock("Send 1 BTC to Ivan");
        blockchain.addBlock("Send 2 more BTC to Ivan");

        for (Block block : blockchain.getBlockList()) {
            System.out.println("Prev.hash: " + block.getPrevBlockHash());
            System.out.println("Data: " + block.getData());
            System.out.println("Hash: " + block.getHash());
            System.out.println("Nonce: " + block.getNonce());

            ProofOfWork pow = ProofOfWork.newProofOfWork(block);
            System.out.println("Pow valid: " +  pow.validate() + "\n");
        }
    }
}
