package J2EE.Config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;


@Configuration
@ComponentScan("J2EE")
@EnableWebMvc
@EnableTransactionManagement
@PropertySource("classpath:DataSource/datasource-cfg.properties")
public class MvcConfig extends WebMvcConfigurerAdapter
{
    //region Attribute
    private static final Charset UTF8 = Charset.forName("UTF-8");

    private final Environment env;

    @Autowired
    public MvcConfig(Environment env)
    {
        this.env = env;
    }
    //endregion

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/resources/css/**").addResourceLocations("resources/css/");
        registry.addResourceHandler("/resources/js/**").addResourceLocations("resources/js/");
        registry.addResourceHandler("/resources/fonts/**").addResourceLocations("resources/fonts/");
        registry.addResourceHandler("/resources/images/**").addResourceLocations("resources/images/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
    {
        configurer.enable();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setSupportedMediaTypes(Collections.singletonList(new MediaType("text", "plain", UTF8)));
        converters.add(stringConverter);
        converters.add(new MappingJackson2HttpMessageConverter());
    }

    @Bean
    public InternalResourceViewResolver getViewResolver()
    {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean(name = "dataSource")
    public DataSource getDataSource()
    {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("ds.database-driver"));
        dataSource.setUrl(env.getProperty("ds.url"));
        dataSource.setUsername(env.getProperty("ds.username"));
        dataSource.setPassword(env.getProperty("ds.password"));
        System.out.println("## getDataSource: " + dataSource);
        return dataSource;
    }

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter()
    {
        return new StringHttpMessageConverter(UTF8);
    }

    @Autowired
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager getTransactionManager(DataSource dataSource)
    {
        return new DataSourceTransactionManager(dataSource);
    }


}
