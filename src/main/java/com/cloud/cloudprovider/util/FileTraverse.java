package com.cloud.cloudprovider.util;

import com.google.common.collect.Sets;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

/**
 * @author linjianhui
 * @description 文件遍历
 * @date 2025/3/17 10:29
 */
public class FileTraverse {

    //    public static void main(String[] args) {
//        System.out.println("975e968152feb871c20c941e3469f90e".length());
//        System.out.println(UUID.randomUUID().toString().replace("-",""));
//    }

//    public static void main(String[] args) {
//        // 目标目录路径
//        String directoryPath = "/Users/jianhuilin/Downloads/target/CORPUS";
//        File directory = new File(directoryPath);
//
//        // 检查目录是否存在
//        if (directory.exists() && directory.isDirectory()) {
//            // 遍历目录下的所有文件夹
//            File[] folders = directory.listFiles(File::isDirectory);
//            if (folders!= null) {
//                for (File folder : folders) {
//                    System.out.println("正在遍历文件夹: " + folder.getAbsolutePath());
//                    // 获取文件夹内的文件列表并打印文件名
//                    File[] files = folder.listFiles();
//                    if (files!= null) {
//                        for (File file : files) {
//                            if (file.isFile()) {
//                                System.out.println("  - " + file.getName());
//                            }
//                        }
//                    }
//                }
//            }
//        } else {
//            System.out.println("指定的目录不存在或不是一个目录。");
//        }
//    }


    public static void main2(String[] args) {
        // 目标目录路径
        String directoryPath = "/Users/jianhuilin/Downloads/target/CORPUS";
        File directory = new File(directoryPath);

        // 用于存储uuid和文件名的映射关系
        Map<String, String> uuidFilenameMap = new HashMap<>();

        // 检查目录是否存在
        if (directory.exists() && directory.isDirectory()) {
            // 遍历目录下的所有文件夹
            File[] folders = directory.listFiles(File::isDirectory);
            if (folders!= null) {
                for (File folder : folders) {
                    System.out.println("正在遍历文件夹: " + folder.getAbsolutePath());
                    // 获取文件夹内的文件列表并查找meta.yaml文件
                    File[] files = folder.listFiles();
                    if (files!= null) {
                        for (File file : files) {
                            if (file.isFile() && file.getName().equals("meta.yaml")) {
                                try {
                                    // 读取meta.yaml文件内容
                                    Yaml yaml = new Yaml();
                                    Map<String, Object> metaYamlData = yaml.load(new FileInputStream(file));
                                    // 假设meta.yaml中uuid字段的键为"uuid"，根据实际情况修改
                                    if (metaYamlData.containsKey("resourceName")) {
                                        String uuid = (String) metaYamlData.get("resourceName");
                                        uuidFilenameMap.put(uuid, file.getName());
                                        System.out.println("  - 找到meta.yaml文件: " + file.getAbsolutePath());
                                        System.out.println("    - resourceName: " + uuid);
                                    } else {
                                        System.out.println("    - meta.yaml文件中未找到resourceName字段");
                                    }
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("指定的目录不存在或不是一个目录。");
        }

        // 打印uuid和文件名的映射关系
        System.out.println("resourceName和文件名的映射关系如下：");
        for (Map.Entry<String, String> entry : uuidFilenameMap.entrySet()) {
            System.out.println("  - resourceName: " + entry.getKey() + ", 文件名: " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        // 目标目录路径
        String directoryPath = "/Users/jianhuilin/Downloads/target/CORPUS";
        File directory = new File(directoryPath);

        // 用于存储uuid和文件名的映射关系
        Map<String, String> uuidFilenameMap = new HashMap<>();

        // 检查目录是否存在
        if (directory.exists() && directory.isDirectory()) {
            // 遍历目录下的所有文件夹
            File[] folders = directory.listFiles(File::isDirectory);
            if (folders!= null) {
                for (File folder : folders) {
                    // 获取文件夹内的文件列表并查找meta.yaml文件
                    File[] files = folder.listFiles();
                    if (files!= null) {
                        for (File file : files) {
                            if (file.isFile() && file.getName().equals("meta.yaml")) {
                                try {
                                    // 读取meta.yaml文件内容
                                    Yaml yaml = new Yaml();
                                    Map<String, Object> metaYamlData = yaml.load(new FileInputStream(file));
                                    // 假设meta.yaml中uuid字段的键为"uuid"，根据实际情况修改
                                    if (metaYamlData.containsKey("resourceName")) {
                                        String uuid = (String) metaYamlData.get("resourceName");
                                        uuidFilenameMap.put(uuid, folder.getName());
                                    } else {
                                        System.out.println(file.getName() + "    - meta.yaml文件中未找到resourceName字段");
                                    }
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("指定的目录不存在或不是一个目录。");
        }

        check(uuidFilenameMap);

    }

    private static void check(Map<String, String> uuidFilenameMap) {

        // 目标目录路径
        String directoryPath = "/Users/jianhuilin/Downloads/target/IMAGE";
        File directory = new File(directoryPath);

        // 用于存储uuid和文件名的映射关系
        Map<String, String> imagenameMap = new HashMap<>();

        // 检查目录是否存在
        if (directory.exists() && directory.isDirectory()) {
            // 遍历目录下的所有文件夹
            File[] folders = directory.listFiles(File::isDirectory);
            if (folders!= null) {
                for (File folder : folders) {
                    // 获取文件夹内的文件列表并查找meta.yaml文件
                    File[] files = folder.listFiles();
                    if (files!= null) {
                        for (File file : files) {
                            if (file.isFile() && file.getName().equals("meta.yaml")) {
                                try {
                                    // 读取meta.yaml文件内容
                                    Yaml yaml = new Yaml();
                                    Map<String, Object> metaYamlData = yaml.load(new FileInputStream(file));
                                    // 假设meta.yaml中uuid字段的键为"uuid"，根据实际情况修改
                                    if (metaYamlData.containsKey("name")) {
                                        String uuid = (String) metaYamlData.get("name");
                                        imagenameMap.put(uuid, file.getName());
                                    } else {
                                        System.out.println(file.getName() + "    - meta.yaml文件中未找到name字段");
                                    }
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("指定的目录不存在或不是一个目录。");
        }

        for (Map.Entry<String, String> entry : uuidFilenameMap.entrySet()) {
            if (Objects.isNull(imagenameMap.get(entry.getKey()))){
                System.out.println("  - resourceName: " + entry.getKey() + ", 文件名: " + entry.getValue());
            }
        }
    }


    // 文件对比
    public static void main3(String [] a){
        HashSet<String> set = Sets.newHashSet(
                "ElasticSearch 目录穿越漏洞",
                "XAMPP 跨站脚本漏洞",
                "MinIO未授权SSRF漏洞",
                "Spring-messaging远程代码执行漏洞",
                "Samba 3.5.0远程代码执行漏洞",
                "Weblogic 权限绕过漏洞",
                "Nexus远程命令执行漏洞2019-7238",
                "Weblogic远程代码执行漏洞2",
                "Drupal 远程代码执行漏洞",
                "Supervisord 远程命令执行漏洞",
                "Dedecms后台getshell漏洞",
                "Apache Shiro 权限绕过漏洞",
                "Apache OFBiz反序列化漏洞",
                "Weblogic 权限绕过漏洞",
                "Webmin 命令执行漏洞",
                "WordPress 5.0.0 远程代码执行漏洞",
                "phpmyadmin SQL注入",
                "GoAhead  远程命令执行漏洞",
                "ThinkCMF5.0.19后台代码执行",
                "Weblogic 反序列化漏洞",
                "Ruby on Rails 反序列化漏洞",
                "Apache HTTPD 换行解析漏洞",
                "JBoss 5.x/6.x 反序列化漏洞",
                "OpenSSH命令注入漏洞",
                "[Jira]未授权敏感信息泄露",
                "Struts2 S2-059远程代码执行漏洞",
                "UCMS文件上传漏洞",
                "ElasticSearch Groovy 沙盒绕过&代码执行",
                "uWSGI PHP目录穿越漏洞",
                "WebLogic Server 远程代码执行漏洞",
                "Drupal8 REST RCE 漏洞",
                "Couchdb 任意命令执行漏洞",
                "Drupal XSS漏洞",
                "Weblogic远程代码执行漏洞",
                "XStream反序列化远程代码执行漏洞",
                "Ruby on Rails 路径穿越",
                "JBoss 4.x JBossMQ JMS 反序列化漏洞",
                "Apache OFBiz反序列化漏洞",
                "Couchdb 垂直权限绕过漏洞",
                "Webmin远程命令执行漏洞",
                "Weblogic 任意文件上传漏洞",
                "Weblogic反序列化漏洞CVE-2016-3510",
                "Jenkins-CI远程代码执行漏洞",
                "ActiveMQ任意文件写入漏洞",
                "Joomla Session SQL注入漏洞",
                "CuppaCMS 文件上传漏洞",
                "Typesetter CMS 文件上传漏洞",
                "ThinkAdmin任意文件读取漏洞",
                "OpenSMTPD 远程代码执行漏洞",
                "ActiveMQ 反序列化漏洞",
                "Weblogic远程代码执行3",
                "CUBERITE WEBADMIN 目录遍历",
                "Grafana 任意文件读取漏洞");
        System.out.println(set);

        HashSet<String> setExist = Sets.newHashSet("[Jira]未授权敏感信息泄露",
                "ActiveMQ 反序列化漏洞",
                "ActiveMQ任意文件写入漏洞",
                "Apache OFBiz反序列化漏洞",
                "Apache Shiro 权限绕过漏洞",
                "Couchdb 任意命令执行漏洞",
                "Couchdb 垂直权限绕过漏洞",
                "CuppaCMS 文件上传漏洞",
                "Dedecms后台getshell漏洞",
                "Drupal XSS漏洞",
                "Drupal 远程代码执行漏洞",
                "Drupal8 REST RCE 漏洞",
                "ElasticSearch 目录穿越漏洞",
                "Grafana 任意文件读取漏洞",
                "JBoss 4.x JBossMQ JMS 反序列化漏洞",
                "JBoss 5.x/6.x 反序列化漏洞",
                "Jenkins-CI远程代码执行漏洞",
                "Joomla Session SQL注入漏洞",
                "MinIO未授权SSRF漏洞",
                "Nexus远程命令执行漏洞2019-7238",
                "OpenSMTPD 远程代码执行漏洞",
                "OpenSSH命令注入漏洞",
                "phpmyadmin SQL注入",
                "Ruby on Rails 反序列化漏洞",
                "Ruby on Rails 路径穿越",
                "Samba 3.5.0远程代码执行漏洞",
                "Spring-messaging远程代码执行漏洞",
                "Struts2 S2-059远程代码执行漏洞",
                "Supervisord 远程命令执行漏洞",
                "ThinkAdmin任意文件读取漏洞",
                "ThinkCMF5.0.19后台代码执行",
                "Typesetter CMS 文件上传漏洞",
                "UCMS文件上传漏洞",
                "WebLogic Server 远程代码执行漏洞",
                "Weblogic 任意文件上传漏洞",
                "Weblogic 反序列化漏洞",
                "Weblogic 权限绕过漏洞",
                "Weblogic反序列化漏洞CVE-2016-3510",
                "Weblogic远程代码执行3",
                "Weblogic远程代码执行漏洞",
                "Weblogic远程代码执行漏洞2",
                "Webmin 命令执行漏洞",
                "Webmin远程命令执行漏洞",
                "WordPress 5.0.0 远程代码执行漏洞",
                "XAMPP 跨站脚本漏洞");
        set.forEach(need -> {
            if (!setExist.contains(need)){
                System.out.println("数据库中缺少的镜像：" + need);
            }
        });
    }
}
