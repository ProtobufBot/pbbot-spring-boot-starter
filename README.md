# Pbbot-Spring-Boot-Starter

这是一个spring boot starter，可以用于快速开发对应于 [Spring-Mirai-Client](https://github.com/ProtobufBot/Spring-Mirai-Client) 的消息处理中心

## 使用方法
- pom.xml
```xml
    <dependency>
        <groupId>net.lz1998</groupId>
        <artifactId>pbbot-spring-boot-starter</artifactId>
        <version>0.0.5</version>
    </dependency>
```

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