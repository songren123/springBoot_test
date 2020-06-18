package com.tydic.dateSourceTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 按本地网分组取模，寻找分片规则
 */
public class UdalDnlSx {
    public static void main(String[] args) {
        List<Integer> latnId = Arrays.asList(290, 911, 912, 913, 914, 915, 916, 917, 919);
        List<Integer> outLantIds = new ArrayList<>();
        System.out.print("***************陕西内部分片本地网start***************");
        IntStream.range(0, 18).forEach(i -> {
            System.out.println();
            latnId.stream().forEach(latn -> {
                System.out.print(Integer.valueOf(latn + "" + (i+1)) + ",");
                outLantIds.add(Integer.valueOf(latn + "" + (i+1)));
            });
        });
        System.out.println("\n***************陕西内部分片本地网end****************\n");
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入片区（数字）：");
        int dnCount = Integer.valueOf(sc.nextLine());
        IntStream.range(0, dnCount).forEach(i -> {
            System.out.print(i + "分片下含有：");
            System.out.println(outLantIds.stream().filter(latn -> latn % dnCount == i).collect(Collectors.toList()));
        });
    }
}
