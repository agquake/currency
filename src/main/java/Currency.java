

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;


public class Currency {
    private static Map<String, Integer> map = new HashMap<>();
    private static Integer count = null;
    private static String name = null;
    private static String text = null;
    //正数
    private static String regex1 = "([1-9][0-9]*(\\.\\d{1,2})?)|(0\\.\\d{1,2})|0";
    private static String regex2 = "\\b[a-zA-Z]{3}\\b";

    public static void main(String[] args) throws Exception {
        //源文件地址
        List<String> strings = readTxtFileIntoStringArrList("src/main/resources/initial.txt");
        //初始数据
//        System.out.println(strings);
        for (String string : strings) {
            textToMap(string);
        }
        Scanner scan = new Scanner(System.in);

        //每过1分钟打印一次结果
        Timer timer = new Timer();
        timer.schedule(new MyTask(), 1000, 60000);
        while (scan.hasNextLine()) {
            text = scan.nextLine();
            //输入quit,打印结果并退出
            if ("quit".equals(text)) {
//                System.out.println(map);
                scan.close();
                timer.cancel();
                return;
            }
            textToMap(text);
        }
        scan.close();
    }

    //处理每行信息,放入结果集合
    private static void textToMap(String text) throws Exception {
        //分隔符为空格, =, -
        String[] array = text.split("\\s+|-|=");
        if (array.length > 2) {
            throw new Exception("请不要输入多于一个名称和一个金额");
        }
//        System.out.println(Arrays.toString(array));
        for (String s : array) {
            if (s.matches(regex1)) {
                count = Integer.valueOf(s);
            } else if (s.matches(regex2)) {
                name = s;
            } else {
                throw new Exception("请输入正数或正确货币代码");
            }
        }
        if (map.containsKey(name)) {
            //货币相同数额相加
            Integer old = map.get(name);
            map.put(name, old + count);
        } else {
            //金额为零不显示
            if (count != 0) {
                map.put(name, count);
            }
        }
    }

    //定时器执行任务
    static class MyTask extends java.util.TimerTask {
        ResourceBundle resource = ResourceBundle.getBundle("exchangeRate");

        @Override
        public void run() {
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                String keyUp = key.toUpperCase();
                Integer value = map.get(key);
                if (!keyUp.equals("USD")) {
                    BigDecimal rate = new BigDecimal(resource.getString(keyUp));
                    System.out.println(keyUp + " " + value + "(USD" + new BigDecimal(value).multiply(rate) + ")");
                } else {
                    System.out.println(keyUp + " " + value);
                }
            }

//            System.out.println(map);
        }
    }

    //读取文件信息
    private static List<String> readTxtFileIntoStringArrList(String filePath) {
        List<String> list = new ArrayList<>();
        try {
            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;

                while ((lineTxt = bufferedReader.readLine()) != null) {
                    list.add(lineTxt);
                }
                bufferedReader.close();
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

        return list;
    }
}
