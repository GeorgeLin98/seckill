package com.george.seckill.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.george.seckill.api.user.pojo.UserPO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserUtil {
    private static String PASSWORD = "000000";

    /**
     * @description 创建用户
     * @param count
     * @throws Exception
     */
    public static void createUser(int count) throws Exception {
        // 生成用户信息
        List<UserPO> users = generateUserList(count);
        // 将用户信息插入数据库，以便在后面模拟用户登录时可以找到该用户，从而可以生成token返会给客户端，然后保存到文件中用于压测
        // 首次生成数据库信息的时候需要调用这个方法，非首次需要注释掉
         insertUserToDB(users);
        // 模拟用户登录，生成token
        System.out.println("start to login...");
        String urlString = "http://localhost:7070/user/batchLogin";
        File file = new File("D:\\FileFloder\\tokens.txt");
        if (file.exists()) {
            file.delete();
        }
        RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
        file.createNewFile();
        accessFile.seek(0);

        for (int i = 0; i < users.size(); i++) {
            // 模拟用户登录
            UserPO user = users.get(i);
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream out = httpURLConnection.getOutputStream();
            String params = "mobile=" + user.getPhone() + "&password=" + MD5Util.inputPassToFormPass(PASSWORD);
            out.write(params.getBytes());
            out.flush();
            // 生成token
            InputStream inputStream = httpURLConnection.getInputStream();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte buff[] = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buff)) >= 0) {
                bout.write(buff, 0, len);
            }
            inputStream.close();
            bout.close();
            String response = new String(bout.toByteArray());
            JSONObject jo = JSON.parseObject(response);
            String token = jo.getString("data");
            System.out.println("create token : " + user.getPhone());
            // 将token写入文件中，用于压测时模拟客户端登录时发送的token
            String row = user.getPhone() + "," + token;
            accessFile.seek(accessFile.length());
            accessFile.write(row.getBytes());
            accessFile.write("\r\n".getBytes());
            System.out.println("write to file : " + user.getPhone());
        }
        accessFile.close();
        System.out.println("write token to file done!");
    }

    /**
     * @description 生成用户信息
     * @param count 生成的用户数量
     */
    private static List<UserPO> generateUserList(int count) {
        List<UserPO> users = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            UserPO user = new UserPO();
            user.setPhone(19800000000L + i);// 模拟11位的手机号码
            user.setLoginCount(1);
            user.setNickname("user-" + i);
            user.setRegisterDate(new Date());
            user.setHead("headPicture");
            user.setSalt("1a2b3c4d");
            user.setPassword(MD5Util.inputPassToDbPass(PASSWORD, user.getSalt()));
            users.add(user);
        }
        return users;
    }

    /**
     * @description 将用户信息插入数据库中
     * @param users 待插入的用户信息
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private static void insertUserToDB(List<UserPO> users) throws Exception {
        System.out.println("start create user...");
        Connection conn = getConn();
        String sql = "INSERT INTO `seckill`.`seckill_user`(`phone`, `nickname`, `password`, `salt`, `head`, `register_date`,`login_count`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        for (int i = 0; i < users.size(); i++) {
            UserPO user = users.get(i);
            pstmt.setLong(1, user.getPhone());
            pstmt.setString(2, user.getNickname());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getSalt());
            pstmt.setString(5, user.getHead());
            pstmt.setTimestamp(6, new Timestamp(user.getRegisterDate().getTime()));
            pstmt.setInt(7, user.getLoginCount());
            pstmt.addBatch();
        }
        pstmt.executeBatch();
        pstmt.close();
        conn.close();
        System.out.println("insert to db done!");
    }

    /**
     * @decsription 获取数据库连接
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static Connection getConn() throws Exception {
        String url = "jdbc:mysql://localhost:3306/seckill?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2B8";
        String username = "root";
        String password = "root";
        String driver = "com.mysql.cj.jdbc.Driver";
        // 加载驱动
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public static void main(String[] args) throws Exception {
        createUser(5000);
    }
}
