package com.cloud.cloudprovider.util;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

public class RIDGenerator {

    // 主体名称（固定为 NGCR）
    private static final String ENTITY = "NGCR";

    // 资源类型
    private static final String[] RESOURCE_TYPES = {"IM", "QU", "CT", "EP", "CO", "TR"};

    // 序号的32进制字符表
    private static final String BASE32_CHARACTERS = "0123456789ABCDEFGHIJKLMOPQRSTUVWXYZ";

    // 版本号的36进制字符表
    private static final String BASE36_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // 起始年月
    private static final int START_YEAR = 2024;
    private static final int START_MONTH = 1;

    public static void main(String[] args) {
        // 示例生成RID
        String resourceType = "IM";  // 示例资源类型
        String currentVersion = "00";  // 初始版本号
        String rid = generateRID(resourceType, currentVersion);
        System.out.println("Generated RID: " + rid);

        // 示例版本号递增
        String nextVersion = incrementVersion(currentVersion);
        String ridWithNextVersion = generateRID(resourceType, nextVersion);
        System.out.println("RID with next version: " + ridWithNextVersion);
    }

    // 生成 RID
    public static String generateRID(String resourceType, String version) {
        // 1. 主体名称
        String entity = ENTITY;

        // 2. 资源类型
        if (!isValidResourceType(resourceType)) {
            throw new IllegalArgumentException("Invalid resource type");
        }

        // 3. 年月
        String yearMonth = generateYearMonth();

        // 4. 4位序号
        String sequence = generateRandomBase32Sequence(4);

        // 5. 版本号
        if (!isValidVersion(version)) {
            throw new IllegalArgumentException("Invalid version format");
        }

        // 6. 校验码
        String ridWithoutChecksum = entity + "-" + resourceType + "-" + yearMonth + "-" + sequence + "-" + version;
        char checksum = generateChecksum(ridWithoutChecksum);

        // 完整 RID
        return ridWithoutChecksum + "-" + checksum;
    }

    // 检查资源类型是否合法
    private static boolean isValidResourceType(String resourceType) {
        for (String type : RESOURCE_TYPES) {
            if (type.equals(resourceType)) {
                return true;
            }
        }
        return false;
    }

    // 生成年月
    private static String generateYearMonth() {
        // 当前时间
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();

        // 计算偏移量
        int yearDiff = currentYear - START_YEAR;
        int monthDiff = currentMonth - START_MONTH;
        int totalMonths = yearDiff * 12 + monthDiff;

        // 将偏移量转换为 4 位数字
        return String.format("%04d", totalMonths);
    }

    // 生成4位随机32进制序号
    private static String generateRandomBase32Sequence(int length) {
        Random random = new Random();
        StringBuilder sequence = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(BASE32_CHARACTERS.length());
            sequence.append(BASE32_CHARACTERS.charAt(index));
        }

        return sequence.toString();
    }

    // 校验版本号是否合法
    private static boolean isValidVersion(String version) {
        return version != null && version.matches("^[0-9A-Z]{2}$");
    }

    // 生成校验码
    private static char generateChecksum(String ridWithoutChecksum) {
        int sum = 0;
        for (char c : ridWithoutChecksum.toCharArray()) {
            if (c != '-') {  // 跳过分隔符
                sum += c;
            }
        }
        int checksum = sum % 10;  // 取个位数
        return (char) ('0' + checksum);
    }

    // 版本号递增（36进制，自增1）
    public static String incrementVersion(String currentVersion) {
        // 将当前版本号转换为十进制
        int versionDecimal = Integer.parseInt(currentVersion, 36);
        // 自增1
        versionDecimal++;
        // 转回36进制
        String base36String = Integer.toString(versionDecimal, 36);
        // 确保两位长度
        while (base36String.length() < 2) {
            base36String = "0" + base36String;
        }
        return base36String.toUpperCase();
    }
}
