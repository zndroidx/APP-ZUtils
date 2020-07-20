package com.zndroid.utils.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @name:CollectUtil
 * @author:lazy
 * @email:luzhenyuxfcy@sina.com
 * @date : 2020/7/15 23:47
 * @version:
 * @description:集合操作
 */
class CollectUtil<T> {
    /**
     * 求两个列表的并集
     * 注意如果泛型 ‘T’不是基本类型，需要复写 ‘hashCode()’和‘equals()’方法
     * */
    public List<T> getUnionSet(List<T> list1, List<T> list2) {
        if (null == list1 && null == list2) return null;

        if (null == list1) return list2;
        if (null == list2) return list1;

        HashSet<T> hashSetUnion = new HashSet<T>();

        Set<T> setA = new HashSet<T>(list1);
        Set<T> setB = new HashSet<T>(list2);

        hashSetUnion.addAll(setA);
        hashSetUnion.addAll(setB);

        List<T> result = new CopyOnWriteArrayList<>();
        Iterator<T> iterator = hashSetUnion.iterator();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }

        return result;
    }

    /**
     * 求两个列表的交集
     * 注意如果泛型 ‘T’不是基本类型，需要复写 ‘hashCode()’和‘equals()’方法
     * */
    public List<T> getIntersectionSet(List<T> list1, List<T> list2) {
        if (null == list1 && null == list2) return null;

        List<T> result = new CopyOnWriteArrayList<>();
        if (null == list1 || null == list2) return result;

        HashSet<T> sameSet = new HashSet<T>();

        Set<T> setA = new HashSet<T>(list1);
        Set<T> setB = new HashSet<T>(list2);

        for (T t : setA) {
            if(setB.contains(t)){
                sameSet.add(t);
            }
        }

        Iterator<T> iterator = sameSet.iterator();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }

        return result;
    }
}
