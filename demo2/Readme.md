####第二部分
#####一、使用过滤器
- 1.实现图片验证码 
```
        <!--图片验证码-->
        <dependency>
            <groupId>com.github.penggle</groupId>
            <artifactId>kaptcha</artifactId>
            <version>2.3.2</version>
        </dependency>
```
```
    @Bean
        public Producer captcha(){
            Properties properties = new Properties();
            //图片宽度
            properties.setProperty("kapcchar.image.width","150");
            //图片高度
            properties.setProperty("kapcchar.image.height","50");
            //字符集
            properties.setProperty("kapcchar.textproducer.char.string","0123456789");
            //验证长度
            properties.setProperty("kapcchar.textproducer.char.length","4");
            Config config = new Config(properties);
            DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
            defaultKaptcha.setConfig(config);
            return  defaultKaptcha;
        }
```
- 2.实现验证码校验过滤器
```
      VerificationCodeFilter extends OncePerRequestFilter 
```
- 3.注册过滤器
```
       http.addFilterBefore(new VerificationCodeFilter(),UsernamePasswordAuthenticationFilter.class);
```

#####二、使用自定义认证