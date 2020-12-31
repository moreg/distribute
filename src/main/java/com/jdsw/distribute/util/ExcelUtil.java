package com.jdsw.distribute.util;

import com.jdsw.distribute.model.Distribute;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {
    //private static Logger log= (Logger) LoggerFactory.getLogger(ExcelUtil.class);
    /**
     * 方法名：exportExcel
     * 功能：导出Excel
     * 描述：
     * 创建人：typ
     * 创建时间：2018/10/19 16:00
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
  /*  public static void exportExcel(HttpServletResponse response, ExcelData data) {
        log.info("导出解析开始，fileName:{}",data.getFileName());
        try {
            //实例化HSSFWorkbook
            HSSFWorkbook workbook = new HSSFWorkbook();
            //创建一个Excel表单，参数为sheet的名字
            HSSFSheet sheet = workbook.createSheet("sheet");
            //设置表头
            setTitle(workbook, sheet, data.getHead());
            //设置单元格并赋值
            setData(sheet, data.getData());
            //设置浏览器下载
            setBrowser(response, workbook, data.getFileName());
            log.info("导出解析成功!");
        } catch (Exception e) {
            log.info("导出解析失败!");
            e.printStackTrace();
        }
    }
*/
    /**
     * 方法名：setTitle
     * 功能：设置表头
     * 描述：
     * 创建人：typ
     * 创建时间：2018/10/19 10:20
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    private static void setTitle(HSSFWorkbook workbook, HSSFSheet sheet, String[] str) {
        try {
            HSSFRow row = sheet.createRow(0);
            //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
            for (int i = 0; i <= str.length; i++) {
                sheet.setColumnWidth(i, 15 * 256);
            }
            //设置为居中加粗,格式化时间格式
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
            //创建表头名称
            HSSFCell cell;
            for (int j = 0; j < str.length; j++) {
                cell = row.createCell(j);
                cell.setCellValue(str[j]);
                cell.setCellStyle(style);
            }
        } catch (Exception e) {
            //log.info("导出时设置表头失败！");
            e.printStackTrace();
        }
    }

    /**
     * 方法名：setData
     * 功能：表格赋值
     * 描述：
     * 创建人：typ
     * 创建时间：2018/10/19 16:11
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    private static void setData(HSSFSheet sheet, List<String[]> data) {
        try{
            int rowNum = 1;
            for (int i = 0; i < data.size(); i++) {
                HSSFRow row = sheet.createRow(rowNum);
                for (int j = 0; j < data.get(i).length; j++) {
                    row.createCell(j).setCellValue(data.get(i)[j]);
                }
                rowNum++;
            }
            //log.info("表格赋值成功！");
        }catch (Exception e){
            //log.info("表格赋值失败！");
            e.printStackTrace();
        }
    }

    /**
     * 方法名：setBrowser
     * 功能：使用浏览器下载
     * 描述：
     * 创建人：typ
     * 创建时间：2018/10/19 16:20
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    private static void setBrowser(HttpServletResponse response, HSSFWorkbook workbook, String fileName) {
        try {
            //清空response
            response.reset();
            //设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            //将excel写入到输出流中
            workbook.write(os);
            os.flush();
            os.close();
            //log.info("设置浏览器下载成功！");
        } catch (Exception e) {
            //log.info("设置浏览器下载失败！");
            e.printStackTrace();
        }

    }


    public static List<?> getBankListByExcel(InputStream in) throws Exception {

        List<Distribute> list = new ArrayList<>();
        //创建Excel工作薄
        Workbook work = getWorkbook(in);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            //遍历当前sheet中的所有行
            for (int j =1; j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null ) {
                    continue;
                }

                Distribute Distribute = new Distribute();

                //把每个单元格的值付给对象的对应属性
                if (row.getCell(0)!=null){
                    Distribute.setName(String.valueOf(getCellValue(row.getCell(0))));
                }
                if (row.getCell(1)!=null){
                    Distribute.setContactsPhone(String.valueOf((String) getCellValue(row.getCell(1))));
                }
                if (row.getCell(2)!=null){
                    Distribute.setWechatName(String.valueOf(getCellValue(row.getCell(2))));
                }
                if (row.getCell(3)!=null){
                    Distribute.setWechatNumber(Integer.valueOf((String) getCellValue(row.getCell(3))));
                }
                if (row.getCell(4)!=null){
                    Distribute.setBirthday(DateUtil.parseDate(String.valueOf(getCellValue(row.getCell(4)))));
                }
                if (row.getCell(5)!=null){
                    Distribute.setSex(Integer.valueOf((String)getCellValue(row.getCell(5))));
                }
                if (row.getCell(6)!=null){
                    Distribute.setState(Integer.valueOf((String)getCellValue(row.getCell(6))));
                }
                if (row.getCell(7) != null) {
                    Distribute.setIndustry(String.valueOf(getCellValue(row.getCell(7))));
                }
                if (row.getCell(8) != null) {
                    Distribute.setCorporateName(String.valueOf(getCellValue(row.getCell(8))));
                }
                if (row.getCell(9) != null) {
                    Distribute.setCorporatePhone(String.valueOf((String)getCellValue(row.getCell(9))));
                }
                if (row.getCell(10) != null) {
                    Distribute.setCorporateNumber(String.valueOf(getCellValue(row.getCell(10))));
                }
                if (row.getCell(11) != null) {
                    Distribute.setCreditNumber(String.valueOf(getCellValue(row.getCell(11))));
                }
                if (row.getCell(12) != null) {
                    Distribute.setDomicilePhone(String.valueOf(getCellValue(row.getCell(12))));
                }
                if (row.getCell(13) != null) {
                    Distribute.setDomicile(String.valueOf(getCellValue(row.getCell(13))));
                }
                if (row.getCell(14) != null) {
                    Distribute.setScale(String.valueOf(getCellValue(row.getCell(14))));
                }
                if (row.getCell(15) != null) {
                    Distribute.setSource(Integer.valueOf((String)getCellValue(row.getCell(15))));
                }
                if (row.getCell(16) != null) {
                    Distribute.setFoundTime(String.valueOf(getCellValue(row.getCell(16))));
                }

                //遍历所有的列(把每一行的内容存放到对象中)
                list.add(Distribute);
            }
        }
        return list;
    }
    public static Workbook getWorkbook(InputStream inStr) throws Exception {
        Workbook wb = null;
        wb = WorkbookFactory.create(inStr);
        return wb;
    }
    public static Object getCellValue(Cell cell) {
        Object value = null;
        DecimalFormat df = new DecimalFormat("0");  //格式化number String字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化
        DecimalFormat df2 = new DecimalFormat("0");  //格式化数字
        switch (cell.getCellType()) {
            case STRING:
                value = cell.getRichStringCellValue().getString().replace("\r\n","");
                break;
            case NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue()).replace("\r\n","");
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue()).replace("\r\n","");
                } else {
                    value = df2.format(cell.getNumericCellValue()).replace("\r\n","");
                }
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case BLANK:
                value = "".replace("\r\n","");
                break;
            default:
                break;
        }
        return value;
    }
}
