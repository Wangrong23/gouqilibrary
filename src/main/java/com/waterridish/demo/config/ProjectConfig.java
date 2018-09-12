package com.waterridish.demo.config;

import com.jfinal.config.*;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSourceFactory;
import com.waterridish.demo.controller.IndexController;
import com.waterridish.demo.model.*;

public class ProjectConfig extends JFinalConfig {
    public void configConstant(Constants constants) {

    }

    public void configRoute(Routes routes) {
//        routes.setBaseViewPath("");
        routes.add("/",IndexController.class);
//        routes.add("/manager",ManagerController.class);
    }

    public void configEngine(Engine engine) {
// devMode 配置为 true，将支持模板实时热加载
        engine.setDevMode(true);
        engine.addSharedFunction("/layout.html");
        engine.addSharedMethod(new ShareMethod());
    }

    public void configPlugin(Plugins plugins) {
        DruidPlugin dp = new DruidPlugin("jdbc:mysql://localhost/gouqilibrary?serverTimezone=UTC&characterEncoding=utf-8", "root", "red1902167838");
        plugins.add(dp);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);

        arp.addMapping("reader","reader_id", Reader.class);
        arp.addMapping("mark", "mark_num", Mark.class);
        arp.addMapping("borrow", "borrow_num", Borrow.class);
        arp.addMapping("back", "bac_num", Back.class);
        arp.addMapping("booklist",Booklist.class);

//        ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
        Engine engine = arp.getEngine();

        // 上面的代码获取到了用于 sql 管理功能的 Engine 对象，接着就可以开始配置了
        engine.setSourceFactory(new ClassPathSourceFactory());
        engine.addSharedMethod(new StrKit());

//        arp.start();
        plugins.add(arp);

    }

    public void configInterceptor(Interceptors interceptors) {

    }

    public void configHandler(Handlers handlers) {

    }
}
