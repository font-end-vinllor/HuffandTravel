# 一 . JDBC  
> &emsp;&emsp;sun公司提供的一种数据库访问规则，规范，由于数据库种类较多，并且java语言使用比较广泛，sun公司提供的规范让其他数据库提供商去实现底层的访问规则。java程序只要使用sun公司提供的jdbc驱动即可。
### I. jdbc的基本使用   

       try {
			// 注册驱动  
			DriverManager.deregisterDriver(new com.mysql.cj.jdbc.Driver());
			 // 获取连接  
			 // DriverManager.getConnection("jdbc:mysql://localhost/student?user=root&password=1931165mwj");  
             //协议+访问的数据库   用户名    密码
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/student?serverTimezone=GMT&useSSL=false", "root", "1931165mwj");  
			Statement st = conn.createStatement();  
			ResultSet rs = st.executeQuery("SELECT * FROM s_tu");  
			while (rs.next()) {  
				int id = rs.getInt("id");  
				String name = rs.getString("name");  
				int age = rs.getInt("age");  
				System.out.println("id = " + id + " name = " + name + " age = " + age);  
			}  
			JDBCclose.release(conn, rs, st); 
		} catch (SQLException e) {  
			e.printStackTrace();  
		} 
释放资源  

     public class JDBCclose {
	/**
	 * 释放资源
	 * @param conn
	 * @param rs
	 * @param st
	 */
	public static void release(Connection conn,ResultSet rs, Statement st) {
		closeConnection(conn);
		closeResultset(rs);
		closeStatement(st);
	}
	
	private  static void closeResultset(ResultSet rs) {
		if(rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				rs =  null;
			}
	}
	private static void closeStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				st = null;
			}
		}
	}
	private static void closeConnection(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				conn = null;
			}
		}
	  }
    }
### JDBC工具类构建
1. 资源释放工作的整合

2. 驱动防二次注册
  
         // 注册驱动  
			DriverManager.deregisterDriver(new   com.mysql.cj.jdbc.Driver());
            Driver类里面的静态代码块(加载类时)已经执行驱动注册，上面代码等同注册了俩次。
		--------->
            最后形成的加载类驱动的代码

			jdbc 4.0 以后  可以不用写加载类驱动，会自动连接。mysql jar包底下META-INF services/java.sql.Driver
			查看驱动类
            Class.forName("com.mysql.cj.jdbc.Driver");
3. 使用properties配置文件  
  
         driverclass=com.mysql.cj.jdbc.Driver  
         url=jdbc:mysql://localhost/student?serverTimezone=GMT&useSSL=false  
         name=root
         password=1931165mwj

---
        static String url = null;
	    static String name = null;
	    static String password = null;
	    static String driverclass = null ;
	    static {
			//创建属性配置对象
		   Properties  properties = new Properties();
		   try { 
           // 如果配置文件放在工程的根目录下
			 //properties.load(new FileInputStream("jdbc.properties"));

			//如果配置文件放在src目录下，此时文件是在当前类的class的父目录下，加载类时，也加载了资源
			 InputStream in = JDBCclose.class.getClassLoader().getResourceAsStream ("jdbc.properties");
			 properties.load(in);

		  } catch (Exception e) {
		    	e.printStackTrace();
		  }
		    name = properties.getProperty("name");
		    password = properties.getProperty("password");
		    url = properties.getProperty("url");
		    driverclass = properties.getProperty("driverclass");
	     }

###   数据库的CRUD
1. insert  

                  INSERT INTO s_tu (name,age) VALUES ("靳飞飞"，19);  
		          INSERT INTO s_tu VALUES (1,"靳飞飞",19);  
2.delete

                        DELETE FROM s_tu WHERE  id = 1 ;
3.update
		
					  UPDATE s_tu SET age = 1 WHERE id = 1 ;

4.query

				               SELECT * FROM s_tu;
### 使用单元测试 Junit  
1. 定义一个测试类
2. 添加junit的支持
  
        右键工程 --- add library --- Junit --- junit 4

3. 需测试的方法上添加 @Test

            @Test
			public void testQuery(){...}
4. 光标选中方法名字，右键执行单元测试，/打开outline ，选择测试的方法。 
		