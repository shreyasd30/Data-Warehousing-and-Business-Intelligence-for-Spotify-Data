package project.etl_tracks_v3_0_1;

import routines.Numeric;
import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.StringHandling;
import routines.Relational;
import routines.TalendDate;
import routines.Mathematical;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;

@SuppressWarnings("unused")

/**
 * Job: etl_tracks_v3 Purpose: <br>
 * Description: <br>
 * 
 * @author user@talend.com
 * @version 8.0.1.20211109_1610
 * @status
 */
public class etl_tracks_v3 implements TalendJob {

	protected static void logIgnoredError(String message, Throwable cause) {
		System.err.println(message);
		if (cause != null) {
			cause.printStackTrace();
		}

	}

	public final Object obj = new Object();

	// for transmiting parameters purpose
	private Object valueObject = null;

	public Object getValueObject() {
		return this.valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}

	private final static String defaultCharset = java.nio.charset.Charset.defaultCharset().name();

	private final static String utf8Charset = "UTF-8";

	// contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();

		public PropertiesWithType(java.util.Properties properties) {
			super(properties);
		}

		public PropertiesWithType() {
			super();
		}

		public void setContextType(String key, String type) {
			propertyTypes.put(key, type);
		}

		public String getContextType(String key) {
			return propertyTypes.get(key);
		}
	}

	// create and load default properties
	private java.util.Properties defaultProps = new java.util.Properties();

	// create application properties with default
	public class ContextProperties extends PropertiesWithType {

		private static final long serialVersionUID = 1L;

		public ContextProperties(java.util.Properties properties) {
			super(properties);
		}

		public ContextProperties() {
			super();
		}

		public void synchronizeContext() {

		}

		// if the stored or passed value is "<TALEND_NULL>" string, it mean null
		public String getStringValue(String key) {
			String origin_value = this.getProperty(key);
			if (NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
				return null;
			}
			return origin_value;
		}

	}

	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.

	public ContextProperties getContext() {
		return this.context;
	}

	private final String jobVersion = "0.1";
	private final String jobName = "etl_tracks_v3";
	private final String projectName = "PROJECT_IE7374";
	public Integer errorCode = null;
	private String currentComponent = "";

	private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
	private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();

	private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
	public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";

	private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

	public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
			talendDataSources.put(dataSourceEntry.getKey(),
					new routines.system.TalendDataSource(dataSourceEntry.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	public void setDataSourceReferences(List serviceReferences) throws Exception {

		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();

		for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils
				.getServices(serviceReferences, javax.sql.DataSource.class).entrySet()) {
			dataSources.put(entry.getKey(), entry.getValue());
			talendDataSources.put(entry.getKey(), new routines.system.TalendDataSource(entry.getValue()));
		}

		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
	private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));

	public String getExceptionStackTrace() {
		if ("failure".equals(this.getStatus())) {
			errorMessagePS.flush();
			return baos.toString();
		}
		return null;
	}

	private Exception exception;

	public Exception getException() {
		if ("failure".equals(this.getStatus())) {
			return this.exception;
		}
		return null;
	}

	private class TalendException extends Exception {

		private static final long serialVersionUID = 1L;

		private java.util.Map<String, Object> globalMap = null;
		private Exception e = null;
		private String currentComponent = null;
		private String virtualComponentName = null;

		public void setVirtualComponentName(String virtualComponentName) {
			this.virtualComponentName = virtualComponentName;
		}

		private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
			this.currentComponent = errorComponent;
			this.globalMap = globalMap;
			this.e = e;
		}

		public Exception getException() {
			return this.e;
		}

		public String getCurrentComponent() {
			return this.currentComponent;
		}

		public String getExceptionCauseMessage(Exception e) {
			Throwable cause = e;
			String message = null;
			int i = 10;
			while (null != cause && 0 < i--) {
				message = cause.getMessage();
				if (null == message) {
					cause = cause.getCause();
				} else {
					break;
				}
			}
			if (null == message) {
				message = e.getClass().getName();
			}
			return message;
		}

		@Override
		public void printStackTrace() {
			if (!(e instanceof TalendException || e instanceof TDieException)) {
				if (virtualComponentName != null && currentComponent.indexOf(virtualComponentName + "_") == 0) {
					globalMap.put(virtualComponentName + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				}
				globalMap.put(currentComponent + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
			}
			if (!(e instanceof TDieException)) {
				if (e instanceof TalendException) {
					e.printStackTrace();
				} else {
					e.printStackTrace();
					e.printStackTrace(errorMessagePS);
					etl_tracks_v3.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(etl_tracks_v3.this, new Object[] { e, currentComponent, globalMap });
							break;
						}
					}

					if (!(e instanceof TDieException)) {
					}
				} catch (Exception e) {
					this.e.printStackTrace();
				}
			}
		}
	}

	public void tDBConnection_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBConnection_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBConnection_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBConnection_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_3_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_4_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBCommit_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBCommit_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_5_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row5_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBConnection_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBConnection_2_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBInput_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBCommit_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBConnection_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBConnection_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tDBConnection_1 begin ] start
				 */

				ok_Hash.put("tDBConnection_1", false);
				start_Hash.put("tDBConnection_1", System.currentTimeMillis());

				currentComponent = "tDBConnection_1";

				int tos_count_tDBConnection_1 = 0;

				String dbProperties_tDBConnection_1 = "";
				String url_tDBConnection_1 = "jdbc:postgresql://" + "" + ":" + "5432" + "/" + "spotify";

				if (dbProperties_tDBConnection_1 != null && !"".equals(dbProperties_tDBConnection_1.trim())) {
					url_tDBConnection_1 = url_tDBConnection_1 + "?" + dbProperties_tDBConnection_1;
				}
				String dbUser_tDBConnection_1 = "postgres";

				final String decryptedPassword_tDBConnection_1 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:8xs834I4Rg9NTjqkn0g8bvC3OxAluEP1ize1ubTF65QkzQ==");
				String dbPwd_tDBConnection_1 = decryptedPassword_tDBConnection_1;

				java.sql.Connection conn_tDBConnection_1 = null;

				java.util.Enumeration<java.sql.Driver> drivers_tDBConnection_1 = java.sql.DriverManager.getDrivers();
				java.util.Set<String> redShiftDriverNames_tDBConnection_1 = new java.util.HashSet<String>(
						java.util.Arrays.asList("com.amazon.redshift.jdbc.Driver", "com.amazon.redshift.jdbc41.Driver",
								"com.amazon.redshift.jdbc42.Driver"));
				while (drivers_tDBConnection_1.hasMoreElements()) {
					java.sql.Driver d_tDBConnection_1 = drivers_tDBConnection_1.nextElement();
					if (redShiftDriverNames_tDBConnection_1.contains(d_tDBConnection_1.getClass().getName())) {
						try {
							java.sql.DriverManager.deregisterDriver(d_tDBConnection_1);
							java.sql.DriverManager.registerDriver(d_tDBConnection_1);
						} catch (java.lang.Exception e_tDBConnection_1) {
							globalMap.put("tDBConnection_1_ERROR_MESSAGE", e_tDBConnection_1.getMessage());
							// do nothing
						}
					}
				}

				String sharedConnectionName_tDBConnection_1 = "project_spotify";
				conn_tDBConnection_1 = SharedDBConnection.getDBConnection("org.postgresql.Driver", url_tDBConnection_1,
						dbUser_tDBConnection_1, dbPwd_tDBConnection_1, sharedConnectionName_tDBConnection_1);
				globalMap.put("conn_tDBConnection_1", conn_tDBConnection_1);
				if (null != conn_tDBConnection_1) {

					conn_tDBConnection_1.setAutoCommit(false);
				}

				globalMap.put("schema_" + "tDBConnection_1", "spotify_data");

				/**
				 * [tDBConnection_1 begin ] stop
				 */

				/**
				 * [tDBConnection_1 main ] start
				 */

				currentComponent = "tDBConnection_1";

				tos_count_tDBConnection_1++;

				/**
				 * [tDBConnection_1 main ] stop
				 */

				/**
				 * [tDBConnection_1 process_data_begin ] start
				 */

				currentComponent = "tDBConnection_1";

				/**
				 * [tDBConnection_1 process_data_begin ] stop
				 */

				/**
				 * [tDBConnection_1 process_data_end ] start
				 */

				currentComponent = "tDBConnection_1";

				/**
				 * [tDBConnection_1 process_data_end ] stop
				 */

				/**
				 * [tDBConnection_1 end ] start
				 */

				currentComponent = "tDBConnection_1";

				ok_Hash.put("tDBConnection_1", true);
				end_Hash.put("tDBConnection_1", System.currentTimeMillis());

				tDBConnection_2Process(globalMap);

				/**
				 * [tDBConnection_1 end ] stop
				 */
			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			throw error;
		} finally {

			try {

				/**
				 * [tDBConnection_1 finally ] start
				 */

				currentComponent = "tDBConnection_1";

				/**
				 * [tDBConnection_1 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBConnection_1_SUBPROCESS_STATE", 1);
	}

	public void tDBConnection_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBConnection_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tDBConnection_2 begin ] start
				 */

				ok_Hash.put("tDBConnection_2", false);
				start_Hash.put("tDBConnection_2", System.currentTimeMillis());

				currentComponent = "tDBConnection_2";

				int tos_count_tDBConnection_2 = 0;

				String dbProperties_tDBConnection_2 = "";
				String url_tDBConnection_2 = "jdbc:postgresql://" + "" + ":" + "5432" + "/" + "spotify_datawarehouse";

				if (dbProperties_tDBConnection_2 != null && !"".equals(dbProperties_tDBConnection_2.trim())) {
					url_tDBConnection_2 = url_tDBConnection_2 + "?" + dbProperties_tDBConnection_2;
				}
				String dbUser_tDBConnection_2 = "postgres";

				final String decryptedPassword_tDBConnection_2 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:egwJxtd7i9DHurneyhmGAW7w7fJI1kuhq2tH9IiASwPeeA==");
				String dbPwd_tDBConnection_2 = decryptedPassword_tDBConnection_2;

				java.sql.Connection conn_tDBConnection_2 = null;

				java.util.Enumeration<java.sql.Driver> drivers_tDBConnection_2 = java.sql.DriverManager.getDrivers();
				java.util.Set<String> redShiftDriverNames_tDBConnection_2 = new java.util.HashSet<String>(
						java.util.Arrays.asList("com.amazon.redshift.jdbc.Driver", "com.amazon.redshift.jdbc41.Driver",
								"com.amazon.redshift.jdbc42.Driver"));
				while (drivers_tDBConnection_2.hasMoreElements()) {
					java.sql.Driver d_tDBConnection_2 = drivers_tDBConnection_2.nextElement();
					if (redShiftDriverNames_tDBConnection_2.contains(d_tDBConnection_2.getClass().getName())) {
						try {
							java.sql.DriverManager.deregisterDriver(d_tDBConnection_2);
							java.sql.DriverManager.registerDriver(d_tDBConnection_2);
						} catch (java.lang.Exception e_tDBConnection_2) {
							globalMap.put("tDBConnection_2_ERROR_MESSAGE", e_tDBConnection_2.getMessage());
							// do nothing
						}
					}
				}

				String sharedConnectionName_tDBConnection_2 = "target_spotify";
				conn_tDBConnection_2 = SharedDBConnection.getDBConnection("org.postgresql.Driver", url_tDBConnection_2,
						dbUser_tDBConnection_2, dbPwd_tDBConnection_2, sharedConnectionName_tDBConnection_2);
				globalMap.put("conn_tDBConnection_2", conn_tDBConnection_2);
				if (null != conn_tDBConnection_2) {

					conn_tDBConnection_2.setAutoCommit(false);
				}

				globalMap.put("schema_" + "tDBConnection_2", "spotify_dw");

				/**
				 * [tDBConnection_2 begin ] stop
				 */

				/**
				 * [tDBConnection_2 main ] start
				 */

				currentComponent = "tDBConnection_2";

				tos_count_tDBConnection_2++;

				/**
				 * [tDBConnection_2 main ] stop
				 */

				/**
				 * [tDBConnection_2 process_data_begin ] start
				 */

				currentComponent = "tDBConnection_2";

				/**
				 * [tDBConnection_2 process_data_begin ] stop
				 */

				/**
				 * [tDBConnection_2 process_data_end ] start
				 */

				currentComponent = "tDBConnection_2";

				/**
				 * [tDBConnection_2 process_data_end ] stop
				 */

				/**
				 * [tDBConnection_2 end ] start
				 */

				currentComponent = "tDBConnection_2";

				ok_Hash.put("tDBConnection_2", true);
				end_Hash.put("tDBConnection_2", System.currentTimeMillis());

				tDBInput_1Process(globalMap);

				/**
				 * [tDBConnection_2 end ] stop
				 */
			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			throw error;
		} finally {

			try {

				/**
				 * [tDBConnection_2 finally ] start
				 */

				currentComponent = "tDBConnection_2";

				/**
				 * [tDBConnection_2 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBConnection_2_SUBPROCESS_STATE", 1);
	}

	public static class foundStruct implements routines.system.IPersistableRow<foundStruct> {
		final static byte[] commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3 = new byte[0];
		static byte[] commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public String track_id;

		public String getTrack_id() {
			return this.track_id;
		}

		public String artist_id;

		public String getArtist_id() {
			return this.artist_id;
		}

		public String album_id;

		public String getAlbum_id() {
			return this.album_id;
		}

		public String audio_feature_id;

		public String getAudio_feature_id() {
			return this.audio_feature_id;
		}

		public Integer release_date_id;

		public Integer getRelease_date_id() {
			return this.release_date_id;
		}

		public String track_name;

		public String getTrack_name() {
			return this.track_name;
		}

		public Long track_total_duration;

		public Long getTrack_total_duration() {
			return this.track_total_duration;
		}

		public Integer track_popularity;

		public Integer getTrack_popularity() {
			return this.track_popularity;
		}

		public Integer explicit;

		public Integer getExplicit() {
			return this.explicit;
		}

		public Integer track_number;

		public Integer getTrack_number() {
			return this.track_number;
		}

		public Integer disc_number;

		public Integer getDisc_number() {
			return this.disc_number;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.track_id == null) ? 0 : this.track_id.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final foundStruct other = (foundStruct) obj;

			if (this.track_id == null) {
				if (other.track_id != null)
					return false;

			} else if (!this.track_id.equals(other.track_id))

				return false;

			return true;
		}

		public void copyDataTo(foundStruct other) {

			other.track_id = this.track_id;
			other.artist_id = this.artist_id;
			other.album_id = this.album_id;
			other.audio_feature_id = this.audio_feature_id;
			other.release_date_id = this.release_date_id;
			other.track_name = this.track_name;
			other.track_total_duration = this.track_total_duration;
			other.track_popularity = this.track_popularity;
			other.explicit = this.explicit;
			other.track_number = this.track_number;
			other.disc_number = this.disc_number;

		}

		public void copyKeysDataTo(foundStruct other) {

			other.track_id = this.track_id;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.track_id = readString(dis);

					this.artist_id = readString(dis);

					this.album_id = readString(dis);

					this.audio_feature_id = readString(dis);

					this.release_date_id = readInteger(dis);

					this.track_name = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.track_total_duration = null;
					} else {
						this.track_total_duration = dis.readLong();
					}

					this.track_popularity = readInteger(dis);

					this.explicit = readInteger(dis);

					this.track_number = readInteger(dis);

					this.disc_number = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.track_id = readString(dis);

					this.artist_id = readString(dis);

					this.album_id = readString(dis);

					this.audio_feature_id = readString(dis);

					this.release_date_id = readInteger(dis);

					this.track_name = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.track_total_duration = null;
					} else {
						this.track_total_duration = dis.readLong();
					}

					this.track_popularity = readInteger(dis);

					this.explicit = readInteger(dis);

					this.track_number = readInteger(dis);

					this.disc_number = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.track_id, dos);

				// String

				writeString(this.artist_id, dos);

				// String

				writeString(this.album_id, dos);

				// String

				writeString(this.audio_feature_id, dos);

				// Integer

				writeInteger(this.release_date_id, dos);

				// String

				writeString(this.track_name, dos);

				// Long

				if (this.track_total_duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.track_total_duration);
				}

				// Integer

				writeInteger(this.track_popularity, dos);

				// Integer

				writeInteger(this.explicit, dos);

				// Integer

				writeInteger(this.track_number, dos);

				// Integer

				writeInteger(this.disc_number, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.track_id, dos);

				// String

				writeString(this.artist_id, dos);

				// String

				writeString(this.album_id, dos);

				// String

				writeString(this.audio_feature_id, dos);

				// Integer

				writeInteger(this.release_date_id, dos);

				// String

				writeString(this.track_name, dos);

				// Long

				if (this.track_total_duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.track_total_duration);
				}

				// Integer

				writeInteger(this.track_popularity, dos);

				// Integer

				writeInteger(this.explicit, dos);

				// Integer

				writeInteger(this.track_number, dos);

				// Integer

				writeInteger(this.disc_number, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("track_id=" + track_id);
			sb.append(",artist_id=" + artist_id);
			sb.append(",album_id=" + album_id);
			sb.append(",audio_feature_id=" + audio_feature_id);
			sb.append(",release_date_id=" + String.valueOf(release_date_id));
			sb.append(",track_name=" + track_name);
			sb.append(",track_total_duration=" + String.valueOf(track_total_duration));
			sb.append(",track_popularity=" + String.valueOf(track_popularity));
			sb.append(",explicit=" + String.valueOf(explicit));
			sb.append(",track_number=" + String.valueOf(track_number));
			sb.append(",disc_number=" + String.valueOf(disc_number));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(foundStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.track_id, other.track_id);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class not_foundStruct implements routines.system.IPersistableRow<not_foundStruct> {
		final static byte[] commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3 = new byte[0];
		static byte[] commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public String track_id;

		public String getTrack_id() {
			return this.track_id;
		}

		public String artist_id;

		public String getArtist_id() {
			return this.artist_id;
		}

		public String album_id;

		public String getAlbum_id() {
			return this.album_id;
		}

		public String audio_feature_id;

		public String getAudio_feature_id() {
			return this.audio_feature_id;
		}

		public Integer release_date_id;

		public Integer getRelease_date_id() {
			return this.release_date_id;
		}

		public String track_name;

		public String getTrack_name() {
			return this.track_name;
		}

		public Long track_total_duration;

		public Long getTrack_total_duration() {
			return this.track_total_duration;
		}

		public Integer track_popularity;

		public Integer getTrack_popularity() {
			return this.track_popularity;
		}

		public Integer explicit;

		public Integer getExplicit() {
			return this.explicit;
		}

		public Integer track_number;

		public Integer getTrack_number() {
			return this.track_number;
		}

		public Integer disc_number;

		public Integer getDisc_number() {
			return this.disc_number;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.track_id == null) ? 0 : this.track_id.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final not_foundStruct other = (not_foundStruct) obj;

			if (this.track_id == null) {
				if (other.track_id != null)
					return false;

			} else if (!this.track_id.equals(other.track_id))

				return false;

			return true;
		}

		public void copyDataTo(not_foundStruct other) {

			other.track_id = this.track_id;
			other.artist_id = this.artist_id;
			other.album_id = this.album_id;
			other.audio_feature_id = this.audio_feature_id;
			other.release_date_id = this.release_date_id;
			other.track_name = this.track_name;
			other.track_total_duration = this.track_total_duration;
			other.track_popularity = this.track_popularity;
			other.explicit = this.explicit;
			other.track_number = this.track_number;
			other.disc_number = this.disc_number;

		}

		public void copyKeysDataTo(not_foundStruct other) {

			other.track_id = this.track_id;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.track_id = readString(dis);

					this.artist_id = readString(dis);

					this.album_id = readString(dis);

					this.audio_feature_id = readString(dis);

					this.release_date_id = readInteger(dis);

					this.track_name = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.track_total_duration = null;
					} else {
						this.track_total_duration = dis.readLong();
					}

					this.track_popularity = readInteger(dis);

					this.explicit = readInteger(dis);

					this.track_number = readInteger(dis);

					this.disc_number = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.track_id = readString(dis);

					this.artist_id = readString(dis);

					this.album_id = readString(dis);

					this.audio_feature_id = readString(dis);

					this.release_date_id = readInteger(dis);

					this.track_name = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.track_total_duration = null;
					} else {
						this.track_total_duration = dis.readLong();
					}

					this.track_popularity = readInteger(dis);

					this.explicit = readInteger(dis);

					this.track_number = readInteger(dis);

					this.disc_number = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.track_id, dos);

				// String

				writeString(this.artist_id, dos);

				// String

				writeString(this.album_id, dos);

				// String

				writeString(this.audio_feature_id, dos);

				// Integer

				writeInteger(this.release_date_id, dos);

				// String

				writeString(this.track_name, dos);

				// Long

				if (this.track_total_duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.track_total_duration);
				}

				// Integer

				writeInteger(this.track_popularity, dos);

				// Integer

				writeInteger(this.explicit, dos);

				// Integer

				writeInteger(this.track_number, dos);

				// Integer

				writeInteger(this.disc_number, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.track_id, dos);

				// String

				writeString(this.artist_id, dos);

				// String

				writeString(this.album_id, dos);

				// String

				writeString(this.audio_feature_id, dos);

				// Integer

				writeInteger(this.release_date_id, dos);

				// String

				writeString(this.track_name, dos);

				// Long

				if (this.track_total_duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.track_total_duration);
				}

				// Integer

				writeInteger(this.track_popularity, dos);

				// Integer

				writeInteger(this.explicit, dos);

				// Integer

				writeInteger(this.track_number, dos);

				// Integer

				writeInteger(this.disc_number, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("track_id=" + track_id);
			sb.append(",artist_id=" + artist_id);
			sb.append(",album_id=" + album_id);
			sb.append(",audio_feature_id=" + audio_feature_id);
			sb.append(",release_date_id=" + String.valueOf(release_date_id));
			sb.append(",track_name=" + track_name);
			sb.append(",track_total_duration=" + String.valueOf(track_total_duration));
			sb.append(",track_popularity=" + String.valueOf(track_popularity));
			sb.append(",explicit=" + String.valueOf(explicit));
			sb.append(",track_number=" + String.valueOf(track_number));
			sb.append(",disc_number=" + String.valueOf(disc_number));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(not_foundStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.track_id, other.track_id);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class cp3Struct implements routines.system.IPersistableRow<cp3Struct> {
		final static byte[] commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3 = new byte[0];
		static byte[] commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[0];

		public String track_id;

		public String getTrack_id() {
			return this.track_id;
		}

		public String artist_id;

		public String getArtist_id() {
			return this.artist_id;
		}

		public String album_id;

		public String getAlbum_id() {
			return this.album_id;
		}

		public Integer release_date_id;

		public Integer getRelease_date_id() {
			return this.release_date_id;
		}

		public String audio_feature_id;

		public String getAudio_feature_id() {
			return this.audio_feature_id;
		}

		public Long duration;

		public Long getDuration() {
			return this.duration;
		}

		public Integer explicit;

		public Integer getExplicit() {
			return this.explicit;
		}

		public Integer disc_number;

		public Integer getDisc_number() {
			return this.disc_number;
		}

		public String name;

		public String getName() {
			return this.name;
		}

		public Integer track_number;

		public Integer getTrack_number() {
			return this.track_number;
		}

		public Integer popularity;

		public Integer getPopularity() {
			return this.popularity;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.track_id = readString(dis);

					this.artist_id = readString(dis);

					this.album_id = readString(dis);

					this.release_date_id = readInteger(dis);

					this.audio_feature_id = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

					this.explicit = readInteger(dis);

					this.disc_number = readInteger(dis);

					this.name = readString(dis);

					this.track_number = readInteger(dis);

					this.popularity = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.track_id = readString(dis);

					this.artist_id = readString(dis);

					this.album_id = readString(dis);

					this.release_date_id = readInteger(dis);

					this.audio_feature_id = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

					this.explicit = readInteger(dis);

					this.disc_number = readInteger(dis);

					this.name = readString(dis);

					this.track_number = readInteger(dis);

					this.popularity = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.track_id, dos);

				// String

				writeString(this.artist_id, dos);

				// String

				writeString(this.album_id, dos);

				// Integer

				writeInteger(this.release_date_id, dos);

				// String

				writeString(this.audio_feature_id, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

				// Integer

				writeInteger(this.explicit, dos);

				// Integer

				writeInteger(this.disc_number, dos);

				// String

				writeString(this.name, dos);

				// Integer

				writeInteger(this.track_number, dos);

				// Integer

				writeInteger(this.popularity, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.track_id, dos);

				// String

				writeString(this.artist_id, dos);

				// String

				writeString(this.album_id, dos);

				// Integer

				writeInteger(this.release_date_id, dos);

				// String

				writeString(this.audio_feature_id, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

				// Integer

				writeInteger(this.explicit, dos);

				// Integer

				writeInteger(this.disc_number, dos);

				// String

				writeString(this.name, dos);

				// Integer

				writeInteger(this.track_number, dos);

				// Integer

				writeInteger(this.popularity, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("track_id=" + track_id);
			sb.append(",artist_id=" + artist_id);
			sb.append(",album_id=" + album_id);
			sb.append(",release_date_id=" + String.valueOf(release_date_id));
			sb.append(",audio_feature_id=" + audio_feature_id);
			sb.append(",duration=" + String.valueOf(duration));
			sb.append(",explicit=" + String.valueOf(explicit));
			sb.append(",disc_number=" + String.valueOf(disc_number));
			sb.append(",name=" + name);
			sb.append(",track_number=" + String.valueOf(track_number));
			sb.append(",popularity=" + String.valueOf(popularity));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(cp3Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class cp2Struct implements routines.system.IPersistableRow<cp2Struct> {
		final static byte[] commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3 = new byte[0];
		static byte[] commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[0];

		public String track_id;

		public String getTrack_id() {
			return this.track_id;
		}

		public String artist_id;

		public String getArtist_id() {
			return this.artist_id;
		}

		public String album_id;

		public String getAlbum_id() {
			return this.album_id;
		}

		public Integer disc_number;

		public Integer getDisc_number() {
			return this.disc_number;
		}

		public Long duration;

		public Long getDuration() {
			return this.duration;
		}

		public Integer explicit;

		public Integer getExplicit() {
			return this.explicit;
		}

		public String audio_feature_id;

		public String getAudio_feature_id() {
			return this.audio_feature_id;
		}

		public String name;

		public String getName() {
			return this.name;
		}

		public Integer track_number;

		public Integer getTrack_number() {
			return this.track_number;
		}

		public Integer popularity;

		public Integer getPopularity() {
			return this.popularity;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.track_id = readString(dis);

					this.artist_id = readString(dis);

					this.album_id = readString(dis);

					this.disc_number = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

					this.explicit = readInteger(dis);

					this.audio_feature_id = readString(dis);

					this.name = readString(dis);

					this.track_number = readInteger(dis);

					this.popularity = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.track_id = readString(dis);

					this.artist_id = readString(dis);

					this.album_id = readString(dis);

					this.disc_number = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

					this.explicit = readInteger(dis);

					this.audio_feature_id = readString(dis);

					this.name = readString(dis);

					this.track_number = readInteger(dis);

					this.popularity = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.track_id, dos);

				// String

				writeString(this.artist_id, dos);

				// String

				writeString(this.album_id, dos);

				// Integer

				writeInteger(this.disc_number, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

				// Integer

				writeInteger(this.explicit, dos);

				// String

				writeString(this.audio_feature_id, dos);

				// String

				writeString(this.name, dos);

				// Integer

				writeInteger(this.track_number, dos);

				// Integer

				writeInteger(this.popularity, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.track_id, dos);

				// String

				writeString(this.artist_id, dos);

				// String

				writeString(this.album_id, dos);

				// Integer

				writeInteger(this.disc_number, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

				// Integer

				writeInteger(this.explicit, dos);

				// String

				writeString(this.audio_feature_id, dos);

				// String

				writeString(this.name, dos);

				// Integer

				writeInteger(this.track_number, dos);

				// Integer

				writeInteger(this.popularity, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("track_id=" + track_id);
			sb.append(",artist_id=" + artist_id);
			sb.append(",album_id=" + album_id);
			sb.append(",disc_number=" + String.valueOf(disc_number));
			sb.append(",duration=" + String.valueOf(duration));
			sb.append(",explicit=" + String.valueOf(explicit));
			sb.append(",audio_feature_id=" + audio_feature_id);
			sb.append(",name=" + name);
			sb.append(",track_number=" + String.valueOf(track_number));
			sb.append(",popularity=" + String.valueOf(popularity));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(cp2Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class cp1Struct implements routines.system.IPersistableRow<cp1Struct> {
		final static byte[] commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3 = new byte[0];
		static byte[] commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[0];

		public String track_id;

		public String getTrack_id() {
			return this.track_id;
		}

		public String artist_id;

		public String getArtist_id() {
			return this.artist_id;
		}

		public Integer disc_number;

		public Integer getDisc_number() {
			return this.disc_number;
		}

		public Integer disc_number_1;

		public Integer getDisc_number_1() {
			return this.disc_number_1;
		}

		public Long duration;

		public Long getDuration() {
			return this.duration;
		}

		public Integer explicit;

		public Integer getExplicit() {
			return this.explicit;
		}

		public String audio_feature_id;

		public String getAudio_feature_id() {
			return this.audio_feature_id;
		}

		public String name;

		public String getName() {
			return this.name;
		}

		public Integer track_number;

		public Integer getTrack_number() {
			return this.track_number;
		}

		public Integer popularity;

		public Integer getPopularity() {
			return this.popularity;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.track_id = readString(dis);

					this.artist_id = readString(dis);

					this.disc_number = readInteger(dis);

					this.disc_number_1 = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

					this.explicit = readInteger(dis);

					this.audio_feature_id = readString(dis);

					this.name = readString(dis);

					this.track_number = readInteger(dis);

					this.popularity = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.track_id = readString(dis);

					this.artist_id = readString(dis);

					this.disc_number = readInteger(dis);

					this.disc_number_1 = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

					this.explicit = readInteger(dis);

					this.audio_feature_id = readString(dis);

					this.name = readString(dis);

					this.track_number = readInteger(dis);

					this.popularity = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.track_id, dos);

				// String

				writeString(this.artist_id, dos);

				// Integer

				writeInteger(this.disc_number, dos);

				// Integer

				writeInteger(this.disc_number_1, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

				// Integer

				writeInteger(this.explicit, dos);

				// String

				writeString(this.audio_feature_id, dos);

				// String

				writeString(this.name, dos);

				// Integer

				writeInteger(this.track_number, dos);

				// Integer

				writeInteger(this.popularity, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.track_id, dos);

				// String

				writeString(this.artist_id, dos);

				// Integer

				writeInteger(this.disc_number, dos);

				// Integer

				writeInteger(this.disc_number_1, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

				// Integer

				writeInteger(this.explicit, dos);

				// String

				writeString(this.audio_feature_id, dos);

				// String

				writeString(this.name, dos);

				// Integer

				writeInteger(this.track_number, dos);

				// Integer

				writeInteger(this.popularity, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("track_id=" + track_id);
			sb.append(",artist_id=" + artist_id);
			sb.append(",disc_number=" + String.valueOf(disc_number));
			sb.append(",disc_number_1=" + String.valueOf(disc_number_1));
			sb.append(",duration=" + String.valueOf(duration));
			sb.append(",explicit=" + String.valueOf(explicit));
			sb.append(",audio_feature_id=" + audio_feature_id);
			sb.append(",name=" + name);
			sb.append(",track_number=" + String.valueOf(track_number));
			sb.append(",popularity=" + String.valueOf(popularity));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(cp1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
		final static byte[] commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3 = new byte[0];
		static byte[] commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[0];

		public String id;

		public String getId() {
			return this.id;
		}

		public Integer disc_number;

		public Integer getDisc_number() {
			return this.disc_number;
		}

		public Long duration;

		public Long getDuration() {
			return this.duration;
		}

		public Integer explicit;

		public Integer getExplicit() {
			return this.explicit;
		}

		public String audio_feature_id;

		public String getAudio_feature_id() {
			return this.audio_feature_id;
		}

		public String name;

		public String getName() {
			return this.name;
		}

		public String preview_url;

		public String getPreview_url() {
			return this.preview_url;
		}

		public Integer track_number;

		public Integer getTrack_number() {
			return this.track_number;
		}

		public Integer popularity;

		public Integer getPopularity() {
			return this.popularity;
		}

		public Integer is_playable;

		public Integer getIs_playable() {
			return this.is_playable;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.id = readString(dis);

					this.disc_number = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

					this.explicit = readInteger(dis);

					this.audio_feature_id = readString(dis);

					this.name = readString(dis);

					this.preview_url = readString(dis);

					this.track_number = readInteger(dis);

					this.popularity = readInteger(dis);

					this.is_playable = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.id = readString(dis);

					this.disc_number = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

					this.explicit = readInteger(dis);

					this.audio_feature_id = readString(dis);

					this.name = readString(dis);

					this.preview_url = readString(dis);

					this.track_number = readInteger(dis);

					this.popularity = readInteger(dis);

					this.is_playable = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.id, dos);

				// Integer

				writeInteger(this.disc_number, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

				// Integer

				writeInteger(this.explicit, dos);

				// String

				writeString(this.audio_feature_id, dos);

				// String

				writeString(this.name, dos);

				// String

				writeString(this.preview_url, dos);

				// Integer

				writeInteger(this.track_number, dos);

				// Integer

				writeInteger(this.popularity, dos);

				// Integer

				writeInteger(this.is_playable, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.id, dos);

				// Integer

				writeInteger(this.disc_number, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

				// Integer

				writeInteger(this.explicit, dos);

				// String

				writeString(this.audio_feature_id, dos);

				// String

				writeString(this.name, dos);

				// String

				writeString(this.preview_url, dos);

				// Integer

				writeInteger(this.track_number, dos);

				// Integer

				writeInteger(this.popularity, dos);

				// Integer

				writeInteger(this.is_playable, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id=" + id);
			sb.append(",disc_number=" + String.valueOf(disc_number));
			sb.append(",duration=" + String.valueOf(duration));
			sb.append(",explicit=" + String.valueOf(explicit));
			sb.append(",audio_feature_id=" + audio_feature_id);
			sb.append(",name=" + name);
			sb.append(",preview_url=" + preview_url);
			sb.append(",track_number=" + String.valueOf(track_number));
			sb.append(",popularity=" + String.valueOf(popularity));
			sb.append(",is_playable=" + String.valueOf(is_playable));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class after_tDBInput_1Struct implements routines.system.IPersistableRow<after_tDBInput_1Struct> {
		final static byte[] commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3 = new byte[0];
		static byte[] commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public String id;

		public String getId() {
			return this.id;
		}

		public Integer disc_number;

		public Integer getDisc_number() {
			return this.disc_number;
		}

		public Long duration;

		public Long getDuration() {
			return this.duration;
		}

		public Integer explicit;

		public Integer getExplicit() {
			return this.explicit;
		}

		public String audio_feature_id;

		public String getAudio_feature_id() {
			return this.audio_feature_id;
		}

		public String name;

		public String getName() {
			return this.name;
		}

		public String preview_url;

		public String getPreview_url() {
			return this.preview_url;
		}

		public Integer track_number;

		public Integer getTrack_number() {
			return this.track_number;
		}

		public Integer popularity;

		public Integer getPopularity() {
			return this.popularity;
		}

		public Integer is_playable;

		public Integer getIs_playable() {
			return this.is_playable;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final after_tDBInput_1Struct other = (after_tDBInput_1Struct) obj;

			if (this.id == null) {
				if (other.id != null)
					return false;

			} else if (!this.id.equals(other.id))

				return false;

			return true;
		}

		public void copyDataTo(after_tDBInput_1Struct other) {

			other.id = this.id;
			other.disc_number = this.disc_number;
			other.duration = this.duration;
			other.explicit = this.explicit;
			other.audio_feature_id = this.audio_feature_id;
			other.name = this.name;
			other.preview_url = this.preview_url;
			other.track_number = this.track_number;
			other.popularity = this.popularity;
			other.is_playable = this.is_playable;

		}

		public void copyKeysDataTo(after_tDBInput_1Struct other) {

			other.id = this.id;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.id = readString(dis);

					this.disc_number = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

					this.explicit = readInteger(dis);

					this.audio_feature_id = readString(dis);

					this.name = readString(dis);

					this.preview_url = readString(dis);

					this.track_number = readInteger(dis);

					this.popularity = readInteger(dis);

					this.is_playable = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.id = readString(dis);

					this.disc_number = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

					this.explicit = readInteger(dis);

					this.audio_feature_id = readString(dis);

					this.name = readString(dis);

					this.preview_url = readString(dis);

					this.track_number = readInteger(dis);

					this.popularity = readInteger(dis);

					this.is_playable = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.id, dos);

				// Integer

				writeInteger(this.disc_number, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

				// Integer

				writeInteger(this.explicit, dos);

				// String

				writeString(this.audio_feature_id, dos);

				// String

				writeString(this.name, dos);

				// String

				writeString(this.preview_url, dos);

				// Integer

				writeInteger(this.track_number, dos);

				// Integer

				writeInteger(this.popularity, dos);

				// Integer

				writeInteger(this.is_playable, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.id, dos);

				// Integer

				writeInteger(this.disc_number, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

				// Integer

				writeInteger(this.explicit, dos);

				// String

				writeString(this.audio_feature_id, dos);

				// String

				writeString(this.name, dos);

				// String

				writeString(this.preview_url, dos);

				// Integer

				writeInteger(this.track_number, dos);

				// Integer

				writeInteger(this.popularity, dos);

				// Integer

				writeInteger(this.is_playable, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id=" + id);
			sb.append(",disc_number=" + String.valueOf(disc_number));
			sb.append(",duration=" + String.valueOf(duration));
			sb.append(",explicit=" + String.valueOf(explicit));
			sb.append(",audio_feature_id=" + audio_feature_id);
			sb.append(",name=" + name);
			sb.append(",preview_url=" + preview_url);
			sb.append(",track_number=" + String.valueOf(track_number));
			sb.append(",popularity=" + String.valueOf(popularity));
			sb.append(",is_playable=" + String.valueOf(is_playable));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(after_tDBInput_1Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.id, other.id);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				tDBInput_2Process(globalMap);
				tDBInput_3Process(globalMap);
				tDBInput_4Process(globalMap);
				tDBInput_5Process(globalMap);

				row1Struct row1 = new row1Struct();
				cp1Struct cp1 = new cp1Struct();
				cp2Struct cp2 = new cp2Struct();
				cp3Struct cp3 = new cp3Struct();
				foundStruct found = new foundStruct();
				not_foundStruct not_found = new not_foundStruct();

				/**
				 * [tDBOutput_1 begin ] start
				 */

				ok_Hash.put("tDBOutput_1", false);
				start_Hash.put("tDBOutput_1", System.currentTimeMillis());

				currentComponent = "tDBOutput_1";

				int tos_count_tDBOutput_1 = 0;

				String dbschema_tDBOutput_1 = null;
				dbschema_tDBOutput_1 = (String) globalMap.get("schema_" + "tDBConnection_2");

				String tableName_tDBOutput_1 = null;
				if (dbschema_tDBOutput_1 == null || dbschema_tDBOutput_1.trim().length() == 0) {
					tableName_tDBOutput_1 = ("tracks_info");
				} else {
					tableName_tDBOutput_1 = dbschema_tDBOutput_1 + "\".\"" + ("tracks_info");
				}

				int nb_line_tDBOutput_1 = 0;
				int nb_line_update_tDBOutput_1 = 0;
				int nb_line_inserted_tDBOutput_1 = 0;
				int nb_line_deleted_tDBOutput_1 = 0;
				int nb_line_rejected_tDBOutput_1 = 0;

				int deletedCount_tDBOutput_1 = 0;
				int updatedCount_tDBOutput_1 = 0;
				int insertedCount_tDBOutput_1 = 0;
				int rowsToCommitCount_tDBOutput_1 = 0;
				int rejectedCount_tDBOutput_1 = 0;

				boolean whetherReject_tDBOutput_1 = false;

				java.sql.Connection conn_tDBOutput_1 = null;
				String dbUser_tDBOutput_1 = null;

				conn_tDBOutput_1 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				int batchSize_tDBOutput_1 = 10000;
				int batchSizeCounter_tDBOutput_1 = 0;

				int count_tDBOutput_1 = 0;
				String insert_tDBOutput_1 = "INSERT INTO \"" + tableName_tDBOutput_1
						+ "\" (\"track_id\",\"artist_id\",\"album_id\",\"audio_feature_id\",\"release_date_id\",\"track_name\",\"track_total_duration\",\"track_popularity\",\"explicit\",\"track_number\",\"disc_number\") VALUES (?,?,?,?,?,?,?,?,?,?,?)";

				java.sql.PreparedStatement pstmt_tDBOutput_1 = conn_tDBOutput_1.prepareStatement(insert_tDBOutput_1);
				resourceMap.put("pstmt_tDBOutput_1", pstmt_tDBOutput_1);

				/**
				 * [tDBOutput_1 begin ] stop
				 */

				/**
				 * [tDBOutput_2 begin ] start
				 */

				ok_Hash.put("tDBOutput_2", false);
				start_Hash.put("tDBOutput_2", System.currentTimeMillis());

				currentComponent = "tDBOutput_2";

				int tos_count_tDBOutput_2 = 0;

				String dbschema_tDBOutput_2 = null;
				dbschema_tDBOutput_2 = (String) globalMap.get("schema_" + "tDBConnection_2");

				String tableName_tDBOutput_2 = null;
				if (dbschema_tDBOutput_2 == null || dbschema_tDBOutput_2.trim().length() == 0) {
					tableName_tDBOutput_2 = ("tracks_info");
				} else {
					tableName_tDBOutput_2 = dbschema_tDBOutput_2 + "\".\"" + ("tracks_info");
				}

				int nb_line_tDBOutput_2 = 0;
				int nb_line_update_tDBOutput_2 = 0;
				int nb_line_inserted_tDBOutput_2 = 0;
				int nb_line_deleted_tDBOutput_2 = 0;
				int nb_line_rejected_tDBOutput_2 = 0;

				int deletedCount_tDBOutput_2 = 0;
				int updatedCount_tDBOutput_2 = 0;
				int insertedCount_tDBOutput_2 = 0;
				int rowsToCommitCount_tDBOutput_2 = 0;
				int rejectedCount_tDBOutput_2 = 0;

				boolean whetherReject_tDBOutput_2 = false;

				java.sql.Connection conn_tDBOutput_2 = null;
				String dbUser_tDBOutput_2 = null;

				conn_tDBOutput_2 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				int batchSize_tDBOutput_2 = 10000;
				int batchSizeCounter_tDBOutput_2 = 0;

				int count_tDBOutput_2 = 0;
				String insert_tDBOutput_2 = "INSERT INTO \"" + tableName_tDBOutput_2
						+ "\" (\"track_id\",\"artist_id\",\"album_id\",\"audio_feature_id\",\"release_date_id\",\"track_name\",\"track_total_duration\",\"track_popularity\",\"explicit\",\"track_number\",\"disc_number\") VALUES (?,?,?,?,?,?,?,?,?,?,?)";

				java.sql.PreparedStatement pstmt_tDBOutput_2 = conn_tDBOutput_2.prepareStatement(insert_tDBOutput_2);
				resourceMap.put("pstmt_tDBOutput_2", pstmt_tDBOutput_2);

				/**
				 * [tDBOutput_2 begin ] stop
				 */

				/**
				 * [tMap_4 begin ] start
				 */

				ok_Hash.put("tMap_4", false);
				start_Hash.put("tMap_4", System.currentTimeMillis());

				currentComponent = "tMap_4";

				int tos_count_tMap_4 = 0;

// ###############################
// # Lookup's keys initialization

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row5Struct> tHash_Lookup_row5 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row5Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row5Struct>) globalMap
						.get("tHash_Lookup_row5"));

				row5Struct row5HashKey = new row5Struct();
				row5Struct row5Default = new row5Struct();
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_4__Struct {
				}
				Var__tMap_4__Struct Var__tMap_4 = new Var__tMap_4__Struct();
// ###############################

// ###############################
// # Outputs initialization
				foundStruct found_tmp = new foundStruct();
				not_foundStruct not_found_tmp = new not_foundStruct();
// ###############################

				/**
				 * [tMap_4 begin ] stop
				 */

				/**
				 * [tMap_3 begin ] start
				 */

				ok_Hash.put("tMap_3", false);
				start_Hash.put("tMap_3", System.currentTimeMillis());

				currentComponent = "tMap_3";

				int tos_count_tMap_3 = 0;

// ###############################
// # Lookup's keys initialization

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row4Struct> tHash_Lookup_row4 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row4Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row4Struct>) globalMap
						.get("tHash_Lookup_row4"));

				row4Struct row4HashKey = new row4Struct();
				row4Struct row4Default = new row4Struct();
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_3__Struct {
				}
				Var__tMap_3__Struct Var__tMap_3 = new Var__tMap_3__Struct();
// ###############################

// ###############################
// # Outputs initialization
				cp3Struct cp3_tmp = new cp3Struct();
// ###############################

				/**
				 * [tMap_3 begin ] stop
				 */

				/**
				 * [tMap_2 begin ] start
				 */

				ok_Hash.put("tMap_2", false);
				start_Hash.put("tMap_2", System.currentTimeMillis());

				currentComponent = "tMap_2";

				int tos_count_tMap_2 = 0;

// ###############################
// # Lookup's keys initialization

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct> tHash_Lookup_row3 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct>) globalMap
						.get("tHash_Lookup_row3"));

				row3Struct row3HashKey = new row3Struct();
				row3Struct row3Default = new row3Struct();
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_2__Struct {
				}
				Var__tMap_2__Struct Var__tMap_2 = new Var__tMap_2__Struct();
// ###############################

// ###############################
// # Outputs initialization
				cp2Struct cp2_tmp = new cp2Struct();
// ###############################

				/**
				 * [tMap_2 begin ] stop
				 */

				/**
				 * [tMap_1 begin ] start
				 */

				ok_Hash.put("tMap_1", false);
				start_Hash.put("tMap_1", System.currentTimeMillis());

				currentComponent = "tMap_1";

				int tos_count_tMap_1 = 0;

// ###############################
// # Lookup's keys initialization

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct> tHash_Lookup_row2 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct>) globalMap
						.get("tHash_Lookup_row2"));

				row2Struct row2HashKey = new row2Struct();
				row2Struct row2Default = new row2Struct();
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_1__Struct {
				}
				Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
// ###############################

// ###############################
// # Outputs initialization
				cp1Struct cp1_tmp = new cp1Struct();
// ###############################

				/**
				 * [tMap_1 begin ] stop
				 */

				/**
				 * [tDBInput_1 begin ] start
				 */

				ok_Hash.put("tDBInput_1", false);
				start_Hash.put("tDBInput_1", System.currentTimeMillis());

				currentComponent = "tDBInput_1";

				int tos_count_tDBInput_1 = 0;

				int nb_line_tDBInput_1 = 0;
				java.sql.Connection conn_tDBInput_1 = null;
				conn_tDBInput_1 = (java.sql.Connection) globalMap.get("conn_tDBConnection_1");

				java.sql.Statement stmt_tDBInput_1 = conn_tDBInput_1.createStatement();

				String dbquery_tDBInput_1 = "SELECT \n  \"spotify_data\".\"tracks\".\"id\", \n  \"spotify_data\".\"tracks\".\"disc_number\", \n  \"spotify_data\".\"tra"
						+ "cks\".\"duration\", \n  \"spotify_data\".\"tracks\".\"explicit\", \n  \"spotify_data\".\"tracks\".\"audio_feature_id\", \n "
						+ " \"spotify_data\".\"tracks\".\"name\", \n  \"spotify_data\".\"tracks\".\"preview_url\", \n  \"spotify_data\".\"tracks\".\""
						+ "track_number\", \n  \"spotify_data\".\"tracks\".\"popularity\", \n  \"spotify_data\".\"tracks\".\"is_playable\"\nFROM \"spo"
						+ "tify_data\".\"tracks\"";

				globalMap.put("tDBInput_1_QUERY", dbquery_tDBInput_1);
				java.sql.ResultSet rs_tDBInput_1 = null;

				try {
					rs_tDBInput_1 = stmt_tDBInput_1.executeQuery(dbquery_tDBInput_1);
					java.sql.ResultSetMetaData rsmd_tDBInput_1 = rs_tDBInput_1.getMetaData();
					int colQtyInRs_tDBInput_1 = rsmd_tDBInput_1.getColumnCount();

					String tmpContent_tDBInput_1 = null;

					while (rs_tDBInput_1.next()) {
						nb_line_tDBInput_1++;

						if (colQtyInRs_tDBInput_1 < 1) {
							row1.id = null;
						} else {

							row1.id = routines.system.JDBCUtil.getString(rs_tDBInput_1, 1, false);
						}
						if (colQtyInRs_tDBInput_1 < 2) {
							row1.disc_number = null;
						} else {

							row1.disc_number = rs_tDBInput_1.getInt(2);
							if (rs_tDBInput_1.wasNull()) {
								row1.disc_number = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 3) {
							row1.duration = null;
						} else {

							row1.duration = rs_tDBInput_1.getLong(3);
							if (rs_tDBInput_1.wasNull()) {
								row1.duration = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 4) {
							row1.explicit = null;
						} else {

							row1.explicit = rs_tDBInput_1.getInt(4);
							if (rs_tDBInput_1.wasNull()) {
								row1.explicit = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 5) {
							row1.audio_feature_id = null;
						} else {

							row1.audio_feature_id = routines.system.JDBCUtil.getString(rs_tDBInput_1, 5, false);
						}
						if (colQtyInRs_tDBInput_1 < 6) {
							row1.name = null;
						} else {

							row1.name = routines.system.JDBCUtil.getString(rs_tDBInput_1, 6, false);
						}
						if (colQtyInRs_tDBInput_1 < 7) {
							row1.preview_url = null;
						} else {

							row1.preview_url = routines.system.JDBCUtil.getString(rs_tDBInput_1, 7, false);
						}
						if (colQtyInRs_tDBInput_1 < 8) {
							row1.track_number = null;
						} else {

							row1.track_number = rs_tDBInput_1.getInt(8);
							if (rs_tDBInput_1.wasNull()) {
								row1.track_number = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 9) {
							row1.popularity = null;
						} else {

							row1.popularity = rs_tDBInput_1.getInt(9);
							if (rs_tDBInput_1.wasNull()) {
								row1.popularity = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 10) {
							row1.is_playable = null;
						} else {

							row1.is_playable = rs_tDBInput_1.getInt(10);
							if (rs_tDBInput_1.wasNull()) {
								row1.is_playable = null;
							}
						}

						/**
						 * [tDBInput_1 begin ] stop
						 */

						/**
						 * [tDBInput_1 main ] start
						 */

						currentComponent = "tDBInput_1";

						tos_count_tDBInput_1++;

						/**
						 * [tDBInput_1 main ] stop
						 */

						/**
						 * [tDBInput_1 process_data_begin ] start
						 */

						currentComponent = "tDBInput_1";

						/**
						 * [tDBInput_1 process_data_begin ] stop
						 */

						/**
						 * [tMap_1 main ] start
						 */

						currentComponent = "tMap_1";

						boolean hasCasePrimitiveKeyWithNull_tMap_1 = false;

						// ###############################
						// # Input tables (lookups)
						boolean rejectedInnerJoin_tMap_1 = false;
						boolean mainRowRejected_tMap_1 = false;

						///////////////////////////////////////////////
						// Starting Lookup Table "row2"
						///////////////////////////////////////////////

						boolean forceLooprow2 = false;

						row2Struct row2ObjectFromLookup = null;

						if (!rejectedInnerJoin_tMap_1) { // G_TM_M_020

							hasCasePrimitiveKeyWithNull_tMap_1 = false;

							row2HashKey.track_id = row1.id;

							row2HashKey.hashCodeDirty = true;

							tHash_Lookup_row2.lookup(row2HashKey);

							if (!tHash_Lookup_row2.hasNext()) { // G_TM_M_090

								rejectedInnerJoin_tMap_1 = true;

							} // G_TM_M_090

						} // G_TM_M_020

						if (tHash_Lookup_row2 != null && tHash_Lookup_row2.getCount(row2HashKey) > 1) { // G 071

							// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row2'
							// and it contains more one result from keys : row2.track_id = '" +
							// row2HashKey.track_id + "'");
						} // G 071

						row2Struct row2 = null;

						row2Struct fromLookup_row2 = null;
						row2 = row2Default;

						if (tHash_Lookup_row2 != null && tHash_Lookup_row2.hasNext()) { // G 099

							fromLookup_row2 = tHash_Lookup_row2.next();

						} // G 099

						if (fromLookup_row2 != null) {
							row2 = fromLookup_row2;
						}

						// ###############################
						{ // start of Var scope

							// ###############################
							// # Vars tables

							Var__tMap_1__Struct Var = Var__tMap_1;// ###############################
							// ###############################
							// # Output tables

							cp1 = null;

							if (!rejectedInnerJoin_tMap_1) {

// # Output table : 'cp1'
								cp1_tmp.track_id = row2.track_id;
								cp1_tmp.artist_id = row2.artist_id;
								cp1_tmp.disc_number = row1.disc_number;
								cp1_tmp.disc_number_1 = null;
								cp1_tmp.duration = row1.duration;
								cp1_tmp.explicit = row1.explicit;
								cp1_tmp.audio_feature_id = row1.audio_feature_id;
								cp1_tmp.name = row1.name;
								cp1_tmp.track_number = row1.track_number;
								cp1_tmp.popularity = row1.popularity;
								cp1 = cp1_tmp;
							} // closing inner join bracket (2)
// ###############################

						} // end of Var scope

						rejectedInnerJoin_tMap_1 = false;

						tos_count_tMap_1++;

						/**
						 * [tMap_1 main ] stop
						 */

						/**
						 * [tMap_1 process_data_begin ] start
						 */

						currentComponent = "tMap_1";

						/**
						 * [tMap_1 process_data_begin ] stop
						 */
// Start of branch "cp1"
						if (cp1 != null) {

							/**
							 * [tMap_2 main ] start
							 */

							currentComponent = "tMap_2";

							boolean hasCasePrimitiveKeyWithNull_tMap_2 = false;

							// ###############################
							// # Input tables (lookups)
							boolean rejectedInnerJoin_tMap_2 = false;
							boolean mainRowRejected_tMap_2 = false;

							///////////////////////////////////////////////
							// Starting Lookup Table "row3"
							///////////////////////////////////////////////

							boolean forceLooprow3 = false;

							row3Struct row3ObjectFromLookup = null;

							if (!rejectedInnerJoin_tMap_2) { // G_TM_M_020

								hasCasePrimitiveKeyWithNull_tMap_2 = false;

								row3HashKey.track_id = cp1.track_id;

								row3HashKey.hashCodeDirty = true;

								tHash_Lookup_row3.lookup(row3HashKey);

								if (!tHash_Lookup_row3.hasNext()) { // G_TM_M_090

									rejectedInnerJoin_tMap_2 = true;

								} // G_TM_M_090

							} // G_TM_M_020

							if (tHash_Lookup_row3 != null && tHash_Lookup_row3.getCount(row3HashKey) > 1) { // G 071

								// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row3'
								// and it contains more one result from keys : row3.track_id = '" +
								// row3HashKey.track_id + "'");
							} // G 071

							row3Struct row3 = null;

							row3Struct fromLookup_row3 = null;
							row3 = row3Default;

							if (tHash_Lookup_row3 != null && tHash_Lookup_row3.hasNext()) { // G 099

								fromLookup_row3 = tHash_Lookup_row3.next();

							} // G 099

							if (fromLookup_row3 != null) {
								row3 = fromLookup_row3;
							}

							// ###############################
							{ // start of Var scope

								// ###############################
								// # Vars tables

								Var__tMap_2__Struct Var = Var__tMap_2;// ###############################
								// ###############################
								// # Output tables

								cp2 = null;

								if (!rejectedInnerJoin_tMap_2) {

// # Output table : 'cp2'
									cp2_tmp.track_id = row3.track_id;
									cp2_tmp.artist_id = cp1.artist_id;
									cp2_tmp.album_id = row3.album_id;
									cp2_tmp.disc_number = cp1.disc_number;
									cp2_tmp.duration = cp1.duration;
									cp2_tmp.explicit = cp1.explicit;
									cp2_tmp.audio_feature_id = cp1.audio_feature_id;
									cp2_tmp.name = cp1.name;
									cp2_tmp.track_number = cp1.track_number;
									cp2_tmp.popularity = cp1.popularity;
									cp2 = cp2_tmp;
								} // closing inner join bracket (2)
// ###############################

							} // end of Var scope

							rejectedInnerJoin_tMap_2 = false;

							tos_count_tMap_2++;

							/**
							 * [tMap_2 main ] stop
							 */

							/**
							 * [tMap_2 process_data_begin ] start
							 */

							currentComponent = "tMap_2";

							/**
							 * [tMap_2 process_data_begin ] stop
							 */
// Start of branch "cp2"
							if (cp2 != null) {

								/**
								 * [tMap_3 main ] start
								 */

								currentComponent = "tMap_3";

								boolean hasCasePrimitiveKeyWithNull_tMap_3 = false;

								// ###############################
								// # Input tables (lookups)
								boolean rejectedInnerJoin_tMap_3 = false;
								boolean mainRowRejected_tMap_3 = false;

								///////////////////////////////////////////////
								// Starting Lookup Table "row4"
								///////////////////////////////////////////////

								boolean forceLooprow4 = false;

								row4Struct row4ObjectFromLookup = null;

								if (!rejectedInnerJoin_tMap_3) { // G_TM_M_020

									hasCasePrimitiveKeyWithNull_tMap_3 = false;

									row4HashKey.album_id = cp2.album_id;

									row4HashKey.hashCodeDirty = true;

									tHash_Lookup_row4.lookup(row4HashKey);

									if (!tHash_Lookup_row4.hasNext()) { // G_TM_M_090

										rejectedInnerJoin_tMap_3 = true;

									} // G_TM_M_090

								} // G_TM_M_020

								if (tHash_Lookup_row4 != null && tHash_Lookup_row4.getCount(row4HashKey) > 1) { // G 071

									// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row4'
									// and it contains more one result from keys : row4.album_id = '" +
									// row4HashKey.album_id + "'");
								} // G 071

								row4Struct row4 = null;

								row4Struct fromLookup_row4 = null;
								row4 = row4Default;

								if (tHash_Lookup_row4 != null && tHash_Lookup_row4.hasNext()) { // G 099

									fromLookup_row4 = tHash_Lookup_row4.next();

								} // G 099

								if (fromLookup_row4 != null) {
									row4 = fromLookup_row4;
								}

								// ###############################
								{ // start of Var scope

									// ###############################
									// # Vars tables

									Var__tMap_3__Struct Var = Var__tMap_3;// ###############################
									// ###############################
									// # Output tables

									cp3 = null;

									if (!rejectedInnerJoin_tMap_3) {

// # Output table : 'cp3'
										cp3_tmp.track_id = cp2.track_id;
										cp3_tmp.artist_id = cp2.artist_id;
										cp3_tmp.album_id = row4.album_id;
										cp3_tmp.release_date_id = row4.release_date_id;
										cp3_tmp.audio_feature_id = cp2.audio_feature_id;
										cp3_tmp.duration = cp2.duration;
										cp3_tmp.explicit = cp2.explicit;
										cp3_tmp.disc_number = cp2.disc_number;
										cp3_tmp.name = cp2.name;
										cp3_tmp.track_number = cp2.track_number;
										cp3_tmp.popularity = cp2.popularity;
										cp3 = cp3_tmp;
									} // closing inner join bracket (2)
// ###############################

								} // end of Var scope

								rejectedInnerJoin_tMap_3 = false;

								tos_count_tMap_3++;

								/**
								 * [tMap_3 main ] stop
								 */

								/**
								 * [tMap_3 process_data_begin ] start
								 */

								currentComponent = "tMap_3";

								/**
								 * [tMap_3 process_data_begin ] stop
								 */
// Start of branch "cp3"
								if (cp3 != null) {

									/**
									 * [tMap_4 main ] start
									 */

									currentComponent = "tMap_4";

									boolean hasCasePrimitiveKeyWithNull_tMap_4 = false;

									// ###############################
									// # Input tables (lookups)
									boolean rejectedInnerJoin_tMap_4 = false;
									boolean mainRowRejected_tMap_4 = false;

									///////////////////////////////////////////////
									// Starting Lookup Table "row5"
									///////////////////////////////////////////////

									boolean forceLooprow5 = false;

									row5Struct row5ObjectFromLookup = null;

									if (!rejectedInnerJoin_tMap_4) { // G_TM_M_020

										hasCasePrimitiveKeyWithNull_tMap_4 = false;

										row5HashKey.track_id = cp3.track_id;

										row5HashKey.hashCodeDirty = true;

										tHash_Lookup_row5.lookup(row5HashKey);

										if (!tHash_Lookup_row5.hasNext()) { // G_TM_M_090

											rejectedInnerJoin_tMap_4 = true;

										} // G_TM_M_090

									} // G_TM_M_020

									if (tHash_Lookup_row5 != null && tHash_Lookup_row5.getCount(row5HashKey) > 1) { // G
																													// 071

										// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row5'
										// and it contains more one result from keys : row5.track_id = '" +
										// row5HashKey.track_id + "'");
									} // G 071

									row5Struct row5 = null;

									row5Struct fromLookup_row5 = null;
									row5 = row5Default;

									if (tHash_Lookup_row5 != null && tHash_Lookup_row5.hasNext()) { // G 099

										fromLookup_row5 = tHash_Lookup_row5.next();

									} // G 099

									if (fromLookup_row5 != null) {
										row5 = fromLookup_row5;
									}

									// ###############################
									{ // start of Var scope

										// ###############################
										// # Vars tables

										Var__tMap_4__Struct Var = Var__tMap_4;// ###############################
										// ###############################
										// # Output tables

										found = null;
										not_found = null;

										if (!rejectedInnerJoin_tMap_4) {

// # Output table : 'found'
											found_tmp.track_id = row5.track_id;
											found_tmp.artist_id = cp3.artist_id;
											found_tmp.album_id = cp3.album_id;
											found_tmp.audio_feature_id = Relational.ISNULL(cp3.audio_feature_id)
													? "Is NULL"
													: cp3.audio_feature_id;
											found_tmp.release_date_id = cp3.release_date_id;
											found_tmp.track_name = cp3.name;
											found_tmp.track_total_duration = cp3.duration;
											found_tmp.track_popularity = cp3.popularity;
											found_tmp.explicit = cp3.explicit;
											found_tmp.track_number = cp3.track_number;
											found_tmp.disc_number = cp3.disc_number;
											found = found_tmp;
										} // closing inner join bracket (1)
// ###### START REJECTS ##### 

// # Output reject table : 'not_found'
// # Filter conditions 
										if (rejectedInnerJoin_tMap_4) {
											not_found_tmp.track_id = cp3.track_id;
											not_found_tmp.artist_id = cp3.artist_id;
											not_found_tmp.album_id = cp3.album_id;
											not_found_tmp.audio_feature_id = cp3.audio_feature_id;
											not_found_tmp.release_date_id = cp3.release_date_id;
											not_found_tmp.track_name = cp3.name;
											not_found_tmp.track_total_duration = cp3.duration;
											not_found_tmp.track_popularity = cp3.popularity;
											not_found_tmp.explicit = cp3.explicit;
											not_found_tmp.track_number = cp3.track_number;
											not_found_tmp.disc_number = cp3.disc_number;
											not_found = not_found_tmp;
										} // closing filter/reject
// ###############################

									} // end of Var scope

									rejectedInnerJoin_tMap_4 = false;

									tos_count_tMap_4++;

									/**
									 * [tMap_4 main ] stop
									 */

									/**
									 * [tMap_4 process_data_begin ] start
									 */

									currentComponent = "tMap_4";

									/**
									 * [tMap_4 process_data_begin ] stop
									 */
// Start of branch "found"
									if (found != null) {

										/**
										 * [tDBOutput_1 main ] start
										 */

										currentComponent = "tDBOutput_1";

										whetherReject_tDBOutput_1 = false;
										if (found.track_id == null) {
											pstmt_tDBOutput_1.setNull(1, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_1.setString(1, found.track_id);
										}

										if (found.artist_id == null) {
											pstmt_tDBOutput_1.setNull(2, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_1.setString(2, found.artist_id);
										}

										if (found.album_id == null) {
											pstmt_tDBOutput_1.setNull(3, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_1.setString(3, found.album_id);
										}

										if (found.audio_feature_id == null) {
											pstmt_tDBOutput_1.setNull(4, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_1.setString(4, found.audio_feature_id);
										}

										if (found.release_date_id == null) {
											pstmt_tDBOutput_1.setNull(5, java.sql.Types.INTEGER);
										} else {
											pstmt_tDBOutput_1.setInt(5, found.release_date_id);
										}

										if (found.track_name == null) {
											pstmt_tDBOutput_1.setNull(6, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_1.setString(6, found.track_name);
										}

										if (found.track_total_duration == null) {
											pstmt_tDBOutput_1.setNull(7, java.sql.Types.INTEGER);
										} else {
											pstmt_tDBOutput_1.setLong(7, found.track_total_duration);
										}

										if (found.track_popularity == null) {
											pstmt_tDBOutput_1.setNull(8, java.sql.Types.INTEGER);
										} else {
											pstmt_tDBOutput_1.setInt(8, found.track_popularity);
										}

										if (found.explicit == null) {
											pstmt_tDBOutput_1.setNull(9, java.sql.Types.INTEGER);
										} else {
											pstmt_tDBOutput_1.setInt(9, found.explicit);
										}

										if (found.track_number == null) {
											pstmt_tDBOutput_1.setNull(10, java.sql.Types.INTEGER);
										} else {
											pstmt_tDBOutput_1.setInt(10, found.track_number);
										}

										if (found.disc_number == null) {
											pstmt_tDBOutput_1.setNull(11, java.sql.Types.INTEGER);
										} else {
											pstmt_tDBOutput_1.setInt(11, found.disc_number);
										}

										pstmt_tDBOutput_1.addBatch();
										nb_line_tDBOutput_1++;

										batchSizeCounter_tDBOutput_1++;

										if ((batchSize_tDBOutput_1 > 0)
												&& (batchSize_tDBOutput_1 <= batchSizeCounter_tDBOutput_1)) {
											try {
												int countSum_tDBOutput_1 = 0;

												for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
													countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0
															: countEach_tDBOutput_1);
												}
												rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

												insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

												batchSizeCounter_tDBOutput_1 = 0;
											} catch (java.sql.BatchUpdateException e_tDBOutput_1) {
												globalMap.put("tDBOutput_1_ERROR_MESSAGE", e_tDBOutput_1.getMessage());
												java.sql.SQLException ne_tDBOutput_1 = e_tDBOutput_1.getNextException(),
														sqle_tDBOutput_1 = null;
												String errormessage_tDBOutput_1;
												if (ne_tDBOutput_1 != null) {
													// build new exception to provide the original cause
													sqle_tDBOutput_1 = new java.sql.SQLException(
															e_tDBOutput_1.getMessage() + "\ncaused by: "
																	+ ne_tDBOutput_1.getMessage(),
															ne_tDBOutput_1.getSQLState(), ne_tDBOutput_1.getErrorCode(),
															ne_tDBOutput_1);
													errormessage_tDBOutput_1 = sqle_tDBOutput_1.getMessage();
												} else {
													errormessage_tDBOutput_1 = e_tDBOutput_1.getMessage();
												}

												int countSum_tDBOutput_1 = 0;
												for (int countEach_tDBOutput_1 : e_tDBOutput_1.getUpdateCounts()) {
													countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0
															: countEach_tDBOutput_1);
												}
												rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

												insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

												System.err.println(errormessage_tDBOutput_1);

											}
										}

										tos_count_tDBOutput_1++;

										/**
										 * [tDBOutput_1 main ] stop
										 */

										/**
										 * [tDBOutput_1 process_data_begin ] start
										 */

										currentComponent = "tDBOutput_1";

										/**
										 * [tDBOutput_1 process_data_begin ] stop
										 */

										/**
										 * [tDBOutput_1 process_data_end ] start
										 */

										currentComponent = "tDBOutput_1";

										/**
										 * [tDBOutput_1 process_data_end ] stop
										 */

									} // End of branch "found"

// Start of branch "not_found"
									if (not_found != null) {

										/**
										 * [tDBOutput_2 main ] start
										 */

										currentComponent = "tDBOutput_2";

										whetherReject_tDBOutput_2 = false;
										if (not_found.track_id == null) {
											pstmt_tDBOutput_2.setNull(1, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_2.setString(1, not_found.track_id);
										}

										if (not_found.artist_id == null) {
											pstmt_tDBOutput_2.setNull(2, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_2.setString(2, not_found.artist_id);
										}

										if (not_found.album_id == null) {
											pstmt_tDBOutput_2.setNull(3, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_2.setString(3, not_found.album_id);
										}

										if (not_found.audio_feature_id == null) {
											pstmt_tDBOutput_2.setNull(4, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_2.setString(4, not_found.audio_feature_id);
										}

										if (not_found.release_date_id == null) {
											pstmt_tDBOutput_2.setNull(5, java.sql.Types.INTEGER);
										} else {
											pstmt_tDBOutput_2.setInt(5, not_found.release_date_id);
										}

										if (not_found.track_name == null) {
											pstmt_tDBOutput_2.setNull(6, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_2.setString(6, not_found.track_name);
										}

										if (not_found.track_total_duration == null) {
											pstmt_tDBOutput_2.setNull(7, java.sql.Types.INTEGER);
										} else {
											pstmt_tDBOutput_2.setLong(7, not_found.track_total_duration);
										}

										if (not_found.track_popularity == null) {
											pstmt_tDBOutput_2.setNull(8, java.sql.Types.INTEGER);
										} else {
											pstmt_tDBOutput_2.setInt(8, not_found.track_popularity);
										}

										if (not_found.explicit == null) {
											pstmt_tDBOutput_2.setNull(9, java.sql.Types.INTEGER);
										} else {
											pstmt_tDBOutput_2.setInt(9, not_found.explicit);
										}

										if (not_found.track_number == null) {
											pstmt_tDBOutput_2.setNull(10, java.sql.Types.INTEGER);
										} else {
											pstmt_tDBOutput_2.setInt(10, not_found.track_number);
										}

										if (not_found.disc_number == null) {
											pstmt_tDBOutput_2.setNull(11, java.sql.Types.INTEGER);
										} else {
											pstmt_tDBOutput_2.setInt(11, not_found.disc_number);
										}

										pstmt_tDBOutput_2.addBatch();
										nb_line_tDBOutput_2++;

										batchSizeCounter_tDBOutput_2++;

										if ((batchSize_tDBOutput_2 > 0)
												&& (batchSize_tDBOutput_2 <= batchSizeCounter_tDBOutput_2)) {
											try {
												int countSum_tDBOutput_2 = 0;

												for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
													countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0
															: countEach_tDBOutput_2);
												}
												rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

												insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

												batchSizeCounter_tDBOutput_2 = 0;
											} catch (java.sql.BatchUpdateException e_tDBOutput_2) {
												globalMap.put("tDBOutput_2_ERROR_MESSAGE", e_tDBOutput_2.getMessage());
												java.sql.SQLException ne_tDBOutput_2 = e_tDBOutput_2.getNextException(),
														sqle_tDBOutput_2 = null;
												String errormessage_tDBOutput_2;
												if (ne_tDBOutput_2 != null) {
													// build new exception to provide the original cause
													sqle_tDBOutput_2 = new java.sql.SQLException(
															e_tDBOutput_2.getMessage() + "\ncaused by: "
																	+ ne_tDBOutput_2.getMessage(),
															ne_tDBOutput_2.getSQLState(), ne_tDBOutput_2.getErrorCode(),
															ne_tDBOutput_2);
													errormessage_tDBOutput_2 = sqle_tDBOutput_2.getMessage();
												} else {
													errormessage_tDBOutput_2 = e_tDBOutput_2.getMessage();
												}

												int countSum_tDBOutput_2 = 0;
												for (int countEach_tDBOutput_2 : e_tDBOutput_2.getUpdateCounts()) {
													countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0
															: countEach_tDBOutput_2);
												}
												rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

												insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

												System.err.println(errormessage_tDBOutput_2);

											}
										}

										tos_count_tDBOutput_2++;

										/**
										 * [tDBOutput_2 main ] stop
										 */

										/**
										 * [tDBOutput_2 process_data_begin ] start
										 */

										currentComponent = "tDBOutput_2";

										/**
										 * [tDBOutput_2 process_data_begin ] stop
										 */

										/**
										 * [tDBOutput_2 process_data_end ] start
										 */

										currentComponent = "tDBOutput_2";

										/**
										 * [tDBOutput_2 process_data_end ] stop
										 */

									} // End of branch "not_found"

									/**
									 * [tMap_4 process_data_end ] start
									 */

									currentComponent = "tMap_4";

									/**
									 * [tMap_4 process_data_end ] stop
									 */

								} // End of branch "cp3"

								/**
								 * [tMap_3 process_data_end ] start
								 */

								currentComponent = "tMap_3";

								/**
								 * [tMap_3 process_data_end ] stop
								 */

							} // End of branch "cp2"

							/**
							 * [tMap_2 process_data_end ] start
							 */

							currentComponent = "tMap_2";

							/**
							 * [tMap_2 process_data_end ] stop
							 */

						} // End of branch "cp1"

						/**
						 * [tMap_1 process_data_end ] start
						 */

						currentComponent = "tMap_1";

						/**
						 * [tMap_1 process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 process_data_end ] start
						 */

						currentComponent = "tDBInput_1";

						/**
						 * [tDBInput_1 process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 end ] start
						 */

						currentComponent = "tDBInput_1";

					}
				} finally {
					if (rs_tDBInput_1 != null) {
						rs_tDBInput_1.close();
					}
					if (stmt_tDBInput_1 != null) {
						stmt_tDBInput_1.close();
					}
				}
				globalMap.put("tDBInput_1_NB_LINE", nb_line_tDBInput_1);

				ok_Hash.put("tDBInput_1", true);
				end_Hash.put("tDBInput_1", System.currentTimeMillis());

				/**
				 * [tDBInput_1 end ] stop
				 */

				/**
				 * [tMap_1 end ] start
				 */

				currentComponent = "tMap_1";

// ###############################
// # Lookup hashes releasing
				if (tHash_Lookup_row2 != null) {
					tHash_Lookup_row2.endGet();
				}
				globalMap.remove("tHash_Lookup_row2");

// ###############################      

				ok_Hash.put("tMap_1", true);
				end_Hash.put("tMap_1", System.currentTimeMillis());

				/**
				 * [tMap_1 end ] stop
				 */

				/**
				 * [tMap_2 end ] start
				 */

				currentComponent = "tMap_2";

// ###############################
// # Lookup hashes releasing
				if (tHash_Lookup_row3 != null) {
					tHash_Lookup_row3.endGet();
				}
				globalMap.remove("tHash_Lookup_row3");

// ###############################      

				ok_Hash.put("tMap_2", true);
				end_Hash.put("tMap_2", System.currentTimeMillis());

				/**
				 * [tMap_2 end ] stop
				 */

				/**
				 * [tMap_3 end ] start
				 */

				currentComponent = "tMap_3";

// ###############################
// # Lookup hashes releasing
				if (tHash_Lookup_row4 != null) {
					tHash_Lookup_row4.endGet();
				}
				globalMap.remove("tHash_Lookup_row4");

// ###############################      

				ok_Hash.put("tMap_3", true);
				end_Hash.put("tMap_3", System.currentTimeMillis());

				/**
				 * [tMap_3 end ] stop
				 */

				/**
				 * [tMap_4 end ] start
				 */

				currentComponent = "tMap_4";

// ###############################
// # Lookup hashes releasing
				if (tHash_Lookup_row5 != null) {
					tHash_Lookup_row5.endGet();
				}
				globalMap.remove("tHash_Lookup_row5");

// ###############################      

				ok_Hash.put("tMap_4", true);
				end_Hash.put("tMap_4", System.currentTimeMillis());

				/**
				 * [tMap_4 end ] stop
				 */

				/**
				 * [tDBOutput_1 end ] start
				 */

				currentComponent = "tDBOutput_1";

				try {
					int countSum_tDBOutput_1 = 0;
					if (pstmt_tDBOutput_1 != null && batchSizeCounter_tDBOutput_1 > 0) {

						for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
							countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
						}
						rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

					}

					insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

				} catch (java.sql.BatchUpdateException e_tDBOutput_1) {
					globalMap.put("tDBOutput_1_ERROR_MESSAGE", e_tDBOutput_1.getMessage());
					java.sql.SQLException ne_tDBOutput_1 = e_tDBOutput_1.getNextException(), sqle_tDBOutput_1 = null;
					String errormessage_tDBOutput_1;
					if (ne_tDBOutput_1 != null) {
						// build new exception to provide the original cause
						sqle_tDBOutput_1 = new java.sql.SQLException(
								e_tDBOutput_1.getMessage() + "\ncaused by: " + ne_tDBOutput_1.getMessage(),
								ne_tDBOutput_1.getSQLState(), ne_tDBOutput_1.getErrorCode(), ne_tDBOutput_1);
						errormessage_tDBOutput_1 = sqle_tDBOutput_1.getMessage();
					} else {
						errormessage_tDBOutput_1 = e_tDBOutput_1.getMessage();
					}

					int countSum_tDBOutput_1 = 0;
					for (int countEach_tDBOutput_1 : e_tDBOutput_1.getUpdateCounts()) {
						countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
					}
					rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

					insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

					System.err.println(errormessage_tDBOutput_1);

				}

				if (pstmt_tDBOutput_1 != null) {

					pstmt_tDBOutput_1.close();
					resourceMap.remove("pstmt_tDBOutput_1");
				}
				resourceMap.put("statementClosed_tDBOutput_1", true);

				nb_line_deleted_tDBOutput_1 = nb_line_deleted_tDBOutput_1 + deletedCount_tDBOutput_1;
				nb_line_update_tDBOutput_1 = nb_line_update_tDBOutput_1 + updatedCount_tDBOutput_1;
				nb_line_inserted_tDBOutput_1 = nb_line_inserted_tDBOutput_1 + insertedCount_tDBOutput_1;
				nb_line_rejected_tDBOutput_1 = nb_line_rejected_tDBOutput_1 + rejectedCount_tDBOutput_1;

				globalMap.put("tDBOutput_1_NB_LINE", nb_line_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_UPDATED", nb_line_update_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_DELETED", nb_line_deleted_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_1);

				ok_Hash.put("tDBOutput_1", true);
				end_Hash.put("tDBOutput_1", System.currentTimeMillis());

				/**
				 * [tDBOutput_1 end ] stop
				 */

				/**
				 * [tDBOutput_2 end ] start
				 */

				currentComponent = "tDBOutput_2";

				try {
					int countSum_tDBOutput_2 = 0;
					if (pstmt_tDBOutput_2 != null && batchSizeCounter_tDBOutput_2 > 0) {

						for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
							countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
						}
						rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

					}

					insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

				} catch (java.sql.BatchUpdateException e_tDBOutput_2) {
					globalMap.put("tDBOutput_2_ERROR_MESSAGE", e_tDBOutput_2.getMessage());
					java.sql.SQLException ne_tDBOutput_2 = e_tDBOutput_2.getNextException(), sqle_tDBOutput_2 = null;
					String errormessage_tDBOutput_2;
					if (ne_tDBOutput_2 != null) {
						// build new exception to provide the original cause
						sqle_tDBOutput_2 = new java.sql.SQLException(
								e_tDBOutput_2.getMessage() + "\ncaused by: " + ne_tDBOutput_2.getMessage(),
								ne_tDBOutput_2.getSQLState(), ne_tDBOutput_2.getErrorCode(), ne_tDBOutput_2);
						errormessage_tDBOutput_2 = sqle_tDBOutput_2.getMessage();
					} else {
						errormessage_tDBOutput_2 = e_tDBOutput_2.getMessage();
					}

					int countSum_tDBOutput_2 = 0;
					for (int countEach_tDBOutput_2 : e_tDBOutput_2.getUpdateCounts()) {
						countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
					}
					rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

					insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

					System.err.println(errormessage_tDBOutput_2);

				}

				if (pstmt_tDBOutput_2 != null) {

					pstmt_tDBOutput_2.close();
					resourceMap.remove("pstmt_tDBOutput_2");
				}
				resourceMap.put("statementClosed_tDBOutput_2", true);

				nb_line_deleted_tDBOutput_2 = nb_line_deleted_tDBOutput_2 + deletedCount_tDBOutput_2;
				nb_line_update_tDBOutput_2 = nb_line_update_tDBOutput_2 + updatedCount_tDBOutput_2;
				nb_line_inserted_tDBOutput_2 = nb_line_inserted_tDBOutput_2 + insertedCount_tDBOutput_2;
				nb_line_rejected_tDBOutput_2 = nb_line_rejected_tDBOutput_2 + rejectedCount_tDBOutput_2;

				globalMap.put("tDBOutput_2_NB_LINE", nb_line_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_UPDATED", nb_line_update_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_DELETED", nb_line_deleted_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_2);

				ok_Hash.put("tDBOutput_2", true);
				end_Hash.put("tDBOutput_2", System.currentTimeMillis());

				/**
				 * [tDBOutput_2 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tDBInput_1:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			tDBCommit_1Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			throw error;
		} finally {

			// free memory for "tMap_4"
			globalMap.remove("tHash_Lookup_row5");

			// free memory for "tMap_3"
			globalMap.remove("tHash_Lookup_row4");

			// free memory for "tMap_2"
			globalMap.remove("tHash_Lookup_row3");

			// free memory for "tMap_1"
			globalMap.remove("tHash_Lookup_row2");

			try {

				/**
				 * [tDBInput_1 finally ] start
				 */

				currentComponent = "tDBInput_1";

				/**
				 * [tDBInput_1 finally ] stop
				 */

				/**
				 * [tMap_1 finally ] start
				 */

				currentComponent = "tMap_1";

				/**
				 * [tMap_1 finally ] stop
				 */

				/**
				 * [tMap_2 finally ] start
				 */

				currentComponent = "tMap_2";

				/**
				 * [tMap_2 finally ] stop
				 */

				/**
				 * [tMap_3 finally ] start
				 */

				currentComponent = "tMap_3";

				/**
				 * [tMap_3 finally ] stop
				 */

				/**
				 * [tMap_4 finally ] start
				 */

				currentComponent = "tMap_4";

				/**
				 * [tMap_4 finally ] stop
				 */

				/**
				 * [tDBOutput_1 finally ] start
				 */

				currentComponent = "tDBOutput_1";

				if (resourceMap.get("statementClosed_tDBOutput_1") == null) {
					java.sql.PreparedStatement pstmtToClose_tDBOutput_1 = null;
					if ((pstmtToClose_tDBOutput_1 = (java.sql.PreparedStatement) resourceMap
							.remove("pstmt_tDBOutput_1")) != null) {
						pstmtToClose_tDBOutput_1.close();
					}
				}

				/**
				 * [tDBOutput_1 finally ] stop
				 */

				/**
				 * [tDBOutput_2 finally ] start
				 */

				currentComponent = "tDBOutput_2";

				if (resourceMap.get("statementClosed_tDBOutput_2") == null) {
					java.sql.PreparedStatement pstmtToClose_tDBOutput_2 = null;
					if ((pstmtToClose_tDBOutput_2 = (java.sql.PreparedStatement) resourceMap
							.remove("pstmt_tDBOutput_2")) != null) {
						pstmtToClose_tDBOutput_2.close();
					}
				}

				/**
				 * [tDBOutput_2 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 1);
	}

	public void tDBCommit_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBCommit_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tDBCommit_1 begin ] start
				 */

				ok_Hash.put("tDBCommit_1", false);
				start_Hash.put("tDBCommit_1", System.currentTimeMillis());

				currentComponent = "tDBCommit_1";

				int tos_count_tDBCommit_1 = 0;

				/**
				 * [tDBCommit_1 begin ] stop
				 */

				/**
				 * [tDBCommit_1 main ] start
				 */

				currentComponent = "tDBCommit_1";

				java.sql.Connection conn_tDBCommit_1 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");
				if (conn_tDBCommit_1 != null && !conn_tDBCommit_1.isClosed()) {

					try {

						conn_tDBCommit_1.commit();

					} finally {

						conn_tDBCommit_1.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_tDBConnection_2"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

					}

				}

				tos_count_tDBCommit_1++;

				/**
				 * [tDBCommit_1 main ] stop
				 */

				/**
				 * [tDBCommit_1 process_data_begin ] start
				 */

				currentComponent = "tDBCommit_1";

				/**
				 * [tDBCommit_1 process_data_begin ] stop
				 */

				/**
				 * [tDBCommit_1 process_data_end ] start
				 */

				currentComponent = "tDBCommit_1";

				/**
				 * [tDBCommit_1 process_data_end ] stop
				 */

				/**
				 * [tDBCommit_1 end ] start
				 */

				currentComponent = "tDBCommit_1";

				ok_Hash.put("tDBCommit_1", true);
				end_Hash.put("tDBCommit_1", System.currentTimeMillis());

				/**
				 * [tDBCommit_1 end ] stop
				 */
			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			throw error;
		} finally {

			try {

				/**
				 * [tDBCommit_1 finally ] start
				 */

				currentComponent = "tDBCommit_1";

				/**
				 * [tDBCommit_1 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBCommit_1_SUBPROCESS_STATE", 1);
	}

	public static class row2Struct implements routines.system.IPersistableComparableLookupRow<row2Struct> {
		final static byte[] commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3 = new byte[0];
		static byte[] commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public String track_id;

		public String getTrack_id() {
			return this.track_id;
		}

		public String artist_id;

		public String getArtist_id() {
			return this.artist_id;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.track_id == null) ? 0 : this.track_id.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row2Struct other = (row2Struct) obj;

			if (this.track_id == null) {
				if (other.track_id != null)
					return false;

			} else if (!this.track_id.equals(other.track_id))

				return false;

			return true;
		}

		public void copyDataTo(row2Struct other) {

			other.track_id = this.track_id;
			other.artist_id = this.artist_id;

		}

		public void copyKeysDataTo(row2Struct other) {

			other.track_id = this.track_id;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				dis.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				unmarshaller.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.track_id = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.track_id = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.track_id, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.track_id, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.artist_id = readString(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.artist_id = readString(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				writeString(this.artist_id, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				writeString(this.artist_id, dos, objectOut);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("track_id=" + track_id);
			sb.append(",artist_id=" + artist_id);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row2Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.track_id, other.track_id);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row2Struct row2 = new row2Struct();

				/**
				 * [tAdvancedHash_row2 begin ] start
				 */

				ok_Hash.put("tAdvancedHash_row2", false);
				start_Hash.put("tAdvancedHash_row2", System.currentTimeMillis());

				currentComponent = "tAdvancedHash_row2";

				int tos_count_tAdvancedHash_row2 = 0;

				// connection name:row2
				// source node:tDBInput_2 - inputs:(after_tDBInput_1) outputs:(row2,row2) |
				// target node:tAdvancedHash_row2 - inputs:(row2) outputs:()
				// linked node: tMap_1 - inputs:(row1,row2) outputs:(cp1)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row2 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct> tHash_Lookup_row2 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row2Struct>getLookup(matchingModeEnum_row2);

				globalMap.put("tHash_Lookup_row2", tHash_Lookup_row2);

				/**
				 * [tAdvancedHash_row2 begin ] stop
				 */

				/**
				 * [tDBInput_2 begin ] start
				 */

				ok_Hash.put("tDBInput_2", false);
				start_Hash.put("tDBInput_2", System.currentTimeMillis());

				currentComponent = "tDBInput_2";

				int tos_count_tDBInput_2 = 0;

				int nb_line_tDBInput_2 = 0;
				java.sql.Connection conn_tDBInput_2 = null;
				conn_tDBInput_2 = (java.sql.Connection) globalMap.get("conn_tDBConnection_1");

				java.sql.Statement stmt_tDBInput_2 = conn_tDBInput_2.createStatement();

				String dbquery_tDBInput_2 = "SELECT \n  \"spotify_data\".\"r_track_artist\".\"track_id\", \n  \"spotify_data\".\"r_track_artist\".\"artist_id\"\nFROM "
						+ "\"spotify_data\".\"r_track_artist\"";

				globalMap.put("tDBInput_2_QUERY", dbquery_tDBInput_2);
				java.sql.ResultSet rs_tDBInput_2 = null;

				try {
					rs_tDBInput_2 = stmt_tDBInput_2.executeQuery(dbquery_tDBInput_2);
					java.sql.ResultSetMetaData rsmd_tDBInput_2 = rs_tDBInput_2.getMetaData();
					int colQtyInRs_tDBInput_2 = rsmd_tDBInput_2.getColumnCount();

					String tmpContent_tDBInput_2 = null;

					while (rs_tDBInput_2.next()) {
						nb_line_tDBInput_2++;

						if (colQtyInRs_tDBInput_2 < 1) {
							row2.track_id = null;
						} else {

							row2.track_id = routines.system.JDBCUtil.getString(rs_tDBInput_2, 1, false);
						}
						if (colQtyInRs_tDBInput_2 < 2) {
							row2.artist_id = null;
						} else {

							row2.artist_id = routines.system.JDBCUtil.getString(rs_tDBInput_2, 2, false);
						}

						/**
						 * [tDBInput_2 begin ] stop
						 */

						/**
						 * [tDBInput_2 main ] start
						 */

						currentComponent = "tDBInput_2";

						tos_count_tDBInput_2++;

						/**
						 * [tDBInput_2 main ] stop
						 */

						/**
						 * [tDBInput_2 process_data_begin ] start
						 */

						currentComponent = "tDBInput_2";

						/**
						 * [tDBInput_2 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row2 main ] start
						 */

						currentComponent = "tAdvancedHash_row2";

						row2Struct row2_HashRow = new row2Struct();

						row2_HashRow.track_id = row2.track_id;

						row2_HashRow.artist_id = row2.artist_id;

						tHash_Lookup_row2.put(row2_HashRow);

						tos_count_tAdvancedHash_row2++;

						/**
						 * [tAdvancedHash_row2 main ] stop
						 */

						/**
						 * [tAdvancedHash_row2 process_data_begin ] start
						 */

						currentComponent = "tAdvancedHash_row2";

						/**
						 * [tAdvancedHash_row2 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row2 process_data_end ] start
						 */

						currentComponent = "tAdvancedHash_row2";

						/**
						 * [tAdvancedHash_row2 process_data_end ] stop
						 */

						/**
						 * [tDBInput_2 process_data_end ] start
						 */

						currentComponent = "tDBInput_2";

						/**
						 * [tDBInput_2 process_data_end ] stop
						 */

						/**
						 * [tDBInput_2 end ] start
						 */

						currentComponent = "tDBInput_2";

					}
				} finally {
					if (rs_tDBInput_2 != null) {
						rs_tDBInput_2.close();
					}
					if (stmt_tDBInput_2 != null) {
						stmt_tDBInput_2.close();
					}
				}
				globalMap.put("tDBInput_2_NB_LINE", nb_line_tDBInput_2);

				ok_Hash.put("tDBInput_2", true);
				end_Hash.put("tDBInput_2", System.currentTimeMillis());

				/**
				 * [tDBInput_2 end ] stop
				 */

				/**
				 * [tAdvancedHash_row2 end ] start
				 */

				currentComponent = "tAdvancedHash_row2";

				tHash_Lookup_row2.endPut();

				ok_Hash.put("tAdvancedHash_row2", true);
				end_Hash.put("tAdvancedHash_row2", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row2 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_2 finally ] start
				 */

				currentComponent = "tDBInput_2";

				/**
				 * [tDBInput_2 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row2 finally ] start
				 */

				currentComponent = "tAdvancedHash_row2";

				/**
				 * [tAdvancedHash_row2 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_2_SUBPROCESS_STATE", 1);
	}

	public static class row3Struct implements routines.system.IPersistableComparableLookupRow<row3Struct> {
		final static byte[] commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3 = new byte[0];
		static byte[] commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public String album_id;

		public String getAlbum_id() {
			return this.album_id;
		}

		public String track_id;

		public String getTrack_id() {
			return this.track_id;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.track_id == null) ? 0 : this.track_id.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row3Struct other = (row3Struct) obj;

			if (this.track_id == null) {
				if (other.track_id != null)
					return false;

			} else if (!this.track_id.equals(other.track_id))

				return false;

			return true;
		}

		public void copyDataTo(row3Struct other) {

			other.album_id = this.album_id;
			other.track_id = this.track_id;

		}

		public void copyKeysDataTo(row3Struct other) {

			other.track_id = this.track_id;

		}

		private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				dis.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				unmarshaller.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.track_id = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.track_id = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.track_id, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.track_id, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.album_id = readString(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.album_id = readString(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				writeString(this.album_id, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				writeString(this.album_id, dos, objectOut);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("album_id=" + album_id);
			sb.append(",track_id=" + track_id);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row3Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.track_id, other.track_id);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_3Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_3_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row3Struct row3 = new row3Struct();

				/**
				 * [tAdvancedHash_row3 begin ] start
				 */

				ok_Hash.put("tAdvancedHash_row3", false);
				start_Hash.put("tAdvancedHash_row3", System.currentTimeMillis());

				currentComponent = "tAdvancedHash_row3";

				int tos_count_tAdvancedHash_row3 = 0;

				// connection name:row3
				// source node:tDBInput_3 - inputs:(after_tDBInput_1) outputs:(row3,row3) |
				// target node:tAdvancedHash_row3 - inputs:(row3) outputs:()
				// linked node: tMap_2 - inputs:(cp1,row3) outputs:(cp2)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row3 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct> tHash_Lookup_row3 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row3Struct>getLookup(matchingModeEnum_row3);

				globalMap.put("tHash_Lookup_row3", tHash_Lookup_row3);

				/**
				 * [tAdvancedHash_row3 begin ] stop
				 */

				/**
				 * [tDBInput_3 begin ] start
				 */

				ok_Hash.put("tDBInput_3", false);
				start_Hash.put("tDBInput_3", System.currentTimeMillis());

				currentComponent = "tDBInput_3";

				int tos_count_tDBInput_3 = 0;

				int nb_line_tDBInput_3 = 0;
				java.sql.Connection conn_tDBInput_3 = null;
				conn_tDBInput_3 = (java.sql.Connection) globalMap.get("conn_tDBConnection_1");

				java.sql.Statement stmt_tDBInput_3 = conn_tDBInput_3.createStatement();

				String dbquery_tDBInput_3 = "SELECT \n  \"spotify_data\".\"r_albums_tracks\".\"album_id\", \n  \"spotify_data\".\"r_albums_tracks\".\"track_id\"\nFROM "
						+ "\"spotify_data\".\"r_albums_tracks\"";

				globalMap.put("tDBInput_3_QUERY", dbquery_tDBInput_3);
				java.sql.ResultSet rs_tDBInput_3 = null;

				try {
					rs_tDBInput_3 = stmt_tDBInput_3.executeQuery(dbquery_tDBInput_3);
					java.sql.ResultSetMetaData rsmd_tDBInput_3 = rs_tDBInput_3.getMetaData();
					int colQtyInRs_tDBInput_3 = rsmd_tDBInput_3.getColumnCount();

					String tmpContent_tDBInput_3 = null;

					while (rs_tDBInput_3.next()) {
						nb_line_tDBInput_3++;

						if (colQtyInRs_tDBInput_3 < 1) {
							row3.album_id = null;
						} else {

							row3.album_id = routines.system.JDBCUtil.getString(rs_tDBInput_3, 1, false);
						}
						if (colQtyInRs_tDBInput_3 < 2) {
							row3.track_id = null;
						} else {

							row3.track_id = routines.system.JDBCUtil.getString(rs_tDBInput_3, 2, false);
						}

						/**
						 * [tDBInput_3 begin ] stop
						 */

						/**
						 * [tDBInput_3 main ] start
						 */

						currentComponent = "tDBInput_3";

						tos_count_tDBInput_3++;

						/**
						 * [tDBInput_3 main ] stop
						 */

						/**
						 * [tDBInput_3 process_data_begin ] start
						 */

						currentComponent = "tDBInput_3";

						/**
						 * [tDBInput_3 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row3 main ] start
						 */

						currentComponent = "tAdvancedHash_row3";

						row3Struct row3_HashRow = new row3Struct();

						row3_HashRow.album_id = row3.album_id;

						row3_HashRow.track_id = row3.track_id;

						tHash_Lookup_row3.put(row3_HashRow);

						tos_count_tAdvancedHash_row3++;

						/**
						 * [tAdvancedHash_row3 main ] stop
						 */

						/**
						 * [tAdvancedHash_row3 process_data_begin ] start
						 */

						currentComponent = "tAdvancedHash_row3";

						/**
						 * [tAdvancedHash_row3 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row3 process_data_end ] start
						 */

						currentComponent = "tAdvancedHash_row3";

						/**
						 * [tAdvancedHash_row3 process_data_end ] stop
						 */

						/**
						 * [tDBInput_3 process_data_end ] start
						 */

						currentComponent = "tDBInput_3";

						/**
						 * [tDBInput_3 process_data_end ] stop
						 */

						/**
						 * [tDBInput_3 end ] start
						 */

						currentComponent = "tDBInput_3";

					}
				} finally {
					if (rs_tDBInput_3 != null) {
						rs_tDBInput_3.close();
					}
					if (stmt_tDBInput_3 != null) {
						stmt_tDBInput_3.close();
					}
				}
				globalMap.put("tDBInput_3_NB_LINE", nb_line_tDBInput_3);

				ok_Hash.put("tDBInput_3", true);
				end_Hash.put("tDBInput_3", System.currentTimeMillis());

				/**
				 * [tDBInput_3 end ] stop
				 */

				/**
				 * [tAdvancedHash_row3 end ] start
				 */

				currentComponent = "tAdvancedHash_row3";

				tHash_Lookup_row3.endPut();

				ok_Hash.put("tAdvancedHash_row3", true);
				end_Hash.put("tAdvancedHash_row3", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row3 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_3 finally ] start
				 */

				currentComponent = "tDBInput_3";

				/**
				 * [tDBInput_3 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row3 finally ] start
				 */

				currentComponent = "tAdvancedHash_row3";

				/**
				 * [tAdvancedHash_row3 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_3_SUBPROCESS_STATE", 1);
	}

	public static class row4Struct implements routines.system.IPersistableComparableLookupRow<row4Struct> {
		final static byte[] commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3 = new byte[0];
		static byte[] commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public String album_id;

		public String getAlbum_id() {
			return this.album_id;
		}

		public String album_name;

		public String getAlbum_name() {
			return this.album_name;
		}

		public String album_type;

		public String getAlbum_type() {
			return this.album_type;
		}

		public Integer release_date_id;

		public Integer getRelease_date_id() {
			return this.release_date_id;
		}

		public Integer popularity;

		public Integer getPopularity() {
			return this.popularity;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.album_id == null) ? 0 : this.album_id.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row4Struct other = (row4Struct) obj;

			if (this.album_id == null) {
				if (other.album_id != null)
					return false;

			} else if (!this.album_id.equals(other.album_id))

				return false;

			return true;
		}

		public void copyDataTo(row4Struct other) {

			other.album_id = this.album_id;
			other.album_name = this.album_name;
			other.album_type = this.album_type;
			other.release_date_id = this.release_date_id;
			other.popularity = this.popularity;

		}

		public void copyKeysDataTo(row4Struct other) {

			other.album_id = this.album_id;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				dis.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				unmarshaller.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			Integer intReturn;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = unmarshaller.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.album_id = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.album_id = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.album_id, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.album_id, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.album_name = readString(dis, ois);

				this.album_type = readString(dis, ois);

				this.release_date_id = readInteger(dis, ois);

				this.popularity = readInteger(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.album_name = readString(dis, objectIn);

				this.album_type = readString(dis, objectIn);

				this.release_date_id = readInteger(dis, objectIn);

				this.popularity = readInteger(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				writeString(this.album_name, dos, oos);

				writeString(this.album_type, dos, oos);

				writeInteger(this.release_date_id, dos, oos);

				writeInteger(this.popularity, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				writeString(this.album_name, dos, objectOut);

				writeString(this.album_type, dos, objectOut);

				writeInteger(this.release_date_id, dos, objectOut);

				writeInteger(this.popularity, dos, objectOut);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("album_id=" + album_id);
			sb.append(",album_name=" + album_name);
			sb.append(",album_type=" + album_type);
			sb.append(",release_date_id=" + String.valueOf(release_date_id));
			sb.append(",popularity=" + String.valueOf(popularity));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row4Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.album_id, other.album_id);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_4Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_4_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row4Struct row4 = new row4Struct();

				/**
				 * [tAdvancedHash_row4 begin ] start
				 */

				ok_Hash.put("tAdvancedHash_row4", false);
				start_Hash.put("tAdvancedHash_row4", System.currentTimeMillis());

				currentComponent = "tAdvancedHash_row4";

				int tos_count_tAdvancedHash_row4 = 0;

				// connection name:row4
				// source node:tDBInput_4 - inputs:(after_tDBInput_1) outputs:(row4,row4) |
				// target node:tAdvancedHash_row4 - inputs:(row4) outputs:()
				// linked node: tMap_3 - inputs:(cp2,row4) outputs:(cp3)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row4 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row4Struct> tHash_Lookup_row4 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row4Struct>getLookup(matchingModeEnum_row4);

				globalMap.put("tHash_Lookup_row4", tHash_Lookup_row4);

				/**
				 * [tAdvancedHash_row4 begin ] stop
				 */

				/**
				 * [tDBInput_4 begin ] start
				 */

				ok_Hash.put("tDBInput_4", false);
				start_Hash.put("tDBInput_4", System.currentTimeMillis());

				currentComponent = "tDBInput_4";

				int tos_count_tDBInput_4 = 0;

				int nb_line_tDBInput_4 = 0;
				java.sql.Connection conn_tDBInput_4 = null;
				conn_tDBInput_4 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				java.sql.Statement stmt_tDBInput_4 = conn_tDBInput_4.createStatement();

				String dbquery_tDBInput_4 = "SELECT \n  \"spotify_dw\".\"albums\".\"album_id\", \n  \"spotify_dw\".\"albums\".\"album_name\", \n  \"spotify_dw\".\"albu"
						+ "ms\".\"album_type\", \n  \"spotify_dw\".\"albums\".\"release_date_id\", \n  \"spotify_dw\".\"albums\".\"popularity\"\nFROM "
						+ "\"spotify_dw\".\"albums\"";

				globalMap.put("tDBInput_4_QUERY", dbquery_tDBInput_4);
				java.sql.ResultSet rs_tDBInput_4 = null;

				try {
					rs_tDBInput_4 = stmt_tDBInput_4.executeQuery(dbquery_tDBInput_4);
					java.sql.ResultSetMetaData rsmd_tDBInput_4 = rs_tDBInput_4.getMetaData();
					int colQtyInRs_tDBInput_4 = rsmd_tDBInput_4.getColumnCount();

					String tmpContent_tDBInput_4 = null;

					while (rs_tDBInput_4.next()) {
						nb_line_tDBInput_4++;

						if (colQtyInRs_tDBInput_4 < 1) {
							row4.album_id = null;
						} else {

							row4.album_id = routines.system.JDBCUtil.getString(rs_tDBInput_4, 1, false);
						}
						if (colQtyInRs_tDBInput_4 < 2) {
							row4.album_name = null;
						} else {

							row4.album_name = routines.system.JDBCUtil.getString(rs_tDBInput_4, 2, false);
						}
						if (colQtyInRs_tDBInput_4 < 3) {
							row4.album_type = null;
						} else {

							row4.album_type = routines.system.JDBCUtil.getString(rs_tDBInput_4, 3, false);
						}
						if (colQtyInRs_tDBInput_4 < 4) {
							row4.release_date_id = null;
						} else {

							row4.release_date_id = rs_tDBInput_4.getInt(4);
							if (rs_tDBInput_4.wasNull()) {
								row4.release_date_id = null;
							}
						}
						if (colQtyInRs_tDBInput_4 < 5) {
							row4.popularity = null;
						} else {

							row4.popularity = rs_tDBInput_4.getInt(5);
							if (rs_tDBInput_4.wasNull()) {
								row4.popularity = null;
							}
						}

						/**
						 * [tDBInput_4 begin ] stop
						 */

						/**
						 * [tDBInput_4 main ] start
						 */

						currentComponent = "tDBInput_4";

						tos_count_tDBInput_4++;

						/**
						 * [tDBInput_4 main ] stop
						 */

						/**
						 * [tDBInput_4 process_data_begin ] start
						 */

						currentComponent = "tDBInput_4";

						/**
						 * [tDBInput_4 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row4 main ] start
						 */

						currentComponent = "tAdvancedHash_row4";

						row4Struct row4_HashRow = new row4Struct();

						row4_HashRow.album_id = row4.album_id;

						row4_HashRow.album_name = row4.album_name;

						row4_HashRow.album_type = row4.album_type;

						row4_HashRow.release_date_id = row4.release_date_id;

						row4_HashRow.popularity = row4.popularity;

						tHash_Lookup_row4.put(row4_HashRow);

						tos_count_tAdvancedHash_row4++;

						/**
						 * [tAdvancedHash_row4 main ] stop
						 */

						/**
						 * [tAdvancedHash_row4 process_data_begin ] start
						 */

						currentComponent = "tAdvancedHash_row4";

						/**
						 * [tAdvancedHash_row4 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row4 process_data_end ] start
						 */

						currentComponent = "tAdvancedHash_row4";

						/**
						 * [tAdvancedHash_row4 process_data_end ] stop
						 */

						/**
						 * [tDBInput_4 process_data_end ] start
						 */

						currentComponent = "tDBInput_4";

						/**
						 * [tDBInput_4 process_data_end ] stop
						 */

						/**
						 * [tDBInput_4 end ] start
						 */

						currentComponent = "tDBInput_4";

					}
				} finally {
					if (rs_tDBInput_4 != null) {
						rs_tDBInput_4.close();
					}
					if (stmt_tDBInput_4 != null) {
						stmt_tDBInput_4.close();
					}
				}
				globalMap.put("tDBInput_4_NB_LINE", nb_line_tDBInput_4);

				ok_Hash.put("tDBInput_4", true);
				end_Hash.put("tDBInput_4", System.currentTimeMillis());

				/**
				 * [tDBInput_4 end ] stop
				 */

				/**
				 * [tAdvancedHash_row4 end ] start
				 */

				currentComponent = "tAdvancedHash_row4";

				tHash_Lookup_row4.endPut();

				ok_Hash.put("tAdvancedHash_row4", true);
				end_Hash.put("tAdvancedHash_row4", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row4 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_4 finally ] start
				 */

				currentComponent = "tDBInput_4";

				/**
				 * [tDBInput_4 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row4 finally ] start
				 */

				currentComponent = "tAdvancedHash_row4";

				/**
				 * [tAdvancedHash_row4 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_4_SUBPROCESS_STATE", 1);
	}

	public static class row5Struct implements routines.system.IPersistableComparableLookupRow<row5Struct> {
		final static byte[] commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3 = new byte[0];
		static byte[] commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public String track_id;

		public String getTrack_id() {
			return this.track_id;
		}

		public String artist_id;

		public String getArtist_id() {
			return this.artist_id;
		}

		public String album_id;

		public String getAlbum_id() {
			return this.album_id;
		}

		public String audio_feature_id;

		public String getAudio_feature_id() {
			return this.audio_feature_id;
		}

		public Integer release_date_id;

		public Integer getRelease_date_id() {
			return this.release_date_id;
		}

		public String track_name;

		public String getTrack_name() {
			return this.track_name;
		}

		public Long track_total_duration;

		public Long getTrack_total_duration() {
			return this.track_total_duration;
		}

		public Integer track_popularity;

		public Integer getTrack_popularity() {
			return this.track_popularity;
		}

		public Integer explicit;

		public Integer getExplicit() {
			return this.explicit;
		}

		public Integer track_number;

		public Integer getTrack_number() {
			return this.track_number;
		}

		public Integer disc_number;

		public Integer getDisc_number() {
			return this.disc_number;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.track_id == null) ? 0 : this.track_id.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row5Struct other = (row5Struct) obj;

			if (this.track_id == null) {
				if (other.track_id != null)
					return false;

			} else if (!this.track_id.equals(other.track_id))

				return false;

			return true;
		}

		public void copyDataTo(row5Struct other) {

			other.track_id = this.track_id;
			other.artist_id = this.artist_id;
			other.album_id = this.album_id;
			other.audio_feature_id = this.audio_feature_id;
			other.release_date_id = this.release_date_id;
			other.track_name = this.track_name;
			other.track_total_duration = this.track_total_duration;
			other.track_popularity = this.track_popularity;
			other.explicit = this.explicit;
			other.track_number = this.track_number;
			other.disc_number = this.disc_number;

		}

		public void copyKeysDataTo(row5Struct other) {

			other.track_id = this.track_id;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJECT_IE7374_etl_tracks_v3.length) {
					if (length < 1024 && commonByteArray_PROJECT_IE7374_etl_tracks_v3.length == 0) {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[1024];
					} else {
						commonByteArray_PROJECT_IE7374_etl_tracks_v3 = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length);
				strReturn = new String(commonByteArray_PROJECT_IE7374_etl_tracks_v3, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				dis.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				unmarshaller.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			Integer intReturn;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = unmarshaller.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.track_id = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJECT_IE7374_etl_tracks_v3) {

				try {

					int length = 0;

					this.track_id = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.track_id, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.track_id, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.artist_id = readString(dis, ois);

				this.album_id = readString(dis, ois);

				this.audio_feature_id = readString(dis, ois);

				this.release_date_id = readInteger(dis, ois);

				this.track_name = readString(dis, ois);

				length = dis.readByte();
				if (length == -1) {
					this.track_total_duration = null;
				} else {
					this.track_total_duration = dis.readLong();
				}

				this.track_popularity = readInteger(dis, ois);

				this.explicit = readInteger(dis, ois);

				this.track_number = readInteger(dis, ois);

				this.disc_number = readInteger(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.artist_id = readString(dis, objectIn);

				this.album_id = readString(dis, objectIn);

				this.audio_feature_id = readString(dis, objectIn);

				this.release_date_id = readInteger(dis, objectIn);

				this.track_name = readString(dis, objectIn);

				length = objectIn.readByte();
				if (length == -1) {
					this.track_total_duration = null;
				} else {
					this.track_total_duration = objectIn.readLong();
				}

				this.track_popularity = readInteger(dis, objectIn);

				this.explicit = readInteger(dis, objectIn);

				this.track_number = readInteger(dis, objectIn);

				this.disc_number = readInteger(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				writeString(this.artist_id, dos, oos);

				writeString(this.album_id, dos, oos);

				writeString(this.audio_feature_id, dos, oos);

				writeInteger(this.release_date_id, dos, oos);

				writeString(this.track_name, dos, oos);

				if (this.track_total_duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.track_total_duration);
				}

				writeInteger(this.track_popularity, dos, oos);

				writeInteger(this.explicit, dos, oos);

				writeInteger(this.track_number, dos, oos);

				writeInteger(this.disc_number, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				writeString(this.artist_id, dos, objectOut);

				writeString(this.album_id, dos, objectOut);

				writeString(this.audio_feature_id, dos, objectOut);

				writeInteger(this.release_date_id, dos, objectOut);

				writeString(this.track_name, dos, objectOut);

				if (this.track_total_duration == null) {
					objectOut.writeByte(-1);
				} else {
					objectOut.writeByte(0);
					objectOut.writeLong(this.track_total_duration);
				}

				writeInteger(this.track_popularity, dos, objectOut);

				writeInteger(this.explicit, dos, objectOut);

				writeInteger(this.track_number, dos, objectOut);

				writeInteger(this.disc_number, dos, objectOut);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("track_id=" + track_id);
			sb.append(",artist_id=" + artist_id);
			sb.append(",album_id=" + album_id);
			sb.append(",audio_feature_id=" + audio_feature_id);
			sb.append(",release_date_id=" + String.valueOf(release_date_id));
			sb.append(",track_name=" + track_name);
			sb.append(",track_total_duration=" + String.valueOf(track_total_duration));
			sb.append(",track_popularity=" + String.valueOf(track_popularity));
			sb.append(",explicit=" + String.valueOf(explicit));
			sb.append(",track_number=" + String.valueOf(track_number));
			sb.append(",disc_number=" + String.valueOf(disc_number));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row5Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.track_id, other.track_id);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_5Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_5_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row5Struct row5 = new row5Struct();

				/**
				 * [tAdvancedHash_row5 begin ] start
				 */

				ok_Hash.put("tAdvancedHash_row5", false);
				start_Hash.put("tAdvancedHash_row5", System.currentTimeMillis());

				currentComponent = "tAdvancedHash_row5";

				int tos_count_tAdvancedHash_row5 = 0;

				// connection name:row5
				// source node:tDBInput_5 - inputs:(after_tDBInput_1) outputs:(row5,row5) |
				// target node:tAdvancedHash_row5 - inputs:(row5) outputs:()
				// linked node: tMap_4 - inputs:(cp3,row5) outputs:(found,not_found)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row5 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row5Struct> tHash_Lookup_row5 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row5Struct>getLookup(matchingModeEnum_row5);

				globalMap.put("tHash_Lookup_row5", tHash_Lookup_row5);

				/**
				 * [tAdvancedHash_row5 begin ] stop
				 */

				/**
				 * [tDBInput_5 begin ] start
				 */

				ok_Hash.put("tDBInput_5", false);
				start_Hash.put("tDBInput_5", System.currentTimeMillis());

				currentComponent = "tDBInput_5";

				int tos_count_tDBInput_5 = 0;

				int nb_line_tDBInput_5 = 0;
				java.sql.Connection conn_tDBInput_5 = null;
				conn_tDBInput_5 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				java.sql.Statement stmt_tDBInput_5 = conn_tDBInput_5.createStatement();

				String dbquery_tDBInput_5 = "SELECT \n  \"spotify_dw\".\"tracks_info\".\"track_id\", \n  \"spotify_dw\".\"tracks_info\".\"artist_id\", \n  \"spotify_dw"
						+ "\".\"tracks_info\".\"album_id\", \n  \"spotify_dw\".\"tracks_info\".\"audio_feature_id\", \n  \"spotify_dw\".\"tracks_info"
						+ "\".\"release_date_id\", \n  \"spotify_dw\".\"tracks_info\".\"track_name\", \n  \"spotify_dw\".\"tracks_info\".\"track_tota"
						+ "l_duration\", \n  \"spotify_dw\".\"tracks_info\".\"track_popularity\", \n  \"spotify_dw\".\"tracks_info\".\"explicit\", \n "
						+ " \"spotify_dw\".\"tracks_info\".\"track_number\", \n  \"spotify_dw\".\"tracks_info\".\"disc_number\"\nFROM \"spotify_dw\"."
						+ "\"tracks_info\"";

				globalMap.put("tDBInput_5_QUERY", dbquery_tDBInput_5);
				java.sql.ResultSet rs_tDBInput_5 = null;

				try {
					rs_tDBInput_5 = stmt_tDBInput_5.executeQuery(dbquery_tDBInput_5);
					java.sql.ResultSetMetaData rsmd_tDBInput_5 = rs_tDBInput_5.getMetaData();
					int colQtyInRs_tDBInput_5 = rsmd_tDBInput_5.getColumnCount();

					String tmpContent_tDBInput_5 = null;

					while (rs_tDBInput_5.next()) {
						nb_line_tDBInput_5++;

						if (colQtyInRs_tDBInput_5 < 1) {
							row5.track_id = null;
						} else {

							row5.track_id = routines.system.JDBCUtil.getString(rs_tDBInput_5, 1, false);
						}
						if (colQtyInRs_tDBInput_5 < 2) {
							row5.artist_id = null;
						} else {

							row5.artist_id = routines.system.JDBCUtil.getString(rs_tDBInput_5, 2, false);
						}
						if (colQtyInRs_tDBInput_5 < 3) {
							row5.album_id = null;
						} else {

							row5.album_id = routines.system.JDBCUtil.getString(rs_tDBInput_5, 3, false);
						}
						if (colQtyInRs_tDBInput_5 < 4) {
							row5.audio_feature_id = null;
						} else {

							row5.audio_feature_id = routines.system.JDBCUtil.getString(rs_tDBInput_5, 4, false);
						}
						if (colQtyInRs_tDBInput_5 < 5) {
							row5.release_date_id = null;
						} else {

							row5.release_date_id = rs_tDBInput_5.getInt(5);
							if (rs_tDBInput_5.wasNull()) {
								row5.release_date_id = null;
							}
						}
						if (colQtyInRs_tDBInput_5 < 6) {
							row5.track_name = null;
						} else {

							row5.track_name = routines.system.JDBCUtil.getString(rs_tDBInput_5, 6, false);
						}
						if (colQtyInRs_tDBInput_5 < 7) {
							row5.track_total_duration = null;
						} else {

							row5.track_total_duration = rs_tDBInput_5.getLong(7);
							if (rs_tDBInput_5.wasNull()) {
								row5.track_total_duration = null;
							}
						}
						if (colQtyInRs_tDBInput_5 < 8) {
							row5.track_popularity = null;
						} else {

							row5.track_popularity = rs_tDBInput_5.getInt(8);
							if (rs_tDBInput_5.wasNull()) {
								row5.track_popularity = null;
							}
						}
						if (colQtyInRs_tDBInput_5 < 9) {
							row5.explicit = null;
						} else {

							row5.explicit = rs_tDBInput_5.getInt(9);
							if (rs_tDBInput_5.wasNull()) {
								row5.explicit = null;
							}
						}
						if (colQtyInRs_tDBInput_5 < 10) {
							row5.track_number = null;
						} else {

							row5.track_number = rs_tDBInput_5.getInt(10);
							if (rs_tDBInput_5.wasNull()) {
								row5.track_number = null;
							}
						}
						if (colQtyInRs_tDBInput_5 < 11) {
							row5.disc_number = null;
						} else {

							row5.disc_number = rs_tDBInput_5.getInt(11);
							if (rs_tDBInput_5.wasNull()) {
								row5.disc_number = null;
							}
						}

						/**
						 * [tDBInput_5 begin ] stop
						 */

						/**
						 * [tDBInput_5 main ] start
						 */

						currentComponent = "tDBInput_5";

						tos_count_tDBInput_5++;

						/**
						 * [tDBInput_5 main ] stop
						 */

						/**
						 * [tDBInput_5 process_data_begin ] start
						 */

						currentComponent = "tDBInput_5";

						/**
						 * [tDBInput_5 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row5 main ] start
						 */

						currentComponent = "tAdvancedHash_row5";

						row5Struct row5_HashRow = new row5Struct();

						row5_HashRow.track_id = row5.track_id;

						row5_HashRow.artist_id = row5.artist_id;

						row5_HashRow.album_id = row5.album_id;

						row5_HashRow.audio_feature_id = row5.audio_feature_id;

						row5_HashRow.release_date_id = row5.release_date_id;

						row5_HashRow.track_name = row5.track_name;

						row5_HashRow.track_total_duration = row5.track_total_duration;

						row5_HashRow.track_popularity = row5.track_popularity;

						row5_HashRow.explicit = row5.explicit;

						row5_HashRow.track_number = row5.track_number;

						row5_HashRow.disc_number = row5.disc_number;

						tHash_Lookup_row5.put(row5_HashRow);

						tos_count_tAdvancedHash_row5++;

						/**
						 * [tAdvancedHash_row5 main ] stop
						 */

						/**
						 * [tAdvancedHash_row5 process_data_begin ] start
						 */

						currentComponent = "tAdvancedHash_row5";

						/**
						 * [tAdvancedHash_row5 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row5 process_data_end ] start
						 */

						currentComponent = "tAdvancedHash_row5";

						/**
						 * [tAdvancedHash_row5 process_data_end ] stop
						 */

						/**
						 * [tDBInput_5 process_data_end ] start
						 */

						currentComponent = "tDBInput_5";

						/**
						 * [tDBInput_5 process_data_end ] stop
						 */

						/**
						 * [tDBInput_5 end ] start
						 */

						currentComponent = "tDBInput_5";

					}
				} finally {
					if (rs_tDBInput_5 != null) {
						rs_tDBInput_5.close();
					}
					if (stmt_tDBInput_5 != null) {
						stmt_tDBInput_5.close();
					}
				}
				globalMap.put("tDBInput_5_NB_LINE", nb_line_tDBInput_5);

				ok_Hash.put("tDBInput_5", true);
				end_Hash.put("tDBInput_5", System.currentTimeMillis());

				/**
				 * [tDBInput_5 end ] stop
				 */

				/**
				 * [tAdvancedHash_row5 end ] start
				 */

				currentComponent = "tAdvancedHash_row5";

				tHash_Lookup_row5.endPut();

				ok_Hash.put("tAdvancedHash_row5", true);
				end_Hash.put("tAdvancedHash_row5", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row5 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_5 finally ] start
				 */

				currentComponent = "tDBInput_5";

				/**
				 * [tDBInput_5 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row5 finally ] start
				 */

				currentComponent = "tAdvancedHash_row5";

				/**
				 * [tAdvancedHash_row5 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_5_SUBPROCESS_STATE", 1);
	}

	public String resuming_logs_dir_path = null;
	public String resuming_checkpoint_path = null;
	public String parent_part_launcher = null;
	private String resumeEntryMethodName = null;
	private boolean globalResumeTicket = false;

	public boolean watch = false;
	// portStats is null, it means don't execute the statistics
	public Integer portStats = null;
	public int portTraces = 4334;
	public String clientHost;
	public String defaultClientHost = "localhost";
	public String contextStr = "Default";
	public boolean isDefaultContext = true;
	public String pid = "0";
	public String rootPid = null;
	public String fatherPid = null;
	public String fatherNode = null;
	public long startTime = 0;
	public boolean isChildJob = false;
	public String log4jLevel = "";

	private boolean enableLogStash;

	private boolean execStat = true;

	private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
		protected java.util.Map<String, String> initialValue() {
			java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
			threadRunResultMap.put("errorCode", null);
			threadRunResultMap.put("status", "");
			return threadRunResultMap;
		};
	};

	protected PropertiesWithType context_param = new PropertiesWithType();
	public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

	public String status = "";

	public static void main(String[] args) {
		final etl_tracks_v3 etl_tracks_v3Class = new etl_tracks_v3();

		int exitCode = etl_tracks_v3Class.runJobInTOS(args);

		System.exit(exitCode);
	}

	public String[][] runJob(String[] args) {

		int exitCode = runJobInTOS(args);
		String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

		return bufferValue;
	}

	public boolean hastBufferOutputComponent() {
		boolean hastBufferOutput = false;

		return hastBufferOutput;
	}

	public int runJobInTOS(String[] args) {
		// reset status
		status = "";

		String lastStr = "";
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--context_param")) {
				lastStr = arg;
			} else if (lastStr.equals("")) {
				evalParam(arg);
			} else {
				evalParam(lastStr + " " + arg);
				lastStr = "";
			}
		}
		enableLogStash = "true".equalsIgnoreCase(System.getProperty("audit.enabled"));

		if (clientHost == null) {
			clientHost = defaultClientHost;
		}

		if (pid == null || "0".equals(pid)) {
			pid = TalendString.getAsciiRandomString(6);
		}

		if (rootPid == null) {
			rootPid = pid;
		}
		if (fatherPid == null) {
			fatherPid = pid;
		} else {
			isChildJob = true;
		}

		boolean inOSGi = routines.system.BundleUtils.inOSGi();

		if (inOSGi) {
			java.util.Dictionary<String, Object> jobProperties = routines.system.BundleUtils.getJobProperties(jobName);

			if (jobProperties != null && jobProperties.get("context") != null) {
				contextStr = (String) jobProperties.get("context");
			}
		}

		try {
			// call job/subjob with an existing context, like: --context=production. if
			// without this parameter, there will use the default context instead.
			java.io.InputStream inContext = etl_tracks_v3.class.getClassLoader()
					.getResourceAsStream("project_ie7374/etl_tracks_v3_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = etl_tracks_v3.class.getClassLoader()
						.getResourceAsStream("config/contexts/" + contextStr + ".properties");
			}
			if (inContext != null) {
				try {
					// defaultProps is in order to keep the original context value
					if (context != null && context.isEmpty()) {
						defaultProps.load(inContext);
						context = new ContextProperties(defaultProps);
					}
				} finally {
					inContext.close();
				}
			} else if (!isDefaultContext) {
				// print info and job continue to run, for case: context_param is not empty.
				System.err.println("Could not find the context " + contextStr);
			}

			if (!context_param.isEmpty()) {
				context.putAll(context_param);
				// set types for params from parentJobs
				for (Object key : context_param.keySet()) {
					String context_key = key.toString();
					String context_type = context_param.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
			}
			class ContextProcessing {
				private void processContext_0() {
				}

				public void processAllContext() {
					processContext_0();
				}
			}

			new ContextProcessing().processAllContext();
		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextStr);
			ie.printStackTrace();
		}

		// get context value from parent directly
		if (parentContextMap != null && !parentContextMap.isEmpty()) {
		}

		// Resume: init the resumeUtil
		resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
		resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
		resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
		// Resume: jobStart
		resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "",
				"", "", "", "", resumeUtil.convertToJsonText(context, parametersToEncrypt));

		java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
		globalMap.put("concurrentHashMap", concurrentHashMap);

		long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long endUsedMemory = 0;
		long end = 0;

		startTime = System.currentTimeMillis();

		this.globalResumeTicket = true;// to run tPreJob

		this.globalResumeTicket = false;// to run others jobs

		try {
			errorCode = null;
			tDBConnection_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tDBConnection_1) {
			globalMap.put("tDBConnection_1_SUBPROCESS_STATE", -1);

			e_tDBConnection_1.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out
					.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : etl_tracks_v3");
		}

		int returnCode = 0;

		if (errorCode == null) {
			returnCode = status != null && status.equals("failure") ? 1 : 0;
		} else {
			returnCode = errorCode.intValue();
		}
		resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "",
				"" + returnCode, "", "", "");

		return returnCode;

	}

	// only for OSGi env
	public void destroy() {
		closeSqlDbConnections();

	}

	private void closeSqlDbConnections() {
		try {
			Object obj_conn;
			obj_conn = globalMap.remove("conn_tDBConnection_1");
			if (null != obj_conn) {
				((java.sql.Connection) obj_conn).close();
			}
			obj_conn = globalMap.remove("conn_tDBConnection_2");
			if (null != obj_conn) {
				((java.sql.Connection) obj_conn).close();
			}
		} catch (java.lang.Exception e) {
		}
	}

	private java.util.Map<String, Object> getSharedConnections4REST() {
		java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();
		connections.put("conn_tDBConnection_1", globalMap.get("conn_tDBConnection_1"));
		connections.put("conn_tDBConnection_2", globalMap.get("conn_tDBConnection_2"));

		return connections;
	}

	private void evalParam(String arg) {
		if (arg.startsWith("--resuming_logs_dir_path")) {
			resuming_logs_dir_path = arg.substring(25);
		} else if (arg.startsWith("--resuming_checkpoint_path")) {
			resuming_checkpoint_path = arg.substring(27);
		} else if (arg.startsWith("--parent_part_launcher")) {
			parent_part_launcher = arg.substring(23);
		} else if (arg.startsWith("--watch")) {
			watch = true;
		} else if (arg.startsWith("--stat_port=")) {
			String portStatsStr = arg.substring(12);
			if (portStatsStr != null && !portStatsStr.equals("null")) {
				portStats = Integer.parseInt(portStatsStr);
			}
		} else if (arg.startsWith("--trace_port=")) {
			portTraces = Integer.parseInt(arg.substring(13));
		} else if (arg.startsWith("--client_host=")) {
			clientHost = arg.substring(14);
		} else if (arg.startsWith("--context=")) {
			contextStr = arg.substring(10);
			isDefaultContext = false;
		} else if (arg.startsWith("--father_pid=")) {
			fatherPid = arg.substring(13);
		} else if (arg.startsWith("--root_pid=")) {
			rootPid = arg.substring(11);
		} else if (arg.startsWith("--father_node=")) {
			fatherNode = arg.substring(14);
		} else if (arg.startsWith("--pid=")) {
			pid = arg.substring(6);
		} else if (arg.startsWith("--context_type")) {
			String keyValue = arg.substring(15);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.setContextType(keyValue.substring(0, index),
							replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1));
				}

			}

		} else if (arg.startsWith("--context_param")) {
			String keyValue = arg.substring(16);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
				}
			}
		} else if (arg.startsWith("--log4jLevel=")) {
			log4jLevel = arg.substring(13);
		} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {// for trunjob call
			final int equal = arg.indexOf('=');
			final String key = arg.substring("--".length(), equal);
			System.setProperty(key, arg.substring(equal + 1));
		}
	}

	private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

	private final String[][] escapeChars = { { "\\\\", "\\" }, { "\\n", "\n" }, { "\\'", "\'" }, { "\\r", "\r" },
			{ "\\f", "\f" }, { "\\b", "\b" }, { "\\t", "\t" } };

	private String replaceEscapeChars(String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0], currIndex);
				if (index >= 0) {

					result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0],
							strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left into the
			// result
			if (index < 0) {
				result.append(keyValue.substring(currIndex));
				currIndex = currIndex + keyValue.length();
			}
		}

		return result.toString();
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getStatus() {
		return status;
	}

	ResumeUtil resumeUtil = null;
}
