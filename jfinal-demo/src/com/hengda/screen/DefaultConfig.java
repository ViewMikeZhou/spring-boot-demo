package com.hengda.screen;


import com.hengda.screen.controller.ScreenController;
import com.hengda.screen.model.User;
import com.jfinal.aop.Interceptor;
import com.jfinal.config.*;
import com.jfinal.kit.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.template.Engine;


public class DefaultConfig extends JFinalConfig {


    @Override
    public void configConstant(Constants constants) {
        PropKit.use("jdbc.properties");
        constants.setDevMode(true);
    }

    @Override
    public void configRoute(Routes routes) {
        routes.add("/inducation", ScreenController.class);
    }

    @Override
    public void configEngine(Engine engine) {

    }

    public static C3p0Plugin createC3p0Plugin() {
        return new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("username"), PropKit.get("password").trim());
    }

    @Override
    public void configPlugin(Plugins plugins) {

      /*  DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("username"),PropKit.get("password").trim());
        // 配置C3p0数据库连接池插件
        //  C3p0Plugin c3p0Plugin = createC3p0Plugin();

        plugins.add(druidPlugin);
        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin("mysql",druidPlugin);
        String rootClassPath = PathKit.getRootClassPath();
        arp.setBaseSqlTemplatePath(rootClassPath+"/sql");
        arp.addSqlTemplate("all.sql");
        plugins.add(arp);
        // 配置属性名(字段名)大小写不敏感容器工厂
        arp.setContainerFactory(new CaseInsensitiveContainerFactory());
        //关系映射model
        arp.addMapping("User_table", User.class);*/
    }

    @Override
    public void configInterceptor(Interceptors interceptors) {

    }

    @Override
    public void configHandler(Handlers handlers) {

    }
}
