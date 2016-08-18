package com.servitization.embedder.core;

import com.servitization.metadata.define.XmlSerializer;
import com.servitization.metadata.define.embedder.ServiceDefine;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;

public class LocalBackupManager {

	private static final Logger LOG = LoggerFactory.getLogger(LocalBackupManager.class);

	private static final String fileName = "local_backup_metadata.xml";

	public static void backup(ServiceDefine sd) {
		OutputStream os = null;
		try {
			String path = LocalBackupManager.class.getClassLoader().getResource("").getPath();
			File file = new File(path + fileName);
			if (!file.exists())
				file.createNewFile();
			os = new FileOutputStream(file, false); // 非追加模式
			String xml = XmlSerializer.serialize(sd);
			System.out.println("noformat:" + xml);

			os.write(xml.getBytes());
		} catch (Exception e) {
			LOG.error(sd.getName() + "#Failed to backup the sd to local!", e);
		} finally {
			if (os != null)
				try {
					os.close();
				} catch (IOException e) {
					LOG.error(sd.getName() + "#Failed to close the " + fileName);
				}
		}
	}

	public static void backupFormate(ServiceDefine sd) {
		XMLWriter xmlWriter = null;
		FileWriter fileWriter = null;
		try {
			String path = LocalBackupManager.class.getClassLoader().getResource("").getPath();
			File file = new File(path + fileName);
			if (!file.exists())
				file.createNewFile();
			String xml = XmlSerializer.serialize(sd);
			Document document = DocumentHelper.parseText(xml);
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			format.setNewLineAfterDeclaration(false);
			fileWriter = new FileWriter(file, false);
			xmlWriter = new XMLWriter(fileWriter, format);
			xmlWriter.write(document);

		} catch (Exception e) {
			LOG.error(sd.getName() + "#Failed to backup the sd to local!", e);
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
					LOG.error(sd.getName() + "#Failed to close the " + fileName);
				}
			}
			if (xmlWriter != null) {
				try {
					xmlWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
					LOG.error(sd.getName() + "#Failed to close the " + fileName);
				}
			}
		}
	}

	public static ServiceDefine restore() {
		InputStream is = null;
		try {
			is = LocalBackupManager.class.getClassLoader().getResourceAsStream(fileName);
			if (is != null && is.available() > 0) {
				byte[] xmlb = new byte[is.available()];
				is.read(xmlb);
				String xml = new String(xmlb);
				if (xml.trim().length() > 0) {
					return XmlSerializer.deserialize(xml);
				}
			}
		} catch (Exception e) {
			LOG.error("Failed to restore the sd to local!", e);
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					LOG.error("Failed to close the " + fileName);
				}
		}
		return null;
	}
}
