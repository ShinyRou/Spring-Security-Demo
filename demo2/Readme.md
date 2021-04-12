####第二部分  通过验证码案例了解认证相关的接口级使用
####一、使用过滤器
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

####二、使用自定义认证
#####1.主体的定义
系统中的用户称为主体，包含通过认证具有系统访问权限的用户、设备、其他系统
Authentication 继承于Java Security 中的Principal
```
public interface Authentication extends Principal, Serializable {
    //权限列表
    Collection<? extends GrantedAuthority> getAuthorities();
    //用户密码
    Object getCredentials();
    //详细信息
    Object getDetails();
    //用户名
    Object getPrincipal();
    //主体是否验证成功
    boolean isAuthenticated();

    void setAuthenticated(boolean var1) throws IllegalArgumentException;
}
```

#####2.AuthenticationProvider 与 ProviderManager
- AuthenticationProvider 被 Spring Security定义为 一个验证过程
- Authentication 作为AuthenticationProvider 验证方法的入参
- Providermanager 用来管理AuthenticationProvider 
- UsernamePasswordAuthenticationFilter 过滤器调用Providermanager 进行验证过程
    ```
        return this.getAuthenticationManager().authenticate(authRequest);
    ```


#####3.ProviderManager源码
```
public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Class<? extends Authentication> toTest = authentication.getClass();
        AuthenticationException lastException = null;
        AuthenticationException parentException = null;
        Authentication result = null;
        Authentication parentResult = null;
        boolean debug = logger.isDebugEnabled();
        Iterator var8 = this.getProviders().iterator();
        //迭代验证每一个AuthenticationProvider 直到一个验证通过即可跳出
        while(var8.hasNext()) {
            AuthenticationProvider provider = (AuthenticationProvider)var8.next();
            if (provider.supports(toTest)) {
                if (debug) {
                    logger.debug("Authentication attempt using " + provider.getClass().getName());
                }

                try {
                    result = provider.authenticate(authentication);
                    if (result != null) {
                        this.copyDetails(authentication, result);
                        break;
                    }
                } catch (InternalAuthenticationServiceException | AccountStatusException var13) {
                    this.prepareException(var13, authentication);
                    throw var13;
                } catch (AuthenticationException var14) {
                    lastException = var14;
                }
            }
        }
        .......

    }
```
#####4.SpringSecurity支持的认证方式与 用户名密码验证技术
- http层面的认证技术
- LDAP 轻量级目录访问技术
- OpenID 聚焦于证明用户身份
- Oauth 聚焦于授权
- 系统内维护的用户名密码（使用最广泛）

以用户名密码认证技术为例Spring Security提供了 **AbstractUserDetailsAuthenticationProvider**接口
```aidl
  //检索用户
 protected abstract UserDetails retrieveUser(String var1, UsernamePasswordAuthenticationToken var2) throws AuthenticationException;
 //附加认证过程
 protected abstract void additionalAuthenticationChecks(UserDetails var1, UsernamePasswordAuthenticationToken var2) throws AuthenticationException;
```
通过继承AbstractUserDetailsAuthenticationProvider接口 实现以上两个方法就可自定义核心认证过程

**DaoAuthenticationProvider**是框架提供的实现
检索用户方法retrieveUser 的用户信息来源于 UserDetailsService，即在demo1中的实现的loadUserByUserName
方法

从上述分析过程中要实现自定义验证
- 1.**继承DaoAuthenticationProvider 重写附加验证方法 additionalAuthenticationChecks
添加验证码的处理**
- 2.**验证码信息存储在session中，需要获取到HttpServletRequest，要从UsernamePasswordAuthenticationFilter
过滤器中构造Authentication过程中入手**
UsernamePasswordAuthenticationFilter 中 
authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
实际是通过 WebAuthenticationDetailsSource 生成  WebAuthenticationDetails 
只需要对 WebAuthenticationDetails  WebAuthenticationDetailsSource进行继承添加验证码属性
- 3.**WebSecurityConfig 中指定自定义的  AuthenticationDetailsSource 、AuthenticationProvider**



