/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandogh;

import ir.mnm.util.io;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 *
 * @author mghasemy
 */
public class convertor {

    io i = new io();

    public void start(String csv,String out) {
        List<String> in = i.readfromfile(new File(csv));
        int i=1;
        for (String s : in) {
            String a[]=s.split(",");
            print(a, i,out);
            System.out.println(i+"complete");
            i++;
        }
        
    }
    public void print(String input[],int i,String out){
        List<Map<String, ?>> maps = new ArrayList<Map<String, ?>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fild1", input[0]);
        map.put("fild2", input[1]);
        map.put("fild3", input[2]);
        map.put("fild4", input[3]);
        maps.add(map);
        JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(maps);
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("report1.jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), dataSource);
            Exporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            File exportReportFile = new File(out+System.getProperty("File.separator")+"file_"+i+".docx");
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportReportFile));
            exporter.exportReport();
            System.out.println("done");
        } catch (Exception s) {
            s.printStackTrace();
        }
    }
    public static void main(String a[]) {
        convertor cp = new convertor();
        try {
            cp.start("/disk/workspace/sandogh/input.csv","");
        } catch (Exception s) {
        }
    }
}
