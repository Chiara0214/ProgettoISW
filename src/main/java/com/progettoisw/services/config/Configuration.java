package com.progettoisw.services.config;

import java.util.Calendar;
import java.util.logging.Level;

import com.progettoisw.model.dao.DAOFactory;

public class Configuration {
  
  /* Database Configruation */
  public static final String DAO_IMPL=DAOFactory.MYSQLJDBCIMPL;
  public static final String DATABASE_DRIVER="com.mysql.cj.jdbc.Driver";
  public static final String SERVER_TIMEZONE=Calendar.getInstance().getTimeZone().getID();
  public static final String 
    DATABASE_URL="jdbc:mysql://localhost/progetto?user=root&password=mysql0214&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone="+SERVER_TIMEZONE;
  
  /* Session Configuration */
  public static final String COOKIE_IMPL=DAOFactory.COOKIEIMPL;
  
  /* Logger Configuration */
  public static final String GLOBAL_LOGGER_NAME="progetto";
  public static final String GLOBAL_LOGGER_FILE="/home/studente/Documents/logs/progetto_log.%g.%u.txt";
  public static final Level GLOBAL_LOGGER_LEVEL=Level.ALL;

}
