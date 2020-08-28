drop table if exists t_merchants_account;
create table t_merchants_account(
	id char(19) primary key comment '主键id',
	nick_name varchar(20) comment '昵称',
	telephone varchar(15) not null default '' comment '商户手机号码(商户登录帐号)',
	password varchar(32) not null default '' comment '密码',
	delete_flag tinyint(1) not null default 0 comment '是否删除标识(0:未删除 -1:已删除)',
	create_time timestamp comment '创建时间',
	update_time timestamp comment '更新时间',
	create_user_id char(19) comment '创建人id',
	update_user_id char(19) comment '更新人id'
) comment '商铺用户表';