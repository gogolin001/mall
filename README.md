# mall
mall 多租户商城

不兼容类库情况：  
druid:不支持监控  
mybatis-plus：开发版本非正式版  

接下来工作：  
短信接口（腾讯云、阿里云）  
oss存储（minio、阿里云、腾讯云、七牛）  
微信集成（消息推送，接受默认消息）  
支付（微信小程序、H5、支付宝）  

加密要求：  
1、密码存储使用SM3摘要算法加密。  
2、前端登录使用服务器公钥SM2进行加密传送密码。  
3、Jwt使用SM3加密摘要，根据不同访问ip地址获取用户归属地，如果用户相隔一个小时都没有再次访问则校验归属地，归属地变化则需要重新登录，每5分钟更新一次最后访问时间。  
4、数据库配置使用mybatis-plus加密，其他参数使用国密SM4加密。  
5、第三方系统调用接口使用服务器公钥SM2加密。

