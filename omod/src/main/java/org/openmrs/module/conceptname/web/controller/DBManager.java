package org.openmrs.module.conceptname.web.controller;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.openmrs.module.conceptname.web.controller.Utils;
import org.openmrs.module.conceptname.web.controller.DBConnection;
import org.openmrs.util.OpenmrsUtil;

public class DBManager {
	
Connection conn = null;
	
	PreparedStatement pStatement = null;
	
	private ResultSet resultSet = null;
	
	public DBManager() {
		
	}
	
	public void openConnection() throws SQLException {
		DBConnection openmrsConn = Utils.getNmrsConnectionDetails();
		
		conn = DriverManager.getConnection(openmrsConn.getUrl(), openmrsConn.getUsername(), openmrsConn.getPassword());
	}
	
	public void closeConnection() {
		try {
			if (conn != null) {
				conn.close();
			}
			if (pStatement != null) {
				pStatement.close();
			}
		}
		catch (Exception ex) {
			
		}
	}
	
	
	
	

}
