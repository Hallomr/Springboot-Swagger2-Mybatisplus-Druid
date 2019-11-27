/*
 Navicat Premium Data Transfer

 Source Server         : localhost_5432
 Source Server Type    : PostgreSQL
 Source Server Version : 100011
 Source Host           : localhost:5432
 Source Catalog        : mp
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 100011
 File Encoding         : 65001

 Date: 25/11/2019 17:17:11
*/


-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS "public"."users";
CREATE TABLE "public"."users" (
  "id" serial8 NOT NULL,
  "password" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "username" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "status" int2 NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6),
  "content" jsonb
)
;
COMMENT ON COLUMN "public"."users"."id" IS '主键id';
COMMENT ON COLUMN "public"."users"."password" IS '用户密码';
COMMENT ON COLUMN "public"."users"."username" IS '用户名';
COMMENT ON COLUMN "public"."users"."status" IS '状态 1启用 0 停用';
COMMENT ON COLUMN "public"."users"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."users"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."users"."content" IS '相关内容';

-- ----------------------------
-- Primary Key structure for table users
-- ----------------------------
ALTER TABLE "public"."users" ADD CONSTRAINT "users_pkey" PRIMARY KEY ("id");
