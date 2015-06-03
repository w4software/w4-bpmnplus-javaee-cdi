package eu.w4.contrib.bpmnplus.cdi;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import eu.w4.common.configuration.ConfigurationParameter;
import eu.w4.common.exception.CheckedException;
import eu.w4.contrib.bpmnplus.apiextra.ExtraConfigurationParameter;
import eu.w4.contrib.bpmnplus.apiextra.ExtraEngineServiceFactory;
import eu.w4.engine.client.configuration.NetworkConfigurationParameter;
import eu.w4.engine.client.service.EngineService;

@Singleton
public class EngineClientFactory
{
  public static final String PROPERTY_FILE_NAME = "w4bpmnplus.properties";

  private volatile EngineService _service;
  private Map<ConfigurationParameter, String> _configurationParameters;

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
    _configurationParameters = new HashMap<ConfigurationParameter, String>();
    for (final NetworkConfigurationParameter configurationParameter : NetworkConfigurationParameter.values())
    {
      final String value = properties.getProperty(configurationParameter.name());
      if (value != null)
      {
        _configurationParameters.put(configurationParameter, value);
      }
    }
    for (final ExtraConfigurationParameter configurationParameter : ExtraConfigurationParameter.values())
    {
      final String value = properties.getProperty(configurationParameter.name());
      if (value != null)
      {
        _configurationParameters.put(configurationParameter, value);
      }
    }
  }

  private void buildService() throws CheckedException, RemoteException
  {
    synchronized (_configurationParameters)
    {
      if (_service == null)
      {
        _service = ExtraEngineServiceFactory.getEngineService(_configurationParameters);
      }
    }
  }

  @Produces
  public EngineService getService() throws CheckedException, RemoteException
  {
    if (_service == null)
    {
      buildService();
    }
    return _service;
  }

}
