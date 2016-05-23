package com.sd.mobileapi.service.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;



import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.sd.mobileapi.util.StringUtil;



public class BaseServiceImp {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	/**
	 * 批量更新
	 * @param sql
	 * @param batchArgs
	 */
	public void batchUpdate(String sql, List<Object[]> batchArgs){
		if(batchArgs.size() == 0){
			LogFactory.getLog(this.getClass()).info("batchUpdate sql:"+sql+",size:0");
		}else{
			LogFactory.getLog(this.getClass()).info("batchUpdate sql:"+sql+",size:"+batchArgs.size());
			this.jdbcTemplate.batchUpdate(sql, batchArgs);
		}
	}
	
	/**
	 * 执行单条更新操作的语�?
	 * @param sql
	 */
	public void update(String sql){
		LogFactory.getLog(this.getClass()).info("update sql:"+sql);
		this.jdbcTemplate.execute(sql);
	}
	
	/**
	 * 用户insert、update、delete类型的sql语句
	 * @param sql
	 * @param params  对象类型的数组，如果是list 则在调用发放前必须调用list.toArray()
	 */
	public void update(String sql,Object[] params){
		LogFactory.getLog(this.getClass()).info("update sql:"+sql+",["+StringUtil.join(params,",")+"]");
		this.jdbcTemplate.update(sql, params);
	}
	
	/**
	 * 返回单个实体对象
	 * @param <T>
	 * @param sql
	 * @param targetClazz
	 * @param params
	 * @return
	 */
	public <T> T getEntry(String sql,RowMapper<T> rowMapper,Object[] params){
		List<T> list = getEntryList(sql,rowMapper,params);
		if(list.size() == 0) return null;
		return list.get(0);
	}
	
	/**
	 * 返回多个实体对象
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @param params
	 * @return
	 */
	public <T> List<T> getEntryList(String sql,RowMapper<T> rowMapper,Object[] params){
		LogFactory.getLog(this.getClass()).info("query sql:"+sql+",["+StringUtil.join(params,",")+"]");
		return jdbcTemplate.query(sql, params, rowMapper);
	}
	
	/**
	 * 获取查询单个字段的字符串数组
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<String> queryStringArray(String sql,Object[] params){
		LogFactory.getLog(this.getClass()).info("queryForList sql:"+sql+",["+StringUtil.join(params,",")+"]");
		return this.jdbcTemplate.queryForList(sql, String.class, params);
	}
	
	/**
	 * 获取查询单个字段的整数�?数组
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Integer> queryIntegerArray(String sql,Object[] params){
		LogFactory.getLog(this.getClass()).info("queryForList sql:"+sql+",["+StringUtil.join(params,",")+"]");
		return this.jdbcTemplate.queryForList(sql, Integer.class, params);
	}

	/**
	 * 查询单个实体，没有则返回null
	 * @param sql
	 * @param params 对象类型的数组，如果是list 则在调用发放前必须调用list.toArray()
	 * @return
	 */
	public Map<String,Object> queryMap(String sql,Object[] params){
		List<Map<String,Object>> list = queryListMap(sql,params);
		if(list.size() == 0) return null;
		return list.get(0);
	}
	
	/**
	 * 返回多个实体对象
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> queryListMap(String sql,Object[] params){
		LogFactory.getLog(this.getClass()).info("queryForList sql:"+sql+",["+StringUtil.join(params,",")+"]");
		return this.jdbcTemplate.queryForList(sql, params);
	}
	
	/**
	 * 根据sql语句，获取返回的单个数字�?
	 * @param sql 类似SELECT count(*) FROM�?ABLE
	 * @param params 对象类型的数组，如果是list 则在调用发放前必须调用list.toArray()
	 * @return 返回NULL或�?其他
	 */
	public Long queryLong(String sql,Object[] params){
		LogFactory.getLog(this.getClass()).info("queryForLong sql:"+sql+",["+StringUtil.join(params,",")+"]");
		List<Long> list = this.jdbcTemplate.queryForList(sql, Long.class, params);
		if(list.size() == 0) return null;
		return list.get(0);
	}
	
	/**
	 * 根据sql语句，获取返回的单个数字�?
	 * @param sql 类似SELECT count(*) FROM�?ABLE
	 * @param params 对象类型的数组，如果是list 则在调用发放前必须调用list.toArray()
	 * @return 返回 NULL或�?其他
	 */
	public Integer queryInteger(String sql,Object[] params){
		List<Integer> list = queryIntegerArray(sql, params);
		if(list.size() == 0) return null;
		return list.get(0);
	}
	
	/**
	 * 保存�?��记录，并返回生成的自增id
	 * @param sql
	 * @param params 对象类型的数组，如果是list 则在调用发放前必须调用list.toArray()
	 * @return
	 */
	public Long saveAndGetId(final String sql,final Object[] params){
		LogFactory.getLog(this.getClass()).info("insert sql:"+sql+",["+StringUtil.join(params,",")+"]");
		KeyHolder keyHolder = new GeneratedKeyHolder();  
		jdbcTemplate.update(new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				
				for(int i=0;i<params.length;i++){
					ps.setObject(i+1, params[i]);
				}
				return ps;
			}
			
		}, keyHolder);
		return keyHolder.getKey().longValue();
	}
	
	/**
	 * 获取查询单个字段的单行�?
	 * @param sql
	 * @param params
	 * @return
	 */
	public String queryString(String sql,Object[] params){
		List<String> list = queryStringArray(sql, params);
		if(list.size() == 0) return null;
		return list.get(0);
	}
}
