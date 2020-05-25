package com.http.demo.skiplist;

import java.util.*;

public class SkipList {

    private static final int DEFAULT_HEIGHT = 4;

    // 头节点
    private Node header;

    // 尾节点
    private Node tail;

    //当前节点,用于构建跳表
    private Node current;

    private class Node {

        private int data;

        private Node[] forword;

        public Node(int data, int level) {
            this.data = data;
            forword = new Node[level];
        }

    }

    public SkipList() {
        header = new Node(Integer.MIN_VALUE, DEFAULT_HEIGHT);
        tail = new Node(Integer.MAX_VALUE, DEFAULT_HEIGHT);

        for (int i = 0; i < DEFAULT_HEIGHT; i++) {
            header.forword[i] = tail;
            tail.forword[i] = null;
        }
        current = header;
    }

    public void addArrayToSkipList(Integer[] array) {

        Arrays.sort(array, Comparator.comparingInt(a -> a));

        //因为有序数组，所以用尾插入
        for (Integer data : array) {
            System.out.print(data + " ");
            addTail(data);
        }
        System.out.println();
    }

    private void addTail(int data) {
        int k = level();
//        System.out.println("k:" + k);
        Node node = new Node(data, k);
        if (node.forword.length <= current.forword.length) {

            for (int i = node.forword.length - 1; i >= 0; i--) {
                Node tmp = current.forword[i];
                current.forword[i] = node;
                node.forword[i] = tmp;
            }
        } else {
            // 暂时选择高度固定
            if(header.forword.length>= node.forword.length){
                for (int i = current.forword.length - 1; i >= 0; i--) {

                    current.forword[i] = node;
                    node.forword[i] = tail;
                }

                for(int i = current.forword.length; i< node.forword.length;i++){
                    header.forword[i] = node;
                    node.forword[i] = tail;

                }
            }
        }
        current = node;
    }

    // 遍历
    private static class Itr implements Iterator<Integer> {
        private Node node;

        private SkipList skipList;

        @Override
        public boolean hasNext() {
            return this.node.forword != null && this.node.forword[0] != null;
        }

        @Override
        public Integer next() {
            this.node = this.node.forword[0];
            return node.data;
        }

        public Itr(SkipList skipList) {
            this.skipList = skipList;
            this.node = this.skipList.header;
        }

    }

    public static void main(String[] args) {
        Integer[] a = new Integer[10];
        Random random = new Random();
        for (int i = 0; i < a.length; i++) {
            a[i] = random.nextInt(500);
        }
        SkipList skipList = new SkipList();
        skipList.addArrayToSkipList(a);

        Iterator<Integer> iterator = skipList.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.print(next + " ");
        }
    }

    public Iterator<Integer> iterator() {
        return new Itr(this);
    }

    private int level() {
        int k = 1;

        Random random = new Random();
        while (random.nextInt(DEFAULT_HEIGHT + 1) != 2 && k < DEFAULT_HEIGHT) {
            k++;
        }
        return k;

    }


}
