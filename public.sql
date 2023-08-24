/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : PostgreSQL
 Source Server Version : 120011
 Source Host           : localhost:5432
 Source Catalog        : v3-admin
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 120011
 File Encoding         : 65001

 Date: 24/08/2023 14:31:05
*/


-- ----------------------------
-- Table structure for sys_client
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_client";
CREATE TABLE "public"."sys_client" (
  "client_id" int8 NOT NULL,
  "client_key" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "client_secret" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "client_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "create_by" varchar(64) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_by" varchar(64) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."sys_client"."client_id" IS '主键';
COMMENT ON COLUMN "public"."sys_client"."client_key" IS '客户端key';
COMMENT ON COLUMN "public"."sys_client"."client_secret" IS '客户端密码';
COMMENT ON COLUMN "public"."sys_client"."client_name" IS '客户端名称';

-- ----------------------------
-- Records of sys_client
-- ----------------------------
INSERT INTO "public"."sys_client" VALUES (1613402658943131649, 'bd61192a317d41bab74eb58b6b55fa4d', 'a4302c96c4e84895b3ccaa7d27f1a2d4', 'test客户端', 'zhong', '2023-01-12 13:09:04.131', 'zhong', '2023-01-12 14:34:03.88');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_config";
CREATE TABLE "public"."sys_config" (
  "config_id" int8 NOT NULL,
  "config_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "config_key" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "config_value" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6),
  "create_by" varchar(64) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "update_by" varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_config"."config_id" IS '配置id';
COMMENT ON COLUMN "public"."sys_config"."config_name" IS '配置名称';
COMMENT ON COLUMN "public"."sys_config"."config_key" IS '配置key';
COMMENT ON COLUMN "public"."sys_config"."config_value" IS '配置value';
COMMENT ON TABLE "public"."sys_config" IS '系统配置表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO "public"."sys_config" VALUES (1, '初始密码', 'initPassword', '123456', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dept";
CREATE TABLE "public"."sys_dept" (
  "dept_id" int8 NOT NULL,
  "parent_id" int8 NOT NULL,
  "dept_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "order_num" int4 NOT NULL,
  "create_by" varchar(64) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_by" varchar COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "tree_path" varchar(1024) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_dept"."dept_id" IS 'id';
COMMENT ON COLUMN "public"."sys_dept"."parent_id" IS '父类id';
COMMENT ON COLUMN "public"."sys_dept"."dept_name" IS '部门名称';
COMMENT ON COLUMN "public"."sys_dept"."order_num" IS '排序';
COMMENT ON COLUMN "public"."sys_dept"."tree_path" IS '父节点id';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO "public"."sys_dept" VALUES (1605373585801097217, 0, '阿麒公司', 0, '', NULL, 'zhong', '2023-08-22 16:39:41.271', '0');
INSERT INTO "public"."sys_dept" VALUES (1605373585801097219, 1605373585801097217, '开发部', 0, '', NULL, 'zhong', '2023-08-22 16:40:28.808', '0,1605373585801097217');
INSERT INTO "public"."sys_dept" VALUES (1605373585801097218, 1605373585801097219, '测试部', 0, '', NULL, 'zhong', '2023-08-22 16:40:44.581', '0,1605373585801097217,1605373585801097219');
INSERT INTO "public"."sys_dept" VALUES (1605376298555330561, 1605373585801097219, '运维部', 0, '', NULL, 'zhong', '2023-08-22 16:40:57.47', '0,1605373585801097217,1605373585801097219');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dict";
CREATE TABLE "public"."sys_dict" (
  "dict_code" int8 NOT NULL,
  "dict_label" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_value" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_type" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "create_by" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_by" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."sys_dict"."dict_code" IS '主键id';
COMMENT ON COLUMN "public"."sys_dict"."dict_label" IS '字典项释义';
COMMENT ON COLUMN "public"."sys_dict"."dict_value" IS '字典项值';
COMMENT ON COLUMN "public"."sys_dict"."dict_type" IS '字典项类型';
COMMENT ON COLUMN "public"."sys_dict"."dict_name" IS '字段名称';
COMMENT ON TABLE "public"."sys_dict" IS '系统码表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO "public"."sys_dict" VALUES (1590561828507324418, '女', '1', 'sys_user_sex', '用户性别女', NULL, NULL, 'admin', '2022-07-06 05:21:44');
INSERT INTO "public"."sys_dict" VALUES (1590562296960647169, '男', '0', 'sys_user_sex', '用户性别男', NULL, NULL, NULL, NULL);
INSERT INTO "public"."sys_dict" VALUES (1590562530717605889, '显示', '0', 'sys_show_hide', '显示', NULL, NULL, NULL, NULL);
INSERT INTO "public"."sys_dict" VALUES (1590562683046359041, '隐藏', '1', 'sys_show_hide', '隐藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."sys_dict" VALUES (1593528559924158465, '立即执行', '1', 'error_policy', '定时任务错误策略', 'admin', '2022-11-18 16:56:30', 'admin', '2022-11-18 16:56:30');
INSERT INTO "public"."sys_dict" VALUES (1593528740014989314, '执行一次', '2', 'error_policy', '定时任务错误策略', 'admin', '2022-11-18 16:57:13', 'admin', '2022-11-18 16:57:13');
INSERT INTO "public"."sys_dict" VALUES (1593528840229494785, '放弃执行', '3', 'error_policy', '定时任务错误策略', 'admin', '2022-11-18 16:57:37', 'admin', '2022-11-18 16:57:37');
INSERT INTO "public"."sys_dict" VALUES (1593530105860726786, '允许', '0', 'concurrent', '是否允许并发', 'admin', '2022-11-18 17:02:38', 'admin', '2022-11-18 17:02:38');
INSERT INTO "public"."sys_dict" VALUES (1593530185200181250, '禁止', '1', 'concurrent', '是否允许并发', 'admin', '2022-11-18 17:02:57', 'admin', '2022-11-18 17:02:57');
INSERT INTO "public"."sys_dict" VALUES (1594639252673073153, '成功', '0', 'success_status', '通用状态成功', 'admin', '2022-11-21 18:29:59', 'admin', '2022-11-21 18:29:59');
INSERT INTO "public"."sys_dict" VALUES (1594639486111256577, '失败', '1', 'success_status', '通用状态失败', 'admin', '2022-11-21 18:30:55', 'admin', '2022-11-21 18:30:55');
INSERT INTO "public"."sys_dict" VALUES (1590561327237648385, '停用', '1', 'sys_status', '通用状态', NULL, NULL, 'zhong', '2023-01-03 10:49:45.255');
INSERT INTO "public"."sys_dict" VALUES (1676753260103860226, '记录', '0', 'save_log', '是否记录日志', 'zhong', '2023-07-06 08:41:44.438', 'zhong', '2023-07-06 08:41:44.438');
INSERT INTO "public"."sys_dict" VALUES (1676753335639080961, '忽略', '1', 'save_log', '是否记录日志', 'zhong', '2023-07-06 08:42:02.451', 'zhong', '2023-07-06 08:42:08.272');
INSERT INTO "public"."sys_dict" VALUES (1677165288673050626, '所有数据', '0', 'data_scope', '数据权限', 'zhong', '2023-07-07 11:58:59.703', 'zhong', '2023-07-07 11:59:30.225');
INSERT INTO "public"."sys_dict" VALUES (1677165360911548417, '部门及子部门数据', '1', 'data_scope', '数据权限', 'zhong', '2023-07-07 11:59:16.931', 'zhong', '2023-07-07 11:59:35.84');
INSERT INTO "public"."sys_dict" VALUES (1677165521675026433, '本部门数据', '2', 'data_scope', '数据权限', 'zhong', '2023-07-07 11:59:55.258', 'zhong', '2023-07-07 11:59:55.258');
INSERT INTO "public"."sys_dict" VALUES (1677165608434204674, '本人数据', '3', 'data_scope', '数据权限', 'zhong', '2023-07-07 12:00:15.942', 'zhong', '2023-07-07 12:00:15.942');
INSERT INTO "public"."sys_dict" VALUES (1590561440349589506, '启用', '0', 'sys_status', '通用状态', NULL, NULL, 'zhong', '2023-07-28 12:06:31.671');

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_job";
CREATE TABLE "public"."sys_job" (
  "job_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "job_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "invoke_target" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "cron_expression" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "misfire_policy" varchar(8) COLLATE "pg_catalog"."default" NOT NULL,
  "concurrent" varchar(8) COLLATE "pg_catalog"."default" NOT NULL,
  "status" varchar(8) COLLATE "pg_catalog"."default" NOT NULL,
  "create_by" varchar(64) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_by" varchar(64) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "invoke_method" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "invoke_param" varchar(255) COLLATE "pg_catalog"."default",
  "save_log" varchar(8) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_job"."job_id" IS 'id';
COMMENT ON COLUMN "public"."sys_job"."job_name" IS '定时任务名称';
COMMENT ON COLUMN "public"."sys_job"."invoke_target" IS '调用目标';
COMMENT ON COLUMN "public"."sys_job"."cron_expression" IS 'cron表达式';
COMMENT ON COLUMN "public"."sys_job"."misfire_policy" IS '计划执行错误策略（1立即执行 2执行一次 3放弃执行）';
COMMENT ON COLUMN "public"."sys_job"."concurrent" IS '是否并发执行（0允许 1禁止）';
COMMENT ON COLUMN "public"."sys_job"."status" IS '状态';
COMMENT ON COLUMN "public"."sys_job"."invoke_method" IS '调用方法';
COMMENT ON COLUMN "public"."sys_job"."invoke_param" IS '参数';
COMMENT ON COLUMN "public"."sys_job"."save_log" IS '是否保存日志';

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO "public"."sys_job" VALUES ('1610872324963700738', '清理定时日志', 'clearLogTask', '0 20 1 * * ?', '1', '0', '1', 'zhong', '2023-01-05 13:34:25.491', 'zhong', '2023-07-06 08:51:55.052', 'clearJobLog', NULL, '0');
INSERT INTO "public"."sys_job" VALUES ('1610871878773641218', '清理系统日志', 'clearLogTask', '0 15 1 * * ?', '1', '0', '1', 'zhong', '2023-01-05 13:32:39.11', 'zhong', '2023-07-06 14:17:28.375', 'clearSysLog', NULL, '0');

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_job_log";
CREATE TABLE "public"."sys_job_log" (
  "job_log_id" int8 NOT NULL,
  "job_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "invoke_target" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "job_message" varchar(255) COLLATE "pg_catalog"."default",
  "status" varchar(8) COLLATE "pg_catalog"."default" NOT NULL,
  "exception_info" varchar(255) COLLATE "pg_catalog"."default",
  "invoke_method" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "invoke_param" varchar(255) COLLATE "pg_catalog"."default",
  "start_time" timestamp(6) NOT NULL,
  "end_time" timestamp(6) NOT NULL
)
;
COMMENT ON COLUMN "public"."sys_job_log"."job_log_id" IS 'id';
COMMENT ON COLUMN "public"."sys_job_log"."job_name" IS '任务名称';
COMMENT ON COLUMN "public"."sys_job_log"."invoke_target" IS '目标类';
COMMENT ON COLUMN "public"."sys_job_log"."job_message" IS '日志';
COMMENT ON COLUMN "public"."sys_job_log"."status" IS '执行状态（0正常 1失败）';
COMMENT ON COLUMN "public"."sys_job_log"."exception_info" IS '异常';
COMMENT ON COLUMN "public"."sys_job_log"."invoke_method" IS '调用目标方法';
COMMENT ON COLUMN "public"."sys_job_log"."invoke_param" IS '参数';
COMMENT ON COLUMN "public"."sys_job_log"."start_time" IS '定时任务开始时间';
COMMENT ON COLUMN "public"."sys_job_log"."end_time" IS '定时任务结束时间';

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_log";
CREATE TABLE "public"."sys_log" (
  "log_id" int8 NOT NULL,
  "execution_time" int8 NOT NULL,
  "ip" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "method" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "url" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "username" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "visit_time" timestamp(6) NOT NULL
)
;
COMMENT ON COLUMN "public"."sys_log"."log_id" IS 'id';
COMMENT ON COLUMN "public"."sys_log"."execution_time" IS '执行时间';
COMMENT ON COLUMN "public"."sys_log"."ip" IS 'ip';
COMMENT ON COLUMN "public"."sys_log"."method" IS '方法';
COMMENT ON COLUMN "public"."sys_log"."url" IS '地址';
COMMENT ON COLUMN "public"."sys_log"."username" IS '用户名';
COMMENT ON COLUMN "public"."sys_log"."visit_time" IS '访问时间';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO "public"."sys_log" VALUES (1694546725550206977, 3938, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 11:06:33.238');
INSERT INTO "public"."sys_log" VALUES (1694547203025580034, 47599, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 11:07:43.428');
INSERT INTO "public"."sys_log" VALUES (1694547380415279106, 26199, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 11:08:47.121');
INSERT INTO "public"."sys_log" VALUES (1694547736318730242, 50, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 11:10:38.113');
INSERT INTO "public"."sys_log" VALUES (1694548222736359426, 93548, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 11:11:00.591');
INSERT INTO "public"."sys_log" VALUES (1694549688364589058, 86974, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 11:16:56.599');
INSERT INTO "public"."sys_log" VALUES (1694550663431217153, 65816, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 11:21:10.231');
INSERT INTO "public"."sys_log" VALUES (1694550799154700289, 29, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 11:22:48.387');
INSERT INTO "public"."sys_log" VALUES (1694550974950563841, 37362, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 11:22:52.959');
INSERT INTO "public"."sys_log" VALUES (1694551038846590978, 8659, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 11:23:36.9');
INSERT INTO "public"."sys_log" VALUES (1694551290626465793, 31, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 11:24:45.564');
INSERT INTO "public"."sys_log" VALUES (1694551506897362946, 38663, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 11:24:58.484');
INSERT INTO "public"."sys_log" VALUES (1694577946271444993, 58, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 13:10:40.719');
INSERT INTO "public"."sys_log" VALUES (1694578040962052097, 30, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 13:11:03.334');
INSERT INTO "public"."sys_log" VALUES (1694578106191867905, 31, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 13:11:18.895');
INSERT INTO "public"."sys_log" VALUES (1694578498652893186, 51667, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 13:12:00.814');
INSERT INTO "public"."sys_log" VALUES (1694579046579990529, 55977, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 13:14:07.14');
INSERT INTO "public"."sys_log" VALUES (1694579909046333442, 33565, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 13:17:55.181');
INSERT INTO "public"."sys_log" VALUES (1694581009753333762, 117, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 13:22:51.05');
INSERT INTO "public"."sys_log" VALUES (1694581064459640834, 37, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 13:23:04.182');
INSERT INTO "public"."sys_log" VALUES (1694581131446870018, 29, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 13:23:20.169');
INSERT INTO "public"."sys_log" VALUES (1694581150933606402, 30, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 13:23:24.818');
INSERT INTO "public"."sys_log" VALUES (1694583643046436865, 55, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 13:33:18.938');
INSERT INTO "public"."sys_log" VALUES (1694583655721623553, 30, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 13:33:22.004');
INSERT INTO "public"."sys_log" VALUES (1694583681252352002, 33, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUser', '/user/updateUser', 'zhong', '2023-08-24 13:33:28.087');
INSERT INTO "public"."sys_log" VALUES (1694592183748288513, 34, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] createUser', '/user/createUser', 'zhong', '2023-08-24 14:07:15.241');
INSERT INTO "public"."sys_log" VALUES (1694592313612328962, 7, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUserProfile', '/user/updateUserProfile', 'zhong', '2023-08-24 14:07:46.223');
INSERT INTO "public"."sys_log" VALUES (1694592355278544897, 8, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUserProfile', '/user/updateUserProfile', 'zhong', '2023-08-24 14:07:56.156');
INSERT INTO "public"."sys_log" VALUES (1694592388208025601, 8, '127.0.0.1', '[类名] com.aqi.admin.controller.SysUserController[方法名] updateUserProfile', '/user/updateUserProfile', 'zhong', '2023-08-24 14:08:04.015');
INSERT INTO "public"."sys_log" VALUES (1694593440441458690, 16, '127.0.0.1', '[类名] com.aqi.admin.controller.SysRoleController[方法名] updateRole', '/role/updateRole', 'zhong', '2023-08-24 14:12:14.872');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_menu";
CREATE TABLE "public"."sys_menu" (
  "menu_id" int8 NOT NULL,
  "menu_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_parent_id" int8 NOT NULL,
  "menu_order_num" int4 NOT NULL,
  "menu_path" varchar(64) COLLATE "pg_catalog"."default",
  "menu_component" varchar(64) COLLATE "pg_catalog"."default",
  "menu_type" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_visible" varchar(5) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_perms" varchar(64) COLLATE "pg_catalog"."default",
  "menu_icon" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "create_by" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_by" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "redirect" varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_menu"."menu_id" IS '菜单id';
COMMENT ON COLUMN "public"."sys_menu"."menu_name" IS '菜单名称';
COMMENT ON COLUMN "public"."sys_menu"."menu_parent_id" IS '菜单上级id';
COMMENT ON COLUMN "public"."sys_menu"."menu_order_num" IS '菜单排序';
COMMENT ON COLUMN "public"."sys_menu"."menu_path" IS '菜单路径';
COMMENT ON COLUMN "public"."sys_menu"."menu_component" IS '菜单组件';
COMMENT ON COLUMN "public"."sys_menu"."menu_type" IS '菜单类型';
COMMENT ON COLUMN "public"."sys_menu"."menu_visible" IS '菜单可见';
COMMENT ON COLUMN "public"."sys_menu"."menu_perms" IS '菜单权限';
COMMENT ON COLUMN "public"."sys_menu"."menu_icon" IS '菜单图标';
COMMENT ON TABLE "public"."sys_menu" IS '系统菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO "public"."sys_menu" VALUES (1590912260672901122, '菜单管理', 1589912152229580802, 3, 'menu', 'system/menu/index', 'C', '0', NULL, 'menu', NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."sys_menu" VALUES (1591976130573275138, '用户新增', 1589913214403518465, 1, NULL, NULL, 'F', '0', 'system:user:add', '#', 'admin', '2022-11-14 10:07:42', 'admin', '2022-11-14 10:07:42', NULL);
INSERT INTO "public"."sys_menu" VALUES (1591976305236676610, '用户修改', 1589913214403518465, 2, NULL, NULL, 'F', '0', 'system:user:update', '#', 'admin', '2022-11-14 10:08:23', 'admin', '2022-11-14 10:08:23', NULL);
INSERT INTO "public"."sys_menu" VALUES (1591976390704009218, '用户删除', 1589913214403518465, 3, NULL, NULL, 'F', '0', 'system:user:delete', '#', 'admin', '2022-11-14 10:08:44', 'admin', '2022-11-14 10:08:44', NULL);
INSERT INTO "public"."sys_menu" VALUES (1591979441078702082, '用户列表', 1589913214403518465, 4, NULL, NULL, 'F', '0', 'system:user:list', '#', 'admin', '2022-11-14 10:20:51', 'admin', '2022-11-14 10:20:51', NULL);
INSERT INTO "public"."sys_menu" VALUES (1591987298297745410, '密码重置', 1589913214403518465, 5, NULL, NULL, 'F', '0', 'system:user:reset', '#', 'admin', '2022-11-14 10:52:04', 'admin', '2022-11-14 10:52:04', NULL);
INSERT INTO "public"."sys_menu" VALUES (1591988097069387777, '角色新增', 1590584601531154433, 1, NULL, NULL, 'F', '0', 'system:role:add', '#', 'admin', '2022-11-14 10:55:15', 'admin', '2022-11-14 10:55:15', NULL);
INSERT INTO "public"."sys_menu" VALUES (1591988181131628545, '角色更新', 1590584601531154433, 2, NULL, NULL, 'F', '0', 'system:role:update', '#', 'admin', '2022-11-14 10:55:35', 'admin', '2022-11-14 10:55:35', NULL);
INSERT INTO "public"."sys_menu" VALUES (1591988281702649858, '角色删除', 1590584601531154433, 3, NULL, NULL, 'F', '0', 'system:role:delete', '#', 'admin', '2022-11-14 10:55:59', 'admin', '2022-11-14 10:55:59', NULL);
INSERT INTO "public"."sys_menu" VALUES (1591988352515084290, '角色列表', 1590584601531154433, 4, NULL, NULL, 'F', '0', 'system:role:list', '#', 'admin', '2022-11-14 10:56:16', 'admin', '2022-11-14 10:56:16', NULL);
INSERT INTO "public"."sys_menu" VALUES (1591989054838706177, '菜单新增', 1590912260672901122, 1, NULL, NULL, 'F', '0', 'system:menu:add', '#', 'admin', '2022-11-14 10:59:03', 'admin', '2022-11-14 10:59:03', NULL);
INSERT INTO "public"."sys_menu" VALUES (1591989124468346882, '菜单更新', 1590912260672901122, 2, NULL, NULL, 'F', '0', 'system:menu:update', '#', 'admin', '2022-11-14 10:59:20', 'admin', '2022-11-14 10:59:20', NULL);
INSERT INTO "public"."sys_menu" VALUES (1591989182140026882, '菜单删除', 1590912260672901122, 3, NULL, NULL, 'F', '0', 'system:menu:delete', '#', 'admin', '2022-11-14 10:59:33', 'admin', '2022-11-14 10:59:33', NULL);
INSERT INTO "public"."sys_menu" VALUES (1591989238498889730, '菜单列表', 1590912260672901122, 4, NULL, NULL, 'F', '0', 'system:menu:list', '#', 'admin', '2022-11-14 10:59:47', 'admin', '2022-11-14 10:59:47', NULL);
INSERT INTO "public"."sys_menu" VALUES (1591992207009157121, '用户停用', 1589913214403518465, 6, NULL, NULL, 'F', '0', 'system:user:stop', '#', 'admin', '2022-11-14 11:11:35', 'admin', '2022-11-14 11:11:35', NULL);
INSERT INTO "public"."sys_menu" VALUES (1592001649704468481, '角色停用', 1590584601531154433, 5, NULL, NULL, 'F', '0', 'system:role:stop', '#', 'admin', '2022-11-14 11:49:06', 'admin', '2022-11-14 11:49:06', NULL);
INSERT INTO "public"."sys_menu" VALUES (1592001720810504194, '菜单停用', 1590912260672901122, 5, NULL, NULL, 'F', '0', 'system:menu:stop', '#', 'admin', '2022-11-14 11:49:23', 'admin', '2022-11-14 11:49:23', NULL);
INSERT INTO "public"."sys_menu" VALUES (1592011823668338689, '字典新增', 1592002158486126594, 1, NULL, NULL, 'F', '0', 'system:dict:add', '#', 'admin', '2022-11-14 12:29:32', 'admin', '2022-11-14 12:29:32', NULL);
INSERT INTO "public"."sys_menu" VALUES (1592011905293688833, '字典更新', 1592002158486126594, 2, NULL, NULL, 'F', '0', 'system:dict:update', '#', 'admin', '2022-11-14 12:29:51', 'admin', '2022-11-14 12:29:51', NULL);
INSERT INTO "public"."sys_menu" VALUES (1592011971324616705, '字典删除', 1592002158486126594, 3, NULL, NULL, 'F', '0', 'system:dict:delete', '#', 'admin', '2022-11-14 12:30:07', 'admin', '2022-11-14 12:30:07', NULL);
INSERT INTO "public"."sys_menu" VALUES (1592012054690603010, '字典列表', 1592002158486126594, 4, NULL, NULL, 'F', '0', 'system:dict:list', '#', 'admin', '2022-11-14 12:30:27', 'admin', '2022-11-14 12:30:27', NULL);
INSERT INTO "public"."sys_menu" VALUES (1592012110395154433, '字典停用', 1592002158486126594, 5, NULL, NULL, 'F', '0', 'system:dict:stop', '#', 'admin', '2022-11-14 12:30:40', 'admin', '2022-11-14 12:30:40', NULL);
INSERT INTO "public"."sys_menu" VALUES (1592172206064939009, '日志删除', 1594622381961789442, 1, NULL, NULL, 'F', '0', 'system:operateLog:delete', '#', 'admin', '2022-11-14 23:06:50', 'admin', '2022-11-21 17:26:26', NULL);
INSERT INTO "public"."sys_menu" VALUES (1592172298998132738, '日志清空', 1594622381961789442, 2, NULL, NULL, 'F', '0', 'system:operateLog:clear', '#', 'admin', '2022-11-14 23:07:12', 'admin', '2022-11-21 17:27:45', NULL);
INSERT INTO "public"."sys_menu" VALUES (1592173017809563649, '日志列表', 1594622381961789442, 3, NULL, NULL, 'F', '0', 'system:operateLog:list', '#', 'admin', '2022-11-14 23:10:03', 'admin', '2022-11-21 17:27:59', NULL);
INSERT INTO "public"."sys_menu" VALUES (1592312770470727682, '配置管理', 1589912152229580802, 6, 'config', 'system/config/index', 'C', '0', NULL, 'menu', 'admin', '2022-11-15 08:25:23', 'admin', '2022-12-20 17:08:51', NULL);
INSERT INTO "public"."sys_menu" VALUES (1592376134496485377, '配置新增', 1592312770470727682, 1, NULL, NULL, 'F', '0', 'system:config:add', '#', 'admin', '2022-11-15 12:37:10', 'admin', '2022-11-15 12:37:10', NULL);
INSERT INTO "public"."sys_menu" VALUES (1592376227006054401, '配置修改', 1592312770470727682, 2, NULL, NULL, 'F', '0', 'system:config:update', '#', 'admin', '2022-11-15 12:37:32', 'admin', '2022-11-15 12:37:32', NULL);
INSERT INTO "public"."sys_menu" VALUES (1592376307633160194, '配置删除', 1592312770470727682, 3, NULL, NULL, 'F', '0', 'system:config:delete', '#', 'admin', '2022-11-15 12:37:51', 'admin', '2022-11-15 12:37:51', NULL);
INSERT INTO "public"."sys_menu" VALUES (1592376372711981058, '配置列表', 1592312770470727682, 4, NULL, NULL, 'F', '0', 'system:config:list', '#', 'admin', '2022-11-15 12:38:07', 'admin', '2022-11-15 12:38:07', NULL);
INSERT INTO "public"."sys_menu" VALUES (1593441240542093313, '定时任务', 1589912152229580802, 7, 'job', 'system/job/index', 'C', '0', NULL, 'menu', 'admin', '2022-11-18 11:09:31', 'admin', '2022-12-20 17:08:55', NULL);
INSERT INTO "public"."sys_menu" VALUES (1594622381961789442, '操作日志', 1592168694979502082, 1, 'operateLog', 'system/log/operateLog/index', 'C', '0', NULL, 'menu', 'admin', '2022-11-21 17:22:57', 'admin', '2022-12-20 17:08:32', NULL);
INSERT INTO "public"."sys_menu" VALUES (1594622890433069058, '定时任务日志', 1592168694979502082, 2, 'jobLog', 'system/log/jobLog/index', 'C', '0', NULL, 'menu', 'admin', '2022-11-21 17:24:58', 'admin', '2022-12-20 17:08:39', NULL);
INSERT INTO "public"."sys_menu" VALUES (1594630245879152642, '定时任务日志列表', 1594622890433069058, 1, NULL, NULL, 'F', '0', 'system:jobLog:list', '#', 'admin', '2022-11-21 17:54:12', 'admin', '2022-11-21 17:54:12', NULL);
INSERT INTO "public"."sys_menu" VALUES (1594632378712141826, '定时任务日志删除', 1594622890433069058, 2, NULL, NULL, 'F', '0', 'system:jobLog:delete', '#', 'admin', '2022-11-21 18:02:41', 'admin', '2022-11-21 18:02:51', NULL);
INSERT INTO "public"."sys_menu" VALUES (1594632534635393025, '定时任务日志清空', 1594622890433069058, 3, NULL, NULL, 'F', '0', 'system:jobLog:clear', '#', 'admin', '2022-11-21 18:03:18', 'admin', '2022-11-21 18:03:18', NULL);
INSERT INTO "public"."sys_menu" VALUES (1594883019434954754, '定时任务列表', 1593441240542093313, 1, NULL, NULL, 'F', '0', 'system:job:list', '#', 'admin', '2022-11-22 10:38:38', 'admin', '2022-11-22 10:38:38', NULL);
INSERT INTO "public"."sys_menu" VALUES (1594883117451644930, '定时任务新增', 1593441240542093313, 2, NULL, NULL, 'F', '0', 'system:job:add', '#', 'admin', '2022-11-22 10:39:01', 'admin', '2022-11-22 10:39:01', NULL);
INSERT INTO "public"."sys_menu" VALUES (1594883194450677762, '定时任务修改', 1593441240542093313, 3, NULL, NULL, 'F', '0', 'system:job:update', '#', 'admin', '2022-11-22 10:39:20', 'admin', '2022-11-22 10:39:20', NULL);
INSERT INTO "public"."sys_menu" VALUES (1594883252789252098, '定时任务删除', 1593441240542093313, 4, NULL, NULL, 'F', '0', 'system:job:delete', '#', 'admin', '2022-11-22 10:39:34', 'admin', '2022-11-22 10:39:34', NULL);
INSERT INTO "public"."sys_menu" VALUES (1594883323534577665, '定时任务执行', 1593441240542093313, 5, NULL, NULL, 'F', '0', 'system:job:run', '#', 'admin', '2022-11-22 10:39:50', 'admin', '2022-11-22 10:39:50', NULL);
INSERT INTO "public"."sys_menu" VALUES (1594883861240156162, '定时任务停用', 1593441240542093313, 6, NULL, NULL, 'F', '0', 'system:job:stop', '#', 'admin', '2022-11-22 10:41:59', 'admin', '2022-11-22 10:41:59', NULL);
INSERT INTO "public"."sys_menu" VALUES (1608260294360436737, '部门列表', 1605408060887085058, 1, NULL, NULL, 'F', '0', 'system:dept:list', '#', 'zhong', '2022-12-29 08:35:08.89', 'zhong', '2022-12-29 08:35:08.89', NULL);
INSERT INTO "public"."sys_menu" VALUES (1608260379127320577, '部门新增', 1605408060887085058, 2, NULL, NULL, 'F', '0', 'system:dept:add', '#', 'zhong', '2022-12-29 08:35:29.083', 'zhong', '2022-12-29 08:35:29.083', NULL);
INSERT INTO "public"."sys_menu" VALUES (1608260470152105986, '部门修改', 1605408060887085058, 3, NULL, NULL, 'F', '0', 'system:dept:update', '#', 'zhong', '2022-12-29 08:35:50.784', 'zhong', '2022-12-29 08:35:50.784', NULL);
INSERT INTO "public"."sys_menu" VALUES (1608260552893140993, '部门删除', 1605408060887085058, 4, NULL, NULL, 'F', '0', 'system:dept:delete', '#', 'zhong', '2022-12-29 08:36:10.503', 'zhong', '2022-12-29 08:36:10.503', NULL);
INSERT INTO "public"."sys_menu" VALUES (1613424794093084673, '客户端列表', 1612657612996280321, 0, NULL, NULL, 'F', '0', 'system:client:list', '#', 'zhong', '2023-01-12 14:37:01.588', 'zhong', '2023-01-12 14:37:01.588', NULL);
INSERT INTO "public"."sys_menu" VALUES (1613424867673759746, '客户端新增', 1612657612996280321, 0, NULL, NULL, 'F', '0', 'system:client:add', '#', 'zhong', '2023-01-12 14:37:19.107', 'zhong', '2023-01-12 14:37:19.107', NULL);
INSERT INTO "public"."sys_menu" VALUES (1613424934912647170, '客户端更新', 1612657612996280321, 0, NULL, NULL, 'F', '0', 'system:client:update', '#', 'zhong', '2023-01-12 14:37:35.134', 'zhong', '2023-01-12 14:37:35.134', NULL);
INSERT INTO "public"."sys_menu" VALUES (1613424988348080129, '客户端删除', 1612657612996280321, 0, NULL, NULL, 'F', '0', 'system:client:delete', '#', 'zhong', '2023-01-12 14:37:47.867', 'zhong', '2023-01-12 14:37:47.867', NULL);
INSERT INTO "public"."sys_menu" VALUES (1592002158486126594, '字典管理', 1589912152229580802, 4, 'dict', 'system/dict/index', 'C', '0', NULL, 'dict', 'admin', '2022-11-14 11:51:07', 'zhong', '2023-07-04 12:03:31.054', NULL);
INSERT INTO "public"."sys_menu" VALUES (1589913214403518465, '用户管理', 1589912152229580802, 1, 'user', 'system/user/index', 'C', '0', '', 'user', 'admin', '2021-09-05 09:08:27', 'zhong', '2023-07-04 12:02:35.316', NULL);
INSERT INTO "public"."sys_menu" VALUES (1605408060887085058, '部门管理', 1589912152229580802, 5, 'dept', 'system/dept/index', 'C', '0', NULL, 'peoples', 'admin', '2022-12-21 11:41:23', 'zhong', '2023-07-04 12:04:07.778', NULL);
INSERT INTO "public"."sys_menu" VALUES (1592168694979502082, '日志管理', 1589912152229580802, 8, 'log', '', 'M', '0', NULL, 'menu', 'admin', '2022-11-14 22:52:53', 'admin', '2022-12-21 11:42:04', NULL);
INSERT INTO "public"."sys_menu" VALUES (1676412443413733377, '权限配置', 1590584601531154433, 0, '', '', 'F', '0', 'system:role:auth', '', 'zhong', '2023-07-05 10:07:27.414', 'zhong', '2023-07-05 10:07:27.414', NULL);
INSERT INTO "public"."sys_menu" VALUES (1685849121375248386, '系统文档', 0, 0, '/doc', '', 'M', '0', '', 'component', 'zhong', '2023-07-31 11:05:26.773', 'zhong', '2023-07-31 11:05:26.773', NULL);
INSERT INTO "public"."sys_menu" VALUES (1612657612996280321, '客户端管理', 1589912152229580802, 0, 'client', 'system/client/index', 'C', '0', NULL, 'menu', 'zhong', '2023-01-10 11:48:31.336', 'zhong', '2023-01-10 11:48:31.336', NULL);
INSERT INTO "public"."sys_menu" VALUES (1589912152229580802, '系统管理', 0, 1, '/system', '', 'M', '0', '', 'system', 'admin', '2021-09-05 08:47:00', 'zhong', '2023-07-28 10:34:28.983', '/system/user');
INSERT INTO "public"."sys_menu" VALUES (1590584601531154433, '角色管理', 1589912152229580802, 2, 'role', 'system/role/index', 'C', '0', NULL, 'role', NULL, NULL, 'zhong', '2023-07-28 10:53:25.891', NULL);
INSERT INTO "public"."sys_menu" VALUES (1685849578856374274, 'admin文档', 1685849121375248386, 0, 'http://localhost:10004/doc.html#/home', NULL, 'L', '0', '', 'component', 'zhong', '2023-07-31 11:07:15.843', 'zhong', '2023-07-31 11:07:15.843', NULL);
INSERT INTO "public"."sys_menu" VALUES (1685849697643257857, 'job文档', 1685849121375248386, 0, 'http://localhost:10003/doc.html#/home', NULL, 'L', '0', '', 'component', 'zhong', '2023-07-31 11:07:44.154', 'zhong', '2023-07-31 11:07:44.154', NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role";
CREATE TABLE "public"."sys_role" (
  "role_id" int8 NOT NULL,
  "role_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "role_key" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "status" varchar(8) COLLATE "pg_catalog"."default" NOT NULL,
  "create_by" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_by" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "data_scope" int2
)
;
COMMENT ON COLUMN "public"."sys_role"."role_id" IS '主键id';
COMMENT ON COLUMN "public"."sys_role"."role_name" IS '角色名称';
COMMENT ON COLUMN "public"."sys_role"."role_key" IS '角色key';
COMMENT ON COLUMN "public"."sys_role"."status" IS '角色状态';
COMMENT ON COLUMN "public"."sys_role"."data_scope" IS '数据权限';
COMMENT ON TABLE "public"."sys_role" IS '系统角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO "public"."sys_role" VALUES (1676408833992843265, '测试角色', 'test', '0', 'zhong', '2023-07-05 09:53:06.874', 'zhong', '2023-07-07 15:01:41.883', 1);
INSERT INTO "public"."sys_role" VALUES (1589911702990249985, '超级管理员', 'admin', '0', NULL, NULL, 'zhong', '2023-08-24 14:12:14.883', 0);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role_menu";
CREATE TABLE "public"."sys_role_menu" (
  "role_id" int8 NOT NULL,
  "menu_id" int8 NOT NULL
)
;
COMMENT ON COLUMN "public"."sys_role_menu"."role_id" IS '角色id';
COMMENT ON COLUMN "public"."sys_role_menu"."menu_id" IS '菜单id';
COMMENT ON TABLE "public"."sys_role_menu" IS '角色菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1589912152229580802);
INSERT INTO "public"."sys_role_menu" VALUES (1676408833992843265, 1589912152229580802);
INSERT INTO "public"."sys_role_menu" VALUES (1676408833992843265, 1605408060887085058);
INSERT INTO "public"."sys_role_menu" VALUES (1676408833992843265, 1608260552893140993);
INSERT INTO "public"."sys_role_menu" VALUES (1676408833992843265, 1608260470152105986);
INSERT INTO "public"."sys_role_menu" VALUES (1676408833992843265, 1608260379127320577);
INSERT INTO "public"."sys_role_menu" VALUES (1676408833992843265, 1608260294360436737);
INSERT INTO "public"."sys_role_menu" VALUES (1676408833992843265, 1589913214403518465);
INSERT INTO "public"."sys_role_menu" VALUES (1676408833992843265, 1591992207009157121);
INSERT INTO "public"."sys_role_menu" VALUES (1676408833992843265, 1591987298297745410);
INSERT INTO "public"."sys_role_menu" VALUES (1676408833992843265, 1591979441078702082);
INSERT INTO "public"."sys_role_menu" VALUES (1676408833992843265, 1591976390704009218);
INSERT INTO "public"."sys_role_menu" VALUES (1676408833992843265, 1591976305236676610);
INSERT INTO "public"."sys_role_menu" VALUES (1676408833992843265, 1591976130573275138);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1592168694979502082);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1594622890433069058);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1594632534635393025);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1594632378712141826);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1594630245879152642);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1594622381961789442);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1592173017809563649);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1592172298998132738);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1592172206064939009);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1593441240542093313);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1594883861240156162);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1594883323534577665);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1594883252789252098);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1594883194450677762);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1594883117451644930);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1594883019434954754);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1592312770470727682);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1592376372711981058);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1592376307633160194);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1592376227006054401);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1592376134496485377);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1605408060887085058);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1608260552893140993);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1608260470152105986);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1608260379127320577);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1608260294360436737);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1592002158486126594);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1592012110395154433);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1592012054690603010);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1592011971324616705);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1592011905293688833);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1592011823668338689);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1590912260672901122);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1592001720810504194);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1591989238498889730);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1591989182140026882);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1591989124468346882);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1591989054838706177);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1590584601531154433);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1592001649704468481);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1591988352515084290);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1591988281702649858);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1591988181131628545);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1591988097069387777);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1676412443413733377);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1589913214403518465);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1591992207009157121);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1591987298297745410);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1591979441078702082);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1591976390704009218);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1591976305236676610);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1591976130573275138);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1612657612996280321);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1613424794093084673);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1613424867673759746);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1613424934912647170);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1613424988348080129);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1685849121375248386);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1685849578856374274);
INSERT INTO "public"."sys_role_menu" VALUES (1589911702990249985, 1685849697643257857);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user";
CREATE TABLE "public"."sys_user" (
  "user_id" int8 NOT NULL,
  "user_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "user_real_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "user_email" varchar(128) COLLATE "pg_catalog"."default",
  "user_avatar" varchar(128) COLLATE "pg_catalog"."default",
  "status" varchar(8) COLLATE "pg_catalog"."default" NOT NULL,
  "user_sex" varchar(8) COLLATE "pg_catalog"."default" NOT NULL,
  "user_password" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "create_by" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_by" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "dept_id" int8,
  "phone" varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_user"."user_id" IS '主键id';
COMMENT ON COLUMN "public"."sys_user"."user_name" IS '用户账号';
COMMENT ON COLUMN "public"."sys_user"."user_real_name" IS '用户姓名';
COMMENT ON COLUMN "public"."sys_user"."user_email" IS '用户邮箱';
COMMENT ON COLUMN "public"."sys_user"."user_avatar" IS '用户头像';
COMMENT ON COLUMN "public"."sys_user"."status" IS '用户状态';
COMMENT ON COLUMN "public"."sys_user"."user_sex" IS '用户性别';
COMMENT ON COLUMN "public"."sys_user"."user_password" IS '用户密码';
COMMENT ON COLUMN "public"."sys_user"."dept_id" IS '部门id';
COMMENT ON COLUMN "public"."sys_user"."phone" IS '电话';
COMMENT ON TABLE "public"."sys_user" IS '系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO "public"."sys_user" VALUES (1676409003203649538, 'test', '测试用户', '55b8a30a374c32558e0aeec6ecc25478cb35cfff922646e409a2d66c439dedd7', NULL, '0', '0', '753afef49e2471c77f38b813aa6be958', 'zhong', '2023-07-05 09:53:47.215', 'zhong', '2023-08-24 13:33:22.024', 1605373585801097218, '9f64206b2b59a1d58a2f812e903e3bee');
INSERT INTO "public"."sys_user" VALUES (1694592183618265090, 'aqi', 'aqi', '24779fdece90b7d76c683d94204f6916', NULL, '0', '0', '753afef49e2471c77f38b813aa6be958', 'zhong', '2023-08-24 14:07:15.264', 'zhong', '2023-08-24 14:07:15.264', 1605373585801097218, '7d8a7aea870d6e67fd98d72ad376d23e');
INSERT INTO "public"."sys_user" VALUES (1590581006056333313, 'zhong', '钟', '71c85bd294794a11899f5da78e33dc47', NULL, '0', '0', '343c18b975a1b4524f8dd8711936984e', 'admin', '2022-11-10 05:43:58', 'zhong', '2023-08-24 14:08:04.016', 1605376298555330561, '9949ba9f335a484d9ef016bc2cfcfd6b');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user_role";
CREATE TABLE "public"."sys_user_role" (
  "user_id" int8 NOT NULL,
  "role_id" int8 NOT NULL
)
;
COMMENT ON COLUMN "public"."sys_user_role"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."sys_user_role"."role_id" IS '角色id';
COMMENT ON TABLE "public"."sys_user_role" IS '用户角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO "public"."sys_user_role" VALUES (1676409003203649538, 1676408833992843265);
INSERT INTO "public"."sys_user_role" VALUES (1590581006056333313, 1589911702990249985);
INSERT INTO "public"."sys_user_role" VALUES (1694592183618265090, 1676408833992843265);

-- ----------------------------
-- Primary Key structure for table sys_client
-- ----------------------------
ALTER TABLE "public"."sys_client" ADD CONSTRAINT "sys_client_pkey" PRIMARY KEY ("client_id");

-- ----------------------------
-- Primary Key structure for table sys_config
-- ----------------------------
ALTER TABLE "public"."sys_config" ADD CONSTRAINT "sys_config_pkey" PRIMARY KEY ("config_id");

-- ----------------------------
-- Primary Key structure for table sys_dept
-- ----------------------------
ALTER TABLE "public"."sys_dept" ADD CONSTRAINT "sys_dept_pkey" PRIMARY KEY ("dept_id");

-- ----------------------------
-- Primary Key structure for table sys_dict
-- ----------------------------
ALTER TABLE "public"."sys_dict" ADD CONSTRAINT "sys_dict_pkey" PRIMARY KEY ("dict_code");

-- ----------------------------
-- Primary Key structure for table sys_job
-- ----------------------------
ALTER TABLE "public"."sys_job" ADD CONSTRAINT "sys_job_pkey" PRIMARY KEY ("job_id");

-- ----------------------------
-- Primary Key structure for table sys_job_log
-- ----------------------------
ALTER TABLE "public"."sys_job_log" ADD CONSTRAINT "sys_job_log_pkey" PRIMARY KEY ("job_log_id");

-- ----------------------------
-- Primary Key structure for table sys_log
-- ----------------------------
ALTER TABLE "public"."sys_log" ADD CONSTRAINT "sys_log_pkey" PRIMARY KEY ("log_id");

-- ----------------------------
-- Primary Key structure for table sys_menu
-- ----------------------------
ALTER TABLE "public"."sys_menu" ADD CONSTRAINT "sys_menu_pkey" PRIMARY KEY ("menu_id");

-- ----------------------------
-- Primary Key structure for table sys_role
-- ----------------------------
ALTER TABLE "public"."sys_role" ADD CONSTRAINT "sys_role_pkey" PRIMARY KEY ("role_id");

-- ----------------------------
-- Primary Key structure for table sys_role_menu
-- ----------------------------
ALTER TABLE "public"."sys_role_menu" ADD CONSTRAINT "sys_role_menu_pkey" PRIMARY KEY ("role_id", "menu_id");

-- ----------------------------
-- Primary Key structure for table sys_user
-- ----------------------------
ALTER TABLE "public"."sys_user" ADD CONSTRAINT "sys_user_pkey" PRIMARY KEY ("user_id");

-- ----------------------------
-- Primary Key structure for table sys_user_role
-- ----------------------------
ALTER TABLE "public"."sys_user_role" ADD CONSTRAINT "sys_user_role_pkey" PRIMARY KEY ("user_id", "role_id");
