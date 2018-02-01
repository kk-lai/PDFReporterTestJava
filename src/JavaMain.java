import com.my3devtools.pdfreportertest.ReportExporter;
import org.oss.pdfreporter.sql.SqlFactory;
import org.oss.pdfreporter.registry.ApiRegistry;
import org.oss.pdfreporter.sql.IConnection;
import org.oss.pdfreporter.sql.SQLException;

public class JavaMain {

	public static void main(String[] args) {
		ReportExporter engine = new ReportExporter();
		
		SqlFactory.registerFactory("org.sqlite.JDBC");
		try {
			String root = "assets";
			IConnection conn = ApiRegistry.getSqlFactory().createConnection("jdbc:sqlite:"+root+"/report/chinook.db", null, null);
			engine.generateReport(root + "/report/main.jrxml", 
					root + "/resources", 
					root + "/photos", "report.pdf",  conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
