package eu.w4.contrib.bpmnplus.cdi;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import eu.w4.common.configuration.ConfigurationParameter;
import eu.w4.common.exception.CheckedException;
import eu.w4.engine.client.configuration.NetworkConfigurationParameter;
import eu.w4.engine.client.service.EngineService;
import eu.w4.engine.client.service.EngineServiceFactory;

@Singleton
public class EngineClientFactory
{
  public static final String PROPERTY_FILE_NAME = "w4bpmnplus.properties";

  private EngineService _service;

  @PostConstruct
  public void construct()
    throws CheckedException, IOException
  {
    final Properties properties = new Properties();
    final ClassLoader classLoader = this.getClass().getClassLoader();
    final InputStream inputStream = classLoader.getResourceAsStream(PROPERTY_FILE_NAME);
    if (inputStream != null)
    {
      properties.load(inputStream);
    }
    final Map<ConfigurationParameter, String> configurationParameters = new HashMap<ConfigurationParameter, String>();
    for (final NetworkConfigurationParameter configurationParameter : NetworkConfigurationParameter.values())
    {
      final String value = properties.getProperty(configurationParameter.name());
      if (value != null)
      {
        configurationParameters.put(configurationParameter, value);
      }
    }

    _service = EngineServiceFactory.getEngineService(configurationParameters);
  }

  @Produces
  public EngineService getService()
  {
    return _service;
  }
}
