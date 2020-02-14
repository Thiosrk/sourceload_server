package com.csga.sourceload_server.Utils.Data;

import com.csga.sourceload_server.Model.Column;
import com.csga.sourceload_server.Model.TaskInfo;
import com.csga.sourceload_server.Service.ColumnService;
import com.csga.sourceload_server.Service.TaskInfoService;
import com.csga.sourceload_server.Utils.DataBase.DataSourceManager;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.tools.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ModelGenerator {

    private static final Logger logger = LoggerFactory.getLogger(ModelGenerator.class);

    @Autowired
    TaskInfoService taskInfoService;
    @Autowired
    ColumnService columnService;

    private String modelName;

    private List<Column> columnList = new ArrayList<>();

    private Workbook getWorkbook(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        if (!is.markSupported()) {
            is = new PushbackInputStream(is, 8);
        }
        Workbook wb = null;
        try {
            wb = new  HSSFWorkbook(is);//支持excel 2003
        } catch (Exception e) {
            logger.info("excel 2007及以上版本");
            wb = new  XSSFWorkbook(new FileInputStream(file));//支持excel 2007
        }

        return wb;
    }

    public void generateByExcel(File file) throws IOException {
        Workbook workBook = null;
        try {
            workBook = getWorkbook(file);
        } catch (IOException e) {

            e.printStackTrace();
        }
        //默认读取第一页
        Sheet sheet = workBook.getSheetAt(0);
        Row row;
        int rowCount = sheet.getLastRowNum();//逻辑行，包括空行
        row = sheet.getRow(0);
        if (row==null||"".equals(row.getCell(0).getStringCellValue())){
            logger.info("excel表格式出错");
        }
        modelName = sheet.getRow(1).getCell(0).getStringCellValue();
        if (modelName==null||"".equals(modelName)){
            logger.info("excel表格式出错");
        }
        for (int i = 3; i <= rowCount; i++) {
            row = sheet.getRow(i);
            if (row==null)
                break;
            if (StringUtils.isEmpty(sheet.getRow(i).getCell(0).getStringCellValue())){
                break;
            }
            String name = sheet.getRow(i).getCell(0).getStringCellValue();
            String comment = sheet.getRow(i).getCell(1).getStringCellValue();
            String type = sheet.getRow(i).getCell(2).getStringCellValue();
            columnList.add(new Column(modelName,name,type,comment));
        }
        String modelContent = parseModel();
        String modelOutPutPath="";
        File directory = new File("");
        modelOutPutPath = directory.getAbsolutePath()
                + "/src/main/java/"+ ModelGeneratorConstants.modelOutPath.replace(".", "/")+"/"+modelName+ ".java";

        try {
            Class.forName("com.csga.sourceload_server.Model."+modelName);
            logger.info("已有该class文件！");
        } catch (Exception e){
            logger.info("未生成class文件！");
            //生成Java文件
            generateJavaFile(modelOutPutPath,modelContent);
            //动态编译生成的java文件
            generateClassFile(modelOutPutPath);
        }
        logger.info("实体类生成完毕！");
        logger.info("开始数据库建表，表名："+modelName);
        try {
            generateDBTable(modelName,columnList);
        }catch (Exception e){
            logger.info("建表失败，错误内容：");
            e.printStackTrace();
        }
        logger.info("建表完成！");
        logger.info("开始保存任务数据表结构，表名："+modelName);
        try {
            saveTable(columnList);
        }catch (Exception e){
            logger.info("保存失败，错误内容：");
            e.printStackTrace();
        }
        logger.info("保存完毕！已录入数据库");

    }

    private void saveTable(List<Column> columns){
        columnService.addOrUpdateColumns(columns);
    }

    private void generateDBTable(String modelName,List<Column> columns){
        TaskInfo taskInfo = taskInfoService.getTaskInfoByTableName(modelName);
        DataSourceManager.INSTANCE.setCurrentDataSource(taskInfo.getDataSource());
        QueryRunner queryRunner = new QueryRunner(DataSourceManager.INSTANCE.getCurrentDataSource());

        StringBuffer sb = new StringBuffer("create table ");
        sb.append(modelName).append("(");
        for (int i=0 ; i<columns.size()-1 ; i++){
            sb.append(columns.get(i).getColName()).append(" ");
            sb.append(oracleType(columns.get(i).getColType())).append(",");
        }
        sb.append(columns.get(columns.size()-1).getColName()).append(" ");
        sb.append(oracleType(columns.get(columns.size()-1).getColType())).append(")");

        logger.info("建表语句："+sb.toString());
        DBUtils.createTable(sb.toString(),queryRunner);

    }

    private void generateJavaFile(String outputPath,String content){
        try {
            FileWriter fileWriter = new FileWriter(outputPath);
            PrintWriter pw = new PrintWriter(fileWriter);
            pw.println(content);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info(".java文件生成完毕！");
    }

    private void generateClassFile(String outputPath) throws IOException {
        JavaCompiler cm = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager sm = cm.getStandardFileManager(null, null, null);
        JavaFileManager.Location location = StandardLocation.CLASS_OUTPUT;
        String sourcepath = String.valueOf(this.getClass().getResource("/"));
        sourcepath = sourcepath.substring(sourcepath.lastIndexOf(':')-1);
        List<File> fileList = new ArrayList<>();
        fileList.add(new File(sourcepath));
        sm.setLocation(location, fileList);
        Iterable<? extends JavaFileObject> javaFileObjects = sm.getJavaFileObjects(outputPath);
        JavaCompiler.CompilationTask task = cm.getTask(null, sm, null, null, null, javaFileObjects);
        task.call();
        sm.close();
        logger.info(".class文件编译完毕！");
    }

//    private String parseRepo() {
//        StringBuffer sb = new StringBuffer();
//        sb.append("package com.csga.sourceload_server.Repository;\r\n\r\n");
//        sb.append("import org.springframework.data.jpa.repository.*;\r\n");
//        sb.append("import com.csga.sourceload_server.Model.*;\r\n\r\n");
//        sb.append("public interface ");
//        sb.append(modelName);
//        sb.append("Repository extends JpaRepository<");
//        sb.append(modelName);
//        sb.append(", Integer> {\r\n}");
//        return sb.toString();
//    }

    private String parseModel() {
        StringBuffer sb = new StringBuffer();
        sb.append("package com.csga.sourceload_server.Model;\r\n\r\n");
        sb.append("import javax.persistence.*;\r\n");
        sb.append("import java.util.*;\r\n");
//        sb.append("import java.sql.Date;\r\n");
        sb.append("import java.math.BigDecimal;\r\n\r\n");
        sb.append("@Table(name = \"");sb.append(modelName);sb.append("\")\r\n");
        sb.append("public class ");sb.append(modelName);sb.append(" {\r\n\r\n");
//        sb.append("    @Id\r\n");
//        sb.append("    @GeneratedValue\r\n");
//        sb.append("    private Integer u_id;//临时主键\r\n");
        processAllAttrs(sb);
        processAllMethod(sb);
        sb.append("}\r\n");
        return sb.toString();
    }

    private void processAllAttrs(StringBuffer sb) {

        for (Column column:columnList) {
            System.out.println(column.toString());
            sb.append("    private " + sqlType2JavaType(column.getColType()) + " "
                    + column.getColName() + "; //"+column.getDescription()+"\r\n");
        }
        sb.append("\r\n");

    }

    private void processAllMethod(StringBuffer sb) {

//        sb.append("    public Integer getU_id(){\r\n");
//        sb.append("        return u_id;\r\n");
//        sb.append("    }\r\n");
//        sb.append("    public void setU_id(Integer u_id){\r\n");
//        sb.append("        this.u_id = u_id;\r\n");
//        sb.append("    }\r\n");

        for (Column column:columnList){
            String name = column.getColName();
            String type = column.getColType();
            sb.append("    public " + sqlType2JavaType(type) + " get"
                    + initcap(name) + "(){\r\n");
            sb.append("        return " + name + ";\r\n");
            sb.append("    }\r\n");
            sb.append("    public void set" + initcap(name) + "("
                    + sqlType2JavaType(type) + " " + name
                    + "){\r\n");
            sb.append("        this." + name + "=" + name + ";\r\n");
            sb.append("    }\r\n");
        }
        sb.append("@Override\n" +
                "                    public String toString() {\n" +
                "                       return \""+modelName+"{ +\" +");
        for (Column column : columnList){
            sb.append("                \", "+column.getColName()+"=\" + "+column.getColName()+" +\n");
        }
        sb.append("'}';\n");
        sb.append("}");
    }

    private String initcap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    private String oracleType(String colType){
        if (colType.equalsIgnoreCase("timestamp"))
            return "date";
        return colType;
    }

    private String sqlType2JavaType(String colType) {
        colType = colType.replaceAll("\\(.*?\\)","");
        System.out.println(colType);

        if (colType.equalsIgnoreCase("bit")) {
            return "Boolean";
        } else if (colType.equalsIgnoreCase("binary")
                || colType.equalsIgnoreCase("image")
                || colType.equalsIgnoreCase("blob")
                || colType.equalsIgnoreCase("clob")
                || colType.equalsIgnoreCase("varbinary")) {
            return "byte[]";
        } else if (colType.equalsIgnoreCase("smallint")
                || colType.equalsIgnoreCase("tinyint")) {
            return "Short";
        } else if (colType.equalsIgnoreCase("int")) {
            return "Integer";
        } else if (colType.equalsIgnoreCase("bigint")
                || colType.equalsIgnoreCase("number")) {
            return "Long";
        } else if (colType.equalsIgnoreCase("real")) {
            return "Float";
        } else if (colType.equalsIgnoreCase("float")) {
            return "Double";
        } else if (colType.equalsIgnoreCase("money")
                || colType.equalsIgnoreCase("decimal")
                || colType.equalsIgnoreCase("smallmoney")
                || colType.equalsIgnoreCase("numeric")) {
            return "BigDecimal";
        } else if (colType.equalsIgnoreCase("varchar")
                || colType.equalsIgnoreCase("char")
                || colType.equalsIgnoreCase("varchar2")
                || colType.equalsIgnoreCase("nvarchar2")) {
            return "String";
        } else if (colType.equalsIgnoreCase("date")
                || colType.equalsIgnoreCase("timestamp")
                || colType.equalsIgnoreCase("timestamp with local time zone")
                || colType.equalsIgnoreCase("timestamp with time zone")) {
            return "Date";
        }
        logger.warn("未知字段类型，可能有误！");
        return "String";

    }

    public String getModelName() {
        return modelName;
    }
}
