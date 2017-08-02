package com.mall.b2bp.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */
public class CollectionUtils {


    /**
     * 查找重复数据
     *
     * @param alist
     *            待查找数据
     */
    public static void checkStringRepeat(List<String> alist) {
        // 记录重复数据
        List<String> cflist = new ArrayList<String>();
        // 复制一个list
        List<String> blist = alist;
        for (int i = 0; i < alist.size(); i++) {
            boolean b = false;
            String mk = "第" + (i + 1) + "条数据和第";
            for (int j = 0; j < blist.size(); j++) {
                // 不和本身比较
                if (j != i) {
                    // 找到相同的值
                    if (alist.get(i).equals(blist.get(j))) {
                        // 不存在重复数据
                        if (!cflist.isEmpty()) {
                            boolean bo = true;
                            // 遍历重复数据集
                            for (int k = 0; k < cflist.size(); k++) {
                                // 取出第k行的重复数据
                                String[] cf = cflist.get(k).toString()
                                        .split("-");
                                // 取出第k行的第二个索引（复制数据的索引+1）
                                int s = Integer.parseInt(cf[1].toString());
                                // 比较当前顺序和重复中的顺序，如果相等则标记为false
                                if (s == (i + 1)) {
                                    bo = false;
                                }
                            }
                            // 判断是否添加到重复数据集中（true表示重复数据集里面不存在该数据，false反之）
                            if (bo) {
                                cflist.add((i + 1) + "-" + (j + 1));
                                if (b) {
                                    mk += ",";
                                }
                                mk += (j + 1);
                                // 记录重复数据时修改b的值，表示和第i行和第j行存在重复数据
                                b = true;
                            }
                        } else {
                            // 记录重复的数据，格式为2-3（第一个数字为原重复数据的索引+1，第二个为复制数据的索引+1）
                            cflist.add((i + 1) + "-" + (j + 1));
                            mk += (j + 1);
                            b = true;
                        }
                    }
                }
            }
            mk += "条数据重复,重复值：" + alist.get(i);
            if (b) {
                System.out.println(mk);
            }
        }
    }
}
