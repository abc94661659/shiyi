/*
 Navicat Premium Data Transfer

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 80404
 Source Host           : localhost:3306
 Source Schema         : blog_article

 Target Server Type    : MySQL
 Target Server Version : 80404
 File Encoding         : 65001

 Date: 30/10/2025 20:32:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章标题',
  `summary` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文章摘要',
  `author_id` bigint NOT NULL COMMENT '作者ID',
  `category_id` int NOT NULL COMMENT '分类ID',
  `cover_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封面图URL',
  `view_count` int NOT NULL DEFAULT 0 COMMENT '阅读量',
  `comment_count` int NOT NULL DEFAULT 0 COMMENT '评论数',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0-草稿/1-待审核/2-已发布/3-已拒绝/4-已下架）',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除（0-未删除/1-已删除）',
  `is_top` tinyint NOT NULL DEFAULT 0 COMMENT '是否置顶（0-否/1-是）',
  `auditor_id` bigint NULL DEFAULT NULL COMMENT '审核人ID',
  `audit_time` datetime NULL DEFAULT NULL COMMENT '审核时间',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_author_id`(`author_id` ASC) USING BTREE,
  INDEX `idx_category_id`(`category_id` ASC) USING BTREE,
  INDEX `idx_status_publish_time`(`status` ASC, `publish_time` ASC) USING BTREE,
  INDEX `idx_is_top_publish_time`(`is_top` ASC, `publish_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, '今天是个好日子', '测试简介', 1, 1, '封面图', 0, 0, 0, 0, 0, NULL, NULL, NULL, '2025-10-18 16:45:34', '2025-10-18 17:33:20');
INSERT INTO `article` VALUES (2, '111', '', 1, 1, '', 0, 0, 0, 0, 0, NULL, NULL, NULL, '2025-10-19 18:14:12', '2025-10-19 18:14:12');
INSERT INTO `article` VALUES (6, '测试文章', '', 1, 1, '', 0, 0, 0, 0, 0, NULL, NULL, NULL, '2025-10-19 19:19:20', '2025-10-19 19:19:20');
INSERT INTO `article` VALUES (7, '11111111', '', 1, 1, '', 0, 0, 0, 0, 0, NULL, NULL, NULL, '2025-10-19 19:27:37', '2025-10-19 19:27:37');
INSERT INTO `article` VALUES (8, '111', '111', 1, 1, '', 0, 0, 0, 0, 0, NULL, NULL, NULL, '2025-10-24 22:49:00', '2025-10-24 22:49:00');
INSERT INTO `article` VALUES (10, '测试123', '测试123', 1, 1, '', 0, 0, 0, 0, 0, NULL, NULL, NULL, '2025-10-25 00:09:57', '2025-10-25 00:09:57');

-- ----------------------------
-- Table structure for article_content
-- ----------------------------
DROP TABLE IF EXISTS `article_content`;
CREATE TABLE `article_content`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `article_id` bigint NOT NULL COMMENT '关联文章ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章正文',
  `content_version` int NOT NULL DEFAULT 1 COMMENT '内容版本号',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章内容表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_content
-- ----------------------------
INSERT INTO `article_content` VALUES (1, 1, '这是文章的正文', 1, '2025-10-18 16:45:34');
INSERT INTO `article_content` VALUES (2, 1, '# 测试\n\n```java\npackage com.linshiyi.article.utils;\n\nimport org.commonmark.Extension;\nimport org.commonmark.ext.autolink.AutolinkExtension;\nimport org.commonmark.ext.gfm.tables.TablesExtension;\nimport org.commonmark.parser.Parser;\nimport org.commonmark.renderer.html.HtmlRenderer;\n\nimport java.util.Arrays;\nimport java.util.List;\n\n/**\n * Markdown 与 HTML 转换工具类\n */\npublic class MarkdownUtils {\n\n    // 初始化扩展（支持表格、自动链接等）\n    private static final List<Extension> EXTENSIONS = Arrays.asList(\n            TablesExtension.create(), // 支持 GitHub 风格表格（| 表头 | ... |）\n            AutolinkExtension.create() // 自动识别链接并转换为 <a> 标签\n    );\n\n    // 初始化解析器（带扩展）\n    private static final Parser PARSER = Parser.builder()\n            .extensions(EXTENSIONS)\n            .build();\n\n    // 初始化 HTML 渲染器（带扩展）\n    private static final HtmlRenderer RENDERER = HtmlRenderer.builder()\n            .extensions(EXTENSIONS)\n            .build();\n\n    /**\n     * 将 Markdown 文本转换为 HTML\n     * @param markdown 原始 Markdown 字符串（如 \"# 标题\\n\\n代码块```java...```\"）\n     * @return 转换后的 HTML 字符串\n     */\n    public static String markdownToHtml(String markdown) {\n        if (markdown == null || markdown.trim().isEmpty()) {\n            return \"\";\n        }\n        // 解析 Markdown 为文档对象\n        var document = PARSER.parse(markdown);\n        // 渲染为 HTML\n        return RENDERER.render(document);\n    }\n}\n```\n# 1\n\n```java\n@GetMapping(\"/hello\")\n    public Result<String> hello() {\n        LocalDateTime  now = LocalDateTime.now();\n        return Result.success(now.format(DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")));\n    }\n```', 2, '2025-10-18 17:19:55');
INSERT INTO `article_content` VALUES (10, 6, '# 测试\n\n```java\npackage com.linshiyi.article.utils;\n\nimport org.commonmark.Extension;\nimport org.commonmark.ext.autolink.AutolinkExtension;\nimport org.commonmark.ext.gfm.tables.TablesExtension;\nimport org.commonmark.parser.Parser;\nimport org.commonmark.renderer.html.HtmlRenderer;\n\nimport java.util.Arrays;\nimport java.util.List;\n\n/**\n * Markdown 与 HTML 转换工具类\n */\npublic class MarkdownUtils {\n\n    // 初始化扩展（支持表格、自动链接等）\n    private static final List<Extension> EXTENSIONS = Arrays.asList(\n            TablesExtension.create(), // 支持 GitHub 风格表格（| 表头 | ... |）\n            AutolinkExtension.create() // 自动识别链接并转换为 <a> 标签\n    );\n\n    // 初始化解析器（带扩展）\n    private static final Parser PARSER = Parser.builder()\n            .extensions(EXTENSIONS)\n            .build();\n\n    // 初始化 HTML 渲染器（带扩展）\n    private static final HtmlRenderer RENDERER = HtmlRenderer.builder()\n            .extensions(EXTENSIONS)\n            .build();\n\n    /**\n     * 将 Markdown 文本转换为 HTML\n     * @param markdown 原始 Markdown 字符串（如 \"# 标题\\n\\n代码块```java...```\"）\n     * @return 转换后的 HTML 字符串\n     */\n    public static String markdownToHtml(String markdown) {\n        if (markdown == null || markdown.trim().isEmpty()) {\n            return \"\";\n        }\n        // 解析 Markdown 为文档对象\n        var document = PARSER.parse(markdown);\n        // 渲染为 HTML\n        return RENDERER.render(document);\n    }\n}\n```\n# 1\n\n```java\n@GetMapping(\"/hello\")\n    public Result<String> hello() {\n        LocalDateTime  now = LocalDateTime.now();\n        return Result.success(now.format(DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")));\n    }\n```', 1, '2025-10-19 19:19:20');
INSERT INTO `article_content` VALUES (11, 7, '# 测试\n\n```java\npackage com.linshiyi.article.utils;\n\nimport org.commonmark.Extension;\nimport org.commonmark.ext.autolink.AutolinkExtension;\nimport org.commonmark.ext.gfm.tables.TablesExtension;\nimport org.commonmark.parser.Parser;\nimport org.commonmark.renderer.html.HtmlRenderer;\n\nimport java.util.Arrays;\nimport java.util.List;\n\n/**\n * Markdown 与 HTML 转换工具类\n */\npublic class MarkdownUtils {\n\n    // 初始化扩展（支持表格、自动链接等）\n    private static final List<Extension> EXTENSIONS = Arrays.asList(\n            TablesExtension.create(), // 支持 GitHub 风格表格（| 表头 | ... |）\n            AutolinkExtension.create() // 自动识别链接并转换为 <a> 标签\n    );\n\n    // 初始化解析器（带扩展）\n    private static final Parser PARSER = Parser.builder()\n            .extensions(EXTENSIONS)\n            .build();\n\n    // 初始化 HTML 渲染器（带扩展）\n    private static final HtmlRenderer RENDERER = HtmlRenderer.builder()\n            .extensions(EXTENSIONS)\n            .build();\n\n    /**\n     * 将 Markdown 文本转换为 HTML\n     * @param markdown 原始 Markdown 字符串（如 \"# 标题\\n\\n代码块```java...```\"）\n     * @return 转换后的 HTML 字符串\n     */\n    public static String markdownToHtml(String markdown) {\n        if (markdown == null || markdown.trim().isEmpty()) {\n            return \"\";\n        }\n        // 解析 Markdown 为文档对象\n        var document = PARSER.parse(markdown);\n        // 渲染为 HTML\n        return RENDERER.render(document);\n    }\n}\n```\n# 1\n\n```java\n@GetMapping(\"/hello\")\n    public Result<String> hello() {\n        LocalDateTime  now = LocalDateTime.now();\n        return Result.success(now.format(DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")));\n    }\n```', 1, '2025-10-19 19:27:37');
INSERT INTO `article_content` VALUES (12, 8, '#1 你好啊', 1, '2025-10-24 22:49:00');
INSERT INTO `article_content` VALUES (13, 10, '测试123', 1, '2025-10-25 00:09:57');

SET FOREIGN_KEY_CHECKS = 1;
