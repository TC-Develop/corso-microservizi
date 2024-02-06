package it.tcgroup.oldwar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.List;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("it.tcgroup.oldwar")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper());
        converters.add(converter);
    }

    @Bean
    protected ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        // objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat(StdDateFormat.DATE_FORMAT_STR_ISO8601));

        return objectMapper;
    }


    @Bean
    protected DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDefaultAutoCommit(false);
        dataSource.setAutoCommitOnReturn(false);
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/testDb");
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setMaxTotal(100);
        dataSource.setMaxIdle(10);
        dataSource.setInitialSize(8);
        return dataSource;
    }

    @Bean
    protected DataSourceTransactionManager transactionManager(@Autowired DataSource dataSource){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    @Bean
    protected NamedParameterJdbcTemplate jdbcTemplate(@Autowired DataSource dataSource){
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        return jdbcTemplate;
    }
}
