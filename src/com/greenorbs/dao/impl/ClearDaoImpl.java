package com.greenorbs.dao.impl;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Repository;

import com.greenorbs.dao.ClearDaoI;
import com.greenorbs.model.Video;
import com.greenorbs.util.ConfigUtil;
import com.greeorbs.zookeeper.ZookeeperClient;

@Repository
public class ClearDaoImpl extends BaseDaoImpl<Video> implements ClearDaoI{
	
	private static Logger log = Logger.getLogger(ZookeeperClient.class);
	String Path = this.getClass().getClassLoader().getResource("/").getPath();
	Stat stat = new Stat();
	int last =Path.lastIndexOf("WEB-INF");
	String realPath = Path.substring(1,last);
	String http_node=ConfigUtil.get("http_node");
	SimpleDateFormat myFmt=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
/*	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}*/

	@Override
	public void clearAllData(){
		//from zookeeper get all data update to sql
		System.out.println("clearAllData()"+realPath);
		try {
			List<String> macurl = ZookeeperClient.getClient().getChildren().forPath(http_node);//连接zookeeper获取数据
			for (String mac : macurl) {
				String url = new String(ZookeeperClient.getClient().getData().storingStatIn(stat).forPath(http_node+"/"+mac),"utf-8");//从zookper中读取url
				String runtime = myFmt.format(new Date(stat.getCtime()));//从zookper中读取部署时间
				String UpdataSql = "update camerainfo set runtime='"+runtime+"',"+"url='"+url+"'"+" where mac='"+mac+"'";//根据mac 更新部署时间和url
				if(executeSql(UpdataSql)==0){//如果数据库返回0 说明该条数据不存在 生成页面 变存入数据库
					
					try {
						BufferedReader reader = new BufferedReader(new FileReader(java.net.URLDecoder.decode(realPath+"Demo.jsp", "UTF-8")));
						BufferedWriter writer = new BufferedWriter(new FileWriter(java.net.URLDecoder.decode(realPath+mac+".jsp", "UTF-8")));
						String str = null;
						while ((str = reader.readLine()) != null) {
							str=str.replaceAll("Number", mac);
							writer.write(str);
							writer.newLine();
						}
						writer.flush();
						reader.close();
						writer.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						log.error("12点定时执行时未找到Demo.jsp。。。。");
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						log.error("12点定时执行时文件流io异常。。。。");
						e.printStackTrace();
					}
					
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("mac", mac);
					params.put("url", url);
					params.put("html", "http://127.0.0.1:8080/video/"+mac+".jsp");
					params.put("runtime", runtime);
					String sql="insert into camerainfo (mac,url,html,runtime) values (:mac,:url,:html,:runtime)";
					executeSql(sql, params);//插入不存在数据
				}else{//如果数据库返回非0 说明该条数据存在 直接生成页面
					try {
						BufferedReader reader = new BufferedReader(new FileReader(java.net.URLDecoder.decode(realPath+"Demo.jsp", "UTF-8")));
						BufferedWriter writer = new BufferedWriter(new FileWriter(java.net.URLDecoder.decode(realPath+mac+".jsp", "UTF-8")));
						String str = null;
						while ((str = reader.readLine()) != null) {
							str=str.replaceAll("Number", mac);
							writer.write(str);
							writer.newLine();
						}
						writer.flush();
						reader.close();
						writer.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						log.error("12点定时执行时未找到Demo.jsp。。。。");
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						log.error("12点定时执行时文件流io异常。。。。");
						e.printStackTrace();
					}
				}
				
			}
			System.out.println("12点定时同步数据完毕.....");
			//by myp
			String selectSql="select mac from camerainfo";
			List mlist=findBySql(selectSql);
			int lenList=mlist.size();
			boolean findFlag=false;
		    for(int i=0;i<lenList;i++)//visit all macs in the datatable camerainfo,if any of them  does not exist in the zookeeper,then set url of this record null
			{
		    	findFlag=false;
		    	for (String mac : macurl)
		    	{
		    		if(mlist.get(i).equals(mac))
		    		{
		    			findFlag=true;
		    			break;
		    		}
		    	}
		    	if(!findFlag)
		    	{
		    		 
		    		String url = "";
		    		String sql="update camerainfo set url='"+url+"'"+" where mac='"+mlist.get(i)+"'";
		    		executeSql(sql);
		    		System.out.println("zookeeper does not keep this mac+++"+mlist.get(i));
		    	}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("12点定时执行获取children或循环处理错误。。。。。");
			e.printStackTrace();
		}
		
		/*注解定时器报not session Session session = super.openSession();
		String UpdataSql = "update camerainfo set url='1111' where mac='11111'";//camara exist 
		SQLQuery q = session.createSQLQuery(UpdataSql);
		if (session != null && session.isOpen()) {
			session.close();
		}
		System.out.println(q.executeUpdate());*/
		
	}

}
