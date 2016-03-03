package com.pragmasphere.hibernate;

import java.io.IOError;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor;

/**
 * Solución suboptima para la importación de datos con una codificación diferente a la del sistema.
 * Otra solución sería utilizar el parámetro javax.persistence.sql-load-script-source que admite un {@link java.io.Reader}
 *   
 * @author Toilal
 * @see http://stackoverflow.com/questions/8966030/hibernate-jpa-import-sql-utf8-characters-corrupted
 *
 */
public class CustomSqlExtractor extends MultipleLinesSqlCommandExtractor {

  /**
	 * @see java.io.Serializable 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String SOURCE_CHARSET = "UTF-8";

  @Override
  public String[] extractCommands(final Reader reader) {
      String[] lines = super.extractCommands(reader);

      Charset charset = Charset.defaultCharset();
      if (!charset.equals(Charset.forName(SOURCE_CHARSET))) {
          for (int i = 0; i < lines.length; i++) {
              try {
                  lines[i] = new String(lines[i].getBytes(), SOURCE_CHARSET);
              } catch (UnsupportedEncodingException e) {
                  throw new IOError(e);
              }
          }
      }

      return lines;
  }
}