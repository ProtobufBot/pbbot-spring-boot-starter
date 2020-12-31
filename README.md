# Pbbot-Spring-Boot-Starter

[![QQ群](https://img.shields.io/static/v1?label=QQ%E7%BE%A4&message=335783090&color=blue)](https://jq.qq.com/?_wv=1027&k=B7Of3GMZ)

这是一个spring boot starter，可以用于快速开发对应于 [Go-Mirai-Client](https://github.com/protobufbot/go-Mirai-Client) 或 [Spring-Mirai-Client](https://github.com/ProtobufBot/Spring-Mirai-Client) 的消息处理中心。

仅用于编写业务逻辑，不涉及登陆等功能，建议配合[Go-Mirai-Client](https://github.com/protobufbot/Go-Mirai-Client)使用，下载地址：[Go-Mirai-Client-Release](https://github.com/ProtobufBot/Go-Mirai-Client/releases)

文档：https://blog.lz1998.net/blogs/bot/2020/pbbot-doc/

例子：https://github.com/ProtobufBot/Spring-Mirai-Server

## 使用方法
- pom.xml
```xml
    <dependency>
        <groupId>net.lz1998</groupId>
        <artifactId>pbbot-spring-boot-starter</artifactId>
        <version>0.0.19</version>
    </dependency>
```
最新版：[![maven](https://img.shields.io/maven-central/v/net.lz1998/pbbot-spring-boot-starter)](https://search.maven.org/artifact/net.lz1998/pbbot-spring-boot-starter)

- HelloPlugin
```java
package com.example.demo.plugin;

import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;


@Component
public class HelloPlugin extends BotPlugin {
    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull OnebotEvent.PrivateMessageEvent event) {
        Msg msg = Msg.builder()
                .text("123")
                .face(1);
        bot.sendPrivateMsg(event.getUserId(), msg.build(), false);
        return super.onPrivateMessage(bot, event);
    }
}
```

- application.yml
```yaml
spring:
  bot:
    plugin-list: 
      - com.example.demo.plugin.HelloPlugin
server:
  port: 8081
```
