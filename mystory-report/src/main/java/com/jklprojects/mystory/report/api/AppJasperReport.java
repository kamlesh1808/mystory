/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.report.api;

import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.i18n.I18N;
import com.jklprojects.mystory.common.report.ReportOutputFormat;
import com.jklprojects.mystory.common.util.TimeUtil;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.HtmlResourceHandler;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * An enum of Jasper Reports with APIs. It is responsible for configuration, and
 * generation of reports to desired output format such as HTML, PDF.
 *
 * <p>
 * Java 8 required {@link java.util.Base64}
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2016-09-19
 * @version 2, 2017-10-23
 * @version 3, 2017-11-23
 * @version 3, 2018-12-23
 */
public enum AppJasperReport implements IReportConsts {
	ACCOUNT_VERIFY("accountVerify", "account", ReportOutputFormat.HTML), ACCOUNT_VERIFIED("accountVerified", "account",
			ReportOutputFormat.HTML), ACCOUNT_RECOVERY("accountRecovery", "account",
					ReportOutputFormat.HTML), CONTACTUS_CREATED("contactUsCreated", "contactUs",
							ReportOutputFormat.HTML), CONTACTUS_REPLY("contactUsReply", "contactUs",
									ReportOutputFormat.HTML), STORY_CREATED("storyCreated", "story",
											ReportOutputFormat.HTML), STORY_REVIEW("storyReview", "story",
													ReportOutputFormat.HTML), STORY_REVIEWED("storyReviewed", "story",
															ReportOutputFormat.HTML), USERS_CREATED("usersCreated",
																	"user", ReportOutputFormat.PDF), STORIES_CREATED(
																			"storiesCreated", "story",
																			ReportOutputFormat.PDF), STORIES_VIEWS(
																					"storiesViews", "story",
																					ReportOutputFormat.PDF), PASSWORD_CHANGED(
																							"passwordChanged",
																							"account",
																							ReportOutputFormat.HTML), VERIFY_EMAIL_UPDATE(
																									"verifyEmailUpdate",
																									"account",
																									ReportOutputFormat.HTML);

	private static final XLogger logger = XLoggerFactory.getXLogger(AppJasperReport.class);

	private static final char forwardSeparatorChar = '/';
	private static final String REPORTS_ROOT = forwardSeparatorChar + "jasperreports";

	private Map<String, Object> reportParams = new HashMap<>();
	private final String reportName;
	private final String reportDirName;

	private final ReportOutputFormat reportOutputFormat;
	private JasperReport jasperReport;

	/**
	 * @param reportName
	 * @param reportDirName
	 * @param reportOutputFormat
	 */
	AppJasperReport(String reportName, String reportDirName, ReportOutputFormat reportOutputFormat) {
		this.reportName = reportName;
		this.reportDirName = reportDirName;
		this.reportOutputFormat = reportOutputFormat;

		reportParams = new HashMap<>();
		reportParams.put(JRParameter.REPORT_RESOURCE_BUNDLE, I18N.REPORT.getRBUsingBaseName());
	}

	/**
	 * Bind data source parameter name in the Jasper report to a value.
	 *
	 * @param dataSourceName
	 *            data source parameter name in the Jasper report
	 * @param reportBeans
	 */
	public void bindJRBeanCollectionDataSource(String dataSourceName, List<? extends AppReportBean> reportBeans) {
		if (getReportParams().containsKey(dataSourceName)) {
			getReportParams().replace(dataSourceName, new JRBeanCollectionDataSource(reportBeans));
		} else {
			getReportParams().put(dataSourceName, new JRBeanCollectionDataSource(reportBeans));
		}
	}

	/**
	 * Bind data source parameter name in the Jasper report to a value.
	 *
	 * @param dataSourceName
	 *            data source parameter name in the Jasper report
	 * @param reportBean
	 */
	public <T extends AppReportBean> void bindJRBeanCollectionDataSource(String dataSourceName, T reportBean) {
		bindJRBeanCollectionDataSource(dataSourceName, Arrays.asList(reportBean));
	}

	/**
	 * Bind report Image resource parameter.
	 *
	 * @param key
	 * @param resourceName
	 * @return
	 * @throws AppCodeException
	 */
	public AppJasperReport bindReportImageResourceParam(String key, String resourceName) throws AppCodeException {
		try {
			getReportParams().put(key, ImageIO.read(getClass().getResource(getResourcePath(resourceName))));
		} catch (IOException e) {
			logger.catching(e);
			throw ReportExceptions.REPORT_002.newAppCodeException();
		}
		return this;
	}

	/**
	 * Bind data source parameter name in the Jasper report to a value.
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public IReportConsts bindReportParam(String key, Object value) {
		getReportParams().put(key, value);
		return this;
	}

	/**
	 * Generate the report to file.
	 *
	 * @return report
	 * @throws AppCodeException
	 */
	public File generateReportToFile() throws AppCodeException {
		if (ReportOutputFormat.PDF == getReportOutputFormat()) {
			return exportToPDFFile();
		}
		return null;
	}

	/**
	 * Generates the report as a {@link String} output of
	 * {@link #getReportOutputFormat()} with report bean.
	 *
	 * @param reportBean
	 * @return report as a String
	 * @throws AppCodeException
	 */
	public <T extends AppReportBean> String generateReportToString(T reportBean) throws AppCodeException {
		String reportOutput = "";

		if (ReportOutputFormat.HTML == getReportOutputFormat()) {

			HtmlExporter exporter = new HtmlExporter();
			Map<String, String> images = new HashMap<>();
			SimpleHtmlExporterOutput htmlOutput = new SimpleHtmlExporterOutput(new StringWriter());
			exporter.setExporterOutput(htmlOutput);

			htmlOutput.setImageHandler(new HtmlResourceHandler() {

				@Override
				public String getResourcePath(String id) {
					return images.get(id);
				}

				@Override
				public void handleResource(String id, byte[] data) {
					String mimeType = "image/png";
					try {
						mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(data));
					} catch (IOException e) {
						logger.catching(e);
						logger.warn("Unable to get content type, so using the default {}", mimeType);
					}
					images.put(id, "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(data));
				}
			});

			try {
				exporter.setExporterInput(new SimpleExporterInput(fill(reportBean)));
				exporter.exportReport();
				reportOutput = htmlOutput.getWriter().toString();
			} catch (JRException e) {
				logger.catching(e);
				throw ReportExceptions.REPORT_001.newAppCodeException();
			}
		}
		return reportOutput;
	}

	public String getReportDirName() {
		return reportDirName;
	}

	public String getReportName() {
		return reportName;
	}

	public ReportOutputFormat getReportOutputFormat() {
		return reportOutputFormat;
	}

	public Map<String, Object> getReportParams() {
		return reportParams;
	}

	private File exportToPDFFile() throws AppCodeException {
		String reportFileName = getReportUniqueFileName();
		File pdfFile = new File(reportFileName);

		try {
			JasperExportManager.exportReportToPdfStream(fill(), new FileOutputStream(pdfFile));
			logger.info("Exported PDF file name is {}", reportFileName);
		} catch (FileNotFoundException | JRException e) {
			logger.catching(e);
			throw ReportExceptions.REPORT_001.newAppCodeException();
		}
		return pdfFile;
	}

	/**
	 * Fill the report with parameters.
	 *
	 * @return jasperPrint
	 * @throws JRException
	 */
	private JasperPrint fill() throws JRException {
		if (jasperReport == null) {
			jasperReport = (JasperReport) JRLoader.loadObject(getReportInputStream());
		}

		return JasperFillManager.fillReport(jasperReport, getReportParams(), new JREmptyDataSource());
	}

	/**
	 * Fill the report with the single report bean and parameters.
	 *
	 * @return jasperPrint
	 * @throws JRException
	 */
	private <T extends AppReportBean> JasperPrint fill(T reportBean) throws JRException {
		if (jasperReport == null) {
			jasperReport = (JasperReport) JRLoader.loadObject(getReportInputStream());
		}

		return JasperFillManager.fillReport(jasperReport, getReportParams(),
				new JRBeanCollectionDataSource(Arrays.asList(reportBean)));
	}

	private InputStream getReportInputStream() {
		return this.getClass().getResourceAsStream(REPORTS_ROOT + forwardSeparatorChar + getReportDirName()
				+ forwardSeparatorChar + getReportName() + ".jasper");
	}

	private String getReportUniqueFileName() {
		return getReportName() + "-" + TimeUtil.getUniqueFileIdentifier() + getReportOutputFormat().getFileExtension();
	}

	private String getResourcePath(String resourceName) {
		return REPORTS_ROOT + forwardSeparatorChar + resourceName;
	}
}
