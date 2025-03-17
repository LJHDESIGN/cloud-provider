package com.cloud.cloudprovider.util;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.data.MutableDataSet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author linjianhui
 * @description Markdown 解析器
 * @date 2024/3/19 10:20
 */
@Slf4j
public class MarkdownParser {

    public static void main1(String[] args) {
        String markdownContent = "# Heading\n\nThis is a paragraph with a local image ![alt text](/path/to/local/image.png).";
        try {
            markdownContent = new String(Files.readAllBytes(Paths.get("/Users/jianhuilin/Desktop/Wp/test_副本.md")));
        } catch (IOException e) {
            log.error("获取本地Markdown失败", e);
        }

        // 创建 Flexmark 解析器
        MutableDataSet options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, Arrays.asList(
                AutolinkExtension.create(),
                TablesExtension.create(),
                YamlFrontMatterExtension.create()
        ));
        Parser parser = Parser.builder(options).build();

        // 解析 Markdown 内容
        Node document = parser.parse(markdownContent);

        // 替换图片地址为图床 URL
        replaceLocalImageUrls(document);

//        String serialize = serialize(document);

        // 读取document
//        Formatter formatter = Formatter.builder(options).build();
//        String html = formatter.render(document);
//        System.out.println(html);

        // 将修改后的 Markdown 内容输出

        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        // 将修改后的节点树渲染为 HTML 文本
        String htmlOutput = renderer.render(document);

        // 打印或处理 HTML 输出
        System.out.println(htmlOutput);


    }



    static void replaceLocalImageUrls(Node node) {
        NodeVisitor visitor = new NodeVisitor(
                new VisitHandler<>(com.vladsch.flexmark.ast.Image.class, image -> {
                    // 获取图片节点的 URL
                    String imageUrl = image.getUrl().toString();
                    // 替换本地图片地址为图床 URL
                    if (imageUrl.startsWith("./")) {
                        // 创建 BasedSequence 对象
                        com.vladsch.flexmark.util.sequence.BasedSequence newUrl =
                                com.vladsch.flexmark.util.sequence.BasedSequence.of("https://example.com/images/" + imageUrl.substring(imageUrl.lastIndexOf("./") + 1));
                        // 设置图片节点的 URL
                        image.setUrl(newUrl);
                    }
                })
        );
        visitor.visit(node);
    }

    public static String serialize(Node node) {
        StringBuilder stringBuilder = new StringBuilder();
        NodeVisitor visitor = new NodeVisitor(
                new VisitHandler<>(Text.class, text -> stringBuilder.append(text.getChars()))
        );
        visitor.visit(node);
        return stringBuilder.toString();
    }



    private final static Pattern pattern = Pattern.compile("!\\[(.*?)\\]\\((.*?)\\)");

    public static void main2(String[] args) {
        // 输入的 Markdown 文件路径
        String inputFilePath = "/Users/jianhuilin/Desktop/Wp/test_副本.md";
        // 输出的 Markdown 文件路径
        String outputFilePath = "/Users/jianhuilin/Desktop/Wp/output.md";
        // 图床的基础 URL
        String imageBaseUrl = "https://example.com/images/";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));

            String line;
            while ((line = reader.readLine()) != null) {
                // 使用正则表达式匹配图片地址
                Matcher matcher = pattern.matcher(line);

                StringBuffer buffer = new StringBuffer();
                while (matcher.find()) {
                    String altText = matcher.group(1);
                    String imagePath = matcher.group(2);

                    // 替换图片地址为图床 URL
                    String newImagePath = imageBaseUrl + imagePath.substring(imagePath.lastIndexOf("/") + 1);
                    matcher.appendReplacement(buffer, "![" + altText + "](" + newImagePath + ")");
                }
                matcher.appendTail(buffer);

                // 写入替换后的行到输出文件
                writer.write(buffer.toString());
                writer.newLine();
            }

            // 关闭文件读写流
            reader.close();
            writer.close();

            System.out.println("图片地址替换完成。");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 定义 Markdown 文件路径
        File markdownFile = new File("/Users/jianhuilin/Desktop/Wp/test_副本.md");

        // 定义图片文件夹路径（假设图片文件夹与 Markdown 文件在同一目录下）
        String imageFolder = markdownFile.getParent() + "/images";

        try {
            // 读取 Markdown 文件内容到字符串
            String markdownContent = new String(Files.readAllBytes(Paths.get(markdownFile.getPath())), "UTF-8");

            // 使用正则表达式匹配图片地址
            Matcher matcher = pattern.matcher(markdownContent);


            StringBuffer buffer = new StringBuffer();
            while (matcher.find()) {
                String altText = matcher.group(1);
                String imagePath = matcher.group(2);

                // 替换图片地址为图床 URL
                String newImagePath = uploadImageToAliyun(imagePath);
                matcher.appendReplacement(buffer, "![" + altText + "](" + newImagePath + ")");
            }
            matcher.appendTail(buffer);

            // 输出修改后的 Markdown 内容
            System.out.println(buffer.toString());

            String outputFilePath = "/Users/jianhuilin/Desktop/Wp/output2.md";
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
            writer.write(buffer.toString());
            writer.close();

            // 如果需要，将修改后的 Markdown 内容写回到文件中
            // FileUtils.writeStringToFile(markdownFile, modifiedMarkdownContent, "UTF-8");
        } catch (IOException e) {
            // 处理读取文件或写入文件时的异常
            e.printStackTrace();
        }
    }


    static String uploadImageToAliyun(String imagePath) {
        // 图床的基础 URL
        String imageBaseUrl = "https://example.com/images/".substring(imagePath.lastIndexOf("/") + 1);;
        // 在这里实现上传图片到阿里云的逻辑

        // 这里只是一个示例，假设上传成功后返回一个假的阿里云链接
        return "https://aliyun.com/images/your-image.jpg";
    }

    public static void main5(String[] args) {
        String markdownContent = "# Heading\n\nThis is a paragraph with a local image ![alt text](/path/to/local/image.png).";
        try {
            markdownContent = new String(Files.readAllBytes(Paths.get("/Users/jianhuilin/Desktop/Wp/test_副本.md")));
        } catch (IOException e) {
            log.error("获取本地Markdown失败", e);
        }

        // 创建 Flexmark 解析器
        MutableDataSet options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, Arrays.asList(
                AutolinkExtension.create(),
                TablesExtension.create(),
                YamlFrontMatterExtension.create()
        ));
        Parser parser = Parser.builder(options).build();

        // 解析 Markdown 内容
        Node root = parser.parse(markdownContent);

        Document a=  root.getDocument();


    }
}
