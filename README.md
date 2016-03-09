REST模拟器
----

# 使用方法
使用IDE打开工程,在/src/main/resources/data目录下，  
根据URL和Method增加对应的JSON文件。

* 规则如下:
    * 使用url-method.json方式命名文件,如/users,使用GET方法，对应的文件名为users-get.json;
    * 如果URL多级,使用下划线(_)来替代URL中的斜线(/),首个不替换,如/users/1,使用GET方法，对应的文件名则为users_1-get.json;

* 访问方式：
    * 默认的端口为9000，直接使用http://ip:port/后面加要访问的具体URL即可，对应上面的两个文件，URL为http://ip:port/users和http://ip:port/users/1
    
# TODO
* 提供Jar包方式运行；
* URL中使用占位符，解决ID可变；
* 可统一配置方式，不需要直接操作源代码；

# 版本说明
* 0.1: 基础功能
* 0.2: 修正打成Jar包后无法读取Json文件的Bug
* 0.3: 
    * 增加处理请求参数,将参数名与值合并进对应的JSON文件的文件名中
    * 增加请求内容与响应内容的日志打印（使用System.out）

----
着急使用,先上传一个简陋版.  
需要自己打开源码,添加欲模拟的URL对应的JSON文件.  
后续会改进...
