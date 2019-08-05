package com.dahai.wanwu.dal.config.slave;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@MapperScan(basePackages=SlaveOpsDataSourceConfig.BASEPACKAGES,sqlSessionFactoryRef="slaveSqlSessionFactory")
public class SlaveOpsDataSourceConfig {
	protected static final String BASEPACKAGES = "com.dahai.wanwu.dal.dao.slave";
	protected static final String MAPPER_LOCATION = "classpath:com/dahai/wanwu/dal/dao/slave/*.xml";

	@Value("${spring.datasource.ops.master.url}")
	private String url;
	@Value("${spring.datasource.ops.master.username}")
	private String userName;
	@Value("${spring.datasource.ops.master.password}")
	private String password;
	@Value("${spring.datasource.ops.master.driver-class-name}")
	private String driverClass;
	
	@Bean(name="slaveDataSource")
	public DataSource slaveDataSource(){
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		dataSource.setDriverClassName(driverClass);
		return dataSource;
	}
	
	@Bean(name="slaveTransactionManager")
	@Primary
	public DataSourceTransactionManager slaveTransactionManager(){
		return new DataSourceTransactionManager(slaveDataSource());
	}
	
	@Bean(name="slaveSqlSessionFactory")
	@Primary
	public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("slaveDataSource")DataSource slaveDataSource) throws Exception{
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(slaveDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
        	.getResources(SlaveOpsDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
	}
}
