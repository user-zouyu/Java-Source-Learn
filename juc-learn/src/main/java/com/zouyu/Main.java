package com.zouyu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入点的横坐标 x：");
        double x = scanner.nextDouble();
        System.out.print("请输入点的纵坐标 y：");
        double y = scanner.nextDouble();
        if (x * x + y * y <= 100.0D) {
            System.out.println("该点在以原点为圆心，半径为 10 的圆内。");
        } else {
            System.out.println("该点不在以原点为圆心，半径为 10 的圆内。");
        }
    }
}