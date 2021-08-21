package com.nowcoder.community;


import java.util.Stack;

public class Solution {


    public int hammingWeight(int n) {
        int ans = 0;
        for(int i=0;i<32;i++){
            if((n&(1<<i)) != 0 ){
                ans++;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Solution obj = new Solution();
        System.out.println(obj.hammingWeight(-1));
    }

}

