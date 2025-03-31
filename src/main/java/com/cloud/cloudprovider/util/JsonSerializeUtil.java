package com.cloud.cloudprovider.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cloudprovider.model.User2;
import com.cloud.cloudprovider.model.User2Son;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * @author linjianhui
 * @description
 * @date 2022/7/11 4:20 下午
 */
public class JsonSerializeUtil {
    /**
     * 线程安全，可全局使用
     */
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        //反序列化的时候如果多了其他属性,不抛出异常
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //如果是空对象的时候,不抛异常
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        //属性为null不转换
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Data
    static class ResponseData<T> {
        private int code;
        private String message;
        private T data;
    }

    static class ResponseData1 extends ResponseData<User> {

    }

    @Data
    static class User implements Serializable {
        private String name;
        private Integer age;
        private Boolean used;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    static class UserSon extends User{
        private String son;
    }

    public static void main(String[] args) throws Exception {

        UserSon user = new UserSon();
        user.setUsed(true);
        user.setName("用户1");
        user.setSon("用户1子类属性");

        User user1 = user;

        String userStr1 = OBJECT_MAPPER.writeValueAsString(user1);

//        byte [] a = SerializationUtils.serialize(user);
//
//        Object ob = SerializationUtils.deserialize(a);
//        User user2 = (User) ob;
//        System.out.println(user2);


        //反序列化
        TypeReference<User2Son> type = new TypeReference<User2Son>() {};
        User2 res1 = OBJECT_MAPPER.readValue(userStr1, type);
        System.out.println(res1);
        //
        // ();
    }


    // 反序列化
    public static void deserialize() throws Exception {
        String json = "{\n" +
                "  \"code\": 1,\n" +
                "  \"message\": \"test message\",\n" +
                "  \"data\": {\n" +
                "    \"name\":\"zhang\",\n" +
                "    \"age\":\"100\",\n" +
                "    \"used\":true,\n" +
                "    \"test\":\"sds\"\n" +
                "  }\n" +
                "}";
        // 泛型反序列化的5种方式
        // 方式1  -- 推荐1
        TypeReference<ResponseData<User>> type = new TypeReference<ResponseData<User>>() {
        };
        ResponseData<User> res1 = OBJECT_MAPPER.readValue(json, type);

        // 方式2 -- 推荐2 依次指定主体class,n个泛型class
        JavaType javaType1 = OBJECT_MAPPER.getTypeFactory().constructParametricType(ResponseData.class, User.class);
        ResponseData<User> res2 = OBJECT_MAPPER.readValue(json, javaType1);

        // 方式3 过时
        JavaType javaType2 = OBJECT_MAPPER.getTypeFactory().constructParametrizedType(ResponseData.class,
                ResponseData.class,
                User.class);
        ResponseData<User> res3 = OBJECT_MAPPER.readValue(json, javaType2);


        // 方式4
        Type[] types = new Type[1];
        types[0] = User.class;
        final ParameterizedTypeImpl type4 = ParameterizedTypeImpl.make(ResponseData.class, types,
                ResponseData.class.getDeclaringClass());
        TypeReference<ResponseData> typeReference = new TypeReference<ResponseData>() {
            @Override
            public Type getType() {
                return type4;
            }
        };
        ResponseData<User> res4 = OBJECT_MAPPER.readValue(json, typeReference);

        // 方式5 新建一个类指定泛型
        ResponseData1 res5 = OBJECT_MAPPER.readValue(json, ResponseData1.class);

        System.out.println("res1:" + res1);
        System.out.println("res2:" + res2);
        System.out.println("res3:" + res3);
        System.out.println("res4:" + res4);
        System.out.println("res5:" + res5);

        String data1 = "{\n" +
                "  \"code\": 1,\n" +
                "  \"message\": \"test message\",\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"name\": \"zhang1\",\n" +
                "      \"age\": \"100\",\n" +
                "      \"used\": false,\n" +
                "      \"test\": \"sds\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"zhang2\",\n" +
                "      \"age\": \"100\",\n" +
                "      \"used\": false,\n" +
                "      \"test\": \"sds\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        JsonNode root = OBJECT_MAPPER.readTree(data1);
        // get 没有返回null
        System.out.println(root.get("111"));
        // path 不会异常，没有返回空字符串
        System.out.println(root.path("111").asText().length());

        // 方式1 获取数组
        JsonNode node = root.path("data");
        if (node.isArray()) {
            for (JsonNode objNode : node) {
                System.out.println(objNode);
            }
        }
        // 方式2 获取数组
        ArrayNode arrayNode = (ArrayNode) (root.get("data"));
        for (JsonNode jsonNode : arrayNode) {
            System.out.println(jsonNode);
        }
    }

    // 序列化成字符串
    public static void serialize() throws Exception {
        // ======================= alibaba
        JSONObject root = new JSONObject();
        root.put("code", 1);
        root.put("message", "test");
        JSONArray jsonArray = new JSONArray();

        jsonArray.add("test1");
        jsonArray.add("test2");
        jsonArray.add("test3");
        root.put("data", jsonArray);

        System.out.println(OBJECT_MAPPER.writeValueAsString(root));

        // ========================== jackson
        ObjectNode root1 = OBJECT_MAPPER.createObjectNode();
        root1.put("code", 1);
        root1.put("message", "test");
        ArrayNode array1 = OBJECT_MAPPER.createArrayNode();
        ObjectNode element1 = OBJECT_MAPPER.createObjectNode();
        element1.put("name", "zhang");
        element1.put("age", 99);
        element1.put("used", true);
        array1.add(element1);
        root1.set("data", array1);
        System.out.println(OBJECT_MAPPER.writeValueAsString(root1));

    }
}
