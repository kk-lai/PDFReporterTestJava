package com.my3devtools.pdfreportertest;

import org.oss.pdfreporter.engine.JRException;
import org.oss.pdfreporter.repo.RepositoryManager;
import org.oss.pdfreporter.repo.FileResourceLoader;
import org.oss.pdfreporter.engine.design.JasperDesign;
import org.oss.pdfreporter.engine.util.JRSaver;
import org.oss.pdfreporter.registry.ApiRegistry;
import org.oss.pdfreporter.engine.JasperPrint;
import org.oss.pdfreporter.engine.JasperReport;
import org.oss.pdfreporter.engine.xml.JRXmlLoader;
import org.oss.pdfreporter.engine.JasperCompileManager;
import org.oss.pdfreporter.sql.IConnection;
import org.oss.pdfreporter.engine.JasperFillManager;
import org.oss.pdfreporter.engine.JasperExportManager;
import org.oss.pdfreporter.sql.SQLException;

import java.io.IOException;
import java.io.InputStream;


public class ReportExporter {
	public void generateReport(String jrxmlFile, String resourcesFolder,  String photosFolder, String pdfPath, IConnection conn) {
		String jrxmlFolder = jrxmlFile.substring(0, jrxmlFile.lastIndexOf('/'));

        try {
            ApiRegistry.initSession();
            RepositoryManager repositoryManager = RepositoryManager.getInstance();
            repositoryManager.reset();
            repositoryManager.setDefaulReportFolder(jrxmlFolder);
            repositoryManager.setDefaultResourceFolder(resourcesFolder);
            repositoryManager.addExtraReportFolder(photosFolder);

            InputStream isReport = FileResourceLoader.getInputStream(jrxmlFile);
            JasperDesign design = JRXmlLoader.load(isReport);
            isReport.close();
            JasperReport jrReport = JasperCompileManager.compileReport(design);
//            IConnection sqlDataSource = ApiRegistry.getSqlFactory().newConnection(sqliteFile, null, null);
            JasperPrint jprint = JasperFillManager.fillReport(jrReport, null, conn);
            JasperExportManager.exportReportToPdfFile(jprint, pdfPath);

            ApiRegistry.dispose();

        } catch (IOException e) {
        	System.err.println(e.getMessage());
        } catch (JRException e) {
        	System.err.println(e.getMessage());
        }
	}

		
	public void generateReport(String jrxmlFile, String sqliteFile, String resourcesFolder,  String photosFolder, String pdfPath) {
        String jrxmlFolder = jrxmlFile.substring(0, jrxmlFile.lastIndexOf('/'));

        try {
        	
            ApiRegistry.initSession();
            RepositoryManager repositoryManager = RepositoryManager.getInstance();
            repositoryManager.reset();
            repositoryManager.setDefaulReportFolder(jrxmlFolder);
            repositoryManager.setDefaultResourceFolder(resourcesFolder);
            repositoryManager.addExtraReportFolder(photosFolder);

            InputStream isReport = FileResourceLoader.getInputStream(jrxmlFile);
            JasperDesign design = JRXmlLoader.load(isReport);
            isReport.close();           
            JasperReport jrReport = JasperCompileManager.compileReport(design);
            
            IConnection sqlDataSource = ApiRegistry.getSqlFactory().newConnection(sqliteFile, null, null);
            JasperPrint jprint = JasperFillManager.fillReport(jrReport, null, sqlDataSource);
            JasperExportManager.exportReportToPdfFile(jprint, pdfPath);
            ApiRegistry.dispose();

        } catch (IOException e) {
        	System.err.println(e.getMessage());
        } catch (JRException e) {
        	System.err.println(e.getMessage());
        } catch (SQLException e) {
        	System.err.println(e.getMessage());
        }		
	}
}

