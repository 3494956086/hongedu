import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

public class CodeGenerator {
    @Test
    public void main1(){
        //1 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        //2全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
        gc.setOutputDir(projectPath+"/src/main/java");
        gc.setAuthor("hong");
        gc.setOpen(false);//生成后是否打开资源管理器
        gc.setFileOverride(false);//重新生成时文件是否覆盖
        /**
         * 生成service层代码
         */
        gc.setServiceName("%sService");//去掉首字母I
        gc.setIdType(IdType.ID_WORKER_STR);//主键策略
        gc.setDateType(DateType.ONLY_DATE);//生成实体类日期类型
        gc.setSwagger2(true);//开启swagger2模式
        autoGenerator.setGlobalConfig(gc);
        //3 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/hongedu?serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setDbType(DbType.MYSQL);
        autoGenerator.setDataSource(dsc);
        //4 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("statisticsservice");//模块名
        pc.setParent("com.hong");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        autoGenerator.setPackageInfo(pc);
        //5 策略配置
        StrategyConfig sc = new StrategyConfig();
        sc.setInclude("statistics_daily");
        sc.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体
        sc.setTablePrefix(pc.getModuleName()+"_");
        sc.setColumnNaming(NamingStrategy.underline_to_camel);
        sc.setEntityLombokModel(true);//lombok模型
        sc.setRestControllerStyle(true);//restful api 风格
        sc.setControllerMappingHyphenStyle(true);//url驼峰转连字符
        autoGenerator.setStrategy(sc);
        //6执行
        autoGenerator.execute();
    }
}
