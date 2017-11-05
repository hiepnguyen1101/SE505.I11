package J2EE.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@ComponentScan("J2EE")
@EnableWebSecurity
public class MvcSercurityCfg extends WebSecurityConfigurerAdapter
{
    private final WebAuth webAuth;
    @Autowired
    public MvcSercurityCfg(WebAuth webAuth)
    {
        this.webAuth = webAuth;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(webAuth).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception
    {
        webSecurity.ignoring().antMatchers("/account/changePass");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/").access("hasAnyRole('Admin', 'Employee')");
        http.authorizeRequests().antMatchers("/accounts/**").access("hasRole('Admin')");
        http.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/j_spring_security_check")
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .usernameParameter("user")
                .passwordParameter("pass")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login")
                .and().exceptionHandling().accessDeniedPage("/403");
    }
}
