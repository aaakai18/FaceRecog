package com.example.component;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.utils.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author websocket服务
 */
@ServerEndpoint(value = "/loginserver/{username}")


@Component
public class WebSocketServerAll {

    private static final Logger log = LoggerFactory.getLogger(WebSocketServerAll.class);
    /**
     * 记录当前在线连接数
     */
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    /**
     * 存储不同课所对应的学生的session
     */
    public static List<Map<String, Session>> mapList = new ArrayList<>();

    //群聊的设置
    private  String groupName = "group";
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        sessionMap.put(username, session);
        log.info("总服务器有新用户加入，username={}, 当前在线人数为：{}", username, sessionMap.size());
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        result.set("users", array);
        for (Object key : sessionMap.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("username", key);
            // {"username", "zhang", "username": "admin"}
            array.add(jsonObject);
        }
//        {"users": [{"username": "zhang"},{ "username": "admin"}]}
        sendAllMessage(JSONUtil.toJsonStr(result));  // 后台发送消息给所有的客户端
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        sessionMap.remove(username);
        log.info("总服务器有一连接关闭，移除username={}的用户session, 当前在线人数为：{}", username, sessionMap.size());
    }
    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("username") String username) {
        log.info("总服务器服务端收到用户username={}的消息:{}", username, message);
        JSONObject obj = JSONUtil.parseObj(message);
        String toUsername = obj.getStr("to"); // to表示发送给哪个用户，比如 admin
        String text = obj.getStr("text"); // 发送的消息文本  hello
        // {"to": "admin", "text": "聊天文本"}
        Session toSession = sessionMap.get(toUsername); // 根据 to用户名来获取 session，再通过session发送消息文本
        if (toSession != null) {
            // 服务器端 再把消息组装一下，组装后的消息包含发送人和发送的文本内容
            // {"from": "zhang", "text": "hello"}
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("from", username);  // from 是 zhang
            jsonObject.set("text", text);  // text 同上面的text
            this.sendMessage(jsonObject.toString(), toSession);
            log.info("总服务器：发送给用户username={}，消息：{}", toUsername, jsonObject.toString());
        } else if (toUsername.equals(this.groupName)) {
            //群聊发消息
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("from", username);  // from 是 zhang
            jsonObject.set("text", text);  // text 同上面的text
            this.sendMessageToAll(jsonObject.toString(),username);
            log.info("{} 发送给所有用户，消息：{}",username, jsonObject.toString());
        } else if (MessageType.CreateNewSessionMap.equals(text)) {
            Map<String,Session>map = new ConcurrentHashMap<>();
            map.put(username,session);
            mapList.add(map);

        } else {
            log.info("发送失败，未找到用户username={}的session", toUsername);
        }
    }
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }
    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }
    /**
     * 服务端发送消息给所有客户端
     */
    private void sendAllMessage(String message) {
        try {
            for (Session session : sessionMap.values()) {
                log.info("111服务端给客户端[{}]发送消息{}", session.getId(), message);
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }

    /**
     * 实现群发消息
     * @param message
     */
    private void sendMessageToAll(String message,String username) {

        try {
            for(String userId:sessionMap.keySet()){
                if(!userId.equals(username)){
                    Session session = sessionMap.get(userId);
                    session.getBasicRemote().sendText(message);
                    log.info("111服务端给客户端[{}]发送消息{}", session.getId(), message);
                }
            }
        } catch (IOException e) {
            log.error("服务端发送消息给客户端失败", e);

        }

//        try {
//            for (Session session : sessionMap.values()) {
//
//                log.info("111服务端给客户端[{}]发送消息{}", session.getId(), message);
//                session.getBasicRemote().sendText(message);
//            }
//        } catch (Exception e) {
//            log.error("服务端发送消息给客户端失败", e);
//        }
    }
}