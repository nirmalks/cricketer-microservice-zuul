package com.example.users.security

import com.example.users.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
open class WebSecurityConfig(
    @Autowired
    var usersService: UserService,
    val customEnvironment: Environment
): WebSecurityConfigurerAdapter() {
    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity.csrf().disable()
        httpSecurity.authorizeRequests().antMatchers("api/users/**").permitAll()
            .and()
            .addFilter(getAuthenticationFilter())
    }

    @Bean
    open fun bcryptEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    private fun getAuthenticationFilter(): AuthenticationFilter {
        val authFilter = AuthenticationFilter(usersService, customEnvironment, authenticationManager())
        authFilter.setAuthenticationManager(authenticationManager())
        return authFilter
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(usersService).passwordEncoder(bcryptEncoder())
    }
}