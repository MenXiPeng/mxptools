package com.example.mxptools.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：
 *
 * @author firefly by 2024/3/15
 */
public class goodsClas {


    public static Map<String, String> addressList(String address) {
        String[] addrArray = address.split("\n");

        Map<String,String> map = new HashMap<>();

        for (String s : addrArray) {
            String[] kv = s.split(",");
            map.put(kv[0],kv[1]);
        }

        return map;
        // return Map.of(
        //         "yh1q", "迎晖一期_迎晖1期_迎辉一期",
        //         "yh2q", "迎晖二期_迎晖2期_迎辉二期",
        //         "yl1q", "云岭一期_云岭1期",
        //         "yl2q", "云岭二期_云岭2期",
        //         "qhdj", "秦皇帝锦月_锦玥_秦皇",
        //         "gs1i", "观山一期",
        //         "txaz", "腾讯A座_腾讯a座_腾讯大厦A座_腾讯大厦a座",
        //         "txbz", "腾讯B座_腾讯b座_腾讯大厦B座_腾讯大厦b座"
        // );
    }

    static Map<String, Objects> result = null;
    static Map<String, List<String>> temp = new HashMap<>();
    static Map<String, Integer> tempNum = new HashMap<>();

    public static void matchStr(List<String> array,String address) {

        Map<String, String> addressMap = addressList(address);

        for (String s : array) {
            boolean flag = false;

            for (Map.Entry<String, String> entry : addressMap.entrySet()) {
                String key = entry.getKey();

                temp.computeIfAbsent(key, k -> new ArrayList<>());

                String value = entry.getValue();
                String[] addrArray = value.split("_");
                // 匹配值
                for (String string : addrArray) {
                    if (s.contains(string)) {
                        temp.get(key).add(s);
                        flag = true;
                        break;
                    }
                }
                //
                if (flag) {
                    break;
                }
            }
            if (!flag) {
                temp.computeIfAbsent("other", k -> new ArrayList<>());
                temp.get("other").add(s);
            }

        }
    }

    public static Map<String, List<String>> cla(String text,String address) {
        result = new HashMap<>();
        temp.clear();
        tempNum.clear();
        List<String> list = new ArrayList<>();
        String[] array = text.split("\n");
        Pattern yz = Pattern.compile(".*柚子.*");
        Pattern MoSmallCm = Pattern.compile(".*草莓.*");
        Pattern bigMoCm = Pattern.compile(".*草莓中大.*");
        Pattern pg = Pattern.compile(".*椪柑.*");
        for (String s : array) {
            int totalP = 0;
            // if (yz.matcher(s).matches()) {
            //     String patternString1 = "大柚子((\\d+)|一|两|三|四|五|六|七|八|九)个";
            //     totalP += moBigJ(patternString1, s, 25);
            // }
            if (MoSmallCm.matcher(s).matches()) {
                String patternString1 = "草莓中小((\\d+)|一|两|三|四|五|六|七|八|九)";
                totalP += moBigJ(patternString1, s, 25,"中小");
                String patternString2 = "草莓中大((\\d+)|一|两|三|四|五|六|七|八|九)";
                totalP += moBigJ(patternString2, s, 40,"中大");
                String patternString3 = "中小草莓((\\d+)|一|两|三|四|五|六|七|八|九)";
                totalP += moBigJ(patternString3, s, 25, "中小");
                String patternString4 = "中大草莓((\\d+)|一|两|三|四|五|六|七|八|九)";
                totalP += moBigJ(patternString4, s, 40, "中大");
            }
            if (pg.matcher(s).matches()) {
                String patternString = "椪柑((\\d+)|一|两|三|四|五|六|七|八|九)袋";
                totalP += moBigJ(patternString, s, 15,"椪柑");
                String patternString1 = "((\\d+)|一|两|三|四|五|六|七|八|九)袋椪柑";
                totalP += moBigJ(patternString1, s, 15,"椪柑");
            }
            list.add(s + "---" + totalP);
        }
        matchStr(list,address);
        // temp.forEach((k, v) -> {
        //     if (v ==null || v.isEmpty()){
        //         temp.remove(k);
        //     }
        // });



        return temp;
    }

    public static int moBigJ(String patternString, String val, int PerBasket,String tag) {

        Pattern pattern = Pattern.compile(patternString);
        val = val.replaceAll(" ", "");
        Matcher matcher = pattern.matcher(val);

        temp.computeIfAbsent(tag, k -> {
            List<String> list = new ArrayList<>();
            list.add("0");
            return list;
        });
        // 单价
        int totalPrice = 0;

        // 中文数字到阿拉伯数字的映射
        Map<String, Integer> chineseNumMap = new HashMap<>();
        chineseNumMap.put("一", 1);
        chineseNumMap.put("两", 2);
        chineseNumMap.put("三", 3);
        chineseNumMap.put("四", 4);
        chineseNumMap.put("五", 5);
        chineseNumMap.put("六", 6);
        chineseNumMap.put("七", 7);
        chineseNumMap.put("八", 8);
        chineseNumMap.put("九", 9);

        while (matcher.find()) {
            String match = matcher.group(1);
            int numBaskets;
            if (match.matches("\\d+")) { // 是阿拉伯数字
                numBaskets = Integer.parseInt(match);
            } else { // 是中文数字
                numBaskets = chineseNumMap.getOrDefault(match, 0);
            }
            totalPrice += numBaskets * PerBasket;
            int num = Integer.parseInt(temp.get(tag).get(0)) + numBaskets;
            temp.get(tag).set(0, String.valueOf(num));
            //temp.put(tag, );
        }

        return totalPrice;
    }

}
