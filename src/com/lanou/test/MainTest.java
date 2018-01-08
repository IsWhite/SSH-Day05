package com.lanou.test;

import com.lanou.domain.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * 单元测试类:每一层都要写
 */
public class MainTest {
    /*数据库连接的工厂对象*/
    private SessionFactory sessionFactory;
    /*数据库连接对象 真正拥有数据库的Crud操作的*/
    private Session session;
    /*数据库事物对象*/
    private Transaction transaction;

    @Before //初始化前
    public void init() {
        /*1.构建配置对象*/
        Configuration config = new Configuration();
        /*2.默认加载src下的配置文件Hibernate.cfg.xml*/
        config.configure();
        sessionFactory = config.buildSessionFactory();
        /*3.得到一个连接对象*/
        session = sessionFactory.openSession();
        /*4.开启一个事务对象*/
        transaction = session.beginTransaction();

    }

    @After //初始化后
    public void destroy() {
        /*关闭数据库连接*/

        /*6.提交事务*/
        transaction.commit();
        /*7.关闭连接*/
        session.close();
    }

    @Test //测试声明
    public void add() {
        User user = new User("李四", "123"); //User构造方法

        /*保存一个实体类对象,是以插入insert的方式调用
        * 即要求该对象的主键id不能有值,否则会报错*/
        session.save(user);

        /*save方法插入时不考虑id,只将非主键的执行insert
        *
        * saveOrUpdate方法:检查当前对象的主键id是否存在,如果当前实体类对象的主键id不存在,则执行的
        * 是save操作,否则insert插入非主键的update更新操作;
        * id不存在-save
        * id存在-update
        * */
        user.setId(1);
        session.saveOrUpdate(user); //常用
    }

    @Test
    public void delete() {
        User user = new User("李四", "123");
        /*删除某个对象,如果传入的实体类对象若有设置主键id则不进行任何操作*/

        user.setId(5);
        /*session 自带的delete方法只根据id进行删除,不考虑其他删除条件*/
        session.delete(user);


    }

    @Test
    public void update() {//更改
        User user = new User("王五", "123");

        user.setId(2);
        /*update更新方法要求主键id有值*/
        session.update(user);

    }

    @Test
    public void query() {
        /*两种
        * 1.根据主键id查询单个对象
        * 2.根据sql语句查询
        * */

        /*根据id查询单个对象*/
        /*get:第一个参数给的是要查询的实体类类名,
        *     第二个参数是主键id*/
        User user = session.get(User.class, 1);
        System.out.println(user);
        /*以实体类名*/
        System.out.println("实体类名" + User.class.getName());

        User user1 = (User) session.get(User.class.getName(), 1);
        System.out.println(user1);

        /*根据sql语句查询*/
        /*创建一个query查询对象,createQuery中的参数是从from开始,不需要加入前面的select
        * sql语句中涉及的类名和属性名都是住的是实体类中的,不是数据库中的,hibernate内部会自动进行转换
        * ?占位符*/
        Query query = session.createQuery("from  User where  name =? and password=?");
        /*设置sql语句中问好所对应的值*/
        query.setString(0, "三胖"); //第1个问号的替换值
        query.setString(1, "123456");//第二个问号的替换值
        List<User> users = query.list();
        System.out.println(users);

        /*给条件语句中的条件设置别名,根据别名设置对应的参数*/
        Query query2 = session.createQuery("from  User where  name =:a and password=:b");

        query2.setString("a", "王五");
        query2.setString("b", "123");

        List<User> users1 = query2.list();
        System.out.println(users1);
    }

    @Test
    public void query2() {
        Query query = session.createQuery("from User  where  name =?");
    /*设置参数 即sql语句中的问号对应的值
    * setString内部实际上还是调用的setParameter
    * 只不过是直接给定参数的类型;而setParameter会根据属性类型自动匹配
    * */
        query.setParameter(0, "李四"); //推荐使用的策略
        /*返回一个迭代器,默认返回的只是符合条件的对象主键id,当进行结果遍历的时候需要进行二次查询(根据id)*/
        Iterator<User> iterator = query.iterate();
        /*遍历迭代器*/
        while (iterator.hasNext()) {
            User user = iterator.next();
            System.out.println(user);
        }
    }

    @Test
    public void query3() {
        Query query = session.createQuery("from  User ");
        /*分页处理*/
        int start = 1;//起始页
        int pageSize = 2;//每页大小

        /*设置返回结果的最大条数 用它控制每页的数目*/
        query.setMaxResults(pageSize);
        /*设置返回结果集的偏移量*/
        query.setFirstResult((start - 1) * pageSize);


        List<User> users = query.list();
        System.out.println(users);


    }
}
