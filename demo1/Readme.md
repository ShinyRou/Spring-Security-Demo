## 第一部分

### 1.创建简单的Spring Security项目

### 2.默认表单认证/自定义表单认证

```
 @Override
    protected void configure(HttpSecurity http) throws Exception {
        ((HttpSecurity)((HttpSecurity)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.
                authorizeRequests().
                antMatchers("/js/**","/css/**").permitAll(). //不拦截静态文件
                anyRequest()). //所有请求  如果是外置的css 与 js 需要修改 否则静态资源也会被拦截
                authenticated().and()).
                formLogin().loginPage("/login.html").permitAll().//自定义登陆页面  登录页不设限访问
                //successHandler、failureHandler 指定登陆成功、失败的逻辑
                and()).
                csrf().disable();//关闭 跨站请求伪造防护功能
    }
```

formLogin.loginPage("/login.html") 指定了登陆的表单页面，同时生成"/login.html"的post路由表单来接收登陆参数
所以login.html文件中的form的属性action="login.html" method="post"

loginProcessingUrl 指定登录请求路径例如指定为 /login 则form表单中 action = "login"

### 3.认证与授权
   - 3.0 基于apllication.properties
   ```
    spring.security.user.password=zhujun
    spring.security.user.name=zhujun
   ```
   - 3.1 基于内存
   
    @Bean
        public UserDetailsService userDetailsService(){
            InMemoryUserDetailsManager im = new InMemoryUserDetailsManager();
            im.createUser(User.withUsername("user2").password("user2").roles("USER").build());
            im.createUser(User.withUsername("admin2").password("admin2").roles("ADMIN").build());
            return im;
        }

   - 3.2 基于默认数据库类型
   
   默认的建表语句存在于
   /org/springframework/security/core/userdetails/jdbc/users.ddl中
   
   ```
@Bean
    public UserDetailsService userDetailsService(){
      JdbcUserDetailsManager im = new JdbcUserDetailsManager();
      im.setDataSource(dataSource);
        im.createUser(User.withUsername("user2").password("user2").roles("USER").build());
        im.createUser(User.withUsername("admin2").password("admin2").roles("ADMIN").build());
        return im;
    }
   ```
   - 3.3 基于自定义数据库模型
   通过继承UserDetailService接口实现 loadUserByUsername方法，获取UserDetail对象
   注意 User类继承 UserDetails时几个角色控制相关属性可以默认返回true，否则会认为账户异常。