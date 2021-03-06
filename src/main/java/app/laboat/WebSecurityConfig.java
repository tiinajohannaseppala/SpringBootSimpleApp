package app.laboat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import app.laboat.service.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailServiceImpl userDetailsService;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests().antMatchers("/webjars/**").permitAll() // CSS:lle pitää antaa lupa toimia koko ajan
        .and()
        .authorizeRequests().antMatchers("/signup", "/saveuser").permitAll() //kaikilla mahdollisuus luoda uudet tunnukset
        .and()
        .authorizeRequests().anyRequest().authenticated()
        .and()
      .formLogin()
          .loginPage("/login")
          .defaultSuccessUrl("/boatlist")
          .permitAll()
          .and()
      .logout()
          .permitAll();
          //.invalidateHttpSession(true); // Invalidate session
    }
    
  
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
  	auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
  }
}	


